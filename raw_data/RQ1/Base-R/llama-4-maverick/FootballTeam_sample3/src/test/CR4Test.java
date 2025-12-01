import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Initialize a new football team before each test
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // Test Case 1: Calculate Goal Saving Announcements for a Goalkeeper with No Saves
        FootballPlayer goalkeeper = new FootballPlayer("TeamA", "John Doe", 30, 1, Position.DEFENSE);
        goalkeeper.setGoalSavingAnnouncements(0);
        
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(goalkeeper);
        team.setStartingEleven(startingEleven);
        
        int result = team.countGoalSavingAnnouncements();
        assertEquals("Total goal-saving announcements should be 0", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // Test Case 2: Calculate Goal Saving Announcements for a Goalkeeper with One Save
        FootballPlayer goalkeeper = new FootballPlayer("TeamB", "Jane Smith", 28, 12, Position.DEFENSE);
        goalkeeper.setGoalSavingAnnouncements(1);
        
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(goalkeeper);
        team.setStartingEleven(startingEleven);
        
        int result = team.countGoalSavingAnnouncements();
        assertEquals("Total goal-saving announcements should be 1", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // Test Case 3: Calculate Goal Saving Announcements for a Goalkeeper with Multiple Saves
        FootballPlayer goalkeeper = new FootballPlayer("TeamC", "Mark Johnson", 32, 5, Position.DEFENSE);
        goalkeeper.setGoalSavingAnnouncements(3);
        
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(goalkeeper);
        team.setStartingEleven(startingEleven);
        
        int result = team.countGoalSavingAnnouncements();
        assertEquals("Total goal-saving announcements should be 3", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // Test Case 4: Calculate Goal Saving Announcements for a Goalkeeper with Consecutive Saves
        FootballPlayer goalkeeper = new FootballPlayer("TeamD", "Emily Davis", 26, 22, Position.DEFENSE);
        goalkeeper.setGoalSavingAnnouncements(5);
        
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(goalkeeper);
        team.setStartingEleven(startingEleven);
        
        int result = team.countGoalSavingAnnouncements();
        assertEquals("Total goal-saving announcements should be 5", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // Test Case 5: Calculate Goal Saving Announcements for a Goalkeeper with Invalid Entries
        FootballPlayer goalkeeper = new FootballPlayer("TeamE", "Chris Brown", 31, 10, Position.DEFENSE);
        // Set goal-saving announcements to 2 (only these should be counted)
        goalkeeper.setGoalSavingAnnouncements(2);
        // The goal announcement (scoreAnnouncements) should not be counted
        
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(goalkeeper);
        team.setStartingEleven(startingEleven);
        
        int result = team.countGoalSavingAnnouncements();
        assertEquals("Total goal-saving announcements should be 2", 2, result);
    }
}