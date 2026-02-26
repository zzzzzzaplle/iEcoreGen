import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR2Test {
    
    private Artist artist;
    private Membership membership;
    
    @Before
    public void setUp() {
        artist = new Artist();
        membership = new Membership();
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() {
        // SetUp: Create artist A001 with individual membership
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.FEMALE);
        
        membership.setID("M001");
        membership.setStartDate("2022-01-01");
        membership.setEndDate("2024-01-01");
        membership.setRewardPoint(100);
        membership.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-01-01 to 2023-12-31
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        
        // Expected Output: Reward points = 100
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Valid individual membership should return 100 reward points", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // SetUp: Create artist A002 with agency membership that expired before the period
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.MALE);
        
        membership.setID("M002");
        membership.setStartDate("2022-03-01");
        membership.setEndDate("2022-09-01");
        membership.setRewardPoint(200);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-06-01 to 2023-07-01
        LocalDate startDate = LocalDate.of(2023, 6, 1);
        LocalDate endDate = LocalDate.of(2023, 7, 1);
        
        // Expected Output: Reward points = 0 (membership invalid during this period)
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Invalid membership period should return 0 reward points", 0, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // SetUp: Create artist A003 with valid agency membership
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        membership.setID("M003");
        membership.setStartDate("2023-01-01");
        membership.setEndDate("2024-01-01");
        membership.setRewardPoint(300);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-05-01 to 2023-10-01
        LocalDate startDate = LocalDate.of(2023, 5, 1);
        LocalDate endDate = LocalDate.of(2023, 10, 1);
        
        // Expected Output: Reward points = 300
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Valid agency membership should return 300 reward points", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() {
        // SetUp: Create artist A004 with initial individual membership
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender(Gender.MALE);
        
        // First membership: individual
        Membership firstMembership = new Membership();
        firstMembership.setID("M004");
        firstMembership.setStartDate("2023-01-05");
        firstMembership.setEndDate("2023-06-01");
        firstMembership.setRewardPoint(150);
        firstMembership.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(firstMembership);
        
        // Input: Get reward points from 2023-01-06 to 2023-01-21 (first membership is valid)
        LocalDate startDate = LocalDate.of(2023, 1, 6);
        LocalDate endDate = LocalDate.of(2023, 1, 21);
        
        // Expected Output: Reward points = 150 (from first membership)
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Should return reward points from the valid first membership", 150, result);
        
        // Update to second membership: agency affiliate
        Membership secondMembership = new Membership();
        secondMembership.setID("M005");
        secondMembership.setStartDate("2023-02-01");
        secondMembership.setEndDate("2024-02-01");
        secondMembership.setRewardPoint(100);
        secondMembership.setType(MembershipType.AGENCY_AFFILIATE);
        
        artist.setMembership(secondMembership);
        
        // Second membership is not valid during the period, should still return 0
        result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Second membership not valid during period should return 0", 0, result);
    }
    
    @Test
    public void testCase5_BoundaryCondition_MembershipExpiration() {
        // SetUp: Create artist A005 with membership ending on 2023-01-01
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender(Gender.FEMALE);
        
        membership.setID("M006");
        membership.setStartDate("2022-01-01");
        membership.setEndDate("2023-01-01");
        membership.setRewardPoint(250);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Input: Get reward points on 2023-01-02 (single day period)
        LocalDate startDate = LocalDate.of(2023, 1, 2);
        LocalDate endDate = LocalDate.of(2023, 1, 2);
        
        // Expected Output: Reward points = 0 (membership expired on the end date)
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Membership expired on end date should return 0 reward points", 0, result);
    }
}