package com.hryshchenko.model.dto;

public class SearchDTO {
    private Long source;
    private String request;

    public Long getSource() {
        return source;
    }

    public void setSource(Long sources) {
        this.source = sources;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
