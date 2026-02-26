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
        // Set up players for test case 1
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
        
        // Add players to team
        team.getPlayers().add(playerA);
        team.getPlayers().add(playerB);
        team.getPlayers().add(playerC);
        
        // Execute the method under test
        FootballPlayer result = team.findMidfielderWithHighestNumber();
        
        // Verify the result
        assertNotNull("Result should not be null", result);
        assertEquals("Player B", result.getName());
        assertEquals(22, result.getNumber());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // Set up players for test case 2
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
        
        // Add players to team
        team.getPlayers().add(playerD);
        team.getPlayers().add(playerE);
        team.getPlayers().add(playerF);
        
        // Execute the method under test
        FootballPlayer result = team.findMidfielderWithHighestNumber();
        
        // Verify the result
        assertNotNull("Result should not be null", result);
        assertEquals("Player D", result.getName());
        assertEquals(12, result.getNumber());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // Set up players for test case 3
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
        
        // Add players to team
        team.getPlayers().add(playerG);
        team.getPlayers().add(playerH);
        team.getPlayers().add(playerI);
        
        // Execute the method under test
        FootballPlayer result = team.findMidfielderWithHighestNumber();
        
        // Verify the result - should return the first midfielder with highest number (Player G)
        assertNotNull("Result should not be null", result);
        assertEquals("Player G", result.getName());
        assertEquals(20, result.getNumber());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // Set up players for test case 4
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
        team.getPlayers().add(playerJ);
        team.getPlayers().add(playerK);
        team.getPlayers().add(playerL);
        
        // Execute the method under test
        FootballPlayer result = team.findMidfielderWithHighestNumber();
        
        // Verify the result should be null when no midfielders are available
        assertNull("Result should be null when no midfielders available", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // Set up players for test case 5
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
        
        // Add players to team
        team.getPlayers().add(playerM);
        team.getPlayers().add(playerN);
        team.getPlayers().add(playerO);
        team.getPlayers().add(playerP);
        
        // Execute the method under test
        FootballPlayer result = team.findMidfielderWithHighestNumber();
        
        // Verify the result
        assertNotNull("Result should not be null", result);
        assertEquals("Player N", result.getName());
        assertEquals(25, result.getNumber());
    }
}