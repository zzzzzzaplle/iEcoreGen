import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
     * @param artwork The artwork to add
     */
    public void addArtwork(Artwork artwork) {
        this.artworks.add(artwork);
    }

    /**
     * Calculates the total price of all artworks owned by this artist.
     * 
     * @return The sum of prices of all artworks
     */
    public double calculateTotalArtworkPrice() {
        double total = 0.0;
        for (Artwork artwork : artworks) {
            total += artwork.getPrice();
        }
        return total;
    }

    /**
     * Gets reward points if the artist's membership is valid during the specified period.
     * 
     * @param startDateStr Start date in "yyyy-MM-dd" format
     * @param endDateStr End date in "yyyy-MM-dd" format
     * @return Reward points if membership is valid during the period, otherwise 0
     * @throws ParseException if date strings are not in the correct format
     */
    public int getRewardPointsWithinPeriod(String startDateStr, String endDateStr) throws ParseException {
        if (membership == null) {
            return 0;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date requestedStartDate = formatter.parse(startDateStr);
        Date requestedEndDate = formatter.parse(endDateStr);
        
        Date membershipStartDate = formatter.parse(membership.getStartDate());
        Date membershipEndDate = formatter.parse(membership.getEndDate());

        // Check if the requested period overlaps with membership period
        if (requestedStartDate.compareTo(membershipEndDate) <= 0 && requestedEndDate.compareTo(membershipStartDate) >= 0) {
            return membership.getRewardPoints();
        }
        
        return 0;
    }

    /**
     * Counts the artist's artworks by category.
     * 
     * @return A map with category as key and count as value
     */
    public Map<String, Integer> countArtworksByCategory() {
        Map<String, Integer> categoryCount = new HashMap<>();
        categoryCount.put("painting", 0);
        categoryCount.put("sculpture", 0);
        categoryCount.put("architecture", 0);

        for (Artwork artwork : artworks) {
            String category = artwork.getCategory().toLowerCase();
            if (categoryCount.containsKey(category)) {
                categoryCount.put(category, categoryCount.get(category) + 1);
            }
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