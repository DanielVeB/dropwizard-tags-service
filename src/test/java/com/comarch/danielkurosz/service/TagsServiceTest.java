package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoTagsDAO;
import com.comarch.danielkurosz.data.UserTagsEntity;
import com.comarch.danielkurosz.exceptions.AppException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
    public void init(){
        testObject = new TagsService(mongoTagsDAO,tagMapper);
    }

    @Test
    public void getTags_ShouldCallTagMapperOnce() throws AppException {
        UserTagsEntity entity = new UserTagsEntity();
        when(mongoTagsDAO.getUserTagsEntity(any())).thenReturn(entity);

        testObject.getTags(UUID.randomUUID().toString());
        verify(tagMapper, times(1)).mapToTagDTO(entity);
    }

    @Test(expected = AppException.class)
    public void getTags_WhenUUIDStringIsInvalid_ThenThrowAppException() throws AppException {
        testObject.getTags("zzzzzzzz");
    }
    @Test(expected = AppException.class)
    public void getTags_WhenUUIDisCorrectButThereIsNotClientWithThisID_ThenThrowAppException() throws AppException{
        when(mongoTagsDAO.getUserTagsEntity(any())).thenThrow(IndexOutOfBoundsException.class);
        testObject.getTags(UUID.randomUUID().toString());
    }

    



}