import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
    
    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public void setEndDate(String endDate) {
        this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
    
    /**
     * Checks if the membership is valid during a specific time period
     * @param startDate the start date of the period to check (inclusive)
     * @param endDate the end date of the period to check (inclusive)
     * @return true if the membership is valid during the specified period, false otherwise
     */
    public boolean isValidDuringPeriod(LocalDate startDate, LocalDate endDate) {
        return !startDate.isAfter(this.endDate) && !endDate.isBefore(this.startDate);
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
    
    public void addArtwork(Artwork artwork) {
        if (artwork != null) {
            artwork.setArtist(this);
            artworks.add(artwork);
        }
    }
    
    /**
     * Calculates the total price of all artworks belonging to this artist
     * @return the sum of prices of all artworks owned by the artist
     */
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Artwork artwork : artworks) {
            total += artwork.getPrice();
        }
        return total;
    }
    
    /**
     * Gets reward points for the artist within a specific time period if the membership is valid
     * @param startDate the start date of the period (inclusive) in "yyyy-MM-dd" format
     * @param endDate the end date of the period (inclusive) in "yyyy-MM-dd" format
     * @return the reward points if membership is valid during the period, 0 otherwise
     * @throws IllegalArgumentException if the date format is invalid or if startDate is after endDate
     */
    public int calculateRewardPoints(String startDate, String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            
            if (start.isAfter(end)) {
                throw new IllegalArgumentException("Start date cannot be after end date");
            }
            
            return calculateRewardPoints(start, end);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd", e);
        }
    }
    
    /**
     * Gets reward points for the artist within a specific time period if the membership is valid
     * @param startDate the start date of the period (inclusive)
     * @param endDate the end date of the period (inclusive)
     * @return the reward points if membership is valid during the period, 0 otherwise
     * @throws IllegalArgumentException if startDate is after endDate
     */
    public int calculateRewardPoints(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        if (membership != null && membership.isValidDuringPeriod(startDate, endDate)) {
            return membership.getRewardPoint();
        }
        return 0;
    }
    
    /**
     * Counts the number of artworks for each category owned by the artist
     * @return a map containing the count of artworks for each ArtworkCategory
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
}