import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Enum representing different positions in a football team.
 */
enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE,
    GOALKEEPER
}

/**
 * Enum representing different types of announcements.
 */
enum AnnouncementType {
    SCORE,
    SAVE
}

/**
 * Represents a player in the football team.
 */
class Player {
    private String name;
    private int age;
    private int number;
    private Position position;

    /**
     * Unparameterized constructor for Player.
     */
    public Player() {}

    /**
     * Gets the name of the player.
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the age of the player.
     * @return the age of the player
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the player.
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the number of the player.
     * @return the number of the player
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the number of the player.
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Gets the position of the player.
     * @return the position of the player
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the player.
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }
}

/**
 * Represents an announcement made during a game.
 */
class Announcement {
    private Date time;
    private AnnouncementType type;
    private Player player;

    /**
     * Unparameterized constructor for Announcement.
     */
    public Announcement() {}

    /**
     * Gets the time of the announcement.
     * @return the time of the announcement
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets the time of the announcement.
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Gets the type of the announcement.
     * @return the type of the announcement
     */
    public AnnouncementType getType() {
        return type;
    }

    /**
     * Sets the type of the announcement.
     * @param type the type to set
     */
    public void setType(AnnouncementType type) {
        this.type = type;
    }

    /**
     * Gets the player assoc