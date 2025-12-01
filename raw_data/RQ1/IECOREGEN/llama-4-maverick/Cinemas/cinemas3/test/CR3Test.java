package edu.cinemas.cinemas3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.cinemas.Cinema;
import edu.cinemas.Film;
import edu.cinemas.CinemasFactory;
import edu.cinemas.CinemasPackage;

public class CR3Test {
    
    private Cinema cinema;
    private Film film1;
    private Film film2;
    
    @Before
    public void setUp() {
        // Initialize the cinema package and create test objects using Ecore factory
        CinemasPackage.eINSTANCE.eClass();
        cinema = CinemasFactory.eINSTANCE.createCinema();
        film1 = CinemasFactory.eINSTANCE.createFilm();
        film2 = CinemasFactory.eINSTANCE.createFilm();
    }
    
    @Test
    public void testCase1_AddNewFilm() {
        // Setup: Create Cinema C1, Film F1
        // Action: add Film F1 to Cinema C1
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding new film should return true", result);
        assertTrue("Film should be in cinema's film list", cinema.getFilm().contains(film1));
    }
    
    @Test
    public void testCase2_AddDuplicateFilm() {
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 first
        cinema.addFilm(film1);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: false
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Film list should contain only one instance", 1, cinema.getFilm().size());
    }
    
    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Setup:
        // 1. Create Cinema C1, Film F1, Film F2
        // 2. Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertTrue("First film should still be in list", cinema.getFilm().contains(film1));
        assertTrue("Second film should be in list", cinema.getFilm().contains(film2));
        assertEquals("Film list should contain both films", 2, cinema.getFilm().size());
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilms_Alternative() {
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertTrue("First film should be in list", cinema.getFilm().contains(film1));
        assertTrue("Second film should be in list", cinema.getFilm().contains(film2));
    }
    
    @Test
    public void testCase5_AddFilmAfterRemoval() {
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // 3. Remove Film F1 from Cinema C1 (true)
        // Create a current time for removal (using a past date since we don't have screenings)
        java.util.Date currentTime = new java.util.Date(System.currentTimeMillis() - 1000000);
        boolean removeResult = cinema.removeFilm(film1, currentTime);
        assertTrue("Removing film should return true", removeResult);
        assertFalse("Film should not be in list after removal", cinema.getFilm().contains(film1));
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding previously removed film should return true", result);
        assertTrue("Film should be back in cinema's film list", cinema.getFilm().contains(film1));
    }
}