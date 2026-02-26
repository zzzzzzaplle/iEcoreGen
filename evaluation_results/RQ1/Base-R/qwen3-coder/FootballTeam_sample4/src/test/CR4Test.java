import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Initialize a new FootballTeam before each test
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.add(goalkeeper);
        team.setAllPlayers(allPlayers);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify expected output: Total goal-saving announcements = 0
        assertEquals("Goal-saving announcements should be 0 when goalkeeper has no saves", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper with one save
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.add(goalkeeper);
        team.setAllPlayers(allPlayers);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify expected output: Total goal-saving announcements = 1
        assertEquals("Goal-saving announcements should be 1 when goalkeeper has one save", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.add(goalkeeper);
        team.setAllPlayers(allPlayers);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify expected output: Total goal-saving announcements = 3
        assertEquals("Goal-saving announcements should be 3 when goalkeeper has multiple saves", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.add(goalkeeper);
        team.setAllPlayers(allPlayers);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify expected output: Total goal-saving announcements = 5
        assertEquals("Goal-saving announcements should be 5 when goalkeeper has consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with invalid entries
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.add(goalkeeper);
        team.setAllPlayers(allPlayers);
        
        // Execute the method under test
        int result = team.countGoalSavingAnnouncements();
        
        // Verify expected output: Total goal-saving announcements = 2
        assertEquals("Goal-saving announcements should be 2 when goalkeeper has invalid entries", 2, result);
    }
}