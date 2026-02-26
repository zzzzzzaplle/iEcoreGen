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
        // Create players
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
        
        FootballPlayer spare1 = new FootballPlayer();
        spare1.setName("Mike");
        spare1.setAge(26);
        
        FootballPlayer spare2 = new FootballPlayer();
        spare2.setName("Karl");
        spare2.setAge(24);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(spare1);
        team.addPlayer(spare2);
        
        // Set starting eleven (first three players)
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(player1);
        startingEleven.add(player2);
        startingEleven.add(player3);
        team.setStartingEleven(startingEleven);
        
        // Set spare team (last two players)
        List<FootballPlayer> spareTeam = new ArrayList<>();
        spareTeam.add(spare1);
        spareTeam.add(spare2);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double expectedAverage = 25.0; // (26 + 24) / 2 = 25.0
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 25.0", expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // Create players (all in starting eleven, no spare players)
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Fatih");
        player1.setAge(27);
        player1.setPosition(Position.GOALKEEPER);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Gökhan");
        player2.setAge(25);
        player2.setPosition(Position.DEFENSE);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Hakan");
        player3.setAge(29);
        player3.setPosition(Position.MIDFIELD);
        
        // Add players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        
        // Set all players as starting eleven
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(player1);
        startingEleven.add(player2);
        startingEleven.add(player3);
        team.setStartingEleven(startingEleven);
        
        // Set empty spare team
        team.setSpareTeam(new ArrayList<FootballPlayer>());
        
        // Calculate average age and verify result
        double expectedAverage = 0.0; // No spare players
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 0.0 when no spare players", expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // Create players
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
        
        FootballPlayer spare1 = new FootballPlayer();
        spare1.setName("Nihat");
        spare1.setAge(35);
        spare1.setPosition(Position.FORWARD);
        
        FootballPlayer spare2 = new FootballPlayer();
        spare2.setName("Onur");
        spare2.setAge(22);
        spare2.setPosition(Position.MIDFIELD);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(spare1);
        team.addPlayer(spare2);
        
        // Set starting eleven (first three players)
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(player1);
        startingEleven.add(player2);
        startingEleven.add(player3);
        team.setStartingEleven(startingEleven);
        
        // Set spare team (last two players)
        List<FootballPlayer> spareTeam = new ArrayList<>();
        spareTeam.add(spare1);
        spareTeam.add(spare2);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double expectedAverage = 28.5; // (35 + 22) / 2 = 28.5
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 28.5", expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // Create players
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Tolga");
        player1.setAge(28);
        player1.setPosition(Position.GOALKEEPER);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Umut");
        player2.setAge(25);
        player2.setPosition(Position.DEFENSE);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Volkan");
        player3.setAge(30);
        player3.setPosition(Position.FORWARD);
        
        FootballPlayer spare1 = new FootballPlayer();
        spare1.setName("Yiğit");
        spare1.setAge(31);
        spare1.setPosition(Position.FORWARD);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(spare1);
        
        // Set starting eleven (first three players)
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(player1);
        startingEleven.add(player2);
        startingEleven.add(player3);
        team.setStartingEleven(startingEleven);
        
        // Set spare team (only one player)
        List<FootballPlayer> spareTeam = new ArrayList<>();
        spareTeam.add(spare1);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double expectedAverage = 31.0; // Only one spare player aged 31
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 31.0", expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Create players
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
        
        FootballPlayer spare1 = new FootballPlayer();
        spare1.setName("Zafer");
        spare1.setAge(27);
        spare1.setPosition(Position.MIDFIELD);
        
        FootballPlayer spare2 = new FootballPlayer();
        spare2.setName("Burak");
        spare2.setAge(27);
        spare2.setPosition(Position.MIDFIELD);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(spare1);
        team.addPlayer(spare2);
        
        // Set starting eleven (first three players)
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(player1);
        startingEleven.add(player2);
        startingEleven.add(player3);
        team.setStartingEleven(startingEleven);
        
        // Set spare team (last two players)
        List<FootballPlayer> spareTeam = new ArrayList<>();
        spareTeam.add(spare1);
        spareTeam.add(spare2);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double expectedAverage = 27.0; // (27 + 27) / 2 = 27.0
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 27.0", expectedAverage, actualAverage, 0.01);
    }
}