import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
     * Adds a video tape rental for this customer.
     * Verifies that the customer has less than 20 rentals and no past-due amount,
     * and that the tape is available.
     *
     * @param tape The tape to rent
     * @param currentDate The current date of the rental
     * @return true if all checks pass and the rental is processed; otherwise, false
     */
    public boolean addVedioTapeRental(Tape tape, Date currentDate) {
        // Check if customer has 20 or more rentals
        if (this.rentals.size() >= 20) {
            return false;
        }

        // Check if customer has past due amounts
        if (calculateTotalPastDueAmount(currentDate) > 0) {
            return false;
        }

        // Check if tape is available
        if (!tape.isAvailable(currentDate)) {
            return false;
        }

        // Create a new rental (due date would typically be set based on business rules)
        // For this implementation, we'll set due date to 3 days from current date
        LocalDate localCurrentDate = convertDateToLocalDate(currentDate);
        LocalDate dueLocalDate = localCurrentDate.plusDays(3);
        Date dueDate = convertLocalDateToDate(dueLocalDate);

        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        rental.setOwnedPastDueAmount(0.0);

        this.rentals.add(rental);
        return true;
    }

    /**
     * Retrieves a list of all tapes rented by a customer that have not been returned.
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
     * Calculates the total past due amount for all rentals of this customer.
     *
     * @param currentDate The current date to calculate past due amounts
     * @return The total past due amount
     */
    public double calculateTotalPastDueAmount(Date currentDate) {
        double total = 0.0;
        for (VideoRental rental : this.rentals) {
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

    // Helper methods for date conversion
    private LocalDate convertDateToLocalDate(Date date) {
        return LocalDate.parse(convertToDateString(date), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
    }

    private String convertToDateString(Date date) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
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
     * Checks if the tape is available for rental on the given date.
     * A tape is unavailable if it belongs to any active rental whose return date is null.
     *
     * @param currentDate The date to check availability
     * @return true if the tape is available; otherwise, false
     */
    public boolean isAvailable(Date currentDate) {
        // In a real implementation, this would check against all rentals in the system
        // For this model, we're assuming the check happens at the customer level
        // This method is included to satisfy the design model but would need
        // access to all rentals in a complete system implementation
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

    /**
     * Calculates the past-due amount for this video rental.
     * If return date ≤ due date → overdue amount = 0.
     * If return date > due date → overdue days = (return date - due date), overdue fee = overdue days × $0.5.
     * If the tape has not been returned (return date is null) → overdue day = current date – due date;
     * overdue fee = overdueDays × $0.50.
     *
     * @param currentDate The current date for calculation
     * @return The past due fee rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(Date currentDate) {
        // If already returned
        if (this.returnDate != null) {
            LocalDate returnLocalDate = convertDateToLocalDate(this.returnDate);
            LocalDate dueLocalDate = convertDateToLocalDate(this.dueDate);

            // If return date is before or equal to due date, no fee
            if (returnLocalDate.isBefore(dueLocalDate) || returnLocalDate.isEqual(dueLocalDate)) {
                return 0.0;
            }

            // Calculate overdue days and fee
            long overdueDays = ChronoUnit.DAYS.between(dueLocalDate, returnLocalDate);
            return Math.round(overdueDays * 0.5 * 100.0) / 100.0;
        } else {
            // Not returned yet
            LocalDate currentLocalDate = convertDateToLocalDate(currentDate);
            LocalDate dueLocalDate = convertDateToLocalDate(this.dueDate);

            // If current date is before or equal to due date, no fee
            if (currentLocalDate.isBefore(dueLocalDate) || currentLocalDate.isEqual(dueLocalDate)) {
                return 0.0;
            }

            // Calculate overdue days and fee
            long overdueDays = ChronoUnit.DAYS.between(dueLocalDate, currentLocalDate);
            return Math.round(overdueDays * 0.5 * 100.0) / 100.0;
        }
    }

    /**
     * Calculates the base rental fee for this rental.
     * Base rental fee = rental days × $1 per day.
     * Any partial rental day counts as a full day.
     *
     * @param currentDate The current date for calculation
     * @return The base rental fee
     */
    public double calculateBaseRentalFee(Date currentDate) {
        Date endDate = (this.returnDate != null) ? this.returnDate : currentDate;
        
        // Calculate rental start date (3 days before due date as per standard rental period)
        LocalDate dueLocalDate = convertDateToLocalDate(this.dueDate);
        LocalDate startLocalDate = dueLocalDate.minusDays(3); // Standard 3-day rental period
        LocalDate endLocalDate = convertDateToLocalDate(endDate);

        // Calculate rental days (any partial day counts as full day)
        long rentalDays = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);
        if (rentalDays < 1) {
            rentalDays = 1; // Minimum one day
        }

        return rentalDays * 1.0;
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

    // Helper method for date conversion
    private LocalDate convertDateToLocalDate(Date date) {
        return LocalDate.parse(convertToDateString(date), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private String convertToDateString(Date date) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
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
     * Calculates the total price for this rental transaction.
     * Each rental total price = base rental fee + overdue fee.
     *
     * @param rentalDate The date the rental started
     * @param currentDate The current date for calculation
     * @return The total price rounded to two decimal places
     */
    public double calculateTotalPrice(Date rentalDate, Date currentDate) {
        double total = 0.0;
        for (VideoRental rental : this.rentals) {
            total += rental.calculateBaseRentalFee(currentDate) + rental.calculateOwedPastDueAmount(currentDate);
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