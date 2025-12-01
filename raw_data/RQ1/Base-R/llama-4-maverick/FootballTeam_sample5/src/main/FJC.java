import java.util.ArrayList;
import java.util.List;

/**
 * Enum for player positions.
 */
enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE,
    STRIKER,
    GOALKEEPER,
    MIDFIELDER
}

/**
 * Represents a football player.
 */
class Player {
    private String team;
    private String name;
    private int age;
    private int number;
    private Position position;

    /**
     * Unparameterized constructor for Player.
     */
    public Player() {}

    /**
     * Constructor for Player.
     * 
     * @param team     the team of the player
     * @param name     the name of the player
     * @param age      the age of the player
     * @param number   the number of the player
     * @param position the position of the player
     */
    public Player(String team, String name, int age, int number, Position position) {
        this.team = team;
        this.name = name;
        this.age = age;
        this.number = number;
        this.position = position;
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
 * Represents a football team.
 */
class FootballTeam {
    private List<Player> startingEleven;
    private List<Player> spareTeam;

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
     * @param startingEleven the starting eleven players
     * @param spareTeam      the spare team players
     */
    public FootballTeam(List<Player> startingEleven, List<Player> spareTeam) {
        this.startingEleven = startingEleven;
        this.spareTeam = spareTeam;
    }

    // Getters and setters
    public List<Player> getStartingEleven() {
        return startingEleven;
    }

    public void setStartingEleven(List<Player> startingEleven) {
        this.startingEleven = startingEleven;
    }

    public List<Player> getSpareTeam() {
        return spareTeam;
    }

    public void setSpareTeam(List<Player> spareTeam) {
        this.spareTeam = spareTeam;
    }

    /**
     * Calculate the total number of goal-scoring announcements for the starting
     * eleven players.
     * 
     * @return the total number of goal-scoring announcements
     */
    public int calculateTotalGoalScoringAnnouncements() {
        int count = 0;
        for (Player player : startingEleven) {
            if (player.getPosition() == Position.FORWARD || player.getPosition() == Position.STRIKER) {
                count++;
            }
        }
        return count;
    }

    /**
     * Identify the midfielder with the highest number.
     * 
     * @return the midfielder with the highest number, or null if no midfielders found
     */
    public Player identifyMidfielderWithHighestNumber() {
        Player maxMidfielder = null;
        int maxNumber = Integer.MIN_VALUE;
        for (Player player : startingEleven) {
            if (player.getPosition() == Position.MIDFIELD || player.getPosition() == Position.MIDFIELDER) {
                if (player.getNumber() > maxNumber) {
                    maxNumber = player.getNumber();
                    maxMidfielder = player;
                }
            }
        }
        for (Player player : spareTeam) {
            if (player.getPosition() == Position.MIDFIELD || player.getPosition() == Position.MIDFIELDER) {
                if (player.getNumber() > maxNumber) {
                    maxNumber = player.getNumber();
                    maxMidfielder = player;
                }
            }
        }
        return maxMidfielder;
    }

    /**
     * Calculate the average age of spare team players.
     * 
     * @return the average age of spare team players, or 0.0 if the spare team is empty
     */
    public double calculateAverageAgeOfSpareTeam() {
        if (spareTeam.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (Player player : spareTeam) {
            sum += player.getAge();
        }
        return (double) sum / spareTeam.size();
    }

    /**
     * Count the number of goal-saving announcements made by all goalkeepers on the
     * team.
     * 
     * @return the number of goal-saving announcements
     */
    public int countGoalSavingAnnouncements() {
        int count = 0;
        for (Player player : startingEleven) {
            if (player.getPosition() == Position.GOALKEEPER) {
                count++;
            }
        }
        for (Player player : spareTeam) {
            if (player.getPosition() == Position.GOALKEEPER) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculate the total number of announcements made by all forwards on the team.
     * 
     * @return the total number of announcements made by forwards
     */
    public int calculateTotalAnnouncementsByForwards() {
        int count = 0;
        for (Player player : startingEleven) {
            if (player.getPosition() == Position.FORWARD || player.getPosition() == Position.STRIKER) {
                count++;
            }
        }
        for (Player player : spareTeam) {
            if (player.getPosition() == Position.FORWARD || player.getPosition() == Position.STRIKER) {
                count++;
            }
        }
        return count;
    }
}