import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     * Calculates the total price of an artist's artworks.
     * @return The sum of prices of all artworks by the artist.
     */
    public double calculateTotalPrice() {
        return artworks.stream().mapToDouble(Artwork::getPrice).sum();
    }

    /**
     * Calculates the reward points for the artist within a specific period.
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     * @return The reward points if the membership is valid during the period, otherwise 0.
     * @throws ParseException If the date format is invalid.
     */
    public int calculateRewardPoints(String startDate, String endDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date periodStart = dateFormat.parse(startDate);
        Date periodEnd = dateFormat.parse(endDate);
        Date membershipStart = dateFormat.parse(membership.getStartDate());
        Date membershipEnd = dateFormat.parse(membership.getEndDate());

        if (periodStart.before(membershipEnd) && periodEnd.after(membershipStart)) {
            return membership.getRewardPoint();
        }
        return 0;
    }

    /**
     * Counts the number of artworks by category for the artist.
     * @return A map with artwork categories as keys and counts as values.
     */
    public Map<ArtworkCategory, Integer> countArtworksByCategory() {
        Map<ArtworkCategory, Integer> categoryCount = new EnumMap<>(ArtworkCategory.class);
        for (ArtworkCategory category : ArtworkCategory.values()) {
            categoryCount.put(category, 0);
        }

        for (Artwork artwork : artworks) {
            categoryCount.put(artwork.getCategory(), categoryCount.get(artwork.getCategory()) + 1);
        }

        return categoryCount;
    }

    /**
     * Adds an artwork to the artist's list of artworks.
     * @param artwork The artwork to add.
     */
    public void addArtwork(Artwork artwork) {
        this.artworks.add(artwork);
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
    private String startDate;
    private String endDate;
    private int rewardPoint;
    private MembershipType type;

    public Membership() {
    }

    // Getters and Setters
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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