package com.comarch.danielkurosz.dto;

import com.comarch.danielkurosz.data.Tag;

import java.util.List;

public class ClientTagDTO {


    private String clientId;
    private List<Tag> tags;

    public ClientTagDTO() {

    }

    public ClientTagDTO(String clientId, List<Tag> tags) {
        this.clientId = clientId;
        this.tags = tags;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }


}
