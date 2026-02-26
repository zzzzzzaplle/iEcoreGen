import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Create a new football team before each test
        team = new FootballTeam();
        team.setTeam("Turgutlu");
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // Test Case 1: "All Strikers with Goals"
        // SetUp: Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        for (int i = 1; i <= 4; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.FORWARD);
            striker.setGoalsScored(2);
            team.addPlayerToStartingEleven(striker);
        }
        
        for (int i = 1; i <= 7; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.MIDFIELD); // Non-forward position
            player.setGoalsScored(0);
            team.addPlayerToStartingEleven(player);
        }
        
        // Calculate total announcements
        int result = team.calculateTotalGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        assertEquals("Total announcements should be 8", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // Test Case 2: "Mixed Players Scoring"
        // SetUp: Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        for (int i = 1; i <= 2; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.FORWARD);
            striker.setGoalsScored(3);
            team.addPlayerToStartingEleven(striker);
        }
        
        for (int i = 1; i <= 2; i++) {
            FootballPlayer midfielder = new FootballPlayer();
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setGoalsScored(0);
            team.addPlayerToStartingEleven(midfielder);
        }
        
        for (int i = 1; i <= 7; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.DEFENSE); // Non-forward position
            player.setGoalsScored(0);
            team.addPlayerToStartingEleven(player);
        }
        
        // Calculate total announcements
        int result = team.calculateTotalGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        assertEquals("Total announcements should be 6", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // Test Case 3: "First Eleven with No Goals"
        // SetUp: Add 11 players: All players (including 3 Strikers) scored 0 goals
        for (int i = 1; i <= 3; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.FORWARD);
            striker.setGoalsScored(0);
            team.addPlayerToStartingEleven(striker);
        }
        
        for (int i = 1; i <= 8; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.MIDFIELD); // Non-forward position
            player.setGoalsScored(0);
            team.addPlayerToStartingEleven(player);
        }
        
        // Calculate total announcements
        int result = team.calculateTotalGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0
        assertEquals("Total announcements should be 0", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // Test Case 4: "Only Goalkeeper with Save Announcements"
        // SetUp: Add 11 players, including 1 Goalkeeper who saved 2 goals
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(2); // Save announcements, not score announcements
        team.addPlayerToStartingEleven(goalkeeper);
        
        for (int i = 1; i <= 10; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.DEFENSE); // Non-forward position
            player.setGoalsScored(0);
            team.addPlayerToStartingEleven(player);
        }
        
        // Calculate total announcements
        int result = team.calculateTotalGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        assertEquals("Total announcements should be 0", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // Test Case 5: "No Players in First Eleven"
        // SetUp: Do not add any players to the first eleven
        // Team is already created with empty starting eleven in setUp()
        
        // Calculate total announcements
        int result = team.calculateTotalGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        assertEquals("Total announcements should be 0", 0, result);
    }
}