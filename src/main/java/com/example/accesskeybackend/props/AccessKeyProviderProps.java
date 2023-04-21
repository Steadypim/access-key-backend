package com.example.accesskeybackend.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("access-key-backend.provider")
public class AccessKeyProviderProps {
    private String rootUri;
    private String authUser;
    private String authPwd;
}
