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
     * Default constructor for Artist.
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
     * Calculates the total price of all artworks owned by this artist.
     *
     * @return the sum of prices of all artworks
     */
    public double calculateTotalArtworkPrice() {
        return artworks.stream().mapToDouble(Artwork::getPrice).sum();
    }

    /**
     * Gets the reward points for this artist's membership if it is valid within the specified period.
     *
     * @param startDate the start date of the period (inclusive)
     * @param endDate the end date of the period (inclusive)
     * @return the reward points if membership is valid during the period, otherwise 0
     */
    public int getRewardPointsInPeriod(String startDate, String endDate) {
        if (membership == null) {
            return 0;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        LocalDate membershipStart = LocalDate.parse(membership.getStartDate(), formatter);
        LocalDate membershipEnd = LocalDate.parse(membership.getEndDate(), formatter);

        // Check if the membership is valid during the given period
        if (!start.isAfter(membershipEnd) && !end.isBefore(membershipStart)) {
            return membership.getRewardPoints();
        }

        return 0;
    }

    /**
     * Counts the number of artworks in each category for this artist.
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
    private String startDate;
    private String endDate;
    private int rewardPoints;
    private String type; // individual, agency, agency affiliate

    /**
     * Default constructor for Membership.
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
     * Default constructor for Artwork.
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