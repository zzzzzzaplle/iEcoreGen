import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents a cinema system that manages rooms, films, and screenings.
 */
 class Cinema {
    private List<Room> rooms;
    private List<Screening> screenings;
    private List<Film> films;
    
    /**
     * Default constructor that initializes empty lists for rooms, screenings, and films.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.films = new ArrayList<>();
    }
    
    // Getter and setter methods
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
    
    /**
     * Adds a room to the cinema if it is not already registered.
     * 
     * @param room The room to be added to the cinema
     * @return true if the room was added successfully, false if the room already exists
     */
    public boolean addRoom(Room room) {
        if (rooms.contains(room)) {
            return false;
        }
        rooms.add(room);
        return true;
    }
    
    /**
     * Checks if a room is available at a given time by verifying it exists in the cinema 
     * and is not assigned to another screening at that time.
     * 
     * @param room The room to check availability for
     * @param timeString The time to check in format "yyyy-MM-dd HH:mm:ss"
     * @return true if the room is available, false if the room is already assigned or inputs are invalid
     */
    public boolean checkAvailability(Room room, String timeString) {
        if (room == null || timeString == null) {
            return false;
        }
        
        if (!rooms.contains(room)) {
            return false;
        }
        
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = format.parse(timeString);
            
            for (Screening screening : screenings) {
                if (screening.getRoom().equals(room) && screening.getTime().equals(time)) {
                    return false;
                }
            }
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    /**
     * Adds a film to the cinema if it does not already exist.
     * 
     * @param film The film to be added to the cinema
     * @return true if the film was added successfully, false if the film already exists
     */
    public boolean addFilm(Film film) {
        if (films.contains(film)) {
            return false;
        }
        films.add(film);
        return true;
    }
    
    /**
     * Removes a film from the cinema system at the specified current time, 
     * and removes all of its future scheduled screenings (screening time >= current time).
     * 
     * @param film The film to be removed
     * @param currentTimeString The current time in format "yyyy-MM-dd HH:mm:ss"
     * @return true if the film was removed successfully, false if it does not exist
     */
    public boolean removeFilm(Film film, String currentTimeString) {
        if (!films.contains(film)) {
            return false;
        }
        
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentTime = format.parse(currentTimeString);
            
            // Remove future screenings for this film
            List<Screening> screeningsToRemove = new ArrayList<>();
            for (Screening screening : screenings) {
                if (screening.getFilm().equals(film) && 
                    !screening.getTime().before(currentTime)) {
                    screeningsToRemove.add(screening);
                }
            }
            screenings.removeAll(screeningsToRemove);
            
            // Remove the film
            films.remove(film);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    /**
     * Assigns a screening to a film and room at a specific screening time.
     * Verifies that the current time is before the screening time, the film is added to the cinema,
     * and the room is available at the screening time.
     * 
     * @param film The film for the screening
     * @param room The room for the screening
     * @param screening The screening to be assigned
     * @param currentTimeString The current time in format "yyyy-MM-dd HH:mm:ss"
     * @return true if the screening was assigned successfully, false otherwise
     */
    public boolean assignScreening(Film film, Room room, Screening screening, String currentTimeString) {
        if (film == null || room == null || screening == null || currentTimeString == null) {
            return false;
        }
        
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentTime = format.parse(currentTimeString);
            Date screeningTime = screening.getTime();
            
            // Verify current time is before screening time
            if (!currentTime.before(screeningTime)) {
                return false;
            }
            
            // Verify film is added to cinema
            if (!films.contains(film)) {
                return false;
            }
            
            // Verify room availability
            if (!checkAvailability(room, format.format(screeningTime))) {
                return false;
            }
            
            // Set film and room for screening
            screening.setFilm(film);
            screening.setRoom(room);
            
            // Add screening to cinema
            screenings.add(screening);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    /**
     * Cancels a future screening at a given current time.
     * Checks if the screening exists in the cinema and the screening time is after the current time.
     * 
     * @param screening The screening to be canceled
     * @param currentTimeString The current time in format "yyyy-MM-dd HH:mm:ss"
     * @return true if canceled successfully, false otherwise
     */
    public boolean cancelScreening(Screening screening, String currentTimeString) {
        if (screening == null || currentTimeString == null) {
            return false;
        }
        
        if (!screenings.contains(screening)) {
            return false;
        }
        
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentTime = format.parse(currentTimeString);
            Date screeningTime = screening.getTime();
            
            // Verify screening time is after current time
            if (!screeningTime.after(currentTime)) {
                return false;
            }
            
            screenings.remove(screening);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}

/**
 * Represents a room in a cinema.
 */
class Room {
    /**
     * Default constructor.
     */
    public Room() {
    }
    
    // Room class can be extended with additional properties as needed
}

/**
 * Represents a film in the cinema system.
 */
class Film {
    /**
     * Default constructor.
     */
    public Film() {
    }
    
    // Film class can be extended with additional properties as needed
}

/**
 * Represents a screening of a film in a room at a specific time.
 */
class Screening {
    private Date time;
    private Film film;
    private Room room;
    
    /**
     * Default constructor.
     */
    public Screening() {
    }
    
    // Getter and setter methods
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