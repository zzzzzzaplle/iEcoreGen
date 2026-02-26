import java.util.*;

/**
 * Represents a football team with players, announcements, and various operations.
 */
 class FootballTeam {
    private List<Player> firstEleven;
    private List<Player> spareTeam;
    private List<Player> players;
    private List<Announcement> announcements;

    /**
     * Default constructor initializes all lists as empty ArrayLists.
     */
    public FootballTeam() {
        this.firstEleven = new ArrayList<>();
        this.spareTeam = new ArrayList<>();
        this.players = new ArrayList<>();
        this.announcements = new ArrayList<>();
    }

    /**
     * Calculates the total number of goal-scoring announcements made by forwards in the starting eleven.
     * Only considers players in the firstEleven list with position FORWARD and announcement type SCORE.
     *
     * @return the count of goal-scoring announcements by forwards in the starting eleven
     */
    public int calculateTotalGoalScoringAnnouncementsForFirstEleven() {
        int count = 0;
        for (Announcement announcement : announcements) {
            if (announcement.getType() == AnnouncementType.SCORE) {
                Player player = announcement.getPlayer();
                if (player.getPosition() == Position.FORWARD && firstEleven.contains(player)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Finds the midfielder player with the highest number within all players of this football team.
     * 
     * @return the midfielder with the highest number, or null if no midfielders are found
     */
    public Player findMidfielderWithHighestNumber() {
        Player highestNumberMidfielder = null;
        int maxNumber = Integer.MIN_VALUE;
        
        for (Player player : players) {
            if (player.getPosition() == Position.MIDFIELD) {
                if (player.getNumber() > maxNumber) {
                    maxNumber = player.getNumber();
                    highestNumberMidfielder = player;
                }
            }
        }
        
        return highestNumberMidfielder;
    }

    /**
     * Calculates the average age of players in the spare team.
     * 
     * @return the average age as a double, or 0.0 if the spare team is empty
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
     * Counts the number of goal-saving announcements made by goalkeepers on the team.
     * 
     * @return the count of goal-saving announcements by goalkeepers, or 0 if none exist
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
     * @return the total count of announcements by forwards, or 0 if there are no forwards or no announcements
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

    // Getters and setters
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
 * Enumeration representing the possible positions a player can have.
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
     * Default constructor initializes all fields to default values.
     */
    public Player() {
        this.name = "";
        this.age = 0;
        this.number = 0;
        this.position = null;
    }

    // Getters and setters
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
 * Enumeration representing the types of announcements that can be made.
 */
enum AnnouncementType {
    SCORE,
    SAVE
}

/**
 * Represents an announcement made during a game, either a score or a save.
 */
 class Announcement {
    private Date time;
    private AnnouncementType type;
    private Player player;

    /**
     * Default constructor initializes all fields to default values.
     */
    public Announcement() {
        this.time = new Date();
        this.type = null;
        this.player = null;
    }

    // Getters and setters
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