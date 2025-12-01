import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private FootballTeam team;
    
    @Before
    public void setUp() {
        team = new FootballTeam();
    }
    
    @Test
    public void testCase1_MidfielderWithHighestNumber_BasicCase() {
        // Test Case 1: Basic case with multiple midfielders
        // Setup
        FootballPlayer playerA = new FootballPlayer();
        playerA.setName("Player A");
        playerA.setAge(24);
        playerA.setNumber(15);
        playerA.setPosition(Position.MIDFIELD);
        
        FootballPlayer playerB = new FootballPlayer();
        playerB.setName("Player B");
        playerB.setAge(26);
        playerB.setNumber(22);
        playerB.setPosition(Position.MIDFIELD);
        
        FootballPlayer playerC = new FootballPlayer();
        playerC.setName("Player C");
        playerC.setAge(23);
        playerC.setNumber(18);
        playerC.setPosition(Position.DEFENSE);
        
        team.addPlayer(playerA);
        team.addPlayer(playerB);
        team.addPlayer(playerC);
        
        // Execute
        FootballPlayer result = team.findMidfielderWithHighestNumber();