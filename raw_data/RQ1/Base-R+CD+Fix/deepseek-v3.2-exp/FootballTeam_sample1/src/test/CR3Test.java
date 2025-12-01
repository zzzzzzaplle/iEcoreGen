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
        // Set up first eleven players
        List<Player> firstEleven = new ArrayList<>();
        
        Player player1 = new Player();
        player1.setName("John");
        player1.setAge(25);
        player1.setPosition(Position.MIDFIELD);
        firstEleven.add(player1);
        
        Player player2 = new Player();
        player2.setName("Alex");
        player2.setAge(28);
        player2.setPosition(Position.FORWARD);
        firstEleven.add(player2);
        
        Player player3 = new Player();
        player3.setName("Sam");
        player3.setAge(30);
        player3.setPosition(Position.DEFENSE);
        firstEleven.add(player3);
        
        footballTeam.setFirstEleven(firstEleven);
        
        // Set up spare team players
        List<Player> spareTeam = new ArrayList<>();
        
        Player spare1 = new Player();
        spare1.setName("Mike");
        spare1.setAge(26);
        spare1.setPosition(Position.MIDFIELD);
        spareTeam.add(spare1);
        
        Player spare2 = new Player();
        spare2.setName("Karl");
        spare2.setAge(24);
        spare2.setPosition(Position.DEFENSE);
        spareTeam.add(spare2);
        
        footballTeam.setSpareTeam(spareTeam);
        
        // Set up all players (first eleven + spare team)
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(firstEleven);
        allPlayers.addAll(spareTeam);
        footballTeam.setPlayers(allPlayers);
        
        // Calculate average age and verify
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // Set up first eleven players
        List<Player> firstEleven = new ArrayList<>();
        
        Player player1 = new Player();
        player1.setName("Fatih");
        player1.setAge(27);
        player1.setPosition(Position.GOALKEEPER);
        firstEleven.add(player1);
        
        Player player2 = new Player();
        player2.setName("Gökhan");
        player2.setAge(25);
        player2.setPosition(Position.DEFENSE);
        firstEleven.add(player2);
        
        Player player3 = new Player();
        player3.setName("Hakan");
        player3.setAge(29);
        player3.setPosition(Position.MIDFIELD);
        firstEleven.add(player3);
        
        footballTeam.setFirstEleven(firstEleven);
        footballTeam.setPlayers(firstEleven);
        
        // Set empty spare team
        footballTeam.setSpareTeam(new ArrayList<>());
        
        // Calculate average age and verify
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // Set up first eleven players
        List<Player> firstEleven = new ArrayList<>();
        
        Player player1 = new Player();
        player1.setName("Kerem");
        player1.setAge(22);
        player1.setPosition(Position.FORWARD);
        firstEleven.add(player1);
        
        Player player2 = new Player();
        player2.setName("Levent");
        player2.setAge(24);
        player2.setPosition(Position.MIDFIELD);
        firstEleven.add(player2);
        
        Player player3 = new Player();
        player3.setName("Mehmet");
        player3.setAge(26);
        player3.setPosition(Position.DEFENSE);
        firstEleven.add(player3);
        
        footballTeam.setFirstEleven(firstEleven);
        
        // Set up spare team players
        List<Player> spareTeam = new ArrayList<>();
        
        Player spare1 = new Player();
        spare1.setName("Nihat");
        spare1.setAge(35);
        spare1.setPosition(Position.FORWARD);
        spareTeam.add(spare1);
        
        Player spare2 = new Player();
        spare2.setName("Onur");
        spare2.setAge(22);
        spare2.setPosition(Position.MIDFIELD);
        spareTeam.add(spare2);
        
        footballTeam.setSpareTeam(spareTeam);
        
        // Set up all players (first eleven + spare team)
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(firstEleven);
        allPlayers.addAll(spareTeam);
        footballTeam.setPlayers(allPlayers);
        
        // Calculate average age and verify
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        assertEquals(28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // Set up first eleven players
        List<Player> firstEleven = new ArrayList<>();
        
        Player player1 = new Player();
        player1.setName("Tolga");
        player1.setAge(28);
        player1.setPosition(Position.GOALKEEPER);
        firstEleven.add(player1);
        
        Player player2 = new Player();
        player2.setName("Umut");
        player2.setAge(25);
        player2.setPosition(Position.DEFENSE);
        firstEleven.add(player2);
        
        Player player3 = new Player();
        player3.setName("Volkan");
        player3.setAge(30);
        player3.setPosition(Position.FORWARD);
        firstEleven.add(player3);
        
        footballTeam.setFirstEleven(firstEleven);
        
        // Set up spare team players
        List<Player> spareTeam = new ArrayList<>();
        
        Player spare1 = new Player();
        spare1.setName("Yiğit");
        spare1.setAge(31);
        spare1.setPosition(Position.FORWARD);
        spareTeam.add(spare1);
        
        footballTeam.setSpareTeam(spareTeam);
        
        // Set up all players (first eleven + spare team)
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(firstEleven);
        allPlayers.addAll(spareTeam);
        footballTeam.setPlayers(allPlayers);
        
        // Calculate average age and verify
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        assertEquals(31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Set up first eleven players
        List<Player> firstEleven = new ArrayList<>();
        
        Player player1 = new Player();
        player1.setName("Eren");
        player1.setAge(24);
        player1.setPosition(Position.FORWARD);
        firstEleven.add(player1);
        
        Player player2 = new Player();
        player2.setName("Furkan");
        player2.setAge(26);
        player2.setPosition(Position.MIDFIELD);
        firstEleven.add(player2);
        
        Player player3 = new Player();
        player3.setName("Kadir");
        player3.setAge(28);
        player3.setPosition(Position.DEFENSE);
        firstEleven.add(player3);
        
        footballTeam.setFirstEleven(firstEleven);
        
        // Set up spare team players
        List<Player> spareTeam = new ArrayList<>();
        
        Player spare1 = new Player();
        spare1.setName("Zafer");
        spare1.setAge(27);
        spare1.setPosition(Position.MIDFIELD);
        spareTeam.add(spare1);
        
        Player spare2 = new Player();
        spare2.setName("Burak");
        spare2.setAge(27);
        spare2.setPosition(Position.MIDFIELD);
        spareTeam.add(spare2);
        
        footballTeam.setSpareTeam(spareTeam);
        
        // Set up all players (first eleven + spare team)
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(firstEleven);
        allPlayers.addAll(spareTeam);
        footballTeam.setPlayers(allPlayers);
        
        // Calculate average age and verify
        double result = footballTeam.calculateAverageAgeOfSpareTeam();
        assertEquals(27.0, result, 0.001);
    }
}