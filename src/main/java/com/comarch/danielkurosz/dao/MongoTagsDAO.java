package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.ClientTagsEntity;
import com.comarch.danielkurosz.dto.Tag;
import com.mongodb.DuplicateKeyException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class MongoTagsDAO {

    private Datastore datastore;


    public MongoTagsDAO(Datastore datastore) {
        this.datastore = datastore;
    }



    public ClientTagsEntity getUserTagsEntity(UUID userid) throws IndexOutOfBoundsException {

        Query<ClientTagsEntity> query = this.datastore.createQuery(ClientTagsEntity.class);

        query.field("clientId").equal(userid);
        return query.asList().get(0);

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




    public ClientTagsEntity create(ClientTagsEntity userTagsEntity) throws DuplicateKeyException {
        datastore.save(userTagsEntity);

        return userTagsEntity;
    }

    public void update(ClientTagsEntity userTagsEntity){

        Query<ClientTagsEntity> query = datastore.createQuery(ClientTagsEntity.class).field("clientId").equal(userTagsEntity.getClientId());

        UpdateOperations<ClientTagsEntity> update = datastore.createUpdateOperations(ClientTagsEntity.class);

        for(Tag tag : userTagsEntity.getTags()){
            update = pushTag(update,tag);
        }
        datastore.update(query,update);

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
