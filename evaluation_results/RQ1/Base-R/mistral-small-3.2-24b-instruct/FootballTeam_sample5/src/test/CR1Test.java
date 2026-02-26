import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // Create a football team named "Turgutlu"
        FootballTeam turgutlu = new FootballTeam();
        
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        for (int i = 0; i < 4; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.FORWARD);
            striker.setGoalScoringAnnouncements(2);
            turgutlu.addPlayer(striker);
        }
        
        for (int i = 0; i < 7; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.MIDFIELD);
            player.setGoalScoringAnnouncements(0);
            turgutlu.addPlayer(player);
        }
        
        // Calculate total goal scoring announcements
        int result = turgutlu.calculateGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // Create a football team named "Turgutlu"
        FootballTeam turgutlu = new FootballTeam();
        
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        for (int i = 0; i < 2; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.FORWARD);
            striker.setGoalScoringAnnouncements(3);
            turgutlu.addPlayer(striker);
        }
        
        for (int i = 0; i < 2; i++) {
            FootballPlayer midfielder = new FootballPlayer();
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setGoalScoringAnnouncements(0);
            turgutlu.addPlayer(midfielder);
        }
        
        for (int i = 0; i < 7; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.DEFENSE);
            player.setGoalScoringAnnouncements(0);
            turgutlu.addPlayer(player);
        }
        
        // Calculate total goal scoring announcements
        int result = turgutlu.calculateGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // Create a football team named "Turgutlu"
        FootballTeam turgutlu = new FootballTeam();
        
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        for (int i = 0; i < 3; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.FORWARD);
            striker.setGoalScoringAnnouncements(0);
            turgutlu.addPlayer(striker);
        }
        
        for (int i = 0; i < 8; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.MIDFIELD);
            player.setGoalScoringAnnouncements(0);
            turgutlu.addPlayer(player);
        }
        
        // Calculate total goal scoring announcements
        int result = turgutlu.calculateGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // Create a football team named "Turgutlu"
        FootballTeam turgutlu = new FootballTeam();
        
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalSavingAnnouncements(2);
        goalkeeper.setGoalScoringAnnouncements(0);
        turgutlu.addPlayer(goalkeeper);
        
        for (int i = 0; i < 10; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.DEFENSE);
            player.setGoalScoringAnnouncements(0);
            turgutlu.addPlayer(player);
        }
        
        // Calculate total goal scoring announcements
        int result = turgutlu.calculateGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // Create a football team named "Turgutlu"
        FootballTeam turgutlu = new FootballTeam();
        
        // Do not add any players to the first eleven
        // Calculate total goal scoring announcements
        int result = turgutlu.calculateGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        assertEquals(0, result);
    }
}