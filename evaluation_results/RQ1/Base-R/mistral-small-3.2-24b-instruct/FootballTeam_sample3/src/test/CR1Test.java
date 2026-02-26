import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Create a new team before each test
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 4 forwards with 2 goals each
        for (int i = 0; i < 4; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.FORWARD);
            striker.setGoalScoringAnnouncements(2); // 2 goals each
            startingEleven.add(striker);
        }
        
        // Create 7 non-forward players with no goals
        for (int i = 0; i < 7; i++) {
            FootballPlayer nonStriker = new FootballPlayer();
            nonStriker.setPosition(Position.MIDFIELD); // Can be any non-forward position
            nonStriker.setGoalScoringAnnouncements(0); // No goals
            startingEleven.add(nonStriker);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateGoalScoringAnnouncements();
        assertEquals("Total announcements should be 8 for 4 strikers with 2 goals each", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 2 forwards with 3 goals each
        for (int i = 0; i < 2; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.FORWARD);
            striker.setGoalScoringAnnouncements(3); // 3 goals each
            startingEleven.add(striker);
        }
        
        // Create 2 midfielders
        for (int i = 0; i < 2; i++) {
            FootballPlayer midfielder = new FootballPlayer();
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setGoalScoringAnnouncements(0); // No goals (ignored anyway)
            startingEleven.add(midfielder);
        }
        
        // Create 7 other players with no goals
        for (int i = 0; i < 7; i++) {
            FootballPlayer otherPlayer = new FootballPlayer();
            otherPlayer.setPosition(Position.DEFENSE); // Any non-forward position
            otherPlayer.setGoalScoringAnnouncements(0); // No goals
            startingEleven.add(otherPlayer);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateGoalScoringAnnouncements();
        assertEquals("Total announcements should be 6 for 2 strikers with 3 goals each", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 3 forwards with 0 goals
        for (int i = 0; i < 3; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.FORWARD);
            striker.setGoalScoringAnnouncements(0); // No goals
            startingEleven.add(striker);
        }
        
        // Create 8 other players with no goals
        for (int i = 0; i < 8; i++) {
            FootballPlayer otherPlayer = new FootballPlayer();
            otherPlayer.setPosition(Position.MIDFIELD); // Any non-forward position
            otherPlayer.setGoalScoringAnnouncements(0); // No goals
            startingEleven.add(otherPlayer);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Expected Output: Total announcements = 0
        int result = team.calculateGoalScoringAnnouncements();
        assertEquals("Total announcements should be 0 when no strikers scored goals", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 1 goalkeeper with 2 saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalSavingAnnouncements(2); // 2 saves (but these don't count for goal scoring)
        goalkeeper.setGoalScoringAnnouncements(0); // No goals scored
        startingEleven.add(goalkeeper);
        
        // Create 10 other non-forward players with no goals
        for (int i = 0; i < 10; i++) {
            FootballPlayer otherPlayer = new FootballPlayer();
            otherPlayer.setPosition(Position.DEFENSE); // Any non-forward position
            otherPlayer.setGoalScoringAnnouncements(0); // No goals
            startingEleven.add(otherPlayer);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateGoalScoringAnnouncements();
        assertEquals("Total announcements should be 0 when only goalkeeper has save announcements", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // SetUp: Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        List<FootballPlayer> emptyStartingEleven = new ArrayList<>();
        team.setStartingEleven(emptyStartingEleven);
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateGoalScoringAnnouncements();
        assertEquals("Total announcements should be 0 when no players in starting eleven", 0, result);
    }
}