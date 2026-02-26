import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR5Test {
    
    private FootballTeam team;
    private Player john, alice, bob, tom, mike, emma, lucas, noah, liam;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Create a forward player named "John" with number 9
        john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        // Add player to team
        team.getPlayers().add(john);
        
        // Create a score announcement for John
        Announcement announcement = new Announcement();
        announcement.setType(AnnouncementType.SCORE);
        announcement.setPlayer(john);
        try {
            announcement.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 12:00:00"));
        } catch (ParseException e) {
            fail("Failed to parse date");
        }
        
        // Add announcement to team
        team.getAnnouncements().add(announcement);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: Total announcements = 1 (SCORE for John)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Create forward players: "Alice" (number 10), "Bob" (number 11)
        alice = new Player();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        
        bob = new Player();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        
        // Add players to team
        team.getPlayers().add(alice);
        team.getPlayers().add(bob);
        
        // Create score announcements for Alice and Bob
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
        
        // Add announcements to team
        team.getAnnouncements().add(announcement1);
        team.getAnnouncements().add(announcement2);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: Total announcements = 2 (SCORES for Alice and Bob)
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // Create a forward player named "Tom" with number 7
        tom = new Player();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        
        // Create a goalkeeper named "Mike" with number 1
        mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        // Add players to team
        team.getPlayers().add(tom);
        team.getPlayers().add(mike);
        
        // Create a save announcement for Mike
        Announcement announcement = new Announcement();
        announcement.setType(AnnouncementType.SAVE);
        announcement.setPlayer(mike);
        try {
            announcement.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 12:00:00"));
        } catch (ParseException e) {
            fail("Failed to parse date");
        }
        
        // Add announcement to team
        team.getAnnouncements().add(announcement);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Create forward players: "Emma" (number 8), "Lucas" (number 12)
        emma = new Player();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        
        lucas = new Player();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        
        // Create a goalkeeper named "Mike" with number 1
        mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        // Add players to team
        team.getPlayers().add(emma);
        team.getPlayers().add(lucas);
        team.getPlayers().add(mike);
        
        // Create a score announcement for Emma
        Announcement announcement1 = new Announcement();
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setPlayer(emma);
        try {
            announcement1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 12:00:00"));
        } catch (ParseException e) {
            fail("Failed to parse date");
        }
        
        // Create a save announcement for Mike
        Announcement announcement2 = new Announcement();
        announcement2.setType(AnnouncementType.SAVE);
        announcement2.setPlayer(mike);
        try {
            announcement2.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 12:05:00"));
        } catch (ParseException e) {
            fail("Failed to parse date");
        }
        
        // Add announcements to team
        team.getAnnouncements().add(announcement1);
        team.getAnnouncements().add(announcement2);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        noah = new Player();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        
        liam = new Player();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        
        // Add players to team
        team.getPlayers().add(noah);
        team.getPlayers().add(liam);
        
        // Create some announcements for non-forward players
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
        
        // Add announcements to team
        team.getAnnouncements().add(announcement1);
        team.getAnnouncements().add(announcement2);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: Total announcements = 0 (No forward player available)
        assertEquals(0, result);
    }
}