import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        // Announce scores for each of the 4 Strikers
        
        List<Player> firstEleven = new ArrayList<>();
        List<Announcement> announcements = new ArrayList<>();
        
        // Create 4 forwards with 2 goals each
        for (int i = 1; i <= 4; i++) {
            Player forward = new Player();
            forward.setName("Forward" + i);
            forward.setAge(25);
            forward.setNumber(i);
            forward.setPosition(Position.FORWARD);
            firstEleven.add(forward);
            
            // Add 2 score announcements for each forward
            for (int j = 0; j < 2; j++) {
                Announcement announcement = new Announcement();
                announcement.setPlayer(forward);
                announcement.setType(AnnouncementType.SCORE);
                announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                announcements.add(announcement);
            }
        }
        
        // Add 7 other players (non-forwards) with no goals
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD); // Non-forward position
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        // Announce scores for Strikers
        
        List<Player> firstEleven = new ArrayList<>();
        List<Announcement> announcements = new ArrayList<>();
        
        // Create 2 forwards with 3 goals each
        for (int i = 1; i <= 2; i++) {
            Player forward = new Player();
            forward.setName("Forward" + i);
            forward.setAge(25);
            forward.setNumber(i);
            forward.setPosition(Position.FORWARD);
            firstEleven.add(forward);
            
            // Add 3 score announcements for each forward
            for (int j = 0; j < 3; j++) {
                Announcement announcement = new Announcement();
                announcement.setPlayer(forward);
                announcement.setType(AnnouncementType.SCORE);
                announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                announcements.add(announcement);
            }
        }
        
        // Add 2 midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setAge(26);
            midfielder.setNumber(i);
            midfielder.setPosition(Position.MIDFIELD);
            firstEleven.add(midfielder);
        }
        
        // Add 7 other players with no goals
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(27);
            player.setNumber(i);
            player.setPosition(Position.DEFENSE); // Non-forward position
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        
        List<Player> firstEleven = new ArrayList<>();
        List<Announcement> announcements = new ArrayList<>();
        
        // Create 3 forwards with no goals
        for (int i = 1; i <= 3; i++) {
            Player forward = new Player();
            forward.setName("Forward" + i);
            forward.setAge(25);
            forward.setNumber(i);
            forward.setPosition(Position.FORWARD);
            firstEleven.add(forward);
        }
        
        // Add 8 other players with no goals
        for (int i = 4; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD);
            firstEleven.add(player);
        }
        
        // Add some save announcements (not score announcements)
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
        announcements.add(saveAnnouncement);
        
        team.setFirstEleven(firstEleven);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        
        List<Player> firstEleven = new ArrayList<>();
        List<Announcement> announcements = new ArrayList<>();
        
        // Create 1 goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        firstEleven.add(goalkeeper);
        
        // Add 10 other players (non-forwards)
        for (int i = 2; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.DEFENSE); // Non-forward position
            firstEleven.add(player);
        }
        
        // Add 2 save announcements for goalkeeper
        for (int i = 0; i < 2; i++) {
            Announcement announcement = new Announcement();
            announcement.setPlayer(goalkeeper);
            announcement.setType(AnnouncementType.SAVE);
            announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            announcements.add(announcement);
        }
        
        team.setFirstEleven(firstEleven);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        
        List<Player> firstEleven = new ArrayList<>(); // Empty list
        List<Announcement> announcements = new ArrayList<>();
        
        // Add some announcements for players not in first eleven
        Player player = new Player();
        player.setName("Player");
        player.setAge(25);
        player.setNumber(1);
        player.setPosition(Position.FORWARD);
        
        Announcement announcement = new Announcement();
        announcement.setPlayer(player);
        announcement.setType(AnnouncementType.SCORE);
        announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
        announcements.add(announcement);
        
        team.setFirstEleven(firstEleven);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}