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
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() {
        // Create artist with ID A001
        artist = new Artist();
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender("Female");
        
        // Assign individual membership to artist
        Membership membership = new Membership();
        membership.setId("M001");
        membership.setStartDate(LocalDate.parse("2022-01-01", formatter));
        membership.setEndDate(LocalDate.parse("2024-01-01", formatter));
        membership.setRewardPoints(100);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Test period: 2023-01-01 to 2023-12-31
        LocalDate startDate = LocalDate.parse("2023-01-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-12-31", formatter);
        
        // Expected output: Reward points = 100
        int result = OnlineArtGallerySystem.getRewardPointsWithinPeriod(artist, startDate, endDate);
        assertEquals("Reward points should be 100 for valid individual membership", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // Create artist with ID A002
        artist = new Artist();
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender("Male");
        
        // Assign agency membership to artist
        Membership membership = new Membership();
        membership.setId("M002");
        membership.setStartDate(LocalDate.parse("2022-03-01", formatter));
        membership.setEndDate(LocalDate.parse("2022-09-01", formatter));
        membership.setRewardPoints(200);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test period: 2023-06-01 to 2023-07-01
        LocalDate startDate = LocalDate.parse("2023-06-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-07-01", formatter);
        
        // Expected output: Reward points = -1 (membership invalid during this period)
        int result = OnlineArtGallerySystem.getRewardPointsWithinPeriod(artist, startDate, endDate);
        assertEquals("Reward points should be -1 for invalid membership period", -1, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // Create artist with ID A003
        artist = new Artist();
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender("Female");
        
        // Assign agency membership to artist
        Membership membership = new Membership();
        membership.setId("M003");
        membership.setStartDate(LocalDate.parse("2023-01-01", formatter));
        membership.setEndDate(LocalDate.parse("2024-01-01", formatter));
        membership.setRewardPoints(300);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test period: 2023-05-01 to 2023-10-01
        LocalDate startDate = LocalDate.parse("2023-05-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-10-01", formatter);
        
        // Expected output: Reward points = 300
        int result = OnlineArtGallerySystem.getRewardPointsWithinPeriod(artist, startDate, endDate);
        assertEquals("Reward points should be 300 for valid agency membership", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() {
        // Create artist with ID A004
        artist = new Artist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender("Male");
        
        // Assign first membership (individual) to artist
        Membership firstMembership = new Membership();
        firstMembership.setId("M004");
        firstMembership.setStartDate(LocalDate.parse("2023-01-05", formatter));
        firstMembership.setEndDate(LocalDate.parse("2023-06-01", formatter));
        firstMembership.setRewardPoints(150);
        firstMembership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(firstMembership);
        
        // Update to second membership (agency affiliate)
        Membership secondMembership = new Membership();
        secondMembership.setId("M005");
        secondMembership.setStartDate(LocalDate.parse("2023-02-01", formatter));
        secondMembership.setEndDate(LocalDate.parse("2024-02-01", formatter));
        secondMembership.setRewardPoints(100);
        secondMembership.setType(MembershipType.AGENCY_AFFILIATE);
        artist.setMembership(secondMembership);
        
        // Test period: 2023-01-06 to 2023-01-21
        LocalDate startDate = LocalDate.parse("2023-01-06", formatter);
        LocalDate endDate = LocalDate.parse("2023-01-21", formatter);
        
        // Expected output: Reward points = 150 (from first membership)
        int result = OnlineArtGallerySystem.getRewardPointsWithinPeriod(artist, startDate, endDate);
        assertEquals("Reward points should be 150 from the first membership", 150, result);
    }
    
    @Test
    public void testCase5_BoundaryCondition_MembershipExpiration() {
        // Create artist with ID A005
        artist = new Artist();
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender("Female");
        
        // Assign agency membership to artist
        Membership membership = new Membership();
        membership.setId("M006");
        membership.setStartDate(LocalDate.parse("2022-01-01", formatter));
        membership.setEndDate(LocalDate.parse("2023-01-01", formatter));
        membership.setRewardPoints(250);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test period: 2023-01-02 (single day)
        LocalDate startDate = LocalDate.parse("2023-01-02", formatter);
        LocalDate endDate = LocalDate.parse("2023-01-02", formatter);
        
        // Expected output: Reward points = -1 (membership expired on the end date)
        int result = OnlineArtGallerySystem.getRewardPointsWithinPeriod(artist, startDate, endDate);
        assertEquals("Reward points should be -1 for expired membership", -1, result);
    }
}