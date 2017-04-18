package com.hryshchenko.repository.category;

import com.hryshchenko.model.entity.Category;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class CategoryRepository {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public Category getById(long id) {
        return (Category) getSession().createQuery("FROM Category WHERE id = :id")
                .setParameter("id", id)
                .uniqueResult();
    }

    @Transactional
    public void save(Category category) {
        getSession().save(category);
    }

    @Transactional
    public Optional<Category> getCategoryByName(String value) {
        Criteria criteria = getSession().createCriteria(Category.class);
        return Optional.ofNullable((Category) criteria.add(
                Restrictions.ilike("name", value))
                .uniqueResult());
    }
}
