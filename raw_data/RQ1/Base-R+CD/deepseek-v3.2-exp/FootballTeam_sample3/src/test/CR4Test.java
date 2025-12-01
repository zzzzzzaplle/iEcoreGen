import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        // Initialize a new FootballTeam before each test
        footballTeam = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // Test Case 1: Calculate Goal Saving Announcements for a Goalkeeper with No Saves
        // Input: Calculate goal-saving announcements for goalkeeper with no save recorded.
        
        // SetUp:
        // 1. Create a football team (done in @Before)
        // 2. Add a goalkeeper with number: 1, name: "John Doe", age: 30.
        Player goalkeeper = new Player();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // 3. The goalkeeper has not made any saves in the match.
        // Announcements list remains empty
        
        // Execute the method under test
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals("Goal-saving announcements should be 0 when goalkeeper has no saves", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // Test Case 2: Calculate Goal Saving Announcements for a Goalkeeper with One Save
        // Input: Calculate goal-saving announcements for goalkeeper who made one save.
        
        // SetUp:
        // 1. Create a football team (done in @Before)
        // 2. Add a goalkeeper with number: 12, name: "Jane Smith", age: 28.
        Player goalkeeper = new Player();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // 3. Goalkeeper made 1 announcement of type SAVE.
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(saveAnnouncement);
        footballTeam.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals("Goal-saving announcements should be 1 when goalkeeper has one save", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // Test Case 3: Calculate Goal Saving Announcements for a Goalkeeper with Multiple Saves
        // Input: Calculate goal-saving announcements for goalkeeper who made multiple saves.
        
        // SetUp:
        // 1. Create a football team (done in @Before)
        // 2. Add a goalkeeper with number: 5, name: "Mark Johnson", age: 32.
        Player goalkeeper = new Player();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // 3. Goalkeeper made 3 announcements of type SAVE during the match.
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            announcements.add(saveAnnouncement);
        }
        footballTeam.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals("Goal-saving announcements should be 3 when goalkeeper has three saves", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // Test Case 4: Calculate Goal Saving Announcements for a Goalkeeper with Consecutive Saves
        // Input: Calculate goal-saving announcements for goalkeeper who made consecutive saves.
        
        // SetUp:
        // 1. Create a football team (done in @Before)
        // 2. Add a goalkeeper with number: 22, name: "Emily Davis", age: 26.
        Player goalkeeper = new Player();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // 3. Goalkeeper made 5 announcements of type SAVE during the match, all in the second half.
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            announcements.add(saveAnnouncement);
        }
        footballTeam.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals("Goal-saving announcements should be 5 when goalkeeper has five consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // Test Case 5: Calculate Goal Saving Announcements for a Goalkeeper with Invalid Entries
        // Input: Calculate goal-saving announcements for goalkeeper with some invalid entries.
        
        // SetUp:
        // 1. Create a football team (done in @Before)
        // 2. Add a goalkeeper with number: 10, name: "Chris Brown", age: 31.
        Player goalkeeper = new Player();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // 3. Goalkeeper made 2 announcements of type SAVE and 1 announcement of type GOAL (which should not be counted).
        List<Announcement> announcements = new ArrayList<>();
        
        // Add 2 SAVE announcements
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            announcements.add(saveAnnouncement);
        }
        
        // Add 1 GOAL announcement (should not be counted)
        Announcement goalAnnouncement = new Announcement();
        goalAnnouncement.setType(AnnouncementType.SCORE);
        goalAnnouncement.setPlayer(goalkeeper);
        goalAnnouncement.setTime(new Date());
        announcements.add(goalAnnouncement);
        
        footballTeam.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 2
        assertEquals("Goal-saving announcements should be 2 when goalkeeper has 2 saves and 1 goal announcement", 2, result);
    }
}