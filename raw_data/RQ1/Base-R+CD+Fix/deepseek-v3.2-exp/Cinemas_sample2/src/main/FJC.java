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
    private SimpleDateFormat dateFormat;

    /**
     * Default constructor for Cinema class.
     * Initializes empty lists for rooms, screenings, and films.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.films = new ArrayList<>();
        this.dateFormat = new SimpleDateFormat(DATE_FORMAT);
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
     * Adds a room to the cinema if it doesn't already exist.
     * 
     * @param room The room to be added to the cinema
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
     * 
     * @param room The room to check availability for
     * @param time The time to check availability at (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the room is available, false if the room is already assigned or inputs are invalid
     */
    public boolean checkAvailability(Room room, String time) {
        if (room == null || time == null) {
            return false;
        }
        
        if (!rooms.contains(room)) {
            return false;
        }
        
        try {
            Date checkTime = dateFormat.parse(time);
            
            for (Screening screening : screenings) {
                if (screening.getRoom().equals(room) && 
                    screening.getTime().equals(checkTime)) {
                    return false;
                }
            }
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Adds a film to the cinema if it doesn't already exist.
     * 
     * @param film The film to be added to the cinema
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
     * Removes a film from the cinema system and all its future scheduled screenings.
     * 
     * @param film The film to be removed
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the film was removed successfully, false if the film doesn't exist
     * @throws ParseException if the currentTime format is invalid
     */
    public boolean removeFilm(Film film, String currentTime) throws ParseException {
        if (!films.contains(film)) {
            return false;
        }
        
        Date currentDate = dateFormat.parse(currentTime);
        
        // Remove all future screenings for this film
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
     * Assigns a screening to a film and room at a specific screening time.
     * 
     * @param film The film for the screening
     * @param room The room for the screening
     * @param screeningTime The screening time (format: yyyy-MM-dd HH:mm:ss)
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the screening was assigned successfully, false otherwise
     * @throws ParseException if the time formats are invalid
     */
    public boolean assignScreening(Film film, Room room, String screeningTime, String currentTime) throws ParseException {
        Date screeningDate = dateFormat.parse(screeningTime);
        Date currentDate = dateFormat.parse(currentTime);
        
        // Verify current time is before screening time
        if (currentDate.compareTo(screeningDate) >= 0) {
            return false;
        }
        
        // Verify film exists in cinema
        if (!films.contains(film)) {
            return false;
        }
        
        // Verify room availability
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
     * Cancels a future screening at a given current time.
     * 
     * @param screening The screening to be canceled
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the screening was canceled successfully, false otherwise
     * @throws ParseException if the currentTime format is invalid
     */
    public boolean cancelScreening(Screening screening, String currentTime) throws ParseException {
        if (!screenings.contains(screening)) {
            return false;
        }
        
        Date currentDate = dateFormat.parse(currentTime);
        
        // Verify screening time is after current time
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
    
    // Additional fields and methods can be added here based on future requirements
}

class Film {
    /**
     * Default constructor for Film class.
     */
    public Film() {
    }
    
    // Additional fields and methods can be added here based on future requirements
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