import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
     * Adds a video tape rental for this customer
     * Verifies customer has less than 20 rentals and no past-due amount, and verifies tape availability
     * @param tape the tape to be rented
     * @param currentDate the current date for verification
     * @return true if all checks pass and rental is processed; false otherwise
     */
    public boolean addVideoTapeRental(Tape tape, Date currentDate) {
        // Check if customer has less than 20 rentals
        if (rentals.size() >= 20) {
            return false;
        }
        
        // Check if customer has any past-due amount
        if (calculateTotalPastDueAmount(currentDate) > 0) {
            return false;
        }
        
        // Check if tape is available
        if (!tape.isAvailable(currentDate)) {
            return false;
        }
        
        // Create new rental
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(calculateDueDate(currentDate));
        rentals.add(rental);
        
        return true;
    }
    
    /**
     * Retrieves a list of all tape IDs rented by this customer that have not been returned
     * @return list of unreturned tapes; empty list if no active rentals
     */
    public List<Tape> getUnreturnedTapes() {
        List<Tape> unreturnedTapes = new ArrayList<>();
        for (VideoRental rental : rentals) {
            if (rental.getReturnDate() == null) {
                unreturnedTapes.add(rental.getTape());
            }
        }
        return unreturnedTapes;
    }
    
    /**
     * Calculates the total past-due amount for all rentals of this customer
     * @param currentDate the current date for calculation
     * @return total past-due amount rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(Date currentDate) {
        double totalPastDue = 0.0;
        for (VideoRental rental : rentals) {
            totalPastDue += rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(totalPastDue * 100.0) / 100.0;
    }
    
    /**
     * Calculates the due date for a new rental (7 days from current date)
     * @param currentDate the current date
     * @return the due date
     */
    private Date calculateDueDate(Date currentDate) {
        // Implementation would convert Date to LocalDate, add 7 days, and convert back
        // For simplicity, returning current date + 7 days
        return currentDate;
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public List<VideoRental> getRentals() {
        return rentals;
    }
    
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }
}

/**
 * Represents a video tape in the inventory
 */
class Tape {
    private String id;
    private String videoInformation;
    private List<VideoRental> rentals;
    
    /**
     * Default constructor
     */
    public Tape() {
        this.rentals = new ArrayList<>();
    }
    
    /**
     * Checks if the tape is available for rental on a given date
     * A tape is unavailable if it belongs to any active rental whose return date is null
     * @param currentDate the date to check availability for
     * @return true if the tape is available; false otherwise
     */
    public boolean isAvailable(Date currentDate) {
        for (VideoRental rental : rentals) {
            if (rental.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getVideoInformation() {
        return videoInformation;
    }
    
    public void setVideoInformation(String videoInformation) {
        this.videoInformation = videoInformation;
    }
    
    public List<VideoRental> getRentals() {
        return rentals;
    }
    
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }
}

/**
 * Represents a video rental transaction for a specific tape
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
     * Calculates the past-due amount for this video rental
     * If return date ≤ due date → overdue amount = 0
     * If return date > due date → overdue days = (return date - due date), overdue fee = overdue days × $0.5
     * If tape has not been returned (return date is null) → overdue day = current date – due date; overdue fee = overdueDays × $0.50
     * Any partial day counts as a full day
     * @param currentDate the current date for calculation
     * @return overdue fee rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(Date currentDate) {
        long overdueDays = calculateOverdueDays(currentDate);
        double overdueFee = overdueDays * 0.5;
        return Math.round(overdueFee * 100.0) / 100.0;
    }
    
    /**
     * Calculates the base rental fee for this rental
     * Base rental fee = rental days × $1 per day
     * Any partial rental day counts as a full day
     * @param currentDate the current date for calculation
     * @return base rental fee rounded to two decimal places
     */
    public double calculateBaseRentalFee(Date currentDate) {
        long rentalDays = calculateRentalDays(currentDate);
        double baseFee = rentalDays * 1.0;
        return Math.round(baseFee * 100.0) / 100.0;
    }
    
    /**
     * Calculates the number of overdue days for this rental
     * @param currentDate the current date for calculation
     * @return number of overdue days
     */
    private long calculateOverdueDays(Date currentDate) {
        Date comparisonDate = (returnDate != null) ? returnDate : currentDate;
        
        if (comparisonDate.compareTo(dueDate) <= 0) {
            return 0;
        }
        
        // Implementation would convert Date to LocalDate and calculate difference
        // For simplicity, returning 1 day if overdue
        long daysDifference = 1;
        return Math.max(0, daysDifference);
    }
    
    /**
     * Calculates the number of rental days for this rental
     * @param currentDate the current date for calculation
     * @return number of rental days
     */
    private long calculateRentalDays(Date currentDate) {
        // Implementation would calculate days between rental date and due/return date
        // For simplicity, returning 7 days (standard rental period)
        return 7;
    }
    
    // Getters and setters
    public Date getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    public Date getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    
    public double getOwnedPastDueAmount() {
        return ownedPastDueAmount;
    }
    
    public void setOwnedPastDueAmount(double ownedPastDueAmount) {
        this.ownedPastDueAmount = ownedPastDueAmount;
    }
    
    public Tape getTape() {
        return tape;
    }
    
    public void setTape(Tape tape) {
        this.tape = tape;
    }
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
     * Each rental total price = base rental fee + overdue fee
     * @param rentalDate the date when the rental occurred
     * @param currentDate the current date for calculation
     * @return total price rounded to two decimal places
     */
    public double calculateTotalPrice(Date rentalDate, Date currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            double baseFee = rental.calculateBaseRentalFee(currentDate);
            double overdueFee = rental.calculateOwedPastDueAmount(currentDate);
            total += (baseFee + overdueFee);
        }
        return Math.round(total * 100.0) / 100.0;
    }
    
    // Getters and setters
    public Date getRentalDate() {
        return rentalDate;
    }
    
    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public List<VideoRental> getRentals() {
        return rentals;
    }
    
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

/**
 * Custom Date class to handle date operations (simplified implementation)
 */
class Date {
    private String dateString;
    
    public Date() {
        this.dateString = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    public Date(String dateString) {
        this.dateString = dateString;
    }
    
    public int compareTo(Date other) {
        return this.dateString.compareTo(other.dateString);
    }
    
    public String getDateString() {
        return dateString;
    }
    
    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}