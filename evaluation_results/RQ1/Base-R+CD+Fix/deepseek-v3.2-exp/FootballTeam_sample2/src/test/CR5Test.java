import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR5Test {
    
    private FootballTeam team;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() throws Exception {
        // Test Case 1: "Total Announcements for Single Forward Player with One Score"
        // SetUp: Create football team "Turgutlu", forward player "John" with number 9, announce score for John
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        Player john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        team.getPlayers().add(john);
        
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setPlayer(john);
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        team.getAnnouncements().add(scoreAnnouncement);
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        assertEquals(1, team.calculateTotalAnnouncementsForForwardPlayers());
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() throws Exception {
        // Test Case 2: "Total Announcements for Multiple Forward Players with Scores"
        // SetUp: Create football team "Turgutlu", forward players "Alice" (10) and "Bob" (11), announce scores for both
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        Player alice = new Player();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        team.getPlayers().add(alice);
        
        Player bob = new Player();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        team.getPlayers().add(bob);
        
        Announcement scoreAlice = new Announcement();
        scoreAlice.setPlayer(alice);
        scoreAlice.setType(AnnouncementType.SCORE);
        scoreAlice.setTime(dateFormat.parse("2024-01-15 15:35:00"));
        team.getAnnouncements().add(scoreAlice);
        
        Announcement scoreBob = new Announcement();
        scoreBob.setPlayer(bob);
        scoreBob.setType(AnnouncementType.SCORE);
        scoreBob.setTime(dateFormat.parse("2024-01-15 15:40:00"));
        team.getAnnouncements().add(scoreBob);
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        assertEquals(2, team.calculateTotalAnnouncementsForForwardPlayers());
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() throws Exception {
        // Test Case 3: "Total Announcements for Forward Players with Saves"
        // SetUp: Create football team "Turgutlu", forward player "Tom" (7), goalkeeper "Mike" (1), announce save for Mike
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        Player tom = new Player();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        team.getPlayers().add(tom);
        
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        team.getPlayers().add(mike);
        
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setPlayer(mike);
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setTime(dateFormat.parse("2024-01-15 15:45:00"));
        team.getAnnouncements().add(saveAnnouncement);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals(0, team.calculateTotalAnnouncementsForForwardPlayers());
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() throws Exception {
        // Test Case 4: "Total Announcements for Forward Players with Scores and Saves"
        // SetUp: Create football team "Turgutlu", forward players "Emma" (8) and "Lucas" (12), 
        // announce score for Emma, save for Mike (goalkeeper)
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        Player emma = new Player();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        team.getPlayers().add(emma);
        
        Player lucas = new Player();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        team.getPlayers().add(lucas);
        
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        team.getPlayers().add(mike);
        
        Announcement scoreEmma = new Announcement();
        scoreEmma.setPlayer(emma);
        scoreEmma.setType(AnnouncementType.SCORE);
        scoreEmma.setTime(dateFormat.parse("2024-01-15 15:50:00"));
        team.getAnnouncements().add(scoreEmma);
        
        Announcement saveMike = new Announcement();
        saveMike.setPlayer(mike);
        saveMike.setType(AnnouncementType.SAVE);
        saveMike.setTime(dateFormat.parse("2024-01-15 15:55:00"));
        team.getAnnouncements().add(saveMike);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals(1, team.calculateTotalAnnouncementsForForwardPlayers());
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() throws Exception {
        // Test Case 5: "Total Announcements with No Forward Players"
        // SetUp: Create football team "Turgutlu", midfield player "Noah" (5) and defender "Liam" (4),
        // announce some saves and scores for others, but none for forwards
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        Player noah = new Player();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        team.getPlayers().add(noah);
        
        Player liam = new Player();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        team.getPlayers().add(liam);
        
        Announcement scoreNoah = new Announcement();
        scoreNoah.setPlayer(noah);
        scoreNoah.setType(AnnouncementType.SCORE);
        scoreNoah.setTime(dateFormat.parse("2024-01-15 16:00:00"));
        team.getAnnouncements().add(scoreNoah);
        
        Announcement saveLiam = new Announcement();
        saveLiam.setPlayer(liam);
        saveLiam.setType(AnnouncementType.SAVE);
        saveLiam.setTime(dateFormat.parse("2024-01-15 16:05:00"));
        team.getAnnouncements().add(saveLiam);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        assertEquals(0, team.calculateTotalAnnouncementsForForwardPlayers());
    }
}