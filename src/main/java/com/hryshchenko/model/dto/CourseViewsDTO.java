package com.hryshchenko.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseViewsDTO {

    private Long courseId;
    @JsonProperty("label")
    private String courseName;
    @JsonProperty("value")
    private Long views;

    public CourseViewsDTO(Long courseId, String courseName, Long views) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.views = views;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }
}
