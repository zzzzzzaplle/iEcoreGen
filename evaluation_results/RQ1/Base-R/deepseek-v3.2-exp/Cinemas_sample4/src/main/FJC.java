import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a film in the cinema system.
 */
class Film {
    private String title;
    private List<Screening> screenings;

    /**
     * Default constructor for Film.
     */
    public Film() {
        this.screenings = new ArrayList<>();
    }

    /**
     * Gets the title of the film.
     * @return the film title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the film.
     * @param title the film title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the list of screenings for this film.
     * @return list of screenings
     */
    public List<Screening> getScreenings() {
        return screenings;
    }

    /**
     * Sets the list of screenings for this film.
     * @param screenings the list of screenings to set
     */
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    /**
     * Adds a screening to this film.
     * @param screening the screening to add
     */
    public void addScreening(Screening screening) {
        this.screenings.add(screening);
    }

    /**
     * Removes future screenings from this film that are scheduled at or after the given time.
     * @param currentTime the current time to compare against
     * @return true if any screenings were removed, false otherwise
     */
    public boolean removeFutureScreenings(LocalDateTime currentTime) {
        return screenings.removeIf(screening -> 
            !screening.getScreeningTime().isBefore(currentTime)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(title, film.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}

/**
 * Represents a room in the cinema.
 */
class Room {
    private String roomId;
    private List<Screening> screenings;

    /**
     * Default constructor for Room.
     */
    public Room() {
        this.screenings = new ArrayList<>();
    }

    /**
     * Gets the room identifier.
     * @return the room ID
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * Sets the room identifier.
     * @param roomId the room ID to set
     */
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    /**
     * Gets the list of screenings scheduled in this room.
     * @return list of screenings
     */
    public List<Screening> getScreenings() {
        return screenings;
    }

    /**
     * Sets the list of screenings for this room.
     * @param screenings the list of screenings to set
     */
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    /**
     * Adds a screening to this room.
     * @param screening the screening to add
     */
    public void addScreening(Screening screening) {
        this.screenings.add(screening);
    }

    /**
     * Checks if this room is available at the given time.
     * @param screeningTime the time to check availability for
     * @return true if available, false if already booked
     */
    public boolean isAvailableAt(LocalDateTime screeningTime) {
        return screenings.stream()
            .noneMatch(screening -> screening.getScreeningTime().equals(screeningTime));
    }

    /**
     * Removes a specific screening from this room.
     * @param screening the screening to remove
     * @return true if removed successfully, false otherwise
     */
    public boolean removeScreening(Screening screening) {
        return screenings.remove(screening);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomId, room.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId);
    }
}

/**
 * Represents a screening of a film in a room at a specific time.
 */
class Screening {
    private Film film;
    private Room room;
    private LocalDateTime screeningTime;

    /**
     * Default constructor for Screening.
     */
    public Screening() {
    }

    /**
     * Gets the film being screened.
     * @return the film
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Sets the film for this screening.
     * @param film the film to set
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * Gets the room where the screening takes place.
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room for this screening.
     * @param room the room to set
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Gets the screening time.
     * @return the screening time
     */
    public LocalDateTime getScreeningTime() {
        return screeningTime;
    }

    /**
     * Sets the screening time.
     * @param screeningTime the screening time to set
     */
    public void setScreeningTime(LocalDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Screening screening = (Screening) o;
        return Objects.equals(film, screening.film) &&
               Objects.equals(room, screening.room) &&
               Objects.equals(screeningTime, screening.screeningTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film, room, screeningTime);
    }
}

/**
 * Main cinema management system that handles rooms, films, and screenings.
 */
class Cinema {
    private List<Room> rooms;
    private List<Film> films;
    private List<Screening> screenings;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Default constructor for Cinema.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.films = new ArrayList<>();
        this.screenings = new ArrayList<>();
    }

    /**
     * Gets the list of rooms in the cinema.
     * @return list of rooms
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Sets the list of rooms for the cinema.
     * @param rooms the list of rooms to set
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Gets the list of films in the cinema.
     * @return list of films
     */
    public List<Film> getFilms() {
        return films;
    }

    /**
     * Sets the list of films for the cinema.
     * @param films the list of films to set
     */
    public void setFilms(List<Film> films) {
        this.films = films;
    }

    /**
     * Gets the list of all screenings in the cinema.
     * @return list of screenings
     */
    public List<Screening> getScreenings() {
        return screenings;
    }

    /**
     * Sets the list of screenings for the cinema.
     * @param screenings the list of screenings to set
     */
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    /**
     * Adds a room to the cinema if it doesn't already exist.
     * @param room the room to add
     * @return true if added successfully, false if room already exists
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
     * @param roomId the ID of the room to check
     * @param timeString the time to check in format "yyyy-MM-dd HH:mm:ss"
     * @return true if the room is available, false if already assigned or inputs are invalid
     */
    public boolean checkRoomAvailability(String roomId, String timeString) {
        try {
            LocalDateTime screeningTime = LocalDateTime.parse(timeString, formatter);
            Room room = findRoomById(roomId);
            
            if (room == null) {
                return false;
            }
            
            return room.isAvailableAt(screeningTime);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Adds a film to the cinema if it doesn't already exist.
     * @param film the film to add
     * @return true if added successfully, false if film already exists
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
     * @param filmTitle the title of the film to remove
     * @param currentTimeString the current time in format "yyyy-MM-dd HH:mm:ss"
     * @return true if removed successfully, false if film doesn't exist
     */
    public boolean removeFilm(String filmTitle, String currentTimeString) {
        try {
            LocalDateTime currentTime = LocalDateTime.parse(currentTimeString, formatter);
            Film film = findFilmByTitle(filmTitle);
            
            if (film == null) {
                return false;
            }
            
            // Remove future screenings for this film
            film.removeFutureScreenings(currentTime);
            
            // Remove screenings from cinema's main list and rooms
            screenings.removeIf(screening -> 
                screening.getFilm().equals(film) && 
                !screening.getScreeningTime().isBefore(currentTime)
            );
            
            // Remove screenings from rooms
            for (Room room : rooms) {
                room.getScreenings().removeIf(screening -> 
                    screening.getFilm().equals(film) && 
                    !screening.getScreeningTime().isBefore(currentTime)
                );
            }
            
            // Remove the film
            films.remove(film);
            return true;
            
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     * @param filmTitle the title of the film
     * @param roomId the ID of the room
     * @param screeningTimeString the screening time in format "yyyy-MM-dd HH:mm:ss"
     * @param currentTimeString the current time in format "yyyy-MM-dd HH:mm:ss"
     * @return true if assigned successfully, false otherwise
     */
    public boolean assignScreening(String filmTitle, String roomId, String screeningTimeString, String currentTimeString) {
        try {
            LocalDateTime screeningTime = LocalDateTime.parse(screeningTimeString, formatter);
            LocalDateTime currentTime = LocalDateTime.parse(currentTimeString, formatter);
            
            // Verify current time is before screening time
            if (!currentTime.isBefore(screeningTime)) {
                return false;
            }
            
            Film film = findFilmByTitle(filmTitle);
            Room room = findRoomById(roomId);
            
            // Verify film exists and room is available
            if (film == null || room == null || !room.isAvailableAt(screeningTime)) {
                return false;
            }
            
            // Create and assign the screening
            Screening screening = new Screening();
            screening.setFilm(film);
            screening.setRoom(room);
            screening.setScreeningTime(screeningTime);
            
            film.addScreening(screening);
            room.addScreening(screening);
            screenings.add(screening);
            
            return true;
            
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Cancels a future screening.
     * @param filmTitle the title of the film
     * @param roomId the ID of the room
     * @param screeningTimeString the screening time in format "yyyy-MM-dd HH:mm:ss"
     * @param currentTimeString the current time in format "yyyy-MM-dd HH:mm:ss"
     * @return true if canceled successfully, false otherwise
     */
    public boolean cancelScreening(String filmTitle, String roomId, String screeningTimeString, String currentTimeString) {
        try {
            LocalDateTime screeningTime = LocalDateTime.parse(screeningTimeString, formatter);
            LocalDateTime currentTime = LocalDateTime.parse(currentTimeString, formatter);
            
            // Verify screening time is after current time
            if (!screeningTime.isAfter(currentTime)) {
                return false;
            }
            
            Film film = findFilmByTitle(filmTitle);
            Room room = findRoomById(roomId);
            
            if (film == null || room == null) {
                return false;
            }
            
            // Find the screening
            Screening screeningToRemove = null;
            for (Screening screening : screenings) {
                if (screening.getFilm().equals(film) && 
                    screening.getRoom().equals(room) && 
                    screening.getScreeningTime().equals(screeningTime)) {
                    screeningToRemove = screening;
                    break;
                }
            }
            
            if (screeningToRemove == null) {
                return false;
            }
            
            // Remove from all places
            film.getScreenings().remove(screeningToRemove);
            room.getScreenings().remove(screeningToRemove);
            screenings.remove(screeningToRemove);
            
            return true;
            
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Helper method to find a room by its ID.
     * @param roomId the room ID to search for
     * @return the room if found, null otherwise
     */
    private Room findRoomById(String roomId) {
        return rooms.stream()
            .filter(room -> room.getRoomId().equals(roomId))
            .findFirst()
            .orElse(null);
    }

    /**
     * Helper method to find a film by its title.
     * @param title the film title to search for
     * @return the film if found, null otherwise
     */
    private Film findFilmByTitle(String title) {
        return films.stream()
            .filter(film -> film.getTitle().equals(title))
            .findFirst()
            .orElse(null);
    }
}