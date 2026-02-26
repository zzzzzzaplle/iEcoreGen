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
        team.setFirstEleven(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() throws Exception {
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
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
            
            // Announce 2 goals for each striker
            for (int j = 0; j < 2; j++) {
                Announcement scoreAnnouncement = new Announcement();
                scoreAnnouncement.setPlayer(striker);
                scoreAnnouncement.setType(AnnouncementType.SCORE);
                scoreAnnouncement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                team.getAnnouncements().add(scoreAnnouncement);
            }
        }
        
        // Add 7 players with no goals
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD); // Not forward, so no goals counted
            player.setNumber(i);
            player.setAge(27);
            team.getFirstEleven().add(player);
        }
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        team.setFirstEleven(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 2 Strikers who scored 3 goals each
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(24);
            team.getFirstEleven().add(striker);
            
            // Announce 3 goals for each striker
            for (int j = 0; j < 3; j++) {
                Announcement scoreAnnouncement = new Announcement();
                scoreAnnouncement.setPlayer(striker);
                scoreAnnouncement.setType(AnnouncementType.SCORE);
                scoreAnnouncement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                team.getAnnouncements().add(scoreAnnouncement);
            }
        }
        
        // Add 2 Midfielders (no goals counted as they are not forwards)
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setNumber(i);
            midfielder.setAge(26);
            team.getFirstEleven().add(midfielder);
        }
        
        // Add 7 players with no goals
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE); // Not forward, so no goals counted
            player.setNumber(i);
            player.setAge(28);
            team.getFirstEleven().add(player);
        }
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        team.setFirstEleven(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        for (int i = 1; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            
            // 3 Strikers but no goals announced
            if (i <= 3) {
                player.setPosition(Position.FORWARD);
            } else if (i <= 6) {
                player.setPosition(Position.MIDFIELD);
            } else if (i <= 9) {
                player.setPosition(Position.DEFENSE);
            } else {
                player.setPosition(Position.GOALKEEPER);
            }
            
            player.setNumber(i);
            player.setAge(25);
            team.getFirstEleven().add(player);
        }
        
        // No announcements added, so no goals
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        team.setFirstEleven(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 11 players, including 1 Goalkeeper
        for (int i = 1; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            
            if (i == 1) {
                player.setPosition(Position.GOALKEEPER);
            } else if (i <= 4) {
                player.setPosition(Position.DEFENSE);
            } else if (i <= 8) {
                player.setPosition(Position.MIDFIELD);
            } else {
                player.setPosition(Position.FORWARD);
            }
            
            player.setNumber(i);
            player.setAge(26);
            team.getFirstEleven().add(player);
        }
        
        // Goalkeeper saved 2 goals (save announcements, not score announcements)
        Player goalkeeper = team.getFirstEleven().get(0);
        for (int j = 0; j < 2; j++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            team.getAnnouncements().add(saveAnnouncement);
        }
        
        // No Strikers have scored goals
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        team.setFirstEleven(new ArrayList<Player>()); // Empty first eleven
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Add some score announcements but no players in first eleven
        Player striker = new Player();
        striker.setName("Striker");
        striker.setPosition(Position.FORWARD);
        striker.setNumber(1);
        striker.setAge(25);
        
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setPlayer(striker);
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
        team.getAnnouncements().add(scoreAnnouncement);
        
        // Do not add any players to the first eleven
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}