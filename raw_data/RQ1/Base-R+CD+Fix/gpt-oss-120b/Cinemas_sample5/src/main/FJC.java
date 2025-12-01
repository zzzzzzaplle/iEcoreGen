import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Represents a cinema that manages rooms, films and screenings.
 */
 class Cinema {

    /** List of rooms that belong to this cinema. */
    private List<Room> rooms;

    /** List of films that are available in this cinema. */
    private List<Film> films;

    /** List of all scheduled screenings. */
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
     * Adds a room to the cinema.
     *
     * @param room the room to add
     * @return {@code true} if the room was not already registered and was added,
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
     * Checks whether a given room is available at the specified time.
     *
     * @param room the room to check
     * @param time the time for which availability is required
     * @return {@code true} if the room exists in the cinema and no other screening
     *         occupies it at the given time; {@code false} otherwise (including
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
                return false; // room already assigned at this exact time
            }
        }
        return true;
    }

    /**
     * Adds a film to the cinema.
     *
     * @param film the film to add
     * @return {@code true} if the film was not already present and was added,
     *         {@code false} if the film already exists
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
     * @param film        the film to remove
     * @param currentTime the current time; screenings with {@code time >= currentTime}
     *                    will be removed
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
        // Remove the film
        films.remove(film);
        // Remove future screenings of this film
        Iterator<Screening> iterator = screenings.iterator();
        while (iterator.hasNext()) {
            Screening s = iterator.next();
            if (film.equals(s.getFilm()) && !s.getTime().before(currentTime)) {
                iterator.remove();
            }
        }
        return true;
    }

    /**
     * Assigns a screening to a film and a room.
     *
     * @param film        the film to be shown
     * @param room        the room where the film will be shown
     * @param screening   the screening containing the desired time
     * @param currentTime the current time; must be before the screening time
     * @return {@code true} if the screening was successfully assigned;
     *         {@code false} otherwise (invalid input, film not present,
     *         room unavailable, or current time not before screening time)
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (film == null || room == null || screening == null || currentTime == null) {
            return false;
        }
        // current time must be before screening time
        if (!currentTime.before(screening.getTime())) {
            return false;
        }
        // film must be registered
        if (!films.contains(film)) {
            return false;
        }
        // room must be registered and available
        if (!checkAvailability(room, screening.getTime())) {
            return false;
        }
        // set proper references (defensive copy)
        screening.setFilm(film);
        screening.setRoom(room);
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * @param screening   the screening to cancel
     * @param currentTime the current time; the screening must be scheduled after this time
     * @return {@code true} if the screening existed, was scheduled after the current
     *         time and was removed; {@code false} otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || currentTime == null) {
            return false;
        }
        if (!screenings.contains(screening)) {
            return false;
        }
        if (!screening.getTime().after(currentTime)) {
            return false; // cannot cancel past or current screenings
        }
        screenings.remove(screening);
        return true;
    }
}

/**
 * Represents a cinema room.
 */
class Room {

    /** Human‑readable name or identifier of the room. */
    private String name;

    /** Default constructor. */
    public Room() {
    }

    /**
     * Constructs a room with a given name.
     *
     * @param name the name of the room
     */
    public Room(String name) {
        this.name = name;
    }

    /** @return the room name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on the room name. */
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
 * Represents a film.
 */
class Film {

    /** Title of the film. */
    private String title;

    /** Default constructor. */
    public Film() {
    }

    /**
     * Constructs a film with a given title.
     *
     * @param title the film title
     */
    public Film(String title) {
        this.title = title;
    }

    /** @return the film title */
    public String getTitle() {
        return title;
    }

    /** @param title the title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Equality based on the film title. */
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
 * Represents a screening – an occurrence of a film shown in a specific room at a specific time.
 */
class Screening {

    /** Date and time of the screening. */
    private Date time;

    /** Film that will be shown. */
    private Film film;

    /** Room where the screening takes place. */
    private Room room;

    /** Default constructor. */
    public Screening() {
    }

    /**
     * Constructs a screening with a given time.
     *
     * @param time the date and time of the screening
     */
    public Screening(Date time) {
        this.time = time;
    }

    /** @return the screening time */
    public Date getTime() {
        return time;
    }

    /** @param time the time to set */
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

    @Override
    public int hashCode() {
        return Objects.hash(time, film, room);
    }
}