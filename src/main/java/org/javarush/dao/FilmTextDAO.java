package org.javarush.dao;

import org.hibernate.SessionFactory;
import org.javarush.domain.entity.FilmText;

public class FilmTextDAO extends GenericDAO<FilmText> {
    public FilmTextDAO(SessionFactory sessionFactory) {
        super(FilmText.class, sessionFactory);
    }
}
