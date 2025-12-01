import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        team.setName("Turgutlu");
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 4 forwards with 2 goals each
        for (int i = 1; i <= 4; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.FORWARD);
            striker.setGoalsScored(2);
            striker.setNumber(i);
            striker.setName("Striker" + i);
            startingEleven.add(striker);
        }
        
        // Create 7 non-forward players with 0 goals
        for (int i = 5; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.MIDFIELD); // Using midfielders as example
            player.setGoalsScored(0);
            player.setNumber(i);
            player.setName("Player" + i);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalAnnouncements();
        assertEquals("Total announcements should be 8 for 4 strikers with 2 goals each", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 2 forwards with 3 goals each
        for (int i = 1; i <= 2; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.FORWARD);
            striker.setGoalsScored(3);
            striker.setNumber(i);
            striker.setName("Striker" + i);
            startingEleven.add(striker);
        }
        
        // Create 2 midfielders (should not count for goal announcements)
        for (int i = 3; i <= 4; i++) {
            FootballPlayer midfielder = new FootballPlayer();
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setGoalsScored(0); // Midfielders don't score in this test case
            midfielder.setNumber(i);
            midfielder.setName("Midfielder" + i);
            startingEleven.add(midfielder);
        }
        
        // Create 7 other players with no goals
        for (int i = 5; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.DEFENSE); // Using defenders as example
            player.setGoalsScored(0);
            player.setNumber(i);
            player.setName("Player" + i);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalAnnouncements();
        assertEquals("Total announcements should be 6 for 2 strikers with 3 goals each", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 3 forwards with 0 goals
        for (int i = 1; i <= 3; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.FORWARD);
            striker.setGoalsScored(0);
            striker.setNumber(i);
            striker.setName("Striker" + i);
            startingEleven.add(striker);
        }
        
        // Create 8 other players with 0 goals
        for (int i = 4; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.MIDFIELD); // Using midfielders as example
            player.setGoalsScored(0);
            player.setNumber(i);
            player.setName("Player" + i);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalAnnouncements();
        assertEquals("Total announcements should be 0 when no players have scored goals", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 1 goalkeeper with 2 saves (but saves don't count for goal announcements)
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(2); // Saves, not goals scored
        goalkeeper.setGoalsScored(0); // No goals scored
        goalkeeper.setNumber(1);
        goalkeeper.setName("Goalkeeper");
        startingEleven.add(goalkeeper);
        
        // Create 10 other players with no goals
        for (int i = 2; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.DEFENSE); // Using defenders as example
            player.setGoalsScored(0);
            player.setNumber(i);
            player.setName("Player" + i);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalAnnouncements();
        assertEquals("Total announcements should be 0 when only goalkeeper has saves but no forwards have scored", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // SetUp: Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        team.setStartingEleven(new ArrayList<FootballPlayer>()); // Empty starting eleven
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalAnnouncements();
        assertEquals("Total announcements should be 0 when starting eleven is empty", 0, result);
    }
}