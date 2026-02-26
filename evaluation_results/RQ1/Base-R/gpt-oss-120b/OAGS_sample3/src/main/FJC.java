import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
 * Represents a piece of artwork belonging to an artist.
 */
class Artwork {

    private String title;
    private String description;
    private Category category;
    private double price;

    /** Un‑parameterized constructor required for test frameworks. */
    public Artwork() {
    }

    /** @return the title of the artwork */
    public String getTitle() {
        return title;
    }

    /** @param title the title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return the description of the artwork */
    public String getDescription() {
        return description;
    }

    /** @param description the description to set */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return the category of the artwork */
    public Category getCategory() {
        return category;
    }

    /** @param category the category to set */
    public void setCategory(Category category) {
        this.category = category;
    }

    /** @return the price of the artwork */
    public double getPrice() {
        return price;
    }

    /** @param price the price to set */
    public void setPrice(double price) {
        this.price = price;
    }
}

/**
 * Base class for all membership types.
 */
class Membership {

    private String membershipId;
    private LocalDate startDate;   // inclusive
    private LocalDate endDate;     // inclusive
    private int rewardPoints;

    /** Un‑parameterized constructor required for test frameworks. */
    public Membership() {
    }

    /** @return the unique identifier of the membership */
    public String getMembershipId() {
        return membershipId;
    }

    /** @param membershipId the identifier to set */
    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    /** @return the start date of the membership (inclusive) */
    public LocalDate getStartDate() {
        return startDate;
    }

    /** @param startDate the start date to set */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /** @return the end date of the membership (inclusive) */
    public LocalDate getEndDate() {
        return endDate;
    }

    /** @param endDate the end date to set */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /** @return the reward points accumulated for this membership */
    public int getRewardPoints() {
        return rewardPoints;
    }

    /** @param rewardPoints the reward points to set */
    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    /**
     * Checks whether a given period (both dates inclusive) lies completely inside
     * the membership interval.
     *
     * @param periodStart the start of the period to check
     * @param periodEnd   the end of the period to check
     * @return {@code true} if the period is fully covered by the membership,
     *         {@code false} otherwise
     */
    public boolean isValidForPeriod(LocalDate periodStart, LocalDate periodEnd) {
        return (startDate != null && endDate != null
                && !periodStart.isBefore(startDate)
                && !periodEnd.isAfter(endDate));
    }
}

/**
 * Represents an individual membership.
 */
class IndividualMembership extends Membership {
    /** Un‑parameterized constructor required for test frameworks. */
    public IndividualMembership() {
        super();
    }
}

/**
 * Represents an agency membership.
 */
class AgencyMembership extends Membership {
    /** Un‑parameterized constructor required for test frameworks. */
    public AgencyMembership() {
        super();
    }
}

/**
 * Represents an agency affiliate membership.
 */
class AgencyAffiliateMembership extends Membership {
    /** Un‑parameterized constructor required for test frameworks. */
    public AgencyAffiliateMembership() {
        super();
    }
}

/**
 * Represents an artist who can place artworks on auction.
 */
class Artist {

    private String name;
    private String phoneNumber;
    private String artistId;
    private String email;
    private String address;
    private String gender;                     // Could be enum, kept simple as String
    private Membership membership;             // Exactly one membership per artist
    private List<Artwork> artworks = new ArrayList<>();

    /** Un‑parameterized constructor required for test frameworks. */
    public Artist() {
    }

    /** @return the artist's full name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the phone number */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** @param phoneNumber the phone number to set */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** @return the unique artist identifier */
    public String getArtistId() {
        return artistId;
    }

    /** @param artistId the identifier to set */
    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    /** @return the e‑mail address */
    public String getEmail() {
        return email;
    }

    /** @param email the e‑mail address to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return the physical address */
    public String getAddress() {
        return address;
    }

    /** @param address the address to set */
    public void setAddress(String address) {
        this.address = address;
    }

    /** @return the gender of the artist */
    public String getGender() {
        return gender;
    }

    /** @param gender the gender to set */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /** @return the membership associated with this artist */
    public Membership getMembership() {
        return membership;
    }

    /** @param membership the membership to set */
    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    /** @return mutable list of artworks belonging to the artist */
    public List<Artwork> getArtworks() {
        return artworks;
    }

    /** @param artworks the list of artworks to set */
    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    /**
     * Calculates the total price of all artworks owned by this artist.
     *
     * @return the sum of the price of each artwork; {@code 0.0} if the artist has no artworks
     */
    public double calculateTotalArtworkPrice() {
        double total = 0.0;
        for (Artwork a : artworks) {
            total += a.getPrice();
        }
        return total;
    }

    /**
     * Returns the reward points of the artist's membership if the membership
     * is valid for the whole requested period (both dates inclusive).
     *
     * @param periodStart the start date of the period (inclusive) in {@code yyyy-MM-dd} format
     * @param periodEnd   the end date of the period (inclusive) in {@code yyyy-MM-dd} format
     * @return the reward points associated with the membership
     * @throws IllegalArgumentException if the artist does not have a membership,
     *                                  or the membership does not cover the requested period
     */
    public int getRewardPointsWithinPeriod(LocalDate periodStart, LocalDate periodEnd) {
        if (membership == null) {
            throw new IllegalArgumentException("Artist has no membership assigned.");
        }
        if (!membership.isValidForPeriod(periodStart, periodEnd)) {
            throw new IllegalArgumentException("Membership is not valid for the given period.");
        }
        return membership.getRewardPoints();
    }

    /**
     * Counts the artist's artworks per {@link Category}.
     *
     * @return a map where the key is the {@link Category} and the value is the number of artworks
     *         in that category. Categories with zero artworks are omitted from the map.
     */
    public Map<Category, Integer> countArtworksByCategory() {
        Map<Category, Integer> counts = new HashMap<>();
        for (Artwork a : artworks) {
            Category cat = a.getCategory();
            counts.put(cat, counts.getOrDefault(cat, 0) + 1);
        }
        return counts;
    }
}