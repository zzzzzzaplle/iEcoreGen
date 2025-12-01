import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Team team;
    
    @Before
    public void setUp() {
        // Create a new team before each test
        team = new Team("Test Team");
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no saves recorded
        Goalkeeper goalkeeper = new Goalkeeper("Test Team", "John Doe", 30, 1);
        team.addPlayer(goalkeeper);
        
        // The goalkeeper has not made any saves in the match (saveAnnouncements = 0 by default)
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals("Goalkeeper with no saves should return 0", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper with one save
        Goalkeeper goalkeeper = new Goalkeeper("Test Team", "Jane Smith", 28, 12);
        team.addPlayer(goalkeeper);
        
        // Goalkeeper made 1 announcement of type SAVE
        goalkeeper.setSaveAnnouncements(1);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals("Goalkeeper with one save should return 1", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        Goalkeeper goalkeeper = new Goalkeeper("Test Team", "Mark Johnson", 32, 5);
        team.addPlayer(goalkeeper);
        
        // Goalkeeper made 3 announcements of type SAVE during the match
        goalkeeper.setSaveAnnouncements(3);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals("Goalkeeper with three saves should return 3", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        Goalkeeper goalkeeper = new Goalkeeper("Test Team", "Emily Davis", 26, 22);
        team.addPlayer(goalkeeper);
        
        // Goalkeeper made 5 announcements of type SAVE during the match, all in the second half
        goalkeeper.setSaveAnnouncements(5);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals("Goalkeeper with five consecutive saves should return 5", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed announcements
        Goalkeeper goalkeeper = new Goalkeeper("Test Team", "Chris Brown", 31, 10);
        team.addPlayer(goalkeeper);
        
        // Goalkeeper made 2 announcements of type SAVE and 1 announcement of type GOAL
        // Only save announcements should be counted (score announcements are ignored)
        goalkeeper.setSaveAnnouncements(2);
        goalkeeper.setScoreAnnouncements(1); // This should NOT be counted
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 2
        assertEquals("Only save announcements should be counted, goal announcements ignored", 2, result);
    }
}