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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR4Test {
    
    private FootballTeam footballTeam;
    private FootballteamFactory factory;
    
    @Before
    public void setUp() {
        factory = FootballteamFactory.eINSTANCE;
        footballTeam = factory.createFootballTeam();
    }
    
    @Test
    public void testCase1_goalkeeperWithNoSaves() {
        // Create a goalkeeper with number: 1, name: "John Doe", age: 30
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        footballTeam.getPlayers().add(goalkeeper);
        
        // The goalkeeper has not made any saves in the match
        // No announcements added
        
        // Expected Output: Total goal-saving announcements = 0
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_goalkeeperWithOneSave() throws ParseException {
        // Create a goalkeeper with number: 12, name: "Jane Smith", age: 28
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        footballTeam.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 1 announcement of type SAVE
        Announcement saveAnnouncement = factory.createAnnouncement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = sdf.parse("2023-10-15 15:30:00");
        saveAnnouncement.setTime(time);
        
        footballTeam.getAnnouncements().add(saveAnnouncement);
        
        // Expected Output: Total goal-saving announcements = 1
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_goalkeeperWithMultipleSaves() throws ParseException {
        // Create a goalkeeper with number: 5, name: "Mark Johnson", age: 32
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        footballTeam.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 3 announcements of type SAVE during the match
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = factory.createAnnouncement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            Date time = sdf.parse("2023-10-15 16:0" + i + ":00");
            saveAnnouncement.setTime(time);
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Expected Output: Total goal-saving announcements = 3
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_goalkeeperWithConsecutiveSaves() throws ParseException {
        // Create a goalkeeper with number: 22, name: "Emily Davis", age: 26
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        footballTeam.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 5 announcements of type SAVE during the match, all in the second half
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = factory.createAnnouncement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            Date time = sdf.parse("2023-10-15 19:1" + i + ":00");
            saveAnnouncement.setTime(time);
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Expected Output: Total goal-saving announcements = 5
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_goalkeeperWithInvalidEntries() throws ParseException {
        // Create a goalkeeper with number: 10, name: "Chris Brown", age: 31
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        footballTeam.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 2 announcements of type SAVE and 1 announcement of type SCORE (which should not be counted)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Add 2 SAVE announcements
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = factory.createAnnouncement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            Date time = sdf.parse("2023-10-15 17:0" + i + ":00");
            saveAnnouncement.setTime(time);
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Add 1 SCORE announcement (should not be counted)
        Announcement scoreAnnouncement = factory.createAnnouncement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(goalkeeper);
        Date time = sdf.parse("2023-10-15 17:30:00");
        scoreAnnouncement.setTime(time);
        footballTeam.getAnnouncements().add(scoreAnnouncement);
        
        // Expected Output: Total goal-saving announcements = 2
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        assertEquals(2, result);
    }
}