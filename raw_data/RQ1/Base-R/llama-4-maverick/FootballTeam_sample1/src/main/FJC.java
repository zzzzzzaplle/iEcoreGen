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
 * Represents a football player with properties like team, name, age, number, position, and duty.
 */
class FootballPlayer {
    private String team;
    private String name;
    private int age;
    private int number;
    private Position position;
    private Duty duty;
    private int goalsScored;
    private int goalsSaved;

    /**
     * Unparameterized constructor for FootballPlayer.
     */
    public FootballPlayer() {}

    /**
     * Constructor for FootballPlayer with parameters.
     * 
     * @param team     the team of the player
     * @param name     the name of the player
     * @param age      the age of the player
     * @param number   the number of the player
     * @param position the position of the player
     * @param duty     the duty of the player based on their position
     */
    public FootballPlayer(String team, String name, int age, int number, Position position, Duty duty) {
        this.team = team;
        this.name = name;
        this.age = age;
        this.number = number;
        this.position = position;
        this.duty = duty;
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

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
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
     * Scores a goal and increments the goalsScored count.
     */
    public void scoreGoal() {
        if (duty == Duty.STRIKER) {
            goalsScored++;
        }
    }

    /**
     * Saves a goal and increments the goalsSaved count.
     */
    public void saveGoal() {
        if (duty == Duty.GOALKEEPER) {
            goalsSaved++;
        }
    }

    /**
     * Announces the player's number and name when they score a goal or save a goal.
     * 
     * @return the announcement message
     */
    public String announce() {
        if (duty == Duty.STRIKER && goalsScored > 0) {
            return "Player " + number + " " + name + " scored a goal!";
        } else if (duty == Duty.GOALKEEPER && goalsSaved > 0) {
            return "Player " + number + " " + name + " saved a goal!";
        }
        return "";
    }
}

/**
 * Represents a football team with a list of players and methods to manage them.
 */
class FootballTeam {
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
     * Constructor for FootballTeam with parameters.
     * 
     * @param startingEleven the list of starting eleven players
     * @param spareTeam      the list of spare team players
     */
    public FootballTeam(List<FootballPlayer> startingEleven, List<FootballPlayer> spareTeam) {
        this.startingEleven = startingEleven;
        this.spareTeam = spareTeam;
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
     * Calculates the total number of goal-scoring announcements for the starting eleven players.
     * 
     * @return the total number of goal-scoring announcements
     */
    public int calculateTotalGoalScoringAnnouncements() {
        int total = 0;
        for (FootballPlayer player : startingEleven) {
            if (player.getDuty() == Duty.STRIKER) {
                total += player.getGoalsScored();
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
     * @return the total number of goal-saving announcements
     */
    public int countGoalSavingAnnouncements() {
        int total = 0;
        for (FootballPlayer player : startingEleven) {
            if (player.getDuty() == Duty.GOALKEEPER) {
                total += player.getGoalsSaved();
            }
        }
        for (FootballPlayer player : spareTeam) {
            if (player.getDuty() == Duty.GOALKEEPER) {
                total += player.getGoalsSaved();
            }
        }
        return total;
    }

    /**
     * Calculates the total number of announcements made by all forwards on the team.
     * 
     * @return the total number of announcements made by forwards
     */
    public int calculateTotalAnnouncementsByForwards() {
        int total = 0;
        for (FootballPlayer player : startingEleven) {
            if (player.getDuty() == Duty.STRIKER) {
                total += player.getGoalsScored();
            }
        }
        for (FootballPlayer player : spareTeam) {
            if (player.getDuty() == Duty.STRIKER) {
                total += player.getGoalsScored();
            }
        }
        return total;
    }
}