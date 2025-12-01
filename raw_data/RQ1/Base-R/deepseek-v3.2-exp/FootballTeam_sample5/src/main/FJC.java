import java.util.ArrayList;
import java.util.List;

/**
 * Represents a football player with team, name, age, number, and position information.
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
    }

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
     * Announces when the player scores a goal.
     * Only forwards can generate score announcements.
     */
    public void scoreGoal() {
        if (this.position == Position.FORWARD) {
            this.goalsScored++;
            System.out.println("Goal scored by number " + number + ": " + name);
        }
    }

    /**
     * Announces when the goalkeeper saves a goal.
     * Only goalkeepers can generate save announcements.
     */
    public void saveGoal() {
        if (this.position == Position.GOALKEEPER) {
            this.goalsSaved++;
            System.out.println("Goal saved by number " + number + ": " + name);
        }
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
 * Represents a football team with players, starting eleven, and spare team.
 */
class FootballTeam {
    private String name;
    private List<FootballPlayer> players;
    private List<FootballPlayer> startingEleven;
    private List<FootballPlayer> spareTeam;

    /**
     * Default constructor for FootballTeam.
     */
    public FootballTeam() {
        players = new ArrayList<>();
        startingEleven = new ArrayList<>();
        spareTeam = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FootballPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<FootballPlayer> players) {
        this.players = players;
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

    /**
     * Calculates the total number of goal-scoring announcements for the starting eleven players.
     * Only forwards can generate score announcements, goals scored by other positions are ignored.
     *
     * @return the total number of goal-scoring announcements by forwards in the starting eleven
     */
    public int calculateTotalGoalAnnouncements() {
        int total = 0;
        for (FootballPlayer player : startingEleven) {
            if (player.getPosition() == Position.FORWARD) {
                total += player.getGoalsScored();
            }
        }
        return total;
    }

    /**
     * Identifies the midfielder with the highest number within all players of the football team.
     *
     * @return the midfielder player with the highest number, or null if no midfielders found
     */
    public FootballPlayer findMidfielderWithHighestNumber() {
        FootballPlayer highestNumberMidfielder = null;
        for (FootballPlayer player : players) {
            if (player.getPosition() == Position.MIDFIELD) {
                if (highestNumberMidfielder == null || player.getNumber() > highestNumberMidfielder.getNumber()) {
                    highestNumberMidfielder = player;
                }
            }
        }
        return highestNumberMidfielder;
    }

    /**
     * Calculates the average age of spare team players.
     *
     * @return the average age of players in the spare team as a floating-point number,
     *         or 0.0 if the reserve team is empty
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
     * Only announcements generated by players whose position is GOALKEEPER are considered.
     *
     * @return the total number of goal-saving announcements by goalkeepers, or 0 if none exist
     */
    public int countGoalSavingAnnouncements() {
        int total = 0;
        for (FootballPlayer player : players) {
            if (player.getPosition() == Position.GOALKEEPER) {
                total += player.getGoalsSaved();
            }
        }
        return total;
    }

    /**
     * Calculates the total number of announcements made by all forwards on the team.
     * This includes both goal-scoring announcements and any other forward-specific announcements.
     *
     * @return the total number of announcements by forwards, or 0 if there are no forwards or no announcements
     */
    public int calculateTotalForwardAnnouncements() {
        int total = 0;
        for (FootballPlayer player : players) {
            if (player.getPosition() == Position.FORWARD) {
                total += player.getGoalsScored();
            }
        }
        return total;
    }
}