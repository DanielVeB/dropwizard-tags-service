package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoTagsDAO;
import com.comarch.danielkurosz.data.UserTagsEntity;
import com.comarch.danielkurosz.dto.UserTagDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.exceptions.InvalidClientIdException;

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

    public HashMap<String, List<UserTagDTO>> getTags(List<String> clientsID){

        HashMap<String, List<UserTagDTO>> tags = new HashMap<>();

        UserTagsEntity userTagsEntity;
        for (String clientID : clientsID) {
            try {
                userTagsEntity = mongoTagsDAO.getUserTagsEntity(UUID.fromString(clientID));
                tags.put(clientID, tagMapper.mapToTagDTO(userTagsEntity));
            } catch (IndexOutOfBoundsException | IllegalArgumentException ex) {
                // do nothing
            }
        }
        return tags;
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
