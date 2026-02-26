import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 定义Room类
class Room {
    private String name;

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room room = (Room) obj;
        return name.equals(room.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

// 定义Film类
class Film {
    private String title;

    public Film(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Film film = (Film) obj;
        return title.equals(film.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}

// 定义Screening类
class Screening {
    private Film film;
    private Room room;
    private Date time;

    public Screening(Film film, Room room, Date time) {
        this.film = film;
        this.room = room;
        this.time = time;
    }

    public Film getFilm() {
        return film;
    }

    public Room getRoom() {
        return room;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Screening that = (Screening) obj;
        return film.equals(that.film) && room.equals(that.room) && time.equals(that.time);
    }

    @Override
    public int hashCode() {
        return film.hashCode() + room.hashCode() + time.hashCode();
    }
}

/**
 * Represents a cinema that manages rooms, films, and screenings.
 */
class Cinema {
    private List<Room> rooms;
    private List<Screening> screenings;
    private List<Film> films;

    /**
     * Constructs a new Cinema with empty lists for rooms, screenings, and films.
     */
    public Cinema() {
        this.rooms = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.films = new ArrayList<>();
    }

    /**
     * Adds a room to the cinema if it's not already registered.
     *
     * @param room the room to add
     * @return true if the room was added, false if it already exists
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
     * A room is available if it exists in the cinema and is not assigned to another screening at that time.
     *
     * @param room the room to check
     * @param time the time to check availability for
     * @return true if the room is available, false otherwise
     */
    public boolean checkAvailability(Room room, Date time) {
        if (!rooms.contains(room) || time == null) {
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
     * Adds a film to the cinema if it's not already registered.
     *
     * @param film the film to add
     * @return true if the film was added, false if it already exists
     */
    public boolean addFilm(Film film) {
        if (films.contains(film)) {
            return false;
        }
        films.add(film);
        return true;
    }

    /**
     * Removes a film from the cinema and all its future screenings.
     *
     * @param film the film to remove
     * @param currentTime the current time for comparison
     * @return true if the film was removed, false if it doesn't exist
     */
    public boolean removeFilm(Film film, Date currentTime) {
        if (!films.contains(film)) {
            return false;
        }

        // 删除所有未来排片
        screenings.removeIf(screening -> 
            screening.getFilm().equals(film) && 
            !screening.getTime().before(currentTime)
        );

        // 从电影列表中删除电影
        films.remove(film);
        return true;
    }

    /**
     * Assigns a screening to a film and room at a specific time.
     *
     * @param film the film to screen
     * @param room the room to use
     * @param screeningTime the time of the screening
     * @param currentTime the current time
     * @return true if the screening was assigned, false otherwise
     */
    public boolean assignScreening(Film film, Room room, Date screeningTime, Date currentTime) {
        // 检查当前时间应在放映时间之前
        if (!currentTime.before(screeningTime)) {
            return false;
        }

        // 检查电影是否已添加到影院
        if (!films.contains(film)) {
            return false;
        }

        // 检查房间在该时间是否可用
        if (!checkAvailability(room, screeningTime)) {
            return false;
        }

        // 添加放映安排
        screenings.add(new Screening(film, room, screeningTime));
        return true;
    }

    /**
     * Cancels a future screening.
     *
     * @param screening the screening to cancel
     * @param currentTime the current time for comparison
     * @return true if the screening was canceled, false otherwise
     */
    public boolean cancelScreening(Screening screening, Date currentTime) {
        // 检查放映是否存在
        if (!screenings.contains(screening)) {
            return false;
        }

        // 检查放映时间是否在当前时间之后
        if (!screening.getTime().after(currentTime)) {
            return false;
        }

        // 取消放映
        screenings.remove(screening);
        return true;
    }
}