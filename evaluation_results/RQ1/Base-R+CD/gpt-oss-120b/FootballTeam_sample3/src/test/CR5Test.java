import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR5Test {
    
    private FootballTeam team;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() throws ParseException {
        // Create forward player named "John" with number 9
        Player john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        // Add player to team
        team.getPlayers().add(john);
        
        // Create a score announcement for John
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(john);
        Date time = dateFormat.parse("2023-10-15 15:30:00");
        scoreAnnouncement.setTime(time);
        
        // Add announcement to team
        team.getAnnouncements().add(scoreAnnouncement);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: Total announcements = 1 (SCORE for John)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() throws ParseException {
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
        team.getPlayers().add(alice);
        team.getPlayers().add(bob);
        
        // Create score announcements for Alice and Bob
        Announcement aliceScore = new Announcement();
        aliceScore.setType(AnnouncementType.SCORE);
        aliceScore.setPlayer(alice);
        Date time1 = dateFormat.parse("2023-10-15 16:00:00");
        aliceScore.setTime(time1);
        
        Announcement bobScore = new Announcement();
        bobScore.setType(AnnouncementType.SCORE);
        bobScore.setPlayer(bob);
        Date time2 = dateFormat.parse("2023-10-15 16:15:00");
        bobScore.setTime(time2);
        
        // Add announcements to team
        team.getAnnouncements().add(aliceScore);
        team.getAnnouncements().add(bobScore);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: Total announcements = 2 (SCORES for Alice and Bob)
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() throws ParseException {
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
        team.getPlayers().add(tom);
        team.getPlayers().add(mike);
        
        // Create a save announcement for Mike
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(mike);
        Date time = dateFormat.parse("2023-10-15 15:45:00");
        saveAnnouncement.setTime(time);
        
        // Add announcement to team
        team.getAnnouncements().add(saveAnnouncement);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() throws ParseException {
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
        team.getPlayers().add(emma);
        team.getPlayers().add(lucas);
        team.getPlayers().add(mike);
        
        // Create a score announcement for Emma
        Announcement emmaScore = new Announcement();
        emmaScore.setType(AnnouncementType.SCORE);
        emmaScore.setPlayer(emma);
        Date time1 = dateFormat.parse("2023-10-15 16:30:00");
        emmaScore.setTime(time1);
        
        // Create a save announcement for Mike
        Announcement mikeSave = new Announcement();
        mikeSave.setType(AnnouncementType.SAVE);
        mikeSave.setPlayer(mike);
        Date time2 = dateFormat.parse("2023-10-15 16:45:00");
        mikeSave.setTime(time2);
        
        // Add announcements to team
        team.getAnnouncements().add(emmaScore);
        team.getAnnouncements().add(mikeSave);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() throws ParseException {
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
        team.getPlayers().add(noah);
        team.getPlayers().add(liam);
        
        // Create announcements for non-forward players
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(noah);
        Date time1 = dateFormat.parse("2023-10-15 17:00:00");
        saveAnnouncement.setTime(time1);
        
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(liam);
        Date time2 = dateFormat.parse("2023-10-15 17:15:00");
        scoreAnnouncement.setTime(time2);
        
        // Add announcements to team
        team.getAnnouncements().add(saveAnnouncement);
        team.getAnnouncements().add(scoreAnnouncement);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: Total announcements = 0 (No forward player available)
        assertEquals(0, result);
    }
}