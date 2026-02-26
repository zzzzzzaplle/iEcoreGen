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
        // SetUp: Create players
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
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Karl");
        sparePlayer2.setAge(24);
        
        // SetUp: Configure football team
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
        
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        team.setPlayers(allPlayers);
        
        // Test: Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Expected Output: Average age = (26 + 24) / 2 = 25.0
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // SetUp: Create players
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
        
        // SetUp: Configure football team (all players in first eleven, no spare players)
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        List<Player> spareTeam = new ArrayList<>(); // Empty spare team
        List<Player> allPlayers = new ArrayList<>(firstEleven);
        
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        team.setPlayers(allPlayers);
        
        // Test: Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Expected Output: Average age = 0.0 (No spare players available)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // SetUp: Create players
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
        
        // SetUp: Configure football team
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
        
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        team.setPlayers(allPlayers);
        
        // Test: Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Expected Output: Average age = (35 + 22) / 2 = 28.5
        assertEquals(28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // SetUp: Create players
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
        
        // SetUp: Configure football team
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(firstEleven);
        allPlayers.add(sparePlayer1);
        
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        team.setPlayers(allPlayers);
        
        // Test: Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Expected Output: Average age = 31.0 (Only one spare player)
        assertEquals(31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // SetUp: Create players
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
        
        // SetUp: Configure football team
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
        
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        team.setPlayers(allPlayers);
        
        // Test: Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Expected Output: Average age = (27 + 27) / 2 = 27.0
        assertEquals(27.0, result, 0.001);
    }
}