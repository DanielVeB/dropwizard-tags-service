package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.data.ClientTagsEntity;
import com.comarch.danielkurosz.dto.Tag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class TagMapperTest {

    private TagMapper tagMapper;

    @Before
    public void init() {
        tagMapper = new TagMapper();
    }

    @Test
    public void mapToTagDTO_WhenEntityIsNull_ThenReturnNull() {
        Assert.assertNull(tagMapper.mapToTagDTO(null));
    }

    @Test
    public void mapToTagDTO_WhenEntityHasNotTags_ThenReturnNull() {
        ClientTagsEntity userTagsEntity = new ClientTagsEntity();
        Assert.assertNull(tagMapper.mapToTagDTO(userTagsEntity));
    }

    @Test
    public void mapToTagDTO_WhenEntityHasTags_ThenReturnListOfTagsDTO() {
        ClientTagsEntity userTagsEntity = new ClientTagsEntity();
        List<TagEntity> tagEntities = new LinkedList<>();
        tagEntities.add(new TagEntity());

        userTagsEntity.setTagEntities(tagEntities);

        List<Tag> tagDTOS = tagMapper.mapToTagDTO(userTagsEntity);
        Assert.assertNotEquals("list should have size at least 1", 0, tagDTOS.size());
    }


}