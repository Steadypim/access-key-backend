package com.example.accesskeybackend.template.repo;

import com.example.accesskeybackend.template.entity.AccessKeyTemplate;
import jakarta.persistence.LockModeType;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;
import java.util.UUID;

public interface AccessKeyTemplateRepository extends JpaRepository<AccessKeyTemplate, UUID> {
    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @NonNull
    Optional<AccessKeyTemplate> findById(@NonNull final UUID id);
}