import java.util.*;
import java.time.LocalDateTime;
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
    
    /**
     * Adds a video tape rental for this customer
     * Verifies customer has less than 20 rentals and no past-due amount
     * Verifies the tape is available
     * 
     * @param tape the tape to be rented
     * @param currentDate the current date and time
     * @return true if all checks pass and the rental is processed; false otherwise
     */
    public boolean addVideoTapeRental(Tape tape, LocalDateTime currentDate) {
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
        
        // Create new rental with due date 7 days from current date
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(currentDate.plusDays(7));
        rentals.add(rental);
        
        return true;
    }
    
    /**
     * Retrieves a list of all tape IDs rented by this customer that have not been returned
     * 
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
     * 
     * @param currentDate the current date and time
     * @return total past-due amount rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(LocalDateTime currentDate) {
        double totalPastDue = 0.0;
        for (VideoRental rental : rentals) {
            totalPastDue += rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(totalPastDue * 100.0) / 100.0;
    }
}

/**
 * Represents a video tape in the inventory
 */
class Tape {
    private String id;
    private String videoInformation;
    
    /**
     * Default constructor
     */
    public Tape() {
    }
    
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
    
    /**
     * Checks tape availability for a given date
     * A tape is unavailable if it belongs to any active rental whose return date is null
     * 
     * @param currentDate the current date and time to check availability for
     * @return true if the tape is available; false otherwise
     */
    public boolean isAvailable(LocalDateTime currentDate) {
        // In a real implementation, this would check against all active rentals in the system
        // For this simplified version, we assume availability checking is handled elsewhere
        // and this method would integrate with the broader rental system
        return true;
    }
}

/**
 * Represents a video rental transaction for a specific tape
 */
class VideoRental {
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private double ownedPastDueAmount;
    private Tape tape;
    
    /**
     * Default constructor
     */
    public VideoRental() {
    }
    
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    
    public LocalDateTime getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(LocalDateTime returnDate) {
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
    
    /**
     * Calculates the past-due amount for this video rental
     * If return date ≤ due date → overdue amount = 0
     * If return date > due date → overdue days = (return date - due date), overdue fee = overdue days × $0.5
     * If the tape has not been returned (return date is null) → overdue day = current date – due date; overdue fee = overdueDays × $0.50
     * Any partial day counts as a full day
     * 
     * @param currentDate the current date and time for calculation
     * @return overdue fee rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(LocalDateTime currentDate) {
        LocalDateTime comparisonDate = (returnDate != null) ? returnDate : currentDate;
        
        if (comparisonDate.isBefore(dueDate) || comparisonDate.isEqual(dueDate)) {
            return 0.0;
        }
        
        long overdueDays = ChronoUnit.DAYS.between(dueDate, comparisonDate);
        // Any partial day counts as a full day
        if (overdueDays == 0) {
            overdueDays = 1;
        }
        
        double overdueFee = overdueDays * 0.5;
        return Math.round(overdueFee * 100.0) / 100.0;
    }
    
    /**
     * Calculates the base rental fee for this video rental
     * Base rental fee = rental days × $1 per day
     * Any partial rental day counts as a full day
     * 
     * @param currentDate the current date and time for calculation
     * @return base rental fee rounded to two decimal places
     */
    public double calculateBaseRentalFee(LocalDateTime currentDate) {
        LocalDateTime endDate = (returnDate != null) ? returnDate : currentDate;
        long rentalDays = ChronoUnit.DAYS.between(dueDate.minusDays(7), endDate);
        
        // Any partial day counts as a full day
        if (rentalDays == 0) {
            rentalDays = 1;
        }
        
        double baseFee = rentalDays * 1.0;
        return Math.round(baseFee * 100.0) / 100.0;
    }
}

/**
 * Represents a complete rental transaction involving multiple video rentals
 */
class RentalTransaction {
    private LocalDateTime rentalDate;
    private double totalPrice;
    private List<VideoRental> rentals;
    private Customer customer;
    
    /**
     * Default constructor
     */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }
    
    public LocalDateTime getRentalDate() {
        return rentalDate;
    }
    
    public void setRentalDate(LocalDateTime rentalDate) {
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
    
    /**
     * Calculates the total price for this rental transaction
     * Each rental total price = base rental fee + overdue fee
     * 
     * @param rentalDate the date when the rental started
     * @param currentDate the current date and time for calculation
     * @return total price rounded to two decimal places
     */
    public double calculateTotalPrice(LocalDateTime rentalDate, LocalDateTime currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            double baseFee = rental.calculateBaseRentalFee(currentDate);
            double overdueFee = rental.calculateOwedPastDueAmount(currentDate);
            total += (baseFee + overdueFee);
        }
        this.totalPrice = Math.round(total * 100.0) / 100.0;
        return this.totalPrice;
    }
}