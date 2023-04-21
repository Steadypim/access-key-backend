package com.example.accesskeybackend.template.mapper;

import com.example.accesskeybackend.template.dto.AccessKeyConfigDto;
import com.example.accesskeybackend.template.entity.AccessKeyConfig;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AccessKeyConfigMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accessKeyTemplates", ignore = true)
    AccessKeyConfig toEntity(AccessKeyConfigDto accessKeyConfigDto);

    AccessKeyConfigDto toDto(AccessKeyConfig accessKeyConfig);
}