import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class Cinema {
    private List<Room> rooms;
    private List<Film> films;
    private Map<Film, List<Screening>> screenings;
    private SimpleDateFormat dateFormat;

    /**
     * Constructor for Cinema class.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.films = new ArrayList<>();
        this.screenings = new HashMap<>();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Adds a room to the cinema.
     *
     * @param room The room to be added.
     * @return true if the room was added successfully, false if the room already exists.
     */
    public boolean addRoom(Room room) {
        if (room == null || rooms.contains(room)) {
            return false;
        }
        rooms.add(room);
        return true;
    }

    /**
     * Checks the availability of a room at a given time.
     *
     * @param room The room to check.
     * @param time The time to check availability.
     * @return true if the room is available at the given time, false otherwise.
     * @throws ParseException if the time format is invalid.
     */
    public boolean isRoomAvailable(Room room, String time) throws ParseException {
        if (room == null || time == null) {
            return false;
        }
        Date dateTime = dateFormat.parse(time);
        for (Film film : screenings.keySet()) {
            for (Screening screening : screenings.get(film)) {
                if (screening.getRoom().equals(room) && screening.getScreeningTime().equals(dateTime)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Adds a film to the cinema.
     *
     * @param film The film to be added.
     * @return true if the film was added successfully, false if the film already exists.
     */
    public boolean addFilm(Film film) {
        if (film == null || films.contains(film)) {
            return false;
        }
        films.add(film);
        screenings.put(film, new ArrayList<Screening>());
        return true;
    }

    /**
     * Removes a film from the cinema and its future screenings.
     *
     * @param film The film to be removed.
     * @param currentTime The current time.
     * @return true if the film was removed successfully, false if the film does not exist.
     * @throws ParseException if the time format is invalid.
     */
    public boolean removeFilm(Film film, String currentTime) throws ParseException {
        if (film == null || !films.contains(film)) {
            return false;
        }
        Date currentDateTime = dateFormat.parse(currentTime);
        List<Screening> screeningsToRemove = new ArrayList<>();
        for (Screening screening : screenings.get(film)) {
            if (screening.getScreeningTime().after(currentDateTime)) {
                screeningsToRemove.add(screening);
            }
        }
        screenings.get(film).removeAll(screeningsToRemove);
        films.remove(film);
        screenings.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     *
     * @param film The film to be screened.
     * @param room The room where the film will be screened.
     * @param screeningTime The time of the screening.
     * @param currentTime The current time.
     * @return true if the screening was assigned successfully, false otherwise.
     * @throws ParseException if the time format is invalid.
     */
    public boolean assignScreening(Film film, Room room, String screeningTime, String currentTime) throws ParseException {
        if (film == null || room == null || screeningTime == null || currentTime == null) {
            return false;
        }
        Date screeningDateTime = dateFormat.parse(screeningTime);
        Date currentDateTime = dateFormat.parse(currentTime);
        if (currentDateTime.after(screeningDateTime)) {
            return false;
        }
        if (!films.contains(film)) {
            return false;
        }
        if (!isRoomAvailable(room, screeningTime)) {
            return false;
        }
        Screening screening = new Screening(film, room, screeningDateTime, currentDateTime);
        screenings.get(film).add(screening);
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * @param film The film of the screening to be canceled.
     * @param screeningTime The time of the screening to be canceled.
     * @param currentTime The current time.
     * @return true if the screening was canceled successfully, false otherwise.
     * @throws ParseException if the time format is invalid.
     */
    public boolean cancelScreening(Film film, String screeningTime, String currentTime) throws ParseException {
        if (film == null || screeningTime == null || currentTime == null) {
            return false;
        }
        Date screeningDateTime = dateFormat.parse(screeningTime);
        Date currentDateTime = dateFormat.parse(currentTime);
        if (currentDateTime.after(screeningDateTime)) {
            return false;
        }
        if (!screenings.containsKey(film)) {
            return false;
        }
        List<Screening> filmScreenings = screenings.get(film);
        for (Screening screening : filmScreenings) {
            if (screening.getScreeningTime().equals(screeningDateTime)) {
                filmScreenings.remove(screening);
                return true;
            }
        }
        return false;
    }

    // Getters and Setters
    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public Map<Film, List<Screening>> getScreenings() {
        return screenings;
    }

    public void setScreenings(Map<Film, List<Screening>> screenings) {
        this.screenings = screenings;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }
}

class Room {
    private String roomId;

    /**
     * Constructor for Room class.
     */
    public Room() {
    }

    // Getters and Setters
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}

class Film {
    private String filmId;

    /**
     * Constructor for Film class.
     */
    public Film() {
    }

    // Getters and Setters
    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }
}

class Screening {
    private Film film;
    private Room room;
    private Date screeningTime;
    private Date currentTime;

    /**
     * Constructor for Screening class.
     *
     * @param film The film to be screened.
     * @param room The room where the film will be screened.
     * @param screeningTime The time of the screening.
     * @param currentTime The current time.
     */
    public Screening(Film film, Room room, Date screeningTime, Date currentTime) {
        this.film = film;
        this.room = room;
        this.screeningTime = screeningTime;
        this.currentTime = currentTime;
    }

    // Getters and Setters
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

    public Date getScreeningTime() {
        return screeningTime;
    }

    public void setScreeningTime(Date screeningTime) {
        this.screeningTime = screeningTime;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }
}