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
        // Create football team named "Turgutlu"
        team = new FootballTeam();
        
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        for (int i = 1; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setNumber(i);
            player.setAge(25);
            
            if (i <= 4) {
                // 4 Strikers
                player.setPosition(Position.FORWARD);
                team.addToFirstEleven(player);
                team.addPlayer(player);
                
                // Announce 2 scores for each Striker
                for (int j = 0; j < 2; j++) {
                    Announcement announcement = new Announcement();
                    announcement.setPlayer(player);
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                    team.addAnnouncement(announcement);
                }
            } else {
                // 7 players with no goals (other positions)
                player.setPosition(Position.MIDFIELD);
                team.addToFirstEleven(player);
                team.addPlayer(player);
            }
        }
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // Create football team named "Turgutlu"
        team = new FootballTeam();
        
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        for (int i = 1; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setNumber(i);
            player.setAge(25);
            
            if (i <= 2) {
                // 2 Strikers who scored 3 goals each
                player.setPosition(Position.FORWARD);
                team.addToFirstEleven(player);
                team.addPlayer(player);
                
                // Announce 3 scores for each Striker
                for (int j = 0; j < 3; j++) {
                    Announcement announcement = new Announcement();
                    announcement.setPlayer(player);
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                    team.addAnnouncement(announcement);
                }
            } else if (i <= 4) {
                // 2 Midfielders
                player.setPosition(Position.MIDFIELD);
                team.addToFirstEleven(player);
                team.addPlayer(player);
            } else {
                // 7 players with no goals
                player.setPosition(Position.DEFENSE);
                team.addToFirstEleven(player);
                team.addPlayer(player);
            }
        }
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // Create football team named "Turgutlu"
        team = new FootballTeam();
        
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        for (int i = 1; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setNumber(i);
            player.setAge(25);
            
            if (i <= 3) {
                // 3 Strikers with no goals
                player.setPosition(Position.FORWARD);
            } else {
                // Other positions
                player.setPosition(Position.MIDFIELD);
            }
            team.addToFirstEleven(player);
            team.addPlayer(player);
        }
        
        // No announcements added
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create football team named "Turgutlu"
        team = new FootballTeam();
        
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        for (int i = 1; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setNumber(i);
            player.setAge(25);
            
            if (i == 1) {
                // 1 Goalkeeper who saved 2 goals
                player.setPosition(Position.GOALKEEPER);
                team.addToFirstEleven(player);
                team.addPlayer(player);
                
                // Announce 2 saves for the Goalkeeper
                for (int j = 0; j < 2; j++) {
                    Announcement announcement = new Announcement();
                    announcement.setPlayer(player);
                    announcement.setType(AnnouncementType.SAVE);
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                    team.addAnnouncement(announcement);
                }
            } else {
                // Other players (no goals)
                player.setPosition(Position.DEFENSE);
                team.addToFirstEleven(player);
                team.addPlayer(player);
            }
        }
        
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // Create football team named "Turgutlu"
        team = new FootballTeam();
        
        // Do not add any players to the first eleven
        // (team is already initialized with empty lists in setUp)
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}