package org.javarush.service;

import org.javarush.app.AppConfig;
import org.javarush.dao.AddressDAO;
import org.javarush.dao.CityDAO;
import org.javarush.dao.CustomerDAO;
import org.javarush.dao.StoreDAO;
import org.javarush.domain.entity.Address;
import org.javarush.domain.entity.City;
import org.javarush.domain.entity.Customer;
import org.javarush.domain.entity.Store;

public class CustomerService {
    private final AppConfig appConfig = AppConfig.getInstance();
    private final StoreDAO storeDAO = appConfig.getDAO(StoreDAO.class);
    private final CityDAO cityDAO = appConfig.getDAO(CityDAO.class);
    private final AddressDAO addressDAO = appConfig.getDAO(AddressDAO.class);
    private final CustomerDAO customerDAO = appConfig.getDAO(CustomerDAO.class);

    public Address createAddress() {
        City city = cityDAO.getByName("Kragujevac");
        Address address = new Address();
        address.setAddress("Kneza Mihaila 1");
        address.setCity(city);
        address.setDistrict("Pivara");
        address.setPhone("381641234567");

        addressDAO.save(address);
        return address;
    }

    public Customer createCustomer(Address address) {
        Store store = storeDAO.getItems(0, 1).get(0);

        Customer customer = new Customer();
        customer.setIsActive(true);
        customer.setFirstName("Marko");
        customer.setLastName("Markovic");
        customer.setEmail("test@gmail.com");
        customer.setAddress(address);
        customer.setStore(store);
        customerDAO.save(customer);

        return customer;
    }
}
