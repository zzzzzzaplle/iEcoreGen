import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam("Turgutlu FC");
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumber_BasicCase() {
        // SetUp: Create team and add players
        FootballPlayer playerA = new FootballPlayer("Turgutlu FC", "Player A", 24, 15, Position.MIDFIELD);
        FootballPlayer playerB = new FootballPlayer("Turgutlu FC", "Player B", 26, 22, Position.MIDFIELD);
        FootballPlayer playerC = new FootballPlayer("Turgutlu FC", "Player C", 23, 18, Position.DEFENSE);
        
        team.addPlayer(playerA);
        team.addPlayer(playerB);
        team.addPlayer(playerC);
        
        // Execute: Find midfielder with highest number
        FootballPlayer result = team.findMidfielderWithHighestNumber();
        
        // Verify: Expected Output: "Player B, Number: 22"
        assertNotNull("Should find a midfielder", result);
        assertEquals("Player B", result.getName());
        assertEquals(22, result.getNumber());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // SetUp: Create team and add players
        FootballPlayer playerD = new FootballPlayer("Turgutlu FC", "Player D", 28, 12, Position.MIDFIELD);
        FootballPlayer playerE = new FootballPlayer("Turgutlu FC", "Player E", 30, 10, Position.FORWARD);
        FootballPlayer playerF = new FootballPlayer("Turgutlu FC", "Player F", 22, 2, Position.DEFENSE);
        
        team.addPlayer(playerD);
        team.addPlayer(playerE);
        team.addPlayer(playerF);
        
        // Execute: Find midfielder with highest number
        FootballPlayer result = team.findMidfielderWithHighestNumber();
        
        // Verify: Expected Output: "Player D, Number: 12"
        assertNotNull("Should find a midfielder", result);
        assertEquals("Player D", result.getName());
        assertEquals(12, result.getNumber());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // SetUp: Create team and add players
        FootballPlayer playerG = new FootballPlayer("Turgutlu FC", "Player G", 21, 20, Position.MIDFIELD);
        FootballPlayer playerH = new FootballPlayer("Turgutlu FC", "Player H", 29, 20, Position.MIDFIELD);
        FootballPlayer playerI = new FootballPlayer("Turgutlu FC", "Player I", 27, 3, Position.DEFENSE);
        
        team.addPlayer(playerG);
        team.addPlayer(playerH);
        team.addPlayer(playerI);
        
        // Execute: Find midfielder with highest number
        FootballPlayer result = team.findMidfielderWithHighestNumber();
        
        // Verify: Expected Output: "Player G, Number: 20" (Assuming it returns the first based on insertion order)
        assertNotNull("Should find a midfielder", result);
        assertEquals("Player G", result.getName()); // First added midfielder with highest number
        assertEquals(20, result.getNumber());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // SetUp: Create team and add players
        FootballPlayer playerJ = new FootballPlayer("Turgutlu FC", "Player J", 25, 9, Position.FORWARD);
        FootballPlayer playerK = new FootballPlayer("Turgutlu FC", "Player K", 30, 1, Position.GOALKEEPER);
        FootballPlayer playerL = new FootballPlayer("Turgutlu FC", "Player L", 23, 4, Position.DEFENSE);
        
        team.addPlayer(playerJ);
        team.addPlayer(playerK);
        team.addPlayer(playerL);
        
        // Execute: Find midfielder with highest number
        FootballPlayer result = team.findMidfielderWithHighestNumber();
        
        // Verify: Expected Output: null (no midfielders available)
        assertNull("Should return null when no midfielders available", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // SetUp: Create team and add players
        FootballPlayer playerM = new FootballPlayer("Turgutlu FC", "Player M", 24, 19, Position.MIDFIELD);
        FootballPlayer playerN = new FootballPlayer("Turgutlu FC", "Player N", 26, 25, Position.MIDFIELD);
        FootballPlayer playerO = new FootballPlayer("Turgutlu FC", "Player O", 27, 5, Position.DEFENSE);
        FootballPlayer playerP = new FootballPlayer("Turgutlu FC", "Player P", 29, 30, Position.FORWARD);
        
        team.addPlayer(playerM);
        team.addPlayer(playerN);
        team.addPlayer(playerO);
        team.addPlayer(playerP);
        
        // Execute: Find midfielder with highest number
        FootballPlayer result = team.findMidfielderWithHighestNumber();
        
        // Verify: Expected Output: "Player N, Number: 25"
        assertNotNull("Should find a midfielder", result);
        assertEquals("Player N", result.getName());
        assertEquals(25, result.getNumber());
    }
}