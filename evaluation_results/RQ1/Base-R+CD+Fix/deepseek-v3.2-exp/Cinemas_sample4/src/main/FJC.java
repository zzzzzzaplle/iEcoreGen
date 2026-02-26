import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;

class Cinema {
    private List<Room> rooms;
    private List<Screening> screenings;
    private List<Film> films;
    
    /**
     * Default constructor for Cinema
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.films = new ArrayList<>();
    }
    
    /**
     * Adds a room to the cinema if it doesn't already exist
     * @param room The room to be added
     * @return true if room was added successfully, false if room already exists
     */
    public boolean addRoom(Room room) {
        if (!rooms.contains(room)) {
            rooms.add(room);
            return true;
        }
        return false;
    }
    
    /**
     * Checks if a room is available at a specific time
     * @param room The room to check availability for
     * @param time The time to check availability at (format: yyyy-MM-dd HH:mm:ss)
     * @return true if room is available, false if room is already assigned or inputs are invalid
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
     * Adds a film to the cinema if it doesn't already exist
     * @param film The film to be added
     * @return true if film was added successfully, false if film already exists
     */
    public boolean addFilm(Film film) {
        if (!films.contains(film)) {
            films.add(film);
            return true;
        }
        return false;
    }
    
    /**
     * Removes a film from the cinema system and all its future screenings
     * @param film The film to be removed
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if film was removed successfully, false if film doesn't exist
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
     * Assigns a screening to a film and room at a specific time
     * @param film The film for the screening
     * @param room The room for the screening
     * @param screeningTime The screening time (format: yyyy-MM-dd HH:mm:ss)
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if screening was assigned successfully, false otherwise
     */
    public boolean assignScreening(Film film, Room room, Date screeningTime, Date currentTime) {
        if (film == null || room == null || screeningTime == null || currentTime == null) {
            return false;
        }
        
        // Verify current time is before screening time
        if (currentTime.compareTo(screeningTime) >= 0) {
            return false;
        }
        
        // Verify film exists in cinema
        if (!films.contains(film)) {
            return false;
        }
        
        // Verify room availability
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
     * Cancels a future screening
     * @param screening The screening to cancel
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if screening was canceled successfully, false otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || currentTime == null) {
            return false;
        }
        
        if (!screenings.contains(screening)) {
            return false;
        }
        
        // Verify screening time is after current time
        if (screening.getTime().compareTo(currentTime) <= 0) {
            return false;
        }
        
        screenings.remove(screening);
        return true;
    }
    
    // Getters and setters
    public List<Room> getRooms() {
        return rooms;
    }
    
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
    
    public List<Screening> getScreenings() {
        return screenings;
    }
    
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }
    
    public List<Film> getFilms() {
        return films;
    }
    
    public void setFilms(List<Film> films) {
        this.films = films;
    }
}

class Room {
    /**
     * Default constructor for Room
     */
    public Room() {
    }
    
    // Room class can be extended with additional properties as needed
    // For now, it's a simple class that can be identified by object reference
}

class Film {
    /**
     * Default constructor for Film
     */
    public Film() {
    }
    
    // Film class can be extended with additional properties as needed
    // For now, it's a simple class that can be identified by object reference
}

class Screening {
    private Date time;
    private Film film;
    private Room room;
    
    /**
     * Default constructor for Screening
     */
    public Screening() {
    }
    
    // Getters and setters
    public Date getTime() {
        return time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }
    
    public Film getFilm() {
        return film;
    }
    
    public void setFilm(Film film) {
        this.film = film;
    }
    
    public Room getRoom() {
        return room;
    }
    
    public void setRoom(Room room) {
        this.room = room;
    }
}

// Helper class for date parsing and validation
class DateHelper {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Parses a date string into a Date object
     * @param dateString The date string in format "yyyy-MM-dd HH:mm:ss"
     * @return Parsed Date object
     * @throws ParseException if the date string is in invalid format
     */
    public static Date parseDate(String dateString) throws ParseException {
        return DATE_FORMAT.parse(dateString);
    }
    
    /**
     * Formats a Date object into a string
     * @param date The Date object to format
     * @return Formatted date string in "yyyy-MM-dd HH:mm:ss" format
     */
    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }
    
    /**
     * Validates if a date string is in the correct format
     * @param dateString The date string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDate(String dateString) {
        try {
            DATE_FORMAT.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}