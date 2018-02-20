package com.dzmitryf.catalog.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public abstract class BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;

    @JsonIgnore
    private Date creatingDate;

    @JsonIgnore
    private Date editingDate;

    public void update(BaseEntity entity){
        if (entity != null) {
            setId(entity.getId());
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="simple_seq")
    @SequenceGenerator(name="simple_seq", sequenceName="simple_seq", allocationSize=1)
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATING_DATE", unique = false, nullable = true)
    public Date getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(Date creatingDate) {
        this.creatingDate = creatingDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "EDITING_DATE", unique = false, nullable = true)
    public Date getEditingDate() {
        return editingDate;
    }

    public void setEditingDate(Date editingDate) {
        this.editingDate = editingDate;
    }
}