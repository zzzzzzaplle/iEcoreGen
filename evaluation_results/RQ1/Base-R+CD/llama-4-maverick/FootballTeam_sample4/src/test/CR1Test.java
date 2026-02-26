import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR1Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_allStrikersWithGoals() {
        // Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 4 strikers who scored 2 goals each
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            firstEleven.add(striker);
        }
        
        // Create 7 other players with no goals
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE); // Not forwards
            player.setNumber(i);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Create announcements for each of the 4 Strikers (2 goals each)
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Player striker = firstEleven.get(i);
            // Each striker scores 2 goals
            for (int j = 0; j < 2; j++) {
                Announcement announcement = new Announcement();
                announcement.setPlayer(striker);
                announcement.setType(AnnouncementType.SCORE);
                announcement.setTime(new Date());
                announcements.add(announcement);
            }
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_mixedPlayersScoring() {
        // Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 2 strikers who scored 3 goals each
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            firstEleven.add(striker);
        }
        
        // Create 2 midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setNumber(i);
            firstEleven.add(midfielder);
        }
        
        // Create 7 other players with no goals
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Create announcements for Strikers (3 goals each)
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Player striker = firstEleven.get(i);
            // Each striker scores 3 goals
            for (int j = 0; j < 3; j++) {
                Announcement announcement = new Announcement();
                announcement.setPlayer(striker);
                announcement.setType(AnnouncementType.SCORE);
                announcement.setTime(new Date());
                announcements.add(announcement);
            }
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_firstElevenWithNoGoals() {
        // Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 3 strikers
        for (int i = 1; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            firstEleven.add(striker);
        }
        
        // Create 8 other players
        for (int i = 4; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // No announcements added (all players scored 0 goals)
        team.setAnnouncements(new ArrayList<>());
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_onlyGoalkeeperWithSaveAnnouncements() {
        // Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 1 goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        firstEleven.add(goalkeeper);
        
        // Create 10 other players (no strikers)
        for (int i = 2; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Create save announcements for the goalkeeper
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Announcement announcement = new Announcement();
            announcement.setPlayer(goalkeeper);
            announcement.setType(AnnouncementType.SAVE);
            announcement.setTime(new Date());
            announcements.add(announcement);
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_noPlayersInFirstEleven() {
        // Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        team.setFirstEleven(new ArrayList<>());
        team.setAnnouncements(new ArrayList<>());
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}