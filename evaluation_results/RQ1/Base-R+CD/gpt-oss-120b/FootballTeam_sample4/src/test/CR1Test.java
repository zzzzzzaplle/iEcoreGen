import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_allStrikersWithGoals() {
        // Create 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Add 4 strikers
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            firstEleven.add(striker);
            team.addPlayer(striker);
        }
        
        // Add 7 other players
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i);
            player.setAge(25);
            firstEleven.add(player);
            team.addPlayer(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Announce scores for each of the 4 Strikers (2 goals each)
        for (Player striker : firstEleven.subList(0, 4)) {
            for (int j = 0; j < 2; j++) {
                Announcement announcement = new Announcement();
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                announcement.setTime(new Date());
                team.addAnnouncement(announcement);
            }
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 4 players × 2 goals = 8
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_mixedPlayersScoring() {
        // Create 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Add 2 strikers
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            firstEleven.add(striker);
            team.addPlayer(striker);
        }
        
        // Add 2 midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setNumber(i);
            midfielder.setAge(25);
            firstEleven.add(midfielder);
            team.addPlayer(midfielder);
        }
        
        // Add 7 other players
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            player.setAge(25);
            firstEleven.add(player);
            team.addPlayer(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Announce scores for Strikers (3 goals each)
        for (Player striker : firstEleven.subList(0, 2)) {
            for (int j = 0; j < 3; j++) {
                Announcement announcement = new Announcement();
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                announcement.setTime(new Date());
                team.addAnnouncement(announcement);
            }
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: (2 players × 3 goals) = 6
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_firstElevenWithNoGoals() {
        // Create 11 players: All players (including 3 Strikers) scored 0 goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Add 3 strikers
        for (int i = 1; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            firstEleven.add(striker);
            team.addPlayer(striker);
        }
        
        // Add 8 other players
        for (int i = 4; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i);
            player.setAge(25);
            firstEleven.add(player);
            team.addPlayer(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // No announcements added - all players scored 0 goals
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_onlyGoalkeeperWithSaveAnnouncements() {
        // Create 11 players, including 1 Goalkeeper who saved 2 goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Add 1 goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        goalkeeper.setAge(30);
        firstEleven.add(goalkeeper);
        team.addPlayer(goalkeeper);
        
        // Add 10 other players
        for (int i = 2; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            player.setAge(25);
            firstEleven.add(player);
            team.addPlayer(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Add save announcements for the goalkeeper
        for (int j = 0; j < 2; j++) {
            Announcement announcement = new Announcement();
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            announcement.setTime(new Date());
            team.addAnnouncement(announcement);
        }
        
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 0 (only saves recorded, no score announcements)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_noPlayersInFirstEleven() {
        // Do not add any players to the first eleven
        team.setFirstEleven(new ArrayList<>());
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 0 (no players, no announcements)
        assertEquals(0, result);
    }
}