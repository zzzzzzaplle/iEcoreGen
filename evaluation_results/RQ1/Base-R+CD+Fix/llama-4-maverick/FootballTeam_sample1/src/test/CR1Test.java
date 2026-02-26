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
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() throws Exception {
        // Create football team "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        // Announce scores for each of the 4 Strikers
        
        // Create 4 strikers
        for (int i = 0; i < 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i + 1);
            striker.setAge(25);
            team.getFirstEleven().add(striker);
            team.getPlayers().add(striker);
            
            // Add 2 score announcements for each striker
            for (int j = 0; j < 2; j++) {
                Announcement announcement = new Announcement();
                announcement.setPlayer(striker);
                announcement.setType(AnnouncementType.SCORE);
                announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                team.getAnnouncements().add(announcement);
            }
        }
        
        // Add 7 other players with no goals
        for (int i = 0; i < 7; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setPosition(Position.MIDFIELD); // Not forward, so no goals counted
            otherPlayer.setNumber(i + 5);
            otherPlayer.setAge(28);
            team.getFirstEleven().add(otherPlayer);
            team.getPlayers().add(otherPlayer);
        }
        
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 4 players × 2 goals = 8", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // Create football team "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        // Announce scores for Strikers
        
        // Create 2 strikers with 3 goals each
        for (int i = 0; i < 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i + 1);
            striker.setAge(24);
            team.getFirstEleven().add(striker);
            team.getPlayers().add(striker);
            
            // Add 3 score announcements for each striker
            for (int j = 0; j < 3; j++) {
                Announcement announcement = new Announcement();
                announcement.setPlayer(striker);
                announcement.setType(AnnouncementType.SCORE);
                announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                team.getAnnouncements().add(announcement);
            }
        }
        
        // Add 2 midfielders
        for (int i = 0; i < 2; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setNumber(i + 3);
            midfielder.setAge(26);
            team.getFirstEleven().add(midfielder);
            team.getPlayers().add(midfielder);
        }
        
        // Add 7 other players with no goals
        for (int i = 0; i < 7; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setPosition(Position.DEFENSE);
            otherPlayer.setNumber(i + 5);
            otherPlayer.setAge(27);
            team.getFirstEleven().add(otherPlayer);
            team.getPlayers().add(otherPlayer);
        }
        
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 2 players × 3 goals = 6", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // Create football team "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        
        // Create 3 strikers with no goals
        for (int i = 0; i < 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i + 1);
            striker.setAge(23);
            team.getFirstEleven().add(striker);
            team.getPlayers().add(striker);
        }
        
        // Add 8 other players with no goals
        for (int i = 0; i < 8; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setPosition(Position.MIDFIELD);
            otherPlayer.setNumber(i + 4);
            otherPlayer.setAge(25);
            team.getFirstEleven().add(otherPlayer);
            team.getPlayers().add(otherPlayer);
        }
        
        // No announcements added
        
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 0 when no goals scored", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create football team "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        
        // Create 1 goalkeeper with 2 save announcements
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        goalkeeper.setAge(30);
        team.getFirstEleven().add(goalkeeper);
        team.getPlayers().add(goalkeeper);
        
        // Add 2 save announcements for goalkeeper
        for (int i = 0; i < 2; i++) {
            Announcement announcement = new Announcement();
            announcement.setPlayer(goalkeeper);
            announcement.setType(AnnouncementType.SAVE);
            announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            team.getAnnouncements().add(announcement);
        }
        
        // Add 10 other players (no strikers)
        for (int i = 0; i < 10; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setPosition(Position.DEFENSE); // Not forward, so no goals counted
            otherPlayer.setNumber(i + 2);
            otherPlayer.setAge(26);
            team.getFirstEleven().add(otherPlayer);
            team.getPlayers().add(otherPlayer);
        }
        
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 0 when only save announcements exist", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // Create football team "Turgutlu"
        // Do not add any players to the first eleven
        
        // First eleven is already empty from setup
        
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 0 when no players in first eleven", 0, result);
    }
}