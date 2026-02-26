import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import com.turgutlu.football.*;

public class CR3Test {
    
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_averageAgeCalculationWithAllPlayers() {
        // Create players
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
        
        // Create spare players
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Mike");
        sparePlayer1.setAge(26);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Karl");
        sparePlayer2.setAge(24);
        
        // Add all players to the team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(sparePlayer1);
        team.addPlayer(sparePlayer2);
        
        // Add players to first eleven
        team.addFirstEleven(player1);
        team.addFirstEleven(player2);
        team.addFirstEleven(player3);
        
        // Add spare players to spare team
        team.addSpareTeam(sparePlayer1);
        team.addSpareTeam(sparePlayer2);
        
        // Calculate average age of spare team
        double averageAge = team.calculateAverageAgeOfSpareTeam();
        
        // Expected: (26 + 24) / 2 = 25.0
        assertEquals(25.0, averageAge, 0.001);
    }
    
    @Test
    public void testCase2_averageAgeCalculationWithNoSparePlayers() {
        // Create players
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
        
        // Add all players to the team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        
        // Add all players to first eleven (no spare players)
        team.addFirstEleven(player1);
        team.addFirstEleven(player2);
        team.addFirstEleven(player3);
        
        // Calculate average age of spare team
        double averageAge = team.calculateAverageAgeOfSpareTeam();
        
        // Expected: 0.0 (No spare players)
        assertEquals(0.0, averageAge, 0.001);
    }
    
    @Test
    public void testCase3_averageAgeCalculationWithMixedAgeSparePlayers() {
        // Create players
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
        
        // Create spare players
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Nihat");
        sparePlayer1.setAge(35);
        sparePlayer1.setPosition(Position.FORWARD);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Onur");
        sparePlayer2.setAge(22);
        sparePlayer2.setPosition(Position.MIDFIELD);
        
        // Add all players to the team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(sparePlayer1);
        team.addPlayer(sparePlayer2);
        
        // Add regular players to first eleven
        team.addFirstEleven(player1);
        team.addFirstEleven(player2);
        team.addFirstEleven(player3);
        
        // Add spare players to spare team
        team.addSpareTeam(sparePlayer1);
        team.addSpareTeam(sparePlayer2);
        
        // Calculate average age of spare team
        double averageAge = team.calculateAverageAgeOfSpareTeam();
        
        // Expected: (35 + 22) / 2 = 28.5
        assertEquals(28.5, averageAge, 0.001);
    }
    
    @Test
    public void testCase4_averageAgeCalculationWithOneSparePlayer() {
        // Create players
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
        
        // Create spare player
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Yiğit");
        sparePlayer1.setAge(31);
        sparePlayer1.setPosition(Position.FORWARD);
        
        // Add all players to the team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(sparePlayer1);
        
        // Add regular players to first eleven
        team.addFirstEleven(player1);
        team.addFirstEleven(player2);
        team.addFirstEleven(player3);
        
        // Add spare player to spare team
        team.addSpareTeam(sparePlayer1);
        
        // Calculate average age of spare team
        double averageAge = team.calculateAverageAgeOfSpareTeam();
        
        // Expected: 31.0 (Only one spare player)
        assertEquals(31.0, averageAge, 0.001);
    }
    
    @Test
    public void testCase5_averageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Create players
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
        
        // Create spare players with identical ages
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Zafer");
        sparePlayer1.setAge(27);
        sparePlayer1.setPosition(Position.MIDFIELD);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Burak");
        sparePlayer2.setAge(27);
        sparePlayer2.setPosition(Position.MIDFIELD);
        
        // Add all players to the team
        team.addPlayer(player1);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(sparePlayer1);
        team.addPlayer(sparePlayer2);
        
        // Add regular players to first eleven
        team.addFirstEleven(player1);
        team.addFirstEleven(player2);
        team.addFirstEleven(player3);
        
        // Add spare players to spare team
        team.addSpareTeam(sparePlayer1);
        team.addSpareTeam(sparePlayer2);
        
        // Calculate average age of spare team
        double averageAge = team.calculateAverageAgeOfSpareTeam();
        
        // Expected: (27 + 27) / 2 = 27.0
        assertEquals(27.0, averageAge, 0.001);
    }
}