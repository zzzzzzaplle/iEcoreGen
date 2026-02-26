import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        team.setName("Turgutlu FC");
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumber_BasicCase() {
        // Setup: Create players with midfield and defense positions
        FootballPlayer playerA = new FootballPlayer();
        playerA.setName("Player A");
        playerA.setAge(24);
        playerA.setNumber(15);
        playerA.setPosition(Position.MIDFIELDER);
        
        FootballPlayer playerB = new FootballPlayer();
        playerB.setName("Player B");
        playerB.setAge(26);
        playerB.setNumber(22);
        playerB.setPosition(Position.MIDFIELDER);
        
        FootballPlayer playerC = new FootballPlayer();
        playerC.setName("Player C");
        playerC.setAge(23);
        playerC.setNumber(18);
        playerC.setPosition(Position.DEFENSE);
        
        // Add players to team
        team.addPlayer(playerA);
        team.addPlayer(playerB);
        team.addPlayer(playerC);
        
        // Execute: Find midfielder with highest number
        FootballPlayer result = team.midfielderWithHighestNumber();
        
        // Verify: Player B should be returned with number 22
        assertNotNull("Result should not be null", result);
        assertEquals("Player B", result.getName());
        assertEquals(22, result.getNumber());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // Setup: Create players with one midfielder
        FootballPlayer playerD = new FootballPlayer();
        playerD.setName("Player D");
        playerD.setAge(28);
        playerD.setNumber(12);
        playerD.setPosition(Position.MIDFIELDER);
        
        FootballPlayer playerE = new FootballPlayer();
        playerE.setName("Player E");
        playerE.setAge(30);
        playerE.setNumber(10);
        playerE.setPosition(Position.FORWARD);
        
        FootballPlayer playerF = new FootballPlayer();
        playerF.setName("Player F");
        playerF.setAge(22);
        playerF.setNumber(2);
        playerF.setPosition(Position.DEFENSE);
        
        // Add players to team
        team.addPlayer(playerD);
        team.addPlayer(playerE);
        team.addPlayer(playerF);
        
        // Execute: Find midfielder with highest number
        FootballPlayer result = team.midfielderWithHighestNumber();
        
        // Verify: Player D should be returned with number 12
        assertNotNull("Result should not be null", result);
        assertEquals("Player D", result.getName());
        assertEquals(12, result.getNumber());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // Setup: Create midfielders with same highest number
        FootballPlayer playerG = new FootballPlayer();
        playerG.setName("Player G");
        playerG.setAge(21);
        playerG.setNumber(20);
        playerG.setPosition(Position.MIDFIELDER);
        
        FootballPlayer playerH = new FootballPlayer();
        playerH.setName("Player H");
        playerH.setAge(29);
        playerH.setNumber(20);
        playerH.setPosition(Position.MIDFIELDER);
        
        FootballPlayer playerI = new FootballPlayer();
        playerI.setName("Player I");
        playerI.setAge(27);
        playerI.setNumber(3);
        playerI.setPosition(Position.DEFENSE);
        
        // Add players to team (playerG added first)
        team.addPlayer(playerG);
        team.addPlayer(playerH);
        team.addPlayer(playerI);
        
        // Execute: Find midfielder with highest number
        FootballPlayer result = team.midfielderWithHighestNumber();
        
        // Verify: Player G should be returned (first inserted with highest number)
        assertNotNull("Result should not be null", result);
        assertEquals("Player G", result.getName());
        assertEquals(20, result.getNumber());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // Setup: Create players with no midfielders
        FootballPlayer playerJ = new FootballPlayer();
        playerJ.setName("Player J");
        playerJ.setAge(25);
        playerJ.setNumber(9);
        playerJ.setPosition(Position.FORWARD);
        
        FootballPlayer playerK = new FootballPlayer();
        playerK.setName("Player K");
        playerK.setAge(30);
        playerK.setNumber(1);
        playerK.setPosition(Position.GOALKEEPER);
        
        FootballPlayer playerL = new FootballPlayer();
        playerL.setName("Player L");
        playerL.setAge(23);
        playerL.setNumber(4);
        playerL.setPosition(Position.DEFENSE);
        
        // Add players to team
        team.addPlayer(playerJ);
        team.addPlayer(playerK);
        team.addPlayer(playerL);
        
        // Execute: Find midfielder with highest number
        FootballPlayer result = team.midfielderWithHighestNumber();
        
        // Verify: Should return null when no midfielders available
        assertNull("Result should be null when no midfielders available", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // Setup: Create players with all positions including midfielders
        FootballPlayer playerM = new FootballPlayer();
        playerM.setName("Player M");
        playerM.setAge(24);
        playerM.setNumber(19);
        playerM.setPosition(Position.MIDFIELDER);
        
        FootballPlayer playerN = new FootballPlayer();
        playerN.setName("Player N");
        playerN.setAge(26);
        playerN.setNumber(25);
        playerN.setPosition(Position.MIDFIELDER);
        
        FootballPlayer playerO = new FootballPlayer();
        playerO.setName("Player O");
        playerO.setAge(27);
        playerO.setNumber(5);
        playerO.setPosition(Position.DEFENSE);
        
        FootballPlayer playerP = new FootballPlayer();
        playerP.setName("Player P");
        playerP.setAge(29);
        playerP.setNumber(30);
        playerP.setPosition(Position.FORWARD);
        
        // Add players to team
        team.addPlayer(playerM);
        team.addPlayer(playerN);
        team.addPlayer(playerO);
        team.addPlayer(playerP);
        
        // Execute: Find midfielder with highest number
        FootballPlayer result = team.midfielderWithHighestNumber();
        
        // Verify: Player N should be returned with number 25
        assertNotNull("Result should not be null", result);
        assertEquals("Player N", result.getName());
        assertEquals(25, result.getNumber());
    }
}