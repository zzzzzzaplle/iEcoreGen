import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a cinema that manages rooms, films and their screenings.
 */
 class Cinema {

    /** Formatter used for parsing and formatting date‑time strings. */
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Set<Room> rooms = new HashSet<>();
    private Set<Film> films = new HashSet<>();
    private List<Screening> screenings = new ArrayList<>();

    /** Default constructor required by the task description. */
    public Cinema() {
    }

    /** Getter for the set of rooms. */
    public Set<Room> getRooms() {
        return rooms;
    }

    /** Setter for the set of rooms (used mainly in tests). */
    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    /** Getter for the set of films. */
    public Set<Film> getFilms() {
        return films;
    }

    /** Setter for the set of films (used mainly in tests). */
    public void setFilms(Set<Film> films) {
        this.films = films;
    }

    /** Getter for the list of screenings. */
    public List<Screening> getScreenings() {
        return screenings;
    }

    /** Setter for the list of screenings (used mainly in tests). */
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    /**
     * Adds a room to the cinema.
     *
     * @param room the {@link Room} to be added
     * @return {@code true} if the room was not already registered and was added,
     *         {@code false} if a room with the same name already exists
     */
    public boolean addRoom(Room room) {
        if (room == null || room.getName() == null) {
            return false;
        }
        return rooms.add(room); // Set.add returns false if element already present
    }

    /**
     * Checks whether a room is available at a given time.
     *
     * @param roomName the name of the room to be checked
     * @param dateTimeStr the date‑time string in the format {@code yyyy-MM-dd HH:mm:ss}
     * @return {@code true} if the room exists in the cinema and has no screening at the given time,
     *         {@code false} otherwise (including invalid inputs)
     */
    public boolean isRoomAvailable(String roomName, String dateTimeStr) {
        if (roomName == null || dateTimeStr == null) {
            return false;
        }

        Room targetRoom = findRoomByName(roomName);
        if (targetRoom == null) {
            return false; // room not registered
        }

        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return false; // invalid date format
        }

        for (Screening s : screenings) {
            if (s.getRoom().equals(targetRoom) && s.getScreeningTime().equals(dateTime)) {
                return false; // already assigned at that time
            }
        }
        return true;
    }

    /**
     * Adds a film to the cinema.
     *
     * @param film the {@link Film} to be added
     * @return {@code true} if the film was not already present and was added,
     *         {@code false} if a film with the same title already exists
     */
    public boolean addFilm(Film film) {
        if (film == null || film.getTitle() == null) {
            return false;
        }
        return films.add(film);
    }

    /**
     * Removes a film from the system together with all its future screenings.
     *
     * @param filmTitle the title of the film to be removed
     * @param currentTimeStr the current time (format {@code yyyy-MM-dd HH:mm:ss}) used as a cutoff
     * @return {@code true} if the film existed and was removed, {@code false} otherwise
     */
    public boolean removeFilm(String filmTitle, String currentTimeStr) {
        if (filmTitle == null || currentTimeStr == null) {
            return false;
        }

        Film targetFilm = findFilmByTitle(filmTitle);
        if (targetFilm == null) {
            return false; // film not found
        }

        LocalDateTime currentTime;
        try {
            currentTime = LocalDateTime.parse(currentTimeStr, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return false; // invalid current time format
        }

        // Remove film from collection
        films.remove(targetFilm);

        // Remove all future screenings (screeningTime >= currentTime)
        Iterator<Screening> iter = screenings.iterator();
        while (iter.hasNext()) {
            Screening s = iter.next();
            if (s.getFilm().equals(targetFilm) && !s.getScreeningTime().isBefore(currentTime)) {
                iter.remove();
            }
        }

        return true;
    }

    /**
     * Assigns a screening to a film and a room at a specific future time.
     *
     * @param filmTitle the title of the film to be screened
     * @param roomName the name of the room where the screening will take place
     * @param screeningTimeStr the desired screening time (format {@code yyyy-MM-dd HH:mm:ss})
     * @param currentTimeStr the current time (format {@code yyyy-MM-dd HH:mm:ss})
     * @return {@code true} if the screening was successfully assigned, {@code false} otherwise
     */
    public boolean assignScreening(String filmTitle, String roomName,
                                   String screeningTimeStr, String currentTimeStr) {
        if (filmTitle == null || roomName == null || screeningTimeStr == null || currentTimeStr == null) {
            return false;
        }

        Film film = findFilmByTitle(filmTitle);
        Room room = findRoomByName(roomName);
        if (film == null || room == null) {
            return false; // film or room not registered
        }

        LocalDateTime screeningTime;
        LocalDateTime currentTime;
        try {
            screeningTime = LocalDateTime.parse(screeningTimeStr, DATE_TIME_FORMATTER);
            currentTime = LocalDateTime.parse(currentTimeStr, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return false; // invalid date format
        }

        // current time must be before the screening time
        if (!currentTime.isBefore(screeningTime)) {
            return false;
        }

        // check room availability at the screening time
        if (!isRoomAvailable(roomName, screeningTimeStr)) {
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
     * @param filmTitle the title of the film whose screening is to be cancelled
     * @param roomName the name of the room where the screening was scheduled
     * @param screeningTimeStr the scheduled screening time (format {@code yyyy-MM-dd HH:mm:ss})
     * @param currentTimeStr the current time (format {@code yyyy-MM-dd HH:mm:ss})
     * @return {@code true} if the screening existed, was in the future and was cancelled,
     *         {@code false} otherwise
     */
    public boolean cancelScreening(String filmTitle, String roomName,
                                   String screeningTimeStr, String currentTimeStr) {
        if (filmTitle == null || roomName == null || screeningTimeStr == null || currentTimeStr == null) {
            return false;
        }

        LocalDateTime screeningTime;
        LocalDateTime currentTime;
        try {
            screeningTime = LocalDateTime.parse(screeningTimeStr, DATE_TIME_FORMATTER);
            currentTime = LocalDateTime.parse(currentTimeStr, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return false; // invalid date format
        }

        // screening must be after current time
        if (!screeningTime.isAfter(currentTime)) {
            return false;
        }

        Iterator<Screening> iter = screenings.iterator();
        while (iter.hasNext()) {
            Screening s = iter.next();
            if (s.getFilm().getTitle().equals(filmTitle)
                    && s.getRoom().getName().equals(roomName)
                    && s.getScreeningTime().equals(screeningTime)) {
                iter.remove();
                return true; // cancelled successfully
            }
        }
        return false; // screening not found
    }

    /** Helper method to locate a room by its name. */
    private Room findRoomByName(String name) {
        for (Room r : rooms) {
            if (name.equals(r.getName())) {
                return r;
            }
        }
        return null;
    }

    /** Helper method to locate a film by its title. */
    private Film findFilmByTitle(String title) {
        for (Film f : films) {
            if (title.equals(f.getTitle())) {
                return f;
            }
        }
        return null;
    }
}

/**
 * Represents a room (theater hall) inside a cinema.
 */
class Room {

    private String name; // unique identifier for a room

    /** Default constructor required by the task description. */
    public Room() {
    }

    /** Constructor with name. */
    public Room(String name) {
        this.name = name;
    }

    /** Getter for the room name. */
    public String getName() {
        return name;
    }

    /** Setter for the room name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on the unique room name. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;
        return Objects.equals(name, room.name);
    }

    /** Hash code based on the unique room name. */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

/**
 * Represents a film that can be shown in a cinema.
 */
class Film {

    private String title; // unique identifier for a film

    /** Default constructor required by the task description. */
    public Film() {
    }

    /** Constructor with title. */
    public Film(String title) {
        this.title = title;
    }

    /** Getter for the film title. */
    public String getTitle() {
        return title;
    }

    /** Setter for the film title. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Equality based on the unique film title. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;
        return Objects.equals(title, film.title);
    }

    /** Hash code based on the unique film title. */
    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}

/**
 * Represents a specific screening of a film in a room at a given time.
 */
class Screening {

    private Film film;
    private Room room;
    private LocalDateTime screeningTime;

    /** Default constructor required by the task description. */
    public Screening() {
    }

    /** Getter for the film. */
    public Film getFilm() {
        return film;
    }

    /** Setter for the film. */
    public void setFilm(Film film) {
        this.film = film;
    }

    /** Getter for the room. */
    public Room getRoom() {
        return room;
    }

    /** Setter for the room. */
    public void setRoom(Room room) {
        this.room = room;
    }

    /** Getter for the screening time. */
    public LocalDateTime getScreeningTime() {
        return screeningTime;
    }

    /** Setter for the screening time. */
    public void setScreeningTime(LocalDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }
}