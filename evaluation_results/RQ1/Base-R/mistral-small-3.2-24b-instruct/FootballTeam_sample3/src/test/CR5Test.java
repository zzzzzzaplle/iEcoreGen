import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Create a football team named "Turgutlu" before each test
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Test Case 1: Total Announcements for Single Forward Player with One Score
        // SetUp: Create a forward player named "John" with number 9
        FootballPlayer john = new FootballPlayer();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        // Announce a score for John
        john.incrementGoalScoringAnnouncements();
        
        // Add player to team
        team.addPlayer(john);
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        assertEquals(1, team.calculateTotalAnnouncementsByForwards());
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Test Case 2: Total Announcements for Multiple Forward Players with Scores
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
        alice.incrementGoalScoringAnnouncements();
        bob.incrementGoalScoringAnnouncements();
        
        // Add players to team
        team.addPlayer(alice);
        team.addPlayer(bob);
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        assertEquals(2, team.calculateTotalAnnouncementsByForwards());
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // Test Case 3: Total Announcements for Forward Players with Saves
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
        mike.incrementGoalSavingAnnouncements();
        
        // Add players to team
        team.addPlayer(tom);
        team.addPlayer(mike);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals(0, team.calculateTotalAnnouncementsByForwards());
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Test Case 4: Total Announcements for Forward Players with Scores and Saves
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
        emma.incrementGoalScoringAnnouncements();
        
        // Announce a save for Mike (goalkeeper)
        mike.incrementGoalSavingAnnouncements();
        
        // Add players to team
        team.addPlayer(emma);
        team.addPlayer(lucas);
        team.addPlayer(mike);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals(1, team.calculateTotalAnnouncementsByForwards());
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // Test Case 5: Total Announcements with No Forward Players
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
        noah.incrementGoalScoringAnnouncements();
        liam.incrementGoalSavingAnnouncements();
        
        // Add players to team
        team.addPlayer(noah);
        team.addPlayer(liam);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        assertEquals(0, team.calculateTotalAnnouncementsByForwards());
    }
}