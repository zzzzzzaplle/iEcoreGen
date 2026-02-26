import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR4Test {
    
    private FootballTeam team;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        team.setPlayers(new ArrayList<>());
        team.setAnnouncements(new ArrayList<>());
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
        
        // Add goalkeeper to team
        team.getPlayers().add(goalkeeper);
        
        // No saves recorded - no announcements added
        
        // Execute the method and verify result
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
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
        
        // Add goalkeeper to team
        team.getPlayers().add(goalkeeper);
        
        // Create one SAVE announcement for the goalkeeper
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setTime(dateFormat.parse("2023-10-15 15:30:00"));
        
        // Add announcement to team
        team.getAnnouncements().add(saveAnnouncement);
        
        // Execute the method and verify result
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
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
        
        // Add goalkeeper to team
        team.getPlayers().add(goalkeeper);
        
        // Create three SAVE announcements for the goalkeeper
        for (int i = 1; i <= 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(dateFormat.parse("2023-10-15 16:0" + i + ":00"));
            team.getAnnouncements().add(saveAnnouncement);
        }
        
        // Execute the method and verify result
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
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
        
        // Add goalkeeper to team
        team.getPlayers().add(goalkeeper);
        
        // Create five SAVE announcements for the goalkeeper
        for (int i = 1; i <= 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(dateFormat.parse("2023-10-15 17:0" + i + ":00"));
            team.getAnnouncements().add(saveAnnouncement);
        }
        
        // Execute the method and verify result
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
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
        
        // Add goalkeeper to team
        team.getPlayers().add(goalkeeper);
        
        // Create two SAVE announcements for the goalkeeper
        for (int i = 1; i <= 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(dateFormat.parse("2023-10-15 18:0" + i + ":00"));
            team.getAnnouncements().add(saveAnnouncement);
        }
        
        // Create one SCORE announcement (should not be counted)
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(goalkeeper);
        scoreAnnouncement.setTime(dateFormat.parse("2023-10-15 18:15:00"));
        team.getAnnouncements().add(scoreAnnouncement);
        
        // Execute the method and verify result
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(2, result);
    }
}