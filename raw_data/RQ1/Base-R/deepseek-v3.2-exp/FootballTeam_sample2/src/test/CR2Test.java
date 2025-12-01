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
        team.setName("Turgutlu FC");
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumber_BasicCase() {
        // SetUp: Create players and add to team
        FootballPlayer playerA = new FootballPlayer();
        playerA.setName("Player A");
        playerA.setAge(24);
        playerA.setNumber(15);
        playerA.setPosition(Position.MIDFIELD);
        
        FootballPlayer playerB = new FootballPlayer();
        playerB.setName("Player B");
        playerB.setAge(26);
        playerB.setNumber(22);
        playerB.setPosition(Position.MIDFIELD);
        
        FootballPlayer playerC = new FootballPlayer();
        playerC.setName("Player C");
        playerC.setAge(23);
        playerC.setNumber(18);
        playerC.setPosition(Position.DEFENSE);
        
        List<FootballPlayer> players = new ArrayList<>();
        players.add(playerA);
        players.add(playerB);
        players.add(playerC);
        team.setStartingEleven(players);
        
        // Execute: Find midfielder with highest number
        FootballPlayer result = team.findMidfielderWithHighestNumber();
        
        // Verify: Player B should be returned with number 22
        assertNotNull("Result should not be null", result);
        assertEquals("Player B", result.getName());
        assertEquals(22, result.getNumber());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // SetUp: Create players and add to team
        FootballPlayer playerD = new FootballPlayer();
        playerD.setName("Player D");
        playerD.setAge(28);
        playerD.setNumber(12);
        playerD.setPosition(Position.MIDFIELD);
        
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
        
        List<FootballPlayer> players = new ArrayList<>();
        players.add(playerD);
        players.add(playerE);
        players.add(playerF);
        team.setStartingEleven(players);
        
        // Execute: Find midfielder with highest number
        FootballPlayer result = team.findMidfielderWithHighestNumber();
        
        // Verify: Player D should be returned with number 12
        assertNotNull("Result should not be null", result);
        assertEquals("Player D", result.getName());
        assertEquals(12, result.getNumber());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // SetUp: Create players and add to team
        FootballPlayer playerG = new FootballPlayer();
        playerG.setName("Player G");
        playerG.setAge(21);
        playerG.setNumber(20);
        playerG.setPosition(Position.MIDFIELD);
        
        FootballPlayer playerH = new FootballPlayer();
        playerH.setName("Player H");
        playerH.setAge(29);
        playerH.setNumber(20);
        playerH.setPosition(Position.MIDFIELD);
        
        FootballPlayer playerI = new FootballPlayer();
        playerI.setName("Player I");
        playerI.setAge(27);
        playerI.setNumber(3);
        playerI.setPosition(Position.DEFENSE);
        
        List<FootballPlayer> players = new ArrayList<>();
        players.add(playerG);
        players.add(playerH);
        players.add(playerI);
        team.setStartingEleven(players);
        
        // Execute: Find midfielder with highest number
        FootballPlayer result = team.findMidfielderWithHighestNumber();
        
        // Verify: First midfielder with number 20 (Player G) should be returned
        assertNotNull("Result should not be null", result);
        assertEquals("Player G", result.getName());
        assertEquals(20, result.getNumber());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // SetUp: Create players and add to team (no midfielders)
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
        
        List<FootballPlayer> players = new ArrayList<>();
        players.add(playerJ);
        players.add(playerK);
        players.add(playerL);
        team.setStartingEleven(players);
        
        // Execute: Find midfielder with highest number
        FootballPlayer result = team.findMidfielderWithHighestNumber();
        
        // Verify: Should return null when no midfielders available
        assertNull("Result should be null when no midfielders available", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // SetUp: Create players and add to team
        FootballPlayer playerM = new FootballPlayer();
        playerM.setName("Player M");
        playerM.setAge(24);
        playerM.setNumber(19);
        playerM.setPosition(Position.MIDFIELD);
        
        FootballPlayer playerN = new FootballPlayer();
        playerN.setName("Player N");
        playerN.setAge(26);
        playerN.setNumber(25);
        playerN.setPosition(Position.MIDFIELD);
        
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
        
        List<FootballPlayer> players = new ArrayList<>();
        players.add(playerM);
        players.add(playerN);
        players.add(playerO);
        players.add(playerP);
        team.setStartingEleven(players);
        
        // Execute: Find midfielder with highest number
        FootballPlayer result = team.findMidfielderWithHighestNumber();
        
        // Verify: Player N should be returned with number 25
        assertNotNull("Result should not be null", result);
        assertEquals("Player N", result.getName());
        assertEquals(25, result.getNumber());
    }
}