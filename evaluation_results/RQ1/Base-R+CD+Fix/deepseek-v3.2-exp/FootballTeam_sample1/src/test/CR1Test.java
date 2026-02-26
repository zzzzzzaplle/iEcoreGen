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
        // Create football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        // Announce scores for each of the 4 Strikers
        
        // Create 4 forwards with goals
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            team.getFirstEleven().add(striker);
            team.getPlayers().add(striker);
            
            // Add 2 score announcements for each striker
            for (int j = 0; j < 2; j++) {
                Announcement announcement = new Announcement();
                announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                team.getAnnouncements().add(announcement);
            }
        }
        
        // Add 7 other players with no goals
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD); // Non-forward position
            team.getFirstEleven().add(player);
            team.getPlayers().add(player);
        }
        
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 4 players × 2 goals = 8", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // Create football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        // Announce scores for Strikers
        
        // Create 2 forwards with goals
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            team.getFirstEleven().add(striker);
            team.getPlayers().add(striker);
            
            // Add 3 score announcements for each striker
            for (int j = 0; j < 3; j++) {
                Announcement announcement = new Announcement();
                announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                team.getAnnouncements().add(announcement);
            }
        }
        
        // Add 2 midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setAge(26);
            midfielder.setNumber(i);
            midfielder.setPosition(Position.MIDFIELD);
            team.getFirstEleven().add(midfielder);
            team.getPlayers().add(midfielder);
        }
        
        // Add 7 other players with no goals
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(27);
            player.setNumber(i);
            player.setPosition(Position.DEFENSE); // Non-forward position
            team.getFirstEleven().add(player);
            team.getPlayers().add(player);
        }
        
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 2 players × 3 goals = 6", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // Create football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        
        // Add 3 forwards with no goals
        for (int i = 1; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            team.getFirstEleven().add(striker);
            team.getPlayers().add(striker);
            
            // No announcements for these players
        }
        
        // Add 8 other players with no goals
        for (int i = 4; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD);
            team.getFirstEleven().add(player);
            team.getPlayers().add(player);
        }
        
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 0 when no goals scored", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        
        // Add 1 goalkeeper with save announcements
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        team.getFirstEleven().add(goalkeeper);
        team.getPlayers().add(goalkeeper);
        
        // Add 2 save announcements for goalkeeper
        for (int j = 0; j < 2; j++) {
            Announcement announcement = new Announcement();
            announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            team.getAnnouncements().add(announcement);
        }
        
        // Add 10 other players with no announcements
        for (int i = 2; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.DEFENSE);
            team.getFirstEleven().add(player);
            team.getPlayers().add(player);
        }
        
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 0 when only save announcements exist", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // Create football team named "Turgutlu"
        // Do not add any players to the first eleven
        
        // First eleven is already empty from setUp()
        // No players added to firstEleven list
        
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 0 when no players in first eleven", 0, result);
    }
}