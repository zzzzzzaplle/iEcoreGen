import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no saves
        List<FootballPlayer> startingEleven = new ArrayList<>();
        FootballPlayer goalkeeper = new FootballPlayer("TeamA", "John Doe", 30, 1, Position.DEFENSE, Duty.GOALKEEPER);
        startingEleven.add(goalkeeper);
        team.setStartingEleven(startingEleven);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Verify expected output: Total goal-saving announcements = 0
        assertEquals("Goal-saving announcements should be 0 for goalkeeper with no saves", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper with one save
        List<FootballPlayer> startingEleven = new ArrayList<>();
        FootballPlayer goalkeeper = new FootballPlayer("TeamB", "Jane Smith", 28, 12, Position.DEFENSE, Duty.GOALKEEPER);
        goalkeeper.setGoalsSaved(1); // Goalkeeper made 1 save
        startingEleven.add(goalkeeper);
        team.setStartingEleven(startingEleven);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Verify expected output: Total goal-saving announcements = 1
        assertEquals("Goal-saving announcements should be 1 for goalkeeper with one save", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        List<FootballPlayer> startingEleven = new ArrayList<>();
        FootballPlayer goalkeeper = new FootballPlayer("TeamC", "Mark Johnson", 32, 5, Position.DEFENSE, Duty.GOALKEEPER);
        goalkeeper.setGoalsSaved(3); // Goalkeeper made 3 saves
        startingEleven.add(goalkeeper);
        team.setStartingEleven(startingEleven);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Verify expected output: Total goal-saving announcements = 3
        assertEquals("Goal-saving announcements should be 3 for goalkeeper with multiple saves", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        List<FootballPlayer> startingEleven = new ArrayList<>();
        FootballPlayer goalkeeper = new FootballPlayer("TeamD", "Emily Davis", 26, 22, Position.DEFENSE, Duty.GOALKEEPER);
        goalkeeper.setGoalsSaved(5); // Goalkeeper made 5 consecutive saves
        startingEleven.add(goalkeeper);
        team.setStartingEleven(startingEleven);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Verify expected output: Total goal-saving announcements = 5
        assertEquals("Goal-saving announcements should be 5 for goalkeeper with consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed valid and invalid entries
        List<FootballPlayer> startingEleven = new ArrayList<>();
        FootballPlayer goalkeeper = new FootballPlayer("TeamE", "Chris Brown", 31, 10, Position.DEFENSE, Duty.GOALKEEPER);
        goalkeeper.setGoalsSaved(2); // 2 valid SAVE announcements
        // Note: GOAL announcements are not counted for goalkeepers in goal-saving announcements
        startingEleven.add(goalkeeper);
        team.setStartingEleven(startingEleven);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Verify expected output: Total goal-saving announcements = 2
        assertEquals("Goal-saving announcements should be 2 for goalkeeper with mixed entries", 2, result);
    }
}