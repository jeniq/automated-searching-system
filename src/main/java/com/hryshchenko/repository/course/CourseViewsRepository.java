package com.hryshchenko.repository.course;

import com.hryshchenko.model.entity.CourseView;
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
    public void save(CourseView courseView) {
        getSession().save(courseView);
    }

    @Transactional
    public void update(CourseView courseView) {
        getSession().update(courseView);
    }

    @Transactional(readOnly = true)
    public CourseView getByCourse(long id) {
        return (CourseView) getSession().createQuery("FROM CourseView WHERE course.id = :courseId")
                .setParameter("courseId", id)
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<CourseView> getTopCourses() {
        return getSession().createQuery("FROM CourseView ORDER BY views DESC")
                .setFirstResult(0)
                .setMaxResults(5)
                .list();
    }
}
