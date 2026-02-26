package edu.footballteam.footballteam1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.footballteam.FootballteamFactory;
import edu.footballteam.FootballTeam;
import edu.footballteam.Player;
import edu.footballteam.Position;

public class CR2Test {
    
    private FootballteamFactory factory;
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Use Ecore factory to create objects
        factory = FootballteamFactory.eINSTANCE;
        team = factory.createFootballTeam();
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumber_BasicCase() {
        // Test Case 1: "Midfielder with Highest Number - Basic Case"
        
        // Create players using factory
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
        
        // Add players to team
        team.getPlayers().add(playerA);
        team.getPlayers().add(playerB);
        team.getPlayers().add(playerC);
        
        // Execute the method under test
        Player result = team.findMidfielderWithHighestNumber();
        
        // Verify the result
        assertNotNull("Result should not be null", result);
        assertEquals("Player name should be Player B", "Player B", result.getName());
        assertEquals("Player number should be 22", 22, result.getNumber());
        assertEquals("Player position should be MIDFIELD", Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase2_MidfielderWithHighestNumber_SingleMidfielder() {
        // Test Case 2: "Midfielder with Highest Number - Single Midfielder"
        
        // Create players using factory
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
        
        // Add players to team
        team.getPlayers().add(playerD);
        team.getPlayers().add(playerE);
        team.getPlayers().add(playerF);
        
        // Execute the method under test
        Player result = team.findMidfielderWithHighestNumber();
        
        // Verify the result
        assertNotNull("Result should not be null", result);
        assertEquals("Player name should be Player D", "Player D", result.getName());
        assertEquals("Player number should be 12", 12, result.getNumber());
        assertEquals("Player position should be MIDFIELD", Position.MIDFIELD, result.getPosition());
    }
    
    @Test
    public void testCase3_MidfielderWithHighestNumber_MultipleMidfieldersWithSameNumbers() {
        // Test Case 3: "Midfielder with Highest Number - Multiple Midfielders with Same Numbers"
        
        // Create players using factory
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
        
        // Add players to team
        team.getPlayers().add(playerG);
        team.getPlayers().add(playerH);
        team.getPlayers().add(playerI);
        
        // Execute the method under test
        Player result = team.findMidfielderWithHighestNumber();
        
        // Verify the result
        assertNotNull("Result should not be null", result);
        assertEquals("Player number should be 20", 20, result.getNumber());
        assertEquals("Player position should be MIDFIELD", Position.MIDFIELD, result.getPosition());
        
        // Since both have same number, it should return the first one encountered (Player G)
        assertEquals("Player name should be Player G (first with highest number)", "Player G", result.getName());
    }
    
    @Test
    public void testCase4_MidfielderWithHighestNumber_NoMidfieldersAvailable() {
        // Test Case 4: "Midfielder with Highest Number - No Midfielders Available"
        
        // Create players using factory (no midfielders)
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
        
        // Add players to team
        team.getPlayers().add(playerJ);
        team.getPlayers().add(playerK);
        team.getPlayers().add(playerL);
        
        // Execute the method under test
        Player result = team.findMidfielderWithHighestNumber();
        
        // Verify the result - should be null when no midfielders available
        assertNull("Result should be null when no midfielders available", result);
    }
    
    @Test
    public void testCase5_MidfielderWithHighestNumber_AllPositionsIncluded() {
        // Test Case 5: "Midfielder with Highest Number - All Positions Included"
        
        // Create players using factory
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
        
        // Add players to team
        team.getPlayers().add(playerM);
        team.getPlayers().add(playerN);
        team.getPlayers().add(playerO);
        team.getPlayers().add(playerP);
        
        // Execute the method under test
        Player result = team.findMidfielderWithHighestNumber();
        
        // Verify the result
        assertNotNull("Result should not be null", result);
        assertEquals("Player name should be Player N", "Player N", result.getName());
        assertEquals("Player number should be 25", 25, result.getNumber());
        assertEquals("Player position should be MIDFIELD", Position.MIDFIELD, result.getPosition());
    }
}