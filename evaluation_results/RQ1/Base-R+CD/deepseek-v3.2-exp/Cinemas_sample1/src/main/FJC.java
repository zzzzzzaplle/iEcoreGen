import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a cinema that contains rooms, films, and screenings.
 * Provides functionality to manage rooms, films, and screenings.
 */
 class Cinema {
    private List<Room> rooms;
    private List<Screening> screenings;
    private List<Film> films;

    /**
     * Default constructor for Cinema.
     * Initializes empty lists for rooms, screenings, and films.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.films = new ArrayList<>();
    }

    /**
     * Gets the list of rooms in the cinema.
     * @return List of Room objects
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Sets the list of rooms in the cinema.
     * @param rooms List of Room objects
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Gets the list of screenings in the cinema.
     * @return List of Screening objects
     */
    public List<Screening> getScreenings() {
        return screenings;
    }

    /**
     * Sets the list of screenings in the cinema.
     * @param screenings List of Screening objects
     */
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    /**
     * Gets the list of films in the cinema.
     * @return List of Film objects
     */
    public List<Film> getFilms() {
        return films;
    }

    /**
     * Sets the list of films in the cinema.
     * @param films List of Film objects
     */
    public void setFilms(List<Film> films) {
        this.films = films;
    }

    /**
     * Adds a room to the cinema if it is not already registered.
     * @param room The room to be added
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
     * Checks if a room is available at a given time.
     * Verifies that the room exists in the cinema and is not assigned to another screening at the given time.
     * @param room The room to check availability for
     * @param time The time to check availability at (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the room is available, false if the room is already assigned or inputs are invalid
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
     * Adds a film to the cinema if it does not already exist.
     * @param film The film to be added
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
     * Removes a film from the cinema system and removes all its future scheduled screenings.
     * @param film The film to be removed
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the film was removed successfully, false if the film does not exist
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (!films.contains(film)) {
            return false;
        }
        
        films.remove(film);
        
        // Remove future screenings for this film
        List<Screening> screeningsToRemove = new ArrayList<>();
        for (Screening screening : screenings) {
            if (screening.getFilm().equals(film) && screening.getTime().compareTo(currentTime) >= 0) {
                screeningsToRemove.add(screening);
            }
        }
        screenings.removeAll(screeningsToRemove);
        
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific screening time.
     * Verifies that the current time is before the screening time, the film exists in the cinema,
     * and the room is available at the screening time.
     * @param film The film for the screening
     * @param room The room for the screening
     * @param screeningTime The screening time (format: yyyy-MM-dd HH:mm:ss)
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the screening was assigned successfully, false otherwise
     */
    public boolean assignScreening(Film film, Room room, Date screeningTime, Date currentTime) {
        if (film == null || room == null || screeningTime == null || currentTime == null) {
            return false;
        }
        
        if (currentTime.compareTo(screeningTime) >= 0) {
            return false;
        }
        
        if (!films.contains(film)) {
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
     * Cancels a future screening at a given current time.
     * Checks if the screening exists and if the screening time is after the current time.
     * @param screening The screening to cancel
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if canceled successfully, false otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || currentTime == null) {
            return false;
        }
        
        if (!screenings.contains(screening)) {
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
    /**
     * Default constructor for Room.
     */
    public Room() {
    }
    
    // Room class could be extended with additional properties like capacity, name, etc.
    // For now, it's a simple class as per the design model
}

/**
 * Represents a film in a cinema.
 */
class Film {
    /**
     * Default constructor for Film.
     */
    public Film() {
    }
    
    // Film class could be extended with additional properties like title, duration, etc.
    // For now, it's a simple class as per the design model
}

/**
 * Represents a screening of a film in a room at a specific time.
 */
class Screening {
    private Date time;
    private Film film;
    private Room room;

    /**
     * Default constructor for Screening.
     */
    public Screening() {
    }

    /**
     * Gets the screening time.
     * @return The screening time
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets the screening time.
     * @param time The screening time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Gets the film for this screening.
     * @return The film object
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Sets the film for this screening.
     * @param film The film to set
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * Gets the room for this screening.
     * @return The room object
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room for this screening.
     * @param room The room to set
     */
    public void setRoom(Room room) {
        this.room = room;
    }
}