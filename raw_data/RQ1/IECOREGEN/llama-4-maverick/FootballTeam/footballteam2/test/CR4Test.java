package edu.footballteam.footballteam2.test;

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
    
    private FootballTeam team;
    private FootballteamFactory factory;
    
    @Before
    public void setUp() {
        factory = FootballteamFactory.eINSTANCE;
        team = factory.createFootballTeam();
    }
    
    /**
     * Test Case 1: Calculate Goal Saving Announcements for a Goalkeeper with No Saves
     * Input: Calculate goal-saving announcements for goalkeeper with no save recorded.
     * SetUp:
     * 1. Create a football team.
     * 2. Add a goalkeeper with number: 1, name: "John Doe", age: 30.
     * 3. The goalkeeper has not made any saves in the match.
     * Expected Output: Total goal-saving announcements = 0.
     */
    @Test
    public void testCase1_goalkeeperWithNoSaves() {
        // Create a goalkeeper
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(1);
        goalkeeper.setName("John Doe");
        goalkeeper.setAge(30);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to team players
        team.getPlayers().add(goalkeeper);
        
        // No announcements added
        
        // Execute the operation
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the result
        assertEquals(0, result);
    }
    
    /**
     * Test Case 2: Calculate Goal Saving Announcements for a Goalkeeper with One Save
     * Input: Calculate goal-saving announcements for goalkeeper who made one save.
     * SetUp:
     * 1. Create a football team.
     * 2. Add a goalkeeper with number: 12, name: "Jane Smith", age: 28.
     * 3. Goalkeeper made 1 announcement of type SAVE.
     * Expected Output: Total goal-saving announcements = 1.
     */
    @Test
    public void testCase2_goalkeeperWithOneSave() {
        // Create a goalkeeper
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(12);
        goalkeeper.setName("Jane Smith");
        goalkeeper.setAge(28);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to team players
        team.getPlayers().add(goalkeeper);
        
        // Create a save announcement
        Announcement saveAnnouncement = factory.createAnnouncement();
        saveAnnouncement.setType(AnnouncementType.SAVE);
        saveAnnouncement.setPlayer(goalkeeper);
        
        // Add announcement to team
        team.getAnnouncements().add(saveAnnouncement);
        
        // Execute the operation
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the result
        assertEquals(1, result);
    }
    
    /**
     * Test Case 3: Calculate Goal Saving Announcements for a Goalkeeper with Multiple Saves
     * Input: Calculate goal-saving announcements for goalkeeper who made multiple saves.
     * SetUp:
     * 1. Create a football team.
     * 2. Add a goalkeeper with number: 5, name: "Mark Johnson", age: 32.
     * 3. Goalkeeper made 3 announcements of type SAVE during the match.
     * Expected Output: Total goal-saving announcements = 3.
     */
    @Test
    public void testCase3_goalkeeperWithMultipleSaves() {
        // Create a goalkeeper
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(5);
        goalkeeper.setName("Mark Johnson");
        goalkeeper.setAge(32);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to team players
        team.getPlayers().add(goalkeeper);
        
        // Create 3 save announcements
        for (int i = 0; i < 3; i++) {
            Announcement saveAnnouncement = factory.createAnnouncement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            team.getAnnouncements().add(saveAnnouncement);
        }
        
        // Execute the operation
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the result
        assertEquals(3, result);
    }
    
    /**
     * Test Case 4: Calculate Goal Saving Announcements for a Goalkeeper with Consecutive Saves
     * Input: Calculate goal-saving announcements for goalkeeper who made consecutive saves.
     * SetUp:
     * 1. Create a football team.
     * 2. Add a goalkeeper with number: 22, name: "Emily Davis", age: 26.
     * 3. Goalkeeper made 5 announcements of type SAVE during the match, all in the second half.
     * Expected Output: Total goal-saving announcements = 5.
     */
    @Test
    public void testCase4_goalkeeperWithConsecutiveSaves() {
        // Create a goalkeeper
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(22);
        goalkeeper.setName("Emily Davis");
        goalkeeper.setAge(26);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to team players
        team.getPlayers().add(goalkeeper);
        
        // Create 5 save announcements
        for (int i = 0; i < 5; i++) {
            Announcement saveAnnouncement = factory.createAnnouncement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            team.getAnnouncements().add(saveAnnouncement);
        }
        
        // Execute the operation
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the result
        assertEquals(5, result);
    }
    
    /**
     * Test Case 5: Calculate Goal Saving Announcements for a Goalkeeper with Invalid Entries
     * Input: Calculate goal-saving announcements for goalkeeper with some invalid entries.
     * SetUp:
     * 1. Create a football team.
     * 2. Add a goalkeeper with number: 10, name: "Chris Brown", age: 31.
     * 3. Goalkeeper made 2 announcements of type SAVE and 1 announcement of type GOAL (which should not be counted).
     * Expected Output: Total goal-saving announcements = 2.
     */
    @Test
    public void testCase5_goalkeeperWithInvalidEntries() {
        // Create a goalkeeper
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setNumber(10);
        goalkeeper.setName("Chris Brown");
        goalkeeper.setAge(31);
        goalkeeper.setPosition(Position.GOALKEEPER);
        
        // Add goalkeeper to team players
        team.getPlayers().add(goalkeeper);
        
        // Create 2 save announcements
        for (int i = 0; i < 2; i++) {
            Announcement saveAnnouncement = factory.createAnnouncement();
            saveAnnouncement.setType(AnnouncementType.SAVE);
            saveAnnouncement.setPlayer(goalkeeper);
            team.getAnnouncements().add(saveAnnouncement);
        }
        
        // Create 1 score announcement (should not be counted)
        Announcement scoreAnnouncement = factory.createAnnouncement();
        scoreAnnouncement.setType(AnnouncementType.SCORE);
        scoreAnnouncement.setPlayer(goalkeeper);
        team.getAnnouncements().add(scoreAnnouncement);
        
        // Execute the operation
        int result = team.calculateGoalSavingAnnouncementsByGoalkeeper();
        
        // Verify the result
        assertEquals(2, result);
    }
}