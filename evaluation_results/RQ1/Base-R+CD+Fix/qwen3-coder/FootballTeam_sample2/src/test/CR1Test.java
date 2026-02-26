import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private FootballTeam footballTeam;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        footballTeam = new FootballTeam();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // Create a football team named "Turgutlu"
        footballTeam.setFirstEleven(new ArrayList<Player>());
        footballTeam.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<Player>();
        
        // Create 4 forwards (strikers)
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            firstEleven.add(striker);
        }
        
        // Create 7 other players (non-strikers)
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i);
            player.setAge(26);
            firstEleven.add(player);
        }
        
        footballTeam.setFirstEleven(firstEleven);
        
        // Announce scores for each of the 4 Strikers (2 goals each)
        List<Announcement> announcements = new ArrayList<Announcement>();
        
        for (int i = 0; i < 4; i++) {
            Player striker = firstEleven.get(i);
            // Create 2 score announcements for each striker
            for (int j = 0; j < 2; j++) {
                Announcement announcement = new Announcement();
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                try {
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                } catch (Exception e) {
                    fail("Date parsing failed");
                }
                announcements.add(announcement);
            }
        }
        
        footballTeam.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = footballTeam.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should count 8 goal-scoring announcements from 4 strikers with 2 goals each", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // Create a football team named "Turgutlu"
        footballTeam.setFirstEleven(new ArrayList<Player>());
        footballTeam.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<Player>();
        
        // Create 2 forwards (strikers)
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            firstEleven.add(striker);
        }
        
        // Create 2 midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setNumber(i);
            midfielder.setAge(26);
            firstEleven.add(midfielder);
        }
        
        // Create 7 other players
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            player.setAge(27);
            firstEleven.add(player);
        }
        
        footballTeam.setFirstEleven(firstEleven);
        
        // Announce scores for Strikers
        List<Announcement> announcements = new ArrayList<Announcement>();
        
        for (int i = 0; i < 2; i++) {
            Player striker = firstEleven.get(i);
            // Create 3 score announcements for each striker
            for (int j = 0; j < 3; j++) {
                Announcement announcement = new Announcement();
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                try {
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                } catch (Exception e) {
                    fail("Date parsing failed");
                }
                announcements.add(announcement);
            }
        }
        
        footballTeam.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = footballTeam.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should count 6 goal-scoring announcements from 2 strikers with 3 goals each", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // Create a football team named "Turgutlu"
        footballTeam.setFirstEleven(new ArrayList<Player>());
        footballTeam.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        List<Player> firstEleven = new ArrayList<Player>();
        
        // Create 3 forwards (strikers)
        for (int i = 1; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            firstEleven.add(striker);
        }
        
        // Create 8 other players
        for (int i = 4; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i);
            player.setAge(26);
            firstEleven.add(player);
        }
        
        footballTeam.setFirstEleven(firstEleven);
        
        // No announcements added (all players scored 0 goals)
        footballTeam.setAnnouncements(new ArrayList<Announcement>());
        
        // Expected Output: Total announcements = 0
        int result = footballTeam.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should return 0 when no goals were scored", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // Create a football team named "Turgutlu"
        footballTeam.setFirstEleven(new ArrayList<Player>());
        footballTeam.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        List<Player> firstEleven = new ArrayList<Player>();
        
        // Create 1 goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        goalkeeper.setAge(28);
        firstEleven.add(goalkeeper);
        
        // Create 10 other players (including strikers)
        for (int i = 2; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(i <= 5 ? Position.FORWARD : Position.MIDFIELD);
            player.setNumber(i);
            player.setAge(25);
            firstEleven.add(player);
        }
        
        footballTeam.setFirstEleven(firstEleven);
        
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        List<Announcement> announcements = new ArrayList<Announcement>();
        
        // Create 2 save announcements for goalkeeper
        for (int j = 0; j < 2; j++) {
            Announcement announcement = new Announcement();
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            try {
                announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            } catch (Exception e) {
                fail("Date parsing failed");
            }
            announcements.add(announcement);
        }
        
        footballTeam.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = footballTeam.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should return 0 when only save announcements exist", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // Create a football team named "Turgutlu"
        footballTeam.setFirstEleven(new ArrayList<Player>());
        footballTeam.setAnnouncements(new ArrayList<Announcement>());
        
        // Do not add any players to the first eleven
        footballTeam.setFirstEleven(new ArrayList<Player>());
        
        // No announcements added
        footballTeam.setAnnouncements(new ArrayList<Announcement>());
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = footballTeam.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should return 0 when no players in first eleven", 0, result);
    }
}