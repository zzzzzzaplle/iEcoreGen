package edu.footballteam.footballteam3.test;

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
        
        // Create 4 strikers with 2 goals each
        for (int i = 0; i < 4; i++) {
            Player striker = factory.createPlayer();
            striker.setName("Striker" + (i + 1));
            striker.setPosition(Position.FORWARD);
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
        for (int i = 0; i < 7; i++) {
            Player otherPlayer = factory.createPlayer();
            otherPlayer.setName("Player" + (i + 1));
            otherPlayer.setPosition(Position.MIDFIELD);
            team.getPlayers().add(otherPlayer);
            team.getFirstEleven().add(otherPlayer);
        }
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should count 8 goal-scoring announcements from 4 strikers with 2 goals each", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        // Announce scores for Strikers
        
        // Create 2 strikers with 3 goals each
        for (int i = 0; i < 2; i++) {
            Player striker = factory.createPlayer();
            striker.setName("Striker" + (i + 1));
            striker.setPosition(Position.FORWARD);
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
        for (int i = 0; i < 2; i++) {
            Player midfielder = factory.createPlayer();
            midfielder.setName("Midfielder" + (i + 1));
            midfielder.setPosition(Position.MIDFIELD);
            team.getPlayers().add(midfielder);
            team.getFirstEleven().add(midfielder);
        }
        
        // Create 7 other players with no goals
        for (int i = 0; i < 7; i++) {
            Player otherPlayer = factory.createPlayer();
            otherPlayer.setName("Player" + (i + 1));
            otherPlayer.setPosition(Position.DEFENSE);
            team.getPlayers().add(otherPlayer);
            team.getFirstEleven().add(otherPlayer);
        }
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should count 6 goal-scoring announcements from 2 strikers with 3 goals each", 6, result);
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
            team.getPlayers().add(striker);
            team.getFirstEleven().add(striker);
        }
        
        // Create 8 other players with no goals
        for (int i = 0; i < 8; i++) {
            Player otherPlayer = factory.createPlayer();
            otherPlayer.setName("Player" + (i + 1));
            otherPlayer.setPosition(Position.MIDFIELD);
            team.getPlayers().add(otherPlayer);
            team.getFirstEleven().add(otherPlayer);
        }
        
        // No announcements created, so no goals
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should return 0 when no goal-scoring announcements exist", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        
        // Create 1 goalkeeper with 2 save announcements
        Player goalkeeper = factory.createPlayer();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
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
        
        // Create 10 other players with no goals
        for (int i = 0; i < 10; i++) {
            Player otherPlayer = factory.createPlayer();
            otherPlayer.setName("Player" + (i + 1));
            otherPlayer.setPosition(Position.DEFENSE);
            team.getPlayers().add(otherPlayer);
            team.getFirstEleven().add(otherPlayer);
        }
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should return 0 when only save announcements exist, no score announcements", 0, result);
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
            team.getPlayers().add(player);
            // Note: NOT added to firstEleven list
        }
        
        // Create some announcements for players not in first eleven
        for (Player player : team.getPlayers()) {
            Announcement announcement = factory.createAnnouncement();
            announcement.setType(AnnouncementType.SCORE);
            announcement.setPlayer(player);
            announcement.setTime(new Date());
            team.getAnnouncements().add(announcement);
        }
        
        // Expected Output: Total announcements = 0 (no players in first eleven, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should return 0 when no players are in the first eleven", 0, result);
    }
}