import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
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
        // Create 4 Strikers who scored 2 goals each
        List<Player> firstEleven = new ArrayList<>();
        List<Announcement> announcements = new ArrayList<>();
        
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
            
            // Add 2 score announcements for each striker
            for (int j = 0; j < 2; j++) {
                Announcement announcement = new Announcement();
                announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                announcements.add(announcement);
            }
        }
        
        // Add 7 players with no goals (non-forward positions)
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setAnnouncements(announcements);
        
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 4 players × 2 goals = 8
        assertEquals("Total goal scoring announcements should be 8", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // Create 2 Strikers who scored 3 goals each
        List<Player> firstEleven = new ArrayList<>();
        List<Announcement> announcements = new ArrayList<>();
        
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
            
            // Add 3 score announcements for each striker
            for (int j = 0; j < 3; j++) {
                Announcement announcement = new Announcement();
                announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                announcements.add(announcement);
            }
        }
        
        // Add 2 Midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setAge(26);
            midfielder.setNumber(i);
            midfielder.setPosition(Position.MIDFIELD);
            firstEleven.add(midfielder);
        }
        
        // Add 7 players with no goals (other positions)
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(27);
            player.setNumber(i);
            player.setPosition(Position.DEFENSE);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setAnnouncements(announcements);
        
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 2 players × 3 goals = 6
        assertEquals("Total goal scoring announcements should be 6", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // Create 11 players including 3 Strikers, but all scored 0 goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Add 3 Strikers with no goals
        for (int i = 1; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
        }
        
        // Add remaining 8 players
        for (int i = 4; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        // No announcements added
        
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 0 (no score announcements)
        assertEquals("Total goal scoring announcements should be 0", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create 11 players including 1 Goalkeeper with save announcements
        List<Player> firstEleven = new ArrayList<>();
        List<Announcement> announcements = new ArrayList<>();
        
        // Add 1 Goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        firstEleven.add(goalkeeper);
        
        // Add 2 save announcements for goalkeeper
        for (int i = 0; i < 2; i++) {
            Announcement announcement = new Announcement();
            announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            announcements.add(announcement);
        }
        
        // Add remaining 10 players (including 3 Strikers with no goals)
        for (int i = 2; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
        }
        
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setAnnouncements(announcements);
        
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 0 (only save announcements, no score announcements)
        assertEquals("Total goal scoring announcements should be 0", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // No players added to first eleven
        
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 0 (no players, no announcements)
        assertEquals("Total goal scoring announcements should be 0", 0, result);
    }
}