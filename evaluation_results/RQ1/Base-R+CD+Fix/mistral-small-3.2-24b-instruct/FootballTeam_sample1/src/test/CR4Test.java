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
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team
        // Add a goalkeeper with number: 1, name: "John Doe", age: 30
        Player goalkeeper = new Player();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // The goalkeeper has not made any saves in the match
        // Announcements list remains empty
        
        // Expected Output: Total goal-saving announcements = 0
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() throws Exception {
        // SetUp: Create a football team
        // Add a goalkeeper with number: 12, name: "Jane Smith", age: 28
        Player goalkeeper = new Player();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 1 announcement of type SAVE
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        
        footballTeam.getAnnouncements().add(saveAnnouncement);
        
        // Expected Output: Total goal-saving announcements = 1
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() throws Exception {
        // SetUp: Create a football team
        // Add a goalkeeper with number: 5, name: "Mark Johnson", age: 32
        Player goalkeeper = new Player();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 3 announcements of type SAVE during the match
        for (int i = 1; i <= 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setTime(dateFormat.parse("2024-01-15 15:" + (20 + i) + ":00"));
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Expected Output: Total goal-saving announcements = 3
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() throws Exception {
        // SetUp: Create a football team
        // Add a goalkeeper with number: 22, name: "Emily Davis", age: 26
        Player goalkeeper = new Player();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 5 announcements of type SAVE during the match, all in the second half
        for (int i = 1; i <= 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setTime(dateFormat.parse("2024-01-15 16:" + String.format("%02d", i) + ":00"));
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Expected Output: Total goal-saving announcements = 5
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() throws Exception {
        // SetUp: Create a football team
        // Add a goalkeeper with number: 10, name: "Chris Brown", age: 31
        Player goalkeeper = new Player();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 2 announcements of type SAVE and 1 announcement of type GOAL (which should not be counted)
        Announcement saveAnnouncement1 = new Announcement();
        saveAnnouncement1.setPlayer(goalkeeper);
        saveAnnouncement1.setType(AnnouncementType.SAVE);
        saveAnnouncement1.setTime(dateFormat.parse("2024-01-15 15:25:00"));
        footballTeam.getAnnouncements().add(saveAnnouncement1);
        
        Announcement saveAnnouncement2 = new Announcement();
        saveAnnouncement2.setPlayer(goalkeeper);
        saveAnnouncement2.setType(AnnouncementType.SAVE);
        saveAnnouncement2.setTime(dateFormat.parse("2024-01-15 15:40:00"));
        footballTeam.getAnnouncements().add(saveAnnouncement2);
        
        Announcement goalAnnouncement = new Announcement();
        goalAnnouncement.setPlayer(goalkeeper);
        goalAnnouncement.setType(AnnouncementType.SCORE);
        goalAnnouncement.setTime(dateFormat.parse("2024-01-15 15:50:00"));
        footballTeam.getAnnouncements().add(goalAnnouncement);
        
        // Expected Output: Total goal-saving announcements = 2
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(2, result);
    }
}