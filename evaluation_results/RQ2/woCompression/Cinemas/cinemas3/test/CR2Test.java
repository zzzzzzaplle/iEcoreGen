package edu.cinemas.cinemas3.test;

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
        
        // Action - Check room availability by attempting to assign a screening
        Film film = factory.createFilm();
        cinema.addFilm(film);
        Screening screening = factory.createScreening();
        screening.setTime(checkTime);
        
        Date currentTime = dateFormat.parse("2024-10-04 13:00:00");
        
        // The room should be available since no screenings are assigned
        boolean isAvailable = cinema.assignScreening(film, currentTime, screening, room);
        
        // Expected Output: true (room is available)
        assertTrue("Room should be available when no screenings exist", isAvailable);
    }
    
    /**
     * Test Case 2: "Check assigned room"
     * Setup: Create Cinema C1, film F1, Add Room1, film F1 to C1,
     * Assign film f1 screening at "2024-10-05 13:00:00", room Room1 (current time "2024-10-04 13:00:00")
     * Action: check Room1 availability at the current time "2024-10-05 13:00:00"
     * Expected Output: false
     */
    @Test
    public void testCase2_CheckAssignedRoom() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Room room = factory.createRoom();
        Film film = factory.createFilm();
        
        cinema.addRoom(room);
        cinema.addFilm(film);
        
        // Assign screening
        Screening screening1 = factory.createScreening();
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTimeSetup = dateFormat.parse("2024-10-04 13:00:00");
        screening1.setTime(screeningTime);
        
        boolean assignmentResult = cinema.assignScreening(film, currentTimeSetup, screening1, room);
        assertTrue("Screening assignment should succeed", assignmentResult);
        
        // Action - Check room availability at the same time
        Screening testScreening = factory.createScreening();
        testScreening.setTime(screeningTime);
        Date currentTimeCheck = dateFormat.parse("2024-10-04 14:00:00");
        
        boolean isAvailable = cinema.assignScreening(film, currentTimeCheck, testScreening, room);
        
        // Expected Output: false (room is already assigned)
        assertFalse("Room should not be available when already assigned", isAvailable);
    }
    
    /**
     * Test Case 3: "Check room at exact screening time"
     * Setup: Create Cinema C1, Add Film F1 and Room R1 to C1,
     * Assign screening for F1 in R1 at "2024-12-01 14:00:00"
     * Action: Check R1 availability at the current time "2024-12-02 14:00:00"
     * Expected Output: true
     */
    @Test
    public void testCase3_CheckRoomAtExactScreeningTime() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Room room = factory.createRoom();
        Film film = factory.createFilm();
        
        cinema.addRoom(room);
        cinema.addFilm(film);
        
        // Assign screening
        Screening screening = factory.createScreening();
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        Date currentTimeSetup = dateFormat.parse("2024-11-30 14:00:00");
        screening.setTime(screeningTime);
        
        boolean assignmentResult = cinema.assignScreening(film, currentTimeSetup, screening, room);
        assertTrue("Screening assignment should succeed", assignmentResult);
        
        // Action - Check room availability at different time
        Screening testScreening = factory.createScreening();
        Date checkTime = dateFormat.parse("2024-12-02 14:00:00");
        testScreening.setTime(checkTime);
        Date currentTimeCheck = dateFormat.parse("2024-12-01 14:00:00");
        
        boolean isAvailable = cinema.assignScreening(film, currentTimeCheck, testScreening, room);
        
        // Expected Output: true (room is available at different time)
        assertTrue("Room should be available at different time slot", isAvailable);
    }
    
    /**
     * Test Case 4: "Check multiple rooms"
     * Setup: Create Cinema C1, film F1, film F2, Add Room1, Room2, film F1, film F2 to C1,
     * Assign film F1 screening S1 at "2024-10-05 13:00:00", room Room1 (current time: "2024-10-01 13:00:00")
     * Assign film F2 screening S2 at "2024-10-05 13:00:00", room Room2 (current time: "2024-10-03 13:00:00")
     * Action: check Room1, Room2 availability at the current time "2024-10-05 13:00:00"
     * Expected Output: S1: false, S2: false
     */
    @Test
    public void testCase4_CheckMultipleRooms() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        Film film1 = factory.createFilm();
        Film film2 = factory.createFilm();
        
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        
        // Assign screenings
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        
        Screening screening1 = factory.createScreening();
        screening1.setTime(screeningTime);
        Date currentTime1 = dateFormat.parse("2024-10-01 13:00:00");
        boolean assignment1 = cinema.assignScreening(film1, currentTime1, screening1, room1);
        assertTrue("First screening assignment should succeed", assignment1);
        
        Screening screening2 = factory.createScreening();
        screening2.setTime(screeningTime);
        Date currentTime2 = dateFormat.parse("2024-10-03 13:00:00");
        boolean assignment2 = cinema.assignScreening(film2, currentTime2, screening2, room2);
        assertTrue("Second screening assignment should succeed", assignment2);
        
        // Action - Check both rooms availability
        Film testFilm = factory.createFilm();
        cinema.addFilm(testFilm);
        
        Screening testScreening1 = factory.createScreening();
        testScreening1.setTime(screeningTime);
        Date currentTimeCheck = dateFormat.parse("2024-10-04 13:00:00");
        boolean availableRoom1 = cinema.assignScreening(testFilm, currentTimeCheck, testScreening1, room1);
        
        Screening testScreening2 = factory.createScreening();
        testScreening2.setTime(screeningTime);
        boolean availableRoom2 = cinema.assignScreening(testFilm, currentTimeCheck, testScreening2, room2);
        
        // Expected Output: both false (both rooms are assigned)
        assertFalse("Room1 should not be available", availableRoom1);
        assertFalse("Room2 should not be available", availableRoom2);
    }
    
    /**
     * Test Case 5: "Check different time slot"
     * Setup: Create Cinema C1, Film F1, Add Room1, Film F1 to C1,
     * Assign Film F1 screening at "2024-10-05 13:00:00", room Room1 (current time: "2024-09-03 13:00:00")
     * Action: check Room1 availability at the current time "2024-10-05 14:00:00"
     * Expected Output: true
     */
    @Test
    public void testCase5_CheckDifferentTimeSlot() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Room room = factory.createRoom();
        Film film = factory.createFilm();
        
        cinema.addRoom(room);
        cinema.addFilm(film);
        
        // Assign screening
        Screening screening = factory.createScreening();
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTimeSetup = dateFormat.parse("2024-09-03 13:00:00");
        screening.setTime(screeningTime);
        
        boolean assignmentResult = cinema.assignScreening(film, currentTimeSetup, screening, room);
        assertTrue("Screening assignment should succeed", assignmentResult);
        
        // Action - Check room availability at different time
        Film testFilm = factory.createFilm();
        cinema.addFilm(testFilm);
        
        Screening testScreening = factory.createScreening();
        Date checkTime = dateFormat.parse("2024-10-05 14:00:00");
        testScreening.setTime(checkTime);
        Date currentTimeCheck = dateFormat.parse("2024-10-04 13:00:00");
        
        boolean isAvailable = cinema.assignScreening(testFilm, currentTimeCheck, testScreening, room);
        
        // Expected Output: true (room is available at different time)
        assertTrue("Room should be available at different time slot", isAvailable);
    }
}