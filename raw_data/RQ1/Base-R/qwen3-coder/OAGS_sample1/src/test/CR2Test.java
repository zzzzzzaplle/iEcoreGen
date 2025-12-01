import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR2Test {
    
    private Artist artist;
    private Membership membership;
    
    @Before
    public void setUp() {
        // Reset artist and membership before each test
        artist = new Artist();
        membership = new Membership();
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() {
        // Set up artist details
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender("Female");
        
        // Set up membership details
        membership.setId("M001");
        membership.setStartDate("2022-01-01");
        membership.setEndDate("2024-01-01");
        membership.setRewardPoints(100);
        membership.setMembershipType("individual");
        
        // Assign membership to artist
        artist.setMembership(membership);
        
        // Test period: 2023-01-01 to 2023-12-31
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        
        // Verify reward points calculation
        int result = artist.getRewardPointsInPeriod(startDate, endDate);
        assertEquals("Valid individual membership should return 100 reward points", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // Set up artist details
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender("Male");
        
        // Set up membership details (expired before test period)
        membership.setId("M002");
        membership.setStartDate("2022-03-01");
        membership.setEndDate("2022-09-01");
        membership.setRewardPoints(200);
        membership.setMembershipType("agency");
        
        // Assign membership to artist
        artist.setMembership(membership);
        
        // Test period: 2023-06-01 to 2023-07-01 (after membership expired)
        LocalDate startDate = LocalDate.of(2023, 6, 1);
        LocalDate endDate = LocalDate.of(2023, 7, 1);
        
        // Verify reward points calculation
        int result = artist.getRewardPointsInPeriod(startDate, endDate);
        assertEquals("Expired membership should return 0 reward points", 0, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // Set up artist details
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender("Female");
        
        // Set up membership details
        membership.setId("M003");
        membership.setStartDate("2023-01-01");
        membership.setEndDate("2024-01-01");
        membership.setRewardPoints(300);
        membership.setMembershipType("agency");
        
        // Assign membership to artist
        artist.setMembership(membership);
        
        // Test period: 2023-05-01 to 2023-10-01 (within membership period)
        LocalDate startDate = LocalDate.of(2023, 5, 1);
        LocalDate endDate = LocalDate.of(2023, 10, 1);
        
        // Verify reward points calculation
        int result = artist.getRewardPointsInPeriod(startDate, endDate);
        assertEquals("Valid agency membership should return 300 reward points", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() {
        // Set up artist details
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender("Male");
        
        // Set up first membership (active during test period)
        membership.setId("M004");
        membership.setStartDate("2023-01-05");
        membership.setEndDate("2023-06-01");
        membership.setRewardPoints(150);
        membership.setMembershipType("individual");
        
        // Assign first membership to artist
        artist.setMembership(membership);
        
        // Test period: 2023-01-06 to 2023-01-21 (within first membership period)
        LocalDate startDate = LocalDate.of(2023, 1, 6);
        LocalDate endDate = LocalDate.of(2023, 1, 21);
        
        // Verify reward points from first membership
        int result = artist.getRewardPointsInPeriod(startDate, endDate);
        assertEquals("Should return reward points from first valid membership (150)", 150, result);
        
        // Update to second membership (starts after test period)
        Membership secondMembership = new Membership();
        secondMembership.setId("M005");
        secondMembership.setStartDate("2023-02-01");
        secondMembership.setEndDate("2024-02-01");
        secondMembership.setRewardPoints(100);
        secondMembership.setMembershipType("agency affiliate");
        
        // Assign second membership to artist
        artist.setMembership(secondMembership);
        
        // Verify reward points calculation with second membership (should be 0 for test period)
        result = artist.getRewardPointsInPeriod(startDate, endDate);
        assertEquals("Second membership starts after test period, should return 0", 0, result);
    }
    
    @Test
    public void testCase5_BoundaryCondition_MembershipExpiration() {
        // Set up artist details
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender("Female");
        
        // Set up membership details (expires on 2023-01-01)
        membership.setId("M006");
        membership.setStartDate("2022-01-01");
        membership.setEndDate("2023-01-01");
        membership.setRewardPoints(250);
        membership.setMembershipType("agency");
        
        // Assign membership to artist
        artist.setMembership(membership);
        
        // Test period: single day 2023-01-02 (after membership expiration)
        LocalDate startDate = LocalDate.of(2023, 1, 2);
        LocalDate endDate = LocalDate.of(2023, 1, 2);
        
        // Verify reward points calculation
        int result = artist.getRewardPointsInPeriod(startDate, endDate);
        assertEquals("Membership expired on 2023-01-01, so 2023-01-02 should return 0", 0, result);
    }
}