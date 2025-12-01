package edu.cinemas.cinemas2.test;

import edu.cinemas.CinemasFactory;
import edu.cinemas.Cinema;
import edu.cinemas.Film;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR3Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private CinemasFactory factory;

    @Before
    public void setUp() {
        // Create Ecore factory instance
        factory = CinemasFactory.eINSTANCE;
        
        // Create cinema instance using factory
        cinema = factory.createCinema();
        
        // Create film instances using factory
        film1 = factory.createFilm();
        film2 = factory.createFilm();
    }

    @Test
    public void testCase1_AddNewFilm() {
        // Add new film
        boolean result = cinema.addFilm(film1);
        
        // Verify film was added successfully
        assertTrue(result);
    }

    @Test
    public void testCase2_AddDuplicateFilm() {
        // Add film first time
        cinema.addFilm(film1);
        
        // Try to add same film again
        boolean result = cinema.addFilm(film1);
        
        // Verify duplicate film wasn't added
        assertFalse(result);
    }

    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Add first film
        boolean result1 = cinema.addFilm(film1);
        
        // Add second film
        boolean result2 = cinema.addFilm(film2);
        
        // Verify both were added successfully
        assertTrue(result1);
        assertTrue(result2);
    }

    @Test
    public void testCase4_AddSecondUniqueFilm() {
        // Add first film
        cinema.addFilm(film1);
        
        // Add second film
        boolean result = cinema.addFilm(film2);
        
        // Verify second film was added
        assertTrue(result);
    }

    @Test
    public void testCase5_AddFilmAfterRemoval() {
        // Add film initially
        cinema.addFilm(film1);
        
        // Remove film (using arbitrary date since no screenings exist)
        cinema.removeFilm(film1, new java.util.Date());
        
        // Add film again
        boolean result = cinema.addFilm(film1);
        
        // Verify film was successfully re-added
        assertTrue(result);
    }
}