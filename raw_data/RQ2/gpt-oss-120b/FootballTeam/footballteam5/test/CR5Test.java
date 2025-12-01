package edu.footballteam.footballteam5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.footballteam.FootballteamFactory;
import edu.footballteam.FootballTeam;
import edu.footballteam.Player;
import edu.footballteam.Announcement;
import edu.footballteam.AnnouncementType;
import edu.footballteam.Position;
import java.util.Date;

public class CR5Test {
    
    private FootballteamFactory factory;
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory and create test objects
        factory = FootballteamFactory.eINSTANCE;
        team = factory.createFootballTeam();
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Test Case 1: "Total Announcements for Single Forward Player with One Score"
        
        // SetUp: Create a football team named "Turgutlu"
        FootballTeam team = factory.createFootballTeam();
        
        // Create a forward player named "John" with number 9
        Player john = factory.createPlayer();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        team.getPlayers().add(john);
        
        // Announce a score for John
        Announcement announcement = factory.createAnnouncement();
        announcement.setType(AnnouncementType.SCORE);
        announcement.setPlayer(john);
        announcement.setTime(new Date());
        team.getAnnouncements().add(announcement);
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Should count 1 announcement for forward player John", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Test Case 2: "Total Announcements for Multiple Forward Players with Scores"
        
        // SetUp: Create a football team named "Turgutlu"
        FootballTeam team = factory.createFootballTeam();
        
        // Create forward players: "Alice" (number 10), "Bob" (number 11)
        Player alice = factory.createPlayer();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        team.getPlayers().add(alice);
        
        Player bob = factory.createPlayer();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        team.getPlayers().add(bob);
        
        // Announce a score for Alice and a score for Bob
        Announcement announcement1 = factory.createAnnouncement();
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setPlayer(alice);
        announcement1.setTime(new Date());
        team.getAnnouncements().add(announcement1);
        
        Announcement announcement2 = factory.createAnnouncement();
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setPlayer(bob);
        announcement2.setTime(new Date());
        team.getAnnouncements().add(announcement2);
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Should count 2 announcements for forward players Alice and Bob", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // Test Case 3: "Total Announcements for Forward Players with Saves"
        
        // SetUp: Create a football team named "Turgutlu"
        FootballTeam team = factory.createFootballTeam();
        
        // Create a forward player named "Tom" with number 7
        Player tom = factory.createPlayer();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        team.getPlayers().add(tom);
        
        // Create a goalkeeper named "Mike" with number 1
        Player mike = factory.createPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        team.getPlayers().add(mike);
        
        // Announce a save for Mike
        Announcement announcement = factory.createAnnouncement();
        announcement.setType(AnnouncementType.SAVE);
        announcement.setPlayer(mike);
        announcement.setTime(new Date());
        team.getAnnouncements().add(announcement);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Should count 0 announcements since no forward player made announcements", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Test Case 4: "Total Announcements for Forward Players with Scores and Saves"
        
        // SetUp: Create a football team named "Turgutlu"
        FootballTeam team = factory.createFootballTeam();
        
        // Create forward players: "Emma" (number 8), "Lucas" (number 12)
        Player emma = factory.createPlayer();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        team.getPlayers().add(emma);
        
        Player lucas = factory.createPlayer();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        team.getPlayers().add(lucas);
        
        // Create a goalkeeper named "Mike" with number 1
        Player mike = factory.createPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        team.getPlayers().add(mike);
        
        // Announce a score for Emma
        Announcement scoreAnnouncement = factory.createAnnouncement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(emma);
        scoreAnnouncement.setTime(new Date());
        team.getAnnouncements().add(scoreAnnouncement);
        
        // Announce a save for Mike (goalkeeper)
        Announcement saveAnnouncement = factory.createAnnouncement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(mike);
        saveAnnouncement.setTime(new Date());
        team.getAnnouncements().add(saveAnnouncement);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Should count 1 announcement for forward player Emma", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // Test Case 5: "Total Announcements with No Forward Players"
        
        // SetUp: Create a football team named "Turgutlu"
        FootballTeam team = factory.createFootballTeam();
        
        // Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        Player noah = factory.createPlayer();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        team.getPlayers().add(noah);
        
        Player liam = factory.createPlayer();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        team.getPlayers().add(liam);
        
        // Create a goalkeeper named "Mike" with number 1
        Player mike = factory.createPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        team.getPlayers().add(mike);
        
        // Announce some saves and scores for others, but none for forwards
        Announcement saveAnnouncement = factory.createAnnouncement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(mike);
        saveAnnouncement.setTime(new Date());
        team.getAnnouncements().add(saveAnnouncement);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals("Should count 0 announcements since no forward players exist", 0, result);
    }
}