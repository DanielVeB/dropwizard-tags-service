package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.ClientTagEntity;
import com.github.fakemongo.Fongo;
import com.mongodb.DuplicateKeyException;
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
    private Datastore datastore;

    @Before
    public void init() {
        Fongo fongo = new Fongo("dropwizardTags-test");
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.comarch.danielkurosz.data");
        datastore = morphia.createDatastore(fongo.getMongo(), "dropwizardTags-test");
        datastore.ensureIndexes();
        setUpDatabase(datastore);
        mongoTagsDAO = new MongoTagsDAO(datastore);
    }

    private ClientTagEntity createEntity(UUID id, String tagId, String tagValue) {
        return null;
    }

    private void setUpDatabase(Datastore database) {
        database.save(createEntity(UUID.fromString("62fba7eb-eef8-44c1-8d33-f1499cd6145e"), "zodiac", "cancer"));
        database.save(createEntity(UUID.fromString("87aca7eb-ba51-44c1-5233-f1472cd6612e"), "account", "premium"));
    }

    private long getSize() {
        return datastore.getCount(ClientTagEntity.class);
    }


    @Test(expected = NullPointerException.class)
    public void getClientsId_WhenIsPassedTwoNullLists_ThenThrowNullPointerException() {
        mongoTagsDAO.getClientsId(null, null);
    }

    @Test
    public void getClientsId_WhenIsPassedTwoEmptyLists_ThenReturnEveryClientsId() {
        List<UUID> clientsId = mongoTagsDAO.getClientsId(new LinkedList<>(), new LinkedList<>());
        Assert.assertEquals("size of list should be 2", 2, clientsId.size());
    }

    @Test
    public void getClientsId_WhenIsPassedListWithWhichTagsClientHasToBe_ThenReturnOneClient() {
        List<String> withTags = new LinkedList<>();
        withTags.add("zodiac");
        List<UUID> clientsId = mongoTagsDAO.getClientsId(new LinkedList<>(), withTags);
        Assert.assertEquals("size of list should be 1", 1, clientsId.size());
        Assert.assertEquals("62fba7eb-eef8-44c1-8d33-f1499cd6145e", clientsId.get(0).toString());
    }

    @Test
    public void getClientsId_WhenIsPassedListWithWhichTagsClientHasNotToBe_ThenReturnOneClient() {
        List<String> withoutTags = new LinkedList<>();
        withoutTags.add("zodiac");
        List<UUID> clientsId = mongoTagsDAO.getClientsId(withoutTags, new LinkedList<>());
        Assert.assertEquals("size of list should be 1", 1, clientsId.size());
        Assert.assertEquals("87aca7eb-ba51-44c1-5233-f1472cd6612e", clientsId.get(0).toString());
    }

    @Test
    public void getClientsId_WhenIsPassedListWithTwoTagsWhichTagsClientHasToBe_ThenReturnZeroClients() {
        List<String> withTags = new LinkedList<>();
        withTags.add("account");
        withTags.add("zodiac");
        List<UUID> clientsId = mongoTagsDAO.getClientsId(new LinkedList<>(), withTags);
        Assert.assertEquals("size of list should be 0", 0, clientsId.size());
    }

    @Test
    public void create_WhenIsPassedClientWithCorrectId_ThenIncreaseDatabaseSizeByOne() {
        ClientTagEntity clientTagsEntity = new ClientTagEntity();
        clientTagsEntity.setClientId(UUID.randomUUID());
        mongoTagsDAO.create(clientTagsEntity);
        Assert.assertEquals("database size shoudl be 3", 3, getSize());
    }

    @Test(expected = DuplicateKeyException.class)
    public void create_WhenIsPassedClientWithDuplicateId_ThenThrowException() {
        ClientTagEntity clientTagsEntity = new ClientTagEntity();
        clientTagsEntity.setClientId(UUID.fromString("87aca7eb-ba51-44c1-5233-f1472cd6612e"));
        mongoTagsDAO.create(clientTagsEntity);
    }

    @Test
    public void update_PushTagWhenThisTagDoesNotExist_ThenAdd() {

    }

    @Test
    public void update_PushTagWhenThisTagAlreadyExists_ThenNotAdd() {

    }

    @Test
    public void update_PushTagWhenThisTagWithThisIdAlreadyExists_ThenNotAdd() {

    }

}

