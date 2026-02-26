import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() throws ParseException {
        // Test Case 1: "Valid Individual Membership Reward Points Calculation"
        
        // SetUp
        Artist artist = new Artist();
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.FEMALE);
        
        Membership membership = new Membership();
        membership.setID("M001");
        membership.setStartDate(dateFormat.parse("2022-01-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2024-01-01 00:00:00"));
        membership.setRewardPoint(100);
        membership.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership);
        
        // Input dates
        Date startDate = dateFormat.parse("2023-01-01 00:00:00");
        Date endDate = dateFormat.parse("2023-12-31 23:59:59");
        
        // Execute method
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output
        assertEquals("Reward points should be 100 for valid individual membership", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() throws ParseException {
        // Test Case 2: "Invalid Membership Period Calculation"
        
        // SetUp
        Artist artist = new Artist();
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.MALE);
        
        Membership membership = new Membership();
        membership.setID("M002");
        membership.setStartDate(dateFormat.parse("2022-03-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2022-09-01 00:00:00"));
        membership.setRewardPoint(200);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Input dates
        Date startDate = dateFormat.parse("2023-06-01 00:00:00");
        Date endDate = dateFormat.parse("2023-07-01 23:59:59");
        
        // Execute method
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output
        assertEquals("Reward points should be 0 for invalid membership period", -1, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() throws ParseException {
        // Test Case 3: "Agency Membership Reward Points Calculation"
        
        // SetUp
        Artist artist = new Artist();
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        Membership membership = new Membership();
        membership.setID("M003");
        membership.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2024-01-01 00:00:00"));
        membership.setRewardPoint(300);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Input dates
        Date startDate = dateFormat.parse("2023-05-01 00:00:00");
        Date endDate = dateFormat.parse("2023-10-01 23:59:59");
        
        // Execute method
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output
        assertEquals("Reward points should be 300 for valid agency membership", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() throws ParseException {
        // Test Case 4: "Multiple Memberships Consideration"
        
        // SetUp
        Artist artist = new Artist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender(Gender.MALE);
        
        // First membership (individual) - this one should be active during the test period
        Membership membership1 = new Membership();
        membership1.setID("M004");
        membership1.setStartDate(dateFormat.parse("2023-01-05 00:00:00"));
        membership1.setEndDate(dateFormat.parse("2023-06-01 00:00:00"));
        membership1.setRewardPoint(150);
        membership1.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership1);
        
        // Input dates
        Date startDate = dateFormat.parse("2023-01-06 00:00:00");
        Date endDate = dateFormat.parse("2023-01-21 23:59:59");
        
        // Execute method with first membership
        int result1 = artist.calculateRewardPoints(startDate, endDate);
        
        // Update to second membership (agency affiliate) - this one starts later
        Membership membership2 = new Membership();
        membership2.setID("M005");
        membership2.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        membership2.setEndDate(dateFormat.parse("2024-02-01 00:00:00"));
        membership2.setRewardPoint(100);
        membership2.setType(MembershipType.AGENCY_AFFILIATE);
        
        artist.setMembership(membership2);
        
        // Execute method with second membership
        int result2 = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output - should use the first membership's reward points
        assertEquals("Reward points should be 150 from the first valid membership", 150, result1);
        assertEquals("Reward points should be -1 for second membership (not active during period)", -1, result2);
    }
    
    @Test
    public void testCase5_BoundaryConditionMembershipExpiration() throws ParseException {
        // Test Case 5: "Boundary Condition - Membership Expiration"
        
        // SetUp
        Artist artist = new Artist();
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender(Gender.FEMALE);
        
        Membership membership = new Membership();
        membership.setID("M006");
        membership.setStartDate(dateFormat.parse("2022-01-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2023-01-01 00:00:00"));
        membership.setRewardPoint(250);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Input dates - single day on 2023-01-02 (after expiration)
        Date startDate = dateFormat.parse("2023-01-02 00:00:00");
        Date endDate = dateFormat.parse("2023-01-02 23:59:59");
        
        // Execute method
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output
        assertEquals("Reward points should be 0 for expired membership", -1, result);
    }
}