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
        // No announcements added to the team
        
        // Calculate goal saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_goalkeeperWithOneSave() {
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
        
        // Add announcement to the team
        footballTeam.getAnnouncements().add(saveAnnouncement);
        
        // Calculate goal saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_goalkeeperWithMultipleSaves() {
        // Create a goalkeeper with number: 5, name: "Mark Johnson", age: 32
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        footballTeam.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 3 announcements of type SAVE during the match
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = factory.createAnnouncement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Calculate goal saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_goalkeeperWithConsecutiveSaves() {
        // Create a goalkeeper with number: 22, name: "Emily Davis", age: 26
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        footballTeam.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 5 announcements of type SAVE during the match, all in the second half
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = factory.createAnnouncement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Calculate goal saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_goalkeeperWithInvalidEntries() {
        // Create a goalkeeper with number: 10, name: "Chris Brown", age: 31
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team
        footballTeam.getPlayers().add(goalkeeper);
        
        // Goalkeeper made 2 announcements of type SAVE
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = factory.createAnnouncement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // ... and 1 announcement of type SCORE (which should not be counted)
        Announcement scoreAnnouncement = factory.createAnnouncement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(goalkeeper);
        footballTeam.getAnnouncements().add(scoreAnnouncement);
        
        // Calculate goal saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 2
        assertEquals(2, result);
    }
}