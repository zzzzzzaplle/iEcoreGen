import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        // Announce scores for each of the 4 Strikers
        
        // Create 4 forwards with goals
        List<Player> firstEleven = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Player forward = new Player();
            forward.setName("Forward" + i);
            forward.setAge(25);
            forward.setNumber(i);
            forward.setPosition(Position.FORWARD);
            firstEleven.add(forward);
        }
        
        // Create 7 other players (non-forwards)
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD); // Non-forward position
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Create announcements: 4 forwards × 2 goals each = 8 announcements
        List<Announcement> announcements = new ArrayList<>();
        for (Player forward : firstEleven) {
            if (forward.getPosition() == Position.FORWARD) {
                // Create 2 goal announcements for each forward
                for (int i = 0; i < 2; i++) {
                    Announcement announcement = new Announcement();
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(forward);
                    announcements.add(announcement);
                }
            }
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        // Announce scores for Strikers
        
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 2 forwards with goals
        for (int i = 1; i <= 2; i++) {
            Player forward = new Player();
            forward.setName("Forward" + i);
            forward.setAge(25);
            forward.setNumber(i);
            forward.setPosition(Position.FORWARD);
            firstEleven.add(forward);
        }
        
        // Create 2 midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setAge(26);
            midfielder.setNumber(i);
            midfielder.setPosition(Position.MIDFIELD);
            firstEleven.add(midfielder);
        }
        
        // Create 7 other players
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(27);
            player.setNumber(i);
            player.setPosition(Position.DEFENSE);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Create announcements: 2 forwards × 3 goals each = 6 announcements
        List<Announcement> announcements = new ArrayList<>();
        for (Player forward : firstEleven) {
            if (forward.getPosition() == Position.FORWARD) {
                for (int i = 0; i < 3; i++) {
                    Announcement announcement = new Announcement();
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(forward);
                    announcements.add(announcement);
                }
            }
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 3 forwards with no goals
        for (int i = 1; i <= 3; i++) {
            Player forward = new Player();
            forward.setName("Forward" + i);
            forward.setAge(25);
            forward.setNumber(i);
            forward.setPosition(Position.FORWARD);
            firstEleven.add(forward);
        }
        
        // Create 8 other players
        for (int i = 4; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // No goal announcements - only save announcements to ensure they're ignored
        List<Announcement> announcements = new ArrayList<>();
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(firstEleven.get(0)); // Use first player (a forward)
        announcements.add(saveAnnouncement);
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 1 goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        firstEleven.add(goalkeeper);
        
        // Create 10 other players (non-forwards)
        for (int i = 2; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.DEFENSE);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Create 2 save announcements for goalkeeper (no score announcements)
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Announcement announcement = new Announcement();
            announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            announcements.add(announcement);
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        
        // First eleven is empty by default from constructor
        // But let's explicitly set it to empty to be sure
        team.setFirstEleven(new ArrayList<>());
        
        // Create some announcements to ensure they're ignored when no players in first eleven
        List<Announcement> announcements = new ArrayList<>();
        Player somePlayer = new Player();
        somePlayer.setName("SomePlayer");
        somePlayer.setAge(25);
        somePlayer.setNumber(1);
        somePlayer.setPosition(Position.FORWARD);
        
        Announcement announcement = new Announcement();
        announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
        announcement.setType(AnnouncementType.SCORE);
        announcement.setPlayer(somePlayer);
        announcements.add(announcement);
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}