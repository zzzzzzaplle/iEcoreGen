import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR4Test {
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        footballTeam = new FootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no save recorded
        Player goalkeeper = new Player();
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list (method requires checking all players)
        ArrayList<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals(0, footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper());
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper with one save
        Player goalkeeper = new Player();
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(12);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Create save announcement
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setTime(new Date());
        
        // Add goalkeeper to players list and announcement to announcements list
        ArrayList<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        ArrayList<Announcement> announcements = new ArrayList<>();
        announcements.add(saveAnnouncement);
        footballTeam.setAnnouncements(announcements);
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals(1, footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper());
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper with multiple saves
        Player goalkeeper = new Player();
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setNumber(5);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Create 3 save announcements
        ArrayList<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            announcements.add(saveAnnouncement);
        }
        
        // Add goalkeeper to players list and announcements to announcements list
        ArrayList<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        footballTeam.setAnnouncements(announcements);
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals(3, footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper());
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper with consecutive saves
        Player goalkeeper = new Player();
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setNumber(22);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Create 5 save announcements (all in second half)
        ArrayList<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            announcements.add(saveAnnouncement);
        }
        
        // Add goalkeeper to players list and announcements to announcements list
        ArrayList<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        footballTeam.setAnnouncements(announcements);
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals(5, footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper());
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed announcements
        Player goalkeeper = new Player();
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setNumber(10);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Create 2 save announcements and 1 goal announcement (should not be counted)
        ArrayList<Announcement> announcements = new ArrayList<>();
        
        // Add 2 save announcements
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            announcements.add(saveAnnouncement);
        }
        
        // Add 1 goal announcement (should not be counted)
        Announcement goalAnnouncement = new Announcement();
        goalAnnouncement.setType(AnnouncementType.SCORE);
        goalAnnouncement.setPlayer(goalkeeper);
        goalAnnouncement.setTime(new Date());
        announcements.add(goalAnnouncement);
        
        // Add goalkeeper to players list and announcements to announcements list
        ArrayList<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        footballTeam.setAnnouncements(announcements);
        
        // Expected Output: Total goal-saving announcements = 2 (only SAVE types counted)
        assertEquals(2, footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper());
    }
}