package org.javarush.service;

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
    private final FilmDAO filmDAO;
    private final StoreDAO storeDAO;
    private final InventoryDAO inventoryDAO;
    private final RentalDAO rentalDAO;
    private final PaymentDAO paymentDAO;

    public RentingService(FilmDAO filmDAO, StoreDAO storeDAO, InventoryDAO inventoryDAO,
                          RentalDAO rentalDAO, PaymentDAO paymentDAO) {
        this.filmDAO = filmDAO;
        this.storeDAO = storeDAO;
        this.inventoryDAO = inventoryDAO;
        this.rentalDAO = rentalDAO;
        this.paymentDAO = paymentDAO;
    }

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
