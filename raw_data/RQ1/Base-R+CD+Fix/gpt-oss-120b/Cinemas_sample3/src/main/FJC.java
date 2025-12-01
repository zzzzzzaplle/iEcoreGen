import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents a cinema that manages rooms, films and screenings.
 */
 class Cinema {

    /** List of rooms belonging to the cinema. */
    private List<Room> rooms;

    /** List of all scheduled screenings. */
    private List<Screening> screenings;

    /** List of films available in the cinema system. */
    private List<Film> films;

    /** No‑argument constructor that initialises internal collections. */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.films = new ArrayList<>();
    }

    /* --------------------------------------------------------------------- */
    /* Getters and Setters                                                   */
    /* --------------------------------------------------------------------- */

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

    /* --------------------------------------------------------------------- */
    /* Business methods                                                       */
    /* --------------------------------------------------------------------- */

    /**
     * Adds a new room to the cinema.
     *
     * @param room the {@link Room} to be added
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
     * Checks whether a given room is available at the specified time.
     *
     * @param room the {@link Room} to be checked
     * @param time the {@link Date} representing the moment of interest
     * @return {@code true} if the room exists in the cinema and has no screening
     *         scheduled at the given time, {@code false} otherwise (including
     *         invalid inputs)
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
                return false; // already assigned at that time
            }
        }
        return true;
    }

    /**
     * Adds a new film to the cinema system.
     *
     * @param film the {@link Film} to be added
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
     * Removes a film from the system and deletes all its future screenings.
     *
     * @param film        the {@link Film} to be removed
     * @param currentTime the current {@link Date}; screenings with a time
     *                    greater than or equal to this value are considered future
     * @return {@code true} if the film existed and was removed, {@code false}
     *         otherwise
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (film == null || currentTime == null) {
            return false;
        }
        if (!films.contains(film)) {
            return false;
        }
        films.remove(film);
        // Remove future screenings of this film
        screenings.removeIf(s -> s.getFilm().equals(film) && !s.getTime().before(currentTime));
        return true;
    }

    /**
     * Assigns a screening to a film and a room at a specific future time.
     *
     * @param film        the {@link Film} to be screened
     * @param room        the {@link Room} where the screening will take place
     * @param screening   the {@link Screening} object containing the desired time
     * @param currentTime the current {@link Date}; must be before the screening time
     * @return {@code true} if the screening was successfully assigned,
     *         {@code false} otherwise (invalid times, missing film/room,
     *         or room not available)
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (film == null || room == null || screening == null || currentTime == null) {
            return false;
        }
        Date screeningTime = screening.getTime();
        if (screeningTime == null) {
            return false;
        }
        // current time must be before the screening time
        if (!currentTime.before(screeningTime)) {
            return false;
        }
        // film and room must already be registered
        if (!films.contains(film) || !rooms.contains(room)) {
            return false;
        }
        // room must be free at that time
        if (!checkAvailability(room, screeningTime)) {
            return false;
        }
        // associate film and room with the screening (defensive copy)
        screening.setFilm(film);
        screening.setRoom(room);
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * @param screening   the {@link Screening} to cancel
     * @param currentTime the current {@link Date}; the screening must be scheduled
     *                    after this time
     * @return {@code true} if the screening existed and was cancelled,
     *         {@code false} otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || currentTime == null) {
            return false;
        }
        // Find the exact screening instance in the list
        for (Screening s : screenings) {
            if (s.equals(screening)) {
                if (s.getTime().after(currentTime)) {
                    screenings.remove(s);
                    return true;
                } else {
                    return false; // cannot cancel past or ongoing screening
                }
            }
        }
        return false; // screening not found
    }
}

/**
 * Represents a room (theatre) inside a cinema.
 */
class Room {

    /** Unique identifier for the room (e.g., "Room 1"). */
    private String name;

    /** No‑argument constructor. */
    public Room() {
    }

    /** Constructor with name. */
    public Room(String name) {
        this.name = name;
    }

    /* Getters and Setters */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* Equality based on name */

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
 * Represents a film that can be scheduled in the cinema.
 */
class Film {

    /** Unique title of the film. */
    private String title;

    /** No‑argument constructor. */
    public Film() {
    }

    /** Constructor with title. */
    public Film(String title) {
        this.title = title;
    }

    /* Getters and Setters */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /* Equality based on title */

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
 * Represents a screening of a film in a specific room at a given time.
 */
class Screening {

    /** The date and time when the screening starts. */
    private Date time;

    /** The film being screened. */
    private Film film;

    /** The room where the screening takes place. */
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

    /* Getters and Setters */

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

    /* Equality based on film, room and time */

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