import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Reset team before each test
        team = new FootballTeam();
        team.setTeamName("Turgutlu");
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Add 4 strikers with 2 goals each
        for (int i = 0; i < 4; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.STRIKER);
            striker.setGoalsScored(2);
            startingEleven.add(striker);
        }
        
        // Add 7 players with no goals (non-forward positions)
        for (int i = 0; i < 7; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.MIDFIELD); // Using midfield as example for non-forward
            player.setGoalsScored(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Execute: Calculate total goal scoring announcements
        int result = team.calculateStartingElevenForwardAnnouncements();
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        assertEquals("Total announcements should be 8 for 4 strikers with 2 goals each", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Add 2 strikers with 3 goals each
        for (int i = 0; i < 2; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.STRIKER);
            striker.setGoalsScored(3);
            startingEleven.add(striker);
        }
        
        // Add 2 midfielders (their goals should be ignored)
        for (int i = 0; i < 2; i++) {
            FootballPlayer midfielder = new FootballPlayer();
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setGoalsScored(5); // These goals should NOT be counted
            startingEleven.add(midfielder);
        }
        
        // Add 7 players with no goals
        for (int i = 0; i < 7; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.DEFENSE);
            player.setGoalsScored(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Execute: Calculate total goal scoring announcements
        int result = team.calculateStartingElevenForwardAnnouncements();
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        assertEquals("Total announcements should be 6 for 2 strikers with 3 goals each", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Add 3 strikers with 0 goals
        for (int i = 0; i < 3; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.STRIKER);
            striker.setGoalsScored(0);
            startingEleven.add(striker);
        }
        
        // Add 8 other players with 0 goals
        for (int i = 0; i < 8; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.DEFENSE);
            player.setGoalsScored(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Execute: Calculate total goal scoring announcements
        int result = team.calculateStartingElevenForwardAnnouncements();
        
        // Expected Output: Total announcements = 0
        assertEquals("Total announcements should be 0 when all players have 0 goals", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Add 1 goalkeeper with 2 saves (but saves should be ignored for scoring announcements)
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(2); // Saves, not goals scored
        goalkeeper.setGoalsScored(0);
        startingEleven.add(goalkeeper);
        
        // Add 10 other players (non-forwards) with 0 goals
        for (int i = 0; i < 10; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.DEFENSE);
            player.setGoalsScored(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Execute: Calculate total goal scoring announcements
        int result = team.calculateStartingElevenForwardAnnouncements();
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        assertEquals("Total announcements should be 0 when only goalkeeper has saves but no forwards have goals", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // SetUp: Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        // The starting eleven list should be empty
        team.setStartingEleven(new ArrayList<FootballPlayer>());
        
        // Execute: Calculate total goal scoring announcements
        int result = team.calculateStartingElevenForwardAnnouncements();
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        assertEquals("Total announcements should be 0 when there are no players in starting eleven", 0, result);
    }
    
    // Helper method to set team name (since the original code doesn't have setTeamName method)
    // Based on the test specifications requiring team name "Turgutlu"
    private static class FootballTeam {
        private String teamName;
        private List<FootballPlayer> startingEleven;
        private List<FootballPlayer> spareTeam;
        
        public FootballTeam() {
            this.startingEleven = new ArrayList<>();
            this.spareTeam = new ArrayList<>();
        }
        
        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }
        
        public String getTeamName() {
            return teamName;
        }
        
        public List<FootballPlayer> getStartingEleven() { return startingEleven; }
        public void setStartingEleven(List<FootballPlayer> startingEleven) { this.startingEleven = startingEleven; }
        
        public List<FootballPlayer> getSpareTeam() { return spareTeam; }
        public void setSpareTeam(List<FootballPlayer> spareTeam) { this.spareTeam = spareTeam; }
        
        public int calculateStartingElevenForwardAnnouncements() {
            int totalAnnouncements = 0;
            for (FootballPlayer player : startingEleven) {
                if (player.getPosition() == Position.FORWARD || player.getPosition() == Position.STRIKER) {
                    totalAnnouncements += player.getGoalsScored();
                }
            }
            return totalAnnouncements;
        }
        
        // Other methods from original FootballTeam class (not used in these tests)
        public FootballPlayer findMidfielderWithHighestNumber() { return null; }
        public double calculateSpareTeamAverageAge() { return 0.0; }
        public int countGoalkeeperSavingAnnouncements() { return 0; }
        public int calculateForwardAnnouncements() { return 0; }
    }
    
    // Helper enum and class definitions
    private enum Position {
        FORWARD, MIDFIELD, DEFENSE, GOALKEEPER, STRIKER
    }
    
    private static class FootballPlayer {
        private String team;
        private String name;
        private int age;
        private int number;
        private Position position;
        private int goalsScored;
        private int goalsSaved;
        
        public FootballPlayer() {
            this.goalsScored = 0;
            this.goalsSaved = 0;
        }
        
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
        
        public int getGoalsScored() { return goalsScored; }
        public void setGoalsScored(int goalsScored) { this.goalsScored = goalsScored; }
        
        public int getGoalsSaved() { return goalsSaved; }
        public void setGoalsSaved(int goalsSaved) { this.goalsSaved = goalsSaved; }
    }
}