import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Reset team before each test
        team = new FootballTeam("Turgutlu");
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        // Announce scores for each of the 4 Strikers
        
        // Create 4 forwards with 2 goals each
        for (int i = 1; i <= 4; i++) {
            FootballPlayer striker = new FootballPlayer("Turgutlu", "Striker" + i, 25, i, Position.FORWARD);
            striker.setScoreAnnouncements(2); // Set 2 goals for each striker
            team.addPlayer(striker);
            team.setPlayerAsStarter(striker);
        }
        
        // Create 7 non-forward players with no goals
        for (int i = 5; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer("Turgutlu", "Player" + i, 25, i, Position.MIDFIELD);
            team.addPlayer(player);
            team.setPlayerAsStarter(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalScoreAnnouncementsForStartingEleven();
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        // Announce scores for Strikers
        
        // Create 2 forwards with 3 goals each
        for (int i = 1; i <= 2; i++) {
            FootballPlayer striker = new FootballPlayer("Turgutlu", "Striker" + i, 25, i, Position.FORWARD);
            striker.setScoreAnnouncements(3); // Set 3 goals for each striker
            team.addPlayer(striker);
            team.setPlayerAsStarter(striker);
        }
        
        // Create 2 midfielders
        for (int i = 3; i <= 4; i++) {
            FootballPlayer midfielder = new FootballPlayer("Turgutlu", "Midfielder" + i, 25, i, Position.MIDFIELD);
            team.addPlayer(midfielder);
            team.setPlayerAsStarter(midfielder);
        }
        
        // Create 7 additional players (non-forwards)
        for (int i = 5; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer("Turgutlu", "Player" + i, 25, i, Position.DEFENSE);
            team.addPlayer(player);
            team.setPlayerAsStarter(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalScoreAnnouncementsForStartingEleven();
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        
        // Create 3 forwards with 0 goals
        for (int i = 1; i <= 3; i++) {
            FootballPlayer striker = new FootballPlayer("Turgutlu", "Striker" + i, 25, i, Position.FORWARD);
            striker.setScoreAnnouncements(0); // No goals
            team.addPlayer(striker);
            team.setPlayerAsStarter(striker);
        }
        
        // Create 8 additional players (non-forwards)
        for (int i = 4; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer("Turgutlu", "Player" + i, 25, i, Position.MIDFIELD);
            team.addPlayer(player);
            team.setPlayerAsStarter(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalScoreAnnouncementsForStartingEleven();
        
        // Expected Output: Total announcements = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        
        // Create 1 goalkeeper with 2 saves
        FootballPlayer goalkeeper = new FootballPlayer("Turgutlu", "Goalkeeper", 28, 1, Position.GOALKEEPER);
        goalkeeper.setSaveAnnouncements(2); // 2 saves
        team.addPlayer(goalkeeper);
        team.setPlayerAsStarter(goalkeeper);
        
        // Create 10 additional players (non-forwards)
        for (int i = 2; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer("Turgutlu", "Player" + i, 25, i, Position.DEFENSE);
            team.addPlayer(player);
            team.setPlayerAsStarter(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalScoreAnnouncementsForStartingEleven();
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // SetUp: Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        
        // Add some players to the team but NOT to the starting eleven
        FootballPlayer striker = new FootballPlayer("Turgutlu", "Striker", 25, 1, Position.FORWARD);
        striker.setScoreAnnouncements(5); // Player has goals but is not in starting eleven
        team.addPlayer(striker);
        // Note: We don't call setPlayerAsStarter() - player remains only in spare team
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalScoreAnnouncementsForStartingEleven();
        
        // Expected Output: Total announcements = 0 (no players in first eleven, no announcements)
        assertEquals(0, result);
    }
}