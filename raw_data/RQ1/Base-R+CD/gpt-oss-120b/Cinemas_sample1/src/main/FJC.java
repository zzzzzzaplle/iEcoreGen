import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private List<Room> rooms;

    /** List of films owned by the cinema. */
    private List<Film> films;

    /** List of scheduled screenings. */
    private List<Screening> screenings;

    /** Default constructor – creates empty collections. */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.films = new ArrayList<>();
        this.screenings = new ArrayList<>();
    }

    /** @return the list of rooms */
    public List<Room> getRooms() {
        return rooms;
    }

    /** @param rooms the list of rooms to set */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /** @return the list of films */
    public List<Film> getFilms() {
        return films;
    }

    /** @param films the list of films to set */
    public void setFilms(List<Film> films) {
        this.films = films;
    }

    /** @return the list of screenings */
    public List<Screening> getScreenings() {
        return screenings;
    }

    /** @param screenings the list of screenings to set */
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    /**
     * Adds a {@link Room} to the cinema.
     *
     * @param room the room to add; must not be {@code null}
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
     * Adds a {@link Film} to the cinema.
     *
     * @param film the film to add; must not be {@code null}
     * @return {@code true} if the film was not already registered and was added,
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
     * @param film        the film to remove; must not be {@code null}
     * @param currentTime the current time; screenings with {@code time >= currentTime}
     *                    will be removed; must not be {@code null}
     * @return {@code true} if the film existed and was removed, {@code false}
     *         otherwise (including {@code null} arguments)
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (film == null || currentTime == null) {
            return false;
        }
        if (!films.contains(film)) {
            return false;
        }
        // Remove the film from the film list
        films.remove(film);

        // Remove all future screenings of this film (time >= currentTime)
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
     * @param film        the film to be screened; must already be added to the cinema
     * @param room        the room where the screening will take place; must already be added
     * @param screening   the screening object containing the desired time; its {@code film}
     *                    and {@code room} fields will be set by this method
     * @param currentTime the current time; must be before the screening time
     * @return {@code true} if the screening was successfully assigned, {@code false}
     *         otherwise (invalid arguments, film/room not registered, room unavailable,
     *         or current time not before screening time)
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (film == null || room == null || screening == null || currentTime == null) {
            return false;
        }

        // Verify that the current time is before the screening time
        Date screeningTime = screening.getTime();
        if (screeningTime == null || !currentTime.before(screeningTime)) {
            return false;
        }

        // Verify that the film is already added to the cinema
        if (!films.contains(film)) {
            return false;
        }

        // Verify that the room is already added to the cinema
        if (!rooms.contains(room)) {
            return false;
        }

        // Verify that the room is available at the screening time
        if (!checkAvailability(room, screeningTime)) {
            return false;
        }

        // All checks passed – set film and room on the screening and add it
        screening.setFilm(film);
        screening.setRoom(room);
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * @param screening   the screening to cancel; must not be {@code null}
     * @param currentTime the current time; the screening must be scheduled after this time
     * @return {@code true} if the screening existed and was cancelled, {@code false}
     *         otherwise (including invalid arguments or screening not in the future)
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || currentTime == null) {
            return false;
        }

        // Screening must exist in the system
        if (!screenings.contains(screening)) {
            return false;
        }

        // Screening time must be after current time
        if (!screening.getTime().after(currentTime)) {
            return false;
        }

        // Remove the screening
        screenings.remove(screening);
        return true;
    }

    /**
     * Checks whether a given {@link Room} is available at a specific time.
     *
     * @param room the room to check; must not be {@code null}
     * @param time the time to check for availability; must not be {@code null}
     * @return {@code true} if the room is registered in the cinema and no other
     *         screening is scheduled for that room at the given time; {@code false}
     *         otherwise (including {@code null} arguments)
     */
    public boolean checkAvailability(Room room, Date time) {
        if (room == null || time == null) {
            return false;
        }

        // The room must be part of the cinema
        if (!rooms.contains(room)) {
            return false;
        }

        // No screening should already be assigned to this room at the given time
        for (Screening s : screenings) {
            if (room.equals(s.getRoom()) && time.equals(s.getTime())) {
                return false; // room already booked at this exact time
            }
        }
        return true;
    }

    // -------------------------------------------------------------------------
    // Helper methods (optional, not required by the specification)
    // -------------------------------------------------------------------------

    /**
     * Finds a screening that matches the given film, room and time.
     *
     * @param film the film of the screening
     * @param room the room of the screening
     * @param time the time of the screening
     * @return the matching {@link Screening} or {@code null} if none found
     */
    public Screening findScreening(Film film, Room room, Date time) {
        for (Screening s : screenings) {
            if (Objects.equals(s.getFilm(), film)
                    && Objects.equals(s.getRoom(), room)
                    && Objects.equals(s.getTime(), time)) {
                return s;
            }
        }
        return null;
    }
}

