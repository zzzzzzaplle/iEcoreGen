import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    // Getters and setters
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
     * Adds an artwork to the artist's collection.
     *
     * @param artwork the artwork to add
     */
    public void addArtwork(Artwork artwork) {
        this.artworks.add(artwork);
    }

    /**
     * Calculates the total price of all artworks owned by this artist.
     *
     * @return the sum of prices of all artworks
     */
    public double calculateTotalArtworkPrice() {
        double total = 0;
        for (Artwork artwork : artworks) {
            total += artwork.getPrice();
        }
        return total;
    }

    /**
     * Gets the reward points for this artist's membership within a specific period.
     * The membership must be valid during the entire period (inclusive of boundary dates).
     *
     * @param startDateStr the start date of the period in "yyyy-MM-dd" format
     * @param endDateStr the end date of the period in "yyyy-MM-dd" format
     * @return the reward points if membership is valid during the period, otherwise 0
     * @throws ParseException if the date strings cannot be parsed
     */
    public int getRewardPointsInPeriod(String startDateStr, String endDateStr) throws ParseException {
        if (membership == null) {
            return 0;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse(startDateStr);
        Date endDate = formatter.parse(endDateStr);
        Date membershipStart = formatter.parse(membership.getStartDate());
        Date membershipEnd = formatter.parse(membership.getEndDate());

        // Check if the period is within the membership validity
        if (!startDate.before(membershipStart) && !endDate.after(membershipEnd)) {
            return membership.getRewardPoints();
        }
        return 0;
    }

    /**
     * Counts the number of artworks in each category for this artist.
     *
     * @return an array where index 0 = paintings, 1 = sculptures, 2 = architectures
     */
    public int[] countArtworksByCategory() {
        int[] counts = new int[3]; // painting, sculpture, architecture
        for (Artwork artwork : artworks) {
            switch (artwork.getCategory().toLowerCase()) {
                case "painting":
                    counts[0]++;
                    break;
                case "sculpture":
                    counts[1]++;
                    break;
                case "architecture":
                    counts[2]++;
                    break;
            }
        }
        return counts;
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
    private String type; // individual, agency, agency affiliate

    /**
     * Default constructor for Membership.
     */
    public Membership() {
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

/**
 * Represents an artwork in the online art gallery system.
 */
class Artwork {
    private String title;
    private String description;
    private String category; // painting, sculpture, architecture
    private double price;

    /**
     * Default constructor for Artwork.
     */
    public Artwork() {
    }

    // Getters and setters
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