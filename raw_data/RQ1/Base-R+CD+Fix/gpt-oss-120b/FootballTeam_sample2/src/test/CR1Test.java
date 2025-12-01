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
        team = new FootballTeam();
        
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 4 strikers who will each score 2 goals
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            firstEleven.add(striker);
        }
        
        // Add 7 other players (non-strikers)
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD); // Using midfield for non-strikers
            player.setNumber(i);
            player.setAge(27);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Announce scores for each of the 4 Strikers (2 goals each)
        List<Announcement> announcements = new ArrayList<>();
        for (Player striker : firstEleven) {
            if (striker.getPosition() == Position.FORWARD) {
                // Add 2 score announcements for each striker
                for (int j = 0; j < 2; j++) {
                    Announcement announcement = new Announcement();
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(striker);
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                    announcements.add(announcement);
                }
            }
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 8", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 2 strikers who will each score 3 goals
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            firstEleven.add(striker);
        }
        
        // Add 2 midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setNumber(i);
            midfielder.setAge(26);
            firstEleven.add(midfielder);
        }
        
        // Add 7 other players
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            player.setAge(28);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Announce scores for Strikers (3 goals each)
        List<Announcement> announcements = new ArrayList<>();
        for (Player striker : firstEleven) {
            if (striker.getPosition() == Position.FORWARD) {
                // Add 3 score announcements for each striker
                for (int j = 0; j < 3; j++) {
                    Announcement announcement = new Announcement();
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(striker);
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                    announcements.add(announcement);
                }
            }
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 6", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 3 strikers with 0 goals
        for (int i = 1; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            firstEleven.add(striker);
        }
        
        // Add 8 other players
        for (int i = 4; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i);
            player.setAge(27);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // No score announcements added
        List<Announcement> announcements = new ArrayList<>();
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 0", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 1 goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        goalkeeper.setAge(30);
        firstEleven.add(goalkeeper);
        
        // Add 10 other players (no strikers)
        for (int i = 2; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            player.setAge(26);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        List<Announcement> announcements = new ArrayList<>();
        
        // Add 2 save announcements for the goalkeeper
        for (int i = 0; i < 2; i++) {
            Announcement announcement = new Announcement();
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            announcements.add(announcement);
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 0", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Do not add any players to the first eleven
        team.setFirstEleven(new ArrayList<>());
        
        // Add some announcements (shouldn't matter since no players in first eleven)
        List<Announcement> announcements = new ArrayList<>();
        
        Player somePlayer = new Player();
        somePlayer.setName("SomePlayer");
        somePlayer.setPosition(Position.FORWARD);
        somePlayer.setNumber(10);
        somePlayer.setAge(25);
        
        Announcement announcement = new Announcement();
        announcement.setType(AnnouncementType.SCORE);
        announcement.setPlayer(somePlayer);
        announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
        announcements.add(announcement);
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 0", 0, result);
    }
}