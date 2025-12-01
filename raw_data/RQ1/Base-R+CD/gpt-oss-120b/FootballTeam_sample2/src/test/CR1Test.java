import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import com.turgutlu.football.*;

public class CR1Test {
    
    private FootballTeam team;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_allStrikersWithGoals() throws ParseException {
        // Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        for (int i = 1; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.FORWARD);
            player.setNumber(i);
            team.addPlayer(player);
            team.addFirstEleven(player);
        }
        
        // Set positions correctly - first 4 as forwards, rest can be other positions
        for (int i = 0; i < 11; i++) {
            if (i < 4) {
                team.getFirstEleven().get(i).setPosition(Position.FORWARD);
            } else {
                team.getFirstEleven().get(i).setPosition(Position.DEFENSE); // Just to vary positions
            }
        }
        
        // Announce scores for each of the 4 Strikers (2 goals each)
        Date time = dateFormat.parse("2023-01-01 15:00:00");
        for (int i = 0; i < 4; i++) {
            Player striker = team.getFirstEleven().get(i);
            // Each striker scores 2 goals
            for (int j = 0; j < 2; j++) {
                Announcement announcement = new Announcement();
                announcement.setTime(new Date(time.getTime() + j * 1000));
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                team.addAnnouncement(announcement);
            }
        }
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_mixedPlayersScoring() throws ParseException {
        // Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        for (int i = 1; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setNumber(i);
            team.addPlayer(player);
            team.addFirstEleven(player);
        }
        
        // Set positions: first 2 as forwards, next 2 as midfielders, rest as defense
        for (int i = 0; i < 11; i++) {
            if (i < 2) {
                team.getFirstEleven().get(i).setPosition(Position.FORWARD);
            } else if (i < 4) {
                team.getFirstEleven().get(i).setPosition(Position.MIDFIELD);
            } else {
                team.getFirstEleven().get(i).setPosition(Position.DEFENSE);
            }
        }
        
        // Announce scores for Strikers (2 strikers with 3 goals each)
        Date time = dateFormat.parse("2023-01-01 15:00:00");
        for (int i = 0; i < 2; i++) {
            Player striker = team.getFirstEleven().get(i);
            // Each striker scores 3 goals
            for (int j = 0; j < 3; j++) {
                Announcement announcement = new Announcement();
                announcement.setTime(new Date(time.getTime() + j * 1000));
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                team.addAnnouncement(announcement);
            }
        }
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_firstElevenWithNoGoals() {
        // Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        for (int i = 1; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setNumber(i);
            if (i <= 3) {
                player.setPosition(Position.FORWARD);
            } else {
                player.setPosition(Position.DEFENSE);
            }
            team.addPlayer(player);
            team.addFirstEleven(player);
        }
        
        // No announcements added
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_onlyGoalkeeperWithSaveAnnouncements() throws ParseException {
        // Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        for (int i = 1; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setNumber(i);
            if (i == 1) {
                player.setPosition(Position.GOALKEEPER);
            } else {
                player.setPosition(Position.DEFENSE);
            }
            team.addPlayer(player);
            team.addFirstEleven(player);
        }
        
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        Player goalkeeper = team.getFirstEleven().get(0);
        Date time = dateFormat.parse("2023-01-01 15:00:00");
        for (int i = 0; i < 2; i++) {
            Announcement announcement = new Announcement();
            announcement.setTime(new Date(time.getTime() + i * 1000));
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            team.addAnnouncement(announcement);
        }
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_noPlayersInFirstEleven() {
        // Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}