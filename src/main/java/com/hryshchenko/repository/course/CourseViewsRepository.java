package com.hryshchenko.repository.course;

import com.hryshchenko.model.entity.CourseViews;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CourseViewsRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public void save(CourseViews courseViews) {
        getSession().save(courseViews);
    }

    @Transactional
    public void update(CourseViews courseViews) {
        getSession().update(courseViews);
    }

    @Transactional(readOnly = true)
    public CourseViews getByCourse(long id) {
        return (CourseViews) getSession().createQuery("FROM CourseViews WHERE course.id = :courseId")
                .setParameter("courseId", id)
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<CourseViews> getTopCourses() {
        return getSession().createQuery("FROM CourseViews ORDER BY views DESC")
                .setFirstResult(0)
                .setMaxResults(5)
                .list();
    }
}
