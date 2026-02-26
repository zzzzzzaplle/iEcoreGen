import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR4Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no save recorded
        Player goalkeeper = new Player();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        team.addPlayer(goalkeeper);
        
        // No announcements added for the goalkeeper
        
        // Execute the method under test
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 0 when goalkeeper has no saves", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper with one save
        Player goalkeeper = new Player();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        team.addPlayer(goalkeeper);
        
        // Add one SAVE announcement for the goalkeeper
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setTime(new Date());
        
        team.addAnnouncement(saveAnnouncement);
        
        // Execute the method under test
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 1 when goalkeeper made one save", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        Player goalkeeper = new Player();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        team.addPlayer(goalkeeper);
        
        // Add 3 SAVE announcements for the goalkeeper
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            team.addAnnouncement(saveAnnouncement);
        }
        
        // Execute the method under test
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 3 when goalkeeper made three saves", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        Player goalkeeper = new Player();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        team.addPlayer(goalkeeper);
        
        // Add 5 consecutive SAVE announcements for the goalkeeper (all in second half)
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            team.addAnnouncement(saveAnnouncement);
        }
        
        // Execute the method under test
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 5 when goalkeeper made five consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed announcement types
        Player goalkeeper = new Player();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        team.addPlayer(goalkeeper);
        
        // Add 2 SAVE announcements for the goalkeeper
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            team.addAnnouncement(saveAnnouncement);
        }
        
        // Add 1 GOAL announcement for the goalkeeper (should not be counted)
        Announcement goalAnnouncement = new Announcement();
        goalAnnouncement.setType(AnnouncementType.SCORE); // This is the GOAL announcement type
        goalAnnouncement.setPlayer(goalkeeper);
        goalAnnouncement.setTime(new Date());
        team.addAnnouncement(goalAnnouncement);
        
        // Execute the method under test
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the expected output - only SAVE announcements should be counted
        assertEquals("Total goal-saving announcements should be 2 when goalkeeper made 2 saves and 1 goal", 2, result);
    }
}