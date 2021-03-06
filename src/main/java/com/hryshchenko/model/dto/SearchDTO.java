package com.hryshchenko.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.hryshchenko.model.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @JsonProperty("locale")
    private String locale;

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
        this.sources = sources.stream().filter(s -> s != null).collect(Collectors.toList());
    }

    public List<Long> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Long> languages) {
        this.languages = languages.stream().filter(l -> l != null).collect(Collectors.toList());
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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
