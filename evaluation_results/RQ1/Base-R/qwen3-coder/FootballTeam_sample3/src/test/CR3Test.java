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
        // Set up starting eleven players
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("John");
        player1.setAge(25);
        player1.setTeam("Turgutlu");
        player1.setPosition(Position.MIDFIELD);
        
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
        
        // Set up spare team players
        FootballPlayer spare1 = new FootballPlayer();
        spare1.setName("Mike");
        spare1.setAge(26);
        spare1.setTeam("Turgutlu");
        
        FootballPlayer spare2 = new FootballPlayer();
        spare2.setName("Karl");
        spare2.setAge(24);
        spare2.setTeam("Turgutlu");
        
        // Add players to teams
        team.getStartingEleven().add(player1);
        team.getStartingEleven().add(player2);
        team.getStartingEleven().add(player3);
        team.getSpareTeam().add(spare1);
        team.getSpareTeam().add(spare2);
        
        // Calculate average age and verify
        double result = team.calculateSpareTeamAverageAge();
        assertEquals("Average age should be 25.0", 25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // Set up starting eleven players only (no spare players)
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
        player3.setPosition(Position.MIDFIELD);
        
        // Add players to starting eleven only
        team.getStartingEleven().add(player1);
        team.getStartingEleven().add(player2);
        team.getStartingEleven().add(player3);
        
        // Calculate average age and verify (should be 0.0 for empty spare team)
        double result = team.calculateSpareTeamAverageAge();
        assertEquals("Average age should be 0.0 when no spare players", 0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // Set up starting eleven players
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Kerem");
        player1.setAge(22);
        player1.setTeam("Turgutlu");
        player1.setPosition(Position.FORWARD);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Levent");
        player2.setAge(24);
        player2.setTeam("Turgutlu");
        player2.setPosition(Position.MIDFIELD);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Mehmet");
        player3.setAge(26);
        player3.setTeam("Turgutlu");
        player3.setPosition(Position.DEFENSE);
        
        // Set up spare team players with mixed ages
        FootballPlayer spare1 = new FootballPlayer();
        spare1.setName("Nihat");
        spare1.setAge(35);
        spare1.setTeam("Turgutlu");
        spare1.setPosition(Position.FORWARD);
        
        FootballPlayer spare2 = new FootballPlayer();
        spare2.setName("Onur");
        spare2.setAge(22);
        spare2.setTeam("Turgutlu");
        spare2.setPosition(Position.MIDFIELD);
        
        // Add players to teams
        team.getStartingEleven().add(player1);
        team.getStartingEleven().add(player2);
        team.getStartingEleven().add(player3);
        team.getSpareTeam().add(spare1);
        team.getSpareTeam().add(spare2);
        
        // Calculate average age and verify
        double result = team.calculateSpareTeamAverageAge();
        assertEquals("Average age should be 28.5", 28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // Set up starting eleven players
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
        
        // Set up one spare player
        FootballPlayer spare1 = new FootballPlayer();
        spare1.setName("Yiğit");
        spare1.setAge(31);
        spare1.setTeam("Turgutlu");
        spare1.setPosition(Position.FORWARD);
        
        // Add players to teams
        team.getStartingEleven().add(player1);
        team.getStartingEleven().add(player2);
        team.getStartingEleven().add(player3);
        team.getSpareTeam().add(spare1);
        
        // Calculate average age and verify
        double result = team.calculateSpareTeamAverageAge();
        assertEquals("Average age should be 31.0 for one spare player", 31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Set up starting eleven players
        FootballPlayer player1 = new FootballPlayer();
        player1.setName("Eren");
        player1.setAge(24);
        player1.setTeam("Turgutlu");
        player1.setPosition(Position.FORWARD);
        
        FootballPlayer player2 = new FootballPlayer();
        player2.setName("Furkan");
        player2.setAge(26);
        player2.setTeam("Turgutlu");
        player2.setPosition(Position.MIDFIELD);
        
        FootballPlayer player3 = new FootballPlayer();
        player3.setName("Kadir");
        player3.setAge(28);
        player3.setTeam("Turgutlu");
        player3.setPosition(Position.DEFENSE);
        
        // Set up spare players with identical ages
        FootballPlayer spare1 = new FootballPlayer();
        spare1.setName("Zafer");
        spare1.setAge(27);
        spare1.setTeam("Turgutlu");
        spare1.setPosition(Position.MIDFIELD);
        
        FootballPlayer spare2 = new FootballPlayer();
        spare2.setName("Burak");
        spare2.setAge(27);
        spare2.setTeam("Turgutlu");
        spare2.setPosition(Position.MIDFIELD);
        
        // Add players to teams
        team.getStartingEleven().add(player1);
        team.getStartingEleven().add(player2);
        team.getStartingEleven().add(player3);
        team.getSpareTeam().add(spare1);
        team.getSpareTeam().add(spare2);
        
        // Calculate average age and verify
        double result = team.calculateSpareTeamAverageAge();
        assertEquals("Average age should be 27.0 for two identical age spare players", 27.0, result, 0.001);
    }
}