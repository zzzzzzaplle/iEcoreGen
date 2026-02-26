import java.util.ArrayList;
import java.util.List;

/**
 * Represents a football player in the Turgutlu football team system.
 * Each player has a team, name, age, number, and position.
 */
class FootballPlayer {
    private String team;
    private String name;
    private int age;
    private int number;
    private Position position;
    private int goalsScored;
    private int goalsSaved;
    private boolean isInStartingEleven;

    /**
     * Default constructor for FootballPlayer.
     */
    public FootballPlayer() {
    }

    /**
     * Gets the team of the player.
     * @return the team name
     */
    public String getTeam() {
        return team;
    }

    /**
     * Sets the team of the player.
     * @param team the team name to set
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * Gets the name of the player.
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the age of the player.
     * @return the player's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the player.
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the number of the player.
     * @return the player's number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the number of the player.
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Gets the position of the player.
     * @return the player's position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the player.
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Gets the number of goals scored by the player.
     * @return the number of goals scored
     */
    public int getGoalsScored() {
        return goalsScored;
    }

    /**
     * Sets the number of goals scored by the player.
     * @param goalsScored the number of goals scored to set
     */
    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    /**
     * Gets the number of goals saved by the player.
     * @return the number of goals saved
     */
    public int getGoalsSaved() {
        return goalsSaved;
    }

    /**
     * Sets the number of goals saved by the player.
     * @param goalsSaved the number of goals saved to set
     */
    public void setGoalsSaved(int goalsSaved) {
        this.goalsSaved = goalsSaved;
    }

    /**
     * Checks if the player is in the starting eleven.
     * @return true if the player is in the starting eleven, false otherwise
     */
    public boolean isInStartingEleven() {
        return isInStartingEleven;
    }

    /**
     * Sets whether the player is in the starting eleven.
     * @param inStartingEleven true if the player is in the starting eleven, false otherwise
     */
    public void setInStartingEleven(boolean inStartingEleven) {
        isInStartingEleven = inStartingEleven;
    }

    /**
     * Simulates a goal scoring event by the player.
     * If the player is a forward, the system announces the goal.
     * @return the announcement message if the player is a forward, null otherwise
     */
    public String scoreGoal() {
        if (this.position == Position.FORWARD) {
            this.goalsScored++;
            return "Goal scored by number " + this.number + ", " + this.name;
        }
        return null;
    }

    /**
     * Simulates a goal saving event by the player.
     * If the player is a goalkeeper, the system announces the save.
     * @return the announcement message if the player is a goalkeeper, null otherwise
     */
    public String saveGoal() {
        if (this.position == Position.GOALKEEPER) {
            this.goalsSaved++;
            return "Goal saved by number " + this.number + ", " + this.name;
        }
        return null;
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
 * Represents a football team with players and operations related to team management.
 */
class FootballTeam {
    private String name;
    private List<FootballPlayer> players;

    /**
     * Default constructor for FootballTeam.
     */
    public FootballTeam() {
        this.players = new ArrayList<>();
    }

    /**
     * Gets the name of the team.
     * @return the team name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the team.
     * @param name the team name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of players in the team.
     * @return the list of players
     */
    public List<FootballPlayer> getPlayers() {
        return players;
    }

    /**
     * Sets the list of players in the team.
     * @param players the list of players to set
     */
    public void setPlayers(List<FootballPlayer> players) {
        this.players = players;
    }

    /**
     * Adds a player to the team.
     * @param player the player to add
     */
    public void addPlayer(FootballPlayer player) {
        this.players.add(player);
    }

    /**
     * Gets the starting eleven players of the team.
     * @return a list of players in the starting eleven
     */
    public List<FootballPlayer> getStartingEleven() {
        List<FootballPlayer> startingEleven = new ArrayList<>();
        for (FootballPlayer player : this.players) {
            if (player.isInStartingEleven()) {
                startingEleven.add(player);
            }
        }
        return startingEleven;
    }

    /**
     * Gets the spare team players (non-starting eleven).
     * @return a list of players in the spare team
     */
    public List<FootballPlayer> getSpareTeam() {
        List<FootballPlayer> spareTeam = new ArrayList<>();
        for (FootballPlayer player : this.players) {
            if (!player.isInStartingEleven()) {
                spareTeam.add(player);
            }
        }
        return spareTeam;
    }

    /**
     * Calculates the total number of goal-scoring announcements for the starting eleven players.
     * Only forwards in the starting eleven are considered.
     * @return the total number of goal-scoring announcements by starting eleven forwards
     */
    public int calculateTotalGoalScoringAnnouncements() {
        int total = 0;
        List<FootballPlayer> startingEleven = getStartingEleven();
        for (FootballPlayer player : startingEleven) {
            if (player.getPosition() == Position.FORWARD) {
                total += player.getGoalsScored();
            }
        }
        return total;
    }

    /**
     * Identifies the midfielder with the highest number within all players of the team.
     * @return the midfielder with the highest number, or null if no midfielders found
     */
    public FootballPlayer findMidfielderWithHighestNumber() {
        FootballPlayer highestNumberMidfielder = null;
        for (FootballPlayer player : this.players) {
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
     * @return the average age as a floating-point number, or 0.0 if the spare team is empty
     */
    public double calculateAverageAgeOfSpareTeam() {
        List<FootballPlayer> spareTeam = getSpareTeam();
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
     * @return the total number of goal-saving announcements by goalkeepers, or 0 if none exist
     */
    public int countGoalSavingAnnouncements() {
        int total = 0;
        for (FootballPlayer player : this.players) {
            if (player.getPosition() == Position.GOALKEEPER) {
                total += player.getGoalsSaved();
            }
        }
        return total;
    }

    /**
     * Calculates the total number of announcements made by all forwards on the team.
     * This includes both goal-scoring announcements.
     * @return the total number of announcements by forwards, or 0 if none exist
     */
    public int calculateTotalAnnouncementsByForwards() {
        int total = 0;
        for (FootballPlayer player : this.players) {
            if (player.getPosition() == Position.FORWARD) {
                total += player.getGoalsScored();
            }
        }
        return total;
    }
}