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
import java.util.Date;

public class CR1Test {
    
    private FootballteamFactory factory;
    private FootballTeam team;
    
    @Before
    public void setUp() {
        factory = FootballteamFactory.eINSTANCE;
        team = factory.createFootballTeam();
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        // Announce scores for each of the 4 Strikers
        
        // Create 4 strikers in first eleven
        for (int i = 0; i < 4; i++) {
            Player striker = factory.createPlayer();
            striker.setName("Striker" + (i + 1));
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i + 1);
            team.getPlayers().add(striker);
            team.getFirstEleven().add(striker);
            
            // Create 2 score announcements for each striker
            for (int j = 0; j < 2; j++) {
                Announcement announcement = factory.createAnnouncement();
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                announcement.setTime(new Date());
                team.getAnnouncements().add(announcement);
            }
        }
        
        // Create 7 other players with no goals
        for (int i = 4; i < 11; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i + 1);
            team.getPlayers().add(player);
            team.getFirstEleven().add(player);
        }
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        // Announce scores for Strikers
        
        // Create 2 strikers in first eleven
        for (int i = 0; i < 2; i++) {
            Player striker = factory.createPlayer();
            striker.setName("Striker" + (i + 1));
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i + 1);
            team.getPlayers().add(striker);
            team.getFirstEleven().add(striker);
            
            // Create 3 score announcements for each striker
            for (int j = 0; j < 3; j++) {
                Announcement announcement = factory.createAnnouncement();
                announcement.setType(AnnouncementType.SCORE);
                announcement.setPlayer(striker);
                announcement.setTime(new Date());
                team.getAnnouncements().add(announcement);
            }
        }
        
        // Create 2 midfielders
        for (int i = 2; i < 4; i++) {
            Player midfielder = factory.createPlayer();
            midfielder.setName("Midfielder" + (i + 1));
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setNumber(i + 1);
            team.getPlayers().add(midfielder);
            team.getFirstEleven().add(midfielder);
        }
        
        // Create 7 other players with no goals
        for (int i = 4; i < 11; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.DEFENSE);
            player.setNumber(i + 1);
            team.getPlayers().add(player);
            team.getFirstEleven().add(player);
        }
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        
        // Create 3 strikers with no goals
        for (int i = 0; i < 3; i++) {
            Player striker = factory.createPlayer();
            striker.setName("Striker" + (i + 1));
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i + 1);
            team.getPlayers().add(striker);
            team.getFirstEleven().add(striker);
        }
        
        // Create 8 other players
        for (int i = 3; i < 11; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i + 1);
            team.getPlayers().add(player);
            team.getFirstEleven().add(player);
        }
        
        // No announcements created for any player
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        
        // Create 1 goalkeeper
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        team.getPlayers().add(goalkeeper);
        team.getFirstEleven().add(goalkeeper);
        
        // Create 2 save announcements for goalkeeper
        for (int i = 0; i < 2; i++) {
            Announcement announcement = factory.createAnnouncement();
            announcement.setType(AnnouncementType.SAVE);
            announcement.setPlayer(goalkeeper);
            announcement.setTime(new Date());
            team.getAnnouncements().add(announcement);
        }
        
        // Create 10 other players
        for (int i = 1; i < 11; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.DEFENSE);
            player.setNumber(i + 1);
            team.getPlayers().add(player);
            team.getFirstEleven().add(player);
        }
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() {
        // SetUp: Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        
        // Create some players but don't add them to first eleven
        for (int i = 0; i < 5; i++) {
            Player player = factory.createPlayer();
            player.setName("Player" + (i + 1));
            player.setPosition(Position.FORWARD);
            player.setNumber(i + 1);
            team.getPlayers().add(player);
            
            // Create score announcements but players are not in first eleven
            Announcement announcement = factory.createAnnouncement();
            announcement.setType(AnnouncementType.SCORE);
            announcement.setPlayer(player);
            announcement.setTime(new Date());
            team.getAnnouncements().add(announcement);
        }
        
        // Expected Output: Total announcements = 0 (no players in first eleven, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}