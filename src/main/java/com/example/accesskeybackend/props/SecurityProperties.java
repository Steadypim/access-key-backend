package com.example.accesskeybackend.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("access-key-backend.security")
public class SecurityProperties {
    private String adminUser;
    private String adminPwd;
}
