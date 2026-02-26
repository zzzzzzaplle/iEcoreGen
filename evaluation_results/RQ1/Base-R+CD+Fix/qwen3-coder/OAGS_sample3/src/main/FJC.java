import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enum representing the gender of an artist.
 */
enum Gender {
    MALE,
    FEMALE
}

/**
 * Class representing an artist in the online art gallery system.
 */
class Artist {
    private String name;
    private String phoneNumber;
    private String id;
    private String email;
    private String address;
    private Gender gender;
    private List<Artwork> artworks;
    private Membership membership;

    /**
     * Default constructor for Artist.
     */
    public Artist() {
        this.artworks = new ArrayList<>();
    }

    /**
     * Gets the name of the artist.
     *
     * @return the name of the artist
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the artist.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the phone number of the artist.
     *
     * @return the phone number of the artist
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the artist.
     *
     * @param phoneNumber the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the ID of the artist.
     *
     * @return the ID of the artist
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the artist.
     *
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the email of the artist.
     *
     * @return the email of the artist
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the artist.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the address of the artist.
     *
     * @return the address of the artist
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the artist.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the gender of the artist.
     *
     * @return the gender of the artist
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the gender of the artist.
     *
     * @param gender the gender to set
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Gets the list of artworks created by the artist.
     *
     * @return the list of artworks
     */
    public List<Artwork> getArtworks() {
        return artworks;
    }

    /**
     * Sets the list of artworks created by the artist.
     *
     * @param artworks the list of artworks to set
     */
    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    /**
     * Gets the membership of the artist.
     *
     * @return the membership of the artist
     */
    public Membership getMembership() {
        return membership;
    }

    /**
     * Sets the membership of the artist.
     *
     * @param membership the membership to set
     */
    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    /**
     * Calculates the total price of all artworks created by the artist.
     *
     * @return the sum of prices of all artworks
     */
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Artwork artwork : artworks) {
            total += artwork.getPrice();
        }
        return total;
    }

    /**
     * Calculates the reward points for the artist within a specific period.
     * Verifies that the artist's membership is valid during the given interval.
     *
     * @param startDate the start date of the period (inclusive)
     * @param endDate the end date of the period (inclusive)
     * @return the reward points if membership is valid, 0 otherwise
     * @throws IllegalArgumentException if startDate or endDate is null
     */
    public int calculateRewardPoints(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }

        if (membership == null) {
            return 0;
        }

        Date membershipStart = membership.getStartDate();
        Date membershipEnd = membership.getEndDate();

        // Check if the given period overlaps with the membership period
        if (startDate.compareTo(membershipEnd) <= 0 && endDate.compareTo(membershipStart) >= 0) {
            return membership.getRewardPoint();
        }

        return 0;
    }

    /**
     * Counts the number of artworks by category for this artist.
     *
     * @return a map with artwork categories as keys and their counts as values
     */
    public Map<ArtworkCategory, Integer> countArtworksByCategory() {
        Map<ArtworkCategory, Integer> categoryCount = new HashMap<>();
        
        // Initialize counts for all categories
        for (ArtworkCategory category : ArtworkCategory.values()) {
            categoryCount.put(category, 0);
        }
        
        // Count artworks by category
        for (Artwork artwork : artworks) {
            ArtworkCategory category = artwork.getCategory();
            categoryCount.put(category, categoryCount.get(category) + 1);
        }
        
        return categoryCount;
    }

    /**
     * Adds an artwork to the artist's collection.
     *
     * @param artwork the artwork to add
     */
    public void addArtwork(Artwork artwork) {
        if (artwork != null) {
            artworks.add(artwork);
            artwork.setArtist(this);
        }
    }
}

/**
 * Enum representing the category of an artwork.
 */
enum ArtworkCategory {
    PAINTING,
    SCULPTURE,
    ARCHITECTURE
}

/**
 * Class representing an artwork in the online art gallery system.
 */
class Artwork {
    private String title;
    private String description;
    private ArtworkCategory category;
    private double price;
    private Artist artist;

    /**
     * Default constructor for Artwork.
     */
    public Artwork() {
    }

    /**
     * Gets the title of the artwork.
     *
     * @return the title of the artwork
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the artwork.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the artwork.
     *
     * @return the description of the artwork
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the artwork.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the category of the artwork.
     *
     * @return the category of the artwork
     */
    public ArtworkCategory getCategory() {
        return category;
    }

    /**
     * Sets the category of the artwork.
     *
     * @param category the category to set
     */
    public void setCategory(ArtworkCategory category) {
        this.category = category;
    }

    /**
     * Gets the price of the artwork.
     *
     * @return the price of the artwork
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the artwork.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the artist who created the artwork.
     *
     * @return the artist of the artwork
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Sets the artist who created the artwork.
     *
     * @param artist the artist to set
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}

/**
 * Enum representing the type of membership.
 */
enum MembershipType {
    INDIVIDUAL,
    AGENCY,
    AGENCY_AFFILIATE
}

/**
 * Class representing a membership in the online art gallery system.
 */
class Membership {
    private String ID;
    private Date startDate;
    private Date endDate;
    private int rewardPoint;
    private MembershipType type;

    /**
     * Default constructor for Membership.
     */
    public Membership() {
    }

    /**
     * Gets the ID of the membership.
     *
     * @return the ID of the membership
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the ID of the membership.
     *
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Gets the start date of the membership.
     *
     * @return the start date of the membership
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the membership.
     *
     * @param startDate the start date to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the membership.
     *
     * @return the end date of the membership
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the membership.
     *
     * @param endDate the end date to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the reward points of the membership.
     *
     * @return the reward points of the membership
     */
    public int getRewardPoint() {
        return rewardPoint;
    }

    /**
     * Sets the reward points of the membership.
     *
     * @param rewardPoint the reward points to set
     */
    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    /**
     * Gets the type of the membership.
     *
     * @return the type of the membership
     */
    public MembershipType getType() {
        return type;
    }

    /**
     * Sets the type of the membership.
     *
     * @param type the type to set
     */
    public void setType(MembershipType type) {
        this.type = type;
    }
}