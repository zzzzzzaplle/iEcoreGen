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
     * @param time The time to check availability
     * @return true if the room is available, false otherwise
     */
    public boolean isRoomAvailable(Room room, LocalDateTime time) {
        if (room == null || time == null) {
            return false;
        }
        if (!rooms.contains(room)) {
            return false;
        }
        for (Screening screening : screenings) {
            if (screening.getRoom().equals(room)) {
                LocalDateTime screeningTime = screening.getTime();
                if (screeningTime.equals(time)) {
                    return false;
                }
            }
        }
        return true;
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
     * Removes a film and all its future screenings from the cinema.
     *
     * @param film The film to be removed
     * @param currentTime The current time for comparison
     * @return true if the film was removed successfully, false otherwise
     */
    public boolean removeFilm(Film film, LocalDateTime currentTime) {
        if (film == null || currentTime == null) {
            return false;
        }
        if (!films.contains(film)) {
            return false;
        }
        
        // Remove all future screenings of this film
        screenings.removeIf(screening -> 
            screening.getFilm().equals(film) && 
            !screening.getTime().isBefore(currentTime));
        
        // Remove the film itself
        films.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     *
     * @param film The film to be screened
     * @param room The room for the screening
     * @param screeningTime The time of the screening
     * @param currentTime The current time for validation
     * @return true if the screening was assigned successfully, false otherwise
     */
    public boolean assignScreening(Film film, Room room, LocalDateTime screeningTime, LocalDateTime currentTime) {
        if (film == null || room == null || screeningTime == null || currentTime == null) {
            return false;
        }
        // Check if current time is before screening time
        if (!currentTime.isBefore(screeningTime)) {
            return false;
        }
        // Check if film is added to the cinema
        if (!films.contains(film)) {
            return false;
        }
        // Check room availability
        if (!isRoomAvailable(room, screeningTime)) {
            return false;
        }
        // Assign the screening
        Screening screening = new Screening(film, room, screeningTime);
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * @param screening The screening to be canceled
     * @param currentTime The current time for comparison
     * @return true if the screening was canceled successfully, false otherwise
     */
    public boolean cancelScreening(Screening screening, LocalDateTime currentTime) {
        if (screening == null || currentTime == null) {
            return false;
        }
        if (!screenings.contains(screening)) {
            return false;
        }
        // Check if screening time is after current time
        if (!screening.getTime().isAfter(currentTime)) {
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

    /**
     * Constructs a new Room with default values.
     */
    public Room() {
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
        return name != null ? name.equals(room.name) : room.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

/**
 * Represents a film shown in a cinema.
 */
class Film {
    private String title;

    /**
     * Constructs a new Film with default values.
     */
    public Film() {
    }

    /**
     * Constructs a new Film with the specified title.
     *
     * @param title The title of the film
     */
    public Film(String title) {
        this.title = title;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    private LocalDateTime time;

    /**
     * Constructs a new Screening with default values.
     */
    public Screening() {
    }

    /**
     * Constructs a new Screening with the specified film, room, and time.
     *
     * @param film The film being screened
     * @param room The room where the screening takes place
     * @param time The time of the screening
     */
    public Screening(Film film, Room room, LocalDateTime time) {
        this.film = film;
        this.room = room;
        this.time = time;
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Screening screening = (Screening) obj;
        return film != null ? film.equals(screening.film) : screening.film == null &&
               room != null ? room.equals(screening.room) : screening.room == null &&
               time != null ? time.equals(screening.time) : screening.time == null;
    }

    @Override
    public int hashCode() {
        int result = film != null ? film.hashCode() : 0;
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}