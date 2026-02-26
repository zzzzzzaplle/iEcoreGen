package edu.cinemas.cinemas2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.cinemas.Cinema;
import edu.cinemas.CinemasFactory;
import edu.cinemas.Film;
import edu.cinemas.Room;
import edu.cinemas.Screening;

public class CR2Test {
    
    private CinemasFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = CinemasFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    private Date parseDate(String dateString) throws Exception {
        return dateFormat.parse(dateString);
    }
    
    /**
     * Test Case 1: "Check available room with no screenings"
     * Setup: Create Cinema C1, Add Room1 to C1
     * Action: check Room1 availability at the current time "2024-10-05 13:00:00"
     * Expected Output: true
     */
    @Test
    public void testCase1_checkAvailableRoomWithNoScreenings() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Room room = factory.createRoom();
        cinema.addRoom(room);
        
        Date checkTime = parseDate("2024-10-05 13:00:00");
        
        // Action - Check room availability (no screenings assigned)
        boolean isAvailable = true; // Room has no screenings, so it's available
        for (Screening screening : cinema.getScreenings()) {
            if (screening.getRoom() == room && screening.getTime().equals(checkTime)) {
                isAvailable = false;
                break;
            }
        }
        
        // Expected Output: true
        assertTrue("Room should be available when no screenings are assigned", isAvailable);
    }
    
    /**
     * Test Case 2: "Check assigned room"
     * Setup: Create Cinema C1, film F1, Add Room1, film F1 to C1
     * Assign film f1 screening at "2024-10-05 13:00:00", room Room1 (current time "2024-10-04 13:00:00")
     * Action: check Room1 availability at the current time "2024-10-05 13:00:00"
     * Expected Output: false
     */
    @Test
    public void testCase2_checkAssignedRoom() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Date currentTime = parseDate("2024-10-04 13:00:00");
        Date screeningTime = parseDate("2024-10-05 13:00:00");
        
        Screening screening = factory.createScreening();
        screening.setTime(screeningTime);
        
        // Assign screening
        cinema.assignScreening(film, currentTime, screening, room);
        
        Date checkTime = parseDate("2024-10-05 13:00:00");
        
        // Action - Check room availability
        boolean isAvailable = true;
        for (Screening s : cinema.getScreenings()) {
            if (s.getRoom() == room && s.getTime().equals(checkTime)) {
                isAvailable = false;
                break;
            }
        }
        
        // Expected Output: false
        assertFalse("Room should not be available when assigned for screening", isAvailable);
    }
    
    /**
     * Test Case 3: "Check room at exact screening time"
     * Setup: Create Cinema C1, Add Film F1 and Room R1 to C1
     * Assign screening for F1 in R1 at "2024-12-01 14:00:00"
     * Action: Check R1 availability at the current time "2024-12-02 14:00:00"
     * Expected Output: true
     */
    @Test
    public void testCase3_checkRoomAtExactScreeningTime() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Date currentTime = parseDate("2024-11-30 14:00:00");
        Date screeningTime = parseDate("2024-12-01 14:00:00");
        
        Screening screening = factory.createScreening();
        screening.setTime(screeningTime);
        
        // Assign screening
        cinema.assignScreening(film, currentTime, screening, room);
        
        Date checkTime = parseDate("2024-12-02 14:00:00");
        
        // Action - Check room availability at different time
        boolean isAvailable = true;
        for (Screening s : cinema.getScreenings()) {
            if (s.getRoom() == room && s.getTime().equals(checkTime)) {
                isAvailable = false;
                break;
            }
        }
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", isAvailable);
    }
    
    /**
     * Test Case 4: "Check multiple rooms"
     * Setup: Create Cinema C1, film F1, film F2
     * Add Room1, Room2, film F1, film F2 to C1
     * Assign film F1 screening S1 at "2024-10-05 13:00:00", room Room1 (current time: "2024-10-01 13:00:00")
     * Assign film F2 screening S2 at "2024-10-05 13:00:00", room Room2 (current time: "2024-10-03 13:00:00")
     * Action: check Room1, Room2 availability at the current time "2024-10-05 13:00:00"
     * Expected Output: S1: false, S2: false
     */
    @Test
    public void testCase4_checkMultipleRooms() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film1 = factory.createFilm();
        Film film2 = factory.createFilm();
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        
        Date currentTime1 = parseDate("2024-10-01 13:00:00");
        Date currentTime2 = parseDate("2024-10-03 13:00:00");
        Date screeningTime = parseDate("2024-10-05 13:00:00");
        
        Screening screening1 = factory.createScreening();
        screening1.setTime(screeningTime);
        Screening screening2 = factory.createScreening();
        screening2.setTime(screeningTime);
        
        // Assign screenings
        cinema.assignScreening(film1, currentTime1, screening1, room1);
        cinema.assignScreening(film2, currentTime2, screening2, room2);
        
        Date checkTime = parseDate("2024-10-05 13:00:00");
        
        // Action - Check room availability for both rooms
        boolean isRoom1Available = true;
        boolean isRoom2Available = true;
        
        for (Screening s : cinema.getScreenings()) {
            if (s.getRoom() == room1 && s.getTime().equals(checkTime)) {
                isRoom1Available = false;
            }
            if (s.getRoom() == room2 && s.getTime().equals(checkTime)) {
                isRoom2Available = false;
            }
        }
        
        // Expected Output: S1: false, S2: false
        assertFalse("Room1 should not be available", isRoom1Available);
        assertFalse("Room2 should not be available", isRoom2Available);
    }
    
    /**
     * Test Case 5: "Check different time slot"
     * Setup: Create Cinema C1, Film F1
     * Add Room1, Film F1 to C1
     * Assign Film F1 screening at "2024-10-05 13:00:00", room Room1 (current time: "2024-09-03 13:00:00")
     * Action: check Room1 availability at the current time "2024-10-05 14:00:00"
     * Expected Output: true
     */
    @Test
    public void testCase5_checkDifferentTimeSlot() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Date currentTime = parseDate("2024-09-03 13:00:00");
        Date screeningTime = parseDate("2024-10-05 13:00:00");
        
        Screening screening = factory.createScreening();
        screening.setTime(screeningTime);
        
        // Assign screening
        cinema.assignScreening(film, currentTime, screening, room);
        
        Date checkTime = parseDate("2024-10-05 14:00:00");
        
        // Action - Check room availability at different time
        boolean isAvailable = true;
        for (Screening s : cinema.getScreenings()) {
            if (s.getRoom() == room && s.getTime().equals(checkTime)) {
                isAvailable = false;
                break;
            }
        }
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", isAvailable);
    }
}