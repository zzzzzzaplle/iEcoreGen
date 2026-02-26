import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import com.turgutlu.football.*;

public class CR4Test {
    
    private FootballTeam team;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // Create a goalkeeper with number: 1, name: "John Doe", age: 30
        Player goalkeeper = new Player();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        team.addPlayer(goalkeeper);
        
        // The goalkeeper has not made any saves in the match
        // No announcements added
        
        // Expected Output: Total goal-saving announcements = 0
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() throws ParseException {
        // Create a goalkeeper with number: 12, name: "Jane Smith", age: 28
        Player goalkeeper = new Player();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        team.addPlayer(goalkeeper);
        
        // Goalkeeper made 1 announcement of type SAVE
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setTime(dateFormat.parse("2023-10-15 15:30:00"));
        
        team.addAnnouncement(saveAnnouncement);
        
        // Expected Output: Total goal-saving announcements = 1
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() throws ParseException {
        // Create a goalkeeper with number: 5, name: "Mark Johnson", age: 32
        Player goalkeeper = new Player();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        team.addPlayer(goalkeeper);
        
        // Goalkeeper made 3 announcements of type SAVE during the match
        for (int i = 1; i <= 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(dateFormat.parse("2023-10-15 16:" + String.format("%02d", i*10) + ":00"));
            team.addAnnouncement(saveAnnouncement);
        }
        
        // Expected Output: Total goal-saving announcements = 3
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() throws ParseException {
        // Create a goalkeeper with number: 22, name: "Emily Davis", age: 26
        Player goalkeeper = new Player();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        team.addPlayer(goalkeeper);
        
        // Goalkeeper made 5 announcements of type SAVE during the match, all in the second half
        for (int i = 1; i <= 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(dateFormat.parse("2023-10-15 17:" + String.format("%02d", i*5) + ":00"));
            team.addAnnouncement(saveAnnouncement);
        }
        
        // Expected Output: Total goal-saving announcements = 5
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() throws ParseException {
        // Create a goalkeeper with number: 10, name: "Chris Brown", age: 31
        Player goalkeeper = new Player();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        team.addPlayer(goalkeeper);
        
        // Goalkeeper made 2 announcements of type SAVE
        for (int i = 1; i <= 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(dateFormat.parse("2023-10-15 18:" + String.format("%02d", i*15) + ":00"));
            team.addAnnouncement(saveAnnouncement);
        }
        
        // and 1 announcement of type SCORE (which should not be counted)
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(goalkeeper);
        scoreAnnouncement.setTime(dateFormat.parse("2023-10-15 18:45:00"));
        team.addAnnouncement(scoreAnnouncement);
        
        // Expected Output: Total goal-saving announcements = 2
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(2, result);
    }
}