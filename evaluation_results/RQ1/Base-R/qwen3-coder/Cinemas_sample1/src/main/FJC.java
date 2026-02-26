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
     * @param room the room to add
     * @return true if the room was added, false if it already exists
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
     * @param room the room to check
     * @param time the time to check availability
     * @return true if the room is available, false otherwise
     */
    public boolean isRoomAvailable(Room room, LocalDateTime time) {
        if (!rooms.contains(room) || time == null) {
            return false;
        }

        for (Screening screening : screenings) {
            if (screening.getRoom().equals(room)) {
                LocalDateTime screeningTime = screening.getScreeningTime();
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
     * @param film the film to add
     * @return true if the film was added, false if it already exists
     */
    public boolean addFilm(Film film) {
        if (films.contains(film)) {
            return false;
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film and all its future screenings from the cinema.
     *
     * @param film the film to remove
     * @param currentTime the current time for comparison
     * @return true if the film was removed, false if it doesn't exist
     */
    public boolean removeFilm(Film film, LocalDateTime currentTime) {
        if (!films.contains(film)) {
            return false;
        }

        // Remove all future screenings of this film
        screenings.removeIf(screening -> 
            screening.getFilm().equals(film) && 
            !screening.getScreeningTime().isBefore(currentTime));

        films.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     *
     * @param film the film to be screened
     * @param room the room for the screening
     * @param screeningTime the time of the screening
     * @param currentTime the current time
     * @return true if the screening was assigned, false otherwise
     */
    public boolean assignScreening(Film film, Room room, LocalDateTime screeningTime, LocalDateTime currentTime) {
        // Check if current time is before screening time
        if (!currentTime.isBefore(screeningTime)) {
            return false;
        }

        // Check if film is added to cinema
        if (!films.contains(film)) {
            return false;
        }

        // Check if room is available
        if (!isRoomAvailable(room, screeningTime)) {
            return false;
        }

        // Check if room is registered in cinema
        if (!rooms.contains(room)) {
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
     * @param screening the screening to cancel
     * @param currentTime the current time for comparison
     * @return true if the screening was canceled, false otherwise
     */
    public boolean cancelScreening(Screening screening, LocalDateTime currentTime) {
        // Check if screening exists in cinema
        if (!screenings.contains(screening)) {
            return false;
        }

        // Check if screening time is after current time
        if (!screening.getScreeningTime().isAfter(currentTime)) {
            return false;
        }

        // Cancel the screening
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
        this.name = "";
    }

    /**
     * Constructs a new Room with the specified name.
     *
     * @param name the name of the room
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

    /**
     * Constructs a new Film with default values.
     */
    public Film() {
        this.title = "";
    }

    /**
     * Constructs a new Film with the specified title.
     *
     * @param title the title of the film
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
     * @param film the film being screened
     * @param room the room where the screening takes place
     * @param screeningTime the time of the screening
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