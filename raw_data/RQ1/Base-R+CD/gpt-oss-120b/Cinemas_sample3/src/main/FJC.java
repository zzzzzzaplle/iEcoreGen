import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents a cinema that contains rooms, films and scheduled screenings.
 */
 class Cinema {

    /** List of rooms owned by the cinema. */
    private List<Room> rooms = new ArrayList<>();

    /** List of films owned by the cinema. */
    private List<Film> films = new ArrayList<>();

    /** List of all scheduled screenings. */
    private List<Screening> screenings = new ArrayList<>();

    /** Default constructor. */
    public Cinema() {
        // No‑arg constructor as required.
    }

    /**
     * Returns the list of rooms.
     *
     * @return mutable list of rooms
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Sets the list of rooms.
     *
     * @param rooms the new list of rooms
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Returns the list of films.
     *
     * @return mutable list of films
     */
    public List<Film> getFilms() {
        return films;
    }

    /**
     * Sets the list of films.
     *
     * @param films the new list of films
     */
    public void setFilms(List<Film> films) {
        this.films = films;
    }

    /**
     * Returns the list of screenings.
     *
     * @return mutable list of screenings
     */
    public List<Screening> getScreenings() {
        return screenings;
    }

    /**
     * Sets the list of screenings.
     *
     * @param screenings the new list of screenings
     */
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    /**
     * Adds a room to the cinema.
     *
     * <p>If the room is not already registered, it is added and the method returns {@code true}.
     * If the room already exists (based on {@link Room#equals(Object)}), the method returns {@code false}.
     *
     * @param room the room to add; must not be {@code null}
     * @return {@code true} if the room was added; {@code false} otherwise
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
     * Adds a film to the cinema.
     *
     * <p>If the film is not already present, it is added and the method returns {@code true}.
     * If the film already exists (based on {@link Film#equals(Object)}), the method returns {@code false}.
     *
     * @param film the film to add; must not be {@code null}
     * @return {@code true} if the film was added; {@code false} otherwise
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
     * Removes a film from the cinema system and deletes all of its future scheduled screenings.
     *
     * <p>A screening is considered "future" if its scheduled time is greater than or equal to the
     * supplied {@code currentTime}.
     *
     * @param film        the film to remove; must not be {@code null}
     * @param currentTime the reference time; must not be {@code null}
     * @return {@code true} if the film existed and was removed; {@code false} otherwise
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (film == null || currentTime == null) {
            return false;
        }
        if (!films.contains(film)) {
            return false;
        }
        // Remove the film from the film list.
        films.remove(film);

        // Remove all future screenings of this film.
        screenings.removeIf(screening ->
                screening.getFilm().equals(film) &&
                !screening.getTime().before(currentTime) // time >= currentTime
        );
        return true;
    }

    /**
     * Assigns a screening to a film and a room at a specific screening time.
     *
     * <p>The method validates the following constraints:
     * <ul>
     *   <li>The {@code currentTime} must be strictly before the screening time.</li>
     *   <li>The film must already be added to the cinema.</li>
     *   <li>The room must already be added to the cinema and be available at the screening time.</li>
     * </ul>
     *
     * If all constraints are satisfied, the screening is added to the cinema's schedule and the
     * method returns {@code true}. Otherwise, it returns {@code false}.
     *
     * @param film        the film to be screened; must not be {@code null}
     * @param room        the room where the screening will take place; must not be {@code null}
     * @param screening   the screening object containing the scheduled time; must not be {@code null}
     * @param currentTime the current time used for validation; must not be {@code null}
     * @return {@code true} if the screening was successfully assigned; {@code false} otherwise
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (film == null || room == null || screening == null || currentTime == null) {
            return false;
        }
        Date screeningTime = screening.getTime();
        if (screeningTime == null) {
            return false;
        }
        // currentTime must be before screeningTime
        if (!currentTime.before(screeningTime)) {
            return false;
        }
        // Film must be registered
        if (!films.contains(film)) {
            return false;
        }
        // Room must be registered
        if (!rooms.contains(room)) {
            return false;
        }
        // Room must be available at the screening time
        if (!checkAvailability(room, screeningTime)) {
            return false;
        }
        // Set the film and room references inside the screening (in case they are not set)
        screening.setFilm(film);
        screening.setRoom(room);
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * <p>The method checks that the screening exists in the cinema and that its scheduled time
     * is after the supplied {@code currentTime}. If both conditions hold, the screening is removed
     * and the method returns {@code true}; otherwise it returns {@code false}.
     *
     * @param screening   the screening to cancel; must not be {@code null}
     * @param currentTime the current time used for validation; must not be {@code null}
     * @return {@code true} if the screening was successfully cancelled; {@code false} otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || currentTime == null) {
            return false;
        }
        if (!screenings.contains(screening)) {
            return false;
        }
        // Screening time must be after current time
        if (!screening.getTime().after(currentTime)) {
            return false;
        }
        screenings.remove(screening);
        return true;
    }

    /**
     * Checks whether a given room is available at a specific time.
     *
     * <p>A room is considered available if:
     * <ul>
     *   <li>The room is registered in the cinema.</li>
     *   <li>No other screening is scheduled in that room at the exact same time.</li>
     * </ul>
     *
     * If any of the inputs are {@code null} or the room is not registered, the method returns {@code false}.
     *
     * @param room the room to check; must not be {@code null}
     * @param time the time to check for availability; must not be {@code null}
     * @return {@code true} if the room is available at the given time; {@code false} otherwise
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
                return false; // already assigned at this time
            }
        }
        return true;
    }
}

/**
 * Represents a room (theater) inside a cinema.
 */
class Room {

    /** Unique name or identifier of the room. */
    private String name;

    /** Default constructor. */
    public Room() {
        // No‑arg constructor as required.
    }

    /**
     * Returns the name of the room.
     *
     * @return the room name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the room.
     *
     * @param name the new name; must not be {@code null}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Two rooms are considered equal if they have the same name (case‑sensitive).
     *
     * @param o the other object
     * @return {@code true} if both rooms have the same name; {@code false} otherwise
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
     * Returns a string representation of the room.
     *
     * @return string containing the room name
     */
    @Override
    public String toString() {
        return "Room{name='" + name + '\'' + '}';
    }
}

/**
 * Represents a film that can be shown in a cinema.
 */
class Film {

    /** Unique title of the film. */
    private String title;

    /** Default constructor. */
    public Film() {
        // No‑arg constructor as required.
    }

    /**
     * Returns the title of the film.
     *
     * @return the film title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the film.
     *
     * @param title the new title; must not be {@code null}
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Two films are considered equal if they have the same title (case‑sensitive).
     *
     * @param o the other object
     * @return {@code true} if both films have the same title; {@code false} otherwise
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
     * Returns a string representation of the film.
     *
     * @return string containing the film title
     */
    @Override
    public String toString() {
        return "Film{title='" + title + '\'' + '}';
    }
}

/**
 * Represents a screening of a film in a specific room at a specific time.
 */
class Screening {

    /** Date and time of the screening. */
    private Date time;

    /** Film that will be shown. */
    private Film film;

    /** Room where the screening will take place. */
    private Room room;

    /** Default constructor. */
    public Screening() {
        // No‑arg constructor as required.
    }

    /**
     * Returns the scheduled time of the screening.
     *
     * @return the screening time
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets the scheduled time of the screening.
     *
     * @param time the new screening time; must not be {@code null}
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Returns the film being screened.
     *
     * @return the film
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Sets the film for this screening.
     *
     * @param film the film; must not be {@code null}
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * Returns the room where the screening takes place.
     *
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room for this screening.
     *
     * @param room the room; must not be {@code null}
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Two screenings are considered equal if they have the same time, film and room.
     *
     * @param o the other object
     * @return {@code true} if both screenings are identical; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Screening that = (Screening) o;
        return Objects.equals(time, that.time) &&
               Objects.equals(film, that.film) &&
               Objects.equals(room, that.room);
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
     * Returns a string representation of the screening.
     *
     * @return string containing time, film title and room name
     */
    @Override
    public String toString() {
        String timeStr = (time != null) ? time.toString() : "null";
        String filmStr = (film != null) ? film.getTitle() : "null";
        String roomStr = (room != null) ? room.getName() : "null";
        return "Screening{time=" + timeStr + ", film=" + filmStr + ", room=" + roomStr + '}';
    }
}