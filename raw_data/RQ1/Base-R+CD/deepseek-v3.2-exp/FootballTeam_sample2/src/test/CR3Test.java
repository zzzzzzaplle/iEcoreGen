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
        // SetUp: Create players for the team
        List<Player> firstEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        List<Player> allPlayers = new ArrayList<>();
        
        // Create first eleven players
        Player player1 = new Player();
        player1.setName("John");
        player1.setAge(25);
        player1.setPosition(Position.MIDFIELD);
        firstEleven.add(player1);
        allPlayers.add(player1);
        
        Player player2 = new Player();
        player2.setName("Alex");
        player2.setAge(28);
        player2.setPosition(Position.FORWARD);
        firstEleven.add(player2);
        allPlayers.add(player2);
        
        Player player3 = new Player();
        player3.setName("Sam");
        player3.setAge(30);
        player3.setPosition(Position.DEFENSE);
        firstEleven.add(player3);
        allPlayers.add(player3);
        
        // Create spare players
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Mike");
        sparePlayer1.setAge(26);
        spareTeam.add(sparePlayer1);
        allPlayers.add(sparePlayer1);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Karl");
        sparePlayer2.setAge(24);
        spareTeam.add(sparePlayer2);
        allPlayers.add(sparePlayer2);
        
        // Configure the football team
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        team.setPlayers(allPlayers);
        
        // Calculate average age and verify
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // SetUp: Create players for the team (all in first eleven)
        List<Player> firstEleven = new ArrayList<>();
        List<Player> allPlayers = new ArrayList<>();
        
        Player player1 = new Player();
        player1.setName("Fatih");
        player1.setAge(27);
        player1.setPosition(Position.GOALKEEPER);
        firstEleven.add(player1);
        allPlayers.add(player1);
        
        Player player2 = new Player();
        player2.setName("Gökhan");
        player2.setAge(25);
        player2.setPosition(Position.DEFENSE);
        firstEleven.add(player2);
        allPlayers.add(player2);
        
        Player player3 = new Player();
        player3.setName("Hakan");
        player3.setAge(29);
        player3.setPosition(Position.MIDFIELD);
        firstEleven.add(player3);
        allPlayers.add(player3);
        
        // Configure the football team with empty spare team
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(new ArrayList<>());
        team.setPlayers(allPlayers);
        
        // Calculate average age and verify (should be 0.0 for empty spare team)
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // SetUp: Create players for the team
        List<Player> firstEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        List<Player> allPlayers = new ArrayList<>();
        
        // Create first eleven players
        Player player1 = new Player();
        player1.setName("Kerem");
        player1.setAge(22);
        player1.setPosition(Position.FORWARD);
        firstEleven.add(player1);
        allPlayers.add(player1);
        
        Player player2 = new Player();
        player2.setName("Levent");
        player2.setAge(24);
        player2.setPosition(Position.MIDFIELD);
        firstEleven.add(player2);
        allPlayers.add(player2);
        
        Player player3 = new Player();
        player3.setName("Mehmet");
        player3.setAge(26);
        player3.setPosition(Position.DEFENSE);
        firstEleven.add(player3);
        allPlayers.add(player3);
        
        // Create spare players with mixed ages
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Nihat");
        sparePlayer1.setAge(35);
        sparePlayer1.setPosition(Position.FORWARD);
        spareTeam.add(sparePlayer1);
        allPlayers.add(sparePlayer1);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Onur");
        sparePlayer2.setAge(22);
        sparePlayer2.setPosition(Position.MIDFIELD);
        spareTeam.add(sparePlayer2);
        allPlayers.add(sparePlayer2);
        
        // Configure the football team
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        team.setPlayers(allPlayers);
        
        // Calculate average age and verify
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals(28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // SetUp: Create players for the team
        List<Player> firstEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        List<Player> allPlayers = new ArrayList<>();
        
        // Create first eleven players
        Player player1 = new Player();
        player1.setName("Tolga");
        player1.setAge(28);
        player1.setPosition(Position.GOALKEEPER);
        firstEleven.add(player1);
        allPlayers.add(player1);
        
        Player player2 = new Player();
        player2.setName("Umut");
        player2.setAge(25);
        player2.setPosition(Position.DEFENSE);
        firstEleven.add(player2);
        allPlayers.add(player2);
        
        Player player3 = new Player();
        player3.setName("Volkan");
        player3.setAge(30);
        player3.setPosition(Position.FORWARD);
        firstEleven.add(player3);
        allPlayers.add(player3);
        
        // Create only one spare player
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Yiğit");
        sparePlayer1.setAge(31);
        sparePlayer1.setPosition(Position.FORWARD);
        spareTeam.add(sparePlayer1);
        allPlayers.add(sparePlayer1);
        
        // Configure the football team
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        team.setPlayers(allPlayers);
        
        // Calculate average age and verify
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals(31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // SetUp: Create players for the team
        List<Player> firstEleven = new ArrayList<>();
        List<Player> spareTeam = new ArrayList<>();
        List<Player> allPlayers = new ArrayList<>();
        
        // Create first eleven players
        Player player1 = new Player();
        player1.setName("Eren");
        player1.setAge(24);
        player1.setPosition(Position.FORWARD);
        firstEleven.add(player1);
        allPlayers.add(player1);
        
        Player player2 = new Player();
        player2.setName("Furkan");
        player2.setAge(26);
        player2.setPosition(Position.MIDFIELD);
        firstEleven.add(player2);
        allPlayers.add(player2);
        
        Player player3 = new Player();
        player3.setName("Kadir");
        player3.setAge(28);
        player3.setPosition(Position.DEFENSE);
        firstEleven.add(player3);
        allPlayers.add(player3);
        
        // Create spare players with identical ages
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Zafer");
        sparePlayer1.setAge(27);
        sparePlayer1.setPosition(Position.MIDFIELD);
        spareTeam.add(sparePlayer1);
        allPlayers.add(sparePlayer1);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Burak");
        sparePlayer2.setAge(27);
        sparePlayer2.setPosition(Position.MIDFIELD);
        spareTeam.add(sparePlayer2);
        allPlayers.add(sparePlayer2);
        
        // Configure the football team
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        team.setPlayers(allPlayers);
        
        // Calculate average age and verify
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals(27.0, result, 0.001);
    }
}