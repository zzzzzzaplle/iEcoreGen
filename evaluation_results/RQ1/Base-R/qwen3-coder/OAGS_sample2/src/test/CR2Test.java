import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;

public class CR2Test {
    
    private Artist artist;
    
    @Before
    public void setUp() {
        artist = new Artist();
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() {
        // SetUp: Create artist A001 with individual membership
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
        membership.setType("individual");
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-01-01 to 2023-12-31
        int result = artist.getRewardPointsInPeriod("2023-01-01", "2023-12-31");
        
        // Expected Output: Reward points = 100
        assertEquals("Valid individual membership should return 100 reward points", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // SetUp: Create artist A002 with expired agency membership
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
        membership.setType("agency");
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-06-01 to 2023-07-01
        int result = artist.getRewardPointsInPeriod("2023-06-01", "2023-07-01");
        
        // Expected Output: Reward points = 0 (membership invalid during this period)
        assertEquals("Expired membership should return 0 reward points", 0, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // SetUp: Create artist A003 with valid agency membership
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
        membership.setType("agency");
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-05-01 to 2023-10-01
        int result = artist.getRewardPointsInPeriod("2023-05-01", "2023-10-01");
        
        // Expected Output: Reward points = 300
        assertEquals("Valid agency membership should return 300 reward points", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() {
        // SetUp: Create artist A004 with individual membership
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender("Male");
        
        // Assign individual membership
        Membership membership1 = new Membership();
        membership1.setId("M004");
        membership1.setStartDate("2023-01-05");
        membership1.setEndDate("2023-06-01");
        membership1.setRewardPoints(150);
        membership1.setType("individual");
        
        artist.setMembership(membership1);
        
        // Input: Get reward points from 2023-01-06 to 2023-01-21 using current membership
        int result = artist.getRewardPointsInPeriod("2023-01-06", "2023-01-21");
        
        // Expected Output: Reward points = 150 (from individual membership)
        assertEquals("Should return reward points from current valid membership", 150, result);
        
        // Update to agency affiliate membership (note: only one membership can be active at a time)
        Membership membership2 = new Membership();
        membership2.setId("M005");
        membership2.setStartDate("2023-02-01");
        membership2.setEndDate("2024-02-01");
        membership2.setRewardPoints(100);
        membership2.setType("agency affiliate");
        
        artist.setMembership(membership2);
        
        // Verify the membership update worked correctly
        assertEquals("M005", artist.getMembership().getId());
    }
    
    @Test
    public void testCase5_BoundaryConditionMembershipExpiration() {
        // SetUp: Create artist A005 with expired agency membership
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
        membership.setType("agency");
        
        artist.setMembership(membership);
        
        // Input: Get reward points on 2023-01-02 (single day)
        int result = artist.getRewardPointsInPeriod("2023-01-02", "2023-01-02");
        
        // Expected Output: Reward points = 0 (membership expired on the end date)
        assertEquals("Expired membership on boundary date should return 0", 0, result);
    }
}