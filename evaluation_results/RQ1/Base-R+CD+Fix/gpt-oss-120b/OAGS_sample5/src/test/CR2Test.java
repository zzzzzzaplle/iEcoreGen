import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    private Date parseDate(String dateStr) throws Exception {
        return dateFormat.parse(dateStr + " 00:00:00");
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() throws Exception {
        // SetUp: Create artist A001 with individual membership
        Artist artist = new Artist();
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.FEMALE);
        
        Membership membership = new Membership();
        membership.setID("M001");
        membership.setStartDate(parseDate("2022-01-01"));
        membership.setEndDate(parseDate("2024-01-01"));
        membership.setRewardPoint(100);
        membership.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-01-01 to 2023-12-31
        Date startDate = parseDate("2023-01-01");
        Date endDate = parseDate("2023-12-31");
        
        // Expected Output: Reward points = 100
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals(100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() throws Exception {
        // SetUp: Create artist A002 with agency membership
        Artist artist = new Artist();
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.MALE);
        
        Membership membership = new Membership();
        membership.setID("M002");
        membership.setStartDate(parseDate("2022-03-01"));
        membership.setEndDate(parseDate("2022-09-01"));
        membership.setRewardPoint(200);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-06-01 to 2023-07-01
        Date startDate = parseDate("2023-06-01");
        Date endDate = parseDate("2023-07-01");
        
        // Expected Output: Reward points = 0 (membership invalid during this period)
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() throws Exception {
        // SetUp: Create artist A003 with agency membership
        Artist artist = new Artist();
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        Membership membership = new Membership();
        membership.setID("M003");
        membership.setStartDate(parseDate("2023-01-01"));
        membership.setEndDate(parseDate("2024-01-01"));
        membership.setRewardPoint(300);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-05-01 to 2023-10-01
        Date startDate = parseDate("2023-05-01");
        Date endDate = parseDate("2023-10-01");
        
        // Expected Output: Reward points = 300
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals(300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() throws Exception {
        // SetUp: Create artist A004 with initial individual membership
        Artist artist = new Artist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender(Gender.MALE);
        
        // First membership
        Membership membership1 = new Membership();
        membership1.setID("M004");
        membership1.setStartDate(parseDate("2023-01-05"));
        membership1.setEndDate(parseDate("2023-06-01"));
        membership1.setRewardPoint(150);
        membership1.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership1);
        
        // Second membership (should not affect calculation as we're using the current membership)
        Membership membership2 = new Membership();
        membership2.setID("M005");
        membership2.setStartDate(parseDate("2023-02-01"));
        membership2.setEndDate(parseDate("2024-02-01"));
        membership2.setRewardPoint(100);
        membership2.setType(MembershipType.AGENCY_AFFILIATE);
        
        // Input: Get reward points from 2023-01-06 to 2023-01-21
        Date startDate = parseDate("2023-01-06");
        Date endDate = parseDate("2023-01-21");
        
        // Test with first membership
        int result1 = artist.calculateRewardPoints(startDate, endDate);
        assertEquals(150, result1);
        
        // Update to second membership and test again
        artist.setMembership(membership2);
        int result2 = artist.calculateRewardPoints(startDate, endDate);
        assertEquals(0, result2); // Second membership starts on 2023-02-01, so invalid for this period
    }
    
    @Test
    public void testCase5_BoundaryCondition_MembershipExpiration() throws Exception {
        // SetUp: Create artist A005 with agency membership
        Artist artist = new Artist();
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender(Gender.FEMALE);
        
        Membership membership = new Membership();
        membership.setID("M006");
        membership.setStartDate(parseDate("2022-01-01"));
        membership.setEndDate(parseDate("2023-01-01"));
        membership.setRewardPoint(250);
        membership.setType(MembershipType.AGENCY);
        
        artist.setMembership(membership);
        
        // Input: Get reward points on 2023-01-02 (single day)
        Date startDate = parseDate("2023-01-02");
        Date endDate = parseDate("2023-01-02");
        
        // Expected Output: Reward points = 0 (membership expired on the end date)
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals(0, result);
    }
}