import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private FootballTeam team;
    private Date currentTime;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        currentTime = new Date();
    }
    
    @Test
    public void testCase1_allStrikersWithGoals() {
        // Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 4 strikers who scored 2 goals each
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            firstEleven.add(striker);
            
            // Each striker scores 2 goals
            for (int j = 0; j < 2; j++) {
                Announcement announcement = new Announcement();
                announcement.setTime(currentTime);
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                team.getAnnouncements().add(announcement);
            }
        }
        
        // Add 7 more players with no goals
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setNumber(i);
            // Set non-forward positions for these players
            if (i <= 6) {
                player.setPosition(Position.MIDFIELD);
            } else if (i <= 8) {
                player.setPosition(Position.DEFENSE);
            } else {
                player.setPosition(Position.GOALKEEPER);
            }
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_mixedPlayersScoring() {
        // Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 2 strikers who scored 3 goals each
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            firstEleven.add(striker);
            
            // Each striker scores 3 goals
            for (int j = 0; j < 3; j++) {
                Announcement announcement = new Announcement();
                announcement.setTime(currentTime);
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                team.getAnnouncements().add(announcement);
            }
        }
        
        // Add 2 midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setNumber(i);
            firstEleven.add(midfielder);
        }
        
        // Add 7 more players with no goals
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setNumber(i);
            // Set various positions for these players
            if (i <= 6) {
                player.setPosition(Position.MIDFIELD);
            } else if (i <= 8) {
                player.setPosition(Position.DEFENSE);
            } else {
                player.setPosition(Position.GOALKEEPER);
            }
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_firstElevenWithNoGoals() {
        // Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 3 strikers with no goals
        for (int i = 1; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            firstEleven.add(striker);
        }
        
        // Add 8 more players with no goals
        for (int i = 4; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setNumber(i);
            // Set various positions for these players
            if (i <= 5) {
                player.setPosition(Position.MIDFIELD);
            } else if (i <= 7) {
                player.setPosition(Position.DEFENSE);
            } else {
                player.setPosition(Position.GOALKEEPER);
            }
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_onlyGoalkeeperWithSaveAnnouncements() {
        // Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        List<Player> firstEleven = new ArrayList<>();
        
        // Create 10 non-goalkeeper players
        for (int i = 1; i <= 10; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setNumber(i);
            // Set various positions for these players
            if (i <= 4) {
                player.setPosition(Position.FORWARD);
            } else if (i <= 7) {
                player.setPosition(Position.MIDFIELD);
            } else {
                player.setPosition(Position.DEFENSE);
            }
            firstEleven.add(player);
        }
        
        // Create 1 goalkeeper who saved 2 goals
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(11);
        firstEleven.add(goalkeeper);
        
        // Add save announcements for the goalkeeper
        for (int j = 0; j < 2; j++) {
            Announcement announcement = new Announcement();
            announcement.setTime(currentTime);
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            team.getAnnouncements().add(announcement);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_noPlayersInFirstEleven() {
        // Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        // The firstEleven list will be empty
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}