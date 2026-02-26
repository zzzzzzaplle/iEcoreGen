import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR2Test {
    
    private Artist artist;
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() {
        // SetUp: Create artist A001 with individual membership
        artist = new Artist("Alice", "123456789", "A001", "alice@example.com", 
                           "123 Art St", "Female", null);
        IndividualMembership membership = new IndividualMembership("M001", 
            LocalDate.of(2022, 1, 1), LocalDate.of(2024, 1, 1), 100);
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-01-01 to 2023-12-31
        LocalDate periodStart = LocalDate.of(2023, 1, 1);
        LocalDate periodEnd = LocalDate.of(2023, 12, 31);
        
        // Expected Output: Reward points = 100
        int result = artist.getRewardPointsWithinPeriod(periodStart, periodEnd);
        assertEquals(100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // SetUp: Create artist A002 with agency membership
        artist = new Artist("Bob", "987654321", "A002", "bob@example.com", 
                           "456 Art Ave", "Male", null);
        AgencyMembership membership = new AgencyMembership("M002", 
            LocalDate.of(2022, 3, 1), LocalDate.of(2022, 9, 1), 200);
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-06-01 to 2023-07-01
        LocalDate periodStart = LocalDate.of(2023, 6, 1);
        LocalDate periodEnd = LocalDate.of(2023, 7, 1);
        
        // Expected Output: Reward points = 0 (membership invalid during this period)
        try {
            artist.getRewardPointsWithinPeriod(periodStart, periodEnd);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Membership is not valid for the requested period"));
        }
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // SetUp: Create artist A003 with agency membership
        artist = new Artist("Catherine", "135792468", "A003", "catherine@example.com", 
                           "789 Art Blvd", "Female", null);
        AgencyMembership membership = new AgencyMembership("M003", 
            LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), 300);
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-05-01 to 2023-10-01
        LocalDate periodStart = LocalDate.of(2023, 5, 1);
        LocalDate periodEnd = LocalDate.of(2023, 10, 1);
        
        // Expected Output: Reward points = 300
        int result = artist.getRewardPointsWithinPeriod(periodStart, periodEnd);
        assertEquals(300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() {
        // SetUp: Create artist A004 with initial individual membership
        artist = new Artist("David", "246813579", "A004", "david@example.com", 
                           "321 Art Rd", "Male", null);
        IndividualMembership membership1 = new IndividualMembership("M004", 
            LocalDate.of(2023, 1, 5), LocalDate.of(2023, 6, 1), 150);
        artist.setMembership(membership1);
        
        // Update to agency affiliate membership (this replaces the previous membership)
        AgencyAffiliateMembership membership2 = new AgencyAffiliateMembership("M005", 
            LocalDate.of(2023, 2, 1), LocalDate.of(2024, 2, 1), 100);
        artist.setMembership(membership2);
        
        // Input: Get reward points from 2023-01-06 to 2023-01-21
        LocalDate periodStart = LocalDate.of(2023, 1, 6);
        LocalDate periodEnd = LocalDate.of(2023, 1, 21);
        
        // Expected Output: Reward points = 0 (current membership doesn't cover the period)
        try {
            artist.getRewardPointsWithinPeriod(periodStart, periodEnd);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Membership is not valid for the requested period"));
        }
    }
    
    @Test
    public void testCase5_BoundaryCondition_MembershipExpiration() {
        // SetUp: Create artist A005 with agency membership
        artist = new Artist("Eva", "864209753", "A005", "eva@example.com", 
                           "654 Art Pl", "Female", null);
        AgencyMembership membership = new AgencyMembership("M006", 
            LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1), 250);
        artist.setMembership(membership);
        
        // Input: Get reward points for single day 2023-01-02
        LocalDate periodStart = LocalDate.of(2023, 1, 2);
        LocalDate periodEnd = LocalDate.of(2023, 1, 2);
        
        // Expected Output: Reward points = 0 (membership expired on the end date)
        try {
            artist.getRewardPointsWithinPeriod(periodStart, periodEnd);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Membership is not valid for the requested period"));
        }
    }
}