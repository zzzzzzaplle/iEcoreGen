import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an artist that can register artworks for auction.
 */
 class Artist {

    private String name;
    private String phoneNumber;
    private String id;
    private String email;
    private String address;
    private String gender;                     // could be an enum in a real system
    private Membership membership;             // the artist's active membership
    private List<Artwork> artworks = new ArrayList<>();

    /** No‑argument constructor required by the specification. */
    public Artist() {
    }

    /* ---------- Getters & Setters ---------- */

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

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    /**
     * Calculates the total price of all artworks owned by this artist.
     *
     * @return the sum of the price of each artwork; 0.0 if the artist has no artworks.
     */
    public double calculateTotalArtworkPrice() {
        double total = 0.0;
        for (Artwork a : artworks) {
            total += a.getPrice();
        }
        return total;
    }

    /**
     * Returns the reward points of the artist's membership if the membership is
     * valid for the entire supplied period (inclusive). The dates must be supplied
     * in the format {@code yyyy-MM-dd}.
     *
     * @param periodStart the start date of the period (inclusive)
     * @param periodEnd   the end date of the period (inclusive)
     * @return the reward points associated with the membership
     * @throws IllegalArgumentException if the date strings are malformed,
     *                                  if the artist has no membership,
     *                                  or if the membership does not cover the whole period.
     */
    public int getRewardPointsWithinPeriod(String periodStart, String periodEnd) {
        if (membership == null) {
            throw new IllegalArgumentException("Artist does not have a membership.");
        }

        LocalDate start;
        LocalDate end;
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            start = LocalDate.parse(periodStart, fmt);
            end = LocalDate.parse(periodEnd, fmt);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Dates must be in format yyyy-MM-dd.", e);
        }

        // Validate that the membership period fully encloses the requested interval.
        if (membership.getStartDate() == null || membership.getEndDate() == null) {
            throw new IllegalArgumentException("Membership dates are not properly set.");
        }
        if (start.isBefore(membership.getStartDate()) || end.isAfter(membership.getEndDate())) {
            throw new IllegalArgumentException("Membership is not valid for the requested period.");
        }

        return membership.getRewardPoints();
    }

    /**
     * Counts how many artworks the artist has for each category.
     *
     * @return a map where the key is the {@link Category} and the value is the
     *         number of artworks belonging to that category. Categories with zero
     *         artworks are omitted from the map.
     */
    public Map<Category, Long> countArtworksByCategory() {
        Map<Category, Long> counts = new HashMap<>();
        for (Artwork a : artworks) {
            Category cat = a.getCategory();
            counts.put(cat, counts.getOrDefault(cat, 0L) + 1);
        }
        return counts;
    }
}

/**
 * Represents a piece of artwork that can be auctioned.
 */
class Artwork {

    private String title;
    private String description;
    private Category category;
    private double price;

    /** No‑argument constructor required by the specification. */
    public Artwork() {
    }

    /* ---------- Getters & Setters ---------- */

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
 * Enumeration of possible artwork categories.
 */
enum Category {
    PAINTING,
    SCULPTURE,
    ARCHITECTURE
}

/**
 * Base class for all membership types.
 */
abstract class Membership {

    private String membershipId;
    private LocalDate startDate;   // format "yyyy-MM-dd"
    private LocalDate endDate;     // format "yyyy-MM-dd"
    private int rewardPoints;

    /** No‑argument constructor required by the specification. */
    public Membership() {
    }

    /* ---------- Getters & Setters ---------- */

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
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
}

/**
 * Represents an individual membership.
 */
class IndividualMembership extends Membership {

    /** No‑argument constructor required by the specification. */
    public IndividualMembership() {
        super();
    }
}

/**
 * Represents an agency membership.
 */
class AgencyMembership extends Membership {

    /** No‑argument constructor required by the specification. */
    public AgencyMembership() {
        super();
    }
}

/**
 * Represents an agency affiliate membership.
 */
class AgencyAffiliateMembership extends Membership {

    /** No‑argument constructor required by the specification. */
    public AgencyAffiliateMembership() {
        super();
    }
}