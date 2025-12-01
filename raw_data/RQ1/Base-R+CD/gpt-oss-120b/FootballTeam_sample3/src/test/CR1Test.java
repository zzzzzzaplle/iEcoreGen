import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR1Test {
    
    private FootballTeam team;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_allStrikersWithGoals() throws ParseException {
        // Create 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals
        ArrayList<Player> firstEleven = new ArrayList<>();
        
        // Add 4 strikers
        for (int i = 1; i <= 4; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            firstEleven.add(striker);
        }
        
        // Add 7 other players
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Add all players to the team's players list
        team.setPlayers(new ArrayList<>(firstEleven));
        
        // Create announcements: 2 goals per striker
        ArrayList<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Player striker = firstEleven.get(i);
            // Each striker scores 2 goals
            for (int j = 0; j < 2; j++) {
                Announcement ann = new Announcement();
                ann.setType(AnnouncementType.SCORE);
                ann.setPlayer(striker);
                ann.setTime(dateFormat.parse("2023-01-01 10:00:" + (i * 2 + j)));
                announcements.add(ann);
            }
        }
        
        team.setAnnouncements(announcements);
        
        // Expected: 4 strikers × 2 goals = 8 announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(8, result);
    }
    
    @Test
    public void testCase2_mixedPlayersScoring() throws ParseException {
        // Create 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals
        ArrayList<Player> firstEleven = new ArrayList<>();
        
        // Add 2 strikers
        for (int i = 1; i <= 2; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            firstEleven.add(striker);
        }
        
        // Add 2 midfielders
        for (int i = 3; i <= 4; i++) {
            Player midfielder = new Player();
            midfielder.setName("Midfielder" + i);
            midfielder.setPosition(Position.MIDFIELD);
            midfielder.setNumber(i);
            firstEleven.add(midfielder);
        }
        
        // Add 7 other players
        for (int i = 5; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.DEFENSE);
            player.setNumber(i);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Add all players to the team's players list
        team.setPlayers(new ArrayList<>(firstEleven));
        
        // Create announcements: 3 goals per striker
        ArrayList<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Player striker = firstEleven.get(i);
            // Each striker scores 3 goals
            for (int j = 0; j < 3; j++) {
                Announcement ann = new Announcement();
                ann.setType(AnnouncementType.SCORE);
                ann.setPlayer(striker);
                ann.setTime(dateFormat.parse("2023-01-01 10:00:" + (i * 3 + j)));
                announcements.add(ann);
            }
        }
        
        team.setAnnouncements(announcements);
        
        // Expected: 2 strikers × 3 goals = 6 announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(6, result);
    }
    
    @Test
    public void testCase3_firstElevenWithNoGoals() throws ParseException {
        // Create 11 players: All players (including 3 Strikers) scored 0 goals
        ArrayList<Player> firstEleven = new ArrayList<>();
        
        // Add 3 strikers
        for (int i = 1; i <= 3; i++) {
            Player striker = new Player();
            striker.setName("Striker" + i);
            striker.setPosition(Position.FORWARD);
            striker.setNumber(i);
            firstEleven.add(striker);
        }
        
        // Add 8 other players
        for (int i = 4; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Add all players to the team's players list
        team.setPlayers(new ArrayList<>(firstEleven));
        
        // No announcements created
        
        // Expected: 0 announcements
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_onlyGoalkeeperWithSaveAnnouncements() throws ParseException {
        // Create 11 players, including 1 Goalkeeper who saved 2 goals
        ArrayList<Player> firstEleven = new ArrayList<>();
        
        // Add 1 goalkeeper
        Player goalkeeper = new Player();
        goalkeeper.setName("Goalkeeper");
        goalkeeper.setPosition(Position.GOALKEEPER);
        goalkeeper.setNumber(1);
        firstEleven.add(goalkeeper);
        
        // Add 10 other players (no strikers)
        for (int i = 2; i <= 11; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setPosition(Position.MIDFIELD);
            player.setNumber(i);
            firstEleven.add(player);
        }
        
        team.setFirstEleven(firstEleven);
        
        // Add all players to the team's players list
        team.setPlayers(new ArrayList<>(firstEleven));
        
        // Create save announcements for the goalkeeper
        ArrayList<Announcement> announcements = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Announcement ann = new Announcement();
            ann.setType(AnnouncementType.SAVE);
            ann.setPlayer(goalkeeper);
            ann.setTime(dateFormat.parse("2023-01-01 10:00:" + i));
            announcements.add(ann);
        }
        
        team.setAnnouncements(announcements);
        
        // Expected: 0 announcements (only saves recorded, no score announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_noPlayersInFirstEleven() {
        // Do not add any players to the first eleven
        team.setFirstEleven(new ArrayList<>());
        team.setPlayers(new ArrayList<>());
        team.setAnnouncements(new ArrayList<>());
        
        // Expected: 0 announcements (no players, no announcements)
        int result = team.calculateTotalGoalScoringAnnouncementsForFirstEleven();
        assertEquals(0, result);
    }
}