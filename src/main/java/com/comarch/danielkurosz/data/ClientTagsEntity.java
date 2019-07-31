package com.comarch.danielkurosz.data;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.UUID;

@Entity(value = "tags", noClassnameStored = true)
@Indexes({@Index(fields = {@Field("clientId")},options = @IndexOptions(unique = false)),
        @Index(fields = {@Field("clientId"), @Field("tag.tag_id")}, options = @IndexOptions(unique = true))})

public class ClientTagsEntity {

    @Id
    private ObjectId id;
    private UUID clientId;
    private Tag tag;


    public ClientTagsEntity() {
    }

    public ClientTagsEntity(UUID clientId, Tag tag) {
        this.clientId = clientId;
        this.tag = tag;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;

    }

}
