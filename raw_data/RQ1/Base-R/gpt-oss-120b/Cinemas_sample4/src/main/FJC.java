import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Represents a cinema that manages rooms, films and screenings.
 */
 class Cinema {

    /** Formatter for the required date‑time pattern. */
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** Rooms keyed by their unique name. */
    private Map<String, Room> rooms = new HashMap<>();

    /** Films keyed by their unique title. */
    private Map<String, Film> films = new HashMap<>();

    /** All scheduled screenings. */
    private List<Screening> screenings = new ArrayList<>();

    /** Default constructor. */
    public Cinema() {
    }

    /* --------------------------------------------------------------------- */
    /*                     Getters and Setters for fields                    */
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
    /*                     Functional Requirements Methods                   */
    /* --------------------------------------------------------------------- */

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
        String name = room.getName();
        if (rooms.containsKey(name)) {
            return false;
        }
        rooms.put(name, room);
        return true;
    }

    /**
     * Checks whether a room is free at a given time.
     *
     * @param roomName        the name of the room to be checked
     * @param dateTimeString the date‑time string in format {@code yyyy-MM-dd HH:mm:ss}
     * @return {@code true} if the room exists and has no screening scheduled at the
     *         given time; {@code false} otherwise (including parsing errors)
     */
    public boolean isRoomAvailable(String roomName, String dateTimeString) {
        if (roomName == null || dateTimeString == null) {
            return false;
        }
        if (!rooms.containsKey(roomName)) {
            return false;
        }
        LocalDateTime dateTime = parseDateTime(dateTimeString);
        if (dateTime == null) {
            return false;
        }
        for (Screening s : screenings) {
            if (s.getRoom().getName().equals(roomName) && s.getScreeningTime().equals(dateTime)) {
                return false; // already assigned at this exact time
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
        String title = film.getTitle();
        if (films.containsKey(title)) {
            return false;
        }
        films.put(title, film);
        return true;
    }

    /**
     * Removes a film from the cinema system and deletes all its future screenings.
     *
     * @param filmTitle        the title of the film to be removed
     * @param currentTimeString the current time (format {@code yyyy-MM-dd HH:mm:ss})
     * @return {@code true} if the film existed and was removed; {@code false} otherwise
     */
    public boolean removeFilm(String filmTitle, String currentTimeString) {
        if (filmTitle == null || currentTimeString == null) {
            return false;
        }
        if (!films.containsKey(filmTitle)) {
            return false;
        }
        LocalDateTime currentTime = parseDateTime(currentTimeString);
        if (currentTime == null) {
            return false;
        }
        // Remove future screenings (screeningTime >= currentTime)
        Iterator<Screening> iterator = screenings.iterator();
        while (iterator.hasNext()) {
            Screening s = iterator.next();
            if (s.getFilm().getTitle().equals(filmTitle)
                    && !s.getScreeningTime().isBefore(currentTime)) {
                iterator.remove();
            }
        }
        // Finally remove the film
        films.remove(filmTitle);
        return true;
    }

    /**
     * Assigns a screening to a film and a room at a given screening time.
     *
     * @param filmTitle          the title of the film to be screened
     * @param roomName           the name of the room where the screening will occur
     * @param screeningTimeString the desired screening time (format {@code yyyy-MM-dd HH:mm:ss})
     * @param currentTimeString   the current time (format {@code yyyy-MM-dd HH:mm:ss})
     * @return {@code true} if the screening was successfully created; {@code false}
     *         if any validation fails
     */
    public boolean assignScreening(String filmTitle, String roomName,
                                   String screeningTimeString, String currentTimeString) {
        // Validate inputs
        if (filmTitle == null || roomName == null
                || screeningTimeString == null || currentTimeString == null) {
            return false;
        }
        LocalDateTime screeningTime = parseDateTime(screeningTimeString);
        LocalDateTime currentTime = parseDateTime(currentTimeString);
        if (screeningTime == null || currentTime == null) {
            return false;
        }

        // current time must be before screening time
        if (!currentTime.isBefore(screeningTime)) {
            return false;
        }

        // film must be registered
        Film film = films.get(filmTitle);
        if (film == null) {
            return false;
        }

        // room must exist and be available at the screening time
        Room room = rooms.get(roomName);
        if (room == null) {
            return false;
        }
        if (!isRoomAvailable(roomName, screeningTimeString)) {
            return false;
        }

        // Create and store the screening
        Screening screening = new Screening(film, room, screeningTime);
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * @param filmTitle          the title of the film whose screening is to be cancelled
     * @param roomName           the name of the room where the screening was scheduled
     * @param screeningTimeString the scheduled screening time (format {@code yyyy-MM-dd HH:mm:ss})
     * @param currentTimeString   the current time (format {@code yyyy-MM-dd HH:mm:ss})
     * @return {@code true} if the screening existed, was scheduled after the current time,
     *         and was removed; {@code false} otherwise
     */
    public boolean cancelScreening(String filmTitle, String roomName,
                                   String screeningTimeString, String currentTimeString) {
        if (filmTitle == null || roomName == null
                || screeningTimeString == null || currentTimeString == null) {
            return false;
        }
        LocalDateTime screeningTime = parseDateTime(screeningTimeString);
        LocalDateTime currentTime = parseDateTime(currentTimeString);
        if (screeningTime == null || currentTime == null) {
            return false;
        }

        // screening must be in the future relative to current time
        if (!screeningTime.isAfter(currentTime)) {
            return false;
        }

        Iterator<Screening> iterator = screenings.iterator();
        while (iterator.hasNext()) {
            Screening s = iterator.next();
            if (s.getFilm().getTitle().equals(filmTitle)
                    && s.getRoom().getName().equals(roomName)
                    && s.getScreeningTime().equals(screeningTime)) {
                iterator.remove();
                return true;
            }
        }
        return false; // not found
    }

    /* --------------------------------------------------------------------- */
    /*                     Helper Methods                                    */
    /* --------------------------------------------------------------------- */

    /**
     * Parses a date‑time string using the predefined formatter.
     *
     * @param dateTimeString the string to parse
     * @return a {@link LocalDateTime} instance, or {@code null} if parsing fails
     */
    private LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}

/**
 * Simple POJO representing a cinema room.
 */
class Room {

    private String name;
    private int capacity; // optional, can be used later

    /** Default constructor. */
    public Room() {
    }

    /** Parameterised constructor for convenience. */
    public Room(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    /* Getters and Setters */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}

/**
 * Simple POJO representing a film.
 */
class Film {

    private String title;
    private int durationMinutes; // optional, can be used later

    /** Default constructor. */
    public Film() {
    }

    /** Parameterised constructor for convenience. */
    public Film(String title, int durationMinutes) {
        this.title = title;
        this.durationMinutes = durationMinutes;
    }

    /* Getters and Setters */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	public int getDurationMinutes() {
		return durationMinutes;
	}

	public void setDurationMinutes(int durationMinutes) {
		this.durationMinutes = durationMinutes;
	}
}

/**
 * Represents a screening of a film in a specific room at a specific time.
 */
class Screening {

    private Film film;
    private Room room;
    private LocalDateTime screeningTime;

    /** Default constructor. */
    public Screening() {
    }

    /** Full constructor. */
    public Screening(Film film, Room room, LocalDateTime screeningTime) {
        this.film = film;
        this.room = room;
        this.screeningTime = screeningTime;
    }

    /* Getters and Setters */

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

    public LocalDateTime getScreeningTime() {
        return screeningTime;
    }

    public void setScreeningTime(LocalDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }
}