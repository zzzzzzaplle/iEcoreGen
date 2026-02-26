import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR4Test {
    
    private FootballTeam team;
    private Player goalkeeper;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no save recorded
        goalkeeper = new Player();
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // No announcements added
        List<Announcement> announcements = new ArrayList<>();
        team.setAnnouncements(announcements);
        
        // Expected Output: Total goal-saving announcements = 0
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper who made one save
        goalkeeper = new Player();
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Create one SAVE announcement for the goalkeeper
        List<Announcement> announcements = new ArrayList<>();
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setTime(new Date());
        announcements.add(saveAnnouncement);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total goal-saving announcements = 1
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper who made multiple saves
        goalkeeper = new Player();
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Create 3 SAVE announcements for the goalkeeper
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            announcements.add(saveAnnouncement);
        }
        team.setAnnouncements(announcements);
        
        // Expected Output: Total goal-saving announcements = 3
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper who made consecutive saves
        goalkeeper = new Player();
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Create 5 SAVE announcements for the goalkeeper (all in second half - but timing doesn't affect count)
        List<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            announcements.add(saveAnnouncement);
        }
        team.setAnnouncements(announcements);
        
        // Expected Output: Total goal-saving announcements = 5
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed announcement types
        goalkeeper = new Player();
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Create announcements: 2 SAVE and 1 GOAL (which should not be counted)
        List<Announcement> announcements = new ArrayList<>();
        
        // Add 2 SAVE announcements
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            announcements.add(saveAnnouncement);
        }
        
        // Add 1 GOAL announcement (should not be counted)
        Announcement goalAnnouncement = new Announcement();
        goalAnnouncement.setType(AnnouncementType.SCORE);
        goalAnnouncement.setPlayer(goalkeeper);
        goalAnnouncement.setTime(new Date());
        announcements.add(goalAnnouncement);
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total goal-saving announcements = 2 (only SAVE announcements count)
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(2, result);
    }
}