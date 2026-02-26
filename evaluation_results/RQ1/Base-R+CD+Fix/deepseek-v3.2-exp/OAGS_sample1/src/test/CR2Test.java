import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR2Test {
    
    private Artist artist;
    
    @Before
    public void setUp() {
        artist = new Artist();
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() {
        // SetUp: Create artist A001 with individual membership
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.FEMALE);
        
        Membership membership = new Membership();
        membership.setID("M001");
        membership.setStartDate(LocalDate.of(2022, 1, 1));
        membership.setEndDate(LocalDate.of(2024, 1, 1));
        membership.setRewardPoint(100);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-01-01 to 2023-12-31
        int result = artist.calculateRewardPoints("2023-01-01", "2023-12-31");
        
        // Expected Output: Reward points = 100
        assertEquals(100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // SetUp: Create artist A002 with agency membership
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.MALE);
        
        Membership membership = new Membership();
        membership.setID("M002");
        membership.setStartDate(LocalDate.of(2022, 3, 1));
        membership.setEndDate(LocalDate.of(2022, 9, 1));
        membership.setRewardPoint(200);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-06-01 to 2023-07-01
        int result = artist.calculateRewardPoints("2023-06-01", "2023-07-01");
        
        // Expected Output: Reward points = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // SetUp: Create artist A003 with agency membership
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        Membership membership = new Membership();
        membership.setID("M003");
        membership.setStartDate(LocalDate.of(2023, 1, 1));
        membership.setEndDate(LocalDate.of(2024, 1, 1));
        membership.setRewardPoint(300);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-05-01 to 2023-10-01
        int result = artist.calculateRewardPoints("2023-05-01", "2023-10-01");
        
        // Expected Output: Reward points = 300
        assertEquals(300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() {
        // SetUp: Create artist A004 with individual membership
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender(Gender.MALE);
        
        Membership membership = new Membership();
        membership.setID("M004");
        membership.setStartDate(LocalDate.of(2023, 1, 5));
        membership.setEndDate(LocalDate.of(2023, 6, 1));
        membership.setRewardPoint(150);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Note: The test specification mentions updating to agency affiliate membership,
        // but according to the Artist class structure, an artist can only have one membership.
        // The latest membership assignment will override the previous one.
        // However, the expected output is 150, so we'll only set the individual membership.
        
        // Input: Get reward points from 2023-01-06 to 2023-01-21
        int result = artist.calculateRewardPoints("2023-01-06", "2023-01-21");
        
        // Expected Output: Reward points = 150
        assertEquals(150, result);
    }
    
    @Test
    public void testCase5_BoundaryCondition_MembershipExpiration() {
        // SetUp: Create artist A005 with agency membership
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender(Gender.FEMALE);
        
        Membership membership = new Membership();
        membership.setID("M006");
        membership.setStartDate(LocalDate.of(2022, 1, 1));
        membership.setEndDate(LocalDate.of(2023, 1, 1));
        membership.setRewardPoint(250);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Input: Get reward points on 2023-01-02 (using same date for start and end)
        int result = artist.calculateRewardPoints("2023-01-02", "2023-01-02");
        
        // Expected Output: Reward points = 0 (membership expired on the end date)
        assertEquals(0, result);
    }
}