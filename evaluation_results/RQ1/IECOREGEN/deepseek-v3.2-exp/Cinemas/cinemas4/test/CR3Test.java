package edu.cinemas.cinemas4.test;

import edu.cinemas.CinemasFactory;
import edu.cinemas.Cinema;
import edu.cinemas.Film;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR3Test {
    private CinemasFactory factory;
    private Cinema cinema;
    private Film film1;
    private Film film2;

    @Before
    public void setUp() {
        // Create factory and initialize objects before each test
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
        film1 = factory.createFilm();
        film2 = factory.createFilm();
    }

    @Test
    public void testCase1_addNewFilm() {
        // Action: Add film1 to cinema
        boolean result = cinema.addFilm(film1);
        
        // Verify film was added successfully
        assertTrue("Adding new film should return true", result);
        assertTrue("Film should be in cinema's film list", cinema.getFilm().contains(film1));
    }

    @Test
    public void testCase2_addDuplicateFilm() {
        // Setup: Add film1 to cinema first
        cinema.addFilm(film1);
        
        // Action: Add film1 again
        boolean result = cinema.addFilm(film1);
        
        // Verify duplicate film wasn't added
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Film list should contain only one instance", 1, cinema.getFilm().size());
    }

    @Test
    public void testCase3_addMultipleUniqueFilms() {
        // Setup: Add film1 to cinema
        cinema.addFilm(film1);
        
        // Action: Add film2 to cinema
        boolean result = cinema.addFilm(film2);
        
        // Verify second film was added
        assertTrue("Adding second unique film should return true", result);
        assertTrue("Cinema should contain film1", cinema.getFilm().contains(film1));
        assertTrue("Cinema should contain film2", cinema.getFilm().contains(film2));
    }

    @Test
    public void testCase4_addMultipleUniqueFilmsAfterSetup() {
        // Setup: Add film1 to cinema
        cinema.addFilm(film1);
        
        // Action: Add film2 to cinema
        Film newFilm = factory.createFilm();
        boolean result = cinema.addFilm(newFilm);
        
        // Verify new film was added
        assertTrue("Adding new film after setup should return true", result);
        assertTrue("Cinema should contain the new film", cinema.getFilm().contains(newFilm));
    }

    @Test
    public void testCase5_addFilmAfterRemoval() {
        // Setup: Add and then remove film1
        cinema.addFilm(film1);
        boolean removalResult = cinema.removeFilm(film1, new Date());
        
        // Verify removal succeeded
        assertTrue("Film removal should succeed", removalResult);
        assertFalse("Film should not be in cinema after removal", cinema.getFilm().contains(film1));
        
        // Action: Add film1 again after removal
        boolean addResult = cinema.addFilm(film1);
        
        // Verify film was successfully re-added
        assertTrue("Adding film after removal should return true", addResult);
        assertTrue("Film should be in cinema after re-adding", cinema.getFilm().contains(film1));
    }
}