package org.javarush.command;

import org.hibernate.Session;
import org.javarush.app.AppConfig;
import org.javarush.domain.entity.Film;
import org.javarush.service.FilmService;

public class CreateFilmCommand implements Command {
    private static final AppConfig appConfig = AppConfig.getInstance();

    @Override
    public void execute() {
        try (Session session = appConfig.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            FilmService filmService = new FilmService();

            Film film = filmService.createNewFilm();
            filmService.addFilmTextToFilm(film);

            session.getTransaction().commit();
        }
    }

    @Override
    public String getTarget() {
        return CommandType.CREATE_FILM.name();
    }

    @Override
    public String getDescription() {
        return "Create a new Film";
    }
}
