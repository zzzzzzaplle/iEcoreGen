import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() {
        // Create artist A001
        Artist artist = new Artist();
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender("Female");
        
        // Create and assign individual membership M001
        Membership membership = new Membership();
        membership.setId("M001");
        membership.setStartDate("2022-01-01");
        membership.setEndDate("2024-01-01");
        membership.setRewardPoints(100);
        membership.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership);
        
        // Get reward points from 2023-01-01 to 2023-12-31
        int result = artist.getRewardPoints("2023-01-01", "2023-12-31");
        
        // Verify reward points = 100
        assertEquals(100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // Create artist A002
        Artist artist = new Artist();
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender("Male");
        
        // Create and assign agency membership M002
        Membership membership = new Membership();
        membership.setId("M002");
        membership.setStartDate("2022-03-01");
        membership.setEndDate("2022-09-01");
        membership.setRewardPoints(200);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Get reward points from 2023-06-01 to 2023-07-01
        int result = artist.getRewardPoints("2023-06-01", "2023-07-01");
        
        // Verify reward points = -1 (membership invalid during this period)
        assertEquals(-1, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // Create artist A003
        Artist artist = new Artist();
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender("Female");
        
        // Create and assign agency membership M003
        Membership membership = new Membership();
        membership.setId("M003");
        membership.setStartDate("2023-01-01");
        membership.setEndDate("2024-01-01");
        membership.setRewardPoints(300);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Get reward points from 2023-05-01 to 2023-10-01
        int result = artist.getRewardPoints("2023-05-01", "2023-10-01");
        
        // Verify reward points = 300
        assertEquals(300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() {
        // Create artist A004
        Artist artist = new Artist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender("Male");
        
        // Create and assign individual membership M004
        Membership membership1 = new Membership();
        membership1.setId("M004");
        membership1.setStartDate("2023-01-05");
        membership1.setEndDate("2023-06-01");
        membership1.setRewardPoints(150);
        membership1.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership1);
        
        // Get reward points from 2023-01-06 to 2023-01-21 with first membership
        int result1 = artist.getRewardPoints("2023-01-06", "2023-01-21");
        
        // Verify first membership gives reward points = 150
        assertEquals(150, result1);
        
        // Update to assign agency affiliate membership M005
        Membership membership2 = new Membership();
        membership2.setId("M005");
        membership2.setStartDate("2023-02-01");
        membership2.setEndDate("2024-02-01");
        membership2.setRewardPoints(100);
        membership2.setType(MembershipType.AGENCY_AFFILIATE);
        
        artist.setMembership(membership2);
        
        // Get reward points from 2023-01-06 to 2023-01-21 with second membership
        int result2 = artist.getRewardPoints("2023-01-06", "2023-01-21");
        
        // Verify second membership gives reward points = -1 (invalid for this period)
        assertEquals(-1, result2);
    }
    
    @Test
    public void testCase5_BoundaryCondition_MembershipExpiration() {
        // Create artist A005
        Artist artist = new Artist();
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender("Female");
        
        // Create and assign agency membership M006
        Membership membership = new Membership();
        membership.setId("M006");
        membership.setStartDate("2022-01-01");
        membership.setEndDate("2023-01-01");
        membership.setRewardPoints(250);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Get reward points on the 2023-01-02 (membership expired on 2023-01-01)
        int result = artist.getRewardPoints("2023-01-02", "2023-01-02");
        
        // Verify reward points = -1 (membership expired on the end date)
        assertEquals(-1, result);
    }
}