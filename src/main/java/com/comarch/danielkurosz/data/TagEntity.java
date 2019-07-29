package com.comarch.danielkurosz.data;


import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class TagEntity {

    private String tag_id;
    private String tag_value;

    public TagEntity() {
    }

    public String getTagId() {
        return tag_id;
    }

    public void setTagId(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getTagValue() {
        return tag_value;
    }

    public void setTagValue(String tag_value) {
        this.tag_value = tag_value;
    }
}


//  id - value
// 0 - Admin account
// 1 - Premium account
// 2 - Banned account
// 3 - New account
