import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CR2Test {
    private Artist artist;
    private SimpleDateFormat formatter;
    
    @Before
    public void setUp() {
        artist = new Artist();
        formatter = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() throws ParseException {
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
        membership.setType("individual");
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-01-01 to 2023-12-31
        int result = artist.getRewardPointsWithinPeriod("2023-01-01", "2023-12-31");
        
        // Expected Output: Reward points = 100
        assertEquals("Reward points should be 100 for valid individual membership", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() throws ParseException {
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
        membership.setType("agency");
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-06-01 to 2023-07-01
        int result = artist.getRewardPointsWithinPeriod("2023-06-01", "2023-07-01");
        
        // Expected Output: Reward points = 0 (membership invalid during this period)
        assertEquals("Reward points should be 0 for invalid membership period", 0, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() throws ParseException {
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
        membership.setType("agency");
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-05-01 to 2023-10-01
        int result = artist.getRewardPointsWithinPeriod("2023-05-01", "2023-10-01");
        
        // Expected Output: Reward points = 300
        assertEquals("Reward points should be 300 for valid agency membership", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() throws ParseException {
        // SetUp: Create artist A004 with individual membership
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender("Male");
        
        // Assign individual membership first
        Membership membership1 = new Membership();
        membership1.setId("M004");
        membership1.setStartDate("2023-01-05");
        membership1.setEndDate("2023-06-01");
        membership1.setRewardPoints(150);
        membership1.setType("individual");
        artist.setMembership(membership1);
        
        // Input: Get reward points from 2023-01-06 to 2023-01-21 (using first membership)
        int result1 = artist.getRewardPointsWithinPeriod("2023-01-06", "2023-01-21");
        
        // Update to agency affiliate membership
        Membership membership2 = new Membership();
        membership2.setId("M005");
        membership2.setStartDate("2023-02-01");
        membership2.setEndDate("2024-02-01");
        membership2.setRewardPoints(100);
        membership2.setType("agency affiliate");
        artist.setMembership(membership2);
        
        // Expected Output: Reward points = 150 (from first membership during the period)
        assertEquals("Reward points should be 150 from the first membership", 150, result1);
    }
    
    @Test
    public void testCase5_BoundaryConditionMembershipExpiration() throws ParseException {
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
        membership.setType("agency");
        artist.setMembership(membership);
        
        // Input: Get reward points for 2023-01-02 (single day)
        int result = artist.getRewardPointsWithinPeriod("2023-01-02", "2023-01-02");
        
        // Expected Output: Reward points = 0 (membership expired on the end date)
        assertEquals("Reward points should be 0 when membership expired", 0, result);
    }
}