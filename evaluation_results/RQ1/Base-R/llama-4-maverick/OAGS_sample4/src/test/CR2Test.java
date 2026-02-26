import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR2Test {
    
    private Artist artist1;
    private Artist artist2;
    private Artist artist3;
    private Artist artist4;
    private Artist artist5;
    
    @Before
    public void setUp() {
        // Set up artists and memberships for all test cases
        // Test Case 1: Artist A001 with valid individual membership
        artist1 = new Artist();
        artist1.setId("A001");
        artist1.setName("Alice");
        artist1.setPhoneNumber("123456789");
        artist1.setEmail("alice@example.com");
        artist1.setAddress("123 Art St");
        artist1.setGender("Female");
        
        Membership membership1 = new Membership();
        membership1.setId("M001");
        membership1.setStartDate(LocalDate.parse("2022-01-01"));
        membership1.setEndDate(LocalDate.parse("2024-01-01"));
        membership1.setRewardPoints(100);
        membership1.setType(MembershipType.INDIVIDUAL);
        artist1.setMembership(membership1);
        
        // Test Case 2: Artist A002 with expired agency membership
        artist2 = new Artist();
        artist2.setId("A002");
        artist2.setName("Bob");
        artist2.setPhoneNumber("987654321");
        artist2.setEmail("bob@example.com");
        artist2.setAddress("456 Art Ave");
        artist2.setGender("Male");
        
        Membership membership2 = new Membership();
        membership2.setId("M002");
        membership2.setStartDate(LocalDate.parse("2022-03-01"));
        membership2.setEndDate(LocalDate.parse("2022-09-01"));
        membership2.setRewardPoints(200);
        membership2.setType(MembershipType.AGENCY);
        artist2.setMembership(membership2);
        
        // Test Case 3: Artist A003 with valid agency membership
        artist3 = new Artist();
        artist3.setId("A003");
        artist3.setName("Catherine");
        artist3.setPhoneNumber("135792468");
        artist3.setEmail("catherine@example.com");
        artist3.setAddress("789 Art Blvd");
        artist3.setGender("Female");
        
        Membership membership3 = new Membership();
        membership3.setId("M003");
        membership3.setStartDate(LocalDate.parse("2023-01-01"));
        membership3.setEndDate(LocalDate.parse("2024-01-01"));
        membership3.setRewardPoints(300);
        membership3.setType(MembershipType.AGENCY);
        artist3.setMembership(membership3);
        
        // Test Case 4: Artist A004 with multiple memberships (using first valid one)
        artist4 = new Artist();
        artist4.setId("A004");
        artist4.setName("David");
        artist4.setPhoneNumber("246813579");
        artist4.setEmail("david@example.com");
        artist4.setAddress("321 Art Rd");
        artist4.setGender("Male");
        
        Membership membership4 = new Membership();
        membership4.setId("M004");
        membership4.setStartDate(LocalDate.parse("2023-01-05"));
        membership4.setEndDate(LocalDate.parse("2023-06-01"));
        membership4.setRewardPoints(150);
        membership4.setType(MembershipType.INDIVIDUAL);
        artist4.setMembership(membership4);
        
        // Test Case 5: Artist A005 with expired membership
        artist5 = new Artist();
        artist5.setId("A005");
        artist5.setName("Eva");
        artist5.setPhoneNumber("864209753");
        artist5.setEmail("eva@example.com");
        artist5.setAddress("654 Art Pl");
        artist5.setGender("Female");
        
        Membership membership5 = new Membership();
        membership5.setId("M006");
        membership5.setStartDate(LocalDate.parse("2022-01-01"));
        membership5.setEndDate(LocalDate.parse("2023-01-01"));
        membership5.setRewardPoints(250);
        membership5.setType(MembershipType.AGENCY);
        artist5.setMembership(membership5);
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() {
        // Test Case 1: "Valid Individual Membership Reward Points Calculation"
        // Input: Get reward points for artist with ID A001 from 2023-01-01 to 2023-12-31
        // Expected Output: Reward points = 100
        
        LocalDate startDate = LocalDate.parse("2023-01-01");
        LocalDate endDate = LocalDate.parse("2023-12-31");
        
        int result = artist1.getRewardPointsWithinPeriod(startDate, endDate);
        
        assertEquals("Valid individual membership should return 100 reward points", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // Test Case 2: "Invalid Membership Period Calculation"
        // Input: Get reward points for artist with ID A002 from 2023-06-01 to 2023-07-01
        // Expected Output: Reward points = 0 (membership invalid during this period)
        
        LocalDate startDate = LocalDate.parse("2023-06-01");
        LocalDate endDate = LocalDate.parse("2023-07-01");
        
        int result = artist2.getRewardPointsWithinPeriod(startDate, endDate);
        
        assertEquals("Expired membership should return -1", -1, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // Test Case 3: "Agency Membership Reward Points Calculation"
        // Input: Get reward points for artist with ID A003 from 2023-05-01 to 2023-10-01
        // Expected Output: Reward points = 300
        
        LocalDate startDate = LocalDate.parse("2023-05-01");
        LocalDate endDate = LocalDate.parse("2023-10-01");
        
        int result = artist3.getRewardPointsWithinPeriod(startDate, endDate);
        
        assertEquals("Valid agency membership should return 300 reward points", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() {
        // Test Case 4: "Multiple Memberships Consideration"
        // Input: Get reward points for artist with ID A004 from 2023-01-06 to 2023-01-21
        // Expected Output: Reward points = 150
        
        LocalDate startDate = LocalDate.parse("2023-01-06");
        LocalDate endDate = LocalDate.parse("2023-01-21");
        
        int result = artist4.getRewardPointsWithinPeriod(startDate, endDate);
        
        assertEquals("First valid membership should return 150 reward points", 150, result);
    }
    
    @Test
    public void testCase5_BoundaryConditionMembershipExpiration() {
        // Test Case 5: "Boundary Condition - Membership Expiration"
        // Input: Get reward points for artist with ID A005 on the 2023-01-02
        // Expected Output: Reward points = 0 (membership expired on the end date)
        
        LocalDate startDate = LocalDate.parse("2023-01-02");
        LocalDate endDate = LocalDate.parse("2023-01-02");
        
        int result = artist5.getRewardPointsWithinPeriod(startDate, endDate);
        
        assertEquals("Expired membership on boundary date should return -1", -1, result);
    }
}