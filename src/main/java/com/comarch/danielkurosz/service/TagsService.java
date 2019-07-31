package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoTagsDAO;
import com.comarch.danielkurosz.data.ClientTagsEntity;
import com.comarch.danielkurosz.data.Tag;
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
                List<Tag> tags = mongoTagsDAO.getUserTagsEntity(UUID.fromString(clientID));
                clientTags.add(new ClientTagDTO(clientID,tags));
            } catch (IndexOutOfBoundsException | IllegalArgumentException ex) {
                // ignore this id
            }
        }
        return clientTags;
    }

    public void create(ClientTagDTO clientTagDTO) throws AppException {
        UUID uuid;
        try {
            uuid = UUID.fromString(clientTagDTO.getClientId());
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new InvalidClientIdException();
        }

        for (Tag tag : clientTagDTO.getTags()) {
            System.out.println(tag.getTag_id());
            try {
                mongoTagsDAO.create(new ClientTagsEntity(uuid, tag));
            }catch (DuplicateKeyException ex){
                System.out.println("blad" + tag.getTag_id());
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

    public void update(ClientTagDTO clientTagDTO) {
       // mongoTagsDAO.update(tagMapper.mapToClientTagEntity(clientTagDTO));
    }

    private static<T> List<T> safe(List<T> other) {
        return other == null ? Collections.EMPTY_LIST : other;
    }


}
