import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Create a new team before each test
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumber_BasicCase() {
        // SetUp: Create a football team "Turgutlu FC" and add players
        team.setTeam("Turgutlu FC");
        
        List<FootballPlayer> players = new ArrayList<>();
        
        FootballPlayer playerA = new FootballPlayer();
        playerA.setName("Player A");
        playerA.setAge(24);
        playerA.setNumber(15);
        playerA.setPosition(Position.MIDFIELD);
        players.add(playerA);
        
        FootballPlayer playerB = new FootballPlayer();
        playerB.setName("Player B");
        playerB.setAge(26);
        playerB.setNumber(22);
        playerB.setPosition(Position.MIDFIELD);
        players.add(playerB);
        
        FootballPlayer playerC = new FootballPlayer();
        playerC.setName("Player C");
        playerC.setAge(23);
        playerC.setNumber(18);
        playerC.setPosition(Position.DEFENSE);
        players.add(playerC);
        
        team.setAllPlayers(players);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = team.identifyMidfielderWithHighestNumber();
        
        // Verify: Expected output is Player B with number 22
        assertNotNull("Result should not be null", result);
        assertEquals("Player B", result.getName());
        assertEquals(22, result.getNumber());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // SetUp: Create a football team "Turgutlu FC" and add players
        team.setTeam("Turgutlu FC");
        
        List<FootballPlayer> players = new ArrayList<>();
        
        FootballPlayer playerD = new FootballPlayer();
        playerD.setName("Player D");
        playerD.setAge(28);
        playerD.setNumber(12);
        playerD.setPosition(Position.MIDFIELD);
        players.add(playerD);
        
        FootballPlayer playerE = new FootballPlayer();
        playerE.setName("Player E");
        playerE.setAge(30);
        playerE.setNumber(10);
        playerE.setPosition(Position.FORWARD);
        players.add(playerE);
        
        FootballPlayer playerF = new FootballPlayer();
        playerF.setName("Player F");
        playerF.setAge(22);
        playerF.setNumber(2);
        playerF.setPosition(Position.DEFENSE);
        players.add(playerF);
        
        team.setAllPlayers(players);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = team.identifyMidfielderWithHighestNumber();
        
        // Verify: Expected output is Player D with number 12
        assertNotNull("Result should not be null", result);
        assertEquals("Player D", result.getName());
        assertEquals(12, result.getNumber());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // SetUp: Create a football team "Turgutlu FC" and add players
        team.setTeam("Turgutlu FC");
        
        List<FootballPlayer> players = new ArrayList<>();
        
        FootballPlayer playerG = new FootballPlayer();
        playerG.setName("Player G");
        playerG.setAge(21);
        playerG.setNumber(20);
        playerG.setPosition(Position.MIDFIELD);
        players.add(playerG);
        
        FootballPlayer playerH = new FootballPlayer();
        playerH.setName("Player H");
        playerH.setAge(29);
        playerH.setNumber(20);
        playerH.setPosition(Position.MIDFIELD);
        players.add(playerH);
        
        FootballPlayer playerI = new FootballPlayer();
        playerI.setName("Player I");
        playerI.setAge(27);
        playerI.setNumber(3);
        playerI.setPosition(Position.DEFENSE);
        players.add(playerI);
        
        team.setAllPlayers(players);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = team.identifyMidfielderWithHighestNumber();
        
        // Verify: Expected output is Player G with number 20 (first based on insertion order)
        assertNotNull("Result should not be null", result);
        assertEquals("Player G", result.getName());
        assertEquals(20, result.getNumber());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // SetUp: Create a football team "Turgutlu FC" and add players
        team.setTeam("Turgutlu FC");
        
        List<FootballPlayer> players = new ArrayList<>();
        
        FootballPlayer playerJ = new FootballPlayer();
        playerJ.setName("Player J");
        playerJ.setAge(25);
        playerJ.setNumber(9);
        playerJ.setPosition(Position.FORWARD);
        players.add(playerJ);
        
        FootballPlayer playerK = new FootballPlayer();
        playerK.setName("Player K");
        playerK.setAge(30);
        playerK.setNumber(1);
        playerK.setPosition(Position.GOALKEEPER);
        players.add(playerK);
        
        FootballPlayer playerL = new FootballPlayer();
        playerL.setName("Player L");
        playerL.setAge(23);
        playerL.setNumber(4);
        playerL.setPosition(Position.DEFENSE);
        players.add(playerL);
        
        team.setAllPlayers(players);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = team.identifyMidfielderWithHighestNumber();
        
        // Verify: Expected output is null (no midfielders available)
        assertNull("Result should be null when no midfielders found", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // SetUp: Create a football team "Turgutlu FC" and add players
        team.setTeam("Turgutlu FC");
        
        List<FootballPlayer> players = new ArrayList<>();
        
        FootballPlayer playerM = new FootballPlayer();
        playerM.setName("Player M");
        playerM.setAge(24);
        playerM.setNumber(19);
        playerM.setPosition(Position.MIDFIELD);
        players.add(playerM);
        
        FootballPlayer playerN = new FootballPlayer();
        playerN.setName("Player N");
        playerN.setAge(26);
        playerN.setNumber(25);
        playerN.setPosition(Position.MIDFIELD);
        players.add(playerN);
        
        FootballPlayer playerO = new FootballPlayer();
        playerO.setName("Player O");
        playerO.setAge(27);
        playerO.setNumber(5);
        playerO.setPosition(Position.DEFENSE);
        players.add(playerO);
        
        FootballPlayer playerP = new FootballPlayer();
        playerP.setName("Player P");
        playerP.setAge(29);
        playerP.setNumber(30);
        playerP.setPosition(Position.FORWARD);
        players.add(playerP);
        
        team.setAllPlayers(players);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = team.identifyMidfielderWithHighestNumber();
        
        // Verify: Expected output is Player N with number 25
        assertNotNull("Result should not be null", result);
        assertEquals("Player N", result.getName());
        assertEquals(25, result.getNumber());
    }
}