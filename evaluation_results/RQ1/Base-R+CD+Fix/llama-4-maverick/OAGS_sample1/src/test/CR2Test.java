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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() throws Exception {
        // Test Case 1: "Valid Individual Membership Reward Points Calculation"
        
        // Create artist with specified attributes
        Artist artist = new Artist();
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.FEMALE);
        
        // Create and assign individual membership
        Membership membership = new Membership();
        membership.setID("M001");
        membership.setStartDate(dateFormat.parse("2022-01-01"));
        membership.setEndDate(dateFormat.parse("2024-01-01"));
        membership.setRewardPoint(100);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Define test period
        Date startDate = dateFormat.parse("2023-01-01");
        Date endDate = dateFormat.parse("2023-12-31");
        
        // Calculate reward points and verify expected result
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Reward points should be 100 for valid individual membership", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() throws Exception {
        // Test Case 2: "Invalid Membership Period Calculation"
        
        // Create artist with specified attributes
        Artist artist = new Artist();
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.MALE);
        
        // Create and assign agency membership
        Membership membership = new Membership();
        membership.setID("M002");
        membership.setStartDate(dateFormat.parse("2022-03-01"));
        membership.setEndDate(dateFormat.parse("2022-09-01"));
        membership.setRewardPoint(200);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Define test period (outside membership validity)
        Date startDate = dateFormat.parse("2023-06-01");
        Date endDate = dateFormat.parse("2023-07-01");
        
        // Calculate reward points and verify expected result
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Reward points should be -1 for invalid membership period", -1, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() throws Exception {
        // Test Case 3: "Agency Membership Reward Points Calculation"
        
        // Create artist with specified attributes
        Artist artist = new Artist();
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        // Create and assign agency membership
        Membership membership = new Membership();
        membership.setID("M003");
        membership.setStartDate(dateFormat.parse("2023-01-01"));
        membership.setEndDate(dateFormat.parse("2024-01-01"));
        membership.setRewardPoint(300);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Define test period
        Date startDate = dateFormat.parse("2023-05-01");
        Date endDate = dateFormat.parse("2023-10-01");
        
        // Calculate reward points and verify expected result
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Reward points should be 300 for valid agency membership", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() throws Exception {
        // Test Case 4: "Multiple Memberships Consideration"
        
        // Create artist with specified attributes
        Artist artist = new Artist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender(Gender.MALE);
        
        // Create and assign individual membership (first membership)
        Membership membership1 = new Membership();
        membership1.setID("M004");
        membership1.setStartDate(dateFormat.parse("2023-01-05"));
        membership1.setEndDate(dateFormat.parse("2023-06-01"));
        membership1.setRewardPoint(150);
        membership1.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership1);
        
        // Define test period for first membership
        Date startDate = dateFormat.parse("2023-01-06");
        Date endDate = dateFormat.parse("2023-01-21");
        
        // Calculate reward points with first membership and verify expected result
        int result1 = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Reward points should be 150 for valid individual membership", 150, result1);
        
        // Update to assign agency affiliate membership (second membership)
        Membership membership2 = new Membership();
        membership2.setID("M005");
        membership2.setStartDate(dateFormat.parse("2023-02-01"));
        membership2.setEndDate(dateFormat.parse("2024-02-01"));
        membership2.setRewardPoint(100);
        membership2.setType(MembershipType.AGENCY_AFFILIATE);
        artist.setMembership(membership2);
        
        // Calculate reward points with second membership for same period (should be invalid)
        int result2 = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Reward points should be -1 for invalid membership period with second membership", -1, result2);
    }
    
    @Test
    public void testCase5_BoundaryCondition_MembershipExpiration() throws Exception {
        // Test Case 5: "Boundary Condition - Membership Expiration"
        
        // Create artist with specified attributes
        Artist artist = new Artist();
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender(Gender.FEMALE);
        
        // Create and assign agency membership
        Membership membership = new Membership();
        membership.setID("M006");
        membership.setStartDate(dateFormat.parse("2022-01-01"));
        membership.setEndDate(dateFormat.parse("2023-01-01"));
        membership.setRewardPoint(250);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Define test period as single day (membership expiration date)
        Date startDate = dateFormat.parse("2023-01-02");
        Date endDate = dateFormat.parse("2023-01-02");
        
        // Calculate reward points and verify expected result
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Reward points should be -1 for expired membership", -1, result);
    }
}