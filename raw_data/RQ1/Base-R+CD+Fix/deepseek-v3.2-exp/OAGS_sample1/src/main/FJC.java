import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    private LocalDate startDate;
    private LocalDate endDate;
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
     * Adds an artwork to the artist's collection and sets the artist reference in the artwork
     * @param artwork The artwork to be added to the artist's collection
     */
    public void addArtwork(Artwork artwork) {
        if (artwork != null) {
            artwork.setArtist(this);
            this.artworks.add(artwork);
        }
    }
    
    /**
     * Calculates the total price of all artworks belonging to this artist
     * Sums up the prices of all artworks in the artist's collection
     * @return The total price of all artworks as a double value
     */
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Artwork artwork : artworks) {
            total += artwork.getPrice();
        }
        return total;
    }
    
    /**
     * Calculates the reward points for the artist's membership within a specific time period
     * Verifies that the membership is valid during the given interval and returns the reward points
     * @param startDate The start date of the period to check (inclusive)
     * @param endDate The end date of the period to check (inclusive)
     * @return The reward points if membership is valid during the period, 0 otherwise
     * @throws IllegalArgumentException if startDate is after endDate
     */
    public int calculateRewardPoints(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        if (membership == null) {
            return 0;
        }
        
        // Check if membership period overlaps with the given period
        // Membership is valid if it starts on or before endDate and ends on or after startDate
        boolean isValidPeriod = !membership.getStartDate().isAfter(endDate) && 
                               !membership.getEndDate().isBefore(startDate);
        
        return isValidPeriod ? membership.getRewardPoint() : 0;
    }
    
    /**
     * Counts the number of artworks in each category for this artist
     * Creates a map with ArtworkCategory as key and count as value
     * @return A map containing the count of artworks for each category
     */
    public Map<ArtworkCategory, Integer> countArtworksByCategory() {
        Map<ArtworkCategory, Integer> categoryCount = new EnumMap<>(ArtworkCategory.class);
        
        // Initialize all categories with 0 count
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
     * Overloaded method to calculate reward points using string dates in "yyyy-MM-dd" format
     * @param startDateString The start date string in "yyyy-MM-dd" format
     * @param endDateString The end date string in "yyyy-MM-dd" format
     * @return The reward points if membership is valid during the period, 0 otherwise
     * @throws IllegalArgumentException if date strings are in invalid format or start date is after end date
     */
    public int calculateRewardPoints(String startDateString, String endDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDateString, formatter);
        LocalDate endDate = LocalDate.parse(endDateString, formatter);
        return calculateRewardPoints(startDate, endDate);
    }
}