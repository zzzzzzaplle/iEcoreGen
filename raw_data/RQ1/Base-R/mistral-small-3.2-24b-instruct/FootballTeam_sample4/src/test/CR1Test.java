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
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // Setup: Create 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 4 forwards with 2 goal announcements each
        for (int i = 0; i < 4; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.FORWARD);
            striker.setGoalAnnouncements(2);
            startingEleven.add(striker);
        }
        
        // Create 7 other players (non-forwards) with no goals
        for (int i = 0; i < 7; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(i % 2 == 0 ? Position.MIDFIELD : Position.DEFENSE);
            player.setGoalAnnouncements(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Execute: Calculate total goal scoring announcements
        int result = team.calculateStartingElevenGoalAnnouncements();
        
        // Verify: Total announcements = 4 players × 2 goals = 8
        assertEquals("Total announcements should be 8 for 4 strikers with 2 goals each", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // Setup: Create 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 2 forwards with 3 goal announcements each
        for (int i = 0; i < 2; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.FORWARD);
            striker.setGoalAnnouncements(3);
            startingEleven.add(striker);
        }
        
        // Create 2 midfielders
        for (int i = 0; i < 2; i++) {
            FootballPlayer midfielder = new FootballPlayer();
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setGoalAnnouncements(0);
            startingEleven.add(midfielder);
        }
        
        // Create 7 other players with no goals
        for (int i = 0; i < 7; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.DEFENSE);
            player.setGoalAnnouncements(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Execute: Calculate total goal scoring announcements
        int result = team.calculateStartingElevenGoalAnnouncements();
        
        // Verify: Total announcements = (2 players × 3 goals) = 6
        assertEquals("Total announcements should be 6 for 2 strikers with 3 goals each", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // Setup: Create 11 players: All players (including 3 Strikers) scored 0 goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 3 forwards with 0 goal announcements
        for (int i = 0; i < 3; i++) {
            FootballPlayer striker = new FootballPlayer();
            striker.setPosition(Position.FORWARD);
            striker.setGoalAnnouncements(0);
            startingEleven.add(striker);
        }
        
        // Create 8 other players with no goals
        for (int i = 0; i < 8; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(i % 2 == 0 ? Position.MIDFIELD : Position.DEFENSE);
            player.setGoalAnnouncements(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Execute: Calculate total goal scoring announcements
        int result = team.calculateStartingElevenGoalAnnouncements();
        
        // Verify: Total announcements = 0
        assertEquals("Total announcements should be 0 when no players have scored goals", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // Setup: Create 11 players, including 1 Goalkeeper who saved 2 goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 1 goalkeeper with 2 goal saving announcements
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setPosition(Position.DEFENSE);
        goalkeeper.setGoalSavingAnnouncements(2);
        goalkeeper.setGoalAnnouncements(0);
        startingEleven.add(goalkeeper);
        
        // Create 10 other players (non-forwards) with no goals
        for (int i = 0; i < 10; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.MIDFIELD);
            player.setGoalAnnouncements(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Execute: Calculate total goal scoring announcements
        int result = team.calculateStartingElevenGoalAnnouncements();
        
        // Verify: Total announcements = 0 (only saves recorded, no score announcements)
        assertEquals("Total announcements should be 0 when only goalkeeper has save announcements", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // Setup: Do not add any players to the first eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        team.setStartingEleven(startingEleven);
        
        // Execute: Calculate total goal scoring announcements
        int result = team.calculateStartingElevenGoalAnnouncements();
        
        // Verify: Total announcements = 0 (no players, no announcements)
        assertEquals("Total announcements should be 0 when no players in starting eleven", 0, result);
    }
}