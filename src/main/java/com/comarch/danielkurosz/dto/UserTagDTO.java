package com.comarch.danielkurosz.dto;

public class UserTagDTO {

    private int tag_id;
    private String tag_value;

    public UserTagDTO(){}

    public UserTagDTO(int tag_id, String tag_value) {
        this.tag_id = tag_id;
        this.tag_value = tag_value;
    }

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_value() {
        return tag_value;
    }

    public void setTag_value(String tag_value) {
        this.tag_value = tag_value;
    }
}
