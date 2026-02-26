import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private OnlineArtGallerySystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new OnlineArtGallerySystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() {
        // SetUp: Create artist A001 with individual membership
        Artist artist = new Artist();
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
        
        List<Artist> artists = new ArrayList<>();
        artists.add(artist);
        system.setArtists(artists);
        
        // Input: Get reward points for artist A001 from 2023-01-01 to 2023-12-31
        LocalDate startDate = LocalDate.parse("2023-01-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-12-31", formatter);
        int result = system.getRewardPoints("A001", startDate, endDate);
        
        // Expected Output: Reward points = 100
        assertEquals("Reward points should be 100 for valid individual membership", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // SetUp: Create artist A002 with agency membership that expired before the period
        Artist artist = new Artist();
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
        
        List<Artist> artists = new ArrayList<>();
        artists.add(artist);
        system.setArtists(artists);
        
        // Input: Get reward points for artist A002 from 2023-06-01 to 2023-07-01
        LocalDate startDate = LocalDate.parse("2023-06-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-07-01", formatter);
        int result = system.getRewardPoints("A002", startDate, endDate);
        
        // Expected Output: Reward points = -1 (membership invalid during this period)
        assertEquals("Reward points should be -1 for invalid membership period", -1, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // SetUp: Create artist A003 with valid agency membership
        Artist artist = new Artist();
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
        
        List<Artist> artists = new ArrayList<>();
        artists.add(artist);
        system.setArtists(artists);
        
        // Input: Get reward points for artist A003 from 2023-05-01 to 2023-10-01
        LocalDate startDate = LocalDate.parse("2023-05-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-10-01", formatter);
        int result = system.getRewardPoints("A003", startDate, endDate);
        
        // Expected Output: Reward points = 300
        assertEquals("Reward points should be 300 for valid agency membership", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() {
        // SetUp: Create artist A004 with first membership that is valid during the period
        Artist artist = new Artist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender("Male");
        
        // First membership (valid during the period)
        Membership membership1 = new Membership();
        membership1.setId("M004");
        membership1.setStartDate(LocalDate.parse("2023-01-05", formatter));
        membership1.setEndDate(LocalDate.parse("2023-06-01", formatter));
        membership1.setRewardPoints(150);
        membership1.setType(MembershipType.INDIVIDUAL);
        
        artist.setMembership(membership1);
        
        List<Artist> artists = new ArrayList<>();
        artists.add(artist);
        system.setArtists(artists);
        
        // Input: Get reward points for artist A004 from 2023-01-06 to 2023-01-21
        LocalDate startDate = LocalDate.parse("2023-01-06", formatter);
        LocalDate endDate = LocalDate.parse("2023-01-21", formatter);
        int result = system.getRewardPoints("A004", startDate, endDate);
        
        // Expected Output: Reward points = 150 (from first membership)
        assertEquals("Reward points should be 150 from the valid membership", 150, result);
        
        // Update to second membership (not valid during the period)
        Membership membership2 = new Membership();
        membership2.setId("M005");
        membership2.setStartDate(LocalDate.parse("2023-02-01", formatter));
        membership2.setEndDate(LocalDate.parse("2024-02-01", formatter));
        membership2.setRewardPoints(100);
        membership2.setType(MembershipType.AGENCY_AFFILIATE);
        
        artist.setMembership(membership2);
        
        // Should still return -1 since second membership is not valid during the period
        result = system.getRewardPoints("A004", startDate, endDate);
        assertEquals("Reward points should be -1 for invalid membership period", -1, result);
    }
    
    @Test
    public void testCase5_BoundaryCondition_MembershipExpiration() {
        // SetUp: Create artist A005 with membership expired on the end date
        Artist artist = new Artist();
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
        
        List<Artist> artists = new ArrayList<>();
        artists.add(artist);
        system.setArtists(artists);
        
        // Input: Get reward points for artist A005 on 2023-01-02
        LocalDate startDate = LocalDate.parse("2023-01-02", formatter);
        LocalDate endDate = LocalDate.parse("2023-01-02", formatter);
        int result = system.getRewardPoints("A005", startDate, endDate);
        
        // Expected Output: Reward points = -1 (membership expired on the end date)
        assertEquals("Reward points should be -1 for expired membership", -1, result);
    }
}