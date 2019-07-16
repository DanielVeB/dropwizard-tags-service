package com.comarch.danielkurosz.data;


import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;

@Embedded
public class TagEntity {

    @Id
    private int key;
    private String value;

    public TagEntity(){}

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}





//  key - value
// 0 - Admin account
// 1 - Premium account
// 2 - Banned account
// 3 - New account
