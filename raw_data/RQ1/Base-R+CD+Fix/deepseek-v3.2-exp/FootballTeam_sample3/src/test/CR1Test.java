import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private FootballTeam team;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        team.setFirstEleven(new ArrayList<>());
        team.setSpareTeam(new ArrayList<>());
        team.setPlayers(new ArrayList<>());
        team.setAnnouncements(new ArrayList<>());
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AllStrikersWithGoals() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        // Announce scores for each of the 4 Strikers
        
        // Create 4 strikers
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
        striker4.setNumber(7);
        
        // Create 7 non-striker players
        List<Player> nonStrikers = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i);
            nonStrikers.add(player);
        }
        
        // Set up first eleven
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(striker1);
        firstEleven.add(striker2);
        firstEleven.add(striker3);
        firstEleven.add(striker4);
        firstEleven.addAll(nonStrikers);
        team.setFirstEleven(firstEleven);
        
        // Create announcements: 2 goals for each striker (total 8 announcements)
        List<Announcement> announcements = new ArrayList<>();
        
        // Striker1 - 2 goals
        Announcement goal1 = new Announcement();
        goal1.setPlayer(striker1);
        goal1.setType(AnnouncementType.SCORE);
        goal1.setTime(dateFormat.parse("2024-01-15 15:00:00"));
        announcements.add(goal1);
        
        Announcement goal2 = new Announcement();
        goal2.setPlayer(striker1);
        goal2.setType(AnnouncementType.SCORE);
        goal2.setTime(dateFormat.parse("2024-01-15 16:00:00"));
        announcements.add(goal2);
        
        // Striker2 - 2 goals
        Announcement goal3 = new Announcement();
        goal3.setPlayer(striker2);
        goal3.setType(AnnouncementType.SCORE);
        goal3.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        announcements.add(goal3);
        
        Announcement goal4 = new Announcement();
        goal4.setPlayer(striker2);
        goal4.setType(AnnouncementType.SCORE);
        goal4.setTime(dateFormat.parse("2024-01-15 16:30:00"));
        announcements.add(goal4);
        
        // Striker3 - 2 goals
        Announcement goal5 = new Announcement();
        goal5.setPlayer(striker3);
        goal5.setType(AnnouncementType.SCORE);
        goal5.setTime(dateFormat.parse("2024-01-15 15:45:00"));
        announcements.add(goal5);
        
        Announcement goal6 = new Announcement();
        goal6.setPlayer(striker3);
        goal6.setType(AnnouncementType.SCORE);
        goal6.setTime(dateFormat.parse("2024-01-15 16:45:00"));
        announcements.add(goal6);
        
        // Striker4 - 2 goals
        Announcement goal7 = new Announcement();
        goal7.setPlayer(striker4);
        goal7.setType(AnnouncementType.SCORE);
        goal7.setTime(dateFormat.parse("2024-01-15 15:15:00"));
        announcements.add(goal7);
        
        Announcement goal8 = new Announcement();
        goal8.setPlayer(striker4);
        goal8.setType(AnnouncementType.SCORE);
        goal8.setTime(dateFormat.parse("2024-01-15 16:15:00"));
        announcements.add(goal8);
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 4 players × 2 goals = 8
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_MixedPlayersScoring() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        // Announce scores for Strikers
        
        // Create 2 strikers
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
        
        // Create 7 other players
        List<Player> otherPlayers = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i + 10);
            otherPlayers.add(player);
        }
        
        // Set up first eleven
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(striker1);
        firstEleven.add(striker2);
        firstEleven.add(midfielder1);
        firstEleven.add(midfielder2);
        firstEleven.addAll(otherPlayers);
        team.setFirstEleven(firstEleven);
        
        // Create announcements: 3 goals for each striker (total 6 announcements)
        List<Announcement> announcements = new ArrayList<>();
        
        // Striker1 - 3 goals
        for (int i = 1; i <= 3; i++) {
            Announcement goal = new Announcement();
            goal.setPlayer(striker1);
            goal.setType(AnnouncementType.SCORE);
            goal.setTime(dateFormat.parse("2024-01-15 15:" + (i * 15) + ":00"));
            announcements.add(goal);
        }
        
        // Striker2 - 3 goals
        for (int i = 1; i <= 3; i++) {
            Announcement goal = new Announcement();
            goal.setPlayer(striker2);
            goal.setType(AnnouncementType.SCORE);
            goal.setTime(dateFormat.parse("2024-01-15 16:" + (i * 15) + ":00"));
            announcements.add(goal);
        }
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = (2 players × 3 goals) = 6
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_FirstElevenWithNoGoals() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Add 11 players: All players (including 3 Strikers) scored 0 goals
        
        // Create 3 strikers
        Player striker1 = new Player();
        striker1.setName("Striker1");
        striker1.setPosition(Position.FORWARD);
        
        Player striker2 = new Player();
        striker2.setName("Striker2");
        striker2.setPosition(Position.FORWARD);
        
        Player striker3 = new Player();
        striker3.setName("Striker3");
        striker3.setPosition(Position.FORWARD);
        
        // Create 8 other players
        List<Player> otherPlayers = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD);
            otherPlayers.add(player);
        }
        
        // Set up first eleven
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(striker1);
        firstEleven.add(striker2);
        firstEleven.add(striker3);
        firstEleven.addAll(otherPlayers);
        team.setFirstEleven(firstEleven);
        
        // Create empty announcements list (no goals)
        List<Announcement> announcements = new ArrayList<>();
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
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
        
        // Create 10 other players (including strikers but with no goals)
        List<Player> otherPlayers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            if (i <= 3) {
                player.setPosition(Position.FORWARD); // 3 forwards but no goals
            } else if (i <= 6) {
                player.setPosition(Position.MIDFIELD);
            } else {
                player.setPosition(Position.DEFENSE);
            }
            otherPlayers.add(player);
        }
        
        // Set up first eleven
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(goalkeeper);
        firstEleven.addAll(otherPlayers);
        team.setFirstEleven(firstEleven);
        
        // Create announcements: 2 saves by goalkeeper (no score announcements)
        List<Announcement> announcements = new ArrayList<>();
        
        // Goalkeeper - 2 saves
        Announcement save1 = new Announcement();
        save1.setPlayer(goalkeeper);
        save1.setType(AnnouncementType.SAVE);
        save1.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        announcements.add(save1);
        
        Announcement save2 = new Announcement();
        save2.setPlayer(goalkeeper);
        save2.setType(AnnouncementType.SAVE);
        save2.setTime(dateFormat.parse("2024-01-15 16:30:00"));
        announcements.add(save2);
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_NoPlayersInFirstEleven() throws Exception {
        // SetUp: Create a football team named "Turgutlu"
        // Do not add any players to the first eleven
        
        // First eleven is already empty from setUp()
        
        // Create some announcements (but shouldn't matter since no players in first eleven)
        List<Announcement> announcements = new ArrayList<>();
        
        Player somePlayer = new Player();
        somePlayer.setName("SomePlayer");
        somePlayer.setPosition(Position.FORWARD);
        
        Announcement goal = new Announcement();
        goal.setPlayer(somePlayer);
        goal.setType(AnnouncementType.SCORE);
        goal.setTime(dateFormat.parse("2024-01-15 15:30:00"));
        announcements.add(goal);
        
        team.setAnnouncements(announcements);
        
        // Expected Output: Total announcements = 0 (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}