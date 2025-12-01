package edu.footballteam.footballteam4.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

public class CR1Test {
    
    private FootballTeam team;
    private FootballteamFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = FootballteamFactory.eINSTANCE;
        team = factory.createFootballTeam();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_allStrikersWithGoals() throws ParseException {
        // Create 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        for (int i = 1; i <= 4; i++) {
            Player striker = factory.createPlayer();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            team.getPlayers().add(striker);
            team.getFirstEleven().add(striker);
        }
        
        for (int i = 5; i <= 11; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + i);
            player.setAge(25);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD);
            team.getPlayers().add(player);
            team.getFirstEleven().add(player);
        }
        
        // Announce scores for each of the 4 Strikers (2 goals each)
        for (Player striker : team.getFirstEleven()) {
            if (striker.getPosition() == Position.FORWARD) {
                for (int j = 0; j < 2; j++) {
                    Announcement announcement = factory.createAnnouncement();
                    Date time = dateFormat.parse("2023-01-01 10:00:00");
                    announcement.setTime(time);
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(striker);
                    team.getAnnouncements().add(announcement);
                }
            }
        }
        
        // Execute the operation
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Verify the result: 4 players × 2 goals = 8
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_mixedPlayersScoring() throws ParseException {
        // Create 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        for (int i = 1; i <= 2; i++) {
            Player striker = factory.createPlayer();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            team.getPlayers().add(striker);
            team.getFirstEleven().add(striker);
        }
        
        for (int i = 3; i <= 4; i++) {
            Player midfielder = factory.createPlayer();
            midfielder.setName("Midfielder" + i);
            midfielder.setAge(25);
            midfielder.setNumber(i);
            midfielder.setPosition(Position.MIDFIELD);
            team.getPlayers().add(midfielder);
            team.getFirstEleven().add(midfielder);
        }
        
        for (int i = 5; i <= 11; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + i);
            player.setAge(25);
            player.setNumber(i);
            player.setPosition(Position.DEFENSE);
            team.getPlayers().add(player);
            team.getFirstEleven().add(player);
        }
        
        // Announce scores for Strikers (3 goals each)
        for (Player striker : team.getFirstEleven()) {
            if (striker.getPosition() == Position.FORWARD) {
                for (int j = 0; j < 3; j++) {
                    Announcement announcement = factory.createAnnouncement();
                    Date time = dateFormat.parse("2023-01-01 10:00:00");
                    announcement.setTime(time);
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(striker);
                    team.getAnnouncements().add(announcement);
                }
            }
        }
        
        // Execute the operation
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Verify the result: (2 players × 3 goals) = 6
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_firstElevenWithNoGoals() {
        // Create 11 players: All players (including 3 Strikers) scored 0 goals
        for (int i = 1; i <= 3; i++) {
            Player striker = factory.createPlayer();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            team.getPlayers().add(striker);
            team.getFirstEleven().add(striker);
        }
        
        for (int i = 4; i <= 11; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + i);
            player.setAge(25);
            player.setNumber(i);
            player.setPosition(Position.MIDFIELD);
            team.getPlayers().add(player);
            team.getFirstEleven().add(player);
        }
        
        // No announcements added
        
        // Execute the operation
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Verify the result: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_onlyGoalkeeperWithSaveAnnouncements() throws ParseException {
        // Create 11 players, including 1 Goalkeeper who saved 2 goals
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setAge(30);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        team.getPlayers().add(goalkeeper);
        team.getFirstEleven().add(goalkeeper);
        
        for (int i = 2; i <= 11; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + i);
            player.setAge(25);
            player.setNumber(i);
            player.setPosition(Position.DEFENSE);
            team.getPlayers().add(player);
            team.getFirstEleven().add(player);
        }
        
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        for (int j = 0; j < 2; j++) {
            Announcement announcement = factory.createAnnouncement();
            Date time = dateFormat.parse("2023-01-01 10:00:00");
            announcement.setTime(time);
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            team.getAnnouncements().add(announcement);
        }
        
        // Execute the operation
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Verify the result: 0 (only saves recorded, no score announcements)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_noPlayersInFirstEleven() {
        // Do not add any players to the first eleven
        
        // Execute the operation
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Verify the result: 0 (no players, no announcements)
        assertEquals(0, result);
    }
}