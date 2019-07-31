package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoTagsDAO;
import com.comarch.danielkurosz.data.ClientTagsEntity;
import com.comarch.danielkurosz.data.Tag;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.exceptions.DuplicateIdException;
import com.comarch.danielkurosz.exceptions.InvalidClientIdException;
import com.mongodb.DuplicateKeyException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.Silent.class)
public class TagsServiceTest {

    @Mock
    private MongoTagsDAO mongoTagsDAO;
    @Mock
    private TagMapper tagMapper;

    private TagsService testObject;

    @Before
    public void init() {
        testObject = new TagsService(mongoTagsDAO, tagMapper);
    }



    @Test
    public void getTags_WhenUUIDStringIsInvalid_ThenReturnNull() {
        List<String> clientsID = new LinkedList<>();
        clientsID.add("zzzz");
        testObject.getTags(clientsID);
    }


    @Test
    public void getTags_WhenUUIDisCorrectButThereIsNotClientWithThisID_ThenReturnNull() {
        List<String> clientsID = new LinkedList<>();
        clientsID.add(UUID.randomUUID().toString());
        when(mongoTagsDAO.getUserTagsEntity(any())).thenThrow(IndexOutOfBoundsException.class);
        testObject.getTags(clientsID);

    }


    @Test(expected = InvalidClientIdException.class)
    public void create_WhenNullIsPassed_ThenThrowInvalidClientIdException() throws AppException {
        testObject.create(null);
    }

    @Test(expected = InvalidClientIdException.class)
    public void create_WhenPassedIdIsWrongUUIDString_ThenThrowInvalidClientIdException() throws AppException {
        testObject.create("zzzzzzz");
    }

    @Test(expected = DuplicateIdException.class)
    public void create_WhenMongoTagsDaoThrowDuplicateKeyException_ThenThrowDuplicateIdException() throws AppException {
        when(mongoTagsDAO.create(any())).thenThrow(DuplicateKeyException.class);
        testObject.create(UUID.randomUUID().toString());
    }


}