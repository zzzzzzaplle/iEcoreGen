import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Initialize a new FootballTeam before each test
        team = new FootballTeam();
        team.setName("Turgutlu");
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Test Case 1: Total Announcements for Single Forward Player with One Score
        // SetUp: Create a forward player named "John" with number 9 and announce a score for John
        Player john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(john);
        scoreAnnouncement.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAnnouncement);
        team.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Verify the expected output: Total announcements = 1
        assertEquals("Total announcements should be 1 for single forward player with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Test Case 2: Total Announcements for Multiple Forward Players with Scores
        // SetUp: Create forward players: "Alice" (number 10), "Bob" (number 11)
        // Announce a score for Alice and a score for Bob
        Player alice = new Player();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        
        Player bob = new Player();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        
        Announcement scoreAnnouncementAlice = new Announcement();
        scoreAnnouncementAlice.setType(AnnouncementType.SCORE);
        scoreAnnouncementAlice.setPlayer(alice);
        scoreAnnouncementAlice.setTime(new Date());
        
        Announcement scoreAnnouncementBob = new Announcement();
        scoreAnnouncementBob.setType(AnnouncementType.SCORE);
        scoreAnnouncementBob.setPlayer(bob);
        scoreAnnouncementBob.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAnnouncementAlice);
        announcements.add(scoreAnnouncementBob);
        team.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Verify the expected output: Total announcements = 2
        assertEquals("Total announcements should be 2 for multiple forward players with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // Test Case 3: Total Announcements for Forward Players with Saves
        // SetUp: Create a forward player named "Tom" with number 7 and a goalkeeper named "Mike" with number 1
        // Announce a save for Mike (goalkeeper)
        Player tom = new Player();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(mike);
        saveAnnouncement.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(saveAnnouncement);
        team.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Verify the expected output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals("Total announcements should be 0 when only goalkeeper saves exist", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Test Case 4: Total Announcements for Forward Players with Scores and Saves
        // SetUp: Create forward players: "Emma" (number 8), "Lucas" (number 12)
        // Announce a score for Emma and a save for Mike (goalkeeper)
        Player emma = new Player();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        
        Player lucas = new Player();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(emma);
        scoreAnnouncement.setTime(new Date());
        
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(mike);
        saveAnnouncement.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAnnouncement);
        announcements.add(saveAnnouncement);
        team.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Verify the expected output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals("Total announcements should be 1 when forward player scores and goalkeeper saves exist", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // Test Case 5: Total Announcements with No Forward Players
        // SetUp: Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        // Announce some saves and scores for others, but none for forwards
        Player noah = new Player();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        
        Player liam = new Player();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(noah);
        scoreAnnouncement.setTime(new Date());
        
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(liam);
        saveAnnouncement.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAnnouncement);
        announcements.add(saveAnnouncement);
        team.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Verify the expected output: Total announcements = 0 (No forward player available)
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}