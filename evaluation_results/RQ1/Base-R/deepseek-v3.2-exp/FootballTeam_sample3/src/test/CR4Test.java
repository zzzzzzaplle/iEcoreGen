import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Initialize a new team before each test
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // Test Case 1: Calculate Goal Saving Announcements for a Goalkeeper with No Saves
        // SetUp: Create a football team and add a goalkeeper with no saves recorded
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setGoalsSaved(0); // No saves recorded
        
        team.addPlayer(goalkeeper);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 0 when goalkeeper has no saves", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // Test Case 2: Calculate Goal Saving Announcements for a Goalkeeper with One Save
        // SetUp: Create a football team and add a goalkeeper with one save
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setGoalsSaved(1); // One save recorded
        
        team.addPlayer(goalkeeper);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 1 when goalkeeper has one save", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // Test Case 3: Calculate Goal Saving Announcements for a Goalkeeper with Multiple Saves
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setGoalsSaved(3); // Three saves recorded
        
        team.addPlayer(goalkeeper);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 3 when goalkeeper has three saves", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // Test Case 4: Calculate Goal Saving Announcements for a Goalkeeper with Consecutive Saves
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setGoalsSaved(5); // Five consecutive saves recorded
        
        team.addPlayer(goalkeeper);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 5 when goalkeeper has five consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // Test Case 5: Calculate Goal Saving Announcements for a Goalkeeper with Invalid Entries
        // SetUp: Create a football team and add a goalkeeper with mixed entries
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setGoalsSaved(2); // Two saves recorded
        goalkeeper.setGoalsScored(1); // One goal scored (should not be counted)
        
        team.addPlayer(goalkeeper);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 2 when goalkeeper has 2 saves and 1 goal", 2, result);
    }
}