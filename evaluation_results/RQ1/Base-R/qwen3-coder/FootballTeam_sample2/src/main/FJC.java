import java.util.ArrayList;
import java.util.List;

/**
 * Represents a football player with personal and positional information.
 */
class FootballPlayer {
    private String team;
    private String name;
    private int age;
    private int number;
    private Position position;
    private int goalsScored;
    private int goalsSaved;

    /**
     * Default constructor for FootballPlayer.
     */
    public FootballPlayer() {
        this.goalsScored = 0;
        this.goalsSaved = 0;
    }

    // Getters and setters
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

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getGoalsSaved() {
        return goalsSaved;
    }

    public void setGoalsSaved(int goalsSaved) {
        this.goalsSaved = goalsSaved;
    }

    /**
     * Increments the number of goals scored by this player.
     */
    public void scoreGoal() {
        this.goalsScored++;
    }

    /**
     * Increments the number of goals saved by this player.
     */
    public void saveGoal() {
        this.goalsSaved++;
    }
}

/**
 * Enum representing the different positions a football player can have.
 */
enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE,
    GOALKEEPER
}

/**
 * Represents a football team with starting eleven and spare players.
 */
class FootballTeam {
    private List<FootballPlayer> startingEleven;
    private List<FootballPlayer> spareTeam;

    /**
     * Default constructor for FootballTeam.
     */
    public FootballTeam() {
        this.startingEleven = new ArrayList<>();
        this.spareTeam = new ArrayList<>();
    }

    // Getters and setters
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

    /**
     * Adds a player to the starting eleven.
     *
     * @param player the player to add
     */
    public void addPlayerToStartingEleven(FootballPlayer player) {
        this.startingEleven.add(player);
    }

    /**
     * Adds a player to the spare team.
     *
     * @param player the player to add
     */
    public void addPlayerToSpareTeam(FootballPlayer player) {
        this.spareTeam.add(player);
    }

    /**
     * Calculates the total number of goal-scoring announcements for the starting eleven players.
     * Only forwards in the starting eleven can generate score announcements.
     *
     * @return the total number of goal-scoring announcements from forwards in the starting eleven
     */
    public int calculateTotalGoalScoringAnnouncements() {
        int totalAnnouncements = 0;
        for (FootballPlayer player : startingEleven) {
            if (player.getPosition() == Position.FORWARD) {
                totalAnnouncements += player.getGoalsScored();
            }
        }
        return totalAnnouncements;
    }

    /**
     * Identifies the midfielder with the highest number within all players of the football team.
     *
     * @return the midfielder player with the highest number, or null if no midfielders are found
     */
    public FootballPlayer findMidfielderWithHighestNumber() {
        FootballPlayer highestNumberMidfielder = null;
        int maxNumber = Integer.MIN_VALUE;

        // Check all players in both starting eleven and spare team
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.addAll(startingEleven);
        allPlayers.addAll(spareTeam);

        for (FootballPlayer player : allPlayers) {
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
     * @return the average age as a floating-point number, or 0.0 if the spare team is empty
     */
    public double calculateAverageAgeOfSpareTeam() {
        if (spareTeam.isEmpty()) {
            return 0.0;
        }

        int totalAge = 0;
        for (FootballPlayer player : spareTeam) {
            totalAge += player.getAge();
        }

        return (double) totalAge / spareTeam.size();
    }

    /**
     * Counts the number of goal-saving announcements made by all goalkeepers on the team.
     *
     * @return the number of goal-saving announcements, or 0 if no goalkeepers exist
     */
    public int countGoalSavingAnnouncements() {
        int totalSaves = 0;

        // Check all players in both starting eleven and spare team
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.addAll(startingEleven);
        allPlayers.addAll(spareTeam);

        for (FootballPlayer player : allPlayers) {
            if (player.getPosition() == Position.GOALKEEPER) {
                totalSaves += player.getGoalsSaved();
            }
        }

        return totalSaves;
    }

    /**
     * Calculates the total number of announcements made by all forwards on the team.
     *
     * @return the total number of announcements from forwards, or 0 if there are no forwards
     */
    public int calculateTotalForwardAnnouncements() {
        int totalAnnouncements = 0;

        // Check all players in both starting eleven and spare team
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.addAll(startingEleven);
        allPlayers.addAll(spareTeam);

        for (FootballPlayer player : allPlayers) {
            if (player.getPosition() == Position.FORWARD) {
                totalAnnouncements += player.getGoalsScored();
            }
        }

        return totalAnnouncements;
    }
}