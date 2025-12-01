package edu.cinemas.cinemas2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.cinemas.CinemasFactory;
import edu.cinemas.Cinema;
import edu.cinemas.Film;
import java.util.Date;

public class CR3Test {
    
    private CinemasFactory factory;
    private Cinema cinema;
    
    @Before
    public void setUp() {
        // Initialize factory and create cinema instance using Ecore factory pattern
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
    }
    
    @Test
    public void testCase1_AddNewFilm() {
        // Setup: Create Cinema C1, Film F1
        Film film1 = factory.createFilm();
        
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
        Film film1 = factory.createFilm();
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
        Film film1 = factory.createFilm();
        Film film2 = factory.createFilm();
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertTrue("First film should be in cinema's film list", cinema.getFilm().contains(film1));
        assertTrue("Second film should be in cinema's film list", cinema.getFilm().contains(film2));
        assertEquals("Film list should contain two films", 2, cinema.getFilm().size());
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilms() {
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        Film film1 = factory.createFilm();
        Film film2 = factory.createFilm();
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertTrue("First film should be in cinema's film list", cinema.getFilm().contains(film1));
        assertTrue("Second film should be in cinema's film list", cinema.getFilm().contains(film2));
        assertEquals("Film list should contain two films", 2, cinema.getFilm().size());
    }
    
    @Test
    public void testCase5_AddMultipleUniqueFilms() {
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        // 3. Remove Film F1 from Cinema C1 (true)
        Film film1 = factory.createFilm();
        cinema.addFilm(film1);
        
        // Create a current time for removal
        Date currentTime = new Date();
        cinema.removeFilm(film1, currentTime);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding previously removed film should return true", result);
        assertTrue("Film should be in cinema's film list after re-adding", cinema.getFilm().contains(film1));
        assertEquals("Film list should contain one film", 1, cinema.getFilm().size());
    }
}