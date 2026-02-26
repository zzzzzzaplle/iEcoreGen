import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

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
     * Calculates the total price of all artworks by this artist.
     * 
     * @return the sum of prices of all artworks belonging to this artist
     */
    public double calculateTotalArtworkPrice() {
        double total = 0.0;
        for (Artwork artwork : artworks) {
            total += artwork.getPrice();
        }
        return total;
    }

    /**
     * Counts the number of artworks by this artist in each category.
     * 
     * @return a map where keys are artwork categories and values are counts
     */
    public Map<String, Integer> countArtworksByCategory() {
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Artwork artwork : artworks) {
            String category = artwork.getCategory();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }
        return categoryCount;
    }

    /**
     * Gets the reward points for this artist's membership if valid during the specified period.
     * 
     * @param startDate the start date of the period (inclusive) in "yyyy-MM-dd" format
     * @param endDate the end date of the period (inclusive) in "yyyy-MM-dd" format
     * @return the reward points if membership is valid during the period, 0 otherwise
     * @throws IllegalArgumentException if the date format is invalid
     */
    public int getRewardPoints(String startDate, String endDate) {
        if (membership == null) {
            return 0;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate periodStart = LocalDate.parse(startDate, formatter);
            LocalDate periodEnd = LocalDate.parse(endDate, formatter);
            
            if (membership.isValidDuring(periodStart, periodEnd)) {
                return membership.getRewardPoints();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd");
        }
        
        return 0;
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
     * Checks if the membership is valid during the specified period.
     * 
     * @param periodStart the start date of the period to check (inclusive)
     * @param periodEnd the end date of the period to check (inclusive)
     * @return true if the membership is valid during the entire period, false otherwise
     */
    public boolean isValidDuring(LocalDate periodStart, LocalDate periodEnd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate membershipStart = LocalDate.parse(startDate, formatter);
        LocalDate membershipEnd = LocalDate.parse(endDate, formatter);
        
        return !periodStart.isAfter(membershipEnd) && !periodEnd.isBefore(membershipStart);
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