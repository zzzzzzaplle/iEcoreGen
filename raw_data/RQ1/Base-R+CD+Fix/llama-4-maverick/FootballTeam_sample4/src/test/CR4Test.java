import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private FootballTeam team;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no save recorded
        Player goalkeeper = new Player();
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // No announcements added
        
        // Execute the method under test
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 0 when goalkeeper has no saves", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() throws Exception {
        // SetUp: Create a football team and add a goalkeeper with one save
        Player goalkeeper = new Player();
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Create one save announcement for the goalkeeper
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(saveAnnouncement);
        team.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 1 when goalkeeper made one save", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() throws Exception {
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        Player goalkeeper = new Player();
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Create three save announcements for the goalkeeper
        List<Announcement> announcements = new ArrayList<>();
        
        Announcement save1 = new Announcement();
        save1.setPlayer(goalkeeper);
        save1.setType(AnnouncementType.SAVE);
        save1.setTime(dateFormat.parse("2024-01-15 15:15:00"));
        announcements.add(save1);
        
        Announcement save2 = new Announcement();
        save2.setPlayer(goalkeeper);
        save2.setType(AnnouncementType.SAVE);
        save2.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        announcements.add(save2);
        
        Announcement save3 = new Announcement();
        save3.setPlayer(goalkeeper);
        save3.setType(AnnouncementType.SAVE);
        save3.setTime(dateFormat.parse("2024-01-15 15:45:00"));
        announcements.add(save3);
        
        team.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 3 when goalkeeper made three saves", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() throws Exception {
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        Player goalkeeper = new Player();
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Create five consecutive save announcements for the goalkeeper (all in second half)
        List<Announcement> announcements = new ArrayList<>();
        
        Announcement save1 = new Announcement();
        save1.setPlayer(goalkeeper);
        save1.setType(AnnouncementType.SAVE);
        save1.setTime(dateFormat.parse("2024-01-15 16:00:00"));
        announcements.add(save1);
        
        Announcement save2 = new Announcement();
        save2.setPlayer(goalkeeper);
        save2.setType(AnnouncementType.SAVE);
        save2.setTime(dateFormat.parse("2024-01-15 16:05:00"));
        announcements.add(save2);
        
        Announcement save3 = new Announcement();
        save3.setPlayer(goalkeeper);
        save3.setType(AnnouncementType.SAVE);
        save3.setTime(dateFormat.parse("2024-01-15 16:10:00"));
        announcements.add(save3);
        
        Announcement save4 = new Announcement();
        save4.setPlayer(goalkeeper);
        save4.setType(AnnouncementType.SAVE);
        save4.setTime(dateFormat.parse("2024-01-15 16:15:00"));
        announcements.add(save4);
        
        Announcement save5 = new Announcement();
        save5.setPlayer(goalkeeper);
        save5.setType(AnnouncementType.SAVE);
        save5.setTime(dateFormat.parse("2024-01-15 16:20:00"));
        announcements.add(save5);
        
        team.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 5 when goalkeeper made five consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() throws Exception {
        // SetUp: Create a football team and add a goalkeeper with mixed announcement types
        Player goalkeeper = new Player();
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Create announcements: 2 saves and 1 goal (goal should not be counted)
        List<Announcement> announcements = new ArrayList<>();
        
        Announcement save1 = new Announcement();
        save1.setPlayer(goalkeeper);
        save1.setType(AnnouncementType.SAVE);
        save1.setTime(dateFormat.parse("2024-01-15 15:20:00"));
        announcements.add(save1);
        
        Announcement goal = new Announcement();
        goal.setPlayer(goalkeeper);
        goal.setType(AnnouncementType.SCORE); // This should NOT be counted
        goal.setTime(dateFormat.parse("2024-01-15 15:25:00"));
        announcements.add(goal);
        
        Announcement save2 = new Announcement();
        save2.setPlayer(goalkeeper);
        save2.setType(AnnouncementType.SAVE);
        save2.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        announcements.add(save2);
        
        team.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 2 when goalkeeper made 2 saves and 1 goal", 2, result);
    }
}