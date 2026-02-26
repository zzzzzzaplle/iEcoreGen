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
     * @return The sum of the prices of all artworks.
     */
    public double calculateTotalArtworkPrice() {
        double totalPrice = 0.0;
        for (Artwork artwork : artworks) {
            totalPrice += artwork.getPrice();
        }
        return totalPrice;
    }

    /**
     * Counts the number of artworks by category.
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
     * @return true if the membership is valid within the period, false otherwise.
     */
    public boolean isValidWithinPeriod(LocalDate startDate, LocalDate endDate) {
        return !this.startDate.isAfter(endDate) && !this.endDate.isBefore(startDate);
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

class OnlineArtGallerySystem {
    /**
     * Gets the reward points of an artist's membership within a specific period.
     * @param artist The artist whose membership reward points are to be checked.
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     * @return The reward points if the membership is valid within the period, -1 otherwise.
     */
    public static int getRewardPointsWithinPeriod(Artist artist, LocalDate startDate, LocalDate endDate) {
        if (artist.getMembership() == null) {
            return -1;
        }
        if (artist.getMembership().isValidWithinPeriod(startDate, endDate)) {
            return artist.getMembership().getRewardPoints();
        } else {
            return -1;
        }
    }
}