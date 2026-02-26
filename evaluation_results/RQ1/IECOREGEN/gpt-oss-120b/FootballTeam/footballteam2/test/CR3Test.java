package edu.footballteam.footballteam2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.footballteam.FootballteamFactory;
import edu.footballteam.FootballTeam;
import edu.footballteam.Player;
import edu.footballteam.Position;

public class CR3Test {
    
    private FootballteamFactory factory;
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory for object creation
        factory = FootballteamFactory.eINSTANCE;
        // Create a new football team using factory pattern
        team = factory.createFootballTeam();
    }
    
    @Test
    public void testCase1_AverageAgeCalculationWithAllPlayers() {
        // SetUp: Create players and add them to the team
        Player player1 = factory.createPlayer();
        player1.setName("John");
        player1.setAge(25);
        player1.setPosition(Position.MIDFIELD);
        
        Player player2 = factory.createPlayer();
        player2.setName("Alex");
        player2.setAge(28);
        player2.setPosition(Position.FORWARD);
        
        Player player3 = factory.createPlayer();
        player3.setName("Sam");
        player3.setAge(30);
        player3.setPosition(Position.DEFENSE);
        
        Player sparePlayer1 = factory.createPlayer();
        sparePlayer1.setName("Mike");
        sparePlayer1.setAge(26);
        sparePlayer1.setPosition(Position.MIDFIELD);
        
        Player sparePlayer2 = factory.createPlayer();
        sparePlayer2.setName("Karl");
        sparePlayer2.setAge(24);
        sparePlayer2.setPosition(Position.FORWARD);
        
        // Add all players to the team's players list
        team.getPlayers().add(player1);
        team.getPlayers().add(player2);
        team.getPlayers().add(player3);
        team.getPlayers().add(sparePlayer1);
        team.getPlayers().add(sparePlayer2);
        
        // Set first eleven (Players 1, 2, and 3)
        team.getFirstEleven().add(player1);
        team.getFirstEleven().add(player2);
        team.getFirstEleven().add(player3);
        
        // Set spare team (Spare Player 1 and 2)
        team.getSpareTeam().add(sparePlayer1);
        team.getSpareTeam().add(sparePlayer2);
        
        // Calculate average age and verify expected result
        double expectedAverage = (26.0 + 24.0) / 2.0;
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        
        assertEquals("Average age should be 25.0", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // SetUp: Create players and add them to the team
        Player player1 = factory.createPlayer();
        player1.setName("Fatih");
        player1.setAge(27);
        player1.setPosition(Position.GOALKEEPER);
        
        Player player2 = factory.createPlayer();
        player2.setName("Gökhan");
        player2.setAge(25);
        player2.setPosition(Position.DEFENSE);
        
        Player player3 = factory.createPlayer();
        player3.setName("Hakan");
        player3.setAge(29);
        player3.setPosition(Position.MIDFIELD);
        
        // Add all players to the team's players list
        team.getPlayers().add(player1);
        team.getPlayers().add(player2);
        team.getPlayers().add(player3);
        
        // Set first eleven (all players)
        team.getFirstEleven().add(player1);
        team.getFirstEleven().add(player2);
        team.getFirstEleven().add(player3);
        
        // Spare team remains empty
        
        // Calculate average age and verify expected result
        double expectedAverage = 0.0;
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        
        assertEquals("Average age should be 0.0 when no spare players", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // SetUp: Create players and add them to the team
        Player player1 = factory.createPlayer();
        player1.setName("Kerem");
        player1.setAge(22);
        player1.setPosition(Position.FORWARD);
        
        Player player2 = factory.createPlayer();
        player2.setName("Levent");
        player2.setAge(24);
        player2.setPosition(Position.MIDFIELD);
        
        Player player3 = factory.createPlayer();
        player3.setName("Mehmet");
        player3.setAge(26);
        player3.setPosition(Position.DEFENSE);
        
        Player sparePlayer1 = factory.createPlayer();
        sparePlayer1.setName("Nihat");
        sparePlayer1.setAge(35);
        sparePlayer1.setPosition(Position.FORWARD);
        
        Player sparePlayer2 = factory.createPlayer();
        sparePlayer2.setName("Onur");
        sparePlayer2.setAge(22);
        sparePlayer2.setPosition(Position.MIDFIELD);
        
        // Add all players to the team's players list
        team.getPlayers().add(player1);
        team.getPlayers().add(player2);
        team.getPlayers().add(player3);
        team.getPlayers().add(sparePlayer1);
        team.getPlayers().add(sparePlayer2);
        
        // Set first eleven (Players 1, 2, and 3)
        team.getFirstEleven().add(player1);
        team.getFirstEleven().add(player2);
        team.getFirstEleven().add(player3);
        
        // Set spare team (Spare Player 1 and 2)
        team.getSpareTeam().add(sparePlayer1);
        team.getSpareTeam().add(sparePlayer2);
        
        // Calculate average age and verify expected result
        double expectedAverage = (35.0 + 22.0) / 2.0;
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        
        assertEquals("Average age should be 28.5", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // SetUp: Create players and add them to the team
        Player player1 = factory.createPlayer();
        player1.setName("Tolga");
        player1.setAge(28);
        player1.setPosition(Position.GOALKEEPER);
        
        Player player2 = factory.createPlayer();
        player2.setName("Umut");
        player2.setAge(25);
        player2.setPosition(Position.DEFENSE);
        
        Player player3 = factory.createPlayer();
        player3.setName("Volkan");
        player3.setAge(30);
        player3.setPosition(Position.FORWARD);
        
        Player sparePlayer1 = factory.createPlayer();
        sparePlayer1.setName("Yiğit");
        sparePlayer1.setAge(31);
        sparePlayer1.setPosition(Position.FORWARD);
        
        // Add all players to the team's players list
        team.getPlayers().add(player1);
        team.getPlayers().add(player2);
        team.getPlayers().add(player3);
        team.getPlayers().add(sparePlayer1);
        
        // Set first eleven (Players 1, 2, and 3)
        team.getFirstEleven().add(player1);
        team.getFirstEleven().add(player2);
        team.getFirstEleven().add(player3);
        
        // Set spare team (only Spare Player 1)
        team.getSpareTeam().add(sparePlayer1);
        
        // Calculate average age and verify expected result
        double expectedAverage = 31.0;
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        
        assertEquals("Average age should be 31.0 with one spare player", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // SetUp: Create players and add them to the team
        Player player1 = factory.createPlayer();
        player1.setName("Eren");
        player1.setAge(24);
        player1.setPosition(Position.FORWARD);
        
        Player player2 = factory.createPlayer();
        player2.setName("Furkan");
        player2.setAge(26);
        player2.setPosition(Position.MIDFIELD);
        
        Player player3 = factory.createPlayer();
        player3.setName("Kadir");
        player3.setAge(28);
        player3.setPosition(Position.DEFENSE);
        
        Player sparePlayer1 = factory.createPlayer();
        sparePlayer1.setName("Zafer");
        sparePlayer1.setAge(27);
        sparePlayer1.setPosition(Position.MIDFIELD);
        
        Player sparePlayer2 = factory.createPlayer();
        sparePlayer2.setName("Burak");
        sparePlayer2.setAge(27);
        sparePlayer2.setPosition(Position.MIDFIELD);
        
        // Add all players to the team's players list
        team.getPlayers().add(player1);
        team.getPlayers().add(player2);
        team.getPlayers().add(player3);
        team.getPlayers().add(sparePlayer1);
        team.getPlayers().add(sparePlayer2);
        
        // Set first eleven (Players 1, 2, and 3)
        team.getFirstEleven().add(player1);
        team.getFirstEleven().add(player2);
        team.getFirstEleven().add(player3);
        
        // Set spare team (Spare Player 1 and 2)
        team.getSpareTeam().add(sparePlayer1);
        team.getSpareTeam().add(sparePlayer2);
        
        // Calculate average age and verify expected result
        double expectedAverage = (27.0 + 27.0) / 2.0;
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        
        assertEquals("Average age should be 27.0 with two identical age spare players", expectedAverage, actualAverage, 0.001);
    }
}