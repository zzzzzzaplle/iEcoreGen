package edu.footballteam.footballteam4.test;

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
        // Note: Team name is not a property in the model, so we just work with the team object
        
        // Create a forward player named "John" with number 9
        Player john = FootballteamFactory.eINSTANCE.createPlayer();
        john.setName("John");
        john.setNumber(9);
        john.setPosition(Position.FORWARD);
        team.getPlayers().add(john);
        
        // Announce a score for John
        Announcement announcement = FootballteamFactory.eINSTANCE.createAnnouncement();
        announcement.setType(AnnouncementType.SCORE);
        announcement.setPlayer(john);
        announcement.setTime(dateFormat.parse("2023-01-01 10:00:00"));
        team.getAnnouncements().add(announcement);
        
        // Expected Output: Total announcements = 1 (SCORE for John)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(1, result);
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
        Announcement announcement1 = FootballteamFactory.eINSTANCE.createAnnouncement();
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setPlayer(alice);
        announcement1.setTime(dateFormat.parse("2023-01-01 10:00:00"));
        team.getAnnouncements().add(announcement1);
        
        Announcement announcement2 = FootballteamFactory.eINSTANCE.createAnnouncement();
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setPlayer(bob);
        announcement2.setTime(dateFormat.parse("2023-01-01 10:05:00"));
        team.getAnnouncements().add(announcement2);
        
        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(2, result);
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
        Announcement announcement = FootballteamFactory.eINSTANCE.createAnnouncement();
        announcement.setType(AnnouncementType.SAVE);
        announcement.setPlayer(mike);
        announcement.setTime(dateFormat.parse("2023-01-01 10:00:00"));
        team.getAnnouncements().add(announcement);
        
        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
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
        Announcement announcement1 = FootballteamFactory.eINSTANCE.createAnnouncement();
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setPlayer(emma);
        announcement1.setTime(dateFormat.parse("2023-01-01 10:00:00"));
        team.getAnnouncements().add(announcement1);
        
        // Announce a save for Mike (goalkeeper)
        Announcement announcement2 = FootballteamFactory.eINSTANCE.createAnnouncement();
        announcement2.setType(AnnouncementType.SAVE);
        announcement2.setPlayer(mike);
        announcement2.setTime(dateFormat.parse("2023-01-01 10:05:00"));
        team.getAnnouncements().add(announcement2);
        
        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(1, result);
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
        Announcement announcement1 = FootballteamFactory.eINSTANCE.createAnnouncement();
        announcement1.setType(AnnouncementType.SAVE);
        announcement1.setPlayer(noah); // Midfielder
        announcement1.setTime(dateFormat.parse("2023-01-01 10:00:00"));
        team.getAnnouncements().add(announcement1);
        
        Announcement announcement2 = FootballteamFactory.eINSTANCE.createAnnouncement();
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setPlayer(liam); // Defender
        announcement2.setTime(dateFormat.parse("2023-01-01 10:05:00"));
        team.getAnnouncements().add(announcement2);
        
        // Expected Output: Total announcements = 0 (No forward player available)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
    }
}