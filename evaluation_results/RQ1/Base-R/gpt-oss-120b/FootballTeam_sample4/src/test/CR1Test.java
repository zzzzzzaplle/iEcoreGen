import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Team team;
    
    @Before
    public void setUp() {
        team = new Team();
        team.setName("Turgutlu");
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // Create 4 Strikers who scored 2 goals each
        for (int i = 1; i <= 4; i++) {
            Striker striker = new Striker();
            striker.setName("Striker" + i);
            striker.setNumber(i);
            striker.setAge(25);
            // Simulate 2 goal announcements for each striker
            striker.scoreGoal();
            striker.scoreGoal();
            team.addPlayer(striker, true);
        }
        
        // Add 7 other players with no goals (non-strikers)
        for (int i = 5; i <= 11; i++) {
            Midfielder midfielder = new Midfielder();
            midfielder.setName("Midfielder" + i);
            midfielder.setNumber(i);
            midfielder.setAge(26);
            team.addPlayer(midfielder, true);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForStartingEleven();
        
        // Expected: 4 players × 2 goals = 8
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // Create 2 Strikers who scored 3 goals each
        for (int i = 1; i <= 2; i++) {
            Striker striker = new Striker();
            striker.setName("Striker" + i);
            striker.setNumber(i);
            striker.setAge(24);
            // Simulate 3 goal announcements for each striker
            striker.scoreGoal();
            striker.scoreGoal();
            striker.scoreGoal();
            team.addPlayer(striker, true);
        }
        
        // Add 2 Midfielders
        for (int i = 3; i <= 4; i++) {
            Midfielder midfielder = new Midfielder();
            midfielder.setName("Midfielder" + i);
            midfielder.setNumber(i);
            midfielder.setAge(27);
            team.addPlayer(midfielder, true);
        }
        
        // Add 7 other players with no goals
        for (int i = 5; i <= 11; i++) {
            Goalkeeper goalkeeper = new Goalkeeper();
            goalkeeper.setName("Goalkeeper" + i);
            goalkeeper.setNumber(i);
            goalkeeper.setAge(28);
            team.addPlayer(goalkeeper, true);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForStartingEleven();
        
        // Expected: 2 players × 3 goals = 6
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // Create 3 Strikers with 0 goals
        for (int i = 1; i <= 3; i++) {
            Striker striker = new Striker();
            striker.setName("Striker" + i);
            striker.setNumber(i);
            striker.setAge(23);
            team.addPlayer(striker, true);
        }
        
        // Add 8 other players with no goals
        for (int i = 4; i <= 11; i++) {
            Midfielder midfielder = new Midfielder();
            midfielder.setName("Midfielder" + i);
            midfielder.setNumber(i);
            midfielder.setAge(25);
            team.addPlayer(midfielder, true);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForStartingEleven();
        
        // Expected: 0 (no goals scored)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // Create 1 Goalkeeper who saved 2 goals
        Goalkeeper goalkeeper = new Goalkeeper();
        goalkeeper.setName("Goalkeeper1");
        goalkeeper.setNumber(1);
        goalkeeper.setAge(30);
        // Simulate 2 save announcements
        goalkeeper.saveGoal();
        goalkeeper.saveGoal();
        team.addPlayer(goalkeeper, true);
        
        // Add 10 other players with no announcements
        for (int i = 2; i <= 11; i++) {
            Midfielder midfielder = new Midfielder();
            midfielder.setName("Midfielder" + i);
            midfielder.setNumber(i);
            midfielder.setAge(26);
            team.addPlayer(midfielder, true);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForStartingEleven();
        
        // Expected: 0 (only saves recorded, no score announcements)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // Add some players to spare team only
        for (int i = 1; i <= 5; i++) {
            Striker striker = new Striker();
            striker.setName("Striker" + i);
            striker.setNumber(i);
            striker.setAge(24);
            striker.scoreGoal(); // These should not count as they're not in starting eleven
            team.addPlayer(striker, false);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForStartingEleven();
        
        // Expected: 0 (no players in starting eleven)
        assertEquals(0, result);
    }
}