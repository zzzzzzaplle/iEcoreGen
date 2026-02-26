import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;

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
        // Create a forward player named "John" with number 9
        Player john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        john.setAge(25);
        
        // Add John to players list
        team.setPlayers(Arrays.asList(john));
        
        // Announce a score for John
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(john);
        scoreAnnouncement.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        
        team.setAnnouncements(Arrays.asList(scoreAnnouncement));
        
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
        alice.setAge(23);
        
        Player bob = new Player();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        bob.setAge(24);
        
        // Add players to team
        team.setPlayers(Arrays.asList(alice, bob));
        
        // Announce a score for Alice and a score for Bob
        Announcement aliceScore = new Announcement();
        aliceScore.setType(AnnouncementType.SCORE);
        aliceScore.setPlayer(alice);
        aliceScore.setTime(dateFormat.parse("2024-01-15 16:00:00"));
        
        Announcement bobScore = new Announcement();
        bobScore.setType(AnnouncementType.SCORE);
        bobScore.setPlayer(bob);
        bobScore.setTime(dateFormat.parse("2024-01-15 16:15:00"));
        
        team.setAnnouncements(Arrays.asList(aliceScore, bobScore));
        
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
        tom.setAge(22);
        
        // Create a goalkeeper named "Mike" with number 1
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        mike.setAge(28);
        
        // Add players to team
        team.setPlayers(Arrays.asList(tom, mike));
        
        // Announce a save for Mike
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(mike);
        saveAnnouncement.setTime(dateFormat.parse("2024-01-15 16:30:00"));
        
        team.setAnnouncements(Arrays.asList(saveAnnouncement));
        
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
        emma.setAge(26);
        
        Player lucas = new Player();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        lucas.setAge(27);
        
        // Create a goalkeeper named "Mike" with number 1
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        mike.setAge(28);
        
        // Add players to team
        team.setPlayers(Arrays.asList(emma, lucas, mike));
        
        // Announce a score for Emma
        Announcement emmaScore = new Announcement();
        emmaScore.setType(AnnouncementType.SCORE);
        emmaScore.setPlayer(emma);
        emmaScore.setTime(dateFormat.parse("2024-01-15 17:00:00"));
        
        // Announce a save for Mike (goalkeeper)
        Announcement mikeSave = new Announcement();
        mikeSave.setType(AnnouncementType.SAVE);
        mikeSave.setPlayer(mike);
        mikeSave.setTime(dateFormat.parse("2024-01-15 17:05:00"));
        
        team.setAnnouncements(Arrays.asList(emmaScore, mikeSave));
        
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
        noah.setAge(25);
        
        Player liam = new Player();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        liam.setAge(26);
        
        // Add players to team
        team.setPlayers(Arrays.asList(noah, liam));
        
        // Announce some saves and scores for others, but none for forwards
        Announcement noahScore = new Announcement();
        noahScore.setType(AnnouncementType.SCORE);
        noahScore.setPlayer(noah);
        noahScore.setTime(dateFormat.parse("2024-01-15 17:10:00"));
        
        Announcement liamSave = new Announcement();
        liamSave.setType(AnnouncementType.SAVE);
        liamSave.setPlayer(liam);
        liamSave.setTime(dateFormat.parse("2024-01-15 17:15:00"));
        
        team.setAnnouncements(Arrays.asList(noahScore, liamSave));
        
        // Expected Output: Total announcements = 0 (No forward player available)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
    }
}