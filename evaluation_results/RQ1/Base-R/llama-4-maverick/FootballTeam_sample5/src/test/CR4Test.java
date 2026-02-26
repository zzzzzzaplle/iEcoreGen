import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        // Initialize a fresh football team before each test
        footballTeam = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no saves recorded
        Player goalkeeper = new Player("TeamA", "John Doe", 30, 1, Position.GOALKEEPER);
        footballTeam.getStartingEleven().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = footballTeam.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals("Goalkeeper with no saves should return 0", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper who made one save
        Player goalkeeper = new Player("TeamB", "Jane Smith", 28, 12, Position.GOALKEEPER);
        footballTeam.getStartingEleven().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = footballTeam.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals("Goalkeeper with one save should return 1", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper who made multiple saves
        Player goalkeeper = new Player("TeamC", "Mark Johnson", 32, 5, Position.GOALKEEPER);
        footballTeam.getStartingEleven().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = footballTeam.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 1 (each goalkeeper counts as 1 announcement regardless of saves)
        assertEquals("Each goalkeeper counts as 1 announcement", 1, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper who made consecutive saves
        Player goalkeeper = new Player("TeamD", "Emily Davis", 26, 22, Position.GOALKEEPER);
        footballTeam.getStartingEleven().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = footballTeam.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 1 (each goalkeeper counts as 1 announcement)
        assertEquals("Each goalkeeper counts as 1 announcement regardless of save sequence", 1, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed announcements
        Player goalkeeper = new Player("TeamE", "Chris Brown", 31, 10, Position.GOALKEEPER);
        footballTeam.getStartingEleven().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = footballTeam.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 1 (only goalkeeper position counts, not announcement types)
        assertEquals("Only goalkeeper position counts, not announcement types", 1, result);
    }
}