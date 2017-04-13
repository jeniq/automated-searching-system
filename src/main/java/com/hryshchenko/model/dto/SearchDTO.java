package com.hryshchenko.model.dto;

public class SearchDTO {
    private Long source;
    private String request;

    public SearchDTO() {
    }

    public SearchDTO(Long source, String request) {
        this.source = source;
        this.request = request;
    }

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
