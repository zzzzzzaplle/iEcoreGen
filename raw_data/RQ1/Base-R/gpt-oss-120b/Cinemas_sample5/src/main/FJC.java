import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Represents a cinema room.
 */
 class Room {

    /** Unique name of the room (e.g., "Room A") */
    private String name;

    /** Default constructor required for tests */
    public Room() {
    }

    /**
     * Constructs a Room with the given name.
     *
     * @param name the unique name of the room
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

    /** Equality based on room name */
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

    /** Unique title of the film (e.g., "Inception") */
    private String title;

    /** Default constructor required for tests */
    public Film() {
    }

    /**
     * Constructs a Film with the given title.
     *
     * @param title the unique title of the film
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

    /** Equality based on film title */
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
 * Represents a screening (a film shown in a specific room at a given time).
 */
 class Screening {

    private Film film;
    private Room room;
    private LocalDateTime screeningTime;

    /** Default constructor required for tests */
    public Screening() {
    }

    /**
     * Constructs a Screening.
     *
     * @param film          the film being shown
     * @param room          the room where the film is shown
     * @param screeningTime the date‑time of the screening
     */
    public Screening(Film film, Room room, LocalDateTime screeningTime) {
        this.film = film;
        this.room = room;
        this.screeningTime = screeningTime;
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

    /** @return the screening time */
    public LocalDateTime getScreeningTime() {
        return screeningTime;
    }

    /** @param screeningTime the screening time to set */
    public void setScreeningTime(LocalDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }
}

/**
 * Central class managing rooms, films and screenings for a cinema.
 */
 class Cinema {

    private List<Room> rooms;
    private List<Film> films;
    private List<Screening> screenings;

    /** Default constructor initialising internal collections */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.films = new ArrayList<>();
        this.screenings = new ArrayList<>();
    }

    /** @return the list of rooms (modifiable) */
    public List<Room> getRooms() {
        return rooms;
    }

    /** @param rooms the rooms list to set */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /** @return the list of films (modifiable) */
    public List<Film> getFilms() {
        return films;
    }

    /** @param films the films list to set */
    public void setFilms(List<Film> films) {
        this.films = films;
    }

    /** @return the list of screenings (modifiable) */
    public List<Screening> getScreenings() {
        return screenings;
    screenings the screenings list to set */
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Adds a new room to the cinema.
     *
     * @param room the room to add
     * @return {@code true} if the room was not already registered and was added,
     *         {@code false} if the room already exists or the argument is {@code null}
     */
    public boolean addRoom(Room room) {
        if (room == null || room.getName() == null) {
            return false;
        }
        for (Room r : rooms) {
            if (r.getName().equals(room.getName())) {
                return false; // already exists
            }
        }
        rooms.add(room);
        return true;
    }

    /**
     * Checks whether a room is available at the given date‑time.
     *
     * @param roomName   the name of the room to check
     * @param dateTimeStr the date‑time string in format {@code yyyy-MM-dd HH:mm:ss}
     * @return {@code true} if the room exists and has no screening at the given time,
     *         {@code false} otherwise (including parsing errors)
     */
    public boolean isRoomAvailable(String roomName, String dateTimeStr) {
        if (roomName == null || dateTimeStr == null) {
            return false;
        }
        Room room = findRoomByName(roomName);
        if (room == null) {
            return false; // room not registered
        }
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dateTimeStr, FORMATTER);
        } catch (DateTimeParseException e) {
            return false; // invalid format
        }
        for (Screening s : screenings) {
            if (s.getRoom().equals(room) && s.getScreeningTime().equals(dateTime)) {
                return false; // already assigned
            }
        }
        return true;
    }

    /**
     * Adds a new film to the cinema.
     *
     * @param film the film to add
     * @return {@code true} if the film was not already present and was added,
     *         {@code false} if the film already exists or the argument is {@code null}
     */
    public boolean addFilm(Film film) {
        if (film == null || film.getTitle() == null) {
            return false;
        }
        for (Film f : films) {
            if (f.getTitle().equals(film.getTitle())) {
                return false; // already exists
            }
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film from the system and deletes all its future screenings.
     *
     * @param filmTitle      the title of the film to remove
     * @param currentTimeStr the current time (format {@code yyyy-MM-dd HH:mm:ss})
     * @return {@code true} if the film existed and was removed,
     *         {@code false} if the film does not exist or the time format is invalid
     */
    public boolean removeFilm(String filmTitle, String currentTimeStr) {
        if (filmTitle == null || currentTimeStr == null) {
            return false;
        }
        Film film = findFilmByTitle(filmTitle);
        if (film == null) {
            return false; // film not found
        }
        LocalDateTime currentTime;
        try {
            currentTime = LocalDateTime.parse(currentTimeStr, FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }

        // Remove future screenings (>= current time)
        Iterator<Screening> it = screenings.iterator();
        while (it.hasNext()) {
            Screening s = it.next();
            if (s.getFilm().equals(film) && !s.getScreeningTime().isBefore(currentTime)) {
                it.remove();
            }
        }

        // Remove the film itself
        films.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific future time.
     *
     * @param filmTitle       the title of the film
     * @param roomName        the name of the room
     * @param screeningTimeStr the screening time (format {@code yyyy-MM-dd HH:mm:ss})
     * @param currentTimeStr   the current time (format {@code yyyy-MM-dd HH:mm:ss})
     * @return {@code true} if the screening was successfully assigned,
     *         {@code false} otherwise (invalid times, missing film/room, room unavailable, etc.)
     */
    public boolean assignScreening(String filmTitle, String roomName,
                                   String screeningTimeStr, String currentTimeStr) {
        if (filmTitle == null || roomName == null || screeningTimeStr == null || currentTimeStr == null) {
            return false;
        }

        LocalDateTime screeningTime;
        LocalDateTime currentTime;
        try {
            screeningTime = LocalDateTime.parse(screeningTimeStr, FORMATTER);
            currentTime = LocalDateTime.parse(currentTimeStr, FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }

        // current time must be before screening time
        if (!currentTime.isBefore(screeningTime)) {
            return false;
        }

        Film film = findFilmByTitle(filmTitle);
        Room room = findRoomByName(roomName);

        if (film == null || room == null) {
            return false; // missing film or room
        }

        // check room availability at screeningTime
        for (Screening s : screenings) {
            if (s.getRoom().equals(room) && s.getScreeningTime().equals(screeningTime)) {
                return false; // room already booked at that time
            }
        }

        // All checks passed – add the screening
        Screening newScreening = new Screening(film, room, screeningTime);
        screenings.add(newScreening);
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * @param filmTitle       the title of the film
     * @param roomName        the name of the room
     * @param screeningTimeStr the scheduled screening time (format {@code yyyy-MM-dd HH:mm:ss})
     * @param currentTimeStr   the current time (format {@code yyyy-MM-dd HH:mm:ss})
     * @return {@code true} if a matching future screening existed and was cancelled,
     *         {@code false} otherwise (screening not found or not in the future)
     */
    public boolean cancelScreening(String filmTitle, String roomName,
                                   String screeningTimeStr, String currentTimeStr) {
        if (filmTitle == null || roomName == null || screeningTimeStr == null || currentTimeStr == null) {
            return false;
        }

        LocalDateTime screeningTime;
        LocalDateTime currentTime;
        try {
            screeningTime = LocalDateTime.parse(screeningTimeStr, FORMATTER);
            currentTime = LocalDateTime.parse(currentTimeStr, FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }

        // screening must be after current time
        if (!screeningTime.isAfter(currentTime)) {
            return false;
        }

        Iterator<Screening> it = screenings.iterator();
        while (it.hasNext()) {
            Screening s = it.next();
            if (s.getFilm().getTitle().equals(filmTitle) &&
                s.getRoom().getName().equals(roomName) &&
                s.getScreeningTime().equals(screeningTime)) {
                it.remove();
                return true; // cancelled
            }
        }
        return false; // not found
    }

    /** Helper: find a room by its name */
    private Room findRoomByName(String name) {
        for (Room r : rooms) {
            if (r.getName().equals(name)) {
                return r;
            }
        }
        return null;
    }

    /** Helper: find a film by its title */
    private Film findFilmByTitle(String title) {
        for (Film f : films) {
            if (f.getTitle().equals(title)) {
                return f;
            }
        }
        return null;
    }
}