import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        // Input: Calculate announcements for forward players where one player has scored.
        
        // SetUp: Create a football team named "Turgutlu"
        FootballTeam turgutlu = new FootballTeam();
        
        // Create a forward player named "John" with number 9
        Player john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        // Add John to team players
        List<Player> players = new ArrayList<>();
        players.add(john);
        turgutlu.setPlayers(players);
        
        // Announce a score for John
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(john);
        scoreAnnouncement.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAnnouncement);
        turgutlu.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        int result = turgutlu.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 1 for single forward player with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() throws Exception {
        // Test Case 2: "Total Announcements for Multiple Forward Players with Scores"
        // Input: Calculate announcements for forward players with multiple scores.
        
        // SetUp: Create a football team named "Turgutlu"
        FootballTeam turgutlu = new FootballTeam();
        
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
        List<Player> players = new ArrayList<>();
        players.add(alice);
        players.add(bob);
        turgutlu.setPlayers(players);
        
        // Announce a score for Alice and a score for Bob
        Announcement aliceScore = new Announcement();
        aliceScore.setType(AnnouncementType.SCORE);
        aliceScore.setPlayer(alice);
        aliceScore.setTime(dateFormat.parse("2024-01-15 15:35:00"));
        
        Announcement bobScore = new Announcement();
        bobScore.setType(AnnouncementType.SCORE);
        bobScore.setPlayer(bob);
        bobScore.setTime(dateFormat.parse("2024-01-15 15:40:00"));
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(aliceScore);
        announcements.add(bobScore);
        turgutlu.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        int result = turgutlu.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 2 for multiple forward players with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() throws Exception {
        // Test Case 3: "Total Announcements for Forward Players with Saves"
        // Input: Calculate announcements for forward players where no scores but one save.
        
        // SetUp: Create a football team named "Turgutlu"
        FootballTeam turgutlu = new FootballTeam();
        
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
        List<Player> players = new ArrayList<>();
        players.add(tom);
        players.add(mike);
        turgutlu.setPlayers(players);
        
        // Announce a save for Mike
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(mike);
        saveAnnouncement.setTime(dateFormat.parse("2024-01-15 15:45:00"));
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(saveAnnouncement);
        turgutlu.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        int result = turgutlu.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 0 when only goalkeeper has save announcements", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() throws Exception {
        // Test Case 4: "Total Announcements for Forward Players with Scores and Saves"
        // Input: Calculate announcements for forward players with mixed announcements.
        
        // SetUp: Create a football team named "Turgutlu"
        FootballTeam turgutlu = new FootballTeam();
        
        // Create forward players: "Emma" (number 8), "Lucas" (number 12)
        Player emma = new Player();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        
        Player lucas = new Player();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        
        // Create goalkeeper "Mike" for save announcement
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        // Add players to team
        List<Player> players = new ArrayList<>();
        players.add(emma);
        players.add(lucas);
        players.add(mike);
        turgutlu.setPlayers(players);
        
        // Announce a score for Emma
        Announcement emmaScore = new Announcement();
        emmaScore.setType(AnnouncementType.SCORE);
        emmaScore.setPlayer(emma);
        emmaScore.setTime(dateFormat.parse("2024-01-15 15:50:00"));
        
        // Announce a save for Mike (goalkeeper)
        Announcement mikeSave = new Announcement();
        mikeSave.setType(AnnouncementType.SAVE);
        mikeSave.setPlayer(mike);
        mikeSave.setTime(dateFormat.parse("2024-01-15 15:55:00"));
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(emmaScore);
        announcements.add(mikeSave);
        turgutlu.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        int result = turgutlu.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 1 (only forward's score counts, goalkeeper's save doesn't)", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() throws Exception {
        // Test Case 5: "Total Announcements with No Forward Players"
        // Input: Calculate announcements for a team with no forward players.
        
        // SetUp: Create a football team named "Turgutlu"
        FootballTeam turgutlu = new FootballTeam();
        
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
        List<Player> players = new ArrayList<>();
        players.add(noah);
        players.add(liam);
        turgutlu.setPlayers(players);
        
        // Announce some saves and scores for others, but none for forwards
        Announcement noahScore = new Announcement();
        noahScore.setType(AnnouncementType.SCORE);
        noahScore.setPlayer(noah);
        noahScore.setTime(dateFormat.parse("2024-01-15 16:00:00"));
        
        Announcement liamSave = new Announcement();
        liamSave.setType(AnnouncementType.SCORE); // Even if defender scores, it shouldn't count
        liamSave.setPlayer(liam);
        liamSave.setTime(dateFormat.parse("2024-01-15 16:05:00"));
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(noahScore);
        announcements.add(liamSave);
        turgutlu.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        int result = turgutlu.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}