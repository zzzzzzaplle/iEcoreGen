import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no save recorded
        Player goalkeeper = new Player();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Expected Output: Total goal-saving announcements = 0
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() throws Exception {
        // SetUp: Create a football team and add a goalkeeper with one save
        Player goalkeeper = new Player();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Create one SAVE announcement for the goalkeeper
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        
        footballTeam.getAnnouncements().add(saveAnnouncement);
        
        // Expected Output: Total goal-saving announcements = 1
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() throws Exception {
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        Player goalkeeper = new Player();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Create 3 SAVE announcements for the goalkeeper
        for (int i = 1; i <= 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setTime(dateFormat.parse("2024-01-15 15:" + (20 + i) + ":00"));
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Expected Output: Total goal-saving announcements = 3
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() throws Exception {
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        Player goalkeeper = new Player();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Create 5 consecutive SAVE announcements for the goalkeeper in second half
        for (int i = 1; i <= 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setTime(dateFormat.parse("2024-01-15 16:" + String.format("%02d", i) + ":00"));
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Expected Output: Total goal-saving announcements = 5
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() throws Exception {
        // SetUp: Create a football team and add a goalkeeper with mixed announcement types
        Player goalkeeper = new Player();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Create 2 SAVE announcements
        Announcement saveAnnouncement1 = new Announcement();
        saveAnnouncement1.setTime(dateFormat.parse("2024-01-15 15:25:00"));
        saveAnnouncement1.setType(AnnouncementType.SAVE);
        saveAnnouncement1.setPlayer(goalkeeper);
        footballTeam.getAnnouncements().add(saveAnnouncement1);
        
        Announcement saveAnnouncement2 = new Announcement();
        saveAnnouncement2.setTime(dateFormat.parse("2024-01-15 15:40:00"));
        saveAnnouncement2.setType(AnnouncementType.SAVE);
        saveAnnouncement2.setPlayer(goalkeeper);
        footballTeam.getAnnouncements().add(saveAnnouncement2);
        
        // Create 1 GOAL announcement (should not be counted)
        Announcement goalAnnouncement = new Announcement();
        goalAnnouncement.setTime(dateFormat.parse("2024-01-15 15:35:00"));
        goalAnnouncement.setType(AnnouncementType.SCORE);
        goalAnnouncement.setPlayer(goalkeeper);
        footballTeam.getAnnouncements().add(goalAnnouncement);
        
        // Expected Output: Total goal-saving announcements = 2 (only SAVE announcements counted)
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(2, result);
    }
}