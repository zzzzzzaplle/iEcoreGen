import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    private Artist artist;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        artist = new Artist();
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() throws Exception {
        // SetUp
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
        
        // Input
        Date startDate = dateFormat.parse("2023-01-01 00:00:00");
        Date endDate = dateFormat.parse("2023-12-31 23:59:59");
        
        // Expected Output: Reward points = 100
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals(100, result);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase2_InvalidMembershipPeriodCalculation() throws Exception {
        // SetUp
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
        
        // Input
        Date startDate = dateFormat.parse("2023-06-01 00:00:00");
        Date endDate = dateFormat.parse("2023-07-01 23:59:59");
        
        // Expected Output: IllegalArgumentException (membership invalid during this period)
        artist.calculateRewardPoints(startDate, endDate);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() throws Exception {
        // SetUp
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
        
        // Input
        Date startDate = dateFormat.parse("2023-05-01 00:00:00");
        Date endDate = dateFormat.parse("2023-10-01 23:59:59");
        
        // Expected Output: Reward points = 300
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals(300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() throws Exception {
        // SetUp
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender(Gender.MALE);
        
        Membership membership = new Membership();
        membership.setID("M004");
        membership.setStartDate(dateFormat.parse("2023-01-05 00:00:00"));
        membership.setEndDate(dateFormat.parse("2023-06-01 00:00:00"));
        membership.setRewardPoint(150);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Note: The second membership assignment is ignored since we can only set one membership
        // The test follows the specification by using the first membership
        
        // Input
        Date startDate = dateFormat.parse("2023-01-06 00:00:00");
        Date endDate = dateFormat.parse("2023-01-21 23:59:59");
        
        // Expected Output: Reward points = 150
        int result = artist.calculateRewardPoints(startDate, endDate);
        assertEquals(150, result);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase5_BoundaryCondition_MembershipExpiration() throws Exception {
        // SetUp
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
        
        // Input
        Date startDate = dateFormat.parse("2023-01-02 00:00:00");
        Date endDate = dateFormat.parse("2023-01-02 23:59:59");
        
        // Expected Output: IllegalArgumentException (membership expired on the end date)
        artist.calculateRewardPoints(startDate, endDate);
    }
}