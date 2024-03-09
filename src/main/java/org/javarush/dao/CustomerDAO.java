package org.javarush.dao;

import org.hibernate.SessionFactory;
import org.javarush.domain.entity.Customer;

public class CustomerDAO extends GenericDAO<Customer> {
    public CustomerDAO(SessionFactory sessionFactory) {
        super(Customer.class, sessionFactory);
    }
}
