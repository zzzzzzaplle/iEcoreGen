import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an artist in the online art gallery system.
 */
class Artist {
    private String name;
    private String phoneNumber;
    private String id;
    private String email;
    private String address;
    private String gender;
    private Membership membership;
    private List<Artwork> artworks;

    /**
     * Default constructor for Artist.
     */
    public Artist() {
        this.artworks = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public Membership getMembership() { return membership; }
    public void setMembership(Membership membership) { this.membership = membership; }
    public List<Artwork> getArtworks() { return artworks; }
    public void setArtworks(List<Artwork> artworks) { this.artworks = artworks; }

    /**
     * Calculates the total price of the artist's artworks.
     * 
     * @return The total price of the artist's artworks.
     */
    public double calculateTotalPrice() {
        return artworks.stream().mapToDouble(Artwork::getPrice).sum();
    }

    /**
     * Counts the number of artworks by category for the artist.
     * 
     * @return A string representation of the count of artworks by category.
     */
    public String countArtworksByCategory() {
        int paintingCount = 0, sculptureCount = 0, architectureCount = 0;
        for (Artwork artwork : artworks) {
            switch (artwork.getCategory().toLowerCase()) {
                case "painting":
                    paintingCount++;
                    break;
                case "sculpture":
                    sculptureCount++;
                    break;
                case "architecture":
                    architectureCount++;
                    break;
            }
        }
        return "Painting: " + paintingCount + ", Sculpture: " + sculptureCount + ", Architecture: " + architectureCount;
    }
}

/**
 * Represents a membership in the online art gallery system.
 */
class Membership {
    private String id;
    private String startDate;
    private String endDate;
    private int rewardPoints;

    /**
     * Default constructor for Membership.
     */
    public Membership() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public int getRewardPoints() { return rewardPoints; }
    public void setRewardPoints(int rewardPoints) { this.rewardPoints = rewardPoints; }

    /**
     * Checks if the membership is valid within a given date range and returns the reward points.
     * 
     * @param startDate The start date of the period to check.
     * @param endDate   The end date of the period to check.
     * @return The reward points if the membership is valid during the given period.
     * @throws Exception If the membership is not valid during the given period.
     */
    public int getRewardPointsWithinPeriod(String startDate, String endDate) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate membershipStart = LocalDate.parse(this.startDate, formatter);
        LocalDate membershipEnd = LocalDate.parse(this.endDate, formatter);
        LocalDate periodStart = LocalDate.parse(startDate, formatter);
        LocalDate periodEnd = LocalDate.parse(endDate, formatter);

        if (!((periodStart.isAfter(membershipStart) || periodStart.isEqual(membershipStart)) 
                && (periodEnd.isBefore(membershipEnd) || periodEnd.isEqual(membershipEnd)))) {
            throw new Exception("Membership is not valid during the given period.");
        }
        return this.rewardPoints;
    }
}

/**
 * Represents an artwork in the online art gallery system.
 */
class Artwork {
    private String title;
    private String description;
    private String category;
    private double price;

    /**
     * Default constructor for Artwork.
     */
    public Artwork() {}

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}