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
        List<Player> firstEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        
        // Create first eleven players
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
        
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        // Create spare team players
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Mike");
        sparePlayer1.setAge(26);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Karl");
        sparePlayer2.setAge(24);
        
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        
        // Set up the football team
        footballTeam.setFirstEleven(firstEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // Set up players (all in first eleven, no spare players)
        List<Player> firstEleven = new ArrayList<>();
        
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
        
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        // Set up the football team with empty spare team
        footballTeam.setFirstEleven(firstEleven);
        footballTeam.setSpareTeam(new ArrayList<>());
        
        // Calculate average age and verify result
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // Set up players
        List<Player> firstEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        
        // Create first eleven players
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
        
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        // Create spare team players with mixed ages
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Nihat");
        sparePlayer1.setAge(35);
        sparePlayer1.setPosition(Position.FORWARD);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Onur");
        sparePlayer2.setAge(22);
        sparePlayer2.setPosition(Position.MIDFIELD);
        
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        
        // Set up the football team
        footballTeam.setFirstEleven(firstEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        assertEquals(28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // Set up players
        List<Player> firstEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        
        // Create first eleven players
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
        
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        // Create only one spare player
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Yiğit");
        sparePlayer1.setAge(31);
        sparePlayer1.setPosition(Position.FORWARD);
        
        spareTeam.add(sparePlayer1);
        
        // Set up the football team
        footballTeam.setFirstEleven(firstEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        assertEquals(31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Set up players
        List<Player> firstEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        
        // Create first eleven players
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
        
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        // Create spare team players with identical ages
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Zafer");
        sparePlayer1.setAge(27);
        sparePlayer1.setPosition(Position.MIDFIELD);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Burak");
        sparePlayer2.setAge(27);
        sparePlayer2.setPosition(Position.MIDFIELD);
        
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        
        // Set up the football team
        footballTeam.setFirstEleven(firstEleven);
        footballTeam.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        assertEquals(27.0, result, 0.001);
    }
}