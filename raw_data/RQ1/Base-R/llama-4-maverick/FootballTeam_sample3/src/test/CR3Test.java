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
        // SetUp: Create players for starting eleven and spare team
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(new FootballPlayer("Turgutlu", "John", 25, 1, Position.MIDFIELD));
        startingEleven.add(new FootballPlayer("Turgutlu", "Alex", 28, 2, Position.FORWARD));
        startingEleven.add(new FootballPlayer("Turgutlu", "Sam", 30, 3, Position.DEFENSE));
        
        List<FootballPlayer> spareTeam = new ArrayList<>();
        spareTeam.add(new FootballPlayer("Turgutlu", "Mike", 26, 4, Position.MIDFIELD));
        spareTeam.add(new FootballPlayer("Turgutlu", "Karl", 24, 5, Position.FORWARD));
        
        footballTeam.setStartingEleven(startingEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Verify result: (26 + 24) / 2 = 25.0
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // SetUp: Create players only for starting eleven (no spare players)
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(new FootballPlayer("Turgutlu", "Fatih", 27, 1, Position.DEFENSE));
        startingEleven.add(new FootballPlayer("Turgutlu", "Gökhan", 25, 2, Position.DEFENSE));
        startingEleven.add(new FootballPlayer("Turgutlu", "Hakan", 29, 3, Position.MIDFIELD));
        
        footballTeam.setStartingEleven(startingEleven);
        footballTeam.setSpareTeam(new ArrayList<>()); // Empty spare team
        
        // Calculate average age of empty spare team
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Verify result: 0.0 (no spare players available)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // SetUp: Create players for starting eleven and spare team with mixed ages
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(new FootballPlayer("Turgutlu", "Kerem", 22, 1, Position.FORWARD));
        startingEleven.add(new FootballPlayer("Turgutlu", "Levent", 24, 2, Position.MIDFIELD));
        startingEleven.add(new FootballPlayer("Turgutlu", "Mehmet", 26, 3, Position.DEFENSE));
        
        List<FootballPlayer> spareTeam = new ArrayList<>();
        spareTeam.add(new FootballPlayer("Turgutlu", "Nihat", 35, 4, Position.FORWARD));
        spareTeam.add(new FootballPlayer("Turgutlu", "Onur", 22, 5, Position.MIDFIELD));
        
        footballTeam.setStartingEleven(startingEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Verify result: (35 + 22) / 2 = 28.5
        assertEquals(28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // SetUp: Create players for starting eleven and only one spare player
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(new FootballPlayer("Turgutlu", "Tolga", 28, 1, Position.DEFENSE));
        startingEleven.add(new FootballPlayer("Turgutlu", "Umut", 25, 2, Position.DEFENSE));
        startingEleven.add(new FootballPlayer("Turgutlu", "Volkan", 30, 3, Position.FORWARD));
        
        List<FootballPlayer> spareTeam = new ArrayList<>();
        spareTeam.add(new FootballPlayer("Turgutlu", "Yiğit", 31, 4, Position.FORWARD));
        
        footballTeam.setStartingEleven(startingEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team with one player
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Verify result: 31.0 (only one spare player)
        assertEquals(31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // SetUp: Create players for starting eleven and spare players with identical ages
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(new FootballPlayer("Turgutlu", "Eren", 24, 1, Position.FORWARD));
        startingEleven.add(new FootballPlayer("Turgutlu", "Furkan", 26, 2, Position.MIDFIELD));
        startingEleven.add(new FootballPlayer("Turgutlu", "Kadir", 28, 3, Position.DEFENSE));
        
        List<FootballPlayer> spareTeam = new ArrayList<>();
        spareTeam.add(new FootballPlayer("Turgutlu", "Zafer", 27, 4, Position.MIDFIELD));
        spareTeam.add(new FootballPlayer("Turgutlu", "Burak", 27, 5, Position.MIDFIELD));
        
        footballTeam.setStartingEleven(startingEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Verify result: (27 + 27) / 2 = 27.0
        assertEquals(27.0, result, 0.001);
    }
}