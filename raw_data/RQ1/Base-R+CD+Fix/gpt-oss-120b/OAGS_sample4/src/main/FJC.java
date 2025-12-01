import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.EnumMap;

/**
 * Gender enumeration for an artist.
 */
enum Gender {
    MALE,
    FEMALE
}

/**
 * Types of artwork categories.
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

    /** Reward points earned by the membership. */
    private int rewardPoint;

    /** Type of the membership. */
    private MembershipType type;

    /** No‑argument constructor required by the specification. */
    public Membership() {
        // default constructor
    }

    // -----------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------

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

    /** The artist who created the artwork. */
    private Artist artist;

    /** No‑argument constructor required by the specification. */
    public Artwork() {
        // default constructor
    }

    // -----------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------

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
 * Represents an artist that can place artworks in an auction.
 */
class Artist {

    /** Name of the artist. */
    private String name;

    /** Phone number of the artist. */
    private String phoneNumber;

    /** Identifier of the artist. */
    private String id;

    /** Email address of the artist. */
    private String email;

    /** Physical address of the artist. */
    private String address;

    /** Gender of the artist. */
    private Gender gender;

    /** List of artworks owned by the artist. */
    private List<Artwork> artworks;

    /** Membership held by the artist. */
    private Membership membership;

    /** No‑argument constructor required by the specification. */
    public Artist() {
        this.artworks = new ArrayList<>();
    }

    // -----------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------

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

    // -----------------------------------------------------------------
    // Business methods required by the functional specifications
    // -----------------------------------------------------------------

    /**
     * Calculates the total price of all artworks belonging to this artist.
     *
     * @return the sum of the prices of all artworks; 0.0 if the artist has no artworks.
     */
    public double calculateTotalPrice() {
        double total = 0.0;
        if (artworks != null) {
            for (Artwork a : artworks) {
                total += a.getPrice();
            }
        }
        return total;
    }

    /**
     * Returns the reward points of the artist's membership for a given period,
     * provided that the membership is valid for the entire interval (inclusive).
     *
     * @param startDate the beginning date of the period (inclusive)
     * @param endDate   the ending date of the period (inclusive)
     * @return the reward points if the membership covers the whole period; otherwise, 0.
     * @throws IllegalArgumentException if {@code startDate} or {@code endDate} is {@code null},
     *                                  or if {@code startDate} occurs after {@code endDate}.
     */
    public int calculateRewardPoints(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start date must not be after end date.");
        }

        if (membership == null) {
            return 0;
        }

        Date memStart = membership.getStartDate();
        Date memEnd = membership.getEndDate();

        // membership must start on/before the requested startDate
        // and end on/after the requested endDate (inclusive)
        boolean valid = !memStart.after(startDate) && !memEnd.before(endDate);
        return valid ? membership.getRewardPoint() : 0;
    }

    /**
     * Counts the artist's artworks per category.
     *
     * @return a map where each key is an {@link ArtworkCategory} and the corresponding
     *         value is the number of artworks the artist has in that category.
     *         Categories with zero artworks are omitted from the map.
     */
    public Map<ArtworkCategory, Integer> countArtworksByCategory() {
        Map<ArtworkCategory, Integer> counts = new EnumMap<>(ArtworkCategory.class);
        if (artworks != null) {
            for (Artwork a : artworks) {
                ArtworkCategory cat = a.getCategory();
                counts.put(cat, counts.getOrDefault(cat, 0) + 1);
            }
        }
        return counts;
    }

    /**
     * Adds an artwork to the artist's collection and automatically sets the
     * artwork's {@code artist} reference to this artist.
     *
     * @param artwork the artwork to add; must not be {@code null}.
     * @throws IllegalArgumentException if {@code artwork} is {@code null}.
     */
    public void addArtwork(Artwork artwork) {
        if (artwork == null) {
            throw new IllegalArgumentException("Artwork must not be null.");
        }
        if (artworks == null) {
            artworks = new ArrayList<>();
        }
        artwork.setArtist(this);
        artworks.add(artwork);
    }
}