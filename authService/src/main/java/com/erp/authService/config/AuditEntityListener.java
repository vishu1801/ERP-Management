package com.erp.authService.config;

import com.erp.authService.entity.AuditableEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class AuditEntityListener {

    @PrePersist
    public void prePersist(AuditableEntity entity) {
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setCreatedBy(RequestContext.getCurrentContext().getUserId());
    }

    @PreUpdate
    public void preUpdate(AuditableEntity entity) {
        LocalDateTime now = LocalDateTime.now();
        entity.setUpdatedAt(now);
        entity.setUpdatedBy(RequestContext.getCurrentContext().getUserId());
    }
}
