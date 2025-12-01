import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Reset team before each test
        team = new FootballTeam("Turgutlu FC");
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumber_BasicCase() {
        // Setup: Create players and add to team
        FootballPlayer playerA = new FootballPlayer("Turgutlu FC", "Player A", 24, 15, Position.MIDFIELD, Duty.MIDFIELDER);
        FootballPlayer playerB = new FootballPlayer("Turgutlu FC", "Player B", 26, 22, Position.MIDFIELD, Duty.MIDFIELDER);
        FootballPlayer playerC = new FootballPlayer("Turgutlu FC", "Player C", 23, 18, Position.DEFENSE, Duty.MIDFIELDER);
        
        team.getStartingEleven().add(playerA);
        team.getStartingEleven().add(playerB);
        team.getStartingEleven().add(playerC);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = team.identifyMidfielderWithHighestNumber();
        
        // Verify: Player B should be returned with number 22
        assertNotNull("Result should not be null", result);
        assertEquals("Player B", result.getName());
        assertEquals(22, result.getNumber());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // Setup: Create players and add to team
        FootballPlayer playerD = new FootballPlayer("Turgutlu FC", "Player D", 28, 12, Position.MIDFIELD, Duty.MIDFIELDER);
        FootballPlayer playerE = new FootballPlayer("Turgutlu FC", "Player E", 30, 10, Position.FORWARD, Duty.STRIKER);
        FootballPlayer playerF = new FootballPlayer("Turgutlu FC", "Player F", 22, 2, Position.DEFENSE, Duty.MIDFIELDER);
        
        team.getStartingEleven().add(playerD);
        team.getStartingEleven().add(playerE);
        team.getStartingEleven().add(playerF);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = team.identifyMidfielderWithHighestNumber();
        
        // Verify: Player D should be returned with number 12
        assertNotNull("Result should not be null", result);
        assertEquals("Player D", result.getName());
        assertEquals(12, result.getNumber());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // Setup: Create players and add to team
        FootballPlayer playerG = new FootballPlayer("Turgutlu FC", "Player G", 21, 20, Position.MIDFIELD, Duty.MIDFIELDER);
        FootballPlayer playerH = new FootballPlayer("Turgutlu FC", "Player H", 29, 20, Position.MIDFIELD, Duty.MIDFIELDER);
        FootballPlayer playerI = new FootballPlayer("Turgutlu FC", "Player I", 27, 3, Position.DEFENSE, Duty.MIDFIELDER);
        
        team.getStartingEleven().add(playerG);
        team.getStartingEleven().add(playerH);
        team.getStartingEleven().add(playerI);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = team.identifyMidfielderWithHighestNumber();
        
        // Verify: First player with highest number (Player G) should be returned with number 20
        assertNotNull("Result should not be null", result);
        assertEquals("Player G", result.getName());
        assertEquals(20, result.getNumber());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // Setup: Create players with no midfielders and add to team
        FootballPlayer playerJ = new FootballPlayer("Turgutlu FC", "Player J", 25, 9, Position.FORWARD, Duty.STRIKER);
        FootballPlayer playerK = new FootballPlayer("Turgutlu FC", "Player K", 30, 1, Position.DEFENSE, Duty.GOALKEEPER);
        FootballPlayer playerL = new FootballPlayer("Turgutlu FC", "Player L", 23, 4, Position.DEFENSE, Duty.MIDFIELDER);
        
        team.getStartingEleven().add(playerJ);
        team.getStartingEleven().add(playerK);
        team.getStartingEleven().add(playerL);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = team.identifyMidfielderWithHighestNumber();
        
        // Verify: Should return null since no midfielders are available
        assertNull("Result should be null when no midfielders available", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // Setup: Create players with all positions and add to team
        FootballPlayer playerM = new FootballPlayer("Turgutlu FC", "Player M", 24, 19, Position.MIDFIELD, Duty.MIDFIELDER);
        FootballPlayer playerN = new FootballPlayer("Turgutlu FC", "Player N", 26, 25, Position.MIDFIELD, Duty.MIDFIELDER);
        FootballPlayer playerO = new FootballPlayer("Turgutlu FC", "Player O", 27, 5, Position.DEFENSE, Duty.MIDFIELDER);
        FootballPlayer playerP = new FootballPlayer("Turgutlu FC", "Player P", 29, 30, Position.FORWARD, Duty.STRIKER);
        
        team.getStartingEleven().add(playerM);
        team.getStartingEleven().add(playerN);
        team.getStartingEleven().add(playerO);
        team.getStartingEleven().add(playerP);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = team.identifyMidfielderWithHighestNumber();
        
        // Verify: Player N should be returned with number 25 (highest among midfielders)
        assertNotNull("Result should not be null", result);
        assertEquals("Player N", result.getName());
        assertEquals(25, result.getNumber());
    }
}