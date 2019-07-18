package com.comarch.danielkurosz.data;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.*;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity("tags")
@Indexes(@Index(fields = { @Field("clientId")}, options = @IndexOptions(unique = true)))

public class UserTagsEntity {

    @Id
    private ObjectId id;
    private UUID clientId;
    @Embedded
    @NotEmpty
    private List<TagEntity> tags;



    public UserTagsEntity(){}

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public List<TagEntity> getTagEntities() {
        return tags;
    }

    public void setTagEntities(List<TagEntity> tagEntities) {
        this.tags = tagEntities;

    }

}
