import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Team team;
    
    @Before
    public void setUp() {
        // Reset team before each test
        team = new Team("Turgutlu");
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // SetUp: Create a football team named "Turgutlu"
        team = new Team("Turgutlu");
        
        // SetUp: Create a forward player named "John" with number 9
        Forward john = new Forward("Turgutlu", "John", 25, 9);
        team.addPlayer(john);
        
        // SetUp: Announce a score for John
        john.scoreGoal();
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        assertEquals(1, team.totalForwardAnnouncements());
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // SetUp: Create a football team named "Turgutlu"
        team = new Team("Turgutlu");
        
        // SetUp: Create forward players: "Alice" (number 10), "Bob" (number 11)
        Forward alice = new Forward("Turgutlu", "Alice", 24, 10);
        Forward bob = new Forward("Turgutlu", "Bob", 26, 11);
        team.addPlayer(alice);
        team.addPlayer(bob);
        
        // SetUp: Announce a score for Alice and a score for Bob
        alice.scoreGoal();
        bob.scoreGoal();
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        assertEquals(2, team.totalForwardAnnouncements());
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // SetUp: Create a football team named "Turgutlu"
        team = new Team("Turgutlu");
        
        // SetUp: Create a forward player named "Tom" with number 7
        Forward tom = new Forward("Turgutlu", "Tom", 23, 7);
        team.addPlayer(tom);
        
        // SetUp: Create a goalkeeper named "Mike" with number 1
        Goalkeeper mike = new Goalkeeper("Turgutlu", "Mike", 28, 1);
        team.addPlayer(mike);
        
        // SetUp: Announce a save for Mike
        mike.saveGoal();
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals(0, team.totalForwardAnnouncements());
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // SetUp: Create a football team named "Turgutlu"
        team = new Team("Turgutlu");
        
        // SetUp: Create forward players: "Emma" (number 8), "Lucas" (number 12)
        Forward emma = new Forward("Turgutlu", "Emma", 22, 8);
        Forward lucas = new Forward("Turgutlu", "Lucas", 25, 12);
        team.addPlayer(emma);
        team.addPlayer(lucas);
        
        // SetUp: Create a goalkeeper named "Mike" with number 1
        Goalkeeper mike = new Goalkeeper("Turgutlu", "Mike", 28, 1);
        team.addPlayer(mike);
        
        // SetUp: Announce a score for Emma
        emma.scoreGoal();
        
        // SetUp: Announce a save for Mike (goalkeeper)
        mike.saveGoal();
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals(1, team.totalForwardAnnouncements());
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // SetUp: Create a football team named "Turgutlu"
        team = new Team("Turgutlu");
        
        // SetUp: Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        Midfielder noah = new Midfielder("Turgutlu", "Noah", 24, 5);
        FootballPlayer liam = new FootballPlayer("Turgutlu", "Liam", 26, 4, Position.DEFENSE);
        team.addPlayer(noah);
        team.addPlayer(liam);
        
        // SetUp: Create a goalkeeper for save announcement
        Goalkeeper gk = new Goalkeeper("Turgutlu", "GK", 30, 1);
        team.addPlayer(gk);
        
        // SetUp: Announce some saves and scores for others, but none for forwards
        gk.saveGoal();
        // Note: Cannot call scoreGoal() on non-forward players as it's not available
        
        // Expected Output: Total announcements = 0 (No forward player available)
        assertEquals(0, team.totalForwardAnnouncements());
    }
}