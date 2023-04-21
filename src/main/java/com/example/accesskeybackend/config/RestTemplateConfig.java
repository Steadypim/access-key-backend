package com.example.accesskeybackend.config;

import com.example.accesskeybackend.props.AccessKeyProviderProps;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder, final AccessKeyProviderProps props) {
        return builder
                .rootUri(props.getRootUri())
                .basicAuthentication(props.getAuthUser(), props.getAuthPwd())
                .defaultHeader("user-agent","replaced")
                .build();
    }
}
