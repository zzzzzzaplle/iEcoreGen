import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Enum representing the gender of an artist.
 */
enum Gender {
    MALE,
    FEMALE
}

/**
 * Enum representing the category of an artwork.
 */
enum ArtworkCategory {
    PAINTING,
    SCULPTURE,
    ARCHITECTURE
}

/**
 * Enum representing the type of membership.
 */
enum MembershipType {
    INDIVIDUAL,
    AGENCY,
    AGENCY_AFFILIATE
}

/**
 * Represents a membership with its details.
 */
class Membership {
    private String ID;
    private Date startDate;
    private Date endDate;
    private int rewardPoint;
    private MembershipType type;

    /**
     * Default constructor for Membership.
     */
    public Membership() {}

    /**
     * Gets the ID of the membership.
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the ID of the membership.
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Gets the start date of the membership.
     * @return the start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the membership.
     * @param startDate the start date to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the membership.
     * @return the end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the membership.
     * @param endDate the end date to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the reward points of the membership.
     * @return the reward points
     */
    public int getRewardPoint() {
        return rewardPoint;
    }

    /**
     * Sets the reward points of the membership.
     * @param rewardPoint the reward points to set
     */
    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
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
     * Checks if the membership is valid within a specific date range.
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return true if the membership is valid within the range, false otherwise
     */
    public boolean isValid(Date startDate, Date endDate) {
        return !this.startDate.after(endDate) && !this.endDate.before(startDate);
    }
}

/**
 * Represents an artwork with its details.
 */
class Artwork {
    private String title;
    private String description;
    private ArtworkCategory category;
    private double price;
    private Artist artist;

    /**
     * Default constructor for Artwork.
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
    public ArtworkCategory getCategory() {
        return category;
    }

    /**
     * Sets the category of the artwork.
     * @param category the category to set
     */
    public void setCategory(ArtworkCategory category) {
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

    /**
     * Gets the artist of the artwork.
     * @return the artist
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Sets the artist of the artwork.
     * @param artist the artist to set
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}

/**
 * Represents an artist with their details and artworks.
 */
class Artist {
    private String name;
    private String phoneNumber;
    private String id;
    private String email;
    private String address;
    private Gender gender;
    private List<Artwork> artworks;
    private Membership membership;

    /**
     * Default constructor for Artist.
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
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the gender of the artist.
     * @param gender the gender to set
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Gets the list of artworks by the artist.
     * @return the list of artworks
     */
    public List<Artwork> getArtworks() {
        return artworks;
    }

    /**
     * Sets the list of artworks by the artist.
     * @param artworks the list of artworks to set
     */
    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
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
     * Calculates the total price of the artist's artworks.
     * @return the total price
     */
    public double calculateTotalPrice() {
        return artworks.stream().mapToDouble(Artwork::getPrice).sum();
    }

    /**
     * Calculates the reward points within a specific period.
     * @param startDate the start date of the period
     * @param endDate the end date of the period
     * @return the reward points if the membership is valid, -1 otherwise
     */
    public int calculateRewardPoints(Date startDate, Date endDate) {
        if (membership != null && membership.isValid(startDate, endDate)) {
            return membership.getRewardPoint();
        }
        return -1; // or throw an exception based on the requirement
    }

    /**
     * Counts the number of artworks by category.
     * @return a map of artwork categories to their respective counts
     */
    public Map<ArtworkCategory, Integer> countArtworksByCategory() {
        Map<ArtworkCategory, Integer> countMap = new HashMap<>();
        for (Artwork artwork : artworks) {
            countMap.put(artwork.getCategory(), countMap.getOrDefault(artwork.getCategory(), 0) + 1);
        }
        return countMap;
    }

    /**
     * Adds an artwork to the artist's list of artworks.
     * @param artwork the artwork to add
     */
    public void addArtwork(Artwork artwork) {
        this.artworks.add(artwork);
        artwork.setArtist(this);
    }
}

 class Main {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setId("A123");

        Membership membership = new Membership();
        membership.setID("M123");
        membership.setStartDate(dateFormat.parse("2022-01-01"));
        membership.setEndDate(dateFormat.parse("2023-12-31"));
        membership.setRewardPoint(100);
        artist.setMembership(membership);

        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Artwork 1");
        artwork1.setPrice(1000.0);
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artist.addArtwork(artwork1);

        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Artwork 2");
        artwork2.setPrice(2000.0);
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artist.addArtwork(artwork2);

        System.out.println("Total Price: " + artist.calculateTotalPrice());
        System.out.println("Reward Points: " + artist.calculateRewardPoints(dateFormat.parse("2022-06-01"), dateFormat.parse("2022-12-31")));
        System.out.println("Artworks by Category: " + artist.countArtworksByCategory());
    }
}