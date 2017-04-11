package com.hryshchenko.model.event;

public class SearchRequest {
    private String value;
    private String source;

    public SearchRequest() {
    }

    public SearchRequest(String value, String source) {
        this.value = value;
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
