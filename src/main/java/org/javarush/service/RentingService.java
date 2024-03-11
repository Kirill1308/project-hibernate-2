package org.javarush.service;

import org.javarush.app.AppConfig;
import org.javarush.dao.FilmDAO;
import org.javarush.dao.InventoryDAO;
import org.javarush.dao.PaymentDAO;
import org.javarush.dao.RentalDAO;
import org.javarush.dao.StoreDAO;
import org.javarush.domain.entity.Customer;
import org.javarush.domain.entity.Film;
import org.javarush.domain.entity.Inventory;
import org.javarush.domain.entity.Payment;
import org.javarush.domain.entity.Rental;
import org.javarush.domain.entity.Staff;
import org.javarush.domain.entity.Store;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RentingService {
    private final AppConfig appConfig = AppConfig.getInstance();
    private final FilmDAO filmDAO = appConfig.getDAO(FilmDAO.class);
    private final StoreDAO storeDAO = appConfig.getDAO(StoreDAO.class);
    private final InventoryDAO inventoryDAO = appConfig.getDAO(InventoryDAO.class);
    private final RentalDAO rentalDAO = appConfig.getDAO(RentalDAO.class);
    private final PaymentDAO paymentDAO = appConfig.getDAO(PaymentDAO.class);

    public Inventory createInventory() {
        Film film = filmDAO.getFirstAvailableFilm();
        Store store = storeDAO.getItems(0, 1).get(0);

        Inventory inventory = new Inventory();
        inventory.setFilm(film);
        inventory.setStore(store);

        inventoryDAO.save(inventory);

        return inventory;
    }

    public Rental createRental(Customer customer, Inventory inventory, Staff staff) {
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setInventory(inventory);
        rental.setStaff(staff);
        rental.setRentalDate(LocalDateTime.now());

        rentalDAO.save(rental);

        return rental;
    }

    public void createPayment(Customer customer, Rental rental, Staff staff) {
        Payment payment = new Payment();
        payment.setCustomer(customer);
        payment.setRental(rental);
        payment.setStaff(staff);
        payment.setAmount(BigDecimal.valueOf(4.99));
        payment.setPaymentDate(LocalDateTime.now());

        paymentDAO.save(payment);
    }
}
