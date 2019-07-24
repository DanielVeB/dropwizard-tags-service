package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoTagsDAO;
import com.comarch.danielkurosz.data.UserTagsEntity;
import com.comarch.danielkurosz.dto.UserTagDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.exceptions.DuplicateIdException;
import com.comarch.danielkurosz.exceptions.InvalidClientIdException;
import com.mongodb.DuplicateKeyException;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TagsService {

    private MongoTagsDAO mongoTagsDAO;

    private TagMapper tagMapper;

    public TagsService(MongoTagsDAO mongoTagsDAO, TagMapper tagMapper) {
        this.mongoTagsDAO = mongoTagsDAO;
        this.tagMapper = tagMapper;
    }

    public HashMap<String, List<UserTagDTO>> getTags(List<String> clientsID) {
        HashMap<String, List<UserTagDTO>> tags = new HashMap<>();
        if (clientsID == null) {
            return tags;
        }

        UserTagsEntity userTagsEntity;
        for (String clientID : clientsID) {
            try {
                userTagsEntity = mongoTagsDAO.getUserTagsEntity(UUID.fromString(clientID));
                tags.put(clientID, tagMapper.mapToTagDTO(userTagsEntity));
            } catch (IndexOutOfBoundsException | IllegalArgumentException ex) {
                // ignore this id
            }
        }
        return tags;
    }

    public List<UserTagDTO> create(String id) throws AppException {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new InvalidClientIdException();
        }
        UserTagsEntity entity = new UserTagsEntity();
        entity.setClientId(uuid);
        try {
            mongoTagsDAO.create(entity);
        } catch (DuplicateKeyException ex) {
            throw new DuplicateIdException();
        }
        return tagMapper.mapToTagDTO(entity);
    }

}
