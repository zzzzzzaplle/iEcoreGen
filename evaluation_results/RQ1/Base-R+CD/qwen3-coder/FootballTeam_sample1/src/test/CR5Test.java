import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        
        // Create players as needed
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
    public void testCase1_totalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Create a football team named "Turgutlu"
        // Create a forward player named "John" with number 9
        List<Player> players = new ArrayList<>();
        players.add(john);
        team.setPlayers(players);
        
        // Announce a score for John
        List<Announcement> announcements = new ArrayList<>();
        Announcement announcement = new Announcement();
        announcement.setType(AnnouncementType.SCORE);
        announcement.setPlayer(john);
        announcement.setTime(new Date());
        announcements.add(announcement);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase2_totalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Create a football team named "Turgutlu"
        // Create forward players: "Alice" (number 10), "Bob" (number 11)
        List<Player> players = new ArrayList<>();
        players.add(alice);
        players.add(bob);
        team.setPlayers(players);
        
        // Announce a score for Alice and a score for Bob
        List<Announcement> announcements = new ArrayList<>();
        Announcement announcement1 = new Announcement();
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setPlayer(alice);
        announcement1.setTime(new Date());
        announcements.add(announcement1);
        
        Announcement announcement2 = new Announcement();
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setPlayer(bob);
        announcement2.setTime(new Date());
        announcements.add(announcement2);
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_totalAnnouncementsForForwardPlayersWithSaves() {
        // Create a football team named "Turgutlu"
        // Create a forward player named "Tom" with number 7
        List<Player> players = new ArrayList<>();
        players.add(tom);
        team.setPlayers(players);
        
        // Create a goalkeeper named "Mike" with number 1
        players.add(mike);
        
        // Announce a save for Mike
        List<Announcement> announcements = new ArrayList<>();
        Announcement announcement = new Announcement();
        announcement.setType(AnnouncementType.SAVE);
        announcement.setPlayer(mike);
        announcement.setTime(new Date());
        announcements.add(announcement);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_totalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Create a football team named "Turgutlu"
        // Create forward players: "Emma" (number 8), "Lucas" (number 12)
        List<Player> players = new ArrayList<>();
        players.add(emma);
        players.add(lucas);
        players.add(mike);
        team.setPlayers(players);
        
        // Announce a score for Emma
        // Announce a save for Mike (goalkeeper)
        List<Announcement> announcements = new ArrayList<>();
        Announcement announcement1 = new Announcement();
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setPlayer(emma);
        announcement1.setTime(new Date());
        announcements.add(announcement1);
        
        Announcement announcement2 = new Announcement();
        announcement2.setType(AnnouncementType.SAVE);
        announcement2.setPlayer(mike);
        announcement2.setTime(new Date());
        announcements.add(announcement2);
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_totalAnnouncementsWithNoForwardPlayers() {
        // Create a football team named "Turgutlu"
        // Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        List<Player> players = new ArrayList<>();
        players.add(noah);
        players.add(liam);
        team.setPlayers(players);
        
        // Announce some saves and scores for others, but none for forwards
        List<Announcement> announcements = new ArrayList<>();
        Announcement announcement1 = new Announcement();
        announcement1.setType(AnnouncementType.SAVE);
        announcement1.setPlayer(noah);
        announcement1.setTime(new Date());
        announcements.add(announcement1);
        
        Announcement announcement2 = new Announcement();
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setPlayer(liam);
        announcement2.setTime(new Date());
        announcements.add(announcement2);
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
    }
}