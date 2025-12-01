import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Represents a cinema with rooms, films, and screenings.
 */
 class Cinema {
    private List<Room> rooms;
    private List<Screening> screenings;
    private List<Film> films;
    
    /**
     * Default constructor initializes empty lists for rooms, screenings, and films.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.films = new ArrayList<>();
    }
    
    // Getters and setters
    public List<Room> getRooms() { return rooms; }
    public void setRooms(List<Room> rooms) { this.rooms = rooms; }
    
    public List<Screening> getScreenings() { return screenings; }
    public void setScreenings(List<Screening> screenings) { this.screenings = screenings; }
    
    public List<Film> getFilms() { return films; }
    public void setFilms(List<Film> films) { this.films = films; }
    
    /**
     * Adds a room to the cinema if it doesn't already exist.
     * 
     * @param room The room to add
     * @return true if the room was added successfully, false if it already exists
     */
    public boolean addRoom(Room room) {
        if (!rooms.contains(room)) {
            rooms.add(room);
            return true;
        }
        return false;
    }
    
    /**
     * Checks if a room is available at a given time.
     * 
     * @param room The room to check availability for
     * @param time The time to check availability at (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the room is available, false if already assigned or inputs invalid
     */
    public boolean checkAvailability(Room room, Date time) {
        if (room == null || time == null || !rooms.contains(room)) {
            return false;
        }
        
        for (Screening screening : screenings) {
            if (screening.getRoom().equals(room) && screening.getTime().equals(time)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Adds a film to the cinema if it doesn't already exist.
     * 
     * @param film The film to add
     * @return true if the film was added successfully, false if it already exists
     */
    public boolean addFilm(Film film) {
        if (!films.contains(film)) {
            films.add(film);
            return true;
        }
        return false;
    }
    
    /**
     * Removes a film from the cinema system and all its future screenings.
     * 
     * @param film The film to remove
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the film was removed successfully, false if it doesn't exist
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (!films.contains(film)) {
            return false;
        }
        
        films.remove(film);
        
        // Remove future screenings for this film
        List<Screening> screeningsToRemove = new ArrayList<>();
        for (Screening screening : screenings) {
            if (screening.getFilm().equals(film) && 
                screening.getTime().compareTo(currentTime) >= 0) {
                screeningsToRemove.add(screening);
            }
        }
        screenings.removeAll(screeningsToRemove);
        
        return true;
    }
    
    /**
     * Assigns a screening to a film and room at a specific time.
     * 
     * @param film The film for the screening
     * @param room The room for the screening
     * @param screeningTime The screening time (format: yyyy-MM-dd HH:mm:ss)
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the screening was assigned successfully, false otherwise
     * @throws IllegalArgumentException if screening time is before current time
     */
    public boolean assignScreening(Film film, Room room, Date screeningTime, Date currentTime) {
        if (screeningTime.compareTo(currentTime) <= 0) {
            return false;
        }
        
        if (!films.contains(film) || !rooms.contains(room)) {
            return false;
        }
        
        if (!checkAvailability(room, screeningTime)) {
            return false;
        }
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        screening.setFilm(film);
        screening.setRoom(room);
        
        screenings.add(screening);
        return true;
    }
    
    /**
     * Cancels a future screening.
     * 
     * @param screening The screening to cancel
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if canceled successfully, false otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || !screenings.contains(screening)) {
            return false;
        }
        
        if (screening.getTime().compareTo(currentTime) <= 0) {
            return false;
        }
        
        screenings.remove(screening);
        return true;
    }
}

/**
 * Represents a room in a cinema.
 */
class Room {
    // Default constructor
    public Room() {}
    
    // Additional properties and methods can be added as needed
}

/**
 * Represents a film shown in the cinema.
 */
class Film {
    // Default constructor
    public Film() {}
    
    // Additional properties and methods can be added as needed
}

/**
 * Represents a screening of a film in a room at a specific time.
 */
class Screening {
    private Date time;
    private Film film;
    private Room room;
    
    // Default constructor
    public Screening() {}
    
    // Getters and setters
    public Date getTime() { return time; }
    public void setTime(Date time) { this.time = time; }
    
    public Film getFilm() { return film; }
    public void setFilm(Film film) { this.film = film; }
    
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
}

/**
 * Utility class for date parsing and validation.
 */
class DateUtils {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Parses a date string into a Date object.
     * 
     * @param dateString The date string to parse (format: yyyy-MM-dd HH:mm:ss)
     * @return The parsed Date object
     * @throws ParseException if the date string cannot be parsed
     */
    public static Date parseDate(String dateString) throws ParseException {
        return DATE_FORMAT.parse(dateString);
    }
    
    /**
     * Formats a Date object into a string.
     * 
     * @param date The Date object to format
     * @return The formatted date string (format: yyyy-MM-dd HH:mm:ss)
     */
    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }
}