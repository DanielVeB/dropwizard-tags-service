package com.comarch.danielkurosz.resources;

import com.comarch.danielkurosz.auth.UserAuth;
import com.comarch.danielkurosz.data.Tag;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.service.TagsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TagsResourceTest {

    @Mock
    private TagsService tagsService;

    private TagsResource testObject;

    @Before
    public void init() {
        testObject = new TagsResource(tagsService);
    }







    @Test
    public void create_WhenNullIdIsPassed_ThenReturnResponseWithNullEntity() throws AppException {
        when(tagsService.create(any())).thenThrow(AppException.class);
        Response response = testObject.create(new UserAuth(""),null);
        Assert.assertNull("Response should have null entity", response.getEntity());
    }

    @Test
    public void create_WhenTagsServiceThrowAppException_ThenReturnResponseWithNullEntity() throws AppException {
        when(tagsService.create(any())).thenThrow(AppException.class);
        Response response = testObject.create(new UserAuth(""), UUID.randomUUID().toString());
        Assert.assertNull("Response should have null entity", response.getEntity());
    }



}