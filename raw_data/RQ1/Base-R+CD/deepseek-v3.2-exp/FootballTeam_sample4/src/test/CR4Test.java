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
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Add a goalkeeper with number: 1, name: "John Doe", age: 30
        Player goalkeeper = new Player();
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list (though not strictly necessary for this test)
        team.getPlayers().add(goalkeeper);
        
        // The goalkeeper has not made any saves in the match
        // No announcements are added to the team
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals(0, team.calculateGoalSavingAnnouncementsByGoalkeeper());
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Add a goalkeeper with number: 12, name: "Jane Smith", age: 28
        Player goalkeeper = new Player();
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        team.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 1 announcement of type SAVE
        Announcement saveAnnouncement = new Announcement();
        try {
            saveAnnouncement.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        } catch (Exception e) {
            // Handle parse exception
        }
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        
        team.getAnnouncements().add(saveAnnouncement);
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals(1, team.calculateGoalSavingAnnouncementsByGoalkeeper());
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Add a goalkeeper with number: 5, name: "Mark Johnson", age: 32
        Player goalkeeper = new Player();
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        team.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 3 announcements of type SAVE during the match
        for (int i = 1; i <= 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            try {
                saveAnnouncement.setTime(dateFormat.parse("2024-01-15 15:" + (20 + i) + ":00"));
            } catch (Exception e) {
                // Handle parse exception
            }
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            team.getAnnouncements().add(saveAnnouncement);
        }
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals(3, team.calculateGoalSavingAnnouncementsByGoalkeeper());
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Add a goalkeeper with number: 22, name: "Emily Davis", age: 26
        Player goalkeeper = new Player();
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        team.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 5 announcements of type SAVE during the match, all in the second half
        for (int i = 1; i <= 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            try {
                saveAnnouncement.setTime(dateFormat.parse("2024-01-15 16:" + (i + 45) + ":00"));
            } catch (Exception e) {
                // Handle parse exception
            }
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            team.getAnnouncements().add(saveAnnouncement);
        }
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals(5, team.calculateGoalSavingAnnouncementsByGoalkeeper());
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Add a goalkeeper with number: 10, name: "Chris Brown", age: 31
        Player goalkeeper = new Player();
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        team.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 2 announcements of type SAVE
        for (int i = 1; i <= 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            try {
                saveAnnouncement.setTime(dateFormat.parse("2024-01-15 15:" + (25 + i) + ":00"));
            } catch (Exception e) {
                // Handle parse exception
            }
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            team.getAnnouncements().add(saveAnnouncement);
        }
        
        // And 1 announcement of type GOAL (which should not be counted)
        Announcement goalAnnouncement = new Announcement();
        try {
            goalAnnouncement.setTime(dateFormat.parse("2024-01-15 15:35:00"));
        } catch (Exception e) {
            // Handle parse exception
        }
        goalAnnouncement.setType(AnnouncementType.SCORE);
        goalAnnouncement.setPlayer(goalkeeper);
        team.getAnnouncements().add(goalAnnouncement);
        
        // Expected Output: Total goal-saving announcements = 2
        assertEquals(2, team.calculateGoalSavingAnnouncementsByGoalkeeper());
    }
}