package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoTagsDAO;
import com.comarch.danielkurosz.data.UserTagsEntity;
import com.comarch.danielkurosz.dto.UserTagDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.exceptions.InvalidClientIdException;

import java.util.List;
import java.util.UUID;

public class TagsService {

    private MongoTagsDAO mongoTagsDAO;

    private TagMapper tagMapper;

    public TagsService(MongoTagsDAO mongoTagsDAO, TagMapper tagMapper) {
        this.mongoTagsDAO = mongoTagsDAO;
        this.tagMapper = tagMapper;
    }

    public List<UserTagDTO> getTags(String id) throws AppException {

        UserTagsEntity userTagsEntity;
        try {
            userTagsEntity = mongoTagsDAO.getUserTagsEntity(UUID.fromString(id));
            return tagMapper.mapToTagDTO(userTagsEntity);
        } catch (IndexOutOfBoundsException | IllegalArgumentException ex) {
            throw new InvalidClientIdException();
        }

    }

    public List<UserTagDTO> create(String id) throws AppException {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new InvalidClientIdException();
        }
        UserTagsEntity entity = new UserTagsEntity();
        entity.setClientId(uuid);
        mongoTagsDAO.create(entity);
        return tagMapper.mapToTagDTO(entity);
    }

}
