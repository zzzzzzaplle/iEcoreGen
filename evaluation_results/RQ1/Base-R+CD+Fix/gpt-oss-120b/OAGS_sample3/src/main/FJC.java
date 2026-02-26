import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Enumeration for gender of an artist.
 */
enum Gender {
    MALE,
    FEMALE
}

/**
 * Enumeration for artwork categories.
 */
enum ArtworkCategory {
    PAINTING,
    SCULPTURE,
    ARCHITECTURE
}

/**
 * Enumeration for membership types.
 */
enum MembershipType {
    INDIVIDUAL,
    AGENCY,
    AGENCY_AFFILIATE
}

/**
 * Represents a membership that an artist can hold.
 */
class Membership {

    /** Unique identifier of the membership. */
    private String ID;

    /** Start date of the membership (inclusive). */
    private Date startDate;

    /** End date of the membership (inclusive). */
    private Date endDate;

    /** Reward points associated with the membership. */
    private int rewardPoint;

    /** Type of the membership. */
    private MembershipType type;

    /** No‑argument constructor. */
    public Membership() {
    }

    // ------------------------------------------------------------------------
    // Getters and Setters
    // ------------------------------------------------------------------------

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Date getStartDate() {
        return startDate == null ? null : new Date(startDate.getTime());
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate == null ? null : new Date(startDate.getTime());
    }

    public Date getEndDate() {
        return endDate == null ? null : new Date(endDate.getTime());
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate == null ? null : new Date(endDate.getTime());
    }

    public int getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public MembershipType getType() {
        return type;
    }

    public void setType(MembershipType type) {
        this.type = type;
    }
}

/**
 * Represents an artwork created by an artist.
 */
class Artwork {

    /** Title of the artwork. */
    private String title;

    /** Description of the artwork. */
    private String description;

    /** Category of the artwork. */
    private ArtworkCategory category;

    /** Price of the artwork. */
    private double price;

    /** Reference to the author (artist). */
    private Artist artist;

    /** No‑argument constructor. */
    public Artwork() {
    }

    // ------------------------------------------------------------------------
    // Getters and Setters
    // ------------------------------------------------------------------------

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

    public ArtworkCategory getCategory() {
        return category;
    }

    public void setCategory(ArtworkCategory category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}

/**
 * Represents an artist who can own artworks and a membership.
 */
class Artist {

    /** Name of the artist. */
    private String name;

    /** Phone number of the artist. */
    private String phoneNumber;

    /** Unique identifier of the artist. */
    private String id;

    /** Email address of the artist. */
    private String email;

    /** Physical address of the artist. */
    private String address;

    /** Gender of the artist. */
    private Gender gender;

    /** List of artworks owned by the artist. */
    private List<Artwork> artworks;

    /** Membership associated with the artist. */
    private Membership membership;

    /** No‑argument constructor. Initializes the artworks list. */
    public Artist() {
        this.artworks = new ArrayList<>();
    }

    // ------------------------------------------------------------------------
    // Getters and Setters
    // ------------------------------------------------------------------------

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

	public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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

    // ------------------------------------------------------------------------
    // Business methods
    // ------------------------------------------------------------------------

    /**
     * Calculates the total price of all artworks owned by this artist.
     *
     * @return the sum of the price of each artwork; returns 0.0 if the artist has no artworks.
     */
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Artwork art : artworks) {
            total += art.getPrice();
        }
        return total;
    }

    /**
     * Retrieves the reward points of the artist's membership for a given period.
     * The method first verifies that the membership is active for the whole interval
     * (inclusive of the start and end dates). If the membership is not valid for the
     * requested period, the method returns 0.
     *
     * @param startDate the beginning date of the period (inclusive)
     * @param endDate   the ending date of the period (inclusive)
     * @return the reward points associated with the membership if the period is valid; otherwise 0
     * @throws IllegalArgumentException if {@code startDate} is after {@code endDate}
     */
    public int calculateRewardPoints(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        if (membership == null) {
            return 0;
        }

        Date membershipStart = membership.getStartDate();
        Date membershipEnd = membership.getEndDate();

        // Check inclusive validity
        boolean valid = !membershipStart.after(startDate) && !membershipEnd.before(endDate);
        return valid ? membership.getRewardPoint() : 0;
    }

    /**
     * Counts the number of artworks the artist has in each category.
     *
     * @return a map where the key is the {@link ArtworkCategory} and the value is the
     *         count of artworks belonging to that category. Categories with zero artworks
     *         are also present in the map.
     */
    public Map<ArtworkCategory, Integer> countArtworksByCategory() {
        Map<ArtworkCategory, Integer> counts = new EnumMap<>(ArtworkCategory.class);
        // Initialise counts to zero for all categories
        for (ArtworkCategory cat : ArtworkCategory.values()) {
            counts.put(cat, 0);
        }

        for (Artwork art : artworks) {
            ArtworkCategory cat = art.getCategory();
            counts.put(cat, counts.get(cat) + 1);
        }

        return counts;
    }

    /**
     * Adds a new artwork to the artist's collection.
     *
     * @param artwork the artwork to add; must not be {@code null}
     * @throws IllegalArgumentException if {@code artwork} is {@code null}
     */
    public void addArtwork(Artwork artwork) {
        if (artwork == null) {
            throw new IllegalArgumentException("Artwork cannot be null.");
        }
        artwork.setArtist(this);
        artworks.add(artwork);
    }
}