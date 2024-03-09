package org.javarush.app;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.javarush.dao.ActorDAO;
import org.javarush.dao.AddressDAO;
import org.javarush.dao.CategoryDAO;
import org.javarush.dao.CityDAO;
import org.javarush.dao.CountryDAO;
import org.javarush.dao.CustomerDAO;
import org.javarush.dao.FilmDAO;
import org.javarush.dao.FilmTextDAO;
import org.javarush.dao.GenericDAO;
import org.javarush.dao.InventoryDAO;
import org.javarush.dao.LanguageDAO;
import org.javarush.dao.PaymentDAO;
import org.javarush.dao.RentalDAO;
import org.javarush.dao.StaffDAO;
import org.javarush.dao.StoreDAO;

import java.util.ArrayList;
import java.util.List;

public class AppConfig {
    @Getter
    private final SessionFactory sessionFactory;
    private final List<GenericDAO<?>> daoInstances;

    public AppConfig() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        daoInstances = initializeDAOs(sessionFactory);
    }

    private List<GenericDAO<?>> initializeDAOs(SessionFactory sessionFactory) {
        List<GenericDAO<?>> daoList = new ArrayList<>();

        daoList.add(new ActorDAO(sessionFactory));
        daoList.add(new AddressDAO(sessionFactory));
        daoList.add(new CategoryDAO(sessionFactory));
        daoList.add(new CityDAO(sessionFactory));
        daoList.add(new CountryDAO(sessionFactory));
        daoList.add(new CustomerDAO(sessionFactory));
        daoList.add(new FilmDAO(sessionFactory));
        daoList.add(new FilmTextDAO(sessionFactory));
        daoList.add(new InventoryDAO(sessionFactory));
        daoList.add(new LanguageDAO(sessionFactory));
        daoList.add(new PaymentDAO(sessionFactory));
        daoList.add(new RentalDAO(sessionFactory));
        daoList.add(new StaffDAO(sessionFactory));
        daoList.add(new StoreDAO(sessionFactory));

        return daoList;
    }

    @SuppressWarnings("unchecked")
    public <T extends GenericDAO<?>> T getDAO(Class<T> daoClass) {
        return (T) daoInstances.stream()
                .filter(dao -> daoClass.isAssignableFrom(dao.getClass()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("DAO not found for class: " + daoClass));
    }
}
