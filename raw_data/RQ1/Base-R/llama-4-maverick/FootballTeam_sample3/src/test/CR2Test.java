import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        // Reset the football team before each test
        footballTeam = new FootballTeam();
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumber_BasicCase() {
        // Set up: Create a football team "Turgutlu FC"
        footballTeam.setStartingEleven(new ArrayList<>());
        footballTeam.setSpareTeam(new ArrayList<>());
        
        // Add players to starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(new FootballPlayer("Turgutlu FC", "Player A", 24, 15, Position.MIDFIELD));
        startingEleven.add(new FootballPlayer("Turgutlu FC", "Player B", 26, 22, Position.MIDFIELD));
        startingEleven.add(new FootballPlayer("Turgutlu FC", "Player C", 23, 18, Position.DEFENSE));
        footballTeam.setStartingEleven(startingEleven);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify: Expected output is Player B with number 22
        assertNotNull("Result should not be null", result);
        assertEquals("Player B", result.getName());
        assertEquals(22, result.getNumber());
        assertEquals(Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // Set up: Create a football team "Turgutlu FC"
        footballTeam.setStartingEleven(new ArrayList<>());
        footballTeam.setSpareTeam(new ArrayList<>());
        
        // Add players to starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(new FootballPlayer("Turgutlu FC", "Player D", 28, 12, Position.MIDFIELD));
        startingEleven.add(new FootballPlayer("Turgutlu FC", "Player E", 30, 10, Position.FORWARD));
        startingEleven.add(new FootballPlayer("Turgutlu FC", "Player F", 22, 2, Position.DEFENSE));
        footballTeam.setStartingEleven(startingEleven);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify: Expected output is Player D with number 12
        assertNotNull("Result should not be null", result);
        assertEquals("Player D", result.getName());
        assertEquals(12, result.getNumber());
        assertEquals(Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // Set up: Create a football team "Turgutlu FC"
        footballTeam.setStartingEleven(new ArrayList<>());
        footballTeam.setSpareTeam(new ArrayList<>());
        
        // Add players to starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(new FootballPlayer("Turgutlu FC", "Player G", 21, 20, Position.MIDFIELD));
        startingEleven.add(new FootballPlayer("Turgutlu FC", "Player H", 29, 20, Position.MIDFIELD));
        startingEleven.add(new FootballPlayer("Turgutlu FC", "Player I", 27, 3, Position.DEFENSE));
        footballTeam.setStartingEleven(startingEleven);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify: Expected output is Player G with number 20 (first based on insertion order)
        assertNotNull("Result should not be null", result);
        assertEquals("Player G", result.getName());
        assertEquals(20, result.getNumber());
        assertEquals(Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // Set up: Create a football team "Turgutlu FC"
        footballTeam.setStartingEleven(new ArrayList<>());
        footballTeam.setSpareTeam(new ArrayList<>());
        
        // Add players to starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(new FootballPlayer("Turgutlu FC", "Player J", 25, 9, Position.FORWARD));
        startingEleven.add(new FootballPlayer("Turgutlu FC", "Player K", 30, 1, Position.DEFENSE)); // Note: Position should be DEFENSE (not goalkeeper as in spec)
        startingEleven.add(new FootballPlayer("Turgutlu FC", "Player L", 23, 4, Position.DEFENSE));
        footballTeam.setStartingEleven(startingEleven);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify: Expected output is null (no midfielders available)
        assertNull("Result should be null when no midfielders are available", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // Set up: Create a football team "Turgutlu FC"
        footballTeam.setStartingEleven(new ArrayList<>());
        footballTeam.setSpareTeam(new ArrayList<>());
        
        // Add players to starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(new FootballPlayer("Turgutlu FC", "Player M", 24, 19, Position.MIDFIELD));
        startingEleven.add(new FootballPlayer("Turgutlu FC", "Player N", 26, 25, Position.MIDFIELD));
        startingEleven.add(new FootballPlayer("Turgutlu FC", "Player O", 27, 5, Position.DEFENSE));
        startingEleven.add(new FootballPlayer("Turgutlu FC", "Player P", 29, 30, Position.FORWARD));
        footballTeam.setStartingEleven(startingEleven);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify: Expected output is Player N with number 25
        assertNotNull("Result should not be null", result);
        assertEquals("Player N", result.getName());
        assertEquals(25, result.getNumber());
        assertEquals(Position.MIDFIELD, result.getPosition());
    }
}