import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() throws Exception {
        // Create football team
        team.setName("Turgutlu");
        
        // Create 4 strikers and 7 other players
        List<Player> firstEleven = new ArrayList<>();
        List<Player> allPlayers = new ArrayList<>();
        
        // Add 4 strikers (each with 2 goals)
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
            allPlayers.add(striker);
        }
        
        // Add 7 other players (no goals)
        for (int i = 5; i <= 11; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setAge(26);
            otherPlayer.setNumber(i);
            otherPlayer.setPosition(Position.MIDFIELD);
            firstEleven.add(otherPlayer);
            allPlayers.add(otherPlayer);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(allPlayers);
        
        // Create announcements: 4 strikers × 2 goals each
        List<Announcement> announcements = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        for (int i = 1; i <= 4; i++) {
            Announcement goal1 = new Announcement();
            goal1.setType(AnnouncementType.SCORE);
            goal1.setTime(sdf.parse("2023-10-01 15:0" + i + ":00"));
            goal1.setPlayer(firstEleven.get(i-1));
            announcements.add(goal1);
            
            Announcement goal2 = new Announcement();
            goal2.setType(AnnouncementType.SCORE);
            goal2.setTime(sdf.parse("2023-10-01 15:1" + i + ":00"));
            goal2.setPlayer(firstEleven.get(i-1));
            announcements.add(goal2);
        }
        
        team.setAnnouncements(announcements);
        
        // Calculate and verify result
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should count 4 players × 2 goals = 8 announcements", 8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // Create football team
        team.setName("Turgutlu");
        
        // Create 2 strikers and 9 other players
        List<Player> firstEleven = new ArrayList<>();
        List<Player> allPlayers = new ArrayList<>();
        
        // Add 2 strikers (each with 3 goals)
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
            allPlayers.add(striker);
        }
        
        // Add 2 midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setAge(26);
            midfielder.setNumber(i);
            midfielder.setPosition(Position.MIDFIELD);
            firstEleven.add(midfielder);
            allPlayers.add(midfielder);
        }
        
        // Add 7 other players
        for (int i = 5; i <= 11; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setAge(27);
            otherPlayer.setNumber(i);
            otherPlayer.setPosition(Position.DEFENSE);
            firstEleven.add(otherPlayer);
            allPlayers.add(otherPlayer);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(allPlayers);
        
        // Create announcements: 2 strikers × 3 goals each
        List<Announcement> announcements = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 3; j++) {
                Announcement goal = new Announcement();
                goal.setType(AnnouncementType.SCORE);
                goal.setTime(sdf.parse("2023-10-01 15:" + i + j + ":00"));
                goal.setPlayer(firstEleven.get(i-1));
                announcements.add(goal);
            }
        }
        
        team.setAnnouncements(announcements);
        
        // Calculate and verify result
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should count 2 players × 3 goals = 6 announcements", 6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // Create football team
        team.setName("Turgutlu");
        
        // Create 11 players including 3 strikers, but no goals
        List<Player> firstEleven = new ArrayList<>();
        List<Player> allPlayers = new ArrayList<>();
        
        // Add 3 strikers (no goals)
        for (int i = 1; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setAge(25);
            striker.setNumber(i);
            striker.setPosition(Position.FORWARD);
            firstEleven.add(striker);
            allPlayers.add(striker);
        }
        
        // Add 8 other players
        for (int i = 4; i <= 11; i++) {
            Player otherPlayer = new Player();
            otherPlayer.setName("Player" + i);
            otherPlayer.setAge(26);
            otherPlayer.setNumber(i);
            otherPlayer.setPosition(Position.MIDFIELD);
            firstEleven.add(otherPlayer);
            allPlayers.add(otherPlayer);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(allPlayers);
        
        // No announcements (empty list)
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Calculate and verify result
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should return 0 when no goals scored", 0, result);
    }
    
    @Test
    public void testCase4_OnlyGoalkeeperWithSaveAnnouncements() throws Exception {
        // Create football team
        team.setName("Turgutlu");
        
        // Create 11 players including 1 goalkeeper with saves
        List<Player> firstEleven = new ArrayList<>();
        List<Player> allPlayers = new ArrayList<>();
        
        // Add goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setAge(28);
        goalkeeper.setNumber(1);
        goalkeeper.setPosition(Position.GOALKEEPER);
        firstEleven.add(goalkeeper);
        allPlayers.add(goalkeeper);
        
        // Add 10 other players including strikers (no goals)
        for (int i = 2; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setAge(25);
            player.setNumber(i);
            if (i <= 4) {
                player.setPosition(Position.FORWARD);
            } else if (i <= 7) {
                player.setPosition(Position.MIDFIELD);
            } else {
                player.setPosition(Position.DEFENSE);
            }
            firstEleven.add(player);
            allPlayers.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        team.setPlayers(allPlayers);
        
        // Create 2 save announcements by goalkeeper (no score announcements)
        List<Announcement> announcements = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        for (int i = 1; i <= 2; i++) {
            Announcement save = new Announcement();
            save.setType(AnnouncementType.SAVE);
            save.setTime(sdf.parse("2023-10-01 15:0" + i + ":00"));
            save.setPlayer(goalkeeper);
            announcements.add(save);
        }
        
        team.setAnnouncements(announcements);
        
        // Calculate and verify result
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should return 0 when only save announcements exist", 0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // Create football team
        team.setName("Turgutlu");
        
        // Set empty first eleven and players lists
        team.setFirstEleven(new ArrayList<Player>());
        team.setPlayers(new ArrayList<Player>());
        
        // Set empty announcements list
        team.setAnnouncements(new ArrayList<Announcement>());
        
        // Calculate and verify result
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals("Should return 0 when no players in first eleven", 0, result);
    }
}