import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR4Test {
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        footballTeam = new FootballTeam();
    }
    
    @Test
    public void testCase1_calculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no save recorded
        Player goalkeeper = new Player();
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Execute: Calculate goal-saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify: Total goal-saving announcements should be 0
        assertEquals("Goal-saving announcements should be 0 when goalkeeper has no saves", 0, result);
    }
    
    @Test
    public void testCase2_calculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper with one save
        Player goalkeeper = new Player();
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setTime(new Date());
        
        footballTeam.getAnnouncements().add(saveAnnouncement);
        
        // Execute: Calculate goal-saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify: Total goal-saving announcements should be 1
        assertEquals("Goal-saving announcements should be 1 when goalkeeper has one save", 1, result);
    }
    
    @Test
    public void testCase3_calculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        Player goalkeeper = new Player();
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Add 3 save announcements
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Execute: Calculate goal-saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify: Total goal-saving announcements should be 3
        assertEquals("Goal-saving announcements should be 3 when goalkeeper has multiple saves", 3, result);
    }
    
    @Test
    public void testCase4_calculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        Player goalkeeper = new Player();
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Add 5 consecutive save announcements
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Execute: Calculate goal-saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify: Total goal-saving announcements should be 5
        assertEquals("Goal-saving announcements should be 5 when goalkeeper has consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_calculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed announcement types
        Player goalkeeper = new Player();
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Add 2 save announcements
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Add 1 goal announcement (should not be counted)
        Announcement goalAnnouncement = new Announcement();
        goalAnnouncement.setType(AnnouncementType.SCORE);
        goalAnnouncement.setPlayer(goalkeeper);
        goalAnnouncement.setTime(new Date());
        footballTeam.getAnnouncements().add(goalAnnouncement);
        
        // Execute: Calculate goal-saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify: Total goal-saving announcements should be 2 (only SAVE types counted)
        assertEquals("Goal-saving announcements should be 2 when goalkeeper has mixed announcement types", 2, result);
    }
}