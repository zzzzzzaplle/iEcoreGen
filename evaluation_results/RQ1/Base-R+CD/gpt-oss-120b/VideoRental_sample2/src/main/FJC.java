/*********************************************************************************************
 *  Video Rental System – Core Domain Classes
 *  -------------------------------------------------
 *  This file contains the complete implementation of the domain model described in the
 *  specification. All classes are fully documented, include no‑arg constructors,
 *  getters/setters, and implement the functional requirements:
 *
 *  • Past‑due fee calculation
 *  • Tape availability check
 *  • Adding a tape rental for a customer
 *  • Total price calculation for a rental transaction
 *  • Listing a customer's unreturned tapes
 *
 *  The implementation uses java.time.LocalDate for date handling and java.math.BigDecimal
 *  for monetary rounding to two decimal places.
 *********************************************************************************************/

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a customer of the video rental store.
 */
 class Customer {

    /** Unique identifier of the customer (e.g., the ID card number). */
    private String id;

    /** List of all rentals (active and completed) belonging to the customer. */
    private List<VideoRental> rentals;

    /** No‑argument constructor required for frameworks / testing. */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Constructs a Customer with the given identifier.
     *
     * @param id the customer identifier
     */
    public Customer(String id) {
        this.id = id;
        this.rentals = new ArrayList<>();
    }

    /* ==================== Getters & Setters ==================== */

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
     * Attempts to add a new video tape rental for this customer.
     *
     * @param tape        the tape to be rented
     * @param currentDate the date on which the rental is being created
     * @return {@code true} if the rental was successfully added; {@code false}
     *         otherwise (e.g., customer already has 20 outstanding rentals,
     *         has unpaid past‑due amount, or the tape is unavailable)
     */
    public boolean addVedioTapeRental(Tape tape, LocalDate currentDate) {
        // 1. Check outstanding (unreturned) rentals count
        long activeRentals = rentals.stream()
                .filter(r -> r.getReturnDate() == null)
                .count();
        if (activeRentals >= 20) {
            return false; // exceeds maximum allowed outstanding rentals
        }

        // 2. Verify no past‑due amount is owed
        double totalPastDue = calculateTotalPastDueAmount(currentDate);
        if (totalPastDue > 0.0) {
            return false; // customer must settle past‑due fees first
        }

        // 3. Verify tape availability
        if (!tape.isAvailable(currentDate)) {
            return false; // tape currently rented out
        }

        // 4. Create a new VideoRental (default rental period = 7 days)
        LocalDate dueDate = currentDate.plusDays(7);
        VideoRental rental = new VideoRental(dueDate, null, tape);
        rentals.add(rental);
        return true;
    }

    /**
     * Retrieves a list of all tapes that have been rented by this customer and
     * have not yet been returned.
     *
     * @return an immutable list of unreturned {@link Tape} objects; empty if none
     */
    public List<Tape> getUnreturnedTapes() {
        List<Tape> result = new ArrayList<>();
        for (VideoRental rental : rentals) {
            if (rental.getReturnDate() == null) {
                result.add(rental.getTape());
            }
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Calculates the total past‑due amount owed by the customer across all
     * rentals (active and completed) as of the supplied date.
     *
     * @param currentDate the date used for overdue calculation
     * @return the sum of overdue fees, rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(LocalDate currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            total += rental.calculateOwnedPastDueAmount(currentDate);
        }
        return roundToTwoDecimals(total);
    }

    /**
     * Utility method to round a double value to two decimal places using
     * {@link BigDecimal}.
     *
     * @param value the value to round
     * @return value rounded to two decimal places
     */
    private double roundToTwoDecimals(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Represents a video tape in the inventory.
 */
 class Tape {

    /** Barcode identifier of the tape. */
    private String id;

    /** Human‑readable description of the video (title, format, etc.). */
    private String videoInformation;

    /** Registry of all rentals in the system – used to determine availability. */
    private static final List<VideoRental> RENTAL_REGISTRY = new ArrayList<>();

    /** No‑argument constructor required for frameworks / testing. */
    public Tape() {
    }

    /**
     * Constructs a Tape with the given id and information.
     *
     * @param id                the barcode id of the tape
     * @param videoInformation  description of the video
     */
    public Tape(String id, String videoInformation) {
        this.id = id;
        this.videoInformation = videoInformation;
    }

    /* ==================== Getters & Setters ==================== */

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
     * Registers a rental in the global registry. This method is called from the
     * {@link VideoRental} constructor.
     *
     * @param rental the rental to register
     */
    static void registerRental(VideoRental rental) {
        RENTAL_REGISTRY.add(rental);
    }

    /**
     * Checks whether this tape is available for rental on the supplied date.
     * A tape is unavailable if it belongs to any active rental (i.e., a rental
     * whose {@code returnDate} is {@code null}).
     *
     * @param currentDate the date on which availability is being checked
     * @return {@code true} if the tape is free; {@code false} otherwise
     */
    public boolean isAvailable(LocalDate currentDate) {
        for (VideoRental rental : RENTAL_REGISTRY) {
            if (rental.getTape() == this && rental.getReturnDate() == null) {
                // The tape is currently rented out and not yet returned.
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tape tape = (Tape) o;
        return Objects.equals(id, tape.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Represents a single video tape rental.
 */
 class VideoRental {

    /** Date by which the tape should be returned (due date). */
    private LocalDate dueDate;

    /** Actual return date; {@code null} if the tape has not yet been returned. */
    private LocalDate returnDate;

    /** Cached past‑due amount (computed on demand). */
    private double ownedPastDueAmount;

    /** The tape that is being rented. */
    private Tape tape;

    /** No‑argument constructor required for frameworks / testing. */
    public VideoRental() {
    }

    /**
     * Constructs a VideoRental with the supplied due date, return date and tape.
     *
     * @param dueDate    the date the tape is due
     * @param returnDate the actual return date (may be {@code null})
     * @param tape       the tape being rented
     */
    public VideoRental(LocalDate dueDate, LocalDate returnDate, Tape tape) {
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.tape = tape;
        // Register this rental so that Tape.isAvailable can work.
        Tape.registerRental(this);
    }

    /* ==================== Getters & Setters ==================== */

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
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
     * Calculates the overdue fee for this rental as of {@code currentDate}.
     * <p>
     * Rules:
     * <ul>
     *   <li>If {@code returnDate} ≤ {@code dueDate} → fee = 0.</li>
     *   <li>If {@code returnDate} > {@code dueDate} → fee = (days overdue) × $0.50.</li>
     *   <li>If the tape has not been returned ( {@code returnDate == null} )
     *       → fee = (days between {@code currentDate} and {@code dueDate}) × $0.50.</li>
     * </ul>
     * The result is rounded to two decimal places.
     *
     * @param currentDate the date used for calculation when the tape is still out
     * @return overdue fee rounded to two decimal places
     */
    public double calculateOwnedPastDueAmount(LocalDate currentDate) {
        LocalDate effectiveReturn = (returnDate != null) ? returnDate : currentDate;

        // If the effective return date is on or before the due date, no fee.
        if (!effectiveReturn.isAfter(dueDate)) {
            ownedPastDueAmount = 0.0;
            return 0.0;
        }

        long overdueDays = ChronoUnit.DAYS.between(dueDate, effectiveReturn);
        double fee = overdueDays * 0.50;
        ownedPastDueAmount = roundToTwoDecimals(fee);
        return ownedPastDueAmount;
    }

    /**
     * Helper to round a double value to two decimal places.
     *
     * @param value the raw value
     * @return the value rounded to two decimal places
     */
    private double roundToTwoDecimals(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VideoRental that = (VideoRental) o;
        return Objects.equals(dueDate, that.dueDate) &&
                Objects.equals(returnDate, that.returnDate) &&
                Objects.equals(tape, that.tape);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dueDate, returnDate, tape);
    }
}

/**
 * Represents a rental transaction that may contain multiple tape rentals for a
 * single customer.
 */
 class RentalTransaction {

    /** Date on which the transaction was created (first rental date). */
    private LocalDate rentalDate;

    /** Cached total price of the transaction (including overdue fees). */
    private double totalPrice;

    /** All rentals that belong to this transaction. */
    private List<VideoRental> rentals;

    /** Customer who performed the transaction. */
    private Customer customer;

    /** No‑argument constructor required for frameworks / testing. */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Constructs a RentalTransaction for the given customer and rental date.
     *
     * @param rentalDate the date the transaction starts
     * @param customer   the customer involved
     */
    public RentalTransaction(LocalDate rentalDate, Customer customer) {
        this.rentalDate = rentalDate;
        this.customer = customer;
        this.rentals = new ArrayList<>();
    }

    /* ==================== Getters & Setters ==================== */

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    // No public setter for totalPrice – it is derived via calculateTotalPrice

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
     * Adds a {@link VideoRental} to this transaction.
     *
     * @param rental the rental to add
     */
    public void addRental(VideoRental rental) {
        this.rentals.add(rental);
    }

    /**
     * Calculates the total price for the transaction.
     * <p>
     * For each {@link VideoRental} the price consists of:
     * <ul>
     *   <li>Base rental fee = {@code rentalDays} × $1.00 (any fraction of a day counts as a full day)</li>
     *   <li>Overdue fee as computed by {@link VideoRental#calculateOwnedPastDueAmount(LocalDate)}</li>
     * </ul>
     * The sum of all rental prices is rounded to two decimal places and stored in
     * {@code totalPrice}.
     *
     * @param rentalDays the number of days the customer intends to keep each tape
     * @param currentDate the date used for overdue calculation (usually today)
     * @return the total price rounded to two decimal places
     */
    public double calculateTotalPrice(int rentalDays, LocalDate currentDate) {
        double sum = 0.0;
        double baseFee = rentalDays * 1.0; // $1 per day per tape

        for (VideoRental rental : rentals) {
            double overdueFee = rental.calculateOwnedPastDueAmount(currentDate);
            sum += baseFee + overdueFee;
        }

        totalPrice = roundToTwoDecimals(sum);
        return totalPrice;
    }

    /**
     * Helper method to round a double value to two decimal places.
     *
     * @param value the raw value
     * @return the value rounded to two decimal places
     */
    private double roundToTwoDecimals(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RentalTransaction that = (RentalTransaction) o;
        return Objects.equals(rentalDate, that.rentalDate) &&
                Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalDate, customer);
    }
}