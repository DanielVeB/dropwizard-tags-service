package com.comarch.danielkurosz.dto;

public class Statistics {

    private String clientId;
    private long numOfIds;

    public Statistics(String clientId, long numOfIds) {
        this.clientId = clientId;
        this.numOfIds = numOfIds;
    }

    public Statistics() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public long getNumOfIds() {
        return numOfIds;
    }

    public void setNumOfIds(int numOfIds) {
        this.numOfIds = numOfIds;
    }
}
