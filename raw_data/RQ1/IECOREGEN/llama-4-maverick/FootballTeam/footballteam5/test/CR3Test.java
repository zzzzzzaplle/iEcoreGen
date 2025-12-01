package edu.footballteam.footballteam5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.footballteam.FootballTeam;
import edu.footballteam.Player;
import edu.footballteam.FootballteamFactory;

public class CR3Test {
    
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        // Initialize a new football team before each test
        footballTeam = FootballteamFactory.eINSTANCE.createFootballTeam();
    }
    
    @Test
    public void testCase1_averageAgeCalculationWithAllPlayers() {
        // Create players for the team
        Player player1 = FootballteamFactory.eINSTANCE.createPlayer();
        player1.setName("John");
        player1.setAge(25);
        player1.setPosition(edu.footballteam.Position.MIDFIELD);
        
        Player player2 = FootballteamFactory.eINSTANCE.createPlayer();
        player2.setName("Alex");
        player2.setAge(28);
        player2.setPosition(edu.footballteam.Position.FORWARD);
        
        Player player3 = FootballteamFactory.eINSTANCE.createPlayer();
        player3.setName("Sam");
        player3.setAge(30);
        player3.setPosition(edu.footballteam.Position.DEFENSE);
        
        // Create spare players
        Player sparePlayer1 = FootballteamFactory.eINSTANCE.createPlayer();
        sparePlayer1.setName("Mike");
        sparePlayer1.setAge(26);
        
        Player sparePlayer2 = FootballteamFactory.eINSTANCE.createPlayer();
        sparePlayer2.setName("Karl");
        sparePlayer2.setAge(24);
        
        // Add all players to the team
        footballTeam.getPlayers().add(player1);
        footballTeam.getPlayers().add(player2);
        footballTeam.getPlayers().add(player3);
        footballTeam.getPlayers().add(sparePlayer1);
        footballTeam.getPlayers().add(sparePlayer2);
        
        // Set first eleven (players 1, 2, and 3)
        footballTeam.getFirstEleven().add(player1);
        footballTeam.getFirstEleven().add(player2);
        footballTeam.getFirstEleven().add(player3);
        
        // Spare team consists of sparePlayer1 and sparePlayer2
        footballTeam.getSpareTeam().add(sparePlayer1);
        footballTeam.getSpareTeam().add(sparePlayer2);
        
        // Calculate average age of spare team
        double averageAge = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Expected output: (26 + 24) / 2 = 25.0
        assertEquals(25.0, averageAge, 0.001);
    }
    
    @Test
    public void testCase2_averageAgeCalculationWithNoSparePlayers() {
        // Create players for the team
        Player player1 = FootballteamFactory.eINSTANCE.createPlayer();
        player1.setName("Fatih");
        player1.setAge(27);
        player1.setPosition(edu.footballteam.Position.GOALKEEPER);
        
        Player player2 = FootballteamFactory.eINSTANCE.createPlayer();
        player2.setName("Gökhan");
        player2.setAge(25);
        player2.setPosition(edu.footballteam.Position.DEFENSE);
        
        Player player3 = FootballteamFactory.eINSTANCE.createPlayer();
        player3.setName("Hakan");
        player3.setAge(29);
        player3.setPosition(edu.footballteam.Position.MIDFIELD);
        
        // Add all players to the team
        footballTeam.getPlayers().add(player1);
        footballTeam.getPlayers().add(player2);
        footballTeam.getPlayers().add(player3);
        
        // All players are part of the first eleven
        footballTeam.getFirstEleven().add(player1);
        footballTeam.getFirstEleven().add(player2);
        footballTeam.getFirstEleven().add(player3);
        
        // No spare players added to spare team
        
        // Calculate average age of spare team
        double averageAge = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Expected output: 0.0 (No spare players available)
        assertEquals(0.0, averageAge, 0.001);
    }
    
    @Test
    public void testCase3_averageAgeCalculationWithMixedAgeSparePlayers() {
        // Create players for the team
        Player player1 = FootballteamFactory.eINSTANCE.createPlayer();
        player1.setName("Kerem");
        player1.setAge(22);
        player1.setPosition(edu.footballteam.Position.FORWARD);
        
        Player player2 = FootballteamFactory.eINSTANCE.createPlayer();
        player2.setName("Levent");
        player2.setAge(24);
        player2.setPosition(edu.footballteam.Position.MIDFIELD);
        
        Player player3 = FootballteamFactory.eINSTANCE.createPlayer();
        player3.setName("Mehmet");
        player3.setAge(26);
        player3.setPosition(edu.footballteam.Position.DEFENSE);
        
        // Create spare players
        Player sparePlayer1 = FootballteamFactory.eINSTANCE.createPlayer();
        sparePlayer1.setName("Nihat");
        sparePlayer1.setAge(35);
        sparePlayer1.setPosition(edu.footballteam.Position.FORWARD);
        
        Player sparePlayer2 = FootballteamFactory.eINSTANCE.createPlayer();
        sparePlayer2.setName("Onur");
        sparePlayer2.setAge(22);
        sparePlayer2.setPosition(edu.footballteam.Position.MIDFIELD);
        
        // Add all players to the team
        footballTeam.getPlayers().add(player1);
        footballTeam.getPlayers().add(player2);
        footballTeam.getPlayers().add(player3);
        footballTeam.getPlayers().add(sparePlayer1);
        footballTeam.getPlayers().add(sparePlayer2);
        
        // Set first eleven (players 1, 2, and 3)
        footballTeam.getFirstEleven().add(player1);
        footballTeam.getFirstEleven().add(player2);
        footballTeam.getFirstEleven().add(player3);
        
        // Spare team consists of sparePlayer1 and sparePlayer2
        footballTeam.getSpareTeam().add(sparePlayer1);
        footballTeam.getSpareTeam().add(sparePlayer2);
        
        // Calculate average age of spare team
        double averageAge = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Expected output: (35 + 22) / 2 = 28.5
        assertEquals(28.5, averageAge, 0.001);
    }
    
    @Test
    public void testCase4_averageAgeCalculationWithOneSparePlayer() {
        // Create players for the team
        Player player1 = FootballteamFactory.eINSTANCE.createPlayer();
        player1.setName("Tolga");
        player1.setAge(28);
        player1.setPosition(edu.footballteam.Position.GOALKEEPER);
        
        Player player2 = FootballteamFactory.eINSTANCE.createPlayer();
        player2.setName("Umut");
        player2.setAge(25);
        player2.setPosition(edu.footballteam.Position.DEFENSE);
        
        Player player3 = FootballteamFactory.eINSTANCE.createPlayer();
        player3.setName("Volkan");
        player3.setAge(30);
        player3.setPosition(edu.footballteam.Position.FORWARD);
        
        // Create spare player
        Player sparePlayer1 = FootballteamFactory.eINSTANCE.createPlayer();
        sparePlayer1.setName("Yiğit");
        sparePlayer1.setAge(31);
        sparePlayer1.setPosition(edu.footballteam.Position.FORWARD);
        
        // Add all players to the team
        footballTeam.getPlayers().add(player1);
        footballTeam.getPlayers().add(player2);
        footballTeam.getPlayers().add(player3);
        footballTeam.getPlayers().add(sparePlayer1);
        
        // Set first eleven (players 1, 2, and 3)
        footballTeam.getFirstEleven().add(player1);
        footballTeam.getFirstEleven().add(player2);
        footballTeam.getFirstEleven().add(player3);
        
        // Spare team consists of sparePlayer1
        footballTeam.getSpareTeam().add(sparePlayer1);
        
        // Calculate average age of spare team
        double averageAge = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Expected output: 31.0 (Only one spare player)
        assertEquals(31.0, averageAge, 0.001);
    }
    
    @Test
    public void testCase5_averageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Create players for the team
        Player player1 = FootballteamFactory.eINSTANCE.createPlayer();
        player1.setName("Eren");
        player1.setAge(24);
        player1.setPosition(edu.footballteam.Position.FORWARD);
        
        Player player2 = FootballteamFactory.eINSTANCE.createPlayer();
        player2.setName("Furkan");
        player2.setAge(26);
        player2.setPosition(edu.footballteam.Position.MIDFIELD);
        
        Player player3 = FootballteamFactory.eINSTANCE.createPlayer();
        player3.setName("Kadir");
        player3.setAge(28);
        player3.setPosition(edu.footballteam.Position.DEFENSE);
        
        // Create spare players with identical ages
        Player sparePlayer1 = FootballteamFactory.eINSTANCE.createPlayer();
        sparePlayer1.setName("Zafer");
        sparePlayer1.setAge(27);
        sparePlayer1.setPosition(edu.footballteam.Position.MIDFIELD);
        
        Player sparePlayer2 = FootballteamFactory.eINSTANCE.createPlayer();
        sparePlayer2.setName("Burak");
        sparePlayer2.setAge(27);
        sparePlayer2.setPosition(edu.footballteam.Position.MIDFIELD);
        
        // Add all players to the team
        footballTeam.getPlayers().add(player1);
        footballTeam.getPlayers().add(player2);
        footballTeam.getPlayers().add(player3);
        footballTeam.getPlayers().add(sparePlayer1);
        footballTeam.getPlayers().add(sparePlayer2);
        
        // Set first eleven (players 1, 2, and 3)
        footballTeam.getFirstEleven().add(player1);
        footballTeam.getFirstEleven().add(player2);
        footballTeam.getFirstEleven().add(player3);
        
        // Spare team consists of sparePlayer1 and sparePlayer2
        footballTeam.getSpareTeam().add(sparePlayer1);
        footballTeam.getSpareTeam().add(sparePlayer2);
        
        // Calculate average age of spare team
        double averageAge = footballTeam.calculateAverageAgeOfSpareTeam();
        
        // Expected output: (27 + 27) / 2 = 27.0
        assertEquals(27.0, averageAge, 0.001);
    }
}