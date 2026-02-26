import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        footballTeam = new FootballTeam();
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
        
        // Set up the team
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(sparePlayer1);
        players.add(sparePlayer2);
        footballTeam.setPlayers(players);
        
        // Set up first eleven
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        footballTeam.setFirstEleven(firstEleven);
        
        // Set up spare team
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Assert expected result
        assertEquals(25.0, result, 0.001);
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
        
        // Set up the team
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        footballTeam.setPlayers(players);
        
        // Set up first eleven (all players)
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        footballTeam.setFirstEleven(firstEleven);
        
        // Set up empty spare team
        List<Player> spareTeam = new ArrayList<>();
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Assert expected result
        assertEquals(0.0, result, 0.001);
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
        
        // Set up the team
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(sparePlayer1);
        players.add(sparePlayer2);
        footballTeam.setPlayers(players);
        
        // Set up first eleven
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        footballTeam.setFirstEleven(firstEleven);
        
        // Set up spare team
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Assert expected result
        assertEquals(28.5, result, 0.001);
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
        
        // Set up the team
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(sparePlayer1);
        footballTeam.setPlayers(players);
        
        // Set up first eleven
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        footballTeam.setFirstEleven(firstEleven);
        
        // Set up spare team
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Assert expected result
        assertEquals(31.0, result, 0.001);
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
        
        // Set up the team
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(sparePlayer1);
        players.add(sparePlayer2);
        footballTeam.setPlayers(players);
        
        // Set up first eleven
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        footballTeam.setFirstEleven(firstEleven);
        
        // Set up spare team
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Assert expected result
        assertEquals(27.0, result, 0.001);
    }
}