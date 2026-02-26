import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        team.setName("Turgutlu");
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // SetUp: Create a football team named "Turgutlu" and a forward player named "John" with number 9
        FootballPlayer john = new FootballPlayer();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        // Add player to starting eleven
        team.getStartingEleven().add(john);
        
        // Announce a score for John
        john.scoreGoal();
        
        // Verify total announcements = 1
        assertEquals("Total announcements should be 1 for single forward with one score", 
                     1, team.calculateTotalForwardAnnouncements());
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // SetUp: Create forward players: "Alice" (number 10), "Bob" (number 11)
        FootballPlayer alice = new FootballPlayer();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        
        FootballPlayer bob = new FootballPlayer();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        
        // Add players to starting eleven
        team.getStartingEleven().add(alice);
        team.getStartingEleven().add(bob);
        
        // Announce a score for Alice and a score for Bob
        alice.scoreGoal();
        bob.scoreGoal();
        
        // Verify total announcements = 2
        assertEquals("Total announcements should be 2 for two forwards with one score each", 
                     2, team.calculateTotalForwardAnnouncements());
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // SetUp: Create a forward player named "Tom" with number 7 and a goalkeeper named "Mike" with number 1
        FootballPlayer tom = new FootballPlayer();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        
        FootballPlayer mike = new FootballPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        // Add players to starting eleven
        team.getStartingEleven().add(tom);
        team.getStartingEleven().add(mike);
        
        // Announce a save for Mike (goalkeeper)
        mike.saveGoal();
        
        // Verify total announcements = 0 (No SCORE announcements for forward players)
        assertEquals("Total announcements should be 0 when only goalkeeper makes saves", 
                     0, team.calculateTotalForwardAnnouncements());
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
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
        
        // Add players to starting eleven
        team.getStartingEleven().add(emma);
        team.getStartingEleven().add(lucas);
        team.getStartingEleven().add(mike);
        
        // Announce a score for Emma and a save for Mike (goalkeeper)
        emma.scoreGoal();
        mike.saveGoal();
        
        // Verify total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals("Total announcements should be 1 for forward score, goalkeeper saves don't count", 
                     1, team.calculateTotalForwardAnnouncements());
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // SetUp: Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        FootballPlayer noah = new FootballPlayer();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        
        FootballPlayer liam = new FootballPlayer();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        
        // Create a goalkeeper for saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Keeper");
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add players to starting eleven
        team.getStartingEleven().add(noah);
        team.getStartingEleven().add(liam);
        team.getStartingEleven().add(goalkeeper);
        
        // Announce some saves and scores for non-forward players
        goalkeeper.saveGoal();
        goalkeeper.saveGoal();
        
        // Verify total announcements = 0 (No forward player available)
        assertEquals("Total announcements should be 0 when no forward players exist", 
                     0, team.calculateTotalForwardAnnouncements());
    }
}