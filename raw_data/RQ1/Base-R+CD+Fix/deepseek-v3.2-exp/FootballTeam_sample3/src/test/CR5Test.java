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
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Create a forward player named "John" with number 9
        Player john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        // Add John to players list
        ArrayList<Player> players = new ArrayList<>();
        players.add(john);
        team.setPlayers(players);
        
        // Announce a score for John
        Announcement announcement = new Announcement();
        announcement.setPlayer(john);
        announcement.setType(AnnouncementType.SCORE);
        announcement.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        
        ArrayList<Announcement> announcements = new ArrayList<>();
        announcements.add(announcement);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 1 for single forward with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() throws Exception {
        // Test Case 2: "Total Announcements for Multiple Forward Players with Scores"
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        
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
        ArrayList<Player> players = new ArrayList<>();
        players.add(alice);
        players.add(bob);
        team.setPlayers(players);
        
        // Announce a score for Alice and a score for Bob
        Announcement scoreAlice = new Announcement();
        scoreAlice.setPlayer(alice);
        scoreAlice.setType(AnnouncementType.SCORE);
        scoreAlice.setTime(dateFormat.parse("2024-01-15 16:00:00"));
        
        Announcement scoreBob = new Announcement();
        scoreBob.setPlayer(bob);
        scoreBob.setType(AnnouncementType.SCORE);
        scoreBob.setTime(dateFormat.parse("2024-01-15 16:15:00"));
        
        ArrayList<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAlice);
        announcements.add(scoreBob);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 2 for multiple forwards with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() throws Exception {
        // Test Case 3: "Total Announcements for Forward Players with Saves"
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        
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
        ArrayList<Player> players = new ArrayList<>();
        players.add(tom);
        players.add(mike);
        team.setPlayers(players);
        
        // Announce a save for Mike
        Announcement saveMike = new Announcement();
        saveMike.setPlayer(mike);
        saveMike.setType(AnnouncementType.SAVE);
        saveMike.setTime(dateFormat.parse("2024-01-15 17:00:00"));
        
        ArrayList<Announcement> announcements = new ArrayList<>();
        announcements.add(saveMike);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 0 when only goalkeeper has saves", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() throws Exception {
        // Test Case 4: "Total Announcements for Forward Players with Scores and Saves"
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        
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
        ArrayList<Player> players = new ArrayList<>();
        players.add(emma);
        players.add(lucas);
        players.add(mike);
        team.setPlayers(players);
        
        // Announce a score for Emma
        Announcement scoreEmma = new Announcement();
        scoreEmma.setPlayer(emma);
        scoreEmma.setType(AnnouncementType.SCORE);
        scoreEmma.setTime(dateFormat.parse("2024-01-15 18:00:00"));
        
        // Announce a save for Mike (goalkeeper)
        Announcement saveMike = new Announcement();
        saveMike.setPlayer(mike);
        saveMike.setType(AnnouncementType.SAVE);
        saveMike.setTime(dateFormat.parse("2024-01-15 18:15:00"));
        
        ArrayList<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreEmma);
        announcements.add(saveMike);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 1 when forward has score and goalkeeper has save", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() throws Exception {
        // Test Case 5: "Total Announcements with No Forward Players"
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        
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
        ArrayList<Player> players = new ArrayList<>();
        players.add(noah);
        players.add(liam);
        team.setPlayers(players);
        
        // Announce some saves and scores for others, but none for forwards
        Announcement scoreNoah = new Announcement();
        scoreNoah.setPlayer(noah);
        scoreNoah.setType(AnnouncementType.SCORE);
        scoreNoah.setTime(dateFormat.parse("2024-01-15 19:00:00"));
        
        Announcement saveLiam = new Announcement();
        saveLiam.setPlayer(liam);
        saveLiam.setType(AnnouncementType.SAVE);
        saveLiam.setTime(dateFormat.parse("2024-01-15 19:15:00"));
        
        ArrayList<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreNoah);
        announcements.add(saveLiam);
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}