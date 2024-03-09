package org.javarush.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.javarush.domain.entity.Rental;

public class RentalDAO extends GenericDAO<Rental> {
    public RentalDAO(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }

    public Rental getAnyUnReturnedRental() {
        Query<Rental> query = getCurrentSession().createQuery("select r from Rental r where r.returnDate is null", Rental.class);
        return query.setMaxResults(1).getSingleResult();
    }
}
