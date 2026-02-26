import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a customer in the video rental system.
 */
class Customer {
    private String id;
    private List<VideoRental> rentals;

    /**
     * Constructs a new Customer with default values.
     */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Gets the customer's ID.
     *
     * @return the customer's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the customer's ID.
     *
     * @param id the customer's ID to set
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
     * @param currentDate the current date
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

        // Create a new rental (due date would typically be set based on business rules)
        // For this implementation, we'll set due date as 3 days from current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dueDate = new Date(currentDate.getTime() + 3 * 24 * 60 * 60 * 1000L); // 3 days later
            VideoRental rental = new VideoRental();
            rental.setTape(tape);
            rental.setDueDate(dueDate);
            rental.setReturnDate(null);
            rental.setOwnedPastDueAmount(0.0);
            this.rentals.add(rental);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Retrieves a list of all tapes rented by this customer that have not been returned.
     *
     * @return a list of unreturned tapes, or an empty list if no active rentals
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
     * Calculates the total past-due amount for this customer.
     *
     * @param currentDate the current date
     * @return the total past-due amount
     */
    public double calculateTotalPastDueAmount(Date currentDate) {
        double totalPastDue = 0.0;
        for (VideoRental rental : this.rentals) {
            totalPastDue += rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(totalPastDue * 100.0) / 100.0;
    }
}

/**
 * Represents a video tape in the rental system.
 */
class Tape {
    private String id;
    private String videoInformation;

    /**
     * Constructs a new Tape with default values.
     */
    public Tape() {
    }

    /**
     * Gets the tape's ID.
     *
     * @return the tape's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the tape's ID.
     *
     * @param id the tape's ID to set
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
     * Checks if this tape is available for rental on the given date.
     * A tape is unavailable if it belongs to any active rental whose return date is null.
     *
     * @param currentDate the current date to check availability
     * @return true if the tape is available; otherwise, false
     */
    public boolean isAvailable(Date currentDate) {
        // In a real implementation, this would check against all rentals in the system
        // For this simplified version, we're assuming the check happens at the customer level
        // This method would need access to all rentals in a complete system
        return true; // Simplified implementation
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
     * Constructs a new VideoRental with default values.
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
     * @return the return date
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
     *
     * @param currentDate the current date for calculation
     * @return the past-due fee rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(Date currentDate) {
        if (this.returnDate != null && !this.returnDate.after(this.dueDate)) {
            return 0.0;
        }

        long overdueDays;
        if (this.returnDate == null) {
            // Tape has not been returned
            overdueDays = (currentDate.getTime() - this.dueDate.getTime()) / (24 * 60 * 60 * 1000);
        } else {
            // Tape has been returned but past due
            overdueDays = (this.returnDate.getTime() - this.dueDate.getTime()) / (24 * 60 * 60 * 1000);
        }

        if (overdueDays <= 0) {
            return 0.0;
        }

        double fee = overdueDays * 0.5;
        return Math.round(fee * 100.0) / 100.0;
    }

    /**
     * Calculates the base rental fee for this rental.
     * Base rental fee = rental days × $1 per day.
     * Any partial rental day counts as a full day.
     *
     * @param currentDate the current date for calculation
     * @return the base rental fee
     */
    public double calculateBaseRentalFee(Date currentDate) {
        if (this.returnDate != null) {
            currentDate = this.returnDate;
        }

        long rentalDays = (currentDate.getTime() - this.dueDate.getTime() + 3 * 24 * 60 * 60 * 1000L) / (24 * 60 * 60 * 1000);
        if (rentalDays <= 0) {
            rentalDays = 1; // Minimum one day
        }
        return rentalDays * 1.0;
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
     * Constructs a new RentalTransaction with default values.
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
     * Calculates the total price for this rental transaction.
     * Each rental total price = base rental fee + overdue fee.
     *
     * @param rentalDate the date when the rental started
     * @param currentDate the current date for calculation
     * @return the total price rounded to two decimal places
     */
    public double calculateTotalPrice(Date rentalDate, Date currentDate) {
        double total = 0.0;
        for (VideoRental rental : this.rentals) {
            double baseFee = rental.calculateBaseRentalFee(currentDate);
            double overdueFee = rental.calculateOwedPastDueAmount(currentDate);
            total += baseFee + overdueFee;
        }
        return Math.round(total * 100.0) / 100.0;
    }
}