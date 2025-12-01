import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        // SetUp: Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Create a forward player named "John" with number 9
        Player john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        // Add John to the team
        team.addPlayer(john);
        
        // Announce a score for John
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setPlayer(john);
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        
        team.addAnnouncement(scoreAnnouncement);
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        assertEquals("Total announcements should be 1 for single forward with one score", 
                     1, team.calculateTotalAnnouncementsForForwardPlayers());
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
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
        
        // Add players to the team
        team.addPlayer(alice);
        team.addPlayer(bob);
        
        // Announce a score for Alice and a score for Bob
        Announcement scoreAlice = new Announcement();
        scoreAlice.setPlayer(alice);
        scoreAlice.setType(AnnouncementType.SCORE);
        scoreAlice.setTime(dateFormat.parse("2024-01-15 15:35:00"));
        
        Announcement scoreBob = new Announcement();
        scoreBob.setPlayer(bob);
        scoreBob.setType(AnnouncementType.SCORE);
        scoreBob.setTime(dateFormat.parse("2024-01-15 15:40:00"));
        
        team.addAnnouncement(scoreAlice);
        team.addAnnouncement(scoreBob);
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        assertEquals("Total announcements should be 2 for multiple forwards with scores", 
                     2, team.calculateTotalAnnouncementsForForwardPlayers());
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
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
        
        // Add players to the team
        team.addPlayer(tom);
        team.addPlayer(mike);
        
        // Announce a save for Mike
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setPlayer(mike);
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setTime(dateFormat.parse("2024-01-15 15:45:00"));
        
        team.addAnnouncement(saveAnnouncement);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals("Total announcements should be 0 when only goalkeeper has save announcements", 
                     0, team.calculateTotalAnnouncementsForForwardPlayers());
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
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
        
        // Add players to the team
        team.addPlayer(emma);
        team.addPlayer(lucas);
        team.addPlayer(mike);
        
        // Announce a score for Emma
        Announcement scoreEmma = new Announcement();
        scoreEmma.setPlayer(emma);
        scoreEmma.setType(AnnouncementType.SCORE);
        scoreEmma.setTime(dateFormat.parse("2024-01-15 15:50:00"));
        
        // Announce a save for Mike (goalkeeper)
        Announcement saveMike = new Announcement();
        saveMike.setPlayer(mike);
        saveMike.setType(AnnouncementType.SAVE);
        saveMike.setTime(dateFormat.parse("2024-01-15 15:55:00"));
        
        team.addAnnouncement(scoreEmma);
        team.addAnnouncement(saveMike);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals("Total announcements should be 1 for forward players with mixed announcements", 
                     1, team.calculateTotalAnnouncementsForForwardPlayers());
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
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
        
        // Create a goalkeeper named "Mike" with number 1
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        // Add players to the team
        team.addPlayer(noah);
        team.addPlayer(liam);
        team.addPlayer(mike);
        
        // Announce some saves and scores for others, but none for forwards
        Announcement scoreNoah = new Announcement();
        scoreNoah.setPlayer(noah);
        scoreNoah.setType(AnnouncementType.SCORE);
        scoreNoah.setTime(dateFormat.parse("2024-01-15 16:00:00"));
        
        Announcement saveMike = new Announcement();
        saveMike.setPlayer(mike);
        saveMike.setType(AnnouncementType.SAVE);
        saveMike.setTime(dateFormat.parse("2024-01-15 16:05:00"));
        
        team.addAnnouncement(scoreNoah);
        team.addAnnouncement(saveMike);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        assertEquals("Total announcements should be 0 when no forward players exist", 
                     0, team.calculateTotalAnnouncementsForForwardPlayers());
    }
}