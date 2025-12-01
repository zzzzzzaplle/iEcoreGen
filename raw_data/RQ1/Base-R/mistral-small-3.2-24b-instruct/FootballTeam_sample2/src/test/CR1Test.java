import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Create a new football team before each test
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // SetUp: Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Create 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 4 forwards with 2 score announcements each
        for (int i = 1; i <= 4; i++) {
            FootballPlayer forward = new FootballPlayer();
            forward.setPosition(Position.FORWARD);
            forward.setScoreAnnouncements(2);
            startingEleven.add(forward);
        }
        
        // Create 7 non-forward players with 0 score announcements
        for (int i = 1; i <= 7; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.MIDFIELD); // Using midfield as non-forward example
            player.setScoreAnnouncements(0);
            startingEleven.add(player);
        }
        
        // Set the starting eleven
        team.setStartingEleven(startingEleven);
        
        // Calculate total goal scoring announcements
        int result = team.calculateGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        assertEquals("Total announcements should be 8 for 4 forwards with 2 goals each", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // SetUp: Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Create 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 2 forwards with 3 score announcements each
        for (int i = 1; i <= 2; i++) {
            FootballPlayer forward = new FootballPlayer();
            forward.setPosition(Position.FORWARD);
            forward.setScoreAnnouncements(3);
            startingEleven.add(forward);
        }
        
        // Create 2 midfielders (their score announcements should be ignored)
        for (int i = 1; i <= 2; i++) {
            FootballPlayer midfielder = new FootballPlayer();
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setScoreAnnouncements(5); // Even if midfielders have scores, they should be ignored
            startingEleven.add(midfielder);
        }
        
        // Create 7 non-forward players with 0 score announcements
        for (int i = 1; i <= 7; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.DEFENSE); // Using defense as non-forward example
            player.setScoreAnnouncements(0);
            startingEleven.add(player);
        }
        
        // Set the starting eleven
        team.setStartingEleven(startingEleven);
        
        // Calculate total goal scoring announcements
        int result = team.calculateGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        assertEquals("Total announcements should be 6 for 2 forwards with 3 goals each", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // SetUp: Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 3 forwards with 0 score announcements
        for (int i = 1; i <= 3; i++) {
            FootballPlayer forward = new FootballPlayer();
            forward.setPosition(Position.FORWARD);
            forward.setScoreAnnouncements(0);
            startingEleven.add(forward);
        }
        
        // Create 8 non-forward players with 0 score announcements
        for (int i = 1; i <= 8; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.MIDFIELD);
            player.setScoreAnnouncements(0);
            startingEleven.add(player);
        }
        
        // Set the starting eleven
        team.setStartingEleven(startingEleven);
        
        // Calculate total goal scoring announcements
        int result = team.calculateGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0
        assertEquals("Total announcements should be 0 when no players have score announcements", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // SetUp: Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 1 goalkeeper with goal saving announcements but no score announcements
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalSavingAnnouncements(2); // Has save announcements but no score announcements
        goalkeeper.setScoreAnnouncements(0);
        startingEleven.add(goalkeeper);
        
        // Create 10 non-forward players with 0 score announcements
        for (int i = 1; i <= 10; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.DEFENSE);
            player.setScoreAnnouncements(0);
            startingEleven.add(player);
        }
        
        // Set the starting eleven
        team.setStartingEleven(startingEleven);
        
        // Calculate total goal scoring announcements
        int result = team.calculateGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        assertEquals("Total announcements should be 0 when only goalkeeper has save announcements", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // SetUp: Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Do not add any players to the first eleven (startingEleven remains empty)
        List<FootballPlayer> emptyStartingEleven = new ArrayList<>();
        team.setStartingEleven(emptyStartingEleven);
        
        // Calculate total goal scoring announcements
        int result = team.calculateGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        assertEquals("Total announcements should be 0 when starting eleven is empty", 0, result);
    }
}