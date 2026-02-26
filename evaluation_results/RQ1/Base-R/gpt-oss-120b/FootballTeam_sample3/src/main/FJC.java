import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Enumeration of football positions.
 */
enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE   // includes Goalkeeper
}

/**
 * Base class representing a football player.
 */
class FootballPlayer {

    private String team;
    private String name;
    private int age;
    private int number;
    private Position position;

    /** Number of goal‑scoring announcements (relevant for forwards). */
    private int scoreAnnouncements;

    /** Number of goal‑saving announcements (relevant for goalkeepers). */
    private int saveAnnouncements;

    /** No‑arg constructor required by the specification. */
    public FootballPlayer() {
    }

    public FootballPlayer(String team, String name, int age, int number, Position position) {
        this.team = team;
        this.name = name;
        this.age = age;
        this.number = number;
        this.position = position;
    }

    // ------------------------------------------------------------------------
    // Getters and Setters
    // ------------------------------------------------------------------------
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

    public int getScoreAnnouncements() {
        return scoreAnnouncements;
    }

    public void setScoreAnnouncements(int scoreAnnouncements) {
        this.scoreAnnouncements = scoreAnnouncements;
    }

    public int getSaveAnnouncements() {
        return saveAnnouncements;
    }

    public void setSaveAnnouncements(int saveAnnouncements) {
        this.saveAnnouncements = saveAnnouncements;
    }

    // ------------------------------------------------------------------------
    // Behaviour helpers (used by subclasses)
    // ------------------------------------------------------------------------
    /**
     * Increments the number of goal‑scoring announcements for this player.
     * Sub‑classes representing forwards should call this method when a goal is
     * scored.
     */
    protected void addScoreAnnouncement() {
        this.scoreAnnouncements++;
    }

    /**
     * Increments the number of goal‑saving announcements for this player.
     * Sub‑classes representing goalkeepers should call this method when a save
     * is made.
     */
    protected void addSaveAnnouncement() {
        this.saveAnnouncements++;
    }
}

/**
 * Class representing a forward (striker) player.
 */
class Forward extends FootballPlayer {

    /** No‑arg constructor required by the specification. */
    public Forward() {
        super();
        setPosition(Position.FORWARD);
    }

    public Forward(String team, String name, int age, int number) {
        super(team, name, age, number, Position.FORWARD);
    }

    /**
     * Simulates scoring a goal. The system announces the player's number and name.
     */
    public void scoreGoal() {
        // Record the announcement
        addScoreAnnouncement();
        // Real‑world system could output the announcement; here we just print.
        System.out.println("Goal! Player #" + getNumber() + " - " + getName());
    }
}

/**
 * Class representing a midfielder.
 */
class Midfielder extends FootballPlayer {

    /** No‑arg constructor required by the specification. */
    public Midfielder() {
        super();
        setPosition(Position.MIDFIELD);
    }

    public Midfielder(String team, String name, int age, int number) {
        super(team, name, age, number, Position.MIDFIELD);
    }

    // Midfielders do not generate announcements in the current domain.
}

/**
 * Class representing a goalkeeper (a special type of defender).
 */
class Goalkeeper extends FootballPlayer {

    /** No‑arg constructor required by the specification. */
    public Goalkeeper() {
        super();
        setPosition(Position.DEFENSE);
    }

    public Goalkeeper(String team, String name, int age, int number) {
        super(team, name, age, number, Position.DEFENSE);
    }

    /**
     * Simulates saving a goal. The system announces the player's number and name.
     */
    public void saveGoal() {
        // Record the announcement
        addSaveAnnouncement();
        System.out.println("Save! Player #" + getNumber() + " - " + getName());
    }
}

/**
 * Class representing a football team. It contains all players, the starting
 * eleven and the spare (reserve) team.
 */
class Team {

    private String name;
    private final List<FootballPlayer> players = new ArrayList<>();
    private final List<FootballPlayer> startingEleven = new ArrayList<>();
    private final List<FootballPlayer> spareTeam = new ArrayList<>();

