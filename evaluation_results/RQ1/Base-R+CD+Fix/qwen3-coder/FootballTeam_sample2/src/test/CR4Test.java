import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        footballTeam = new FootballTeam();
    }
    
    @Test
    public void testCase1_calculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no save announcements
        Player goalkeeper = new Player();
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // No announcements added, so announcements list remains empty
        
        // Execute method under test
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify expected output
        assertEquals("Total goal-saving announcements should be 0 when goalkeeper has no saves", 0, result);
    }
    
    @Test
    public void testCase2_calculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper with one save announcement
        Player goalkeeper = new Player();
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(saveAnnouncement);
        footballTeam.setAnnouncements(announcements);
        
        // Execute method under test
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify expected output
        assertEquals("Total goal-saving announcements should be 1 when goalkeeper has one save", 1, result);
    }
    
    @Test
    public void testCase3_calculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper with three save announcements
        Player goalkeeper = new Player();
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        Announcement save1 = new Announcement();
        save1.setType(AnnouncementType.SAVE);
        save1.setPlayer(goalkeeper);
        
        Announcement save2 = new Announcement();
        save2.setType(AnnouncementType.SAVE);
        save2.setPlayer(goalkeeper);
        
        Announcement save3 = new Announcement();
        save3.setType(AnnouncementType.SAVE);
        save3.setPlayer(goalkeeper);
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(save1);
        announcements.add(save2);
        announcements.add(save3);
        footballTeam.setAnnouncements(announcements);
        
        // Execute method under test
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify expected output
        assertEquals("Total goal-saving announcements should be 3 when goalkeeper has three saves", 3, result);
    }
    
    @Test
    public void testCase4_calculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper with five consecutive save announcements
        Player goalkeeper = new Player();
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        List<Announcement> announcements = new ArrayList<>();
        // Create 5 consecutive save announcements
        for (int i = 0; i < 5; i++) {
            Announcement save = new Announcement();
            save.setType(AnnouncementType.SAVE);
            save.setPlayer(goalkeeper);
            announcements.add(save);
        }
        footballTeam.setAnnouncements(announcements);
        
        // Execute method under test
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify expected output
        assertEquals("Total goal-saving announcements should be 5 when goalkeeper has five consecutive saves", 5, result);
    }
    
    @Test
    public void testCase5_calculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed announcement types
        Player goalkeeper = new Player();
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        Announcement save1 = new Announcement();
        save1.setType(AnnouncementType.SAVE);
        save1.setPlayer(goalkeeper);
        
        Announcement save2 = new Announcement();
        save2.setType(AnnouncementType.SAVE);
        save2.setPlayer(goalkeeper);
        
        Announcement goalAnnouncement = new Announcement();
        goalAnnouncement.setType(AnnouncementType.SCORE); // This should not be counted
        goalAnnouncement.setPlayer(goalkeeper);
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(save1);
        announcements.add(save2);
        announcements.add(goalAnnouncement);
        footballTeam.setAnnouncements(announcements);
        
        // Execute method under test
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify expected output - only SAVE announcements should be counted
        assertEquals("Total goal-saving announcements should be 2 when goalkeeper has 2 saves and 1 goal announcement", 2, result);
    }
}