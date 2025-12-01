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
 * Category enumeration for artworks.
 */
enum ArtworkCategory {
    PAINTING,
    SCULPTURE,
    ARCHITECTURE
}

/**
 * Membership type enumeration.
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
    /** Start date of the membership (inclusive). */
    private Date startDate;
    /** End date of the membership (inclusive). */
    private Date endDate;
    /** Reward points associated with the membership. */
    private int rewardPoint;
    /** Type of the membership. */
    private MembershipType type;

    /** No‑argument constructor required by the specification. */
    public Membership() {
        // initialise with default values
    }

    // ---------- Getters and Setters ----------
    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
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
    /** Description of the artwork. */
    private String description;
    /** Category of the artwork. */
    private ArtworkCategory category;
    /** Price of the artwork. */
    private double price;
    /** Reference to the author (artist). */
    private Artist artist;

    /** No‑argument constructor required by the specification. */
    public Artwork() {
        // initialise with default values
    }

    // ---------- Getters and Setters ----------
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
    /** Artist's full name. */
    private String name;
    /** Contact phone number. */
    private String phoneNumber;
    /** Unique identifier for the artist. */
    private String id;
    /** Email address. */
    private String email;
    /** Physical address. */
    private String address;
    /** Gender of the artist. */
    private Gender gender;
    /** List of artworks owned by the artist. */
    private List<Artwork> artworks;
    /** Membership associated with the artist. */
    private Membership membership;

    /** No‑argument constructor required by the specification. */
    public Artist() {
        this.artworks = new ArrayList<>();
    }

    // ---------- Getters and Setters ----------
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
     * Adds an artwork to the artist's collection and sets the artwork's author reference.
     *
     * @param artwork the artwork to be added; must not be {@code null}
     * @throws IllegalArgumentException if {@code artwork} is {@code null}
     */
    public void addArtwork(Artwork artwork) {
        if (artwork == null) {
            throw new IllegalArgumentException("Artwork cannot be null");
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
        for (Artwork a : artworks) {
            total += a.getPrice();
        }
        return total;
    }

    /**
     * Returns the reward points of the artist's membership for a specific period.
     * The period is considered valid only if the artist's membership exists and
     * both {@code startDate} and {@code endDate} fall within the membership period
     * (inclusive of the boundary dates).
     *
     * @param startDate the start date of the period (inclusive); must not be {@code null}
     * @param endDate   the end date of the period (inclusive); must not be {@code null}
     * @return the reward points associated with the membership
     * @throws IllegalArgumentException if {@code startDate} or {@code endDate} is {@code null},
     *                                  if {@code startDate} is after {@code endDate},
     *                                  or if the period does not lie within the membership dates
     */
    public int calculateRewardPoints(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        if (membership == null) {
            throw new IllegalArgumentException("Artist does not have a membership");
        }
        Date memStart = membership.getStartDate();
        Date memEnd = membership.getEndDate();

        // inclusive check
        if (!startDate.before(memStart) && !endDate.after(memEnd)) {
            return membership.getRewardPoint();
        } else {
            throw new IllegalArgumentException(
                "The given period is not fully covered by the artist's membership dates");
        }
    }

    /**
     * Counts the number of artworks the artist has in each {@link ArtworkCategory}.
     *
     * @return a map where the key is the {@code ArtworkCategory} and the value is the count
     *         of artworks belonging to that category. Categories with zero artworks are omitted.
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