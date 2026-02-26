import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an artist in the online art gallery system.
 */
class Artist {
    private String name;
    private String phoneNumber;
    private String id;
    private String email;
    private String address;
    private String gender;
    private List<Artwork> artworks;
    private Membership membership;

    public Artist() {
        this.artworks = new ArrayList<>();
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

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    /**
     * Calculates the total price of all artworks belonging to this artist.
     *
     * @return the sum of prices of all artworks
     */
    public double calculateTotalArtworkPrice() {
        double total = 0.0;
        for (Artwork artwork : artworks) {
            total += artwork.getPrice();
        }
        return total;
    }

    /**
     * Counts the number of artworks in each category for this artist.
     *
     * @return a map where keys are category names and values are counts
     */
    public Map<String, Integer> countArtworksByCategory() {
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Artwork artwork : artworks) {
            String category = artwork.getCategory();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }
        return categoryCount;
    }
}

/**
 * Represents an artwork in the online art gallery system.
 */
class Artwork {
    private String title;
    private String description;
    private String category;
    private double price;

    public Artwork() {
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

/**
 * Represents a membership for an artist in the online art gallery system.
 */
class Membership {
    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int rewardPoints;
    private MembershipType type;

    public Membership() {
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

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
     * Checks if the membership is valid during the specified time period.
     *
     * @param startDate the start date of the period to check
     * @param endDate the end date of the period to check
     * @return true if the membership is valid during the entire period, false otherwise
     */
    public boolean isValidDuringPeriod(LocalDate startDate, LocalDate endDate) {
        return !startDate.isAfter(this.endDate) && !endDate.isBefore(this.startDate);
    }

    /**
     * Gets the reward points if the membership is valid during the specified time period.
     *
     * @param artist the artist whose membership is being checked
     * @param periodStart the start date of the period (inclusive)
     * @param periodEnd the end date of the period (inclusive)
     * @return the reward points if membership is valid, 0 otherwise
     * @throws IllegalArgumentException if the artist has no membership
     */
    public static int getRewardPointsInPeriod(Artist artist, LocalDate periodStart, LocalDate periodEnd) {
        if (artist.getMembership() == null) {
            throw new IllegalArgumentException("Artist has no membership");
        }
        
        Membership membership = artist.getMembership();
        if (membership.isValidDuringPeriod(periodStart, periodEnd)) {
            return membership.getRewardPoints();
        }
        return 0;
    }
}

/**
 * Enum representing the different types of memberships available.
 */
enum MembershipType {
    INDIVIDUAL,
    AGENCY,
    AGENCY_AFFILIATE
}