import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private FootballTeam team;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() throws Exception {
        // Create a football team named "Turgutlu"
        FootballTeam turgutlu = new FootballTeam();
        
        // Create 4 Strikers who scored 2 goals each
        List<Player> firstEleven = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            firstEleven.add(striker);
        }
        
        // Add 7 players with no goals (other positions)
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i);
            player.setAge(26);
            firstEleven.add(player);
        }
        
        turgutlu.setFirstEleven(firstEleven);
        
        // Create announcements: 4 Strikers × 2 goals each = 8 announcements
        List<Announcement> announcements = new ArrayList<>();
        for (Player striker : firstEleven) {
            if (striker.getPosition() == Position.FORWARD) {
                // Add 2 score announcements for each striker
                for (int j = 0; j < 2; j++) {
                    Announcement announcement = new Announcement();
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(striker);
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                    announcements.add(announcement);
                }
            }
        }
        
        turgutlu.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = turgutlu.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // Create a football team named "Turgutlu"
        FootballTeam turgutlu = new FootballTeam();
        
        // Create 2 Strikers who scored 3 goals each
        List<Player> firstEleven = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            firstEleven.add(striker);
        }
        
        // Add 2 Midfielders and 7 players with no goals
        for (int i = 3; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(i <= 4 ? Position.MIDFIELD : Position.DEFENSE);
            player.setNumber(i);
            player.setAge(26);
            firstEleven.add(player);
        }
        
        turgutlu.setFirstEleven(firstEleven);
        
        // Create announcements: 2 Strikers × 3 goals each = 6 announcements
        List<Announcement> announcements = new ArrayList<>();
        for (Player striker : firstEleven) {
            if (striker.getPosition() == Position.FORWARD) {
                // Add 3 score announcements for each striker
                for (int j = 0; j < 3; j++) {
                    Announcement announcement = new Announcement();
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(striker);
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                    announcements.add(announcement);
                }
            }
        }
        
        turgutlu.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = turgutlu.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // Create a football team named "Turgutlu"
        FootballTeam turgutlu = new FootballTeam();
        
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        List<Player> firstEleven = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            firstEleven.add(striker);
        }
        
        for (int i = 4; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i);
            player.setAge(26);
            firstEleven.add(player);
        }
        
        turgutlu.setFirstEleven(firstEleven);
        
        // No score announcements - only save announcements for non-forwards
        List<Announcement> announcements = new ArrayList<>();
        for (Player player : firstEleven) {
            if (player.getPosition() != Position.FORWARD) {
                Announcement announcement = new Announcement();
                announcement.setType(AnnouncementType.SAVE);
                announcement.setPlayer(player);
                announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                announcements.add(announcement);
            }
        }
        
        turgutlu.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0
        int result = turgutlu.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create a football team named "Turgutlu"
        FootballTeam turgutlu = new FootballTeam();
        
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Add goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        goalkeeper.setAge(30);
        firstEleven.add(goalkeeper);
        
        // Add 10 other players (no strikers)
        for (int i = 2; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            player.setAge(26);
            firstEleven.add(player);
        }
        
        turgutlu.setFirstEleven(firstEleven);
        
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Announcement announcement = new Announcement();
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            announcements.add(announcement);
        }
        
        turgutlu.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = turgutlu.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // Create a football team named "Turgutlu"
        FootballTeam turgutlu = new FootballTeam();
        
        // Do not add any players to the first eleven (already empty by default)
        // Add some announcements for players not in first eleven
        List<Announcement> announcements = new ArrayList<>();
        
        Player striker = new Player();
        striker.setName("Striker");
        striker.setPosition(Position.FORWARD);
        striker.setNumber(9);
        striker.setAge(25);
        
        for (int i = 0; i < 3; i++) {
            Announcement announcement = new Announcement();
            announcement.setType(AnnouncementType.SCORE);
            announcement.setPlayer(striker);
            announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            announcements.add(announcement);
        }
        
        turgutlu.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (no players in first eleven, no announcements)
        int result = turgutlu.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}