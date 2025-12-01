import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR1Test {
    
    private FootballTeam team;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        // Announce scores for each of the 4 Strikers
        
        // Create 4 forwards (strikers)
        Player striker1 = new Player();
        striker1.setName("Striker1");
        striker1.setPosition(Position.FORWARD);
        striker1.setNumber(9);
        
        Player striker2 = new Player();
        striker2.setName("Striker2");
        striker2.setPosition(Position.FORWARD);
        striker2.setNumber(10);
        
        Player striker3 = new Player();
        striker3.setName("Striker3");
        striker3.setPosition(Position.FORWARD);
        striker3.setNumber(11);
        
        Player striker4 = new Player();
        striker4.setName("Striker4");
        striker4.setPosition(Position.FORWARD);
        striker4.setNumber(18);
        
        // Create 7 other players (non-forwards)
        Player midfielder1 = new Player();
        midfielder1.setName("Midfielder1");
        midfielder1.setPosition(Position.MIDFIELD);
        midfielder1.setNumber(8);
        
        Player midfielder2 = new Player();
        midfielder2.setName("Midfielder2");
        midfielder2.setPosition(Position.MIDFIELD);
        midfielder2.setNumber(6);
        
        Player defender1 = new Player();
        defender1.setName("Defender1");
        defender1.setPosition(Position.DEFENSE);
        defender1.setNumber(4);
        
        Player defender2 = new Player();
        defender2.setName("Defender2");
        defender2.setPosition(Position.DEFENSE);
        defender2.setNumber(5);
        
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        
        Player defender3 = new Player();
        defender3.setName("Defender3");
        defender3.setPosition(Position.DEFENSE);
        defender3.setNumber(2);
        
        Player defender4 = new Player();
        defender4.setName("Defender4");
        defender4.setPosition(Position.DEFENSE);
        defender4.setNumber(3);
        
        Player midfielder3 = new Player();
        midfielder3.setName("Midfielder3");
        midfielder3.setPosition(Position.MIDFIELD);
        midfielder3.setNumber(7);
        
        // Add all players to first eleven
        team.addFirstEleven(striker1);
        team.addFirstEleven(striker2);
        team.addFirstEleven(striker3);
        team.addFirstEleven(striker4);
        team.addFirstEleven(midfielder1);
        team.addFirstEleven(midfielder2);
        team.addFirstEleven(defender1);
        team.addFirstEleven(defender2);
        team.addFirstEleven(goalkeeper);
        team.addFirstEleven(defender3);
        team.addFirstEleven(midfielder3);
        
        // Create announcements: 2 goals for each striker (total 8 goal announcements)
        Date time = dateFormat.parse("2024-01-01 15:00:00");
        
        // Striker1 - 2 goals
        Announcement goal1 = new Announcement();
        goal1.setType(AnnouncementType.SCORE);
        goal1.setPlayer(striker1);
        goal1.setTime(time);
        team.addAnnouncement(goal1);
        
        Announcement goal2 = new Announcement();
        goal2.setType(AnnouncementType.SCORE);
        goal2.setPlayer(striker1);
        goal2.setTime(new Date(time.getTime() + 60000)); // 1 minute later
        team.addAnnouncement(goal2);
        
        // Striker2 - 2 goals
        Announcement goal3 = new Announcement();
        goal3.setType(AnnouncementType.SCORE);
        goal3.setPlayer(striker2);
        goal3.setTime(new Date(time.getTime() + 120000));
        team.addAnnouncement(goal3);
        
        Announcement goal4 = new Announcement();
        goal4.setType(AnnouncementType.SCORE);
        goal4.setPlayer(striker2);
        goal4.setTime(new Date(time.getTime() + 180000));
        team.addAnnouncement(goal4);
        
        // Striker3 - 2 goals
        Announcement goal5 = new Announcement();
        goal5.setType(AnnouncementType.SCORE);
        goal5.setPlayer(striker3);
        goal5.setTime(new Date(time.getTime() + 240000));
        team.addAnnouncement(goal5);
        
        Announcement goal6 = new Announcement();
        goal6.setType(AnnouncementType.SCORE);
        goal6.setPlayer(striker3);
        goal6.setTime(new Date(time.getTime() + 300000));
        team.addAnnouncement(goal6);
        
        // Striker4 - 2 goals
        Announcement goal7 = new Announcement();
        goal7.setType(AnnouncementType.SCORE);
        goal7.setPlayer(striker4);
        goal7.setTime(new Date(time.getTime() + 360000));
        team.addAnnouncement(goal7);
        
        Announcement goal8 = new Announcement();
        goal8.setType(AnnouncementType.SCORE);
        goal8.setPlayer(striker4);
        goal8.setTime(new Date(time.getTime() + 420000));
        team.addAnnouncement(goal8);
        
        // Calculate and verify
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 4 players × 2 goals = 8", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        // Announce scores for Strikers
        
        // Create 2 forwards (strikers)
        Player striker1 = new Player();
        striker1.setName("Striker1");
        striker1.setPosition(Position.FORWARD);
        striker1.setNumber(9);
        
        Player striker2 = new Player();
        striker2.setName("Striker2");
        striker2.setPosition(Position.FORWARD);
        striker2.setNumber(10);
        
        // Create 2 midfielders
        Player midfielder1 = new Player();
        midfielder1.setName("Midfielder1");
        midfielder1.setPosition(Position.MIDFIELD);
        midfielder1.setNumber(8);
        
        Player midfielder2 = new Player();
        midfielder2.setName("Midfielder2");
        midfielder2.setPosition(Position.MIDFIELD);
        midfielder2.setNumber(6);
        
        // Create 7 other players (non-scoring positions)
        Player defender1 = new Player();
        defender1.setName("Defender1");
        defender1.setPosition(Position.DEFENSE);
        defender1.setNumber(4);
        
        Player defender2 = new Player();
        defender2.setName("Defender2");
        defender2.setPosition(Position.DEFENSE);
        defender2.setNumber(5);
        
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        
        Player defender3 = new Player();
        defender3.setName("Defender3");
        defender3.setPosition(Position.DEFENSE);
        defender3.setNumber(2);
        
        Player defender4 = new Player();
        defender4.setName("Defender4");
        defender4.setPosition(Position.DEFENSE);
        defender4.setNumber(3);
        
        Player midfielder3 = new Player();
        midfielder3.setName("Midfielder3");
        midfielder3.setPosition(Position.MIDFIELD);
        midfielder3.setNumber(7);
        
        Player defender5 = new Player();
        defender5.setName("Defender5");
        defender5.setPosition(Position.DEFENSE);
        defender5.setNumber(12);
        
        Player defender6 = new Player();
        defender6.setName("Defender6");
        defender6.setPosition(Position.DEFENSE);
        defender6.setNumber(13);
        
        // Add all players to first eleven
        team.addFirstEleven(striker1);
        team.addFirstEleven(striker2);
        team.addFirstEleven(midfielder1);
        team.addFirstEleven(midfielder2);
        team.addFirstEleven(defender1);
        team.addFirstEleven(defender2);
        team.addFirstEleven(goalkeeper);
        team.addFirstEleven(defender3);
        team.addFirstEleven(midfielder3);
        team.addFirstEleven(defender5);
        team.addFirstEleven(defender6);
        
        // Create announcements: 3 goals for each striker (total 6 goal announcements)
        Date time = dateFormat.parse("2024-01-01 15:00:00");
        
        // Striker1 - 3 goals
        for (int i = 0; i < 3; i++) {
            Announcement goal = new Announcement();
            goal.setType(AnnouncementType.SCORE);
            goal.setPlayer(striker1);
            goal.setTime(new Date(time.getTime() + i * 60000));
            team.addAnnouncement(goal);
        }
        
        // Striker2 - 3 goals
        for (int i = 0; i < 3; i++) {
            Announcement goal = new Announcement();
            goal.setType(AnnouncementType.SCORE);
            goal.setPlayer(striker2);
            goal.setTime(new Date(time.getTime() + (i + 3) * 60000));
            team.addAnnouncement(goal);
        }
        
        // Calculate and verify
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 2 players × 3 goals = 6", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        
        // Create 3 forwards (strikers) with no goals
        Player striker1 = new Player();
        striker1.setName("Striker1");
        striker1.setPosition(Position.FORWARD);
        striker1.setNumber(9);
        
        Player striker2 = new Player();
        striker2.setName("Striker2");
        striker2.setPosition(Position.FORWARD);
        striker2.setNumber(10);
        
        Player striker3 = new Player();
        striker3.setName("Striker3");
        striker3.setPosition(Position.FORWARD);
        striker3.setNumber(11);
        
        // Create 8 other players
        Player midfielder1 = new Player();
        midfielder1.setName("Midfielder1");
        midfielder1.setPosition(Position.MIDFIELD);
        midfielder1.setNumber(8);
        
        Player midfielder2 = new Player();
        midfielder2.setName("Midfielder2");
        midfielder2.setPosition(Position.MIDFIELD);
        midfielder2.setNumber(6);
        
        Player defender1 = new Player();
        defender1.setName("Defender1");
        defender1.setPosition(Position.DEFENSE);
        defender1.setNumber(4);
        
        Player defender2 = new Player();
        defender2.setName("Defender2");
        defender2.setPosition(Position.DEFENSE);
        defender2.setNumber(5);
        
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        
        Player defender3 = new Player();
        defender3.setName("Defender3");
        defender3.setPosition(Position.DEFENSE);
        defender3.setNumber(2);
        
        Player defender4 = new Player();
        defender4.setName("Defender4");
        defender4.setPosition(Position.DEFENSE);
        defender4.setNumber(3);
        
        Player midfielder3 = new Player();
        midfielder3.setName("Midfielder3");
        midfielder3.setPosition(Position.MIDFIELD);
        midfielder3.setNumber(7);
        
        Player defender5 = new Player();
        defender5.setName("Defender5");
        defender5.setPosition(Position.DEFENSE);
        defender5.setNumber(12);
        
        // Add all players to first eleven
        team.addFirstEleven(striker1);
        team.addFirstEleven(striker2);
        team.addFirstEleven(striker3);
        team.addFirstEleven(midfielder1);
        team.addFirstEleven(midfielder2);
        team.addFirstEleven(defender1);
        team.addFirstEleven(defender2);
        team.addFirstEleven(goalkeeper);
        team.addFirstEleven(defender3);
        team.addFirstEleven(midfielder3);
        team.addFirstEleven(defender5);
        
        // No goal announcements added
        
        // Calculate and verify
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 0 when no goals scored", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players, including 1 Goalkeeper who saved 2 goals
        // No Strikers have scored goals but the Goalkeeper had announcements for saves
        
        // Create 1 goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        
        // Create 10 other players (including forwards)
        Player striker1 = new Player();
        striker1.setName("Striker1");
        striker1.setPosition(Position.FORWARD);
        striker1.setNumber(9);
        
        Player striker2 = new Player();
        striker2.setName("Striker2");
        striker2.setPosition(Position.FORWARD);
        striker2.setNumber(10);
        
        Player midfielder1 = new Player();
        midfielder1.setName("Midfielder1");
        midfielder1.setPosition(Position.MIDFIELD);
        midfielder1.setNumber(8);
        
        Player midfielder2 = new Player();
        midfielder2.setName("Midfielder2");
        midfielder2.setPosition(Position.MIDFIELD);
        midfielder2.setNumber(6);
        
        Player defender1 = new Player();
        defender1.setName("Defender1");
        defender1.setPosition(Position.DEFENSE);
        defender1.setNumber(4);
        
        Player defender2 = new Player();
        defender2.setName("Defender2");
        defender2.setPosition(Position.DEFENSE);
        defender2.setNumber(5);
        
        Player defender3 = new Player();
        defender3.setName("Defender3");
        defender3.setPosition(Position.DEFENSE);
        defender3.setNumber(2);
        
        Player defender4 = new Player();
        defender4.setName("Defender4");
        defender4.setPosition(Position.DEFENSE);
        defender4.setNumber(3);
        
        Player midfielder3 = new Player();
        midfielder3.setName("Midfielder3");
        midfielder3.setPosition(Position.MIDFIELD);
        midfielder3.setNumber(7);
        
        Player defender5 = new Player();
        defender5.setName("Defender5");
        defender5.setPosition(Position.DEFENSE);
        defender5.setNumber(12);
        
        Player midfielder4 = new Player();
        midfielder4.setName("Midfielder4");
        midfielder4.setPosition(Position.MIDFIELD);
        midfielder4.setNumber(14);
        
        // Add all players to first eleven
        team.addFirstEleven(goalkeeper);
        team.addFirstEleven(striker1);
        team.addFirstEleven(striker2);
        team.addFirstEleven(midfielder1);
        team.addFirstEleven(midfielder2);
        team.addFirstEleven(defender1);
        team.addFirstEleven(defender2);
        team.addFirstEleven(defender3);
        team.addFirstEleven(defender4);
        team.addFirstEleven(midfielder3);
        team.addFirstEleven(defender5);
        team.addFirstEleven(midfielder4);
        
        // Create save announcements for goalkeeper (2 saves)
        Date time = dateFormat.parse("2024-01-01 15:00:00");
        
        Announcement save1 = new Announcement();
        save1.setType(AnnouncementType.SAVE);
        save1.setPlayer(goalkeeper);
        save1.setTime(time);
        team.addAnnouncement(save1);
        
        Announcement save2 = new Announcement();
        save2.setType(AnnouncementType.SAVE);
        save2.setPlayer(goalkeeper);
        save2.setTime(new Date(time.getTime() + 60000));
        team.addAnnouncement(save2);
        
        // Calculate and verify
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 0 when only saves recorded, no score announcements", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        
        // No players added to first eleven (team is empty as created in setUp)
        
        // Create some announcements for players not in first eleven
        Player sparePlayer = new Player();
        sparePlayer.setName("Spare Player");
        sparePlayer.setPosition(Position.FORWARD);
        sparePlayer.setNumber(20);
        
        team.addSpareTeam(sparePlayer);
        
        Date time = dateFormat.parse("2024-01-01 15:00:00");
        Announcement goal = new Announcement();
        goal.setType(AnnouncementType.SCORE);
        goal.setPlayer(sparePlayer);
        goal.setTime(time);
        team.addAnnouncement(goal);
        
        // Calculate and verify
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Total announcements should be 0 when no players in first eleven", 0, result);
    }
}