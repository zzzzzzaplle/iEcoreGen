import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
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
        // Create football team named "Turgutlu"
        team.setFirstEleven(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 4 Strikers who scored 2 goals each
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            team.getFirstEleven().add(striker);
            
            // Announce 2 scores for each striker
            for (int j = 1; j <= 2; j++) {
                Announcement scoreAnnouncement = new Announcement();
                scoreAnnouncement.setType(AnnouncementType.SCORE);
                scoreAnnouncement.setPlayer(striker);
                scoreAnnouncement.setTime(dateFormat.parse("2024-01-01 1" + i + ":00:00"));
                team.getAnnouncements().add(scoreAnnouncement);
            }
        }
        
        // Add 7 players with no goals
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            player.setAge(26);
            team.getFirstEleven().add(player);
        }
        
        // Expected: 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // Create football team named "Turgutlu"
        team.setFirstEleven(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 2 Strikers who scored 3 goals each
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            team.getFirstEleven().add(striker);
            
            // Announce 3 scores for each striker
            for (int j = 1; j <= 3; j++) {
                Announcement scoreAnnouncement = new Announcement();
                scoreAnnouncement.setType(AnnouncementType.SCORE);
                scoreAnnouncement.setPlayer(striker);
                scoreAnnouncement.setTime(dateFormat.parse("2024-01-01 1" + i + ":00:00"));
                team.getAnnouncements().add(scoreAnnouncement);
            }
        }
        
        // Add 2 Midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setNumber(i);
            midfielder.setAge(27);
            team.getFirstEleven().add(midfielder);
        }
        
        // Add 7 players with no goals
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            player.setAge(26);
            team.getFirstEleven().add(player);
        }
        
        // Expected: (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // Create football team named "Turgutlu"
        team.setFirstEleven(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 11 players including 3 Strikers with 0 goals
        for (int i = 1; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            team.getFirstEleven().add(striker);
        }
        
        // Add remaining 8 players
        for (int i = 4; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            player.setAge(26);
            team.getFirstEleven().add(player);
        }
        
        // No goal announcements added
        
        // Expected: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create football team named "Turgutlu"
        team.setFirstEleven(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 11 players including 1 Goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        goalkeeper.setAge(30);
        team.getFirstEleven().add(goalkeeper);
        
        // Add remaining 10 players
        for (int i = 2; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            player.setAge(26);
            team.getFirstEleven().add(player);
        }
        
        // Add 2 save announcements for goalkeeper (no score announcements)
        for (int i = 1; i <= 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            team.getAnnouncements().add(saveAnnouncement);
        }
        
        // Expected: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // Create football team named "Turgutlu"
        team.setFirstEleven(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Do not add any players to the first eleven (already empty)
        
        // Add some announcements (should not be counted since no players in first eleven)
        Player striker = new Player();
        striker.setName("Striker");
        striker.setPosition(Position.FORWARD);
        striker.setNumber(1);
        striker.setAge(25);
        
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(striker);
        scoreAnnouncement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
        team.getAnnouncements().add(scoreAnnouncement);
        
        // Expected: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}