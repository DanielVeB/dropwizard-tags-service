package com.comarch.danielkurosz.dto;

import com.comarch.danielkurosz.data.Tag;

public class ClientTagDTO {

    String clientId;
    Tag tag;

    public ClientTagDTO(String clientId, Tag tag) {
        this.clientId = clientId;
        this.tag = tag;
    }

    public ClientTagDTO() {
    }


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
