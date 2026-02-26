import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Cinema {
    private List<Room> rooms;
    private List<Film> films;

    /**
     * Default constructor.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.films = new ArrayList<>();
    }

    /**
     * Adds a room to the cinema if it is not already registered.
     *
     * @param room the room to add
     * @return true if the room was added, false if it already exists
     */
    public boolean addRoom(Room room) {
        if (room == null || rooms.contains(room)) {
            return false;
        }
        rooms.add(room);
        return true;
    }

    /**
     * Checks the availability of a room at a given time.
     *
     * @param room the room to check
     * @param time the time to check in the format "yyyy-MM-dd HH:mm:ss"
     * @return true if the room is available, false otherwise
     */
    public boolean isRoomAvailable(Room room, String time) {
        if (room == null || !rooms.contains(room)) {
            return false;
        }
        LocalDateTime dateTime = parseDateTime(time);
        if (dateTime == null) {
            return false;
        }
        for (Screening screening : room.getScreenings()) {
            if (screening.getScreeningTime().toLocalDateTime().equals(dateTime)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a film to the cinema if it is not already added.
     *
     * @param film the film to add
     * @return true if the film was added, false if it already exists
     */
    public boolean addFilm(Film film) {
        if (film == null || films.contains(film)) {
            return false;
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film from the cinema and its future screenings.
     *
     * @param film the film to remove
     * @param currentTime the current time in the format "yyyy-MM-dd HH:mm:ss"
     * @return true if the film was removed, false otherwise
     */
    public boolean removeFilm(Film film, String currentTime) {
        if (film == null || !films.contains(film)) {
            return false;
        }
        LocalDateTime dateTime = parseDateTime(currentTime);
        if (dateTime == null) {
            return false;
        }
        for (Room room : rooms) {
            room.removeFutureScreenings(film, dateTime);
        }
        films.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific screening time.
     *
     * @param film the film to screen
     * @param room the room to screen in
     * @param screeningTime the screening time in the format "yyyy-MM-dd HH:mm:ss"
     * @param currentTime the current time in the format "yyyy-MM-dd HH:mm:ss"
     * @return true if the screening was assigned, false otherwise
     */
    public boolean assignScreening(Film film, Room room, String screeningTime, String currentTime) {
        if (film == null || room == null || !films.contains(film) || !rooms.contains(room)) {
            return false;
        }
        LocalDateTime screenDateTime = parseDateTime(screeningTime);
        LocalDateTime currentDateTime = parseDateTime(currentTime);
        if (screenDateTime == null || currentDateTime == null || !screenDateTime.isAfter(currentDateTime)) {
            return false;
        }
        if (!isRoomAvailable(room, screeningTime)) {
            return false;
        }
        Screening screening = new Screening(film, room, screenDateTime);
        room.addScreening(screening);
        return true;
    }

    /**
     * Cancels a future screening at a given current time.
     *
     * @param film the film to cancel
     * @param room the room where the screening is scheduled
     * @param screeningTime the screening time in the format "yyyy-MM-dd HH:mm:ss"
     * @param currentTime the current time in the format "yyyy-MM-dd HH:mm:ss"
     * @return true if the screening was canceled, false otherwise
     */
    public boolean cancelScreening(Film film, Room room, String screeningTime, String currentTime) {
        if (film == null || room == null || !films.contains(film) || !rooms.contains(room)) {
            return false;
        }
        LocalDateTime screenDateTime = parseDateTime(screeningTime);
        LocalDateTime currentDateTime = parseDateTime(currentTime);
        if (screenDateTime == null || currentDateTime == null || !screenDateTime.isAfter(currentDateTime)) {
            return false;
        }
        for (Screening screening : room.getScreenings()) {
            if (screening.getFilm().equals(film) && screening.getScreeningTime().toLocalDateTime().equals(screenDateTime)) {
                room.removeScreening(screening);
                return true;
            }
        }
        return false;
    }

    /**
     * Parses a date-time string into a LocalDateTime object.
     *
     * @param dateTimeStr the date-time string in the format "yyyy-MM-dd HH:mm:ss"
     * @return the parsed LocalDateTime object, or null if parsing fails
     */
    private LocalDateTime parseDateTime(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @return the list of rooms
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * @param rooms the list of rooms to set
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * @return the list of films
     */
    public List<Film> getFilms() {
        return films;
    }

    /**
     * @param films the list of films to set
     */
    public void setFilms(List<Film> films) {
        this.films = films;
    }
}

class Room {
    private List<Screening> screenings;

    /**
     * Default constructor.
     */
    public Room() {
        this.screenings = new ArrayList<>();
    }

    /**
     * Adds a screening to the room.
     *
     * @param screening the screening to add
     */
    public void addScreening(Screening screening) {
        if (screening != null) {
            screenings.add(screening);
        }
    }

    /**
     * Removes a screening from the room.
     *
     * @param screening the screening to remove
     */
    public void removeScreening(Screening screening) {
        if (screening != null) {
            screenings.remove(screening);
        }
    }

    /**
     * Removes future screenings of a film from the room.
     *
     * @param film the film whose screenings to remove
     * @param currentTime the current time
     */
    public void removeFutureScreenings(Film film, LocalDateTime currentTime) {
        if (film == null) {
            return;
        }
        screenings.removeIf(screening ->
            screening.getFilm().equals(film) && screening.getScreeningTime().toLocalDateTime().isAfter(currentTime));
    }

    /**
     * @return the list of screenings
     */
    public List<Screening> getScreenings() {
        return screenings;
    }

    /**
     * @param screenings the list of screenings to set
     */
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

class Film {
    private String title;

    /**
     * Default constructor.
     */
    public Film() {
    }

    /**
     * Constructor with title.
     *
     * @param title the title of the film
     */
    public Film(String title) {
        this.title = title;
    }

    /**
     * @return the title of the film
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title of the film to set
     */
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

class Screening {
    private Film film;
    private Room room;
    private LocalDateTime screeningTime;

    /**
     * Default constructor.
     */
    public Screening() {
    }

    /**
     * Constructor with film, room, and screening time.
     *
     * @param film the film to screen
     * @param room the room to screen in
     * @param screeningTime the screening time
     */
    public Screening(Film film, Room room, LocalDateTime screeningTime) {
        this.film = film;
        this.room = room;
        this.screeningTime = screeningTime;
    }

    /**
     * @return the film
     */
    public Film getFilm() {
        return film;
    }

    /**
     * @param film the film to set
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * @return the screening time
     */
    public LocalDateTime getScreeningTime() {
        return screeningTime;
    }

    /**
     * @param screeningTime the screening time to set
     */
    public void setScreeningTime(LocalDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Screening screening = (Screening) o;
        return Objects.equals(film, screening.film) &&
                Objects.equals(room, screening.room) &&
                Objects.equals(screeningTime, screening.screeningTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film, room, screeningTime);
    }
}