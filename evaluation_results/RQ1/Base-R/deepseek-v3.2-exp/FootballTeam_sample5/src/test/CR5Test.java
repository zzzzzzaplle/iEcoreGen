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
        team.setPlayers(new ArrayList<>());
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // SetUp: Create a forward player named "John" with number 9
        FootballPlayer john = new FootballPlayer();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        john.setGoalsScored(1); // Announce a score for John
        
        // Add player to team
        List<FootballPlayer> players = new ArrayList<>();
        players.add(john);
        team.setPlayers(players);
        
        // Expected Output: Total announcements = 1
        int result = team.calculateTotalForwardAnnouncements();
        assertEquals("Total announcements should be 1 for single forward with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // SetUp: Create forward players: "Alice" (number 10), "Bob" (number 11)
        FootballPlayer alice = new FootballPlayer();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        alice.setGoalsScored(1); // Announce a score for Alice
        
        FootballPlayer bob = new FootballPlayer();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        bob.setGoalsScored(1); // Announce a score for Bob
        
        // Add players to team
        List<FootballPlayer> players = new ArrayList<>();
        players.add(alice);
        players.add(bob);
        team.setPlayers(players);
        
        // Expected Output: Total announcements = 2
        int result = team.calculateTotalForwardAnnouncements();
        assertEquals("Total announcements should be 2 for multiple forwards with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // SetUp: Create a forward player named "Tom" with number 7
        FootballPlayer tom = new FootballPlayer();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        tom.setGoalsScored(0); // No scores for Tom
        
        // Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        mike.setGoalsSaved(1); // Announce a save for Mike
        
        // Add players to team
        List<FootballPlayer> players = new ArrayList<>();
        players.add(tom);
        players.add(mike);
        team.setPlayers(players);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        int result = team.calculateTotalForwardAnnouncements();
        assertEquals("Total announcements should be 0 when no forward players have scores", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // SetUp: Create forward players: "Emma" (number 8), "Lucas" (number 12)
        FootballPlayer emma = new FootballPlayer();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        emma.setGoalsScored(1); // Announce a score for Emma
        
        FootballPlayer lucas = new FootballPlayer();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        lucas.setGoalsScored(0); // No score for Lucas
        
        // Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        mike.setGoalsSaved(1); // Announce a save for Mike
        
        // Add players to team
        List<FootballPlayer> players = new ArrayList<>();
        players.add(emma);
        players.add(lucas);
        players.add(mike);
        team.setPlayers(players);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        int result = team.calculateTotalForwardAnnouncements();
        assertEquals("Total announcements should be 1 for forward with score, saves don't count", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // SetUp: Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        FootballPlayer noah = new FootballPlayer();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        noah.setGoalsScored(1); // Score for midfielder (shouldn't count)
        
        FootballPlayer liam = new FootballPlayer();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        liam.setGoalsScored(1); // Score for defender (shouldn't count)
        
        // Add players to team
        List<FootballPlayer> players = new ArrayList<>();
        players.add(noah);
        players.add(liam);
        team.setPlayers(players);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        int result = team.calculateTotalForwardAnnouncements();
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}