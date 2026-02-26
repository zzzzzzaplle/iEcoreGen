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

    /** List of all scheduled screenings. */
    private List<Screening> screenings;

    /** List of films available in the cinema system. */
    private List<Film> films;

    /** Default constructor – creates empty collections. */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.films = new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Business methods
    // -------------------------------------------------------------------------

    /**
     * Adds a new room to the cinema.
     *
     * @param room the {@link Room} to add
     * @return {@code true} if the room was not already registered and was added,
     *         {@code false} otherwise
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
     * Checks whether a given room is available at the supplied time.
     *
     * @param room the {@link Room} to check
     * @param time the {@link Date} representing the moment to test (yyyy‑MM‑dd HH:mm:ss)
     * @return {@code true} if the room exists in the cinema and no screening is
     *         scheduled for that room at the given time; {@code false} otherwise
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
                return false; // already assigned at that moment
            }
        }
        return true;
    }

    /**
     * Adds a new film to the cinema system.
     *
     * @param film the {@link Film} to add
     * @return {@code true} if the film was not already present and was added,
     *         {@code false} otherwise
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
     * Removes a film from the system together with all its future screenings.
     *
     * @param film        the {@link Film} to remove
     * @param currentTime the current time; screenings with time {@code >=}
     *                    {@code currentTime} will be deleted
     * @return {@code true} if the film existed and was removed; {@code false}
     *         otherwise
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (film == null || currentTime == null) {
            return false;
        }
        if (!films.contains(film)) {
            return false;
        }
        // Remove future screenings of this film
        Iterator<Screening> it = screenings.iterator();
        while (it.hasNext()) {
            Screening s = it.next();
            if (film.equals(s.getFilm()) && !s.getTime().before(currentTime)) {
                it.remove();
            }
        }
        // Finally remove the film itself
        films.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and a room at a specific future time.
     *
     * @param film        the {@link Film} to be screened
     * @param room        the {@link Room} where the screening will happen
     * @param screening   the {@link Screening} object containing the scheduled time
     * @param currentTime the current time; must be before the screening time
     * @return {@code true} if all validations succeed and the screening is added;
     *         {@code false} otherwise
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (film == null || room == null || screening == null || currentTime == null) {
            return false;
        }
        // Current time must be before the screening time
        if (!currentTime.before(screening.getTime())) {
            return false;
        }
        // Film must be registered
        if (!films.contains(film)) {
            return false;
        }
        // Room must be registered and available
        if (!rooms.contains(room) || !checkAvailability(room, screening.getTime())) {
            return false;
        }
        // Link film and room to the screening (defensive copy)
        screening.setFilm(film);
        screening.setRoom(room);
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * @param screening   the {@link Screening} to cancel
     * @param currentTime the current time; the screening must be scheduled after this moment
     * @return {@code true} if the screening existed and was after the current time and
     *         was successfully removed; {@code false} otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || currentTime == null) {
            return false;
        }
        if (!screenings.contains(screening)) {
            return false;
        }
        // Screening must be in the future
        if (!screening.getTime().after(currentTime)) {
            return false;
        }
        screenings.remove(screening);
        return true;
    }
}

/**
 * Represents a room (theater) inside a cinema.
 */
class Room {

    /** Unique identifier of the room (e.g., "Room 1"). */
    private String name;

    /** Default constructor. */
    public Room() {
    }

    /**
     * Constructs a room with the given name.
     *
     * @param name the name of the room
     */
    public Room(String name) {
        this.name = name;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // -------------------------------------------------------------------------
    // Equality based on the room name
    // -------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;
        return Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

/**
 * Represents a film that can be screened in the cinema.
 */
class Film {

    /** Unique title of the film. */
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

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // -------------------------------------------------------------------------
    // Equality based on the film title
    // -------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;
        return Objects.equals(title, film.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}

/**
 * Represents a screening of a film in a specific room at a certain time.
 */
class Screening {

    /** Time of the screening (format: yyyy‑MM‑dd HH:mm:ss). */
    private Date time;

    /** Film that will be shown. */
    private Film film;

    /** Room where the screening takes place. */
    private Room room;

    /** Default constructor. */
    public Screening() {
    }

    /**
     * Constructs a screening with the given time.
     *
     * @param time the scheduled time of the screening
     */
    public Screening(Date time) {
        this.time = time;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Equality based on time, film and room (all three define a unique screening)
    // -------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Screening screening = (Screening) o;
        return Objects.equals(time, screening.time) &&
               Objects.equals(film, screening.film) &&
               Objects.equals(room, screening.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, film, room);
    }
}