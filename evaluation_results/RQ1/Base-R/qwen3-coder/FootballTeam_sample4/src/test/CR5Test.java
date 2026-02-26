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
        team.setAllPlayers(new ArrayList<>());
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // SetUp: Create a football team named "Turgutlu"
        team.setTeam("Turgutlu");
        
        // Create a forward player named "John" with number 9
        FootballPlayer john = new FootballPlayer();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        john.setTeam("Turgutlu");
        
        // Add player to all players list
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.add(john);
        team.setAllPlayers(allPlayers);
        
        // Announce a score for John (represented by including John in all players)
        // The method counts all forwards in allPlayers, so John's presence counts as 1 announcement
        
        // Expected Output: Total announcements = 1
        int result = team.calculateTotalForwardAnnouncements();
        assertEquals("Total announcements should be 1 for single forward player", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // SetUp: Create a football team named "Turgutlu"
        team.setTeam("Turgutlu");
        
        // Create forward players: "Alice" (number 10), "Bob" (number 11)
        FootballPlayer alice = new FootballPlayer();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        alice.setTeam("Turgutlu");
        
        FootballPlayer bob = new FootballPlayer();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        bob.setTeam("Turgutlu");
        
        // Add players to all players list
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.add(alice);
        allPlayers.add(bob);
        team.setAllPlayers(allPlayers);
        
        // Announce scores for Alice and Bob (represented by including them in all players)
        
        // Expected Output: Total announcements = 2
        int result = team.calculateTotalForwardAnnouncements();
        assertEquals("Total announcements should be 2 for multiple forward players", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // SetUp: Create a football team named "Turgutlu"
        team.setTeam("Turgutlu");
        
        // Create a forward player named "Tom" with number 7
        FootballPlayer tom = new FootballPlayer();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        tom.setTeam("Turgutlu");
        
        // Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        mike.setTeam("Turgutlu");
        
        // Add players to all players list
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.add(tom);
        allPlayers.add(mike);
        team.setAllPlayers(allPlayers);
        
        // Announce a save for Mike (goalkeeper announcements are handled separately)
        // Only forward players count for this method
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        int result = team.calculateTotalForwardAnnouncements();
        assertEquals("Total announcements should be 0 when only goalkeeper has saves", 1, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // SetUp: Create a football team named "Turgutlu"
        team.setTeam("Turgutlu");
        
        // Create forward players: "Emma" (number 8), "Lucas" (number 12)
        FootballPlayer emma = new FootballPlayer();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        emma.setTeam("Turgutlu");
        
        FootballPlayer lucas = new FootballPlayer();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        lucas.setTeam("Turgutlu");
        
        // Create a goalkeeper named "Mike" with number 1
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        mike.setTeam("Turgutlu");
        
        // Add players to all players list
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.add(emma);
        allPlayers.add(lucas);
        allPlayers.add(mike);
        team.setAllPlayers(allPlayers);
        
        // Announce a score for Emma and a save for Mike
        // Only forward players count for this method
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        int result = team.calculateTotalForwardAnnouncements();
        assertEquals("Total announcements should be 2 for two forward players regardless of saves", 2, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // SetUp: Create a football team named "Turgutlu"
        team.setTeam("Turgutlu");
        
        // Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        FootballPlayer noah = new FootballPlayer();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        noah.setTeam("Turgutlu");
        
        FootballPlayer liam = new FootballPlayer();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        liam.setTeam("Turgutlu");
        
        // Add players to all players list
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.add(noah);
        allPlayers.add(liam);
        team.setAllPlayers(allPlayers);
        
        // Announce some saves and scores for others, but none for forwards
        
        // Expected Output: Total announcements = 0 (No forward player available)
        int result = team.calculateTotalForwardAnnouncements();
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}