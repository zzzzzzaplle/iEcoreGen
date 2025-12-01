package edu.cinemas.cinemas4.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import edu.cinemas.Cinema;
import edu.cinemas.CinemasFactory;
import edu.cinemas.Film;
import edu.cinemas.Room;
import edu.cinemas.Screening;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    private CinemasFactory factory;
    private Cinema cinema;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    private Date createDate(String dateString) throws Exception {
        return dateFormat.parse(dateString);
    }

    // Helper method to check room availability
    private boolean isRoomAvailable(Room room, Date time) {
        // Check if room is registered in cinema
        if (!cinema.getRooms().contains(room)) {
            return false;
        }

        // Check for conflicting screenings
        for (Screening screening : cinema.getScreenings()) {
            if (screening.getRoom().equals(room) && 
                screening.getTime().equals(time)) {
                return false;
            }
        }
        
        return true;
    }

    @Test
    public void testCase1_CheckAvailableRoomWithNoScreenings() throws Exception {
        // Setup
        Room room1 = factory.createRoom();
        cinema.addRoom(room1);
        
        // Action
        Date checkTime = createDate("2024-10-05 13:00:00");
        boolean isAvailable = isRoomAvailable(room1, checkTime);
        
        // Verification
        assertTrue("Room should be available with no screenings", isAvailable);
    }

    @Test
    public void testCase2_CheckAssignedRoom() throws Exception {
        // Setup
        Room room1 = factory.createRoom();
        Film film1 = factory.createFilm();
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        Screening screening = factory.createScreening();
        screening.setTime(createDate("2024-10-05 13:00:00"));
        
        // Assign screening
        cinema.assignScreening(film1, createDate("2024-10-04 13:00:00"), 
                               screening, room1);
        
        // Action
        Date checkTime = createDate("2024-10-05 13:00:00");
        boolean isAvailable = isRoomAvailable(room1, checkTime);
        
        // Verification
        assertFalse("Room should be occupied by a screening", isAvailable);
    }

    @Test
    public void testCase3_CheckRoomAtExactScreeningTime() throws Exception {
        // Setup
        Room room1 = factory.createRoom();
        Film film1 = factory.createFilm();
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        Screening screening = factory.createScreening();
        screening.setTime(createDate("2024-12-01 14:00:00"));
        cinema.assignScreening(film1, createDate("2024-11-30 14:00:00"), 
                               screening, room1);
        
        // Action: Check different time
        Date checkTime = createDate("2024-12-02 14:00:00");
        boolean isAvailable = isRoomAvailable(room1, checkTime);
        
        // Verification
        assertTrue("Room should be available at different time", isAvailable);
    }

    @Test
    public void testCase4_CheckMultipleRooms() throws Exception {
        // Setup
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        Film film1 = factory.createFilm();
        Film film2 = factory.createFilm();
        
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        
        Screening screening1 = factory.createScreening();
        screening1.setTime(createDate("2024-10-05 13:00:00"));
        cinema.assignScreening(film1, createDate("2024-10-01 13:00:00"), 
                              screening1, room1);
        
        Screening screening2 = factory.createScreening();
        screening2.setTime(createDate("2024-10-05 13:00:00"));
        cinema.assignScreening(film2, createDate("2024-10-03 13:00:00"), 
                              screening2, room2);
        
        // Action
        Date checkTime = createDate("2024-10-05 13:00:00");
        boolean room1Available = isRoomAvailable(room1, checkTime);
        boolean room2Available = isRoomAvailable(room2, checkTime);
        
        // Verification
        assertFalse("Room1 should be occupied", room1Available);
        assertFalse("Room2 should be occupied", room2Available);
    }

    @Test
    public void testCase5_CheckDifferentTimeSlot() throws Exception {
        // Setup
        Room room1 = factory.createRoom();
        Film film1 = factory.createFilm();
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        Screening screening = factory.createScreening();
        screening.setTime(createDate("2024-10-05 13:00:00"));
        cinema.assignScreening(film1, createDate("2024-09-03 13:00:00"), 
                               screening, room1);
        
        // Action: Check different time slot
        Date checkTime = createDate("2024-10-05 14:00:00");
        boolean isAvailable = isRoomAvailable(room1, checkTime);
        
        // Verification
        assertTrue("Room should be available at different time slot", isAvailable);
    }
}