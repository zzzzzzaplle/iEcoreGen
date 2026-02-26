import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Artist artist1;
    private Artist artist2;
    private Artist artist3;
    private Artist artist4;
    private Artist artist5;
    
    @Before
    public void setUp() {
        // Initialize artists for test cases
        artist1 = new Artist();
        artist2 = new Artist();
        artist3 = new Artist();
        artist4 = new Artist();
        artist5 = new Artist();
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() {
        // Set up artist A001 with individual membership
        artist1.setId("A001");
        artist1.setName("Alice");
        artist1.setPhoneNumber("123456789");
        artist1.setEmail("alice@example.com");
        artist1.setAddress("123 Art St");
        artist1.setGender("Female");
        
        IndividualMembership membership1 = new IndividualMembership();
        membership1.setMembershipId("M001");
        membership1.setStartDate(LocalDate.of(2022, 1, 1));
        membership1.setEndDate(LocalDate.of(2024, 1, 1));
        membership1.setRewardPoints(100);
        
        artist1.setMembership(membership1);
        
        // Test: Get reward points from 2023-01-01 to 2023-12-31
        int result = artist1.getRewardPointsWithinPeriod("2023-01-01", "2023-12-31");
        
        // Verify expected reward points
        assertEquals("Reward points should be 100 for valid individual membership", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // Set up artist A002 with agency membership
        artist2.setId("A002");
        artist2.setName("Bob");
        artist2.setPhoneNumber("987654321");
        artist2.setEmail("bob@example.com");
        artist2.setAddress("456 Art Ave");
        artist2.setGender("Male");
        
        AgencyMembership membership2 = new AgencyMembership();
        membership2.setMembershipId("M002");
        membership2.setStartDate(LocalDate.of(2022, 3, 1));
        membership2.setEndDate(LocalDate.of(2022, 9, 1));
        membership2.setRewardPoints(200);
        
        artist2.setMembership(membership2);
        
        // Test: Get reward points from 2023-06-01 to 2023-07-01 (outside membership period)
        try {
            artist2.getRewardPointsWithinPeriod("2023-06-01", "2023-07-01");
            fail("Expected IllegalArgumentException for invalid membership period");
        } catch (IllegalArgumentException e) {
            // Expected exception - membership is not valid for the requested period
            assertTrue("Exception message should indicate membership is not valid", 
                      e.getMessage().contains("Membership is not valid for the requested period"));
        }
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // Set up artist A003 with agency membership
        artist3.setId("A003");
        artist3.setName("Catherine");
        artist3.setPhoneNumber("135792468");
        artist3.setEmail("catherine@example.com");
        artist3.setAddress("789 Art Blvd");
        artist3.setGender("Female");
        
        AgencyMembership membership3 = new AgencyMembership();
        membership3.setMembershipId("M003");
        membership3.setStartDate(LocalDate.of(2023, 1, 1));
        membership3.setEndDate(LocalDate.of(2024, 1, 1));
        membership3.setRewardPoints(300);
        
        artist3.setMembership(membership3);
        
        // Test: Get reward points from 2023-05-01 to 2023-10-01
        int result = artist3.getRewardPointsWithinPeriod("2023-05-01", "2023-10-01");
        
        // Verify expected reward points
        assertEquals("Reward points should be 300 for valid agency membership", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() {
        // Set up artist A004 with individual membership
        artist4.setId("A004");
        artist4.setName("David");
        artist4.setPhoneNumber("246813579");
        artist4.setEmail("david@example.com");
        artist4.setAddress("321 Art Rd");
        artist4.setGender("Male");
        
        IndividualMembership membership4 = new IndividualMembership();
        membership4.setMembershipId("M004");
        membership4.setStartDate(LocalDate.of(2023, 1, 5));
        membership4.setEndDate(LocalDate.of(2023, 6, 1));
        membership4.setRewardPoints(150);
        
        artist4.setMembership(membership4);
        
        // Test: Get reward points from 2023-01-06 to 2023-01-21 (using first membership)
        int result = artist4.getRewardPointsWithinPeriod("2023-01-06", "2023-01-21");
        
        // Verify expected reward points from first membership
        assertEquals("Reward points should be 150 from individual membership", 150, result);
        
        // Update to agency affiliate membership (this replaces the previous membership)
        AgencyAffiliateMembership membership5 = new AgencyAffiliateMembership();
        membership5.setMembershipId("M005");
        membership5.setStartDate(LocalDate.of(2023, 2, 1));
        membership5.setEndDate(LocalDate.of(2024, 2, 1));
        membership5.setRewardPoints(100);
        
        artist4.setMembership(membership5);
        
        // Test: Get reward points for same period with new membership (should use current membership)
        try {
            artist4.getRewardPointsWithinPeriod("2023-01-06", "2023-01-21");
            fail("Expected IllegalArgumentException for period outside membership range");
        } catch (IllegalArgumentException e) {
            // Expected exception - new membership doesn't cover the requested period
            assertTrue("Exception message should indicate membership is not valid", 
                      e.getMessage().contains("Membership is not valid for the requested period"));
        }
    }
    
    @Test
    public void testCase5_BoundaryConditionMembershipExpiration() {
        // Set up artist A005 with agency membership
        artist5.setId("A005");
        artist5.setName("Eva");
        artist5.setPhoneNumber("864209753");
        artist5.setEmail("eva@example.com");
        artist5.setAddress("654 Art Pl");
        artist5.setGender("Female");
        
        AgencyMembership membership6 = new AgencyMembership();
        membership6.setMembershipId("M006");
        membership6.setStartDate(LocalDate.of(2022, 1, 1));
        membership6.setEndDate(LocalDate.of(2023, 1, 1));
        membership6.setRewardPoints(250);
        
        artist5.setMembership(membership6);
        
        // Test: Get reward points for single day 2023-01-02 (after membership expiration)
        try {
            artist5.getRewardPointsWithinPeriod("2023-01-02", "2023-01-02");
            fail("Expected IllegalArgumentException for expired membership");
        } catch (IllegalArgumentException e) {
            // Expected exception - membership expired on 2023-01-01
            assertTrue("Exception message should indicate membership is not valid", 
                      e.getMessage().contains("Membership is not valid for the requested period"));
        }
    }
}