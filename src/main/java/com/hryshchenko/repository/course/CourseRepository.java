package com.hryshchenko.repository.course;

import com.hryshchenko.model.dto.CourseDTO;
import com.hryshchenko.model.dto.SearchDTO;
import com.hryshchenko.model.entity.Course;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
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
    public List<Course> getAll(Integer pageSize) {
        return getSession().createQuery("from Course ORDER BY id DESC").setFirstResult(0).setMaxResults(pageSize).list();
    }

    @Transactional
    public Course getCourseBySourceId(String courseSourceId) {
        return (Course) getSession().createQuery("FROM Course WHERE course_source_id = :id")
                .setParameter("id", courseSourceId)
                .uniqueResult();
    }

    @Transactional
    public Course getById(long id) {
        return (Course) getSession().createQuery("FROM Course WHERE id = :id")
                .setParameter("id", id)
                .uniqueResult();
    }

    @Transactional
    public void update(Course course) {
        getSession().update(course);
    }

    public List getCourseByName(String name) {
        Criteria criteria = getSession().createCriteria(Course.class);
        return criteria.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE)).list();
    }

    public List getCoursesByNameAndSource(SearchDTO searchDTO, Integer pageSize) {
        Criteria criteria = getSession().createCriteria(Course.class);
        return criteria.add(Restrictions.ilike("name", searchDTO.getRequest(), MatchMode.ANYWHERE))
                .add(Restrictions.eq("source.id", searchDTO.getSource()))
                .setFirstResult(0)
                .setMaxResults(pageSize)
                .addOrder(Order.desc("id"))
                .list();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Course> getCourses(SearchDTO searchDTO, Integer pageSize) {
        Criteria criteria = getSession().createCriteria(Course.class);

        criteria.add(Restrictions.ilike("name", searchDTO.getRequest(), MatchMode.ANYWHERE))
                .addOrder(Order.desc("id"));

        if (searchDTO.getSources() != null && searchDTO.getSources().size() > 0) {
                criteria.add(Restrictions.in("source.id", searchDTO.getSources()));
        }
        if (searchDTO.getLanguages() != null && searchDTO.getLanguages().size() > 0) {
                criteria.add(Restrictions.in("language.id", searchDTO.getLanguages()));
        }
        if (searchDTO.getCategories() != null && searchDTO.getCategories().size() > 0) {
            criteria.add(Restrictions.in("category.id", searchDTO.getCategories()));
        }

        criteria.setFirstResult(0).setMaxResults(pageSize);
        return criteria.list();
    }

}
