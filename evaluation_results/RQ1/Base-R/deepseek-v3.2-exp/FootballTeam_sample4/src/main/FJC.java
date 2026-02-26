import java.util.ArrayList;
import java.util.List;

/**
 * Represents a football player in the Turgutlu football team system.
 */
class FootballPlayer {
    private String team;
    private String name;
    private int age;
    private int number;
    private Position position;
    private int goalsScored;
    private int goalsSaved;
    private boolean isStartingEleven;

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
     * @param name the player's name to set
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
     * @param age the player's age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the jersey number of the player.
     * @return the player's number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the jersey number of the player.
     * @param number the player's number to set
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
     * @param position the player's position to set
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
     * @return true if the player is in starting eleven, false otherwise
     */
    public boolean isStartingEleven() {
        return isStartingEleven;
    }

    /**
     * Sets whether the player is in the starting eleven.
     * @param isStartingEleven true if the player is in starting eleven, false otherwise
     */
    public void setStartingEleven(boolean isStartingEleven) {
        this.isStartingEleven = isStartingEleven;
    }

    /**
     * Simulates a goal being scored by the player.
     * The system announces the player's number and name if they are a striker.
     * @return true if the goal was announced (player is a striker), false otherwise
     */
    public boolean scoreGoal() {
        if (this.position == Position.FORWARD) {
            this.goalsScored++;
            System.out.println("Goal scored by #" + this.number + " " + this.name);
            return true;
        }
        return false;
    }

    /**
     * Simulates a goal being saved by the player.
     * The system announces the player's number and name if they are a goalkeeper.
     * @return true if the save was announced (player is a goalkeeper), false otherwise
     */
    public boolean saveGoal() {
        if (this.position == Position.GOALKEEPER) {
            this.goalsSaved++;
            System.out.println("Goal saved by #" + this.number + " " + this.name);
            return true;
        }
        return false;
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
 * Represents a football team with players and operations for team management.
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
     * Calculates the total number of goal-scoring announcements for the starting eleven players.
     * Only goals scored by forwards in the starting eleven are counted.
     * @return the total number of goal-scoring announcements by starting eleven forwards
     */
    public int calculateStartingElevenGoalAnnouncements() {
        int totalAnnouncements = 0;
        for (FootballPlayer player : players) {
            if (player.isStartingEleven() && player.getPosition() == Position.FORWARD) {
                totalAnnouncements += player.getGoalsScored();
            }
        }
        return totalAnnouncements;
    }

    /**
     * Identifies the midfielder with the highest jersey number within all players of the team.
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
     * Calculates the average age of players in the spare team.
     * The spare team consists of players who are not in the starting eleven.
     * @return the average age of spare team players as a floating-point number, or 0.0 if the spare team is empty
     */
    public double calculateSpareTeamAverageAge() {
        List<FootballPlayer> spareTeam = new ArrayList<>();
        for (FootballPlayer player : players) {
            if (!player.isStartingEleven()) {
                spareTeam.add(player);
            }
        }

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
     * @return the total number of goal-saving announcements by goalkeepers, or 0 if none exist
     */
    public int countGoalkeeperSaveAnnouncements() {
        int totalSaves = 0;
        for (FootballPlayer player : players) {
            if (player.getPosition() == Position.GOALKEEPER) {
                totalSaves += player.getGoalsSaved();
            }
        }
        return totalSaves;
    }

    /**
     * Calculates the total number of announcements made by all forwards on the team.
     * This includes both goal-scoring announcements.
     * @return the total number of announcements by forwards, or 0 if there are no forwards or no announcements
     */
    public int calculateTotalForwardAnnouncements() {
        int totalAnnouncements = 0;
        for (FootballPlayer player : players) {
            if (player.getPosition() == Position.FORWARD) {
                totalAnnouncements += player.getGoalsScored();
            }
        }
        return totalAnnouncements;
    }

    /**
     * Gets the list of players in the starting eleven.
     * @return the list of starting eleven players
     */
    public List<FootballPlayer> getStartingEleven() {
        List<FootballPlayer> startingEleven = new ArrayList<>();
        for (FootballPlayer player : players) {
            if (player.isStartingEleven()) {
                startingEleven.add(player);
            }
        }
        return startingEleven;
    }

    /**
     * Gets the list of players in the spare team (not in starting eleven).
     * @return the list of spare team players
     */
    public List<FootballPlayer> getSpareTeam() {
        List<FootballPlayer> spareTeam = new ArrayList<>();
        for (FootballPlayer player : players) {
            if (!player.isStartingEleven()) {
                spareTeam.add(player);
            }
        }
        return spareTeam;
    }
}