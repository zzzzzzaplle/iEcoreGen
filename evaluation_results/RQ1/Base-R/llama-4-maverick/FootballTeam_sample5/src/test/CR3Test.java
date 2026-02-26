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
        // SetUp: Create football team "Turgutlu" with players
        List<Player> startingEleven = new ArrayList<>();
        startingEleven.add(new Player("Turgutlu", "John", 25, 1, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu", "Alex", 28, 2, Position.FORWARD));
        startingEleven.add(new Player("Turgutlu", "Sam", 30, 3, Position.DEFENSE));
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(new Player("Turgutlu", "Mike", 26, 4, Position.MIDFIELD));
        spareTeam.add(new Player("Turgutlu", "Karl", 24, 5, Position.MIDFIELD));
        
        footballTeam.setStartingEleven(startingEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team players
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Expected Output: Average age = (26 + 24) / 2 = 25.0
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // SetUp: Create football team "Turgutlu" with players (no spare players)
        List<Player> startingEleven = new ArrayList<>();
        startingEleven.add(new Player("Turgutlu", "Fatih", 27, 1, Position.GOALKEEPER));
        startingEleven.add(new Player("Turgutlu", "Gökhan", 25, 2, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Hakan", 29, 3, Position.MIDFIELD));
        
        List<Player> spareTeam = new ArrayList<>(); // Empty spare team
        
        footballTeam.setStartingEleven(startingEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team players
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Expected Output: Average age = 0.0 (No spare players available)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // SetUp: Create football team "Turgutlu" with players
        List<Player> startingEleven = new ArrayList<>();
        startingEleven.add(new Player("Turgutlu", "Kerem", 22, 1, Position.FORWARD));
        startingEleven.add(new Player("Turgutlu", "Levent", 24, 2, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu", "Mehmet", 26, 3, Position.DEFENSE));
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(new Player("Turgutlu", "Nihat", 35, 4, Position.FORWARD));
        spareTeam.add(new Player("Turgutlu", "Onur", 22, 5, Position.MIDFIELD));
        
        footballTeam.setStartingEleven(startingEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team players
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Expected Output: Average age = (35 + 22) / 2 = 28.5
        assertEquals(28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // SetUp: Create football team "Turgutlu" with players
        List<Player> startingEleven = new ArrayList<>();
        startingEleven.add(new Player("Turgutlu", "Tolga", 28, 1, Position.GOALKEEPER));
        startingEleven.add(new Player("Turgutlu", "Umut", 25, 2, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu", "Volkan", 30, 3, Position.FORWARD));
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(new Player("Turgutlu", "Yiğit", 31, 4, Position.FORWARD));
        
        footballTeam.setStartingEleven(startingEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team players
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Expected Output: Average age = 31.0 (Only one spare player)
        assertEquals(31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // SetUp: Create football team "Turgutlu" with players
        List<Player> startingEleven = new ArrayList<>();
        startingEleven.add(new Player("Turgutlu", "Eren", 24, 1, Position.FORWARD));
        startingEleven.add(new Player("Turgutlu", "Furkan", 26, 2, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu", "Kadir", 28, 3, Position.DEFENSE));
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(new Player("Turgutlu", "Zafer", 27, 4, Position.MIDFIELD));
        spareTeam.add(new Player("Turgutlu", "Burak", 27, 5, Position.MIDFIELD));
        
        footballTeam.setStartingEleven(startingEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team players
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Expected Output: Average age = (27 + 27) / 2 = 27.0
        assertEquals(27.0, result, 0.001);
    }
}