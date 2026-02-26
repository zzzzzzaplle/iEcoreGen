import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        // Initialize team before each test
        team = new FootballTeam();
        team.setName("Turgutlu");
        
        // Initialize players that might be used in tests
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
        // Test Case 1: Total Announcements for Single Forward Player with One Score
        // SetUp: Create team, forward player John, announce score for John
        List<Player> players = new ArrayList<>();
        players.add(john);
        team.setPlayers(players);
        
        List<Announcement> announcements = new ArrayList<>();
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(john);
        scoreAnnouncement.setTime(new Date());
        announcements.add(scoreAnnouncement);
        team.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Verify expected output: Total announcements = 1
        assertEquals("Total announcements should be 1 for single forward with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Test Case 2: Total Announcements for Multiple Forward Players with Scores
        // SetUp: Create team, forward players Alice and Bob, announce scores for both
        List<Player> players = new ArrayList<>();
        players.add(alice);
        players.add(bob);
        team.setPlayers(players);
        
        List<Announcement> announcements = new ArrayList<>();
        
        // Announce score for Alice
        Announcement aliceScore = new Announcement();
        aliceScore.setType(AnnouncementType.SCORE);
        aliceScore.setPlayer(alice);
        aliceScore.setTime(new Date());
        announcements.add(aliceScore);
        
        // Announce score for Bob
        Announcement bobScore = new Announcement();
        bobScore.setType(AnnouncementType.SCORE);
        bobScore.setPlayer(bob);
        bobScore.setTime(new Date());
        announcements.add(bobScore);
        
        team.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Verify expected output: Total announcements = 2
        assertEquals("Total announcements should be 2 for multiple forwards with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // Test Case 3: Total Announcements for Forward Players with Saves
        // SetUp: Create team with forward Tom and goalkeeper Mike, announce save for Mike
        List<Player> players = new ArrayList<>();
        players.add(tom);
        players.add(mike);
        team.setPlayers(players);
        
        List<Announcement> announcements = new ArrayList<>();
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(mike);
        saveAnnouncement.setTime(new Date());
        announcements.add(saveAnnouncement);
        team.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Verify expected output: Total announcements = 0 (no announcements for forwards)
        assertEquals("Total announcements should be 0 when only goalkeeper has saves", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Test Case 4: Total Announcements for Forward Players with Scores and Saves
        // SetUp: Create team with forwards Emma and Lucas, goalkeeper Mike
        // Announce score for Emma and save for Mike
        List<Player> players = new ArrayList<>();
        players.add(emma);
        players.add(lucas);
        players.add(mike);
        team.setPlayers(players);
        
        List<Announcement> announcements = new ArrayList<>();
        
        // Announce score for Emma (forward)
        Announcement emmaScore = new Announcement();
        emmaScore.setType(AnnouncementType.SCORE);
        emmaScore.setPlayer(emma);
        emmaScore.setTime(new Date());
        announcements.add(emmaScore);
        
        // Announce save for Mike (goalkeeper)
        Announcement mikeSave = new Announcement();
        mikeSave.setType(AnnouncementType.SAVE);
        mikeSave.setPlayer(mike);
        mikeSave.setTime(new Date());
        announcements.add(mikeSave);
        
        team.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Verify expected output: Total announcements = 1 (only Emma's score counts)
        assertEquals("Total announcements should be 1 for forward with score, ignoring goalkeeper save", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // Test Case 5: Total Announcements with No Forward Players
        // SetUp: Create team with midfielder Noah and defender Liam, announce some events
        List<Player> players = new ArrayList<>();
        players.add(noah);
        players.add(liam);
        team.setPlayers(players);
        
        List<Announcement> announcements = new ArrayList<>();
        
        // Announce score for Noah (midfielder)
        Announcement noahScore = new Announcement();
        noahScore.setType(AnnouncementType.SCORE);
        noahScore.setPlayer(noah);
        noahScore.setTime(new Date());
        announcements.add(noahScore);
        
        // Announce save for Liam (defender)
        Announcement liamSave = new Announcement();
        liamSave.setType(AnnouncementType.SAVE);
        liamSave.setPlayer(liam);
        liamSave.setTime(new Date());
        announcements.add(liamSave);
        
        team.setAnnouncements(announcements);
        
        // Execute the method under test
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Verify expected output: Total announcements = 0 (no forward players)
        assertEquals("Total announcements should be 0 when there are no forward players", 0, result);
    }
}