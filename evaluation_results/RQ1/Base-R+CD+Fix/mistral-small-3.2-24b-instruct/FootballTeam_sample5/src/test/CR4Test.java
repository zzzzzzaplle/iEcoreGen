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
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Add a goalkeeper with number: 1, name: "John Doe", age: 30
        Player goalkeeper = new Player();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        team.getPlayers().add(goalkeeper);
        
        // The goalkeeper has not made any saves in the match (no announcements)
        
        // Calculate goal-saving announcements
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify total goal-saving announcements = 0
        assertEquals("Goal-saving announcements should be 0 when goalkeeper has no saves", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() throws Exception {
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Add a goalkeeper with number: 12, name: "Jane Smith", age: 28
        Player goalkeeper = new Player();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        team.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 1 announcement of type SAVE
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        
        team.getAnnouncements().add(saveAnnouncement);
        
        // Calculate goal-saving announcements
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify total goal-saving announcements = 1
        assertEquals("Goal-saving announcements should be 1 when goalkeeper has one save", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() throws Exception {
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Add a goalkeeper with number: 5, name: "Mark Johnson", age: 32
        Player goalkeeper = new Player();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        team.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 3 announcements of type SAVE during the match
        for (int i = 1; i <= 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(dateFormat.parse("2024-01-15 15:" + (20 + i) + ":00"));
            team.getAnnouncements().add(saveAnnouncement);
        }
        
        // Calculate goal-saving announcements
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify total goal-saving announcements = 3
        assertEquals("Goal-saving announcements should be 3 when goalkeeper has three saves", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() throws Exception {
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Add a goalkeeper with number: 22, name: "Emily Davis", age: 26
        Player goalkeeper = new Player();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        team.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 5 announcements of type SAVE during the match, all in the second half
        for (int i = 1; i <= 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(dateFormat.parse("2024-01-15 16:" + (10 + i) + ":00")); // Second half times
            team.getAnnouncements().add(saveAnnouncement);
        }
        
        // Calculate goal-saving announcements
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify total goal-saving announcements = 5
        assertEquals("Goal-saving announcements should be 5 when goalkeeper has five consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() throws Exception {
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Add a goalkeeper with number: 10, name: "Chris Brown", age: 31
        Player goalkeeper = new Player();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        team.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 2 announcements of type SAVE
        Announcement saveAnnouncement1 = new Announcement();
        saveAnnouncement1.setType(AnnouncementType.SAVE);
        saveAnnouncement1.setPlayer(goalkeeper);
        saveAnnouncement1.setTime(dateFormat.parse("2024-01-15 15:25:00"));
        team.getAnnouncements().add(saveAnnouncement1);
        
        Announcement saveAnnouncement2 = new Announcement();
        saveAnnouncement2.setType(AnnouncementType.SAVE);
        saveAnnouncement2.setPlayer(goalkeeper);
        saveAnnouncement2.setTime(dateFormat.parse("2024-01-15 15:40:00"));
        team.getAnnouncements().add(saveAnnouncement2);
        
        // And 1 announcement of type GOAL (which should not be counted)
        Announcement goalAnnouncement = new Announcement();
        goalAnnouncement.setType(AnnouncementType.SCORE);
        goalAnnouncement.setPlayer(goalkeeper);
        goalAnnouncement.setTime(dateFormat.parse("2024-01-15 15:35:00"));
        team.getAnnouncements().add(goalAnnouncement);
        
        // Calculate goal-saving announcements
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify total goal-saving announcements = 2 (only SAVE announcements counted)
        assertEquals("Goal-saving announcements should be 2 when goalkeeper has two saves and one goal", 2, result);
    }
}