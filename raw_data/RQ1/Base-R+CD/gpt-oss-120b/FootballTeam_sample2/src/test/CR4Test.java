import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR4Test {
    
    private FootballTeam team;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() throws ParseException {
        // Create a goalkeeper with number: 1, name: "John Doe", age: 30
        Player goalkeeper = new Player();
        goalkeeper.setName("John Doe");
        goalkeeper.setNumber(1);
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        team.addPlayer(goalkeeper);
        
        // No SAVE announcements recorded
        
        // Execute the method
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Assert that total goal-saving announcements = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() throws ParseException {
        // Create a goalkeeper with number: 12, name: "Jane Smith", age: 28
        Player goalkeeper = new Player();
        goalkeeper.setName("Jane Smith");
        goalkeeper.setNumber(12);
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        team.addPlayer(goalkeeper);
        
        // Create one SAVE announcement by the goalkeeper
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setTime(dateFormat.parse("2023-10-15 15:30:00"));
        
        // Add announcement to the team
        team.addAnnouncement(saveAnnouncement);
        
        // Execute the method
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Assert that total goal-saving announcements = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() throws ParseException {
        // Create a goalkeeper with number: 5, name: "Mark Johnson", age: 32
        Player goalkeeper = new Player();
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setNumber(5);
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        team.addPlayer(goalkeeper);
        
        // Create three SAVE announcements by the goalkeeper
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(dateFormat.parse("2023-10-15 16:00:00"));
            team.addAnnouncement(saveAnnouncement);
        }
        
        // Execute the method
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Assert that total goal-saving announcements = 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() throws ParseException {
        // Create a goalkeeper with number: 22, name: "Emily Davis", age: 26
        Player goalkeeper = new Player();
        goalkeeper.setName("Emily Davis");
        goalkeeper.setNumber(22);
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        team.addPlayer(goalkeeper);
        
        // Create five SAVE announcements by the goalkeeper
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(dateFormat.parse("2023-10-15 17:00:00"));
            team.addAnnouncement(saveAnnouncement);
        }
        
        // Execute the method
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Assert that total goal-saving announcements = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() throws ParseException {
        // Create a goalkeeper with number: 10, name: "Chris Brown", age: 31
        Player goalkeeper = new Player();
        goalkeeper.setName("Chris Brown");
        goalkeeper.setNumber(10);
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        team.addPlayer(goalkeeper);
        
        // Create two SAVE announcements by the goalkeeper
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(dateFormat.parse("2023-10-15 18:00:00"));
            team.addAnnouncement(saveAnnouncement);
        }
        
        // Create one SCORE announcement by the goalkeeper (should not be counted)
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(goalkeeper);
        scoreAnnouncement.setTime(dateFormat.parse("2023-10-15 18:30:00"));
        team.addAnnouncement(scoreAnnouncement);
        
        // Execute the method
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Assert that total goal-saving announcements = 2 (SCORE should not be counted)
        assertEquals(2, result);
    }
}