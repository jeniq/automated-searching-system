package com.hryshchenko.repository.course;

import com.hryshchenko.model.dto.CourseDTO;
import com.hryshchenko.model.dto.SearchDTO;
import com.hryshchenko.model.entity.Course;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CourseRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public void save(Course course) {
        getSession().save(course);
    }

    @Transactional
    public void saveAll(List<CourseDTO> courseDTOs) {
        for (CourseDTO courseDTO : courseDTOs) {
            getSession().save(courseDTO.toCourse());
        }
    }

    public void delete(Course course) {
        getSession().delete(course);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Course> getAll() {
        return getSession().createQuery("from Course").list();
    }

    public Course getById(long id) {
        return (Course) getSession().load(Course.class, id);
    }

    public void update(Course course) {
        getSession().update(course);
    }

    public List getCourseByName(String name) {
        Criteria criteria = getSession().createCriteria(Course.class);
        return criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE)).list();
    }

    public List getCourseByNameAndSource(SearchDTO searchDTO) {
        return getSession().createQuery("FROM Course WHERE name LIKE LOWER(:name) AND source = :source")
                .setParameter("name", searchDTO.getRequest())
                .setParameter("source", searchDTO.getSource())
                .list();
    }
}
