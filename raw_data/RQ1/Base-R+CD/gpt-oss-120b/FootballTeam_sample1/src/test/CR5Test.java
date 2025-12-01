import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import com.turgutlu.football.*;

public class CR5Test {
    
    private FootballTeam team;
    private Player john, alice, bob, tom, mike, emma, lucas, noah, liam;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() throws ParseException {
        // Create a forward player named "John" with number 9
        john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        // Add player to team
        team.addPlayer(john);
        
        // Announce a score for John
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(john);
        scoreAnnouncement.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 15:30:00"));
        team.addAnnouncement(scoreAnnouncement);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() throws ParseException {
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
        team.addPlayer(alice);
        team.addPlayer(bob);
        
        // Announce a score for Alice
        Announcement aliceScore = new Announcement();
        aliceScore.setType(AnnouncementType.SCORE);
        aliceScore.setPlayer(alice);
        aliceScore.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 15:35:00"));
        team.addAnnouncement(aliceScore);
        
        // Announce a score for Bob
        Announcement bobScore = new Announcement();
        bobScore.setType(AnnouncementType.SCORE);
        bobScore.setPlayer(bob);
        bobScore.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 15:40:00"));
        team.addAnnouncement(bobScore);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() throws ParseException {
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
        team.addPlayer(tom);
        team.addPlayer(mike);
        
        // Announce a save for Mike
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(mike);
        saveAnnouncement.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 15:45:00"));
        team.addAnnouncement(saveAnnouncement);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() throws ParseException {
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
        team.addPlayer(emma);
        team.addPlayer(lucas);
        team.addPlayer(mike);
        
        // Announce a score for Emma
        Announcement emmaScore = new Announcement();
        emmaScore.setType(AnnouncementType.SCORE);
        emmaScore.setPlayer(emma);
        emmaScore.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 15:50:00"));
        team.addAnnouncement(emmaScore);
        
        // Announce a save for Mike (goalkeeper)
        Announcement mikeSave = new Announcement();
        mikeSave.setType(AnnouncementType.SAVE);
        mikeSave.setPlayer(mike);
        mikeSave.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 15:55:00"));
        team.addAnnouncement(mikeSave);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() throws ParseException {
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
        team.addPlayer(noah);
        team.addPlayer(liam);
        
        // Announce some saves and scores for others, but none for forwards
        Announcement noahScore = new Announcement();
        noahScore.setType(AnnouncementType.SCORE);
        noahScore.setPlayer(noah);
        noahScore.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 16:00:00"));
        team.addAnnouncement(noahScore);
        
        Announcement liamSave = new Announcement();
        liamSave.setType(AnnouncementType.SAVE);
        liamSave.setPlayer(liam);
        liamSave.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 16:05:00"));
        team.addAnnouncement(liamSave);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected Output: Total announcements = 0 (No forward player available)
        assertEquals(0, result);
    }
}