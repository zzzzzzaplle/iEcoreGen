import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a cinema with rooms, films, and screenings.
 */
 class Cinema {
    private List<Room> rooms;
    private List<Film> films;
    private List<Screening> screenings;

    /**
     * Constructs a new Cinema with empty lists of rooms, films, and screenings.
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
        if (room == null || rooms.contains(room)) {
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
     * @return true if the room is available, false if the room is already assigned or inputs are invalid
     */
    public boolean checkRoomAvailability(Room room, String timeString) {
        if (room == null || timeString == null || !rooms.contains(room)) {
            return false;
        }
        
        LocalDateTime time;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            time = LocalDateTime.parse(timeString, formatter);
        } catch (DateTimeParseException e) {
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
     * 
     * @param film the film to add
     * @return true if the film was added successfully, false if it already exists
     */
    public boolean addFilm(Film film) {
        if (film == null || films.contains(film)) {
            return false;
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film from the cinema system and all its future screenings.
     * 
     * @param film the film to remove
     * @param currentTimeString the current time in format "yyyy-MM-dd HH:mm:ss"
     * @return true if the film was removed successfully, false if it doesn't exist
     */
    public boolean removeFilm(Film film, String currentTimeString) {
        if (film == null || currentTimeString == null || !films.contains(film)) {
            return false;
        }
        
        LocalDateTime currentTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            currentTime = LocalDateTime.parse(currentTimeString, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        
        films.remove(film);
        
        List<Screening> screeningsToRemove = new ArrayList<>();
        for (Screening screening : screenings) {
            if (screening.getFilm().equals(film) && 
                !screening.getScreeningTime().isBefore(currentTime)) {
                screeningsToRemove.add(screening);
            }
        }
        
        screenings.removeAll(screeningsToRemove);
        return true;
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
        if (film == null || room == null || screeningTimeString == null || currentTimeString == null) {
            return false;
        }
        
        if (!films.contains(film) || !rooms.contains(room)) {
            return false;
        }
        
        LocalDateTime screeningTime;
        LocalDateTime currentTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            screeningTime = LocalDateTime.parse(screeningTimeString, formatter);
            currentTime = LocalDateTime.parse(currentTimeString, formatter);
        } catch (DateTimeParseException e) {
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
     * Cancels a future screening.
     * 
     * @param screening the screening to cancel
     * @param currentTimeString the current time in format "yyyy-MM-dd HH:mm:ss"
     * @return true if the screening was canceled successfully, false otherwise
     */
    public boolean cancelScreening(Screening screening, String currentTimeString) {
        if (screening == null || currentTimeString == null || !screenings.contains(screening)) {
            return false;
        }
        
        LocalDateTime currentTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            currentTime = LocalDateTime.parse(currentTimeString, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        
        if (!screening.getScreeningTime().isAfter(currentTime)) {
            return false;
        }
        
        screenings.remove(screening);
        return true;
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
     * Constructs a new Room with default values.
     */
    public Room() {
    }

    /**
     * Constructs a new Room with specified name and capacity.
     * 
     * @param name the name of the room
     * @param capacity the capacity of the room
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room room = (Room) obj;
        return name != null ? name.equals(room.name) : room.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

/**
 * Represents a film that can be screened in a cinema.
 */
 class Film {
    private String title;
    private int duration; // in minutes

    /**
     * Constructs a new Film with default values.
     */
    public Film() {
    }

    /**
     * Constructs a new Film with specified title and duration.
     * 
     * @param title the title of the film
     * @param duration the duration of the film in minutes
     */
    public Film(String title, int duration) {
        this.title = title;
        this.duration = duration;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Film film = (Film) obj;
        return title != null ? title.equals(film.title) : film.title == null;
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
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
     * Constructs a new Screening with default values.
     */
    public Screening() {
    }

    /**
     * Constructs a new Screening with specified film, room, and screening time.
     * 
     * @param film the film being screened
     * @param room the room where the screening takes place
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Screening screening = (Screening) obj;
        if (film != null ? !film.equals(screening.film) : screening.film != null) return false;
        if (room != null ? !room.equals(screening.room) : screening.room != null) return false;
        return screeningTime != null ? screeningTime.equals(screening.screeningTime) : screening.screeningTime == null;
    }

    @Override
    public int hashCode() {
        int result = film != null ? film.hashCode() : 0;
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + (screeningTime != null ? screeningTime.hashCode() : 0);
        return result;
    }
}