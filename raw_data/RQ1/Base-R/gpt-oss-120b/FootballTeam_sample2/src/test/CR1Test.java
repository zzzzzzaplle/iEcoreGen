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
        // SetUp: Create 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 4 forwards with 2 goal announcements each
        for (int i = 1; i <= 4; i++) {
            FootballPlayer forward = new FootballPlayer();
            forward.setPosition(Position.FORWARD);
            forward.setNumber(i);
            forward.setName("Forward " + i);
            forward.setGoalAnnouncements(2);
            startingEleven.add(forward);
        }
        
        // Create 7 other players (non-forwards) with no goals
        for (int i = 5; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.MIDFIELDER);
            player.setNumber(i);
            player.setName("Player " + i);
            player.setGoalAnnouncements(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.totalGoalScoringAnnouncementsForStartingEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // SetUp: Create 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 2 forwards with 3 goal announcements each
        FootballPlayer forward1 = new FootballPlayer();
        forward1.setPosition(Position.FORWARD);
        forward1.setNumber(1);
        forward1.setName("Forward 1");
        forward1.setGoalAnnouncements(3);
        startingEleven.add(forward1);
        
        FootballPlayer forward2 = new FootballPlayer();
        forward2.setPosition(Position.FORWARD);
        forward2.setNumber(2);
        forward2.setName("Forward 2");
        forward2.setGoalAnnouncements(3);
        startingEleven.add(forward2);
        
        // Create 2 midfielders
        FootballPlayer midfielder1 = new FootballPlayer();
        midfielder1.setPosition(Position.MIDFIELDER);
        midfielder1.setNumber(3);
        midfielder1.setName("Midfielder 1");
        midfielder1.setGoalAnnouncements(0);
        startingEleven.add(midfielder1);
        
        FootballPlayer midfielder2 = new FootballPlayer();
        midfielder2.setPosition(Position.MIDFIELDER);
        midfielder2.setNumber(4);
        midfielder2.setName("Midfielder 2");
        midfielder2.setGoalAnnouncements(0);
        startingEleven.add(midfielder2);
        
        // Create 7 other players with no goals
        for (int i = 5; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            player.setName("Player " + i);
            player.setGoalAnnouncements(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.totalGoalScoringAnnouncementsForStartingEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // SetUp: Create 11 players: All players (including 3 Strikers) scored 0 goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 3 forwards with 0 goal announcements
        for (int i = 1; i <= 3; i++) {
            FootballPlayer forward = new FootballPlayer();
            forward.setPosition(Position.FORWARD);
            forward.setNumber(i);
            forward.setName("Forward " + i);
            forward.setGoalAnnouncements(0);
            startingEleven.add(forward);
        }
        
        // Create 8 other players with no goals
        for (int i = 4; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.MIDFIELDER);
            player.setNumber(i);
            player.setName("Player " + i);
            player.setGoalAnnouncements(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Expected Output: Total announcements = 0
        int result = team.totalGoalScoringAnnouncementsForStartingEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // SetUp: Create 11 players, including 1 Goalkeeper who saved 2 goals
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        // Create 1 goalkeeper with save announcements
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setSaveAnnouncements(2);
        goalkeeper.setGoalAnnouncements(0);
        startingEleven.add(goalkeeper);
        
        // Create 10 other players with no goals
        for (int i = 2; i <= 11; i++) {
            FootballPlayer player = new FootballPlayer();
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            player.setName("Player " + i);
            player.setGoalAnnouncements(0);
            startingEleven.add(player);
        }
        
        team.setStartingEleven(startingEleven);
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.totalGoalScoringAnnouncementsForStartingEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // SetUp: Do not add any players to the first eleven
        team.setStartingEleven(new ArrayList<>());
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.totalGoalScoringAnnouncementsForStartingEleven();
        assertEquals(0, result);
    }
}