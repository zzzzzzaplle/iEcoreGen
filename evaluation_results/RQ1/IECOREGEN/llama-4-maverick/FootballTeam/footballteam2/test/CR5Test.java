package edu.footballteam.footballteam2.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.footballteam.Announcement;
import edu.footballteam.AnnouncementType;
import edu.footballteam.FootballTeam;
import edu.footballteam.FootballteamFactory;
import edu.footballteam.Player;
import edu.footballteam.Position;

public class CR5Test {

    private FootballTeam team;
    private FootballteamFactory factory;
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Before
    public void setUp() {
        factory = FootballteamFactory.eINSTANCE;
        team = factory.createFootballTeam();
        team.getPlayers().clear();
        team.getAnnouncements().clear();
    }

    @Test
    public void testCase1_totalAnnouncementsForSingleForwardPlayerWithOneScore() throws ParseException {
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
        Date time = dateFormat.parse("2023-10-10 15:30:00");
        announcement.setTime(time);
        team.getAnnouncements().add(announcement);

        // Expected Output: Total announcements = 1 (SCORE for John)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(1, result);
    }

    @Test
    public void testCase2_totalAnnouncementsForMultipleForwardPlayersWithScores() throws ParseException {
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
        Date time1 = dateFormat.parse("2023-10-10 15:31:00");
        announcement1.setTime(time1);
        team.getAnnouncements().add(announcement1);

        Announcement announcement2 = factory.createAnnouncement();
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setPlayer(bob);
        Date time2 = dateFormat.parse("2023-10-10 15:32:00");
        announcement2.setTime(time2);
        team.getAnnouncements().add(announcement2);

        // Expected Output: Total announcements = 2 (SCORES for Alice and Bob)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(2, result);
    }

    @Test
    public void testCase3_totalAnnouncementsForForwardPlayersWithSaves() throws ParseException {
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
        Date time = dateFormat.parse("2023-10-10 15:33:00");
        announcement.setTime(time);
        team.getAnnouncements().add(announcement);

        // Expected Output: Total announcements = 0 (No SCORE announcements for forward players)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
    }

    @Test
    public void testCase4_totalAnnouncementsForForwardPlayersWithScoresAndSaves() throws ParseException {
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
        Announcement announcement1 = factory.createAnnouncement();
        announcement1.setType(AnnouncementType.SCORE);
        announcement1.setPlayer(emma);
        Date time1 = dateFormat.parse("2023-10-10 15:34:00");
        announcement1.setTime(time1);
        team.getAnnouncements().add(announcement1);

        // Announce a save for Mike (goalkeeper)
        Announcement announcement2 = factory.createAnnouncement();
        announcement2.setType(AnnouncementType.SAVE);
        announcement2.setPlayer(mike);
        Date time2 = dateFormat.parse("2023-10-10 15:35:00");
        announcement2.setTime(time2);
        team.getAnnouncements().add(announcement2);

        // Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(1, result);
    }

    @Test
    public void testCase5_totalAnnouncementsWithNoForwardPlayers() throws ParseException {
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

        // Announce some saves and scores for others, but none for forwards
        Announcement announcement1 = factory.createAnnouncement();
        announcement1.setType(AnnouncementType.SAVE);
        announcement1.setPlayer(noah); // Not a goalkeeper, but still not a forward
        Date time1 = dateFormat.parse("2023-10-10 15:36:00");
        announcement1.setTime(time1);
        team.getAnnouncements().add(announcement1);

        Announcement announcement2 = factory.createAnnouncement();
        announcement2.setType(AnnouncementType.SCORE);
        announcement2.setPlayer(liam); // Not a forward
        Date time2 = dateFormat.parse("2023-10-10 15:37:00");
        announcement2.setTime(time2);
        team.getAnnouncements().add(announcement2);

        // Expected Output: Total announcements = 0 (No forward player available)
        int result = team.calculateTotalAnnouncementsForForwardPlayers();
        assertEquals(0, result);
    }
}