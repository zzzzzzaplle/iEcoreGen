import java.util.ArrayList;
import java.util.List;

/**
 * Represents a football team with players and team management functionality.
 */
class FootballTeam {
    private String name;
    private List<FootballPlayer> players;
    private List<FootballPlayer> startingEleven;
    private List<FootballPlayer> spareTeam;

    /**
     * Default constructor initializing empty player lists.
     */
    public FootballTeam() {
        this.players = new ArrayList<>();
        this.startingEleven = new ArrayList<>();
        this.spareTeam = new ArrayList<>();
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
     * Only announcements from forwards are counted.
     *
     * @return total number of goal-scoring announcements by forwards in starting eleven
     */
    public int calculateStartingElevenGoalAnnouncements() {
        int total = 0;
        for (FootballPlayer player : startingEleven) {
            if (player.getPosition() == Position.FORWARD) {
                total += player.getGoalAnnouncements();
            }
        }
        return total;
    }

    /**
     * Finds the midfielder with the highest player number in the team.
     *
     * @return midfielder with highest number, or null if no midfielders exist
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
     * Counts the total number of goal-saving announcements made by all goalkeepers.
     *
     * @return total goal-saving announcements by goalkeepers, 0 if none exist
     */
    public int countGoalkeeperSaveAnnouncements() {
        int total = 0;
        for (FootballPlayer player : players) {
            if (player.getPosition() == Position.GOALKEEPER) {
                total += player.getSaveAnnouncements();
            }
        }
        return total;
    }

    /**
     * Calculates the total number of announcements made by all forwards on the team.
     *
     * @return total announcements by forwards, 0 if no forwards or announcements
     */
    public int calculateTotalForwardAnnouncements() {
        int total = 0;
        for (FootballPlayer player : players) {
            if (player.getPosition() == Position.FORWARD) {
                total += player.getGoalAnnouncements();
            }
        }
        return total;
    }
}

/**
 * Represents a football player with personal and performance attributes.
 */
class FootballPlayer {
    private String name;
    private int age;
    private int number;
    private Position position;
    private int goalAnnouncements;
    private int saveAnnouncements;

    /**
     * Default constructor initializing default values.
     */
    public FootballPlayer() {
        this.goalAnnouncements = 0;
        this.saveAnnouncements = 0;
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

    public int getGoalAnnouncements() {
        return goalAnnouncements;
    }

    public void setGoalAnnouncements(int goalAnnouncements) {
        this.goalAnnouncements = goalAnnouncements;
    }

    public int getSaveAnnouncements() {
        return saveAnnouncements;
    }

    public void setSaveAnnouncements(int saveAnnouncements) {
        this.saveAnnouncements = saveAnnouncements;
    }

    /**
     * Records a goal announcement for this player.
     * System would announce the player's number and name when called.
     */
    public void scoreGoal() {
        this.goalAnnouncements++;
        System.out.println("Goal! Number " + number + ": " + name);
    }

    /**
     * Records a save announcement for this player.
     * System would announce the player's number and name when called.
     */
    public void saveGoal() {
        this.saveAnnouncements++;
        System.out.println("Save! Number " + number + ": " + name);
    }
}

/**
 * Enumeration of possible football player positions.
 */
enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE,
    GOALKEEPER
}