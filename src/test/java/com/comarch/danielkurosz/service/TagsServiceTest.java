package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoTagsDAO;
import com.comarch.danielkurosz.data.UserTagsEntity;
import com.comarch.danielkurosz.dto.UserTagDTO;
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
    public void getTags_ShouldCallTagMapperOnce() {
        UserTagsEntity entity = new UserTagsEntity();
        when(mongoTagsDAO.getUserTagsEntity(any())).thenReturn(entity);
        List<String> clientsID = new LinkedList<>();
        clientsID.add(UUID.randomUUID().toString());
        testObject.getTags(clientsID);
        verify(tagMapper, times(1)).mapToTagDTO(entity);
    }

    @Test
    public void getTags_WhenNullIsPassed_ThenReturnEmptyHashMap() {
        HashMap<String, List<UserTagDTO>> result = testObject.getTags(null);
        Assert.assertEquals("hashMap should be empty", 0, result.size());
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

    @Test
    public void getTags_WhenOneUUIDIsCorrectButSecondIsInvalid_ThenReturnHashMapWithOneValue() {
        HashMap<String, List<UserTagDTO>> userTags;
        UserTagsEntity userTagsEntity = new UserTagsEntity();
        List<UserTagDTO> tagDTOS = new LinkedList<>();
        List<String> clientsID = new LinkedList<>();
        clientsID.add("invalid uuid");
        clientsID.add(UUID.randomUUID().toString());

        when(mongoTagsDAO.getUserTagsEntity(any())).thenReturn(userTagsEntity);
        when(tagMapper.mapToTagDTO(userTagsEntity)).thenReturn(tagDTOS);

        userTags = testObject.getTags(clientsID);

        Assert.assertEquals("size should be 1", 1, userTags.size());

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

    @Test
    public void create_WhenMongoReturnNothing_ThenCallTagMapper() throws AppException {
        when(mongoTagsDAO.create(any())).thenReturn(null);
        testObject.create(UUID.randomUUID().toString());
        UserTagsEntity entity = new UserTagsEntity();
        verify(tagMapper, times(1)).mapToTagDTO(any());
    }
}