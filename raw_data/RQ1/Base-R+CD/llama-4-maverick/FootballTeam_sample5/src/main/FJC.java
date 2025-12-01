import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a football team with players and announcements.
 */
class FootballTeam {
    private List<Player> firstEleven;
    private List<Player> spareTeam;
    private List<Player> players;
    private List<Announcement> announcements;

    /**
     * Default constructor for FootballTeam.
     */
    public FootballTeam() {
        this.firstEleven = new ArrayList<>();
        this.spareTeam = new ArrayList<>();
        this.players = new ArrayList<>();
        this.announcements = new ArrayList<>();
    }

    /**
     * Gets the first eleven players of the team.
     * @return the first eleven players
     */
    public List<Player> getFirstEleven() {
        return firstEleven;
    }

    /**
     * Sets the first eleven players of the team.
     * @param firstEleven the first eleven players to set
     */
    public void setFirstEleven(List<Player> firstEleven) {
        this.firstEleven = firstEleven;
    }

    /**
     * Gets the spare team players.
     * @return the spare team players
     */
    public List<Player> getSpareTeam() {
        return spareTeam;
    }

    /**
     * Sets the spare team players.
     * @param spareTeam the spare team players to set
     */
    public void setSpareTeam(List<Player> spareTeam) {
        this.spareTeam = spareTeam;
    }

    /**
     * Gets all players of the team.
     * @return all players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Sets all players of the team.
     * @param players the players to set
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Gets all announcements made by the team.
     * @return all announcements
     */
    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    /**
     * Sets all announcements made by the team.
     * @param announcements the announcements to set
     */
    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    /**
     * Calculates the total number of goal-scoring announcements for the starting eleven players.
     * @return the total number of goal-scoring announcements
     */
    public int calculateTotalGoalScoringAnnouncementsForFirstEleven() {
        int count = 0;
        for (Player player : firstEleven) {
            if (player.getPosition() == Position.FORWARD) {
                for (Announcement announcement : announcements) {
                    if (announcement.getPlayer().equals(player) && announcement.getType() == AnnouncementType.SCORE) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Finds the midfielder with the highest number among all players.
     * @return the midfielder with the highest number, or null if no midfielders are found
     */
    public Player findMidfielderWithHighestNumber() {
        Player midfielderWithHighestNumber = null;
        int highestNumber = 0;
        for (Player player : players) {
            if (player.getPosition() == Position.MIDFIELD) {
                if (player.getNumber() > highestNumber) {
                    highestNumber = player.getNumber();
                    midfielderWithHighestNumber = player;
                }
            }
        }
        return midfielderWithHighestNumber;
    }

    /**
     * Calculates the average age of the spare team players.
     * @return the average age, or 0.0 if the spare team is empty
     */
    public double calculateAverageAgeOfSpareTeam() {
        if (spareTeam.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (Player player : spareTeam) {
            sum += player.getAge();
        }
        return (double) sum / spareTeam.size();
    }

    /**
     * Counts the number of goal-saving announcements made by all goalkeepers on the team.
     * @return the number of goal-saving announcements
     */
    public int calculateGoalSavingAnnouncementsByGoalkeeper() {
        int count = 0;
        for (Announcement announcement : announcements) {
            if (announcement.getType() == AnnouncementType.SAVE && announcement.getPlayer().getPosition() == Position.GOALKEEPER) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the total number of announcements made by all forwards on the team.
     * @return the total number of announcements
     */
    public int calculateTotalAnnouncementsForForwardPlayers() {
        int count = 0;
        for (Announcement announcement : announcements) {
            if (announcement.getPlayer().getPosition() == Position.FORWARD) {
                count++;
            }
        }
        return count;
    }
}

/**
 * Enum representing the position of a player.
 */
enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE,
    GOALKEEPER
}

/**
 * Represents a football player with name, age, number, and position.
 */
class Player {
    private String name;
    private int age;
    private int number;
    private Position position;

    /**
     * Default constructor for Player.
     */
    public Player() {}

    /**
     * Gets the name of the player.
     * @return the name
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
     * @return the age
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
     * @return the number
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
     * @return the position
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
 * Enum representing the type of an announcement.
 */
enum AnnouncementType {
    SCORE,
    SAVE
}

/**
 * Represents an announcement made by a player.
 */
class Announcement {
    private Date time;
    private AnnouncementType type;
    private Player player;

    /**
     * Default constructor for Announcement.
     */
    public Announcement() {}

    /**
     * Gets the time of the announcement.
     * @return the time
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
     * @return the type
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
     * Gets the player who made the announcement.
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player who made the announcement.
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}