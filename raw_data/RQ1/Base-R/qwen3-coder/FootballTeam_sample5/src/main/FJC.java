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
    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    
    public int getGoalsScored() { return goalsScored; }
    public void setGoalsScored(int goalsScored) { this.goalsScored = goalsScored; }
    
    public int getGoalsSaved() { return goalsSaved; }
    public void setGoalsSaved(int goalsSaved) { this.goalsSaved = goalsSaved; }
}

/**
 * Represents the position of a football player.
 */
enum Position {
    FORWARD, MIDFIELD, DEFENSE, GOALKEEPER, STRIKER
}

/**
 * Represents a football team with players divided into starting eleven and spare team.
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
    public List<FootballPlayer> getStartingEleven() { return startingEleven; }
    public void setStartingEleven(List<FootballPlayer> startingEleven) { this.startingEleven = startingEleven; }
    
    public List<FootballPlayer> getSpareTeam() { return spareTeam; }
    public void setSpareTeam(List<FootballPlayer> spareTeam) { this.spareTeam = spareTeam; }

    /**
     * Calculates the total number of goal-scoring announcements for the starting eleven players.
     * Only forwards in the starting eleven can generate score announcements.
     * 
     * @return the total number of goal-scoring announcements from starting eleven forwards
     */
    public int calculateStartingElevenForwardAnnouncements() {
        int totalAnnouncements = 0;
        for (FootballPlayer player : startingEleven) {
            if (player.getPosition() == Position.FORWARD || player.getPosition() == Position.STRIKER) {
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
        
        // Check starting eleven
        for (FootballPlayer player : startingEleven) {
            if (player.getPosition() == Position.MIDFIELD && player.getNumber() > maxNumber) {
                maxNumber = player.getNumber();
                highestNumberMidfielder = player;
            }
        }
        
        // Check spare team
        for (FootballPlayer player : spareTeam) {
            if (player.getPosition() == Position.MIDFIELD && player.getNumber() > maxNumber) {
                maxNumber = player.getNumber();
                highestNumberMidfielder = player;
            }
        }
        
        return highestNumberMidfielder;
    }

    /**
     * Calculates the average age of players in the spare team.
     * 
     * @return the average age of spare team players as a floating-point number,
     *         or 0.0 if the spare team is empty
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
     * @return the total number of goal-saving announcements by goalkeepers,
     *         or 0 if no such announcements exist
     */
    public int countGoalkeeperSavingAnnouncements() {
        int totalSaves = 0;
        
        // Check starting eleven
        for (FootballPlayer player : startingEleven) {
            if (player.getPosition() == Position.GOALKEEPER) {
                totalSaves += player.getGoalsSaved();
            }
        }
        
        // Check spare team
        for (FootballPlayer player : spareTeam) {
            if (player.getPosition() == Position.GOALKEEPER) {
                totalSaves += player.getGoalsSaved();
            }
        }
        
        return totalSaves;
    }

    /**
     * Calculates the total number of announcements made by all forwards on the team.
     * 
     * @return the total number of announcements by forwards,
     *         or 0 if there are no forwards or no announcements
     */
    public int calculateForwardAnnouncements() {
        int totalAnnouncements = 0;
        
        // Check starting eleven
        for (FootballPlayer player : startingEleven) {
            if (player.getPosition() == Position.FORWARD || player.getPosition() == Position.STRIKER) {
                totalAnnouncements += player.getGoalsScored();
            }
        }
        
        // Check spare team
        for (FootballPlayer player : spareTeam) {
            if (player.getPosition() == Position.FORWARD || player.getPosition() == Position.STRIKER) {
                totalAnnouncements += player.getGoalsScored();
            }
        }
        
        return totalAnnouncements;
    }
}