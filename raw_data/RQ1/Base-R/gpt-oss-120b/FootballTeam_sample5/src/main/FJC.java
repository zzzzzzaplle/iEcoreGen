import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Represents a generic football player.
 */
public abstract class FootballPlayer {

    /** The team name the player belongs to. */
    private String team;

    /** Player's full name. */
    private String name;

    /** Player's age. */
    private int age;

    /** Player's jersey number. */
    private int number;

    /** Position of the player on the field. */
    private Position position;

    /** No‑argument constructor required by the specification. */
    public FootballPlayer() {
        // default values
    }

    /** Full constructor for convenience. */
    public FootballPlayer(String team, String name, int age, int number, Position position) {
        this.team = team;
        this.name = name;
        this.age = age;
        this.number = number;
        this.position = position;
    }

    /* ---------- Getters & Setters ---------- */

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

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

    /* ---------- Helper methods that may be overridden ---------- */

    /**
     * Returns the number of goal‑scoring announcements produced by this player.
     * Default implementation returns {@code 0}. Sub‑classes that can score
     * (e.g., {@link Striker}) should override.
     *
     * @return number of goal‑scoring announcements
     */
    public int getGoalScoringAnnouncements() {
        return 0;
    }

    /**
     * Returns the number of goal‑saving announcements produced by this player.
     * Default implementation returns {@code 0}. Sub‑classes that can save
     * (e.g., {@link Goalkeeper}) should override.
     *
     * @return number of goal‑saving announcements
     */
    public int getGoalSavingAnnouncements() {
        return 0;
    }
}

/**
 * Enum describing possible positions on the pitch.
 */
enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE
}

/**
 * Striker – a forward player who can score goals.
 */
class Striker extends FootballPlayer {

    /** Counter for how many times this striker has generated a scoring announcement. */
    private int goalScoringAnnouncements = 0;

    /** No‑argument constructor. */
    public Striker() {
        super();
        setPosition(Position.FORWARD);
    }

    /** Full constructor. */
    public Striker(String team, String name, int age, int number) {
        super(team, name, age, number, Position.FORWARD);
    }

    /**
     * Simulates scoring a goal. The method increments the internal counter and
     * returns an announcement string containing the player's number and name.
     *
     * @return announcement string in the form "Goal! #<number> <name>"
     */
    public String scoreGoal() {
        goalScoringAnnouncements++;
        return "Goal! #" + getNumber() + " " + getName();
    }

    @Override
    public int getGoalScoringAnnouncements() {
        return goalScoringAnnouncements;
    }

    /* ---------- Getters & Setters for the counter ---------- */

    public int getGoalScoringAnnouncementsCount() {
        return goalScoringAnnouncements;
    }

    public void setGoalScoringAnnouncementsCount(int goalScoringAnnouncements) {
        this.goalScoringAnnouncements = goalScoringAnnouncements;
    }
}

/**
 * Midfielder – a player who distributes the ball.
 */
class Midfielder extends FootballPlayer {

    /** No‑argument constructor. */
    public Midfielder() {
        super();
        setPosition(Position.MIDFIELD);
    }

    /** Full constructor. */
    public Midfielder(String team, String name, int age, int number) {
        super(team, name, age, number, Position.MIDFIELD);
    }
}

/**
 * Goalkeeper – a defensive player who can save goals.
 */
class Goalkeeper extends FootballPlayer {

    /** Counter for how many times this goalkeeper has generated a saving announcement. */
    private int goalSavingAnnouncements = 0;

    /** No‑argument constructor. */
    public Goalkeeper() {
        super();
        setPosition(Position.DEFENSE);
    }

    /** Full constructor. */
    public Goalkeeper(String team, String name, int age, int number) {
        super(team, name, age, number, Position.DEFENSE);
    }

    /**
     * Simulates saving a goal. The method increments the internal counter and
     * returns an announcement string containing the player's number and name.
     *
     * @return announcement string in the form "Saved! #<number> <name>"
     */
    public String saveGoal() {
        goalSavingAnnouncements++;
        return "Saved! #" + getNumber() + " " + getName();
    }

    @Override
    public int getGoalSavingAnnouncements() {
        return goalSavingAnnouncements;
    }

    /* ---------- Getters & Setters for the counter ---------- */

    public int getGoalSavingAnnouncementsCount() {
        return goalSavingAnnouncements;
    }

    public void setGoalSavingAnnouncementsCount(int goalSavingAnnouncements) {
        this.goalSavingAnnouncements = goalSavingAnnouncements;
    }
}

/**
 * Represents a football team, containing all players as well as the
 * starting eleven and the spare (reserve) team.
 */
class FootballTeam {

    /** Name of the team. */
    private String teamName;

