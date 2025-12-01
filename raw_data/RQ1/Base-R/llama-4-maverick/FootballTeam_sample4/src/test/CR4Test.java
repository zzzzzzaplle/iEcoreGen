import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // Test Case 1: Calculate Goal Saving Announcements for a Goalkeeper with No Saves
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Create a goalkeeper with no saves
        Player goalkeeper = new Player("TeamA", "John Doe", 30, 1, Position.DEFENSE);
        
        // Add goalkeeper to starting eleven
        List<Player> startingEleven = new ArrayList<>();
        startingEleven.add(goalkeeper);
        team.setStartingEleven(startingEleven);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected output: Total goal-saving announcements = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // Test Case 2: Calculate Goal Saving Announcements for a Goalkeeper with One Save
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Create a goalkeeper who made one save
        Player goalkeeper = new Player("TeamB", "Jane Smith", 28, 12, Position.DEFENSE);
        
        // Add goalkeeper to starting eleven
        List<Player> startingEleven = new ArrayList<>();
        startingEleven.add(goalkeeper);
        team.setStartingEleven(startingEleven);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected output: Total goal-saving announcements = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // Test Case 3: Calculate Goal Saving Announcements for a Goalkeeper with Multiple Saves
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Create a goalkeeper who made multiple saves
        Player goalkeeper = new Player("TeamC", "Mark Johnson", 32, 5, Position.DEFENSE);
        
        // Add goalkeeper to starting eleven
        List<Player> startingEleven = new ArrayList<>();
        startingEleven.add(goalkeeper);
        team.setStartingEleven(startingEleven);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected output: Total goal-saving announcements = 1 (one goalkeeper in defense position)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // Test Case 4: Calculate Goal Saving Announcements for a Goalkeeper with Consecutive Saves
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Create a goalkeeper who made consecutive saves
        Player goalkeeper = new Player("TeamD", "Emily Davis", 26, 22, Position.DEFENSE);
        
        // Add goalkeeper to starting eleven
        List<Player> startingEleven = new ArrayList<>();
        startingEleven.add(goalkeeper);
        team.setStartingEleven(startingEleven);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected output: Total goal-saving announcements = 1 (one goalkeeper in defense position)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // Test Case 5: Calculate Goal Saving Announcements for a Goalkeeper with Invalid Entries
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Create a goalkeeper with mixed valid and invalid entries
        Player goalkeeper = new Player("TeamE", "Chris Brown", 31, 10, Position.DEFENSE);
        
        // Add goalkeeper to starting eleven
        List<Player> startingEleven = new ArrayList<>();
        startingEleven.add(goalkeeper);
        team.setStartingEleven(startingEleven);
        
        // Calculate goal-saving announcements
        int result = team.countGoalSavingAnnouncements();
        
        // Expected output: Total goal-saving announcements = 1 (one goalkeeper in defense position)
        assertEquals(1, result);
    }
}