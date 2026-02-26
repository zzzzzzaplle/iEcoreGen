import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class Cinema {
    private List<Room> rooms;
    private List<Screening> screenings;
    private List<Film> films;
    private SimpleDateFormat dateFormat;

    /**
     * Default constructor for Cinema class.
     * Initializes empty lists for rooms, screenings, and films.
     * Also initializes the date format for parsing and formatting dates.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.films = new ArrayList<>();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

    /**
     * Adds a room to the cinema if it is not already registered.
     * 
     * @param room the room to be added
     * @return true if the room was added successfully, false if the room already exists
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
     * Verifies that the room exists in the cinema and is not assigned to any screening at the specified time.
     * 
     * @param room the room to check availability for
     * @param time the time to check availability at (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the room is available, false if the room is already assigned or inputs are invalid
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

    /**
     * Adds a film to the cinema if it does not already exist.
     * 
     * @param film the film to be added
     * @return true if the film was added successfully, false if the film already exists
     */
    public boolean addFilm(Film film) {
        if (films.contains(film)) {
            return false;
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film from the cinema system and cancels all its future screenings.
     * 
     * @param film the film to be removed
     * @param currentTime the current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the film was removed successfully, false if the film does not exist
     * @throws ParseException if the currentTime string is not in the correct format
     */
    public boolean removeFilm(Film film, Date currentTime) throws ParseException {
        if (!films.contains(film)) {
            return false;
        }
        
        films.remove(film);
        
        List<Screening> screeningsToRemove = new ArrayList<>();
        for (Screening screening : screenings) {
            if (screening.getFilm().equals(film) && screening.getTime().after(currentTime)) {
                screeningsToRemove.add(screening);
            }
        }
        screenings.removeAll(screeningsToRemove);
        
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     * Verifies that the current time is before the screening time, the film exists, and the room is available.
     * 
     * @param film the film for the screening
     * @param room the room for the screening
     * @param screeningTime the time of the screening (format: yyyy-MM-dd HH:mm:ss)
     * @param currentTime the current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the screening was assigned successfully, false otherwise
     * @throws ParseException if the time strings are not in the correct format
     */
    public boolean assignScreening(Film film, Room room, Date screeningTime, Date currentTime) throws ParseException {
        if (film == null || room == null || screeningTime == null || currentTime == null) {
            return false;
        }
        
        if (currentTime.after(screeningTime) || !films.contains(film) || !rooms.contains(room)) {
            return false;
        }
        
        if (!checkAvailability(room, screeningTime)) {
            return false;
        }
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        screening.setFilm(film);
        screening.setRoom(room);
        screenings.add(screening);
        
        return true;
    }

    /**
     * Cancels a future screening.
     * Verifies that the screening exists and its time is after the current time.
     * 
     * @param screening the screening to be canceled
     * @param currentTime the current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the screening was canceled successfully, false otherwise
     * @throws ParseException if the currentTime string is not in the correct format
     */
    public boolean cancelScreening(Screening screening, Date currentTime) throws ParseException {
        if (screening == null || currentTime == null || !screenings.contains(screening)) {
            return false;
        }
        
        if (screening.getTime().after(currentTime)) {
            screenings.remove(screening);
            return true;
        }
        
        return false;
    }
}

class Room {
    /**
     * Default constructor for Room class.
     */
    public Room() {
    }
    
    // Additional fields and methods can be added here as needed
}

class Film {
    /**
     * Default constructor for Film class.
     */
    public Film() {
    }
    
    // Additional fields and methods can be added here as needed
}

class Screening {
    private Date time;
    private Film film;
    private Room room;

    /**
     * Default constructor for Screening class.
     */
    public Screening() {
    }

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