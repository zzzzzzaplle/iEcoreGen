import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private FootballTeam footballTeam;
    
    @Before
    public void setUp() {
        footballTeam = new FootballTeam();
    }
    
    @Test
    public void testCase1_AverageAgeCalculationWithAllPlayers() {
        // Set up players
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("John");
        player1.setAge(25);
        player1.setTeam("Turgutlu");
        player1.setPosition(Position.MIDFIELD);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Alex");
        player2.setAge(28);
        player2.setTeam("Turgutlu");
        player2.setPosition(Position.FORWARD);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Sam");
        player3.setAge(30);
        player3.setTeam("Turgutlu");
        player3.setPosition(Position.DEFENSE);
        
        FootballPlayer sparePlayer1 = new FootballPlayer();
        sparePlayer1.setName("Mike");
        sparePlayer1.setAge(26);
        sparePlayer1.setTeam("Turgutlu");
        
        FootballPlayer sparePlayer2 = new FootballPlayer();
        sparePlayer2.setName("Karl");
        sparePlayer2.setAge(24);
        sparePlayer2.setTeam("Turgutlu");
        
        // Set up starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(player1);
        startingEleven.add(player2);
        startingEleven.add(player3);
        footballTeam.setStartingEleven(startingEleven);
        
        // Set up spare team
        List<FootballPlayer> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        footballTeam.setSpareTeam(spareTeam);
        
        // Set up all players
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.add(player1);
        allPlayers.add(player2);
        allPlayers.add(player3);
        allPlayers.add(sparePlayer1);
        allPlayers.add(sparePlayer2);
        footballTeam.setAllPlayers(allPlayers);
        
        // Calculate average age and verify
        double result = footballTeam.calculateAverageAgeOfSpareTeamPlayers();
        assertEquals("Average age should be 25.0", 25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // Set up players
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Fatih");
        player1.setAge(27);
        player1.setTeam("Turgutlu");
        player1.setPosition(Position.GOALKEEPER);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Gökhan");
        player2.setAge(25);
        player2.setTeam("Turgutlu");
        player2.setPosition(Position.DEFENSE);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Hakan");
        player3.setAge(29);
        player3.setTeam("Turgutlu");
        player3.setPosition(Position.MIDFIELD);
        
        // Set up starting eleven (all players)
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(player1);
        startingEleven.add(player2);
        startingEleven.add(player3);
        footballTeam.setStartingEleven(startingEleven);
        
        // Set up all players
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.add(player1);
        allPlayers.add(player2);
        allPlayers.add(player3);
        footballTeam.setAllPlayers(allPlayers);
        
        // Spare team remains empty
        footballTeam.setSpareTeam(new ArrayList<>());
        
        // Calculate average age and verify
        double result = footballTeam.calculateAverageAgeOfSpareTeamPlayers();
        assertEquals("Average age should be 0.0 when no spare players", 0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // Set up players
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Kerem");
        player1.setAge(22);
        player1.setTeam("Turgutlu");
        player1.setPosition(Position.FORWARD);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Levent");
        player2.setAge(24);
        player2.setTeam("Turgutlu");
        player2.setPosition(Position.MIDFIELD);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Mehmet");
        player3.setAge(26);
        player3.setTeam("Turgutlu");
        player3.setPosition(Position.DEFENSE);
        
        FootballPlayer sparePlayer1 = new FootballPlayer();
        sparePlayer1.setName("Nihat");
        sparePlayer1.setAge(35);
        sparePlayer1.setTeam("Turgutlu");
        sparePlayer1.setPosition(Position.FORWARD);
        
        FootballPlayer sparePlayer2 = new FootballPlayer();
        sparePlayer2.setName("Onur");
        sparePlayer2.setAge(22);
        sparePlayer2.setTeam("Turgutlu");
        sparePlayer2.setPosition(Position.MIDFIELD);
        
        // Set up starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(player1);
        startingEleven.add(player2);
        startingEleven.add(player3);
        footballTeam.setStartingEleven(startingEleven);
        
        // Set up spare team
        List<FootballPlayer> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        footballTeam.setSpareTeam(spareTeam);
        
        // Set up all players
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.add(player1);
        allPlayers.add(player2);
        allPlayers.add(player3);
        allPlayers.add(sparePlayer1);
        allPlayers.add(sparePlayer2);
        footballTeam.setAllPlayers(allPlayers);
        
        // Calculate average age and verify
        double result = footballTeam.calculateAverageAgeOfSpareTeamPlayers();
        assertEquals("Average age should be 28.5", 28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // Set up players
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Tolga");
        player1.setAge(28);
        player1.setTeam("Turgutlu");
        player1.setPosition(Position.GOALKEEPER);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Umut");
        player2.setAge(25);
        player2.setTeam("Turgutlu");
        player2.setPosition(Position.DEFENSE);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Volkan");
        player3.setAge(30);
        player3.setTeam("Turgutlu");
        player3.setPosition(Position.FORWARD);
        
        FootballPlayer sparePlayer1 = new FootballPlayer();
        sparePlayer1.setName("Yiğit");
        sparePlayer1.setAge(31);
        sparePlayer1.setTeam("Turgutlu");
        sparePlayer1.setPosition(Position.FORWARD);
        
        // Set up starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(player1);
        startingEleven.add(player2);
        startingEleven.add(player3);
        footballTeam.setStartingEleven(startingEleven);
        
        // Set up spare team
        List<FootballPlayer> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        footballTeam.setSpareTeam(spareTeam);
        
        // Set up all players
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.add(player1);
        allPlayers.add(player2);
        allPlayers.add(player3);
        allPlayers.add(sparePlayer1);
        footballTeam.setAllPlayers(allPlayers);
        
        // Calculate average age and verify
        double result = footballTeam.calculateAverageAgeOfSpareTeamPlayers();
        assertEquals("Average age should be 31.0", 31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Set up players
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Eren");
        player1.setAge(24);
        player1.setTeam("Turgutlu");
        player1.setPosition(Position.FORWARD);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Furkan");
        player2.setAge(26);
        player2.setTeam("Turgutlu");
        player2.setPosition(Position.MIDFIELD);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Kadir");
        player3.setAge(28);
        player3.setTeam("Turgutlu");
        player3.setPosition(Position.DEFENSE);
        
        FootballPlayer sparePlayer1 = new FootballPlayer();
        sparePlayer1.setName("Zafer");
        sparePlayer1.setAge(27);
        sparePlayer1.setTeam("Turgutlu");
        sparePlayer1.setPosition(Position.MIDFIELD);
        
        FootballPlayer sparePlayer2 = new FootballPlayer();
        sparePlayer2.setName("Burak");
        sparePlayer2.setAge(27);
        sparePlayer2.setTeam("Turgutlu");
        sparePlayer2.setPosition(Position.MIDFIELD);
        
        // Set up starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(player1);
        startingEleven.add(player2);
        startingEleven.add(player3);
        footballTeam.setStartingEleven(startingEleven);
        
        // Set up spare team
        List<FootballPlayer> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        footballTeam.setSpareTeam(spareTeam);
        
        // Set up all players
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.add(player1);
        allPlayers.add(player2);
        allPlayers.add(player3);
        allPlayers.add(sparePlayer1);
        allPlayers.add(sparePlayer2);
        footballTeam.setAllPlayers(allPlayers);
        
        // Calculate average age and verify
        double result = footballTeam.calculateAverageAgeOfSpareTeamPlayers();
        assertEquals("Average age should be 27.0", 27.0, result, 0.001);
    }
}