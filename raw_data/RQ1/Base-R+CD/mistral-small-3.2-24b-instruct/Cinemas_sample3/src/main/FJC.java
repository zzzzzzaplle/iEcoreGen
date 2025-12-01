import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Cinema {
    private List<Room> rooms;
    private List<Screening> screenings;
    private List<Film> films;

    /**
     * Constructs a new Cinema with empty lists for rooms, screenings, and films.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
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
     * Adds a film to the cinema if it is not already registered.
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
     * Removes a film from the cinema and all its future screenings.
     *
     * @param film the film to remove
     * @param currentTime the current time
     * @return true if the film was removed, false if it does not exist
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (film == null || !films.contains(film)) {
            return false;
        }
        // Remove future screenings
        screenings.removeIf(screening -> screening.getFilm().equals(film) && screening.getTime().after(currentTime));
        // Remove the film
        films.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     *
     * @param film the film to screen
     * @param room the room to screen in
     * @param screening the screening to assign
     * @param currentTime the current time
     * @return true if the screening was assigned, false otherwise
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (film == null || room == null || screening == null || currentTime == null) {
            return false;
        }
        if (!films.contains(film) || !rooms.contains(room) || !checkAvailability(room, screening.getTime())) {
            return false;
        }
        if (screening.getTime().before(currentTime)) {
            return false;
        }
        screening.setFilm(film);
        screening.setRoom(room);
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * @param screening the screening to cancel
     * @param currentTime the current time
     * @return true if the screening was canceled, false otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || currentTime == null || !screenings.contains(screening)) {
            return false;
        }
        if (screening.getTime().before(currentTime)) {
            return false;
        }
        screenings.remove(screening);
        return true;
    }

    /**
     * Checks the availability of a room at a given time.
     *
     * @param room the room to check
     * @param time the time to check
     * @return true if the room is available, false otherwise
     */
    public boolean checkAvailability(Room room, Date time) {
        if (room == null || time == null || !rooms.contains(room)) {
            return false;
        }
        for (Screening screening : screenings) {
            if (screening.getRoom().equals(room) && screening.getTime().equals(time)) {
                return false;
            }
        }
        return true;
    }

    // Getters and Setters
    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}

class Room {
    // Room properties can be added here as needed

    /**
     * Constructs a new Room.
     */
    public Room() {
    }

    // Getters and Setters
    // Add any necessary properties and their getters and setters
}

class Film {
    // Film properties can be added here as needed

    /**
     * Constructs a new Film.
     */
    public Film() {
    }

    // Getters and Setters
    // Add any necessary properties and their getters and setters
}

class Screening {
    private Date time;
    private Film film;
    private Room room;

    /**
     * Constructs a new Screening.
     */
    public Screening() {
    }

    /**
     * Constructs a new Screening with the specified time.
     *
     * @param time the time of the screening
     */
    public Screening(Date time) {
        this.time = time;
    }

    // Getters and Setters
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
}