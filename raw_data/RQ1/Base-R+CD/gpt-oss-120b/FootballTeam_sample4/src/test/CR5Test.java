import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR5Test {
    
    private FootballTeam team;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_totalAnnouncementsForSingleForwardPlayerWithOneScore() throws ParseException {
        // Create a forward player named "John" with number 9
        Player john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        // Add player to team
        team.addPlayer(john);
        
        // Create a score announcement for John
        Announcement score = new Announcement();
        score.setTime(dateFormat.parse("2023-10-15 15:30:00"));
        score.setType(AnnouncementType.SCORE);
        score.setPlayer(john);
        
        // Add announcement to team
        team.addAnnouncement(score);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: 1 (SCORE for John)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase2_totalAnnouncementsForMultipleForwardPlayersWithScores() throws ParseException {
        // Create forward players: "Alice" (number 10), "Bob" (number 11)
        Player alice = new Player();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        
        Player bob = new Player();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        
        // Add players to team
        team.addPlayer(alice);
        team.addPlayer(bob);
        
        // Create score announcements for Alice and Bob
        Announcement aliceScore = new Announcement();
        aliceScore.setTime(dateFormat.parse("2023-10-15 16:00:00"));
        aliceScore.setType(AnnouncementType.SCORE);
        aliceScore.setPlayer(alice);
        
        Announcement bobScore = new Announcement();
        bobScore.setTime(dateFormat.parse("2023-10-15 16:15:00"));
        bobScore.setType(AnnouncementType.SCORE);
        bobScore.setPlayer(bob);
        
        // Add announcements to team
        team.addAnnouncement(aliceScore);
        team.addAnnouncement(bobScore);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: 2 (SCORES for Alice and Bob)
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_totalAnnouncementsForForwardPlayersWithSaves() throws ParseException {
        // Create a forward player named "Tom" with number 7
        Player tom = new Player();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        
        // Create a goalkeeper named "Mike" with number 1
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        // Add players to team
        team.addPlayer(tom);
        team.addPlayer(mike);
        
        // Create a save announcement for Mike
        Announcement save = new Announcement();
        save.setTime(dateFormat.parse("2023-10-15 15:45:00"));
        save.setType(AnnouncementType.SAVE);
        save.setPlayer(mike);
        
        // Add announcement to team
        team.addAnnouncement(save);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: 0 (No SCORE announcements for forward players)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_totalAnnouncementsForForwardPlayersWithScoresAndSaves() throws ParseException {
        // Create forward players: "Emma" (number 8), "Lucas" (number 12)
        Player emma = new Player();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        
        Player lucas = new Player();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        
        // Create a goalkeeper named "Mike" with number 1
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        // Add players to team
        team.addPlayer(emma);
        team.addPlayer(lucas);
        team.addPlayer(mike);
        
        // Create a score announcement for Emma
        Announcement emmaScore = new Announcement();
        emmaScore.setTime(dateFormat.parse("2023-10-15 16:30:00"));
        emmaScore.setType(AnnouncementType.SCORE);
        emmaScore.setPlayer(emma);
        
        // Create a save announcement for Mike
        Announcement mikeSave = new Announcement();
        mikeSave.setTime(dateFormat.parse("2023-10-15 16:45:00"));
        mikeSave.setType(AnnouncementType.SAVE);
        mikeSave.setPlayer(mike);
        
        // Add announcements to team
        team.addAnnouncement(emmaScore);
        team.addAnnouncement(mikeSave);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: 1 (SCORE for Emma; saves do not count)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_totalAnnouncementsWithNoForwardPlayers() throws ParseException {
        // Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        Player noah = new Player();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        
        Player liam = new Player();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        
        // Add players to team
        team.addPlayer(noah);
        team.addPlayer(liam);
        
        // Create some announcements for non-forward players
        Announcement save = new Announcement();
        save.setTime(dateFormat.parse("2023-10-15 17:00:00"));
        save.setType(AnnouncementType.SAVE);
        save.setPlayer(noah);
        
        Announcement score = new Announcement();
        score.setTime(dateFormat.parse("2023-10-15 17:15:00"));
        score.setType(AnnouncementType.SCORE);
        score.setPlayer(liam);
        
        // Add announcements to team
        team.addAnnouncement(save);
        team.addAnnouncement(score);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: 0 (No forward player available)
        assertEquals(0, result);
    }
}