package edu.footballteam.footballteam5.test;

import edu.footballteam.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {

    private FootballteamFactory factory;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        factory = FootballteamFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_allStrikersWithGoals() throws ParseException {
        // Create a football team named "Turgutlu"
        FootballTeam team = factory.createFootballTeam();

        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        for (int i = 1; i <= 11; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + i);
            player.setAge(25);
            player.setNumber(i);
            
            if (i <= 4) {
                // First 4 are strikers
                player.setPosition(Position.FORWARD);
                
                // Add them to first eleven
                team.getFirstEleven().add(player);
                
                // Create 2 goal scoring announcements for each striker
                for (int j = 1; j <= 2; j++) {
                    Announcement announcement = factory.createAnnouncement();
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(player);
                    Date time = dateFormat.parse("2023-01-01 10:00:00");
                    announcement.setTime(time);
                    team.getAnnouncements().add(announcement);
                }
            } else {
                // Others are not strikers (set as midfield for example)
                player.setPosition(Position.MIDFIELD);
                team.getFirstEleven().add(player);
            }
            
            team.getPlayers().add(player);
        }

        // Calculate total goal scoring announcements for first eleven
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        assertEquals(8, result);
    }

    @Test
    public void testCase2_mixedPlayersScoring() throws ParseException {
        // Create a football team named "Turgutlu"
        FootballTeam team = factory.createFootballTeam();

        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        for (int i = 1; i <= 11; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + i);
            player.setAge(25);
            player.setNumber(i);
            
            if (i <= 2) {
                // First 2 are strikers
                player.setPosition(Position.FORWARD);
                
                // Add them to first eleven
                team.getFirstEleven().add(player);
                
                // Create 3 goal scoring announcements for each striker
                for (int j = 1; j <= 3; j++) {
                    Announcement announcement = factory.createAnnouncement();
                    announcement.setType(AnnouncementType.SCORE);
                    announcement.setPlayer(player);
                    Date time = dateFormat.parse("2023-01-01 10:00:00");
                    announcement.setTime(time);
                    team.getAnnouncements().add(announcement);
                }
            } else if (i <= 4) {
                // Next 2 are midfielders
                player.setPosition(Position.MIDFIELD);
                team.getFirstEleven().add(player);
            } else {
                // Others (set as defense for example)
                player.setPosition(Position.DEFENSE);
                team.getFirstEleven().add(player);
            }
            
            team.getPlayers().add(player);
        }

        // Calculate total goal scoring announcements for first eleven
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        assertEquals(6, result);
    }

    @Test
    public void testCase3_firstElevenWithNoGoals() {
        // Create a football team named "Turgutlu"
        FootballTeam team = factory.createFootballTeam();

        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        for (int i = 1; i <= 11; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + i);
            player.setAge(25);
            player.setNumber(i);
            
            if (i <= 3) {
                // First 3 are strikers
                player.setPosition(Position.FORWARD);
            } else {
                // Others are not strikers
                player.setPosition(Position.MIDFIELD);
            }
            
            // Add all to first eleven
            team.getFirstEleven().add(player);
            team.getPlayers().add(player);
            // No announcements added
        }

        // Calculate total goal scoring announcements for first eleven
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected Output: Total announcements = 0
        assertEquals(0, result);
    }

    @Test
    public void testCase4_onlyGoalkeeperWithSaveAnnouncements() throws ParseException {
        // Create a football team named "Turgutlu"
        FootballTeam team = factory.createFootballTeam();

        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        for (int i = 1; i <= 11; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + i);
            player.setAge(25);
            player.setNumber(i);
            
            if (i == 1) {
                // First player is goalkeeper
                player.setPosition(Position.GOALKEEPER);
                
                // Add to first eleven
                team.getFirstEleven().add(player);
                
                // Create 2 save announcements for goalkeeper
                for (int j = 1; j <= 2; j++) {
                    Announcement announcement = factory.createAnnouncement();
                    announcement.setType(AnnouncementType.SAVE);
                    announcement.setPlayer(player);
                    Date time = dateFormat.parse("2023-01-01 10:00:00");
                    announcement.setTime(time);
                    team.getAnnouncements().add(announcement);
                }
            } else {
                // Others are not strikers (set as midfield for example)
                player.setPosition(Position.MIDFIELD);
                team.getFirstEleven().add(player);
            }
            
            team.getPlayers().add(player);
        }

        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        // Calculate total goal scoring announcements for first eleven
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        assertEquals(0, result);
    }

    @Test
    public void testCase5_noPlayersInFirstEleven() {
        // Create a football team named "Turgutlu"
        FootballTeam team = factory.createFootballTeam();

        // Do not add any players to the first eleven
        // Just add some players to the team
        for (int i = 1; i <= 5; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + i);
            player.setAge(25);
            player.setNumber(i);
            player.setPosition(Position.FORWARD);
            team.getPlayers().add(player);
            // Not adding to first eleven
        }

        // Calculate total goal scoring announcements for first eleven
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        assertEquals(0, result);
    }
}