package com.comarch.danielkurosz.data;

import com.comarch.danielkurosz.dto.Tag;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.*;

import java.util.List;
import java.util.UUID;

@Entity(value = "tags", noClassnameStored = true)
@Indexes(@Index(fields = {@Field("clientId")}, options = @IndexOptions(unique = true)))

public class ClientTagsEntity {

    @Id
    private ObjectId id;
    private UUID clientId;
    @Embedded
    @NotEmpty
    private List<Tag> tags;


    public ClientTagsEntity() {
    }

    public ClientTagsEntity(UUID clientId, List<Tag> tags) {
        this.clientId = clientId;
        this.tags = tags;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tagEntities) {
        this.tags = tagEntities;

    }

}
