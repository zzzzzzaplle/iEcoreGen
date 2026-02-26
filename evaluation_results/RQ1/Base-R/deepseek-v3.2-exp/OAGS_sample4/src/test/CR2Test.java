import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Artist artist;
    
    @Before
    public void setUp() {
        // Initialize a fresh artist object before each test
        artist = new Artist();
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
        
        // Create and set membership
        Membership membership = new Membership();
        membership.setId("M001");
        membership.setStartDate("2022-01-01");
        membership.setEndDate("2024-01-01");
        membership.setRewardPoints(100);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Test reward points calculation for specified period
        int rewardPoints = artist.getRewardPointsInPeriod("2023-01-01", "2023-12-31");
        
        // Verify expected output
        assertEquals("Reward points should be 100 for valid membership period", 100, rewardPoints);
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
        
        // Create and set membership
        Membership membership = new Membership();
        membership.setId("M002");
        membership.setStartDate("2022-03-01");
        membership.setEndDate("2022-09-01");
        membership.setRewardPoints(200);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test reward points calculation for period outside membership
        int rewardPoints = artist.getRewardPointsInPeriod("2023-06-01", "2023-07-01");
        
        // Verify expected output
        assertEquals("Reward points should be 0 for invalid membership period", 0, rewardPoints);
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
        
        // Create and set membership
        Membership membership = new Membership();
        membership.setId("M003");
        membership.setStartDate("2023-01-01");
        membership.setEndDate("2024-01-01");
        membership.setRewardPoints(300);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test reward points calculation for specified period
        int rewardPoints = artist.getRewardPointsInPeriod("2023-05-01", "2023-10-01");
        
        // Verify expected output
        assertEquals("Reward points should be 300 for valid agency membership period", 300, rewardPoints);
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
        
        // Create and set first membership (this one should be active during the test period)
        Membership membership = new Membership();
        membership.setId("M004");
        membership.setStartDate("2023-01-05");
        membership.setEndDate("2023-06-01");
        membership.setRewardPoints(150);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Note: The test specification mentions updating to a second membership, but according to the Artist class,
        // an artist can only have one membership at a time. The second membership assignment would overwrite the first.
        // Since the test period (2023-01-06 to 2023-01-21) falls within the first membership's validity,
        // we'll test with the first membership only.
        
        // Test reward points calculation for specified period
        int rewardPoints = artist.getRewardPointsInPeriod("2023-01-06", "2023-01-21");
        
        // Verify expected output
        assertEquals("Reward points should be 150 for the active membership during the period", 150, rewardPoints);
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
        
        // Create and set membership
        Membership membership = new Membership();
        membership.setId("M006");
        membership.setStartDate("2022-01-01");
        membership.setEndDate("2023-01-01");
        membership.setRewardPoints(250);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test reward points calculation for single day after membership expiration
        int rewardPoints = artist.getRewardPointsInPeriod("2023-01-02", "2023-01-02");
        
        // Verify expected output
        assertEquals("Reward points should be 0 for period after membership expiration", 0, rewardPoints);
    }
}