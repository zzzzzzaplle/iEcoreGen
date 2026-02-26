import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.EnumMap;

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

    /** Date when the membership starts (inclusive). */
    private Date startDate;

    /** Date when the membership ends (inclusive). */
    private Date endDate;

    /** Reward points associated with this membership. */
    private int rewardPoint;

    /** Type of the membership. */
    private MembershipType type;

    /** No‑argument constructor required for frameworks / testing. */
    public Membership() {
    }

    // -----------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------
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

    /** No‑argument constructor required for frameworks / testing. */
    public Artwork() {
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

    /** List of artworks owned by the artist. */
    private List<Artwork> artworks = new ArrayList<>();

    /** Membership held by the artist. */
    private Membership membership;

    /** No‑argument constructor required for frameworks / testing. */
    public Artist() {
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
    // Business Methods
    // -----------------------------------------------------------------

    /**
     * Calculates the total price of all artworks owned by this artist.
     *
     * @return the sum of the prices of each artwork; {@code 0.0} if the artist has no artworks.
     */
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Artwork a : artworks) {
            total += a.getPrice();
        }
        return total;
    }

    /**
     * Returns the reward points of the artist's membership for a given period,
     * provided that the membership is valid for the entire interval (inclusive).
     *
     * @param startDate the start date of the period (inclusive)
     * @param endDate   the end date of the period (inclusive)
     * @return the reward points if the membership covers the whole period; otherwise {@code 0}
     * @throws IllegalArgumentException if {@code startDate} is after {@code endDate}
     * @throws IllegalStateException    if the artist does not have a membership assigned
     */
    public int calculateRewardPoints(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start date must not be after end date.");
        }
        if (membership == null) {
            throw new IllegalStateException("Artist does not have a membership.");
        }

        // Membership is considered valid if its period fully encompasses the requested interval.
        Date membershipStart = membership.getStartDate();
        Date membershipEnd = membership.getEndDate();

        if (membershipStart == null || membershipEnd == null) {
            return 0;
        }

        boolean startsBeforeOrOn = !membershipStart.after(startDate); // membershipStart <= startDate
        boolean endsAfterOrOn = !membershipEnd.before(endDate);      // membershipEnd >= endDate

        return (startsBeforeOrOn && endsAfterOrOn) ? membership.getRewardPoint() : 0;
    }

    /**
     * Counts the number of artworks owned by this artist for each {@link ArtworkCategory}.
     *
     * @return a map where the key is an {@link ArtworkCategory} and the value is the count of artworks in that category.
     *         Categories with zero artworks are omitted from the map.
     */
    public Map<ArtworkCategory, Integer> countArtworksByCategory() {
        Map<ArtworkCategory, Integer> counts = new EnumMap<>(ArtworkCategory.class);
        for (Artwork a : artworks) {
            ArtworkCategory cat = a.getCategory();
            counts.put(cat, counts.getOrDefault(cat, 0) + 1);
        }
        return counts;
    }

    /**
     * Adds an {@link Artwork} to the artist's collection and sets the artwork's {@code artist} reference.
     *
     * @param artwork the artwork to add; must not be {@code null}
     * @throws IllegalArgumentException if {@code artwork} is {@code null}
     */
    public void addArtwork(Artwork artwork) {
        if (artwork == null) {
            throw new IllegalArgumentException("Artwork cannot be null.");
        }
        artworks.add(artwork);
        artwork.setArtist(this);
    }
}