import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    private FootballTeam footballTeam;
    private Player goalkeeper;
    private List<Announcement> announcements;

    @Before
    public void setUp() {
        footballTeam = new FootballTeam();
        announcements = new ArrayList<>();
        footballTeam.setAnnouncements(announcements);
    }

    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team and add a goalkeeper with no save announcements
        goalkeeper = new Player();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list (required for the method to find goalkeepers)
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // The announcements list remains empty (no saves recorded)
        
        // Execute the method
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 0 when goalkeeper has no saves", 0, result);
    }

    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team and add a goalkeeper with one save announcement
        goalkeeper = new Player();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create one SAVE announcement for the goalkeeper
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setTime(new Date());
        
        announcements.add(saveAnnouncement);
        
        // Execute the method
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 1 when goalkeeper has one save", 1, result);
    }

    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team and add a goalkeeper with multiple save announcements
        goalkeeper = new Player();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create 3 SAVE announcements for the goalkeeper
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            announcements.add(saveAnnouncement);
        }
        
        // Execute the method
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 3 when goalkeeper has three saves", 3, result);
    }

    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team and add a goalkeeper with consecutive save announcements
        goalkeeper = new Player();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create 5 consecutive SAVE announcements for the goalkeeper
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            announcements.add(saveAnnouncement);
        }
        
        // Execute the method
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the expected output
        assertEquals("Total goal-saving announcements should be 5 when goalkeeper has five consecutive saves", 5, result);
    }

    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team and add a goalkeeper with mixed announcement types
        goalkeeper = new Player();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to players list
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        footballTeam.setPlayers(players);
        
        // Create 2 SAVE announcements (should be counted)
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = new Announcement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            announcements.add(saveAnnouncement);
        }
        
        // Create 1 SCORE announcement (should NOT be counted)
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(goalkeeper);
        scoreAnnouncement.setTime(new Date());
        announcements.add(scoreAnnouncement);
        
        // Execute the method
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the expected output - only SAVE announcements should be counted
        assertEquals("Total goal-saving announcements should be 2 when goalkeeper has 2 saves and 1 goal announcement", 2, result);
    }
}