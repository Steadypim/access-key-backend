package com.example.accesskeybackend.template.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "access_key_template")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessKeyTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @CreationTimestamp
    private Instant createdAt;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "access_key_config_id", nullable = false, updatable = false)
    private AccessKeyConfig accessKeyConfig;

    @ToString.Exclude
    @Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "activated_access_key_id")
    private ActivatedAccessKey activatedAccessKey;
}
