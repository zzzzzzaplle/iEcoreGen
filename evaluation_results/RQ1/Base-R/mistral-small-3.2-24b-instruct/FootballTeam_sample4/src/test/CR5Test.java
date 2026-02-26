import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Initialize a new team before each test
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // SetUp: Create a football team named "Turgutlu"
        team.setPlayers(new ArrayList<>());
        
        // Create a forward player named "John" with number 9
        FootballPlayer john = new FootballPlayer();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        john.setGoalAnnouncements(0);
        team.addPlayer(john);
        
        // Announce a score for John
        john.announceGoal();
        
        // Calculate total forward announcements
        int result = team.calculateTotalForwardAnnouncements();
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        assertEquals("Total announcements should be 1 for single forward with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // SetUp: Create a football team named "Turgutlu"
        team.setPlayers(new ArrayList<>());
        
        // Create forward players: "Alice" (number 10), "Bob" (number 11)
        FootballPlayer alice = new FootballPlayer();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        alice.setGoalAnnouncements(0);
        team.addPlayer(alice);
        
        FootballPlayer bob = new FootballPlayer();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        bob.setGoalAnnouncements(0);
        team.addPlayer(bob);
        
        // Announce a score for Alice and a score for Bob
        alice.announceGoal();
        bob.announceGoal();
        
        // Calculate total forward announcements
        int result = team.calculateTotalForwardAnnouncements();
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        assertEquals("Total announcements should be 2 for multiple forwards with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // SetUp: Create a football team named "Turgutlu"
        team.setPlayers(new ArrayList<>());
        
        // Create a forward player named "Tom" with number 7
        FootballPlayer tom = new FootballPlayer();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        tom.setGoalAnnouncements(0);
        team.addPlayer(tom);
        
        // Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.DEFENSE);
        mike.setGoalSavingAnnouncements(0);
        team.addPlayer(mike);
        
        // Announce a save for Mike
        mike.announceGoalSaved();
        
        // Calculate total forward announcements
        int result = team.calculateTotalForwardAnnouncements();
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals("Total announcements should be 0 when only saves by non-forwards", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // SetUp: Create a football team named "Turgutlu"
        team.setPlayers(new ArrayList<>());
        
        // Create forward players: "Emma" (number 8), "Lucas" (number 12)
        FootballPlayer emma = new FootballPlayer();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        emma.setGoalAnnouncements(0);
        team.addPlayer(emma);
        
        FootballPlayer lucas = new FootballPlayer();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        lucas.setGoalAnnouncements(0);
        team.addPlayer(lucas);
        
        // Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.DEFENSE);
        mike.setGoalSavingAnnouncements(0);
        team.addPlayer(mike);
        
        // Announce a score for Emma
        emma.announceGoal();
        
        // Announce a save for Mike (goalkeeper)
        mike.announceGoalSaved();
        
        // Calculate total forward announcements
        int result = team.calculateTotalForwardAnnouncements();
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals("Total announcements should be 1 (only forward scores count)", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // SetUp: Create a football team named "Turgutlu"
        team.setPlayers(new ArrayList<>());
        
        // Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        FootballPlayer noah = new FootballPlayer();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        noah.setGoalAnnouncements(0);
        team.addPlayer(noah);
        
        FootballPlayer liam = new FootballPlayer();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        liam.setGoalSavingAnnouncements(0);
        team.addPlayer(liam);
        
        // Announce some saves and scores for others, but none for forwards
        noah.announceGoal(); // This should not count as noah is midfield
        liam.announceGoalSaved();
        
        // Calculate total forward announcements
        int result = team.calculateTotalForwardAnnouncements();
        
        // Expected Output: Total announcements = 0 (No forward player available)
        assertEquals("Total announcements should be 0 when no forward players", 0, result);
    }
}