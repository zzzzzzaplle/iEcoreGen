import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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

    public Artist() {
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
     * Calculates the total price of an artist's artworks.
     *
     * @return The sum of the prices of all the artist's artworks.
     */
    public double calculateTotalPrice() {
        return artworks.stream().mapToDouble(Artwork::getPrice).sum();
    }

    /**
     * Calculates the reward points for an artist within a specific period.
     *
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     * @return The reward points if the membership is valid during the period, otherwise 0.
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
        if (startDate.before(membershipEndDate) && endDate.after(membershipStartDate)) {
            return membership.getRewardPoint();
        }
        return 0;
    }

    /**
     * Counts the number of an artist's artworks in each category.
     *
     * @return A map with artwork categories as keys and counts as values.
     */
    public Map<ArtworkCategory, Integer> countArtworksByCategory() {
        Map<ArtworkCategory, Integer> categoryCounts = new HashMap<>();
        for (ArtworkCategory category : ArtworkCategory.values()) {
            categoryCounts.put(category, 0);
        }
        for (Artwork artwork : artworks) {
            ArtworkCategory category = artwork.getCategory();
            categoryCounts.put(category, categoryCounts.get(category) + 1);
        }
        return categoryCounts;
    }

    public void addArtwork(Artwork artwork) {
        if (artwork != null) {
            artworks.add(artwork);
            artwork.setArtist(this);
        }
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

    public Artwork() {
    }

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

    public Membership() {
    }

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