package com.edsh.is_lab1.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(EntityChangeListener.class)
public abstract class ChangeListenedEntity {
    public abstract Long getListenedId();
}
