package com.comarch.danielkurosz.dto;


public class Tag {

    private String tag_id;
    private String tag_value;

    public Tag() {
    }

    public Tag(String tag_id, String tag_value) {
        this.tag_id = tag_id;
        this.tag_value = tag_value;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_value() {
        return tag_value;
    }

    public void setTag_value(String tag_value) {
        this.tag_value = tag_value;
    }
}
