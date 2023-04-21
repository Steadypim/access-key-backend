package com.example.accesskeybackend.template.service;

import com.example.accesskeybackend.exception.AlreadyActivatedException;
import com.example.accesskeybackend.exception.NotFoundException;
import com.example.accesskeybackend.template.dto.AccessKeyConfigDto;
import com.example.accesskeybackend.template.dto.AccessKeyTemplateDto;
import com.example.accesskeybackend.template.entity.AccessKeyConfig;
import com.example.accesskeybackend.template.entity.AccessKeyTemplate;
import com.example.accesskeybackend.template.entity.ActivatedAccessKey;
import com.example.accesskeybackend.template.mapper.AccessKeyConfigMapper;
import com.example.accesskeybackend.template.mapper.AccessKeyTemplateMapper;
import com.example.accesskeybackend.template.repo.AccessKeyConfigRepository;
import com.example.accesskeybackend.template.repo.AccessKeyTemplateRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
@Log4j2
public class AccessKeyTemplateService {

    private final AccessKeyTemplateRepository templateRepo;
    private final AccessKeyTemplateMapper templateMapper;
    private final AccessKeyConfigRepository configRepo;
    private final AccessKeyConfigMapper configMapper;
    private final AccessKeyProviderService keyProvider;

    @Transactional(timeout = 60)
    public AccessKeyTemplateDto templateById(final String templateId) {
        final AccessKeyTemplate keyTemplate = findById(templateId);
        return templateMapper.toDto(keyTemplate);
    }

    @Transactional(timeout = 120)
    public AccessKeyTemplateDto activate(final String templateId) {
        final AccessKeyTemplate keyTemplate = findById(templateId);

        if (keyTemplate.getActivatedAccessKey() != null) {
            throw new AlreadyActivatedException(String.format(
                    "AccessKeyTemplate with id %s already activated",
                    templateId
            ));
        }

        final String generatedAccessKey = keyProvider.generateAccessKey(keyTemplate);
        keyTemplate.setActivatedAccessKey(
                ActivatedAccessKey.builder()
                        .accessKeyTemplate(keyTemplate)
                        .accessKeyUuid(generatedAccessKey)
                        .build()
        );

        final AccessKeyTemplate savedTemplate = templateRepo.saveAndFlush(keyTemplate);
        log.info("Successful activated template: {}", keyTemplate);
        return templateMapper.toDto(savedTemplate);
    }


    private AccessKeyTemplate findById(final String templateId) {
        return templateRepo.findById(UUID.fromString(templateId))
                .orElseThrow(() -> new NotFoundException(String.format(
                        "AccessKeyTemplate with id %s not found", templateId
                )));
    }

    public List<UUID> generateTemplates(final AccessKeyConfigDto configDto, final int count) {
        final AccessKeyConfig mappedKeyConfig = configMapper.toEntity(configDto);
        final AccessKeyConfig keyConfig = configRepo.save(mappedKeyConfig);

        final List<AccessKeyTemplate> keyTemplates =
                IntStream.range(0, count)
                        .mapToObj(ignored ->
                                AccessKeyTemplate.builder()
                                        .accessKeyConfig(keyConfig)
                                        .build()
                        )
                        .toList();

        return templateRepo.saveAllAndFlush(keyTemplates).stream()
                .map(AccessKeyTemplate::getId)
                .toList();
    }
}
