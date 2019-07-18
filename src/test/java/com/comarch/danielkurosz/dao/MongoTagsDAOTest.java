package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.TagEntity;
import com.comarch.danielkurosz.data.UserTagsEntity;
import com.github.fakemongo.Fongo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class MongoTagsDAOTest {

    private MongoTagsDAO mongoTagsDAO;

    @Before
    public void init() {
        Fongo fongo = new Fongo("dropwizardTags-test");
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.comarch.danielkurosz.data");
        Datastore datastore = morphia.createDatastore(fongo.getMongo(), "dropwizardTags-test");
        datastore.ensureIndexes();
        setUpDatabase(datastore);
        mongoTagsDAO = new MongoTagsDAO(datastore);
    }

    private UserTagsEntity createEntity(UUID id) {
        UserTagsEntity entity = new UserTagsEntity();
        entity.setClientId(id);
        TagEntity tagEntity = new TagEntity();
        tagEntity.setTagValue("");
        tagEntity.setTagId(1);
        List<TagEntity> tags = new LinkedList<>();
        tags.add(tagEntity);
        entity.setTagEntities(tags);
        return entity;
    }

    private void setUpDatabase(Datastore database) {
        database.save(createEntity(UUID.fromString("62fba7eb-eef8-44c1-8d33-f1499cd6145e")));
    }

    @Test
    public void getUserTagsEntity_WhenClientWithThisIdExists_ThenReturnUserTagsEntity() {
        UserTagsEntity returnEntity = mongoTagsDAO.getUserTagsEntity(UUID.fromString("62fba7eb-eef8-44c1-8d33-f1499cd6145e"));
        Assert.assertNotNull(returnEntity);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getUserTagsEntity_WhenClientWithThisIdDoesNotExist_ThenThrowIndexOutOfBoundException() {
        UserTagsEntity returnEntity = mongoTagsDAO.getUserTagsEntity(UUID.fromString("62daa7eb-eef8-44c1-8d33-f1499cd6145e"));
        Assert.assertNotNull(returnEntity);
    }

    @Test(expected = NumberFormatException.class)
    public void getUserTagsEntity_WhenIdIsInvalid_ThenThrowIndexOutOfBoundException() {
        UserTagsEntity returnEntity = mongoTagsDAO.getUserTagsEntity(UUID.fromString("62zzzzzz-eef8-44c1-8d33-f1499cd6145e"));
        Assert.assertNotNull(returnEntity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserTagsEntity_WhenIdIsInvalidStringTooLarge_ThenThrowIllegalArgumentException() {
        UserTagsEntity returnEntity = mongoTagsDAO.getUserTagsEntity(UUID.fromString("1111111111111111111111111111111111111111111111111111111111"));
        Assert.assertNotNull(returnEntity);
    }
}

