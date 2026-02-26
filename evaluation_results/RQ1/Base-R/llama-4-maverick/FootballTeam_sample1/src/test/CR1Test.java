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
        team.setStartingEleven(new ArrayList<FootballPlayer>());
        team.setSpareTeam(new ArrayList<FootballPlayer>());
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // SetUp: Create a football team named "Turgutlu"
        team.setStartingEleven(new ArrayList<FootballPlayer>());
        
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        for (int i = 1; i <= 4; i++) {
            FootballPlayer striker = new FootballPlayer("Turgutlu", "Striker" + i, 25, i, Position.FORWARD, Duty.STRIKER);
            striker.setGoalsScored(2);
            team.getStartingEleven().add(striker);
        }
        
        // Add 7 players with no goals (mix of positions)
        for (int i = 5; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer("Turgutlu", "Player" + i, 25, i, 
                i % 2 == 0 ? Position.MIDFIELD : Position.DEFENSE, 
                i % 2 == 0 ? Duty.MIDFIELDER : Duty.GOALKEEPER);
            player.setGoalsScored(0);
            team.getStartingEleven().add(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // SetUp: Create a football team named "Turgutlu"
        team.setStartingEleven(new ArrayList<FootballPlayer>());
        
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        for (int i = 1; i <= 2; i++) {
            FootballPlayer striker = new FootballPlayer("Turgutlu", "Striker" + i, 25, i, Position.FORWARD, Duty.STRIKER);
            striker.setGoalsScored(3);
            team.getStartingEleven().add(striker);
        }
        
        // Add 2 Midfielders
        for (int i = 3; i <= 4; i++) {
            FootballPlayer midfielder = new FootballPlayer("Turgutlu", "Midfielder" + i, 25, i, Position.MIDFIELD, Duty.MIDFIELDER);
            midfielder.setGoalsScored(0);
            team.getStartingEleven().add(midfielder);
        }
        
        // Add 7 players with no goals
        for (int i = 5; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer("Turgutlu", "Player" + i, 25, i, Position.DEFENSE, Duty.GOALKEEPER);
            player.setGoalsScored(0);
            team.getStartingEleven().add(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // SetUp: Create a football team named "Turgutlu"
        team.setStartingEleven(new ArrayList<FootballPlayer>());
        
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        for (int i = 1; i <= 3; i++) {
            FootballPlayer striker = new FootballPlayer("Turgutlu", "Striker" + i, 25, i, Position.FORWARD, Duty.STRIKER);
            striker.setGoalsScored(0);
            team.getStartingEleven().add(striker);
        }
        
        // Add remaining 8 players with no goals
        for (int i = 4; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer("Turgutlu", "Player" + i, 25, i, 
                i % 2 == 0 ? Position.MIDFIELD : Position.DEFENSE, 
                i % 2 == 0 ? Duty.MIDFIELDER : Duty.GOALKEEPER);
            player.setGoalsScored(0);
            team.getStartingEleven().add(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // SetUp: Create a football team named "Turgutlu"
        team.setStartingEleven(new ArrayList<FootballPlayer>());
        
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        FootballPlayer goalkeeper = new FootballPlayer("Turgutlu", "Goalkeeper", 28, 1, Position.DEFENSE, Duty.GOALKEEPER);
        goalkeeper.setGoalsSaved(2);
        team.getStartingEleven().add(goalkeeper);
        
        // Add remaining 10 players with no goals
        for (int i = 2; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer("Turgutlu", "Player" + i, 25, i, 
                i % 2 == 0 ? Position.FORWARD : Position.MIDFIELD, 
                i % 2 == 0 ? Duty.STRIKER : Duty.MIDFIELDER);
            player.setGoalsScored(0);
            team.getStartingEleven().add(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // SetUp: Create a football team named "Turgutlu"
        // Do not add any players to the first eleven (already empty from setUp)
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        assertEquals(0, result);
    }
}