import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Artist(String name, String phoneNumber, String id, String email, String address, String gender) {
        this();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.email = email;
        this.address = address;
        this.gender = gender;
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
     * Adds an artwork to the artist's list of artworks.
     * @param artwork The artwork to be added.
     */
    public void addArtwork(Artwork artwork) {
        this.artworks.add(artwork);
    }

    /**
     * Calculates the total price of the artist's artworks.
     * @return The total price of all artworks.
     */
    public double calculateTotalArtworkPrice() {
        return artworks.stream().mapToDouble(Artwork::getPrice).sum();
    }

    /**
     * Counts the number of artworks by category for the artist.
     * @return A map with categories as keys and counts as values.
     */
    public Map<String, Integer> countArtworksByCategory() {
        Map<String, Integer> categoryCounts = new HashMap<>();
        for (Artwork artwork : artworks) {
            String category = artwork.getCategory();
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
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
    }

    public Membership(String id, String startDate, String endDate, int rewardPoints, MembershipType type) {
        this();
        this.id = id;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = LocalDate.parse(startDate, formatter);
        this.endDate = LocalDate.parse(endDate, formatter);
        this.rewardPoints = rewardPoints;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = LocalDate.parse(startDate, formatter);
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.endDate = LocalDate.parse(endDate, formatter);
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
     * Checks if the membership is valid within the specified period.
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     * @return true if the membership is valid within the period, false otherwise.
     */
    public boolean isValidWithinPeriod(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate periodStart = LocalDate.parse(startDate, formatter);
        LocalDate periodEnd = LocalDate.parse(endDate, formatter);
        return !this.startDate.isAfter(periodEnd) && !this.endDate.isBefore(periodStart);
    }

    /**
     * Gets the reward points of the membership if it is valid within the specified period.
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     * @return The reward points if the membership is valid, -1 otherwise.
     */
    public int getRewardPointsWithinPeriod(String startDate, String endDate) {
        if (isValidWithinPeriod(startDate, endDate)) {
            return this.rewardPoints;
        }
        return -1;
    }
}

enum MembershipType {
    INDIVIDUAL,
    AGENCY,
    AGENCY_AFFILIATE
}

class Artwork {
    private String title;
    private String description;
    private String category;
    private double price;

    public Artwork() {
    }

    public Artwork(String title, String description, String category, double price) {
        this();
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}