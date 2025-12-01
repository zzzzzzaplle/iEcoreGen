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
        team.setPlayers(new ArrayList<>());
        team.setFirstEleven(new ArrayList<>());
        team.setAnnouncements(new ArrayList<>());
        
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 4 forwards (strikers)
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(10 + i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
            
            // Add 2 score announcements for each striker
            for (int j = 1; j <= 2; j++) {
                Announcement announcement = new Announcement();
                announcement.setPlayer(striker);
                announcement.setType(AnnouncementType.SCORE);
                announcement.setTime(dateFormat.parse("2024-01-0" + j + " 15:30:00"));
                team.getAnnouncements().add(announcement);
            }
        }
        
        // Add 7 other players (non-forwards) with no goals
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(24);
            player.setNumber(20 + i);
            player.setPosition(Position.MIDFIELD); // Midfielders who don't score
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Calculate and verify total announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 4 players × 2 goals = 8", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // Create a football team named "Turgutlu"
        team.setPlayers(new ArrayList<>());
        team.setFirstEleven(new ArrayList<>());
        team.setAnnouncements(new ArrayList<>());
        
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 2 forwards (strikers) who scored 3 goals each
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(10 + i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
            
            // Add 3 score announcements for each striker
            for (int j = 1; j <= 3; j++) {
                Announcement announcement = new Announcement();
                announcement.setPlayer(striker);
                announcement.setType(AnnouncementType.SCORE);
                announcement.setTime(dateFormat.parse("2024-01-0" + j + " 15:30:00"));
                team.getAnnouncements().add(announcement);
            }
        }
        
        // Add 2 midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setAge(26);
            midfielder.setNumber(20 + i);
            midfielder.setPosition(Position.MIDFIELD);
            firstEleven.add(midfielder);
        }
        
        // Add 7 other players with no goals
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(24);
            player.setNumber(30 + i);
            player.setPosition(Position.DEFENSE);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Calculate and verify total announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 2 players × 3 goals = 6", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // Create a football team named "Turgutlu"
        team.setPlayers(new ArrayList<>());
        team.setFirstEleven(new ArrayList<>());
        team.setAnnouncements(new ArrayList<>());
        
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 3 forwards (strikers) with no goals
        for (int i = 1; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(10 + i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
        }
        
        // Add 8 other players
        for (int i = 4; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(24);
            player.setNumber(20 + i);
            player.setPosition(Position.MIDFIELD);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // No announcements added - all players have 0 goals
        
        // Calculate and verify total announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 0 when no goals scored", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create a football team named "Turgutlu"
        team.setPlayers(new ArrayList<>());
        team.setFirstEleven(new ArrayList<>());
        team.setAnnouncements(new ArrayList<>());
        
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 1 goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        firstEleven.add(goalkeeper);
        
        // Add 10 other players (no strikers)
        for (int i = 2; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(24);
            player.setNumber(i);
            player.setPosition(Position.DEFENSE);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Add 2 save announcements for goalkeeper (no score announcements)
        for (int j = 1; j <= 2; j++) {
            Announcement announcement = new Announcement();
            announcement.setPlayer(goalkeeper);
            announcement.setType(AnnouncementType.SAVE);
            announcement.setTime(dateFormat.parse("2024-01-0" + j + " 15:30:00"));
            team.getAnnouncements().add(announcement);
        }
        
        // Calculate and verify total announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 0 when only save announcements exist", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // Create a football team named "Turgutlu"
        team.setPlayers(new ArrayList<>());
        team.setFirstEleven(new ArrayList<>()); // Empty first eleven
        team.setAnnouncements(new ArrayList<>());
        
        // No players added to first eleven
        
        // Calculate and verify total announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 0 when no players in first eleven", 0, result);
    }
}