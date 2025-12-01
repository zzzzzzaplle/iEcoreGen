import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum MembershipType {
    INDIVIDUAL,
    AGENCY,
    AGENCY_AFFILIATE
}

enum ArtworkCategory {
    PAINTING,
    SCULPTURE,
    ARCHITECTURE
}

class Artist {
    private String name;
    private String phoneNumber;
    private String id;
    private String email;
    private String address;
    private String gender;
    private Membership membership;
    private List<Artwork> artworks;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    /**
     * Calculates the total price of an artist's artworks.
     * @return The total price of all artworks.
     */
    public double calculateTotalArtworkPrice() {
        double totalPrice = 0.0;
        for (Artwork artwork : artworks) {
            totalPrice += artwork.getPrice();
        }
        return totalPrice;
    }

    /**
     * Counts the number of artworks in each category.
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
}

class Membership {
    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int rewardPoints;
    private MembershipType type;

    public Membership() {
        this.rewardPoints = 0;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public MembershipType getType() {
        return type;
    }

    public void setType(MembershipType type) {
        this.type = type;
    }

    /**
     * Checks if the membership is valid within a specific period.
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     * @return True if the membership is valid within the period, false otherwise.
     */
    public boolean isValidWithinPeriod(LocalDate startDate, LocalDate endDate) {
        return !this.startDate.isAfter(endDate) && !this.endDate.isBefore(startDate);
    }
}

class Artwork {
    private String title;
    private String description;
    private ArtworkCategory category;
    private double price;

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
}

class OnlineArtGallerySystem {
    public static void main(String[] args) {
        // Example usage
        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setId("A123");
        artist.setEmail("john.doe@example.com");
        artist.setPhoneNumber("1234567890");
        artist.setAddress("123 Art Street");
        artist.setGender("Male");

        Membership membership = new Membership();
        membership.setId("M123");
        membership.setStartDate(LocalDate.parse("2023-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        membership.setEndDate(LocalDate.parse("2023-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        membership.setRewardPoints(100);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);

        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Painting 1");
        artwork1.setDescription("A beautiful painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(1000.0);

        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Sculpture 1");
        artwork2.setDescription("A stunning sculpture");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(1500.0);

        artist.getArtworks().add(artwork1);
        artist.getArtworks().add(artwork2);

        System.out.println("Total Artwork Price: " + artist.calculateTotalArtworkPrice());
        System.out.println("Artworks by Category: " + artist.countArtworksByCategory());
    }
}