import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        team.setTeam("Turgutlu");
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // SetUp: Create a forward player named "John" with number 9
        FootballPlayer john = new FootballPlayer();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        // Announce a score for John
        john.scoreGoal();
        
        // Add player to team (both starting eleven and spare team)
        team.getStartingEleven().add(john);
        
        // Calculate total announcements
        int result = team.calculateForwardAnnouncements();
        
        // Expected Output: Total announcements = 1
        assertEquals("Total announcements should be 1 for single forward with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // SetUp: Create forward players: "Alice" (number 10), "Bob" (number 11)
        FootballPlayer alice = new FootballPlayer();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        
        FootballPlayer bob = new FootballPlayer();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        
        // Announce a score for Alice and a score for Bob
        alice.scoreGoal();
        bob.scoreGoal();
        
        // Add players to team
        team.getStartingEleven().add(alice);
        team.getStartingEleven().add(bob);
        
        // Calculate total announcements
        int result = team.calculateForwardAnnouncements();
        
        // Expected Output: Total announcements = 2
        assertEquals("Total announcements should be 2 for multiple forwards with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // SetUp: Create a forward player named "Tom" with number 7
        FootballPlayer tom = new FootballPlayer();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        
        // Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        // Announce a save for Mike
        mike.saveGoal();
        
        // Add players to team
        team.getStartingEleven().add(tom);
        team.getStartingEleven().add(mike);
        
        // Calculate total announcements
        int result = team.calculateForwardAnnouncements();
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals("Total announcements should be 0 when forward players have no scores", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // SetUp: Create forward players: "Emma" (number 8), "Lucas" (number 12)
        FootballPlayer emma = new FootballPlayer();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        
        FootballPlayer lucas = new FootballPlayer();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        
        // Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        // Announce a score for Emma
        emma.scoreGoal();
        
        // Announce a save for Mike (goalkeeper)
        mike.saveGoal();
        
        // Add players to team
        team.getStartingEleven().add(emma);
        team.getStartingEleven().add(lucas);
        team.getStartingEleven().add(mike);
        
        // Calculate total announcements
        int result = team.calculateForwardAnnouncements();
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals("Total announcements should be 1 (only Emma's score counts)", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // SetUp: Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        FootballPlayer noah = new FootballPlayer();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        
        FootballPlayer liam = new FootballPlayer();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        
        // Announce some saves and scores for others, but none for forwards
        noah.scoreGoal(); // Midfield score
        liam.scoreGoal(); // Defense score
        
        // Add players to team
        team.getStartingEleven().add(noah);
        team.getStartingEleven().add(liam);
        
        // Calculate total announcements
        int result = team.calculateForwardAnnouncements();
        
        // Expected Output: Total announcements = 0 (No forward player available)
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}