import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    private FootballTeam footballTeam;
    private Player goalkeeper;
    private Announcement saveAnnouncement;
    private Announcement scoreAnnouncement;

    @Before
    public void setUp() {
        // Initialize fresh objects before each test
        footballTeam = new FootballTeam();
        goalkeeper = new Player();
        saveAnnouncement = new Announcement();
        scoreAnnouncement = new Announcement();
    }

    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // Test Case 1: Calculate Goal Saving Announcements for a Goalkeeper with No Saves
        // SetUp: Create a football team and add a goalkeeper with no save announcements
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // No announcements added to the team
        footballTeam.setAnnouncements(new ArrayList<>());
        
        // Verify result matches expected output
        assertEquals("Total goal-saving announcements should be 0 when goalkeeper has no saves", 
                     0, footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper());
    }

    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // Test Case 2: Calculate Goal Saving Announcements for a Goalkeeper with One Save
        // SetUp: Create a football team with goalkeeper who made one save announcement
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create one save announcement for the goalkeeper
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(saveAnnouncement);
        footballTeam.setAnnouncements(announcements);
        
        // Verify result matches expected output
        assertEquals("Total goal-saving announcements should be 1 when goalkeeper has one save", 
                     1, footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper());
    }

    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // Test Case 3: Calculate Goal Saving Announcements for a Goalkeeper with Multiple Saves
        // SetUp: Create a football team with goalkeeper who made three save announcements
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create three save announcements for the goalkeeper
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Announcement announcement = new Announcement();
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            announcement.setTime(new Date());
            announcements.add(announcement);
        }
        footballTeam.setAnnouncements(announcements);
        
        // Verify result matches expected output
        assertEquals("Total goal-saving announcements should be 3 when goalkeeper has three saves", 
                     3, footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper());
    }

    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // Test Case 4: Calculate Goal Saving Announcements for a Goalkeeper with Consecutive Saves
        // SetUp: Create a football team with goalkeeper who made five consecutive save announcements
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create five save announcements for the goalkeeper
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Announcement announcement = new Announcement();
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            announcement.setTime(new Date());
            announcements.add(announcement);
        }
        footballTeam.setAnnouncements(announcements);
        
        // Verify result matches expected output
        assertEquals("Total goal-saving announcements should be 5 when goalkeeper has five consecutive saves", 
                     5, footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper());
    }

    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // Test Case 5: Calculate Goal Saving Announcements for a Goalkeeper with Invalid Entries
        // SetUp: Create a football team with goalkeeper who has mixed valid and invalid announcements
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create two save announcements and one score announcement (invalid type)
        List<Announcement> announcements = new ArrayList<>();
        
        // Add two valid save announcements
        for (int i = 0; i < 2; i++) {
            Announcement announcement = new Announcement();
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            announcement.setTime(new Date());
            announcements.add(announcement);
        }
        
        // Add one invalid score announcement (should not be counted)
        Announcement invalidAnnouncement = new Announcement();
        invalidAnnouncement.setType(AnnouncementType.SCORE);
        invalidAnnouncement.setPlayer(goalkeeper);
        invalidAnnouncement.setTime(new Date());
        announcements.add(invalidAnnouncement);
        
        footballTeam.setAnnouncements(announcements);
        
        // Verify result matches expected output (only saves should be counted)
        assertEquals("Total goal-saving announcements should be 2 when goalkeeper has 2 saves and 1 invalid score announcement", 
                     2, footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper());
    }
}