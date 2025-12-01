import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents a cinema which manages rooms, films, and screenings.
 */
class Cinema {
    private List<Room> rooms;
    private List<Screening> screenings;
    private List<Film> films;

    /**
     * Constructs a new Cinema with empty lists for rooms, screenings, and films.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.films = new ArrayList<>();
    }

    /**
     * Adds a room to the cinema if it is not already registered.
     *
     * @param room The room to be added
     * @return true if the room was added, false if it already exists
     */
    public boolean addRoom(Room room) {
        if (room == null) {
            return false;
        }
        if (!rooms.contains(room)) {
            rooms.add(room);
            return true;
        }
        return false;
    }

    /**
     * Checks if a room is available at a given time.
     * A room is available if it is registered and not assigned to another screening at that time.
     *
     * @param room The room to check
     * @param time The time to check availability
     * @return true if the room is available, false otherwise
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
     * Adds a film to the cinema if it is not already registered.
     *
     * @param film The film to be added
     * @return true if the film was added, false if it already exists
     */
    public boolean addFilm(Film film) {
        if (film == null) {
            return false;
        }
        if (!films.contains(film)) {
            films.add(film);
            return true;
        }
        return false;
    }

    /**
     * Removes a film from the cinema and all its future screenings.
     *
     * @param film The film to be removed
     * @param currentTime The current time for comparison with screening times
     * @return true if the film was removed successfully, false otherwise
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (film == null || !films.contains(film) || currentTime == null) {
            return false;
        }
        
        // Remove all future screenings of this film
        Iterator<Screening> iterator = screenings.iterator();
        while (iterator.hasNext()) {
            Screening screening = iterator.next();
            if (screening.getFilm().equals(film) && 
                !screening.getTime().before(currentTime)) {
                iterator.remove();
            }
        }
        
        // Remove the film itself
        films.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     * Checks that current time is before the screening time, the film is registered,
     * and the room is available at the specified time.
     *
     * @param film The film to be screened
     * @param room The room where the film will be shown
     * @param screening The screening to be assigned
     * @param currentTime The current time for validation
     * @return true if the screening was assigned successfully, false otherwise
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (film == null || room == null || screening == null || currentTime == null) {
            return false;
        }
        
        Date screeningTime = screening.getTime();
        if (screeningTime == null) {
            return false;
        }
        
        // Check if current time is before screening time
        if (!currentTime.before(screeningTime)) {
            return false;
        }
        
        // Check if film is registered
        if (!films.contains(film)) {
            return false;
        }
        
        // Check if room is registered
        if (!rooms.contains(room)) {
            return false;
        }
        
        // Check room availability at the screening time
        if (!checkAvailability(room, screeningTime)) {
            return false;
        }
        
        // Assign the screening
        screening.setFilm(film);
        screening.setRoom(room);
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening if it exists and its time is after the current time.
     *
     * @param screening The screening to be canceled
     * @param currentTime The current time for comparison
     * @return true if the screening was canceled successfully, false otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || currentTime == null) {
            return false;
        }
        
        Date screeningTime = screening.getTime();
        if (screeningTime == null) {
            return false;
        }
        
        // Check if screening time is after current time
        if (!screeningTime.after(currentTime)) {
            return false;
        }
        
        // Check if screening exists in the cinema
        if (!screenings.contains(screening)) {
            return false;
        }
        
        // Remove the screening
        screenings.remove(screening);
        return true;
    }

    // Getters and Setters
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

/**
 * Represents a room in a cinema where films are shown.
 */
class Room {
    /**
     * Constructs a new Room.
     */
    public Room() {
    }
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

/**
 * Represents a film that can be shown in a cinema.
 */
class Film {
    /**
     * Constructs a new Film.
     */
    public Film() {
    }
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

/**
 * Represents a screening of a film in a specific room at a specific time.
 */
class Screening {
    private Date time;
    private Film film;
    private Room room;

    /**
     * Constructs a new Screening with default values.
     */
    public Screening() {
        this.time = null;
        this.film = null;
        this.room = null;
    }

    /**
     * Gets the time of the screening.
     *
     * @return the time of the screening
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets the time of the screening.
     *
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Gets the film being screened.
     *
     * @return the film being screened
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Sets the film being screened.
     *
     * @param film the film to set
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * Gets the room where the screening takes place.
     *
     * @return the room of the screening
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room where the screening takes place.
     *
     * @param room the room to set
     */
    public void setRoom(Room room) {
        this.room = room;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Screening screening = (Screening) obj;
        return Objects.equals(time, screening.time) &&
               Objects.equals(film, screening.film) &&
               Objects.equals(room, screening.room);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(time, film, room);
    }
}