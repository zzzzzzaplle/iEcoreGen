import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Gender enumeration for artists.
 */
enum Gender {
    MALE,
    FEMALE
}

/**
 * Categories of artworks that can be offered in the gallery.
 */
enum ArtworkCategory {
    PAINTING,
    SCULPTURE,
    ARCHITECTURE
}

/**
 * Types of membership an artist can hold.
 */
enum MembershipType {
    INDIVIDUAL,
    AGENCY,
    AGENCY_AFFILIATE
}

/**
 * Represents a membership held by an artist.
 */
class Membership {

    /** Unique identifier of the membership. */
    private String ID;

    /** Date when the membership starts (inclusive). */
    private Date startDate;

    /** Date when the membership ends (inclusive). */
    private Date endDate;

    /** Reward points awarded for the membership period. */
    private int rewardPoint;

    /** Type of the membership. */
    private MembershipType type;

    /** No‑argument constructor required for frameworks or testing. */
    public Membership() {
    }

    // ---------- Getters & Setters ----------
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
 * Represents an artwork created by an artist.
 */
class Artwork {

    /** Title of the artwork. */
    private String title;

    /** Brief description of the artwork. */
    private String description;

    /** Category of the artwork (painting, sculpture, architecture). */
    private ArtworkCategory category;

    /** Price at which the artwork is offered. */
    private double price;

    /** The artist who created the artwork. */
    private Artist artist;

    /** No‑argument constructor. */
    public Artwork() {
    }

    // ---------- Getters & Setters ----------
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
 * Represents an artist who can place artworks for auction.
 */
class Artist {

    /** Full name of the artist. */
    private String name;

    /** Contact phone number. */
    private String phoneNumber;

    /** Unique identifier of the artist. */
    private String id;

    /** Email address. */
    private String email;

    /** Physical address. */
    private String address;

    /** Gender of the artist. */
    private Gender gender;

    /** List of artworks owned by the artist. */
    private List<Artwork> artworks = new ArrayList<>();

    /** Membership held by the artist. */
    private Membership membership;

    /** No‑argument constructor. */
    public Artist() {
    }

    // ---------- Getters & Setters ----------
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

    /**
     * Adds an artwork to the artist's collection and sets the back‑reference.
     *
     * @param artwork the artwork to be added; must not be {@code null}
     */
    public void addArtwork(Artwork artwork) {
        if (artwork == null) {
            throw new IllegalArgumentException("Artwork cannot be null.");
        }
        artwork.setArtist(this);
        this.artworks.add(artwork);
    }

    /**
     * Calculates the total price of all artworks owned by this artist.
     *
     * @return the sum of the prices of all artworks; {@code 0.0} if the artist has no artworks
     */
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Artwork art : artworks) {
            total += art.getPrice();
        }
        return total;
    }

    /**
     * Returns the reward points associated with the artist's membership for a given period.
     * <p>
     * The method verifies that the supplied period (inclusive) lies completely within the
     * membership's start and end dates. If the membership does not cover the interval,
     * an {@link IllegalArgumentException} is thrown.
     *
     * @param startDate the start date of the period (inclusive); must not be {@code null}
     * @param endDate   the end date of the period (inclusive); must not be {@code null}
     * @return the reward points of the membership
     * @throws IllegalArgumentException if the artist has no membership or if the period
     *                                  is outside the membership validity dates
     */
    public int calculateRewardPoints(Date startDate, Date endDate) {
        if (membership == null) {
            throw new IllegalArgumentException("Artist does not have a membership.");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }
        // Ensure the interval is within membership dates (inclusive)
        if (startDate.compareTo(membership.getStartDate()) < 0 ||
            endDate.compareTo(membership.getEndDate()) > 0) {
            throw new IllegalArgumentException("The requested period is not covered by the membership.");
        }
        return membership.getRewardPoint();
    }

    /**
     * Counts the number of artworks the artist owns for each artwork category.
     *
     * @return a map where the key is an {@link ArtworkCategory} and the value is the count
     *         of artworks belonging to that category. Categories with zero artworks are omitted.
     */
    public Map<ArtworkCategory, Integer> countArtworksByCategory() {
        Map<ArtworkCategory, Integer> counts = new HashMap<>();
        for (Artwork art : artworks) {
            ArtworkCategory cat = art.getCategory();
            counts.put(cat, counts.getOrDefault(cat, 0) + 1);
        }
        return counts;
    }
}