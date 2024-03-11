package org.javarush.service;

import org.javarush.app.AppConfig;
import org.javarush.dao.ActorDAO;
import org.javarush.dao.CategoryDAO;
import org.javarush.dao.FilmDAO;
import org.javarush.dao.FilmTextDAO;
import org.javarush.dao.LanguageDAO;
import org.javarush.domain.entity.Actor;
import org.javarush.domain.entity.Category;
import org.javarush.domain.entity.Feature;
import org.javarush.domain.entity.Film;
import org.javarush.domain.entity.FilmText;
import org.javarush.domain.entity.Language;
import org.javarush.domain.entity.Rating;

import java.math.BigDecimal;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilmService {
    private final AppConfig appConfig = AppConfig.getInstance();
    private final FilmDAO filmDAO = appConfig.getDAO(FilmDAO.class);
    private final FilmTextDAO filmTextDAO = appConfig.getDAO(FilmTextDAO.class);
    private final ActorDAO actorDAO = appConfig.getDAO(ActorDAO.class);
    private final LanguageDAO languageDAO = appConfig.getDAO(LanguageDAO.class);
    private final CategoryDAO categoryDAO = appConfig.getDAO(CategoryDAO.class);

    public Film createNewFilm() {
        Language language = languageDAO.getItems(0, 20).stream().unordered().findAny().get();
        List<Category> categories = categoryDAO.getItems(0, 5);
        List<Actor> actors = actorDAO.getItems(0, 20);

        Film film = new Film();
        film.setActors(new HashSet<>(actors));
        film.setRating(Rating.NC17);
        film.setSpecialFeatures(Set.of(Feature.TRAILERS, Feature.COMMENTARIES));
        film.setLength((short) 123);
        film.setReplacementCost(BigDecimal.TEN);
        film.setRentalRate(BigDecimal.ZERO);
        film.setLanguage(language);
        film.setDescription("new scary movie");
        film.setTitle("Scary Movie");
        film.setCategories(new HashSet<>(categories));
        film.setRentalDuration((byte) 44);
        film.setOriginalLanguage(language);
        film.setYear(Year.now());

        filmDAO.save(film);
        return film;
    }

    public void addFilmTextToFilm(Film film) {
        FilmText filmText = new FilmText();

        filmText.setFilm(film);
        filmText.setId(film.getId());
        filmText.setTitle("Scary Movie");
        filmText.setDescription("new scary movie");

        filmTextDAO.save(filmText);
    }
}
