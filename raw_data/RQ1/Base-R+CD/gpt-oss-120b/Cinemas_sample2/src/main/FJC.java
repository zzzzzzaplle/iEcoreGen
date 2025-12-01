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

    /** List of films owned by the cinema. */
    private List<Film> films = new ArrayList<>();

    /** List of all scheduled screenings. */
    private List<Screening> screenings = new ArrayList<>();

    /** Unparameterized constructor. */
    public Cinema() {
        // empty constructor
    }

    /** @return the list of rooms (modifiable). */
    public List<Room> getRooms() {
        return rooms;
    }

    /** @param rooms the list of rooms to set. */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /** @return the list of films (modifiable). */
    public List<Film> getFilms() {
        return films;
    }

    /** @param films the list of films to set. */
    public void setFilms(List<Film> films) {
        this.films = films;
    }

    /** @return the list of screenings (modifiable). */
    public List<Screening> getScreenings() {
        return screenings;
    }

    /** @param screenings the list of screenings to set. */
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    /**
     * Adds a new room to the cinema.
     *
     * @param room the {@link Room} to add
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
     * Adds a new film to the cinema.
     *
     * @param film the {@link Film} to add
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
     * @param film        the {@link Film} to remove
     * @param currentTime the current time; screenings with a time {@code >=} this value are removed
     * @return {@code true} if the film existed and was removed, {@code false} otherwise
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
        Iterator<Screening> it = screenings.iterator();
        while (it.hasNext()) {
            Screening s = it.next();
            if (s.getFilm().equals(film) && !s.getTime().before(currentTime)) {
                it.remove();
            }
        }
        return true;
    }

    /**
     * Assigns a screening to a film and a room at a specific time.
     *
     * @param film        the {@link Film} to be screened (must already be added to the cinema)
     * @param room        the {@link Room} where the screening will take place (must be added to the cinema)
     * @param screening   the {@link Screening} containing the desired screening time
     * @param currentTime the current time; must be before the screening time
     * @return {@code true} if the screening was successfully assigned, {@code false} otherwise
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (film == null || room == null || screening == null || currentTime == null) {
            return false;
        }
        // Verify film and room are registered
        if (!films.contains(film) || !rooms.contains(room)) {
            return false;
        }
        // Verify current time is before screening time
        if (!currentTime.before(screening.getTime())) {
            return false;
        }
        // Verify room availability at the screening time
        if (!checkAvailability(room, screening.getTime())) {
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
     * @param screening   the {@link Screening} to cancel
     * @param currentTime the current time; the screening must be scheduled after this time
     * @return {@code true} if the screening existed and was canceled, {@code false} otherwise
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

    /**
     * Checks whether a given room is available at a specific time.
     *
     * @param room the {@link Room} to check (must be registered in the cinema)
     * @param time the time to check availability for
     * @return {@code true} if the room exists in the cinema and is not assigned to another
     *         screening at the given time; {@code false} otherwise (including invalid inputs)
     */
    public boolean checkAvailability(Room room, Date time) {
        if (room == null || time == null) {
            return false;
        }
        if (!rooms.contains(room)) {
            return false;
        }
        for (Screening s : screenings) {
            if (s.getRoom().equals(room) && s.getTime().equals(time)) {
                return false; // already assigned at this exact time
            }
        }
        return true;
    }
}

/**
 * Represents a cinema room.
 */
class Room {

    /** Unique name or identifier of the room. */
    private String name;

    /** Unparameterized constructor. */
    public Room() {
        // empty constructor
    }

    /**
     * Constructs a room with the given name.
     *
     * @param name the name of the room
     */
    public Room(String name) {
        this.name = name;
    }

    /** @return the name of the room */
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

    /** Human‑readable representation. */
    @Override
    public String toString() {
        return "Room{name='" + name + "'}";
    }
}

/**
 * Represents a film.
 */
class Film {

    /** Unique title of the film. */
    private String title;

    /** Unparameterized constructor. */
    public Film() {
        // empty constructor
    }

    /**
     * Constructs a film with the given title.
     *
     * @param title the title of the film
     */
    public Film(String title) {
        this.title = title;
    }

    /** @return the title of the film */
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

    /** Human‑readable representation. */
    @Override
    public String toString() {
        return "Film{title='" + title + "'}";
    }
}

/**
 * Represents a screening of a film in a specific room at a specific time.
 */
class Screening {

    /** Time of the screening. */
    private Date time;

    /** Film being screened. */
    private Film film;

    /** Room where the screening takes place. */
    private Room room;

    /** Unparameterized constructor. */
    public Screening() {
        // empty constructor
    }

    /**
     * Constructs a screening with the given time, film and room.
     *
     * @param time  the date and time of the screening
     * @param film  the film to be shown
     * @param room  the room where the screening occurs
     */
    public Screening(Date time, Film film, Room room) {
        this.time = time;
        this.film = film;
        this.room = room;
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

        Screening that = (Screening) o;
        return Objects.equals(time, that.time) &&
               Objects.equals(film, that.film) &&
               Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, film, room);
    }

    /** Human‑readable representation. */
    @Override
    public String toString() {
        return "Screening{time=" + time + ", film=" + film + ", room=" + room + '}';
    }
}