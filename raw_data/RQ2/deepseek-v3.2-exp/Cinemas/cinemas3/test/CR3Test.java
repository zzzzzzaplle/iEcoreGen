package edu.cinemas.cinemas3.test;

import edu.cinemas.CinemasFactory;
import edu.cinemas.Cinema;
import edu.cinemas.Film;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR3Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    
    @Before
    public void setUp() {
        // Create cinema and films using Ecore factory
        CinemasFactory factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
        film1 = factory.createFilm();
        film2 = factory.createFilm();
    }
    
    // Test Case 1: Add new film
    @Test
    public void testCase1_AddNewFilm() {
        // Action: Add film1 to cinema
        boolean result = cinema.addFilm(film1);
        
        // Verify film was added
        assertTrue(result);
        assertTrue("Film should be in cinema's film list", cinema.getFilm().contains(film1));
    }
    
    // Test Case 2: Add duplicate film
    @Test
    public void testCase2_AddDuplicateFilm() {
        // Setup: Add film1 to cinema
        cinema.addFilm(film1);
        
        // Action: Add same film again
        boolean result = cinema.addFilm(film1);
        
        // Verify duplicate not added
        assertFalse(result);
        assertEquals("Film list should contain only one instance", 1, cinema.getFilm().size());
    }
    
    // Test Case 3: Add multiple unique films (first film)
    @Test
    public void testCase3_AddFirstFilm() {
        // Action: Add film1 to cinema
        boolean result = cinema.addFilm(film1);
        
        // Verify first film added
        assertTrue(result);
        assertTrue("Film1 should be in list", cinema.getFilm().contains(film1));
    }
    
    // Test Case 4: Add multiple unique films (second film)
    @Test
    public void testCase4_AddSecondFilm() {
        // Setup: Add film1 to cinema
        cinema.addFilm(film1);
        
        // Action: Add film2 to cinema
        boolean result = cinema.addFilm(film2);
        
        // Verify second film added
        assertTrue(result);
        assertTrue("Film2 should be in list", cinema.getFilm().contains(film2));
        assertEquals("Cinema should have 2 films", 2, cinema.getFilm().size());
    }
    
    // Test Case 5: Re-add after removal
    @Test
    public void testCase5_ReAddAfterRemoval() {
        // Setup: Add then remove film1
        cinema.addFilm(film1);
        cinema.removeFilm(film1, new Date()); // Dummy date for removal
        
        // Action: Add film1 again
        boolean result = cinema.addFilm(film1);
        
        // Verify film re-added successfully
        assertTrue(result);
        assertTrue("Film1 should be back in list", cinema.getFilm().contains(film1));
    }
}