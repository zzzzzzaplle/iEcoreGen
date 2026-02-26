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

    /**
     * Default constructor for Artist.
     */
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
     * @return the sum of prices of all artworks, returns 0.0 if no artworks exist
     */
    public double calculateTotalPriceOfArtworks() {
        double total = 0.0;
        for (Artwork artwork : artworks) {
            total += artwork.getPrice();
        }
        return total;
    }

    /**
     * Counts the number of artworks in each category for this artist.
     * 
     * @return a map where keys are category names and values are counts of artworks in that category
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

    /**
     * Default constructor for Artwork.
     */
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
    private String startDate;
    private String endDate;
    private int rewardPoints;
    private MembershipType type;

    /**
     * Default constructor for Membership.
     */
    public Membership() {
    }

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

    public MembershipType getType() {
        return type;
    }

    public void setType(MembershipType type) {
        this.type = type;
    }

    /**
     * Checks if the membership is valid during a specific time period.
     * 
     * @param periodStart the start date of the period in "yyyy-MM-dd" format
     * @param periodEnd the end date of the period in "yyyy-MM-dd" format
     * @return true if the membership is valid during the period (inclusive of boundaries), false otherwise
     * @throws IllegalArgumentException if the date format is invalid
     */
    public boolean isValidDuringPeriod(String periodStart, String periodEnd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate membershipStart = LocalDate.parse(startDate, formatter);
            LocalDate membershipEnd = LocalDate.parse(endDate, formatter);
            LocalDate checkStart = LocalDate.parse(periodStart, formatter);
            LocalDate checkEnd = LocalDate.parse(periodEnd, formatter);
            
            // Check if the membership period overlaps with the given period
            return !membershipStart.isAfter(checkEnd) && !membershipEnd.isBefore(checkStart);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd");
        }
    }

    /**
     * Retrieves reward points if the membership is valid during the specified period.
     * 
     * @param periodStart the start date of the period in "yyyy-MM-dd" format
     * @param periodEnd the end date of the period in "yyyy-MM-dd" format
     * @return the reward points if membership is valid during the period
     * @throws IllegalArgumentException if the date format is invalid
     * @throws IllegalStateException if the membership is not valid during the specified period
     */
    public int getRewardPointsInPeriod(String periodStart, String periodEnd) {
        if (!isValidDuringPeriod(periodStart, periodEnd)) {
            throw new IllegalStateException("Membership is not valid during the specified period");
        }
        return rewardPoints;
    }
}

/**
 * Represents the type of membership an artist can have.
 */
enum MembershipType {
    INDIVIDUAL,
    AGENCY,
    AGENCY_AFFILIATE
}