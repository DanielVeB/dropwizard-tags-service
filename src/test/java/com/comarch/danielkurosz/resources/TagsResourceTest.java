package com.comarch.danielkurosz.resources;

import com.comarch.danielkurosz.auth.UserAuth;
import com.comarch.danielkurosz.dto.UserTagDTO;
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
        HashMap<String, List<UserTagDTO>> hashMap = new HashMap<>();
        when(tagsService.getTags(anyList())).thenReturn(hashMap);

        Response response = testObject.getTags(new UserAuth(""), "");
        Assert.assertEquals("result hash map should be empty", hashMap, response.getEntity());

    }

    @Test
    public void getTags_WhenTagsServiceReturnHashMapWithTags_ThenReturnResponseWithHashMap() {
        HashMap<String, List<UserTagDTO>> hashMap = new HashMap<>();
        List<UserTagDTO> tags = new LinkedList<>();
        hashMap.put("new id", tags);

        when(tagsService.getTags(anyList())).thenReturn(hashMap);
        Response response = testObject.getTags(new UserAuth(""), "");
        Assert.assertEquals("result hashMap should have one field", hashMap, response.getEntity());
    }


}