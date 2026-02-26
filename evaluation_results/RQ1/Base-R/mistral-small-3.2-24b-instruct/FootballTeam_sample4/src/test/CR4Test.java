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
        // Create a football team
        FootballTeam team = new FootballTeam();
        
        // Add a goalkeeper with number: 1, name: "John Doe", age: 30
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.DEFENSE); // Assuming DEFENSE represents goalkeeper
        
        // The goalkeeper has not made any saves in the match
        goalkeeper.setGoalSavingAnnouncements(0);
        
        team.addPlayer(goalkeeper);
        
        // Calculate total goal-saving announcements
        int result = team.countGoalkeeperGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals(0, result);
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
        goalkeeper.setPosition(Position.DEFENSE); // Assuming DEFENSE represents goalkeeper
        
        // Goalkeeper made 1 announcement of type SAVE
        goalkeeper.setGoalSavingAnnouncements(1);
        
        team.addPlayer(goalkeeper);
        
        // Calculate total goal-saving announcements
        int result = team.countGoalkeeperGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals(1, result);
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
        goalkeeper.setPosition(Position.DEFENSE); // Assuming DEFENSE represents goalkeeper
        
        // Goalkeeper made 3 announcements of type SAVE during the match
        goalkeeper.setGoalSavingAnnouncements(3);
        
        team.addPlayer(goalkeeper);
        
        // Calculate total goal-saving announcements
        int result = team.countGoalkeeperGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals(3, result);
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
        goalkeeper.setPosition(Position.DEFENSE); // Assuming DEFENSE represents goalkeeper
        
        // Goalkeeper made 5 announcements of type SAVE during the match, all in the second half
        goalkeeper.setGoalSavingAnnouncements(5);
        
        team.addPlayer(goalkeeper);
        
        // Calculate total goal-saving announcements
        int result = team.countGoalkeeperGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals(5, result);
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
        goalkeeper.setPosition(Position.DEFENSE); // Assuming DEFENSE represents goalkeeper
        
        // Goalkeeper made 2 announcements of type SAVE and 1 announcement of type GOAL (which should not be counted)
        goalkeeper.setGoalSavingAnnouncements(2);
        goalkeeper.setGoalAnnouncements(1); // This should not be counted for goal-saving announcements
        
        team.addPlayer(goalkeeper);
        
        // Calculate total goal-saving announcements
        int result = team.countGoalkeeperGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 2
        assertEquals(2, result);
    }
}