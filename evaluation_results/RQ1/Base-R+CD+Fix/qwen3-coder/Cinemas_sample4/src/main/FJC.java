import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a cinema which contains rooms, films, and screenings.
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
     * @param room the room to add
     * @return true if the room was added, false if it already exists
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
     * A room is available if it exists in the cinema and is not assigned to another screening at that time.
     *
     * @param room the room to check
     * @param time the time to check availability for
     * @return true if the room is available, false otherwise
     */
    public boolean checkAvailability(Room room, Date time) {
        // Check if room exists in cinema
        if (!rooms.contains(room)) {
            return false;
        }

        // Check if room is assigned to any screening at the given time
        for (Screening screening : screenings) {
            if (screening.getRoom().equals(room) && screening.getTime().equals(time)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Adds a film to the cinema if it does not already exist.
     *
     * @param film the film to add
     * @return true if the film was added, false if it already exists
     */
    public boolean addFilm(Film film) {
        if (!films.contains(film)) {
            films.add(film);
            return true;
        }
        return false;
    }

    /**
     * Removes a film from the cinema and all its future screenings.
     *
     * @param film the film to remove
     * @param currentTime the current time to determine which screenings are future screenings
     * @return true if the film was removed successfully, false if it doesn't exist
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (!films.contains(film)) {
            return false;
        }

        // Remove all future screenings of this film
        screenings.removeIf(screening -> 
            screening.getFilm().equals(film) && 
            !screening.getTime().before(currentTime));

        // Remove the film itself
        films.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     * Verifies that the current time is before the screening time,
     * the film is added to the cinema, and the room is available.
     *
     * @param film the film to be screened
     * @param room the room for the screening
     * @param screening the screening to assign
     * @param currentTime the current time
     * @return true if the screening is assigned successfully, false otherwise
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        // Check if current time is before screening time
        if (!currentTime.before(screening.getTime())) {
            return false;
        }

        // Check if film is added to cinema
        if (!films.contains(film)) {
            return false;
        }

        // Check if room is available at the screening time
        if (!checkAvailability(room, screening.getTime())) {
            return false;
        }

        // Assign the screening
        screening.setFilm(film);
        screening.setRoom(room);
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * @param screening the screening to cancel
     * @param currentTime the current time
     * @return true if the screening was canceled successfully, false otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        // Check if screening exists in cinema
        if (!screenings.contains(screening)) {
            return false;
        }

        // Check if screening time is after current time
        if (!screening.getTime().after(currentTime)) {
            return false;
        }

        // Cancel the screening
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

/**
 * Represents a room in a cinema.
 */
class Room {
    /**
     * Constructs a new Room.
     */
    public Room() {
    }

    @Override
    public boolean equals(Object obj) {
        // Default implementation - rooms are compared by reference
        return super.equals(obj);
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
        // Default implementation - films are compared by reference
        return super.equals(obj);
    }
}

/**
 * Represents a screening of a film in a room at a specific time.
 */
class Screening {
    private Date time;
    private Film film;
    private Room room;

    /**
     * Constructs a new Screening.
     */
    public Screening() {
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
     * @return the room where the screening takes place
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
        
        if (time != null ? !time.equals(screening.time) : screening.time != null) return false;
        if (film != null ? !film.equals(screening.film) : screening.film != null) return false;
        return room != null ? room.equals(screening.room) : screening.room == null;
    }

    @Override
    public int hashCode() {
        int result = time != null ? time.hashCode() : 0;
        result = 31 * result + (film != null ? film.hashCode() : 0);
        result = 31 * result + (room != null ? room.hashCode() : 0);
        return result;
    }
}