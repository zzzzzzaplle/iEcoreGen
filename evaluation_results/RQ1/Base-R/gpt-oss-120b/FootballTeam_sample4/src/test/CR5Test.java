import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Team team;
    
    @Before
    public void setUp() {
        // Reset team before each test
        team = new Team();
        team.setName("Turgutlu");
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Create a forward player named "John" with number 9
        Striker john = new Striker();
        john.setName("John");
        john.setNumber(9);
        john.setAge(25);
        
        // Add John to the team as a starter
        team.addPlayer(john, true);
        
        // Announce a score for John
        john.scoreGoal();
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected: Total announcements = 1 (SCORE for John)
        assertEquals("Total announcements should be 1 for single forward with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Create forward players: "Alice" (number 10), "Bob" (number 11)
        Striker alice = new Striker();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setAge(24);
        
        Striker bob = new Striker();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setAge(26);
        
        // Add both players to the team
        team.addPlayer(alice, true);
        team.addPlayer(bob, true);
        
        // Announce a score for Alice and a score for Bob
        alice.scoreGoal();
        bob.scoreGoal();
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected: Total announcements = 2 (SCORES for Alice and Bob)
        assertEquals("Total announcements should be 2 for multiple forwards with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // Create a forward player named "Tom" with number 7
        Striker tom = new Striker();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setAge(23);
        
        // Create a goalkeeper named "Mike" with number 1
        Goalkeeper mike = new Goalkeeper();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setAge(28);
        
        // Add both players to the team
        team.addPlayer(tom, true);
        team.addPlayer(mike, true);
        
        // Announce a save for Mike (goalkeeper)
        mike.saveGoal();
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals("Total announcements should be 0 when only goalkeeper makes saves", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Create forward players: "Emma" (number 8), "Lucas" (number 12)
        Striker emma = new Striker();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setAge(22);
        
        Striker lucas = new Striker();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setAge(27);
        
        // Create a goalkeeper named "Mike" with number 1
        Goalkeeper mike = new Goalkeeper();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setAge(28);
        
        // Add all players to the team
        team.addPlayer(emma, true);
        team.addPlayer(lucas, true);
        team.addPlayer(mike, true);
        
        // Announce a score for Emma
        emma.scoreGoal();
        
        // Announce a save for Mike (goalkeeper)
        mike.saveGoal();
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals("Total announcements should be 1 (only forward scores count)", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // Create midfield player "Noah" (number 5)
        Midfielder noah = new Midfielder();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setAge(24);
        
        // Create defender "Liam" (number 4) - using Goalkeeper as defender since no Defender class exists
        Goalkeeper liam = new Goalkeeper();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setAge(26);
        
        // Create another goalkeeper for saves
        Goalkeeper keeper = new Goalkeeper();
        keeper.setName("Keeper");
        keeper.setNumber(1);
        keeper.setAge(30);
        
        // Add all players to the team
        team.addPlayer(noah, true);
        team.addPlayer(liam, true);
        team.addPlayer(keeper, true);
        
        // Announce some saves and scores for non-forward players
        keeper.saveGoal();
        keeper.saveGoal();
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected: Total announcements = 0 (No forward player available)
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}