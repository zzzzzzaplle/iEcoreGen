import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam("Turgutlu");
    }
    
    @Test
    public void testCase1_AverageAgeCalculationWithAllPlayers() {
        // Create players
        FootballPlayer player1 = new FootballPlayer("Turgutlu", "John", 25, 1, Position.MIDFIELD);
        FootballPlayer player2 = new FootballPlayer("Turgutlu", "Alex", 28, 2, Position.FORWARD);
        FootballPlayer player3 = new FootballPlayer("Turgutlu", "Sam", 30, 3, Position.DEFENSE);
        FootballPlayer spare1 = new FootballPlayer("Turgutlu", "Mike", 26, 4, Position.MIDFIELD);
        FootballPlayer spare2 = new FootballPlayer("Turgutlu", "Karl", 24, 5, Position.FORWARD);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(spare1);
        team.addPlayer(spare2);
        
        // Set starting eleven (first three players)
        team.setPlayerAsStarter(player1);
        team.setPlayerAsStarter(player2);
        team.setPlayerAsStarter(player3);
        
        // Set spare team (last two players)
        team.setPlayerAsSpare(spare1);
        team.setPlayerAsSpare(spare2);
        
        // Calculate average age and verify expected result
        double expectedAverage = (26.0 + 24.0) / 2;
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 25.0", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // Create players
        FootballPlayer player1 = new FootballPlayer("Turgutlu", "Fatih", 27, 1, Position.GOALKEEPER);
        FootballPlayer player2 = new FootballPlayer("Turgutlu", "Gökhan", 25, 2, Position.DEFENSE);
        FootballPlayer player3 = new FootballPlayer("Turgutlu", "Hakan", 29, 3, Position.MIDFIELD);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        
        // Set all players as starters (no spare players)
        team.setPlayerAsStarter(player1);
        team.setPlayerAsStarter(player2);
        team.setPlayerAsStarter(player3);
        
        // Calculate average age and verify expected result (0.0 for empty spare team)
        double expectedAverage = 0.0;
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 0.0 when no spare players", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // Create players
        FootballPlayer player1 = new FootballPlayer("Turgutlu", "Kerem", 22, 1, Position.FORWARD);
        FootballPlayer player2 = new FootballPlayer("Turgutlu", "Levent", 24, 2, Position.MIDFIELD);
        FootballPlayer player3 = new FootballPlayer("Turgutlu", "Mehmet", 26, 3, Position.DEFENSE);
        FootballPlayer spare1 = new FootballPlayer("Turgutlu", "Nihat", 35, 4, Position.FORWARD);
        FootballPlayer spare2 = new FootballPlayer("Turgutlu", "Onur", 22, 5, Position.MIDFIELD);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(spare1);
        team.addPlayer(spare2);
        
        // Set starting eleven (first three players)
        team.setPlayerAsStarter(player1);
        team.setPlayerAsStarter(player2);
        team.setPlayerAsStarter(player3);
        
        // Set spare team (last two players)
        team.setPlayerAsSpare(spare1);
        team.setPlayerAsSpare(spare2);
        
        // Calculate average age and verify expected result
        double expectedAverage = (35.0 + 22.0) / 2;
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 28.5", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // Create players
        FootballPlayer player1 = new FootballPlayer("Turgutlu", "Tolga", 28, 1, Position.GOALKEEPER);
        FootballPlayer player2 = new FootballPlayer("Turgutlu", "Umut", 25, 2, Position.DEFENSE);
        FootballPlayer player3 = new FootballPlayer("Turgutlu", "Volkan", 30, 3, Position.FORWARD);
        FootballPlayer spare1 = new FootballPlayer("Turgutlu", "Yiğit", 31, 4, Position.FORWARD);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(spare1);
        
        // Set starting eleven (first three players)
        team.setPlayerAsStarter(player1);
        team.setPlayerAsStarter(player2);
        team.setPlayerAsStarter(player3);
        
        // Set spare team (last player)
        team.setPlayerAsSpare(spare1);
        
        // Calculate average age and verify expected result
        double expectedAverage = 31.0;
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 31.0", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Create players
        FootballPlayer player1 = new FootballPlayer("Turgutlu", "Eren", 24, 1, Position.FORWARD);
        FootballPlayer player2 = new FootballPlayer("Turgutlu", "Furkan", 26, 2, Position.MIDFIELD);
        FootballPlayer player3 = new FootballPlayer("Turgutlu", "Kadir", 28, 3, Position.DEFENSE);
        FootballPlayer spare1 = new FootballPlayer("Turgutlu", "Zafer", 27, 4, Position.MIDFIELD);
        FootballPlayer spare2 = new FootballPlayer("Turgutlu", "Burak", 27, 5, Position.MIDFIELD);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(spare1);
        team.addPlayer(spare2);
        
        // Set starting eleven (first three players)
        team.setPlayerAsStarter(player1);
        team.setPlayerAsStarter(player2);
        team.setPlayerAsStarter(player3);
        
        // Set spare team (last two players)
        team.setPlayerAsSpare(spare1);
        team.setPlayerAsSpare(spare2);
        
        // Calculate average age and verify expected result
        double expectedAverage = (27.0 + 27.0) / 2;
        double actualAverage = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 27.0", expectedAverage, actualAverage, 0.001);
    }
}