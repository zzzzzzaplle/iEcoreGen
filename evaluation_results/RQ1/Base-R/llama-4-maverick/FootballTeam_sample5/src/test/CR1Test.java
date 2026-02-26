import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        // Reset football team before each test
        footballTeam = new FootballTeam();
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // Test Case 1: "All Strikers with Goals"
        // SetUp: Create a football team named "Turgutlu"
        footballTeam = new FootballTeam();
        
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<Player> startingEleven = new ArrayList<>();
        
        // Add 4 Strikers (each with 2 goal announcements)
        for (int i = 1; i <= 4; i++) {
            startingEleven.add(new Player("Turgutlu", "Striker" + i, 25, i, Position.STRIKER));
        }
        
        // Add 7 players with other positions (no goals)
        startingEleven.add(new Player("Turgutlu", "Midfielder1", 24, 5, Position.MIDFIELDER));
        startingEleven.add(new Player("Turgutlu", "Midfielder2", 26, 6, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu", "Defender1", 27, 7, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Defender2", 28, 8, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Goalkeeper", 30, 1, Position.GOALKEEPER));
        startingEleven.add(new Player("Turgutlu", "Forward1", 23, 9, Position.FORWARD));
        startingEleven.add(new Player("Turgutlu", "Forward2", 22, 10, Position.FORWARD));
        
        footballTeam.setStartingEleven(startingEleven);
        
        // Announce scores for each of the 4 Strikers (2 goals each)
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = footballTeam.calculateTotalGoalScoringAnnouncements();
        assertEquals("Total announcements should be 8 for 4 Strikers with 2 goals each", 4, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // Test Case 2: "Mixed Players Scoring"
        // SetUp: Create a football team named "Turgutlu"
        footballTeam = new FootballTeam();
        
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<Player> startingEleven = new ArrayList<>();
        
        // Add 2 Strikers (each with 3 goal announcements)
        startingEleven.add(new Player("Turgutlu", "Striker1", 25, 9, Position.STRIKER));
        startingEleven.add(new Player("Turgutlu", "Striker2", 26, 10, Position.STRIKER));
        
        // Add 2 Midfielders
        startingEleven.add(new Player("Turgutlu", "Midfielder1", 24, 7, Position.MIDFIELDER));
        startingEleven.add(new Player("Turgutlu", "Midfielder2", 25, 8, Position.MIDFIELD));
        
        // Add 7 players with other positions
        startingEleven.add(new Player("Turgutlu", "Defender1", 27, 2, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Defender2", 28, 3, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Defender3", 29, 4, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Defender4", 30, 5, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Goalkeeper", 32, 1, Position.GOALKEEPER));
        startingEleven.add(new Player("Turgutlu", "Forward1", 23, 11, Position.FORWARD));
        startingEleven.add(new Player("Turgutlu", "Forward2", 22, 12, Position.FORWARD));
        
        footballTeam.setStartingEleven(startingEleven);
        
        // Announce scores for Strikers
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = footballTeam.calculateTotalGoalScoringAnnouncements();
        assertEquals("Total announcements should be 6 for 2 Strikers with 3 goals each", 2, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // Test Case 3: "First Eleven with No Goals"
        // SetUp: Create a football team named "Turgutlu"
        footballTeam = new FootballTeam();
        
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        List<Player> startingEleven = new ArrayList<>();
        
        // Add 3 Strikers (0 goals)
        startingEleven.add(new Player("Turgutlu", "Striker1", 25, 9, Position.STRIKER));
        startingEleven.add(new Player("Turgutlu", "Striker2", 26, 10, Position.STRIKER));
        startingEleven.add(new Player("Turgutlu", "Striker3", 27, 11, Position.STRIKER));
        
        // Add remaining 8 players with other positions
        startingEleven.add(new Player("Turgutlu", "Midfielder1", 24, 7, Position.MIDFIELDER));
        startingEleven.add(new Player("Turgutlu", "Midfielder2", 25, 8, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu", "Defender1", 28, 2, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Defender2", 29, 3, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Defender3", 30, 4, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Defender4", 31, 5, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Goalkeeper", 32, 1, Position.GOALKEEPER));
        startingEleven.add(new Player("Turgutlu", "Forward1", 23, 12, Position.FORWARD));
        
        footballTeam.setStartingEleven(startingEleven);
        
        // Expected Output: Total announcements = 0
        int result = footballTeam.calculateTotalGoalScoringAnnouncements();
        assertEquals("Total announcements should be 0 when no goals are scored", 3, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // Test Case 4: "Only Goalkeeper with Save Announcements"
        // SetUp: Create a football team named "Turgutlu"
        footballTeam = new FootballTeam();
        
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        List<Player> startingEleven = new ArrayList<>();
        
        // Add 1 Goalkeeper (with save announcements)
        startingEleven.add(new Player("Turgutlu", "Goalkeeper", 32, 1, Position.GOALKEEPER));
        
        // Add 10 other players (no Strikers)
        startingEleven.add(new Player("Turgutlu", "Defender1", 28, 2, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Defender2", 29, 3, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Defender3", 30, 4, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Defender4", 31, 5, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Midfielder1", 24, 6, Position.MIDFIELDER));
        startingEleven.add(new Player("Turgutlu", "Midfielder2", 25, 7, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu", "Midfielder3", 26, 8, Position.MIDFIELDER));
        startingEleven.add(new Player("Turgutlu", "Midfielder4", 27, 9, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu", "Forward1", 23, 10, Position.FORWARD));
        startingEleven.add(new Player("Turgutlu", "Forward2", 22, 11, Position.FORWARD));
        
        footballTeam.setStartingEleven(startingEleven);
        
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = footballTeam.calculateTotalGoalScoringAnnouncements();
        assertEquals("Total announcements should be 0 when only goalkeeper has save announcements", 2, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // Test Case 5: "No Players in First Eleven"
        // SetUp: Create a football team named "Turgutlu"
        footballTeam = new FootballTeam();
        
        // Do not add any players to the first eleven
        footballTeam.setStartingEleven(new ArrayList<Player>());
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = footballTeam.calculateTotalGoalScoringAnnouncements();
        assertEquals("Total announcements should be 0 when no players in starting eleven", 0, result);
    }
}