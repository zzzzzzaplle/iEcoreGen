import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

/**
 * Enumeration for player positions.
 */
enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE,
    GOALKEEPER
}

/**
 * Enumeration for announcement types.
 */
enum AnnouncementType {
    SCORE,
    SAVE
}

/**
 * Represents a football player with personal details and position.
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

    /** @return the player's name */
    public String getName() {
        return name;
    }

    /** @param name the player's name */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the player's age */
    public int getAge() {
        return age;
    }

    /** @param age the player's age */
    public void setAge(int age) {
        this.age = age;
    }

    /** @return the player's jersey number */
    public int getNumber() {
        return number;
    }

    /** @param number the player's jersey number */
    public void setNumber(int number) {
        this.number = number;
    }

    /** @return the player's position */
    public Position getPosition() {
        return position;
    }

    /** @param position the player's position */
    public void setPosition(Position position) {
        this.position = position;
    }
}

/**
 * Represents an announcement made during a match (e.g., goal scored, goal saved).
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

    /** @return the time of the announcement */
    public Date getTime() {
        return time;
    }

    /** @param time the time of the announcement */
    public void setTime(Date time) {
        this.time = time;
    }

    /** @return the type of announcement */
    public AnnouncementType getType() {
        return type;
    }

    /** @param type the type of announcement */
    public void setType(AnnouncementType type) {
        this.type = type;
    }

    /** @return the player associated with this announcement */
    public Player getPlayer() {
        return player;
    }

    /** @param player the player associated with this announcement */
    public void setPlayer(Player player) {
        this.player = player;
    }
}

/**
 * Represents a football team with its squad and match announcements.
 */
class FootballTeam {

    private List<Player> firstEleven;
    private List<Player> spareTeam;
    private List<Player> players;
    private List<Announcement> announcements;

    /**
     * Default constructor. Initializes all collections.
     */
    public FootballTeam() {
        this.firstEleven = new ArrayList<>();
        this.spareTeam = new ArrayList<>();
        this.players = new ArrayList<>();
        this.announcements = new ArrayList<>();
    }

    /** @return the list of first eleven players */
    public List<Player> getFirstEleven() {
        return firstEleven;
    }

    /** @param firstEleven the list of first eleven players */
    public void setFirstEleven(List<Player> firstEleven) {
        this.firstEleven = firstEleven;
    }

    /** @return the list of spare (reserve) players */
    public List<Player> getSpareTeam() {
        return spareTeam;
    }

    /** @param spareTeam the list of spare (reserve) players */
    public void setSpareTeam(List<Player> spareTeam) {
        this.spareTeam = spareTeam;
    }

    /** @return the list of all players in the team */
    public List<Player> getPlayers() {
        return players;
    }

    /** @param players the list of all players in the team */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /** @return the list of all announcements made during matches */
    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    /** @param announcements the list of all announcements made during matches */
    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    /**
     * Calculates the total number of goal-scoring announcements made by the
     * starting eleven players. Only forwards can generate score announcements,
     * so announcements from other positions are ignored.
     *
     * @return the count of scoring announcements for the first eleven
     */
    public int calculateTotalGoalScoringAnnouncementsForFirstEleven() {
        return (int) announcements.stream()
                .filter(a -> a.getType() == AnnouncementType.SCORE)
                .filter(a -> firstEleven.contains(a.getPlayer()))
                .filter(a -> a.getPlayer().getPosition() == Position.FORWARD)
                .count();
    }

    /**
     * Finds the midfielder with the highest jersey number in the entire team.
     *
     * @return the midfielder with the highest number, or {@code null} if no midfielders exist
     */
    public Player findMidfielderWithHighestNumber() {
        return players.stream()
                .filter(p -> p.getPosition() == Position.MIDFIELD)
                .max((p1, p2) -> Integer.compare(p1.getNumber(), p2.getNumber()))
                .orElse(null);
    }

    /**
     * Calculates the average age of players in the spare team.
     *
     * @return the average age as a {@code double}. Returns {@code 0.0} if the spare team is empty.
     */
    public double calculateAverageAgeOfSpareTeam() {
        OptionalDouble avg = spareTeam.stream()
                .mapToInt(Player::getAge)
                .average();
        return avg.isPresent() ? avg.getAsDouble() : 0.0;
    }

    /**
     * Counts the number of goal-saving announcements made by all goalkeepers
     * in the team. Only announcements generated by players whose position is
     * {@code GOALKEEPER} are considered.
     *
     * @return the count of goal-saving announcements by goalkeepers
     */
    public int calculateGoalSavingAnnouncementsByGoalkeeper() {
        return (int) announcements.stream()
                .filter(a -> a.getType() == AnnouncementType.SAVE)
                .filter(a -> a.getPlayer().getPosition() == Position.GOALKEEPER)
                .count();
    }

    /**
     * Calculates the total number of announcements made by all forwards on the team.
     *
     * @return the count of announcements for forward players
     */
    public int calculateTotalAnnouncementsForForwardPlayers() {
        return (int) announcements.stream()
                .filter(a -> a.getPlayer().getPosition() == Position.FORWARD)
                .count();
    }
}