import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() throws Exception {
        // SetUp: Create artist with individual membership
        Artist artist = new Artist();
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.FEMALE);
        
        Membership membership = new Membership();
        membership.setID("M001");
        membership.setStartDate(dateFormat.parse("2022-01-01"));
        membership.setEndDate(dateFormat.parse("2024-01-01"));
        membership.setRewardPoint(100);
        membership.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-01-01 to 2023-12-31
        Date startDate = dateFormat.parse("2023-01-01");
        Date endDate = dateFormat.parse("2023-12-31");
        
        // Expected Output: Reward points = 100
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Valid individual membership should return 100 reward points", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() throws Exception {
        // SetUp: Create artist with expired membership
        Artist artist = new Artist();
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.MALE);
        
        Membership membership = new Membership();
        membership.setID("M002");
        membership.setStartDate(dateFormat.parse("2022-03-01"));
        membership.setEndDate(dateFormat.parse("2022-09-01"));
        membership.setRewardPoint(200);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-06-01 to 2023-07-01
        Date startDate = dateFormat.parse("2023-06-01");
        Date endDate = dateFormat.parse("2023-07-01");
        
        // Expected Output: Reward points = 0 (membership invalid during this period)
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Invalid membership period should return 0 reward points", 0, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() throws Exception {
        // SetUp: Create artist with agency membership
        Artist artist = new Artist();
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        Membership membership = new Membership();
        membership.setID("M003");
        membership.setStartDate(dateFormat.parse("2023-01-01"));
        membership.setEndDate(dateFormat.parse("2024-01-01"));
        membership.setRewardPoint(300);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-05-01 to 2023-10-01
        Date startDate = dateFormat.parse("2023-05-01");
        Date endDate = dateFormat.parse("2023-10-01");
        
        // Expected Output: Reward points = 300
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Valid agency membership should return 300 reward points", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() throws Exception {
        // SetUp: Create artist with individual membership (only one membership is active at a time)
        Artist artist = new Artist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender(Gender.MALE);
        
        Membership membership = new Membership();
        membership.setID("M004");
        membership.setStartDate(dateFormat.parse("2023-01-05"));
        membership.setEndDate(dateFormat.parse("2023-06-01"));
        membership.setRewardPoint(150);
        membership.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-01-06 to 2023-01-21
        Date startDate = dateFormat.parse("2023-01-06");
        Date endDate = dateFormat.parse("2023-01-21");
        
        // Expected Output: Reward points = 150 (from the first membership)
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("First membership should return 150 reward points", 150, result);
    }
    
    @Test
    public void testCase5_BoundaryCondition_MembershipExpiration() throws Exception {
        // SetUp: Create artist with expired membership
        Artist artist = new Artist();
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender(Gender.FEMALE);
        
        Membership membership = new Membership();
        membership.setID("M006");
        membership.setStartDate(dateFormat.parse("2022-01-01"));
        membership.setEndDate(dateFormat.parse("2023-01-01"));
        membership.setRewardPoint(250);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Input: Get reward points on the 2023-01-02 (single day)
        Date startDate = dateFormat.parse("2023-01-02");
        Date endDate = dateFormat.parse("2023-01-02");
        
        // Expected Output: Reward points = 0 (membership expired on the end date)
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals("Expired membership should return 0 reward points", 0, result);
    }
}