import java.util.*;

/**
 * Represents a cinema that manages rooms, films, and screenings.
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

    /**
     * Adds a room to the cinema if it doesn't already exist.
     *
     * @param room The room to be added
     * @return true if the room was added, false if it already exists
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
     * A room is considered available if:
     * - It exists in the cinema
     * - It is not assigned to any screening at the specified time
     *
     * @param room The room to check
     * @param time The time to check availability for
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
     * Adds a film to the cinema if it doesn't already exist.
     *
     * @param film The film to be added
     * @return true if the film was added, false if it already exists
     */
    public boolean addFilm(Film film) {
        if (film == null || films.contains(film)) {
            return false;
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film from the cinema and all of its future scheduled screenings.
     *
     * @param film The film to be removed
     * @param currentTime The current time used to determine which screenings are future screenings
     * @return true if the film was removed successfully, false if it doesn't exist
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
     * Assigns a screening to a film and room at a specific time.
     * Validates that:
     * - Current time is before the screening time
     * - Film is registered in the cinema
     * - Room is available at the screening time
     *
     * @param film The film to be screened
     * @param room The room where the screening will take place
     * @param screening The screening to be assigned
     * @param currentTime The current time for validation
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

        // Check if room is available at the screening time
        if (!checkAvailability(room, screening.getTime())) {
            return false;
        }

        // Check if room exists in cinema
        if (!rooms.contains(room)) {
            return false;
        }

        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening.
     * Validates that:
     * - The screening exists in the cinema
     * - The screening time is after the current time
     *
     * @param screening The screening to be canceled
     * @param currentTime The current time for validation
     * @return true if the screening is canceled successfully, false otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || currentTime == null) {
            return false;
        }

        // Check if screening exists in cinema
        if (!screenings.contains(screening)) {
            return false;
        }

        // Check if screening time is after current time
        if (!screening.getTime().after(currentTime)) {
            return false;
        }

        screenings.remove(screening);
        return true;
    }

    // Getters and setters for private fields
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
     * Default constructor for Room.
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
     * Default constructor for Film.
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
     * Default constructor for Screening.
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