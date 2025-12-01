package edu.cinemas.cinemas2.test;

import edu.cinemas.Cinema;
import edu.cinemas.CinemasFactory;
import edu.cinemas.Film;
import edu.cinemas.Room;
import edu.cinemas.Screening;
import org.junit.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.*;

public class CR4Test {
    private final CinemasFactory factory = CinemasFactory.eINSTANCE;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private Date parseDate(String dateStr) throws ParseException {
        return sdf.parse(dateStr);
    }

    @Test
    public void testCase1_RemoveFilmWithNoScreenings() throws ParseException {
        // Create Cinema C1
        Cinema cinema = factory.createCinema();
        
        // Add Film F1 to C1
        Film film = factory.createFilm();
        cinema.getFilm().add(film);
        
        // Remove Film F1 at current time
        boolean result = cinema.removeFilm(film, parseDate("2024-12-01 10:00:00"));
        
        // Verify removal success and film no longer exists
        assertTrue(result);
        assertFalse(cinema.getFilm().contains(film));
    }

    @Test
    public void testCase2_RemoveNonExistentFilm() throws ParseException {
        // Create Cinema C1
        Cinema cinema = factory.createCinema();
        
        // Create Film F1 but don't add to cinema
        Film film = factory.createFilm();
        
        // Attempt to remove non-existent film
        boolean result = cinema.removeFilm(film, parseDate("2024-12-01 10:00:00"));
        
        // Verify removal failed
        assertFalse(result);
    }

    @Test
    public void testCase3_RemoveFilmWithFutureScreening() throws ParseException {
        // Create Cinema C1
        Cinema cinema = factory.createCinema();
        
        // Add Film F1 to C1
        Film film = factory.createFilm();
        cinema.getFilm().add(film);
        
        // Create and assign screening
        Room room = factory.createRoom();
        cinema.getRooms().add(room);
        
        Screening screening = factory.createScreening();
        screening.setFilm(film);
        screening.setRoom(room);
        screening.setTime(parseDate("2024-12-02 15:00:00"));
        cinema.getScreenings().add(screening);
        
        // Remove film with future screening
        boolean result = cinema.removeFilm(film, parseDate("2024-12-01 10:00:00"));
        
        // Verify removal success and no screenings remain
        assertTrue(result);
        assertFalse(cinema.getFilm().contains(film));
        assertTrue(cinema.getScreenings().isEmpty());
    }

    @Test
    public void testCase4_RemoveFilmWithPastScreening() throws ParseException {
        // Create Cinema C1
        Cinema cinema = factory.createCinema();
        
        // Add Film F1 to C1
        Film film = factory.createFilm();
        cinema.getFilm().add(film);
        
        // Create past screening
        Room room = factory.createRoom();
        cinema.getRooms().add(room);
        
        Screening screening = factory.createScreening();
        screening.setFilm(film);
        screening.setRoom(room);
        screening.setTime(parseDate("2024-11-30 18:00:00"));
        cinema.getScreenings().add(screening);
        
        // Remove film at current time
        boolean result = cinema.removeFilm(film, parseDate("2024-12-01 10:00:00"));
        
        // Verify removal success and screening remains
        assertTrue(result);
        assertFalse(cinema.getFilm().contains(film));
        assertEquals(1, cinema.getScreenings().size());
    }

    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() throws ParseException {
        // Create Cinema C1
        Cinema cinema = factory.createCinema();
        
        // Add Film F1 to C1
        Film film = factory.createFilm();
        cinema.getFilm().add(film);
        
        // Create screening at current time
        Room room = factory.createRoom();
        cinema.getRooms().add(room);
        
        Date screeningTime = parseDate("2024-12-01 10:00:00");
        Screening screening = factory.createScreening();
        screening.setFilm(film);
        screening.setRoom(room);
        screening.setTime(screeningTime);
        cinema.getScreenings().add(screening);
        
        // Remove film with current-time screening
        boolean result = cinema.removeFilm(film, screeningTime);
        
        // Verify removal success and no screenings remain
        assertTrue(result);
        assertFalse(cinema.getFilm().contains(film));
        assertTrue(cinema.getScreenings().isEmpty());
    }
}