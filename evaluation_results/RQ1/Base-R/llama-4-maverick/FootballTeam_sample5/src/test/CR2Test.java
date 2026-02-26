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
        // Set up test data
        List<Player> startingEleven = new ArrayList<>();
        startingEleven.add(new Player("Turgutlu FC", "Player A", 24, 15, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu FC", "Player B", 26, 22, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu FC", "Player C", 23, 18, Position.DEFENSE));
        
        footballTeam.setStartingEleven(startingEleven);
        
        // Execute method under test
        Player result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify results
        assertNotNull("Result should not be null", result);
        assertEquals("Player name should match", "Player B", result.getName());
        assertEquals("Player number should match", 22, result.getNumber());
        assertEquals("Player position should be midfield", Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // Set up test data
        List<Player> startingEleven = new ArrayList<>();
        startingEleven.add(new Player("Turgutlu FC", "Player D", 28, 12, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu FC", "Player E", 30, 10, Position.FORWARD));
        startingEleven.add(new Player("Turgutlu FC", "Player F", 22, 2, Position.DEFENSE));
        
        footballTeam.setStartingEleven(startingEleven);
        
        // Execute method under test
        Player result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify results
        assertNotNull("Result should not be null", result);
        assertEquals("Player name should match", "Player D", result.getName());
        assertEquals("Player number should match", 12, result.getNumber());
        assertEquals("Player position should be midfield", Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // Set up test data
        List<Player> startingEleven = new ArrayList<>();
        startingEleven.add(new Player("Turgutlu FC", "Player G", 21, 20, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu FC", "Player H", 29, 20, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu FC", "Player I", 27, 3, Position.DEFENSE));
        
        footballTeam.setStartingEleven(startingEleven);
        
        // Execute method under test
        Player result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify results - should return the first player with highest number based on insertion order
        assertNotNull("Result should not be null", result);
        assertEquals("Player name should match (first occurrence)", "Player G", result.getName());
        assertEquals("Player number should match", 20, result.getNumber());
        assertEquals("Player position should be midfield", Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // Set up test data
        List<Player> startingEleven = new ArrayList<>();
        startingEleven.add(new Player("Turgutlu FC", "Player J", 25, 9, Position.FORWARD));
        startingEleven.add(new Player("Turgutlu FC", "Player K", 30, 1, Position.GOALKEEPER));
        startingEleven.add(new Player("Turgutlu FC", "Player L", 23, 4, Position.DEFENSE));
        
        footballTeam.setStartingEleven(startingEleven);
        
        // Execute method under test
        Player result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify results - should return null when no midfielders found
        assertNull("Result should be null when no midfielders available", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // Set up test data
        List<Player> startingEleven = new ArrayList<>();
        startingEleven.add(new Player("Turgutlu FC", "Player M", 24, 19, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu FC", "Player N", 26, 25, Position.MIDFIELD));
        startingEleven.add(new Player("Turgutlu FC", "Player O", 27, 5, Position.DEFENSE));
        startingEleven.add(new Player("Turgutlu FC", "Player P", 29, 30, Position.FORWARD));
        
        footballTeam.setStartingEleven(startingEleven);
        
        // Execute method under test
        Player result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify results
        assertNotNull("Result should not be null", result);
        assertEquals("Player name should match", "Player N", result.getName());
        assertEquals("Player number should match", 25, result.getNumber());
        assertEquals("Player position should be midfield", Position.MIDFIELD, result.getPosition());
    }
}