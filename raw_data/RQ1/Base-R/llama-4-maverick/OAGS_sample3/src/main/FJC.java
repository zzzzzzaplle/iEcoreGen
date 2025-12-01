import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Enum for the category of artwork.
 */
enum Category {
    PAINTING,
    SCULPTURE,
    ARCHITECTURE
}

/**
 * Enum for the type of membership.
 */
enum MembershipType {
    INDIVIDUAL,
    AGENCY,
    AGENCY_AFFILIATE
}

/**
 * Represents a membership.
 */
class Membership {
    private String id;
    private String startDate;
    private String endDate;
    private int rewardPoints;
    private MembershipType type;

    /**
     * Unparameterized constructor for Membership.
     */
    public Membership() {}

    /**
     * Gets the ID of the membership.
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the membership.
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the start date of the membership.
     * @return the start date
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the membership.
     * @param startDate the start date to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the membership.
     * @return the end date
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the membership.
     * @param endDate the end date to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the reward points of the membership.
     * @return the reward points
     */
    public int getRewardPoints() {
        return rewardPoints;
    }

    /**
     * Sets the reward points of the membership.
     * @param rewardPoints the reward points to set
     */
    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    /**
     * Gets the type of the membership.
     * @return the type
     */
    public MembershipType getType() {
        return type;
    }

    /**
     * Sets the type of the membership.
     * @param type the type to set
     */
    public void setType(MembershipType type) {
        this.type = type;
    }

    /**
     * Checks if the membership is valid within a specific period.
     * @param startDate the start date of the period
     * @param endDate the end date of the period
     * @return true if the membership is valid, false otherwise
     */
    public boolean isValid(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate membershipStart = LocalDate.parse(this.startDate, formatter);
        LocalDate membershipEnd = LocalDate.parse(this.endDate, formatter);
        LocalDate periodStart = LocalDate.parse(startDate, formatter);
        LocalDate periodEnd = LocalDate.parse(endDate, formatter);

        return !periodStart.isBefore(membershipStart) && !periodEnd.isAfter(membershipEnd);
    }
}

/**
 * Represents an artwork.
 */
class Artwork {
    private String title;
    private String description;
    private Category category;
    private double price;

    /**
     * Unparameterized constructor for Artwork.
     */
    public Artwork() {}

    /**
     * Gets the title of the artwork.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the artwork.
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the artwork.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the artwork.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the category of the artwork.
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the category of the artwork.
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Gets the price of the artwork.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the artwork.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
}

/**
 * Represents an artist.
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
     * Unparameterized constructor for Artist.
     */
    public Artist() {
        this.artworks = new ArrayList<>();
    }

    /**
     * Gets the name of the artist.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the artist.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the phone number of the artist.
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the artist.
     * @param phoneNumber the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the ID of the artist.
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the artist.
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the email of the artist.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the artist.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the address of the artist.
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the artist.
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the gender of the artist.
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the artist.
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the membership of the artist.
     * @return the membership
     */
    public Membership getMembership() {
        return membership;
    }

    /**
     * Sets the membership of the artist.
     * @param membership the membership to set
     */
    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    /**
     * Gets the artworks of the artist.
     * @return the artworks
     */
    public List<Artwork> getArtworks() {
        return artworks;
    }

    /**
     * Sets the artworks of the artist.
     * @param artworks the artworks to set
     */
    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    /**
     * Adds an artwork to the artist's artworks.
     * @param artwork the artwork to add
     */
    public void addArtwork(Artwork artwork) {
        this.artworks.add(artwork);
    }

    /**
     * Calculates the total price of the artist's artworks.
     * @return the total price
     */
    public double calculateTotalPrice() {
        return artworks.stream().mapToDouble(Artwork::getPrice).sum();
    }

    /**
     * Gets the reward points within a specific period.
     * @param startDate the start date of the period
     * @param endDate the end date of the period
     * @return the reward points if the membership is valid, -1 otherwise
     */
    public int getRewardPoints(String startDate, String endDate) {
        if (membership.isValid(startDate, endDate)) {
            return membership.getRewardPoints();
        } else {
            return -1;
        }
    }

    /**
     * Counts the artworks by category.
     * @return a string representation of the count of artworks in each category
     */
    public String countArtworksByCategory() {
        int paintingCount = 0;
        int sculptureCount = 0;
        int architectureCount = 0;

        for (Artwork artwork : artworks) {
            switch (artwork.getCategory()) {
                case PAINTING:
                    paintingCount++;
                    break;
                case SCULPTURE:
                    sculptureCount++;
                    break;
                case ARCHITECTURE:
                    architectureCount++;
                    break;
            }
        }

        return "Painting: " + paintingCount + ", Sculpture: " + sculptureCount + ", Architecture: " + architectureCount;
    }
}