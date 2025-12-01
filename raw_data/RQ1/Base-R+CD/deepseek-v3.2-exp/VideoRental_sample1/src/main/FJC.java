import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.stream.Collectors;

/**
 * Represents a customer in the video rental system
 */
class Customer {
    private String id;
    private List<VideoRental> rentals;
    
    /**
     * Default constructor
     */
    public Customer() {
        this.rentals = new ArrayList<>();
    }
    
    /**
     * Adds a video tape rental for this customer after verifying constraints
     * @param tape The tape to be rented
     * @param currentDate The current date for availability checking
     * @return true if rental is successfully added, false otherwise
     */
    public boolean addVideoTapeRental(Tape tape, Date currentDate) {
        // Check if customer has less than 20 rentals
        if (rentals.size() >= 20) {
            return false;
        }
        
        // Check if customer has any past due amount
        if (calculateTotalPastDueAmount(currentDate) > 0) {
            return false;
        }
        
        // Check if tape is available
        if (!tape.isAvailable(currentDate)) {
            return false;
        }
        
        // Create new rental and add to customer's rentals
        VideoRental newRental = new VideoRental();
        newRental.setTape(tape);
        // Set due date to 7 days from current date (example rental period)
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        newRental.setDueDate(cal.getTime());
        
        rentals.add(newRental);
        return true;
    }
    
    /**
     * Retrieves all tapes rented by this customer that have not been returned
     * @return List of unreturned tapes, empty list if none
     */
    public List<Tape> getUnreturnedTapes() {
        return rentals.stream()
                     .filter(rental -> rental.getReturnDate() == null)
                     .map(VideoRental::getTape)
                     .collect(Collectors.toList());
    }
    
    /**
     * Calculates the total past due amount for all rentals of this customer
     * @param currentDate The current date for calculating overdue fees
     * @return Total past due amount rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(Date currentDate) {
        return rentals.stream()
                     .mapToDouble(rental -> rental.calculateOwedPastDueAmount(currentDate))
                     .sum();
    }
    
    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public List<VideoRental> getRentals() { return rentals; }
    public void setRentals(List<VideoRental> rentals) { this.rentals = rentals; }
}

/**
 * Represents a video tape in the inventory
 */
class Tape {
    private String id;
    private String videoInformation;
    private List<VideoRental> rentals; // Active rentals for this tape
    
    /**
     * Default constructor
     */
    public Tape() {
        this.rentals = new ArrayList<>();
    }
    
    /**
     * Checks if this tape is available for rental on the given date
     * @param currentDate The date to check availability for
     * @return true if tape is available, false otherwise
     */
    public boolean isAvailable(Date currentDate) {
        // Tape is unavailable if it has any active rental with null return date
        return rentals.stream()
                     .noneMatch(rental -> rental.getReturnDate() == null);
    }
    
    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getVideoInformation() { return videoInformation; }
    public void setVideoInformation(String videoInformation) { this.videoInformation = videoInformation; }
    public List<VideoRental> getRentals() { return rentals; }
    public void setRentals(List<VideoRental> rentals) { this.rentals = rentals; }
}

/**
 * Represents a video rental transaction for a single tape
 */
class VideoRental {
    private Date dueDate;
    private Date returnDate;
    private double ownedPastDueAmount;
    private Tape tape;
    
    /**
     * Default constructor
     */
    public VideoRental() {
        this.ownedPastDueAmount = 0.0;
    }
    
    /**
     * Calculates the overdue amount for this rental
     * @param currentDate The current date for calculation
     * @return Overdue fee rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(Date currentDate) {
        Date effectiveReturnDate = (returnDate != null) ? returnDate : currentDate;
        
        if (dueDate == null || effectiveReturnDate.before(dueDate) || effectiveReturnDate.equals(dueDate)) {
            return 0.0;
        }
        
        long diffInMillis = effectiveReturnDate.getTime() - dueDate.getTime();
        long overdueDays = (long) Math.ceil(diffInMillis / (1000.0 * 60 * 60 * 24));
        
        double overdueFee = overdueDays * 0.5;
        return Math.round(overdueFee * 100.0) / 100.0;
    }
    
    /**
     * Calculates the base rental fee for this rental
     * @param currentDate The current date for calculation
     * @return Base rental fee rounded to two decimal places
     */
    public double calculateBaseRentalFee(Date currentDate) {
        if (dueDate == null) {
            return 0.0;
        }
        
        Date effectiveReturnDate = (returnDate != null) ? returnDate : currentDate;
        long diffInMillis = effectiveReturnDate.getTime() - dueDate.getTime() + (7 * 24 * 60 * 60 * 1000); // Add 7 days rental period
        long rentalDays = (long) Math.ceil(diffInMillis / (1000.0 * 60 * 60 * 24));
        
        double baseFee = rentalDays * 1.0;
        return Math.round(baseFee * 100.0) / 100.0;
    }
    
    // Getters and setters
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public double getOwnedPastDueAmount() { return ownedPastDueAmount; }
    public void setOwnedPastDueAmount(double ownedPastDueAmount) { this.ownedPastDueAmount = ownedPastDueAmount; }
    public Tape getTape() { return tape; }
    public void setTape(Tape tape) { this.tape = tape; }
}

/**
 * Represents a complete rental transaction containing multiple video rentals
 */
class RentalTransaction {
    private Date rentalDate;
    private double totalPrice;
    private List<VideoRental> rentals;
    private Customer customer;
    
    /**
     * Default constructor
     */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }
    
    /**
     * Calculates the total price for this rental transaction
     * @param rentalDate The date when the rental started
     * @param currentDate The current date for calculation
     * @return Total price rounded to two decimal places
     */
    public double calculateTotalPrice(Date rentalDate, Date currentDate) {
        double total = rentals.stream()
                             .mapToDouble(rental -> 
                                 rental.calculateBaseRentalFee(currentDate) + 
                                 rental.calculateOwedPastDueAmount(currentDate))
                             .sum();
        
        this.totalPrice = Math.round(total * 100.0) / 100.0;
        return this.totalPrice;
    }
    
    // Getters and setters
    public Date getRentalDate() { return rentalDate; }
    public void setRentalDate(Date rentalDate) { this.rentalDate = rentalDate; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public List<VideoRental> getRentals() { return rentals; }
    public void setRentals(List<VideoRental> rentals) { this.rentals = rentals; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
}

/**
 * Utility class for date parsing and formatting
 */
class DateUtils {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Parses a date string into a Date object
     * @param dateString The date string in "yyyy-MM-dd HH:mm:ss" format
     * @return Parsed Date object
     * @throws ParseException If the date string cannot be parsed
     */
    public static Date parseDate(String dateString) throws ParseException {
        return DATE_FORMAT.parse(dateString);
    }
    
    /**
     * Formats a Date object into a string
     * @param date The Date object to format
     * @return Formatted date string
     */
    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }
}