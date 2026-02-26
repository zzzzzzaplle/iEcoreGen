import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Create a new football team before each test
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Test Case 1: Total Announcements for Single Forward Player with One Score
        // SetUp: Create a football team named "Turgutlu"
        // Create a forward player named "John" with number 9
        FootballPlayer john = new FootballPlayer();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        // Add player to team
        team.addPlayer(john);
        
        // Set John as part of starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(john);
        team.setStartingEleven(startingEleven);
        
        // Announce a score for John
        john.announceGoalScoring();
        
        // Calculate total announcements
        int result = team.calculateGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        assertEquals("Total announcements should be 1 for single forward with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Test Case 2: Total Announcements for Multiple Forward Players with Scores
        // SetUp: Create a football team named "Turgutlu"
        // Create forward players: "Alice" (number 10), "Bob" (number 11)
        FootballPlayer alice = new FootballPlayer();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        
        FootballPlayer bob = new FootballPlayer();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        
        // Add players to team
        team.addPlayer(alice);
        team.addPlayer(bob);
        
        // Set players as part of starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(alice);
        startingEleven.add(bob);
        team.setStartingEleven(startingEleven);
        
        // Announce a score for Alice and a score for Bob
        alice.announceGoalScoring();
        bob.announceGoalScoring();
        
        // Calculate total announcements
        int result = team.calculateGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        assertEquals("Total announcements should be 2 for multiple forwards with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // Test Case 3: Total Announcements for Forward Players with Saves
        // SetUp: Create a football team named "Turgutlu"
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
        
        // Add players to team
        team.addPlayer(tom);
        team.addPlayer(mike);
        
        // Set Tom as part of starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(tom);
        team.setStartingEleven(startingEleven);
        
        // Announce a save for Mike (goalkeeper)
        mike.announceGoalSaving();
        
        // Calculate total announcements
        int result = team.calculateGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals("Total announcements should be 0 when only saves occur", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Test Case 4: Total Announcements for Forward Players with Scores and Saves
        // SetUp: Create a football team named "Turgutlu"
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
        
        // Add players to team
        team.addPlayer(emma);
        team.addPlayer(lucas);
        team.addPlayer(mike);
        
        // Set Emma and Lucas as part of starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(emma);
        startingEleven.add(lucas);
        team.setStartingEleven(startingEleven);
        
        // Announce a score for Emma
        emma.announceGoalScoring();
        
        // Announce a save for Mike (goalkeeper)
        mike.announceGoalSaving();
        
        // Calculate total announcements
        int result = team.calculateGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals("Total announcements should be 1 (only scores count, saves don't)", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // Test Case 5: Total Announcements with No Forward Players
        // SetUp: Create a football team named "Turgutlu"
        // Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        FootballPlayer noah = new FootballPlayer();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        
        FootballPlayer liam = new FootballPlayer();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        
        // Add players to team
        team.addPlayer(noah);
        team.addPlayer(liam);
        
        // Set Noah and Liam as part of starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(noah);
        startingEleven.add(liam);
        team.setStartingEleven(startingEleven);
        
        // Announce some saves and scores for others, but none for forwards
        noah.announceGoalScoring(); // This won't count since Noah is midfield
        liam.announceGoalScoring(); // This won't count since Liam is defense
        
        // Calculate total announcements
        int result = team.calculateGoalScoringAnnouncements();
        
        // Expected Output: Total announcements = 0 (No forward player available)
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}