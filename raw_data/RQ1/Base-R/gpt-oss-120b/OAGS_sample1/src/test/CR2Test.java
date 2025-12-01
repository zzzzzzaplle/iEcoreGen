import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR2Test {
    
    private Artist artist1;
    private Artist artist2;
    private Artist artist3;
    private Artist artist4;
    private Artist artist5;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Set up Artist 1 for Test Case 1
        artist1 = new Artist();
        artist1.setId("A001");
        artist1.setName("Alice");
        artist1.setPhoneNumber("123456789");
        artist1.setEmail("alice@example.com");
        artist1.setAddress("123 Art St");
        artist1.setGender(Gender.FEMALE);
        
        IndividualMembership membership1 = new IndividualMembership();
        membership1.setMembershipId("M001");
        membership1.setStartDate(LocalDate.parse("2022-01-01", formatter));
        membership1.setEndDate(LocalDate.parse("2024-01-01", formatter));
        membership1.setRewardPoints(100);
        artist1.setMembership(membership1);
        
        // Set up Artist 2 for Test Case 2
        artist2 = new Artist();
        artist2.setId("A002");
        artist2.setName("Bob");
        artist2.setPhoneNumber("987654321");
        artist2.setEmail("bob@example.com");
        artist2.setAddress("456 Art Ave");
        artist2.setGender(Gender.MALE);
        
        AgencyMembership membership2 = new AgencyMembership();
        membership2.setMembershipId("M002");
        membership2.setStartDate(LocalDate.parse("2022-03-01", formatter));
        membership2.setEndDate(LocalDate.parse("2022-09-01", formatter));
        membership2.setRewardPoints(200);
        artist2.setMembership(membership2);
        
        // Set up Artist 3 for Test Case 3
        artist3 = new Artist();
        artist3.setId("A003");
        artist3.setName("Catherine");
        artist3.setPhoneNumber("135792468");
        artist3.setEmail("catherine@example.com");
        artist3.setAddress("789 Art Blvd");
        artist3.setGender(Gender.FEMALE);
        
        AgencyMembership membership3 = new AgencyMembership();
        membership3.setMembershipId("M003");
        membership3.setStartDate(LocalDate.parse("2023-01-01", formatter));
        membership3.setEndDate(LocalDate.parse("2024-01-01", formatter));
        membership3.setRewardPoints(300);
        artist3.setMembership(membership3);
        
        // Set up Artist 4 for Test Case 4
        artist4 = new Artist();
        artist4.setId("A004");
        artist4.setName("David");
        artist4.setPhoneNumber("246813579");
        artist4.setEmail("david@example.com");
        artist4.setAddress("321 Art Rd");
        artist4.setGender(Gender.MALE);
        
        IndividualMembership membership4 = new IndividualMembership();
        membership4.setMembershipId("M004");
        membership4.setStartDate(LocalDate.parse("2023-01-05", formatter));
        membership4.setEndDate(LocalDate.parse("2023-06-01", formatter));
        membership4.setRewardPoints(150);
        artist4.setMembership(membership4);
        
        // Set up Artist 5 for Test Case 5
        artist5 = new Artist();
        artist5.setId("A005");
        artist5.setName("Eva");
        artist5.setPhoneNumber("864209753");
        artist5.setEmail("eva@example.com");
        artist5.setAddress("654 Art Pl");
        artist5.setGender(Gender.FEMALE);
        
        AgencyMembership membership5 = new AgencyMembership();
        membership5.setMembershipId("M006");
        membership5.setStartDate(LocalDate.parse("2022-01-01", formatter));
        membership5.setEndDate(LocalDate.parse("2023-01-01", formatter));
        membership5.setRewardPoints(250);
        artist5.setMembership(membership5);
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() {
        // Test Case 1: Valid Individual Membership Reward Points Calculation
        LocalDate periodStart = LocalDate.parse("2023-01-01", formatter);
        LocalDate periodEnd = LocalDate.parse("2023-12-31", formatter);
        
        // Get reward points for the specified period
        int rewardPoints = artist1.getRewardPointsWithinPeriod(periodStart, periodEnd);
        
        // Verify expected output: Reward points = 100
        assertEquals("Reward points should be 100 for valid individual membership", 100, rewardPoints);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // Test Case 2: Invalid Membership Period Calculation
        LocalDate periodStart = LocalDate.parse("2023-06-01", formatter);
        LocalDate periodEnd = LocalDate.parse("2023-07-01", formatter);
        
        // Attempt to get reward points - should throw IllegalArgumentException
        artist2.getRewardPointsWithinPeriod(periodStart, periodEnd);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // Test Case 3: Agency Membership Reward Points Calculation
        LocalDate periodStart = LocalDate.parse("2023-05-01", formatter);
        LocalDate periodEnd = LocalDate.parse("2023-10-01", formatter);
        
        // Get reward points for the specified period
        int rewardPoints = artist3.getRewardPointsWithinPeriod(periodStart, periodEnd);
        
        // Verify expected output: Reward points = 300
        assertEquals("Reward points should be 300 for valid agency membership", 300, rewardPoints);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() {
        // Test Case 4: Multiple Memberships Consideration
        LocalDate periodStart = LocalDate.parse("2023-01-06", formatter);
        LocalDate periodEnd = LocalDate.parse("2023-01-21", formatter);
        
        // Get reward points for the specified period with initial membership
        int rewardPoints = artist4.getRewardPointsWithinPeriod(periodStart, periodEnd);
        
        // Verify expected output: Reward points = 150
        assertEquals("Reward points should be 150 for valid individual membership", 150, rewardPoints);
        
        // Update to assign agency affiliate membership (as specified in test case)
        AgencyAffiliateMembership membership5 = new AgencyAffiliateMembership();
        membership5.setMembershipId("M005");
        membership5.setStartDate(LocalDate.parse("2023-02-01", formatter));
        membership5.setEndDate(LocalDate.parse("2024-02-01", formatter));
        membership5.setRewardPoints(100);
        artist4.setMembership(membership5);
        
        // Verify that with new membership, the period is no longer valid
        try {
            artist4.getRewardPointsWithinPeriod(periodStart, periodEnd);
            fail("Should have thrown IllegalArgumentException for invalid period with new membership");
        } catch (IllegalArgumentException e) {
            // Expected behavior - membership doesn't cover the period
            assertTrue("Exception message should indicate membership is invalid", 
                      e.getMessage().contains("Membership is not valid"));
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase5_BoundaryConditionMembershipExpiration() {
        // Test Case 5: Boundary Condition - Membership Expiration
        LocalDate periodStart = LocalDate.parse("2023-01-02", formatter);
        LocalDate periodEnd = LocalDate.parse("2023-01-02", formatter);
        
        // Attempt to get reward points - should throw IllegalArgumentException since membership expired
        artist5.getRewardPointsWithinPeriod(periodStart, periodEnd);
    }
}