package com.hryshchenko.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.hryshchenko.model.view.View;

public class CategoryDTO {

    @JsonView(View.Public.class)
    private Long id;
    @JsonView(View.Public.class)
    private String name;

    public CategoryDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
