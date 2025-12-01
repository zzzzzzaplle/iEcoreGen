import java.util.ArrayList;
import java.util.List;

 class FootballTeam {
    private List<FootballPlayer> players;
    private List<FootballPlayer> spareTeam;

    public FootballTeam() {
        this.players = new ArrayList<>();
        this.spareTeam = new ArrayList<>();
    }

    public void addPlayer(FootballPlayer player) {
        players.add(player);
    }

    public void addSparePlayer(FootballPlayer player) {
        spareTeam.add(player);
    }

    public List<FootballPlayer> getPlayers() {
        return players;
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
        for (FootballPlayer player : players) {
            if (player.getPosition() == Position.FORWARD && player.getGoalScoringAnnouncements() > 0) {
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
     * @return the average age as a floating-point number, or 0.0 if the spare team is empty
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
        int count = 0;
        for (FootballPlayer player : players) {
            if (player.getPosition() == Position.GOALKEEPER && player.getGoalSavingAnnouncements() > 0) {
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
            if (player.getPosition() == Position.FORWARD && player.getAnnouncements() > 0) {
                count += player.getAnnouncements();
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
    private int announcements;

    public FootballPlayer() {
        this.goalScoringAnnouncements = 0;
        this.goalSavingAnnouncements = 0;
        this.announcements = 0;
    }

    public FootballTeam getTeam() {
        return team;
    }

    public void setTeam(FootballTeam team) {
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

    public int getGoalScoringAnnouncements() {
        return goalScoringAnnouncements;
    }

    public void setGoalScoringAnnouncements(int goalScoringAnnouncements) {
        this.goalScoringAnnouncements = goalScoringAnnouncements;
    }

    public int getGoalSavingAnnouncements() {
        return goalSavingAnnouncements;
    }

    public void setGoalSavingAnnouncements(int goalSavingAnnouncements) {
        this.goalSavingAnnouncements = goalSavingAnnouncements;
    }

    public int getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(int announcements) {
        this.announcements = announcements;
    }

    /**
     * Announces the player's number and name when a goal is scored.
     */
    public void announceGoalScored() {
        if (position == Position.FORWARD) {
            System.out.println("Goal scored by " + name + " (Number: " + number + ")");
            goalScoringAnnouncements++;
            announcements++;
        }
    }

    /**
     * Announces the player's number and name when a goal is saved.
     */
    public void announceGoalSaved() {
        if (position == Position.GOALKEEPER) {
            System.out.println("Goal saved by " + name + " (Number: " + number + ")");
            goalSavingAnnouncements++;
            announcements++;
        }
    }
}

 enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE,
    GOALKEEPER
}