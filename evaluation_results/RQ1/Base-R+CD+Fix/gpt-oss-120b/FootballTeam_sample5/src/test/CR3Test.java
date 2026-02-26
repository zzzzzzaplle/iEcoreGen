import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        // Initialize a new FootballTeam before each test
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_AverageAgeCalculationWithAllPlayers() {
        // Set up players as specified in test case 1
        Player player1 = new Player();
        player1.setName("John");
        player1.setAge(25);
        player1.setPosition(Position.MIDFIELD);
        
        Player player2 = new Player();
        player2.setName("Alex");
        player2.setAge(28);
        player2.setPosition(Position.FORWARD);
        
        Player player3 = new Player();
        player3.setName("Sam");
        player3.setAge(30);
        player3.setPosition(Position.DEFENSE);
        
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Mike");
        sparePlayer1.setAge(26);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Karl");
        sparePlayer2.setAge(24);
        
        // Add players to the team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(sparePlayer1);
        team.addPlayer(sparePlayer2);
        
        // Set first eleven (players 1, 2, 3)
        team.addToFirstEleven(player1);
        team.addToFirstEleven(player2);
        team.addToFirstEleven(player3);
        
        // Add spare players to spare team
        team.addToSpareTeam(sparePlayer1);
        team.addToSpareTeam(sparePlayer2);
        
        // Calculate average age and verify expected result
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // Set up players as specified in test case 2
        Player player1 = new Player();
        player1.setName("Fatih");
        player1.setAge(27);
        player1.setPosition(Position.GOALKEEPER);
        
        Player player2 = new Player();
        player2.setName("Gökhan");
        player2.setAge(25);
        player2.setPosition(Position.DEFENSE);
        
        Player player3 = new Player();
        player3.setName("Hakan");
        player3.setAge(29);
        player3.setPosition(Position.MIDFIELD);
        
        // Add players to the team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        
        // Set all players as first eleven (no spare players)
        team.addToFirstEleven(player1);
        team.addToFirstEleven(player2);
        team.addToFirstEleven(player3);
        
        // Calculate average age and verify expected result (0.0 for empty spare team)
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // Set up players as specified in test case 3
        Player player1 = new Player();
        player1.setName("Kerem");
        player1.setAge(22);
        player1.setPosition(Position.FORWARD);
        
        Player player2 = new Player();
        player2.setName("Levent");
        player2.setAge(24);
        player2.setPosition(Position.MIDFIELD);
        
        Player player3 = new Player();
        player3.setName("Mehmet");
        player3.setAge(26);
        player3.setPosition(Position.DEFENSE);
        
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Nihat");
        sparePlayer1.setAge(35);
        sparePlayer1.setPosition(Position.FORWARD);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Onur");
        sparePlayer2.setAge(22);
        sparePlayer2.setPosition(Position.MIDFIELD);
        
        // Add players to the team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(sparePlayer1);
        team.addPlayer(sparePlayer2);
        
        // Set first eleven (players 1, 2, 3)
        team.addToFirstEleven(player1);
        team.addToFirstEleven(player2);
        team.addToFirstEleven(player3);
        
        // Add spare players to spare team
        team.addToSpareTeam(sparePlayer1);
        team.addToSpareTeam(sparePlayer2);
        
        // Calculate average age and verify expected result
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals(28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // Set up players as specified in test case 4
        Player player1 = new Player();
        player1.setName("Tolga");
        player1.setAge(28);
        player1.setPosition(Position.GOALKEEPER);
        
        Player player2 = new Player();
        player2.setName("Umut");
        player2.setAge(25);
        player2.setPosition(Position.DEFENSE);
        
        Player player3 = new Player();
        player3.setName("Volkan");
        player3.setAge(30);
        player3.setPosition(Position.FORWARD);
        
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Yiğit");
        sparePlayer1.setAge(31);
        sparePlayer1.setPosition(Position.FORWARD);
        
        // Add players to the team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(sparePlayer1);
        
        // Set first eleven (players 1, 2, 3)
        team.addToFirstEleven(player1);
        team.addToFirstEleven(player2);
        team.addToFirstEleven(player3);
        
        // Add spare player to spare team
        team.addToSpareTeam(sparePlayer1);
        
        // Calculate average age and verify expected result
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals(31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Set up players as specified in test case 5
        Player player1 = new Player();
        player1.setName("Eren");
        player1.setAge(24);
        player1.setPosition(Position.FORWARD);
        
        Player player2 = new Player();
        player2.setName("Furkan");
        player2.setAge(26);
        player2.setPosition(Position.MIDFIELD);
        
        Player player3 = new Player();
        player3.setName("Kadir");
        player3.setAge(28);
        player3.setPosition(Position.DEFENSE);
        
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Zafer");
        sparePlayer1.setAge(27);
        sparePlayer1.setPosition(Position.MIDFIELD);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Burak");
        sparePlayer2.setAge(27);
        sparePlayer2.setPosition(Position.MIDFIELD);
        
        // Add players to the team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(sparePlayer1);
        team.addPlayer(sparePlayer2);
        
        // Set first eleven (players 1, 2, 3)
        team.addToFirstEleven(player1);
        team.addToFirstEleven(player2);
        team.addToFirstEleven(player3);
        
        // Add spare players to spare team
        team.addToSpareTeam(sparePlayer1);
        team.addToSpareTeam(sparePlayer2);
        
        // Calculate average age and verify expected result
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals(27.0, result, 0.001);
    }
}