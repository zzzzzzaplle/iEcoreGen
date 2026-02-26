package edu.footballteam.footballteam3.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import edu.footballteam.FootballTeam;
import edu.footballteam.FootballteamFactory;
import edu.footballteam.Player;
import edu.footballteam.Position;

public class CR2Test {
    
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        // Create a football team using the factory
        footballTeam = FootballteamFactory.eINSTANCE.createFootballTeam();
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumberBasicCase() {
        // Setup: Create players
        Player playerA = FootballteamFactory.eINSTANCE.createPlayer();
        playerA.setName("Player A");
        playerA.setAge(24);
        playerA.setNumber(15);
        playerA.setPosition(Position.MIDFIELD);
        
        Player playerB = FootballteamFactory.eINSTANCE.createPlayer();
        playerB.setName("Player B");
        playerB.setAge(26);
        playerB.setNumber(22);
        playerB.setPosition(Position.MIDFIELD);
        
        Player playerC = FootballteamFactory.eINSTANCE.createPlayer();
        playerC.setName("Player C");
        playerC.setAge(23);
        playerC.setNumber(18);
        playerC.setPosition(Position.DEFENSE);
        
        // Add players to the team
        footballTeam.getPlayers().add(playerA);
        footballTeam.getPlayers().add(playerB);
        footballTeam.getPlayers().add(playerC);
        
        // Execute: Find midfielder with highest number
        Player result = footballTeam.findMidfielderWithHighestNumber();
        
        // Verify: Expected "Player B, Number: 22"
        assertNotNull(result);
        assertEquals("Player B", result.getName());
        assertEquals(22, result.getNumber());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumberSingleMidfielder() {
        // Setup: Create players
        Player playerD = FootballteamFactory.eINSTANCE.createPlayer();
        playerD.setName("Player D");
        playerD.setAge(28);
        playerD.setNumber(12);
        playerD.setPosition(Position.MIDFIELD);
        
        Player playerE = FootballteamFactory.eINSTANCE.createPlayer();
        playerE.setName("Player E");
        playerE.setAge(30);
        playerE.setNumber(10);
        playerE.setPosition(Position.FORWARD);
        
        Player playerF = FootballteamFactory.eINSTANCE.createPlayer();
        playerF.setName("Player F");
        playerF.setAge(22);
        playerF.setNumber(2);
        playerF.setPosition(Position.DEFENSE);
        
        // Add players to the team
        footballTeam.getPlayers().add(playerD);
        footballTeam.getPlayers().add(playerE);
        footballTeam.getPlayers().add(playerF);
        
        // Execute: Find midfielder with highest number
        Player result = footballTeam.findMidfielderWithHighestNumber();
        
        // Verify: Expected "Player D, Number: 12"
        assertNotNull(result);
        assertEquals("Player D", result.getName());
        assertEquals(12, result.getNumber());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumberMultipleMidfieldersSameNumbers() {
        // Setup: Create players
        Player playerG = FootballteamFactory.eINSTANCE.createPlayer();
        playerG.setName("Player G");
        playerG.setAge(21);
        playerG.setNumber(20);
        playerG.setPosition(Position.MIDFIELD);
        
        Player playerH = FootballteamFactory.eINSTANCE.createPlayer();
        playerH.setName("Player H");
        playerH.setAge(29);
        playerH.setNumber(20);
        playerH.setPosition(Position.MIDFIELD);
        
        Player playerI = FootballteamFactory.eINSTANCE.createPlayer();
        playerI.setName("Player I");
        playerI.setAge(27);
        playerI.setNumber(3);
        playerI.setPosition(Position.DEFENSE);
        
        // Add players to the team
        footballTeam.getPlayers().add(playerG);
        footballTeam.getPlayers().add(playerH);
        footballTeam.getPlayers().add(playerI);
        
        // Execute: Find midfielder with highest number
        Player result = footballTeam.findMidfielderWithHighestNumber();
        
        // Verify: Expected "Player G, Number: 20" (first one based on insertion order)
        assertNotNull(result);
        assertEquals("Player G", result.getName());
        assertEquals(20, result.getNumber());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumberNoMidfieldersAvailable() {
        // Setup: Create players without midfielders
        Player playerJ = FootballteamFactory.eINSTANCE.createPlayer();
        playerJ.setName("Player J");
        playerJ.setAge(25);
        playerJ.setNumber(9);
        playerJ.setPosition(Position.FORWARD);
        
        Player playerK = FootballteamFactory.eINSTANCE.createPlayer();
        playerK.setName("Player K");
        playerK.setAge(30);
        playerK.setNumber(1);
        playerK.setPosition(Position.GOALKEEPER);
        
        Player playerL = FootballteamFactory.eINSTANCE.createPlayer();
        playerL.setName("Player L");
        playerL.setAge(23);
        playerL.setNumber(4);
        playerL.setPosition(Position.DEFENSE);
        
        // Add players to the team
        footballTeam.getPlayers().add(playerJ);
        footballTeam.getPlayers().add(playerK);
        footballTeam.getPlayers().add(playerL);
        
        // Execute: Find midfielder with highest number
        Player result = footballTeam.findMidfielderWithHighestNumber();
        
        // Verify: Expected "No midfielders available" -> null
        assertNull(result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumberAllPositionsIncluded() {
        // Setup: Create players with all positions
        Player playerM = FootballteamFactory.eINSTANCE.createPlayer();
        playerM.setName("Player M");
        playerM.setAge(24);
        playerM.setNumber(19);
        playerM.setPosition(Position.MIDFIELD);
        
        Player playerN = FootballteamFactory.eINSTANCE.createPlayer();
        playerN.setName("Player N");
        playerN.setAge(26);
        playerN.setNumber(25);
        playerN.setPosition(Position.MIDFIELD);
        
        Player playerO = FootballteamFactory.eINSTANCE.createPlayer();
        playerO.setName("Player O");
        playerO.setAge(27);
        playerO.setNumber(5);
        playerO.setPosition(Position.DEFENSE);
        
        Player playerP = FootballteamFactory.eINSTANCE.createPlayer();
        playerP.setName("Player P");
        playerP.setAge(29);
        playerP.setNumber(30);
        playerP.setPosition(Position.FORWARD);
        
        // Add players to the team
        footballTeam.getPlayers().add(playerM);
        footballTeam.getPlayers().add(playerN);
        footballTeam.getPlayers().add(playerO);
        footballTeam.getPlayers().add(playerP);
        
        // Execute: Find midfielder with highest number
        Player result = footballTeam.findMidfielderWithHighestNumber();
        
        // Verify: Expected "Player N, Number: 25"
        assertNotNull(result);
        assertEquals("Player N", result.getName());
        assertEquals(25, result.getNumber());
    }
}