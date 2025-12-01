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
 * Represents a membership that an artist can hold.
 */
class Membership {

    private String ID;
    private Date startDate;
    private Date endDate;
    private int rewardPoint;
    private MembershipType type;

    /** Default constructor */
    public Membership() {
        // empty
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

    private String title;
    private String description;
    private ArtworkCategory category;
    private double price;
    private Artist artist;

    /** Default constructor */
    public Artwork() {
        // empty
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
 * Represents an artist participating in the online art gallery.
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
     * Adds a new artwork to the artist's collection.
     *
     * @param artwork the artwork to add
     */
    public void addArtwork(Artwork artwork) {
        if (artwork != null) {
            artwork.setArtist(this);
            this.artworks.add(artwork);
        }
    }

    /**
     * Calculates the total price of all artworks belonging to this artist.
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
     * Returns the reward points of the artist's membership if the membership is
     * valid for the whole given period (inclusive). If the membership does not
     * cover the period, the method returns 0.
     *
     * @param startDate the start date of the requested period (inclusive)
     * @param endDate   the end date of the requested period (inclusive)
     * @return reward points for the period, or 0 if the membership is not valid
     */
    public int calculateRewardPoints(Date startDate, Date endDate) {
        if (membership == null) {
            return 0;
        }
        Date memStart = membership.getStartDate();
        Date memEnd = membership.getEndDate();

        // Validate that the whole interval [startDate, endDate] lies within the membership period.
        if (memStart != null && memEnd != null &&
            !startDate.before(memStart) && !endDate.after(memEnd)) {
            return membership.getRewardPoint();
        }
        return 0;
    }

    /**
     * Counts the artist's artworks by their category.
     *
     * @return a map where each key is an {@link ArtworkCategory} and the value is the number of artworks in that category
     */
    public Map<ArtworkCategory, Integer> countArtworksByCategory() {
        Map<ArtworkCategory, Integer> counts = new EnumMap<>(ArtworkCategory.class);
        // Initialize counts to zero for all categories
        for (ArtworkCategory cat : ArtworkCategory.values()) {
            counts.put(cat, 0);
        }

        for (Artwork a : artworks) {
            ArtworkCategory cat = a.getCategory();
            if (cat != null) {
                counts.put(cat, counts.get(cat) + 1);
            }
        }
        return counts;
    }
}