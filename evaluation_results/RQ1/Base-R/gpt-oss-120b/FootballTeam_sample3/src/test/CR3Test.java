import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Team team;
    
    @Before
    public void setUp() {
        team = new Team("Turgutlu");
    }
    
    @Test
    public void testCase1_AverageAgeCalculationWithAllPlayers() {
        // Create players
        FootballPlayer player1 = new Midfielder("Turgutlu", "John", 25, 1);
        FootballPlayer player2 = new Forward("Turgutlu", "Alex", 28, 2);
        FootballPlayer player3 = new Goalkeeper("Turgutlu", "Sam", 30, 3);
        FootballPlayer sparePlayer1 = new FootballPlayer("Turgutlu", "Mike", 26, 4, Position.MIDFIELD);
        FootballPlayer sparePlayer2 = new FootballPlayer("Turgutlu", "Karl", 24, 5, Position.FORWARD);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(sparePlayer1);
        team.addPlayer(sparePlayer2);
        
        // Set starting eleven
        team.addToStartingEleven(player1);
        team.addToStartingEleven(player2);
        team.addToStartingEleven(player3);
        
        // Set spare team
        team.addToSpareTeam(sparePlayer1);
        team.addToSpareTeam(sparePlayer2);
        
        // Calculate average age of spare team
        double result = team.averageAgeSpareTeam();
        
        // Expected: (26 + 24) / 2 = 25.0
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // Create players
        FootballPlayer player1 = new Goalkeeper("Turgutlu", "Fatih", 27, 1);
        FootballPlayer player2 = new FootballPlayer("Turgutlu", "Gökhan", 25, 2, Position.DEFENSE);
        FootballPlayer player3 = new Midfielder("Turgutlu", "Hakan", 29, 3);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        
        // Set all players as starting eleven
        team.addToStartingEleven(player1);
        team.addToStartingEleven(player2);
        team.addToStartingEleven(player3);
        
        // Calculate average age of spare team (empty)
        double result = team.averageAgeSpareTeam();
        
        // Expected: 0.0 (No spare players available)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // Create players
        FootballPlayer player1 = new Forward("Turgutlu", "Kerem", 22, 1);
        FootballPlayer player2 = new Midfielder("Turgutlu", "Levent", 24, 2);
        FootballPlayer player3 = new Goalkeeper("Turgutlu", "Mehmet", 26, 3);
        FootballPlayer sparePlayer1 = new Forward("Turgutlu", "Nihat", 35, 4);
        FootballPlayer sparePlayer2 = new Midfielder("Turgutlu", "Onur", 22, 5);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(sparePlayer1);
        team.addPlayer(sparePlayer2);
        
        // Set starting eleven
        team.addToStartingEleven(player1);
        team.addToStartingEleven(player2);
        team.addToStartingEleven(player3);
        
        // Set spare team
        team.addToSpareTeam(sparePlayer1);
        team.addToSpareTeam(sparePlayer2);
        
        // Calculate average age of spare team
        double result = team.averageAgeSpareTeam();
        
        // Expected: (35 + 22) / 2 = 28.5
        assertEquals(28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // Create players
        FootballPlayer player1 = new Goalkeeper("Turgutlu", "Tolga", 28, 1);
        FootballPlayer player2 = new FootballPlayer("Turgutlu", "Umut", 25, 2, Position.DEFENSE);
        FootballPlayer player3 = new Forward("Turgutlu", "Volkan", 30, 3);
        FootballPlayer sparePlayer1 = new Forward("Turgutlu", "Yiğit", 31, 4);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(sparePlayer1);
        
        // Set starting eleven
        team.addToStartingEleven(player1);
        team.addToStartingEleven(player2);
        team.addToStartingEleven(player3);
        
        // Set spare team (only one player)
        team.addToSpareTeam(sparePlayer1);
        
        // Calculate average age of spare team
        double result = team.averageAgeSpareTeam();
        
        // Expected: 31.0 (Only one spare player)
        assertEquals(31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Create players
        FootballPlayer player1 = new Forward("Turgutlu", "Eren", 24, 1);
        FootballPlayer player2 = new Midfielder("Turgutlu", "Furkan", 26, 2);
        FootballPlayer player3 = new Goalkeeper("Turgutlu", "Kadir", 28, 3);
        FootballPlayer sparePlayer1 = new Midfielder("Turgutlu", "Zafer", 27, 4);
        FootballPlayer sparePlayer2 = new Midfielder("Turgutlu", "Burak", 27, 5);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(sparePlayer1);
        team.addPlayer(sparePlayer2);
        
        // Set starting eleven
        team.addToStartingEleven(player1);
        team.addToStartingEleven(player2);
        team.addToStartingEleven(player3);
        
        // Set spare team
        team.addToSpareTeam(sparePlayer1);
        team.addToSpareTeam(sparePlayer2);
        
        // Calculate average age of spare team
        double result = team.averageAgeSpareTeam();
        
        // Expected: (27 + 27) / 2 = 27.0
        assertEquals(27.0, result, 0.001);
    }
}