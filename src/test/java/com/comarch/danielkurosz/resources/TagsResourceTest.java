package com.comarch.danielkurosz.resources;

import com.comarch.danielkurosz.auth.UserAuth;
import com.comarch.danielkurosz.dto.Tag;
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
    public void getTags_WhenTagsServiceReturnEmptyHashMap_ThenReturnEmptyHashMap() {
        HashMap<String, List<Tag>> hashMap = new HashMap<>();
        when(tagsService.getTags(anyList())).thenReturn(hashMap);

        Response response = testObject.getTags(new UserAuth(""), new LinkedList<>());
        Assert.assertEquals("result hash map should be empty", hashMap, response.getEntity());

    }

    @Test
    public void getTags_WhenTagsServiceReturnHashMapWithTags_ThenReturnResponseWithHashMap() {
        HashMap<String, List<Tag>> hashMap = new HashMap<>();
        List<Tag> tags = new LinkedList<>();
        hashMap.put("new id", tags);

        when(tagsService.getTags(anyList())).thenReturn(hashMap);
        Response response = testObject.getTags(new UserAuth(""), new LinkedList<>());
        Assert.assertEquals("result hashMap should have one field", hashMap, response.getEntity());
    }

    @Test
    public void getTags_WheNullIsPassed_ThenReturnEmptyHashMap(){
        HashMap<String, List<Tag>> result = new HashMap<>();
        when(tagsService.getTags(null)).thenReturn(result);
        Response response = testObject.getTags(new UserAuth(""),null);
        Assert.assertEquals("result hash map should be empty", result, response.getEntity());

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

    @Test
    public void create_WhenTagsServiceReturnListOfUserTags_ThenReturnResponseWithTags() throws AppException{
        List<Tag> userTags = new LinkedList<>();
        userTags.add(new Tag(1,"Premium account"));

        when(tagsService.create(any())).thenReturn(userTags);
        Response response = testObject.create(new UserAuth(""),UUID.randomUUID().toString());
        Assert.assertEquals(userTags,response.getEntity());
    }

}