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
        team.setFirstEleven(new ArrayList<>());
        team.setPlayers(new ArrayList<>());
        team.setAnnouncements(new ArrayList<>());
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() throws Exception {
        // Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        // Announce scores for each of the 4 Strikers
        
        // Create 4 forwards with goals
        for (int i = 1; i <= 4; i++) {
            Player forward = new Player();
            forward.setName("Forward" + i);
            forward.setPosition(Position.FORWARD);
            forward.setNumber(i);
            forward.setAge(25);
            team.getFirstEleven().add(forward);
            team.getPlayers().add(forward);
            
            // Add 2 score announcements for each forward
            for (int j = 0; j < 2; j++) {
                Announcement announcement = new Announcement();
                announcement.setPlayer(forward);
                announcement.setType(AnnouncementType.SCORE);
                announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                team.getAnnouncements().add(announcement);
            }
        }
        
        // Add 7 other players with no goals
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD); // Not forwards
            player.setNumber(i);
            player.setAge(26);
            team.getFirstEleven().add(player);
            team.getPlayers().add(player);
        }
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        // Announce scores for Strikers
        
        // Create 2 forwards with 3 goals each
        for (int i = 1; i <= 2; i++) {
            Player forward = new Player();
            forward.setName("Forward" + i);
            forward.setPosition(Position.FORWARD);
            forward.setNumber(i);
            forward.setAge(25);
            team.getFirstEleven().add(forward);
            team.getPlayers().add(forward);
            
            // Add 3 score announcements for each forward
            for (int j = 0; j < 3; j++) {
                Announcement announcement = new Announcement();
                announcement.setPlayer(forward);
                announcement.setType(AnnouncementType.SCORE);
                announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                team.getAnnouncements().add(announcement);
            }
        }
        
        // Add 2 midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setNumber(i);
            midfielder.setAge(27);
            team.getFirstEleven().add(midfielder);
            team.getPlayers().add(midfielder);
        }
        
        // Add 7 other players with no goals
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            player.setAge(28);
            team.getFirstEleven().add(player);
            team.getPlayers().add(player);
        }
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        
        // Add 3 forwards with no goals
        for (int i = 1; i <= 3; i++) {
            Player forward = new Player();
            forward.setName("Forward" + i);
            forward.setPosition(Position.FORWARD);
            forward.setNumber(i);
            forward.setAge(25);
            team.getFirstEleven().add(forward);
            team.getPlayers().add(forward);
        }
        
        // Add 8 other players
        for (int i = 4; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i);
            player.setAge(26);
            team.getFirstEleven().add(player);
            team.getPlayers().add(player);
        }
        
        // No announcements added, so all players have 0 goals
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        
        // Add 1 goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        goalkeeper.setAge(30);
        team.getFirstEleven().add(goalkeeper);
        team.getPlayers().add(goalkeeper);
        
        // Add 10 other players
        for (int i = 2; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            player.setAge(26);
            team.getFirstEleven().add(player);
            team.getPlayers().add(player);
        }
        
        // Add 2 save announcements for the goalkeeper
        for (int i = 0; i < 2; i++) {
            Announcement announcement = new Announcement();
            announcement.setPlayer(goalkeeper);
            announcement.setType(AnnouncementType.SAVE);
            announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            team.getAnnouncements().add(announcement);
        }
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        
        // First eleven is already empty from setUp()
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}