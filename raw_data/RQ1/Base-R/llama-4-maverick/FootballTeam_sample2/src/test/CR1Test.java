import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Create a football team named "Turgutlu" before each test
        team = new FootballTeam("Turgutlu");
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        // Announce scores for each of the 4 Strikers
        
        // Create 4 strikers with 2 score announcements each
        for (int i = 1; i <= 4; i++) {
            FootballPlayer striker = new FootballPlayer("Turgutlu", "Striker" + i, 25, i, Position.FORWARD, Duty.STRIKER);
            striker.setScoreAnnouncements(2);
            team.getStartingEleven().add(striker);
        }
        
        // Create 7 other players (non-strikers) with no goals
        for (int i = 5; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer("Turgutlu", "Player" + i, 25, i, Position.MIDFIELD, Duty.MIDFIELDER);
            player.setScoreAnnouncements(0);
            team.getStartingEleven().add(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalScoreAnnouncements();
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        // Announce scores for Strikers
        
        // Create 2 strikers with 3 score announcements each
        FootballPlayer striker1 = new FootballPlayer("Turgutlu", "Striker1", 25, 1, Position.FORWARD, Duty.STRIKER);
        striker1.setScoreAnnouncements(3);
        team.getStartingEleven().add(striker1);
        
        FootballPlayer striker2 = new FootballPlayer("Turgutlu", "Striker2", 26, 2, Position.FORWARD, Duty.STRIKER);
        striker2.setScoreAnnouncements(3);
        team.getStartingEleven().add(striker2);
        
        // Create 2 midfielders
        FootballPlayer midfielder1 = new FootballPlayer("Turgutlu", "Midfielder1", 27, 3, Position.MIDFIELD, Duty.MIDFIELDER);
        midfielder1.setScoreAnnouncements(0);
        team.getStartingEleven().add(midfielder1);
        
        FootballPlayer midfielder2 = new FootballPlayer("Turgutlu", "Midfielder2", 28, 4, Position.MIDFIELD, Duty.MIDFIELDER);
        midfielder2.setScoreAnnouncements(0);
        team.getStartingEleven().add(midfielder2);
        
        // Create 7 other players with no goals
        for (int i = 5; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer("Turgutlu", "Player" + i, 25 + i, i, Position.DEFENSE, Duty.GOALKEEPER);
            player.setScoreAnnouncements(0);
            team.getStartingEleven().add(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalScoreAnnouncements();
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        
        // Create 3 strikers with 0 score announcements
        for (int i = 1; i <= 3; i++) {
            FootballPlayer striker = new FootballPlayer("Turgutlu", "Striker" + i, 25, i, Position.FORWARD, Duty.STRIKER);
            striker.setScoreAnnouncements(0);
            team.getStartingEleven().add(striker);
        }
        
        // Create 8 other players with 0 score announcements
        for (int i = 4; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer("Turgutlu", "Player" + i, 25 + i, i, Position.MIDFIELD, Duty.MIDFIELDER);
            player.setScoreAnnouncements(0);
            team.getStartingEleven().add(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalScoreAnnouncements();
        
        // Expected Output: Total announcements = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        
        // Create 1 goalkeeper with 2 goal saving announcements
        FootballPlayer goalkeeper = new FootballPlayer("Turgutlu", "Goalkeeper", 30, 1, Position.DEFENSE, Duty.GOALKEEPER);
        goalkeeper.setGoalSavingAnnouncements(2);
        goalkeeper.setScoreAnnouncements(0);
        team.getStartingEleven().add(goalkeeper);
        
        // Create 10 other players (non-strikers) with no goals
        for (int i = 2; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer("Turgutlu", "Player" + i, 25 + i, i, Position.MIDFIELD, Duty.MIDFIELDER);
            player.setScoreAnnouncements(0);
            team.getStartingEleven().add(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalScoreAnnouncements();
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // SetUp: Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        
        // Starting eleven is already empty from setUp()
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalScoreAnnouncements();
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        assertEquals(0, result);
    }
}