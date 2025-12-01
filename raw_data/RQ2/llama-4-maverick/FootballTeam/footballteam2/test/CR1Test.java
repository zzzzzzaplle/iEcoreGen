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
    public void testCase1_AllStrikersWithGoals() throws ParseException {
        // Create a football team named "Turgutlu"
        FootballTeam team = factory.createFootballTeam();
        
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        Player[] strikers = new Player[4];
        for (int i = 0; i < 4; i++) {
            Player striker = factory.createPlayer();
            striker.setName("Striker" + (i + 1));
            striker.setPosition(Position.FORWARD);
            strikers[i] = striker;
            team.getPlayers().add(striker);
        }
        
        // Add 7 other players with no goals
        for (int i = 0; i < 7; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.MIDFIELD); // Not forwards
            team.getPlayers().add(player);
        }
        
        // Add all 11 players to first eleven
        for (int i = 0; i < 4; i++) {
            team.getFirstEleven().add(strikers[i]);
        }
        for (int i = 0; i < 7; i++) {
            team.getFirstEleven().add(team.getPlayers().get(i + 4));
        }
        
        // Announce scores for each of the 4 Strikers (2 goals each)
        Date time = dateFormat.parse("2023-01-01 15:30:00");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                Announcement announcement = factory.createAnnouncement();
                announcement.setTime(time);
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(strikers[i]);
                team.getAnnouncements().add(announcement);
            }
        }
        
        // Execute the method under test
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws ParseException {
        // Create a football team named "Turgutlu"
        FootballTeam team = factory.createFootballTeam();
        
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        Player[] strikers = new Player[2];
        for (int i = 0; i < 2; i++) {
            Player striker = factory.createPlayer();
            striker.setName("Striker" + (i + 1));
            striker.setPosition(Position.FORWARD);
            strikers[i] = striker;
            team.getPlayers().add(striker);
        }
        
        // Add 2 Midfielders
        Player[] midfielders = new Player[2];
        for (int i = 0; i < 2; i++) {
            Player midfielder = factory.createPlayer();
            midfielder.setName("Midfielder" + (i + 1));
            midfielder.setPosition(Position.MIDFIELD);
            midfielders[i] = midfielder;
            team.getPlayers().add(midfielder);
        }
        
        // Add 7 other players with no goals
        for (int i = 0; i < 7; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.DEFENSE); // Not forwards
            team.getPlayers().add(player);
        }
        
        // Add all 11 players to first eleven
        for (int i = 0; i < 2; i++) {
            team.getFirstEleven().add(strikers[i]);
        }
        for (int i = 0; i < 2; i++) {
            team.getFirstEleven().add(midfielders[i]);
        }
        for (int i = 0; i < 7; i++) {
            team.getFirstEleven().add(team.getPlayers().get(i + 4));
        }
        
        // Announce scores for Strikers (3 goals each)
        Date time = dateFormat.parse("2023-01-01 15:30:00");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                Announcement announcement = factory.createAnnouncement();
                announcement.setTime(time);
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(strikers[i]);
                team.getAnnouncements().add(announcement);
            }
        }
        
        // Execute the method under test
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // Create a football team named "Turgutlu"
        FootballTeam team = factory.createFootballTeam();
        
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        for (int i = 0; i < 11; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + (i + 1));
            if (i < 3) {
                player.setPosition(Position.FORWARD); // Make first 3 forwards
            } else {
                player.setPosition(Position.MIDFIELD);
            }
            team.getPlayers().add(player);
            team.getFirstEleven().add(player);
        }
        
        // No announcements added (0 goals)
        
        // Execute the method under test
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected Output: Total announcements = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws ParseException {
        // Create a football team named "Turgutlu"
        FootballTeam team = factory.createFootballTeam();
        
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        team.getPlayers().add(goalkeeper);
        team.getFirstEleven().add(goalkeeper);
        
        // Add 10 other players
        for (int i = 0; i < 10; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.DEFENSE);
            team.getPlayers().add(player);
            team.getFirstEleven().add(player);
        }
        
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        Date time = dateFormat.parse("2023-01-01 15:30:00");
        for (int i = 0; i < 2; i++) {
            Announcement announcement = factory.createAnnouncement();
            announcement.setTime(time);
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            team.getAnnouncements().add(announcement);
        }
        
        // Execute the method under test
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // Create a football team named "Turgutlu"
        FootballTeam team = factory.createFootballTeam();
        
        // Do not add any players to the first eleven
        
        // Execute the method under test
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        assertEquals(0, result);
    }
}