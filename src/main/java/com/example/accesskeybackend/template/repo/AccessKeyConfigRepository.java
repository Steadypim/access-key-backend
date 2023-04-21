package com.example.accesskeybackend.template.repo;

import com.example.accesskeybackend.template.entity.AccessKeyConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessKeyConfigRepository extends JpaRepository<AccessKeyConfig, Long> {
}