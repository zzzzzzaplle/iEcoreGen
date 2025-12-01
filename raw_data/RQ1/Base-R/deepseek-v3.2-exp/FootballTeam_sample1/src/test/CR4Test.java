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
        // Test Case 1: Calculate goal-saving announcements for goalkeeper with no save recorded
        // SetUp: Create a football team and add a goalkeeper with no saves
        
        // Create goalkeeper player
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setSaveAnnouncements(0); // No saves recorded
        
        // Add goalkeeper to team players list
        List<FootballPlayer> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Calculate and verify the result
        int result = team.countGoalkeeperSaveAnnouncements();
        assertEquals("Total goal-saving announcements should be 0 for goalkeeper with no saves", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // Test Case 2: Calculate goal-saving announcements for goalkeeper who made one save
        // SetUp: Create a football team and add a goalkeeper with one save
        
        // Create goalkeeper player
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setSaveAnnouncements(1); // One save recorded
        
        // Add goalkeeper to team players list
        List<FootballPlayer> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Calculate and verify the result
        int result = team.countGoalkeeperSaveAnnouncements();
        assertEquals("Total goal-saving announcements should be 1 for goalkeeper with one save", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // Test Case 3: Calculate goal-saving announcements for goalkeeper who made multiple saves
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        
        // Create goalkeeper player
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setSaveAnnouncements(3); // Three saves recorded
        
        // Add goalkeeper to team players list
        List<FootballPlayer> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Calculate and verify the result
        int result = team.countGoalkeeperSaveAnnouncements();
        assertEquals("Total goal-saving announcements should be 3 for goalkeeper with three saves", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // Test Case 4: Calculate goal-saving announcements for goalkeeper who made consecutive saves
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        
        // Create goalkeeper player
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setSaveAnnouncements(5); // Five consecutive saves recorded
        
        // Add goalkeeper to team players list
        List<FootballPlayer> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Calculate and verify the result
        int result = team.countGoalkeeperSaveAnnouncements();
        assertEquals("Total goal-saving announcements should be 5 for goalkeeper with five consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // Test Case 5: Calculate goal-saving announcements for goalkeeper with some invalid entries
        // SetUp: Create a football team and add a goalkeeper with mixed announcements
        
        // Create goalkeeper player
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setSaveAnnouncements(2); // Two save announcements
        goalkeeper.setGoalAnnouncements(1); // One goal announcement (should not be counted)
        
        // Add goalkeeper to team players list
        List<FootballPlayer> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Calculate and verify the result
        int result = team.countGoalkeeperSaveAnnouncements();
        assertEquals("Total goal-saving announcements should be 2 (goal announcements should not be counted)", 2, result);
    }
}