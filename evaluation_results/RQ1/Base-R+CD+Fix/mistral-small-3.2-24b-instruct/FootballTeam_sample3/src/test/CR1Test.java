import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public void testCase1_AllStrikersWithGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        // Announce scores for each of the 4 Strikers
        
        // Create 4 forwards (strikers)
        List<Player> firstEleven = new ArrayList<>();
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
            player.setAge(27);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD); // Using midfielders as non-forwards
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(new ArrayList<>(firstEleven)); // Also add to players list
        
        // Create announcements: 4 strikers × 2 goals each = 8 announcements
        List<Announcement> announcements = new ArrayList<>();
        for (Player striker : firstEleven) {
            if (striker.getPosition() == Position.FORWARD) {
                // Add 2 score announcements for each striker
                for (int i = 0; i < 2; i++) {
                    Announcement announcement = new Announcement();
                    try {
                        announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                    } catch (Exception e) {
                        announcement.setTime(new Date());
                    }
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(striker);
                    announcements.add(announcement);
                }
            }
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total goal scoring announcements should be 8", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        // Announce scores for Strikers
        
        List<Player> firstEleven = new ArrayList<>();
        
        // Add 2 forwards (strikers)
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
        
        // Add 7 other players (defense/goalkeeper)
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(28);
            player.setNumber(i);
            player.setPosition(i == 11 ? Position.GOALKEEPER : Position.DEFENSE);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(new ArrayList<>(firstEleven));
        
        // Create announcements: 2 strikers × 3 goals each = 6 announcements
        List<Announcement> announcements = new ArrayList<>();
        for (Player striker : firstEleven) {
            if (striker.getPosition() == Position.FORWARD) {
                // Add 3 score announcements for each striker
                for (int i = 0; i < 3; i++) {
                    Announcement announcement = new Announcement();
                    try {
                        announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                    } catch (Exception e) {
                        announcement.setTime(new Date());
                    }
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(striker);
                    announcements.add(announcement);
                }
            }
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total goal scoring announcements should be 6", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        
        List<Player> firstEleven = new ArrayList<>();
        
        // Add 3 forwards (strikers)
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
            player.setAge(27);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(new ArrayList<>(firstEleven));
        team.setAnnouncements(new ArrayList<>()); // Empty announcements list (no goals)
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total goal scoring announcements should be 0 when no goals scored", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        
        List<Player> firstEleven = new ArrayList<>();
        
        // Add 11 players including 1 goalkeeper and various positions
        for (int i = 1; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(25 + i);
            player.setNumber(i);
            
            if (i == 1) {
                player.setPosition(Position.GOALKEEPER);
            } else if (i <= 4) {
                player.setPosition(Position.DEFENSE);
            } else if (i <= 8) {
                player.setPosition(Position.MIDFIELD);
            } else {
                player.setPosition(Position.FORWARD);
            }
            
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(new ArrayList<>(firstEleven));
        
        // Create save announcements for goalkeeper (not score announcements)
        List<Announcement> announcements = new ArrayList<>();
        Player goalkeeper = firstEleven.get(0); // First player is goalkeeper
        
        // Add 2 save announcements for goalkeeper
        for (int i = 0; i < 2; i++) {
            Announcement announcement = new Announcement();
            try {
                announcement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            } catch (Exception e) {
                announcement.setTime(new Date());
            }
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            announcements.add(announcement);
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total goal scoring announcements should be 0 when only save announcements exist", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // SetUp: Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        
        team.setFirstEleven(new ArrayList<>()); // Empty first eleven
        team.setPlayers(new ArrayList<>()); // Empty players list
        team.setAnnouncements(new ArrayList<>()); // Empty announcements list
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total goal scoring announcements should be 0 when no players in first eleven", 0, result);
    }
}