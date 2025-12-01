import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Enumeration of possible artwork categories.
 */
enum Category {
    PAINTING,
    SCULPTURE,
    ARCHITECTURE
}

/**
 * Represents a single piece of artwork.
 */
 class Artwork {

    private String title;
    private String description;
    private Category category;
    private double price;

    /** Un‑parameterized constructor required by the specification. */
    public Artwork() {
        // default constructor
    }

    /** Full constructor for convenience (not required by the specification). */
    public Artwork(String title, String description, Category category, double price) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

/**
 * Abstract base class for all membership types.
 */
public abstract class Membership {

    private String membershipId;
    private LocalDate startDate;   // inclusive
    private LocalDate endDate;     // inclusive
    private int rewardPoints;

    /** Un‑parameterized constructor required by the specification. */
    public Membership() {
        // default constructor
    }

    /** Full constructor for convenience (not required by the specification). */
    public Membership(String membershipId, String startDateStr, String endDateStr, int rewardPoints) {
        this.membershipId = membershipId;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = LocalDate.parse(startDateStr, fmt);
        this.endDate = LocalDate.parse(endDateStr, fmt);
        this.rewardPoints = rewardPoints;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the membership.
     *
     * @param startDateStr the start date string in format "yyyy-MM-dd"
     * @throws IllegalArgumentException if the string cannot be parsed
     */
    public void setStartDate(String startDateStr) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = LocalDate.parse(startDateStr, fmt);
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the membership.
     *
     * @param endDateStr the end date string in format "yyyy-MM-dd"
     * @throws IllegalArgumentException if the string cannot be parsed
     */
    public void setEndDate(String endDateStr) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.endDate = LocalDate.parse(endDateStr, fmt);
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    /**
     * Checks whether the membership is valid for the entire given period
     * (inclusive of the boundary dates).
     *
     * @param periodStart the start of the period (inclusive)
     * @param periodEnd   the end of the period (inclusive)
     * @return {@code true} if the membership covers the whole period,
     *         {@code false} otherwise
     */
    public boolean isValidDuring(LocalDate periodStart, LocalDate periodEnd) {
        Objects.requireNonNull(periodStart, "periodStart must not be null");
        Objects.requireNonNull(periodEnd, "periodEnd must not be null");
        return ( !startDate.isAfter(periodStart) ) && ( !endDate.isBefore(periodEnd) );
    }
}

/**
 * Membership for an individual artist.
 */
 class IndividualMembership extends Membership {

    /** Un‑parameterized constructor required by the specification. */
    public IndividualMembership() {
        super();
    }

    /** Full constructor for convenience (not required by the specification). */
    public IndividualMembership(String membershipId, String startDateStr, String endDateStr, int rewardPoints) {
        super(membershipId, startDateStr, endDateStr, rewardPoints);
    }
}

/**
 * Membership for an agency.
 */
 class AgencyMembership extends Membership {

    /** Un‑parameterized constructor required by the specification. */
    public AgencyMembership() {
        super();
    }

    /** Full constructor for convenience (not required by the specification). */
    public AgencyMembership(String membershipId, String startDateStr, String endDateStr, int rewardPoints) {
        super(membershipId, startDateStr, endDateStr, rewardPoints);
    }
}

/**
 * Membership for an agency affiliate.
 */
 class AgencyAffiliateMembership extends Membership {

    /** Un‑parameterized constructor required by the specification. */
    public AgencyAffiliateMembership() {
        super();
    }

    /** Full constructor for convenience (not required by the specification). */
    public AgencyAffiliateMembership(String membershipId, String startDateStr, String endDateStr, int rewardPoints) {
        super(membershipId, startDateStr, endDateStr, rewardPoints);
    }
}

/**
 * Represents an artist who can own artworks and hold a membership.
 */
 class Artist {

    private String name;
    private String phoneNumber;
    private String id;
    private String email;
    private String address;
    private String gender;
    private Membership membership;
    private List<Artwork> artworks = new ArrayList<>();

    /** Un‑parameterized constructor required by the specification. */
    public Artist() {
        // default constructor
    }

    /** Full constructor for convenience (not required by the specification). */
    public Artist(String name, String phoneNumber, String id,
                  String email, String address, String gender,
                  Membership membership) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.membership = membership;
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

	public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    /**
     * Returns an unmodifiable view of the artist's artworks.
     *
     * @return list of artworks
     */
    public List<Artwork> getArtworks() {
        return Collections.unmodifiableList(artworks);
    }

    /**
     * Replaces the internal artwork list with a copy of the supplied list.
     *
     * @param artworks the new list of artworks (may be empty but not null)
     * @throws NullPointerException if {@code artworks} is {@code null}
     */
    public void setArtworks(List<Artwork> artworks) {
        Objects.requireNonNull(artworks, "artworks must not be null");
        this.artworks = new ArrayList<>(artworks);
    }

    /**
     * Adds a single artwork to the artist's collection.
     *
     * @param artwork the artwork to add (must not be {@code null})
     * @throws NullPointerException if {@code artwork} is {@code null}
     */
    public void addArtwork(Artwork artwork) {
        Objects.requireNonNull(artwork, "artwork must not be null");
        this.artworks.add(artwork);
    }

    // -------------------------------------------------------------------------
    // Functional requirement implementations
    // -------------------------------------------------------------------------

    /**
     * Calculates the total price of all artworks owned by this artist.
     *
     * @return the sum of the price of each artwork; {@code 0.0} if the artist has no artworks
     */
    public double calculateTotalArtworkPrice() {
        return artworks.stream()
                       .mapToDouble(Artwork::getPrice)
                       .sum();
    }

    /**
     * Retrieves the reward points of the artist's membership for a given period.
     *
     * <p>The method first checks that the artist actually has a membership.
     * Then it verifies that the membership is valid for the entire period
     * (inclusive of the start and end dates). If the membership does not
     * cover the period, an {@link IllegalArgumentException} is thrown.</p>
     *
     * @param periodStartStr the start date of the period in format "yyyy-MM-dd"
     * @param periodEndStr   the end date of the period in format "yyyy-MM-dd"
     * @return the reward points associated with the membership
     * @throws IllegalArgumentException if the artist has no membership or if the
     *                                  membership does not span the entire period
     */
    public int getRewardPointsWithinPeriod(String periodStartStr, String periodEndStr) {
        if (membership == null) {
            throw new IllegalArgumentException("Artist does not have a membership.");
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate periodStart = LocalDate.parse(periodStartStr, fmt);
        LocalDate periodEnd   = LocalDate.parse(periodEndStr, fmt);

        if (!membership.isValidDuring(periodStart, periodEnd)) {
            throw new IllegalArgumentException("Membership is not valid for the requested period.");
        }

        return membership.getRewardPoints();
    }

    /**
     * Counts the number of artworks the artist owns for each category.
     *
     * @return a map where the key is a {@link Category} and the value is the
     *         number of artworks of that category owned by the artist.
     *         Categories with zero artworks are omitted from the map.
     */
    public Map<Category, Integer> countArtworksByCategory() {
        Map<Category, Integer> result = new HashMap<>();
        for (Artwork a : artworks) {
            result.merge(a.getCategory(), 1, Integer::sum);
        }
        return result;
    }
}