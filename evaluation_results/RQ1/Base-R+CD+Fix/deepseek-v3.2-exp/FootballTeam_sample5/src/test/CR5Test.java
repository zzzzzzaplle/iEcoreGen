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
        // SetUp: Create a football team named "Turgutlu"
        team = new FootballTeam(); // Team name is not stored in the class, so we just create the object
        
        // Create a forward player named "John" with number 9
        Player john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        john.setAge(25);
        
        // Add player to team's players list
        team.getPlayers().add(john);
        
        // Announce a score for John
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(john);
        scoreAnnouncement.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        
        // Add announcement to team's announcements list
        team.getAnnouncements().add(scoreAnnouncement);
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 1 for single forward player with one score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() throws Exception {
        // Test Case 2: "Total Announcements for Multiple Forward Players with Scores"
        // SetUp: Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Create forward players: "Alice" (number 10), "Bob" (number 11)
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
        
        // Add players to team's players list
        team.getPlayers().add(alice);
        team.getPlayers().add(bob);
        
        // Announce a score for Alice and a score for Bob
        Announcement aliceScore = new Announcement();
        aliceScore.setType(AnnouncementType.SCORE);
        aliceScore.setPlayer(alice);
        aliceScore.setTime(dateFormat.parse("2024-01-15 16:00:00"));
        
        Announcement bobScore = new Announcement();
        bobScore.setType(AnnouncementType.SCORE);
        bobScore.setPlayer(bob);
        bobScore.setTime(dateFormat.parse("2024-01-15 16:15:00"));
        
        // Add announcements to team's announcements list
        team.getAnnouncements().add(aliceScore);
        team.getAnnouncements().add(bobScore);
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 2 for multiple forward players with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() throws Exception {
        // Test Case 3: "Total Announcements for Forward Players with Saves"
        // SetUp: Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Create a forward player named "Tom" with number 7
        Player tom = new Player();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        tom.setAge(23);
        
        // Create a goalkeeper named "Mike" with number 1
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        mike.setAge(28);
        
        // Add players to team's players list
        team.getPlayers().add(tom);
        team.getPlayers().add(mike);
        
        // Announce a save for Mike
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(mike);
        saveAnnouncement.setTime(dateFormat.parse("2024-01-15 16:30:00"));
        
        // Add announcement to team's announcements list
        team.getAnnouncements().add(saveAnnouncement);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 0 when only goalkeeper has saves", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() throws Exception {
        // Test Case 4: "Total Announcements for Forward Players with Scores and Saves"
        // SetUp: Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Create forward players: "Emma" (number 8), "Lucas" (number 12)
        Player emma = new Player();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        emma.setAge(22);
        
        Player lucas = new Player();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        lucas.setAge(25);
        
        // Create a goalkeeper named "Mike" with number 1
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        mike.setAge(28);
        
        // Add players to team's players list
        team.getPlayers().add(emma);
        team.getPlayers().add(lucas);
        team.getPlayers().add(mike);
        
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
        
        // Add announcements to team's announcements list
        team.getAnnouncements().add(emmaScore);
        team.getAnnouncements().add(mikeSave);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 1 for forward players with mixed announcements", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() throws Exception {
        // Test Case 5: "Total Announcements with No Forward Players"
        // SetUp: Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        Player noah = new Player();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        noah.setAge(27);
        
        Player liam = new Player();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        liam.setAge(29);
        
        // Add players to team's players list
        team.getPlayers().add(noah);
        team.getPlayers().add(liam);
        
        // Announce some saves and scores for others, but none for forwards
        Announcement noahScore = new Announcement();
        noahScore.setType(AnnouncementType.SCORE);
        noahScore.setPlayer(noah);
        noahScore.setTime(dateFormat.parse("2024-01-15 17:10:00"));
        
        Announcement liamSave = new Announcement();
        liamSave.setType(AnnouncementType.SAVE);
        liamSave.setPlayer(liam);
        liamSave.setTime(dateFormat.parse("2024-01-15 17:15:00"));
        
        // Add announcements to team's announcements list
        team.getAnnouncements().add(noahScore);
        team.getAnnouncements().add(liamSave);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}