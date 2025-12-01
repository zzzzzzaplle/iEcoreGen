import java.util.ArrayList;
import java.util.List;

 class FootballTeam {
    private List<FootballPlayer> players;
    private List<FootballPlayer> startingEleven;
    private List<FootballPlayer> spareTeam;

    public FootballTeam() {
        this.players = new ArrayList<>();
        this.startingEleven = new ArrayList<>();
        this.spareTeam = new ArrayList<>();
    }

    public void addPlayer(FootballPlayer player) {
        this.players.add(player);
    }

    public void setStartingEleven(List<FootballPlayer> startingEleven) {
        this.startingEleven = startingEleven;
    }

    public void setSpareTeam(List<FootballPlayer> spareTeam) {
        this.spareTeam = spareTeam;
    }

    public List<FootballPlayer> getPlayers() {
        return players;
    }

    public List<FootballPlayer> getStartingEleven() {
        return startingEleven;
    }

    public List<FootballPlayer> getSpareTeam() {
        return spareTeam;
    }

    /**
     * Calculates the total number of goal-scoring announcements for the starting eleven players.
     *
     * @return the total number of goal-scoring announcements
     */
    public int calculateGoalScoringAnnouncements() {
        int count = 0;
        for (FootballPlayer player : startingEleven) {
            if (player.getPosition() == Position.FORWARD) {
                count += player.getGoalScoringAnnouncements();
            }
        }
        return count;
    }

    /**
     * Identifies the midfielder with the highest number.
     *
     * @return the midfielder with the highest number, or null if no midfielders found
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
     * Counts the number of goal-saving announcements made by all goalkeepers on the team.
     *
     * @return the number of goal-saving announcements
     */
    public int countGoalSavingAnnouncements() {
        int count = 0;
        for (FootballPlayer player : players) {
            if (player.getPosition() == Position.GOALKEEPER) {
                count += player.getGoalSavingAnnouncements();
            }
        }
        return count;
    }

    /**
     * Calculates the total number of announcements made by all forwards on the team.
     *
     * @return the total number of announcements made by forwards
     */
    public int calculateTotalAnnouncementsByForwards() {
        int count = 0;
        for (FootballPlayer player : players) {
            if (player.getPosition() == Position.FORWARD) {
                count += player.getTotalAnnouncements();
            }
        }
        return count;
    }
}

 class FootballPlayer {
    private FootballTeam team;
    private String name;
    private int age;
    private int number;
    private Position position;
    private int goalScoringAnnouncements;
    private int goalSavingAnnouncements;

    public FootballPlayer() {
        this.goalScoringAnnouncements = 0;
        this.goalSavingAnnouncements = 0;
    }

    public void setTeam(FootballTeam team) {
        this.team = team;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public FootballTeam getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getNumber() {
        return number;
    }

    public Position getPosition() {
        return position;
    }

    public int getGoalScoringAnnouncements() {
        return goalScoringAnnouncements;
    }

    public int getGoalSavingAnnouncements() {
        return goalSavingAnnouncements;
    }

    public int getTotalAnnouncements() {
        return goalScoringAnnouncements + goalSavingAnnouncements;
    }

    /**
     * Increments the goal-scoring announcements count.
     */
    public void incrementGoalScoringAnnouncements() {
        this.goalScoringAnnouncements++;
    }

    /**
     * Increments the goal-saving announcements count.
     */
    public void incrementGoalSavingAnnouncements() {
        this.goalSavingAnnouncements++;
    }
}

 enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE,
    GOALKEEPER
}