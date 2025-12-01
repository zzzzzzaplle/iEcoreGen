import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_AverageAgeCalculationWithAllPlayers() {
        // Set up players
        List<Player> players = new ArrayList<>();
        
        Player player1 = new Player();
        player1.setName("John");
        player1.setAge(25);
        player1.setPosition(Position.MIDFIELD);
        
        Player player2 = new Player();
        player2.setName("Alex");
        player2.setAge(28);
        player2.setPosition(Position.FORWARD);
        
        Player player3 = new Player();
        player3.setName("Sam");
        player3.setAge(30);
        player3.setPosition(Position.DEFENSE);
        
        Player spare1 = new Player();
        spare1.setName("Mike");
        spare1.setAge(26);
        
        Player spare2 = new Player();
        spare2.setName("Karl");
        spare2.setAge(24);
        
        // Add all players to team
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(spare1);
        players.add(spare2);
        team.setPlayers(players);
        
        // Set first eleven (Players 1, 2, 3)
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        team.setFirstEleven(firstEleven);
        
        // Set spare team (Spare Player 1 and 2)
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(spare1);
        spareTeam.add(spare2);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify
        double expectedAverage = (26.0 + 24.0) / 2.0; // 25.0
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 25.0", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // Set up players
        List<Player> players = new ArrayList<>();
        
        Player player1 = new Player();
        player1.setName("Fatih");
        player1.setAge(27);
        player1.setPosition(Position.GOALKEEPER);
        
        Player player2 = new Player();
        player2.setName("Gökhan");
        player2.setAge(25);
        player2.setPosition(Position.DEFENSE);
        
        Player player3 = new Player();
        player3.setName("Hakan");
        player3.setAge(29);
        player3.setPosition(Position.MIDFIELD);
        
        // Add all players to team
        players.add(player1);
        players.add(player2);
        players.add(player3);
        team.setPlayers(players);
        
        // Set first eleven (all players)
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        team.setFirstEleven(firstEleven);
        
        // Set empty spare team
        List<Player> spareTeam = new ArrayList<>();
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify
        double expectedAverage = 0.0;
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 0.0 when no spare players", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // Set up players
        List<Player> players = new ArrayList<>();
        
        Player player1 = new Player();
        player1.setName("Kerem");
        player1.setAge(22);
        player1.setPosition(Position.FORWARD);
        
        Player player2 = new Player();
        player2.setName("Levent");
        player2.setAge(24);
        player2.setPosition(Position.MIDFIELD);
        
        Player player3 = new Player();
        player3.setName("Mehmet");
        player3.setAge(26);
        player3.setPosition(Position.DEFENSE);
        
        Player spare1 = new Player();
        spare1.setName("Nihat");
        spare1.setAge(35);
        spare1.setPosition(Position.FORWARD);
        
        Player spare2 = new Player();
        spare2.setName("Onur");
        spare2.setAge(22);
        spare2.setPosition(Position.MIDFIELD);
        
        // Add all players to team
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(spare1);
        players.add(spare2);
        team.setPlayers(players);
        
        // Set first eleven (Players 1, 2, 3)
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        team.setFirstEleven(firstEleven);
        
        // Set spare team (Spare Player 1 and 2)
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(spare1);
        spareTeam.add(spare2);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify
        double expectedAverage = (35.0 + 22.0) / 2.0; // 28.5
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 28.5", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // Set up players
        List<Player> players = new ArrayList<>();
        
        Player player1 = new Player();
        player1.setName("Tolga");
        player1.setAge(28);
        player1.setPosition(Position.GOALKEEPER);
        
        Player player2 = new Player();
        player2.setName("Umut");
        player2.setAge(25);
        player2.setPosition(Position.DEFENSE);
        
        Player player3 = new Player();
        player3.setName("Volkan");
        player3.setAge(30);
        player3.setPosition(Position.FORWARD);
        
        Player spare1 = new Player();
        spare1.setName("Yiğit");
        spare1.setAge(31);
        spare1.setPosition(Position.FORWARD);
        
        // Add all players to team
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(spare1);
        team.setPlayers(players);
        
        // Set first eleven (Players 1, 2, 3)
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        team.setFirstEleven(firstEleven);
        
        // Set spare team (only Spare Player 1)
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(spare1);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify
        double expectedAverage = 31.0;
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 31.0 when one spare player", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Set up players
        List<Player> players = new ArrayList<>();
        
        Player player1 = new Player();
        player1.setName("Eren");
        player1.setAge(24);
        player1.setPosition(Position.FORWARD);
        
        Player player2 = new Player();
        player2.setName("Furkan");
        player2.setAge(26);
        player2.setPosition(Position.MIDFIELD);
        
        Player player3 = new Player();
        player3.setName("Kadir");
        player3.setAge(28);
        player3.setPosition(Position.DEFENSE);
        
        Player spare1 = new Player();
        spare1.setName("Zafer");
        spare1.setAge(27);
        spare1.setPosition(Position.MIDFIELD);
        
        Player spare2 = new Player();
        spare2.setName("Burak");
        spare2.setAge(27);
        spare2.setPosition(Position.MIDFIELD);
        
        // Add all players to team
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(spare1);
        players.add(spare2);
        team.setPlayers(players);
        
        // Set first eleven (Players 1, 2, 3)
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        team.setFirstEleven(firstEleven);
        
        // Set spare team (Spare Player 1 and 2)
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(spare1);
        spareTeam.add(spare2);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify
        double expectedAverage = (27.0 + 27.0) / 2.0; // 27.0
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 27.0", expectedAverage, actualAverage, 0.001);
    }
}