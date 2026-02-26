import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

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
     * @param currentDate the current date when rental is being processed
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
        
        // Create new rental
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        
        // Set due date (assuming 7-day rental period)
        long dueDateMillis = currentDate.getTime() + TimeUnit.DAYS.toMillis(7);
        rental.setDueDate(new Date(dueDateMillis));
        
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
     * Calculates the total past due amount for all rentals
     * @param currentDate the current date for calculation
     * @return total past due amount rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(Date currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            total += rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(total * 100.0) / 100.0;
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
 * Represents a video tape in the rental system
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
     * @param currentDate the date to check availability for
     * @return true if available, false otherwise
     */
    public boolean isAvailable(Date currentDate) {
        // This method would typically check against a database of active rentals
        // For this implementation, we assume all tapes are available
        // In a real system, this would check if the tape is currently rented out
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
    }
    
    /**
     * Calculates the overdue amount for this rental
     * @param currentDate the current date for calculation
     * @return overdue amount rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(Date currentDate) {
        if (returnDate != null) {
            // Tape has been returned
            if (returnDate.after(dueDate)) {
                long diff = returnDate.getTime() - dueDate.getTime();
                long overdueDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                double fee = overdueDays * 0.5;
                return Math.round(fee * 100.0) / 100.0;
            }
        } else {
            // Tape not yet returned
            if (currentDate.after(dueDate)) {
                long diff = currentDate.getTime() - dueDate.getTime();
                long overdueDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                double fee = overdueDays * 0.5;
                return Math.round(fee * 100.0) / 100.0;
            }
        }
        return 0.0;
    }
    
    /**
     * Calculates the base rental fee for this rental
     * @param currentDate the current date for calculation
     * @return base rental fee rounded to two decimal places
     */
    public double calculateBaseRentalFee(Date currentDate) {
        Date endDate = (returnDate != null) ? returnDate : currentDate;
        if (endDate.before(dueDate)) {
            // Returned early or calculating before due date
            long diff = endDate.getTime() - dueDate.getTime() + TimeUnit.DAYS.toMillis(7);
            long rentalDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            double fee = rentalDays * 1.0;
            return Math.round(fee * 100.0) / 100.0;
        } else {
            // Standard 7-day rental period applies
            return 7.0;
        }
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
 * Represents a complete rental transaction for a customer
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
     * Calculates the total price for all rentals in this transaction
     * @param rentalDate the date when rental started
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
        this.totalPrice = Math.round(total * 100.0) / 100.0;
        return this.totalPrice;
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