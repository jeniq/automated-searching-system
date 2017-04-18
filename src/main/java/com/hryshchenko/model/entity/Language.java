package com.hryshchenko.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "language")
public class Language {

    @Id
    @Column(name = "language_id")
    private Long id;
    private String language;
    @Column(name = "abbreviate_form")
    private String abbr;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "language")
    private Set<Course> courses;

    public Language() {
    }

    public Language(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
