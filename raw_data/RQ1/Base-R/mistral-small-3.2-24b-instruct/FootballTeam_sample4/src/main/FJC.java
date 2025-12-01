import java.util.ArrayList;
import java.util.List;

class FootballTeam {
    private List<FootballPlayer> players = new ArrayList<>();
    private List<FootballPlayer> startingEleven = new ArrayList<>();
    private List<FootballPlayer> spareTeam = new ArrayList<>();

    /**
     * Adds a new player to the team.
     * @param player The player to be added.
     */
    public void addPlayer(FootballPlayer player) {
        players.add(player);
    }

    /**
     * Sets the starting eleven players.
     * @param startingEleven The list of starting eleven players.
     */
    public void setStartingEleven(List<FootballPlayer> startingEleven) {
        this.startingEleven = startingEleven;
    }

    /**
     * Sets the spare team players.
     * @param spareTeam The list of spare team players.
     */
    public void setSpareTeam(List<FootballPlayer> spareTeam) {
        this.spareTeam = spareTeam;
    }

    /**
     * Calculates the total number of goal-scoring announcements for the starting eleven players.
     * @return The total number of goal-scoring announcements.
     */
    public int calculateStartingElevenGoalAnnouncements() {
        return startingEleven.stream()
                .filter(player -> player.getPosition() == Position.FORWARD)
                .mapToInt(FootballPlayer::getGoalAnnouncements)
                .sum();
    }

    /**
     * Identifies the midfielder with the highest number.
     * @return The midfielder with the highest number, or null if no midfielders found.
     */
    public FootballPlayer findHighestNumberMidfielder() {
        return players.stream()
                .filter(player -> player.getPosition() == Position.MIDFIELD)
                .max((p1, p2) -> Integer.compare(p1.getNumber(), p2.getNumber()))
                .orElse(null);
    }

    /**
     * Calculates the average age of spare team players.
     * @return The average age of spare team players, or 0.0 if the spare team is empty.
     */
    public double calculateAverageAgeSpareTeam() {
        if (spareTeam.isEmpty()) {
            return 0.0;
        }
        double sum = spareTeam.stream()
                .mapToDouble(FootballPlayer::getAge)
                .sum();
        return sum / spareTeam.size();
    }

    /**
     * Counts the number of goal-saving announcements made by all goalkeepers on the team.
     * @return The number of goal-saving announcements.
     */
    public int countGoalkeeperGoalSavingAnnouncements() {
        return players.stream()
                .filter(player -> player.getPosition() == Position.DEFENSE)
                .mapToInt(FootballPlayer::getGoalSavingAnnouncements)
                .sum();
    }

    /**
     * Calculates the total number of announcements made by all forwards on the team.
     * @return The total number of announcements made by forwards.
     */
    public int calculateTotalForwardAnnouncements() {
        return players.stream()
                .filter(player -> player.getPosition() == Position.FORWARD)
                .mapToInt(player -> player.getGoalAnnouncements() + player.getGoalSavingAnnouncements())
                .sum();
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
     * @return the spareTeam
     */
    public List<FootballPlayer> getSpareTeam() {
        return spareTeam;
    }
}

class FootballPlayer {
    private FootballTeam team;
    private String name;
    private int age;
    private int number;
    private Position position;
    private int goalAnnouncements;
    private int goalSavingAnnouncements;

    public FootballPlayer() {
    }

    /**
     * Announces a goal scored by the player.
     */
    public void announceGoal() {
        if (position == Position.FORWARD) {
            goalAnnouncements++;
        }
    }

    /**
     * Announces a goal saved by the player.
     */
    public void announceGoalSaved() {
        if (position == Position.DEFENSE) {
            goalSavingAnnouncements++;
        }
    }

    /**
     * @return the team
     */
    public FootballTeam getTeam() {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(FootballTeam team) {
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
     * @return the goalAnnouncements
     */
    public int getGoalAnnouncements() {
        return goalAnnouncements;
    }

    /**
     * @param goalAnnouncements the goalAnnouncements to set
     */
    public void setGoalAnnouncements(int goalAnnouncements) {
        this.goalAnnouncements = goalAnnouncements;
    }

    /**
     * @return the goalSavingAnnouncements
     */
    public int getGoalSavingAnnouncements() {
        return goalSavingAnnouncements;
    }

    /**
     * @param goalSavingAnnouncements the goalSavingAnnouncements to set
     */
    public void setGoalSavingAnnouncements(int goalSavingAnnouncements) {
        this.goalSavingAnnouncements = goalSavingAnnouncements;
    }
}

enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE
}