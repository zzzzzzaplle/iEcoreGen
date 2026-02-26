import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
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
        // Create football team
        team.setName("Turgutlu");
        
        // Create forward player
        Player john = new Player();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        
        // Add player to team
        List<Player> players = new ArrayList<>();
        players.add(john);
        team.setPlayers(players);
        
        // Create score announcement for John
        Announcement scoreAnnouncement = new Announcement();
        scoreAnnouncement.setPlayer(john);
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setTime(new Date());
        
        // Add announcement to team
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAnnouncement);
        team.setAnnouncements(announcements);
        
        // Verify total announcements for forwards
        assertEquals(1, team.calculateTotalAnnouncementsForForwardPlayers());
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Create football team
        team.setName("Turgutlu");
        
        // Create forward players
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
        
        // Create score announcements
        Announcement scoreAlice = new Announcement();
        scoreAlice.setPlayer(alice);
        scoreAlice.setType(AnnouncementType.SCORE);
        scoreAlice.setTime(new Date());
        
        Announcement scoreBob = new Announcement();
        scoreBob.setPlayer(bob);
        scoreBob.setType(AnnouncementType.SCORE);
        scoreBob.setTime(new Date());
        
        // Add announcements to team
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreAlice);
        announcements.add(scoreBob);
        team.setAnnouncements(announcements);
        
        // Verify total announcements for forwards
        assertEquals(2, team.calculateTotalAnnouncementsForForwardPlayers());
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // Create football team
        team.setName("Turgutlu");
        
        // Create forward player and goalkeeper
        Player tom = new Player();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        
        Player mike = new Player();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        
        // Add players to team
        List<Player> players = new ArrayList<>();
        players.add(tom);
        players.add(mike);
        team.setPlayers(players);
        
        // Create save announcement for goalkeeper (not forward)
        Announcement saveAnnouncement = new Announcement();
        saveAnnouncement.setPlayer(mike);
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setTime(new Date());
        
        // Add announcement to team
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(saveAnnouncement);
        team.setAnnouncements(announcements);
        
        // Verify total announcements for forwards (should be 0)
        assertEquals(0, team.calculateTotalAnnouncementsForForwardPlayers());
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Create football team
        team.setName("Turgutlu");
        
        // Create forward players and goalkeeper
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
        
        // Add players to team
        List<Player> players = new ArrayList<>();
        players.add(emma);
        players.add(lucas);
        players.add(mike);
        team.setPlayers(players);
        
        // Create score announcement for forward and save for goalkeeper
        Announcement scoreEmma = new Announcement();
        scoreEmma.setPlayer(emma);
        scoreEmma.setType(AnnouncementType.SCORE);
        scoreEmma.setTime(new Date());
        
        Announcement saveMike = new Announcement();
        saveMike.setPlayer(mike);
        saveMike.setType(AnnouncementType.SAVE);
        saveMike.setTime(new Date());
        
        // Add announcements to team
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreEmma);
        announcements.add(saveMike);
        team.setAnnouncements(announcements);
        
        // Verify total announcements for forwards (only score counts)
        assertEquals(1, team.calculateTotalAnnouncementsForForwardPlayers());
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // Create football team
        team.setName("Turgutlu");
        
        // Create midfield and defender players (no forwards)
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
        
        // Create announcements (none for forwards)
        Announcement scoreNoah = new Announcement();
        scoreNoah.setPlayer(noah);
        scoreNoah.setType(AnnouncementType.SCORE);
        scoreNoah.setTime(new Date());
        
        Announcement saveLiam = new Announcement();
        saveLiam.setPlayer(liam);
        saveLiam.setType(AnnouncementType.SAVE);
        saveLiam.setTime(new Date());
        
        // Add announcements to team
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(scoreNoah);
        announcements.add(saveLiam);
        team.setAnnouncements(announcements);
        
        // Verify total announcements for forwards (should be 0)
        assertEquals(0, team.calculateTotalAnnouncementsForForwardPlayers());
    }
}