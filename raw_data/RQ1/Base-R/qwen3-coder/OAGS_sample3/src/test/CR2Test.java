import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR2Test {
    
    private Artist artist;
    private Membership membership;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() {
        // SetUp: Create artist A001 with individual membership
        artist = new Artist();
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender("Female");
        
        membership = new Membership();
        membership.setId("M001");
        membership.setStartDate(LocalDate.parse("2022-01-01", formatter));
        membership.setEndDate(LocalDate.parse("2024-01-01", formatter));
        membership.setRewardPoints(100);
        membership.setMembershipType("individual");
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-01-01 to 2023-12-31
        LocalDate startDate = LocalDate.parse("2023-01-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-12-31", formatter);
        int result = artist.getRewardPointsInPeriod(startDate, endDate);
        
        // Expected Output: Reward points = 100
        assertEquals("Valid individual membership should return 100 reward points", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // SetUp: Create artist A002 with expired agency membership
        artist = new Artist();
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender("Male");
        
        membership = new Membership();
        membership.setId("M002");
        membership.setStartDate(LocalDate.parse("2022-03-01", formatter));
        membership.setEndDate(LocalDate.parse("2022-09-01", formatter));
        membership.setRewardPoints(200);
        membership.setMembershipType("agency");
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-06-01 to 2023-07-01
        LocalDate startDate = LocalDate.parse("2023-06-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-07-01", formatter);
        int result = artist.getRewardPointsInPeriod(startDate, endDate);
        
        // Expected Output: Reward points = 0
        assertEquals("Expired membership should return 0 reward points", 0, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // SetUp: Create artist A003 with valid agency membership
        artist = new Artist();
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender("Female");
        
        membership = new Membership();
        membership.setId("M003");
        membership.setStartDate(LocalDate.parse("2023-01-01", formatter));
        membership.setEndDate(LocalDate.parse("2024-01-01", formatter));
        membership.setRewardPoints(300);
        membership.setMembershipType("agency");
        
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-05-01 to 2023-10-01
        LocalDate startDate = LocalDate.parse("2023-05-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-10-01", formatter);
        int result = artist.getRewardPointsInPeriod(startDate, endDate);
        
        // Expected Output: Reward points = 300
        assertEquals("Valid agency membership should return 300 reward points", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() {
        // SetUp: Create artist A004 with individual membership
        artist = new Artist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender("Male");
        
        // Assign first membership
        Membership firstMembership = new Membership();
        firstMembership.setId("M004");
        firstMembership.setStartDate(LocalDate.parse("2023-01-05", formatter));
        firstMembership.setEndDate(LocalDate.parse("2023-06-01", formatter));
        firstMembership.setRewardPoints(150);
        firstMembership.setMembershipType("individual");
        
        artist.setMembership(firstMembership);
        
        // Input: Get reward points from 2023-01-06 to 2023-01-21 (using first membership)
        LocalDate startDate = LocalDate.parse("2023-01-06", formatter);
        LocalDate endDate = LocalDate.parse("2023-01-21", formatter);
        int result = artist.getRewardPointsInPeriod(startDate, endDate);
        
        // Expected Output: Reward points = 150 (from first membership)
        assertEquals("Should return reward points from first valid membership", 150, result);
        
        // Update to second membership
        Membership secondMembership = new Membership();
        secondMembership.setId("M005");
        secondMembership.setStartDate(LocalDate.parse("2023-02-01", formatter));
        secondMembership.setEndDate(LocalDate.parse("2024-02-01", formatter));
        secondMembership.setRewardPoints(100);
        secondMembership.setMembershipType("agency affiliate");
        
        artist.setMembership(secondMembership);
        
        // Input: Get reward points from 2023-01-06 to 2023-01-21 (now using second membership)
        result = artist.getRewardPointsInPeriod(startDate, endDate);
        
        // Expected Output: Reward points = 0 (second membership not valid during this period)
        assertEquals("Second membership not valid during period should return 0", 0, result);
    }
    
    @Test
    public void testCase5_BoundaryConditionMembershipExpiration() {
        // SetUp: Create artist A005 with expired agency membership
        artist = new Artist();
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender("Female");
        
        membership = new Membership();
        membership.setId("M006");
        membership.setStartDate(LocalDate.parse("2022-01-01", formatter));
        membership.setEndDate(LocalDate.parse("2023-01-01", formatter));
        membership.setRewardPoints(250);
        membership.setMembershipType("agency");
        
        artist.setMembership(membership);
        
        // Input: Get reward points for single day 2023-01-02
        LocalDate startDate = LocalDate.parse("2023-01-02", formatter);
        LocalDate endDate = LocalDate.parse("2023-01-02", formatter);
        int result = artist.getRewardPointsInPeriod(startDate, endDate);
        
        // Expected Output: Reward points = 0 (membership expired on 2023-01-01)
        assertEquals("Membership expired on end date should return 0 reward points", 0, result);
    }
}