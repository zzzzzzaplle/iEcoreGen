import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        footballTeam = new FootballTeam();
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumber_BasicCase() {
        // Test Case 1: "Midfielder with Highest Number - Basic Case"
        // SetUp: Create a football team "Turgutlu FC" and add players
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
        
        List<Player> players = new ArrayList<>();
        players.add(playerA);
        players.add(playerB);
        players.add(playerC);
        footballTeam.setPlayers(players);
        
        // Execute the method under test
        Player result = footballTeam.findMidfielderWithHighestNumber();
        
        // Verify expected output: "Player B, Number: 22"
        assertNotNull("Result should not be null", result);
        assertEquals("Player name should be Player B", "Player B", result.getName());
        assertEquals("Player number should be 22", 22, result.getNumber());
        assertEquals("Player position should be MIDFIELD", Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // Test Case 2: "Midfielder with Highest Number - Single Midfielder"
        // SetUp: Create a football team "Turgutlu FC" and add players
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
        
        List<Player> players = new ArrayList<>();
        players.add(playerD);
        players.add(playerE);
        players.add(playerF);
        footballTeam.setPlayers(players);
        
        // Execute the method under test
        Player result = footballTeam.findMidfielderWithHighestNumber();
        
        // Verify expected output: "Player D, Number: 12"
        assertNotNull("Result should not be null", result);
        assertEquals("Player name should be Player D", "Player D", result.getName());
        assertEquals("Player number should be 12", 12, result.getNumber());
        assertEquals("Player position should be MIDFIELD", Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // Test Case 3: "Midfielder with Highest Number - Multiple Midfielders with Same Numbers"
        // SetUp: Create a football team "Turgutlu FC" and add players
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
        
        List<Player> players = new ArrayList<>();
        players.add(playerG);
        players.add(playerH);
        players.add(playerI);
        footballTeam.setPlayers(players);
        
        // Execute the method under test
        Player result = footballTeam.findMidfielderWithHighestNumber();
        
        // Verify expected output: "Player G, Number: 20" (first one based on insertion order)
        assertNotNull("Result should not be null", result);
        assertEquals("Player name should be Player G", "Player G", result.getName());
        assertEquals("Player number should be 20", 20, result.getNumber());
        assertEquals("Player position should be MIDFIELD", Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // Test Case 4: "Midfielder with Highest Number - No Midfielders Available"
        // SetUp: Create a football team "Turgutlu FC" and add players
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
        
        List<Player> players = new ArrayList<>();
        players.add(playerJ);
        players.add(playerK);
        players.add(playerL);
        footballTeam.setPlayers(players);
        
        // Execute the method under test
        Player result = footballTeam.findMidfielderWithHighestNumber();
        
        // Verify expected output: null (no midfielders available)
        assertNull("Result should be null when no midfielders available", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // Test Case 5: "Midfielder with Highest Number - All Positions Included"
        // SetUp: Create a football team "Turgutlu FC" and add players
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
        
        List<Player> players = new ArrayList<>();
        players.add(playerM);
        players.add(playerN);
        players.add(playerO);
        players.add(playerP);
        footballTeam.setPlayers(players);
        
        // Execute the method under test
        Player result = footballTeam.findMidfielderWithHighestNumber();
        
        // Verify expected output: "Player N, Number: 25"
        assertNotNull("Result should not be null", result);
        assertEquals("Player name should be Player N", "Player N", result.getName());
        assertEquals("Player number should be 25", 25, result.getNumber());
        assertEquals("Player position should be MIDFIELD", Position.MIDFIELD, result.getPosition());
    }
}