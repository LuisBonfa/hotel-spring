package com.senior.challenge.user.persistence;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@Data
@MappedSuperclass
public class Updatable extends Creatable {
    //Used for when the entity has the updated_at field.
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    @PreUpdate
    public void setUpdateDate() {
        this.updatedAt = new Date();
    }
}
