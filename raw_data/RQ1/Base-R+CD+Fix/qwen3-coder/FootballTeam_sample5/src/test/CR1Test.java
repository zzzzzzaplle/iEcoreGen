import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
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
        // Create football team "Turgutlu"
        team.setFirstEleven(new ArrayList<Player>());
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 4 strikers and 7 other players to first eleven
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            team.getFirstEleven().add(striker);
            team.getPlayers().add(striker);
        }
        
        for (int i = 5; i <= 11; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setPosition(Position.MIDFIELD);
            otherPlayer.setNumber(i);
            otherPlayer.setAge(26);
            team.getFirstEleven().add(otherPlayer);
            team.getPlayers().add(otherPlayer);
        }
        
        // Announce 2 goals for each of the 4 strikers
        for (Player striker : team.getFirstEleven()) {
            if (striker.getPosition() == Position.FORWARD) {
                // Add 2 score announcements for each striker
                Announcement goal1 = new Announcement();
                goal1.setType(AnnouncementType.SCORE);
                goal1.setPlayer(striker);
                goal1.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                team.getAnnouncements().add(goal1);
                
                Announcement goal2 = new Announcement();
                goal2.setType(AnnouncementType.SCORE);
                goal2.setPlayer(striker);
                goal2.setTime(dateFormat.parse("2024-01-01 10:30:00"));
                team.getAnnouncements().add(goal2);
            }
        }
        
        // Expected: 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // Create football team "Turgutlu"
        team.setFirstEleven(new ArrayList<Player>());
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 2 strikers and 2 midfielders and 7 other players to first eleven
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            team.getFirstEleven().add(striker);
            team.getPlayers().add(striker);
        }
        
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setNumber(i);
            midfielder.setAge(26);
            team.getFirstEleven().add(midfielder);
            team.getPlayers().add(midfielder);
        }
        
        for (int i = 5; i <= 11; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setPosition(Position.DEFENSE);
            otherPlayer.setNumber(i);
            otherPlayer.setAge(27);
            team.getFirstEleven().add(otherPlayer);
            team.getPlayers().add(otherPlayer);
        }
        
        // Announce 3 goals for each of the 2 strikers
        for (Player striker : team.getFirstEleven()) {
            if (striker.getPosition() == Position.FORWARD) {
                // Add 3 score announcements for each striker
                for (int j = 1; j <= 3; j++) {
                    Announcement goal = new Announcement();
                    goal.setType(AnnouncementType.SCORE);
                    goal.setPlayer(striker);
                    goal.setTime(dateFormat.parse("2024-01-01 10:00:00"));
                    team.getAnnouncements().add(goal);
                }
            }
        }
        
        // Expected: 2 players × 3 goals = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // Create football team "Turgutlu"
        team.setFirstEleven(new ArrayList<Player>());
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 11 players including 3 strikers with 0 goals
        for (int i = 1; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            team.getFirstEleven().add(striker);
            team.getPlayers().add(striker);
        }
        
        for (int i = 4; i <= 11; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setPosition(Position.MIDFIELD);
            otherPlayer.setNumber(i);
            otherPlayer.setAge(26);
            team.getFirstEleven().add(otherPlayer);
            team.getPlayers().add(otherPlayer);
        }
        
        // No goal announcements added
        // Expected: 0 announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create football team "Turgutlu"
        team.setFirstEleven(new ArrayList<Player>());
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Add 11 players including 1 goalkeeper and strikers
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        goalkeeper.setAge(28);
        team.getFirstEleven().add(goalkeeper);
        team.getPlayers().add(goalkeeper);
        
        for (int i = 2; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            striker.setAge(25);
            team.getFirstEleven().add(striker);
            team.getPlayers().add(striker);
        }
        
        for (int i = 5; i <= 11; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setPosition(Position.DEFENSE);
            otherPlayer.setNumber(i);
            otherPlayer.setAge(26);
            team.getFirstEleven().add(otherPlayer);
            team.getPlayers().add(otherPlayer);
        }
        
        // Add 2 save announcements for goalkeeper (no score announcements)
        for (int i = 1; i <= 2; i++) {
            Announcement save = new Announcement();
            save.setType(AnnouncementType.SAVE);
            save.setPlayer(goalkeeper);
            save.setTime(dateFormat.parse("2024-01-01 10:00:00"));
            team.getAnnouncements().add(save);
        }
        
        // Expected: 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // Create football team "Turgutlu"
        team.setFirstEleven(new ArrayList<Player>());
        team.setPlayers(new ArrayList<Player>());
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // No players added to first eleven
        // Expected: 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}