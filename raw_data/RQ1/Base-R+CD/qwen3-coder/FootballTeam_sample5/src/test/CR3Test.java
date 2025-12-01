import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    private FootballTeam team;
    private List<Player> firstEleven;
    private List<Player> spareTeam;
    private List<Player> players;

    @Before
    public void setUp() {
        team = new FootballTeam();
        firstEleven = new ArrayList<>();
        spareTeam = new ArrayList<>();
        players = new ArrayList<>();
        
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        team.setPlayers(players);
    }

    @Test
    public void testCase1_averageAgeCalculationWithAllPlayers() {
        // Create players
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
        
        // Create spare players
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Mike");
        sparePlayer1.setAge(26);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Karl");
        sparePlayer2.setAge(24);
        
        // Add to players list
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(sparePlayer1);
        players.add(sparePlayer2);
        
        // Set first eleven
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        // Set spare team
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        
        // Calculate average age
        double averageAge = team.calculateAverageAgeOfSpareTeam();
        
        // Expected: (26 + 24) / 2 = 25.0
        assertEquals(25.0, averageAge, 0.001);
    }

    @Test
    public void testCase2_averageAgeCalculationWithNoSparePlayers() {
        // Create players
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
        
        // Add to players list
        players.add(player1);
        players.add(player2);
        players.add(player3);
        
        // Set first eleven (all players)
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        // No spare players
        
        // Calculate average age
        double averageAge = team.calculateAverageAgeOfSpareTeam();
        
        // Expected: 0.0 (No spare players)
        assertEquals(0.0, averageAge, 0.001);
    }

    @Test
    public void testCase3_averageAgeCalculationWithMixedAgeSparePlayers() {
        // Create players
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
        
        // Create spare players
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Nihat");
        sparePlayer1.setAge(35);
        sparePlayer1.setPosition(Position.FORWARD);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Onur");
        sparePlayer2.setAge(22);
        sparePlayer2.setPosition(Position.MIDFIELD);
        
        // Add to players list
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(sparePlayer1);
        players.add(sparePlayer2);
        
        // Set first eleven
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        // Set spare team
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        
        // Calculate average age
        double averageAge = team.calculateAverageAgeOfSpareTeam();
        
        // Expected: (35 + 22) / 2 = 28.5
        assertEquals(28.5, averageAge, 0.001);
    }

    @Test
    public void testCase4_averageAgeCalculationWithOneSparePlayer() {
        // Create players
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
        
        // Create spare player
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Yiğit");
        sparePlayer1.setAge(31);
        sparePlayer1.setPosition(Position.FORWARD);
        
        // Add to players list
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(sparePlayer1);
        
        // Set first eleven
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        // Set spare team
        spareTeam.add(sparePlayer1);
        
        // Calculate average age
        double averageAge = team.calculateAverageAgeOfSpareTeam();
        
        // Expected: 31.0 (Only one spare player)
        assertEquals(31.0, averageAge, 0.001);
    }

    @Test
    public void testCase5_averageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Create players
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
        
        // Create spare players
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Zafer");
        sparePlayer1.setAge(27);
        sparePlayer1.setPosition(Position.MIDFIELD);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Burak");
        sparePlayer2.setAge(27);
        sparePlayer2.setPosition(Position.MIDFIELD);
        
        // Add to players list
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(sparePlayer1);
        players.add(sparePlayer2);
        
        // Set first eleven
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        // Set spare team
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        
        // Calculate average age
        double averageAge = team.calculateAverageAgeOfSpareTeam();
        
        // Expected: (27 + 27) / 2 = 27.0
        assertEquals(27.0, averageAge, 0.001);
    }
}