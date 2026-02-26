import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Cinema {
    private List<Room> rooms = new ArrayList<>();
    private List<Screening> screenings = new ArrayList<>();
    private List<Film> films = new ArrayList<>();

    /**
     * Adds a room to the cinema if it is not already registered.
     *
     * @param room The room to be added.
     * @return true if the room was added, false if it already exists.
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
     * @param film The film to be added.
     * @return true if the film was added, false if it already exists.
     */
    public boolean addFilm(Film film) {
        if (film == null || films.contains(film)) {
            return false;
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film from the cinema and all of its future scheduled screenings.
     *
     * @param film The film to be removed.
     * @param currentTime The current time.
     * @return true if the film was removed, false if it does not exist.
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (film == null || !films.contains(film)) {
            return false;
        }
        // Remove all future screenings of the film
        screenings.removeIf(screening -> screening.getFilm().equals(film) && screening.getTime().after(currentTime));
        films.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific screening time.
     *
     * @param film The film to be screened.
     * @param room The room where the film will be screened.
     * @param screening The screening to be assigned.
     * @param currentTime The current time.
     * @return true if the screening was assigned, false otherwise.
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (film == null || room == null || screening == null || currentTime == null) {
            return false;
        }
        if (!films.contains(film) || !rooms.contains(room)) {
            return false;
        }
        if (screening.getTime().before(currentTime) || !checkAvailability(room, screening.getTime())) {
            return false;
        }
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening at a given current time.
     *
     * @param screening The screening to be canceled.
     * @param currentTime The current time.
     * @return true if the screening was canceled, false otherwise.
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
     * @param room The room to check.
     * @param time The time to check.
     * @return true if the room is available, false otherwise.
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
    // Room properties and methods can be added here
}

class Film {
    // Film properties and methods can be added here
}

class Screening {
    private Date time;
    private Film film;
    private Room room;

    /**
     * Creates a new Screening with the specified time, film, and room.
     *
     * @param time The time of the screening.
     * @param film The film to be screened.
     * @param room The room where the film will be screened.
     */
    public Screening(Date time, Film film, Room room) {
        this.time = time;
        this.film = film;
        this.room = room;
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