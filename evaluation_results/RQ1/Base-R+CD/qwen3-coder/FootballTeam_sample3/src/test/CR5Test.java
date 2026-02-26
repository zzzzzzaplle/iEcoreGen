import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR5Test {
    
    private FootballTeam team;
    private Player john;
    private Player alice;
    private Player bob;
    private Player tom;
    private Player mike;
    private Player emma;
    private Player lucas;
    private Player noah;
    private Player liam;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        
        // Initialize players
        john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        alice = new Player();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        
        bob = new Player();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        
        tom = new Player();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        
        mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        emma = new Player();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        
        lucas = new Player();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        
        noah = new Player();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        
        liam = new Player();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Create a football team named "Turgutlu"
        // Create a forward player named "John" with number 9
        team.getPlayers().add(john);
        
        // Announce a score for John
        Announcement announcement = new Announcement();
        announcement.setType(AnnouncementType.SCORE);
        announcement.setPlayer(john);
        try {
            announcement.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 12:00:00"));
        } catch (ParseException e) {
            fail("Failed to parse date");
        }
        team.getAnnouncements().add(announcement);
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Create a football team named "Turgutlu"
        // Create forward players: "Alice" (number 10), "Bob" (number 11)
        team.getPlayers().add(alice);
        team.getPlayers().add(bob);
        
        // Announce a score for Alice and a score for Bob
        Announcement announcement1 = new Announcement();
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setPlayer(alice);
        try {
            announcement1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 12:00:00"));
        } catch (ParseException e) {
            fail("Failed to parse date");
        }
        
        Announcement announcement2 = new Announcement();
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setPlayer(bob);
        try {
            announcement2.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 12:05:00"));
        } catch (ParseException e) {
            fail("Failed to parse date");
        }
        
        team.getAnnouncements().add(announcement1);
        team.getAnnouncements().add(announcement2);
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // Create a football team named "Turgutlu"
        // Create a forward player named "Tom" with number 7
        team.getPlayers().add(tom);
        
        // Create a goalkeeper named "Mike" with number 1
        team.getPlayers().add(mike);
        
        // Announce a save for Mike
        Announcement announcement = new Announcement();
        announcement.setType(AnnouncementType.SAVE);
        announcement.setPlayer(mike);
        try {
            announcement.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 12:00:00"));
        } catch (ParseException e) {
            fail("Failed to parse date");
        }
        team.getAnnouncements().add(announcement);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Create a football team named "Turgutlu"
        // Create forward players: "Emma" (number 8), "Lucas" (number 12)
        team.getPlayers().add(emma);
        team.getPlayers().add(lucas);
        team.getPlayers().add(mike); // goalkeeper
        
        // Announce a score for Emma
        Announcement announcement1 = new Announcement();
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setPlayer(emma);
        try {
            announcement1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 12:00:00"));
        } catch (ParseException e) {
            fail("Failed to parse date");
        }
        
        // Announce a save for Mike (goalkeeper)
        Announcement announcement2 = new Announcement();
        announcement2.setType(AnnouncementType.SAVE);
        announcement2.setPlayer(mike);
        try {
            announcement2.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 12:05:00"));
        } catch (ParseException e) {
            fail("Failed to parse date");
        }
        
        team.getAnnouncements().add(announcement1);
        team.getAnnouncements().add(announcement2);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // Create a football team named "Turgutlu"
        // Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        team.getPlayers().add(noah);
        team.getPlayers().add(liam);
        
        // Announce some saves and scores for others, but none for forwards
        Announcement announcement1 = new Announcement();
        announcement1.setType(AnnouncementType.SAVE);
        announcement1.setPlayer(noah);
        try {
            announcement1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 12:00:00"));
        } catch (ParseException e) {
            fail("Failed to parse date");
        }
        
        Announcement announcement2 = new Announcement();
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setPlayer(liam);
        try {
            announcement2.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 12:05:00"));
        } catch (ParseException e) {
            fail("Failed to parse date");
        }
        
        team.getAnnouncements().add(announcement1);
        team.getAnnouncements().add(announcement2);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
    }
}