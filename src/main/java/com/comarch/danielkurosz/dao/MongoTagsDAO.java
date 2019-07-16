package com.comarch.danielkurosz.dao;

import org.mongodb.morphia.Datastore;

import javax.xml.crypto.Data;

public class MongoTagsDAO {

    private Datastore datastore;

    public static void main(String[] args) {
        MongoTagsDAO mongoTagsDAO = MongoDatabaseConfigurator.configureMongo();
    }
    public MongoTagsDAO(Datastore datastore){
        this.datastore = datastore;
    }





}
