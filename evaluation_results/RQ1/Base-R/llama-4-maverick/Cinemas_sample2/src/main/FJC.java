import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     * Adds a room to the cinema.
     * 
     * @param room the room to be added
     * @return true if the room is added successfully, false if the room already exists
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
     * 
     * @param room the room to be checked
     * @param time the time to check availability
     * @return true if the room is available, false otherwise
     */
    public boolean isRoomAvailable(Room room, String time) {
        if (!rooms.contains(room)) {
            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime screeningTime = LocalDateTime.parse(time, formatter);
            return room.isAvailable(screeningTime);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Adds a film to the cinema.
     * 
     * @param film the film to be added
     * @return true if the film is added successfully, false if the film already exists
     */
    public boolean addFilm(Film film) {
        if (films.contains(film)) {
            return false;
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film from the cinema.
     * 
     * @param film        the film to be removed
     * @param currentTime the current time
     * @return true if the film is removed successfully, false if the film does not exist
     */
    public boolean removeFilm(Film film, String currentTime) {
        if (!films.contains(film)) {
            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime currentTimeParsed = LocalDateTime.parse(currentTime, formatter);
            films.remove(film);
            for (Room room : rooms) {
                room.removeFutureScreenings(film, currentTimeParsed);
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Assigns a screening to a film and room.
     * 
     * @param film         the film to be screened
     * @param room         the room where the screening will take place
     * @param screeningTime the time of the screening
     * @param currentTime   the current time
     * @return true if the screening is assigned successfully, false otherwise
     */
    public boolean assignScreening(Film film, Room room, String screeningTime, String currentTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime screeningTimeParsed = LocalDateTime.parse(screeningTime, formatter);
            LocalDateTime currentTimeParsed = LocalDateTime.parse(currentTime, formatter);
            if (currentTimeParsed.isAfter(screeningTimeParsed) || !films.contains(film) || !rooms.contains(room)) {
                return false;
            }
            return room.assignScreening(film, screeningTimeParsed);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Cancels a future screening.
     * 
     * @param screening   the screening to be canceled
     * @param currentTime the current time
     * @return true if the screening is canceled successfully, false otherwise
     */
    public boolean cancelScreening(Screening screening, String currentTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime currentTimeParsed = LocalDateTime.parse(currentTime, formatter);
            if (screening.getScreeningTime().isBefore(currentTimeParsed) || !screening.getRoom().getScreenings().contains(screening)) {
                return false;
            }
            screening.getRoom().cancelScreening(screening);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}

/**
 * Represents a room in the cinema.
 */
class Room {
    private List<Screening> screenings;

    /**
     * Constructs a new Room object.
     */
    public Room() {
        this.screenings = new ArrayList<>();
    }

    /**
     * Checks if the room is available at a given time.
     * 
     * @param time the time to check availability
     * @return true if the room is available, false otherwise
     */
    public boolean isAvailable(LocalDateTime time) {
        for (Screening screening : screenings) {
            if (screening.getScreeningTime().equals(time)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Assigns a screening to the room.
     * 
     * @param film  the film to be screened
     * @param time  the time of the screening
     * @return true if the screening is assigned successfully, false otherwise
     */
    public boolean assignScreening(Film film, LocalDateTime time) {
        if (!isAvailable(time)) {
            return false;
        }
        Screening screening = new Screening(film, this, time);
        screenings.add(screening);
        return true;
    }

    /**
     * Removes future screenings of a film.
     * 
     * @param film        the film whose screenings are to be removed
     * @param currentTime the current time
     */
    public void removeFutureScreenings(Film film, LocalDateTime currentTime) {
        screenings.removeIf(screening -> screening.getFilm().equals(film) && screening.getScreeningTime().isAfter(currentTime) || screening.getScreeningTime().equals(currentTime));
    }

    /**
     * Cancels a screening.
     * 
     * @param screening the screening to be canceled
     */
    public void cancelScreening(Screening screening) {
        screenings.remove(screening);
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(screenings, room.screenings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(screenings);
    }
}

/**
 * Represents a film.
 */
class Film {
    private String name;

    /**
     * Constructs a new Film object.
     */
    public Film() {
    }

    /**
     * Constructs a new Film object with a given name.
     * 
     * @param name the name of the film
     */
    public Film(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

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
 * Represents a screening of a film.
 */
class Screening {
    private Film film;
    private Room room;
    private LocalDateTime screeningTime;

    /**
     * Constructs a new Screening object.
     */
    public Screening() {
    }

    /**
     * Constructs a new Screening object with a given film, room, and screening time.
     * 
     * @param film         the film to be screened
     * @param room         the room where the screening will take place
     * @param screeningTime the time of the screening
     */
    public Screening(Film film, Room room, LocalDateTime screeningTime) {
        this.film = film;
        this.room = room;
        this.screeningTime = screeningTime;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Screening screening = (Screening) o;
        return Objects.equals(film, screening.film) && Objects.equals(room, screening.room) && Objects.equals(screeningTime, screening.screeningTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film, room, screeningTime);
    }
}