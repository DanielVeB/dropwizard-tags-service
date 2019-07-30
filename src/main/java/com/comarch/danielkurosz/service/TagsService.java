package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoTagsDAO;
import com.comarch.danielkurosz.data.ClientTagsEntity;
import com.comarch.danielkurosz.dto.ClientTagDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.exceptions.DuplicateIdException;
import com.comarch.danielkurosz.exceptions.InvalidClientIdException;
import com.mongodb.DuplicateKeyException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class TagsService {

    private MongoTagsDAO mongoTagsDAO;

    private TagMapper tagMapper;

    public TagsService(MongoTagsDAO mongoTagsDAO, TagMapper tagMapper) {
        this.mongoTagsDAO = mongoTagsDAO;
        this.tagMapper = tagMapper;
    }

    public List<ClientTagDTO> getTags(List<String> clientsID) {
        List<ClientTagDTO> clientTags = new LinkedList<>();
        ClientTagsEntity userTagsEntity;
        for (String clientID : safe(clientsID)) {
            try {
                userTagsEntity = mongoTagsDAO.getUserTagsEntity(UUID.fromString(clientID));
                clientTags.add(tagMapper.mapToClientTagDTO(userTagsEntity));
            } catch (IndexOutOfBoundsException | IllegalArgumentException ex) {
                // ignore this id
            }
        }
        return clientTags;
    }

    public ClientTagDTO create(String id) throws AppException {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new InvalidClientIdException();
        }
        ClientTagsEntity entity = new ClientTagsEntity();

        entity.setClientId(uuid);
        try {
            mongoTagsDAO.create(entity);
        } catch (DuplicateKeyException ex) {
            throw new DuplicateIdException();
        }

        return tagMapper.mapToClientTagDTO(entity);
    }


    public List<String> getClientsId(List<String> withoutTags, List<String> withTags) {
        List<UUID> uuids = mongoTagsDAO.getClientsId(withoutTags, withTags);
        List<String> ids = new LinkedList<>();
        for (UUID uuid : uuids) {
            ids.add(uuid.toString());
        }
        return ids;
    }

    public void update(ClientTagDTO clientTagDTO) {
        mongoTagsDAO.update(tagMapper.mapToClientTagEntity(clientTagDTO));
    }

    private static<T> List<T> safe(List<T> other) {
        return other == null ? Collections.EMPTY_LIST : other;
    }


}
