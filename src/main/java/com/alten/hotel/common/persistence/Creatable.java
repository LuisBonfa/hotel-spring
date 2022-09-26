package com.alten.hotel.common.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * All entities have the created_at field, so this was created to
 * easily maintain this fields in entities;
 *
 * @author luis.bonfa
 */
@Getter
@Setter
@MappedSuperclass
public abstract class Creatable extends Identifiable {

    @NotNull(message = "Creation date is required")
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    /**
     * Used to when the entity is going to be persisted
     * the created_at field is set automatically.
     */
    @PrePersist
    public void setCreatedAt() {
        this.createdAt = Instant.now();
    }
}
