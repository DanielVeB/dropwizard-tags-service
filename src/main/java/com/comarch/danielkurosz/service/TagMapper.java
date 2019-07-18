package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.data.TagEntity;
import com.comarch.danielkurosz.data.UserTagsEntity;
import com.comarch.danielkurosz.dto.UserTagDTO;

import java.util.LinkedList;
import java.util.List;

public class TagMapper {

    List<UserTagDTO> mapToTagDTO(UserTagsEntity entity){
        if(entity==null){
            return null;
        }
        List<TagEntity> tagEntities = entity.getTagEntities();
        if(tagEntities == null){
            return null;
        }
        List<UserTagDTO> tagDTOs = new LinkedList<>();
        for(TagEntity tagEntity: tagEntities){
            tagDTOs.add(new UserTagDTO(tagEntity.getTagId(),tagEntity.getTagValue()));
        }
        return tagDTOs;
    }
}
