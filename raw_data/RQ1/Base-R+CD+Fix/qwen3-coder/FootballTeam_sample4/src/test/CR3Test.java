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
        // SetUp: Create players
        List<Player> allPlayers = new ArrayList<>();
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
        
        // Create spare players
        Player spare1 = new Player();
        spare1.setName("Mike");
        spare1.setAge(26);
        
        Player spare2 = new Player();
        spare2.setName("Karl");
        spare2.setAge(24);
        
        spareTeam.add(spare1);
        spareTeam.add(spare2);
        
        // Combine all players
        allPlayers.addAll(firstEleven);
        allPlayers.addAll(spareTeam);
        
        // Set up the football team
        team.setPlayers(allPlayers);
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Expected output: (26 + 24) / 2 = 25.0
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // SetUp: Create players with no spare players
        List<Player> allPlayers = new ArrayList<>();
        List<Player> firstEleven = new ArrayList<>();
        
        // Create first eleven players
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
        
        // All players are part of first eleven, no spare players
        allPlayers.addAll(firstEleven);
        
        // Set up the football team
        team.setPlayers(allPlayers);
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(new ArrayList<>()); // Empty spare team
        
        // Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Expected output: 0.0 (No spare players available)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // SetUp: Create players with mixed age spare players
        List<Player> allPlayers = new ArrayList<>();
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
        
        // Create spare players with mixed ages
        Player spare1 = new Player();
        spare1.setName("Nihat");
        spare1.setAge(35);
        spare1.setPosition(Position.FORWARD);
        
        Player spare2 = new Player();
        spare2.setName("Onur");
        spare2.setAge(22);
        spare2.setPosition(Position.MIDFIELD);
        
        spareTeam.add(spare1);
        spareTeam.add(spare2);
        
        // Combine all players
        allPlayers.addAll(firstEleven);
        allPlayers.addAll(spareTeam);
        
        // Set up the football team
        team.setPlayers(allPlayers);
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Expected output: (35 + 22) / 2 = 28.5
        assertEquals(28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // SetUp: Create players with one spare player
        List<Player> allPlayers = new ArrayList<>();
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
        
        // Create one spare player
        Player spare1 = new Player();
        spare1.setName("Yiğit");
        spare1.setAge(31);
        spare1.setPosition(Position.FORWARD);
        
        spareTeam.add(spare1);
        
        // Combine all players
        allPlayers.addAll(firstEleven);
        allPlayers.addAll(spareTeam);
        
        // Set up the football team
        team.setPlayers(allPlayers);
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Expected output: 31.0 (Only one spare player)
        assertEquals(31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // SetUp: Create players with two spare players of identical age
        List<Player> allPlayers = new ArrayList<>();
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
        
        // Create spare players with identical age
        Player spare1 = new Player();
        spare1.setName("Zafer");
        spare1.setAge(27);
        spare1.setPosition(Position.MIDFIELD);
        
        Player spare2 = new Player();
        spare2.setName("Burak");
        spare2.setAge(27);
        spare2.setPosition(Position.MIDFIELD);
        
        spareTeam.add(spare1);
        spareTeam.add(spare2);
        
        // Combine all players
        allPlayers.addAll(firstEleven);
        allPlayers.addAll(spareTeam);
        
        // Set up the football team
        team.setPlayers(allPlayers);
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        
        // Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Expected output: (27 + 27) / 2 = 27.0
        assertEquals(27.0, result, 0.001);
    }
}