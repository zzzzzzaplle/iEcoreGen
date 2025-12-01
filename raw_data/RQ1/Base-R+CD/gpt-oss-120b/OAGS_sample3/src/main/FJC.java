import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enumeration of possible genders for an {@link Artist}.
 */
enum Gender {
    MALE,
    FEMALE
}

/**
 * Enumeration of artwork categories.
 */
enum ArtworkCategory {
    PAINTING,
    SCULPTURE,
    ARCHITECTURE
}

/**
 * Enumeration of membership types.
 */
enum MembershipType {
    INDIVIDUAL,
    AGENCY,
    AGENCY_AFFILIATE
}

/**
 * Represents a membership that an {@link Artist} can hold.
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

    /** Unparameterized constructor required by the specification. */
    public Membership() {
        // Default constructor – fields can be set via setters.
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
 * Represents a piece of artwork created by an {@link Artist}.
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

    /** The artist who created the artwork. */
    private Artist artist;

    /** Unparameterized constructor required by the specification. */
    public Artwork() {
        // Default constructor – fields can be set via setters.
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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
 * Represents an artist who can own artworks and hold a membership.
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

    /** List of artworks belonging to the artist. */
    private List<Artwork> artworks;

    /** Membership held by the artist. */
    private Membership membership;

    /** Unparameterized constructor required by the specification. */
    public Artist() {
        // Initialise the collection to avoid NullPointerExceptions.
        this.artworks = new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Business Methods
    // -------------------------------------------------------------------------

    /**
     * Adds an {@link Artwork} to the artist's collection.
     *
     * @param artwork the artwork to add; must not be {@code null}
     */
    public void addArtwork(Artwork artwork) {
        if (artwork == null) {
            throw new IllegalArgumentException("Artwork cannot be null");
        }
        // Establish bidirectional relationship
        artwork.setArtist(this);
        this.artworks.add(artwork);
    }

    /**
     * Calculates the total price of all artworks belonging to this artist.
     *
     * @return the sum of the prices of all artworks; {@code 0.0} if the artist has no artworks
     */
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Artwork a : artworks) {
            total += a.getPrice();
        }
        return total;
    }

    /**
     * Returns the reward points of the artist's membership if the membership is
     * valid for the whole period defined by {@code startDate} and {@code endDate}
     * (both inclusive). If the membership does not cover the entire interval,
     * {@code 0} is returned.
     *
     * @param startDate the start of the period (inclusive); must not be {@code null}
     * @param endDate   the end of the period (inclusive); must not be {@code null}
     * @return reward points for the period, or {@code 0} if the membership is not valid
     * @throws IllegalArgumentException if {@code startDate} is after {@code endDate}
     */
    public int calculateRewardPoints(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start date must not be after end date");
        }

        if (membership == null) {
            return 0;
        }

        // Membership dates are inclusive.
        boolean startsBeforeOrOn = !membership.getStartDate().after(startDate);
        boolean endsAfterOrOn = !membership.getEndDate().before(endDate);

        if (startsBeforeOrOn && endsAfterOrOn) {
            return membership.getRewardPoint();
        }

        return 0;
    }

    /**
     * Counts the artist's artworks per {@link ArtworkCategory}.
     *
     * @return a map where the key is an {@link ArtworkCategory} and the value is
     *         the number of artworks the artist has in that category.
     *         Categories with a count of zero are omitted from the map.
     */
    public Map<ArtworkCategory, Integer> countArtworksByCategory() {
        Map<ArtworkCategory, Integer> counts = new HashMap<>();
        for (Artwork a : artworks) {
            ArtworkCategory cat = a.getCategory();
            counts.put(cat, counts.getOrDefault(cat, 0) + 1);
        }
        return counts;
    }
}