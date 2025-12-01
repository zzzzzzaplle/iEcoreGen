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
    private Membership membership;
    private List<Artwork> artworks;

    /**
     * Creates a new Artist with default values.
     */
    public Artist() {
        this.artworks = new ArrayList<>();
    }

    // Getters and setters
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
     * Adds an artwork to the artist's collection.
     *
     * @param artwork the artwork to add
     */
    public void addArtwork(Artwork artwork) {
        this.artworks.add(artwork);
    }

    /**
     * Calculates the total price of all artworks owned by this artist.
     *
     * @return the sum of prices of all artworks
     */
    public double calculateTotalArtworkPrice() {
        return artworks.stream().mapToDouble(Artwork::getPrice).sum();
    }

    /**
     * Gets the reward points for this artist's membership within a specific period.
     * Verifies that the membership is valid during the given time interval.
     *
     * @param startDate the start date of the period (inclusive)
     * @param endDate the end date of the period (inclusive)
     * @return the reward points if membership is valid during the period, 0 otherwise
     */
    public int getRewardPointsInPeriod(LocalDate startDate, LocalDate endDate) {
        if (membership == null) {
            return 0;
        }

        LocalDate membershipStart = membership.getStartDate();
        LocalDate membershipEnd = membership.getEndDate();

        // Check if the given period overlaps with the membership period
        if (!startDate.isAfter(membershipEnd) && !endDate.isBefore(membershipStart)) {
            return membership.getRewardPoints();
        }

        return 0;
    }

    /**
     * Counts the number of artworks by category for this artist.
     *
     * @return a map containing category names as keys and their counts as values
     */
    public Map<String, Integer> countArtworksByCategory() {
        Map<String, Integer> categoryCount = new HashMap<>();
        categoryCount.put("painting", 0);
        categoryCount.put("sculpture", 0);
        categoryCount.put("architecture", 0);

        for (Artwork artwork : artworks) {
            String category = artwork.getCategory();
            categoryCount.put(category, categoryCount.get(category) + 1);
        }

        return categoryCount;
    }
}

/**
 * Represents a membership in the online art gallery system.
 */
class Membership {
    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int rewardPoints;
    private String membershipType; // individual, agency, agency affiliate

    /**
     * Creates a new Membership with default values.
     */
    public Membership() {
    }

    // Getters and setters
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = LocalDate.parse(startDate, formatter);
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
}

/**
 * Represents an artwork in the online art gallery system.
 */
class Artwork {
    private String title;
    private String description;
    private String category; // painting, sculpture, architecture
    private double price;

    /**
     * Creates a new Artwork with default values.
     */
    public Artwork() {
    }

    // Getters and setters
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