// ------------------------------------------------------------
// File: Room.java
// ------------------------------------------------------------
package com.cinema;

import java.util.Objects;

/**
 * Represents a cinema room.
 */
 class Room {

    /** Name or identifier of the room. */
    private String name;

    /** Unparameterized constructor. */
    public Room() {
        // Default constructor
    }

    /**
     * Returns the name of the room.
     *
     * @return the room name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the room.
     *
     * @param name the room name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Two rooms are considered equal if they have the same name (case‑sensitive).
     *
     * @param o the other object
     * @return true if the rooms have the same name
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;
        return Objects.equals(name, room.name);
    }

    /**
     * Hash code based on the room name.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Returns a string representation of the room.
     *
     * @return string describing the room
     */
    @Override
    public String toString() {
        return "Room{name='" + name + '\'' + '}';
    }
}


// ------------------------------------------------------------
// File: Film.java
// ------------------------------------------------------------
package com.cinema;

import java.util.Objects;

/**
 * Represents a film that can be shown in a cinema.
 */
 class Film {

    /** Title of the film. */
    private String title;

    /** Unparameterized constructor. */
    public Film() {
        // Default constructor
    }

    /**
     * Returns the title of the film.
     *
     * @return the film title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the film.
     *
     * @param title the film title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Two films are considered equal if they have the same title (case‑sensitive).
     *
     * @param o the other object
     * @return true if the films have the same title
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;
        return Objects.equals(title, film.title);
    }

    /**
     * Hash code based on the film title.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    /**
     * Returns a string representation of the film.
     *
     * @return string describing the film
     */
    @Override
    public String toString() {
        return "Film{title='" + title + '\'' + '}';
    }
}


// ------------------------------------------------------------
// File: Screening.java
// ------------------------------------------------------------
package com.cinema;

import java.util.Date;

/**
 * Represents a screening of a film in a specific room at a specific time.
 */
 class Screening {

    /** The date and time when the screening starts. */
    private Date time;

    /** The film that will be shown. */
    private Film film;

    /** The room where the screening will take place. */
    private Room room;

    /** Unparameterized constructor. */
    public Screening() {
        // Default constructor
    }

    /**
     * Returns the screening time.
     *
     * @return the time of the screening
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets the screening time.
     *
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Returns the film associated with this screening.
     *
     * @return the film
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Sets the film for this screening.
     *
     * @param film the film to set
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * Returns the room where this screening will be held.
     *
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room for this screening.
     *
     * @param room the room to set
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Returns a string representation of the screening.
     *
     * @return string describing the screening
     */
    @Override
    public String toString() {
        return "Screening{time=" + time + ", film=" + film + ", room=" + room + '}';
    }
}


