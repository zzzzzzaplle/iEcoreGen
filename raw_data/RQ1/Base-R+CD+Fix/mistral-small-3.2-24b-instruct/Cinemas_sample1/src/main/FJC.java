import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Cinema {
    private List<Room> rooms;
    private List<Screening> screenings;
    private List<Film> films;

    /**
     * Constructor for Cinema class.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.films = new ArrayList<>();
    }

    /**
     * Adds a room to the cinema if it does not already exist.
     *
     * @param room The room to be added.
     * @return true if the room was added, false if it already exists.
     */
    public boolean addRoom(Room room) {
        if (rooms.contains(room)) {
            return false;
        }
        rooms.add(room);
        return true;
    }

    /**
     * Adds a film to the cinema if it does not already exist.
     *
     * @param film The film to be added.
     * @return true if the film was added, false if it already exists.
     */
    public boolean addFilm(Film film) {
        if (films.contains(film)) {
            return false;
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film from the cinema and all its future screenings.
     *
     * @param film The film to be removed.
     * @param currentTime The current time.
     * @return true if the film was removed, false if it does not exist.
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (!films.contains(film)) {
            return false;
        }
        films.remove(film);
        screenings.removeIf(screening -> screening.getFilm().equals(film) && screening.getTime().after(currentTime));
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     *
     * @param film The film to be screened.
     * @param room The room where the film will be screened.
     * @param screening The screening to be assigned.
     * @param currentTime The current time.
     * @return true if the screening was assigned, false otherwise.
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (!films.contains(film) || !rooms.contains(room) || screening.getTime().before(currentTime) || !checkAvailability(room, screening.getTime())) {
            return false;
        }
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * @param screening The screening to be canceled.
     * @param currentTime The current time.
     * @return true if the screening was canceled, false otherwise.
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (!screenings.contains(screening) || screening.getTime().before(currentTime)) {
            return false;
        }
        screenings.remove(screening);
        return true;
    }

    /**
     * Checks the availability of a room at a specific time.
     *
     * @param room The room to check.
     * @param time The time to check.
     * @return true if the room is available, false otherwise.
     */
    public boolean checkAvailability(Room room, Date time) {
        if (!rooms.contains(room)) {
            return false;
        }
        for (Screening screening : screenings) {
            if (screening.getRoom().equals(room) && screening.getTime().equals(time)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the list of rooms in the cinema.
     *
     * @return the list of rooms.
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Sets the list of rooms in the cinema.
     *
     * @param rooms the list of rooms to set.
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Gets the list of screenings in the cinema.
     *
     * @return the list of screenings.
     */
    public List<Screening> getScreenings() {
        return screenings;
    }

    /**
     * Sets the list of screenings in the cinema.
     *
     * @param screenings the list of screenings to set.
     */
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    /**
     * Gets the list of films in the cinema.
     *
     * @return the list of films.
     */
    public List<Film> getFilms() {
        return films;
    }

    /**
     * Sets the list of films in the cinema.
     *
     * @param films the list of films to set.
     */
    public void setFilms(List<Film> films) {
        this.films = films;
    }
}

class Room {
    // Room class implementation
}

class Film {
    // Film class implementation
}

class Screening {
    private Date time;
    private Film film;
    private Room room;

    /**
     * Constructor for Screening class.
     */
    public Screening() {
    }

    /**
     * Gets the time of the screening.
     *
     * @return the time of the screening.
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets the time of the screening.
     *
     * @param time the time of the screening to set.
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Gets the film of the screening.
     *
     * @return the film of the screening.
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Sets the film of the screening.
     *
     * @param film the film of the screening to set.
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * Gets the room of the screening.
     *
     * @return the room of the screening.
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room of the screening.
     *
     * @param room the room of the screening to set.
     */
    public void setRoom(Room room) {
        this.room = room;
    }
}