package com.example.accesskeybackend.template.controller;

import com.example.accesskeybackend.template.service.IPv6Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Map;

@Tag(name = "ipv6Controller", description = "Контроллер для проверки поддержки IPv6 сайтом")
@RestController
@RequestMapping("/api/web")
@AllArgsConstructor
public class IPv6Controller {

    private final IPv6Service ipv6Service;

    @Operation(summary = "Возвращает результат проверки")
    @GetMapping("/checkIpv6Support/")
    public @ResponseBody Map<String, Boolean> checkIpv6Support(@RequestParam("siteUrl") String siteUrl) {
        boolean supportsIpv6 = ipv6Service.checkIPv6Support(siteUrl);
        Map<String, Boolean> response = Collections.singletonMap("success", supportsIpv6);
        return ResponseEntity.ok().body(response).getBody();
    }
}