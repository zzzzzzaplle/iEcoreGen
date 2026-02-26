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

    /**
     * Gets the name of the artist.
     * @return the artist's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the artist.
     * @param name the artist's name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the phone number of the artist.
     * @return the artist's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the artist.
     * @param phoneNumber the artist's phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the ID of the artist.
     * @return the artist's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the artist.
     * @param id the artist's ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the email of the artist.
     * @return the artist's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the artist.
     * @param email the artist's email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the address of the artist.
     * @return the artist's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the artist.
     * @param address the artist's address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the gender of the artist.
     * @return the artist's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the artist.
     * @param gender the artist's gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the membership of the artist.
     * @return the artist's membership
     */
    public Membership getMembership() {
        return membership;
    }

    /**
     * Sets the membership of the artist.
     * @param membership the artist's membership to set
     */
    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    /**
     * Gets the list of artworks created by the artist.
     * @return the list of artworks
     */
    public List<Artwork> getArtworks() {
        return artworks;
    }

    /**
     * Sets the list of artworks created by the artist.
     * @param artworks the list of artworks to set
     */
    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    /**
     * Adds an artwork to the artist's collection.
     * @param artwork the artwork to add
     */
    public void addArtwork(Artwork artwork) {
        if (artwork != null) {
            this.artworks.add(artwork);
        }
    }

    /**
     * Calculates the total price of all artworks created by the artist.
     * @return the total price of all artworks
     */
    public double calculateTotalPriceOfArtworks() {
        double total = 0.0;
        for (Artwork artwork : artworks) {
            total += artwork.getPrice();
        }
        return total;
    }

    /**
     * Gets the reward points from the artist's membership if it is valid during the specified period.
     * @param startDate the start date of the period in "yyyy-MM-dd" format
     * @param endDate the end date of the period in "yyyy-MM-dd" format
     * @return the reward points if membership is valid, otherwise 0
     * @throws IllegalArgumentException if the date format is invalid
     */
    public int getRewardPointsInPeriod(String startDate, String endDate) {
        if (membership == null) {
            return 0;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate periodStart = LocalDate.parse(startDate, formatter);
            LocalDate periodEnd = LocalDate.parse(endDate, formatter);
            
            LocalDate membershipStart = LocalDate.parse(membership.getStartDate(), formatter);
            LocalDate membershipEnd = LocalDate.parse(membership.getEndDate(), formatter);
            
            // Check if membership is valid during the specified period (inclusive of boundaries)
            if (!membershipStart.isAfter(periodEnd) && !membershipEnd.isBefore(periodStart)) {
                return membership.getRewardPoints();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd");
        }
        
        return 0;
    }

    /**
     * Counts the number of artworks in each category.
     * @return a map with category as key and count as value
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

    /**
     * Gets the membership ID.
     * @return the membership ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the membership ID.
     * @param id the membership ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the start date of the membership.
     * @return the start date in "yyyy-MM-dd" format
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the membership.
     * @param startDate the start date in "yyyy-MM-dd" format
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the membership.
     * @return the end date in "yyyy-MM-dd" format
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the membership.
     * @param endDate the end date in "yyyy-MM-dd" format
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the reward points of the membership.
     * @return the reward points
     */
    public int getRewardPoints() {
        return rewardPoints;
    }

    /**
     * Sets the reward points of the membership.
     * @param rewardPoints the reward points to set
     */
    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    /**
     * Gets the type of membership.
     * @return the membership type
     */
    public MembershipType getType() {
        return type;
    }

    /**
     * Sets the type of membership.
     * @param type the membership type to set
     */
    public void setType(MembershipType type) {
        this.type = type;
    }
}

/**
 * Enum representing the types of membership available.
 */
enum MembershipType {
    INDIVIDUAL,
    AGENCY,
    AGENCY_AFFILIATE
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

    /**
     * Gets the title of the artwork.
     * @return the artwork title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the artwork.
     * @param title the artwork title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the artwork.
     * @return the artwork description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the artwork.
     * @param description the artwork description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the category of the artwork.
     * @return the artwork category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the artwork.
     * @param category the artwork category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the price of the artwork.
     * @return the artwork price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the artwork.
     * @param price the artwork price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
}