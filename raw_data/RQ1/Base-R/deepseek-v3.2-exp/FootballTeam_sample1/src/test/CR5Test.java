import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        team.setName("Turgutlu");
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Test Case 1: "Total Announcements for Single Forward Player with One Score"
        // Input: Calculate announcements for forward players where one player has scored.
        
        // SetUp: 
        // 1. Create a football team named "Turgutlu"
        // 2. Create a forward player named "John" with number 9
        FootballPlayer john = new FootballPlayer();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        // 3. Announce a score for John
        john.scoreGoal();
        
        // Add player to team
        List<FootballPlayer> players = new ArrayList<>();
        players.add(john);
        team.setPlayers(players);
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        int result = team.calculateTotalForwardAnnouncements();
        assertEquals("Total announcements should be 1 for single forward with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Test Case 2: "Total Announcements for Multiple Forward Players with Scores"
        // Input: Calculate announcements for forward players with multiple scores.
        
        // SetUp:
        // 1. Create a football team named "Turgutlu"
        // 2. Create forward players: "Alice" (number 10), "Bob" (number 11)
        FootballPlayer alice = new FootballPlayer();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        
        FootballPlayer bob = new FootballPlayer();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        
        // 3. Announce a score for Alice and a score for Bob
        alice.scoreGoal();
        bob.scoreGoal();
        
        // Add players to team
        List<FootballPlayer> players = new ArrayList<>();
        players.add(alice);
        players.add(bob);
        team.setPlayers(players);
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        int result = team.calculateTotalForwardAnnouncements();
        assertEquals("Total announcements should be 2 for multiple forwards with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // Test Case 3: "Total Announcements for Forward Players with Saves"
        // Input: Calculate announcements for forward players where no scores but one save.
        
        // SetUp:
        // 1. Create a football team named "Turgutlu"
        // 2. Create a forward player named "Tom" with number 7
        FootballPlayer tom = new FootballPlayer();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        
        // 3. Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        // 4. Announce a save for Mike
        mike.saveGoal();
        
        // Add players to team
        List<FootballPlayer> players = new ArrayList<>();
        players.add(tom);
        players.add(mike);
        team.setPlayers(players);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        int result = team.calculateTotalForwardAnnouncements();
        assertEquals("Total announcements should be 0 when forwards have no scores", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Test Case 4: "Total Announcements for Forward Players with Scores and Saves"
        // Input: Calculate announcements for forward players with mixed announcements.
        
        // SetUp:
        // 1. Create a football team named "Turgutlu"
        // 2. Create forward players: "Emma" (number 8), "Lucas" (number 12)
        FootballPlayer emma = new FootballPlayer();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        
        FootballPlayer lucas = new FootballPlayer();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        
        // 3. Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        // 4. Announce a score for Emma
        emma.scoreGoal();
        
        // 5. Announce a save for Mike (goalkeeper)
        mike.saveGoal();
        
        // Add players to team
        List<FootballPlayer> players = new ArrayList<>();
        players.add(emma);
        players.add(lucas);
        players.add(mike);
        team.setPlayers(players);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        int result = team.calculateTotalForwardAnnouncements();
        assertEquals("Total announcements should be 1 for forward score (saves don't count)", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // Test Case 5: "Total Announcements with No Forward Players"
        // Input: Calculate announcements for a team with no forward players.
        
        // SetUp:
        // 1. Create a football team named "Turgutlu"
        // 2. Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        FootballPlayer noah = new FootballPlayer();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        
        FootballPlayer liam = new FootballPlayer();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        
        // 3. Create a goalkeeper for saves
        FootballPlayer keeper = new FootballPlayer();
        keeper.setName("Keeper");
        keeper.setNumber(1);
        keeper.setPosition(Position.GOALKEEPER);
        
        // Announce some saves and scores for others, but none for forwards
        noah.scoreGoal(); // Midfielder scores (shouldn't count for forwards)
        keeper.saveGoal(); // Goalkeeper saves (shouldn't count for forwards)
        
        // Add players to team
        List<FootballPlayer> players = new ArrayList<>();
        players.add(noah);
        players.add(liam);
        players.add(keeper);
        team.setPlayers(players);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        int result = team.calculateTotalForwardAnnouncements();
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}