import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Reset team before each test
        team = new FootballTeam("Test Team");
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // Create a football team
        FootballTeam team = new FootballTeam("Team A");
        
        // Add a goalkeeper with number: 1, name: "John Doe", age: 30
        FootballPlayer goalkeeper = new FootballPlayer("Team A", "John Doe", 30, 1, Position.DEFENSE, Duty.GOALKEEPER);
        // The goalkeeper has not made any saves in the match (default value is 0)
        
        // Add goalkeeper to starting eleven
        team.getStartingEleven().add(goalkeeper);
        
        // Calculate total goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals("Goal-saving announcements should be 0 for goalkeeper with no saves", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // Create a football team
        FootballTeam team = new FootballTeam("Team B");
        
        // Add a goalkeeper with number: 12, name: "Jane Smith", age: 28
        FootballPlayer goalkeeper = new FootballPlayer("Team B", "Jane Smith", 28, 12, Position.DEFENSE, Duty.GOALKEEPER);
        // Goalkeeper made 1 announcement of type SAVE
        goalkeeper.setGoalSavingAnnouncements(1);
        
        // Add goalkeeper to starting eleven
        team.getStartingEleven().add(goalkeeper);
        
        // Calculate total goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals("Goal-saving announcements should be 1 for goalkeeper with one save", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // Create a football team
        FootballTeam team = new FootballTeam("Team C");
        
        // Add a goalkeeper with number: 5, name: "Mark Johnson", age: 32
        FootballPlayer goalkeeper = new FootballPlayer("Team C", "Mark Johnson", 32, 5, Position.DEFENSE, Duty.GOALKEEPER);
        // Goalkeeper made 3 announcements of type SAVE during the match
        goalkeeper.setGoalSavingAnnouncements(3);
        
        // Add goalkeeper to starting eleven
        team.getStartingEleven().add(goalkeeper);
        
        // Calculate total goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals("Goal-saving announcements should be 3 for goalkeeper with multiple saves", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // Create a football team
        FootballTeam team = new FootballTeam("Team D");
        
        // Add a goalkeeper with number: 22, name: "Emily Davis", age: 26
        FootballPlayer goalkeeper = new FootballPlayer("Team D", "Emily Davis", 26, 22, Position.DEFENSE, Duty.GOALKEEPER);
        // Goalkeeper made 5 announcements of type SAVE during the match, all in the second half
        goalkeeper.setGoalSavingAnnouncements(5);
        
        // Add goalkeeper to starting eleven
        team.getStartingEleven().add(goalkeeper);
        
        // Calculate total goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals("Goal-saving announcements should be 5 for goalkeeper with consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // Create a football team
        FootballTeam team = new FootballTeam("Team E");
        
        // Add a goalkeeper with number: 10, name: "Chris Brown", age: 31
        FootballPlayer goalkeeper = new FootballPlayer("Team E", "Chris Brown", 31, 10, Position.DEFENSE, Duty.GOALKEEPER);
        // Goalkeeper made 2 announcements of type SAVE and 1 announcement of type GOAL (which should not be counted)
        goalkeeper.setGoalSavingAnnouncements(2);
        goalkeeper.setScoreAnnouncements(1); // This should not be counted in goal-saving announcements
        
        // Add goalkeeper to starting eleven
        team.getStartingEleven().add(goalkeeper);
        
        // Calculate total goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 2
        assertEquals("Goal-saving announcements should be 2, ignoring goal-scoring announcements", 2, result);
    }
}