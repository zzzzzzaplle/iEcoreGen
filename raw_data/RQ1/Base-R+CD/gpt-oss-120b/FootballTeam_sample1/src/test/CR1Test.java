import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR1Test {
    
    private FootballTeam team;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_allStrikersWithGoals() throws ParseException {
        // Create 11 players: 4 strikers with 2 goals each, 7 players with no goals
        Player striker1 = createPlayer("Striker1", 25, 10, Position.FORWARD);
        Player striker2 = createPlayer("Striker2", 26, 9, Position.FORWARD);
        Player striker3 = createPlayer("Striker3", 24, 11, Position.FORWARD);
        Player striker4 = createPlayer("Striker4", 27, 8, Position.FORWARD);
        
        // Add 7 more players with no goals
        Player player5 = createPlayer("Player5", 25, 5, Position.DEFENSE);
        Player player6 = createPlayer("Player6", 26, 6, Position.DEFENSE);
        Player player7 = createPlayer("Player7", 24, 7, Position.DEFENSE);
        Player player8 = createPlayer("Player8", 27, 12, Position.MIDFIELD);
        Player player9 = createPlayer("Player9", 25, 13, Position.MIDFIELD);
        Player player10 = createPlayer("Player10", 26, 14, Position.MIDFIELD);
        Player player11 = createPlayer("Player11", 24, 15, Position.GOALKEEPER);
        
        // Add all players to first eleven
        team.addToFirstEleven(striker1);
        team.addToFirstEleven(striker2);
        team.addToFirstEleven(striker3);
        team.addToFirstEleven(striker4);
        team.addToFirstEleven(player5);
        team.addToFirstEleven(player6);
        team.addToFirstEleven(player7);
        team.addToFirstEleven(player8);
        team.addToFirstEleven(player9);
        team.addToFirstEleven(player10);
        team.addToFirstEleven(player11);
        
        // Create score announcements for each striker (2 goals each)
        Date time = dateFormat.parse("2023-01-01 15:00:00");
        for (int i = 0; i < 2; i++) {
            team.addAnnouncement(createAnnouncement(time, AnnouncementType.SCORE, striker1));
            team.addAnnouncement(createAnnouncement(time, AnnouncementType.SCORE, striker2));
            team.addAnnouncement(createAnnouncement(time, AnnouncementType.SCORE, striker3));
            team.addAnnouncement(createAnnouncement(time, AnnouncementType.SCORE, striker4));
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 4 strikers * 2 goals = 8
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_mixedPlayersScoring() throws ParseException {
        // Create 11 players: 2 strikers with 3 goals each, 2 midfielders, 7 players with no goals
        Player striker1 = createPlayer("Striker1", 25, 10, Position.FORWARD);
        Player striker2 = createPlayer("Striker2", 26, 9, Position.FORWARD);
        Player midfielder1 = createPlayer("Midfielder1", 24, 8, Position.MIDFIELD);
        Player midfielder2 = createPlayer("Midfielder2", 27, 7, Position.MIDFIELD);
        
        // Add 7 more players with no goals
        Player player5 = createPlayer("Player5", 25, 5, Position.DEFENSE);
        Player player6 = createPlayer("Player6", 26, 6, Position.DEFENSE);
        Player player7 = createPlayer("Player7", 24, 11, Position.DEFENSE);
        Player player8 = createPlayer("Player8", 27, 12, Position.MIDFIELD);
        Player player9 = createPlayer("Player9", 25, 13, Position.MIDFIELD);
        Player player10 = createPlayer("Player10", 26, 14, Position.MIDFIELD);
        Player player11 = createPlayer("Player11", 24, 15, Position.GOALKEEPER);
        
        // Add all players to first eleven
        team.addToFirstEleven(striker1);
        team.addToFirstEleven(striker2);
        team.addToFirstEleven(midfielder1);
        team.addToFirstEleven(midfielder2);
        team.addToFirstEleven(player5);
        team.addToFirstEleven(player6);
        team.addToFirstEleven(player7);
        team.addToFirstEleven(player8);
        team.addToFirstEleven(player9);
        team.addToFirstEleven(player10);
        team.addToFirstEleven(player11);
        
        // Create score announcements for strikers (3 goals each)
        Date time = dateFormat.parse("2023-01-01 15:00:00");
        for (int i = 0; i < 3; i++) {
            team.addAnnouncement(createAnnouncement(time, AnnouncementType.SCORE, striker1));
            team.addAnnouncement(createAnnouncement(time, AnnouncementType.SCORE, striker2));
        }
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 2 strikers * 3 goals = 6
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_firstElevenWithNoGoals() throws ParseException {
        // Create 11 players including 3 strikers with no goals
        Player striker1 = createPlayer("Striker1", 25, 10, Position.FORWARD);
        Player striker2 = createPlayer("Striker2", 26, 9, Position.FORWARD);
        Player striker3 = createPlayer("Striker3", 24, 11, Position.FORWARD);
        
        // Add 8 more players with no goals
        Player player4 = createPlayer("Player4", 27, 5, Position.DEFENSE);
        Player player5 = createPlayer("Player5", 25, 6, Position.DEFENSE);
        Player player6 = createPlayer("Player6", 26, 7, Position.DEFENSE);
        Player player7 = createPlayer("Player7", 24, 8, Position.MIDFIELD);
        Player player8 = createPlayer("Player8", 27, 12, Position.MIDFIELD);
        Player player9 = createPlayer("Player9", 25, 13, Position.MIDFIELD);
        Player player10 = createPlayer("Player10", 26, 14, Position.MIDFIELD);
        Player player11 = createPlayer("Player11", 24, 15, Position.GOALKEEPER);
        
        // Add all players to first eleven
        team.addToFirstEleven(striker1);
        team.addToFirstEleven(striker2);
        team.addToFirstEleven(striker3);
        team.addToFirstEleven(player4);
        team.addToFirstEleven(player5);
        team.addToFirstEleven(player6);
        team.addToFirstEleven(player7);
        team.addToFirstEleven(player8);
        team.addToFirstEleven(player9);
        team.addToFirstEleven(player10);
        team.addToFirstEleven(player11);
        
        // No score announcements added
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 0 (no goals scored)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_onlyGoalkeeperWithSaveAnnouncements() throws ParseException {
        // Create 11 players including 1 goalkeeper with save announcements
        Player goalkeeper = createPlayer("Goalkeeper", 28, 1, Position.GOALKEEPER);
        
        // Add 10 more players
        Player player2 = createPlayer("Player2", 25, 2, Position.DEFENSE);
        Player player3 = createPlayer("Player3", 26, 3, Position.DEFENSE);
        Player player4 = createPlayer("Player4", 24, 4, Position.DEFENSE);
        Player player5 = createPlayer("Player5", 27, 5, Position.DEFENSE);
        Player player6 = createPlayer("Player6", 25, 6, Position.MIDFIELD);
        Player player7 = createPlayer("Player7", 26, 7, Position.MIDFIELD);
        Player player8 = createPlayer("Player8", 24, 8, Position.MIDFIELD);
        Player player9 = createPlayer("Player9", 27, 9, Position.MIDFIELD);
        Player player10 = createPlayer("Player10", 25, 10, Position.FORWARD);
        Player player11 = createPlayer("Player11", 26, 11, Position.FORWARD);
        
        // Add all players to first eleven
        team.addToFirstEleven(goalkeeper);
        team.addToFirstEleven(player2);
        team.addToFirstEleven(player3);
        team.addToFirstEleven(player4);
        team.addToFirstEleven(player5);
        team.addToFirstEleven(player6);
        team.addToFirstEleven(player7);
        team.addToFirstEleven(player8);
        team.addToFirstEleven(player9);
        team.addToFirstEleven(player10);
        team.addToFirstEleven(player11);
        
        // Add save announcements for goalkeeper (no score announcements)
        Date time = dateFormat.parse("2023-01-01 15:00:00");
        team.addAnnouncement(createAnnouncement(time, AnnouncementType.SAVE, goalkeeper));
        team.addAnnouncement(createAnnouncement(time, AnnouncementType.SAVE, goalkeeper));
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 0 (only saves recorded, no score announcements)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_noPlayersInFirstEleven() {
        // Do not add any players to the first eleven
        
        // Calculate total goal scoring announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected: 0 (no players, no announcements)
        assertEquals(0, result);
    }
    
    // Helper method to create a player
    private Player createPlayer(String name, int age, int number, Position position) {
        Player player = new Player();
        player.setName(name);
        player.setAge(age);
        player.setNumber(number);
        player.setPosition(position);
        team.addPlayer(player);
        return player;
    }
    
    // Helper method to create an announcement
    private Announcement createAnnouncement(Date time, AnnouncementType type, Player player) {
        Announcement announcement = new Announcement();
        announcement.setTime(time);
        announcement.setType(type);
        announcement.setPlayer(player);
        team.addAnnouncement(announcement);
        return announcement;
    }
}