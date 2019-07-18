package com.comarch.danielkurosz.resources;

import com.comarch.danielkurosz.dto.UserTagDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.exceptions.InvalidClientIdException;
import com.comarch.danielkurosz.service.TagsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TagsResourceTest {

    @Mock
    private TagsService tagsService;

    private TagsResource testObject;

    @Before
    public void init(){
        testObject = new TagsResource(tagsService);
    }

    @Test
    public void getTags_WhenTagsServiceThrowException_thenReturnResponseWithNull() throws AppException {
        when(tagsService.getTags(any())).thenThrow(InvalidClientIdException.class);
        Response response = testObject.getTags(true,"id");
        Assert.assertNull(response.getEntity());
    }

    @Test
    public void getTags_WhenTagsServiceReturnTags_ThenReturnResponseWithTags()throws AppException{
        List<UserTagDTO>tags = new LinkedList<>();
        tags.add(new UserTagDTO(1,"Premium account"));

        when(tagsService.getTags(anyString())).thenReturn(tags);
        Response response = testObject.getTags(true, "id");
        Assert.assertEquals(tags,response.getEntity());
    }
}