import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        // SetUp: Create a football team and add a goalkeeper with no saves recorded
        
        // Create goalkeeper with number: 1, name: "John Doe", age: 30
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setTeam("Test Team");
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(0); // No saves recorded
        
        // Add goalkeeper to team players list
        List<FootballPlayer> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals("Goal-saving announcements should be 0 when goalkeeper has no saves", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // Test Case 2: Calculate Goal Saving Announcements for a Goalkeeper with One Save
        // SetUp: Create a football team and add a goalkeeper with one save
        
        // Create goalkeeper with number: 12, name: "Jane Smith", age: 28
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setTeam("Test Team");
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(1); // One save recorded
        
        // Add goalkeeper to team players list
        List<FootballPlayer> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals("Goal-saving announcements should be 1 when goalkeeper has one save", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // Test Case 3: Calculate Goal Saving Announcements for a Goalkeeper with Multiple Saves
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        
        // Create goalkeeper with number: 5, name: "Mark Johnson", age: 32
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setTeam("Test Team");
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(3); // Three saves recorded
        
        // Add goalkeeper to team players list
        List<FootballPlayer> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals("Goal-saving announcements should be 3 when goalkeeper has three saves", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // Test Case 4: Calculate Goal Saving Announcements for a Goalkeeper with Consecutive Saves
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        
        // Create goalkeeper with number: 22, name: "Emily Davis", age: 26
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setTeam("Test Team");
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(5); // Five saves recorded (consecutive in second half)
        
        // Add goalkeeper to team players list
        List<FootballPlayer> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals("Goal-saving announcements should be 5 when goalkeeper has five consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // Test Case 5: Calculate Goal Saving Announcements for a Goalkeeper with Invalid Entries
        // SetUp: Create a football team and add a goalkeeper with mixed valid and invalid entries
        
        // Create goalkeeper with number: 10, name: "Chris Brown", age: 31
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setTeam("Test Team");
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(2); // Two valid SAVE announcements
        goalkeeper.setGoalsScored(1); // One invalid GOAL announcement (should not be counted)
        
        // Add goalkeeper to team players list
        List<FootballPlayer> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 2
        // Note: Only goal-saving announcements (goalsSaved) are counted, goal-scoring announcements (goalsScored) are ignored
        assertEquals("Goal-saving announcements should be 2 when goalkeeper has two saves and one goal", 2, result);
    }
}