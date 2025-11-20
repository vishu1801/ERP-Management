package com.erp.studentInformationSystem.config;

import com.erp.studentInformationSystem.model.AuditableEntity;
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
