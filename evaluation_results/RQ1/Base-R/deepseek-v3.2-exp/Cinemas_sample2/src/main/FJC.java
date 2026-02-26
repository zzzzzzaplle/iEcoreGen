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
     * Default constructor initializes empty lists for rooms, films, and screenings.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.films = new ArrayList<>();
        this.screenings = new ArrayList<>();
    }

    /**
     * Adds a room to the cinema if it doesn't already exist.
     * 
     * @param room the room to add
     * @return true if the room was added successfully, false if it already exists
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
     * @param room the room to check availability for
     * @param timeString the time to check in format "yyyy-MM-dd HH:mm:ss"
     * @return true if the room is available, false if already assigned or inputs invalid
     */
    public boolean checkRoomAvailability(Room room, String timeString) {
        if (!rooms.contains(room)) {
            return false;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime checkTime = LocalDateTime.parse(timeString, formatter);
            
            for (Screening screening : screenings) {
                if (screening.getRoom().equals(room) && screening.getScreeningTime().equals(checkTime)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Adds a film to the cinema if it doesn't already exist.
     * 
     * @param film the film to add
     * @return true if the film was added successfully, false if it already exists
     */
    public boolean addFilm(Film film) {
        if (films.contains(film)) {
            return false;
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film from the cinema and cancels all its future screenings.
     * 
     * @param film the film to remove
     * @param currentTimeString the current time in format "yyyy-MM-dd HH:mm:ss"
     * @return true if the film was removed successfully, false if it doesn't exist
     */
    public boolean removeFilm(Film film, String currentTimeString) {
        if (!films.contains(film)) {
            return false;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime currentTime = LocalDateTime.parse(currentTimeString, formatter);
            
            // Remove future screenings for this film
            screenings.removeIf(screening -> 
                screening.getFilm().equals(film) && 
                !screening.getScreeningTime().isBefore(currentTime)
            );
            
            films.remove(film);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     * 
     * @param film the film for the screening
     * @param room the room for the screening
     * @param screeningTimeString the screening time in format "yyyy-MM-dd HH:mm:ss"
     * @param currentTimeString the current time in format "yyyy-MM-dd HH:mm:ss"
     * @return true if the screening was assigned successfully, false otherwise
     */
    public boolean assignScreening(Film film, Room room, String screeningTimeString, String currentTimeString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime screeningTime = LocalDateTime.parse(screeningTimeString, formatter);
            LocalDateTime currentTime = LocalDateTime.parse(currentTimeString, formatter);
            
            // Verify current time is before screening time
            if (!currentTime.isBefore(screeningTime)) {
                return false;
            }
            
            // Verify film exists in cinema
            if (!films.contains(film)) {
                return false;
            }
            
            // Verify room availability
            if (!checkRoomAvailability(room, screeningTimeString)) {
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
     * Cancels a future screening.
     * 
     * @param screening the screening to cancel
     * @param currentTimeString the current time in format "yyyy-MM-dd HH:mm:ss"
     * @return true if the screening was canceled successfully, false otherwise
     */
    public boolean cancelScreening(Screening screening, String currentTimeString) {
        if (!screenings.contains(screening)) {
            return false;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime currentTime = LocalDateTime.parse(currentTimeString, formatter);
            
            // Verify screening time is after current time
            if (!screening.getScreeningTime().isAfter(currentTime)) {
                return false;
            }
            
            screenings.remove(screening);
            return true;
        } catch (Exception e) {
            return false;
        }
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
 * Represents a room in the cinema.
 */
class Room {
    private String roomId;
    private int capacity;
    private String roomType;

    /**
     * Default constructor.
     */
    public Room() {
    }

    /**
     * Constructor with room ID.
     * 
     * @param roomId the unique identifier for the room
     */
    public Room(String roomId) {
        this.roomId = roomId;
    }

    /**
     * Constructor with all fields.
     * 
     * @param roomId the unique identifier for the room
     * @param capacity the seating capacity of the room
     * @param roomType the type of room (e.g., standard, IMAX, VIP)
     */
    public Room(String roomId, int capacity, String roomType) {
        this.roomId = roomId;
        this.capacity = capacity;
        this.roomType = roomType;
    }

    // Getters and setters
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomId, room.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", capacity=" + capacity +
                ", roomType='" + roomType + '\'' +
                '}';
    }
}

/**
 * Represents a film that can be shown in the cinema.
 */
class Film {
    private String filmId;
    private String title;
    private int duration; // in minutes
    private String genre;
    private String rating;

    /**
     * Default constructor.
     */
    public Film() {
    }

    /**
     * Constructor with film ID and title.
     * 
     * @param filmId the unique identifier for the film
     * @param title the title of the film
     */
    public Film(String filmId, String title) {
        this.filmId = filmId;
        this.title = title;
    }

    /**
     * Constructor with all fields.
     * 
     * @param filmId the unique identifier for the film
     * @param title the title of the film
     * @param duration the duration of the film in minutes
     * @param genre the genre of the film
     * @param rating the rating of the film (e.g., PG, PG-13, R)
     */
    public Film(String filmId, String title, int duration, String genre, String rating) {
        this.filmId = filmId;
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.rating = rating;
    }

    // Getters and setters
    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(filmId, film.filmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId);
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmId='" + filmId + '\'' +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", genre='" + genre + '\'' +
                ", rating='" + rating + '\'' +
                '}';
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
     * Constructor with all fields.
     * 
     * @param film the film being screened
     * @param room the room where the screening takes place
     * @param screeningTime the date and time of the screening
     */
    public Screening(Film film, Room room, LocalDateTime screeningTime) {
        this.film = film;
        this.room = room;
        this.screeningTime = screeningTime;
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

    @Override
    public String toString() {
        return "Screening{" +
                "film=" + film +
                ", room=" + room +
                ", screeningTime=" + screeningTime +
                '}';
    }
}