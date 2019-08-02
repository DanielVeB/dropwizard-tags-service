package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoTagsDAO;
import com.comarch.danielkurosz.data.ClientTagEntity;
import com.comarch.danielkurosz.data.Tag;
import com.comarch.danielkurosz.dto.ClientTagDTO;
import com.comarch.danielkurosz.dto.ClientTagsDTO;
import com.comarch.danielkurosz.dto.Statistics;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.exceptions.InvalidClientIdException;
import com.mongodb.DuplicateKeyException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class TagsService {

    private MongoTagsDAO mongoTagsDAO;

    private Mapper tagMapper;

    public TagsService(MongoTagsDAO mongoTagsDAO, Mapper tagMapper) {
        this.mongoTagsDAO = mongoTagsDAO;
        this.tagMapper = tagMapper;
    }


    public List<Tag> getTagsByClientId(String clientId, Tag tag, int limit, int offset) {
        ClientTagEntity entity = new ClientTagEntity(UUID.fromString(clientId), tag);
        List<ClientTagEntity> clientTagEntities = mongoTagsDAO.getTagsByClientID(entity, limit, offset);
        return tagMapper.mapToTagList(clientTagEntities);
    }

    public List<ClientTagDTO> getTagsWithClientId(Tag tag, int limit, int offset) {
        List<ClientTagEntity> clientTagEntities = mongoTagsDAO.getClientTags(tag, limit, offset);
        return tagMapper.mapToDTOList(clientTagEntities);
    }

    public List<UUID> getClientsUUID(Tag tag, int limit, int offset) {
        List<ClientTagEntity> clientTagEntities = mongoTagsDAO.getClientTags(tag, limit, offset);
        List<UUID> ids = new LinkedList<>();
        for (ClientTagEntity entity : clientTagEntities) {
            ids.add(entity.getClientId());
        }
        return ids;
    }

    public void create(ClientTagsDTO clientTagDTO) throws AppException {
        UUID uuid;
        try {
            uuid = UUID.fromString(clientTagDTO.getClientId());
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new InvalidClientIdException();
        }

        for (Tag tag : clientTagDTO.getTags()) {
            try {
                mongoTagsDAO.create(new ClientTagEntity(uuid, tag));
            } catch (DuplicateKeyException ex) {
                // ignore this
            }
        }


    }


    public List<String> getClientsId(List<String> withoutTags, List<String> withTags) {
        List<UUID> uuids = mongoTagsDAO.getClientsId(withoutTags, withTags);
        List<String> ids = new LinkedList<>();
        for (UUID uuid : uuids) {
            ids.add(uuid.toString());
        }
        return ids;
    }

    public void update(ClientTagsDTO clientTagDTO) {
        // mongoTagsDAO.update(tagMapper.mapToClientTagEntity(clientTagDTO));
    }

    private static <T> List<T> safe(List<T> other) {
        return other == null ? Collections.EMPTY_LIST : other;
    }


    public List<Statistics> getStats(int limit, int offset, int sortValue) {
        return mongoTagsDAO.getStats(limit,offset,sortValue);
    }
}
