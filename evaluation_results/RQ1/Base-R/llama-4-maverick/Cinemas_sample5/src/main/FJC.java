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
 * Represents a screening of a film in a room at a specific time.
 */
class Screening {
    private Film film;
    private LocalDateTime screeningTime;

    /**
     * Constructs a new Screening object.
     */
    public Screening() {}

    /**
     * Constructs a new Screening object with the given film and screening time.
     * @param film The film being screened.
     * @param screeningTime The time of the screening.
     */
    public Screening(Film film, LocalDateTime screeningTime) {
        this.film = film;
        this.screeningTime = screeningTime;
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
     * Gets the screening time.
     * @return The screening time.
     */
    public LocalDateTime getScreeningTime() {
        return screeningTime;
    }

    /**
     * Sets the screening time.
     * @param screeningTime The screening time.
     */
    public void setScreeningTime(LocalDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Screening screening = (Screening) o;
        return Objects.equals(film, screening.film) && Objects.equals(screeningTime, screening.screeningTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film, screeningTime);
    }
}

/**
 * Represents a cinema with multiple rooms and films.
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
     * Adds a room to the cinema if it does not already exist.
     * @param room The room to be added.
     * @return True if the room is added successfully, false otherwise.
     */
    public boolean addRoom(Room room) {
        if (rooms.contains(room)) {
            return false;
        }
        return rooms.add(room);
    }

    /**
     * Checks if a room is available at a given time.
     * @param room The room to check.
     * @param time The time to check.
     * @return True if the room is available, false otherwise.
     */
    public boolean isRoomAvailable(Room room, String time) {
        try {
            LocalDateTime screeningTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (!rooms.contains(room)) {
                return false;
            }
            for (Screening screening : room.getScreenings()) {
                if (screening.getScreeningTime().equals(screeningTime)) {
                    return false;
                }
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Adds a film to the cinema if it does not already exist.
     * @param film The film to be added.
     * @return True if the film is added successfully, false otherwise.
     */
    public boolean addFilm(Film film) {
        if (films.contains(film)) {
            return false;
        }
        return films.add(film);
    }

    /**
     * Removes a film from the cinema and its future screenings.
     * @param film The film to be removed.
     * @param currentTime The current time.
     * @return True if the film is removed successfully, false otherwise.
     */
    public boolean removeFilm(Film film, String currentTime) {
        try {
            LocalDateTime currentTimeParsed = LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (!films.contains(film)) {
                return false;
            }
            films.remove(film);
            for (Room room : rooms) {
                room.getScreenings().removeIf(screening -> screening.getFilm().equals(film) && screening.getScreeningTime().isAfter(currentTimeParsed) || screening.getScreeningTime().equals(currentTimeParsed));
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     * @param film The film to be screened.
     * @param room The room where the screening will take place.
     * @param screeningTime The time of the screening.
     * @param currentTime The current time.
     * @return True if the screening is assigned successfully, false otherwise.
     */
    public boolean assignScreening(Film film, Room room, String screeningTime, String currentTime) {
        try {
            LocalDateTime screeningTimeParsed = LocalDateTime.parse(screeningTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime currentTimeParsed = LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (!films.contains(film) || !rooms.contains(room) || !isRoomAvailable(room, screeningTime) || screeningTimeParsed.isBefore(currentTimeParsed) || screeningTimeParsed.equals(currentTimeParsed)) {
                return false;
            }
            room.getScreenings().add(new Screening(film, screeningTimeParsed));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Cancels a future screening.
     * @param film The film being screened.
     * @param room The room where the screening is taking place.
     * @param screeningTime The time of the screening.
     * @param currentTime The current time.
     * @return True if the screening is canceled successfully, false otherwise.
     */
    public boolean cancelScreening(Film film, Room room, String screeningTime, String currentTime) {
        try {
            LocalDateTime screeningTimeParsed = LocalDateTime.parse(screeningTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime currentTimeParsed = LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (!films.contains(film) || !rooms.contains(room) || screeningTimeParsed.isBefore(currentTimeParsed)) {
                return false;
            }
            return room.getScreenings().removeIf(screening -> screening.getFilm().equals(film) && screening.getScreeningTime().equals(screeningTimeParsed));
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Gets the list of rooms in the cinema.
     * @return The list of rooms.
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Sets the list of rooms in the cinema.
     * @param rooms The list of rooms.
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Gets the list of films in the cinema.
     * @return The list of films.
     */
    public List<Film> getFilms() {
        return films;
    }

    /**
     * Sets the list of films in the cinema.
     * @param films The list of films.
     */
    public void setFilms(List<Film> films) {
        this.films = films;
    }
}