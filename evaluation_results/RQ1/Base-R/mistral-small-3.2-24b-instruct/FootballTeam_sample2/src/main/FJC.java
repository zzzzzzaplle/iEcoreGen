import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     * Only forwards can generate score announcements.
     *
     * @return the total number of goal-scoring announcements
     */
    public int calculateGoalScoringAnnouncements() {
        int count = 0;
        for (FootballPlayer player : startingEleven) {
            if (player.getPosition() == Position.FORWARD) {
                count += player.getScoreAnnouncements();
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
}

 class FootballPlayer {
    private String name;
    private int age;
    private int number;
    private Position position;
    private int scoreAnnouncements;
    private int goalSavingAnnouncements;

    public FootballPlayer() {
        this.name = "";
        this.age = 0;
        this.number = 0;
        this.position = Position.FORWARD;
        this.scoreAnnouncements = 0;
        this.goalSavingAnnouncements = 0;
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

    public int getScoreAnnouncements() {
        return scoreAnnouncements;
    }

    public void setScoreAnnouncements(int scoreAnnouncements) {
        this.scoreAnnouncements = scoreAnnouncements;
    }

    public int getGoalSavingAnnouncements() {
        return goalSavingAnnouncements;
    }

    public void setGoalSavingAnnouncements(int goalSavingAnnouncements) {
        this.goalSavingAnnouncements = goalSavingAnnouncements;
    }

    /**
     * Announces the player's number and name when they score a goal.
     */
    public void announceGoalScoring() {
        if (position == Position.FORWARD) {
            System.out.println("Goal scored by player #" + number + ": " + name);
            scoreAnnouncements++;
        }
    }

    /**
     * Announces the player's number and name when they save a goal.
     */
    public void announceGoalSaving() {
        if (position == Position.GOALKEEPER) {
            System.out.println("Goal saved by player #" + number + ": " + name);
            goalSavingAnnouncements++;
        }
    }
}

 enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE,
    GOALKEEPER
}