import java.util.ArrayList;
import java.util.Date;
import java.util.List;

enum Position {
    /**
     * Represents the position of a forward player.
     */
    FORWARD,

    /**
     * Represents the position of a midfield player.
     */
    MIDFIELD,

    /**
     * Represents the position of a defense player.
     */
    DEFENSE,

    /**
     * Represents the position of a goalkeeper.
     */
    GOALKEEPER;
}

enum AnnouncementType {
    /**
     * Represents a goal scoring announcement.
     */
    SCORE,

    /**
     * Represents a goal saving announcement.
     */
    SAVE;
}

class Player {
    private String name;
    private int age;
    private int number;
    private Position position;

    /**
     * Default constructor for the Player class.
     */
    public Player() {
    }

    /**
     * Gets the name of the player.
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the age of the player.
     * @return The age of the player.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the player.
     * @param age The age to set.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the number of the player.
     * @return The number of the player.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the number of the player.
     * @param number The number to set.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Gets the position of the player.
     * @return The position of the player.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the player.
     * @param position The position to set.
     */
    public void setPosition(Position position) {
        this.position = position;
    }
}

class Announcement {
    private Date time;
    private AnnouncementType type;
    private Player player;

    /**
     * Default constructor for the Announcement class.
     */
    public Announcement() {
    }

    /**
     * Gets the time of the announcement.
     * @return The time of the announcement.
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets the time of the announcement.
     * @param time The time to set.
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Gets the type of the announcement.
     * @return The type of the announcement.
     */
    public AnnouncementType getType() {
        return type;
    }

    /**
     * Sets the type of the announcement.
     * @param type The type to set.
     */
    public void setType(AnnouncementType type) {
        this.type = type;
    }

    /**
     * Gets the player associated with the announcement.
     * @return The player associated with the announcement.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player associated with the announcement.
     * @param player The player to set.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}

class FootballTeam {
    private List<Player> firstEleven;
    private List<Player> spareTeam;
    private List<Player> players;
    private List<Announcement> announcements;

    /**
     * Default constructor for the FootballTeam class.
     */
    public FootballTeam() {
        this.firstEleven = new ArrayList<>();
        this.spareTeam = new ArrayList<>();
        this.players = new ArrayList<>();
        this.announcements = new ArrayList<>();
    }

    /**
     * Calculates the total number of goal-scoring announcements for the starting eleven players.
     * @return The total number of goal-scoring announcements for the starting eleven players.
     */
    public int calculateTotalGoalScoringAnnouncementsForFirstEleven() {
        int count = 0;
        for (Announcement announcement : announcements) {
            if (announcement.getType() == AnnouncementType.SCORE && firstEleven.contains(announcement.getPlayer())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the midfielder player with the highest number.
     * @return The midfielder player with the highest number, or null if no midfielders are found.
     */
    public Player findMidfielderWithHighestNumber() {
        Player highestNumberMidfielder = null;
        for (Player player : players) {
            if (player.getPosition() == Position.MIDFIELD) {
                if (highestNumberMidfielder == null || player.getNumber() > highestNumberMidfielder.getNumber()) {
                    highestNumberMidfielder = player;
                }
            }
        }
        return highestNumberMidfielder;
    }

    /**
     * Calculates the average age of players in the spare team.
     * @return The average age of players in the spare team, or 0.0 if the spare team is empty.
     */
    public double calculateAverageAgeOfSpareTeam() {
        if (spareTeam.isEmpty()) {
            return 0.0;
        }
        int totalAge = 0;
        for (Player player : spareTeam) {
            totalAge += player.getAge();
        }
        return (double) totalAge / spareTeam.size();
    }

    /**
     * Counts the number of goal-saving announcements made by all goalkeepers on the team.
     * @return The number of goal-saving announcements made by all goalkeepers on the team.
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
     * @return The total number of announcements made by all forwards on the team.
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

    /**
     * Gets the list of first eleven players.
     * @return The list of first eleven players.
     */
    public List<Player> getFirstEleven() {
        return firstEleven;
    }

    /**
     * Sets the list of first eleven players.
     * @param firstEleven The list of first eleven players to set.
     */
    public void setFirstEleven(List<Player> firstEleven) {
        this.firstEleven = firstEleven;
    }

    /**
     * Gets the list of spare team players.
     * @return The list of spare team players.
     */
    public List<Player> getSpareTeam() {
        return spareTeam;
    }

    /**
     * Sets the list of spare team players.
     * @param spareTeam The list of spare team players to set.
     */
    public void setSpareTeam(List<Player> spareTeam) {
        this.spareTeam = spareTeam;
    }

    /**
     * Gets the list of all players.
     * @return The list of all players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Sets the list of all players.
     * @param players The list of all players to set.
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Gets the list of announcements.
     * @return The list of announcements.
     */
    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    /**
     * Sets the list of announcements.
     * @param announcements The list of announcements to set.
     */
    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }
}