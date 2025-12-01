package edu.footballteam.footballteam4.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import edu.footballteam.FootballTeam;
import edu.footballteam.FootballteamFactory;
import edu.footballteam.Player;
import edu.footballteam.Position;

public class CR2Test {
    
    private FootballteamFactory factory;
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        // Initialize the factory
        factory = FootballteamFactory.eINSTANCE;
        // Create a football team
        footballTeam = factory.createFootballTeam();
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumberBasicCase() {
        // Create players
        Player playerA = factory.createPlayer();
        playerA.setName("Player A");
        playerA.setAge(24);
        playerA.setNumber(15);
        playerA.setPosition(Position.MIDFIELD);
        
        Player playerB = factory.createPlayer();
        playerB.setName("Player B");
        playerB.setAge(26);
        playerB.setNumber(22);
        playerB.setPosition(Position.MIDFIELD);
        
        Player playerC = factory.createPlayer();
        playerC.setName("Player C");
        playerC.setAge(23);
        playerC.setNumber(18);
        playerC.setPosition(Position.DEFENSE);
        
        // Add players to the team
        footballTeam.getPlayers().add(playerA);
        footballTeam.getPlayers().add(playerB);
        footballTeam.getPlayers().add(playerC);
        
        // Execute the operation
        Player result = footballTeam.findMidfielderWithHighestNumber();
        
        // Verify the result
        assertNotNull("Result should not be null", result);
        assertEquals("Player B", result.getName());
        assertEquals(22, result.getNumber());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumberSingleMidfielder() {
        // Create players
        Player playerD = factory.createPlayer();
        playerD.setName("Player D");
        playerD.setAge(28);
        playerD.setNumber(12);
        playerD.setPosition(Position.MIDFIELD);
        
        Player playerE = factory.createPlayer();
        playerE.setName("Player E");
        playerE.setAge(30);
        playerE.setNumber(10);
        playerE.setPosition(Position.FORWARD);
        
        Player playerF = factory.createPlayer();
        playerF.setName("Player F");
        playerF.setAge(22);
        playerF.setNumber(2);
        playerF.setPosition(Position.DEFENSE);
        
        // Add players to the team
        footballTeam.getPlayers().add(playerD);
        footballTeam.getPlayers().add(playerE);
        footballTeam.getPlayers().add(playerF);
        
        // Execute the operation
        Player result = footballTeam.findMidfielderWithHighestNumber();
        
        // Verify the result
        assertNotNull("Result should not be null", result);
        assertEquals("Player D", result.getName());
        assertEquals(12, result.getNumber());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumberMultipleMidfieldersWithSameNumbers() {
        // Create players
        Player playerG = factory.createPlayer();
        playerG.setName("Player G");
        playerG.setAge(21);
        playerG.setNumber(20);
        playerG.setPosition(Position.MIDFIELD);
        
        Player playerH = factory.createPlayer();
        playerH.setName("Player H");
        playerH.setAge(29);
        playerH.setNumber(20);
        playerH.setPosition(Position.MIDFIELD);
        
        Player playerI = factory.createPlayer();
        playerI.setName("Player I");
        playerI.setAge(27);
        playerI.setNumber(3);
        playerI.setPosition(Position.DEFENSE);
        
        // Add players to the team
        footballTeam.getPlayers().add(playerG);
        footballTeam.getPlayers().add(playerH);
        footballTeam.getPlayers().add(playerI);
        
        // Execute the operation
        Player result = footballTeam.findMidfielderWithHighestNumber();
        
        // Verify the result - Should return the first midfielder with the highest number
        assertNotNull("Result should not be null", result);
        assertEquals("Player G", result.getName());
        assertEquals(20, result.getNumber());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumberNoMidfieldersAvailable() {
        // Create players with no midfielders
        Player playerJ = factory.createPlayer();
        playerJ.setName("Player J");
        playerJ.setAge(25);
        playerJ.setNumber(9);
        playerJ.setPosition(Position.FORWARD);
        
        Player playerK = factory.createPlayer();
        playerK.setName("Player K");
        playerK.setAge(30);
        playerK.setNumber(1);
        playerK.setPosition(Position.GOALKEEPER);
        
        Player playerL = factory.createPlayer();
        playerL.setName("Player L");
        playerL.setAge(23);
        playerL.setNumber(4);
        playerL.setPosition(Position.DEFENSE);
        
        // Add players to the team
        footballTeam.getPlayers().add(playerJ);
        footballTeam.getPlayers().add(playerK);
        footballTeam.getPlayers().add(playerL);
        
        // Execute the operation
        Player result = footballTeam.findMidfielderWithHighestNumber();
        
        // Verify the result
        assertNull("Result should be null when no midfielders are available", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumberAllPositionsIncluded() {
        // Create players
        Player playerM = factory.createPlayer();
        playerM.setName("Player M");
        playerM.setAge(24);
        playerM.setNumber(19);
        playerM.setPosition(Position.MIDFIELD);
        
        Player playerN = factory.createPlayer();
        playerN.setName("Player N");
        playerN.setAge(26);
        playerN.setNumber(25);
        playerN.setPosition(Position.MIDFIELD);
        
        Player playerO = factory.createPlayer();
        playerO.setName("Player O");
        playerO.setAge(27);
        playerO.setNumber(5);
        playerO.setPosition(Position.DEFENSE);
        
        Player playerP = factory.createPlayer();
        playerP.setName("Player P");
        playerP.setAge(29);
        playerP.setNumber(30);
        playerP.setPosition(Position.FORWARD);
        
        // Add players to the team
        footballTeam.getPlayers().add(playerM);
        footballTeam.getPlayers().add(playerN);
        footballTeam.getPlayers().add(playerO);
        footballTeam.getPlayers().add(playerP);
        
        // Execute the operation
        Player result = footballTeam.findMidfielderWithHighestNumber();
        
        // Verify the result
        assertNotNull("Result should not be null", result);
        assertEquals("Player N", result.getName());
        assertEquals(25, result.getNumber());
    }
}