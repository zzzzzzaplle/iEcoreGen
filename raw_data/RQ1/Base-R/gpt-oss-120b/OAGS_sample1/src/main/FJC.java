import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Enumeration of possible artwork categories.
 */
enum Category {
    PAINTING,
    SCULPTURE,
    ARCHITECTURE
}

/**
 * Simple enumeration for gender representation.
 */
enum Gender {
    MALE,
    FEMALE,
    OTHER
}

/**
 * Base class representing a membership.
 * Concrete membership types can extend this class if additional behaviour is required.
 */
class Membership {

    /** Unique identifier of the membership. */
    private String membershipId;

    /** Membership start date (inclusive). */
    private LocalDate startDate;

    /** Membership end date (inclusive). */
    private LocalDate endDate;

    /** Reward points accumulated for this membership. */
    private int rewardPoints;

    /** Default constructor required by the specification. */
    public Membership() {
        // fields remain null / zero until set via setters
    }

    // ------------------------------------------------------------------------
    // Getters and Setters
    // ------------------------------------------------------------------------

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
     * @param startDate the start date in ISO format (yyyy-MM-dd)
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the membership.
     *
     * @param endDate the end date in ISO format (yyyy-MM-dd)
     */
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
     * Checks whether this membership is valid for the whole period
     * defined by {@code from} and {@code to} (both inclusive).
     *
     * @param from start of the period (inclusive)
     * @param to   end of the period (inclusive)
     * @return {@code true} if the membership covers the whole interval,
     *         {@code false} otherwise
     */
    public boolean isValidForPeriod(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            return false;
        }
        // membership must start on or before the period start and end on or after the period end
        return (startDate != null && !startDate.isAfter(from)) &&
               (endDate != null && !endDate.isBefore(to));
    }
}

/**
 * Represents an individual membership.
 * Currently does not add extra fields beyond {@link Membership},
 * but exists for semantic clarity and future extensions.
 */
class IndividualMembership extends Membership {
    public IndividualMembership() {
        super();
    }
}

/**
 * Represents an agency membership.
 */
class AgencyMembership extends Membership {
    public AgencyMembership() {
        super();
    }
}

/**
 * Represents an agency affiliate membership.
 */
class AgencyAffiliateMembership extends Membership {
    public AgencyAffiliateMembership() {
        super();
    }
}

/**
 * Class representing an artwork placed by an artist.
 */
class Artwork {

    /** Title of the artwork. */
    private String title;

    /** Description of the artwork. */
    private String description;

    /** Category of the artwork (painting, sculpture, architecture). */
    private Category category;

    /** Price of the artwork. */
    private double price;

    /** Default constructor required by the specification. */
    public Artwork() {
        // fields remain null / zero until set via setters
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
 * Class representing an artist who can place artworks on auction.
 * Provides methods to compute totals, reward points and category statistics.
 */
class Artist {

    /** Artist's unique identifier. */
    private String id;

    /** Artist's full name. */
    private String name;

    /** Phone number of the artist. */
    private String phoneNumber;

    /** Email address of the artist. */
    private String email;

    /** Physical address of the artist. */
    private String address;

    /** Gender of the artist. */
    private Gender gender;

    /** Membership associated with the artist. */
    private Membership membership;

    /** List of artworks owned by the artist. */
    private List<Artwork> artworks;

    /** Default constructor required by the specification. */
    public Artist() {
        this.artworks = new ArrayList<>();
    }

    // ------------------------------------------------------------------------
    // Getters and Setters
    // ------------------------------------------------------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public List<Artwork> getArtworks() {
        return Collections.unmodifiableList(artworks);
    }

    /**
     * Adds an artwork to the artist's collection.
     *
     * @param artwork the artwork to add; must not be {@code null}
     */
    public void addArtwork(Artwork artwork) {
        if (artwork != null) {
            artworks.add(artwork);
        }
    }

    /**
     * Removes an artwork from the artist's collection.
     *
     * @param artwork the artwork to remove; must not be {@code null}
     * @return {@code true} if the artwork was present and removed,
     *         {@code false} otherwise
     */
    public boolean removeArtwork(Artwork artwork) {
        if (artwork != null) {
            return artworks.remove(artwork);
        }
        return false;
    }

    // ------------------------------------------------------------------------
    // Functional Requirements
    // ------------------------------------------------------------------------

    /**
     * Calculates the total price of all artworks owned by this artist.
     *
     * @return the sum of the prices of every artwork; {@code 0.0} if the artist has no artworks
     */
    public double calculateTotalArtworkPrice() {
        return artworks.stream()
                .mapToDouble(Artwork::getPrice)
                .sum();
    }

    /**
     * Returns the reward points of the artist's membership for a specific period,
     * provided the membership is valid for the entire interval (inclusive).
     *
     * @param periodStart the start date of the period (inclusive)
     * @param periodEnd   the end date of the period (inclusive)
     * @return the reward points associated with the membership
     * @throws IllegalArgumentException if the membership is {@code null},
     *                                  or if the period is invalid,
     *                                  or if the membership does not cover the whole period
     */
    public int getRewardPointsWithinPeriod(LocalDate periodStart, LocalDate periodEnd) {
        if (membership == null) {
            throw new IllegalArgumentException("Artist does not have a membership.");
        }
        if (periodStart == null || periodEnd == null) {
            throw new IllegalArgumentException("Period start and end dates must not be null.");
        }
        if (periodEnd.isBefore(periodStart)) {
            throw new IllegalArgumentException("Period end date cannot be before start date.");
        }
        if (!membership.isValidForPeriod(periodStart, periodEnd)) {
            throw new IllegalArgumentException("Membership is not valid for the requested period.");
        }
        return membership.getRewardPoints();
    }

    /**
     * Counts the number of artworks belonging to each category.
     *
     * @return an immutable map where the key is a {@link Category} and the value is
     *         the count of artworks in that category. Categories with zero artworks
     *         are omitted from the map.
     */
    public Map<Category, Long> countArtworksByCategory() {
        Map<Category, Long> counts = new EnumMap<>(Category.class);
        artworks.stream()
                .filter(a -> a.getCategory() != null)
                .forEach(a -> counts.merge(a.getCategory(), 1L, Long::sum));
        return Collections.unmodifiableMap(counts);
    }
}