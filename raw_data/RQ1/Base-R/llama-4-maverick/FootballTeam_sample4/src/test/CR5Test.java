import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Initialize a new FootballTeam before each test
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Test Case 1: "Total Announcements for Single Forward Player with One Score"
        // SetUp: Create a football team named "Turgutlu"
        team.setStartingEleven(new ArrayList<>());
        team.setSpareTeam(new ArrayList<>());
        
        // Create a forward player named "John" with number 9
        Player john = new Player("Turgutlu", "John", 25, 9, Position.FORWARD);
        team.getStartingEleven().add(john);
        
        // Announce a score for John (this means we count forward players)
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Test Case 2: "Total Announcements for Multiple Forward Players with Scores"
        // SetUp: Create a football team named "Turgutlu"
        team.setStartingEleven(new ArrayList<>());
        team.setSpareTeam(new ArrayList<>());
        
        // Create forward players: "Alice" (number 10), "Bob" (number 11)
        Player alice = new Player("Turgutlu", "Alice", 23, 10, Position.FORWARD);
        Player bob = new Player("Turgutlu", "Bob", 24, 11, Position.FORWARD);
        team.getStartingEleven().add(alice);
        team.getStartingEleven().add(bob);
        
        // Announce a score for Alice and a score for Bob (count both forward players)
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // Test Case 3: "Total Announcements for Forward Players with Saves"
        // SetUp: Create a football team named "Turgutlu"
        team.setStartingEleven(new ArrayList<>());
        team.setSpareTeam(new ArrayList<>());
        
        // Create a forward player named "Tom" with number 7
        Player tom = new Player("Turgutlu", "Tom", 26, 7, Position.FORWARD);
        team.getStartingEleven().add(tom);
        
        // Create a goalkeeper named "Mike" with number 1
        Player mike = new Player("Turgutlu", "Mike", 30, 1, Position.DEFENSE);
        team.getStartingEleven().add(mike);
        
        // Announce a save for Mike (only defense players count for saves, not forwards)
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Test Case 4: "Total Announcements for Forward Players with Scores and Saves"
        // SetUp: Create a football team named "Turgutlu"
        team.setStartingEleven(new ArrayList<>());
        team.setSpareTeam(new ArrayList<>());
        
        // Create forward players: "Emma" (number 8), "Lucas" (number 12)
        Player emma = new Player("Turgutlu", "Emma", 22, 8, Position.FORWARD);
        Player lucas = new Player("Turgutlu", "Lucas", 21, 12, Position.FORWARD);
        team.getStartingEleven().add(emma);
        team.getStartingEleven().add(lucas);
        
        // Create a goalkeeper named "Mike" with number 1
        Player mike = new Player("Turgutlu", "Mike", 30, 1, Position.DEFENSE);
        team.getStartingEleven().add(mike);
        
        // Announce a score for Emma (counts as 1 forward announcement)
        // Announce a save for Mike (does not count for forward announcements)
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // Test Case 5: "Total Announcements with No Forward Players"
        // SetUp: Create a football team named "Turgutlu"
        team.setStartingEleven(new ArrayList<>());
        team.setSpareTeam(new ArrayList<>());
        
        // Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        Player noah = new Player("Turgutlu", "Noah", 28, 5, Position.MIDFIELD);
        Player liam = new Player("Turgutlu", "Liam", 27, 4, Position.DEFENSE);
        team.getStartingEleven().add(noah);
        team.getStartingEleven().add(liam);
        
        // Announce some saves and scores for others, but none for forwards
        // (No forward players exist, so count should be 0)
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 0 (No forward player available)
        assertEquals(0, result);
    }
}