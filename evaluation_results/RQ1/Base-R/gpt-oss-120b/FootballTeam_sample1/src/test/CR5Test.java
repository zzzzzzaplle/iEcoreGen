import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Create a football team named "Turgutlu" before each test
        team = new FootballTeam("Turgutlu");
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // SetUp: Create a forward player named "John" with number 9
        FootballPlayer john = new FootballPlayer("Turgutlu", "John", 25, 9, Position.FORWARD);
        team.addPlayer(john);
        
        // Announce a score for John
        john.announceGoalScored();
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        assertEquals("Total announcements should be 1 for single forward with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // SetUp: Create forward players: "Alice" (number 10), "Bob" (number 11)
        FootballPlayer alice = new FootballPlayer("Turgutlu", "Alice", 24, 10, Position.FORWARD);
        FootballPlayer bob = new FootballPlayer("Turgutlu", "Bob", 26, 11, Position.FORWARD);
        
        team.addPlayer(alice);
        team.addPlayer(bob);
        
        // Announce a score for Alice and a score for Bob
        alice.announceGoalScored();
        bob.announceGoalScored();
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        assertEquals("Total announcements should be 2 for multiple forwards with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // SetUp: Create a forward player named "Tom" with number 7
        FootballPlayer tom = new FootballPlayer("Turgutlu", "Tom", 23, 7, Position.FORWARD);
        // Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer("Turgutlu", "Mike", 28, 1, Position.GOALKEEPER);
        
        team.addPlayer(tom);
        team.addPlayer(mike);
        
        // Announce a save for Mike
        mike.announceGoalSaved();
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals("Total announcements should be 0 when forwards have no scores", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // SetUp: Create forward players: "Emma" (number 8), "Lucas" (number 12)
        FootballPlayer emma = new FootballPlayer("Turgutlu", "Emma", 22, 8, Position.FORWARD);
        FootballPlayer lucas = new FootballPlayer("Turgutlu", "Lucas", 24, 12, Position.FORWARD);
        // Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer("Turgutlu", "Mike", 28, 1, Position.GOALKEEPER);
        
        team.addPlayer(emma);
        team.addPlayer(lucas);
        team.addPlayer(mike);
        
        // Announce a score for Emma
        emma.announceGoalScored();
        // Announce a save for Mike (goalkeeper)
        mike.announceGoalSaved();
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals("Total announcements should be 1 (only forward scores count)", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // SetUp: Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        FootballPlayer noah = new FootballPlayer("Turgutlu", "Noah", 26, 5, Position.MIDFIELD);
        FootballPlayer liam = new FootballPlayer("Turgutlu", "Liam", 27, 4, Position.DEFENSE);
        // Create a goalkeeper for saves
        FootballPlayer goalkeeper = new FootballPlayer("Turgutlu", "Keeper", 30, 1, Position.GOALKEEPER);
        
        team.addPlayer(noah);
        team.addPlayer(liam);
        team.addPlayer(goalkeeper);
        
        // Announce some saves and scores for others, but none for forwards
        goalkeeper.announceGoalSaved();
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 0 (No forward player available)
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}