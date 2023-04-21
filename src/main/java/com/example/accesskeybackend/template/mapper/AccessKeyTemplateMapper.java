package com.example.accesskeybackend.template.mapper;

import com.example.accesskeybackend.template.dto.AccessKeyTemplateDto;
import com.example.accesskeybackend.template.entity.AccessKeyTemplate;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {AccessKeyConfigMapper.class})
public interface AccessKeyTemplateMapper {
    AccessKeyTemplate toEntity(AccessKeyTemplateDto accessKeyTemplateDto);

    @Mapping(target = "base64Id", expression = "java(com.example.accesskeybackend.template.Base64Util.toBase64(template.getId().toString()))")
    AccessKeyTemplateDto toDto(AccessKeyTemplate template);
}