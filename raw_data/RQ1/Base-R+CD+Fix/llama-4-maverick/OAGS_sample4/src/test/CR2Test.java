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
        // Test Case 1: "Valid Individual Membership Reward Points Calculation"
        // Input: Get reward points for artist with ID A001 from 2023-01-01 to 2023-12-31.
        
        // SetUp:
        // 1. Create an artist with ID: A001, name: "Alice", phone number: "123456789", 
        //    email: "alice@example.com", address: "123 Art St", gender: "Female".
        Artist artist = new Artist();
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.FEMALE);
        
        // 2. Assign an individual membership to artist A001 with ID: M001, 
        //    start date: 2022-01-01, end date: 2024-01-01, and reward points: 100.
        Membership membership = new Membership();
        membership.setID("M001");
        membership.setStartDate(dateFormat.parse("2022-01-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2024-01-01 00:00:00"));
        membership.setRewardPoint(100);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Test the reward points calculation
        Date startDate = dateFormat.parse("2023-01-01 00:00:00");
        Date endDate = dateFormat.parse("2023-12-31 23:59:59");
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        // Expected Output: Reward points = 100
        assertEquals("Reward points should be 100 for valid membership period", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() throws Exception {
        // Test Case 2: "Invalid Membership Period Calculation"
        // Input: Get reward points for artist with ID A002 from 2023-06-01 to 2023-07-01.
        
        // SetUp:
        // 1. Create an artist with ID: A002, name: "Bob", phone number: "987654321", 
        //    email: "bob@example.com", address: "456 Art Ave", gender: "Male".
        Artist artist = new Artist();
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.MALE);
        
        // 2. Assign an agency membership to artist A002 with ID: M002, 
        //    start date: 2022-03-01, end date: 2022-09-01, and reward points: 200.
        Membership membership = new Membership();
        membership.setID("M002");
        membership.setStartDate(dateFormat.parse("2022-03-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2022-09-01 00:00:00"));
        membership.setRewardPoint(200);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test the reward points calculation
        Date startDate = dateFormat.parse("2023-06-01 00:00:00");
        Date endDate = dateFormat.parse("2023-07-01 23:59:59");
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        // Expected Output: Reward points = 0 (membership invalid during this period)
        assertEquals("Reward points should be 0 for invalid membership period", -1, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() throws Exception {
        // Test Case 3: "Agency Membership Reward Points Calculation"
        // Input: Get reward points for artist with ID A003 from 2023-05-01 to 2023-10-01.
        
        // SetUp:
        // 1. Create an artist with ID: A003, name: "Catherine", phone number: "135792468", 
        //    email: "catherine@example.com", address: "789 Art Blvd", gender: "Female".
        Artist artist = new Artist();
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        // 2. Assign an agency membership to artist A003 with ID: M003, 
        //    start date: 2023-01-01, end date: 2024-01-01, and reward points: 300.
        Membership membership = new Membership();
        membership.setID("M003");
        membership.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2024-01-01 00:00:00"));
        membership.setRewardPoint(300);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test the reward points calculation
        Date startDate = dateFormat.parse("2023-05-01 00:00:00");
        Date endDate = dateFormat.parse("2023-10-01 23:59:59");
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        // Expected Output: Reward points = 300
        assertEquals("Reward points should be 300 for valid agency membership", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() throws Exception {
        // Test Case 4: "Multiple Memberships Consideration"
        // Input: Get reward points for artist with ID A004 from 2023-01-06 to 2023-01-21.
        
        // SetUp:
        // 1. Create an artist with ID: A004, name: "David", phone number: "246813579", 
        //    email: "david@example.com", address: "321 Art Rd", gender: "Male".
        Artist artist = new Artist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender(Gender.MALE);
        
        // 2. Assign an individual membership to artist A004 with ID: M004, 
        //    start date: 2023-01-05, end date: 2023-06-01, and reward points: 150.
        Membership membership1 = new Membership();
        membership1.setID("M004");
        membership1.setStartDate(dateFormat.parse("2023-01-05 00:00:00"));
        membership1.setEndDate(dateFormat.parse("2023-06-01 00:00:00"));
        membership1.setRewardPoint(150);
        membership1.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership1);
        
        // Test the reward points calculation with first membership
        Date startDate = dateFormat.parse("2023-01-06 00:00:00");
        Date endDate = dateFormat.parse("2023-01-21 23:59:59");
        int result1 = artist.calculateRewardPoints(startDate, endDate);
        
        // 3. Update to assign an agency affiliate membership with ID: M005, 
        //    start date: 2023-02-01, end date: 2024-02-01, and reward points: 100.
        Membership membership2 = new Membership();
        membership2.setID("M005");
        membership2.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        membership2.setEndDate(dateFormat.parse("2024-02-01 00:00:00"));
        membership2.setRewardPoint(100);
        membership2.setType(MembershipType.AGENCY_AFFILIATE);
        artist.setMembership(membership2);
        
        // Test the reward points calculation with second membership for the same period
        int result2 = artist.calculateRewardPoints(startDate, endDate);
        
        // Expected Output: Reward points = 150 (first membership should be used)
        assertEquals("Reward points should be 150 from the first valid membership", 150, result1);
        assertEquals("Reward points should be -1 as second membership is not valid for the period", -1, result2);
    }
    
    @Test
    public void testCase5_BoundaryCondition_MembershipExpiration() throws Exception {
        // Test Case 5: "Boundary Condition - Membership Expiration"
        // Input: Get reward points for artist with ID A005 on the 2023-01-02.
        
        // SetUp:
        // 1. Create an artist with ID: A005, name: "Eva", phone number: "864209753", 
        //    email: "eva@example.com", address: "654 Art Pl", gender: "Female".
        Artist artist = new Artist();
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender(Gender.FEMALE);
        
        // 2. Assign an agency membership to artist A005 with ID: M006, 
        //    start date: 2022-01-01, end date: 2023-01-01, and reward points: 250.
        Membership membership = new Membership();
        membership.setID("M006");
        membership.setStartDate(dateFormat.parse("2022-01-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2023-01-01 00:00:00"));
        membership.setRewardPoint(250);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test the reward points calculation on the day after expiration
        Date startDate = dateFormat.parse("2023-01-02 00:00:00");
        Date endDate = dateFormat.parse("2023-01-02 23:59:59");
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        // Expected Output: Reward points = 0 (membership expired on the end date)
        assertEquals("Reward points should be -1 as membership expired the day before", -1, result);
    }
}