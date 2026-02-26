import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // SetUp: Create team and forward player
        FootballPlayer john = new FootballPlayer();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        // Announce a score for John
        john.announceGoalScored();
        
        // Add player to team
        team.addPlayer(john);
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 1
        assertEquals("Total announcements should be 1 for single forward with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // SetUp: Create team and forward players
        FootballPlayer alice = new FootballPlayer();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        
        FootballPlayer bob = new FootballPlayer();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        
        // Announce scores for both players
        alice.announceGoalScored();
        bob.announceGoalScored();
        
        // Add players to team
        team.addPlayer(alice);
        team.addPlayer(bob);
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 2
        assertEquals("Total announcements should be 2 for two forwards with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // SetUp: Create team with forward and goalkeeper
        FootballPlayer tom = new FootballPlayer();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        // Announce a save for goalkeeper (not forward)
        mike.announceGoalSaved();
        
        // Add players to team
        team.addPlayer(tom);
        team.addPlayer(mike);
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 0
        assertEquals("Total announcements should be 0 when only goalkeeper makes announcements", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // SetUp: Create team with forwards and goalkeeper
        FootballPlayer emma = new FootballPlayer();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        
        FootballPlayer lucas = new FootballPlayer();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        // Announce score for forward and save for goalkeeper
        emma.announceGoalScored();
        mike.announceGoalSaved();
        
        // Add players to team
        team.addPlayer(emma);
        team.addPlayer(lucas);
        team.addPlayer(mike);
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 1
        assertEquals("Total announcements should be 1 (only forward score counts)", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // SetUp: Create team with midfield and defense players only
        FootballPlayer noah = new FootballPlayer();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        
        FootballPlayer liam = new FootballPlayer();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Keeper");
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Announce some saves and scores (none for forwards since there are no forwards)
        goalkeeper.announceGoalSaved();
        
        // Add players to team
        team.addPlayer(noah);
        team.addPlayer(liam);
        team.addPlayer(goalkeeper);
        
        // Calculate total announcements by forwards
        int result = team.calculateTotalAnnouncementsByForwards();
        
        // Expected Output: Total announcements = 0
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}