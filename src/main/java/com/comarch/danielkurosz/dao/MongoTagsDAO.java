package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.ClientTagEntity;
import com.comarch.danielkurosz.data.Tag;
import com.comarch.danielkurosz.dto.Statistic;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DuplicateKeyException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
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




    public List<UUID> getClientsId(List<String> withoutTags, List<String> withTags){
        Query<ClientTagEntity> query = this.datastore.createQuery(ClientTagEntity.class);

        //searching documents without passed tag_id
        for(String withoutTag : withoutTags){
            query = applyToQuery(query,"tags.tag_id",withoutTag,false);
        }
        // now searching documents with tag_id
        for (String withTag : withTags){
            query = applyToQuery(query,"tags.tag_id",withTag,true);
        }

        List<ClientTagEntity> list = query.asList();
        List<UUID> uuids = new LinkedList<>();
        for(ClientTagEntity entity : list){
            uuids.add(entity.getClientId());
        }
        return uuids;

    }

    public List<ClientTagEntity> getTagsByClientID(ClientTagEntity entity, int limit, int skip){
        Query<ClientTagEntity> query = this.datastore.createQuery(ClientTagEntity.class);

        query.field("clientId").equal(entity.getClientId());

        query = applyToQuery(query,"tag.tag_id",entity.getTag().getTag_id(),true);
        query = applyToQuery(query,"tag.tag_value",entity.getTag().getTag_value(),true);

        return query.asList(new FindOptions().limit(limit).skip(skip));
    }

    public List<ClientTagEntity> getClientTags(Tag tag, int limit, int skip){

        Query<ClientTagEntity> query = this.datastore.createQuery(ClientTagEntity.class);

        query = applyToQuery(query,"tag.tag_id",tag.getTag_id(),true);
        query = applyToQuery(query,"tag.tag_value",tag.getTag_value(),true);

        return query.asList(new FindOptions().limit(limit).skip(skip));
    }


    public ClientTagEntity create(ClientTagEntity clientTagsEntity) throws DuplicateKeyException {
        datastore.save(clientTagsEntity);

        return clientTagsEntity;
    }

    void update(ClientTagEntity userTagsEntity){


    }

    private UpdateOperations<ClientTagEntity> pushTag(UpdateOperations<ClientTagEntity> update, Tag tag){
        update.addToSet("tags", tag);
        return update;
    }


    private Query<ClientTagEntity> applyToQuery(Query<ClientTagEntity> query, String fieldName, String fieldValue, boolean equal){
        if(fieldValue!= null) {
            return equal? query.field(fieldName).equal(fieldValue) : query.field(fieldName).notEqual(fieldValue);
        }
        return query;
    }


    public List<Statistic> getStats(int limit, int offset) {

        Query<ClientTagEntity> query = this.datastore.createQuery(ClientTagEntity.class);

        DBCollection collection = datastore.getCollection(ClientTagEntity.class);
        List ids = collection.distinct("clientId",new BasicDBObject());

        for(Object id : ids){
            query.field("clientId").equal(id);
            //long amountOfTags =collection.find(query).count();
            //System.out.println(id + "  "+ amountOfTags);
        }

        List<Statistic> stats = new LinkedList<>();
        return stats;

    }
}
