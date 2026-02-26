import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Initialize a new team before each test
        team = new FootballTeam();
        team.setStartingEleven(new ArrayList<>());
        team.setSpareTeam(new ArrayList<>());
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Test Case 1: Total Announcements for Single Forward Player with One Score
        // SetUp: Create team "Turgutlu", forward player "John" with number 9, announce score for John
        team.setStartingEleven(new ArrayList<>());
        
        FootballPlayer john = new FootballPlayer("Turgutlu", "John", 25, 9, Position.FORWARD);
        john.scoreGoal(); // Announce a score for John
        team.getStartingEleven().add(john);
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 1
        assertEquals("Total announcements should be 1 for single forward with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Test Case 2: Total Announcements for Multiple Forward Players with Scores
        // SetUp: Create team "Turgutlu", forward players "Alice" (10) and "Bob" (11), announce scores for both
        team.setStartingEleven(new ArrayList<>());
        
        FootballPlayer alice = new FootballPlayer("Turgutlu", "Alice", 24, 10, Position.FORWARD);
        FootballPlayer bob = new FootballPlayer("Turgutlu", "Bob", 26, 11, Position.FORWARD);
        
        alice.scoreGoal(); // Announce score for Alice
        bob.scoreGoal();   // Announce score for Bob
        
        team.getStartingEleven().add(alice);
        team.getStartingEleven().add(bob);
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 2
        assertEquals("Total announcements should be 2 for two forwards with one score each", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // Test Case 3: Total Announcements for Forward Players with Saves
        // SetUp: Create team "Turgutlu", forward player "Tom" (7), goalkeeper "Mike" (1), announce save for Mike
        team.setStartingEleven(new ArrayList<>());
        team.setSpareTeam(new ArrayList<>());
        
        FootballPlayer tom = new FootballPlayer("Turgutlu", "Tom", 23, 7, Position.FORWARD);
        FootballPlayer mike = new FootballPlayer("Turgutlu", "Mike", 28, 1, Position.DEFENSE);
        
        mike.saveGoal(); // Announce save for Mike (goalkeeper)
        
        team.getStartingEleven().add(tom);
        team.getStartingEleven().add(mike);
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals("Total announcements should be 0 when forwards have no score announcements", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Test Case 4: Total Announcements for Forward Players with Scores and Saves
        // SetUp: Create team "Turgutlu", forward players "Emma" (8) and "Lucas" (12), announce score for Emma, save for Mike
        team.setStartingEleven(new ArrayList<>());
        team.setSpareTeam(new ArrayList<>());
        
        FootballPlayer emma = new FootballPlayer("Turgutlu", "Emma", 22, 8, Position.FORWARD);
        FootballPlayer lucas = new FootballPlayer("Turgutlu", "Lucas", 24, 12, Position.FORWARD);
        FootballPlayer mike = new FootballPlayer("Turgutlu", "Mike", 28, 1, Position.DEFENSE);
        
        emma.scoreGoal(); // Announce score for Emma
        mike.saveGoal();  // Announce save for Mike (goalkeeper)
        
        team.getStartingEleven().add(emma);
        team.getStartingEleven().add(lucas);
        team.getStartingEleven().add(mike);
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals("Total announcements should be 1 for forward with score (saves don't count)", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // Test Case 5: Total Announcements with No Forward Players
        // SetUp: Create team "Turgutlu", midfield player "Noah" (5) and defender "Liam" (4)
        team.setStartingEleven(new ArrayList<>());
        
        FootballPlayer noah = new FootballPlayer("Turgutlu", "Noah", 26, 5, Position.MIDFIELD);
        FootballPlayer liam = new FootballPlayer("Turgutlu", "Liam", 27, 4, Position.DEFENSE);
        
        noah.scoreGoal(); // Announce score for Noah (midfielder - should not count for forwards)
        liam.saveGoal();  // Announce save for Liam (defender - should not count for forwards)
        
        team.getStartingEleven().add(noah);
        team.getStartingEleven().add(liam);
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 0 (No forward player available)
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}