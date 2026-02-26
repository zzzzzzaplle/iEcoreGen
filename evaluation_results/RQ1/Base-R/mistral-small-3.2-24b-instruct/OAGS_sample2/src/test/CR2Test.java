import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR2Test {
    private Artist artist;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        artist = new Artist();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() {
        // SetUp
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender("Female");
        
        Membership membership = new Membership();
        membership.setId("M001");
        membership.setStartDate(LocalDate.parse("2022-01-01", formatter));
        membership.setEndDate(LocalDate.parse("2024-01-01", formatter));
        membership.setRewardPoints(100);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Input
        LocalDate startDate = LocalDate.parse("2023-01-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-12-31", formatter);
        
        // Verify membership validity and get reward points
        boolean isValid = artist.getMembership().isValidWithinPeriod(startDate, endDate);
        int rewardPoints = isValid ? artist.getMembership().getRewardPoints() : 0;
        
        // Expected Output: Reward points = 100
        assertEquals(100, rewardPoints);
    }

    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // SetUp
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender("Male");
        
        Membership membership = new Membership();
        membership.setId("M002");
        membership.setStartDate(LocalDate.parse("2022-03-01", formatter));
        membership.setEndDate(LocalDate.parse("2022-09-01", formatter));
        membership.setRewardPoints(200);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Input
        LocalDate startDate = LocalDate.parse("2023-06-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-07-01", formatter);
        
        // Verify membership validity and get reward points
        boolean isValid = artist.getMembership().isValidWithinPeriod(startDate, endDate);
        int rewardPoints = isValid ? artist.getMembership().getRewardPoints() : 0;
        
        // Expected Output: Reward points = 0 (membership invalid during this period)
        assertEquals(0, rewardPoints);
    }

    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // SetUp
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender("Female");
        
        Membership membership = new Membership();
        membership.setId("M003");
        membership.setStartDate(LocalDate.parse("2023-01-01", formatter));
        membership.setEndDate(LocalDate.parse("2024-01-01", formatter));
        membership.setRewardPoints(300);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Input
        LocalDate startDate = LocalDate.parse("2023-05-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-10-01", formatter);
        
        // Verify membership validity and get reward points
        boolean isValid = artist.getMembership().isValidWithinPeriod(startDate, endDate);
        int rewardPoints = isValid ? artist.getMembership().getRewardPoints() : 0;
        
        // Expected Output: Reward points = 300
        assertEquals(300, rewardPoints);
    }

    @Test
    public void testCase4_MultipleMembershipsConsideration() {
        // SetUp
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender("Male");
        
        // First membership assignment
        Membership membership1 = new Membership();
        membership1.setId("M004");
        membership1.setStartDate(LocalDate.parse("2023-01-05", formatter));
        membership1.setEndDate(LocalDate.parse("2023-06-01", formatter));
        membership1.setRewardPoints(150);
        membership1.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership1);
        
        // Second membership assignment (overwrites the first one)
        Membership membership2 = new Membership();
        membership2.setId("M005");
        membership2.setStartDate(LocalDate.parse("2023-02-01", formatter));
        membership2.setEndDate(LocalDate.parse("2024-02-01", formatter));
        membership2.setRewardPoints(100);
        membership2.setType(MembershipType.AGENCY_AFFILIATE);
        artist.setMembership(membership2);
        
        // Input
        LocalDate startDate = LocalDate.parse("2023-01-06", formatter);
        LocalDate endDate = LocalDate.parse("2023-01-21", formatter);
        
        // Verify membership validity and get reward points (using current membership)
        boolean isValid = artist.getMembership().isValidWithinPeriod(startDate, endDate);
        int rewardPoints = isValid ? artist.getMembership().getRewardPoints() : 0;
        
        // Expected Output: Reward points = 150 (from the first membership)
        // Note: The setup assigns two memberships sequentially, but only the last one remains
        // The test specification expects 150, which suggests we should use the first membership
        // This indicates a potential issue with the test specification or implementation
        // Following the specification exactly: Expected Output: Reward points = 150
        assertEquals(150, rewardPoints);
    }

    @Test
    public void testCase5_BoundaryConditionMembershipExpiration() {
        // SetUp
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender("Female");
        
        Membership membership = new Membership();
        membership.setId("M006");
        membership.setStartDate(LocalDate.parse("2022-01-01", formatter));
        membership.setEndDate(LocalDate.parse("2023-01-01", formatter));
        membership.setRewardPoints(250);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Input - Single day check (start and end date are the same)
        LocalDate startDate = LocalDate.parse("2023-01-02", formatter);
        LocalDate endDate = LocalDate.parse("2023-01-02", formatter);
        
        // Verify membership validity and get reward points
        boolean isValid = artist.getMembership().isValidWithinPeriod(startDate, endDate);
        int rewardPoints = isValid ? artist.getMembership().getRewardPoints() : 0;
        
        // Expected Output: Reward points = 0 (membership expired on the end date)
        assertEquals(0, rewardPoints);
    }
}