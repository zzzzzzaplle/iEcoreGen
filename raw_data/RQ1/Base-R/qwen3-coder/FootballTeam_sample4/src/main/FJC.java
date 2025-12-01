import java.util.ArrayList;
import java.util.List;

/**
 * Represents a football player with basic information and position.
 */
class FootballPlayer {
    private String team;
    private String name;
    private int age;
    private int number;
    private Position position;
    
    /**
     * Default constructor for FootballPlayer.
     */
    public FootballPlayer() {
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
}

/**
 * Represents the position of a football player.
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
    private List<FootballPlayer> allPlayers;
    private List<FootballPlayer> startingEleven;
    private List<FootballPlayer> spareTeam;
    
    /**
     * Default constructor for FootballTeam.
     */
    public FootballTeam() {
        this.allPlayers = new ArrayList<>();
        this.startingEleven = new ArrayList<>();
        this.spareTeam = new ArrayList<>();
    }
    
    // Getters and setters
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
    
    /**
     * Calculates the total number of goal-scoring announcements for the starting eleven players.
     * Only forwards in the starting eleven can generate score announcements.
     * 
     * @return the count of goal-scoring announcements from starting eleven forwards
     */
    public int calculateTotalGoalScoringAnnouncements() {
        int count = 0;
        for (FootballPlayer player : startingEleven) {
            if (player.getPosition() == Position.FORWARD) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Identifies the midfielder with the highest number within all players of the football team.
     * 
     * @return the midfielder player with the highest number, or null if no midfielders found
     */
    public FootballPlayer identifyMidfielderWithHighestNumber() {
        FootballPlayer highestNumberMidfielder = null;
        int maxNumber = Integer.MIN_VALUE;
        
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
    public double calculateAverageAgeOfSpareTeamPlayers() {
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
     * @return the count of goal-saving announcements, or 0 if no goalkeepers found
     */
    public int countGoalSavingAnnouncements() {
        int count = 0;
        for (FootballPlayer player : allPlayers) {
            if (player.getPosition() == Position.GOALKEEPER) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Calculates the total number of announcements made by all forwards on the team.
     * 
     * @return the total count of announcements from forwards, or 0 if no forwards found
     */
    public int calculateTotalForwardAnnouncements() {
        int count = 0;
        for (FootballPlayer player : allPlayers) {
            if (player.getPosition() == Position.FORWARD) {
                count++;
            }
        }
        return count;
    }
}