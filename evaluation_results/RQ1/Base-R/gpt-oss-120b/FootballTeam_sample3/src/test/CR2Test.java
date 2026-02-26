import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Team team;
    
    @Before
    public void setUp() {
        // Reset team before each test
        team = new Team("Turgutlu FC");
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumber_BasicCase() {
        // Set up: Create players and add to team
        Midfielder playerA = new Midfielder("Turgutlu FC", "Player A", 24, 15);
        Midfielder playerB = new Midfielder("Turgutlu FC", "Player B", 26, 22);
        FootballPlayer playerC = new FootballPlayer("Turgutlu FC", "Player C", 23, 18, Position.DEFENSE);
        
        team.addPlayer(playerA);
        team.addPlayer(playerB);
        team.addPlayer(playerC);
        
        // Execute: Find midfielder with highest number
        Midfielder result = team.findMidfielderWithHighestNumber();
        
        // Verify: Player B should be returned with number 22
        assertNotNull("Result should not be null", result);
        assertEquals("Player B", result.getName());
        assertEquals(22, result.getNumber());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // Set up: Create players and add to team
        Midfielder playerD = new Midfielder("Turgutlu FC", "Player D", 28, 12);
        Forward playerE = new Forward("Turgutlu FC", "Player E", 30, 10);
        FootballPlayer playerF = new FootballPlayer("Turgutlu FC", "Player F", 22, 2, Position.DEFENSE);
        
        team.addPlayer(playerD);
        team.addPlayer(playerE);
        team.addPlayer(playerF);
        
        // Execute: Find midfielder with highest number
        Midfielder result = team.findMidfielderWithHighestNumber();
        
        // Verify: Player D should be returned with number 12
        assertNotNull("Result should not be null", result);
        assertEquals("Player D", result.getName());
        assertEquals(12, result.getNumber());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // Set up: Create players and add to team
        Midfielder playerG = new Midfielder("Turgutlu FC", "Player G", 21, 20);
        Midfielder playerH = new Midfielder("Turgutlu FC", "Player H", 29, 20);
        FootballPlayer playerI = new FootballPlayer("Turgutlu FC", "Player I", 27, 3, Position.DEFENSE);
        
        team.addPlayer(playerG);
        team.addPlayer(playerH);
        team.addPlayer(playerI);
        
        // Execute: Find midfielder with highest number
        Midfielder result = team.findMidfielderWithHighestNumber();
        
        // Verify: First inserted midfielder with number 20 (Player G) should be returned
        assertNotNull("Result should not be null", result);
        assertEquals("Player G", result.getName());
        assertEquals(20, result.getNumber());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // Set up: Create players and add to team (no midfielders)
        Forward playerJ = new Forward("Turgutlu FC", "Player J", 25, 9);
        Goalkeeper playerK = new Goalkeeper("Turgutlu FC", "Player K", 30, 1);
        FootballPlayer playerL = new FootballPlayer("Turgutlu FC", "Player L", 23, 4, Position.DEFENSE);
        
        team.addPlayer(playerJ);
        team.addPlayer(playerK);
        team.addPlayer(playerL);
        
        // Execute: Find midfielder with highest number
        Midfielder result = team.findMidfielderWithHighestNumber();
        
        // Verify: Should return null when no midfielders available
        assertNull("Result should be null when no midfielders available", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // Set up: Create players and add to team
        Midfielder playerM = new Midfielder("Turgutlu FC", "Player M", 24, 19);
        Midfielder playerN = new Midfielder("Turgutlu FC", "Player N", 26, 25);
        FootballPlayer playerO = new FootballPlayer("Turgutlu FC", "Player O", 27, 5, Position.DEFENSE);
        Forward playerP = new Forward("Turgutlu FC", "Player P", 29, 30);
        
        team.addPlayer(playerM);
        team.addPlayer(playerN);
        team.addPlayer(playerO);
        team.addPlayer(playerP);
        
        // Execute: Find midfielder with highest number
        Midfielder result = team.findMidfielderWithHighestNumber();
        
        // Verify: Player N should be returned with number 25
        assertNotNull("Result should not be null", result);
        assertEquals("Player N", result.getName());
        assertEquals(25, result.getNumber());
    }
}