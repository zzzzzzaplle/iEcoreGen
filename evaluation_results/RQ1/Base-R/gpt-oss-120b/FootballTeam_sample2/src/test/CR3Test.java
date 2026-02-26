import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
        team.setName("Turgutlu");
    }
    
    @Test
    public void testCase1_AverageAgeCalculationWithAllPlayers() {
        // Create players for starting eleven
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("John");
        player1.setAge(25);
        player1.setTeam("Turgutlu");
        player1.setPosition(Position.MIDFIELDER);
        
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
        
        // Create spare players
        FootballPlayer sparePlayer1 = new FootballPlayer();
        sparePlayer1.setName("Mike");
        sparePlayer1.setAge(26);
        sparePlayer1.setTeam("Turgutlu");
        
        FootballPlayer sparePlayer2 = new FootballPlayer();
        sparePlayer2.setName("Karl");
        sparePlayer2.setAge(24);
        sparePlayer2.setTeam("Turgutlu");
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(sparePlayer1);
        team.addPlayer(sparePlayer2);
        
        // Add players to starting eleven
        team.addToStartingEleven(player1);
        team.addToStartingEleven(player2);
        team.addToStartingEleven(player3);
        
        // Add players to spare team
        team.addToSpareTeam(sparePlayer1);
        team.addToSpareTeam(sparePlayer2);
        
        // Calculate average age and verify result
        double expectedAverage = (26 + 24) / 2.0;
        double actualAverage = team.averageAgeOfSpareTeam();
        
        assertEquals("Average age should be 25.0", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // Create players for starting eleven (no spare players)
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
        player3.setPosition(Position.MIDFIELDER);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        
        // Add all players to starting eleven
        team.addToStartingEleven(player1);
        team.addToStartingEleven(player2);
        team.addToStartingEleven(player3);
        
        // Calculate average age and verify result (should be 0.0 for empty spare team)
        double expectedAverage = 0.0;
        double actualAverage = team.averageAgeOfSpareTeam();
        
        assertEquals("Average age should be 0.0 for empty spare team", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // Create players for starting eleven
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Kerem");
        player1.setAge(22);
        player1.setTeam("Turgutlu");
        player1.setPosition(Position.FORWARD);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Levent");
        player2.setAge(24);
        player2.setTeam("Turgutlu");
        player2.setPosition(Position.MIDFIELDER);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Mehmet");
        player3.setAge(26);
        player3.setTeam("Turgutlu");
        player3.setPosition(Position.DEFENSE);
        
        // Create spare players with mixed ages
        FootballPlayer sparePlayer1 = new FootballPlayer();
        sparePlayer1.setName("Nihat");
        sparePlayer1.setAge(35);
        sparePlayer1.setTeam("Turgutlu");
        sparePlayer1.setPosition(Position.FORWARD);
        
        FootballPlayer sparePlayer2 = new FootballPlayer();
        sparePlayer2.setName("Onur");
        sparePlayer2.setAge(22);
        sparePlayer2.setTeam("Turgutlu");
        sparePlayer2.setPosition(Position.MIDFIELDER);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(sparePlayer1);
        team.addPlayer(sparePlayer2);
        
        // Add players to starting eleven
        team.addToStartingEleven(player1);
        team.addToStartingEleven(player2);
        team.addToStartingEleven(player3);
        
        // Add players to spare team
        team.addToSpareTeam(sparePlayer1);
        team.addToSpareTeam(sparePlayer2);
        
        // Calculate average age and verify result
        double expectedAverage = (35 + 22) / 2.0;
        double actualAverage = team.averageAgeOfSpareTeam();
        
        assertEquals("Average age should be 28.5", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // Create players for starting eleven
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
        
        // Create only one spare player
        FootballPlayer sparePlayer1 = new FootballPlayer();
        sparePlayer1.setName("Yiğit");
        sparePlayer1.setAge(31);
        sparePlayer1.setTeam("Turgutlu");
        sparePlayer1.setPosition(Position.FORWARD);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(sparePlayer1);
        
        // Add players to starting eleven
        team.addToStartingEleven(player1);
        team.addToStartingEleven(player2);
        team.addToStartingEleven(player3);
        
        // Add player to spare team
        team.addToSpareTeam(sparePlayer1);
        
        // Calculate average age and verify result
        double expectedAverage = 31.0;
        double actualAverage = team.averageAgeOfSpareTeam();
        
        assertEquals("Average age should be 31.0 for single spare player", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Create players for starting eleven
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Eren");
        player1.setAge(24);
        player1.setTeam("Turgutlu");
        player1.setPosition(Position.FORWARD);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Furkan");
        player2.setAge(26);
        player2.setTeam("Turgutlu");
        player2.setPosition(Position.MIDFIELDER);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Kadir");
        player3.setAge(28);
        player3.setTeam("Turgutlu");
        player3.setPosition(Position.DEFENSE);
        
        // Create spare players with identical ages
        FootballPlayer sparePlayer1 = new FootballPlayer();
        sparePlayer1.setName("Zafer");
        sparePlayer1.setAge(27);
        sparePlayer1.setTeam("Turgutlu");
        sparePlayer1.setPosition(Position.MIDFIELDER);
        
        FootballPlayer sparePlayer2 = new FootballPlayer();
        sparePlayer2.setName("Burak");
        sparePlayer2.setAge(27);
        sparePlayer2.setTeam("Turgutlu");
        sparePlayer2.setPosition(Position.MIDFIELDER);
        
        // Add all players to team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(sparePlayer1);
        team.addPlayer(sparePlayer2);
        
        // Add players to starting eleven
        team.addToStartingEleven(player1);
        team.addToStartingEleven(player2);
        team.addToStartingEleven(player3);
        
        // Add players to spare team
        team.addToSpareTeam(sparePlayer1);
        team.addToSpareTeam(sparePlayer2);
        
        // Calculate average age and verify result
        double expectedAverage = (27 + 27) / 2.0;
        double actualAverage = team.averageAgeOfSpareTeam();
        
        assertEquals("Average age should be 27.0 for two players with identical age", expectedAverage, actualAverage, 0.001);
    }
}