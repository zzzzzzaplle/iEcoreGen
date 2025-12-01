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
     * Adds a room to the cinema if it doesn't already exist.
     *
     * @param room The room to be added.
     * @return true if the room was added successfully, false if it already exists.
     */
    public boolean addRoom(Room room) {
        if (room == null || rooms.contains(room)) {
            return false;
        }
        rooms.add(room);
        return true;
    }

    /**
     * Adds a film to the cinema if it doesn't already exist.
     *
     * @param film The film to be added.
     * @return true if the film was added successfully, false if it already exists.
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
     * @param film The film to be removed.
     * @param currentTime The current time.
     * @return true if the film was removed successfully, false if it doesn't exist.
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (film == null || !films.contains(film)) {
            return false;
        }

        // Remove future screenings
        screenings.removeIf(screening ->
            screening.getFilm().equals(film) && screening.getTime().after(currentTime));

        films.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     *
     * @param film The film to be screened.
     * @param room The room where the film will be screened.
     * @param screening The screening to be assigned.
     * @param currentTime The current time.
     * @return true if the screening was assigned successfully, false otherwise.
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (film == null || room == null || screening == null || currentTime == null ||
            !films.contains(film) || !rooms.contains(room) ||
            screening.getTime().before(currentTime) || !checkAvailability(room, screening.getTime())) {
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
     * @param screening The screening to be canceled.
     * @param currentTime The current time.
     * @return true if the screening was canceled successfully, false otherwise.
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || !screenings.contains(screening) ||
            screening.getTime().before(currentTime)) {
            return false;
        }

        screenings.remove(screening);
        return true;
    }

    /**
     * Checks the availability of a room at a given time.
     *
     * @param room The room to check.
     * @param time The time to check availability.
     * @return true if the room is available, false otherwise.
     */
    public boolean checkAvailability(Room room, Date time) {
        if (room == null || time == null || !rooms.contains(room)) {
            return false;
        }

        return screenings.stream()
            .noneMatch(screening ->
                screening.getRoom().equals(room) && screening.getTime().equals(time));
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

    // Getters and Setters
    // Add any necessary getters and setters here
}

class Film {
    // Film properties can be added here as needed

    // Getters and Setters
    // Add any necessary getters and setters here
}

class Screening {
    private Date time;
    private Film film;
    private Room room;

    public Screening() {
        this.time = new Date();
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