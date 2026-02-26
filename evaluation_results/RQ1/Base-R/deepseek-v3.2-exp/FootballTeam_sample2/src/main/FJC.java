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
     * Announces when a striker scores a goal by printing number and name.
     */
    public void scoreGoal() {
        if (this.position == Position.FORWARD) {
            this.goalsScored++;
            System.out.println("Goal scored by number " + number + ": " + name);
        }
    }

    /**
     * Announces when a goalkeeper saves a goal by printing number and name.
     */
    public void saveGoal() {
        if (this.position == Position.GOALKEEPER) {
            this.goalsSaved++;
            System.out.println("Goal saved by number " + number + ": " + name);
        }
    }
}

/**
 * Represents the different positions a football player can have.
 */
enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE,
    GOALKEEPER
}

/**
 * Represents a football team with starting eleven and spare team players.
 */
class FootballTeam {
    private String name;
    private List<FootballPlayer> startingEleven;
    private List<FootballPlayer> spareTeam;

    /**
     * Default constructor for FootballTeam.
     */
    public FootballTeam() {
        startingEleven = new ArrayList<>();
        spareTeam = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
     * Only goals scored by forwards are counted.
     *
     * @return total number of goal announcements by forwards in starting eleven
     */
    public int calculateStartingElevenGoalAnnouncements() {
        int total = 0;
        for (FootballPlayer player : startingEleven) {
            if (player.getPosition() == Position.FORWARD) {
                total += player.getGoalsScored();
            }
        }
        return total;
    }

    /**
     * Identifies the midfielder with the highest number within all players of the team.
     *
     * @return the midfielder with highest number, or null if no midfielders found
     */
    public FootballPlayer findMidfielderWithHighestNumber() {
        FootballPlayer highestNumberMidfielder = null;
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.addAll(startingEleven);
        allPlayers.addAll(spareTeam);
        
        for (FootballPlayer player : allPlayers) {
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
     *
     * @return average age as floating-point number, 0.0 if spare team is empty
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
     * @return total number of goal-saving announcements by goalkeepers, 0 if none
     */
    public int countGoalkeeperSaveAnnouncements() {
        int total = 0;
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.addAll(startingEleven);
        allPlayers.addAll(spareTeam);
        
        for (FootballPlayer player : allPlayers) {
            if (player.getPosition() == Position.GOALKEEPER) {
                total += player.getGoalsSaved();
            }
        }
        return total;
    }

    /**
     * Calculates the total number of announcements made by all forwards on the team.
     *
     * @return total number of goal announcements by forwards, 0 if none
     */
    public int calculateTotalForwardAnnouncements() {
        int total = 0;
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.addAll(startingEleven);
        allPlayers.addAll(spareTeam);
        
        for (FootballPlayer player : allPlayers) {
            if (player.getPosition() == Position.FORWARD) {
                total += player.getGoalsScored();
            }
        }
        return total;
    }
}