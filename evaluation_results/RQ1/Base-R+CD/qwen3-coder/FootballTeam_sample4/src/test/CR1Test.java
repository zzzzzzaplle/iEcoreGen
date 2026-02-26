import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private FootballTeam team;
    private List<Player> firstEleven;
    private List<Announcement> announcements;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        firstEleven = new ArrayList<>();
        announcements = new ArrayList<>();
        team.setFirstEleven(firstEleven);
        team.setAnnouncements(announcements);
    }
    
    @Test
    public void testCase1_allStrikersWithGoals() throws Exception {
        // Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<Player> players = new ArrayList<>();
        
        // Create 4 strikers
        for (int i = 0; i < 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + (i + 1));
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
            players.add(striker);
        }
        
        // Create 7 other players with no goals
        for (int i = 0; i < 7; i++) {
            Player player = new Player();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.DEFENSE); // Any position other than FORWARD
            firstEleven.add(player);
            players.add(player);
        }
        
        team.setPlayers(players);
        
        // Announce scores for each of the 4 Strikers (2 goals each)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Player striker : firstEleven) {
            if (striker.getPosition() == Position.FORWARD) {
                // Each striker scores 2 goals
                for (int j = 0; j < 2; j++) {
                    Announcement announcement = new Announcement();
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(striker);
                    announcement.setTime(sdf.parse("2023-01-01 10:00:00"));
                    announcements.add(announcement);
                }
            }
        }
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_mixedPlayersScoring() throws Exception {
        // Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<Player> players = new ArrayList<>();
        
        // Create 2 strikers
        for (int i = 0; i < 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + (i + 1));
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
            players.add(striker);
        }
        
        // Create 2 midfielders
        for (int i = 0; i < 2; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + (i + 1));
            midfielder.setPosition(Position.MIDFIELD);
            firstEleven.add(midfielder);
            players.add(midfielder);
        }
        
        // Create 7 other players with no goals
        for (int i = 0; i < 7; i++) {
            Player player = new Player();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.DEFENSE);
            firstEleven.add(player);
            players.add(player);
        }
        
        team.setPlayers(players);
        
        // Announce scores for Strikers (3 goals each)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Player player : firstEleven) {
            if (player.getPosition() == Position.FORWARD) {
                // Each striker scores 3 goals
                for (int j = 0; j < 3; j++) {
                    Announcement announcement = new Announcement();
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(player);
                    announcement.setTime(sdf.parse("2023-01-01 10:00:00"));
                    announcements.add(announcement);
                }
            }
        }
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_firstElevenWithNoGoals() throws Exception {
        // Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        List<Player> players = new ArrayList<>();
        
        // Create 3 strikers
        for (int i = 0; i < 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + (i + 1));
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
            players.add(striker);
        }
        
        // Create 8 other players
        for (int i = 0; i < 8; i++) {
            Player player = new Player();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.DEFENSE);
            firstEleven.add(player);
            players.add(player);
        }
        
        team.setPlayers(players);
        
        // No announcements added (0 goals)
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_onlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        List<Player> players = new ArrayList<>();
        
        // Create 1 goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        firstEleven.add(goalkeeper);
        players.add(goalkeeper);
        
        // Create 10 other players (no strikers)
        for (int i = 0; i < 10; i++) {
            Player player = new Player();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.DEFENSE);
            firstEleven.add(player);
            players.add(player);
        }
        
        team.setPlayers(players);
        
        // Add save announcements for goalkeeper
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 2; i++) {
            Announcement announcement = new Announcement();
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            announcement.setTime(sdf.parse("2023-01-01 10:00:00"));
            announcements.add(announcement);
        }
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_noPlayersInFirstEleven() throws Exception {
        // Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        team.setPlayers(new ArrayList<>());
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}