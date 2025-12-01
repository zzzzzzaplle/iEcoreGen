import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public void testCase1_allStrikersWithGoals() {
        // Create 4 strikers who scored 2 goals each
        for (int i = 0; i < 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + (i + 1));
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
            
            // Create 2 score announcements for each striker
            for (int j = 0; j < 2; j++) {
                Announcement announcement = new Announcement();
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                announcements.add(announcement);
            }
        }
        
        // Add 7 more players with no goals
        for (int i = 0; i < 7; i++) {
            Player player = new Player();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.DEFENSE); // Not forward
            firstEleven.add(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 4 players × 2 goals = 8
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_mixedPlayersScoring() {
        // Create 2 strikers who scored 3 goals each
        for (int i = 0; i < 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + (i + 1));
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
            
            // Create 3 score announcements for each striker
            for (int j = 0; j < 3; j++) {
                Announcement announcement = new Announcement();
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                announcements.add(announcement);
            }
        }
        
        // Add 2 midfielders
        for (int i = 0; i < 2; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + (i + 1));
            midfielder.setPosition(Position.MIDFIELD);
            firstEleven.add(midfielder);
        }
        
        // Add 7 more players with no goals
        for (int i = 0; i < 7; i++) {
            Player player = new Player();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.DEFENSE);
            firstEleven.add(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: (2 players × 3 goals) = 6
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_firstElevenWithNoGoals() {
        // Add 11 players including 3 strikers but no goals
        for (int i = 0; i < 11; i++) {
            Player player = new Player();
            player.setName("Player" + (i + 1));
            if (i < 3) {
                player.setPosition(Position.FORWARD); // 3 strikers
            } else {
                player.setPosition(Position.DEFENSE); // Others
            }
            firstEleven.add(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 0 (no announcements)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_onlyGoalkeeperWithSaveAnnouncements() {
        // Add 11 players including 1 goalkeeper
        for (int i = 0; i < 11; i++) {
            Player player = new Player();
            player.setName("Player" + (i + 1));
            if (i == 0) {
                player.setPosition(Position.GOALKEEPER); // 1 goalkeeper
                
                // Add 2 save announcements for goalkeeper
                for (int j = 0; j < 2; j++) {
                    Announcement announcement = new Announcement();
                    announcement.setType(AnnouncementType.SAVE);
                    announcement.setPlayer(player);
                    announcements.add(announcement);
                }
            } else {
                player.setPosition(Position.DEFENSE); // Others
            }
            firstEleven.add(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 0 (only saves recorded, no score announcements)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_noPlayersInFirstEleven() {
        // Don't add any players to first eleven (already empty)
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 0 (no players, no announcements)
        assertEquals(0, result);
    }
}