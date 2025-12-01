import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Initialize a new football team before each test
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // Test Case 1: Calculate Goal Saving Announcements for a Goalkeeper with No Saves
        // SetUp: Create a football team and add a goalkeeper with no saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(0); // No saves recorded
        
        // Add goalkeeper to starting eleven
        team.addPlayerToStartingEleven(goalkeeper);
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals(0, team.countGoalSavingAnnouncements());
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // Test Case 2: Calculate Goal Saving Announcements for a Goalkeeper with One Save
        // SetUp: Create a football team and add a goalkeeper with one save
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(1); // One save recorded
        
        // Add goalkeeper to starting eleven
        team.addPlayerToStartingEleven(goalkeeper);
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals(1, team.countGoalSavingAnnouncements());
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // Test Case 3: Calculate Goal Saving Announcements for a Goalkeeper with Multiple Saves
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(3); // Three saves recorded
        
        // Add goalkeeper to starting eleven
        team.addPlayerToStartingEleven(goalkeeper);
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals(3, team.countGoalSavingAnnouncements());
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // Test Case 4: Calculate Goal Saving Announcements for a Goalkeeper with Consecutive Saves
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(5); // Five consecutive saves recorded
        
        // Add goalkeeper to starting eleven
        team.addPlayerToStartingEleven(goalkeeper);
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals(5, team.countGoalSavingAnnouncements());
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // Test Case 5: Calculate Goal Saving Announcements for a Goalkeeper with Invalid Entries
        // SetUp: Create a football team and add a goalkeeper with mixed announcements
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(2); // Two saves recorded
        goalkeeper.setGoalsScored(1); // One goal scored (should not be counted for saving announcements)
        
        // Add goalkeeper to starting eleven
        team.addPlayerToStartingEleven(goalkeeper);
        
        // Expected Output: Total goal-saving announcements = 2 (only saves count, goals are ignored)
        assertEquals(2, team.countGoalSavingAnnouncements());
    }
}