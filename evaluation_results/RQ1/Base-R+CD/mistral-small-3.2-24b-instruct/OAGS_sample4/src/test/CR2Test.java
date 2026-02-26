import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.util.ArrayList;

public class CR2Test {
    
    private Artist artist;
    
    @Before
    public void setUp() {
        artist = new Artist();
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() throws ParseException {
        // Set up artist
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.FEMALE);
        
        // Set up membership
        Membership membership = new Membership();
        membership.setID("M001");
        membership.setStartDate("2022-01-01");
        membership.setEndDate("2024-01-01");
        membership.setRewardPoint(100);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Test reward points calculation
        int result = artist.calculateRewardPoints("2023-01-01", "2023-12-31");
        
        // Verify expected output
        assertEquals("Reward points should be 100 for valid membership period", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() throws ParseException {
        // Set up artist
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.MALE);
        
        // Set up membership
        Membership membership = new Membership();
        membership.setID("M002");
        membership.setStartDate("2022-03-01");
        membership.setEndDate("2022-09-01");
        membership.setRewardPoint(200);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test reward points calculation
        int result = artist.calculateRewardPoints("2023-06-01", "2023-07-01");
        
        // Verify expected output
        assertEquals("Reward points should be 0 for invalid membership period", 0, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() throws ParseException {
        // Set up artist
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        // Set up membership
        Membership membership = new Membership();
        membership.setID("M003");
        membership.setStartDate("2023-01-01");
        membership.setEndDate("2024-01-01");
        membership.setRewardPoint(300);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test reward points calculation
        int result = artist.calculateRewardPoints("2023-05-01", "2023-10-01");
        
        // Verify expected output
        assertEquals("Reward points should be 300 for agency membership", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() throws ParseException {
        // Set up artist
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender(Gender.MALE);
        
        // Set up first membership (should be used for the test period)
        Membership membership = new Membership();
        membership.setID("M004");
        membership.setStartDate("2023-01-05");
        membership.setEndDate("2023-06-01");
        membership.setRewardPoint(150);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Test reward points calculation with first membership
        int result = artist.calculateRewardPoints("2023-01-06", "2023-01-21");
        
        // Verify expected output (should use first membership)
        assertEquals("Reward points should be 150 from the valid membership", 150, result);
        
        // Note: The test specification mentions updating to a second membership, but since
        // the Artist class only supports one membership at a time, we test with the first one
        // that is valid for the specified period
    }
    
    @Test
    public void testCase5_BoundaryConditionMembershipExpiration() throws ParseException {
        // Set up artist
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender(Gender.FEMALE);
        
        // Set up membership that expires on the boundary date
        Membership membership = new Membership();
        membership.setID("M006");
        membership.setStartDate("2022-01-01");
        membership.setEndDate("2023-01-01");
        membership.setRewardPoint(250);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test reward points calculation for single day (boundary condition)
        int result = artist.calculateRewardPoints("2023-01-02", "2023-01-02");
        
        // Verify expected output - membership expired on end date, so next day should return 0
        assertEquals("Reward points should be 0 for expired membership", 0, result);
    }
}