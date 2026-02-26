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
        team.setFirstEleven(new ArrayList<Player>());
        team.setSpareTeam(new ArrayList<Player>());
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() throws Exception {
        // Create a football team named "Turgutlu"
        FootballTeam team = new FootballTeam();
        
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 4 forwards who will score goals
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
        }
        
        // Create 7 other players (non-forwards)
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD); // Non-forward position
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(firstEleven); // Also add to all players list
        
        // Announce scores for each of the 4 Strikers (2 goals each)
        List<Announcement> announcements = new ArrayList<>();
        for (Player striker : firstEleven) {
            if (striker.getPosition() == Position.FORWARD) {
                // Create 2 score announcements for each striker
                for (int i = 0; i < 2; i++) {
                    Announcement announcement = new Announcement();
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(striker);
                    announcements.add(announcement);
                }
            }
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // Create a football team named "Turgutlu"
        FootballTeam team = new FootballTeam();
        
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 2 forwards who will score goals
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
        }
        
        // Create 2 midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setAge(26);
            midfielder.setNumber(i);
            midfielder.setPosition(Position.MIDFIELD);
            firstEleven.add(midfielder);
        }
        
        // Create 7 other players
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(27);
            player.setNumber(i);
            player.setPosition(Position.DEFENSE);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(firstEleven);
        
        // Announce scores for Strikers (3 goals each)
        List<Announcement> announcements = new ArrayList<>();
        for (Player striker : firstEleven) {
            if (striker.getPosition() == Position.FORWARD) {
                // Create 3 score announcements for each striker
                for (int i = 0; i < 3; i++) {
                    Announcement announcement = new Announcement();
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(striker);
                    announcements.add(announcement);
                }
            }
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // Create a football team named "Turgutlu"
        FootballTeam team = new FootballTeam();
        
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 3 forwards with no goals
        for (int i = 1; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
        }
        
        // Create 8 other players
        for (int i = 4; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(firstEleven);
        
        // No announcements (no goals scored)
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create a football team named "Turgutlu"
        FootballTeam team = new FootballTeam();
        
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 1 goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        firstEleven.add(goalkeeper);
        
        // Create 10 other players (including forwards)
        for (int i = 2; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(25);
            player.setNumber(i);
            // Some forwards, but they don't score
            player.setPosition(i <= 4 ? Position.FORWARD : Position.DEFENSE);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(firstEleven);
        
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        List<Announcement> announcements = new ArrayList<>();
        
        // Create 2 save announcements for goalkeeper
        for (int i = 0; i < 2; i++) {
            Announcement announcement = new Announcement();
            announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            announcements.add(announcement);
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // Create a football team named "Turgutlu"
        FootballTeam team = new FootballTeam();
        
        // Do not add any players to the first eleven
        team.setFirstEleven(new ArrayList<Player>());
        team.setPlayers(new ArrayList<Player>());
        
        // Create some announcements (should not be counted since no players in first eleven)
        List<Announcement> announcements = new ArrayList<>();
        Player dummyPlayer = new Player();
        dummyPlayer.setName("Dummy");
        dummyPlayer.setAge(25);
        dummyPlayer.setNumber(1);
        dummyPlayer.setPosition(Position.FORWARD);
        
        Announcement announcement = new Announcement();
        announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
        announcement.setType(AnnouncementType.SCORE);
        announcement.setPlayer(dummyPlayer);
        announcements.add(announcement);
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}