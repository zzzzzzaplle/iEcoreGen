import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents a cinema that manages rooms, films and screenings.
 */
 class Cinema {

    /** List of rooms belonging to this cinema. */
    private List<Room> rooms;

    /** List of films known to this cinema. */
    private List<Film> films;

    /** List of scheduled screenings. */
    private List<Screening> screenings;

    /** No‑argument constructor required by the task. */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.films = new ArrayList<>();
        this.screenings = new ArrayList<>();
    }

    /* ---------- Getters & Setters ---------- */

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    /* ---------- Business Methods ---------- */

    /**
     * Adds a new room to the cinema.
     *
     * @param room the room to add; must not be {@code null}
     * @return {@code true} if the room was not already registered and has been added,
     *         {@code false} if the room already exists in the cinema
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
     * Checks whether a given room is available at a specific time.
     *
     * @param room the room to check; must be part of this cinema
     * @param time the moment for which availability is requested; must not be {@code null}
     * @return {@code true} if the room exists in the cinema and has no screening scheduled at the given time,
     *         {@code false} otherwise (including invalid inputs)
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
                return false; // already occupied at this exact time
            }
        }
        return true;
    }

    /**
     * Adds a new film to the cinema.
     *
     * @param film the film to add; must not be {@code null}
     * @return {@code true} if the film was not already present and has been added,
     *         {@code false} if the film already exists in the cinema
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
     * @param currentTime the reference time; screenings with a time {@code >= currentTime}
     *                    will be removed together with the film
     * @return {@code true} if the film existed and was removed,
     *         {@code false} if the film does not exist in the cinema
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (film == null || currentTime == null) {
            return false;
        }
        if (!films.contains(film)) {
            return false;
        }
        // Remove future screenings of this film
        screenings.removeIf(s -> film.equals(s.getFilm()) && !s.getTime().before(currentTime));
        // Remove the film itself
        films.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and a room at a specific future time.
     *
     * @param film        the film to be screened; must already be added to the cinema
     * @param room        the room where the screening will take place; must already be added
     * @param screening   the screening object containing the desired time; its {@code time}
     *                    must be after {@code currentTime}
     * @param currentTime the current time; must be before the screening time
     * @return {@code true} if the screening is successfully scheduled,
     *         {@code false} otherwise (invalid inputs, film/room not registered,
     *         room not available, or temporal constraints violated)
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (film == null || room == null || screening == null || currentTime == null) {
            return false;
        }
        // Temporal validation
        if (!currentTime.before(screening.getTime())) {
            return false; // current time must be before screening time
        }
        // Film and room must be registered
        if (!films.contains(film) || !rooms.contains(room)) {
            return false;
        }
        // Room must be free at the requested screening time
        if (!checkAvailability(room, screening.getTime())) {
            return false;
        }
        // Associate film and room with the screening (defensive copy)
        screening.setFilm(film);
        screening.setRoom(room);
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a previously scheduled future screening.
     *
     * @param screening   the screening to cancel; must be present in the cinema
     * @param currentTime the current time; the screening must be scheduled after this moment
     * @return {@code true} if the screening existed and was cancelled,
     *         {@code false} otherwise (screening not found or not a future screening)
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || currentTime == null) {
            return false;
        }
        if (!screenings.contains(screening)) {
            return false;
        }
        if (!screening.getTime().after(currentTime)) {
            return false; // only future screenings can be cancelled
        }
        screenings.remove(screening);
        return true;
    }
}

/**
 * Represents a room within a cinema.
 */
class Room {

    /** A human‑readable identifier for the room (e.g., "Room A"). */
    private String name;

    /** No‑argument constructor. */
    public Room() {
    }

    /** Constructor with name. */
    public Room(String name) {
        this.name = name;
    }

    /* ---------- Getters & Setters ---------- */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* ---------- Equality based on name ---------- */

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
 * Represents a film that can be screened in a cinema.
 */
class Film {

    /** The title of the film. */
    private String title;

    /** No‑argument constructor. */
    public Film() {
    }

    /** Constructor with title. */
    public Film(String title) {
        this.title = title;
    }

    /* ---------- Getters & Setters ---------- */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /* ---------- Equality based on title ---------- */

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
 * Represents a single screening of a film in a specific room at a given time.
 */
class Screening {

    /** Time of the screening (date & hour). */
    private Date time;

    /** The film being shown. */
    private Film film;

    /** The room where the screening occurs. */
    private Room room;

    /** No‑argument constructor. */
    public Screening() {
    }

    /** Constructor with all fields. */
    public Screening(Date time, Film film, Room room) {
        this.time = time;
        this.film = film;
        this.room = room;
    }

    /* ---------- Getters & Setters ---------- */

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

    /* ---------- Equality based on time, film and room ---------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Screening that = (Screening) o;
        return Objects.equals(time, that.time) &&
               Objects.equals(film, that.film) &&
               Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, film, room);
    }
}