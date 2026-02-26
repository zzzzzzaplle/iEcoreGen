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
    public void testCase1_AverageAgeCalculationWithAllPlayers() {
        // Set up players
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
        
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Mike");
        sparePlayer1.setAge(26);
        sparePlayer1.setPosition(Position.MIDFIELD);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Karl");
        sparePlayer2.setAge(24);
        sparePlayer2.setPosition(Position.MIDFIELD);
        
        // Set up team lists
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(firstEleven);
        allPlayers.addAll(spareTeam);
        
        footballTeam.setFirstEleven(firstEleven);
        footballTeam.setSpareTeam(spareTeam);
        footballTeam.setPlayers(allPlayers);
        
        // Calculate and verify average age
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // Set up players (all in first eleven)
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
        
        // Set up team lists
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        List<Player> spareTeam = new ArrayList<>();
        List<Player> allPlayers = new ArrayList<>(firstEleven);
        
        footballTeam.setFirstEleven(firstEleven);
        footballTeam.setSpareTeam(spareTeam);
        footballTeam.setPlayers(allPlayers);
        
        // Calculate and verify average age
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // Set up players
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
        
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Nihat");
        sparePlayer1.setAge(35);
        sparePlayer1.setPosition(Position.FORWARD);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Onur");
        sparePlayer2.setAge(22);
        sparePlayer2.setPosition(Position.MIDFIELD);
        
        // Set up team lists
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(firstEleven);
        allPlayers.addAll(spareTeam);
        
        footballTeam.setFirstEleven(firstEleven);
        footballTeam.setSpareTeam(spareTeam);
        footballTeam.setPlayers(allPlayers);
        
        // Calculate and verify average age
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        assertEquals(28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // Set up players
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
        
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Yiğit");
        sparePlayer1.setAge(31);
        sparePlayer1.setPosition(Position.FORWARD);
        
        // Set up team lists
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(firstEleven);
        allPlayers.addAll(spareTeam);
        
        footballTeam.setFirstEleven(firstEleven);
        footballTeam.setSpareTeam(spareTeam);
        footballTeam.setPlayers(allPlayers);
        
        // Calculate and verify average age
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        assertEquals(31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Set up players
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
        
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Zafer");
        sparePlayer1.setAge(27);
        sparePlayer1.setPosition(Position.MIDFIELD);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Burak");
        sparePlayer2.setAge(27);
        sparePlayer2.setPosition(Position.MIDFIELD);
        
        // Set up team lists
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(firstEleven);
        allPlayers.addAll(spareTeam);
        
        footballTeam.setFirstEleven(firstEleven);
        footballTeam.setSpareTeam(spareTeam);
        footballTeam.setPlayers(allPlayers);
        
        // Calculate and verify average age
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        assertEquals(27.0, result, 0.001);
    }
}