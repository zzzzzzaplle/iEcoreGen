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
        // Set up artist
        Artist artist = new Artist();
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.FEMALE);
        
        // Set up membership
        Membership membership = new Membership();
        membership.setID("M001");
        membership.setStartDate(dateFormat.parse("2022-01-01"));
        membership.setEndDate(dateFormat.parse("2024-01-01"));
        membership.setRewardPoint(100);
        membership.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership);
        
        // Test parameters
        Date startDate = dateFormat.parse("2023-01-01");
        Date endDate = dateFormat.parse("2023-12-31");
        
        // Execute and verify
        int rewardPoints = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Reward points should be 100 for valid individual membership", 100, rewardPoints);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() throws Exception {
        // Set up artist
        Artist artist = new Artist();
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.MALE);
        
        // Set up membership
        Membership membership = new Membership();
        membership.setID("M002");
        membership.setStartDate(dateFormat.parse("2022-03-01"));
        membership.setEndDate(dateFormat.parse("2022-09-01"));
        membership.setRewardPoint(200);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Test parameters
        Date startDate = dateFormat.parse("2023-06-01");
        Date endDate = dateFormat.parse("2023-07-01");
        
        // Execute and verify exception
        try {
            artist.calculateRewardPoints(startDate, endDate);
            fail("Expected IllegalArgumentException for invalid membership period");
        } catch (IllegalArgumentException e) {
            assertEquals("Exception message should indicate membership is not valid", 
                         "Membership is not valid during the specified period", e.getMessage());
        }
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() throws Exception {
        // Set up artist
        Artist artist = new Artist();
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        // Set up membership
        Membership membership = new Membership();
        membership.setID("M003");
        membership.setStartDate(dateFormat.parse("2023-01-01"));
        membership.setEndDate(dateFormat.parse("2024-01-01"));
        membership.setRewardPoint(300);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Test parameters
        Date startDate = dateFormat.parse("2023-05-01");
        Date endDate = dateFormat.parse("2023-10-01");
        
        // Execute and verify
        int rewardPoints = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Reward points should be 300 for valid agency membership", 300, rewardPoints);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() throws Exception {
        // Set up artist
        Artist artist = new Artist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender(Gender.MALE);
        
        // Set up first membership
        Membership membership1 = new Membership();
        membership1.setID("M004");
        membership1.setStartDate(dateFormat.parse("2023-01-05"));
        membership1.setEndDate(dateFormat.parse("2023-06-01"));
        membership1.setRewardPoint(150);
        membership1.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership1);
        
        // Test parameters for first membership period
        Date startDate = dateFormat.parse("2023-01-06");
        Date endDate = dateFormat.parse("2023-01-21");
        
        // Execute and verify first membership
        int rewardPoints = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Reward points should be 150 for the first membership", 150, rewardPoints);
        
        // Update to second membership (this replaces the first one)
        Membership membership2 = new Membership();
        membership2.setID("M005");
        membership2.setStartDate(dateFormat.parse("2023-02-01"));
        membership2.setEndDate(dateFormat.parse("2024-02-01"));
        membership2.setRewardPoint(100);
        membership2.setType(MembershipType.AGENCY_AFFILIATE);
        
        artist.setMembership(membership2);
        
        // The test period should now be invalid for the new membership
        try {
            artist.calculateRewardPoints(startDate, endDate);
            fail("Expected IllegalArgumentException for invalid membership period after update");
        } catch (IllegalArgumentException e) {
            assertEquals("Exception message should indicate membership is not valid", 
                         "Membership is not valid during the specified period", e.getMessage());
        }
    }
    
    @Test
    public void testCase5_BoundaryCondition_MembershipExpiration() throws Exception {
        // Set up artist
        Artist artist = new Artist();
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender(Gender.FEMALE);
        
        // Set up membership
        Membership membership = new Membership();
        membership.setID("M006");
        membership.setStartDate(dateFormat.parse("2022-01-01"));
        membership.setEndDate(dateFormat.parse("2023-01-01"));
        membership.setRewardPoint(250);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Test parameters - single day after expiration
        Date startDate = dateFormat.parse("2023-01-02");
        Date endDate = dateFormat.parse("2023-01-02");
        
        // Execute and verify exception
        try {
            artist.calculateRewardPoints(startDate, endDate);
            fail("Expected IllegalArgumentException for expired membership");
        } catch (IllegalArgumentException e) {
            assertEquals("Exception message should indicate membership is not valid", 
                         "Membership is not valid during the specified period", e.getMessage());
        }
    }
}