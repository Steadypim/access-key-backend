package com.example.accesskeybackend.template.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * A DTO for the {@link com.example.accesskeybackend.template.entity.AccessKeyTemplate} entity
 */
@Data
public class AccessKeyTemplateDto implements Serializable {
    private final UUID id;
    private final String base64Id;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private final Instant createdAt;
    private final AccessKeyConfigDto accessKeyConfig;
    private final ActivatedAccessKeyDto activatedAccessKey;

    /**
     * A DTO for the {@link com.example.accesskeybackend.template.entity.ActivatedAccessKey} entity
     */
    @Data
    public static class ActivatedAccessKeyDto implements Serializable {
        @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
        private final Instant createdAt;
        private final String accessKeyUuid;
    }
}