import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private FootballTeam team;
    private Player john, alice, bob, tom, mike, emma, lucas, noah, liam;
    
    @Before
    public void setUp() {
        // Create a football team named "Turgutlu"
        team = new FootballTeam();
        
        // Initialize players
        john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        alice = new Player();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        
        bob = new Player();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        
        tom = new Player();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        
        mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        emma = new Player();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        
        lucas = new Player();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        
        noah = new Player();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        
        liam = new Player();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
    }
    
    @Test
    public void testCase1_totalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Add John (forward) to the team's players list
        List<Player> players = new ArrayList<>();
        players.add(john);
        team.setPlayers(players);
        
        // Create a score announcement for John
        Announcement announcement = new Announcement();
        announcement.setTime(new Date());
        announcement.setType(AnnouncementType.SCORE);
        announcement.setPlayer(john);
        
        // Add the announcement to the team
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(announcement);
        team.setAnnouncements(announcements);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: 1 (SCORE for John)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase2_totalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Add Alice and Bob (forwards) to the team's players list
        List<Player> players = new ArrayList<>();
        players.add(alice);
        players.add(bob);
        team.setPlayers(players);
        
        // Create score announcements for Alice and Bob
        Announcement announcement1 = new Announcement();
        announcement1.setTime(new Date());
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setPlayer(alice);
        
        Announcement announcement2 = new Announcement();
        announcement2.setTime(new Date());
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setPlayer(bob);
        
        // Add the announcements to the team
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(announcement1);
        announcements.add(announcement2);
        team.setAnnouncements(announcements);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: 2 (SCORES for Alice and Bob)
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_totalAnnouncementsForForwardPlayersWithSaves() {
        // Add Tom (forward) and Mike (goalkeeper) to the team's players list
        List<Player> players = new ArrayList<>();
        players.add(tom);
        players.add(mike);
        team.setPlayers(players);
        
        // Create a save announcement for Mike (goalkeeper)
        Announcement announcement = new Announcement();
        announcement.setTime(new Date());
        announcement.setType(AnnouncementType.SAVE);
        announcement.setPlayer(mike);
        
        // Add the announcement to the team
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(announcement);
        team.setAnnouncements(announcements);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: 0 (No SCORE announcements for forward players)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_totalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Add Emma, Lucas (forwards) and Mike (goalkeeper) to the team's players list
        List<Player> players = new ArrayList<>();
        players.add(emma);
        players.add(lucas);
        players.add(mike);
        team.setPlayers(players);
        
        // Create a score announcement for Emma
        Announcement announcement1 = new Announcement();
        announcement1.setTime(new Date());
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setPlayer(emma);
        
        // Create a save announcement for Mike (goalkeeper)
        Announcement announcement2 = new Announcement();
        announcement2.setTime(new Date());
        announcement2.setType(AnnouncementType.SAVE);
        announcement2.setPlayer(mike);
        
        // Add the announcements to the team
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(announcement1);
        announcements.add(announcement2);
        team.setAnnouncements(announcements);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: 1 (SCORE for Emma; saves do not count)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_totalAnnouncementsWithNoForwardPlayers() {
        // Add Noah (midfield) and Liam (defense) to the team's players list (no forwards)
        List<Player> players = new ArrayList<>();
        players.add(noah);
        players.add(liam);
        team.setPlayers(players);
        
        // Create some announcements for non-forwards
        Announcement announcement1 = new Announcement();
        announcement1.setTime(new Date());
        announcement1.setType(AnnouncementType.SAVE);
        announcement1.setPlayer(noah);
        
        Announcement announcement2 = new Announcement();
        announcement2.setTime(new Date());
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setPlayer(liam);
        
        // Add the announcements to the team
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(announcement1);
        announcements.add(announcement2);
        team.setAnnouncements(announcements);
        
        // Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected output: 0 (No forward player available)
        assertEquals(0, result);
    }
}