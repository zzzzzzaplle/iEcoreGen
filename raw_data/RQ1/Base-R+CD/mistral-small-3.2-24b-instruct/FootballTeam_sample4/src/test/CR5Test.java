import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
        // SetUp: 
        // 1. Create a football team named "Turgutlu".
        // 2. Create a forward player named "John" with number 9.
        // 3. Announce a score for John.
        
        // Create forward player John
        Player john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        john.setAge(25);
        
        // Add John to players list (required since method checks all players)
        List<Player> players = new ArrayList<>();
        players.add(john);
        team.setPlayers(players);
        
        // Create score announcement for John
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setPlayer(john);
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setTime(dateFormat.parse("2024-01-01 15:30:00"));
        
        // Add announcement to team
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAnnouncement);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 1 for single forward player with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() throws Exception {
        // Test Case 2: "Total Announcements for Multiple Forward Players with Scores"
        // SetUp: 
        // 1. Create a football team named "Turgutlu".
        // 2. Create forward players: "Alice" (number 10), "Bob" (number 11).
        // 3. Announce a score for Alice and a score for Bob.
        
        // Create forward players Alice and Bob
        Player alice = new Player();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        alice.setAge(24);
        
        Player bob = new Player();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        bob.setAge(26);
        
        // Add players to team
        List<Player> players = new ArrayList<>();
        players.add(alice);
        players.add(bob);
        team.setPlayers(players);
        
        // Create score announcements for Alice and Bob
        Announcement aliceScore = new Announcement();
        aliceScore.setPlayer(alice);
        aliceScore.setType(AnnouncementType.SCORE);
        aliceScore.setTime(dateFormat.parse("2024-01-01 15:35:00"));
        
        Announcement bobScore = new Announcement();
        bobScore.setPlayer(bob);
        bobScore.setType(AnnouncementType.SCORE);
        bobScore.setTime(dateFormat.parse("2024-01-01 15:40:00"));
        
        // Add announcements to team
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(aliceScore);
        announcements.add(bobScore);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 2 for multiple forward players with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() throws Exception {
        // Test Case 3: "Total Announcements for Forward Players with Saves"
        // SetUp: 
        // 1. Create a football team named "Turgutlu".
        // 2. Create a forward player named "Tom" with number 7.
        // 3. Create a goalkeeper named "Mike" with number 1.
        // 4. Announce a save for Mike.
        
        // Create forward player Tom and goalkeeper Mike
        Player tom = new Player();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        tom.setAge(23);
        
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        mike.setAge(28);
        
        // Add players to team
        List<Player> players = new ArrayList<>();
        players.add(tom);
        players.add(mike);
        team.setPlayers(players);
        
        // Create save announcement for Mike (goalkeeper)
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setPlayer(mike);
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setTime(dateFormat.parse("2024-01-01 15:45:00"));
        
        // Add announcement to team
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(saveAnnouncement);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 0 when only goalkeeper has announcements", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() throws Exception {
        // Test Case 4: "Total Announcements for Forward Players with Scores and Saves"
        // SetUp: 
        // 1. Create a football team named "Turgutlu".
        // 2. Create forward players: "Emma" (number 8), "Lucas" (number 12).
        // 3. Announce a score for Emma.
        // 4. Announce a save for Mike (goalkeeper).
        
        // Create forward players Emma and Lucas, and goalkeeper Mike
        Player emma = new Player();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        emma.setAge(22);
        
        Player lucas = new Player();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        lucas.setAge(27);
        
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        mike.setAge(28);
        
        // Add players to team
        List<Player> players = new ArrayList<>();
        players.add(emma);
        players.add(lucas);
        players.add(mike);
        team.setPlayers(players);
        
        // Create score announcement for Emma and save announcement for Mike
        Announcement emmaScore = new Announcement();
        emmaScore.setPlayer(emma);
        emmaScore.setType(AnnouncementType.SCORE);
        emmaScore.setTime(dateFormat.parse("2024-01-01 15:50:00"));
        
        Announcement mikeSave = new Announcement();
        mikeSave.setPlayer(mike);
        mikeSave.setType(AnnouncementType.SAVE);
        mikeSave.setTime(dateFormat.parse("2024-01-01 15:55:00"));
        
        // Add announcements to team
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(emmaScore);
        announcements.add(mikeSave);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 1 (only forward's score counts)", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() throws Exception {
        // Test Case 5: "Total Announcements with No Forward Players"
        // SetUp: 
        // 1. Create a football team named "Turgutlu".
        // 2. Create midfield player "Noah" (number 5) and defender "Liam" (number 4).
        // 3. Announce some saves and scores for others, but none for forwards.
        
        // Create midfield player Noah and defender Liam
        Player noah = new Player();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        noah.setAge(25);
        
        Player liam = new Player();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        liam.setAge(26);
        
        // Add players to team (no forward players)
        List<Player> players = new ArrayList<>();
        players.add(noah);
        players.add(liam);
        team.setPlayers(players);
        
        // Create announcements for non-forward players
        Announcement noahScore = new Announcement();
        noahScore.setPlayer(noah);
        noahScore.setType(AnnouncementType.SCORE);
        noahScore.setTime(dateFormat.parse("2024-01-01 16:00:00"));
        
        Announcement liamSave = new Announcement();
        liamSave.setPlayer(liam);
        liamSave.setType(AnnouncementType.SAVE);
        liamSave.setTime(dateFormat.parse("2024-01-01 16:05:00"));
        
        // Add announcements to team
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(noahScore);
        announcements.add(liamSave);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}