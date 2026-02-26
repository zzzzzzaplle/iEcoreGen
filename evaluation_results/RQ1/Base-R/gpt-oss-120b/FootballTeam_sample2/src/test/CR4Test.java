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
        // SetUp: Create a football team and add a goalkeeper with no save recorded
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setTeam("Team A");
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setSaveAnnouncements(0); // No saves recorded
        
        team.addPlayer(goalkeeper);
        
        // Calculate total goal-saving announcements
        int result = team.totalGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper with one save
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setTeam("Team B");
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setSaveAnnouncements(1); // One save announcement
        
        team.addPlayer(goalkeeper);
        
        // Calculate total goal-saving announcements
        int result = team.totalGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setTeam("Team C");
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setSaveAnnouncements(3); // Three save announcements
        
        team.addPlayer(goalkeeper);
        
        // Calculate total goal-saving announcements
        int result = team.totalGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setTeam("Team D");
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setSaveAnnouncements(5); // Five save announcements in second half
        
        team.addPlayer(goalkeeper);
        
        // Calculate total goal-saving announcements
        int result = team.totalGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed announcements
        FootballPlayer goalkeeper = new FootballPlayer();
        goalkeeper.setTeam("Team E");
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setSaveAnnouncements(2); // Two valid save announcements
        goalkeeper.setGoalAnnouncements(1); // One goal announcement (should not be counted)
        
        team.addPlayer(goalkeeper);
        
        // Calculate total goal-saving announcements
        int result = team.totalGoalSavingAnnouncements();
        
        // Expected Output: Total goal-saving announcements = 2 (only save announcements count)
        assertEquals(2, result);
    }
}