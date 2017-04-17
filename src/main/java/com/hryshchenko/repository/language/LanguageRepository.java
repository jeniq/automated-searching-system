package com.hryshchenko.repository.language;

import com.hryshchenko.model.entity.Language;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class LanguageRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public Language getById(long id) {
        return (Language) getSession().createQuery("FROM Course WHERE id = :id")
                .setParameter("id", id)
                .uniqueResult();
    }

    @Transactional
    public void save(Language language) {
        getSession().save(language);
    }

    @Transactional
    public Optional<Language> getLanguageByAbbr(String value) {
        Criteria criteria = getSession().createCriteria(Language.class);
        return Optional.ofNullable((Language) criteria.add(
                Restrictions.ilike("abbr", value.substring(0, 2)))
                .uniqueResult());
    }

}
