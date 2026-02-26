import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Represents a cinema that manages rooms, films and screenings.
 */
 class Cinema {

    /** List of rooms owned by the cinema. */
    private List<Room> rooms = new ArrayList<>();

    /** List of all scheduled screenings. */
    private List<Screening> screenings = new ArrayList<>();

    /** List of films known to the cinema. */
    private List<Film> films = new ArrayList<>();

    /** Unparameterized constructor required by the specification. */
    public Cinema() {
        // No‑arg constructor – fields are already initialised.
    }

    /**
     * Adds a new room to the cinema.
     *
     * @param room the {@link Room} to be added; must not be {@code null}
     * @return {@code true} if the room was not already registered and was added,
     *         {@code false} if the room already exists or the argument is {@code null}
     */
    public boolean addRoom(Room room) {
        if (room == null) {
            return false;
        }
        if (rooms.contains(room)) {
            return false;
        }
        rooms.add(room);
        return true;
    }

    /**
     * Checks whether a given room is available at a specific point in time.
     *
     * @param room the {@link Room} to be checked; must not be {@code null}
     * @param time the moment for which availability is queried; must not be {@code null}
     * @return {@code true} if the room belongs to this cinema and no screening is
     *         scheduled for the same time, {@code false} otherwise
     */
    public boolean checkAvailability(Room room, Date time) {
        if (room == null || time == null) {
            return false;
        }
        if (!rooms.contains(room)) {
            return false;
        }
        for (Screening s : screenings) {
            if (room.equals(s.getRoom()) && time.equals(s.getTime())) {
                return false; // already assigned at this exact time
            }
        }
        return true;
    }

    /**
     * Adds a new film to the cinema.
     *
     * @param film the {@link Film} to be added; must not be {@code null}
     * @return {@code true} if the film was not already present and was added,
     *         {@code false} if the film already exists or the argument is {@code null}
     */
    public boolean addFilm(Film film) {
        if (film == null) {
            return false;
        }
        if (films.contains(film)) {
            return false;
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film from the cinema and deletes all its future screenings.
     *
     * @param film        the {@link Film} to be removed; must not be {@code null}
     * @param currentTime the current time; screenings with {@code time >= currentTime}
     *                    will be removed; must not be {@code null}
     * @return {@code true} if the film existed and was removed, {@code false}
     *         otherwise (including when any argument is {@code null})
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (film == null || currentTime == null) {
            return false;
        }
        if (!films.contains(film)) {
            return false;
        }
        // Remove the film from the list of known films.
        films.remove(film);

        // Remove all future screenings of this film (time >= currentTime).
        Iterator<Screening> it = screenings.iterator();
        while (it.hasNext()) {
            Screening s = it.next();
            if (film.equals(s.getFilm()) && !s.getTime().before(currentTime)) {
                it.remove();
            }
        }
        return true;
    }

    /**
     * Assigns a screening to a film and a room at a specific screening time.
     *
     * @param film        the {@link Film} to be screened; must already be added to the cinema
     * @param room        the {@link Room} where the screening will take place; must already be added
     * @param screening   the {@link Screening} containing the desired time; must not be {@code null}
     * @param currentTime the current time; must be before the screening time
     * @return {@code true} if the screening was successfully scheduled,
     *         {@code false} otherwise (e.g., invalid arguments, room unavailable, film not known)
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (film == null || room == null || screening == null || currentTime == null) {
            return false;
        }
        if (!films.contains(film) || !rooms.contains(room)) {
            return false;
        }
        Date screeningTime = screening.getTime();
        if (screeningTime == null) {
            return false;
        }
        // Current time must be before the screening time.
        if (!currentTime.before(screeningTime)) {
            return false;
        }
        // The room must be free at the screening time.
        if (!checkAvailability(room, screeningTime)) {
            return false;
        }
        // Associate film and room with the screening (in case they are not set).
        screening.setFilm(film);
        screening.setRoom(room);
        // Add to the list of scheduled screenings.
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * @param screening   the {@link Screening} to be cancelled; must exist in the cinema
     * @param currentTime the current time; the screening must be scheduled after this moment
     * @return {@code true} if the screening existed and was cancelled,
     *         {@code false} otherwise (including when arguments are {@code null})
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || currentTime == null) {
            return false;
        }
        if (!screenings.contains(screening)) {
            return false;
        }
        // The screening must be in the future (strictly after current time).
        if (!screening.getTime().after(currentTime)) {
            return false;
        }
        screenings.remove(screening);
        return true;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters for the private fields (required for testing)
    // -------------------------------------------------------------------------

    /**
     * Returns the list of rooms owned by the cinema.
     *
     * @return mutable list of {@link Room}
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Sets the list of rooms. The provided list replaces the current one.
     *
     * @param rooms the new list of rooms; may be {@code null} (treated as empty list)
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = (rooms != null) ? rooms : new ArrayList<>();
    }

    /**
     * Returns the list of scheduled screenings.
     *
     * @return mutable list of {@link Screening}
     */
    public List<Screening> getScreenings() {
        return screenings;
    }

    /**
     * Sets the list of screenings. The provided list replaces the current one.
     *
     * @param screenings the new list of screenings; may be {@code null}
     */
    public void setScreenings(List<Screening> screenings) {
        this.screenings = (screenings != null) ? screenings : new ArrayList<>();
    }

    /**
     * Returns the list of known films.
     *
     * @return mutable list of {@link Film}
     */
    public List<Film> getFilms() {
        return films;
    }

    /**
     * Sets the list of films. The provided list replaces the current one.
     *
     * @param films the new list of films; may be {@code null}
     */
    public void setFilms(List<Film> films) {
        this.films = (films != null) ? films : new ArrayList<>();
    }
}

/**
 * Represents a cinema room. For identification purposes a room has a unique name.
 */
class Room {

    /** Name of the room (must be unique within a cinema). */
    private String name;

    /** No‑argument constructor required by the specification. */
    public Room() {
        // Empty constructor.
    }

    /**
     * Returns the name of the room.
     *
     * @return room name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the room.
     *
     * @param name the name to set; may be {@code null}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Equality based on the room name.
     *
     * @param o other object
     * @return {@code true} if both rooms have the same non‑null name
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;
        return Objects.equals(name, room.name);
    }

    /**
     * Hash code based on the room name.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Human readable representation.
     *
     * @return string describing the room
     */
    @Override
    public String toString() {
        return "Room{name='" + name + "'}";
    }
}

/**
 * Represents a film. For identification purposes a film has a unique title.
 */
class Film {

    /** Title of the film (must be unique within a cinema). */
    private String title;

    /** No‑argument constructor required by the specification. */
    public Film() {
        // Empty constructor.
    }

    /**
     * Returns the title of the film.
     *
     * @return film title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the film.
     *
     * @param title the title to set; may be {@code null}
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Equality based on the film title.
     *
     * @param o other object
     * @return {@code true} if both films have the same non‑null title
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;
        return Objects.equals(title, film.title);
    }

    /**
     * Hash code based on the film title.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    /**
     * Human readable representation.
     *
     * @return string describing the film
     */
    @Override
    public String toString() {
        return "Film{title='" + title + "'}";
    }
}

/**
 * Represents a screening of a film in a specific room at a given time.
 */
class Screening {

    /** Date and time when the screening starts. */
    private Date time;

    /** Film that will be shown. */
    private Film film;

    /** Room where the screening will take place. */
    private Room room;

    /** No‑argument constructor required by the specification. */
    public Screening() {
        // Empty constructor.
    }

    /**
     * Returns the screening time.
     *
     * @return screening {@link Date}
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets the screening time.
     *
     * @param time the time to set; may be {@code null}
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Returns the film associated with this screening.
     *
     * @return {@link Film}
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Sets the film for this screening.
     *
     * @param film the film to set; may be {@code null}
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * Returns the room where this screening will be held.
     *
     * @return {@link Room}
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room for this screening.
     *
     * @param room the room to set; may be {@code null}
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Equality based on time, film and room.
     *
     * @param o other object
     * @return {@code true} if all three attributes are equal (or both {@code null})
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Screening screening = (Screening) o;
        return Objects.equals(time, screening.time) &&
                Objects.equals(film, screening.film) &&
                Objects.equals(room, screening.room);
    }

    /**
     * Hash code based on time, film and room.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(time, film, room);
    }

    /**
     * Human readable representation.
     *
     * @return string describing the screening
     */
    @Override
    public String toString() {
        return "Screening{time=" + time + ", film=" + film + ", room=" + room + '}';
    }
}