import java.util.ArrayList;
import java.util.Date;
import java.util.List;

enum Position {
    FORWARD,
    MIDFIELD,
    DEFENSE,
    GOALKEEPER
}

enum AnnouncementType {
    SCORE,
    SAVE
}

class Player {
    private String name;
    private int age;
    private int number;
    private Position position;

    public Player() {
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

class Announcement {
    private Date time;
    private AnnouncementType type;
    private Player player;

    public Announcement() {
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public AnnouncementType getType() {
        return type;
    }

    public void setType(AnnouncementType type) {
        this.type = type;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

class FootballTeam {
    private List<Player> firstEleven;
    private List<Player> spareTeam;
    private List<Player> players;
    private List<Announcement> announcements;

    public FootballTeam() {
        this.firstEleven = new ArrayList<>();
        this.spareTeam = new ArrayList<>();
        this.players = new ArrayList<>();
        this.announcements = new ArrayList<>();
    }

    /**
     * Calculates the total number of goal-scoring announcements for the starting eleven players.
     * Only announcements made by forwards are counted.
     *
     * @return The total number of goal-scoring announcements by the starting eleven forwards.
     */
    public int calculateTotalGoalScoringAnnouncementsForFirstEleven() {
        int count = 0;
        for (Announcement announcement : announcements) {
            if (announcement.getType() == AnnouncementType.SCORE &&
                firstEleven.contains(announcement.getPlayer()) &&
                announcement.getPlayer().getPosition() == Position.FORWARD) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the midfielder with the highest number.
     *
     * @return The midfielder with the highest number, or null if no midfielders are found.
     */
    public Player findMidfielderWithHighestNumber() {
        Player highestNumberMidfielder = null;
        for (Player player : players) {
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
     * @return The average age of spare team players as a floating-point number.
     *         Returns 0.0 if the spare team is empty.
     */
    public double calculateAverageAgeOfSpareTeam() {
        if (spareTeam.isEmpty()) {
            return 0.0;
        }
        int totalAge = 0;
        for (Player player : spareTeam) {
            totalAge += player.getAge();
        }
        return (double) totalAge / spareTeam.size();
    }

    /**
     * Counts the number of goal-saving announcements made by all goalkeepers on the team.
     *
     * @return The total number of goal-saving announcements by goalkeepers.
     *         Returns 0 if no such announcements exist.
     */
    public int calculateGoalSavingAnnouncementsByGoalkeeper() {
        int count = 0;
        for (Announcement announcement : announcements) {
            if (announcement.getType() == AnnouncementType.SAVE &&
                announcement.getPlayer().getPosition() == Position.GOALKEEPER) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the total number of announcements made by all forwards on the team.
     *
     * @return The total number of announcements made by forwards.
     *         Returns 0 if there are no forwards or no announcements.
     */
    public int calculateTotalAnnouncementsForForwardPlayers() {
        int count = 0;
        for (Announcement announcement : announcements) {
            if (announcement.getPlayer().getPosition() == Position.FORWARD) {
                count++;
            }
        }
        return count;
    }

    // Getters and setters for the fields
    public List<Player> getFirstEleven() {
        return firstEleven;
    }

    public void setFirstEleven(List<Player> firstEleven) {
        this.firstEleven = firstEleven;
    }

    public List<Player> getSpareTeam() {
        return spareTeam;
    }

    public void setSpareTeam(List<Player> spareTeam) {
        this.spareTeam = spareTeam;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }
}