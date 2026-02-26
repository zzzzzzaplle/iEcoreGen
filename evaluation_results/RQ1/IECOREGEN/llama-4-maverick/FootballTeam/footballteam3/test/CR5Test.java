package edu.footballteam.footballteam3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.footballteam.Announcement;
import edu.footballteam.AnnouncementType;
import edu.footballteam.FootballTeam;
import edu.footballteam.FootballteamFactory;
import edu.footballteam.Player;
import edu.footballteam.Position;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR5Test {
    
    private FootballTeam team;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        team = FootballteamFactory.eINSTANCE.createFootballTeam();
        team.getPlayers().clear();
        team.getAnnouncements().clear();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TotalAnnouncementsForSingleForwardPlayerWithOneScore() throws ParseException {
        // Create a football team named "Turgutlu"
        // Note: The team name is not a property in the model, so we just work with the team object
        
        // Create a forward player named "John" with number 9
        Player john = FootballteamFactory.eINSTANCE.createPlayer();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        team.getPlayers().add(john);
        
        // Announce a score for John
        Announcement scoreAnnouncement = FootballteamFactory.eINSTANCE.createAnnouncement();
        Date time = dateFormat.parse("2023-01-01 10:00:00");
        scoreAnnouncement.setTime(time);
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(john);
        team.getAnnouncements().add(scoreAnnouncement);
        
        // Calculate total announcements for forward players
        int totalAnnouncements = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        assertEquals(1, totalAnnouncements);
    }
    
    @Test
    public void testCase2_TotalAnnouncementsForMultipleForwardPlayersWithScores() throws ParseException {
        // Create a football team named "Turgutlu"
        
        // Create forward players: "Alice" (number 10), "Bob" (number 11)
        Player alice = FootballteamFactory.eINSTANCE.createPlayer();
        alice.setName("Alice");
        alice.setNumber(10);
        alice.setPosition(Position.FORWARD);
        team.getPlayers().add(alice);
        
        Player bob = FootballteamFactory.eINSTANCE.createPlayer();
        bob.setName("Bob");
        bob.setNumber(11);
        bob.setPosition(Position.FORWARD);
        team.getPlayers().add(bob);
        
        // Announce a score for Alice and a score for Bob
        Announcement aliceScore = FootballteamFactory.eINSTANCE.createAnnouncement();
        Date time1 = dateFormat.parse("2023-01-01 10:00:00");
        aliceScore.setTime(time1);
        aliceScore.setType(AnnouncementType.SCORE);
        aliceScore.setPlayer(alice);
        team.getAnnouncements().add(aliceScore);
        
        Announcement bobScore = FootballteamFactory.eINSTANCE.createAnnouncement();
        Date time2 = dateFormat.parse("2023-01-01 10:05:00");
        bobScore.setTime(time2);
        bobScore.setType(AnnouncementType.SCORE);
        bobScore.setPlayer(bob);
        team.getAnnouncements().add(bobScore);
        
        // Calculate total announcements for forward players
        int totalAnnouncements = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        assertEquals(2, totalAnnouncements);
    }
    
    @Test
    public void testCase3_TotalAnnouncementsForForwardPlayersWithSaves() throws ParseException {
        // Create a football team named "Turgutlu"
        
        // Create a forward player named "Tom" with number 7
        Player tom = FootballteamFactory.eINSTANCE.createPlayer();
        tom.setName("Tom");
        tom.setNumber(7);
        tom.setPosition(Position.FORWARD);
        team.getPlayers().add(tom);
        
        // Create a goalkeeper named "Mike" with number 1
        Player mike = FootballteamFactory.eINSTANCE.createPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        team.getPlayers().add(mike);
        
        // Announce a save for Mike
        Announcement saveAnnouncement = FootballteamFactory.eINSTANCE.createAnnouncement();
        Date time = dateFormat.parse("2023-01-01 10:00:00");
        saveAnnouncement.setTime(time);
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(mike);
        team.getAnnouncements().add(saveAnnouncement);
        
        // Calculate total announcements for forward players
        int totalAnnouncements = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        assertEquals(0, totalAnnouncements);
    }
    
    @Test
    public void testCase4_TotalAnnouncementsForForwardPlayersWithScoresAndSaves() throws ParseException {
        // Create a football team named "Turgutlu"
        
        // Create forward players: "Emma" (number 8), "Lucas" (number 12)
        Player emma = FootballteamFactory.eINSTANCE.createPlayer();
        emma.setName("Emma");
        emma.setNumber(8);
        emma.setPosition(Position.FORWARD);
        team.getPlayers().add(emma);
        
        Player lucas = FootballteamFactory.eINSTANCE.createPlayer();
        lucas.setName("Lucas");
        lucas.setNumber(12);
        lucas.setPosition(Position.FORWARD);
        team.getPlayers().add(lucas);
        
        // Create a goalkeeper named "Mike" with number 1
        Player mike = FootballteamFactory.eINSTANCE.createPlayer();
        mike.setName("Mike");
        mike.setNumber(1);
        mike.setPosition(Position.GOALKEEPER);
        team.getPlayers().add(mike);
        
        // Announce a score for Emma
        Announcement emmaScore = FootballteamFactory.eINSTANCE.createAnnouncement();
        Date time1 = dateFormat.parse("2023-01-01 10:00:00");
        emmaScore.setTime(time1);
        emmaScore.setType(AnnouncementType.SCORE);
        emmaScore.setPlayer(emma);
        team.getAnnouncements().add(emmaScore);
        
        // Announce a save for Mike (goalkeeper)
        Announcement mikeSave = FootballteamFactory.eINSTANCE.createAnnouncement();
        Date time2 = dateFormat.parse("2023-01-01 10:05:00");
        mikeSave.setTime(time2);
        mikeSave.setType(AnnouncementType.SAVE);
        mikeSave.setPlayer(mike);
        team.getAnnouncements().add(mikeSave);
        
        // Calculate total announcements for forward players
        int totalAnnouncements = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        assertEquals(1, totalAnnouncements);
    }
    
    @Test
    public void testCase5_TotalAnnouncementsWithNoForwardPlayers() throws ParseException {
        // Create a football team named "Turgutlu"
        
        // Create midfield player "Noah" (number 5) and defender "Liam" (number 4)
        Player noah = FootballteamFactory.eINSTANCE.createPlayer();
        noah.setName("Noah");
        noah.setNumber(5);
        noah.setPosition(Position.MIDFIELD);
        team.getPlayers().add(noah);
        
        Player liam = FootballteamFactory.eINSTANCE.createPlayer();
        liam.setName("Liam");
        liam.setNumber(4);
        liam.setPosition(Position.DEFENSE);
        team.getPlayers().add(liam);
        
        // Announce some saves and scores for others, but none for forwards
        Announcement noahScore = FootballteamFactory.eINSTANCE.createAnnouncement();
        Date time1 = dateFormat.parse("2023-01-01 10:00:00");
        noahScore.setTime(time1);
        noahScore.setType(AnnouncementType.SCORE);
        noahScore.setPlayer(noah);
        team.getAnnouncements().add(noahScore);
        
        Announcement liamSave = FootballteamFactory.eINSTANCE.createAnnouncement();
        Date time2 = dateFormat.parse("2023-01-01 10:05:00");
        liamSave.setTime(time2);
        liamSave.setType(AnnouncementType.SAVE);
        liamSave.setPlayer(liam);
        team.getAnnouncements().add(liamSave);
        
        // Calculate total announcements for forward players
        int totalAnnouncements = team.calculateTotalAnnouncementsForForwardPlayers();
        
        // Expected Output: Total announcements = 0 (No forward player available)
        assertEquals(0, totalAnnouncements);
    }
}