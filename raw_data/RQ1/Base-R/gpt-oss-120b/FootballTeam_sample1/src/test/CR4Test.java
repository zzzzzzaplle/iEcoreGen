import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam("Test Team");
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no save recorded
        FootballPlayer goalkeeper = new FootballPlayer("Test Team", "John Doe", 30, 1, Position.GOALKEEPER);
        team.addPlayer(goalkeeper);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 0 when goalkeeper has no saves", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper with one save
        FootballPlayer goalkeeper = new FootballPlayer("Test Team", "Jane Smith", 28, 12, Position.GOALKEEPER);
        goalkeeper.setSaveAnnouncements(1);
        team.addPlayer(goalkeeper);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 1 when goalkeeper has one save", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        FootballPlayer goalkeeper = new FootballPlayer("Test Team", "Mark Johnson", 32, 5, Position.GOALKEEPER);
        goalkeeper.setSaveAnnouncements(3);
        team.addPlayer(goalkeeper);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 3 when goalkeeper has three saves", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        FootballPlayer goalkeeper = new FootballPlayer("Test Team", "Emily Davis", 26, 22, Position.GOALKEEPER);
        goalkeeper.setSaveAnnouncements(5);
        team.addPlayer(goalkeeper);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 5 when goalkeeper has five consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed announcements
        FootballPlayer goalkeeper = new FootballPlayer("Test Team", "Chris Brown", 31, 10, Position.GOALKEEPER);
        goalkeeper.setSaveAnnouncements(2);
        goalkeeper.setScoreAnnouncements(1); // This should not be counted for goal-saving announcements
        team.addPlayer(goalkeeper);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 2 when goalkeeper has 2 saves and 1 goal announcement", 2, result);
    }
}