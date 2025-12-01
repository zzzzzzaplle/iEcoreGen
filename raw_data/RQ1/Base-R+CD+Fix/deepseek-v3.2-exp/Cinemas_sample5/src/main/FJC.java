import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class Cinema {
    private List<Room> rooms;
    private List<Screening> screenings;
    private List<Film> films;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Default constructor initializing empty lists for rooms, screenings, and films.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.films = new ArrayList<>();
    }

    /**
     * Gets the list of rooms in the cinema.
     * @return List of Room objects
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Sets the list of rooms in the cinema.
     * @param rooms List of Room objects to set
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Gets the list of screenings in the cinema.
     * @return List of Screening objects
     */
    public List<Screening> getScreenings() {
        return screenings;
    }

    /**
     * Sets the list of screenings in the cinema.
     * @param screenings List of Screening objects to set
     */
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    /**
     * Gets the list of films in the cinema.
     * @return List of Film objects
     */
    public List<Film> getFilms() {
        return films;
    }

    /**
     * Sets the list of films in the cinema.
     * @param films List of Film objects to set
     */
    public void setFilms(List<Film> films) {
        this.films = films;
    }

    /**
     * Adds a room to the cinema if it is not already registered.
     * @param room The room to be added
     * @return true if the room was added successfully, false if the room already exists
     */
    public boolean addRoom(Room room) {
        if (!rooms.contains(room)) {
            rooms.add(room);
            return true;
        }
        return false;
    }

    /**
     * Checks if a room is available at a given time.
     * Verifies if the room exists in the cinema and if it's not assigned to any screening at the specified time.
     * @param room The room to check availability for
     * @param time The time to check availability at (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the room is available, false if the room is already assigned or inputs are invalid
     * @throws ParseException if the time string format is invalid
     */
    public boolean checkAvailability(Room room, String time) throws ParseException {
        if (room == null || time == null || !rooms.contains(room)) {
            return false;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date checkTime = sdf.parse(time);
        
        for (Screening screening : screenings) {
            if (screening.getRoom().equals(room) && screening.getTime().equals(checkTime)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a film to the cinema if it does not already exist.
     * @param film The film to be added
     * @return true if the film was added successfully, false if the film already exists
     */
    public boolean addFilm(Film film) {
        if (!films.contains(film)) {
            films.add(film);
            return true;
        }
        return false;
    }

    /**
     * Removes a film from the cinema system and cancels all its future screenings.
     * @param film The film to be removed
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the film was removed successfully, false if the film does not exist
     * @throws ParseException if the currentTime string format is invalid
     */
    public boolean removeFilm(Film film, String currentTime) throws ParseException {
        if (!films.contains(film)) {
            return false;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date currentDate = sdf.parse(currentTime);
        
        // Remove future screenings for this film
        List<Screening> screeningsToRemove = new ArrayList<>();
        for (Screening screening : screenings) {
            if (screening.getFilm().equals(film) && 
                screening.getTime().compareTo(currentDate) >= 0) {
                screeningsToRemove.add(screening);
            }
        }
        screenings.removeAll(screeningsToRemove);
        
        // Remove the film
        films.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     * Verifies that current time is before screening time, film exists, and room is available.
     * @param film The film for the screening
     * @param room The room for the screening
     * @param screeningTime The screening time (format: yyyy-MM-dd HH:mm:ss)
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the screening was assigned successfully, false otherwise
     * @throws ParseException if the time string formats are invalid
     */
    public boolean assignScreening(Film film, Room room, String screeningTime, String currentTime) throws ParseException {
        if (film == null || room == null || screeningTime == null || currentTime == null) {
            return false;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date screeningDate = sdf.parse(screeningTime);
        Date currentDate = sdf.parse(currentTime);
        
        // Check if current time is before screening time
        if (currentDate.compareTo(screeningDate) >= 0) {
            return false;
        }
        
        // Check if film exists in cinema
        if (!films.contains(film)) {
            return false;
        }
        
        // Check room availability
        if (!checkAvailability(room, screeningTime)) {
            return false;
        }
        
        // Create and add screening
        Screening screening = new Screening();
        screening.setTime(screeningDate);
        screening.setFilm(film);
        screening.setRoom(room);
        screenings.add(screening);
        
        return true;
    }

    /**
     * Cancels a future screening.
     * Checks if the screening exists and if the screening time is after the current time.
     * @param screening The screening to cancel
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the screening was canceled successfully, false otherwise
     * @throws ParseException if the currentTime string format is invalid
     */
    public boolean cancelScreening(Screening screening, String currentTime) throws ParseException {
        if (screening == null || currentTime == null || !screenings.contains(screening)) {
            return false;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date currentDate = sdf.parse(currentTime);
        
        // Check if screening time is after current time
        if (screening.getTime().compareTo(currentDate) <= 0) {
            return false;
        }
        
        screenings.remove(screening);
        return true;
    }
}

class Room {
    /**
     * Default constructor for Room class.
     */
    public Room() {
    }
    
    // Room class can be extended with additional properties as needed
}

class Film {
    /**
     * Default constructor for Film class.
     */
    public Film() {
    }
    
    // Film class can be extended with additional properties as needed
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

    /**
     * Gets the screening time.
     * @return Date object representing the screening time
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets the screening time.
     * @param time Date object representing the screening time
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Gets the film for this screening.
     * @return Film object associated with this screening
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Sets the film for this screening.
     * @param film Film object to associate with this screening
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * Gets the room for this screening.
     * @return Room object where the screening takes place
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room for this screening.
     * @param room Room object where the screening takes place
     */
    public void setRoom(Room room) {
        this.room = room;
    }
}