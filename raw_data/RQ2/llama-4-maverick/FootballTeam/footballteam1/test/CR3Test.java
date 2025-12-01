package edu.footballteam.footballteam1.test;

import edu.footballteam.FootballTeam;
import edu.footballteam.FootballteamFactory;
import edu.footballteam.Player;
import edu.footballteam.Position;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR3Test {

    private FootballteamFactory factory;
    private FootballTeam footballTeam;

    @Before
    public void setUp() {
        factory = FootballteamFactory.eINSTANCE;
        footballTeam = factory.createFootballTeam();
    }

    @Test
    public void testCase1_averageAgeCalculationWithAllPlayers() {
        // Create players
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

        // Create spare players
        Player sparePlayer1 = factory.createPlayer();
        sparePlayer1.setName("Mike");
        sparePlayer1.setAge(26);

        Player sparePlayer2 = factory.createPlayer();
        sparePlayer2.setName("Karl");
        sparePlayer2.setAge(24);

        // Add all players to the team
        footballTeam.getPlayers().add(player1);
        footballTeam.getPlayers().add(player2);
        footballTeam.getPlayers().add(player3);
        footballTeam.getPlayers().add(sparePlayer1);
        footballTeam.getPlayers().add(sparePlayer2);

        // Add to first eleven (players 1, 2, 3)
        footballTeam.getFirstEleven().add(player1);
        footballTeam.getFirstEleven().add(player2);
        footballTeam.getFirstEleven().add(player3);

        // Add spare players to spare team
        footballTeam.getSpareTeam().add(sparePlayer1);
        footballTeam.getSpareTeam().add(sparePlayer2);

        // Calculate average age of spare team
        double averageAge = footballTeam.calculateAverageAgeOfSpareTeam();

        // Assert the expected result
        assertEquals(25.0, averageAge, 0.001);
    }

    @Test
    public void testCase2_averageAgeCalculationWithNoSparePlayers() {
        // Create players
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

        // Add all players to the team
        footballTeam.getPlayers().add(player1);
        footballTeam.getPlayers().add(player2);
        footballTeam.getPlayers().add(player3);

        // Add all players to first eleven (no spare players)
        footballTeam.getFirstEleven().add(player1);
        footballTeam.getFirstEleven().add(player2);
        footballTeam.getFirstEleven().add(player3);

        // Calculate average age of spare team
        double averageAge = footballTeam.calculateAverageAgeOfSpareTeam();

        // Assert the expected result
        assertEquals(0.0, averageAge, 0.001);
    }

    @Test
    public void testCase3_averageAgeCalculationWithMixedAgeSparePlayers() {
        // Create players
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

        // Create spare players
        Player sparePlayer1 = factory.createPlayer();
        sparePlayer1.setName("Nihat");
        sparePlayer1.setAge(35);
        sparePlayer1.setPosition(Position.FORWARD);

        Player sparePlayer2 = factory.createPlayer();
        sparePlayer2.setName("Onur");
        sparePlayer2.setAge(22);
        sparePlayer2.setPosition(Position.MIDFIELD);

        // Add all players to the team
        footballTeam.getPlayers().add(player1);
        footballTeam.getPlayers().add(player2);
        footballTeam.getPlayers().add(player3);
        footballTeam.getPlayers().add(sparePlayer1);
        footballTeam.getPlayers().add(sparePlayer2);

        // Add regular players to first eleven
        footballTeam.getFirstEleven().add(player1);
        footballTeam.getFirstEleven().add(player2);
        footballTeam.getFirstEleven().add(player3);

        // Add spare players to spare team
        footballTeam.getSpareTeam().add(sparePlayer1);
        footballTeam.getSpareTeam().add(sparePlayer2);

        // Calculate average age of spare team
        double averageAge = footballTeam.calculateAverageAgeOfSpareTeam();

        // Assert the expected result
        assertEquals(28.5, averageAge, 0.001);
    }

    @Test
    public void testCase4_averageAgeCalculationWithOneSparePlayer() {
        // Create players
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

        // Create spare player
        Player sparePlayer1 = factory.createPlayer();
        sparePlayer1.setName("Yiğit");
        sparePlayer1.setAge(31);
        sparePlayer1.setPosition(Position.FORWARD);

        // Add all players to the team
        footballTeam.getPlayers().add(player1);
        footballTeam.getPlayers().add(player2);
        footballTeam.getPlayers().add(player3);
        footballTeam.getPlayers().add(sparePlayer1);

        // Add regular players to first eleven
        footballTeam.getFirstEleven().add(player1);
        footballTeam.getFirstEleven().add(player2);
        footballTeam.getFirstEleven().add(player3);

        // Add spare player to spare team
        footballTeam.getSpareTeam().add(sparePlayer1);

        // Calculate average age of spare team
        double averageAge = footballTeam.calculateAverageAgeOfSpareTeam();

        // Assert the expected result
        assertEquals(31.0, averageAge, 0.001);
    }

    @Test
    public void testCase5_averageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Create players
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

        // Create spare players with identical ages
        Player sparePlayer1 = factory.createPlayer();
        sparePlayer1.setName("Zafer");
        sparePlayer1.setAge(27);
        sparePlayer1.setPosition(Position.MIDFIELD);

        Player sparePlayer2 = factory.createPlayer();
        sparePlayer2.setName("Burak");
        sparePlayer2.setAge(27);
        sparePlayer2.setPosition(Position.MIDFIELD);

        // Add all players to the team
        footballTeam.getPlayers().add(player1);
        footballTeam.getPlayers().add(player2);
        footballTeam.getPlayers().add(player3);
        footballTeam.getPlayers().add(sparePlayer1);
        footballTeam.getPlayers().add(sparePlayer2);

        // Add regular players to first eleven
        footballTeam.getFirstEleven().add(player1);
        footballTeam.getFirstEleven().add(player2);
        footballTeam.getFirstEleven().add(player3);

        // Add spare players to spare team
        footballTeam.getSpareTeam().add(sparePlayer1);
        footballTeam.getSpareTeam().add(sparePlayer2);

        // Calculate average age of spare team
        double averageAge = footballTeam.calculateAverageAgeOfSpareTeam();

        // Assert the expected result
        assertEquals(27.0, averageAge, 0.001);
    }
}