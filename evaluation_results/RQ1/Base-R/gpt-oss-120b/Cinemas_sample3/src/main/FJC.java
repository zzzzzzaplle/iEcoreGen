import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a cinema that manages rooms, films and their screenings.
 */
 class Cinema {

    /** Rooms registered in the cinema, keyed by room identifier. */
    private Map<String, Room> rooms = new HashMap<>();

    /** Films registered in the cinema, keyed by film identifier. */
    private Map<String, Film> films = new HashMap<>();

    /** All screenings scheduled in the cinema. */
    private List<Screening> screenings = new ArrayList<>();

    /** No‑argument constructor (required). */
    public Cinema() {
    }

    /* --------------------------------------------------------------------- */
    /* Getters and Setters                                                   */
    /* --------------------------------------------------------------------- */

    public Map<String, Room> getRooms() {
        return rooms;
    }

    public void setRooms(Map<String, Room> rooms) {
        this.rooms = rooms;
    }

    public Map<String, Film> getFilms() {
        return films;
    }

    public void setFilms(Map<String, Film> films) {
        this.films = films;
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    /* --------------------------------------------------------------------- */
    /* Functional requirements                                               */
    /* --------------------------------------------------------------------- */

    /**
     * Adds a {@link Room} to the cinema.
     *
     * @param room the room to add; must not be {@code null}
     * @return {@code true} if the room was not already registered and was added,
     *         {@code false} if the room already exists or the argument is {@code null}
     */
    public boolean addRoom(Room room) {
        if (room == null || room.getId() == null) {
            return false;
        }
        if (rooms.containsKey(room.getId())) {
            return false;
        }
        rooms.put(room.getId(), room);
        return true;
    }

    /**
     * Checks whether a room is available at a given time.
     *
     * @param roomId the identifier of the room to check; must not be {@code null}
     * @param time   the date‑time to check (format: {@code yyyy-MM-dd HH:mm:ss});
     *               must not be {@code null}
     * @return {@code true} if the room exists and has no screening scheduled at the
     *         given time; {@code false} otherwise (including invalid inputs)
     */
    public boolean isRoomAvailable(String roomId, LocalDateTime time) {
        if (roomId == null || time == null) {
            return false;
        }
        Room room = rooms.get(roomId);
        if (room == null) {
            return false;
        }
        for (Screening s : screenings) {
            if (s.getRoom().getId().equals(roomId) && s.getScreeningTime().equals(time)) {
                return false; // already assigned at that time
            }
        }
        return true;
    }

    /**
     * Adds a {@link Film} to the cinema.
     *
     * @param film the film to add; must not be {@code null}
     * @return {@code true} if the film was not already present and was added,
     *         {@code false} if the film already exists or the argument is {@code null}
     */
    public boolean addFilm(Film film) {
        if (film == null || film.getId() == null) {
            return false;
        }
        if (films.containsKey(film.getId())) {
            return false;
        }
        films.put(film.getId(), film);
        return true;
    }

    /**
     * Removes a film from the cinema system together with all its future screenings.
     *
     * @param filmId      the identifier of the film to remove; must not be {@code null}
     * @param currentTime the current time; screenings with a time {@code >=}
     *                    this value are removed; must not be {@code null}
     * @return {@code true} if the film existed and was removed (including its future
     *         screenings); {@code false} if the film does not exist or inputs are invalid
     */
    public boolean removeFilm(String filmId, LocalDateTime currentTime) {
        if (filmId == null || currentTime == null) {
            return false;
        }
        Film film = films.remove(filmId);
        if (film == null) {
            return false; // film not present
        }
        // Remove future screenings of this film
        Iterator<Screening> it = screenings.iterator();
        while (it.hasNext()) {
            Screening s = it.next();
            if (s.getFilm().getId().equals(filmId) && !s.getScreeningTime().isBefore(currentTime)) {
                it.remove();
            }
        }
        return true;
    }

    /**
     * Assigns a screening to a film and a room at a specific future time.
     *
     * @param filmId        identifier of the film; must be already added
     * @param roomId        identifier of the room; must be already added
     * @param screeningTime the desired screening time; must be after {@code currentTime}
     * @param currentTime   the moment when the assignment is performed; must be before {@code screeningTime}
     * @return {@code true} if the screening could be assigned; {@code false} otherwise
     */
    public boolean assignScreening(String filmId, String roomId,
                                   LocalDateTime screeningTime, LocalDateTime currentTime) {
        // Validate inputs
        if (filmId == null || roomId == null || screeningTime == null || currentTime == null) {
            return false;
        }
        if (!currentTime.isBefore(screeningTime)) {
            return false; // current time must be before screening time
        }
        Film film = films.get(filmId);
        Room room = rooms.get(roomId);
        if (film == null || room == null) {
            return false; // film or room not registered
        }
        // Check room availability at the desired time
        if (!isRoomAvailable(roomId, screeningTime)) {
            return false;
        }
        // All checks passed – create and store the screening
        Screening newScreening = new Screening();
        newScreening.setFilm(film);
        newScreening.setRoom(room);
        newScreening.setScreeningTime(screeningTime);
        screenings.add(newScreening);
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * @param screeningTime the time of the screening to cancel; must be after {@code currentTime}
     * @param currentTime   the moment when the cancellation is requested; must not be {@code null}
     * @return {@code true} if a matching future screening existed and was cancelled;
     *         {@code false} otherwise (including invalid inputs)
     */
    public boolean cancelScreening(LocalDateTime screeningTime, LocalDateTime currentTime) {
        if (screeningTime == null || currentTime == null) {
            return false;
        }
        if (!screeningTime.isAfter(currentTime)) {
            return false; // only future screenings can be cancelled
        }
        Iterator<Screening> it = screenings.iterator();
        while (it.hasNext()) {
            Screening s = it.next();
            if (s.getScreeningTime().equals(screeningTime)) {
                it.remove();
                return true; // cancelled successfully
            }
        }
        return false; // no matching screening found
    }
}

/**
 * Represents a room inside a cinema.
 */
class Room {

    /** Unique identifier for the room (e.g., "R1"). */
    private String id;

    /** Human‑readable name of the room (optional). */
    private String name;

    /** No‑argument constructor. */
    public Room() {
    }

    /* ----------------------------------------------------------------- */
    /* Getters and Setters                                               */
    /* ----------------------------------------------------------------- */

    /**
     * Returns the room identifier.
     *
     * @return the id of the room
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the room identifier.
     *
     * @param id the identifier to set; must not be {@code null}
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the room name.
     *
     * @return the name of the room
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the room name.
     *
     * @param name the name to set; may be {@code null}
     */
    public void setName(String name) {
        this.name = name;
    }

    /* ----------------------------------------------------------------- */
    /* Utility methods                                                   */
    /* ----------------------------------------------------------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Represents a film that can be shown in the cinema.
 */
class Film {

    /** Unique identifier for the film (e.g., "F001"). */
    private String id;

    /** Title of the film. */
    private String title;

    /** No‑argument constructor. */
    public Film() {
    }

    /* ----------------------------------------------------------------- */
    /* Getters and Setters                                               */
    /* ----------------------------------------------------------------- */

    /**
     * Returns the film identifier.
     *
     * @return the id of the film
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the film identifier.
     *
     * @param id the identifier to set; must not be {@code null}
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the film title.
     *
     * @return the title of the film
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the film title.
     *
     * @param title the title to set; may be {@code null}
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /* ----------------------------------------------------------------- */
    /* Utility methods                                                   */
    /* ----------------------------------------------------------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;
        return Objects.equals(id, film.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Represents a screening: a specific film shown in a specific room at a specific time.
 */
class Screening {

    /** Film that will be shown. */
    private Film film;

    /** Room where the film will be shown. */
    private Room room;

    /** Date‑time of the screening. */
    private LocalDateTime screeningTime;

    /** No‑argument constructor. */
    public Screening() {
    }

    /* ----------------------------------------------------------------- */
    /* Getters and Setters                                               */
    /* ----------------------------------------------------------------- */

    /**
     * Returns the film associated with this screening.
     *
     * @return the film
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Sets the film for this screening.
     *
     * @param film the film to set; must not be {@code null}
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * Returns the room where this screening will take place.
     *
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room for this screening.
     *
     * @param room the room to set; must not be {@code null}
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Returns the scheduled date‑time of the screening.
     *
     * @return the screening time
     */
    public LocalDateTime getScreeningTime() {
        return screeningTime;
    }

    /**
     * Sets the scheduled date‑time of the screening.
     *
     * @param screeningTime the time to set; must not be {@code null}
     */
    public void setScreeningTime(LocalDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }

    /* ----------------------------------------------------------------- */
    /* Utility methods                                                   */
    /* ----------------------------------------------------------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Screening that = (Screening) o;
        return Objects.equals(film, that.film) &&
               Objects.equals(room, that.room) &&
               Objects.equals(screeningTime, that.screeningTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film, room, screeningTime);
    }
}