package com.example.accesskeybackend.template.controller;

import com.example.accesskeybackend.template.Base64Util;
import com.example.accesskeybackend.template.dto.AccessKeyConfigDto;
import com.example.accesskeybackend.template.service.AccessKeyTemplateService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/template")
@AllArgsConstructor
public class AccessKeyTemplateController {

    private final AccessKeyTemplateService templateService;

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @SecurityRequirement(name = "authenticated")
    public String generateTemplates(
            @RequestParam(required = false, defaultValue = "") final String prefix,
            @RequestParam final int count,
            @Valid @RequestBody final AccessKeyConfigDto configDto
    ) {

        return templateService.generateTemplates(configDto, count).stream()
                .map(UUID::toString)
                .map(Base64Util::toBase64)
                .map(prefix::concat)
                .collect(Collectors.joining("\r\n"));
    }
}
