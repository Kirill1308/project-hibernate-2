package org.javarush.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class GenericDAO<T> {
    private final Class<T> clazz;
    private final SessionFactory sessionFactory;

    public GenericDAO(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    public T getById(final int id) {
        return getCurrentSession().get(clazz, id);
    }

    public List<T> getItems(int offset, int count) {
        return getCurrentSession().createQuery("from " + clazz.getName(), clazz)
                .setFirstResult(offset)
                .setMaxResults(count)
                .list();
    }

    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + clazz.getName(), clazz).list();
    }

    public T save(final T entity) {
        getCurrentSession().save(entity);
        return entity;
    }

    public T update(final T entity) {
        return (T) getCurrentSession().merge(entity);
    }

    public void delete(final T entity){
        getCurrentSession().delete(entity);
    }

    public void deleteById(final int entityId){
        final T entity = getById(entityId);
        delete(entity);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
