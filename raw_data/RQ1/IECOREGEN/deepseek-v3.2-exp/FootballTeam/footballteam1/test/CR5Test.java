package edu.footballteam.footballteam1.test;

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
        // Initialize factory using Ecore factory pattern
        factory = FootballteamFactory.eINSTANCE;
        // Create football team
        team = factory.createFootballTeam();
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() {
        // Test Case 1: "Total Announcements for Single Forward Player with One Score"
        
        // SetUp: Create a forward player named "John" with number 9
        Player john = factory.createPlayer();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        team.getPlayers().add(john);
        
        // SetUp: Announce a score for John
        Announcement scoreAnnouncement = factory.createAnnouncement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(john);
        scoreAnnouncement.setTime(new Date());
        team.getAnnouncements().add(scoreAnnouncement);
        
        // Execute: Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Verify: Total announcements = 1 (SCORE for John)
        assertEquals("Should count 1 announcement for forward player John", 1, result);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() {
        // Test Case 2: "Total Announcements for Multiple Forward Players with Scores"
        
        // SetUp: Create forward players: "Alice" (number 10), "Bob" (number 11)
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
        
        // SetUp: Announce a score for Alice and a score for Bob
        Announcement aliceScore = factory.createAnnouncement();
        aliceScore.setType(AnnouncementType.SCORE);
        aliceScore.setPlayer(alice);
        aliceScore.setTime(new Date());
        team.getAnnouncements().add(aliceScore);
        
        Announcement bobScore = factory.createAnnouncement();
        bobScore.setType(AnnouncementType.SCORE);
        bobScore.setPlayer(bob);
        bobScore.setTime(new Date());
        team.getAnnouncements().add(bobScore);
        
        // Execute: Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Verify: Total announcements = 2 (SCORES for Alice and Bob)
        assertEquals("Should count 2 announcements for forward players Alice and Bob", 2, result);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() {
        // Test Case 3: "Total Announcements for Forward Players with Saves"
        
        // SetUp: Create a forward player named "Tom" with number 7
        Player tom = factory.createPlayer();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        team.getPlayers().add(tom);
        
        // SetUp: Create a goalkeeper named "Mike" with number 1
        Player mike = factory.createPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        team.getPlayers().add(mike);
        
        // SetUp: Announce a save for Mike
        Announcement saveAnnouncement = factory.createAnnouncement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(mike);
        saveAnnouncement.setTime(new Date());
        team.getAnnouncements().add(saveAnnouncement);
        
        // Execute: Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Verify: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals("Should return 0 as no forward players have announcements", 0, result);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() {
        // Test Case 4: "Total Announcements for Forward Players with Scores and Saves"
        
        // SetUp: Create forward players: "Emma" (number 8), "Lucas" (number 12)
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
        
        // SetUp: Create a goalkeeper named "Mike" with number 1
        Player mike = factory.createPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        team.getPlayers().add(mike);
        
        // SetUp: Announce a score for Emma
        Announcement emmaScore = factory.createAnnouncement();
        emmaScore.setType(AnnouncementType.SCORE);
        emmaScore.setPlayer(emma);
        emmaScore.setTime(new Date());
        team.getAnnouncements().add(emmaScore);
        
        // SetUp: Announce a save for Mike (goalkeeper)
        Announcement mikeSave = factory.createAnnouncement();
        mikeSave.setType(AnnouncementType.SAVE);
        mikeSave.setPlayer(mike);
        mikeSave.setTime(new Date());
        team.getAnnouncements().add(mikeSave);
        
        // Execute: Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Verify: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals("Should count only Emma's score announcement (1 total)", 1, result);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() {
        // Test Case 5: "Total Announcements with No Forward Players"
        
        // SetUp: Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
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
        
        // SetUp: Create a goalkeeper for save announcements
        Player keeper = factory.createPlayer();
        keeper.setName("Keeper");
        keeper.setNumber(1);
        keeper.setPosition(Position.GOALKEEPER);
        team.getPlayers().add(keeper);
        
        // SetUp: Announce some saves and scores for others, but none for forwards
        Announcement midfieldScore = factory.createAnnouncement();
        midfieldScore.setType(AnnouncementType.SCORE);
        midfieldScore.setPlayer(noah); // Midfield player scoring
        midfieldScore.setTime(new Date());
        team.getAnnouncements().add(midfieldScore);
        
        Announcement keeperSave = factory.createAnnouncement();
        keeperSave.setType(AnnouncementType.SAVE);
        keeperSave.setPlayer(keeper);
        keeperSave.setTime(new Date());
        team.getAnnouncements().add(keeperSave);
        
        // Execute: Calculate total announcements for forward players
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Verify: Total announcements = 0 (No forward player available)
        assertEquals("Should return 0 as no forward players exist in the team", 0, result);
    }
}