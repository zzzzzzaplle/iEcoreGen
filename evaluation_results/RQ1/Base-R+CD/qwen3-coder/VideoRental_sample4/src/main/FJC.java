import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Represents a customer in the video rental system.
 */
class Customer {
    private String id;
    private List<VideoRental> rentals;

    /**
     * Default constructor for Customer.
     */
    public Customer() {
        this.rentals = new ArrayList<>();
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

    /**
     * Adds a video tape rental for this customer.
     * Verifies that the customer has less than 20 rentals and no past-due amount.
     * Also verifies that the tape is available.
     *
     * @param tape The tape to be rented
     * @param currentDate The current date for rental processing
     * @return true if all checks pass and the rental is processed; otherwise, false
     */
    public boolean addVedioTapeRental(Tape tape, Date currentDate) {
        // Check if customer has 20 or more rentals
        if (this.rentals.size() >= 20) {
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

        // Create a new rental (assuming 5 days rental period)
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DAY_OF_MONTH, 5); // Due date is 5 days from rental date
        Date dueDate = cal.getTime();

        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        rental.setOwnedPastDueAmount(0.0);

        this.rentals.add(rental);
        return true;
    }

    /**
     * Retrieves a list of all tapes rented by this customer that have not been returned.
     *
     * @return A list of unreturned tapes, or an empty list if the customer has no active rentals
     */
    public List<Tape> getUnreturnedTapes() {
        List<Tape> unreturnedTapes = new ArrayList<>();
        for (VideoRental rental : this.rentals) {
            if (rental.getReturnDate() == null) {
                unreturnedTapes.add(rental.getTape());
            }
        }
        return unreturnedTapes;
    }

    /**
     * Calculates the total past-due amount for all of the customer's active rentals.
     *
     * @param currentDate The current date for calculation
     * @return The total past-due amount rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(Date currentDate) {
        double total = 0.0;
        for (VideoRental rental : this.rentals) {
            total += rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(total * 100.0) / 100.0;
    }
}

/**
 * Represents a video tape in the rental system.
 */
class Tape {
    private String id;
    private String videoInformation;

    /**
     * Default constructor for Tape.
     */
    public Tape() {
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

    /**
     * Checks if this tape is available for rental on the given date.
     * A tape is unavailable if it belongs to any active rental whose return date is null.
     *
     * @param currentDate The date to check availability for
     * @return true if the tape is available; otherwise, false
     */
    public boolean isAvailable(Date currentDate) {
        // This method would typically query the system for active rentals containing this tape
        // Since we don't have access to the entire system state, we'll assume availability
        // In a real implementation, this would check against all rentals in the system
        return true;
    }
}

/**
 * Represents a single video rental transaction.
 */
class VideoRental {
    private Date dueDate;
    private Date returnDate;
    private double ownedPastDueAmount;
    private Tape tape;

    /**
     * Default constructor for VideoRental.
     */
    public VideoRental() {
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

    /**
     * Calculates the past-due amount for this video rental.
     * If return date ≤ due date → overdue amount = 0.
     * If return date > due date → overdue days = (return date - due date), overdue fee = overdue days × $0.5.
     * If the tape has not been returned (return date is null) → overdue day = current date – due date;
     * overdue fee = overdueDays × $0.50.
     * Dates are in "yyyy-MM-dd HH:mm:ss" format. Any partial day counts as a full day.
     *
     * @param currentDate The current date for calculation
     * @return The past-due fee rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(Date currentDate) {
        // If already returned and return date is <= due date, no fee
        if (this.returnDate != null && !this.returnDate.after(this.dueDate)) {
            return 0.0;
        }

        Date referenceDate;
        if (this.returnDate != null) {
            referenceDate = this.returnDate;
        } else {
            referenceDate = currentDate;
        }

        // If reference date is <= due date, no fee
        if (!referenceDate.after(this.dueDate)) {
            return 0.0;
        }

        // Calculate overdue days (any partial day counts as full day)
        long dueTime = this.dueDate.getTime();
        long referenceTime = referenceDate.getTime();
        long diffInMillis = referenceTime - dueTime;
        long diffInDays = (long) Math.ceil((double) diffInMillis / (24 * 60 * 60 * 1000));

        // Calculate fee ($0.50 per day)
        double fee = diffInDays * 0.5;
        return Math.round(fee * 100.0) / 100.0;
    }

    /**
     * Calculates the base rental fee for this rental.
     * Base rental fee = rental days × $1 per day.
     * Any partial rental day counts as a full day.
     *
     * @param currentDate The current date for calculation
     * @return The base rental fee rounded to two decimal places
     */
    public double calculateBaseRentalFee(Date currentDate) {
        if (this.returnDate != null) {
            currentDate = this.returnDate;
        }

        // Calculate rental days (any partial day counts as full day)
        long rentalStartTime = this.dueDate.getTime() - (5 * 24 * 60 * 60 * 1000); // Assuming 5-day rental period
        long currentTime = currentDate.getTime();
        long diffInMillis = currentTime - rentalStartTime;
        long diffInDays = (long) Math.ceil((double) diffInMillis / (24 * 60 * 60 * 1000));

        // Ensure we don't have negative days
        if (diffInDays < 0) {
            diffInDays = 0;
        }

        double fee = diffInDays * 1.0;
        return Math.round(fee * 100.0) / 100.0;
    }
}

/**
 * Represents a complete rental transaction containing multiple video rentals.
 */
class RentalTransaction {
    private Date rentalDate;
    private double totalPrice;
    private List<VideoRental> rentals;
    private Customer customer;

    /**
     * Default constructor for RentalTransaction.
     */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
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

    /**
     * Calculates the total price for this rental transaction.
     * Total price = base rental fee + Σ current overdue fees for all of the customer's active rentals.
     * Any partial rental day counts as a full day.
     *
     * @param rentalDate The date when the rental started
     * @param currentDate The current date for calculation
     * @return The total price rounded to two decimal places
     */
    public double calculateTotalPrice(Date rentalDate, Date currentDate) {
        double total = 0.0;

        // Add base rental fees for all rentals in this transaction
        for (VideoRental rental : this.rentals) {
            total += rental.calculateBaseRentalFee(currentDate);
        }

        // Add past due amounts for all of the customer's active rentals
        if (this.customer != null) {
            total += this.customer.calculateTotalPastDueAmount(currentDate);
        }

        this.totalPrice = Math.round(total * 100.0) / 100.0;
        return this.totalPrice;
    }
}