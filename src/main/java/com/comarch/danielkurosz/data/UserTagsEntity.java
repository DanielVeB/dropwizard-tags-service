package com.comarch.danielkurosz.data;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.List;
import java.util.UUID;

@Entity("tags")
public class UserTagsEntity {

    @Id
    private UUID clientId;
    @Embedded
    private List<TagEntity> tagEntities;
}
