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

    /**
     * Default constructor initializing empty lists for rooms, films, and screenings.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.films = new ArrayList<>();
        this.screenings = new ArrayList<>();
    }

    /**
     * Adds a room to the cinema if it doesn't already exist.
     * @param room the room to add
     * @return true if room was added successfully, false if room already exists
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
     * @param room the room to check availability for
     * @param timeString the time to check in format "yyyy-MM-dd HH:mm:ss"
     * @return true if room is available, false if room is already assigned or inputs are invalid
     */
    public boolean checkRoomAvailability(Room room, String timeString) {
        if (!rooms.contains(room)) {
            return false;
        }
        
        LocalDateTime time;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            time = LocalDateTime.parse(timeString, formatter);
        } catch (Exception e) {
            return false;
        }
        
        for (Screening screening : screenings) {
            if (screening.getRoom().equals(room) && screening.getScreeningTime().equals(time)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a film to the cinema if it doesn't already exist.
     * @param film the film to add
     * @return true if film was added successfully, false if film already exists
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
     * @param film the film to remove
     * @param currentTimeString the current time in format "yyyy-MM-dd HH:mm:ss"
     * @return true if film was removed successfully, false if film doesn't exist
     */
    public boolean removeFilm(Film film, String currentTimeString) {
        if (!films.contains(film)) {
            return false;
        }
        
        LocalDateTime currentTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            currentTime = LocalDateTime.parse(currentTimeString, formatter);
        } catch (Exception e) {
            return false;
        }
        
        films.remove(film);
        
        screenings.removeIf(screening -> 
            screening.getFilm().equals(film) && 
            !screening.getScreeningTime().isBefore(currentTime)
        );
        
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     * @param film the film for the screening
     * @param room the room for the screening
     * @param screeningTimeString the screening time in format "yyyy-MM-dd HH:mm:ss"
     * @param currentTimeString the current time in format "yyyy-MM-dd HH:mm:ss"
     * @return true if screening was assigned successfully, false otherwise
     */
    public boolean assignScreening(Film film, Room room, String screeningTimeString, String currentTimeString) {
        if (!films.contains(film) || !rooms.contains(room)) {
            return false;
        }
        
        LocalDateTime screeningTime;
        LocalDateTime currentTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            screeningTime = LocalDateTime.parse(screeningTimeString, formatter);
            currentTime = LocalDateTime.parse(currentTimeString, formatter);
        } catch (Exception e) {
            return false;
        }
        
        if (!currentTime.isBefore(screeningTime)) {
            return false;
        }
        
        if (!checkRoomAvailability(room, screeningTimeString)) {
            return false;
        }
        
        Screening screening = new Screening(film, room, screeningTime);
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening at the given current time.
     * @param screening the screening to cancel
     * @param currentTimeString the current time in format "yyyy-MM-dd HH:mm:ss"
     * @return true if screening was canceled successfully, false otherwise
     */
    public boolean cancelScreening(Screening screening, String currentTimeString) {
        if (!screenings.contains(screening)) {
            return false;
        }
        
        LocalDateTime currentTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            currentTime = LocalDateTime.parse(currentTimeString, formatter);
        } catch (Exception e) {
            return false;
        }
        
        if (!screening.getScreeningTime().isAfter(currentTime)) {
            return false;
        }
        
        screenings.remove(screening);
        return true;
    }

    // Getters and setters
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
     * @param name the room name
     * @param capacity the room capacity
     */
    public Room(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    /**
     * Compares this room to another object for equality.
     * @param o the object to compare with
     * @return true if the rooms are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(name, room.name);
    }

    /**
     * Generates hash code for the room.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // Getters and setters
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
 * Represents a film in the cinema system.
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
     * @param title the film title
     * @param duration the film duration in minutes
     * @param genre the film genre
     */
    public Film(String title, int duration, String genre) {
        this.title = title;
        this.duration = duration;
        this.genre = genre;
    }

    /**
     * Compares this film to another object for equality.
     * @param o the object to compare with
     * @return true if the films are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(title, film.title);
    }

    /**
     * Generates hash code for the film.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    // Getters and setters
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
     * @param film the film being screened
     * @param room the room where the screening takes place
     * @param screeningTime the time of the screening
     */
    public Screening(Film film, Room room, LocalDateTime screeningTime) {
        this.film = film;
        this.room = room;
        this.screeningTime = screeningTime;
    }

    /**
     * Compares this screening to another object for equality.
     * @param o the object to compare with
     * @return true if the screenings are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Screening screening = (Screening) o;
        return Objects.equals(film, screening.film) && 
               Objects.equals(room, screening.room) && 
               Objects.equals(screeningTime, screening.screeningTime);
    }

    /**
     * Generates hash code for the screening.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(film, room, screeningTime);
    }

    // Getters and setters
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