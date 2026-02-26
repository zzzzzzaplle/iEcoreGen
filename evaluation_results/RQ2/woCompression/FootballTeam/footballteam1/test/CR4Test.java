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

public class CR4Test {
    
    private FootballteamFactory factory;
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory and create a new football team
        factory = FootballteamFactory.eINSTANCE;
        footballTeam = factory.createFootballTeam();
    }
    
    @Test
    public void testCase1_CalculateGoalSavingAnnouncementsForGoalkeeperWithNoSaves() {
        // SetUp: Create a football team with a goalkeeper who has no save announcements
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to the team's players list
        footballTeam.getPlayers().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 0
        assertEquals("Goalkeeper with no saves should return 0", 0, result);
    }
    
    @Test
    public void testCase2_CalculateGoalSavingAnnouncementsForGoalkeeperWithOneSave() {
        // SetUp: Create a football team with a goalkeeper who has one save announcement
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Create one save announcement for the goalkeeper
        Announcement saveAnnouncement = factory.createAnnouncement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        saveAnnouncement.setTime(new Date());
        
        // Add goalkeeper to the team's players list
        footballTeam.getPlayers().add(goalkeeper);
        // Add the save announcement to the team's announcements list
        footballTeam.getAnnouncements().add(saveAnnouncement);
        
        // Calculate goal-saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 1
        assertEquals("Goalkeeper with one save should return 1", 1, result);
    }
    
    @Test
    public void testCase3_CalculateGoalSavingAnnouncementsForGoalkeeperWithMultipleSaves() {
        // SetUp: Create a football team with a goalkeeper who has multiple save announcements
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Create three save announcements for the goalkeeper
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = factory.createAnnouncement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Add goalkeeper to the team's players list
        footballTeam.getPlayers().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 3
        assertEquals("Goalkeeper with three saves should return 3", 3, result);
    }
    
    @Test
    public void testCase4_CalculateGoalSavingAnnouncementsForGoalkeeperWithConsecutiveSaves() {
        // SetUp: Create a football team with a goalkeeper who has consecutive save announcements
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Create five consecutive save announcements for the goalkeeper
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = factory.createAnnouncement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Add goalkeeper to the team's players list
        footballTeam.getPlayers().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 5
        assertEquals("Goalkeeper with five consecutive saves should return 5", 5, result);
    }
    
    @Test
    public void testCase5_CalculateGoalSavingAnnouncementsForGoalkeeperWithInvalidEntries() {
        // SetUp: Create a football team with a goalkeeper who has mixed valid and invalid announcements
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Create two valid save announcements
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = factory.createAnnouncement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            saveAnnouncement.setTime(new Date());
            footballTeam.getAnnouncements().add(saveAnnouncement);
        }
        
        // Create one invalid goal announcement (should not be counted)
        Announcement goalAnnouncement = factory.createAnnouncement();
        goalAnnouncement.setType(AnnouncementType.SCORE); // This is a goal, not a save
        goalAnnouncement.setPlayer(goalkeeper);
        goalAnnouncement.setTime(new Date());
        footballTeam.getAnnouncements().add(goalAnnouncement);
        
        // Add goalkeeper to the team's players list
        footballTeam.getPlayers().add(goalkeeper);
        
        // Calculate goal-saving announcements
        int result = footballTeam.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Expected Output: Total goal-saving announcements = 2 (only saves count)
        assertEquals("Goalkeeper with 2 saves and 1 goal should return 2", 2, result);
    }
}