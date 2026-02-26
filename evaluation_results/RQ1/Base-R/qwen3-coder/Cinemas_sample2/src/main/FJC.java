import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a cinema which contains rooms and films.
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
     * @param room The room to be added
     * @return true if the room was added, false if it already exists
     */
    public boolean addRoom(Room room) {
        if (room == null) {
            return false;
        }
        
        if (!rooms.contains(room)) {
            rooms.add(room);
            return true;
        }
        return false;
    }

    /**
     * Checks if a room is available at a given time.
     *
     * @param room The room to check
     * @param timeStr The time to check in format yyyy-MM-dd HH:mm:ss
     * @return true if the room is available, false otherwise
     */
    public boolean isRoomAvailable(Room room, String timeStr) {
        if (room == null || timeStr == null || !rooms.contains(room)) {
            return false;
        }

        try {
            LocalDateTime time = LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            for (Screening screening : screenings) {
                if (screening.getRoom().equals(room) && 
                    screening.getScreeningTime().equals(time)) {
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
     * @param film The film to be added
     * @return true if the film was added, false if it already exists
     */
    public boolean addFilm(Film film) {
        if (film == null) {
            return false;
        }
        
        if (!films.contains(film)) {
            films.add(film);
            return true;
        }
        return false;
    }

    /**
     * Removes a film from the cinema and all its future screenings.
     *
     * @param film The film to be removed
     * @param currentTimeStr The current time in format yyyy-MM-dd HH:mm:ss
     * @return true if the film was removed successfully, false otherwise
     */
    public boolean removeFilm(Film film, String currentTimeStr) {
        if (film == null || !films.contains(film) || currentTimeStr == null) {
            return false;
        }

        try {
            LocalDateTime currentTime = LocalDateTime.parse(currentTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            // Remove all future screenings of this film
            screenings.removeIf(screening -> 
                screening.getFilm().equals(film) && 
                !screening.getScreeningTime().isBefore(currentTime));
            
            // Remove the film
            films.remove(film);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     *
     * @param film The film to be screened
     * @param room The room for the screening
     * @param screeningTimeStr The time of the screening in format yyyy-MM-dd HH:mm:ss
     * @param currentTimeStr The current time in format yyyy-MM-dd HH:mm:ss
     * @return true if the screening was assigned successfully, false otherwise
     */
    public boolean assignScreening(Film film, Room room, String screeningTimeStr, String currentTimeStr) {
        if (film == null || room == null || screeningTimeStr == null || currentTimeStr == null) {
            return false;
        }
        
        if (!films.contains(film) || !rooms.contains(room)) {
            return false;
        }

        try {
            LocalDateTime screeningTime = LocalDateTime.parse(screeningTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime currentTime = LocalDateTime.parse(currentTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            // Check if current time is before screening time
            if (!currentTime.isBefore(screeningTime)) {
                return false;
            }
            
            // Check room availability
            if (!isRoomAvailable(room, screeningTimeStr)) {
                return false;
            }
            
            // Assign the screening
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
     * @param screening The screening to be canceled
     * @param currentTimeStr The current time in format yyyy-MM-dd HH:mm:ss
     * @return true if the screening was canceled successfully, false otherwise
     */
    public boolean cancelScreening(Screening screening, String currentTimeStr) {
        if (screening == null || currentTimeStr == null || !screenings.contains(screening)) {
            return false;
        }

        try {
            LocalDateTime currentTime = LocalDateTime.parse(currentTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            // Check if screening time is after current time
            if (!screening.getScreeningTime().isAfter(currentTime)) {
                return false;
            }
            
            // Cancel the screening
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
 * Represents a room in a cinema.
 */
class Room {
    private String name;

    /**
     * Constructs a new Room with default values.
     */
    public Room() {
        this.name = "";
    }

    /**
     * Constructs a new Room with the specified name.
     *
     * @param name The name of the room
     */
    public Room(String name) {
        this.name = name;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room room = (Room) obj;
        return name.equals(room.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

/**
 * Represents a film shown in a cinema.
 */
class Film {
    private String title;
    private int duration; // in minutes

    /**
     * Constructs a new Film with default values.
     */
    public Film() {
        this.title = "";
        this.duration = 0;
    }

    /**
     * Constructs a new Film with the specified title and duration.
     *
     * @param title The title of the film
     * @param duration The duration of the film in minutes
     */
    public Film(String title, int duration) {
        this.title = title;
        this.duration = duration;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Film film = (Film) obj;
        return title.equals(film.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
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
        this.film = new Film();
        this.room = new Room();
        this.screeningTime = LocalDateTime.now();
    }

    /**
     * Constructs a new Screening with the specified film, room, and time.
     *
     * @param film The film being screened
     * @param room The room where the film is screened
     * @param screeningTime The time of the screening
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Screening screening = (Screening) obj;
        return film.equals(screening.film) && 
               room.equals(screening.room) && 
               screeningTime.equals(screening.screeningTime);
    }

    @Override
    public int hashCode() {
        return film.hashCode() + room.hashCode() + screeningTime.hashCode();
    }
}