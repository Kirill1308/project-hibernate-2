package org.javarush.command;

import org.hibernate.Session;
import org.javarush.app.AppConfig;
import org.javarush.domain.entity.Address;
import org.javarush.service.CustomerService;

public class CreateCustomerCommand implements Command {
    private static final AppConfig appConfig = AppConfig.getInstance();

    @Override
    public void execute() {
        try (Session session = appConfig.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            CustomerService customerService = new CustomerService();

            Address address = customerService.createAddress();
            customerService.createCustomer(address);

            session.getTransaction().commit();
        }
    }

    @Override
    public String getTarget() {
        return CommandType.CREATE_CUSTOMER.name();
    }

    @Override
    public String getDescription() {
        return "Create a new Customer";
    }
}
