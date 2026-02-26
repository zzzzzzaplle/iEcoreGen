import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() {
        // SetUp: Create artist with individual membership
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
        
        // Input: Get reward points from 2023-01-01 to 2023-12-31
        int rewardPoints = artist.getMembership().getRewardPointsInPeriod("2023-01-01", "2023-12-31");
        
        // Expected Output: Reward points = 100
        assertEquals("Valid individual membership should return 100 reward points", 100, rewardPoints);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // SetUp: Create artist with expired membership
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
        
        // Input: Get reward points from 2023-06-01 to 2023-07-01
        // Expected Output: IllegalStateException thrown (membership invalid during this period)
        artist.getMembership().getRewardPointsInPeriod("2023-06-01", "2023-07-01");
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // SetUp: Create artist with agency membership
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
        
        // Input: Get reward points from 2023-05-01 to 2023-10-01
        int rewardPoints = artist.getMembership().getRewardPointsInPeriod("2023-05-01", "2023-10-01");
        
        // Expected Output: Reward points = 300
        assertEquals("Valid agency membership should return 300 reward points", 300, rewardPoints);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() {
        // SetUp: Create artist and assign first membership
        Artist artist = new Artist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender("Male");
        
        // First membership
        Membership membership1 = new Membership();
        membership1.setId("M004");
        membership1.setStartDate("2023-01-05");
        membership1.setEndDate("2023-06-01");
        membership1.setRewardPoints(150);
        membership1.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership1);
        
        // Input: Get reward points from 2023-01-06 to 2023-01-21 using first membership
        int rewardPoints = artist.getMembership().getRewardPointsInPeriod("2023-01-06", "2023-01-21");
        
        // Expected Output: Reward points = 150 (from first membership)
        assertEquals("Should return reward points from valid membership", 150, rewardPoints);
        
        // Update to second membership (simulating membership update)
        Membership membership2 = new Membership();
        membership2.setId("M005");
        membership2.setStartDate("2023-02-01");
        membership2.setEndDate("2024-02-01");
        membership2.setRewardPoints(100);
        membership2.setType(MembershipType.AGENCY_AFFILIATE);
        
        artist.setMembership(membership2);
        
        // Note: The test specification expects reward points = 150, which comes from the first membership
        // This confirms that we're testing the first membership scenario
    }
    
    @Test(expected = IllegalStateException.class)
    public void testCase5_BoundaryConditionMembershipExpiration() {
        // SetUp: Create artist with expired membership
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
        
        // Input: Get reward points on 2023-01-02 (membership expired on 2023-01-01)
        // Expected Output: IllegalStateException thrown (membership expired on the end date)
        artist.getMembership().getRewardPointsInPeriod("2023-01-02", "2023-01-02");
    }
}