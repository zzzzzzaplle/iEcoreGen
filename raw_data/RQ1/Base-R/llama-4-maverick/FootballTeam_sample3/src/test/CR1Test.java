import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Reset the team before each test
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // Test Case 1: "All Strikers with Goals"
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Create starting eleven players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Add 4 forwards with 2 score announcements each
        for (int i = 1; i <= 4; i++) {
            FootballPlayer striker = new FootballPlayer("Turgutlu", "Striker" + i, 25, i, Position.FORWARD);
            striker.setScoreAnnouncements(2);
            startingEleven.add(striker);
        }
        
        // Add 7 non-forward players (midfielders and defenders) with 0 score announcements
        for (int i = 5; i <= 11; i++) {
            Position position = (i % 2 == 0) ? Position.MIDFIELD : Position.DEFENSE;
            FootballPlayer player = new FootballPlayer("Turgutlu", "Player" + i, 26, i, position);
            player.setScoreAnnouncements(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalScoreAnnouncements();
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        assertEquals("Total announcements should be 8 for 4 strikers with 2 goals each", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // Test Case 2: "Mixed Players Scoring"
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Create starting eleven players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Add 2 forwards with 3 score announcements each
        for (int i = 1; i <= 2; i++) {
            FootballPlayer striker = new FootballPlayer("Turgutlu", "Striker" + i, 24, i, Position.FORWARD);
            striker.setScoreAnnouncements(3);
            startingEleven.add(striker);
        }
        
        // Add 2 midfielders (should not contribute to score announcements)
        for (int i = 3; i <= 4; i++) {
            FootballPlayer midfielder = new FootballPlayer("Turgutlu", "Midfielder" + i, 25, i, Position.MIDFIELD);
            midfielder.setScoreAnnouncements(0);
            startingEleven.add(midfielder);
        }
        
        // Add 7 more players (mixed positions) with 0 score announcements
        for (int i = 5; i <= 11; i++) {
            Position position = (i % 3 == 0) ? Position.FORWARD : ((i % 3 == 1) ? Position.MIDFIELD : Position.DEFENSE);
            FootballPlayer player = new FootballPlayer("Turgutlu", "Player" + i, 26, i, position);
            player.setScoreAnnouncements(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalScoreAnnouncements();
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        assertEquals("Total announcements should be 6 for 2 strikers with 3 goals each", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // Test Case 3: "First Eleven with No Goals"
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Create starting eleven players: All players (including 3 Strikers) scored 0 goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Add 3 forwards with 0 score announcements
        for (int i = 1; i <= 3; i++) {
            FootballPlayer striker = new FootballPlayer("Turgutlu", "Striker" + i, 25, i, Position.FORWARD);
            striker.setScoreAnnouncements(0);
            startingEleven.add(striker);
        }
        
        // Add 8 more players (mixed positions) with 0 score announcements
        for (int i = 4; i <= 11; i++) {
            Position position = (i % 3 == 0) ? Position.FORWARD : ((i % 3 == 1) ? Position.MIDFIELD : Position.DEFENSE);
            FootballPlayer player = new FootballPlayer("Turgutlu", "Player" + i, 26, i, position);
            player.setScoreAnnouncements(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalScoreAnnouncements();
        
        // Expected Output: Total announcements = 0
        assertEquals("Total announcements should be 0 when no players have scored goals", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // Test Case 4: "Only Goalkeeper with Save Announcements"
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Create starting eleven players: including 1 Goalkeeper who saved 2 goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Add 1 goalkeeper (defense position) with 2 goal saving announcements
        FootballPlayer goalkeeper = new FootballPlayer("Turgutlu", "Goalkeeper", 28, 1, Position.DEFENSE);
        goalkeeper.setGoalSavingAnnouncements(2);
        goalkeeper.setScoreAnnouncements(0);
        startingEleven.add(goalkeeper);
        
        // Add 10 more players (including forwards) with 0 score announcements
        for (int i = 2; i <= 11; i++) {
            Position position = (i <= 5) ? Position.FORWARD : ((i <= 8) ? Position.MIDFIELD : Position.DEFENSE);
            FootballPlayer player = new FootballPlayer("Turgutlu", "Player" + i, 25, i, position);
            player.setScoreAnnouncements(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalScoreAnnouncements();
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        assertEquals("Total announcements should be 0 when only goalkeeper has save announcements", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // Test Case 5: "No Players in First Eleven"
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Do not add any players to the first eleven (empty list)
        team.setStartingEleven(new ArrayList<FootballPlayer>());
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalScoreAnnouncements();
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        assertEquals("Total announcements should be 0 when there are no players in starting eleven", 0, result);
    }
}