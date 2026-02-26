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
            striker.setNumber(i + 1);
            firstEleven.add(striker);
            
            // Each striker scores 2 goals
            for (int j = 0; j < 2; j++) {
                Announcement announcement = new Announcement();
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                announcement.setTime(new Date());
                announcements.add(announcement);
            }
        }
        
        // Add 7 more players with no goals
        for (int i = 4; i < 11; i++) {
            Player player = new Player();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.DEFENSE); // Not forward
            player.setNumber(i + 1);
            firstEleven.add(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 4 strikers × 2 goals = 8
        assertEquals(8, result);
    }

    @Test
    public void testCase2_mixedPlayersScoring() {
        // Create 2 strikers who scored 3 goals each
        for (int i = 0; i < 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + (i + 1));
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i + 1);
            firstEleven.add(striker);
            
            // Each striker scores 3 goals
            for (int j = 0; j < 3; j++) {
                Announcement announcement = new Announcement();
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                announcement.setTime(new Date());
                announcements.add(announcement);
            }
        }
        
        // Add 2 midfielders
        for (int i = 2; i < 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + (i - 1));
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setNumber(i + 1);
            firstEleven.add(midfielder);
        }
        
        // Add 7 more players with no goals
        for (int i = 4; i < 11; i++) {
            Player player = new Player();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.DEFENSE);
            player.setNumber(i + 1);
            firstEleven.add(player);
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: (2 strikers × 3 goals) = 6
        assertEquals(6, result);
    }

    @Test
    public void testCase3_firstElevenWithNoGoals() {
        // Add 11 players including 3 strikers but none scored goals
        for (int i = 0; i < 11; i++) {
            Player player = new Player();
            player.setName("Player" + (i + 1));
            if (i < 3) {
                player.setPosition(Position.FORWARD); // 3 strikers
            } else {
                player.setPosition(Position.DEFENSE); // Others
            }
            player.setNumber(i + 1);
            firstEleven.add(player);
        }
        
        // No announcements added
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 0 (no goals scored)
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
            } else {
                player.setPosition(Position.DEFENSE); // Others
            }
            player.setNumber(i + 1);
            firstEleven.add(player);
            
            // Add save announcements for goalkeeper
            if (i == 0) {
                for (int j = 0; j < 2; j++) {
                    Announcement announcement = new Announcement();
                    announcement.setType(AnnouncementType.SAVE);
                    announcement.setPlayer(player);
                    announcement.setTime(new Date());
                    announcements.add(announcement);
                }
            }
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 0 (only saves recorded, no score announcements)
        assertEquals(0, result);
    }

    @Test
    public void testCase5_noPlayersInFirstEleven() {
        // Don't add any players to first eleven (list remains empty)
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 0 (no players, no announcements)
        assertEquals(0, result);
    }
}