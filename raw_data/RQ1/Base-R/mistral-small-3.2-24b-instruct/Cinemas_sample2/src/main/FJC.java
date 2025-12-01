import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class Cinema {
    private List<Room> rooms;
    private List<Film> films;
    private Map<LocalDateTime, Screening> screenings;

    /**
     * Default constructor for the Cinema class.
     * Initializes empty lists for rooms, films, and a map for screenings.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.films = new ArrayList<>();
        this.screenings = new HashMap<>();
    }

    /**
     * Adds a room to the cinema.
     *
     * @param room The room to be added.
     * @return true if the room was added successfully, false if the room already exists.
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
     * @param room The room to check.
     * @param time The time to check availability.
     * @return true if the room is available at the given time, false otherwise.
     */
    public boolean checkRoomAvailability(Room room, LocalDateTime time) {
        if (room == null || time == null || !rooms.contains(room)) {
            return false;
        }
        for (Screening screening : screenings.values()) {
            if (screening.getRoom().equals(room) && screening.getScreeningTime().equals(time)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a film to the cinema.
     *
     * @param film The film to be added.
     * @return true if the film was added successfully, false if the film already exists.
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
     * @param film The film to be removed.
     * @param currentTime The current time.
     * @return true if the film was removed successfully, false if the film does not exist.
     */
    public boolean removeFilm(Film film, LocalDateTime currentTime) {
        if (film == null || !films.contains(film)) {
            return false;
        }
        List<Screening> screeningsToRemove = new ArrayList<>();
        for (Screening screening : screenings.values()) {
            if (screening.getFilm().equals(film) && screening.getScreeningTime().isAfter(currentTime)) {
                screeningsToRemove.add(screening);
            }
        }
        screeningsToRemove.forEach(screening -> screenings.remove(screening.getScreeningTime()));
        films.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     *
     * @param film The film to be screened.
     * @param room The room for the screening.
     * @param screeningTime The time of the screening.
     * @param currentTime The current time.
     * @return true if the screening was assigned successfully, false otherwise.
     */
    public boolean assignScreening(Film film, Room room, LocalDateTime screeningTime, LocalDateTime currentTime) {
        if (film == null || room == null || screeningTime == null || currentTime == null
                || !films.contains(film) || !rooms.contains(room)
                || screeningTime.isBefore(currentTime) || !checkRoomAvailability(room, screeningTime)) {
            return false;
        }
        Screening screening = new Screening(film, room, screeningTime);
        screenings.put(screeningTime, screening);
        return true;
    }

    /**
     * Cancels a future screening at a given current time.
     *
     * @param screeningTime The time of the screening to be canceled.
     * @param currentTime The current time.
     * @return true if the screening was canceled successfully, false otherwise.
     */
    public boolean cancelScreening(LocalDateTime screeningTime, LocalDateTime currentTime) {
        if (screeningTime == null || currentTime == null || !screenings.containsKey(screeningTime)
                || !screenings.get(screeningTime).getScreeningTime().isAfter(currentTime)) {
            return false;
        }
        screenings.remove(screeningTime);
        return true;
    }

    // Getters and Setters
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

    public Map<LocalDateTime, Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(Map<LocalDateTime, Screening> screenings) {
        this.screenings = screenings;
    }
}

class Room {
    private String name;
    private int capacity;

    /**
     * Default constructor for the Room class.
     */
    public Room() {
    }

    /**
     * Constructor for the Room class with name and capacity.
     *
     * @param name The name of the room.
     * @param capacity The capacity of the room.
     */
    public Room(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    // Getters and Setters
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return capacity == room.capacity && Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capacity);
    }
}

class Film {
    private String title;
    private String genre;
    private int duration;

    /**
     * Default constructor for the Film class.
     */
    public Film() {
    }

    /**
     * Constructor for the Film class with title, genre, and duration.
     *
     * @param title The title of the film.
     * @param genre The genre of the film.
     * @param duration The duration of the film in minutes.
     */
    public Film(String title, String genre, int duration) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return duration == film.duration && Objects.equals(title, film.title) && Objects.equals(genre, film.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, duration);
    }
}

class Screening {
    private Film film;
    private Room room;
    private LocalDateTime screeningTime;

    /**
     * Default constructor for the Screening class.
     */
    public Screening() {
    }

    /**
     * Constructor for the Screening class with film, room, and screening time.
     *
     * @param film The film to be screened.
     * @param room The room for the screening.
     * @param screeningTime The time of the screening.
     */
    public Screening(Film film, Room room, LocalDateTime screeningTime) {
        this.film = film;
        this.room = room;
        this.screeningTime = screeningTime;
    }

    // Getters and Setters
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