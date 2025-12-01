import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a room (theater hall) inside a cinema.
 */
 class Room {

    /** Unique identifier or name of the room. */
    private String name;

    /** Seating capacity of the room (optional, not used in current logic). */
    private int capacity;

    /** No‑argument constructor required by the specification. */
    public Room() {
    }

    /** Getter for the room name. */
    public String getName() {
        return name;
    }

    /** Setter for the room name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for the capacity. */
    public int getCapacity() {
        return capacity;
    }

    /** Setter for the capacity. */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

/**
 * Represents a film that can be shown in the cinema.
 */
 class Film {

    /** Title of the film – used as a unique identifier. */
    private String title;

    /** Duration of the film in minutes (optional, not used in current logic). */
    private int durationMinutes;

    /** No‑argument constructor required by the specification. */
    public Film() {
    }

    /** Getter for the film title. */
    public String getTitle() {
        return title;
    }

    /** Setter for the film title. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Getter for the film duration. */
    public int getDurationMinutes() {
        return durationMinutes;
    }

    /** Setter for the film duration. */
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}

/**
 * Represents a single screening (show) of a film in a specific room at a specific time.
 */
 class Screening {

    /** Film that will be shown. */
    private Film film;

    /** Room where the screening takes place. */
    private Room room;

    /** Date and time when the screening starts. */
    private LocalDateTime screeningTime;

    /** No‑argument constructor required by the specification. */
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

/**
 * Central class managing rooms, films and screenings for a cinema.
 */
 class Cinema {

    /** Formatter for parsing and formatting date‑time strings used throughout the API. */
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** Rooms registered in the cinema, keyed by room name. */
    private Map<String, Room> rooms = new HashMap<>();

    /** Films registered in the cinema, keyed by film title. */
    private Map<String, Film> films = new HashMap<>();

    /** All scheduled screenings. */
    private List<Screening> screenings = new ArrayList<>();

    /** No‑argument constructor required by the specification. */
    public Cinema() {
    }

    /** Getter for the rooms map (useful for tests). */
    public Map<String, Room> getRooms() {
        return rooms;
    }

    /** Setter for the rooms map (useful for tests). */
    public void setRooms(Map<String, Room> rooms) {
        this.rooms = rooms;
    }

    /** Getter for the films map (useful for tests). */
    public Map<String, Film> getFilms() {
        return films;
    }

    /** Setter for the films map (useful for tests). */
    public void setFilms(Map<String, Film> films) {
        this.films = films;
    }

    /** Getter for the list of screenings (useful for tests). */
    public List<Screening> getScreenings() {
        return screenings;
    }

    /** Setter for the list of screenings (useful for tests). */
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    /**
     * Adds a new room to the cinema.
     *
     * @param room the {@link Room} object to be added
     * @return {@code true} if the room was not previously registered and was added;
     *         {@code false} if a room with the same name already exists or the input is {@code null}
     */
    public boolean addRoom(Room room) {
        if (room == null || room.getName() == null) {
            return false;
        }
        String name = room.getName();
        if (rooms.containsKey(name)) {
            return false;
        }
        rooms.put(name, room);
        return true;
    }

    /**
     * Checks whether a room is available at a given date‑time.
     *
     * @param roomName         the name of the room to check
     * @param dateTimeStr      the date‑time string in the format {@code yyyy-MM-dd HH:mm:ss}
     * @return {@code true} if the room exists and no screening is scheduled for that exact time;
     *         {@code false} otherwise (including parsing errors)
     */
    public boolean isRoomAvailable(String roomName, String dateTimeStr) {
        if (roomName == null || dateTimeStr == null) {
            return false;
        }
        if (!rooms.containsKey(roomName)) {
            return false;
        }
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }

        for (Screening s : screenings) {
            if (roomName.equals(s.getRoom().getName())
                    && dateTime.equals(s.getScreeningTime())) {
                return false; // already assigned
            }
        }
        return true;
    }

    /**
     * Adds a new film to the cinema.
     *
     * @param film the {@link Film} object to be added
     * @return {@code true} if the film was not previously registered and was added;
     *         {@code false} if a film with the same title already exists or the input is {@code null}
     */
    public boolean addFilm(Film film) {
        if (film == null || film.getTitle() == null) {
            return false;
        }
        String title = film.getTitle();
        if (films.containsKey(title)) {
            return false;
        }
        films.put(title, film);
        return true;
    }

    /**
     * Removes a film from the cinema system together with all its future screenings.
     *
     * @param filmTitle        the title of the film to be removed
     * @param currentTimeStr   the current date‑time string in the format {@code yyyy-MM-dd HH:mm:ss}
     * @return {@code true} if the film existed and was removed (including its future screenings);
     *         {@code false} if the film does not exist or the current time string is invalid
     */
    public boolean removeFilm(String filmTitle, String currentTimeStr) {
        if (filmTitle == null || currentTimeStr == null) {
            return false;
        }
        if (!films.containsKey(filmTitle)) {
            return false;
        }
        LocalDateTime currentTime;
        try {
            currentTime = LocalDateTime.parse(currentTimeStr, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }

        // Remove future screenings (screeningTime >= currentTime)
        screenings.removeIf(s ->
                filmTitle.equals(s.getFilm().getTitle())
                        && !s.getScreeningTime().isBefore(currentTime));

        // Finally remove the film entry
        films.remove(filmTitle);
        return true;
    }

    /**
     * Assigns a screening to a film and a room at a specific screening time.
     *
     * @param filmTitle        title of the film to be screened
     * @param roomName         name of the room where the screening will take place
     * @param screeningTimeStr desired screening date‑time in the format {@code yyyy-MM-dd HH:mm:ss}
     * @param currentTimeStr   current date‑time in the format {@code yyyy-MM-dd HH:mm:ss}
     * @return {@code true} if the screening is successfully scheduled; {@code false}
     *         if any validation fails (film not added, room not available, time constraints, etc.)
     */
    public boolean assignScreening(String filmTitle,
                                   String roomName,
                                   String screeningTimeStr,
                                   String currentTimeStr) {
        // Basic null checks
        if (filmTitle == null || roomName == null
                || screeningTimeStr == null || currentTimeStr == null) {
            return false;
        }

        // Verify film exists
        Film film = films.get(filmTitle);
        if (film == null) {
            return false;
        }

        // Verify room exists
        Room room = rooms.get(roomName);
        if (room == null) {
            return false;
        }

        // Parse date‑times
        LocalDateTime screeningTime;
        LocalDateTime currentTime;
        try {
            screeningTime = LocalDateTime.parse(screeningTimeStr, DATE_TIME_FORMATTER);
            currentTime = LocalDateTime.parse(currentTimeStr, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }

        // Current time must be before screening time
        if (!currentTime.isBefore(screeningTime)) {
            return false;
        }

        // Check room availability at the screening time
        if (!isRoomAvailable(roomName, screeningTimeStr)) {
            return false;
        }

        // All checks passed – create and store the screening
        Screening screening = new Screening();
        screening.setFilm(film);
        screening.setRoom(room);
        screening.setScreeningTime(screeningTime);
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * @param filmTitle        title of the film whose screening should be cancelled
     * @param roomName         name of the room where the screening was scheduled
     * @param screeningTimeStr date‑time of the screening to cancel (format {@code yyyy-MM-dd HH:mm:ss})
     * @param currentTimeStr   current date‑time (format {@code yyyy-MM-dd HH:mm:ss})
     * @return {@code true} if the screening existed, is in the future, and was removed;
     *         {@code false} otherwise (including parsing errors or if the screening is not future)
     */
    public boolean cancelScreening(String filmTitle,
                                   String roomName,
                                   String screeningTimeStr,
                                   String currentTimeStr) {
        if (filmTitle == null || roomName == null
                || screeningTimeStr == null || currentTimeStr == null) {
            return false;
        }

        LocalDateTime screeningTime;
        LocalDateTime currentTime;
        try {
            screeningTime = LocalDateTime.parse(screeningTimeStr, DATE_TIME_FORMATTER);
            currentTime = LocalDateTime.parse(currentTimeStr, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }

        // Screening must be after the current time
        if (!screeningTime.isAfter(currentTime)) {
            return false;
        }

        // Find the exact screening
        Screening toRemove = null;
        for (Screening s : screenings) {
            if (filmTitle.equals(s.getFilm().getTitle())
                    && roomName.equals(s.getRoom().getName())
                    && screeningTime.equals(s.getScreeningTime())) {
                toRemove = s;
                break;
            }
        }

        if (toRemove == null) {
            return false; // no such screening
        }

        screenings.remove(toRemove);
        return true;
    }
}