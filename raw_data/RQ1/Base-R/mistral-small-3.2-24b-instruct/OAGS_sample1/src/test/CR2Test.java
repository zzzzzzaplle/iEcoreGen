import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum MembershipType {
    INDIVIDUAL,
    AGENCY,
    AGENCY_AFFILIATE
}

class Artist {
    private String name;
    private String phoneNumber;
    private String id;
    private String email;
    private String address;
    private String gender;
    private Membership membership;
    private List<Artwork> artworks;

    public Artist() {
        this.artworks = new ArrayList<>();
    }

    public Artist(String name, String phoneNumber, String id, String email, String address, String gender) {
        this();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.email = email;
        this.address = address;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    /**
     * Adds an artwork to the artist's list of artworks.
     * @param artwork The artwork to be added.
     */
    public void addArtwork(Artwork artwork) {
        this.artworks.add(artwork);
    }

    /**
     * Calculates the total price of the artist's artworks.
     * @return The total price of all artworks.
     */
    public double calculateTotalArtworkPrice() {
        return artworks.stream().mapToDouble(Artwork::getPrice).sum();
    }

    /**
     * Counts the number of artworks by category for the artist.
     * @return A map with categories as keys and counts as values.
     */
    public Map<String, Integer> countArtworksByCategory() {
        Map<String, Integer> categoryCounts = new HashMap<>();
        for (Artwork artwork : artworks) {
            String category = artwork.getCategory();
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
        }
        return categoryCounts;
    }
}

class Membership {
    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int rewardPoints;
    private MembershipType type;

    public Membership() {
    }

    public Membership(String id, String startDate, String endDate, int rewardPoints, MembershipType type) {
        this();
        this.id = id;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = LocalDate.parse(startDate, formatter);
        this.endDate = LocalDate.parse(endDate, formatter);
        this.rewardPoints = rewardPoints;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = LocalDate.parse(startDate, formatter);
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.endDate = LocalDate.parse(endDate, formatter);
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public MembershipType getType() {
        return type;
    }

    public void setType(MembershipType type) {
        this.type = type;
    }

    /**
     * Checks if the membership is valid within the specified period.
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     * @return true if the membership is valid within the period, false otherwise.
     */
    public boolean isValidWithinPeriod(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate periodStart = LocalDate.parse(startDate, formatter);
        LocalDate periodEnd = LocalDate.parse(endDate, formatter);
        return !this.startDate.isAfter(periodEnd) && !this.endDate.isBefore(periodStart);
    }

    /**
     * Gets the reward points of the membership if it is valid within the specified period.
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     * @return The reward points if the membership is valid, -1 otherwise.
     */
    public int getRewardPointsWithinPeriod(String startDate, String endDate) {
        if (isValidWithinPeriod(startDate, endDate)) {
            return this.rewardPoints;
        }
        return -1;
    }
}

class Artwork {
    private String title;
    private String description;
    private String category;
    private double price;

    public Artwork() {
    }

    public Artwork(String title, String description, String category, double price) {
        this();
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

public class CR2Test {

    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() {
        // SetUp: Create artist A001 with individual membership
        Artist artist = new Artist("Alice", "123456789", "A001", "alice@example.com", "123 Art St", "Female");
        Membership membership = new Membership("M001", "2022-01-01", "2024-01-01", 100, MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-01-01 to 2023-12-31
        int result = artist.getMembership().getRewardPointsWithinPeriod("2023-01-01", "2023-12-31");
        
        // Expected Output: Reward points = 100
        assertEquals("Valid individual membership should return 100 reward points", 100, result);
    }

    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() {
        // SetUp: Create artist A002 with expired agency membership
        Artist artist = new Artist("Bob", "987654321", "A002", "bob@example.com", "456 Art Ave", "Male");
        Membership membership = new Membership("M002", "2022-03-01", "2022-09-01", 200, MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-06-01 to 2023-07-01
        int result = artist.getMembership().getRewardPointsWithinPeriod("2023-06-01", "2023-07-01");
        
        // Expected Output: Reward points = 0 (membership invalid during this period)
        assertEquals("Expired membership should return -1 (indicating invalid)", -1, result);
    }

    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() {
        // SetUp: Create artist A003 with valid agency membership
        Artist artist = new Artist("Catherine", "135792468", "A003", "catherine@example.com", "789 Art Blvd", "Female");
        Membership membership = new Membership("M003", "2023-01-01", "2024-01-01", 300, MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Input: Get reward points from 2023-05-01 to 2023-10-01
        int result = artist.getMembership().getRewardPointsWithinPeriod("2023-05-01", "2023-10-01");
        
        // Expected Output: Reward points = 300
        assertEquals("Valid agency membership should return 300 reward points", 300, result);
    }

    @Test
    public void testCase4_MultipleMembershipsConsideration() {
        // SetUp: Create artist A004 with individual membership
        Artist artist = new Artist("David", "246813579", "A004", "david@example.com", "321 Art Rd", "Male");
        Membership membership1 = new Membership("M004", "2023-01-05", "2023-06-01", 150, MembershipType.INDIVIDUAL);
        artist.setMembership(membership1);
        
        // Input: Get reward points from 2023-01-06 to 2023-01-21 (using first membership)
        int result = artist.getMembership().getRewardPointsWithinPeriod("2023-01-06", "2023-01-21");
        
        // Expected Output: Reward points = 150 (from first membership)
        assertEquals("First valid membership should return 150 reward points", 150, result);
        
        // Update to assign agency affiliate membership (second membership)
        Membership membership2 = new Membership("M005", "2023-02-01", "2024-02-01", 100, MembershipType.AGENCY_AFFILIATE);
        artist.setMembership(membership2);
        
        // Get reward points for same period with second membership (should be invalid)
        int result2 = artist.getMembership().getRewardPointsWithinPeriod("2023-01-06", "2023-01-21");
        
        // Expected Output: Reward points = -1 (second membership not valid in this period)
        assertEquals("Second membership should return -1 for this period", -1, result2);
    }

    @Test
    public void testCase5_BoundaryConditionMembershipExpiration() {
        // SetUp: Create artist A005 with membership expiring on 2023-01-01
        Artist artist = new Artist("Eva", "864209753", "A005", "eva@example.com", "654 Art Pl", "Female");
        Membership membership = new Membership("M006", "2022-01-01", "2023-01-01", 250, MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Input: Get reward points for single day 2023-01-02 (day after expiration)
        int result = artist.getMembership().getRewardPointsWithinPeriod("2023-01-02", "2023-01-02");
        
        // Expected Output: Reward points = 0 (membership expired on the end date)
        assertEquals("Expired membership should return -1 on day after expiration", -1, result);
    }
}