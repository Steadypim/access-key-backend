package com.example.accesskeybackend.template.entity;

import com.example.accesskeybackend.template.AuthType;
import com.example.accesskeybackend.template.StringListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "access_key_config")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessKeyConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    @Column(name = "server_tag", nullable = false, updatable = false)
    private String serverTag;
    @Column(name = "thread_limit", nullable = false, updatable = false)
    private Integer threadLimit;
    @Column(name = "max_linked_ips", nullable = false, updatable = false)
    private Integer maxLinkedIps;
    @Column(name = "ttl", nullable = false, updatable = false)
    private Duration ttl;
    @Column(name = "auth_type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private AuthType authType;
    @Column(name = "rotate_period", nullable = false, updatable = false)
    private Duration rotatePeriod;
    @Convert(converter = StringListConverter.class)
    @Column(name = "allowed_destinations", length = 1024, nullable = false, updatable = false)
    private List<String> allowedDestinations;
    @Convert(converter = StringListConverter.class)
    @Column(name = "disabled_destinations", length = 1024, nullable = false, updatable = false)
    private List<String> disabledDestinations;

    @ToString.Exclude
    @OneToMany(mappedBy = "accessKeyConfig")
    private Set<AccessKeyTemplate> accessKeyTemplates;

}