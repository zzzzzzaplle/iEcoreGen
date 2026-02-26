import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Team team;
    
    @Before
    public void setUp() {
        // Create a new team before each test
        team = new Team();
        team.setName("Turgutlu FC");
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumber_BasicCase() {
        // Set up players for basic case with multiple midfielders
        Midfielder playerA = new Midfielder();
        playerA.setName("Player A");
        playerA.setAge(24);
        playerA.setNumber(15);
        
        Midfielder playerB = new Midfielder();
        playerB.setName("Player B");
        playerB.setAge(26);
        playerB.setNumber(22);
        
        FootballPlayer playerC = new Goalkeeper(); // Defense position
        playerC.setName("Player C");
        playerC.setAge(23);
        playerC.setNumber(18);
        
        // Add players to team (all as starters for simplicity)
        team.addPlayer(playerA, true);
        team.addPlayer(playerB, true);
        team.addPlayer(playerC, true);
        
        // Find midfielder with highest number
        Midfielder result = team.findMidfielderWithHighestNumber();
        
        // Verify the result
        assertNotNull("Should find a midfielder", result);
        assertEquals("Player B", result.getName());
        assertEquals(22, result.getNumber());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // Set up players with only one midfielder
        Midfielder playerD = new Midfielder();
        playerD.setName("Player D");
        playerD.setAge(28);
        playerD.setNumber(12);
        
        Striker playerE = new Striker(); // Forward position
        playerE.setName("Player E");
        playerE.setAge(30);
        playerE.setNumber(10);
        
        FootballPlayer playerF = new Goalkeeper(); // Defense position
        playerF.setName("Player F");
        playerF.setAge(22);
        playerF.setNumber(2);
        
        // Add players to team
        team.addPlayer(playerD, true);
        team.addPlayer(playerE, true);
        team.addPlayer(playerF, true);
        
        // Find midfielder with highest number
        Midfielder result = team.findMidfielderWithHighestNumber();
        
        // Verify the result
        assertNotNull("Should find the only midfielder", result);
        assertEquals("Player D", result.getName());
        assertEquals(12, result.getNumber());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // Set up players with midfielders having same numbers
        Midfielder playerG = new Midfielder();
        playerG.setName("Player G");
        playerG.setAge(21);
        playerG.setNumber(20);
        
        Midfielder playerH = new Midfielder();
        playerH.setName("Player H");
        playerH.setAge(29);
        playerH.setNumber(20); // Same number as Player G
        
        FootballPlayer playerI = new Goalkeeper(); // Defense position
        playerI.setName("Player I");
        playerI.setAge(27);
        playerI.setNumber(3);
        
        // Add players to team (Player G added first)
        team.addPlayer(playerG, true);
        team.addPlayer(playerH, true);
        team.addPlayer(playerI, true);
        
        // Find midfielder with highest number
        Midfielder result = team.findMidfielderWithHighestNumber();
        
        // Verify the result - should return first midfielder with highest number (Player G)
        assertNotNull("Should find a midfielder", result);
        assertEquals("Player G", result.getName());
        assertEquals(20, result.getNumber());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // Set up players with no midfielders
        Striker playerJ = new Striker(); // Forward position
        playerJ.setName("Player J");
        playerJ.setAge(25);
        playerJ.setNumber(9);
        
        Goalkeeper playerK = new Goalkeeper(); // Goalkeeper position
        playerK.setName("Player K");
        playerK.setAge(30);
        playerK.setNumber(1);
        
        FootballPlayer playerL = new Goalkeeper(); // Defense position
        playerL.setName("Player L");
        playerL.setAge(23);
        playerL.setNumber(4);
        
        // Add players to team
        team.addPlayer(playerJ, true);
        team.addPlayer(playerK, true);
        team.addPlayer(playerL, true);
        
        // Find midfielder with highest number
        Midfielder result = team.findMidfielderWithHighestNumber();
        
        // Verify the result - should be null when no midfielders available
        assertNull("Should return null when no midfielders available", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // Set up players with all positions including multiple midfielders
        Midfielder playerM = new Midfielder();
        playerM.setName("Player M");
        playerM.setAge(24);
        playerM.setNumber(19);
        
        Midfielder playerN = new Midfielder();
        playerN.setName("Player N");
        playerN.setAge(26);
        playerN.setNumber(25); // Highest number among midfielders
        
        FootballPlayer playerO = new Goalkeeper(); // Defense position
        playerO.setName("Player O");
        playerO.setAge(27);
        playerO.setNumber(5);
        
        Striker playerP = new Striker(); // Forward position
        playerP.setName("Player P");
        playerP.setAge(29);
        playerP.setNumber(30); // Highest number overall but not a midfielder
        
        // Add players to team
        team.addPlayer(playerM, true);
        team.addPlayer(playerN, true);
        team.addPlayer(playerO, true);
        team.addPlayer(playerP, true);
        
        // Find midfielder with highest number
        Midfielder result = team.findMidfielderWithHighestNumber();
        
        // Verify the result - should return midfielder with highest number (Player N)
        assertNotNull("Should find a midfielder", result);
        assertEquals("Player N", result.getName());
        assertEquals(25, result.getNumber());
    }
}