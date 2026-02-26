import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam("Turgutlu");
    }
    
    @Test
    public void testCase1_AverageAgeCalculationWithAllPlayers() {
        // Set up starting eleven players
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(new FootballPlayer("Turgutlu", "John", 25, 1, Position.MIDFIELD, Duty.MIDFIELDER));
        startingEleven.add(new FootballPlayer("Turgutlu", "Alex", 28, 2, Position.FORWARD, Duty.STRIKER));
        startingEleven.add(new FootballPlayer("Turgutlu", "Sam", 30, 3, Position.DEFENSE, Duty.MIDFIELDER));
        team.setStartingEleven(startingEleven);
        
        // Set up spare team players
        List<FootballPlayer> spareTeam = new ArrayList<>();
        spareTeam.add(new FootballPlayer("Turgutlu", "Mike", 26, 4, Position.MIDFIELD, Duty.MIDFIELDER));
        spareTeam.add(new FootballPlayer("Turgutlu", "Karl", 24, 5, Position.DEFENSE, Duty.MIDFIELDER));
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageAgeCalculationWithNoSparePlayers() {
        // Set up starting eleven players only
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(new FootballPlayer("Turgutlu", "Fatih", 27, 1, Position.DEFENSE, Duty.GOALKEEPER));
        startingEleven.add(new FootballPlayer("Turgutlu", "Gökhan", 25, 2, Position.DEFENSE, Duty.MIDFIELDER));
        startingEleven.add(new FootballPlayer("Turgutlu", "Hakan", 29, 3, Position.MIDFIELD, Duty.MIDFIELDER));
        team.setStartingEleven(startingEleven);
        
        // Spare team remains empty
        team.setSpareTeam(new ArrayList<>());
        
        // Calculate average age and verify result
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageAgeCalculationWithMixedAgeSparePlayers() {
        // Set up starting eleven players
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(new FootballPlayer("Turgutlu", "Kerem", 22, 1, Position.FORWARD, Duty.STRIKER));
        startingEleven.add(new FootballPlayer("Turgutlu", "Levent", 24, 2, Position.MIDFIELD, Duty.MIDFIELDER));
        startingEleven.add(new FootballPlayer("Turgutlu", "Mehmet", 26, 3, Position.DEFENSE, Duty.MIDFIELDER));
        team.setStartingEleven(startingEleven);
        
        // Set up spare team players with mixed ages
        List<FootballPlayer> spareTeam = new ArrayList<>();
        spareTeam.add(new FootballPlayer("Turgutlu", "Nihat", 35, 4, Position.FORWARD, Duty.STRIKER));
        spareTeam.add(new FootballPlayer("Turgutlu", "Onur", 22, 5, Position.MIDFIELD, Duty.MIDFIELDER));
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals(28.5, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageAgeCalculationWithOneSparePlayer() {
        // Set up starting eleven players
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(new FootballPlayer("Turgutlu", "Tolga", 28, 1, Position.DEFENSE, Duty.GOALKEEPER));
        startingEleven.add(new FootballPlayer("Turgutlu", "Umut", 25, 2, Position.DEFENSE, Duty.MIDFIELDER));
        startingEleven.add(new FootballPlayer("Turgutlu", "Volkan", 30, 3, Position.FORWARD, Duty.STRIKER));
        team.setStartingEleven(startingEleven);
        
        // Set up spare team with only one player
        List<FootballPlayer> spareTeam = new ArrayList<>();
        spareTeam.add(new FootballPlayer("Turgutlu", "Yiğit", 31, 4, Position.FORWARD, Duty.STRIKER));
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals(31.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageAgeCalculationWithTwoIdenticalAgeSparePlayers() {
        // Set up starting eleven players
        List<FootballPlayer> startingEleven = new ArrayList<>();
        startingEleven.add(new FootballPlayer("Turgutlu", "Eren", 24, 1, Position.FORWARD, Duty.STRIKER));
        startingEleven.add(new FootballPlayer("Turgutlu", "Furkan", 26, 2, Position.MIDFIELD, Duty.MIDFIELDER));
        startingEleven.add(new FootballPlayer("Turgutlu", "Kadir", 28, 3, Position.DEFENSE, Duty.MIDFIELDER));
        team.setStartingEleven(startingEleven);
        
        // Set up spare team players with identical ages
        List<FootballPlayer> spareTeam = new ArrayList<>();
        spareTeam.add(new FootballPlayer("Turgutlu", "Zafer", 27, 4, Position.MIDFIELD, Duty.MIDFIELDER));
        spareTeam.add(new FootballPlayer("Turgutlu", "Burak", 27, 5, Position.MIDFIELD, Duty.MIDFIELDER));
        team.setSpareTeam(spareTeam);
        
        // Calculate average age and verify result
        double result = team.calculateAverageAgeOfSpareTeam();
        assertEquals(27.0, result, 0.001);
    }
}