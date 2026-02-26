import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        footballTeam = new FootballTeam();
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumber_BasicCase() {
        // SetUp: Create football team and add players
        footballTeam.setTeamName("Turgutlu FC");
        
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
        
        footballTeam.getStartingEleven().add(playerA);
        footballTeam.getStartingEleven().add(playerB);
        footballTeam.getStartingEleven().add(playerC);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify: Should return Player B with number 22
        assertNotNull("Should find a midfielder", result);
        assertEquals("Player B", result.getName());
        assertEquals(22, result.getNumber());
        assertEquals(Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // SetUp: Create football team with single midfielder
        footballTeam.setTeamName("Turgutlu FC");
        
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
        
        footballTeam.getStartingEleven().add(playerD);
        footballTeam.getStartingEleven().add(playerE);
        footballTeam.getStartingEleven().add(playerF);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify: Should return Player D with number 12
        assertNotNull("Should find a midfielder", result);
        assertEquals("Player D", result.getName());
        assertEquals(12, result.getNumber());
        assertEquals(Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // SetUp: Create football team with midfielders having same numbers
        footballTeam.setTeamName("Turgutlu FC");
        
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
        
        footballTeam.getStartingEleven().add(playerG);
        footballTeam.getStartingEleven().add(playerH);
        footballTeam.getStartingEleven().add(playerI);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify: Should return the first midfielder with highest number (Player G)
        assertNotNull("Should find a midfielder", result);
        assertEquals("Player G", result.getName());
        assertEquals(20, result.getNumber());
        assertEquals(Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // SetUp: Create football team with no midfielders
        footballTeam.setTeamName("Turgutlu FC");
        
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
        
        footballTeam.getStartingEleven().add(playerJ);
        footballTeam.getStartingEleven().add(playerK);
        footballTeam.getStartingEleven().add(playerL);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify: Should return null when no midfielders available
        assertNull("Should return null when no midfielders found", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // SetUp: Create football team with all positions
        footballTeam.setTeamName("Turgutlu FC");
        
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
        
        footballTeam.getStartingEleven().add(playerM);
        footballTeam.getStartingEleven().add(playerN);
        footballTeam.getStartingEleven().add(playerO);
        footballTeam.getStartingEleven().add(playerP);
        
        // Execute: Identify midfielder with highest number
        FootballPlayer result = footballTeam.identifyMidfielderWithHighestNumber();
        
        // Verify: Should return Player N with number 25
        assertNotNull("Should find a midfielder", result);
        assertEquals("Player N", result.getName());
        assertEquals(25, result.getNumber());
        assertEquals(Position.MIDFIELD, result.getPosition());
    }
}