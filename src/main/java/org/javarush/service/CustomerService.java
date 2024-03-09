package org.javarush.service;

import lombok.AllArgsConstructor;
import org.javarush.dao.AddressDAO;
import org.javarush.dao.CityDAO;
import org.javarush.dao.CustomerDAO;
import org.javarush.dao.StoreDAO;
import org.javarush.domain.entity.Address;
import org.javarush.domain.entity.City;
import org.javarush.domain.entity.Customer;
import org.javarush.domain.entity.Store;

@AllArgsConstructor
public class CustomerService {
    private final StoreDAO storeDAO;
    private final CityDAO cityDAO;
    private final AddressDAO addressDAO;
    private final CustomerDAO customerDAO;

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
