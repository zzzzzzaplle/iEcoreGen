import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Team team;
    
    @Before
    public void setUp() {
        team = new Team();
        team.setName("Turgutlu");
    }
    
    @Test
    public void testCase1_AverageAgeCalculationWithAllPlayers() {
        // Create players for starting eleven
        Midfielder player1 = new Midfielder();
        player1.setName("John");
        player1.setAge(25);
        player1.setNumber(1);
        
        Striker player2 = new Striker();
        player2.setName("Alex");
        player2.setAge(28);
        player2.setNumber(2);
        
        Goalkeeper player3 = new Goalkeeper();
        player3.setName("Sam");
        player3.setAge(30);
        player3.setNumber(3);
        
        // Create spare players
        FootballPlayer spare1 = new Midfielder();
        spare1.setName("Mike");
        spare1.setAge(26);
        spare1.setNumber(4);
        
        FootballPlayer spare2 = new Midfielder();
        spare2.setName("Karl");
        spare2.setAge(24);
        spare2.setNumber(5);
        
        // Add starting eleven players
        team.addPlayer(player1, true);
        team.addPlayer(player2, true);
        team.addPlayer(player3, true);
        
        // Add spare players
        team.addPlayer(spare1, false);
        team.addPlayer(spare2, false);
        
        // Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Verify the result: (26 + 24) / 2 = 25.0
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // Create only starting eleven players
        Goalkeeper player1 = new Goalkeeper();
        player1.setName("Fatih");
        player1.setAge(27);
        player1.setNumber(1);
        
        Goalkeeper player2 = new Goalkeeper();
        player2.setName("Gökhan");
        player2.setAge(25);
        player2.setNumber(2);
        
        Midfielder player3 = new Midfielder();
        player3.setName("Hakan");
        player3.setAge(29);
        player3.setNumber(3);
        
        // Add all players as starters
        team.addPlayer(player1, true);
        team.addPlayer(player2, true);
        team.addPlayer(player3, true);
        
        // Calculate average age of spare team (should be empty)
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Verify the result: 0.0 (no spare players)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // Create starting eleven players
        Striker player1 = new Striker();
        player1.setName("Kerem");
        player1.setAge(22);
        player1.setNumber(1);
        
        Midfielder player2 = new Midfielder();
        player2.setName("Levent");
        player2.setAge(24);
        player2.setNumber(2);
        
        Goalkeeper player3 = new Goalkeeper();
        player3.setName("Mehmet");
        player3.setAge(26);
        player3.setNumber(3);
        
        // Create spare players with mixed ages
        Striker spare1 = new Striker();
        spare1.setName("Nihat");
        spare1.setAge(35);
        spare1.setNumber(4);
        
        Midfielder spare2 = new Midfielder();
        spare2.setName("Onur");
        spare2.setAge(22);
        spare2.setNumber(5);
        
        // Add starting eleven players
        team.addPlayer(player1, true);
        team.addPlayer(player2, true);
        team.addPlayer(player3, true);
        
        // Add spare players
        team.addPlayer(spare1, false);
        team.addPlayer(spare2, false);
        
        // Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Verify the result: (35 + 22) / 2 = 28.5
        assertEquals(28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // Create starting eleven players
        Goalkeeper player1 = new Goalkeeper();
        player1.setName("Tolga");
        player1.setAge(28);
        player1.setNumber(1);
        
        Goalkeeper player2 = new Goalkeeper();
        player2.setName("Umut");
        player2.setAge(25);
        player2.setNumber(2);
        
        Striker player3 = new Striker();
        player3.setName("Volkan");
        player3.setAge(30);
        player3.setNumber(3);
        
        // Create only one spare player
        Striker spare1 = new Striker();
        spare1.setName("Yiğit");
        spare1.setAge(31);
        spare1.setNumber(4);
        
        // Add starting eleven players
        team.addPlayer(player1, true);
        team.addPlayer(player2, true);
        team.addPlayer(player3, true);
        
        // Add single spare player
        team.addPlayer(spare1, false);
        
        // Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Verify the result: 31.0 (only one spare player)
        assertEquals(31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Create starting eleven players
        Striker player1 = new Striker();
        player1.setName("Eren");
        player1.setAge(24);
        player1.setNumber(1);
        
        Midfielder player2 = new Midfielder();
        player2.setName("Furkan");
        player2.setAge(26);
        player2.setNumber(2);
        
        Goalkeeper player3 = new Goalkeeper();
        player3.setName("Kadir");
        player3.setAge(28);
        player3.setNumber(3);
        
        // Create spare players with identical ages
        Midfielder spare1 = new Midfielder();
        spare1.setName("Zafer");
        spare1.setAge(27);
        spare1.setNumber(4);
        
        Midfielder spare2 = new Midfielder();
        spare2.setName("Burak");
        spare2.setAge(27);
        spare2.setNumber(5);
        
        // Add starting eleven players
        team.addPlayer(player1, true);
        team.addPlayer(player2, true);
        team.addPlayer(player3, true);
        
        // Add spare players
        team.addPlayer(spare1, false);
        team.addPlayer(spare2, false);
        
        // Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Verify the result: (27 + 27) / 2 = 27.0
        assertEquals(27.0, result, 0.001);
    }
}