import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Team team;
    
    @Before
    public void setUp() {
        // Reset team before each test
        team = new Team("Turgutlu");
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // SetUp: Create a football team named "Turgutlu"
        team = new Team("Turgutlu");
        
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        // Create 4 forwards who will score 2 goals each
        for (int i = 1; i <= 4; i++) {
            Forward forward = new Forward("Turgutlu", "Striker" + i, 25, i);
            // Simulate scoring 2 goals for each forward
            forward.scoreGoal();
            forward.scoreGoal();
            team.addPlayer(forward);
            team.addToStartingEleven(forward);
        }
        
        // Add 7 other players (non-forwards) with no goals
        for (int i = 5; i <= 11; i++) {
            Midfielder midfielder = new Midfielder("Turgutlu", "Player" + i, 25, i);
            team.addPlayer(midfielder);
            team.addToStartingEleven(midfielder);
        }
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.countScoreAnnouncementsStartingEleven();
        assertEquals("Total announcements should be 8 for 4 strikers with 2 goals each", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // SetUp: Create a football team named "Turgutlu"
        team = new Team("Turgutlu");
        
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        // Create 2 forwards who will score 3 goals each
        for (int i = 1; i <= 2; i++) {
            Forward forward = new Forward("Turgutlu", "Striker" + i, 25, i);
            // Simulate scoring 3 goals for each forward
            forward.scoreGoal();
            forward.scoreGoal();
            forward.scoreGoal();
            team.addPlayer(forward);
            team.addToStartingEleven(forward);
        }
        
        // Add 2 midfielders
        for (int i = 3; i <= 4; i++) {
            Midfielder midfielder = new Midfielder("Turgutlu", "Midfielder" + i, 25, i);
            team.addPlayer(midfielder);
            team.addToStartingEleven(midfielder);
        }
        
        // Add 7 other players with no goals
        for (int i = 5; i <= 11; i++) {
            Midfielder player = new Midfielder("Turgutlu", "Player" + i, 25, i);
            team.addPlayer(player);
            team.addToStartingEleven(player);
        }
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.countScoreAnnouncementsStartingEleven();
        assertEquals("Total announcements should be 6 for 2 strikers with 3 goals each", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // SetUp: Create a football team named "Turgutlu"
        team = new Team("Turgutlu");
        
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        // Add 3 forwards with no goals
        for (int i = 1; i <= 3; i++) {
            Forward forward = new Forward("Turgutlu", "Striker" + i, 25, i);
            team.addPlayer(forward);
            team.addToStartingEleven(forward);
            // No goals scored - don't call scoreGoal()
        }
        
        // Add 8 other players with no goals
        for (int i = 4; i <= 11; i++) {
            Midfielder player = new Midfielder("Turgutlu", "Player" + i, 25, i);
            team.addPlayer(player);
            team.addToStartingEleven(player);
        }
        
        // Expected Output: Total announcements = 0
        int result = team.countScoreAnnouncementsStartingEleven();
        assertEquals("Total announcements should be 0 when no goals are scored", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // SetUp: Create a football team named "Turgutlu"
        team = new Team("Turgutlu");
        
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // Add 1 goalkeeper with 2 saves
        Goalkeeper goalkeeper = new Goalkeeper("Turgutlu", "Goalkeeper", 28, 1);
        goalkeeper.saveGoal();
        goalkeeper.saveGoal();
        team.addPlayer(goalkeeper);
        team.addToStartingEleven(goalkeeper);
        
        // Add 10 other players (no strikers)
        for (int i = 2; i <= 11; i++) {
            Midfielder player = new Midfielder("Turgutlu", "Player" + i, 25, i);
            team.addPlayer(player);
            team.addToStartingEleven(player);
        }
        
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.countScoreAnnouncementsStartingEleven();
        assertEquals("Total announcements should be 0 when only goalkeeper has save announcements", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // SetUp: Create a football team named "Turgutlu"
        team = new Team("Turgutlu");
        
        // Do not add any players to the first eleven
        // Add some players to the team but not to starting eleven
        Forward forward = new Forward("Turgutlu", "Striker", 25, 1);
        forward.scoreGoal(); // This goal should not count since player is not in starting eleven
        team.addPlayer(forward);
        // Note: addToStartingEleven() is NOT called for any player
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.countScoreAnnouncementsStartingEleven();
        assertEquals("Total announcements should be 0 when no players in starting eleven", 0, result);
    }
}