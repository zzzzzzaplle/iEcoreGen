import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        // Reset football team before each test
        footballTeam = new FootballTeam();
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumber_BasicCase() {
        // Test Case 1: Basic case with multiple midfielders
        // SetUp: Create football team and add players
        List<Player> startingEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        
        // Add players to starting eleven
        startingEleven.add(new Player("Turgutlu FC", "Player A", 24, 15, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu FC", "Player B", 26, 22, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu FC", "Player C", 23, 18, Position.DEFENSE));
        
        footballTeam.setStartingEleven(startingEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Execute method under test
        Player result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify expected output: "Player B, Number: 22"
        assertNotNull("Result should not be null", result);
        assertEquals("Player B", result.getName());
        assertEquals(22, result.getNumber());
        assertEquals(Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // Test Case 2: Single midfielder case
        // SetUp: Create football team and add players
        List<Player> startingEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        
        // Add players to starting eleven
        startingEleven.add(new Player("Turgutlu FC", "Player D", 28, 12, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu FC", "Player E", 30, 10, Position.FORWARD));
        startingEleven.add(new Player("Turgutlu FC", "Player F", 22, 2, Position.DEFENSE));
        
        footballTeam.setStartingEleven(startingEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Execute method under test
        Player result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify expected output: "Player D, Number: 12"
        assertNotNull("Result should not be null", result);
        assertEquals("Player D", result.getName());
        assertEquals(12, result.getNumber());
        assertEquals(Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // Test Case 3: Multiple midfielders with same numbers
        // SetUp: Create football team and add players
        List<Player> startingEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        
        // Add players to starting eleven
        startingEleven.add(new Player("Turgutlu FC", "Player G", 21, 20, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu FC", "Player H", 29, 20, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu FC", "Player I", 27, 3, Position.DEFENSE));
        
        footballTeam.setStartingEleven(startingEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Execute method under test
        Player result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify expected output: "Player G, Number: 20" (first based on insertion order)
        assertNotNull("Result should not be null", result);
        assertEquals("Player G", result.getName());
        assertEquals(20, result.getNumber());
        assertEquals(Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // Test Case 4: No midfielders available
        // SetUp: Create football team and add players
        List<Player> startingEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        
        // Add players to starting eleven (no midfielders)
        startingEleven.add(new Player("Turgutlu FC", "Player J", 25, 9, Position.FORWARD));
        startingEleven.add(new Player("Turgutlu FC", "Player K", 30, 1, Position.DEFENSE)); // Note: Position should be DEFENSE since there's no GOALKEEPER in enum
        startingEleven.add(new Player("Turgutlu FC", "Player L", 23, 4, Position.DEFENSE));
        
        footballTeam.setStartingEleven(startingEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Execute method under test
        Player result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify expected output: null (no midfielders available)
        assertNull("Result should be null when no midfielders available", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // Test Case 5: All positions included
        // SetUp: Create football team and add players
        List<Player> startingEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        
        // Add players to starting eleven
        startingEleven.add(new Player("Turgutlu FC", "Player M", 24, 19, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu FC", "Player N", 26, 25, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu FC", "Player O", 27, 5, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu FC", "Player P", 29, 30, Position.FORWARD));
        
        footballTeam.setStartingEleven(startingEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Execute method under test
        Player result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify expected output: "Player N, Number: 25"
        assertNotNull("Result should not be null", result);
        assertEquals("Player N", result.getName());
        assertEquals(25, result.getNumber());
        assertEquals(Position.MIDFIELD, result.getPosition());
    }
}