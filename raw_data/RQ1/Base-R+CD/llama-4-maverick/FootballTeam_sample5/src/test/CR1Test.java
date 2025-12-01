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
            player.setPosition(Position.DEFENSE); // Not forwards
            player.setNumber(i + 1);
            firstEleven.add(player);
        }
        
        // Execute the method under test
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Verify the result: 4 players × 2 goals = 8
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
        
        // Add 2 midfielders with no goals
        for (int i = 2; i < 4; i++) {
            Player player = new Player();
            player.setName("Midfielder" + (i - 1));
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i + 1);
            firstEleven.add(player);
        }
        
        // Add 7 more players with no goals
        for (int i = 4; i < 11; i++) {
            Player player = new Player();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.DEFENSE);
            player.setNumber(i + 1);
            firstEleven.add(player);
        }
        
        // Execute the method under test
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Verify the result: (2 players × 3 goals) = 6
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
                player.setPosition(Position.DEFENSE); // Others are not forwards
            }
            player.setNumber(i + 1);
            firstEleven.add(player);
        }
        
        // Execute the method under test
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Verify the result: 0 (no goals scored)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_onlyGoalkeeperWithSaveAnnouncements() {
        // Create 1 goalkeeper with 2 save announcements
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        firstEleven.add(goalkeeper);
        
        // Add save announcements (not score announcements)
        for (int i = 0; i < 2; i++) {
            Announcement announcement = new Announcement();
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            announcement.setTime(new Date());
            announcements.add(announcement);
        }
        
        // Add 10 more players with no goals
        for (int i = 1; i < 11; i++) {
            Player player = new Player();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.DEFENSE);
            player.setNumber(i + 1);
            firstEleven.add(player);
        }
        
        // Execute the method under test
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Verify the result: 0 (only saves recorded, no score announcements)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_noPlayersInFirstEleven() {
        // Don't add any players to first eleven (already empty from setup)
        
        // Execute the method under test
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Verify the result: 0 (no players, no announcements)
        assertEquals(0, result);
    }
}