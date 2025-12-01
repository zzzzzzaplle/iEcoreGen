package edu.cinemas.cinemas4.test;

import edu.cinemas.Cinema;
import edu.cinemas.CinemasFactory;
import edu.cinemas.Film;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR3Test {
    private CinemasFactory factory;

    @Before
    public void setUp() {
        factory = CinemasFactory.eINSTANCE;
    }

    @Test
    public void testCase1_AddNewFilm() {
        // Setup: Create Cinema and Film
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        
        // Action: Add film to cinema
        boolean result = cinema.addFilm(film);
        
        // Verify: Should return true for new film
        assertTrue(result);
    }

    @Test
    public void testCase2_AddDuplicateFilm() {
        // Setup: Create Cinema and Film
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        
        // Add film once (should succeed)
        cinema.addFilm(film);
        
        // Action: Try adding same film again
        boolean result = cinema.addFilm(film);
        
        // Verify: Should return false for duplicate
        assertFalse(result);
    }

    @Test
    public void testCase3_AddSecondUniqueFilmAfterFirst() {
        // Setup: Create Cinema and two Films
        Cinema cinema = factory.createCinema();
        Film film1 = factory.createFilm();
        Film film2 = factory.createFilm();
        
        // Add first film (should succeed)
        cinema.addFilm(film1);
        
        // Action: Add second unique film
        boolean result = cinema.addFilm(film2);
        
        // Verify: Should return true for new film
        assertTrue(result);
    }

    @Test
    public void testCase4_AddSecondUniqueFilmCreatedInAction() {
        // Setup: Create Cinema and first Film
        Cinema cinema = factory.createCinema();
        Film film1 = factory.createFilm();
        
        // Add first film (should succeed)
        cinema.addFilm(film1);
        
        // Create second film and add it
        Film film2 = factory.createFilm();
        boolean result = cinema.addFilm(film2);
        
        // Verify: Should return true for new film
        assertTrue(result);
    }

    @Test
    public void testCase5_ReAddRemovedFilm() {
        // Setup: Create Cinema and Film
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        
        // Add film (should succeed)
        cinema.addFilm(film);
        
        // Remove film (using past date since no screenings exist)
        Date pastDate = new Date(0);
        cinema.removeFilm(film, pastDate);
        
        // Action: Re-add same film
        boolean result = cinema.addFilm(film);
        
        // Verify: Should return true since film was removed
        assertTrue(result);
    }
}