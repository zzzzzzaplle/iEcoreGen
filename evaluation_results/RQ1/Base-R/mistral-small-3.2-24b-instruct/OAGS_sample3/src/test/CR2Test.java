import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

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
        membership.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-01-01 to 2023-12-31
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        
        // Expected Output: Reward points = 100
        int result = artist.getRewardPointsWithinPeriod(startDate, endDate);
        assertEquals(100, result);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // SetUp: Create artist A002 with agency membership
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
        LocalDate startDate = LocalDate.of(2023, 6, 1);
        LocalDate endDate = LocalDate.of(2023, 7, 1);
        
        // Expected Output: IllegalArgumentException (membership invalid during this period)
        artist.getRewardPointsWithinPeriod(startDate, endDate);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // SetUp: Create artist A003 with agency membership
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
        LocalDate startDate = LocalDate.of(2023, 5, 1);
        LocalDate endDate = LocalDate.of(2023, 10, 1);
        
        // Expected Output: Reward points = 300
        int result = artist.getRewardPointsWithinPeriod(startDate, endDate);
        assertEquals(300, result);
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
        
        // Assign initial individual membership
        Membership membership1 = new Membership();
        membership1.setId("M004");
        membership1.setStartDate("2023-01-05");
        membership1.setEndDate("2023-06-01");
        membership1.setRewardPoints(150);
        membership1.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership1);
        
        // Input: Get reward points from 2023-01-06 to 2023-01-21
        LocalDate startDate = LocalDate.of(2023, 1, 6);
        LocalDate endDate = LocalDate.of(2023, 1, 21);
        
        // Expected Output: Reward points = 150 (using the first membership)
        int result = artist.getRewardPointsWithinPeriod(startDate, endDate);
        assertEquals(150, result);
        
        // Update to agency affiliate membership (second membership)
        Membership membership2 = new Membership();
        membership2.setId("M005");
        membership2.setStartDate("2023-02-01");
        membership2.setEndDate("2024-02-01");
        membership2.setRewardPoints(100);
        membership2.setType(MembershipType.AGENCY_AFFILIATE);
        
        artist.setMembership(membership2);
        
        // For the same period, should now throw exception since second membership is not valid
        try {
            artist.getRewardPointsWithinPeriod(startDate, endDate);
            fail("Expected IllegalArgumentException for invalid membership period");
        } catch (IllegalArgumentException e) {
            assertEquals("Membership is not valid within the specified period.", e.getMessage());
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase5_BoundaryCondition_MembershipExpiration() {
        // SetUp: Create artist A005 with agency membership
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
        
        // Input: Get reward points on 2023-01-02 (single day)
        LocalDate startDate = LocalDate.of(2023, 1, 2);
        LocalDate endDate = LocalDate.of(2023, 1, 2);
        
        // Expected Output: IllegalArgumentException (membership expired on the end date)
        artist.getRewardPointsWithinPeriod(startDate, endDate);
    }
}