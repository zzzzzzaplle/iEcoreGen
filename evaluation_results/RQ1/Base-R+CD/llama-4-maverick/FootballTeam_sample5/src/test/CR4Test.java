import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private FootballTeam team;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() throws ParseException {
        // Create a goalkeeper with number: 1, name: "John Doe", age: 30
        Player goalkeeper = new Player();
        goalkeeper.setName("John Doe");
        goalkeeper.setNumber(1);
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to team players
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Set empty announcements list (no saves recorded)
        team.setAnnouncements(new ArrayList<>());
        
        // Execute the method and verify result
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() throws ParseException {
        // Create a goalkeeper with number: 12, name: "Jane Smith", age: 28
        Player goalkeeper = new Player();
        goalkeeper.setName("Jane Smith");
        goalkeeper.setNumber(12);
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to team players
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Create one SAVE announcement for the goalkeeper
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setTime(dateFormat.parse("2023-10-15 15:30:00"));
        
        // Set announcements list with one save
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(saveAnnouncement);
        team.setAnnouncements(announcements);
        
        // Execute the method and verify result
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() throws ParseException {
        // Create a goalkeeper with number: 5, name: "Mark Johnson", age: 32
        Player goalkeeper = new Player();
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setNumber(5);
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to team players
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Create three SAVE announcements for the goalkeeper
        List<Announcement> announcements = new ArrayList<>();
        
        Announcement save1 = new Announcement();
        save1.setType(AnnouncementType.SAVE);
        save1.setPlayer(goalkeeper);
        save1.setTime(dateFormat.parse("2023-10-15 16:00:00"));
        announcements.add(save1);
        
        Announcement save2 = new Announcement();
        save2.setType(AnnouncementType.SAVE);
        save2.setPlayer(goalkeeper);
        save2.setTime(dateFormat.parse("2023-10-15 16:15:00"));
        announcements.add(save2);
        
        Announcement save3 = new Announcement();
        save3.setType(AnnouncementType.SAVE);
        save3.setPlayer(goalkeeper);
        save3.setTime(dateFormat.parse("2023-10-15 16:30:00"));
        announcements.add(save3);
        
        // Set announcements list with three saves
        team.setAnnouncements(announcements);
        
        // Execute the method and verify result
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() throws ParseException {
        // Create a goalkeeper with number: 22, name: "Emily Davis", age: 26
        Player goalkeeper = new Player();
        goalkeeper.setName("Emily Davis");
        goalkeeper.setNumber(22);
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to team players
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Create five consecutive SAVE announcements for the goalkeeper
        List<Announcement> announcements = new ArrayList<>();
        
        Announcement save1 = new Announcement();
        save1.setType(AnnouncementType.SAVE);
        save1.setPlayer(goalkeeper);
        save1.setTime(dateFormat.parse("2023-10-15 17:00:00"));
        announcements.add(save1);
        
        Announcement save2 = new Announcement();
        save2.setType(AnnouncementType.SAVE);
        save2.setPlayer(goalkeeper);
        save2.setTime(dateFormat.parse("2023-10-15 17:05:00"));
        announcements.add(save2);
        
        Announcement save3 = new Announcement();
        save3.setType(AnnouncementType.SAVE);
        save3.setPlayer(goalkeeper);
        save3.setTime(dateFormat.parse("2023-10-15 17:10:00"));
        announcements.add(save3);
        
        Announcement save4 = new Announcement();
        save4.setType(AnnouncementType.SAVE);
        save4.setPlayer(goalkeeper);
        save4.setTime(dateFormat.parse("2023-10-15 17:15:00"));
        announcements.add(save4);
        
        Announcement save5 = new Announcement();
        save5.setType(AnnouncementType.SAVE);
        save5.setPlayer(goalkeeper);
        save5.setTime(dateFormat.parse("2023-10-15 17:20:00"));
        announcements.add(save5);
        
        // Set announcements list with five saves
        team.setAnnouncements(announcements);
        
        // Execute the method and verify result
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() throws ParseException {
        // Create a goalkeeper with number: 10, name: "Chris Brown", age: 31
        Player goalkeeper = new Player();
        goalkeeper.setName("Chris Brown");
        goalkeeper.setNumber(10);
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to team players
        List<Player> players = new ArrayList<>();
        players.add(goalkeeper);
        team.setPlayers(players);
        
        // Create announcements: 2 SAVEs and 1 SCORE (invalid entry that shouldn't be counted)
        List<Announcement> announcements = new ArrayList<>();
        
        Announcement save1 = new Announcement();
        save1.setType(AnnouncementType.SAVE);
        save1.setPlayer(goalkeeper);
        save1.setTime(dateFormat.parse("2023-10-15 18:00:00"));
        announcements.add(save1);
        
        Announcement score = new Announcement();
        score.setType(AnnouncementType.SCORE);
        score.setPlayer(goalkeeper);
        score.setTime(dateFormat.parse("2023-10-15 18:10:00"));
        announcements.add(score);
        
        Announcement save2 = new Announcement();
        save2.setType(AnnouncementType.SAVE);
        save2.setPlayer(goalkeeper);
        save2.setTime(dateFormat.parse("2023-10-15 18:20:00"));
        announcements.add(save2);
        
        // Set announcements list with mixed types
        team.setAnnouncements(announcements);
        
        // Execute the method and verify result (only SAVEs should be counted)
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(2, result);
    }
}