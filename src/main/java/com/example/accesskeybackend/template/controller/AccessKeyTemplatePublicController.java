package com.example.accesskeybackend.template.controller;

import com.example.accesskeybackend.template.Base64Util;
import com.example.accesskeybackend.template.dto.AccessKeyTemplateDto;
import com.example.accesskeybackend.template.service.AccessKeyTemplateService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/public/template")
@AllArgsConstructor
public class AccessKeyTemplatePublicController {

    private final AccessKeyTemplateService templateService;

    @GetMapping("/{templateId}")
    public AccessKeyTemplateDto templateById(
            @Parameter(description = "AccessKeyTemplate ID. UUID or Base64 encoded UUID")
            @PathVariable String templateId
    ) {
        templateId = detectAndParseBase64EncodedUUID(templateId);

        return templateService.templateById(templateId);
    }

    @PostMapping("/{templateId}/activate")
    public AccessKeyTemplateDto activate(
            @Parameter(description = "AccessKeyTemplate ID. UUID or Base64 encoded UUID")
            @PathVariable String templateId
    ) {
        templateId = detectAndParseBase64EncodedUUID(templateId);

        return templateService.activate(templateId);
    }


    private static String detectAndParseBase64EncodedUUID(final String templateId) {
        String parsedTemplateId = null;
        try {
            parsedTemplateId = UUID.fromString(templateId).toString();
        } catch (final IllegalArgumentException ignored1) {
            try {
                final String uuidFromBase64 = Base64Util.fromBase64(templateId);
                parsedTemplateId = UUID.fromString(uuidFromBase64).toString();
            } catch (final IllegalArgumentException ignored2) {
            }
        }

        if (parsedTemplateId == null)
            throw new com.example.accesskeybackend.exception.IllegalArgumentException(String.format(
                    "Bad templateId: %s", templateId
            ));

        return parsedTemplateId;
    }
}
