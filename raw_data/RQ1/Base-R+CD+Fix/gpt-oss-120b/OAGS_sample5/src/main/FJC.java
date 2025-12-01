import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.EnumMap;

/**
 * Enumeration of possible genders for an artist.
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
 * Represents a membership assigned to an artist.
 */
class Membership {
    private String ID;
    private Date startDate;
    private Date endDate;
    private int rewardPoint;
    private MembershipType type;

    /** Default constructor */
    public Membership() {
        // Empty constructor for frameworks / testing
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
    private String title;
    private String description;
    private ArtworkCategory category;
    private double price;
    private Artist artist; // back‑reference to the author

    /** Default constructor */
    public Artwork() {
        // Empty constructor for frameworks / testing
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
 * Represents an artist who can hold artworks and a membership.
 */
class Artist {
    private String name;
    private String phoneNumber;
    private String id;
    private String email;
    private String address;
    private Gender gender;
    private List<Artwork> artworks;
    private Membership membership;

    /** Default constructor */
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
     * Adds an artwork to this artist's collection and sets the artwork's author reference.
     *
     * @param artwork the artwork to be added
     */
    public void addArtwork(Artwork artwork) {
        if (artwork != null) {
            artwork.setArtist(this);
            this.artworks.add(artwork);
        }
    }

    /**
     * Calculates the total price of all artworks owned by this artist.
     *
     * @return the sum of the prices of all artworks; 0.0 if the artist has no artworks
     */
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Artwork a : artworks) {
            total += a.getPrice();
        }
        return total;
    }

    /**
     * Retrieves the reward points of the artist's membership for a given period.
     * The method verifies that the membership is active for the entire interval
     * (including the start and end dates). If the membership is not valid for the
     * specified period, the method returns 0.
     *
     * @param startDate the beginning of the period (inclusive)
     * @param endDate   the end of the period (inclusive)
     * @return the reward points if the membership covers the whole period; otherwise 0
     * @throws IllegalArgumentException if startDate or endDate is null,
     *                                  or if startDate occurs after endDate
     */
    public int calculateRewardPoints(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        if (membership == null) {
            return 0;
        }
        Date memStart = membership.getStartDate();
        Date memEnd = membership.getEndDate();
        if (memStart == null || memEnd == null) {
            return 0;
        }
        // inclusive check
        boolean coversPeriod = !memStart.after(startDate) && !memEnd.before(endDate);
        return coversPeriod ? membership.getRewardPoint() : 0;
    }

    /**
     * Counts the artist's artworks per category.
     *
     * @return a map where each key is an {@link ArtworkCategory} and the value
     *         is the number of artworks the artist has in that category.
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
}