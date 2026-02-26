import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

/**
 * Represents a football player with their attributes and behaviors.
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
     * Creates a new FootballPlayer with default values.
     */
    public FootballPlayer() {
        this.team = "";
        this.name = "";
        this.age = 0;
        this.number = 0;
        this.position = Position.FORWARD;
        this.goalsScored = 0;
        this.goalsSaved = 0;
    }

    /**
     * @return the team
     */
    public String getTeam() {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return the goalsScored
     */
    public int getGoalsScored() {
        return goalsScored;
    }

    /**
     * @param goalsScored the goalsScored to set
     */
    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    /**
     * @return the goalsSaved
     */
    public int getGoalsSaved() {
        return goalsSaved;
    }

    /**
     * @param goalsSaved the goalsSaved to set
     */
    public void setGoalsSaved(int goalsSaved) {
        this.goalsSaved = goalsSaved;
    }

    /**
     * Announces the player's goal.
     */
    public void announceGoal() {
        if (this.position == Position.FORWARD) {
            System.out.println("Goal scored by " + this.name + " (No: " + this.number + ")");
            this.goalsScored++;
        }
    }

    /**
     * Announces the player's save.
     */
    public void announceSave() {
        if (this.position == Position.GOALKEEPER) {
            System.out.println("Goal saved by " + this.name + " (No: " + this.number + ")");
            this.goalsSaved++;
        }
    }
}

/**
 * Enum representing the position of a football player.
 */
enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE
}

/**
 * Represents a football team with a list of players.
 */
class FootballTeam {
    private String teamName;
    private List<FootballPlayer> players;
    private List<FootballPlayer> startingEleven;
    private List<FootballPlayer> spareTeam;

    /**
     * Creates a new FootballTeam with default values.
     */
    public FootballTeam() {
        this.teamName = "";
        this.players = new ArrayList<>();
        this.startingEleven = new ArrayList<>();
        this.spareTeam = new ArrayList<>();
    }

    /**
     * @return the teamName
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * @param teamName the teamName to set
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * @return the players
     */
    public List<FootballPlayer> getPlayers() {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(List<FootballPlayer> players) {
        this.players = players;
    }

    /**
     * @return the startingEleven
     */
    public List<FootballPlayer> getStartingEleven() {
        return startingEleven;
    }

    /**
     * @param startingEleven the startingEleven to set
     */
    public void setStartingEleven(List<FootballPlayer> startingEleven) {
        this.startingEleven = startingEleven;
    }

    /**
     * @return the spareTeam
     */
    public List<FootballPlayer> getSpareTeam() {
        return spareTeam;
    }

    /**
     * @param spareTeam the spareTeam to set
     */
    public void setSpareTeam(List<FootballPlayer> spareTeam) {
        this.spareTeam = spareTeam;
    }

    /**
     * Calculates the total number of goal-scoring announcements for the starting eleven players.
     *
     * @return the total number of goal-scoring announcements
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
     * Identifies the midfielder with the highest number.
     *
     * @return the midfielder with the highest number, or null if no midfielders found
     */
    public FootballPlayer identifyMidfielderWithHighestNumber() {
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
     * @return the average age of spare team players, or 0.0 if the spare team is empty
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
     * Counts the number of goal-saving announcements made by all goalkeepers.
     *
     * @return the number of goal-saving announcements
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
     * Calculates the total number of announcements made by all forwards.
     *
     * @return the total number of announcements made by all forwards
     */
    public int calculateTotalAnnouncementsByForwards() {
        int total = 0;
        for (FootballPlayer player : players) {
            if (player.getPosition() == Position.FORWARD) {
                total += player.getGoalsScored();
            }
        }
        return total;
    }
}