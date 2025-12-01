import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

enum Gender {
    MALE,
    FEMALE
}

enum ArtworkCategory {
    PAINTING,
    SCULPTURE,
    ARCHITECTURE
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
        artworks = new ArrayList<>();
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
     * Calculates the total price of all artworks belonging to this artist
     * @return The sum of prices of all artworks by this artist
     */
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Artwork artwork : artworks) {
            total += artwork.getPrice();
        }
        return total;
    }
    
    /**
     * Verifies if the artist's membership is valid during the specified time period
     * and returns the reward points if valid
     * @param startDate The start date of the period (inclusive)
     * @param endDate The end date of the period (inclusive)
     * @return The reward points if membership is valid during the period, 0 otherwise
     * @throws IllegalArgumentException if startDate is after endDate
     */
    public int calculateRewardPoints(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        if (membership == null) {
            return 0;
        }
        
        // Check if membership is valid during the specified period
        // Membership is valid if it overlaps with the given period (inclusive of boundaries)
        boolean isValid = !(startDate.after(membership.getEndDate()) || endDate.before(membership.getStartDate()));
        
        return isValid ? membership.getRewardPoint() : 0;
    }
    
    /**
     * Counts the number of artworks by this artist in each category
     * @return A map containing the count of artworks for each category
     */
    public Map<ArtworkCategory, Integer> countArtworksByCategory() {
        Map<ArtworkCategory, Integer> categoryCount = new HashMap<>();
        
        // Initialize all categories with zero count
        for (ArtworkCategory category : ArtworkCategory.values()) {
            categoryCount.put(category, 0);
        }
        
        // Count artworks by category
        for (Artwork artwork : artworks) {
            ArtworkCategory category = artwork.getCategory();
            categoryCount.put(category, categoryCount.get(category) + 1);
        }
        
        return categoryCount;
    }
    
    /**
     * Adds an artwork to the artist's collection and sets the artist reference in the artwork
     * @param artwork The artwork to be added to the artist's collection
     * @throws IllegalArgumentException if artwork is null
     */
    public void addArtwork(Artwork artwork) {
        if (artwork == null) {
            throw new IllegalArgumentException("Artwork cannot be null");
        }
        artworks.add(artwork);
        artwork.setArtist(this);
    }
}