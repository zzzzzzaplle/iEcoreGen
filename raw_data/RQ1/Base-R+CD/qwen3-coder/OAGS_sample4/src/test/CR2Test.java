import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize date format for parsing dates
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() throws Exception {
        // Test Case 1: Valid Individual Membership Reward Points Calculation
        // Input: Get reward points for artist with ID A001 from 2023-01-01 to 2023-12-31
        
        // SetUp: Create artist A001
        Artist artist = new Artist();
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.FEMALE);
        
        // SetUp: Assign individual membership to artist A001
        Membership membership = new Membership();
        membership.setID("M001");
        membership.setStartDate(dateFormat.parse("2022-01-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2024-01-01 00:00:00"));
        membership.setRewardPoint(100);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Test input dates
        Date startDate = dateFormat.parse("2023-01-01 00:00:00");
        Date endDate = dateFormat.parse("2023-12-31 23:59:59");
        
        // Execute method and verify result
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Reward points should be 100", 100, result);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase2_InvalidMembershipPeriodCalculation() throws Exception {
        // Test Case 2: Invalid Membership Period Calculation
        // Input: Get reward points for artist with ID A002 from 2023-06-01 to 2023-07-01
        
        // SetUp: Create artist A002
        Artist artist = new Artist();
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.MALE);
        
        // SetUp: Assign agency membership to artist A002
        Membership membership = new Membership();
        membership.setID("M002");
        membership.setStartDate(dateFormat.parse("2022-03-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2022-09-01 00:00:00"));
        membership.setRewardPoint(200);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test input dates
        Date startDate = dateFormat.parse("2023-06-01 00:00:00");
        Date endDate = dateFormat.parse("2023-07-01 23:59:59");
        
        // Execute method - should throw IllegalArgumentException
        artist.calculateRewardPoints(startDate, endDate);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() throws Exception {
        // Test Case 3: Agency Membership Reward Points Calculation
        // Input: Get reward points for artist with ID A003 from 2023-05-01 to 2023-10-01
        
        // SetUp: Create artist A003
        Artist artist = new Artist();
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        // SetUp: Assign agency membership to artist A003
        Membership membership = new Membership();
        membership.setID("M003");
        membership.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2024-01-01 00:00:00"));
        membership.setRewardPoint(300);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test input dates
        Date startDate = dateFormat.parse("2023-05-01 00:00:00");
        Date endDate = dateFormat.parse("2023-10-01 23:59:59");
        
        // Execute method and verify result
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Reward points should be 300", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() throws Exception {
        // Test Case 4: Multiple Memberships Consideration
        // Input: Get reward points for artist with ID A004 from 2023-01-06 to 2023-01-21
        
        // SetUp: Create artist A004
        Artist artist = new Artist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender(Gender.MALE);
        
        // SetUp: Assign individual membership to artist A004 (this is the active one during the period)
        Membership membership = new Membership();
        membership.setID("M004");
        membership.setStartDate(dateFormat.parse("2023-01-05 00:00:00"));
        membership.setEndDate(dateFormat.parse("2023-06-01 00:00:00"));
        membership.setRewardPoint(150);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Note: The second membership (M005) is not assigned to the artist, 
        // so only the first membership (M004) is considered
        
        // Test input dates
        Date startDate = dateFormat.parse("2023-01-06 00:00:00");
        Date endDate = dateFormat.parse("2023-01-21 23:59:59");
        
        // Execute method and verify result
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Reward points should be 150", 150, result);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase5_BoundaryConditionMembershipExpiration() throws Exception {
        // Test Case 5: Boundary Condition - Membership Expiration
        // Input: Get reward points for artist with ID A005 on the 2023-01-02
        
        // SetUp: Create artist A005
        Artist artist = new Artist();
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender(Gender.FEMALE);
        
        // SetUp: Assign agency membership to artist A005
        Membership membership = new Membership();
        membership.setID("M006");
        membership.setStartDate(dateFormat.parse("2022-01-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2023-01-01 00:00:00"));
        membership.setRewardPoint(250);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test input dates - single day after expiration
        Date startDate = dateFormat.parse("2023-01-02 00:00:00");
        Date endDate = dateFormat.parse("2023-01-02 23:59:59");
        
        // Execute method - should throw IllegalArgumentException
        artist.calculateRewardPoints(startDate, endDate);
    }
}