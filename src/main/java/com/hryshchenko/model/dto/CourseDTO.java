package com.hryshchenko.model.dto;

import com.hryshchenko.model.entity.Category;
import com.hryshchenko.model.entity.Course;
import com.hryshchenko.model.entity.Language;
import com.hryshchenko.model.entity.Source;

import java.sql.Timestamp;

public class CourseDTO {
    private Long id;
    private String name;
    private String description;
    private Timestamp startTime;
    private Timestamp endTime;
    private Category category;
    private Source source;
    private String pictureUrl;
    private String courseSourceId;
    private String courseLink;
    private Language language;

    public Course toCourse() {
        Course course = new Course();

        course.setName(this.name);
        course.setDescription(this.description);
        course.setPictureUrl(this.pictureUrl);
        course.setCourseSourceId(this.courseSourceId);
        course.setCourseLink(this.courseLink);
        if (this.startTime != null) {
            course.setStartTime(this.startTime);
        }
        if (this.category != null) {
            course.setCategory(this.category);
        }
        if (this.source != null) {
            course.setSource(this.source);
        }
        if (this.language != null) {
            course.setLanguage(this.language);
        }

        return course;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getCourseSourceId() {
        return courseSourceId;
    }

    public void setCourseSourceId(String courseSourceId) {
        this.courseSourceId = courseSourceId;
    }

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
