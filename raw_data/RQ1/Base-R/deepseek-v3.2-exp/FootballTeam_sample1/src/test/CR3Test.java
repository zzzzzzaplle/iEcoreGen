import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        team.setName("Turgutlu");
    }
    
    @Test
    public void testCase1_AverageAgeCalculationWithAllPlayers() {
        // Set up players for starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("John");
        player1.setAge(25);
        player1.setPosition(Position.MIDFIELD);
        startingEleven.add(player1);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Alex");
        player2.setAge(28);
        player2.setPosition(Position.FORWARD);
        startingEleven.add(player2);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Sam");
        player3.setAge(30);
        player3.setPosition(Position.DEFENSE);
        startingEleven.add(player3);
        
        // Set up spare team players
        List<FootballPlayer> spareTeam = new ArrayList<>();
        
        FootballPlayer spare1 = new FootballPlayer();
        spare1.setName("Mike");
        spare1.setAge(26);
        spareTeam.add(spare1);
        
        FootballPlayer spare2 = new FootballPlayer();
        spare2.setName("Karl");
        spare2.setAge(24);
        spareTeam.add(spare2);
        
        // Combine all players
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.addAll(startingEleven);
        allPlayers.addAll(spareTeam);
        
        // Set up the team
        team.setPlayers(allPlayers);
        team.setStartingEleven(startingEleven);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Verify the result: (26 + 24) / 2 = 25.0
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // Set up players for starting eleven (no spare players)
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Fatih");
        player1.setAge(27);
        player1.setPosition(Position.GOALKEEPER);
        startingEleven.add(player1);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Gökhan");
        player2.setAge(25);
        player2.setPosition(Position.DEFENSE);
        startingEleven.add(player2);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Hakan");
        player3.setAge(29);
        player3.setPosition(Position.MIDFIELD);
        startingEleven.add(player3);
        
        // Set up the team with empty spare team
        team.setPlayers(startingEleven);
        team.setStartingEleven(startingEleven);
        team.setSpareTeam(new ArrayList<FootballPlayer>());
        
        // Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Verify the result: 0.0 (no spare players)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // Set up players for starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Kerem");
        player1.setAge(22);
        player1.setPosition(Position.FORWARD);
        startingEleven.add(player1);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Levent");
        player2.setAge(24);
        player2.setPosition(Position.MIDFIELD);
        startingEleven.add(player2);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Mehmet");
        player3.setAge(26);
        player3.setPosition(Position.DEFENSE);
        startingEleven.add(player3);
        
        // Set up spare team players
        List<FootballPlayer> spareTeam = new ArrayList<>();
        
        FootballPlayer spare1 = new FootballPlayer();
        spare1.setName("Nihat");
        spare1.setAge(35);
        spare1.setPosition(Position.FORWARD);
        spareTeam.add(spare1);
        
        FootballPlayer spare2 = new FootballPlayer();
        spare2.setName("Onur");
        spare2.setAge(22);
        spare2.setPosition(Position.MIDFIELD);
        spareTeam.add(spare2);
        
        // Combine all players
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.addAll(startingEleven);
        allPlayers.addAll(spareTeam);
        
        // Set up the team
        team.setPlayers(allPlayers);
        team.setStartingEleven(startingEleven);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Verify the result: (35 + 22) / 2 = 28.5
        assertEquals(28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // Set up players for starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Tolga");
        player1.setAge(28);
        player1.setPosition(Position.GOALKEEPER);
        startingEleven.add(player1);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Umut");
        player2.setAge(25);
        player2.setPosition(Position.DEFENSE);
        startingEleven.add(player2);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Volkan");
        player3.setAge(30);
        player3.setPosition(Position.FORWARD);
        startingEleven.add(player3);
        
        // Set up spare team players (only one player)
        List<FootballPlayer> spareTeam = new ArrayList<>();
        
        FootballPlayer spare1 = new FootballPlayer();
        spare1.setName("Yiğit");
        spare1.setAge(31);
        spare1.setPosition(Position.FORWARD);
        spareTeam.add(spare1);
        
        // Combine all players
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.addAll(startingEleven);
        allPlayers.addAll(spareTeam);
        
        // Set up the team
        team.setPlayers(allPlayers);
        team.setStartingEleven(startingEleven);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Verify the result: 31.0 (only one spare player)
        assertEquals(31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Set up players for starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Eren");
        player1.setAge(24);
        player1.setPosition(Position.FORWARD);
        startingEleven.add(player1);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Furkan");
        player2.setAge(26);
        player2.setPosition(Position.MIDFIELD);
        startingEleven.add(player2);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Kadir");
        player3.setAge(28);
        player3.setPosition(Position.DEFENSE);
        startingEleven.add(player3);
        
        // Set up spare team players with identical ages
        List<FootballPlayer> spareTeam = new ArrayList<>();
        
        FootballPlayer spare1 = new FootballPlayer();
        spare1.setName("Zafer");
        spare1.setAge(27);
        spare1.setPosition(Position.MIDFIELD);
        spareTeam.add(spare1);
        
        FootballPlayer spare2 = new FootballPlayer();
        spare2.setName("Burak");
        spare2.setAge(27);
        spare2.setPosition(Position.MIDFIELD);
        spareTeam.add(spare2);
        
        // Combine all players
        List<FootballPlayer> allPlayers = new ArrayList<>();
        allPlayers.addAll(startingEleven);
        allPlayers.addAll(spareTeam);
        
        // Set up the team
        team.setPlayers(allPlayers);
        team.setStartingEleven(startingEleven);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Verify the result: (27 + 27) / 2 = 27.0
        assertEquals(27.0, result, 0.001);
    }
}