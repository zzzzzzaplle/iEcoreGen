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
        // SetUp: Create a football team and add a goalkeeper with no save recorded
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalSavingAnnouncements(0); // No saves recorded
        
        team.addPlayer(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper with one save
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalSavingAnnouncements(1); // One save recorded
        
        team.addPlayer(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalSavingAnnouncements(3); // Three saves recorded
        
        team.addPlayer(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalSavingAnnouncements(5); // Five consecutive saves recorded
        
        team.addPlayer(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed announcements
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalSavingAnnouncements(2); // Two valid saves
        goalkeeper.setGoalScoringAnnouncements(1); // One invalid goal announcement (should not be counted)
        
        team.addPlayer(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 2 (only saves count)
        assertEquals(2, result);
    }
}