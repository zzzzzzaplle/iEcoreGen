import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Create Reviewer R001
        Reviewer reviewer = new Reviewer("R001");
        
        // Create Paper P1, P2, P3
        Paper paper1 = new Paper("P1", true);
        Paper paper2 = new Paper("P2", true);
        Paper paper3 = new Paper("P3", true);
        
        // Assign P1/P2/P3 to