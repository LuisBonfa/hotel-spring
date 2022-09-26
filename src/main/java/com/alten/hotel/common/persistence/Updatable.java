package com.alten.hotel.common.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

/**
 * Some entities have the updated_at field, so this was created to
 * easily maintain this fields in entities;
 *
 * @author luis.bonfa
 */
@Getter
@Setter
@MappedSuperclass
public class Updatable extends Creatable {

    @Column(name = "updated_at")
    private Instant updatedAt;

    /**
     * Used to when the entity is going to be persisted or updated
     * the updated_at field is set automatically.
     */
    @PrePersist
    @PreUpdate
    public void setUpdateDate() {
        this.updatedAt = Instant.now();
    }
}
