import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a cinema with multiple rooms, films, and screenings.
 */
class Cinema {
    private List<Room> rooms;
    private List<Screening> screenings;
    private List<Film> films;

    /**
     * Constructs a new Cinema object.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.films = new ArrayList<>();
    }

    /**
     * Adds a room to the cinema if it does not already exist.
     * 
     * @param room the room to be added
     * @return true if the room is added successfully, false otherwise
     */
    public boolean addRoom(Room room) {
        if (!rooms.contains(room)) {
            return rooms.add(room);
        }
        return false;
    }

    /**
     * Checks if a room is available at a given time.
     * 
     * @param room the room to be checked
     * @param time the time to check for availability
     * @return true if the room is available, false otherwise
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
     * Adds a film to the cinema if it does not already exist.
     * 
     * @param film the film to be added
     * @return true if the film is added successfully, false otherwise
     */
    public boolean addFilm(Film film) {
        if (!films.contains(film)) {
            return films.add(film);
        }
        return false;
    }

    /**
     * Removes a film from the cinema and its future screenings.
     * 
     * @param film       the film to be removed
     * @param currentTime the current time
     * @return true if the film is removed successfully, false otherwise
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (films.contains(film)) {
            films.remove(film);
            screenings.removeIf(screening -> screening.getFilm().equals(film) && screening.getTime().compareTo(currentTime) >= 0);
            return true;
        }
        return false;
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     * 
     * @param film       the film to be screened
     * @param room       the room where the screening will take place
     * @param screening  the screening to be assigned
     * @param currentTime the current time
     * @return true if the screening is assigned successfully, false otherwise
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (currentTime.compareTo(screening.getTime()) >= 0 || !films.contains(film) || !rooms.contains(room) || !checkAvailability(room, screening.getTime())) {
            return false;
        }
        screening.setFilm(film);
        screening.setRoom(room);
        return screenings.add(screening);
    }

    /**
     * Cancels a future screening.
     * 
     * @param screening  the screening to be canceled
     * @param currentTime the current time
     * @return true if the screening is canceled successfully, false otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screenings.contains(screening) && screening.getTime().compareTo(currentTime) > 0) {
            return screenings.remove(screening);
        }
        return false;
    }

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

/**
 * Represents a room in the cinema.
 */
class Room {
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        return true; // Assuming Room is identified by its instance
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

/**
 * Represents a film shown in the cinema.
 */
class Film {
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        return true; // Assuming Film is identified by its instance
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

/**
 * Represents a screening of a film in a room at a specific time.
 */
class Screening {
    private Date time;
    private Film film;
    private Room room;

    /**
     * Constructs a new Screening object.
     */
    public Screening() {
    }

    /**
     * Gets the time of the screening.
     * 
     * @return the time of the screening
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets the time of the screening.
     * 
     * @param time the time to be set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Gets the film being screened.
     * 
     * @return the film being screened
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Sets the film being screened.
     * 
     * @param film the film to be set
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * Gets the room where the screening takes place.
     * 
     * @return the room where the screening takes place
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room where the screening takes place.
     * 
     * @param room the room to be set
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Screening screening = (Screening) obj;
        return time.equals(screening.time) && room.equals(screening.room);
    }

    @Override
    public int hashCode() {
        int result = time.hashCode();
        result = 31 * result + room.hashCode();
        return result;
    }
}