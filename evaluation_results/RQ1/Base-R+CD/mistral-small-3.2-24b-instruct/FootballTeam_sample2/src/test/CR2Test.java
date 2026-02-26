import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumber_BasicCase() {
        // SetUp: Create players as specified
        Player playerA = new Player();
        playerA.setName("Player A");
        playerA.setAge(24);
        playerA.setNumber(15);
        playerA.setPosition(Position.MIDFIELD);
        
        Player playerB = new Player();
        playerB.setName("Player B");
        playerB.setAge(26);
        playerB.setNumber(22);
        playerB.setPosition(Position.MIDFIELD);
        
        Player playerC = new Player();
        playerC.setName("Player C");
        playerC.setAge(23);
        playerC.setNumber(18);
        playerC.setPosition(Position.DEFENSE);
        
        // Add players to team
        team.getPlayers().add(playerA);
        team.getPlayers().add(playerB);
        team.getPlayers().add(playerC);
        
        // Execute method under test
        Player result = team.findMidfielderWithHighestNumber();
        
        // Verify expected output: "Player B, Number: 22"
        assertNotNull("Result should not be null", result);
        assertEquals("Player B", result.getName());
        assertEquals(22, result.getNumber());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // SetUp: Create players as specified
        Player playerD = new Player();
        playerD.setName("Player D");
        playerD.setAge(28);
        playerD.setNumber(12);
        playerD.setPosition(Position.MIDFIELD);
        
        Player playerE = new Player();
        playerE.setName("Player E");
        playerE.setAge(30);
        playerE.setNumber(10);
        playerE.setPosition(Position.FORWARD);
        
        Player playerF = new Player();
        playerF.setName("Player F");
        playerF.setAge(22);
        playerF.setNumber(2);
        playerF.setPosition(Position.DEFENSE);
        
        // Add players to team
        team.getPlayers().add(playerD);
        team.getPlayers().add(playerE);
        team.getPlayers().add(playerF);
        
        // Execute method under test
        Player result = team.findMidfielderWithHighestNumber();
        
        // Verify expected output: "Player D, Number: 12"
        assertNotNull("Result should not be null", result);
        assertEquals("Player D", result.getName());
        assertEquals(12, result.getNumber());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // SetUp: Create players as specified
        Player playerG = new Player();
        playerG.setName("Player G");
        playerG.setAge(21);
        playerG.setNumber(20);
        playerG.setPosition(Position.MIDFIELD);
        
        Player playerH = new Player();
        playerH.setName("Player H");
        playerH.setAge(29);
        playerH.setNumber(20);
        playerH.setPosition(Position.MIDFIELD);
        
        Player playerI = new Player();
        playerI.setName("Player I");
        playerI.setAge(27);
        playerI.setNumber(3);
        playerI.setPosition(Position.DEFENSE);
        
        // Add players to team (in specified order)
        team.getPlayers().add(playerG);
        team.getPlayers().add(playerH);
        team.getPlayers().add(playerI);
        
        // Execute method under test
        Player result = team.findMidfielderWithHighestNumber();
        
        // Verify expected output: "Player G, Number: 20" (first based on insertion order)
        assertNotNull("Result should not be null", result);
        assertEquals("Player G", result.getName());
        assertEquals(20, result.getNumber());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // SetUp: Create players as specified (no midfielders)
        Player playerJ = new Player();
        playerJ.setName("Player J");
        playerJ.setAge(25);
        playerJ.setNumber(9);
        playerJ.setPosition(Position.FORWARD);
        
        Player playerK = new Player();
        playerK.setName("Player K");
        playerK.setAge(30);
        playerK.setNumber(1);
        playerK.setPosition(Position.GOALKEEPER);
        
        Player playerL = new Player();
        playerL.setName("Player L");
        playerL.setAge(23);
        playerL.setNumber(4);
        playerL.setPosition(Position.DEFENSE);
        
        // Add players to team
        team.getPlayers().add(playerJ);
        team.getPlayers().add(playerK);
        team.getPlayers().add(playerL);
        
        // Execute method under test
        Player result = team.findMidfielderWithHighestNumber();
        
        // Verify expected output: null (no midfielders available)
        assertNull("Result should be null when no midfielders available", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // SetUp: Create players as specified with all positions
        Player playerM = new Player();
        playerM.setName("Player M");
        playerM.setAge(24);
        playerM.setNumber(19);
        playerM.setPosition(Position.MIDFIELD);
        
        Player playerN = new Player();
        playerN.setName("Player N");
        playerN.setAge(26);
        playerN.setNumber(25);
        playerN.setPosition(Position.MIDFIELD);
        
        Player playerO = new Player();
        playerO.setName("Player O");
        playerO.setAge(27);
        playerO.setNumber(5);
        playerO.setPosition(Position.DEFENSE);
        
        Player playerP = new Player();
        playerP.setName("Player P");
        playerP.setAge(29);
        playerP.setNumber(30);
        playerP.setPosition(Position.FORWARD);
        
        // Add players to team
        team.getPlayers().add(playerM);
        team.getPlayers().add(playerN);
        team.getPlayers().add(playerO);
        team.getPlayers().add(playerP);
        
        // Execute method under test
        Player result = team.findMidfielderWithHighestNumber();
        
        // Verify expected output: "Player N, Number: 25"
        assertNotNull("Result should not be null", result);
        assertEquals("Player N", result.getName());
        assertEquals(25, result.getNumber());
    }
}