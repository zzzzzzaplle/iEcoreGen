import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumber_BasicCase() {
        // SetUp: Create a football team "Turgutlu FC" and add players
        team.setStartingEleven(new ArrayList<>());
        
        FootballPlayer playerA = new FootballPlayer("Turgutlu FC", "Player A", 24, 15, Position.MIDFIELD, Duty.MIDFIELDER);
        FootballPlayer playerB = new FootballPlayer("Turgutlu FC", "Player B", 26, 22, Position.MIDFIELD, Duty.MIDFIELDER);
        FootballPlayer playerC = new FootballPlayer("Turgutlu FC", "Player C", 23, 18, Position.DEFENSE, null);
        
        team.getStartingEleven().add(playerA);
        team.getStartingEleven().add(playerB);
        team.getStartingEleven().add(playerC);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = team.identifyMidfielderWithHighestNumber();
        
        // Verify: Expected Output: "Player B, Number: 22"
        assertNotNull(result);
        assertEquals("Player B", result.getName());
        assertEquals(22, result.getNumber());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // SetUp: Create a football team "Turgutlu FC" and add players
        team.setStartingEleven(new ArrayList<>());
        
        FootballPlayer playerD = new FootballPlayer("Turgutlu FC", "Player D", 28, 12, Position.MIDFIELD, Duty.MIDFIELDER);
        FootballPlayer playerE = new FootballPlayer("Turgutlu FC", "Player E", 30, 10, Position.FORWARD, Duty.STRIKER);
        FootballPlayer playerF = new FootballPlayer("Turgutlu FC", "Player F", 22, 2, Position.DEFENSE, null);
        
        team.getStartingEleven().add(playerD);
        team.getStartingEleven().add(playerE);
        team.getStartingEleven().add(playerF);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = team.identifyMidfielderWithHighestNumber();
        
        // Verify: Expected Output: "Player D, Number: 12"
        assertNotNull(result);
        assertEquals("Player D", result.getName());
        assertEquals(12, result.getNumber());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // SetUp: Create a football team "Turgutlu FC" and add players
        team.setStartingEleven(new ArrayList<>());
        
        FootballPlayer playerG = new FootballPlayer("Turgutlu FC", "Player G", 21, 20, Position.MIDFIELD, Duty.MIDFIELDER);
        FootballPlayer playerH = new FootballPlayer("Turgutlu FC", "Player H", 29, 20, Position.MIDFIELD, Duty.MIDFIELDER);
        FootballPlayer playerI = new FootballPlayer("Turgutlu FC", "Player I", 27, 3, Position.DEFENSE, null);
        
        team.getStartingEleven().add(playerG);
        team.getStartingEleven().add(playerH);
        team.getStartingEleven().add(playerI);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = team.identifyMidfielderWithHighestNumber();
        
        // Verify: Expected Output: "Player G, Number: 20" (Assuming it returns the first based on insertion order)
        assertNotNull(result);
        assertEquals("Player G", result.getName());
        assertEquals(20, result.getNumber());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // SetUp: Create a football team "Turgutlu FC" and add players
        team.setStartingEleven(new ArrayList<>());
        
        FootballPlayer playerJ = new FootballPlayer("Turgutlu FC", "Player J", 25, 9, Position.FORWARD, Duty.STRIKER);
        FootballPlayer playerK = new FootballPlayer("Turgutlu FC", "Player K", 30, 1, null, Duty.GOALKEEPER);
        FootballPlayer playerL = new FootballPlayer("Turgutlu FC", "Player L", 23, 4, Position.DEFENSE, null);
        
        team.getStartingEleven().add(playerJ);
        team.getStartingEleven().add(playerK);
        team.getStartingEleven().add(playerL);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = team.identifyMidfielderWithHighestNumber();
        
        // Verify: Expected Output: null (no midfielders available)
        assertNull(result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // SetUp: Create a football team "Turgutlu FC" and add players
        team.setStartingEleven(new ArrayList<>());
        team.setSpareTeam(new ArrayList<>());
        
        FootballPlayer playerM = new FootballPlayer("Turgutlu FC", "Player M", 24, 19, Position.MIDFIELD, Duty.MIDFIELDER);
        FootballPlayer playerN = new FootballPlayer("Turgutlu FC", "Player N", 26, 25, Position.MIDFIELD, Duty.MIDFIELDER);
        FootballPlayer playerO = new FootballPlayer("Turgutlu FC", "Player O", 27, 5, Position.DEFENSE, null);
        FootballPlayer playerP = new FootballPlayer("Turgutlu FC", "Player P", 29, 30, Position.FORWARD, Duty.STRIKER);
        
        // Add some players to starting eleven and some to spare team to test both lists
        team.getStartingEleven().add(playerM);
        team.getSpareTeam().add(playerN);
        team.getStartingEleven().add(playerO);
        team.getStartingEleven().add(playerP);
        
        // Execute: Identify midfielder with highest number (should search both starting eleven and spare team)
        FootballPlayer result = team.identifyMidfielderWithHighestNumber();
        
        // Verify: Expected Output: "Player N, Number: 25"
        assertNotNull(result);
        assertEquals("Player N", result.getName());
        assertEquals(25, result.getNumber());
    }
}