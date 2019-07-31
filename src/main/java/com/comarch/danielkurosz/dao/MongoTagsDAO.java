package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.ClientTagsEntity;
import com.comarch.danielkurosz.data.Tag;
import com.mongodb.DuplicateKeyException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MongoTagsDAO {

    private Datastore datastore;


    public MongoTagsDAO(Datastore datastore) {
        this.datastore = datastore;
    }



    public List<Tag> getTags(UUID userid) throws IndexOutOfBoundsException {

        Query<ClientTagsEntity> query = this.datastore.createQuery(ClientTagsEntity.class);

        query.field("clientId").equal(userid);
        List<ClientTagsEntity> resultClients =  query.asList();
        List<Tag> tags = new LinkedList<>();
        for(ClientTagsEntity clientTagsEntity : resultClients){
            tags.add(clientTagsEntity.getTag());
        }
        return tags;
    }

    public List<UUID> getClientsId(List<String> withoutTags, List<String> withTags){
        Query<ClientTagsEntity> query = this.datastore.createQuery(ClientTagsEntity.class);

        //searching documents without passed tag_id
        for(String withoutTag : withoutTags){
            query = applyToQuery(query,"tags.tag_id",withoutTag,false);
        }
        // now searching documents with tag_id
        for (String withTag : withTags){
            query = applyToQuery(query,"tags.tag_id",withTag,true);
        }

        List<ClientTagsEntity> list = query.asList();
        List<UUID> uuids = new LinkedList<>();
        for(ClientTagsEntity entity : list){
            uuids.add(entity.getClientId());
        }
        return uuids;

    }

    public List<Tag> getTagsByClientID(UUID clientId, int limit, int skip){
        Query<ClientTagsEntity> query = this.datastore.createQuery(ClientTagsEntity.class);

        query.field("clientId").equal(clientId);

        List<ClientTagsEntity> clientTagsEntities = query.asList(new FindOptions().limit(limit).skip(skip));
        return clientTagsEntities.stream().map(Tag::new).collect(Collectors.toList());
    }


    public ClientTagsEntity create(ClientTagsEntity clientTagsEntity) throws DuplicateKeyException {
        datastore.save(clientTagsEntity);

        return clientTagsEntity;
    }

    void update(ClientTagsEntity userTagsEntity){


    }

    private UpdateOperations<ClientTagsEntity> pushTag(UpdateOperations<ClientTagsEntity> update, Tag tag){
        update.addToSet("tags", tag);
        return update;
    }


    private Query<ClientTagsEntity> applyToQuery(Query<ClientTagsEntity> query, String fieldName, String fieldValue, boolean equal){
        if(equal){
            return query.field(fieldName).equal(fieldValue);
        }else{
            return query.field(fieldName).notEqual(fieldValue);
        }
    }



}
