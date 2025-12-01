import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Gender of an artist.
 */
 enum Gender {
    MALE,
    FEMALE
}

/**
 * Categories that an artwork may belong to.
 */
 enum ArtworkCategory {
    PAINTING,
    SCULPTURE,
    ARCHITECTURE
}

/**
 * Types of memberships an artist can hold.
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

    /** Reward points earned with this membership. */
    private int rewardPoint;

    /** Type of the membership. */
    private MembershipType type;

    /** No‑argument constructor. Initializes fields to default values. */
    public Membership() {
        // Default constructor required for frameworks / testing
    }

    // ----- Getters and Setters -----
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

    /** Short description of the artwork. */
    private String description;

    /** Category of the artwork (painting, sculpture, architecture). */
    private ArtworkCategory category;

    /** Sale price of the artwork. */
    private double price;

    /** Artist who created the artwork. */
    private Artist artist;

    /** No‑argument constructor. */
    public Artwork() {
        // Default constructor required for frameworks / testing
    }

    // ----- Getters and Setters -----
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
 * Represents an artist that can own artworks and hold a membership.
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
    private List<Artwork> artworks;

    /** Membership held by the artist. */
    private Membership membership;

    /** No‑argument constructor. Initializes the artwork list. */
    public Artist() {
        this.artworks = new ArrayList<>();
    }

    // ----- Getters and Setters -----
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
     * Adds an artwork to the artist's collection.
     *
     * @param artwork the artwork to be added; must not be {@code null}
     */
    public void addArtwork(Artwork artwork) {
        if (artwork != null) {
            this.artworks.add(artwork);
            artwork.setArtist(this);
        }
    }

    /**
     * Calculates the total price of all artworks owned by the artist.
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
     * Returns the reward points of the artist's membership if the membership is valid
     * for the whole interval defined by {@code startDate} and {@code endDate} (inclusive).
     *
     * @param startDate the start date of the interval (inclusive)
     * @param endDate   the end date of the interval (inclusive)
     * @return the reward points if the membership covers the interval; otherwise {@code 0}
     * @throws IllegalArgumentException if {@code startDate} is after {@code endDate}
     */
    public int calculateRewardPoints(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start date must not be after end date.");
        }
        if (membership == null) {
            return 0;
        }
        Date membershipStart = membership.getStartDate();
        Date membershipEnd = membership.getEndDate();

        // Membership must be active for the whole requested period (inclusive)
        boolean valid = !membershipStart.after(startDate) && !membershipEnd.before(endDate);
        return valid ? membership.getRewardPoint() : 0;
    }

    /**
     * Counts the artist's artworks per category.
     *
     * @return a map where the key is an {@link ArtworkCategory} and the value is the number of artworks
     *         the artist has in that category. Categories with zero artworks are omitted from the map.
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