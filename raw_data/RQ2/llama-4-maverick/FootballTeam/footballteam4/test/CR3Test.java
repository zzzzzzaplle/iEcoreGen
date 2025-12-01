package edu.footballteam.footballteam4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.footballteam.FootballTeam;
import edu.footballteam.Player;
import edu.footballteam.FootballteamFactory;

public class CR3Test {
    
    private FootballteamFactory factory;
    
    @Before
    public void setUp() {
        factory = FootballteamFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_averageAgeCalculationWithAllPlayers() {
        // Create a football team "Turgutlu"
        FootballTeam team = factory.createFootballTeam();
        
        // Create players
        Player player1 = factory.createPlayer();
        player1.setName("John");
        player1.setAge(25);
        player1.setPosition(edu.footballteam.Position.MIDFIELD);
        
        Player player2 = factory.createPlayer();
        player2.setName("Alex");
        player2.setAge(28);
        player2.setPosition(edu.footballteam.Position.FORWARD);
        
        Player player3 = factory.createPlayer();
        player3.setName("Sam");
        player3.setAge(30);
        player3.setPosition(edu.footballteam.Position.DEFENSE);
        
        // Create spare players
        Player sparePlayer1 = factory.createPlayer();
        sparePlayer1.setName("Mike");
        sparePlayer1.setAge(26);
        
        Player sparePlayer2 = factory.createPlayer();
        sparePlayer2.setName("Karl");
        sparePlayer2.setAge(24);
        
        // Add all players to the team
        team.getPlayers().add(player1);
        team.getPlayers().add(player2);
        team.getPlayers().add(player3);
        team.getPlayers().add(sparePlayer1);
        team.getPlayers().add(sparePlayer2);
        
        // Add players to first eleven (Players 1, 2, and 3)
        team.getFirstEleven().add(player1);
        team.getFirstEleven().add(player2);
        team.getFirstEleven().add(player3);
        
        // Add spare players to spare team
        team.getSpareTeam().add(sparePlayer1);
        team.getSpareTeam().add(sparePlayer2);
        
        // Calculate average age of spare team
        double averageAge = team.calculateAverageAgeOfSpareTeam();
        
        // Expected output: Average age = (26 + 24) / 2 = 25.0
        assertEquals(25.0, averageAge, 0.001);
    }
    
    @Test
    public void testCase2_averageAgeCalculationWithNoSparePlayers() {
        // Create a football team "Turgutlu"
        FootballTeam team = factory.createFootballTeam();
        
        // Create players
        Player player1 = factory.createPlayer();
        player1.setName("Fatih");
        player1.setAge(27);
        player1.setPosition(edu.footballteam.Position.GOALKEEPER);
        
        Player player2 = factory.createPlayer();
        player2.setName("Gökhan");
        player2.setAge(25);
        player2.setPosition(edu.footballteam.Position.DEFENSE);
        
        Player player3 = factory.createPlayer();
        player3.setName("Hakan");
        player3.setAge(29);
        player3.setPosition(edu.footballteam.Position.MIDFIELD);
        
        // Add all players to the team
        team.getPlayers().add(player1);
        team.getPlayers().add(player2);
        team.getPlayers().add(player3);
        
        // Add all players to first eleven (no spare players)
        team.getFirstEleven().add(player1);
        team.getFirstEleven().add(player2);
        team.getFirstEleven().add(player3);
        
        // Calculate average age of spare team
        double averageAge = team.calculateAverageAgeOfSpareTeam();
        
        // Expected output: Average age = 0.0 (No spare players available)
        assertEquals(0.0, averageAge, 0.001);
    }
    
    @Test
    public void testCase3_averageAgeCalculationWithMixedAgeSparePlayers() {
        // Create a football team "Turgutlu"
        FootballTeam team = factory.createFootballTeam();
        
        // Create players
        Player player1 = factory.createPlayer();
        player1.setName("Kerem");
        player1.setAge(22);
        player1.setPosition(edu.footballteam.Position.FORWARD);
        
        Player player2 = factory.createPlayer();
        player2.setName("Levent");
        player2.setAge(24);
        player2.setPosition(edu.footballteam.Position.MIDFIELD);
        
        Player player3 = factory.createPlayer();
        player3.setName("Mehmet");
        player3.setAge(26);
        player3.setPosition(edu.footballteam.Position.DEFENSE);
        
        // Create spare players
        Player sparePlayer1 = factory.createPlayer();
        sparePlayer1.setName("Nihat");
        sparePlayer1.setAge(35);
        sparePlayer1.setPosition(edu.footballteam.Position.FORWARD);
        
        Player sparePlayer2 = factory.createPlayer();
        sparePlayer2.setName("Onur");
        sparePlayer2.setAge(22);
        sparePlayer2.setPosition(edu.footballteam.Position.MIDFIELD);
        
        // Add all players to the team
        team.getPlayers().add(player1);
        team.getPlayers().add(player2);
        team.getPlayers().add(player3);
        team.getPlayers().add(sparePlayer1);
        team.getPlayers().add(sparePlayer2);
        
        // Add main players to first eleven
        team.getFirstEleven().add(player1);
        team.getFirstEleven().add(player2);
        team.getFirstEleven().add(player3);
        
        // Add spare players to spare team
        team.getSpareTeam().add(sparePlayer1);
        team.getSpareTeam().add(sparePlayer2);
        
        // Calculate average age of spare team
        double averageAge = team.calculateAverageAgeOfSpareTeam();
        
        // Expected output: Average age = (35 + 22) / 2 = 28.5
        assertEquals(28.5, averageAge, 0.001);
    }
    
    @Test
    public void testCase4_averageAgeCalculationWithOneSparePlayer() {
        // Create a football team "Turgutlu"
        FootballTeam team = factory.createFootballTeam();
        
        // Create players
        Player player1 = factory.createPlayer();
        player1.setName("Tolga");
        player1.setAge(28);
        player1.setPosition(edu.footballteam.Position.GOALKEEPER);
        
        Player player2 = factory.createPlayer();
        player2.setName("Umut");
        player2.setAge(25);
        player2.setPosition(edu.footballteam.Position.DEFENSE);
        
        Player player3 = factory.createPlayer();
        player3.setName("Volkan");
        player3.setAge(30);
        player3.setPosition(edu.footballteam.Position.FORWARD);
        
        // Create spare player
        Player sparePlayer1 = factory.createPlayer();
        sparePlayer1.setName("Yiğit");
        sparePlayer1.setAge(31);
        sparePlayer1.setPosition(edu.footballteam.Position.FORWARD);
        
        // Add all players to the team
        team.getPlayers().add(player1);
        team.getPlayers().add(player2);
        team.getPlayers().add(player3);
        team.getPlayers().add(sparePlayer1);
        
        // Add main players to first eleven
        team.getFirstEleven().add(player1);
        team.getFirstEleven().add(player2);
        team.getFirstEleven().add(player3);
        
        // Add spare player to spare team
        team.getSpareTeam().add(sparePlayer1);
        
        // Calculate average age of spare team
        double averageAge = team.calculateAverageAgeOfSpareTeam();
        
        // Expected output: Average age = 31.0 (Only one spare player)
        assertEquals(31.0, averageAge, 0.001);
    }
    
    @Test
    public void testCase5_averageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Create a football team "Turgutlu"
        FootballTeam team = factory.createFootballTeam();
        
        // Create players
        Player player1 = factory.createPlayer();
        player1.setName("Eren");
        player1.setAge(24);
        player1.setPosition(edu.footballteam.Position.FORWARD);
        
        Player player2 = factory.createPlayer();
        player2.setName("Furkan");
        player2.setAge(26);
        player2.setPosition(edu.footballteam.Position.MIDFIELD);
        
        Player player3 = factory.createPlayer();
        player3.setName("Kadir");
        player3.setAge(28);
        player3.setPosition(edu.footballteam.Position.DEFENSE);
        
        // Create spare players with identical ages
        Player sparePlayer1 = factory.createPlayer();
        sparePlayer1.setName("Zafer");
        sparePlayer1.setAge(27);
        sparePlayer1.setPosition(edu.footballteam.Position.MIDFIELD);
        
        Player sparePlayer2 = factory.createPlayer();
        sparePlayer2.setName("Burak");
        sparePlayer2.setAge(27);
        sparePlayer2.setPosition(edu.footballteam.Position.MIDFIELD);
        
        // Add all players to the team
        team.getPlayers().add(player1);
        team.getPlayers().add(player2);
        team.getPlayers().add(player3);
        team.getPlayers().add(sparePlayer1);
        team.getPlayers().add(sparePlayer2);
        
        // Add main players to first eleven
        team.getFirstEleven().add(player1);
        team.getFirstEleven().add(player2);
        team.getFirstEleven().add(player3);
        
        // Add spare players to spare team
        team.getSpareTeam().add(sparePlayer1);
        team.getSpareTeam().add(sparePlayer2);
        
        // Calculate average age of spare team
        double averageAge = team.calculateAverageAgeOfSpareTeam();
        
        // Expected output: Average age = (27 + 27) / 2 = 27.0
        assertEquals(27.0, averageAge, 0.001);
    }
}