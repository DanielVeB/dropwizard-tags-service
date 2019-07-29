package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.TagEntity;
import com.comarch.danielkurosz.data.UserTagsEntity;
import com.mongodb.DuplicateKeyException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;
import java.util.UUID;

public class MongoTagsDAO {

    private Datastore datastore;


    public MongoTagsDAO(Datastore datastore) {
        this.datastore = datastore;
    }

    public UserTagsEntity getUserTagsEntity(UUID userid) throws IndexOutOfBoundsException {

        Query<UserTagsEntity> query = this.datastore.createQuery(UserTagsEntity.class);

        query.field("clientId").equal(userid);
        return query.asList().get(0);

    }

    public List<UUID> getClientsWithoutZodiacSign(){
        Query<UserTagsEntity> query = this.datastore.createQuery(UserTagsEntity.class);
        query.field("tags.tag_id").notEqual("zodiac");

        List<UserTagsEntity> list = query.asList();
        int i =1;
        for(UserTagsEntity u : list){
            System.out.println(i++ +"  " +u.getClientId());
        }
        return null;
    }




    public UserTagsEntity create(UserTagsEntity userTagsEntity) throws DuplicateKeyException {
        datastore.save(userTagsEntity);

        return userTagsEntity;
    }

    public void update(UserTagsEntity userTagsEntity){

        Query<UserTagsEntity> query = datastore.createQuery(UserTagsEntity.class).field("clientId").equal(userTagsEntity.getClientId());

        UpdateOperations<UserTagsEntity> update = datastore.createUpdateOperations(UserTagsEntity.class);

        for(TagEntity tag : userTagsEntity.getTagEntities()){
            update = pushTag(update,tag);
        }
        datastore.update(query,update);

    }

    private UpdateOperations<UserTagsEntity> pushTag(UpdateOperations<UserTagsEntity> update, TagEntity tag){
        update.addToSet("tags", tag);
        return update;
    }

}
