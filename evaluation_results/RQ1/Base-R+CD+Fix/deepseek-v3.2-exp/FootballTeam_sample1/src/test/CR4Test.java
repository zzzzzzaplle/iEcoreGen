import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        // Initialize football team before each test
        footballTeam = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // Test Case 1: Calculate Goal Saving Announcements for a Goalkeeper with No Saves
        
        // SetUp: Create a goalkeeper with no save recorded
        Player goalkeeper = new Player();
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // No announcements added, so no saves recorded
        
        // Execute method and verify expected output
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals("Total goal-saving announcements should be 0 when goalkeeper has no saves", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // Test Case 2: Calculate Goal Saving Announcements for a Goalkeeper with One Save
        
        // SetUp: Create a goalkeeper with one save
        Player goalkeeper = new Player();
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create one SAVE announcement for the goalkeeper
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setTime(new Date());
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(saveAnnouncement);
        footballTeam.setAnnouncements(announcements);
        
        // Execute method and verify expected output
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals("Total goal-saving announcements should be 1 when goalkeeper has one save", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // Test Case 3: Calculate Goal Saving Announcements for a Goalkeeper with Multiple Saves
        
        // SetUp: Create a goalkeeper with multiple saves
        Player goalkeeper = new Player();
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create three SAVE announcements for the goalkeeper
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setTime(new Date());
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            announcements.add(saveAnnouncement);
        }
        footballTeam.setAnnouncements(announcements);
        
        // Execute method and verify expected output
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals("Total goal-saving announcements should be 3 when goalkeeper has three saves", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // Test Case 4: Calculate Goal Saving Announcements for a Goalkeeper with Consecutive Saves
        
        // SetUp: Create a goalkeeper with consecutive saves
        Player goalkeeper = new Player();
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create five SAVE announcements for the goalkeeper (all in second half)
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setTime(new Date());
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            announcements.add(saveAnnouncement);
        }
        footballTeam.setAnnouncements(announcements);
        
        // Execute method and verify expected output
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals("Total goal-saving announcements should be 5 when goalkeeper has five consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // Test Case 5: Calculate Goal Saving Announcements for a Goalkeeper with Invalid Entries
        
        // SetUp: Create a goalkeeper with mixed announcements
        Player goalkeeper = new Player();
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create announcements: 2 SAVE and 1 SCORE (which should not be counted)
        List<Announcement> announcements = new ArrayList<>();
        
        // Add two SAVE announcements
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setTime(new Date());
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            announcements.add(saveAnnouncement);
        }
        
        // Add one SCORE announcement (should not be counted)
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setTime(new Date());
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(goalkeeper);
        announcements.add(scoreAnnouncement);
        
        footballTeam.setAnnouncements(announcements);
        
        // Execute method and verify expected output
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals("Total goal-saving announcements should be 2 when goalkeeper has two saves and one goal", 2, result);
    }
}