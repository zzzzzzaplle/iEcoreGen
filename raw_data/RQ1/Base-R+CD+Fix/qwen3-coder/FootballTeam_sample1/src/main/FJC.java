import java.util.*;

/**
 * Represents a football team with players and announcements.
 */
class FootballTeam {
    private List<Player> firstEleven;
    private List<Player> spareTeam;
    private List<Player> players;
    private List<Announcement> announcements;

    /**
     * Default constructor initializes all lists.
     */
    public FootballTeam() {
        this.firstEleven = new ArrayList<>();
        this.spareTeam = new ArrayList<>();
        this.players = new ArrayList<>();
        this.announcements = new ArrayList<>();
    }

    /**
     * Calculates the total number of goal-scoring announcements for the starting eleven players.
     * Only counts announcements made by forwards in the starting eleven.
     *
     * @return The count of goal-scoring announcements by starting eleven forwards
     */
    public int calculateTotalGoalScoringAnnouncementsForFirstEleven() {
        int count = 0;
        for (Announcement announcement : announcements) {
            if (announcement.getType() == AnnouncementType.SCORE) {
                Player player = announcement.getPlayer();
                if (firstEleven.contains(player) && player.getPosition() == Position.FORWARD) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Finds the midfielder player with the highest number within all players of the football team.
     *
     * @return The midfielder with the highest number, or null if no midfielders exist
     */
    public Player findMidfielderWithHighestNumber() {
        Player midfielderWithHighestNumber = null;
        int highestNumber = Integer.MIN_VALUE;

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
     * Calculates the average age of players in the spare team.
     *
     * @return The average age as a floating-point number, or 0.0 if the spare team is empty
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
     *
     * @return The count of goal-saving announcements by goalkeepers, or 0 if none exist
     */
    public int calculateGoalSavingAnnouncementsByGoalkeeper() {
        int count = 0;
        for (Announcement announcement : announcements) {
            if (announcement.getType() == AnnouncementType.SAVE) {
                Player player = announcement.getPlayer();
                if (player.getPosition() == Position.GOALKEEPER) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Calculates the total number of announcements made by all forwards on the team.
     *
     * @return The total count of announcements by forwards, or 0 if there are no forwards or announcements
     */
    public int calculateTotalAnnouncementsForForwardPlayers() {
        int count = 0;
        for (Announcement announcement : announcements) {
            Player player = announcement.getPlayer();
            if (player.getPosition() == Position.FORWARD) {
                count++;
            }
        }
        return count;
    }

    // Getters and Setters
    public List<Player> getFirstEleven() {
        return firstEleven;
    }

    public void setFirstEleven(List<Player> firstEleven) {
        this.firstEleven = firstEleven;
    }

    public List<Player> getSpareTeam() {
        return spareTeam;
    }

    public void setSpareTeam(List<Player> spareTeam) {
        this.spareTeam = spareTeam;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }
}

/**
 * Enumeration representing different player positions.
 */
enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE,
    GOALKEEPER
}

/**
 * Represents a football player with personal and positional information.
 */
class Player {
    private String name;
    private int age;
    private int number;
    private Position position;

    /**
     * Default constructor.
     */
    public Player() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}

/**
 * Enumeration representing different types of announcements.
 */
enum AnnouncementType {
    SCORE,
    SAVE
}

/**
 * Represents an announcement made by a player during a game.
 */
class Announcement {
    private Date time;
    private AnnouncementType type;
    private Player player;

    /**
     * Default constructor.
     */
    public Announcement() {
    }

    // Getters and Setters
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public AnnouncementType getType() {
        return type;
    }

    public void setType(AnnouncementType type) {
        this.type = type;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}