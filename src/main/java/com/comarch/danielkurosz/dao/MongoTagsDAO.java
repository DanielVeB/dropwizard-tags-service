package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.UserTagsEntity;
import org.mongodb.morphia.Datastore;

import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;

import java.util.List;
import java.util.UUID;

public class MongoTagsDAO {

    private Datastore datastore;



    public MongoTagsDAO(Datastore datastore) {
        this.datastore = datastore;
    }

    public UserTagsEntity getUserTagsEntity(UUID userid) throws IndexOutOfBoundsException{

        Query<UserTagsEntity> query = this.datastore.createQuery(UserTagsEntity.class);

        query.field("clientId").equal(userid);
        return query.asList().get(0);

    }


}
