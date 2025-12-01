import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Create a forward player named "John" with number 9
        Player john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        // Add player to team
        List<Player> players = new ArrayList<>();
        players.add(john);
        team.setPlayers(players);
        
        // Announce a score for John
        Announcement announcement = new Announcement();
        announcement.setType(AnnouncementType.SCORE);
        announcement.setPlayer(john);
        announcement.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(announcement);
        team.setAnnouncements(announcements);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
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
        team.setPlayers(players);
        
        // Announce a score for Alice and a score for Bob
        Announcement announcement1 = new Announcement();
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setPlayer(alice);
        announcement1.setTime(new Date());
        
        Announcement announcement2 = new Announcement();
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setPlayer(bob);
        announcement2.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(announcement1);
        announcements.add(announcement2);
        team.setAnnouncements(announcements);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
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
        team.setPlayers(players);
        
        // Announce a save for Mike
        Announcement announcement = new Announcement();
        announcement.setType(AnnouncementType.SAVE);
        announcement.setPlayer(mike);
        announcement.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(announcement);
        team.setAnnouncements(announcements);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
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
        List<Player> players = new ArrayList<>();
        players.add(emma);
        players.add(lucas);
        players.add(mike);
        team.setPlayers(players);
        
        // Announce a score for Emma
        Announcement announcement1 = new Announcement();
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setPlayer(emma);
        announcement1.setTime(new Date());
        
        // Announce a save for Mike (goalkeeper)
        Announcement announcement2 = new Announcement();
        announcement2.setType(AnnouncementType.SAVE);
        announcement2.setPlayer(mike);
        announcement2.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(announcement1);
        announcements.add(announcement2);
        team.setAnnouncements(announcements);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
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
        team.setPlayers(players);
        
        // Announce some saves and scores for others, but none for forwards
        Announcement announcement1 = new Announcement();
        announcement1.setType(AnnouncementType.SAVE);
        announcement1.setPlayer(noah);
        announcement1.setTime(new Date());
        
        Announcement announcement2 = new Announcement();
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setPlayer(liam);
        announcement2.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(announcement1);
        announcements.add(announcement2);
        team.setAnnouncements(announcements);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected Output: Total announcements = 0 (No forward player available)
        assertEquals(0, result);
    }
}