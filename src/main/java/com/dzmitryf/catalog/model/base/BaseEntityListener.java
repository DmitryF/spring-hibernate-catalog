package com.dzmitryf.catalog.model.base;

import com.dzmitryf.catalog.services.impl.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class BaseEntityListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEntityListener.class);

    @PrePersist
    public void prePersistBaseEntity(BaseEntity entity){
        if (entity != null && entity.getCreatingDate() == null) {
            Date creatinDate = new Date();
            entity.setCreatingDate(creatinDate);
            LOGGER.info("New entity creating date: {}", creatinDate);
        }
        updateEditingDate(entity);
    }

    @PreUpdate
    public void preUpdateBaseEntity(BaseEntity entity){
        updateEditingDate(entity);
    }

    private void updateEditingDate(BaseEntity entity){
        if (entity != null) {
            Date editingDate = new Date();
            entity.setEditingDate(editingDate);
            LOGGER.info("New entity editing date: {}", editingDate);
        }
    }
}
