import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    private FootballTeam footballTeam;
    private Player goalkeeper;
    private Announcement announcement;
    
    @Before
    public void setUp() {
        footballTeam = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no save recorded
        goalkeeper = new Player();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Set empty announcements list
        footballTeam.setAnnouncements(new ArrayList<Announcement>());
        
        // Execute method under test
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify expected output: Total goal-saving announcements = 0
        assertEquals("Goalkeeper with no saves should return 0", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper who made one save
        goalkeeper = new Player();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create one SAVE announcement for the goalkeeper
        announcement = new Announcement();
        announcement.setType(AnnouncementType.SAVE);
        announcement.setPlayer(goalkeeper);
        announcement.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(announcement);
        footballTeam.setAnnouncements(announcements);
        
        // Execute method under test
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify expected output: Total goal-saving announcements = 1
        assertEquals("Goalkeeper with one save should return 1", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper who made multiple saves
        goalkeeper = new Player();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create three SAVE announcements for the goalkeeper
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            announcement = new Announcement();
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            announcement.setTime(new Date());
            announcements.add(announcement);
        }
        footballTeam.setAnnouncements(announcements);
        
        // Execute method under test
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify expected output: Total goal-saving announcements = 3
        assertEquals("Goalkeeper with three saves should return 3", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper who made consecutive saves
        goalkeeper = new Player();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create five consecutive SAVE announcements for the goalkeeper
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            announcement = new Announcement();
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            announcement.setTime(new Date());
            announcements.add(announcement);
        }
        footballTeam.setAnnouncements(announcements);
        
        // Execute method under test
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify expected output: Total goal-saving announcements = 5
        assertEquals("Goalkeeper with five consecutive saves should return 5", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed announcement types
        goalkeeper = new Player();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create mixed announcements: 2 SAVE and 1 SCORE (should only count SAVE)
        List<Announcement> announcements = new ArrayList<>();
        
        // First SAVE announcement
        announcement = new Announcement();
        announcement.setType(AnnouncementType.SAVE);
        announcement.setPlayer(goalkeeper);
        announcement.setTime(new Date());
        announcements.add(announcement);
        
        // Second SAVE announcement
        announcement = new Announcement();
        announcement.setType(AnnouncementType.SAVE);
        announcement.setPlayer(goalkeeper);
        announcement.setTime(new Date());
        announcements.add(announcement);
        
        // SCORE announcement (should not be counted)
        announcement = new Announcement();
        announcement.setType(AnnouncementType.SCORE);
        announcement.setPlayer(goalkeeper);
        announcement.setTime(new Date());
        announcements.add(announcement);
        
        footballTeam.setAnnouncements(announcements);
        
        // Execute method under test
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify expected output: Total goal-saving announcements = 2 (only SAVE announcements counted)
        assertEquals("Goalkeeper with 2 saves and 1 goal should return 2", 2, result);
    }
}