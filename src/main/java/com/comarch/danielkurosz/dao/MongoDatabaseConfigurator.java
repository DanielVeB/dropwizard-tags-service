package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.UserTagsEntity;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class MongoDatabaseConfigurator {

    public static MongoTagsDAO configureMongo() {
        Datastore datastore;
        Morphia morphia = new Morphia();
        morphia.map(UserTagsEntity.class);
        datastore = morphia.createDatastore(new MongoClient(), "dropwizard");
        datastore.ensureIndexes();
        return new MongoTagsDAO(datastore);
    }


}
