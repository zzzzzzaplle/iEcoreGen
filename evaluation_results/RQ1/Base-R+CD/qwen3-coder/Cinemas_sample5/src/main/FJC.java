import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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
     * @param room the room to be added
     * @return true if the room was added successfully, false if the room already exists
     */
    public boolean addRoom(Room room) {
        if (room == null || rooms.contains(room)) {
            return false;
        }
        rooms.add(room);
        return true;
    }

    /**
     * Checks if a room is available at a given time.
     * A room is available if it exists in the cinema and is not assigned to another screening at that time.
     *
     * @param room the room to check
     * @param time the time to check availability
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
     * Adds a film to the cinema if it does not already exist.
     *
     * @param film the film to be added
     * @return true if the film was added successfully, false if the film already exists
     */
    public boolean addFilm(Film film) {
        if (film == null || films.contains(film)) {
            return false;
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film from the cinema system and all of its future scheduled screenings.
     *
     * @param film the film to be removed
     * @param currentTime the current time used to determine future screenings
     * @return true if the film was removed successfully, false if it does not exist
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (film == null || !films.contains(film) || currentTime == null) {
            return false;
        }

        // Remove all future screenings of this film
        Iterator<Screening> iterator = screenings.iterator();
        while (iterator.hasNext()) {
            Screening screening = iterator.next();
            if (screening.getFilm().equals(film) && !screening.getTime().before(currentTime)) {
                iterator.remove();
            }
        }

        films.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific screening time.
     * Verifies that the current time is before the screening time, the film is added to the cinema,
     * and the room is available at the screening time.
     *
     * @param film the film to be screened
     * @param room the room where the screening will take place
     * @param screening the screening to be assigned
     * @param currentTime the current time for validation
     * @return true if the screening is assigned successfully, false otherwise
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (film == null || room == null || screening == null || currentTime == null) {
            return false;
        }

        // Check if current time is before screening time
        if (!currentTime.before(screening.getTime())) {
            return false;
        }

        // Check if film is added to the cinema
        if (!films.contains(film)) {
            return false;
        }

        // Check if room is added to the cinema
        if (!rooms.contains(room)) {
            return false;
        }

        // Check room availability at the screening time
        if (!checkAvailability(room, screening.getTime())) {
            return false;
        }

        // Assign the screening
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening if it exists and its time is after the current time.
     *
     * @param screening the screening to be canceled
     * @param currentTime the current time for validation
     * @return true if the screening is canceled successfully, false otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || currentTime == null) {
            return false;
        }

        // Check if screening exists in the cinema
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

    // Getters and setters for private properties
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
        return Objects.equals(time, screening.time) &&
               Objects.equals(film, screening.film) &&
               Objects.equals(room, screening.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, film, room);
    }
}