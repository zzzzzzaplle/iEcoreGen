import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private FootballTeam footballTeam;
    private Player goalkeeper;
    
    @Before
    public void setUp() {
        footballTeam = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no save recorded
        goalkeeper = new Player();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to team's players list (required for announcement association)
        footballTeam.getPlayers().add(goalkeeper);
        
        // Expected Output: Total goal-saving announcements = 0
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals("Goal-saving announcements should be 0 when goalkeeper has no saves", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper with one save
        goalkeeper = new Player();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to team's players list
        footballTeam.getPlayers().add(goalkeeper);
        
        // Create and add one SAVE announcement for the goalkeeper
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setType(AnnouncementType.SAVE);
        footballTeam.getAnnouncements().add(saveAnnouncement);
        
        // Expected Output: Total goal-saving announcements = 1
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals("Goal-saving announcements should be 1 when goalkeeper has one save", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        goalkeeper = new Player();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to team's players list
        footballTeam.getPlayers().add(goalkeeper);
        
        // Create and add 3 SAVE announcements for the goalkeeper
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setType(AnnouncementType.SAVE);
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Expected Output: Total goal-saving announcements = 3
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals("Goal-saving announcements should be 3 when goalkeeper has three saves", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        goalkeeper = new Player();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to team's players list
        footballTeam.getPlayers().add(goalkeeper);
        
        // Create and add 5 consecutive SAVE announcements for the goalkeeper
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setType(AnnouncementType.SAVE);
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Expected Output: Total goal-saving announcements = 5
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals("Goal-saving announcements should be 5 when goalkeeper has five consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed announcements
        goalkeeper = new Player();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to team's players list
        footballTeam.getPlayers().add(goalkeeper);
        
        // Create and add 2 SAVE announcements for the goalkeeper
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setType(AnnouncementType.SAVE);
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Add 1 GOAL announcement for the goalkeeper (should not be counted)
        Announcement goalAnnouncement = new Announcement();
        goalAnnouncement.setPlayer(goalkeeper);
        goalAnnouncement.setType(AnnouncementType.SCORE);
        footballTeam.getAnnouncements().add(goalAnnouncement);
        
        // Expected Output: Total goal-saving announcements = 2
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals("Goal-saving announcements should be 2 when goalkeeper has 2 saves and 1 goal announcement", 2, result);
    }
}