package com.example.accesskeybackend.template.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "activated_access_key")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivatedAccessKey {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @CreationTimestamp
    private Instant createdAt;

    @ToString.Exclude
    @OneToOne(mappedBy = "activatedAccessKey", optional = false, orphanRemoval = true)
    private AccessKeyTemplate accessKeyTemplate;

    @Column(name = "access_key_uuid", updatable = false, nullable = false, unique = true)
    private String accessKeyUuid;
}