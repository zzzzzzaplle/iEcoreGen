import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        
        // Create players as per test specifications
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
    public void testCase1_totalAnnouncementsForSingleForwardPlayerWithOneScore() throws ParseException {
        // Create a football team named "Turgutlu"
        // Create a forward player named "John" with number 9
        team.addPlayer(john);
        
        // Announce a score for John
        Announcement announcement = new Announcement();
        announcement.setType(AnnouncementType.SCORE);
        announcement.setPlayer(john);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        announcement.setTime(sdf.parse("2023-01-01 15:30:00"));
        team.addAnnouncement(announcement);
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase2_totalAnnouncementsForMultipleForwardPlayersWithScores() throws ParseException {
        // Create a football team named "Turgutlu"
        // Create forward players: "Alice" (number 10), "Bob" (number 11)
        team.addPlayer(alice);
        team.addPlayer(bob);
        
        // Announce a score for Alice and a score for Bob
        Announcement announcement1 = new Announcement();
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setPlayer(alice);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        announcement1.setTime(sdf.parse("2023-01-01 15:30:00"));
        team.addAnnouncement(announcement1);
        
        Announcement announcement2 = new Announcement();
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setPlayer(bob);
        announcement2.setTime(sdf.parse("2023-01-01 15:35:00"));
        team.addAnnouncement(announcement2);
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_totalAnnouncementsForForwardPlayersWithSaves() throws ParseException {
        // Create a football team named "Turgutlu"
        // Create a forward player named "Tom" with number 7
        team.addPlayer(tom);
        team.addPlayer(mike); // goalkeeper
        
        // Announce a save for Mike
        Announcement announcement = new Announcement();
        announcement.setType(AnnouncementType.SAVE);
        announcement.setPlayer(mike);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        announcement.setTime(sdf.parse("2023-01-01 15:30:00"));
        team.addAnnouncement(announcement);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_totalAnnouncementsForForwardPlayersWithScoresAndSaves() throws ParseException {
        // Create a football team named "Turgutlu"
        // Create forward players: "Emma" (number 8), "Lucas" (number 12)
        team.addPlayer(emma);
        team.addPlayer(lucas);
        team.addPlayer(mike); // goalkeeper
        
        // Announce a score for Emma
        Announcement announcement1 = new Announcement();
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setPlayer(emma);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        announcement1.setTime(sdf.parse("2023-01-01 15:30:00"));
        team.addAnnouncement(announcement1);
        
        // Announce a save for Mike (goalkeeper)
        Announcement announcement2 = new Announcement();
        announcement2.setType(AnnouncementType.SAVE);
        announcement2.setPlayer(mike);
        announcement2.setTime(sdf.parse("2023-01-01 15:35:00"));
        team.addAnnouncement(announcement2);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_totalAnnouncementsWithNoForwardPlayers() throws ParseException {
        // Create a football team named "Turgutlu"
        // Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        team.addPlayer(noah);
        team.addPlayer(liam);
        
        // Announce some saves and scores for others, but none for forwards
        Announcement announcement1 = new Announcement();
        announcement1.setType(AnnouncementType.SAVE);
        announcement1.setPlayer(noah);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        announcement1.setTime(sdf.parse("2023-01-01 15:30:00"));
        team.addAnnouncement(announcement1);
        
        Announcement announcement2 = new Announcement();
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setPlayer(liam);
        announcement2.setTime(sdf.parse("2023-01-01 15:35:00"));
        team.addAnnouncement(announcement2);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
    }
}