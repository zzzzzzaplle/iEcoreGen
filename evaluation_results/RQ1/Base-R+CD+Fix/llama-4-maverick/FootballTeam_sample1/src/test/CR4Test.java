import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private FootballTeam footballTeam;
    private Player goalkeeper;
    
    @Before
    public void setUp() {
        footballTeam = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no save recorded
        goalkeeper = new Player();
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // No announcements added
        
        // Calculate goal-saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper with one save
        goalkeeper = new Player();
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create one save announcement for the goalkeeper
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setTime(new Date());
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(saveAnnouncement);
        footballTeam.setAnnouncements(announcements);
        
        // Calculate goal-saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        goalkeeper = new Player();
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create three save announcements for the goalkeeper
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setTime(new Date());
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            announcements.add(saveAnnouncement);
        }
        footballTeam.setAnnouncements(announcements);
        
        // Calculate goal-saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        goalkeeper = new Player();
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create five consecutive save announcements for the goalkeeper
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setTime(new Date());
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            announcements.add(saveAnnouncement);
        }
        footballTeam.setAnnouncements(announcements);
        
        // Calculate goal-saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed announcements
        goalkeeper = new Player();
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create announcements: 2 saves and 1 goal (should not be counted)
        List<Announcement> announcements = new ArrayList<>();
        
        // Add 2 save announcements
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setTime(new Date());
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            announcements.add(saveAnnouncement);
        }
        
        // Add 1 goal announcement (should not be counted)
        Announcement goalAnnouncement = new Announcement();
        goalAnnouncement.setTime(new Date());
        goalAnnouncement.setType(AnnouncementType.SCORE);
        goalAnnouncement.setPlayer(goalkeeper);
        announcements.add(goalAnnouncement);
        
        footballTeam.setAnnouncements(announcements);
        
        // Calculate goal-saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 2
        assertEquals(2, result);
    }
}