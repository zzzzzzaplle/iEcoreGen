import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(0); // No saves recorded
        
        team.getStartingEleven().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncementsByGoalkeepers();
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals("Goal-saving announcements should be 0 for goalkeeper with no saves", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper with one save
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(1); // One save recorded
        
        team.getStartingEleven().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncementsByGoalkeepers();
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals("Goal-saving announcements should be 1 for goalkeeper with one save", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(3); // Three saves recorded
        
        team.getStartingEleven().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncementsByGoalkeepers();
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals("Goal-saving announcements should be 3 for goalkeeper with multiple saves", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(5); // Five saves recorded (consecutive in second half)
        
        team.getStartingEleven().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncementsByGoalkeepers();
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals("Goal-saving announcements should be 5 for goalkeeper with consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed entries
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(2); // Two saves recorded
        goalkeeper.setGoalsScored(1); // One goal scored (should not be counted)
        
        team.getStartingEleven().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncementsByGoalkeepers();
        
        // Expected Output: Total goal-saving announcements = 2
        assertEquals("Goal-saving announcements should be 2 (only saves count, not goals)", 2, result);
    }
}