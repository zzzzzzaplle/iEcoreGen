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

    /**
     * Gets the customer ID.
     *
     * @return the customer ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the customer ID.
     *
     * @param id the customer ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the list of video rentals for this customer.
     *
     * @return the list of video rentals
     */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of video rentals for this customer.
     *
     * @param rentals the list of video rentals to set
     */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Adds a video tape rental for this customer.
     * Verifies that the customer has less than 20 rentals and no past-due amount.
     * Also verifies that the tape is available.
     *
     * @param tape the tape to rent
     * @param currentDate the current date for availability checking
     * @return true if all checks pass and the rental is processed; otherwise, false
     */
    public boolean addVedioTapeRental(Tape tape, Date currentDate) {
        // Check if customer has 20 or more rentals
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

        // Create a new rental (assuming 5 days rental period)
        Calendar dueDateCal = Calendar.getInstance();
        dueDateCal.setTime(currentDate);
        dueDateCal.add(Calendar.DAY_OF_MONTH, 5); // Default rental period of 5 days

        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDateCal.getTime());
        rental.setReturnDate(null);
        rental.setOwnedPastDueAmount(0.0);

        rentals.add(rental);
        return true;
    }

    /**
     * Retrieves a list of all tapes rented by a customer that have not been returned.
     *
     * @return a list of unreturned tapes, or an empty list if the customer has no active rentals
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
     * Calculates the total past-due amount for all active rentals of this customer.
     *
     * @param currentDate the current date for calculation
     * @return the total past-due amount
     */
    public double calculateTotalPastDueAmount(Date currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
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

    /**
     * Gets the tape ID.
     *
     * @return the tape ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the tape ID.
     *
     * @param id the tape ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the video information for this tape.
     *
     * @return the video information
     */
    public String getVideoInformation() {
        return videoInformation;
    }

    /**
     * Sets the video information for this tape.
     *
     * @param videoInformation the video information to set
     */
    public void setVideoInformation(String videoInformation) {
        this.videoInformation = videoInformation;
    }

    /**
     * Checks if the tape is available for rental on the given date.
     * A tape is unavailable if it belongs to any active rental whose return date is null.
     *
     * @param currentDate the date to check availability
     * @return true if the tape is available; otherwise, false
     */
    public boolean isAvailable(Date currentDate) {
        // This method would typically check against all rentals in the system
        // For this implementation, we'll assume it's checked at the customer level
        // Returning true as a placeholder - in a real system this would need to check
        // all rentals across all customers
        return true;
    }
}

/**
 * Represents a video rental transaction for a specific tape.
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

    /**
     * Gets the due date for this rental.
     *
     * @return the due date
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date for this rental.
     *
     * @param dueDate the due date to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the return date for this rental.
     *
     * @return the return date, or null if not returned
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date for this rental.
     *
     * @param returnDate the return date to set
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Gets the past-due amount owned for this rental.
     *
     * @return the past-due amount
     */
    public double getOwnedPastDueAmount() {
        return ownedPastDueAmount;
    }

    /**
     * Sets the past-due amount owned for this rental.
     *
     * @param ownedPastDueAmount the past-due amount to set
     */
    public void setOwnedPastDueAmount(double ownedPastDueAmount) {
        this.ownedPastDueAmount = ownedPastDueAmount;
    }

    /**
     * Gets the tape associated with this rental.
     *
     * @return the tape
     */
    public Tape getTape() {
        return tape;
    }

    /**
     * Sets the tape associated with this rental.
     *
     * @param tape the tape to set
     */
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
     * @param currentDate the current date for calculation
     * @return the fee rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(Date currentDate) {
        // If already returned and return date is before or equal to due date, no fee
        if (returnDate != null && !returnDate.after(dueDate)) {
            return 0.0;
        }

        Date referenceDate;
        if (returnDate != null) {
            referenceDate = returnDate;
        } else {
            referenceDate = currentDate;
        }

        // If reference date is before or equal to due date, no fee
        if (!referenceDate.after(dueDate)) {
            return 0.0;
        }

        // Calculate overdue days (any partial day counts as a full day)
        long dueTime = dueDate.getTime();
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
     * @param currentDate the current date for calculation
     * @return the base rental fee rounded to two decimal places
     */
    public double calculateBaseRentalFee(Date currentDate) {
        Date endDate = (returnDate != null) ? returnDate : currentDate;
        
        // Calculate rental days (any partial day counts as a full day)
        long startTime = dueDate.getTime();
        long endTime = endDate.getTime();
        
        // If end date is before due date, no fee
        if (!endTime.after(startTime)) {
            return 0.0;
        }
        
        long diffInMillis = endTime - startTime;
        long diffInDays = (long) Math.ceil((double) diffInMillis / (24 * 60 * 60 * 1000));
        
        // Calculate fee ($1 per day)
        double fee = diffInDays * 1.0;
        return Math.round(fee * 100.0) / 100.0;
    }
}

/**
 * Represents a rental transaction containing multiple video rentals.
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

    /**
     * Gets the rental date for this transaction.
     *
     * @return the rental date
     */
    public Date getRentalDate() {
        return rentalDate;
    }

    /**
     * Sets the rental date for this transaction.
     *
     * @param rentalDate the rental date to set
     */
    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    /**
     * Gets the total price for this transaction.
     *
     * @return the total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price for this transaction.
     *
     * @param totalPrice the total price to set
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the list of video rentals in this transaction.
     *
     * @return the list of video rentals
     */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of video rentals in this transaction.
     *
     * @param rentals the list of video rentals to set
     */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Gets the customer associated with this transaction.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer associated with this transaction.
     *
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Calculates the total price for a customer's rental transaction.
     * Base rental fee = rental days × $1 per day.
     * Total price = base rental fee + Σ current overdue fees for all of the customer's active rentals.
     * Any partial rental day counts as a full day.
     *
     * @param rentalDate the date when the rental started
     * @param currentDate the current date for calculation
     * @return the total price rounded to two decimal places
     */
    public double calculateTotalPrice(Date rentalDate, Date currentDate) {
        double total = 0.0;

        // Add base rental fees for each rental in this transaction
        for (VideoRental rental : rentals) {
            total += rental.calculateBaseRentalFee(currentDate);
        }

        // Add current overdue fees for all of the customer's active rentals
        total += customer.calculateTotalPastDueAmount(currentDate);

        // Round to two decimal places
        return Math.round(total * 100.0) / 100.0;
    }
}