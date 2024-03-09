package org.javarush.dao;

import org.hibernate.SessionFactory;
import org.javarush.domain.entity.Staff;

public class StaffDAO extends GenericDAO<Staff> {
    public StaffDAO(SessionFactory sessionFactory) {
        super(Staff.class, sessionFactory);
    }
}
