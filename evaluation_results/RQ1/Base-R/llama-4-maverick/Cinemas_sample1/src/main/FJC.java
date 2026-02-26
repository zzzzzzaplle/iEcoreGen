import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a film in the cinema system.
 */
class Film {
    private String name;

    /**
     * Constructs a new Film object.
     */
    public Film() {}

    /**
     * Constructs a new Film object with the given name.
     * @param name The name of the film.
     */
    public Film(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the film.
     * @return The name of the film.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the film.
     * @param name The name of the film.
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(name, film.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

/**
 * Represents a room in the cinema.
 */
class Room {
    private String name;
    private List<Screening> screenings;

    /**
     * Constructs a new Room object.
     */
    public Room() {
        this.screenings = new ArrayList<>();
    }

    /**
     * Constructs a new Room object with the given name.
     * @param name The name of the room.
     */
    public Room(String name) {
        this.name = name;
        this.screenings = new ArrayList<>();
    }

    /**
     * Gets the name of the room.
     * @return The name of the room.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the room.
     * @param name The name of the room.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of screenings in the room.
     * @return The list of screenings.
     */
    public List<Screening> getScreenings() {
        return screenings;
    }

    /**
     * Sets the list of screenings in the room.
     * @param screenings The list of screenings.
     */
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    /**
     * Checks if the room is available at a given time.
     * @param time The time to check.
     * @return True if the room is available, false otherwise.
     */
    public boolean isAvailable(LocalDateTime time) {
        for (Screening screening : screenings) {
            if (screening.getTime().equals(time)) {
                return false;
            }
        }
        return true;
    }

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
 * Represents a screening in the cinema.
 */
class Screening {
    private Film film;
    private LocalDateTime time;

    /**
     * Constructs a new Screening object.
     */
    public Screening() {}

    /**
     * Constructs a new Screening object with the given film and time.
     * @param film The film being screened.
     * @param time The time of the screening.
     */
    public Screening(Film film, LocalDateTime time) {
        this.film = film;
        this.time = time;
    }

    /**
     * Gets the film being screened.
     * @return The film.
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Sets the film being screened.
     * @param film The film.
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * Gets the time of the screening.
     * @return The time.
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Sets the time of the screening.
     * @param time The time.
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}

/**
 * Represents a cinema with rooms, films, and screenings.
 */
class Cinema {
    private List<Room> rooms;
    private List<Film> films;

    /**
     * Constructs a new Cinema object.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.films = new ArrayList<>();
    }

    /**
     * Adds a room to the cinema.
     * @param room The room to add.
     * @return True if the room was added, false if it already exists.
     */
    public boolean addRoom(Room room) {
        if (rooms.contains(room)) {
            return false;
        }
        rooms.add(room);
        return true;
    }

    /**
     * Checks if a room is available at a given time.
     * @param roomName The name of the room.
     * @param time The time to check.
     * @return True if the room is available, false otherwise.
     */
    public boolean isRoomAvailable(String roomName, String time) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Room room = getRoomByName(roomName);
            if (room == null) {
                return false;
            }
            return room.isAvailable(dateTime);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Gets a room by its name.
     * @param roomName The name of the room.
     * @return The room, or null if not found.
     */
    private Room getRoomByName(String roomName) {
        for (Room room : rooms) {
            if (room.getName().equals(roomName)) {
                return room;
            }
        }
        return null;
    }

    /**
     * Adds a film to the cinema.
     * @param film The film to add.
     * @return True if the film was added, false if it already exists.
     */
    public boolean addFilm(Film film) {
        if (films.contains(film)) {
            return false;
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film from the cinema and its future screenings.
     * @param filmName The name of the film to remove.
     * @param currentTime The current time.
     * @return True if the film was removed, false if it does not exist.
     */
    public boolean removeFilm(String filmName, String currentTime) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Film film = getFilmByName(filmName);
            if (film == null) {
                return false;
            }
            films.remove(film);
            for (Room room : rooms) {
                room.getScreenings().removeIf(screening -> screening.getFilm().equals(film) && screening.getTime().isAfter(dateTime) || screening.getTime().equals(dateTime));
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Gets a film by its name.
     * @param filmName The name of the film.
     * @return The film, or null if not found.
     */
    private Film getFilmByName(String filmName) {
        for (Film film : films) {
            if (film.getName().equals(filmName)) {
                return film;
            }
        }
        return null;
    }

    /**
     * Assigns a screening to a film and room.
     * @param filmName The name of the film.
     * @param roomName The name of the room.
     * @param screeningTime The time of the screening.
     * @param currentTime The current time.
     * @return True if the screening was assigned, false otherwise.
     */
    public boolean assignScreening(String filmName, String roomName, String screeningTime, String currentTime) {
        try {
            LocalDateTime screeningDateTime = LocalDateTime.parse(screeningTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime currentDateTime = LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (!currentDateTime.isBefore(screeningDateTime)) {
                return false;
            }
            Film film = getFilmByName(filmName);
            if (film == null) {
                return false;
            }
            Room room = getRoomByName(roomName);
            if (room == null || !room.isAvailable(screeningDateTime)) {
                return false;
            }
            Screening screening = new Screening(film, screeningDateTime);
            room.getScreenings().add(screening);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Cancels a future screening.
     * @param filmName The name of the film.
     * @param roomName The name of the room.
     * @param screeningTime The time of the screening.
     * @param currentTime The current time.
     * @return True if the screening was canceled, false otherwise.
     */
    public boolean cancelScreening(String filmName, String roomName, String screeningTime, String currentTime) {
        try {
            LocalDateTime screeningDateTime = LocalDateTime.parse(screeningTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime currentDateTime = LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (!screeningDateTime.isAfter(currentDateTime)) {
                return false;
            }
            Room room = getRoomByName(roomName);
            if (room == null) {
                return false;
            }
            Film film = getFilmByName(filmName);
            if (film == null) {
                return false;
            }
            return room.getScreenings().removeIf(screening -> screening.getFilm().equals(film) && screening.getTime().equals(screeningDateTime));
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}