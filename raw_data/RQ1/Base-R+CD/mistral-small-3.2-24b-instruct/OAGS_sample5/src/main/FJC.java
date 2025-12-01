import java.util.Date;
import java.util.List;
import java.util.ArrayList;
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
        this.artworks = new ArrayList<>();
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
     * Calculates the total price of all artworks by the artist.
     * @return The total price of all artworks.
     */
    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Artwork artwork : artworks) {
            totalPrice += artwork.getPrice();
        }
        return totalPrice;
    }

    /**
     * Calculates the reward points for the artist within a specific period.
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     * @return The reward points within the specified period.
     * @throws IllegalArgumentException If the membership is not valid during the specified period.
     */
    public int calculateRewardPoints(Date startDate, Date endDate) {
        if (membership == null) {
            throw new IllegalArgumentException("Artist has no membership.");
        }
        Date membershipStartDate = membership.getStartDate();
        Date membershipEndDate = membership.getEndDate();

        if (startDate.before(membershipStartDate) || endDate.after(membershipEndDate)) {
            throw new IllegalArgumentException("Membership is not valid during the specified period.");
        }

        return membership.getRewardPoint();
    }

    /**
     * Counts the number of artworks by category.
     * @return A map of artwork categories to their respective counts.
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

    /**
     * Adds an artwork to the artist's collection.
     * @param artwork The artwork to add.
     */
    public void addArtwork(Artwork artwork) {
        this.artworks.add(artwork);
        artwork.setArtist(this);
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