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
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        // Announce scores for each of the 4 Strikers
        
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 4 forwards with 2 goals each
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
            
            // Add 2 goal announcements for each striker
            for (int j = 0; j < 2; j++) {
                Announcement announcement = new Announcement();
                announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                team.getAnnouncements().add(announcement);
            }
        }
        
        // Add 7 other players (non-forwards) with no goals
        for (int i = 5; i <= 11; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setAge(26);
            otherPlayer.setNumber(i);
            otherPlayer.setPosition(Position.MIDFIELD); // Not forward, so goals won't count
            firstEleven.add(otherPlayer);
        }
        
        team.setFirstEleven(firstEleven);
        
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
        
        // Create 2 forwards with 3 goals each
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
            
            // Add 3 goal announcements for each striker
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
            firstEleven.add(midfielder);
        }
        
        // Add 7 other players with no goals
        for (int i = 5; i <= 11; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setAge(27);
            otherPlayer.setNumber(i);
            otherPlayer.setPosition(Position.DEFENSE);
            firstEleven.add(otherPlayer);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        
        List<Player> firstEleven = new ArrayList<>();
        
        // Add 3 forwards with no goals
        for (int i = 1; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
        }
        
        // Add 8 other players
        for (int i = 4; i <= 11; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setAge(26);
            otherPlayer.setNumber(i);
            otherPlayer.setPosition(Position.DEFENSE);
            firstEleven.add(otherPlayer);
        }
        
        team.setFirstEleven(firstEleven);
        
        // No announcements added, so no goals
        
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
        
        // Add 1 goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        firstEleven.add(goalkeeper);
        
        // Add 10 other players including 2 forwards
        for (int i = 2; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
        }
        
        for (int i = 4; i <= 11; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setAge(26);
            otherPlayer.setNumber(i);
            otherPlayer.setPosition(Position.MIDFIELD);
            firstEleven.add(otherPlayer);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Add 2 save announcements for goalkeeper (not score announcements)
        for (int j = 0; j < 2; j++) {
            Announcement announcement = new Announcement();
            announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            team.getAnnouncements().add(announcement);
        }
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // SetUp: Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        
        // First eleven is already empty by default from constructor
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}