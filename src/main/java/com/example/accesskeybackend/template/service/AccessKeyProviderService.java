package com.example.accesskeybackend.template.service;

import com.example.accesskeybackend.exception.AccessKeyProviderException;
import com.example.accesskeybackend.template.Base64Util;
import com.example.accesskeybackend.template.entity.AccessKeyConfig;
import com.example.accesskeybackend.template.entity.AccessKeyTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;
import java.util.function.Function;

@Service
@AllArgsConstructor
@Log4j2
public class AccessKeyProviderService {

    private static final Function<UUID, String> DESCRIPTION_GENERATOR = uuid ->
            String.format("Created by KEY-ACTIVATOR. Template id: %s", Base64Util.toBase64(uuid.toString()));

    private final RestTemplate restTemplate;

    public String generateAccessKey(final AccessKeyTemplate keyTemplate) {
        final AccessKeyConfig config = keyTemplate.getAccessKeyConfig();

        final UriComponentsBuilder componentsBuilder = UriComponentsBuilder.fromPath("/api/v5/key")
                .queryParam("serverTag", config.getServerTag())
                .queryParam("threadLimit", config.getThreadLimit())
                .queryParam("maxLinkedIps", config.getMaxLinkedIps())
                .queryParam("ttlSec", config.getTtl().toSeconds())
                .queryParam("rotatePeriodSec", config.getRotatePeriod().toSeconds())
                .queryParam("initAuthType", config.getAuthType().toString())
                .queryParam("description", DESCRIPTION_GENERATOR.apply(keyTemplate.getId()));

        if (config.getAllowedDestinations().size() > 0) {
            componentsBuilder.queryParam("allowedDestinations", config.getAllowedDestinations());
        }
        if (config.getDisabledDestinations().size() > 0) {
            componentsBuilder.queryParam("disabledDestinations", config.getDisabledDestinations());
        }

        final ResponseEntity<AccessKeyDto> response = restTemplate.exchange(
                componentsBuilder.build().toUriString(),
                HttpMethod.POST,
                null,
                AccessKeyDto.class
        );

        final AccessKeyDto body = response.getBody();
        if(!response.getStatusCode().is2xxSuccessful() || body == null) {
            throw new AccessKeyProviderException(String.format(
                    "Bad response from AccessKey API: %s", response
            ));
        }

        log.info("Successful generated AccessKey for template with id: {}", keyTemplate.getId());
        return body.key();
    }

    private record AccessKeyDto(String key) {
    }
}
