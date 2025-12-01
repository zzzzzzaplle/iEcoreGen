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
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Create a forward player named "John" with number 9
        Player john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        team.getPlayers().add(john);
        
        // Announce a score for John
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(john);
        scoreAnnouncement.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        team.getAnnouncements().add(scoreAnnouncement);
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Create forward players: "Alice" (number 10), "Bob" (number 11)
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
        
        // Announce a score for Alice and a score for Bob
        Announcement scoreAlice = new Announcement();
        scoreAlice.setType(AnnouncementType.SCORE);
        scoreAlice.setPlayer(alice);
        scoreAlice.setTime(dateFormat.parse("2024-01-15 15:35:00"));
        team.getAnnouncements().add(scoreAlice);
        
        Announcement scoreBob = new Announcement();
        scoreBob.setType(AnnouncementType.SCORE);
        scoreBob.setPlayer(bob);
        scoreBob.setTime(dateFormat.parse("2024-01-15 15:40:00"));
        team.getAnnouncements().add(scoreBob);
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Create a forward player named "Tom" with number 7
        Player tom = new Player();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        team.getPlayers().add(tom);
        
        // Create a goalkeeper named "Mike" with number 1
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        team.getPlayers().add(mike);
        
        // Announce a save for Mike
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(mike);
        saveAnnouncement.setTime(dateFormat.parse("2024-01-15 15:45:00"));
        team.getAnnouncements().add(saveAnnouncement);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Create forward players: "Emma" (number 8), "Lucas" (number 12)
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
        
        // Create goalkeeper Mike for the save announcement
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        team.getPlayers().add(mike);
        
        // Announce a score for Emma
        Announcement scoreEmma = new Announcement();
        scoreEmma.setType(AnnouncementType.SCORE);
        scoreEmma.setPlayer(emma);
        scoreEmma.setTime(dateFormat.parse("2024-01-15 15:50:00"));
        team.getAnnouncements().add(scoreEmma);
        
        // Announce a save for Mike (goalkeeper)
        Announcement saveMike = new Announcement();
        saveMike.setType(AnnouncementType.SAVE);
        saveMike.setPlayer(mike);
        saveMike.setTime(dateFormat.parse("2024-01-15 15:55:00"));
        team.getAnnouncements().add(saveMike);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
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
        
        // Create goalkeeper for announcements
        Player keeper = new Player();
        keeper.setName("Keeper");
        keeper.setNumber(1);
        keeper.setPosition(Position.GOALKEEPER);
        team.getPlayers().add(keeper);
        
        // Announce some saves and scores for others, but none for forwards
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(keeper);
        saveAnnouncement.setTime(dateFormat.parse("2024-01-15 16:00:00"));
        team.getAnnouncements().add(saveAnnouncement);
        
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(noah); // midfield player scoring
        scoreAnnouncement.setTime(dateFormat.parse("2024-01-15 16:05:00"));
        team.getAnnouncements().add(scoreAnnouncement);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
    }
}