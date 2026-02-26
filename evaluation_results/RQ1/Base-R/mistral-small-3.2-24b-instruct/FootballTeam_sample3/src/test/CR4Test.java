import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        // Goalkeeper has not made any saves (goalSavingAnnouncements defaults to 0)
        
        team.addPlayer(goalkeeper);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify the expected output
        assertEquals("Goal-saving announcements should be 0 for goalkeeper with no saves", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper with one save
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Set goal-saving announcements to 1
        goalkeeper.incrementGoalSavingAnnouncements();
        
        team.addPlayer(goalkeeper);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify the expected output
        assertEquals("Goal-saving announcements should be 1 for goalkeeper with one save", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Set goal-saving announcements to 3
        for (int i = 0; i < 3; i++) {
            goalkeeper.incrementGoalSavingAnnouncements();
        }
        
        team.addPlayer(goalkeeper);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify the expected output
        assertEquals("Goal-saving announcements should be 3 for goalkeeper with multiple saves", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Set goal-saving announcements to 5 (consecutive saves)
        for (int i = 0; i < 5; i++) {
            goalkeeper.incrementGoalSavingAnnouncements();
        }
        
        team.addPlayer(goalkeeper);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify the expected output
        assertEquals("Goal-saving announcements should be 5 for goalkeeper with consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed announcements
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Set 2 goal-saving announcements (other announcement types like GOAL are not tracked in this player)
        for (int i = 0; i < 2; i++) {
            goalkeeper.incrementGoalSavingAnnouncements();
        }
        // Note: The GOAL announcement mentioned in the spec is not applicable since 
        // FootballPlayer only tracks goal-saving announcements
        
        team.addPlayer(goalkeeper);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify the expected output
        assertEquals("Goal-saving announcements should be 2, ignoring other announcement types", 2, result);
    }
}