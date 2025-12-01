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
        // SetUp: Create a football team and add players
        List<Player> players = new ArrayList<>();
        
        Player playerA = new Player();
        playerA.setName("Player A");
        playerA.setAge(24);
        playerA.setNumber(15);
        playerA.setPosition(Position.MIDFIELD);
        players.add(playerA);
        
        Player playerB = new Player();
        playerB.setName("Player B");
        playerB.setAge(26);
        playerB.setNumber(22);
        playerB.setPosition(Position.MIDFIELD);
        players.add(playerB);
        
        Player playerC = new Player();
        playerC.setName("Player C");
        playerC.setAge(23);
        playerC.setNumber(18);
        playerC.setPosition(Position.DEFENSE);
        players.add(playerC);
        
        team.setPlayers(players);
        
        // Execute: Find midfielder with highest number
        Player result = team.findMidfielderWithHighestNumber();
        
        // Verify: Expected output is Player B with number 22
        assertNotNull("Result should not be null", result);
        assertEquals("Player B", result.getName());
        assertEquals(22, result.getNumber());
        assertEquals(Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // SetUp: Create a football team and add players
        List<Player> players = new ArrayList<>();
        
        Player playerD = new Player();
        playerD.setName("Player D");
        playerD.setAge(28);
        playerD.setNumber(12);
        playerD.setPosition(Position.MIDFIELD);
        players.add(playerD);
        
        Player playerE = new Player();
        playerE.setName("Player E");
        playerE.setAge(30);
        playerE.setNumber(10);
        playerE.setPosition(Position.FORWARD);
        players.add(playerE);
        
        Player playerF = new Player();
        playerF.setName("Player F");
        playerF.setAge(22);
        playerF.setNumber(2);
        playerF.setPosition(Position.DEFENSE);
        players.add(playerF);
        
        team.setPlayers(players);
        
        // Execute: Find midfielder with highest number
        Player result = team.findMidfielderWithHighestNumber();
        
        // Verify: Expected output is Player D with number 12
        assertNotNull("Result should not be null", result);
        assertEquals("Player D", result.getName());
        assertEquals(12, result.getNumber());
        assertEquals(Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // SetUp: Create a football team and add players
        List<Player> players = new ArrayList<>();
        
        Player playerG = new Player();
        playerG.setName("Player G");
        playerG.setAge(21);
        playerG.setNumber(20);
        playerG.setPosition(Position.MIDFIELD);
        players.add(playerG);
        
        Player playerH = new Player();
        playerH.setName("Player H");
        playerH.setAge(29);
        playerH.setNumber(20);
        playerH.setPosition(Position.MIDFIELD);
        players.add(playerH);
        
        Player playerI = new Player();
        playerI.setName("Player I");
        playerI.setAge(27);
        playerI.setNumber(3);
        playerI.setPosition(Position.DEFENSE);
        players.add(playerI);
        
        team.setPlayers(players);
        
        // Execute: Find midfielder with highest number
        Player result = team.findMidfielderWithHighestNumber();
        
        // Verify: Expected output is Player G with number 20 (first based on insertion order)
        assertNotNull("Result should not be null", result);
        assertEquals("Player G", result.getName());
        assertEquals(20, result.getNumber());
        assertEquals(Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // SetUp: Create a football team and add players with no midfielders
        List<Player> players = new ArrayList<>();
        
        Player playerJ = new Player();
        playerJ.setName("Player J");
        playerJ.setAge(25);
        playerJ.setNumber(9);
        playerJ.setPosition(Position.FORWARD);
        players.add(playerJ);
        
        Player playerK = new Player();
        playerK.setName("Player K");
        playerK.setAge(30);
        playerK.setNumber(1);
        playerK.setPosition(Position.GOALKEEPER);
        players.add(playerK);
        
        Player playerL = new Player();
        playerL.setName("Player L");
        playerL.setAge(23);
        playerL.setNumber(4);
        playerL.setPosition(Position.DEFENSE);
        players.add(playerL);
        
        team.setPlayers(players);
        
        // Execute: Find midfielder with highest number
        Player result = team.findMidfielderWithHighestNumber();
        
        // Verify: Expected output is null (no midfielders available)
        assertNull("Result should be null when no midfielders available", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // SetUp: Create a football team and add players with all positions
        List<Player> players = new ArrayList<>();
        
        Player playerM = new Player();
        playerM.setName("Player M");
        playerM.setAge(24);
        playerM.setNumber(19);
        playerM.setPosition(Position.MIDFIELD);
        players.add(playerM);
        
        Player playerN = new Player();
        playerN.setName("Player N");
        playerN.setAge(26);
        playerN.setNumber(25);
        playerN.setPosition(Position.MIDFIELD);
        players.add(playerN);
        
        Player playerO = new Player();
        playerO.setName("Player O");
        playerO.setAge(27);
        playerO.setNumber(5);
        playerO.setPosition(Position.DEFENSE);
        players.add(playerO);
        
        Player playerP = new Player();
        playerP.setName("Player P");
        playerP.setAge(29);
        playerP.setNumber(30);
        playerP.setPosition(Position.FORWARD);
        players.add(playerP);
        
        team.setPlayers(players);
        
        // Execute: Find midfielder with highest number
        Player result = team.findMidfielderWithHighestNumber();
        
        // Verify: Expected output is Player N with number 25
        assertNotNull("Result should not be null", result);
        assertEquals("Player N", result.getName());
        assertEquals(25, result.getNumber());
        assertEquals(Position.MIDFIELD, result.getPosition());
    }
}