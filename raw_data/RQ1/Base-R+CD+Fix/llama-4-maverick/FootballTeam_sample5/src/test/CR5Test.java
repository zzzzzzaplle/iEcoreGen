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
        // Create a football team named "Turgutlu"
        FootballTeam team = new FootballTeam();
        
        // Create a forward player named "John" with number 9
        Player john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        john.setAge(25);
        
        // Add John to players and first eleven
        team.getPlayers().add(john);
        team.getFirstEleven().add(john);
        
        // Announce a score for John
        Announcement announcement = new Announcement();
        announcement.setPlayer(john);
        announcement.setType(AnnouncementType.SCORE);
        announcement.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        team.getAnnouncements().add(announcement);
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() throws Exception {
        // Create a football team named "Turgutlu"
        FootballTeam team = new FootballTeam();
        
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
        
        // Add players to team
        team.getPlayers().add(alice);
        team.getPlayers().add(bob);
        team.getFirstEleven().add(alice);
        team.getFirstEleven().add(bob);
        
        // Announce a score for Alice and a score for Bob
        Announcement announcement1 = new Announcement();
        announcement1.setPlayer(alice);
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setTime(dateFormat.parse("2024-01-15 15:35:00"));
        team.getAnnouncements().add(announcement1);
        
        Announcement announcement2 = new Announcement();
        announcement2.setPlayer(bob);
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setTime(dateFormat.parse("2024-01-15 15:40:00"));
        team.getAnnouncements().add(announcement2);
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() throws Exception {
        // Create a football team named "Turgutlu"
        FootballTeam team = new FootballTeam();
        
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
        
        // Add players to team
        team.getPlayers().add(tom);
        team.getPlayers().add(mike);
        team.getFirstEleven().add(tom);
        team.getFirstEleven().add(mike);
        
        // Announce a save for Mike
        Announcement announcement = new Announcement();
        announcement.setPlayer(mike);
        announcement.setType(AnnouncementType.SAVE);
        announcement.setTime(dateFormat.parse("2024-01-15 15:45:00"));
        team.getAnnouncements().add(announcement);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() throws Exception {
        // Create a football team named "Turgutlu"
        FootballTeam team = new FootballTeam();
        
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
        lucas.setAge(27);
        
        // Create a goalkeeper named "Mike" with number 1
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        mike.setAge(28);
        
        // Add players to team
        team.getPlayers().add(emma);
        team.getPlayers().add(lucas);
        team.getPlayers().add(mike);
        team.getFirstEleven().add(emma);
        team.getFirstEleven().add(lucas);
        team.getFirstEleven().add(mike);
        
        // Announce a score for Emma
        Announcement announcement1 = new Announcement();
        announcement1.setPlayer(emma);
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setTime(dateFormat.parse("2024-01-15 15:50:00"));
        team.getAnnouncements().add(announcement1);
        
        // Announce a save for Mike (goalkeeper)
        Announcement announcement2 = new Announcement();
        announcement2.setPlayer(mike);
        announcement2.setType(AnnouncementType.SAVE);
        announcement2.setTime(dateFormat.parse("2024-01-15 15:55:00"));
        team.getAnnouncements().add(announcement2);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() throws Exception {
        // Create a football team named "Turgutlu"
        FootballTeam team = new FootballTeam();
        
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
        
        // Create a goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("David");
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setAge(30);
        
        // Add players to team
        team.getPlayers().add(noah);
        team.getPlayers().add(liam);
        team.getPlayers().add(goalkeeper);
        team.getFirstEleven().add(noah);
        team.getFirstEleven().add(liam);
        team.getFirstEleven().add(goalkeeper);
        
        // Announce some saves and scores for others, but none for forwards
        Announcement announcement1 = new Announcement();
        announcement1.setPlayer(goalkeeper);
        announcement1.setType(AnnouncementType.SAVE);
        announcement1.setTime(dateFormat.parse("2024-01-15 16:00:00"));
        team.getAnnouncements().add(announcement1);
        
        Announcement announcement2 = new Announcement();
        announcement2.setPlayer(noah);
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setTime(dateFormat.parse("2024-01-15 16:05:00"));
        team.getAnnouncements().add(announcement2);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
    }
}