import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // No announcements added, so no saves recorded
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals(0, team.calculateGoalSavingAnnouncementsByGoalkeeper());
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
        team.setPlayers(players);
        
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(saveAnnouncement);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals(1, team.calculateGoalSavingAnnouncementsByGoalkeeper());
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
        team.setPlayers(players);
        
        List<Announcement> announcements = new ArrayList<>();
        
        // Create 3 save announcements
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            announcements.add(saveAnnouncement);
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals(3, team.calculateGoalSavingAnnouncementsByGoalkeeper());
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
        team.setPlayers(players);
        
        List<Announcement> announcements = new ArrayList<>();
        
        // Create 5 consecutive save announcements (all in second half as specified)
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            announcements.add(saveAnnouncement);
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals(5, team.calculateGoalSavingAnnouncementsByGoalkeeper());
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed valid and invalid entries
        goalkeeper = new Player();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        List<Announcement> announcements = new ArrayList<>();
        
        // Add 2 valid SAVE announcements
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            announcements.add(saveAnnouncement);
        }
        
        // Add 1 invalid GOAL announcement (should not be counted)
        Announcement goalAnnouncement = new Announcement();
        goalAnnouncement.setType(AnnouncementType.SCORE); // GOAL type is SCORE
        goalAnnouncement.setPlayer(goalkeeper);
        goalAnnouncement.setTime(new Date());
        announcements.add(goalAnnouncement);
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total goal-saving announcements = 2 (only SAVE types counted)
        assertEquals(2, team.calculateGoalSavingAnnouncementsByGoalkeeper());
    }
}