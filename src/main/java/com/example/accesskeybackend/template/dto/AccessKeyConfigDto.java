package com.example.accesskeybackend.template.dto;

import com.example.accesskeybackend.template.AuthType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link com.example.accesskeybackend.template.entity.AccessKeyConfig} entity
 */
@Data
@NoArgsConstructor
public class AccessKeyConfigDto implements Serializable {
    @NotEmpty
    @NotNull
    private String serverTag;
    @Min(1)
    @NotNull
    private Integer threadLimit;
    @Min(1)
    @Max(50)
    @NotNull
    private Integer maxLinkedIps;
    @NotNull
    @Schema(type = "string", format = "duration", example = "P3DT4H59M")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private Duration ttl;
    private AuthType authType = AuthType.BY_LOG_PWD;
    @Schema(type = "string", format = "duration", example = "P3DT4H59M")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private Duration rotatePeriod = Duration.ZERO;
    private List<String> allowedDestinations = new ArrayList<>();
    private List<String> disabledDestinations = new ArrayList<>();
}