    /** No‑arg constructor required by the specification. */
    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    // ------------------------------------------------------------------------
    // Getters and Setters
    // ------------------------------------------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   public List<FootballPlayer> getPlayers() {
        return players;
    }

    public List<FootballPlayer> getStartingEleven() {
        return startingEleven;
    }

    public List<FootballPlayer> getSpareTeam() {
        return spareTeam;
    }

    // ------------------------------------------------------------------------
    // Collection management helpers
    // ------------------------------------------------------------------------
    /**
     * Adds a player to the team roster.
     *
     * @param player the player to be added
     */
    public void addPlayer(FootballPlayer player) {
        if (player != null) {
            players.add(player);
        }
    }

    /**
     * Marks a player as part of the starting eleven.
     *
     * @param player the player to be moved to the starting eleven
     */
    public void addToStartingEleven(FootballPlayer player) {
        if (player != null && players.contains(player) && !startingEleven.contains(player)) {
            startingEleven.add(player);
        }
    }

    /**
     * Marks a player as part of the spare team (reserve).
     *
     * @param player the player to be moved to the spare team
     */
    public void addToSpareTeam(FootballPlayer player) {
        if (player != null && players.contains(player) && !spareTeam.contains(player)) {
            spareTeam.add(player);
        }
    }

    // ------------------------------------------------------------------------
    // Functional requirement implementations
    // ------------------------------------------------------------------------

    /**
     * Calculates the total number of goal‑scoring announcements made by the
     * starting eleven players of this team. Only forwards generate such
     * announcements; goals scored by other positions are ignored.
     *
     * @return total count of score announcements from the starting eleven
     */
    public int countScoreAnnouncementsStartingEleven() {
        int total = 0;
        for (FootballPlayer p : startingEleven) {
            if (p.getPosition() == Position.FORWARD) {
                total += p.getScoreAnnouncements();
            }
        }
        return total;
    }

    /**
     * Identifies the midfielder with the highest jersey number among all players
     * of this team.
     *
     * @return the midfielder having the greatest number, or {@code null} if no
     *         midfielder exists in the team
     */
    public Midfielder findMidfielderWithHighestNumber() {
        Midfielder best = null;
        for (FootballPlayer p : players) {
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
     * Calculates the average age of the spare team players.
     *
     * @return the average age as a {@code double}; returns {@code 0.0} when the
     *         spare team is empty
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
     * Counts the total number of goal‑saving announcements made by all
     * goalkeepers of this team.
     *
     * @return total count of save announcements; {@code 0} if none exist
     */
    public int countGoalSavingAnnouncements() {
        int total = 0;
        for (FootballPlayer p : players) {
            if (p instanceof Goalkeeper) {
                total += p.getSaveAnnouncements();
            }
        }
        return total;
    }

    /**
     * Calculates the total number of announcements generated by all forwards on
     * the team (both starting and reserve). Returns {@code 0} if there are no
     * forwards or no announcements.
     *
     * @return total count of forward announcements
     */
    public int totalForwardAnnouncements() {
        int total = 0;
        for (FootballPlayer p : players) {
            if (p.getPosition() == Position.FORWARD) {
                total += p.getScoreAnnouncements();
            }
        }
        return total;
    }
}

/**
 * Simple demonstration class (optional) showing how the model can be used.
 * This class is not required for the core functionality but helps during
 * manual testing.
 */
class Demo {
    public static void main(String[] args) {
        Team turgutlu = new Team("Turgutlu FC");

        // Create players
        Forward f1 = new Forward("Turgutlu FC", "Ali", 24, 9);
        Forward f2 = new Forward("Turgutlu FC", "Mehmet", 27, 11);
        Midfielder m1 = new Midfielder("Turgutlu FC", "Ayşe", 22, 8);
        Midfielder m2 = new Midfielder("Turgutlu FC", "Fatma", 23, 12);
        Goalkeeper gk = new Goalkeeper("Turgutlu FC", "Hasan", 30, 1);

        // Add players to team
        turgutlu.addPlayer(f1);
        turgutlu.addPlayer(f2);
        turgutlu.addPlayer(m1);
        turgutlu.addPlayer(m2);
        turgutlu.addPlayer(gk);

        // Define starting eleven and spare team
        turgutlu.addToStartingEleven(f1);
        turgutlu.addToStartingEleven(f2);
        turgutlu.addToStartingEleven(m1);
        turgutlu.addToStartingEleven(gk);
        turgutlu.addToSpareTeam(m2);

        // Simulate some events
        f1.scoreGoal(); // adds one announcement
        f1.scoreGoal(); // another
        f2.scoreGoal(); // one
        gk.saveGoal();  // goalkeeper save

        // Functional queries
        System.out.println("Score announcements (starting eleven): "
                + turgutlu.countScoreAnnouncementsStartingEleven());

        Midfielder bestMid = turgutlu.findMidfielderWithHighestNumber();
        System.out.println("Midfielder with highest number: "
                + (bestMid != null ? bestMid.getName() + " #" + bestMid.getNumber() : "none"));

        System.out.println("Average age of spare team: " + turgutlu.averageAgeSpareTeam());

        System.out.println("Goal saving announcements (all goalkeepers): "
                + turgutlu.countGoalSavingAnnouncements());

        System.out.println("Total forward announcements (all forwards): "
                + turgutlu.totalForwardAnnouncements());
    }
}