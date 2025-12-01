import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        team = new FootballTeam();
        
        // Create 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<>();
        List<Announcement> announcements = new ArrayList<>();
        
        // Create 4 forwards (strikers)
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            firstEleven.add(striker);
            
            // Add 2 score announcements for each striker
            for (int j = 0; j < 2; j++) {
                Announcement scoreAnnouncement = new Announcement();
                scoreAnnouncement.setType(AnnouncementType.SCORE);
                scoreAnnouncement.setPlayer(striker);
                scoreAnnouncement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                announcements.add(scoreAnnouncement);
            }
        }
        
        // Create 7 other players (non-forwards)
        for (int i = 5; i <= 11; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setPosition(Position.MIDFIELD); // Not forward
            otherPlayer.setNumber(i);
            otherPlayer.setAge(26);
            firstEleven.add(otherPlayer);
        }
        
        team.setFirstEleven(firstEleven);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should count 8 goal-scoring announcements from 4 forwards with 2 goals each", 8, result);
    }

    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Create 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        List<Player> firstEleven = new ArrayList<>();
        List<Announcement> announcements = new ArrayList<>();
        
        // Create 2 forwards (strikers) with 3 goals each
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            firstEleven.add(striker);
            
            // Add 3 score announcements for each striker
            for (int j = 0; j < 3; j++) {
                Announcement scoreAnnouncement = new Announcement();
                scoreAnnouncement.setType(AnnouncementType.SCORE);
                scoreAnnouncement.setPlayer(striker);
                scoreAnnouncement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                announcements.add(scoreAnnouncement);
            }
        }
        
        // Create 2 midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setNumber(i);
            midfielder.setAge(26);
            firstEleven.add(midfielder);
        }
        
        // Create 7 other players
        for (int i = 5; i <= 11; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setPosition(Position.DEFENSE);
            otherPlayer.setNumber(i);
            otherPlayer.setAge(27);
            firstEleven.add(otherPlayer);
        }
        
        team.setFirstEleven(firstEleven);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should count 6 goal-scoring announcements from 2 forwards with 3 goals each", 6, result);
    }

    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Create 11 players: All players (including 3 Strikers) scored 0 goals
        List<Player> firstEleven = new ArrayList<>();
        List<Announcement> announcements = new ArrayList<>();
        
        // Create 3 forwards with no goals
        for (int i = 1; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            firstEleven.add(striker);
        }
        
        // Create 8 other players
        for (int i = 4; i <= 11; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setPosition(Position.DEFENSE);
            otherPlayer.setNumber(i);
            otherPlayer.setAge(26);
            firstEleven.add(otherPlayer);
        }
        
        team.setFirstEleven(firstEleven);
        team.setAnnouncements(announcements); // Empty announcements list
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should return 0 when there are no goal-scoring announcements", 0, result);
    }

    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        List<Player> firstEleven = new ArrayList<>();
        List<Announcement> announcements = new ArrayList<>();
        
        // Create 1 goalkeeper with 2 save announcements
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        goalkeeper.setAge(28);
        firstEleven.add(goalkeeper);
        
        // Add 2 save announcements for the goalkeeper
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            announcements.add(saveAnnouncement);
        }
        
        // Create 10 other players
        for (int i = 2; i <= 11; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setPosition(Position.DEFENSE);
            otherPlayer.setNumber(i);
            otherPlayer.setAge(25);
            firstEleven.add(otherPlayer);
        }
        
        team.setFirstEleven(firstEleven);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should return 0 when there are only save announcements, no score announcements", 0, result);
    }

    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Do not add any players to the first eleven
        List<Player> firstEleven = new ArrayList<>();
        List<Announcement> announcements = new ArrayList<>();
        
        // Create some announcements but no players in first eleven
        Player striker = new Player();
        striker.setName("Striker");
        striker.setPosition(Position.FORWARD);
        striker.setNumber(1);
        striker.setAge(25);
        
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(striker);
        scoreAnnouncement.setTime(dateFormat.parse("2024-01-01 10:00:00"));
        announcements.add(scoreAnnouncement);
        
        team.setFirstEleven(firstEleven); // Empty first eleven
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should return 0 when first eleven is empty", 0, result);
    }
}