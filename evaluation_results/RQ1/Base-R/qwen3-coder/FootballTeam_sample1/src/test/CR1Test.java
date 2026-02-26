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
        team.setTeam("Turgutlu");
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Add 4 forwards with 2 goals each
        for (int i = 0; i < 4; i++) {
            FootballPlayer forward = new FootballPlayer();
            forward.setPosition(Position.FORWARD);
            forward.setGoalsScored(2);
            startingEleven.add(forward);
        }
        
        // Add 7 players with other positions and no goals
        for (int i = 0; i < 7; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.MIDFIELD); // Using midfielders for the remaining players
            player.setGoalsScored(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncements();
        assertEquals("Total announcements should be 8 for 4 forwards with 2 goals each", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Add 2 forwards with 3 goals each
        for (int i = 0; i < 2; i++) {
            FootballPlayer forward = new FootballPlayer();
            forward.setPosition(Position.FORWARD);
            forward.setGoalsScored(3);
            startingEleven.add(forward);
        }
        
        // Add 2 midfielders (goals should be ignored)
        for (int i = 0; i < 2; i++) {
            FootballPlayer midfielder = new FootballPlayer();
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setGoalsScored(5); // Goals should be ignored since they're not forwards
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
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncements();
        assertEquals("Total announcements should be 6 for 2 forwards with 3 goals each", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Add 3 forwards with 0 goals
        for (int i = 0; i < 3; i++) {
            FootballPlayer forward = new FootballPlayer();
            forward.setPosition(Position.FORWARD);
            forward.setGoalsScored(0);
            startingEleven.add(forward);
        }
        
        // Add 8 players with other positions and 0 goals
        for (int i = 0; i < 8; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.MIDFIELD);
            player.setGoalsScored(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncements();
        assertEquals("Total announcements should be 0 when all players have 0 goals", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Add 1 goalkeeper with 2 saves (should be ignored for goal scoring announcements)
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(2);
        goalkeeper.setGoalsScored(0);
        startingEleven.add(goalkeeper);
        
        // Add 10 players with other positions and no goals
        for (int i = 0; i < 10; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.DEFENSE);
            player.setGoalsScored(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncements();
        assertEquals("Total announcements should be 0 when only goalkeeper has saves but no forwards have goals", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // SetUp: Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        team.setStartingEleven(new ArrayList<FootballPlayer>());
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncements();
        assertEquals("Total announcements should be 0 when no players in starting eleven", 0, result);
    }
}