package com.hryshchenko.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name = "course_view")
public class CourseView {

    @Id
    @Column(name = "course_view_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_view_seq_gen")
    @SequenceGenerator(name = "course_view_seq_gen", sequenceName = "course_view_course_view_id_seq",
            initialValue = 1, allocationSize = 1)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    @JsonManagedReference
    private Course course;
    @Column(name = "view")
    private Long views;

    public CourseView() {
    }

    public CourseView(Course course, Long views) {
        this.course = course;
        this.views = views;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }
}
