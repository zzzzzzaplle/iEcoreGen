package edu.cinemas.cinemas5.test;

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
    
    /**
     * Test Case 1: "Check available room with no screenings"
     * Setup: Create Cinema C1, Add Room1 to C1
     * Action: check Room1 availability at the current time "2024-10-05 13:00:00"
     * Expected Output: true
     */
    @Test
    public void testCase1_CheckAvailableRoomWithNoScreenings() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Room room = factory.createRoom();
        cinema.addRoom(room);
        
        Date checkTime = dateFormat.parse("2024-10-05 13:00:00");
        
        // Action - Check room availability
        boolean isAvailable = isRoomAvailable(cinema, room, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available when no screenings are scheduled", isAvailable);
    }
    
    /**
     * Test Case 2: "Check assigned room"
     * Setup: Create Cinema C1, film F1, Add Room1, film F1 to C1
     * Assign film f1 screening at "2024-10-05 13:00:00", room Room1 (current time "2024-10-04 13:00:00")
     * Action: check Room1 availability at the current time "2024-10-05 13:00:00"
     * Expected Output: false
     */
    @Test
    public void testCase2_CheckAssignedRoom() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Screening screening = factory.createScreening();
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime = dateFormat.parse("2024-10-04 13:00:00");
        
        screening.setTime(screeningTime);
        cinema.assignScreening(film, currentTime, screening, room);
        
        // Action - Check room availability at screening time
        boolean isAvailable = isRoomAvailable(cinema, room, screeningTime);
        
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
    public void testCase3_CheckRoomAtExactScreeningTime() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Screening screening = factory.createScreening();
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        Date currentTime = dateFormat.parse("2024-11-30 14:00:00");
        
        screening.setTime(screeningTime);
        cinema.assignScreening(film, currentTime, screening, room);
        
        // Action - Check room availability at different time
        Date checkTime = dateFormat.parse("2024-12-02 14:00:00");
        boolean isAvailable = isRoomAvailable(cinema, room, checkTime);
        
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
    public void testCase4_CheckMultipleRooms() throws Exception {
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
        
        Screening screening1 = factory.createScreening();
        Screening screening2 = factory.createScreening();
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime1 = dateFormat.parse("2024-10-01 13:00:00");
        Date currentTime2 = dateFormat.parse("2024-10-03 13:00:00");
        
        screening1.setTime(screeningTime);
        screening2.setTime(screeningTime);
        
        cinema.assignScreening(film1, currentTime1, screening1, room1);
        cinema.assignScreening(film2, currentTime2, screening2, room2);
        
        // Action - Check both rooms availability at screening time
        boolean isRoom1Available = isRoomAvailable(cinema, room1, screeningTime);
        boolean isRoom2Available = isRoomAvailable(cinema, room2, screeningTime);
        
        // Expected Output: S1: false, S2: false
        assertFalse("Room1 should not be available when assigned for screening", isRoom1Available);
        assertFalse("Room2 should not be available when assigned for screening", isRoom2Available);
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
    public void testCase5_CheckDifferentTimeSlot() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Screening screening = factory.createScreening();
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime = dateFormat.parse("2024-09-03 13:00:00");
        
        screening.setTime(screeningTime);
        cinema.assignScreening(film, currentTime, screening, room);
        
        // Action - Check room availability at different time slot
        Date checkTime = dateFormat.parse("2024-10-05 14:00:00");
        boolean isAvailable = isRoomAvailable(cinema, room, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", isAvailable);
    }
    
    /**
     * Helper method to check room availability at given time
     * Returns true if room is available, false if already assigned or inputs invalid
     */
    private boolean isRoomAvailable(Cinema cinema, Room room, Date checkTime) {
        // Check if room is added to cinema
        if (!cinema.getRooms().contains(room)) {
            return false; // Room not in cinema
        }
        
        // Check if room has any screening at the given time
        for (Screening screening : cinema.getScreenings()) {
            if (screening.getRoom() == room && screening.getTime().equals(checkTime)) {
                return false; // Room already assigned at this time
            }
        }
        
        return true; // Room is available
    }
}