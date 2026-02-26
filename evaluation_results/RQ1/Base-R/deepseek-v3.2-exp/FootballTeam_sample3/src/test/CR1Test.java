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
        // Announce scores for each of the 4 Strikers
        
        // Create 4 forwards with 2 goals each
        for (int i = 0; i < 4; i++) {
            FootballPlayer forward = new FootballPlayer();
            forward.setTeam("Turgutlu");
            forward.setName("Forward " + (i + 1));
            forward.setAge(25);
            forward.setNumber(i + 1);
            forward.setPosition(Position.FORWARD);
            forward.setGoalsScored(2);
            forward.setInStartingEleven(true);
            team.addPlayer(forward);
        }
        
        // Create 7 other players (non-forwards) with no goals
        for (int i = 4; i < 11; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setTeam("Turgutlu");
            player.setName("Player " + (i + 1));
            player.setAge(26);
            player.setNumber(i + 1);
            player.setPosition(Position.MIDFIELD);
            player.setGoalsScored(0);
            player.setInStartingEleven(true);
            team.addPlayer(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        assertEquals("Total announcements should be 8 for 4 forwards with 2 goals each", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        // Announce scores for Strikers
        
        // Create 2 forwards with 3 goals each
        for (int i = 0; i < 2; i++) {
            FootballPlayer forward = new FootballPlayer();
            forward.setTeam("Turgutlu");
            forward.setName("Forward " + (i + 1));
            forward.setAge(24);
            forward.setNumber(i + 1);
            forward.setPosition(Position.FORWARD);
            forward.setGoalsScored(3);
            forward.setInStartingEleven(true);
            team.addPlayer(forward);
        }
        
        // Create 2 midfielders with no goals
        for (int i = 2; i < 4; i++) {
            FootballPlayer midfielder = new FootballPlayer();
            midfielder.setTeam("Turgutlu");
            midfielder.setName("Midfielder " + (i + 1));
            midfielder.setAge(27);
            midfielder.setNumber(i + 1);
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setGoalsScored(0);
            midfielder.setInStartingEleven(true);
            team.addPlayer(midfielder);
        }
        
        // Create 7 other players with no goals
        for (int i = 4; i < 11; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setTeam("Turgutlu");
            player.setName("Player " + (i + 1));
            player.setAge(28);
            player.setNumber(i + 1);
            player.setPosition(Position.DEFENSE);
            player.setGoalsScored(0);
            player.setInStartingEleven(true);
            team.addPlayer(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        assertEquals("Total announcements should be 6 for 2 forwards with 3 goals each", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        
        // Create 3 forwards with 0 goals
        for (int i = 0; i < 3; i++) {
            FootballPlayer forward = new FootballPlayer();
            forward.setTeam("Turgutlu");
            forward.setName("Forward " + (i + 1));
            forward.setAge(23);
            forward.setNumber(i + 1);
            forward.setPosition(Position.FORWARD);
            forward.setGoalsScored(0);
            forward.setInStartingEleven(true);
            team.addPlayer(forward);
        }
        
        // Create 8 other players with 0 goals
        for (int i = 3; i < 11; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setTeam("Turgutlu");
            player.setName("Player " + (i + 1));
            player.setAge(29);
            player.setNumber(i + 1);
            player.setPosition(Position.MIDFIELD);
            player.setGoalsScored(0);
            player.setInStartingEleven(true);
            team.addPlayer(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0
        assertEquals("Total announcements should be 0 when all players have 0 goals", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        
        // Create 1 goalkeeper with 2 saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setTeam("Turgutlu");
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(2);
        goalkeeper.setInStartingEleven(true);
        team.addPlayer(goalkeeper);
        
        // Create 10 other players (non-forwards) with no goals
        for (int i = 1; i < 11; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setTeam("Turgutlu");
            player.setName("Player " + (i + 1));
            player.setAge(25);
            player.setNumber(i + 1);
            player.setPosition(Position.DEFENSE);
            player.setGoalsScored(0);
            player.setInStartingEleven(true);
            team.addPlayer(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        assertEquals("Total announcements should be 0 when only goalkeeper has saves", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // SetUp: Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        
        // Add players but mark them as not in starting eleven
        for (int i = 0; i < 5; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setTeam("Turgutlu");
            player.setName("Player " + (i + 1));
            player.setAge(26);
            player.setNumber(i + 1);
            player.setPosition(Position.FORWARD);
            player.setGoalsScored(5);
            player.setInStartingEleven(false); // Not in starting eleven
            team.addPlayer(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        assertEquals("Total announcements should be 0 when no players are in starting eleven", 0, result);
    }
}