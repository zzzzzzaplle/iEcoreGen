import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Create a new team before each test
        team = new FootballTeam();
        team.setName("Turgutlu");
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Test Case 1: Total Announcements for Single Forward Player with One Score
        // SetUp: Create a forward player named "John" with number 9 and announce a score
        FootballPlayer john = new FootballPlayer();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        john.setGoalAnnouncements(1); // Simulate one goal announcement
        
        team.addPlayer(john);
        
        // Calculate total announcements by all forwards
        int result = team.totalAnnouncementsByAllForwards();
        
        // Expected Output: Total announcements = 1
        assertEquals("Total announcements should be 1 for single forward with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Test Case 2: Total Announcements for Multiple Forward Players with Scores
        // SetUp: Create forward players "Alice" (number 10) and "Bob" (number 11)
        FootballPlayer alice = new FootballPlayer();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        alice.setGoalAnnouncements(1); // One goal announcement for Alice
        
        FootballPlayer bob = new FootballPlayer();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        bob.setGoalAnnouncements(1); // One goal announcement for Bob
        
        team.addPlayer(alice);
        team.addPlayer(bob);
        
        // Calculate total announcements by all forwards
        int result = team.totalAnnouncementsByAllForwards();
        
        // Expected Output: Total announcements = 2
        assertEquals("Total announcements should be 2 for two forwards with one score each", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // Test Case 3: Total Announcements for Forward Players with Saves
        // SetUp: Create forward player "Tom" (number 7) and goalkeeper "Mike" (number 1)
        FootballPlayer tom = new FootballPlayer();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        tom.setGoalAnnouncements(0); // No goal announcements for Tom
        
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        mike.setSaveAnnouncements(1); // One save announcement for Mike (goalkeeper)
        
        team.addPlayer(tom);
        team.addPlayer(mike);
        
        // Calculate total announcements by all forwards
        int result = team.totalAnnouncementsByAllForwards();
        
        // Expected Output: Total announcements = 0 (saves do not count for forwards)
        assertEquals("Total announcements should be 0 when forwards have no goal announcements", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Test Case 4: Total Announcements for Forward Players with Scores and Saves
        // SetUp: Create forward players "Emma" (number 8) and "Lucas" (number 12)
        FootballPlayer emma = new FootballPlayer();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        emma.setGoalAnnouncements(1); // One goal announcement for Emma
        
        FootballPlayer lucas = new FootballPlayer();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        lucas.setGoalAnnouncements(0); // No goal announcements for Lucas
        
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        mike.setSaveAnnouncements(1); // One save announcement for Mike (goalkeeper)
        
        team.addPlayer(emma);
        team.addPlayer(lucas);
        team.addPlayer(mike);
        
        // Calculate total announcements by all forwards
        int result = team.totalAnnouncementsByAllForwards();
        
        // Expected Output: Total announcements = 1 (only Emma's goal counts)
        assertEquals("Total announcements should be 1 when only one forward has a goal announcement", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // Test Case 5: Total Announcements with No Forward Players
        // SetUp: Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        FootballPlayer noah = new FootballPlayer();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELDER);
        noah.setGoalAnnouncements(1); // Goal announcement for midfielder (shouldn't count)
        
        FootballPlayer liam = new FootballPlayer();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        liam.setGoalAnnouncements(0); // No goal announcements for defender
        
        team.addPlayer(noah);
        team.addPlayer(liam);
        
        // Calculate total announcements by all forwards
        int result = team.totalAnnouncementsByAllForwards();
        
        // Expected Output: Total announcements = 0 (no forward players available)
        assertEquals("Total announcements should be 0 when there are no forward players", 0, result);
    }
}