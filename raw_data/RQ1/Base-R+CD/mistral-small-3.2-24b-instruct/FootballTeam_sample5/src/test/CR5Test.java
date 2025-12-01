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
        // SetUp: Create team, forward player, and score announcement
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
        scoreAnnouncement.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAnnouncement);
        team.setAnnouncements(announcements);
        
        // Execute and verify
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 1 for single forward with score", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // SetUp: Create team, multiple forward players, and score announcements
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
        scoreAnnouncement1.setTime(new Date());
        
        Announcement scoreAnnouncement2 = new Announcement();
        scoreAnnouncement2.setType(AnnouncementType.SCORE);
        scoreAnnouncement2.setPlayer(bob);
        scoreAnnouncement2.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAnnouncement1);
        announcements.add(scoreAnnouncement2);
        team.setAnnouncements(announcements);
        
        // Execute and verify
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 2 for multiple forwards with scores", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // SetUp: Create team with forward player and goalkeeper, but only save announcement for goalkeeper
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
        saveAnnouncement.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(saveAnnouncement);
        team.setAnnouncements(announcements);
        
        // Execute and verify
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 0 when only saves exist for goalkeeper", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // SetUp: Create team with forward players, score for forward and save for goalkeeper
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
        scoreAnnouncement.setTime(new Date());
        
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(mike);
        saveAnnouncement.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAnnouncement);
        announcements.add(saveAnnouncement);
        team.setAnnouncements(announcements);
        
        // Execute and verify
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 1 (only score for forward, save doesn't count)", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // SetUp: Create team with no forward players, only midfield and defense
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
        scoreAnnouncement.setPlayer(noah);
        scoreAnnouncement.setTime(new Date());
        
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(liam);
        saveAnnouncement.setTime(new Date());
        
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAnnouncement);
        announcements.add(saveAnnouncement);
        team.setAnnouncements(announcements);
        
        // Execute and verify
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Total announcements should be 0 when no forward players exist", 0, result);
    }
}