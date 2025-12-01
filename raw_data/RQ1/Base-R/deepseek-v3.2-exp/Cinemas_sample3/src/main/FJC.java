import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a cinema with rooms, films, and screenings.
 */
 class Cinema {
    private List<Room> rooms;
    private List<Film> films;
    private List<Screening> screenings;
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Default constructor initializing empty lists for rooms, films, and screenings.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.films = new ArrayList<>();
        this.screenings = new ArrayList<>();
    }

    /**
     * Adds a room to the cinema if it is not already registered.
     * @param room The room to be added
     * @return true if the room was added successfully, false if the room already exists
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
     * @param room The room to check availability for
     * @param timeString The time to check availability at (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the room is available, false if the room is already assigned or inputs are invalid
     * @throws IllegalArgumentException if the time format is invalid
     */
    public boolean checkRoomAvailability(Room room, String timeString) {
        if (!rooms.contains(room)) {
            return false;
        }
        
        try {
            LocalDateTime time = LocalDateTime.parse(timeString, DATE_TIME_FORMATTER);
            
            for (Screening screening : screenings) {
                if (screening.getRoom().equals(room) && screening.getScreeningTime().equals(time)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Adds a film to the cinema if it does not already exist.
     * @param film The film to be added
     * @return true if the film was added successfully, false if the film already exists
     */
    public boolean addFilm(Film film) {
        if (films.contains(film)) {
            return false;
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film from the cinema system and all its future scheduled screenings.
     * @param film The film to be removed
     * @param currentTimeString The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the film was removed successfully, false if it does not exist
     * @throws IllegalArgumentException if the time format is invalid
     */
    public boolean removeFilm(Film film, String currentTimeString) {
        if (!films.contains(film)) {
            return false;
        }
        
        try {
            LocalDateTime currentTime = LocalDateTime.parse(currentTimeString, DATE_TIME_FORMATTER);
            films.remove(film);
            
            screenings.removeIf(screening -> 
                screening.getFilm().equals(film) && 
                !screening.getScreeningTime().isBefore(currentTime)
            );
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Assigns a screening to a film and room at a specific screening time.
     * @param film The film for the screening
     * @param room The room for the screening
     * @param screeningTimeString The screening time (format: yyyy-MM-dd HH:mm:ss)
     * @param currentTimeString The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the screening was assigned successfully, false otherwise
     * @throws IllegalArgumentException if any time format is invalid
     */
    public boolean assignScreening(Film film, Room room, String screeningTimeString, String currentTimeString) {
        try {
            LocalDateTime screeningTime = LocalDateTime.parse(screeningTimeString, DATE_TIME_FORMATTER);
            LocalDateTime currentTime = LocalDateTime.parse(currentTimeString, DATE_TIME_FORMATTER);
            
            if (currentTime.isAfter(screeningTime) || 
                !films.contains(film) || 
                !rooms.contains(room) || 
                !checkRoomAvailability(room, screeningTimeString)) {
                return false;
            }
            
            Screening screening = new Screening(film, room, screeningTime);
            screenings.add(screening);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Cancels a future screening at a given current time.
     * @param screening The screening to cancel
     * @param currentTimeString The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if canceled successfully, false otherwise
     * @throws IllegalArgumentException if the time format is invalid
     */
    public boolean cancelScreening(Screening screening, String currentTimeString) {
        if (!screenings.contains(screening)) {
            return false;
        }
        
        try {
            LocalDateTime currentTime = LocalDateTime.parse(currentTimeString, DATE_TIME_FORMATTER);
            
            if (screening.getScreeningTime().isAfter(currentTime)) {
                screenings.remove(screening);
                return true;
            }
            return false;
        } catch (Exception e) {
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

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }
}

/**
 * Represents a room in a cinema.
 */
class Room {
    private String name;
    private int capacity;

    /**
     * Default constructor.
     */
    public Room() {
    }

    /**
     * Constructor with name and capacity.
     * @param name The name of the room
     * @param capacity The capacity of the room
     */
    public Room(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

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
        return Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

/**
 * Represents a film that can be screened in a cinema.
 */
class Film {
    private String title;
    private int duration; // in minutes
    private String genre;

    /**
     * Default constructor.
     */
    public Film() {
    }

    /**
     * Constructor with title, duration, and genre.
     * @param title The title of the film
     * @param duration The duration of the film in minutes
     * @param genre The genre of the film
     */
    public Film(String title, int duration, String genre) {
        this.title = title;
        this.duration = duration;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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
 * Represents a screening of a film in a room at a specific time.
 */
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
     * @param film The film being screened
     * @param room The room where the screening takes place
     * @param screeningTime The time when the screening occurs
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
        return Objects.equals(film, screening.film) && 
               Objects.equals(room, screening.room) && 
               Objects.equals(screeningTime, screening.screeningTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film, room, screeningTime);
    }
}