    /** All players belonging to the team. */
    private List<FootballPlayer> allPlayers = new ArrayList<>();

    /** Players selected for the starting eleven. */
    private List<FootballPlayer> startingEleven = new ArrayList<>();

    /** Players that are part of the spare/reserve team. */
    private List<FootballPlayer> spareTeam = new ArrayList<>();

    /** No‑argument constructor. */
    public FootballTeam() {
        // default constructor
    }

    /** Constructor with team name. */
    public FootballTeam(String teamName) {
        this.teamName = teamName;
    }

    /* ---------- Getters & Setters ---------- */

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<FootballPlayer> getAllPlayers() {
        return allPlayers;
    }

    public void setAllPlayers(List<FootballPlayer> allPlayers) {
        this.allPlayers = allPlayers;
    }

    public List<FootballPlayer> getStartingEleven() {
        return startingEleven;
    }

    public void setStartingEleven(List<FootballPlayer> startingEleven) {
        this.startingEleven = startingEleven;
    }

    public List<FootballPlayer> getSpareTeam() {
        return spareTeam;
    }

    public void setSpareTeam(List<FootballPlayer> spareTeam) {
        this.spareTeam = spareTeam;
    }

    /* ---------- Player Management ---------- */

    /**
     * Adds a player to the team. The player is automatically added to the
     * collection of all players.
     *
     * @param player the player to add
     */
    public void addPlayer(FootballPlayer player) {
        if (player != null) {
            allPlayers.add(player);
        }
    }

    /**
     * Assigns a player to the starting eleven. The player must already be
     * part of {@link #allPlayers}.
     *
     * @param player the player to be promoted to starter
     */
    public void addStarter(FootballPlayer player) {
        if (player != null && allPlayers.contains(player) && !startingEleven.contains(player)) {
            startingEleven.add(player);
        }
    }

    /**
     * Assigns a player to the spare team. The player must already be part of
     * {@link #allPlayers}.
     *
     * @param player the player to be placed in the reserve
     */
    public void addSpare(FootballPlayer player) {
        if (player != null && allPlayers.contains(player) && !spareTeam.contains(player)) {
            spareTeam.add(player);
        }
    }

    /* ---------- Functional Requirements ---------- */

    /**
     * Calculates the total number of goal‑scoring announcements generated by
     * the starting eleven players of this team. Only forwards (i.e., {@link Striker})
     * can generate such announcements; goals from other positions are ignored.
     *
     * @return total number of scoring announcements for the starting eleven
     */
    public int totalGoalScoringAnnouncementsStartingEleven() {
        int total = 0;
        for (FootballPlayer p : startingEleven) {
            if (p instanceof Striker) {
                total += p.getGoalScoringAnnouncements();
            }
        }
        return total;
    }

    /**
     * Finds the midfielder with the highest jersey number among all players of
     * the team. If no midfielder exists, {@code null} is returned.
     *
     * @return the midfielder with the highest number, or {@code null} if none
     */
    public Midfielder findMidfielderWithHighestNumber() {
        Midfielder best = null;
        for (FootballPlayer p : allPlayers) {
            if (p instanceof Midfielder) {
                Midfielder m = (Midfielder) p;
                if (best == null || m.getNumber() > best.getNumber()) {
                    best = m;
                }
            }
        }
        return best;
    }

    /**
     * Calculates the average age of the players that belong to the spare team.
     * If the spare team is empty, {@code 0.0} is returned.
     *
     * @return average age of spare‑team players as a {@code double}
     */
    public double averageAgeSpareTeam() {
        if (spareTeam.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (FootballPlayer p : spareTeam) {
            sum += p.getAge();
        }
        return (double) sum / spareTeam.size();
    }

    /**
     * Counts the total number of goal‑saving announcements made by all goalkeepers
     * on the team. Only players whose position is {@link Position#DEFENSE}
     * (i.e., {@link Goalkeeper}) are considered.
     *
     * @return total number of saving announcements, or {@code 0} if none
     */
    public int totalGoalSavingAnnouncements() {
        int total = 0;
        for (FootballPlayer p : allPlayers) {
            if (p instanceof Goalkeeper) {
                total += p.getGoalSavingAnnouncements();
            }
        }
        return total;
    }

    /**
     * Calculates the total number of announcements made by all forwards on the
     * team. Forwards are represented by {@link Striker} instances. If there are
     * no forwards or no announcements, {@code 0} is returned.
     *
     * @return total number of forward announcements
     */
    public int totalAnnouncementsByForwards() {
        int total = 0;
        for (FootballPlayer p : allPlayers) {
            if (p instanceof Striker) {
                total += p.getGoalScoringAnnouncements();
            }
        }
        return total;
    }
}