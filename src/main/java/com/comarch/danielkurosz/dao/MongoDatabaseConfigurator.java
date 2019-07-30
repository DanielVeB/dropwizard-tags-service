package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.ClientTagsEntity;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class MongoDatabaseConfigurator {

    public static Datastore configureMongo() {
        Datastore datastore;
        Morphia morphia = new Morphia();
        morphia.map(ClientTagsEntity.class);
        datastore = morphia.createDatastore(new MongoClient(), "dropwizard");
        datastore.ensureIndexes();
        return datastore;
    }


}
