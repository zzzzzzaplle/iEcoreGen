import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        footballTeam = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // Test Case 1: Calculate Goal Saving Announcements for a Goalkeeper with No Saves
        // SetUp:
        // 1. Create a football team (done in @Before)
        // 2. Add a goalkeeper with number: 1, name: "John Doe", age: 30
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(0); // No saves recorded
        
        footballTeam.getStartingEleven().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = footballTeam.countGoalkeeperAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals("Goal-saving announcements should be 0 when goalkeeper has no saves", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // Test Case 2: Calculate Goal Saving Announcements for a Goalkeeper with One Save
        // SetUp:
        // 1. Create a football team (done in @Before)
        // 2. Add a goalkeeper with number: 12, name: "Jane Smith", age: 28
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(1); // One save recorded
        
        footballTeam.getStartingEleven().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = footballTeam.countGoalkeeperAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals("Goal-saving announcements should be 1 when goalkeeper has 1 save", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // Test Case 3: Calculate Goal Saving Announcements for a Goalkeeper with Multiple Saves
        // SetUp:
        // 1. Create a football team (done in @Before)
        // 2. Add a goalkeeper with number: 5, name: "Mark Johnson", age: 32
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(3); // Three saves recorded
        
        footballTeam.getStartingEleven().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = footballTeam.countGoalkeeperAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals("Goal-saving announcements should be 3 when goalkeeper has 3 saves", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // Test Case 4: Calculate Goal Saving Announcements for a Goalkeeper with Consecutive Saves
        // SetUp:
        // 1. Create a football team (done in @Before)
        // 2. Add a goalkeeper with number: 22, name: "Emily Davis", age: 26
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(5); // Five saves recorded (all in second half as specified)
        
        footballTeam.getStartingEleven().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = footballTeam.countGoalkeeperAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals("Goal-saving announcements should be 5 when goalkeeper has 5 saves", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // Test Case 5: Calculate Goal Saving Announcements for a Goalkeeper with Invalid Entries
        // SetUp:
        // 1. Create a football team (done in @Before)
        // 2. Add a goalkeeper with number: 10, name: "Chris Brown", age: 31
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(2); // Two saves recorded
        goalkeeper.setGoalsScored(1); // One goal scored (should not be counted)
        
        footballTeam.getStartingEleven().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = footballTeam.countGoalkeeperAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 2
        assertEquals("Goal-saving announcements should be 2 when goalkeeper has 2 saves and 1 goal", 2, result);
    }
}