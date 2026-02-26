import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
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

    /** Unparameterized constructor. */
    public Artwork() {
    }

    /** Parameterized constructor for convenience. */
    public Artwork(String title, String description, Category category, double price) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

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
abstract class Membership {

    private String id;
    private LocalDate startDate;   // inclusive
    private LocalDate endDate;     // inclusive
    private int rewardPoints;

    /** Unparameterized constructor. */
    public Membership() {
    }

    /** Parameterized constructor for convenience. */
    public Membership(String id, LocalDate startDate, LocalDate endDate, int rewardPoints) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rewardPoints = rewardPoints;
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    /**
     * Checks whether this membership is valid for the whole interval
     * {@code [periodStart, periodEnd]} (inclusive of both boundaries).
     *
     * @param periodStart the start date of the interval (inclusive)
     * @param periodEnd   the end date of the interval (inclusive)
     * @return {@code true} if the membership covers the whole interval,
     *         {@code false} otherwise
     * @throws NullPointerException if either {@code periodStart} or {@code periodEnd} is {@code null}
     */
    public boolean isValidDuring(LocalDate periodStart, LocalDate periodEnd) {
        Objects.requireNonNull(periodStart, "periodStart must not be null");
        Objects.requireNonNull(periodEnd, "periodEnd must not be null");
        // A membership is valid for the period if its startDate <= periodStart
        // and its endDate >= periodEnd.
        return (startDate != null && endDate != null)
                && (!startDate.isAfter(periodStart))
                && (!endDate.isBefore(periodEnd));
    }
}

/**
 * Individual type membership.
 */
class IndividualMembership extends Membership {

    /** Unparameterized constructor. */
    public IndividualMembership() {
        super();
    }

    /** Parameterized constructor for convenience. */
    public IndividualMembership(String id, LocalDate startDate, LocalDate endDate, int rewardPoints) {
        super(id, startDate, endDate, rewardPoints);
    }
}

/**
 * Agency type membership.
 */
class AgencyMembership extends Membership {

    /** Unparameterized constructor. */
    public AgencyMembership() {
        super();
    }

    /** Parameterized constructor for convenience. */
    public AgencyMembership(String id, LocalDate startDate, LocalDate endDate, int rewardPoints) {
        super(id, startDate, endDate, rewardPoints);
    }
}

/**
 * Agency Affiliate type membership.
 */
class AgencyAffiliateMembership extends Membership {

    /** Unparameterized constructor. */
    public AgencyAffiliateMembership() {
        super();
    }

    /** Parameterized constructor for convenience. */
    public AgencyAffiliateMembership(String id, LocalDate startDate, LocalDate endDate, int rewardPoints) {
        super(id, startDate, endDate, rewardPoints);
    }
}

/**
 * Represents an artist who can own multiple artworks and holds a membership.
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

    /** Unparameterized constructor. */
    public Artist() {
    }

    /** Parameterized constructor for convenience. */
    public Artist(String name, String phoneNumber, String id, String email,
                  String address, String gender, Membership membership) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.membership = membership;
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

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
     * Returns an unmodifiable view of the artworks owned by this artist.
     *
     * @return list of artworks (read‑only)
     */
    public List<Artwork> getArtworks() {
        return Collections.unmodifiableList(artworks);
    }

    /**
     * Adds an artwork to the artist's collection.
     *
     * @param artwork the artwork to add; must not be {@code null}
     * @throws NullPointerException if {@code artwork} is {@code null}
     */
    public void addArtwork(Artwork artwork) {
        Objects.requireNonNull(artwork, "artwork must not be null");
        artworks.add(artwork);
    }

    /**
     * Removes an artwork from the artist's collection.
     *
     * @param artwork the artwork to remove; must not be {@code null}
     * @return {@code true} if the artwork was present and removed, {@code false} otherwise
     * @throws NullPointerException if {@code artwork} is {@code null}
     */
    public boolean removeArtwork(Artwork artwork) {
        Objects.requireNonNull(artwork, "artwork must not be null");
        return artworks.remove(artwork);
    }

    // -----------------------------------------------------------------------
    // Functional Requirement Implementations
    // -----------------------------------------------------------------------

    /**
     * Calculates the total price of all artworks owned by this artist.
     *
     * @return the sum of the prices of all artworks; {@code 0.0} if the artist has none
     */
    public double calculateTotalArtworkPrice() {
        return artworks.stream()
                .mapToDouble(Artwork::getPrice)
                .sum();
    }

    /**
     * Retrieves the reward points of the artist's membership for a given period,
     * after verifying that the membership is valid for the whole interval.
     *
     * @param periodStart the start date of the interval (inclusive)
     * @param periodEnd   the end date of the interval (inclusive)
     * @return the reward points associated with the membership
     * @throws IllegalArgumentException if the artist does not have a membership,
     *                                  or if the membership does not cover the requested period
     * @throws NullPointerException     if either {@code periodStart} or {@code periodEnd} is {@code null}
     */
    public int getRewardPointsWithinPeriod(LocalDate periodStart, LocalDate periodEnd) {
        Objects.requireNonNull(periodStart, "periodStart must not be null");
        Objects.requireNonNull(periodEnd, "periodEnd must not be null");

        if (membership == null) {
            throw new IllegalArgumentException("Artist has no membership.");
        }

        if (!membership.isValidDuring(periodStart, periodEnd)) {
            throw new IllegalArgumentException(
                    "Membership is not valid for the requested period [" + periodStart + " to " + periodEnd + "].");
        }

        return membership.getRewardPoints();
    }

    /**
     * Counts the number of artworks owned by this artist per category.
     *
     * @return an {@link EnumMap} where the key is a {@link Category} and the value
     *         is the number of artworks that belong to that category. Categories
     *         with zero artworks are included with a count of {@code 0}.
     */
    public Map<Category, Integer> countArtworksByCategory() {
        EnumMap<Category, Integer> counts = new EnumMap<>(Category.class);
        // Initialise all categories with zero
        for (Category cat : Category.values()) {
            counts.put(cat, 0);
        }

        // Accumulate counts
        for (Artwork art : artworks) {
            Category cat = art.getCategory();
            counts.put(cat, counts.get(cat) + 1);
        }

        return counts;
    }
}