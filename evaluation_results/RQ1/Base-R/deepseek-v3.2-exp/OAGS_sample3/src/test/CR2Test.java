import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR2Test {
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() {
        // Test Case 1: "Valid Individual Membership Reward Points Calculation"
        // Input: Get reward points for artist with ID A001 from 2023-01-01 to 2023-12-31
        
        // SetUp: Create artist and assign individual membership
        Artist artist = new Artist();
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender("Female");
        
        Membership membership = new Membership();
        membership.setId("M001");
        membership.setStartDate("2022-01-01");
        membership.setEndDate("2024-01-01");
        membership.setRewardPoints(100);
        membership.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership);
        
        // Execute method and verify expected output
        int result = artist.getRewardPoints("2023-01-01", "2023-12-31");
        assertEquals("Reward points should be 100 for valid individual membership", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // Test Case 2: "Invalid Membership Period Calculation"
        // Input: Get reward points for artist with ID A002 from 2023-06-01 to 2023-07-01
        
        // SetUp: Create artist and assign agency membership with expired period
        Artist artist = new Artist();
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender("Male");
        
        Membership membership = new Membership();
        membership.setId("M002");
        membership.setStartDate("2022-03-01");
        membership.setEndDate("2022-09-01");
        membership.setRewardPoints(200);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Execute method and verify expected output
        int result = artist.getRewardPoints("2023-06-01", "2023-07-01");
        assertEquals("Reward points should be 0 for invalid membership period", 0, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // Test Case 3: "Agency Membership Reward Points Calculation"
        // Input: Get reward points for artist with ID A003 from 2023-05-01 to 2023-10-01
        
        // SetUp: Create artist and assign agency membership
        Artist artist = new Artist();
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender("Female");
        
        Membership membership = new Membership();
        membership.setId("M003");
        membership.setStartDate("2023-01-01");
        membership.setEndDate("2024-01-01");
        membership.setRewardPoints(300);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Execute method and verify expected output
        int result = artist.getRewardPoints("2023-05-01", "2023-10-01");
        assertEquals("Reward points should be 300 for valid agency membership", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() {
        // Test Case 4: "Multiple Memberships Consideration"
        // Input: Get reward points for artist with ID A004 from 2023-01-06 to 2023-01-21
        
        // SetUp: Create artist and assign individual membership
        Artist artist = new Artist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender("Male");
        
        Membership membership1 = new Membership();
        membership1.setId("M004");
        membership1.setStartDate("2023-01-05");
        membership1.setEndDate("2023-06-01");
        membership1.setRewardPoints(150);
        membership1.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership1);
        
        // Execute method with first membership and verify expected output
        int result1 = artist.getRewardPoints("2023-01-06", "2023-01-21");
        assertEquals("Reward points should be 150 for individual membership", 150, result1);
        
        // Update to assign agency affiliate membership
        Membership membership2 = new Membership();
        membership2.setId("M005");
        membership2.setStartDate("2023-02-01");
        membership2.setEndDate("2024-02-01");
        membership2.setRewardPoints(100);
        membership2.setType(MembershipType.AGENCY_AFFILIATE);
        
        artist.setMembership(membership2);
        
        // Execute method with second membership and verify expected output
        int result2 = artist.getRewardPoints("2023-01-06", "2023-01-21");
        assertEquals("Reward points should be 0 for agency affiliate membership during this period", 0, result2);
    }
    
    @Test
    public void testCase5_BoundaryConditionMembershipExpiration() {
        // Test Case 5: "Boundary Condition - Membership Expiration"
        // Input: Get reward points for artist with ID A005 on the 2023-01-02
        
        // SetUp: Create artist and assign agency membership that expires on boundary date
        Artist artist = new Artist();
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender("Female");
        
        Membership membership = new Membership();
        membership.setId("M006");
        membership.setStartDate("2022-01-01");
        membership.setEndDate("2023-01-01");
        membership.setRewardPoints(250);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Execute method and verify expected output
        int result = artist.getRewardPoints("2023-01-02", "2023-01-02");
        assertEquals("Reward points should be 0 for expired membership", 0, result);
    }
    

}