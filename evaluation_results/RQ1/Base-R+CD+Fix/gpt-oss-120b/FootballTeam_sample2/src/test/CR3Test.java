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
        // Create players for first eleven
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
        Player spare1 = new Player();
        spare1.setName("Mike");
        spare1.setAge(26);
        
        Player spare2 = new Player();
        spare2.setName("Karl");
        spare2.setAge(24);
        
        // Set up first eleven and spare team
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(spare1);
        spareTeam.add(spare2);
        
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(firstEleven);
        allPlayers.addAll(spareTeam);
        
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        team.setPlayers(allPlayers);
        
        // Calculate average age and verify
        double result = team.calculateAverageAgeOfSpareTeam();
        double expected = (26 + 24) / 2.0; // 25.0
        assertEquals("Average age should be 25.0", expected, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // Create players for first eleven only (no spare players)
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
        
        // Set up first eleven (all players) and empty spare team
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        List<Player> spareTeam = new ArrayList<>();
        
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        team.setPlayers(firstEleven);
        
        // Calculate average age and verify
        double result = team.calculateAverageAgeOfSpareTeam();
        double expected = 0.0;
        assertEquals("Average age should be 0.0 when no spare players", expected, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // Create players for first eleven
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
        
        // Create spare players with mixed ages
        Player spare1 = new Player();
        spare1.setName("Nihat");
        spare1.setAge(35);
        spare1.setPosition(Position.FORWARD);
        
        Player spare2 = new Player();
        spare2.setName("Onur");
        spare2.setAge(22);
        spare2.setPosition(Position.MIDFIELD);
        
        // Set up first eleven and spare team
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(spare1);
        spareTeam.add(spare2);
        
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(firstEleven);
        allPlayers.addAll(spareTeam);
        
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        team.setPlayers(allPlayers);
        
        // Calculate average age and verify
        double result = team.calculateAverageAgeOfSpareTeam();
        double expected = (35 + 22) / 2.0; // 28.5
        assertEquals("Average age should be 28.5", expected, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // Create players for first eleven
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
        
        // Create only one spare player
        Player spare1 = new Player();
        spare1.setName("Yiğit");
        spare1.setAge(31);
        spare1.setPosition(Position.FORWARD);
        
        // Set up first eleven and spare team
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(spare1);
        
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(firstEleven);
        allPlayers.addAll(spareTeam);
        
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        team.setPlayers(allPlayers);
        
        // Calculate average age and verify
        double result = team.calculateAverageAgeOfSpareTeam();
        double expected = 31.0;
        assertEquals("Average age should be 31.0 for one spare player", expected, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Create players for first eleven
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
        Player spare1 = new Player();
        spare1.setName("Zafer");
        spare1.setAge(27);
        spare1.setPosition(Position.MIDFIELD);
        
        Player spare2 = new Player();
        spare2.setName("Burak");
        spare2.setAge(27);
        spare2.setPosition(Position.MIDFIELD);
        
        // Set up first eleven and spare team
        List<Player> firstEleven = new ArrayList<>();
        firstEleven.add(player1);
        firstEleven.add(player2);
        firstEleven.add(player3);
        
        List<Player> spareTeam = new ArrayList<>();
        spareTeam.add(spare1);
        spareTeam.add(spare2);
        
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(firstEleven);
        allPlayers.addAll(spareTeam);
        
        team.setFirstEleven(firstEleven);
        team.setSpareTeam(spareTeam);
        team.setPlayers(allPlayers);
        
        // Calculate average age and verify
        double result = team.calculateAverageAgeOfSpareTeam();
        double expected = (27 + 27) / 2.0; // 27.0
        assertEquals("Average age should be 27.0 for two identical age spare players", expected, result, 0.001);
    }
}