package edu.cinemas.cinemas5.test;

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
        // Setup: Create Cinema C1 and add Film F1 first
        Film film1 = factory.createFilm();
        cinema.addFilm(film1); // First addition
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: false
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Film list should contain only one instance", 1, cinema.getFilm().size());
    }
    
    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Setup: Create Cinema C1, Film F1, Film F2
        Film film1 = factory.createFilm();
        Film film2 = factory.createFilm();
        
        // Add Film F1 to Cinema C1 (true)
        boolean result1 = cinema.addFilm(film1);
        assertTrue("First film addition should succeed", result1);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result2 = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Second unique film addition should return true", result2);
        assertEquals("Cinema should contain both films", 2, cinema.getFilm().size());
        assertTrue("Film1 should be in cinema", cinema.getFilm().contains(film1));
        assertTrue("Film2 should be in cinema", cinema.getFilm().contains(film2));
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilmsAlternative() {
        // Setup: Create Cinema C1
        Film film1 = factory.createFilm();
        Film film2 = factory.createFilm();
        
        // Add Film F1 to Cinema C1 (true)
        boolean result1 = cinema.addFilm(film1);
        assertTrue("First film addition should succeed", result1);
        
        // Action: add Film F2 to Cinema C1
        boolean result2 = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result2);
        assertEquals("Cinema should contain exactly 2 films", 2, cinema.getFilm().size());
    }
    
    @Test
    public void testCase5_AddFilmAfterRemoval() {
        // Setup: Create Cinema C1
        Film film1 = factory.createFilm();
        Date currentTime = new Date();
        
        // Add Film F1 to Cinema C1 (true)
        boolean addResult1 = cinema.addFilm(film1);
        assertTrue("Initial film addition should succeed", addResult1);
        
        // Remove Film F1 from Cinema C1 (true)
        boolean removeResult = cinema.removeFilm(film1, currentTime);
        assertTrue("Film removal should succeed", removeResult);
        
        // Action: add Film F1 to Cinema C1 again
        boolean addResult2 = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding film after removal should return true", addResult2);
        assertTrue("Film should be back in cinema's film list", cinema.getFilm().contains(film1));
    }
}