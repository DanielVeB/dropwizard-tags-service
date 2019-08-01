package com.comarch.danielkurosz.dto;

public class Statistic {

    private String clientId;
    private int numOfIds;

    public Statistic(String clientId, int numOfIds) {
        this.clientId = clientId;
        this.numOfIds = numOfIds;
    }

    public Statistic() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getNumOfIds() {
        return numOfIds;
    }

    public void setNumOfIds(int numOfIds) {
        this.numOfIds = numOfIds;
    }
}