/**
 * Represents a room (theater) inside a cinema.
 */
class Room {

    /** Unique identifier for the room (e.g., "Room A", "Hall 1"). */
    private String id;

    /** Default constructor. */
    public Room() {
    }

    /**
     * Constructs a room with the given identifier.
     *
     * @param id the unique identifier of the room
     */
    public Room(String id) {
        this.id = id;
    }

    /** @return the room identifier */
    public String getId() {
        return id;
    }

    /** @param id the room identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** Equality based on the {@code id} field. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;
        return Objects.equals(id, room.id);
    }

    /** Hash code based on the {@code id} field. */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /** Human‑readable representation. */
    @Override
    public String toString() {
        return "Room{id='" + id + '\'' + '}';
    }
}

/**
 * Represents a film that can be shown in a cinema.
 */
class Film {

    /** Unique identifier for the film (e.g., title). */
    private String title;

    /** Default constructor. */
    public Film() {
    }

    /**
     * Constructs a film with the given title.
     *
     * @param title the title of the film
     */
    public Film(String title) {
        this.title = title;
    }

    /** @return the film title */
    public String getTitle() {
        return title;
    }

    /** @param title the film title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Equality based on the {@code title} field. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;
        return Objects.equals(title, film.title);
    }

    /** Hash code based on the {@code title} field. */
    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    /** Human‑readable representation. */
    @Override
    public String toString() {
        return "Film{title='" + title + '\'' + '}';
    }
}

/**
 * Represents a screening of a film in a specific room at a specific time.
 */
class Screening {

    /** The date and time when the screening starts. */
    private Date time;

    /** The film being screened. */
    private Film film;

    /** The room where the screening takes place. */
    private Room room;

    /** Default constructor. */
    public Screening() {
    }

    /**
     * Constructs a screening with the given time.
     *
     * @param time the start time of the screening; must not be {@code null}
     */
    public Screening(Date time) {
        this.time = time;
    }

    /** @return the screening time */
    public Date getTime() {
        return time;
    }

    /** @param time the screening time to set */
    public void setTime(Date time) {
        this.time = time;
    }

    /** @return the film */
    public Film getFilm() {
        return film;
    }

    /** @param film the film to set */
    public void setFilm(Film film) {
        this.film = film;
    }

    /** @return the room */
    public Room getRoom() {
        return room;
    }

    /** @param room the room to set */
    public void setRoom(Room room) {
        this.room = room;
    }

    /** Equality based on time, film and room. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Screening screening = (Screening) o;
        return Objects.equals(time, screening.time) &&
                Objects.equals(film, screening.film) &&
                Objects.equals(room, screening.room);
    }

    /** Hash code based on time, film and room. */
    @Override
    public int hashCode() {
        return Objects.hash(time, film, room);
    }

    /** Human‑readable representation. */
    @Override
    public String toString() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = (time != null) ? fmt.format(time) : "null";
        return "Screening{time=" + timeStr + ", film=" + film + ", room=" + room + '}';
    }
}

/**
 * Utility class for parsing date strings in the required format.
 */
class DateUtil {

    /** Date format used throughout the system. */
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final SimpleDateFormat SDF = new SimpleDateFormat(PATTERN);

    /**
     * Parses a date string according to the pattern {@code yyyy-MM-dd HH:mm:ss}.
     *
     * @param dateStr the string to parse; must not be {@code null}
     * @return the corresponding {@link Date}
     * @throws ParseException if the string cannot be parsed
     */
    public static Date parse(String dateStr) throws ParseException {
        if (dateStr == null) {
            throw new IllegalArgumentException("dateStr must not be null");
        }
        synchronized (SDF) {
            return SDF.parse(dateStr);
        }
    }

    /**
     * Formats a {@link Date} into the standard string representation.
     *
     * @param date the date to format; must not be {@code null}
     * @return a string formatted as {@code yyyy-MM-dd HH:mm:ss}
     */
    public static String format(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date must not be null");
        }
        synchronized (SDF) {
            return SDF.format(date);
        }
    }

    /** Prevent instantiation. */
    private DateUtil() {
    }
}