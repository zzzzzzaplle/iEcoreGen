import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_AverageAgeCalculationWithAllPlayers() {
        // SetUp: Create players and assign to spare team
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
        sparePlayer1.setPosition(Position.MIDFIELD);
        
        Player sparePlayer2 = new Player();
        sparePlayer2.setName("Karl");
        sparePlayer2.setAge(24);
        sparePlayer2.setPosition(Position.DEFENSE);
        
        // Add players to team lists
        team.getFirstEleven().add(player1);
        team.getFirstEleven().add(player2);
        team.getFirstEleven().add(player3);
        team.getSpareTeam().add(sparePlayer1);
        team.getSpareTeam().add(sparePlayer2);
        team.getPlayers().add(player1);
        team.getPlayers().add(player2);
        team.getPlayers().add(player3);
        team.getPlayers().add(sparePlayer1);
        team.getPlayers().add(sparePlayer2);
        
        // Calculate average age and verify
        double result = team.calculateAverageAgeOfSpareTeam();
        double expected = (26.0 + 24.0) / 2.0; // 25.0
        assertEquals("Average age should be 25.0 when spare team has players aged 26 and 24", 
                     expected, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // SetUp: Create players but no spare players
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
        
        // Add only to first eleven and players list (no spare players)
        team.getFirstEleven().add(player1);
        team.getFirstEleven().add(player2);
        team.getFirstEleven().add(player3);
        team.getPlayers().add(player1);
        team.getPlayers().add(player2);
        team.getPlayers().add(player3);
        
        // Calculate average age and verify
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 0.0 when spare team is empty", 
                     0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // SetUp: Create players with mixed age spare players
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
        
        // Add players to team lists
        team.getFirstEleven().add(player1);
        team.getFirstEleven().add(player2);
        team.getFirstEleven().add(player3);
        team.getSpareTeam().add(sparePlayer1);
        team.getSpareTeam().add(sparePlayer2);
        team.getPlayers().add(player1);
        team.getPlayers().add(player2);
        team.getPlayers().add(player3);
        team.getPlayers().add(sparePlayer1);
        team.getPlayers().add(sparePlayer2);
        
        // Calculate average age and verify
        double result = team.calculateAverageAgeOfSpareTeam();
        double expected = (35.0 + 22.0) / 2.0; // 28.5
        assertEquals("Average age should be 28.5 when spare team has players aged 35 and 22", 
                     expected, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // SetUp: Create players with only one spare player
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
        
        // Add players to team lists
        team.getFirstEleven().add(player1);
        team.getFirstEleven().add(player2);
        team.getFirstEleven().add(player3);
        team.getSpareTeam().add(sparePlayer1);
        team.getPlayers().add(player1);
        team.getPlayers().add(player2);
        team.getPlayers().add(player3);
        team.getPlayers().add(sparePlayer1);
        
        // Calculate average age and verify
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals("Average age should be 31.0 when spare team has one player aged 31", 
                     31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // SetUp: Create players with two spare players of same age
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
        
        // Add players to team lists
        team.getFirstEleven().add(player1);
        team.getFirstEleven().add(player2);
        team.getFirstEleven().add(player3);
        team.getSpareTeam().add(sparePlayer1);
        team.getSpareTeam().add(sparePlayer2);
        team.getPlayers().add(player1);
        team.getPlayers().add(player2);
        team.getPlayers().add(player3);
        team.getPlayers().add(sparePlayer1);
        team.getPlayers().add(sparePlayer2);
        
        // Calculate average age and verify
        double result = team.calculateAverageAgeOfSpareTeam();
        double expected = (27.0 + 27.0) / 2.0; // 27.0
        assertEquals("Average age should be 27.0 when spare team has two players both aged 27", 
                     expected, result, 0.001);
    }
}