import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class Cinema {
    private List<Room> rooms;
    private List<Film> films;

    public Cinema() {
        this.rooms = new ArrayList<>();
        this.films = new ArrayList<>();
    }

    /**
     * Adds a room to the cinema if it does not already exist.
     * @param room the room to add
     * @return true if the room was added, false if it already exists
     */
    public boolean addRoom(Room room) {
        if (room == null) {
            return false;
        }
        for (Room existingRoom : rooms) {
            if (existingRoom.getName().equals(room.getName())) {
                return false;
            }
        }
        rooms.add(room);
        return true;
    }

    /**
     * Checks if a room is available at a given time.
     * @param room the room to check
     * @param time the time to check availability
     * @return true if the room is available, false otherwise
     */
    public boolean isRoomAvailable(Room room, String time) {
        if (room == null || time == null) {
            return false;
        }
        LocalDateTime dateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        for (Room existingRoom : rooms) {
            if (existingRoom.getName().equals(room.getName())) {
                for (Screening screening : existingRoom.getScreenings()) {
                    if (screening.getScreeningTime().equals(dateTime)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a film to the cinema if it does not already exist.
     * @param film the film to add
     * @return true if the film was added, false if it already exists
     */
    public boolean addFilm(Film film) {
        if (film == null) {
            return false;
        }
        for (Film existingFilm : films) {
            if (existingFilm.getTitle().equals(film.getTitle())) {
                return false;
            }
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film from the cinema and its future screenings.
     * @param film the film to remove
     * @param currentTime the current time
     * @return true if the film was removed, false if it does not exist
     */
    public boolean removeFilm(Film film, String currentTime) {
        if (film == null || currentTime == null) {
            return false;
        }
        LocalDateTime dateTime = LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        for (Film existingFilm : films) {
            if (existingFilm.getTitle().equals(film.getTitle())) {
                films.remove(existingFilm);
                for (Room room : rooms) {
                    room.removeFutureScreenings(dateTime);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     * @param film the film to assign
     * @param room the room to assign
     * @param screeningTime the screening time
     * @param currentTime the current time
     * @return true if the screening was assigned, false otherwise
     */
    public boolean assignScreening(Film film, Room room, String screeningTime, String currentTime) {
        if (film == null || room == null || screeningTime == null || currentTime == null) {
            return false;
        }
        LocalDateTime screeningDateTime = LocalDateTime.parse(screeningTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime currentDateTime = LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (currentDateTime.isAfter(screeningDateTime)) {
            return false;
        }
        if (!films.contains(film) || !rooms.contains(room)) {
            return false;
        }
        if (!isRoomAvailable(room, screeningTime)) {
            return false;
        }
        Screening screening = new Screening(film, room, screeningDateTime);
        room.addScreening(screening);
        return true;
    }

    /**
     * Cancels a future screening at a given current time.
     * @param film the film of the screening
     * @param room the room of the screening
     * @param screeningTime the screening time
     * @param currentTime the current time
     * @return true if the screening was canceled, false otherwise
     */
    public boolean cancelScreening(Film film, Room room, String screeningTime, String currentTime) {
        if (film == null || room == null || screeningTime == null || currentTime == null) {
            return false;
        }
        LocalDateTime screeningDateTime = LocalDateTime.parse(screeningTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime currentDateTime = LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (currentDateTime.isAfter(screeningDateTime)) {
            return false;
        }
        for (Room existingRoom : rooms) {
            if (existingRoom.getName().equals(room.getName())) {
                for (Screening screening : existingRoom.getScreenings()) {
                    if (screening.getFilm().getTitle().equals(film.getTitle()) &&
                        screening.getScreeningTime().equals(screeningDateTime)) {
                        return existingRoom.removeScreening(screening);
                    }
                }
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
}

class Room {
    private String name;
    private List<Screening> screenings;

    public Room() {
        this.screenings = new ArrayList<>();
    }

    public Room(String name) {
        this();
        this.name = name;
    }

    /**
     * Adds a screening to the room.
     * @param screening the screening to add
     */
    public void addScreening(Screening screening) {
        if (screening != null) {
            screenings.add(screening);
        }
    }

    /**
     * Removes a screening from the room.
     * @param screening the screening to remove
     * @return true if the screening was removed, false otherwise
     */
    public boolean removeScreening(Screening screening) {
        if (screening != null) {
            return screenings.remove(screening);
        }
        return false;
    }

    /**
     * Removes all future screenings from the room.
     * @param currentTime the current time
     */
    public void removeFutureScreenings(LocalDateTime currentTime) {
        screenings.removeIf(screening -> screening.getScreeningTime().isAfter(currentTime));
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }
}

class Film {
    private String title;

    public Film() {
    }

    public Film(String title) {
        this.title = title;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

class Screening {
    private Film film;
    private Room room;
    private LocalDateTime screeningTime;

    public Screening() {
    }

    public Screening(Film film, Room room, LocalDateTime screeningTime) {
        this.film = film;
        this.room = room;
        this.screeningTime = screeningTime;
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

    public LocalDateTime getScreeningTime() {
        return screeningTime;
    }

    public void setScreeningTime(LocalDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }
}