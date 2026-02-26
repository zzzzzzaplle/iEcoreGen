import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Team team;
    
    @Before
    public void setUp() {
        // Create a new team before each test
        team = new Team();
        team.setName("Test Team");
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // Test Case 1: Calculate Goal Saving Announcements for a Goalkeeper with No Saves
        // SetUp: Create a football team and add a goalkeeper with no saves recorded
        Goalkeeper goalkeeper = new Goalkeeper();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        // No saveGoal() calls - goalkeeper has no saves
        
        team.addPlayer(goalkeeper, true);
        
        // Expected Output: Total goal-saving announcements = 0
        int result = team.countGoalSavingAnnouncements();
        assertEquals("Goalkeeper with no saves should return 0", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // Test Case 2: Calculate Goal Saving Announcements for a Goalkeeper with One Save
        // SetUp: Create a football team and add a goalkeeper with one save
        Goalkeeper goalkeeper = new Goalkeeper();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        // Goalkeeper made 1 announcement of type SAVE
        goalkeeper.saveGoal();
        
        team.addPlayer(goalkeeper, true);
        
        // Expected Output: Total goal-saving announcements = 1
        int result = team.countGoalSavingAnnouncements();
        assertEquals("Goalkeeper with one save should return 1", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // Test Case 3: Calculate Goal Saving Announcements for a Goalkeeper with Multiple Saves
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        Goalkeeper goalkeeper = new Goalkeeper();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        // Goalkeeper made 3 announcements of type SAVE during the match
        goalkeeper.saveGoal();
        goalkeeper.saveGoal();
        goalkeeper.saveGoal();
        
        team.addPlayer(goalkeeper, true);
        
        // Expected Output: Total goal-saving announcements = 3
        int result = team.countGoalSavingAnnouncements();
        assertEquals("Goalkeeper with three saves should return 3", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // Test Case 4: Calculate Goal Saving Announcements for a Goalkeeper with Consecutive Saves
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        Goalkeeper goalkeeper = new Goalkeeper();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        // Goalkeeper made 5 announcements of type SAVE during the match, all in the second half
        for (int i = 0; i < 5; i++) {
            goalkeeper.saveGoal();
        }
        
        team.addPlayer(goalkeeper, true);
        
        // Expected Output: Total goal-saving announcements = 5
        int result = team.countGoalSavingAnnouncements();
        assertEquals("Goalkeeper with five consecutive saves should return 5", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // Test Case 5: Calculate Goal Saving Announcements for a Goalkeeper with Invalid Entries
        // SetUp: Create a football team and add a goalkeeper with mixed announcements
        Goalkeeper goalkeeper = new Goalkeeper();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        // Goalkeeper made 2 announcements of type SAVE and 1 announcement of type GOAL
        goalkeeper.saveGoal(); // Valid save announcement
        goalkeeper.saveGoal(); // Valid save announcement
        // Note: Goalkeeper cannot call scoreGoal() method as it's not available in Goalkeeper class
        // The invalid GOAL announcement mentioned in the spec would not be possible in this implementation
        // since Goalkeeper class doesn't have access to scoreGoal() method
        
        team.addPlayer(goalkeeper, true);
        
        // Expected Output: Total goal-saving announcements = 2
        int result = team.countGoalSavingAnnouncements();
        assertEquals("Goalkeeper with two saves should return 2", 2, result);
    }
}