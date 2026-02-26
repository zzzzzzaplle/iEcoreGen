import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

 enum Gender {
    MALE,
    FEMALE
}

 class Artist {
    private String name;
    private String phoneNumber;
    private String id;
    private String email;
    private String address;
    private Gender gender;
    private List<Artwork> artworks;
    private Membership membership;

    /**
     * Default constructor for Artist.
     */
    public Artist() {
        this.artworks = new ArrayList<>();
    }

    /**
     * Calculates the total price of an artist's artworks.
     *
     * @return The total price of all artworks.
     */
    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (Artwork artwork : artworks) {
            totalPrice += artwork.getPrice();
        }
        return totalPrice;
    }

    /**
     * Calculates the reward points for an artist within a specific period.
     *
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     * @return The reward points if the membership is valid, otherwise 0.
     * @throws IllegalArgumentException If the start date is after the end date.
     */
    public int calculateRewardPoints(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
        if (membership == null) {
            return 0;
        }
        Date membershipStartDate = membership.getStartDate();
        Date membershipEndDate = membership.getEndDate();
        if (startDate.before(membershipStartDate) && endDate.after(membershipEndDate)) {
            return 0;
        }
        return membership.getRewardPoint();
    }

    /**
     * Counts the number of artworks by category for an artist.
     *
     * @return A map with artwork categories as keys and counts as values.
     */
    public Map<ArtworkCategory, Integer> countArtworksByCategory() {
        Map<ArtworkCategory, Integer> categoryCount = new EnumMap<>(ArtworkCategory.class);
        for (ArtworkCategory category : ArtworkCategory.values()) {
            categoryCount.put(category, 0);
        }
        for (Artwork artwork : artworks) {
            ArtworkCategory category = artwork.getCategory();
            categoryCount.put(category, categoryCount.get(category) + 1);
        }
        return categoryCount;
    }

    /**
     * Adds an artwork to the artist's list of artworks.
     *
     * @param artwork The artwork to add.
     */
    public void addArtwork(Artwork artwork) {
        this.artworks.add(artwork);
    }

    // Getters and Setters
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
}

 enum ArtworkCategory {
    PAINTING,
    SCULPTURE,
    ARCHITECTURE
}

 class Artwork {
    private String title;
    private String description;
    private ArtworkCategory category;
    private double price;
    private Artist artist;

    /**
     * Default constructor for Artwork.
     */
    public Artwork() {
    }

    // Getters and Setters
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

 enum MembershipType {
    INDIVIDUAL,
    AGENCY,
    AGENCY_AFFILIATE
}

 class Membership {
    private String ID;
    private Date startDate;
    private Date endDate;
    private int rewardPoint;
    private MembershipType type;

    /**
     * Default constructor for Membership.
     */
    public Membership() {
    }

    // Getters and Setters
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