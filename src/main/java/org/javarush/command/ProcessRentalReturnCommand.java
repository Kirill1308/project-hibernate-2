package org.javarush.command;

import org.hibernate.Session;
import org.javarush.app.AppConfig;
import org.javarush.dao.RentalDAO;
import org.javarush.domain.entity.Rental;

import java.time.LocalDateTime;

public class ProcessRentalReturnCommand implements Command {
    private static final AppConfig appConfig = AppConfig.getInstance();

    @Override
    public void execute() {
        try (Session session = appConfig.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            RentalDAO rentalDAO = appConfig.getDAO(RentalDAO.class);
            Rental rental = rentalDAO.getAnyUnReturnedRental();
            rental.setReturnDate(LocalDateTime.now());

            rentalDAO.save(rental);

            session.getTransaction().commit();
        }
    }

    @Override
    public String getTarget() {
        return CommandType.RETURN_INVENTORY.name();
    }

    @Override
    public String getDescription() {
        return "Return an Inventory";
    }
}
