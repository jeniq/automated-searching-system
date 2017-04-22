package com.hryshchenko.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "course_views")
public class CourseViews {

    @Id
    @Column(name = "course_views_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_view_seq_gen")
    @SequenceGenerator(name = "course_view_seq_gen", sequenceName = "course_views_course_views_id_seq")
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="course_id")
    private Course course;
    @Column
    private Long views;

    public CourseViews() {
    }

    public CourseViews(Course course, Long views) {
        this.course = course;
        this.views = views;
    }

   /* public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }*/

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
