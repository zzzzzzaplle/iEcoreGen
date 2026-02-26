import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
        // Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        Player striker1 = createPlayer("Striker1", 25, 9, Position.FORWARD);
        Player striker2 = createPlayer("Striker2", 27, 11, Position.FORWARD);
        Player striker3 = createPlayer("Striker3", 24, 7, Position.FORWARD);
        Player striker4 = createPlayer("Striker4", 26, 10, Position.FORWARD);
        
        // Add 7 other players with no goals
        Player midfielder1 = createPlayer("Midfielder1", 25, 8, Position.MIDFIELD);
        Player midfielder2 = createPlayer("Midfielder2", 26, 6, Position.MIDFIELD);
        Player defender1 = createPlayer("Defender1", 28, 4, Position.DEFENSE);
        Player defender2 = createPlayer("Defender2", 29, 5, Position.DEFENSE);
        Player defender3 = createPlayer("Defender3", 30, 3, Position.DEFENSE);
        Player defender4 = createPlayer("Defender4", 27, 2, Position.DEFENSE);
        Player goalkeeper = createPlayer("Goalkeeper", 31, 1, Position.GOALKEEPER);
        
        // Add all players to the team and first eleven
        ArrayList<Player> allPlayers = new ArrayList<>();
        allPlayers.add(striker1);
        allPlayers.add(striker2);
        allPlayers.add(striker3);
        allPlayers.add(striker4);
        allPlayers.add(midfielder1);
        allPlayers.add(midfielder2);
        allPlayers.add(defender1);
        allPlayers.add(defender2);
        allPlayers.add(defender3);
        allPlayers.add(defender4);
        allPlayers.add(goalkeeper);
        
        team.setPlayers(allPlayers);
        team.setFirstEleven(allPlayers); // All players in first eleven
        
        // Announce scores for each of the 4 Strikers (2 goals each)
        Date time = dateFormat.parse("2023-01-01 15:00:00");
        for (int i = 0; i < 2; i++) { // 2 goals per striker
            team.getAnnouncements().add(createAnnouncement(time, AnnouncementType.SCORE, striker1));
            team.getAnnouncements().add(createAnnouncement(time, AnnouncementType.SCORE, striker2));
            team.getAnnouncements().add(createAnnouncement(time, AnnouncementType.SCORE, striker3));
            team.getAnnouncements().add(createAnnouncement(time, AnnouncementType.SCORE, striker4));
        }
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_mixedPlayersScoring() throws ParseException {
        // Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        Player striker1 = createPlayer("Striker1", 25, 9, Position.FORWARD);
        Player striker2 = createPlayer("Striker2", 27, 11, Position.FORWARD);
        Player midfielder1 = createPlayer("Midfielder1", 25, 8, Position.MIDFIELD);
        Player midfielder2 = createPlayer("Midfielder2", 26, 6, Position.MIDFIELD);
        Player defender1 = createPlayer("Defender1", 28, 4, Position.DEFENSE);
        Player defender2 = createPlayer("Defender2", 29, 5, Position.DEFENSE);
        Player defender3 = createPlayer("Defender3", 30, 3, Position.DEFENSE);
        Player defender4 = createPlayer("Defender4", 27, 2, Position.DEFENSE);
        Player defender5 = createPlayer("Defender5", 28, 12, Position.DEFENSE);
        Player defender6 = createPlayer("Defender6", 29, 13, Position.DEFENSE);
        Player goalkeeper = createPlayer("Goalkeeper", 31, 1, Position.GOALKEEPER);
        
        // Add all players to the team and first eleven
        ArrayList<Player> allPlayers = new ArrayList<>();
        allPlayers.add(striker1);
        allPlayers.add(striker2);
        allPlayers.add(midfielder1);
        allPlayers.add(midfielder2);
        allPlayers.add(defender1);
        allPlayers.add(defender2);
        allPlayers.add(defender3);
        allPlayers.add(defender4);
        allPlayers.add(defender5);
        allPlayers.add(defender6);
        allPlayers.add(goalkeeper);
        
        team.setPlayers(allPlayers);
        team.setFirstEleven(allPlayers); // All players in first eleven
        
        // Announce scores for Strikers (3 goals each)
        Date time = dateFormat.parse("2023-01-01 15:00:00");
        for (int i = 0; i < 3; i++) { // 3 goals per striker
            team.getAnnouncements().add(createAnnouncement(time, AnnouncementType.SCORE, striker1));
            team.getAnnouncements().add(createAnnouncement(time, AnnouncementType.SCORE, striker2));
        }
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_firstElevenWithNoGoals() {
        // Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        Player striker1 = createPlayer("Striker1", 25, 9, Position.FORWARD);
        Player striker2 = createPlayer("Striker2", 27, 11, Position.FORWARD);
        Player striker3 = createPlayer("Striker3", 24, 7, Position.FORWARD);
        Player midfielder1 = createPlayer("Midfielder1", 25, 8, Position.MIDFIELD);
        Player midfielder2 = createPlayer("Midfielder2", 26, 6, Position.MIDFIELD);
        Player defender1 = createPlayer("Defender1", 28, 4, Position.DEFENSE);
        Player defender2 = createPlayer("Defender2", 29, 5, Position.DEFENSE);
        Player defender3 = createPlayer("Defender3", 30, 3, Position.DEFENSE);
        Player defender4 = createPlayer("Defender4", 27, 2, Position.DEFENSE);
        Player goalkeeper1 = createPlayer("Goalkeeper1", 31, 1, Position.GOALKEEPER);
        Player goalkeeper2 = createPlayer("Goalkeeper2", 32, 16, Position.GOALKEEPER);
        
        // Add all players to the team and first eleven
        ArrayList<Player> allPlayers = new ArrayList<>();
        allPlayers.add(striker1);
        allPlayers.add(striker2);
        allPlayers.add(striker3);
        allPlayers.add(midfielder1);
        allPlayers.add(midfielder2);
        allPlayers.add(defender1);
        allPlayers.add(defender2);
        allPlayers.add(defender3);
        allPlayers.add(defender4);
        allPlayers.add(goalkeeper1);
        allPlayers.add(goalkeeper2);
        
        team.setPlayers(allPlayers);
        team.setFirstEleven(allPlayers); // All players in first eleven
        
        // No announcements added
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_onlyGoalkeeperWithSaveAnnouncements() throws ParseException {
        // Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        Player goalkeeper = createPlayer("Goalkeeper", 31, 1, Position.GOALKEEPER);
        Player defender1 = createPlayer("Defender1", 28, 4, Position.DEFENSE);
        Player defender2 = createPlayer("Defender2", 29, 5, Position.DEFENSE);
        Player defender3 = createPlayer("Defender3", 30, 3, Position.DEFENSE);
        Player defender4 = createPlayer("Defender4", 27, 2, Position.DEFENSE);
        Player midfielder1 = createPlayer("Midfielder1", 25, 8, Position.MIDFIELD);
        Player midfielder2 = createPlayer("Midfielder2", 26, 6, Position.MIDFIELD);
        Player midfielder3 = createPlayer("Midfielder3", 24, 12, Position.MIDFIELD);
        Player midfielder4 = createPlayer("Midfielder4", 27, 13, Position.MIDFIELD);
        Player midfielder5 = createPlayer("Midfielder5", 28, 14, Position.MIDFIELD);
        Player midfielder6 = createPlayer("Midfielder6", 29, 15, Position.MIDFIELD);
        
        // Add all players to the team and first eleven
        ArrayList<Player> allPlayers = new ArrayList<>();
        allPlayers.add(goalkeeper);
        allPlayers.add(defender1);
        allPlayers.add(defender2);
        allPlayers.add(defender3);
        allPlayers.add(defender4);
        allPlayers.add(midfielder1);
        allPlayers.add(midfielder2);
        allPlayers.add(midfielder3);
        allPlayers.add(midfielder4);
        allPlayers.add(midfielder5);
        allPlayers.add(midfielder6);
        
        team.setPlayers(allPlayers);
        team.setFirstEleven(allPlayers); // All players in first eleven
        
        // Add save announcements for the goalkeeper
        Date time = dateFormat.parse("2023-01-01 15:00:00");
        team.getAnnouncements().add(createAnnouncement(time, AnnouncementType.SAVE, goalkeeper));
        team.getAnnouncements().add(createAnnouncement(time, AnnouncementType.SAVE, goalkeeper));
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_noPlayersInFirstEleven() {
        // Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        Player striker = createPlayer("Striker", 25, 9, Position.FORWARD);
        
        // Add player to overall players list but not to first eleven
        ArrayList<Player> allPlayers = new ArrayList<>();
        allPlayers.add(striker);
        team.setPlayers(allPlayers);
        
        // Keep first eleven empty
        team.setFirstEleven(new ArrayList<>());
        
        // Add score announcement for the striker
        Date time = new Date();
        team.getAnnouncements().add(createAnnouncement(time, AnnouncementType.SCORE, striker));
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    // Helper methods to create objects
    private Player createPlayer(String name, int age, int number, Position position) {
        Player player = new Player();
        player.setName(name);
        player.setAge(age);
        player.setNumber(number);
        player.setPosition(position);
        return player;
    }
    
    private Announcement createAnnouncement(Date time, AnnouncementType type, Player player) {
        Announcement announcement = new Announcement();
        announcement.setTime(time);
        announcement.setType(type);
        announcement.setPlayer(player);
        return announcement;
    }
}