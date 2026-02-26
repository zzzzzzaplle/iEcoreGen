import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Create a new team before each test
        team = new FootballTeam();
        team.setTeam("Turgutlu");
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Test Case 1: Total Announcements for Single Forward Player with One Score
        // Create a forward player named "John" with number 9
        FootballPlayer john = new FootballPlayer();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        // Announce a score for John
        john.scoreGoal();
        
        // Add player to team (both starting eleven and spare team should be considered)
        team.addPlayerToStartingEleven(john);
        
        // Calculate total forward announcements
        int result = team.calculateTotalForwardAnnouncements();
        
        // Expected Output: Total announcements = 1
        assertEquals("Total announcements should be 1 for single forward with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Test Case 2: Total Announcements for Multiple Forward Players with Scores
        // Create forward players: "Alice" (number 10), "Bob" (number 11)
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
        team.addPlayerToStartingEleven(alice);
        team.addPlayerToSpareTeam(bob);
        
        // Calculate total forward announcements
        int result = team.calculateTotalForwardAnnouncements();
        
        // Expected Output: Total announcements = 2
        assertEquals("Total announcements should be 2 for multiple forwards with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // Test Case 3: Total Announcements for Forward Players with Saves
        // Create a forward player named "Tom" with number 7
        FootballPlayer tom = new FootballPlayer();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        
        // Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        // Announce a save for Mike (goalkeeper)
        mike.saveGoal();
        
        // Add players to team
        team.addPlayerToStartingEleven(tom);
        team.addPlayerToStartingEleven(mike);
        
        // Calculate total forward announcements
        int result = team.calculateTotalForwardAnnouncements();
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals("Total announcements should be 0 when forwards have no scores", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Test Case 4: Total Announcements for Forward Players with Scores and Saves
        // Create forward players: "Emma" (number 8), "Lucas" (number 12)
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
        team.addPlayerToStartingEleven(emma);
        team.addPlayerToSpareTeam(lucas);
        team.addPlayerToStartingEleven(mike);
        
        // Calculate total forward announcements
        int result = team.calculateTotalForwardAnnouncements();
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals("Total announcements should be 1 for Emma's score (saves don't count)", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // Test Case 5: Total Announcements with No Forward Players
        // Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        FootballPlayer noah = new FootballPlayer();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        
        FootballPlayer liam = new FootballPlayer();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        
        // Announce some saves and scores for others, but none for forwards
        noah.scoreGoal(); // Midfield score (shouldn't count)
        liam.scoreGoal(); // Defense score (shouldn't count)
        
        // Add players to team
        team.addPlayerToStartingEleven(noah);
        team.addPlayerToSpareTeam(liam);
        
        // Calculate total forward announcements
        int result = team.calculateTotalForwardAnnouncements();
        
        // Expected Output: Total announcements = 0 (No forward player available)
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}