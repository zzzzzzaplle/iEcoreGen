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
        john.setGoalsScored(1);
        
        // Add player to starting eleven
        team.getStartingEleven().add(john);
        
        // Calculate announcements
        int result = team.calculateStartingElevenForwardAnnouncements();
        
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
        alice.setGoalsScored(1);
        
        FootballPlayer bob = new FootballPlayer();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        bob.setGoalsScored(1);
        
        // Add players to starting eleven
        team.getStartingEleven().add(alice);
        team.getStartingEleven().add(bob);
        
        // Calculate announcements
        int result = team.calculateStartingElevenForwardAnnouncements();
        
        // Expected Output: Total announcements = 2
        assertEquals("Total announcements should be 2 for two forwards with one score each", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // SetUp: Create a forward player named "Tom" with number 7
        FootballPlayer tom = new FootballPlayer();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        tom.setGoalsScored(0); // No goals scored
        
        // Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        mike.setGoalsSaved(1); // Save announced
        
        // Add players to starting eleven
        team.getStartingEleven().add(tom);
        team.getStartingEleven().add(mike);
        
        // Calculate announcements
        int result = team.calculateStartingElevenForwardAnnouncements();
        
        // Expected Output: Total announcements = 0
        assertEquals("Total announcements should be 0 when forward players have no scores", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // SetUp: Create forward players: "Emma" (number 8), "Lucas" (number 12)
        FootballPlayer emma = new FootballPlayer();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        emma.setGoalsScored(1); // Score announced
        
        FootballPlayer lucas = new FootballPlayer();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        lucas.setGoalsScored(0); // No score
        
        // Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        mike.setGoalsSaved(1); // Save announced
        
        // Add players to starting eleven
        team.getStartingEleven().add(emma);
        team.getStartingEleven().add(lucas);
        team.getStartingEleven().add(mike);
        
        // Calculate announcements
        int result = team.calculateStartingElevenForwardAnnouncements();
        
        // Expected Output: Total announcements = 1
        assertEquals("Total announcements should be 1 for one forward with score (saves don't count)", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // SetUp: Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        FootballPlayer noah = new FootballPlayer();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        noah.setGoalsScored(2); // Scores don't count (not forward)
        
        FootballPlayer liam = new FootballPlayer();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        liam.setGoalsScored(1); // Scores don't count (not forward)
        
        // Add players to starting eleven
        team.getStartingEleven().add(noah);
        team.getStartingEleven().add(liam);
        
        // Calculate announcements
        int result = team.calculateStartingElevenForwardAnnouncements();
        
        // Expected Output: Total announcements = 0
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}