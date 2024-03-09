package org.javarush;

import org.hibernate.Session;
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
import org.javarush.domain.entity.Address;
import org.javarush.domain.entity.Customer;
import org.javarush.domain.entity.Film;
import org.javarush.domain.entity.Inventory;
import org.javarush.domain.entity.Rental;
import org.javarush.domain.entity.Staff;
import org.javarush.domain.entity.Store;
import org.javarush.service.CustomerService;
import org.javarush.service.FilmService;
import org.javarush.service.RentingService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private final SessionFactory sessionFactory;
    private final List<GenericDAO<?>> daoInstances;

    public Main() {
        sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();

        daoInstances = initializeDAOs(sessionFactory);
    }

    public static void main(String[] args) {
        Main main = new Main();
        Customer customer = main.createCustomer();

        main.customerRentInventoryFromStore(customer);
        main.customerReturnInventoryToStore();

        main.newFilmWasMade();
    }

    private void newFilmWasMade() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            FilmService filmService = new FilmService(
                    getDAO(FilmDAO.class),
                    getDAO(FilmTextDAO.class),
                    getDAO(ActorDAO.class),
                    getDAO(LanguageDAO.class),
                    getDAO(CategoryDAO.class)
            );

            Film film = filmService.createNewFilm();
            filmService.addFilmTextToFilm(film);

            session.getTransaction().commit();
        }
    }

    private void customerRentInventoryFromStore(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            RentingService rentingService = new RentingService(
                    getDAO(FilmDAO.class),
                    getDAO(StoreDAO.class),
                    getDAO(InventoryDAO.class),
                    getDAO(RentalDAO.class),
                    getDAO(PaymentDAO.class));

            Inventory inventory = rentingService.createInventory();
            Store store = inventory.getStore();
            Staff staff = store.getStaff();

            Rental rental = rentingService.createRental(customer, inventory, staff);
            rentingService.createPayment(customer, rental, staff);

            session.getTransaction().commit();
        }
    }

    private void customerReturnInventoryToStore() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            RentalDAO rentalDAO = getDAO(RentalDAO.class);
            Rental rental = rentalDAO.getAnyUnReturnedRental();
            rental.setReturnDate(LocalDateTime.now());

            rentalDAO.save(rental);

            session.getTransaction().commit();
        }
    }

    private Customer createCustomer() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            CustomerService customerService = new CustomerService(getDAO(StoreDAO.class), getDAO(CityDAO.class), getDAO(AddressDAO.class), getDAO(CustomerDAO.class));

            Address address = customerService.createAddress();
            Customer customer = customerService.createCustomer(address);

            session.getTransaction().commit();
            return customer;
        }
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
