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
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        // Announce scores for each of the 4 Strikers
        
        // Create first eleven players
        List<Player> firstEleven = new ArrayList<>();
        
        // Add 4 forwards (strikers) with goals
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
        }
        
        // Add 7 other players (non-forwards)
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD); // Midfielders who don't score
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(new ArrayList<>(firstEleven)); // Also add to all players list
        
        // Create announcements: 4 strikers × 2 goals each = 8 announcements
        List<Announcement> announcements = new ArrayList<>();
        for (Player striker : firstEleven) {
            if (striker.getPosition() == Position.FORWARD) {
                // Add 2 goal announcements for each striker
                for (int i = 0; i < 2; i++) {
                    Announcement announcement = new Announcement();
                    announcement.setPlayer(striker);
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                    announcements.add(announcement);
                }
            }
        }
        
        team.setAnnouncements(announcements);
        
        // Expected: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should count 8 goal-scoring announcements from 4 forwards", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        // Announce scores for Strikers
        
        List<Player> firstEleven = new ArrayList<>();
        
        // Add 2 forwards (strikers) with goals
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
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
        
        // Add 7 other players
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(27);
            player.setNumber(i);
            player.setPosition(Position.DEFENSE);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(new ArrayList<>(firstEleven));
        
        // Create announcements: 2 strikers × 3 goals each = 6 announcements
        List<Announcement> announcements = new ArrayList<>();
        for (Player striker : firstEleven) {
            if (striker.getPosition() == Position.FORWARD) {
                for (int i = 0; i < 3; i++) {
                    Announcement announcement = new Announcement();
                    announcement.setPlayer(striker);
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                    announcements.add(announcement);
                }
            }
        }
        
        team.setAnnouncements(announcements);
        
        // Expected: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should count 6 goal-scoring announcements from 2 forwards", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        
        List<Player> firstEleven = new ArrayList<>();
        
        // Add 3 forwards (strikers) with no goals
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
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(new ArrayList<>(firstEleven));
        
        // No goal announcements - only save announcements or other types
        List<Announcement> announcements = new ArrayList<>();
        
        // Add some save announcements (not score announcements)
        for (int i = 0; i < 5; i++) {
            Announcement announcement = new Announcement();
            announcement.setPlayer(firstEleven.get(0)); // Use first player
            announcement.setType(AnnouncementType.SAVE); // Save, not score
            announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            announcements.add(announcement);
        }
        
        team.setAnnouncements(announcements);
        
        // Expected: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should return 0 when there are no goal-scoring announcements", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create a football team named "Turgutlu"
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
        
        // Add 10 other players (non-forwards)
        for (int i = 2; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(26);
            player.setNumber(i);
            player.setPosition(Position.DEFENSE);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(new ArrayList<>(firstEleven));
        
        // Create save announcements for goalkeeper (2 saves)
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Announcement announcement = new Announcement();
            announcement.setPlayer(goalkeeper);
            announcement.setType(AnnouncementType.SAVE); // Save announcements, not score
            announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            announcements.add(announcement);
        }
        
        team.setAnnouncements(announcements);
        
        // Expected: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should return 0 when only save announcements exist", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        
        // First eleven is empty by default from constructor
        // But let's explicitly set it to empty to be safe
        team.setFirstEleven(new ArrayList<>());
        
        // Add some players to spare team or all players list, but not to first eleven
        List<Player> sparePlayers = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Player player = new Player();
            player.setName("SparePlayer" + i);
            player.setAge(25);
            player.setNumber(i);
            player.setPosition(Position.FORWARD);
            sparePlayers.add(player);
        }
        team.setSpareTeam(sparePlayers);
        team.setPlayers(sparePlayers);
        
        // Add some announcements for spare players
        List<Announcement> announcements = new ArrayList<>();
        for (Player player : sparePlayers) {
            Announcement announcement = new Announcement();
            announcement.setPlayer(player);
            announcement.setType(AnnouncementType.SCORE);
            announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            announcements.add(announcement);
        }
        team.setAnnouncements(announcements);
        
        // Expected: Total announcements = 0 (no players in first eleven, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should return 0 when first eleven is empty", 0, result);
    }
}