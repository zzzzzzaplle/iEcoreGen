import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        // Reset footballTeam before each test
        footballTeam = null;
    }
    
    @Test
    public void testCase1_AverageAgeCalculationWithAllPlayers() {
        // SetUp: Create a football team "Turgutlu" with players
        List<Player> startingEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        
        // Create starting eleven players
        startingEleven.add(new Player("Turgutlu", "John", 25, 10, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu", "Alex", 28, 9, Position.FORWARD));
        startingEleven.add(new Player("Turgutlu", "Sam", 30, 5, Position.DEFENSE));
        
        // Create spare team players
        spareTeam.add(new Player("Turgutlu", "Mike", 26, 15, Position.MIDFIELD));
        spareTeam.add(new Player("Turgutlu", "Karl", 24, 16, Position.FORWARD));
        
        footballTeam = new FootballTeam(startingEleven, spareTeam);
        
        // Calculate average age of spare team
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Expected Output: Average age = (26 + 24) / 2 = 25.0
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // SetUp: Create a football team "Turgutlu" with players (no spare players)
        List<Player> startingEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        
        // Create starting eleven players (all players in starting eleven)
        startingEleven.add(new Player("Turgutlu", "Fatih", 27, 1, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Gökhan", 25, 2, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Hakan", 29, 8, Position.MIDFIELD));
        
        footballTeam = new FootballTeam(startingEleven, spareTeam);
        
        // Calculate average age of spare team
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Expected Output: Average age = 0.0 (No spare players available)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // SetUp: Create a football team "Turgutlu" with players
        List<Player> startingEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        
        // Create starting eleven players
        startingEleven.add(new Player("Turgutlu", "Kerem", 22, 7, Position.FORWARD));
        startingEleven.add(new Player("Turgutlu", "Levent", 24, 6, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu", "Mehmet", 26, 3, Position.DEFENSE));
        
        // Create spare team players with mixed ages
        spareTeam.add(new Player("Turgutlu", "Nihat", 35, 11, Position.FORWARD));
        spareTeam.add(new Player("Turgutlu", "Onur", 22, 12, Position.MIDFIELD));
        
        footballTeam = new FootballTeam(startingEleven, spareTeam);
        
        // Calculate average age of spare team
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Expected Output: Average age = (35 + 22) / 2 = 28.5
        assertEquals(28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // SetUp: Create a football team "Turgutlu" with players
        List<Player> startingEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        
        // Create starting eleven players
        startingEleven.add(new Player("Turgutlu", "Tolga", 28, 1, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Umut", 25, 4, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Volkan", 30, 9, Position.FORWARD));
        
        // Create only one spare player
        spareTeam.add(new Player("Turgutlu", "Yiğit", 31, 17, Position.FORWARD));
        
        footballTeam = new FootballTeam(startingEleven, spareTeam);
        
        // Calculate average age of spare team
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Expected Output: Average age = 31.0 (Only one spare player)
        assertEquals(31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // SetUp: Create a football team "Turgutlu" with players
        List<Player> startingEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        
        // Create starting eleven players
        startingEleven.add(new Player("Turgutlu", "Eren", 24, 7, Position.FORWARD));
        startingEleven.add(new Player("Turgutlu", "Furkan", 26, 8, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu", "Kadir", 28, 5, Position.DEFENSE));
        
        // Create spare team players with identical ages
        spareTeam.add(new Player("Turgutlu", "Zafer", 27, 13, Position.MIDFIELD));
        spareTeam.add(new Player("Turgutlu", "Burak", 27, 14, Position.MIDFIELD));
        
        footballTeam = new FootballTeam(startingEleven, spareTeam);
        
        // Calculate average age of spare team
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Expected Output: Average age = (27 + 27) / 2 = 27.0
        assertEquals(27.0, result, 0.001);
    }
}