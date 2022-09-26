package com.alten.hotel.common.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.UUID;

/**
 * All entities will have the id column, so this was created to
 * easily maintain this fields in entities;
 *
 * @author luis.bonfa
 */
@Getter
@Setter
@MappedSuperclass
public abstract class Identifiable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Used to when the entity is going to be persisted
     * the id field is set automatically.
     */
    @PrePersist
    public void setId() {
        this.id = UUID.randomUUID();
    }
}
