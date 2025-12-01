import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Represents a football team with players and announcements
 */
class FootballTeam {
    private List<Player> firstEleven;
    private List<Player> spareTeam;
    private List<Player> players;
    private List<Announcement> announcements;

    /**
     * Default constructor initializing empty lists
     */
    public FootballTeam() {
        this.firstEleven = new ArrayList<>();
        this.spareTeam = new ArrayList<>();
        this.players = new ArrayList<>();
        this.announcements = new ArrayList<>();
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

    /**
     * Calculates the total number of goal-scoring announcements for the starting eleven players
     * Only announcements from forwards in the starting eleven are counted
     *
     * @return total number of goal-scoring announcements by forwards in starting eleven
     */
    public int calculateTotalGoalScoringAnnouncementsForFirstEleven() {
        int count = 0;
        for (Announcement announcement : announcements) {
            if (announcement.getType() == AnnouncementType.SCORE && 
                firstEleven.contains(announcement.getPlayer()) && 
                announcement.getPlayer().getPosition() == Position.FORWARD) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the midfielder player with the highest number within all players of the team
     *
     * @return midfielder player with highest number, or null if no midfielders found
     */
    public Player findMidfielderWithHighestNumber() {
        Optional<Player> highestNumberMidfielder = players.stream()
            .filter(player -> player.getPosition() == Position.MIDFIELD)
            .max((p1, p2) -> Integer.compare(p1.getNumber(), p2.getNumber()));
        
        return highestNumberMidfielder.orElse(null);
    }

    /**
     * Calculates the average age of players in the spare team
     *
     * @return average age of spare team players as double, 0.0 if spare team is empty
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
     * Counts the number of goal-saving announcements made by all goalkeepers on the team
     *
     * @return number of goal-saving announcements by goalkeepers, 0 if none exist
     */
    public int calculateGoalSavingAnnouncementsByGoalkeeper() {
        int count = 0;
        for (Announcement announcement : announcements) {
            if (announcement.getType() == AnnouncementType.SAVE && 
                announcement.getPlayer().getPosition() == Position.GOALKEEPER) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the total number of announcements made by all forwards on the team
     *
     * @return total number of announcements by forwards, 0 if no forwards or announcements
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
 * Represents player positions in football
 */
enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE,
    GOALKEEPER
}

/**
 * Represents a football player with personal and professional attributes
 */
class Player {
    private String name;
    private int age;
    private int number;
    private Position position;

    /**
     * Default constructor
     */
    public Player() {
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
 * Represents types of announcements in the football system
 */
enum AnnouncementType {
    SCORE,
    SAVE
}

/**
 * Represents an announcement made during football matches
 */
class Announcement {
    private Date time;
    private AnnouncementType type;
    private Player player;

    /**
     * Default constructor
     */
    public Announcement() {
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