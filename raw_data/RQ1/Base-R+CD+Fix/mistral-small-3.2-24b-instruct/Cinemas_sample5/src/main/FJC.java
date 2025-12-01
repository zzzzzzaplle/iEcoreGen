import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Cinema {
    private List<Room> rooms;
    private List<Screening> screenings;
    private List<Film> films;

    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.films = new ArrayList<>();
    }

    /**
     * Adds a room to the cinema if it's not already registered.
     *
     * @param room the room to be added
     * @return true if the room was added successfully, false otherwise
     */
    public boolean addRoom(Room room) {
        if (room == null || rooms.contains(room)) {
            return false;
        }
        rooms.add(room);
        return true;
    }

    /**
     * Adds a film to the cinema if it's not already added.
     *
     * @param film the film to be added
     * @return true if the film was added successfully, false otherwise
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
     * @param film the film to be removed
     * @param currentTime the current time
     * @return true if the film was removed successfully, false otherwise
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (film == null || !films.contains(film)) {
            return false;
        }

        // Remove future screenings
        screenings.removeIf(screening -> screening.getFilm().equals(film) && screening.getTime().after(currentTime));

        films.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     *
     * @param film the film to be screened
     * @param room the room where the film will be screened
     * @param screening the screening object containing time and other details
     * @param currentTime the current time
     * @return true if the screening was assigned successfully, false otherwise
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (film == null || room == null || screening == null || currentTime == null) {
            return false;
        }

        if (!films.contains(film) || !rooms.contains(room)) {
            return false;
        }

        if (!screening.getTime().after(currentTime)) {
            return false;
        }

        if (!checkAvailability(room, screening.getTime())) {
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
     * @param screening the screening to be canceled
     * @param currentTime the current time
     * @return true if the screening was canceled successfully, false otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || !screenings.contains(screening) || !screening.getTime().after(currentTime)) {
            return false;
        }

        screenings.remove(screening);
        return true;
    }

    /**
     * Checks if a room is available at a given time.
     *
     * @param room the room to check
     * @param time the time to check availability
     * @return true if the room is available, false otherwise
     */
    public boolean checkAvailability(Room room, Date time) {
        if (room == null || time == null) {
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
    // Room details can be added here

    // Getters and Setters
}

class Film {
    // Film details can be added here

    // Getters and Setters
}

class Screening {
    private Date time;
    private Film film;
    private Room room;

    public Screening() {
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