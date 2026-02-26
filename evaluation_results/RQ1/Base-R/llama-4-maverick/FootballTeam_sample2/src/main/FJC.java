import java.util.ArrayList;
import java.util.List;

/**
 * Enum representing the position of a football player.
 */
enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE
}

/**
 * Enum representing the duty of a football player based on their position.
 */
enum Duty {
    STRIKER,
    MIDFIELDER,
    GOALKEEPER
}

/**
 * Class representing a football player.
 */
class FootballPlayer {
    private String team;
    private String name;
    private int age;
    private int number;
    private Position position;
    private Duty duty;
    private int scoreAnnouncements;
    private int goalSavingAnnouncements;

    /**
     * Unparameterized constructor for FootballPlayer.
     */
    public FootballPlayer() {}

    /**
     * Constructor for FootballPlayer.
     * 
     * @param team     the team of the player
     * @param name     the name of the player
     * @param age      the age of the player
     * @param number   the number of the player
     * @param position the position of the player
     * @param duty     the duty of the player
     */
    public FootballPlayer(String team, String name, int age, int number, Position position, Duty duty) {
        this.team = team;
        this.name = name;
        this.age = age;
        this.number = number;
        this.position = position;
        this.duty = duty;
        this.scoreAnnouncements = 0;
        this.goalSavingAnnouncements = 0;
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
    public Duty getDuty() { return duty; }
    public void setDuty(Duty duty) { this.duty = duty; }
    public int getScoreAnnouncements() { return scoreAnnouncements; }
    public void setScoreAnnouncements(int scoreAnnouncements) { this.scoreAnnouncements = scoreAnnouncements; }
    public int getGoalSavingAnnouncements() { return goalSavingAnnouncements; }
    public void setGoalSavingAnnouncements(int goalSavingAnnouncements) { this.goalSavingAnnouncements = goalSavingAnnouncements; }

    /**
     * Announces the player's number and name when they score a goal.
     * 
     * @return true if the announcement is made, false otherwise
     */
    public boolean scoreGoal() {
        if (duty == Duty.STRIKER) {
            scoreAnnouncements++;
            System.out.println("Player " + number + " " + name + " scored a goal.");
            return true;
        }
        return false;
    }

    /**
     * Announces the player's number and name when they save a goal.
     * 
     * @return true if the announcement is made, false otherwise
     */
    public boolean saveGoal() {
        if (duty == Duty.GOALKEEPER) {
            goalSavingAnnouncements++;
            System.out.println("Player " + number + " " + name + " saved a goal.");
            return true;
        }
        return false;
    }
}

/**
 * Class representing a football team.
 */
class FootballTeam {
    private String name;
    private List<FootballPlayer> startingEleven;
    private List<FootballPlayer> spareTeam;

    /**
     * Unparameterized constructor for FootballTeam.
     */
    public FootballTeam() {
        this.startingEleven = new ArrayList<>();
        this.spareTeam = new ArrayList<>();
    }

    /**
     * Constructor for FootballTeam.
     * 
     * @param name the name of the team
     */
    public FootballTeam(String name) {
        this.name = name;
        this.startingEleven = new ArrayList<>();
        this.spareTeam = new ArrayList<>();
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<FootballPlayer> getStartingEleven() { return startingEleven; }
    public void setStartingEleven(List<FootballPlayer> startingEleven) { this.startingEleven = startingEleven; }
    public List<FootballPlayer> getSpareTeam() { return spareTeam; }
    public void setSpareTeam(List<FootballPlayer> spareTeam) { this.spareTeam = spareTeam; }

    /**
     * Calculates the total number of goal-scoring announcements for the starting eleven players.
     * 
     * @return the total number of goal-scoring announcements
     */
    public int calculateTotalScoreAnnouncements() {
        int total = 0;
        for (FootballPlayer player : startingEleven) {
            if (player.getDuty() == Duty.STRIKER) {
                total += player.getScoreAnnouncements();
            }
        }
        return total;
    }

    /**
     * Identifies the midfielder with the highest number.
     * 
     * @return the midfielder with the highest number, or null if no midfielders are found
     */
    public FootballPlayer identifyMidfielderWithHighestNumber() {
        FootballPlayer midfielderWithHighestNumber = null;
        int highestNumber = 0;
        for (FootballPlayer player : startingEleven) {
            if (player.getDuty() == Duty.MIDFIELDER && player.getNumber() > highestNumber) {
                midfielderWithHighestNumber = player;
                highestNumber = player.getNumber();
            }
        }
        for (FootballPlayer player : spareTeam) {
            if (player.getDuty() == Duty.MIDFIELDER && player.getNumber() > highestNumber) {
                midfielderWithHighestNumber = player;
                highestNumber = player.getNumber();
            }
        }
        return midfielderWithHighestNumber;
    }

    /**
     * Calculates the average age of the spare team players.
     * 
     * @return the average age of the spare team players, or 0.0 if the spare team is empty
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
     * @return the number of goal-saving announcements
     */
    public int countGoalSavingAnnouncements() {
        int total = 0;
        for (FootballPlayer player : startingEleven) {
            if (player.getDuty() == Duty.GOALKEEPER) {
                total += player.getGoalSavingAnnouncements();
            }
        }
        for (FootballPlayer player : spareTeam) {
            if (player.getDuty() == Duty.GOALKEEPER) {
                total += player.getGoalSavingAnnouncements();
            }
        }
        return total;
    }

    /**
     * Calculates the total number of announcements made by all forwards on the team.
     * 
     * @return the total number of announcements
     */
    public int calculateTotalAnnouncementsByForwards() {
        int total = 0;
        for (FootballPlayer player : startingEleven) {
            if (player.getPosition() == Position.FORWARD) {
                total += player.getScoreAnnouncements();
            }
        }
        for (FootballPlayer player : spareTeam) {
            if (player.getPosition() == Position.FORWARD) {
                total += player.getScoreAnnouncements();
            }
        }
        return total;
    }
}