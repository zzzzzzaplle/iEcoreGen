import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Add a goalkeeper with number: 1, name: "John Doe", age: 30
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(0); // No saves recorded
        
        team.addPlayer(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = team.countGoalkeeperSaveAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals("Goalkeeper with no saves should return 0", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Add a goalkeeper with number: 12, name: "Jane Smith", age: 28
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(1); // One save announcement
        
        team.addPlayer(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = team.countGoalkeeperSaveAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals("Goalkeeper with one save should return 1", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Add a goalkeeper with number: 5, name: "Mark Johnson", age: 32
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(3); // Three save announcements
        
        team.addPlayer(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = team.countGoalkeeperSaveAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals("Goalkeeper with three saves should return 3", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Add a goalkeeper with number: 22, name: "Emily Davis", age: 26
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(5); // Five consecutive save announcements in second half
        
        team.addPlayer(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = team.countGoalkeeperSaveAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals("Goalkeeper with five consecutive saves should return 5", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Add a goalkeeper with number: 10, name: "Chris Brown", age: 31
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setGoalsSaved(2); // Two valid save announcements
        goalkeeper.setGoalsScored(1); // One goal announcement (should not be counted)
        
        team.addPlayer(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = team.countGoalkeeperSaveAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 2
        assertEquals("Goalkeeper with mixed entries should only count save announcements", 2, result);
    }
}