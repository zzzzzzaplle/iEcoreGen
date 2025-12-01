import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Create a new team before each test
        team = new FootballTeam();
        team.setStartingEleven(new ArrayList<FootballPlayer>());
        team.setSpareTeam(new ArrayList<FootballPlayer>());
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // SetUp: Create a football team named "Turgutlu"
        team.setStartingEleven(new ArrayList<FootballPlayer>());
        
        // Create a forward player named "John" with number 9
        FootballPlayer john = new FootballPlayer("Turgutlu", "John", 25, 9, Position.FORWARD, Duty.STRIKER);
        team.getStartingEleven().add(john);
        
        // Announce a score for John
        john.scoreGoal();
        
        // Calculate total announcements
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 1
        assertEquals("Total announcements should be 1 for single forward with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // SetUp: Create a football team named "Turgutlu"
        team.setStartingEleven(new ArrayList<FootballPlayer>());
        
        // Create forward players: "Alice" (number 10), "Bob" (number 11)
        FootballPlayer alice = new FootballPlayer("Turgutlu", "Alice", 23, 10, Position.FORWARD, Duty.STRIKER);
        FootballPlayer bob = new FootballPlayer("Turgutlu", "Bob", 24, 11, Position.FORWARD, Duty.STRIKER);
        team.getStartingEleven().add(alice);
        team.getStartingEleven().add(bob);
        
        // Announce a score for Alice and a score for Bob
        alice.scoreGoal();
        bob.scoreGoal();
        
        // Calculate total announcements
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 2
        assertEquals("Total announcements should be 2 for multiple forwards with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // SetUp: Create a football team named "Turgutlu"
        team.setStartingEleven(new ArrayList<FootballPlayer>());
        
        // Create a forward player named "Tom" with number 7
        FootballPlayer tom = new FootballPlayer("Turgutlu", "Tom", 26, 7, Position.FORWARD, Duty.STRIKER);
        // Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer("Turgutlu", "Mike", 28, 1, Position.DEFENSE, Duty.GOALKEEPER);
        team.getStartingEleven().add(tom);
        team.getStartingEleven().add(mike);
        
        // Announce a save for Mike
        mike.saveGoal();
        
        // Calculate total announcements
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 0
        assertEquals("Total announcements should be 0 when forwards have no scores", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // SetUp: Create a football team named "Turgutlu"
        team.setStartingEleven(new ArrayList<FootballPlayer>());
        team.setSpareTeam(new ArrayList<FootballPlayer>());
        
        // Create forward players: "Emma" (number 8), "Lucas" (number 12)
        FootballPlayer emma = new FootballPlayer("Turgutlu", "Emma", 22, 8, Position.FORWARD, Duty.STRIKER);
        FootballPlayer lucas = new FootballPlayer("Turgutlu", "Lucas", 21, 12, Position.FORWARD, Duty.STRIKER);
        // Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer("Turgutlu", "Mike", 28, 1, Position.DEFENSE, Duty.GOALKEEPER);
        
        team.getStartingEleven().add(emma);
        team.getStartingEleven().add(lucas);
        team.getStartingEleven().add(mike);
        
        // Announce a score for Emma
        emma.scoreGoal();
        // Announce a save for Mike (goalkeeper)
        mike.saveGoal();
        
        // Calculate total announcements
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 1
        assertEquals("Total announcements should be 1 (only Emma's score counts)", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // SetUp: Create a football team named "Turgutlu"
        team.setStartingEleven(new ArrayList<FootballPlayer>());
        team.setSpareTeam(new ArrayList<FootballPlayer>());
        
        // Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        FootballPlayer noah = new FootballPlayer("Turgutlu", "Noah", 27, 5, Position.MIDFIELD, Duty.MIDFIELDER);
        FootballPlayer liam = new FootballPlayer("Turgutlu", "Liam", 29, 4, Position.DEFENSE, Duty.MIDFIELDER);
        FootballPlayer goalkeeper = new FootballPlayer("Turgutlu", "GK", 30, 1, Position.DEFENSE, Duty.GOALKEEPER);
        
        team.getStartingEleven().add(noah);
        team.getStartingEleven().add(liam);
        team.getStartingEleven().add(goalkeeper);
        
        // Announce some saves and scores for others, but none for forwards
        noah.scoreGoal(); // This won't count as announcement since he's not a striker
        goalkeeper.saveGoal();
        
        // Calculate total announcements
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 0
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}