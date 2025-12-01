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
    }
    
    @Test
    public void testCase1_AverageAgeCalculationWithAllPlayers() {
        // Create players for the team
        List<FootballPlayer> allPlayers = new ArrayList<>();
        List<FootballPlayer> startingEleven = new ArrayList<>();
        List<FootballPlayer> spareTeam = new ArrayList<>();
        
        // Create starting eleven players
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("John");
        player1.setAge(25);
        player1.setPosition(Position.MIDFIELD);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Alex");
        player2.setAge(28);
        player2.setPosition(Position.FORWARD);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Sam");
        player3.setAge(30);
        player3.setPosition(Position.DEFENSE);
        
        startingEleven.add(player1);
        startingEleven.add(player2);
        startingEleven.add(player3);
        
        // Create spare team players
        FootballPlayer spare1 = new FootballPlayer();
        spare1.setName("Mike");
        spare1.setAge(26);
        
        FootballPlayer spare2 = new FootballPlayer();
        spare2.setName("Karl");
        spare2.setAge(24);
        
        spareTeam.add(spare1);
        spareTeam.add(spare2);
        
        // Add all players to the main players list
        allPlayers.addAll(startingEleven);
        allPlayers.addAll(spareTeam);
        
        // Set up the team
        team.setPlayers(allPlayers);
        team.setStartingEleven(startingEleven);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double result = team.calculateAverageAgeSpareTeam();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // Create players for the team
        List<FootballPlayer> allPlayers = new ArrayList<>();
        List<FootballPlayer> startingEleven = new ArrayList<>();
        List<FootballPlayer> spareTeam = new ArrayList<>();
        
        // Create starting eleven players (all are in first eleven)
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Fatih");
        player1.setAge(27);
        player1.setPosition(Position.DEFENSE);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Gökhan");
        player2.setAge(25);
        player2.setPosition(Position.DEFENSE);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Hakan");
        player3.setAge(29);
        player3.setPosition(Position.MIDFIELD);
        
        startingEleven.add(player1);
        startingEleven.add(player2);
        startingEleven.add(player3);
        
        // Set up the team with empty spare team
        team.setPlayers(allPlayers);
        team.setStartingEleven(startingEleven);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double result = team.calculateAverageAgeSpareTeam();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // Create players for the team
        List<FootballPlayer> allPlayers = new ArrayList<>();
        List<FootballPlayer> startingEleven = new ArrayList<>();
        List<FootballPlayer> spareTeam = new ArrayList<>();
        
        // Create starting eleven players
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Kerem");
        player1.setAge(22);
        player1.setPosition(Position.FORWARD);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Levent");
        player2.setAge(24);
        player2.setPosition(Position.MIDFIELD);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Mehmet");
        player3.setAge(26);
        player3.setPosition(Position.DEFENSE);
        
        startingEleven.add(player1);
        startingEleven.add(player2);
        startingEleven.add(player3);
        
        // Create spare team players with mixed ages
        FootballPlayer spare1 = new FootballPlayer();
        spare1.setName("Nihat");
        spare1.setAge(35);
        spare1.setPosition(Position.FORWARD);
        
        FootballPlayer spare2 = new FootballPlayer();
        spare2.setName("Onur");
        spare2.setAge(22);
        spare2.setPosition(Position.MIDFIELD);
        
        spareTeam.add(spare1);
        spareTeam.add(spare2);
        
        // Add all players to the main players list
        allPlayers.addAll(startingEleven);
        allPlayers.addAll(spareTeam);
        
        // Set up the team
        team.setPlayers(allPlayers);
        team.setStartingEleven(startingEleven);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double result = team.calculateAverageAgeSpareTeam();
        assertEquals(28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // Create players for the team
        List<FootballPlayer> allPlayers = new ArrayList<>();
        List<FootballPlayer> startingEleven = new ArrayList<>();
        List<FootballPlayer> spareTeam = new ArrayList<>();
        
        // Create starting eleven players
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Tolga");
        player1.setAge(28);
        player1.setPosition(Position.DEFENSE);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Umut");
        player2.setAge(25);
        player2.setPosition(Position.DEFENSE);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Volkan");
        player3.setAge(30);
        player3.setPosition(Position.FORWARD);
        
        startingEleven.add(player1);
        startingEleven.add(player2);
        startingEleven.add(player3);
        
        // Create only one spare player
        FootballPlayer spare1 = new FootballPlayer();
        spare1.setName("Yiğit");
        spare1.setAge(31);
        spare1.setPosition(Position.FORWARD);
        
        spareTeam.add(spare1);
        
        // Add all players to the main players list
        allPlayers.addAll(startingEleven);
        allPlayers.addAll(spareTeam);
        
        // Set up the team
        team.setPlayers(allPlayers);
        team.setStartingEleven(startingEleven);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double result = team.calculateAverageAgeSpareTeam();
        assertEquals(31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Create players for the team
        List<FootballPlayer> allPlayers = new ArrayList<>();
        List<FootballPlayer> startingEleven = new ArrayList<>();
        List<FootballPlayer> spareTeam = new ArrayList<>();
        
        // Create starting eleven players
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Eren");
        player1.setAge(24);
        player1.setPosition(Position.FORWARD);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Furkan");
        player2.setAge(26);
        player2.setPosition(Position.MIDFIELD);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Kadir");
        player3.setAge(28);
        player3.setPosition(Position.DEFENSE);
        
        startingEleven.add(player1);
        startingEleven.add(player2);
        startingEleven.add(player3);
        
        // Create spare team players with identical ages
        FootballPlayer spare1 = new FootballPlayer();
        spare1.setName("Zafer");
        spare1.setAge(27);
        spare1.setPosition(Position.MIDFIELD);
        
        FootballPlayer spare2 = new FootballPlayer();
        spare2.setName("Burak");
        spare2.setAge(27);
        spare2.setPosition(Position.MIDFIELD);
        
        spareTeam.add(spare1);
        spareTeam.add(spare2);
        
        // Add all players to the main players list
        allPlayers.addAll(startingEleven);
        allPlayers.addAll(spareTeam);
        
        // Set up the team
        team.setPlayers(allPlayers);
        team.setStartingEleven(startingEleven);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double result = team.calculateAverageAgeSpareTeam();
        assertEquals(27.0, result, 0.001);
    }
}