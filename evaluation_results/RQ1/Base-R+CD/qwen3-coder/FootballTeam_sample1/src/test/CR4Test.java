import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR4Test {
    
    private FootballTeam footballTeam;
    private Player goalkeeper;
    
    @Before
    public void setUp() {
        footballTeam = new FootballTeam();
        goalkeeper = new Player();
        goalkeeper.setPosition(Position.GOALKEEPER);
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // Create a football team
        footballTeam = new FootballTeam();
        
        // Add a goalkeeper with number: 1, name: "John Doe", age: 30
        goalkeeper = new Player();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // The goalkeeper has not made any saves in the match
        // No announcements added
        
        // Expected Output: Total goal-saving announcements = 0
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // Create a football team
        footballTeam = new FootballTeam();
        
        // Add a goalkeeper with number: 12, name: "Jane Smith", age: 28
        goalkeeper = new Player();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 1 announcement of type SAVE
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setTime(new Date());
        
        footballTeam.getAnnouncements().add(saveAnnouncement);
        
        // Expected Output: Total goal-saving announcements = 1
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // Create a football team
        footballTeam = new FootballTeam();
        
        // Add a goalkeeper with number: 5, name: "Mark Johnson", age: 32
        goalkeeper = new Player();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 3 announcements of type SAVE during the match
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Expected Output: Total goal-saving announcements = 3
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // Create a football team
        footballTeam = new FootballTeam();
        
        // Add a goalkeeper with number: 22, name: "Emily Davis", age: 26
        goalkeeper = new Player();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 5 announcements of type SAVE during the match, all in the second half
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            
            // Setting different times for the announcements
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date time = sdf.parse("2023-10-15 15:30:" + String.format("%02d", i));
                saveAnnouncement.setTime(time);
            } catch (ParseException e) {
                saveAnnouncement.setTime(new Date());
            }
            
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Expected Output: Total goal-saving announcements = 5
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // Create a football team
        footballTeam = new FootballTeam();
        
        // Add a goalkeeper with number: 10, name: "Chris Brown", age: 31
        goalkeeper = new Player();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        footballTeam.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 2 announcements of type SAVE and 1 announcement of type SCORE (which should not be counted)
        // Add 2 SAVE announcements
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Add 1 SCORE announcement (should not be counted)
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(goalkeeper);
        scoreAnnouncement.setTime(new Date());
        footballTeam.getAnnouncements().add(scoreAnnouncement);
        
        // Expected Output: Total goal-saving announcements = 2
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(2, result);
    }
}