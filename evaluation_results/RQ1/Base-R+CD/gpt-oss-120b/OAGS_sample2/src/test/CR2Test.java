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
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() throws Exception {
        // Create artist with ID A001
        Artist artist = new Artist();
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.FEMALE);
        
        // Assign individual membership
        Membership membership = new Membership();
        membership.setID("M001");
        membership.setStartDate(dateFormat.parse("2022-01-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2024-01-01 00:00:00"));
        membership.setRewardPoint(100);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Calculate reward points for period 2023-01-01 to 2023-12-31
        Date periodStart = dateFormat.parse("2023-01-01 00:00:00");
        Date periodEnd = dateFormat.parse("2023-12-31 00:00:00");
        int result = artist.calculateRewardPoints(periodStart, periodEnd);
        
        // Verify expected reward points = 100
        assertEquals("Reward points should be 100 for valid individual membership", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() throws Exception {
        // Create artist with ID A002
        Artist artist = new Artist();
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.MALE);
        
        // Assign agency membership with expired period
        Membership membership = new Membership();
        membership.setID("M002");
        membership.setStartDate(dateFormat.parse("2022-03-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2022-09-01 00:00:00"));
        membership.setRewardPoint(200);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Calculate reward points for period 2023-06-01 to 2023-07-01
        Date periodStart = dateFormat.parse("2023-06-01 00:00:00");
        Date periodEnd = dateFormat.parse("2023-07-01 00:00:00");
        int result = artist.calculateRewardPoints(periodStart, periodEnd);
        
        // Verify expected reward points = 0 (membership invalid during this period)
        assertEquals("Reward points should be 0 for invalid membership period", 0, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() throws Exception {
        // Create artist with ID A003
        Artist artist = new Artist();
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        // Assign agency membership
        Membership membership = new Membership();
        membership.setID("M003");
        membership.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2024-01-01 00:00:00"));
        membership.setRewardPoint(300);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Calculate reward points for period 2023-05-01 to 2023-10-01
        Date periodStart = dateFormat.parse("2023-05-01 00:00:00");
        Date periodEnd = dateFormat.parse("2023-10-01 00:00:00");
        int result = artist.calculateRewardPoints(periodStart, periodEnd);
        
        // Verify expected reward points = 300
        assertEquals("Reward points should be 300 for valid agency membership", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() throws Exception {
        // Create artist with ID A004
        Artist artist = new Artist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender(Gender.MALE);
        
        // Assign individual membership (first membership)
        Membership membership1 = new Membership();
        membership1.setID("M004");
        membership1.setStartDate(dateFormat.parse("2023-01-05 00:00:00"));
        membership1.setEndDate(dateFormat.parse("2023-06-01 00:00:00"));
        membership1.setRewardPoint(150);
        membership1.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership1);
        
        // Calculate reward points for period 2023-01-06 to 2023-01-21 with first membership
        Date periodStart = dateFormat.parse("2023-01-06 00:00:00");
        Date periodEnd = dateFormat.parse("2023-01-21 00:00:00");
        int result1 = artist.calculateRewardPoints(periodStart, periodEnd);
        
        // Verify first membership gives 150 points
        assertEquals("Reward points should be 150 for first valid membership", 150, result1);
        
        // Update to agency affiliate membership (second membership)
        Membership membership2 = new Membership();
        membership2.setID("M005");
        membership2.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        membership2.setEndDate(dateFormat.parse("2024-02-01 00:00:00"));
        membership2.setRewardPoint(100);
        membership2.setType(MembershipType.AGENCY_AFFILIATE);
        artist.setMembership(membership2);
        
        // Calculate reward points for same period with second membership
        int result2 = artist.calculateRewardPoints(periodStart, periodEnd);
        
        // Verify second membership gives 0 points (not valid for this period)
        assertEquals("Reward points should be 0 for second invalid membership", 0, result2);
    }
    
    @Test
    public void testCase5_BoundaryConditionMembershipExpiration() throws Exception {
        // Create artist with ID A005
        Artist artist = new Artist();
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender(Gender.FEMALE);
        
        // Assign agency membership that expires on 2023-01-01
        Membership membership = new Membership();
        membership.setID("M006");
        membership.setStartDate(dateFormat.parse("2022-01-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2023-01-01 00:00:00"));
        membership.setRewardPoint(250);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Calculate reward points for single day 2023-01-02 (day after expiration)
        Date periodStart = dateFormat.parse("2023-01-02 00:00:00");
        Date periodEnd = dateFormat.parse("2023-01-02 00:00:00");
        int result = artist.calculateRewardPoints(periodStart, periodEnd);
        
        // Verify expected reward points = 0 (membership expired on the end date)
        assertEquals("Reward points should be 0 for expired membership", 0, result);
    }
}