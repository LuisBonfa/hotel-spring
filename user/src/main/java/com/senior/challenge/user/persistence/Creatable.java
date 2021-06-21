package com.senior.challenge.user.persistence;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class Creatable extends Identifiable {
    //Used for when the entity has the created_at field.

    @NotNull(message = "Data de criação é obrigatória")
    @Column(name="created_at", updatable = false)
    private Date createdAt;

    @PrePersist
    public void setCreationDate(){
        this.createdAt = new Date();
    }
}
