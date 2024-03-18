package org.javarush.command;

import org.hibernate.Session;
import org.javarush.app.AppConfig;
import org.javarush.domain.entity.Address;
import org.javarush.domain.entity.Customer;
import org.javarush.domain.entity.Inventory;
import org.javarush.domain.entity.Rental;
import org.javarush.domain.entity.Staff;
import org.javarush.domain.entity.Store;
import org.javarush.service.CustomerService;
import org.javarush.service.RentingService;

public class ProcessCustomerRentingCommand implements Command {
    private static final AppConfig appConfig = AppConfig.getInstance();

    @Override
    public void execute() {
        try (Session session = appConfig.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            RentingService rentingService = new RentingService();
            CustomerService customerService = new CustomerService();

            Address address = customerService.createAddress();
            Customer customer = customerService.createCustomer(address);

            Inventory inventory = rentingService.createInventory();
            Store store = inventory.getStore();
            Staff staff = store.getStaff();

            Rental rental = rentingService.createRental(customer, inventory, staff);
            rentingService.createPayment(customer, rental, staff);

            session.getTransaction().commit();
        }
    }

    @Override
    public String getTarget() {
        return CommandType.RENT_INVENTORY.name();
    }

    @Override
    public String getDescription() {
        return "Rent an Inventory";
    }
}
