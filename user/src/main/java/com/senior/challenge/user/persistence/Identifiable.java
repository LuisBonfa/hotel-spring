package com.senior.challenge.user.persistence;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class Identifiable {
    //All entities have the id column, this class is used to implement that in a easier way.
    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
}
