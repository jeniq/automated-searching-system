package com.hryshchenko.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.hryshchenko.model.view.View;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchDTO {
    private Long source;
    @JsonProperty("source")
    private List<Long> sources = new ArrayList<>();
    @JsonProperty("string")
    private String request;
    @JsonProperty("language")
    private List<Long> languages = new ArrayList<>();
    private Long language;
    @JsonProperty("category")
    private List<Long> categories = new ArrayList<>();
    @JsonView(View.Public.class)
    private Integer pageSize;

    public SearchDTO() {
    }

    public SearchDTO(Long source, String request) {
        this.source = source;
        this.request = request;
    }

    public Long getSource() {
        return source;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public List<Long> getSources() {
        return sources;
    }

    public void setSources(List<Long> sources) {
        this.sources = sources;
    }

    public List<Long> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Long> languages) {
        this.languages = languages;
    }

    public List<Long> getCategories() {
        return categories;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getLanguage() {
        return language;
    }

}
