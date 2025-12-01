import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

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
        // Create football team "Turgutlu"
        team.setFirstEleven(new ArrayList<Player>());
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<>();
        List<Player> allPlayers = new ArrayList<>();
        
        // Create 4 forwards who will score goals
        for (int i = 1; i <= 4; i++) {
            Player forward = new Player();
            forward.setName("Striker" + i);
            forward.setPosition(Position.FORWARD);
            forward.setNumber(i);
            forward.setAge(25);
            firstEleven.add(forward);
            allPlayers.add(forward);
        }
        
        // Add 7 other players (no goals)
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i);
            player.setAge(26);
            firstEleven.add(player);
            allPlayers.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(allPlayers);
        
        // Announce scores for each of the 4 Strikers (2 goals each)
        List<Announcement> announcements = new ArrayList<>();
        for (Player forward : firstEleven) {
            if (forward.getPosition() == Position.FORWARD) {
                // Create 2 score announcements for each forward
                for (int i = 0; i < 2; i++) {
                    Announcement announcement = new Announcement();
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(forward);
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
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
        // Create football team "Turgutlu"
        team.setFirstEleven(new ArrayList<Player>());
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<>();
        List<Player> allPlayers = new ArrayList<>();
        
        // Create 2 forwards who will score goals
        for (int i = 1; i <= 2; i++) {
            Player forward = new Player();
            forward.setName("Striker" + i);
            forward.setPosition(Position.FORWARD);
            forward.setNumber(i);
            forward.setAge(25);
            firstEleven.add(forward);
            allPlayers.add(forward);
        }
        
        // Add 2 midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setNumber(i);
            midfielder.setAge(26);
            firstEleven.add(midfielder);
            allPlayers.add(midfielder);
        }
        
        // Add 7 other players
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            player.setAge(27);
            firstEleven.add(player);
            allPlayers.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(allPlayers);
        
        // Announce scores for Strikers (3 goals each)
        List<Announcement> announcements = new ArrayList<>();
        for (Player forward : firstEleven) {
            if (forward.getPosition() == Position.FORWARD) {
                // Create 3 score announcements for each forward
                for (int i = 0; i < 3; i++) {
                    Announcement announcement = new Announcement();
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(forward);
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
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
        // Create football team "Turgutlu"
        team.setFirstEleven(new ArrayList<Player>());
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        List<Player> firstEleven = new ArrayList<>();
        List<Player> allPlayers = new ArrayList<>();
        
        // Create 3 forwards (no goals)
        for (int i = 1; i <= 3; i++) {
            Player forward = new Player();
            forward.setName("Striker" + i);
            forward.setPosition(Position.FORWARD);
            forward.setNumber(i);
            forward.setAge(25);
            firstEleven.add(forward);
            allPlayers.add(forward);
        }
        
        // Add 8 other players
        for (int i = 4; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i);
            player.setAge(26);
            firstEleven.add(player);
            allPlayers.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(allPlayers);
        
        // No announcements added (all players scored 0 goals)
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create football team "Turgutlu"
        team.setFirstEleven(new ArrayList<Player>());
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        List<Player> firstEleven = new ArrayList<>();
        List<Player> allPlayers = new ArrayList<>();
        
        // Create goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        goalkeeper.setAge(30);
        firstEleven.add(goalkeeper);
        allPlayers.add(goalkeeper);
        
        // Add 10 other players
        for (int i = 2; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            player.setAge(25);
            firstEleven.add(player);
            allPlayers.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(allPlayers);
        
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        List<Announcement> announcements = new ArrayList<>();
        
        // Create 2 save announcements for goalkeeper
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
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // Create football team "Turgutlu"
        team.setFirstEleven(new ArrayList<Player>());
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Do not add any players to the first eleven
        // No announcements added
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}