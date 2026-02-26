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
        // SetUp: Create players and assign to spare team
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Mike");
        sparePlayer1.setAge(26);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Karl");
        sparePlayer2.setAge(24);
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        team.setSpareTeam(spareTeam);
        
        // Execute: Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Verify: Expected average = (26 + 24) / 2 = 25.0
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // SetUp: No spare players (empty spare team)
        List<Player> spareTeam = new ArrayList<>();
        team.setSpareTeam(spareTeam);
        
        // Execute: Calculate average age of empty spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Verify: Expected average = 0.0 (no spare players available)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // SetUp: Create players with mixed ages for spare team
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Nihat");
        sparePlayer1.setAge(35);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Onur");
        sparePlayer2.setAge(22);
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        team.setSpareTeam(spareTeam);
        
        // Execute: Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Verify: Expected average = (35 + 22) / 2 = 28.5
        assertEquals(28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // SetUp: Create only one spare player
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Yiğit");
        sparePlayer1.setAge(31);
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        team.setSpareTeam(spareTeam);
        
        // Execute: Calculate average age of spare team with one player
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Verify: Expected average = 31.0 (only one spare player)
        assertEquals(31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // SetUp: Create two spare players with identical ages
        Player sparePlayer1 = new Player();
        sparePlayer1.setName("Zafer");
        sparePlayer1.setAge(27);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Burak");
        sparePlayer2.setAge(27);
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(sparePlayer1);
        spareTeam.add(sparePlayer2);
        team.setSpareTeam(spareTeam);
        
        // Execute: Calculate average age of spare team
        double result = team.calculateAverageAgeOfSpareTeam();
        
        // Verify: Expected average = (27 + 27) / 2 = 27.0
        assertEquals(27.0, result, 0.001);
    }
}