import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Test Case 1: "Total Announcements for Single Forward Player with One Score"
        // SetUp: 
        // 1. Create a football team named "Turgutlu".
        // 2. Create a forward player named "John" with number 9.
        // 3. Announce a score for John.
        
        Player john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        List<Player> players = new ArrayList<>();
        players.add(john);
        team.setPlayers(players);
        
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(john);
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAnnouncement);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Test Case 2: "Total Announcements for Multiple Forward Players with Scores"
        // SetUp: 
        // 1. Create a football team named "Turgutlu".
        // 2. Create forward players: "Alice" (number 10), "Bob" (number 11).
        // 3. Announce a score for Alice and a score for Bob.
        
        Player alice = new Player();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        
        Player bob = new Player();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        
        List<Player> players = new ArrayList<>();
        players.add(alice);
        players.add(bob);
        team.setPlayers(players);
        
        Announcement scoreAnnouncement1 = new Announcement();
        scoreAnnouncement1.setType(AnnouncementType.SCORE);
        scoreAnnouncement1.setPlayer(alice);
        
        Announcement scoreAnnouncement2 = new Announcement();
        scoreAnnouncement2.setType(AnnouncementType.SCORE);
        scoreAnnouncement2.setPlayer(bob);
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAnnouncement1);
        announcements.add(scoreAnnouncement2);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // Test Case 3: "Total Announcements for Forward Players with Saves"
        // SetUp: 
        // 1. Create a football team named "Turgutlu".
        // 2. Create a forward player named "Tom" with number 7.
        // 3. Create a goalkeeper named "Mike" with number 1.
        // 4. Announce a save for Mike.
        
        Player tom = new Player();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        List<Player> players = new ArrayList<>();
        players.add(tom);
        players.add(mike);
        team.setPlayers(players);
        
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(mike);
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(saveAnnouncement);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Test Case 4: "Total Announcements for Forward Players with Scores and Saves"
        // SetUp: 
        // 1. Create a football team named "Turgutlu".
        // 2. Create forward players: "Emma" (number 8), "Lucas" (number 12).
        // 3. Announce a score for Emma.
        // 4. Announce a save for Mike (goalkeeper).
        
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
        
        List<Player> players = new ArrayList<>();
        players.add(emma);
        players.add(lucas);
        players.add(mike);
        team.setPlayers(players);
        
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(emma);
        
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(mike);
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAnnouncement);
        announcements.add(saveAnnouncement);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // Test Case 5: "Total Announcements with No Forward Players"
        // SetUp: 
        // 1. Create a football team named "Turgutlu".
        // 2. Create midfield player "Noah" (number 5) and defender "Liam" (number 4).
        // 3. Announce some saves and scores for others, but none for forwards.
        
        Player noah = new Player();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        
        Player liam = new Player();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        
        List<Player> players = new ArrayList<>();
        players.add(noah);
        players.add(liam);
        team.setPlayers(players);
        
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(noah); // Midfielder scoring
        
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(liam); // Defender saving (though unusual)
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAnnouncement);
        announcements.add(saveAnnouncement);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
    }
}