package org.javarush.dao;

import org.hibernate.SessionFactory;
import org.javarush.domain.entity.Category;

public class CategoryDAO extends GenericDAO<Category> {
    public CategoryDAO(SessionFactory sessionFactory) {
        super(Category.class, sessionFactory);
    }
}
