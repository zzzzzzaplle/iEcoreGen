import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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
 * Represents a membership held by an {@link Artist}.
 */
class Membership {
    /** Unique identifier of the membership. */
    private String ID;
    /** Date when the membership starts (inclusive). */
    private Date startDate;
    /** Date when the membership ends (inclusive). */
    private Date endDate;
    /** Reward points earned by the member. */
    private int rewardPoint;
    /** Type of the membership. */
    private MembershipType type;

    /** Default constructor. */
    public Membership() {
    }

    // --------------------------------------------------------------------
    // Getters and Setters
    // --------------------------------------------------------------------
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
 * Represents an artwork created by an {@link Artist}.
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

    /** Default constructor. */
    public Artwork() {
    }

    // --------------------------------------------------------------------
    // Getters and Setters
    // --------------------------------------------------------------------
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
 * Represents an artist who can own multiple artworks and a membership.
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
    /** List of artworks belonging to the artist. */
    private List<Artwork> artworks;
    /** Membership associated with the artist. */
    private Membership membership;

    /** Default constructor. */
    public Artist() {
        // Initialise the artworks list to avoid NullPointerExceptions when adding.
        this.artworks = new ArrayList<>();
    }

    // --------------------------------------------------------------------
    // Getters and Setters
    // --------------------------------------------------------------------
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

    // --------------------------------------------------------------------
    // Business Logic Methods
    // --------------------------------------------------------------------

    /**
     * Calculates the total price of all artworks owned by this artist.
     *
     * @return the sum of the prices of all artworks; {@code 0.0} if the artist has no artworks.
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
     * provided that the membership is active for the entire interval (inclusive).
     *
     * @param periodStart the start date of the requested period (inclusive)
     * @param periodEnd   the end date of the requested period (inclusive)
     * @return the reward points if the membership covers the whole period; otherwise {@code 0}
     * @throws IllegalArgumentException if {@code periodStart} is after {@code periodEnd}
     */
    public int calculateRewardPoints(Date periodStart, Date periodEnd) {
        if (periodStart == null || periodEnd == null) {
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }
        if (periodStart.after(periodEnd)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        if (membership == null) {
            return 0;
        }
        Date memStart = membership.getStartDate();
        Date memEnd = membership.getEndDate();

        // Membership must cover the whole requested interval (inclusive).
        if (memStart != null && memEnd != null &&
                !memStart.after(periodStart) && !memEnd.before(periodEnd)) {
            return membership.getRewardPoint();
        }
        return 0;
    }

    /**
     * Counts the artist's artworks per {@link ArtworkCategory}.
     *
     * @return a map where the key is an {@code ArtworkCategory} and the value is the number of
     *         artworks belonging to that category. Categories with zero artworks are omitted.
     */
    public Map<ArtworkCategory, Integer> countArtworksByCategory() {
        Map<ArtworkCategory, Integer> counts = new HashMap<>();
        if (artworks != null) {
            for (Artwork a : artworks) {
                ArtworkCategory cat = a.getCategory();
                if (cat != null) {
                    counts.put(cat, counts.getOrDefault(cat, 0) + 1);
                }
            }
        }
        return counts;
    }

    /**
     * Adds an {@link Artwork} to the artist's collection.
     *
     * @param artwork the artwork to be added; must not be {@code null}
     * @throws IllegalArgumentException if {@code artwork} is {@code null}
     */
    public void addArtwork(Artwork artwork) {
        if (artwork == null) {
            throw new IllegalArgumentException("Artwork cannot be null.");
        }
        if (artworks == null) {
            artworks = new ArrayList<>();
        }
        artworks.add(artwork);
        artwork.setArtist(this);
    }
}