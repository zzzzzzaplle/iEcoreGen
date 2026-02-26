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
    
    /**
     * Adds a video tape rental for this customer
     * @param tape the tape to be rented
     * @param currentDate the current date and time
     * @return true if rental is successful, false otherwise
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
        
        // Create new rental with due date 7 days from now
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(currentDate.plusDays(7));
        rentals.add(rental);
        
        return true;
    }
    
    /**
     * Retrieves all unreturned tapes for this customer
     * @return list of unreturned tapes
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
     * Calculates the total past-due amount for all rentals
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
    
    /**
     * Default constructor
     */
    public Tape() {
    }
    
    /**
     * Checks if the tape is available for rental on the given date
     * @param currentDate the current date and time
     * @return true if available, false otherwise
     */
    public boolean isAvailable(LocalDateTime currentDate) {
        // This method would typically check against a rental database
        // For simplicity, we assume all tapes are available unless rented
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
}

/**
 * Represents a video rental transaction for a single tape
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
    
    /**
     * Calculates the overdue amount for this rental
     * @param currentDate the current date and time
     * @return overdue amount rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(LocalDateTime currentDate) {
        LocalDateTime comparisonDate = (returnDate != null) ? returnDate : currentDate;
        
        if (comparisonDate.isBefore(dueDate) || comparisonDate.isEqual(dueDate)) {
            return 0.0;
        }
        
        long overdueDays = ChronoUnit.DAYS.between(dueDate, comparisonDate);
        // Any partial day counts as a full day
        if (overdueDays == 0 && comparisonDate.isAfter(dueDate)) {
            overdueDays = 1;
        }
        
        double overdueFee = overdueDays * 0.5;
        return Math.round(overdueFee * 100.0) / 100.0;
    }
    
    /**
     * Calculates the base rental fee for this rental
     * @param currentDate the current date and time
     * @return base rental fee rounded to two decimal places
     */
    public double calculateBaseRentalFee(LocalDateTime currentDate) {
        LocalDateTime endDate = (returnDate != null) ? returnDate : currentDate;
        // Assuming rental starts when due date is set minus 7 days
        LocalDateTime startDate = dueDate.minusDays(7);
        
        long rentalDays = ChronoUnit.DAYS.between(startDate, endDate);
        // Any partial day counts as a full day
        if (rentalDays == 0 && endDate.isAfter(startDate)) {
            rentalDays = 1;
        }
        
        double baseFee = rentalDays * 1.0;
        return Math.round(baseFee * 100.0) / 100.0;
    }
    
    // Getters and setters
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
}

/**
 * Represents a complete rental transaction containing multiple video rentals
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
    
    /**
     * Calculates the total price for all rentals in this transaction
     * @param rentalDate the date when the rental started
     * @param currentDate the current date and time for calculation
     * @return total price rounded to two decimal places
     */
    public double calculateTotalPrice(LocalDateTime rentalDate, LocalDateTime currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            double baseFee = rental.calculateBaseRentalFee(currentDate);
            double overdueFee = rental.calculateOwedPastDueAmount(currentDate);
            total += baseFee + overdueFee;
        }
        return Math.round(total * 100.0) / 100.0;
    }
    
    // Getters and setters
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
}