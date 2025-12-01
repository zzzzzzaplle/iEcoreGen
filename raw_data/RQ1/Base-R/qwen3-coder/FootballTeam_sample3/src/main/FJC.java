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

    // Getter and setter methods
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
     * Records a goal scored by this player.
     */
    public void scoreGoal() {
        this.goalsScored++;
    }

    /**
     * Records a goal saved by this player.
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

    // Getter and setter methods
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
     * Calculates the total number of goal-scoring announcements for the starting eleven players.
     * Only forwards in the starting eleven can generate score announcements.
     *
     * @return the total number of goal-scoring announcements by starting eleven forwards
     */
    public int calculateStartingElevenForwardAnnouncements() {
        int totalAnnouncements = 0;
        for (FootballPlayer player : startingEleven) {
            if (player.getPosition() == Position.FORWARD) {
                totalAnnouncements += player.getGoalsScored();
            }
        }
        return totalAnnouncements;
    }

    /**
     * Identifies the midfielder with the highest number within all players of the team.
     *
     * @return the midfielder player with the highest number, or null if no midfielders found
     */
    public FootballPlayer findMidfielderWithHighestNumber() {
        FootballPlayer highestNumberMidfielder = null;
        int highestNumber = Integer.MIN_VALUE;

        // Check both starting eleven and spare team
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.addAll(startingEleven);
        allPlayers.addAll(spareTeam);

        for (FootballPlayer player : allPlayers) {
            if (player.getPosition() == Position.MIDFIELD) {
                if (player.getNumber() > highestNumber) {
                    highestNumber = player.getNumber();
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
    public double calculateSpareTeamAverageAge() {
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
     * @return the total number of goal-saving announcements, or 0 if no goalkeepers exist
     */
    public int countGoalkeeperAnnouncements() {
        int totalSaves = 0;

        // Check both starting eleven and spare team
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
     * @return the total number of forward announcements, or 0 if no forwards exist or no announcements
     */
    public int calculateForwardAnnouncements() {
        int totalGoals = 0;

        // Check both starting eleven and spare team
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.addAll(startingEleven);
        allPlayers.addAll(spareTeam);

        for (FootballPlayer player : allPlayers) {
            if (player.getPosition() == Position.FORWARD) {
                totalGoals += player.getGoalsScored();
            }
        }

        return totalGoals;
    }
}