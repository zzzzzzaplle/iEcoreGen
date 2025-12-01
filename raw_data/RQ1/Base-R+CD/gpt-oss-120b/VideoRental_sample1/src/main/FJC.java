package com.abc.rental;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a customer of the video rental store.
 */
 class Customer {

    /** Unique identifier of the customer (e.g., id‑card barcode). */
    private String id;

    /** List of all rentals belonging to this customer. */
    private List<VideoRental> rentals;

    /** Unparameterized constructor required for test frameworks. */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Adds a video‑tape rental for this customer.
     *
     * @param tape        the {@link Tape} to be rented
     * @param currentDate the date on which the rental is created
     * @return {@code true} if the rental is successfully processed; {@code false}
     *         otherwise (e.g., customer already has 20 rentals, has past‑due
     *         amount, or the tape is unavailable)
     */
    public boolean addVedioTapeRental(Tape tape, LocalDate currentDate) {
        // Check maximum number of rentals
        if (this.rentals.size() >= 20) {
            return false;
        }

        // Check that the customer has no past‑due amount
        if (calculateTotalPastDueAmount(currentDate) > 0.0) {
            return false;
        }

        // Verify tape availability
        if (!tape.isAvailable(currentDate)) {
            return false;
        }

        // Create a new rental (due date is set to 7 days from now as a simple policy)
        LocalDate dueDate = currentDate.plusDays(7);
        VideoRental rental = new VideoRental(dueDate, null, 0.0, tape);
        this.rentals.add(rental);
        VideoRental.registerRental(rental);
        return true;
    }

    /**
     * Retrieves a list of all tapes that have not yet been returned by this
     * customer.
     *
     * @return an immutable list of {@link Tape} objects that are still out on
     *         rent; empty list if none
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
     * Calculates the total past‑due amount for this customer as of the given
     * date.
     *
     * @param currentDate the date used for the calculation
     * @return the sum of overdue fees for all rentals, rounded to two decimal
     *         places
     */
    public double calculateTotalPastDueAmount(LocalDate currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            total += rental.calculateOwnedPastDueAmount(currentDate);
        }
        return round(total);
    }

    /* ------------------- Getters & Setters ------------------- */

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

    /* ------------------- Utility ------------------- */

    private double round(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

/**
 * Represents a video tape in the inventory.
 */
class Tape {

    /** Unique barcode identifier of the tape. */
    private String id;

    /** Human‑readable video information (title, etc.). */
    private String videoInformation;

    /** Unparameterized constructor required for test frameworks. */
    public Tape() {
        // default constructor
    }

    /**
     * Checks whether this tape is available for rental on the given date.
     *
     * @param currentDate the date on which availability is queried
     * @return {@code true} if the tape is not currently rented out (i.e., no
     *         active rental with a {@code null} return date); {@code false}
     *         otherwise
     */
    public boolean isAvailable(LocalDate currentDate) {
        for (VideoRental rental : VideoRental.getAllRentals()) {
            if (rental.getTape().getId().equals(this.id) && rental.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }

    /* ------------------- Getters & Setters ------------------- */

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
 * Represents a single video tape rental.
 */
class VideoRental {

    /** The date by which the tape should be returned. */
    private LocalDate dueDate;

    /** The actual return date; {@code null} if not yet returned. */
    private LocalDate returnDate;

    /** Cached past‑due amount (may be recomputed on demand). */
    private double ownedPastDueAmount;

    /** The tape that is being rented. */
    private Tape tape;

    /** Holds all rentals ever created – used for availability checks. */
    private static final List<VideoRental> ALL_RENTALS = new ArrayList<>();

    /** Unparameterized constructor required for test frameworks. */
    public VideoRental() {
        // default constructor
    }

    /**
     * Full constructor used internally when creating a new rental.
     *
     * @param dueDate            the due date for the rental
     * @param returnDate         the actual return date (may be {@code null})
     * @param ownedPastDueAmount the past‑due amount already computed
     * @param tape               the {@link Tape} being rented
     */
    public VideoRental(LocalDate dueDate, LocalDate returnDate, double ownedPastDueAmount, Tape tape) {
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.ownedPastDueAmount = ownedPastDueAmount;
        this.tape = tape;
    }

    /**
     * Calculates the overdue fee for this rental as of {@code currentDate}.
     *
     * <p>Rules:
     * <ul>
     *   <li>If {@code returnDate} ≤ {@code dueDate}, the overdue fee is 0.</li>
     *   <li>If {@code returnDate} > {@code dueDate}, overdue days = difference
     *   between the two dates; fee = overdue days × $0.50.</li>
     *   <li>If the tape has not been returned ({@code returnDate == null}),
     *   overdue days = {@code currentDate} − {@code dueDate}; fee = overdue
     *   days × $0.50.</li>
     * </ul>
     *
     * @param currentDate the date used for the calculation (ignored when the
     *                    tape has already been returned)
     * @return the overdue fee rounded to two decimal places
     */
    public double calculateOwnedPastDueAmount(LocalDate currentDate) {
        LocalDate effectiveReturn = (returnDate != null) ? returnDate : currentDate;

        // No overdue if returned on or before due date
        if (!effectiveReturn.isAfter(dueDate)) {
            return 0.0;
        }

        long overdueDays = ChronoUnit.DAYS.between(dueDate, effectiveReturn);
        double fee = overdueDays * 0.5;
        return round(fee);
    }

    /**
     * Registers a newly created rental in the global list used for availability
     * queries.
     *
     * @param rental the {@link VideoRental} to register
     */
    public static void registerRental(VideoRental rental) {
        ALL_RENTALS.add(rental);
    }

    /**
     * Returns an unmodifiable view of all rentals ever created.
     *
     * @return list of all {@link VideoRental}s
     */
    public static List<VideoRental> getAllRentals() {
        return Collections.unmodifiableList(ALL_RENTALS);
    }

    /* ------------------- Getters & Setters ------------------- */

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

    /* ------------------- Utility ------------------- */

    private double round(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

/**
 * Represents a rental transaction that may contain multiple tape rentals.
 */
class RentalTransaction {

    /** Date when the transaction was opened. */
    private LocalDate rentalDate;

    /** Total price of the transaction (including overdue fees). */
    private double totalPrice;

    /** All rentals that belong to this transaction. */
    private List<VideoRental> rentals;

    /** Customer who performed the transaction. */
    private Customer customer;

    /** Unparameterized constructor required for test frameworks. */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Calculates the total price for the transaction.
     *
     * <p>For each {@link VideoRental} in the transaction:
     * <ul>
     *   <li>Base rental fee = {@code rentalDays} × $1.00 (any partial day counts as a full day).</li>
     *   <li>Overdue fee = {@link VideoRental#calculateOwnedPastDueAmount(LocalDate)}.</li>
     * </ul>
     * The sum of base fees and overdue fees for all rentals is rounded to two
     * decimal places and stored in {@code totalPrice}.
     *
     * @param rentalDays the number of days each tape is rented (applied uniformly)
     * @param currentDate the date used for overdue calculations
     * @return the total price of the transaction, rounded to two decimal places
     */
    public double calculateTotalPrice(int rentalDays, LocalDate currentDate) {
        double sum = 0.0;
        double baseFee = rentalDays * 1.0; // $1 per day

        for (VideoRental rental : rentals) {
            double overdue = rental.calculateOwnedPastDueAmount(currentDate);
            sum += baseFee + overdue;
        }
        this.totalPrice = round(sum);
        return this.totalPrice;
    }

    /* ------------------- Getters & Setters ------------------- */

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
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

    /* ------------------- Utility ------------------- */

    private double round(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}