import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an artist in the Online Art Gallery System
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
     * Default constructor
     */
    public Artist() {
        this.artworks = new ArrayList<>();
    }

    /**
     * Gets the artist's name
     * @return the artist's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the artist's name
     * @param name the artist's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the artist's phone number
     * @return the artist's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the artist's phone number
     * @param phoneNumber the artist's phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the artist's ID
     * @return the artist's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the artist's ID
     * @param id the artist's ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the artist's email
     * @return the artist's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the artist's email
     * @param email the artist's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the artist's address
     * @return the artist's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the artist's address
     * @param address the artist's address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the artist's gender
     * @return the artist's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the artist's gender
     * @param gender the artist's gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the list of artist's artworks
     * @return the list of artworks
     */
    public List<Artwork> getArtworks() {
        return artworks;
    }

    /**
     * Sets the list of artist's artworks
     * @param artworks the list of artworks
     */
    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    /**
     * Gets the artist's membership
     * @return the artist's membership
     */
    public Membership getMembership() {
        return membership;
    }

    /**
     * Sets the artist's membership
     * @param membership the artist's membership
     */
    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    /**
     * Adds an artwork to the artist's collection
     * @param artwork the artwork to add
     */
    public void addArtwork(Artwork artwork) {
        if (artworks == null) {
            artworks = new ArrayList<>();
        }
        artworks.add(artwork);
    }

    /**
     * Calculates the total price of all artist's artworks
     * @return the total price of all artworks
     */
    public double calculateTotalArtworkPrice() {
        double total = 0.0;
        if (artworks != null) {
            for (Artwork artwork : artworks) {
                total += artwork.getPrice();
            }
        }
        return total;
    }

    /**
     * Counts the number of artworks by category
     * @return a map containing category names as keys and counts as values
     */
    public Map<String, Integer> countArtworksByCategory() {
        Map<String, Integer> categoryCount = new HashMap<>();
        if (artworks != null) {
            for (Artwork artwork : artworks) {
                String category = artwork.getCategory();
                categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
            }
        }
        return categoryCount;
    }
}

/**
 * Represents an artwork in the Online Art Gallery System
 */
class Artwork {
    private String title;
    private String description;
    private String category;
    private double price;

    /**
     * Default constructor
     */
    public Artwork() {
    }

    /**
     * Gets the artwork title
     * @return the artwork title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the artwork title
     * @param title the artwork title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the artwork description
     * @return the artwork description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the artwork description
     * @param description the artwork description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the artwork category
     * @return the artwork category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the artwork category
     * @param category the artwork category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the artwork price
     * @return the artwork price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the artwork price
     * @param price the artwork price
     */
    public void setPrice(double price) {
        this.price = price;
    }
}

/**
 * Represents a membership in the Online Art Gallery System
 */
class Membership {
    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int rewardPoints;
    private MembershipType type;

    /**
     * Default constructor
     */
    public Membership() {
    }

    /**
     * Gets the membership ID
     * @return the membership ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the membership ID
     * @param id the membership ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the membership start date
     * @return the membership start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the membership start date
     * @param startDate the membership start date
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Sets the membership start date from string
     * @param startDateString the membership start date in "yyyy-MM-dd" format
     */
    public void setStartDate(String startDateString) {
        this.startDate = LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * Gets the membership end date
     * @return the membership end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the membership end date
     * @param endDate the membership end date
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Sets the membership end date from string
     * @param endDateString the membership end date in "yyyy-MM-dd" format
     */
    public void setEndDate(String endDateString) {
        this.endDate = LocalDate.parse(endDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * Gets the membership reward points
     * @return the membership reward points
     */
    public int getRewardPoints() {
        return rewardPoints;
    }

    /**
     * Sets the membership reward points
     * @param rewardPoints the membership reward points
     */
    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    /**
     * Gets the membership type
     * @return the membership type
     */
    public MembershipType getType() {
        return type;
    }

    /**
     * Sets the membership type
     * @param type the membership type
     */
    public void setType(MembershipType type) {
        this.type = type;
    }

    /**
     * Checks if the membership is valid during a specific time period
     * @param startDate the start date of the period to check
     * @param endDate the end date of the period to check
     * @return true if the membership is valid during the specified period, false otherwise
     */
    public boolean isValidDuringPeriod(LocalDate startDate, LocalDate endDate) {
        return !this.startDate.isAfter(endDate) && !this.endDate.isBefore(startDate);
    }

    /**
     * Gets the reward points if the membership is valid during the specified period
     * @param startDate the start date of the period to check
     * @param endDate the end date of the period to check
     * @return the reward points if membership is valid, 0 otherwise
     */
    public int getRewardPointsForPeriod(LocalDate startDate, LocalDate endDate) {
        if (isValidDuringPeriod(startDate, endDate)) {
            return rewardPoints;
        }
        return 0;
    }
}

/**
 * Enum representing the types of membership available
 */
enum MembershipType {
    INDIVIDUAL,
    AGENCY,
    AGENCY_AFFILIATE
}

/**
 * Utility class for handling artist-related operations
 */
class ArtistService {
    
    /**
     * Calculates the total price of all artworks for a given artist
     * @param artist the artist whose artworks' total price needs to be calculated
     * @return the total price of all artworks
     */
    public static double calculateTotalArtworkPrice(Artist artist) {
        if (artist == null) {
            return 0.0;
        }
        return artist.calculateTotalArtworkPrice();
    }
    
    /**
     * Gets the reward points for an artist within a specific time period
     * @param artist the artist whose reward points need to be retrieved
     * @param periodStart the start date of the period (inclusive)
     * @param periodEnd the end date of the period (inclusive)
     * @return the reward points if membership is valid during the period, 0 otherwise
     */
    public static int getRewardPointsForPeriod(Artist artist, LocalDate periodStart, LocalDate periodEnd) {
        if (artist == null || artist.getMembership() == null) {
            return 0;
        }
        return artist.getMembership().getRewardPointsForPeriod(periodStart, periodEnd);
    }
    
    /**
     * Gets the reward points for an artist within a specific time period using string dates
     * @param artist the artist whose reward points need to be retrieved
     * @param periodStartString the start date of the period in "yyyy-MM-dd" format (inclusive)
     * @param periodEndString the end date of the period in "yyyy-MM-dd" format (inclusive)
     * @return the reward points if membership is valid during the period, 0 otherwise
     */
    public static int getRewardPointsForPeriod(Artist artist, String periodStartString, String periodEndString) {
        LocalDate periodStart = LocalDate.parse(periodStartString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate periodEnd = LocalDate.parse(periodEndString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return getRewardPointsForPeriod(artist, periodStart, periodEnd);
    }
    
    /**
     * Counts the number of artworks by category for a given artist
     * @param artist the artist whose artworks need to be counted by category
     * @return a map containing category names as keys and counts as values
     */
    public static Map<String, Integer> countArtworksByCategory(Artist artist) {
        if (artist == null) {
            return new HashMap<>();
        }
        return artist.countArtworksByCategory();
    }
}