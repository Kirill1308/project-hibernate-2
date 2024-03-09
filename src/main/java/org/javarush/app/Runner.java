package org.javarush.app;

import org.hibernate.Session;
import org.javarush.dao.ActorDAO;
import org.javarush.dao.AddressDAO;
import org.javarush.dao.CategoryDAO;
import org.javarush.dao.CityDAO;
import org.javarush.dao.CustomerDAO;
import org.javarush.dao.FilmDAO;
import org.javarush.dao.FilmTextDAO;
import org.javarush.dao.InventoryDAO;
import org.javarush.dao.LanguageDAO;
import org.javarush.dao.PaymentDAO;
import org.javarush.dao.RentalDAO;
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
import org.javarush.view.ConsoleViewProvider;

import java.time.LocalDateTime;

public class Runner {
    private final AppConfig appConfig;

    public Runner() {
        appConfig = new AppConfig();
    }

    public void run() {
        ConsoleViewProvider console = new ConsoleViewProvider();

        boolean isRunning = true;
        while (isRunning) {
            console.printOutput("Please select an action:");
            console.printOutput("1. Create a new Film");
            console.printOutput("2. Rent an Inventory");
            console.printOutput("3. Return an Inventory");
            console.printOutput("4. Create a new Customer");
            console.printOutput("5. Exit");

            String userInput = console.readInput();
            switch (userInput) {
                case "1":
                    createFilm();
                    break;
                case "2":
                    Customer customer = createCustomer();
                    processCustomerRenting(customer);
                    break;
                case "3":
                    processRentalReturn();
                    break;
                case "4":
                    createCustomer();
                    break;
                case "5":
                    isRunning = false;
                    break;
                default:
                    console.printOutput("Invalid input");
            }
        }
        console.printOutput("Exiting the app.");
        console.closeScanner();
    }

    private void createFilm() {
        try (Session session = appConfig.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            FilmService filmService = new FilmService(
                    appConfig.getDAO(FilmDAO.class),
                    appConfig.getDAO(FilmTextDAO.class),
                    appConfig.getDAO(ActorDAO.class),
                    appConfig.getDAO(LanguageDAO.class),
                    appConfig.getDAO(CategoryDAO.class)
            );

            Film film = filmService.createNewFilm();
            filmService.addFilmTextToFilm(film);

            session.getTransaction().commit();
        }
    }

    private void processCustomerRenting(Customer customer) {
        try (Session session = appConfig.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            RentingService rentingService = new RentingService(
                    appConfig.getDAO(FilmDAO.class),
                    appConfig.getDAO(StoreDAO.class),
                    appConfig.getDAO(InventoryDAO.class),
                    appConfig.getDAO(RentalDAO.class),
                    appConfig.getDAO(PaymentDAO.class));

            Inventory inventory = rentingService.createInventory();
            Store store = inventory.getStore();
            Staff staff = store.getStaff();

            Rental rental = rentingService.createRental(customer, inventory, staff);
            rentingService.createPayment(customer, rental, staff);

            session.getTransaction().commit();
        }
    }

    private void processRentalReturn() {
        try (Session session = appConfig.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            RentalDAO rentalDAO = appConfig.getDAO(RentalDAO.class);
            Rental rental = rentalDAO.getAnyUnReturnedRental();
            rental.setReturnDate(LocalDateTime.now());

            rentalDAO.save(rental);

            session.getTransaction().commit();
        }
    }

    private Customer createCustomer() {
        try (Session session = appConfig.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            CustomerService customerService = new CustomerService(
                    appConfig.getDAO(StoreDAO.class),
                    appConfig.getDAO(CityDAO.class),
                    appConfig.getDAO(AddressDAO.class),
                    appConfig.getDAO(CustomerDAO.class));

            Address address = customerService.createAddress();
            Customer customer = customerService.createCustomer(address);

            session.getTransaction().commit();
            return customer;
        }
    }
}
