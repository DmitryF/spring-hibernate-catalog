package com.dzmitryf.catalog.model.base;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class BaseEntityListener {

    @PrePersist
    public void prePersistBaseEntity(BaseEntity entity){
        if (entity != null && entity.getCreatingDate() == null) {
            entity.setCreatingDate(new Date());
        }
        updateEditingDate(entity);
    }

    @PreUpdate
    public void preUpdateBaseEntity(BaseEntity entity){
        updateEditingDate(entity);
    }

    private void updateEditingDate(BaseEntity entity){
        if (entity != null) {
            entity.setEditingDate(new Date());
        }
    }
}
