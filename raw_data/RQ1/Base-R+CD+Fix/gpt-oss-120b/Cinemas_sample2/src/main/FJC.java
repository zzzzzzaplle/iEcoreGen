import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

/**
 * Represents a cinema that manages rooms, films and screenings.
 */
 class Cinema {

    /** List of rooms owned by the cinema. */
    private List<Room> rooms;

    /** List of screenings scheduled in the cinema. */
    private List<Screening> screenings;

    /** List of films available in the cinema. */
    private List<Film> films;

    /** No‑argument constructor required by the specification. */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.films = new ArrayList<>();
    }

    /** @return the list of rooms */
    public List<Room> getRooms() {
        return rooms;
    }

    /** @param rooms the list of rooms to set */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /** @return the list of screenings */
    public List<Screening> getScreenings() {
        return screenings;
    }

    /** @param screenings the list of screenings to set */
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    /** @return the list of films */
    public List<Film> getFilms() {
        return films;
    }

    /** @param films the list of films to set */
    public void setFilms(List<Film> films) {
        this.films = films;
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
     * Adds a film to the cinema.
     *
     * @param film the film to add
     * @return {@code true} if the film was not already registered and was added,
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
     * Removes a film from the cinema system and deletes all its future screenings.
     *
     * @param film        the film to remove
     * @param currentTime the current time; screenings with a time {@code >=}
     *                    this value are removed
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
        // Remove the film from the list of films
        films.remove(film);

        // Remove all future screenings of this film
        Iterator<Screening> it = screenings.iterator();
        while (it.hasNext()) {
            Screening sc = it.next();
            if (sc.getFilm().equals(film) && !sc.getTime().before(currentTime)) {
                it.remove();
            }
        }
        return true;
    }

    /**
     * Assigns a screening to a film and a room at a specific screening time.
     *
     * @param film        the film to be screened
     * @param room        the room where the screening will take place
     * @param screening   the screening object containing the desired time
     * @param currentTime the current time; must be before {@code screening.time}
     * @return {@code true} if the screening is successfully assigned, {@code false}
     *         otherwise (invalid times, film/room not registered, room unavailable)
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (film == null || room == null || screening == null || currentTime == null) {
            return false;
        }
        Date screeningTime = screening.getTime();
        if (screeningTime == null) {
            return false;
        }
        // Current time must be before the screening time
        if (!currentTime.before(screeningTime)) {
            return false;
        }
        // Film must be registered
        if (!films.contains(film)) {
            return false;
        }
        // Room must be registered and available at the screening time
        if (!rooms.contains(room) || !checkAvailability(room, screeningTime)) {
            return false;
        }
        // Set film and room references inside the screening (in case they are not set)
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
     * @return {@code true} if the screening existed and was cancelled, {@code false}
     *         otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || currentTime == null) {
            return false;
        }
        if (!screenings.contains(screening)) {
            return false;
        }
        if (!screening.getTime().after(currentTime)) {
            return false; // cannot cancel past or ongoing screenings
        }
        screenings.remove(screening);
        return true;
    }

    /**
     * Checks whether a given room is available at a specific time.
     *
     * @param room the room to check
     * @param time the time to verify availability for
     * @return {@code true} if the room is registered in the cinema and no other
     *         screening occupies it at the given time; {@code false} otherwise
     */
    public boolean checkAvailability(Room room, Date time) {
        if (room == null || time == null) {
            return false;
        }
        if (!rooms.contains(room)) {
            return false;
        }
        for (Screening sc : screenings) {
            if (room.equals(sc.getRoom()) && sc.getTime().equals(time)) {
                return false; // room already assigned at this exact time
            }
        }
        return true;
    }
}

/**
 * Represents a room (theater hall) inside a cinema.
 */
class Room {

    /** Unique name or identifier of the room. */
    private String name;

    /** No‑argument constructor required by the specification. */
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

    /** @return the room name */
    public String getName() {
        return name;
    }

    /** @param name the room name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on the room name (case‑sensitive). */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Room)) return false;
        Room other = (Room) obj;
        return name != null && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return (name == null) ? 0 : name.hashCode();
    }

    @Override
    public String toString() {
        return "Room{name='" + name + "'}";
    }
}

/**
 * Represents a film that can be shown in the cinema.
 */
class Film {

    /** Unique title of the film. */
    private String title;

    /** No‑argument constructor required by the specification. */
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

    /** Equality based on the film title (case‑sensitive). */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Film)) return false;
        Film other = (Film) obj;
        return title != null && title.equals(other.title);
    }

    @Override
    public int hashCode() {
        return (title == null) ? 0 : title.hashCode();
    }

    @Override
    public String toString() {
        return "Film{title='" + title + "'}";
    }
}

/**
 * Represents a screening of a film in a particular room at a specific time.
 */
class Screening {

    /** The date and time when the screening starts. */
    private Date time;

    /** The film being shown. */
    private Film film;

    /** The room where the screening takes place. */
    private Room room;

    /** No‑argument constructor required by the specification. */
    public Screening() {
    }

    /**
     * Constructs a screening with the given time.
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

    /** Equality is based on time, film and room. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Screening)) return false;
        Screening other = (Screening) obj;
        return (time != null && time.equals(other.time))
                && (film != null && film.equals(other.film))
                && (room != null && room.equals(other.room));
    }

    @Override
    public int hashCode() {
        int result = (time == null) ? 0 : time.hashCode();
        result = 31 * result + ((film == null) ? 0 : film.hashCode());
        result = 31 * result + ((room == null) ? 0 : room.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Screening{time=" + time + ", film=" + film + ", room=" + room + '}';
    }
}