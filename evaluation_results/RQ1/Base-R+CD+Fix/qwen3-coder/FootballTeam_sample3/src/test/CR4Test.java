import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private FootballTeam footballTeam;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        footballTeam = new FootballTeam();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_calculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // Test Case 1: Calculate Goal Saving Announcements for a Goalkeeper with No Saves
        // SetUp: Create a football team, add a goalkeeper with no save recorded
        Player goalkeeper = new Player();
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        footballTeam.getPlayers().add(goalkeeper);
        
        // No announcements added, so goalkeeper has no saves
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_calculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // Test Case 2: Calculate Goal Saving Announcements for a Goalkeeper with One Save
        // SetUp: Create a football team, add a goalkeeper with one save
        Player goalkeeper = new Player();
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        footballTeam.getPlayers().add(goalkeeper);
        
        // Create one save announcement for the goalkeeper
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        try {
            saveAnnouncement.setTime(dateFormat.parse("2023-10-15 15:30:00"));
        } catch (Exception e) {
            // Handle parse exception
        }
        
        // Add announcement to team
        footballTeam.getAnnouncements().add(saveAnnouncement);
        
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_calculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // Test Case 3: Calculate Goal Saving Announcements for a Goalkeeper with Multiple Saves
        // SetUp: Create a football team, add a goalkeeper with multiple saves
        Player goalkeeper = new Player();
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        footballTeam.getPlayers().add(goalkeeper);
        
        // Create 3 save announcements for the goalkeeper
        for (int i = 1; i <= 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            try {
                saveAnnouncement.setTime(dateFormat.parse("2023-10-15 15:" + (20 + i) + ":00"));
            } catch (Exception e) {
                // Handle parse exception
            }
            
            // Add announcement to team
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_calculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // Test Case 4: Calculate Goal Saving Announcements for a Goalkeeper with Consecutive Saves
        // SetUp: Create a football team, add a goalkeeper with consecutive saves
        Player goalkeeper = new Player();
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        footballTeam.getPlayers().add(goalkeeper);
        
        // Create 5 consecutive save announcements for the goalkeeper (all in second half)
        for (int i = 1; i <= 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            try {
                // All saves in second half (45+ minutes)
                saveAnnouncement.setTime(dateFormat.parse("2023-10-15 16:" + String.format("%02d", i) + ":00"));
            } catch (Exception e) {
                // Handle parse exception
            }
            
            // Add announcement to team
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_calculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // Test Case 5: Calculate Goal Saving Announcements for a Goalkeeper with Invalid Entries
        // SetUp: Create a football team, add a goalkeeper with mixed valid and invalid announcements
        Player goalkeeper = new Player();
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        footballTeam.getPlayers().add(goalkeeper);
        
        // Create 2 valid SAVE announcements
        for (int i = 1; i <= 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            try {
                saveAnnouncement.setTime(dateFormat.parse("2023-10-15 15:" + (30 + i) + ":00"));
            } catch (Exception e) {
                // Handle parse exception
            }
            
            // Add announcement to team
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Create 1 invalid GOAL announcement (should not be counted)
        Announcement goalAnnouncement = new Announcement();
        goalAnnouncement.setType(AnnouncementType.SCORE);
        goalAnnouncement.setPlayer(goalkeeper);
        try {
            goalAnnouncement.setTime(dateFormat.parse("2023-10-15 15:35:00"));
        } catch (Exception e) {
            // Handle parse exception
        }
        
        // Add invalid announcement to team
        footballTeam.getAnnouncements().add(goalAnnouncement);
        
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 2 (only the SAVE announcements)
        assertEquals(2, result);
    }
}