// ------------------------------------------------------------
// File: Cinema.java
// ------------------------------------------------------------
package com.cinema;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a cinema that manages rooms, films and screenings.
 */
 class Cinema {

    /** List of rooms owned by the cinema. */
    private List<Room> rooms = new ArrayList<>();

    /** List of films known to the cinema. */
    private List<Film> films = new ArrayList<>();

    /** List of scheduled screenings. */
    private List<Screening> screenings = new ArrayList<>();

    /** Unparameterized constructor. */
    public Cinema() {
        // Default constructor
    }

    /**
     * Returns the list of rooms.
     *
     * @return list of rooms
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Sets the list of rooms.
     *
     * @param rooms the rooms to set
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Returns the list of films.
     *
     * @return list of films
     */
    public List<Film> getFilms() {
        return films;
    }

    /**
     * Sets the list of films.
     *
     * @param films the films to set
     */
    public void setFilms(List<Film> films) {
        this.films = films;
    }

    /**
     * Returns the list of screenings.
     *
     * @return list of screenings
     */
    public List<Screening> getScreenings() {
        return screenings;
    }

    /**
     * Sets the list of screenings.
     *
     * @param screenings the screenings to set
     */
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    /**
     * Adds a room to the cinema.
     *
     * <p>If the room is not already registered, it is added and {@code true} is returned.
     * If the room already exists (or the argument is {@code null}), {@code false} is returned.</p>
     *
     * @param room the room to add
     * @return {@code true} if the room was added; {@code false} otherwise
     */
    public boolean addRoom(Room room) {
        if (room == null) {
            return false;
        }
        if (rooms.contains(room)) {
            return false;
        }
        rooms.add(room);
        return true;
    }

    /**
     * Checks whether a given room is available at a specific time.
     *
     * <p>The method verifies that the room is part of the cinema and that no other
     * screening is already scheduled for the same room at the exact same time.</p>
     *
     * @param room the room to check
     * @param time the time to check (must not be {@code null})
     * @return {@code true} if the room is available; {@code false} otherwise
     */
    public boolean checkAvailability(Room room, Date time) {
        if (room == null || time == null) {
            return false;
        }
        if (!rooms.contains(room)) {
            return false;
        }
        for (Screening s : screenings) {
            if (room.equals(s.getRoom()) && time.equals(s.getTime())) {
                return false; // already assigned at that time
            }
        }
        return true;
    }

    /**
     * Adds a film to the cinema.
     *
     * <p>If the film is not already present, it is added and {@code true} is returned.
     * If the film already exists (or the argument is {@code null}), {@code false} is returned.</p>
     *
     * @param film the film to add
     * @return {@code true} if the film was added; {@code false} otherwise
     */
    public boolean addFilm(Film film) {
        if (film == null) {
            return false;
        }
        if (films.contains(film)) {
            return false;
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film from the cinema system at the specified current time.
     *
     * <p>All future scheduled screenings of the film (screening time &gt;= {@code currentTime})
     * are also removed.</p>
     *
     * @param film        the film to remove
     * @param currentTime the current time used as a cutoff for future screenings
     * @return {@code true} if the film existed and was removed; {@code false} otherwise
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (film == null || currentTime == null) {
            return false;
        }
        if (!films.contains(film)) {
            return false;
        }

        // Remove the film from the list of films
        films.remove(film);

        // Remove all future screenings of this film
        Iterator<Screening> it = screenings.iterator();
        while (it.hasNext()) {
            Screening s = it.next();
            if (film.equals(s.getFilm()) && !s.getTime().before(currentTime)) {
                // screening time >= currentTime
                it.remove();
            }
        }
        return true;
    }

    /**
     * Assigns a screening to a film and a room at a specific screening time.
     *
     * <p>Validation steps:</p>
     * <ul>
     *   <li>The {@code currentTime} must be before the screening time.</li>
     *   <li>The film must already be added to the cinema.</li>
     *   <li>The room must be part of the cinema and be available at the screening time.</li>
     * </ul>
     *
     * @param film        the film to be screened
     * @param room        the room where the screening will take place
     * @param screening   the screening object (its {@code time} must be set)
     * @param currentTime the current time used for validation
     * @return {@code true} if the screening was successfully assigned; {@code false} otherwise
     */
    public boolean assignScreening(Film film, Room room, Screening screening, Date currentTime) {
        if (film == null || room == null || screening == null || currentTime == null) {
            return false;
        }
        Date screeningTime = screening.getTime();
        if (screeningTime == null) {
            return false;
        }
        // current time must be before screening time
        if (!currentTime.before(screeningTime)) {
            return false;
        }
        // film must be registered
        if (!films.contains(film)) {
            return false;
        }
        // room must be registered and available
        if (!rooms.contains(room) || !checkAvailability(room, screeningTime)) {
            return false;
        }

        // Set the film and room references in the screening (in case they are not set)
        screening.setFilm(film);
        screening.setRoom(room);

        // Add the screening to the list
        screenings.add(screening);
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * <p>The method checks that the screening exists in the cinema and that its scheduled
     * time is after the provided {@code currentTime}. If both conditions hold, the
     * screening is removed.</p>
     *
     * @param screening   the screening to cancel
     * @param currentTime the current time used for validation
     * @return {@code true} if the screening was successfully canceled; {@code false} otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        if (screening == null || currentTime == null) {
            return false;
        }
        if (!screenings.contains(screening)) {
            return false;
        }
        Date screeningTime = screening.getTime();
        if (screeningTime == null) {
            return false;
        }
        // Screening must be in the future relative to currentTime
        if (!screeningTime.after(currentTime)) {
            return false;
        }

        // Remove the screening
        screenings.remove(screening);
        return true;
    }
}