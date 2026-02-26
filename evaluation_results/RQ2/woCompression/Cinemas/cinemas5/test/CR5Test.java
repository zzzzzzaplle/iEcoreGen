package edu.cinemas.cinemas5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import edu.cinemas.Cinema;
import edu.cinemas.Film;
import edu.cinemas.Room;
import edu.cinemas.Screening;
import edu.cinemas.CinemasFactory;

public class CR5Test {
    
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private Room room1;
    private Room room2;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        // Initialize cinema and factory objects using Ecore factory pattern
        cinema = CinemasFactory.eINSTANCE.createCinema();
        film1 = CinemasFactory.eINSTANCE.createFilm();
        film2 = CinemasFactory.eINSTANCE.createFilm();
        room1 = CinemasFactory.eINSTANCE.createRoom();
        room2 = CinemasFactory.eINSTANCE.createRoom();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidFutureScreeningAssignment() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Screening screening = CinemasFactory.eINSTANCE.createScreening();
        screening.setTime(screeningTime);
        
        boolean result = cinema.assignScreening(film1, currentTime, screening, room1);
        
        // Expected Output: true
        assertTrue("Valid future screening should be assigned successfully", result);
        assertTrue("Screening should be added to cinema screenings", cinema.getScreenings().contains(screening));
        assertEquals("Screening should be assigned to correct film", film1, screening.getFilm());
        assertEquals("Screening should be assigned to correct room", room1, screening.getRoom());
    }
    
    @Test
    public void testCase2_AssignToAlreadyBookedRoom() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // First assignment
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Screening screening1 = CinemasFactory.eINSTANCE.createScreening();
        screening1.setTime(screeningTime);
        cinema.assignScreening(film1, currentTime, screening1, room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
        Screening screening2 = CinemasFactory.eINSTANCE.createScreening();
        screening2.setTime(screeningTime);
        boolean result = cinema.assignScreening(film1, currentTime, screening2, room1);
        
        // Expected Output: false
        assertFalse("Should not assign screening to already booked room", result);
        assertFalse("Second screening should not be added to cinema", cinema.getScreenings().contains(screening2));
    }
    
    @Test
    public void testCase3_AssignWithNonExistentFilm() throws Exception {
        // Setup: Create Cinema C1, Add Room R1 to C1
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Screening screening = CinemasFactory.eINSTANCE.createScreening();
        screening.setTime(screeningTime);
        
        boolean result = cinema.assignScreening(film2, currentTime, screening, room1);
        
        // Expected Output: false
        assertFalse("Should not assign screening with non-existent film", result);
        assertFalse("Screening should not be added to cinema", cinema.getScreenings().contains(screening));
    }
    
    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = CinemasFactory.eINSTANCE.createScreening();
        screening.setTime(screeningTime);
        
        boolean result = cinema.assignScreening(film1, currentTime, screening, room1);
        
        // Expected Output: false
        assertFalse("Should not assign screening at current time boundary", result);
        assertFalse("Screening should not be added to cinema", cinema.getScreenings().contains(screening));
    }
    
    @Test
    public void testCase5_AssignScreeningInPastTime() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-11-30 14:00:00");
        Screening screening = CinemasFactory.eINSTANCE.createScreening();
        screening.setTime(screeningTime);
        
        boolean result = cinema.assignScreening(film1, currentTime, screening, room1);
        
        // Expected Output: false
        assertFalse("Should not assign screening in past time", result);
        assertFalse("Screening should not be added to cinema", cinema.getScreenings().contains(screening));
    }
    
    @Test
    public void testCase6_AssignToNonExistentRoom() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Screening screening = CinemasFactory.eINSTANCE.createScreening();
        screening.setTime(screeningTime);
        
        boolean result = cinema.assignScreening(film1, currentTime, screening, room2);
        
        // Expected Output: false
        assertFalse("Should not assign screening to non-existent room", result);
        assertFalse("Screening should not be added to cinema", cinema.getScreenings().contains(screening));
    }
}