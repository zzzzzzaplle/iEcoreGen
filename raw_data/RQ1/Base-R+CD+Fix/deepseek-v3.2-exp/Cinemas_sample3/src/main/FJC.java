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

    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.films = new ArrayList<>();
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
     * Adds a room to the cinema if it doesn't already exist
     * 
     * @param room The room to be added to the cinema
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
     * Checks if a room is available at a given time
     * 
     * @param room The room to check availability for
     * @param time The time to check availability at (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the room is available, false if the room is already assigned or inputs are invalid
     * @throws ParseException if the time string format is invalid
     */
    public boolean checkAvailability(Room room, String time) throws ParseException {
        if (room == null || time == null || time.trim().isEmpty()) {
            return false;
        }
        
        if (!rooms.contains(room)) {
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
     * Adds a film to the cinema if it doesn't already exist
     * 
     * @param film The film to be added to the cinema
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
     * Removes a film from the cinema system and cancels all its future screenings
     * 
     * @param film The film to be removed
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the film was removed successfully, false if the film doesn't exist
     * @throws ParseException if the currentTime string format is invalid
     */
    public boolean removeFilm(Film film, String currentTime) throws ParseException {
        if (!films.contains(film)) {
            return false;
        }
        
        films.remove(film);
        
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date currentDate = sdf.parse(currentTime);
        
        List<Screening> screeningsToRemove = new ArrayList<>();
        for (Screening screening : screenings) {
            if (screening.getFilm().equals(film) && screening.getTime().compareTo(currentDate) >= 0) {
                screeningsToRemove.add(screening);
            }
        }
        
        screenings.removeAll(screeningsToRemove);
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific time
     * 
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
        
        if (currentDate.compareTo(screeningDate) >= 0) {
            return false;
        }
        
        if (!films.contains(film) || !rooms.contains(room)) {
            return false;
        }
        
        if (!checkAvailability(room, screeningTime)) {
            return false;
        }
        
        Screening screening = new Screening();
        screening.setFilm(film);
        screening.setRoom(room);
        screening.setTime(screeningDate);
        
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening at a given current time
     * 
     * @param screening The screening to be canceled
     * @param currentTime The current time (format: yyyy-MM-dd HH:mm:ss)
     * @return true if the screening was canceled successfully, false otherwise
     * @throws ParseException if the currentTime string format is invalid
     */
    public boolean cancelScreening(Screening screening, String currentTime) throws ParseException {
        if (screening == null || currentTime == null) {
            return false;
        }
        
        if (!screenings.contains(screening)) {
            return false;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date currentDate = sdf.parse(currentTime);
        
        if (screening.getTime().compareTo(currentDate) <= 0) {
            return false;
        }
        
        screenings.remove(screening);
        return true;
    }
}

class Room {
    public Room() {
    }
    
    // Additional properties and methods can be added here as needed
}

class Film {
    public Film() {
    }
    
    // Additional properties and methods can be added here as needed
}

class Screening {
    private Date time;
    private Film film;
    private Room room;

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