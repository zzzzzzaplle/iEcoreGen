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
     * @return true if the room is added successfully, false if the room already exists.
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
     * @param room the room to check.
     * @param time the time to check in the format "yyyy-MM-dd HH:mm:ss".
     * @return true if the room is available, false otherwise.
     */
    public boolean isRoomAvailable(Room room, String time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime screeningTime = LocalDateTime.parse(time, formatter);
            if (!rooms.contains(room)) {
                return false;
            }
            return room.isAvailable(screeningTime);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Adds a film to the cinema.
     * 
     * @return true if the film is added successfully, false if the film already exists.
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
     * 
     * @param film the film to remove.
     * @param currentTime the current time in the format "yyyy-MM-dd HH:mm:ss".
     * @return true if the film is removed successfully, false if the film does not exist.
     */
    public boolean removeFilm(Film film, String currentTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime currentTimeObj = LocalDateTime.parse(currentTime, formatter);
            if (!films.contains(film)) {
                return false;
            }
            films.remove(film);
            for (Room room : rooms) {
                room.removeFutureScreenings(film, currentTimeObj);
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Assigns a screening to a film and room.
     * 
     * @param film the film to screen.
     * @param room the room to screen in.
     * @param screeningTime the screening time in the format "yyyy-MM-dd HH:mm:ss".
     * @param currentTime the current time in the format "yyyy-MM-dd HH:mm:ss".
     * @return true if the screening is assigned successfully, false otherwise.
     */
    public boolean assignScreening(Film film, Room room, String screeningTime, String currentTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime screeningTimeObj = LocalDateTime.parse(screeningTime, formatter);
            LocalDateTime currentTimeObj = LocalDateTime.parse(currentTime, formatter);
            if (!films.contains(film) || !rooms.contains(room) || screeningTimeObj.isBefore(currentTimeObj) || !room.isAvailable(screeningTimeObj)) {
                return false;
            }
            room.addScreening(new Screening(film, screeningTimeObj));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Cancels a future screening.
     * 
     * @param screening the screening to cancel.
     * @param currentTime the current time in the format "yyyy-MM-dd HH:mm:ss".
     * @return true if the screening is canceled successfully, false otherwise.
     */
    public boolean cancelScreening(Screening screening, String currentTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime currentTimeObj = LocalDateTime.parse(currentTime, formatter);
            if (screening.getScreeningTime().isBefore(currentTimeObj)) {
                return false;
            }
            for (Room room : rooms) {
                if (room.cancelScreening(screening)) {
                    return true;
                }
            }
            return false;
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
    private String name;
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
     * @param time the time to check.
     * @return true if the room is available, false otherwise.
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
     * Adds a screening to the room.
     * 
     * @param screening the screening to add.
     */
    public void addScreening(Screening screening) {
        screenings.add(screening);
    }

    /**
     * Removes future screenings of a film.
     * 
     * @param film the film to remove screenings for.
     * @param currentTime the current time.
     */
    public void removeFutureScreenings(Film film, LocalDateTime currentTime) {
        screenings.removeIf(screening -> screening.getFilm().equals(film) && screening.getScreeningTime().isAfter(currentTime) || screening.getScreeningTime().equals(currentTime));
    }

    /**
     * Cancels a screening.
     * 
     * @param screening the screening to cancel.
     * @return true if the screening is canceled successfully, false otherwise.
     */
    public boolean cancelScreening(Screening screening) {
        return screenings.remove(screening);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    private String title;

    /**
     * Constructs a new Film object.
     */
    public Film() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
 * Represents a screening of a film.
 */
class Screening {
    private Film film;
    private LocalDateTime screeningTime;

    /**
     * Constructs a new Screening object.
     * 
     * @param film the film to screen.
     * @param screeningTime the time of the screening.
     */
    public Screening(Film film, LocalDateTime screeningTime) {
        this.film = film;
        this.screeningTime = screeningTime;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
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
        return Objects.equals(film, screening.film) && Objects.equals(screeningTime, screening.screeningTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film, screeningTime);
    }
}