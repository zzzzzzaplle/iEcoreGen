import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private FootballTeam team;
    private Player goalkeeper;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no save recorded
        goalkeeper = new Player();
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        team.addPlayer(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals("Goalkeeper with no saves should return 0", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper with one save
        goalkeeper = new Player();
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        team.addPlayer(goalkeeper);
        
        // Create one save announcement for the goalkeeper
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setTime(new Date());
        
        team.addAnnouncement(saveAnnouncement);
        
        // Calculate goal-saving announcements
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals("Goalkeeper with one save should return 1", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        goalkeeper = new Player();
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        team.addPlayer(goalkeeper);
        
        // Create three save announcements for the goalkeeper
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            team.addAnnouncement(saveAnnouncement);
        }
        
        // Calculate goal-saving announcements
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals("Goalkeeper with three saves should return 3", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        goalkeeper = new Player();
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        team.addPlayer(goalkeeper);
        
        // Create five consecutive save announcements for the goalkeeper
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            team.addAnnouncement(saveAnnouncement);
        }
        
        // Calculate goal-saving announcements
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals("Goalkeeper with five consecutive saves should return 5", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed announcements
        goalkeeper = new Player();
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        team.addPlayer(goalkeeper);
        
        // Create two save announcements
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            team.addAnnouncement(saveAnnouncement);
        }
        
        // Create one goal announcement (should not be counted)
        Announcement goalAnnouncement = new Announcement();
        goalAnnouncement.setType(AnnouncementType.SCORE);
        goalAnnouncement.setPlayer(goalkeeper);
        goalAnnouncement.setTime(new Date());
        team.addAnnouncement(goalAnnouncement);
        
        // Calculate goal-saving announcements
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 2
        assertEquals("Goalkeeper with two saves and one goal should return 2", 2, result);
    }
}