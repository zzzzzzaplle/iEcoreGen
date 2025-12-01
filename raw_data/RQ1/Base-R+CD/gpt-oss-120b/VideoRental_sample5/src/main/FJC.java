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

    /** Unique identifier of the customer (e.g., id card number). */
    private String id;

    /** List of video rentals belonging to the customer (max 20). */
    private List<VideoRental> rentals;

    /** No‑argument constructor (required by the task). */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /** Gets the customer id. */
    public String getId() {
        return id;
    }

    /** Sets the customer id. */
    public void setId(String id) {
        this.id = id;
    }

    /** Gets the rentals list (modifiable). */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /** Sets the rentals list (replaces current list). */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Adds a video tape rental for this customer.
     *
     * <p>The method checks three business rules before creating the rental:
     * <ul>
     *   <li>The customer must have fewer than 20 active rentals.</li>
     *   <li>The customer must have no past‑due amount at the moment of rental.</li>
     *   <li>The requested tape must be available (i.e., not currently rented out).</li>
     * </ul>
     *
     * @param tape        the tape to be rented
     * @param currentDate the date on which the rental is being made
     * @return {@code true} if the rental was successfully added; {@code false}
     *         otherwise (any of the checks failed)
     */
    public boolean addVedioTapeRental(Tape tape, LocalDate currentDate) {
        // check maximum rentals
        if (rentals.size() >= 20) {
            return false;
        }

        // check past‑due amount
        double pastDue = calculateTotalPastDueAmount(currentDate);
        if (pastDue > 0.0) {
            return false;
        }

        // check tape availability
        if (!tape.isAvailable(currentDate)) {
            return false;
        }

        // create a new rental; due date is set to 7 days after rental date
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(currentDate.plusDays(7));
        rental.setReturnDate(null); // not yet returned
        rental.setOwnedPastDueAmount(0.0);

        // register rental
        rentals.add(rental);
        VideoRental.registerRental(rental);
        return true;
    }

    /**
     * Retrieves a list of all tapes that have been rented by this customer but
     * not yet returned.
     *
     * @return an immutable list of {@link Tape} objects that are still out; an
     *         empty list if there are none.
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
     * Calculates the total past‑due amount for this customer across all active
     * rentals.
     *
     * @param currentDate the date used for overdue calculation
     * @return total overdue fee rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(LocalDate currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            total += rental.calculateOwnedPastDueAmount(currentDate);
        }
        return round(total);
    }

    /** Helper to round a double to two decimal places. */
    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
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

    /** Holds a reference to every rental ever created – used for availability checks. */
    private static final List<VideoRental> ALL_RENTALS = new ArrayList<>();

    /** No‑argument constructor. */
    public Tape() {
    }

    /** Gets the tape id. */
    public String getId() {
        return id;
    }

    /** Sets the tape id. */
    public void setId(String id) {
        this.id = id;
    }

    /** Gets the video information. */
    public String getVideoInformation() {
        return videoInformation;
    }

    /** Sets the video information. */
    public void setVideoInformation(String videoInformation) {
        this.videoInformation = videoInformation;
    }

    /**
     * Checks whether this tape is available on the given date.
     *
     * <p>A tape is unavailable if there exists any active {@link VideoRental}
     * (i.e., a rental whose {@code returnDate} is {@code null}) that references
     * this tape. Otherwise the tape is available.
     *
     * @param currentDate the date for which availability is queried (currently
     *                    not used, but kept for signature compatibility)
     * @return {@code true} if the tape is free to be rented; {@code false}
     *         otherwise
     */
    public boolean isAvailable(LocalDate currentDate) {
        for (VideoRental rental : ALL_RENTALS) {
            if (rental.getTape() == this && rental.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }

    /** Package‑private method used by {@link VideoRental} to register a new rental. */
    static void registerRental(VideoRental rental) {
        ALL_RENTALS.add(rental);
    }
}

/**
 * Represents a single video tape rental.
 */
class VideoRental {

    /** Date by which the tape should be returned (due date). */
    private LocalDate dueDate;

    /** Actual return date; {@code null} if not yet returned. */
    private LocalDate returnDate;

    /** Overdue fee that has been calculated (cached). */
    private double ownedPastDueAmount;

    /** The tape that is being rented. */
    private Tape tape;

    /** No‑argument constructor. */
    public VideoRental() {
    }

    /** Gets the due date. */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /** Sets the due date. */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /** Gets the actual return date. */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /** Sets the actual return date. */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /** Gets the cached overdue amount. */
    public double getOwnedPastDueAmount() {
        return ownedPastDueAmount;
    }

    /** Sets the cached overdue amount. */
    public void setOwnedPastDueAmount(double ownedPastDueAmount) {
        this.ownedPastDueAmount = ownedPastDueAmount;
    }

    /** Gets the tape associated with this rental. */
    public Tape getTape() {
        return tape;
    }

    /** Sets the tape associated with this rental. */
    public void setTape(Tape tape) {
        this.tape = tape;
    }

    /**
     * Calculates the overdue fee for this rental as of {@code currentDate}.
     *
     * <p>The rules are:
     * <ul>
     *   <li>If the tape has been returned on or before the due date, the fee is {@code 0}.</li>
     *   <li>If the tape has been returned after the due date, the fee is
     *   {@code (daysLate × 0.5)}.</li>
     *   <li>If the tape has not yet been returned, the fee is calculated using
     *   the difference between {@code currentDate} and the due date.</li>
     * </ul>
     *
     * @param currentDate the date used for calculating overdue fee when the tape
     *                    is still out
     * @return overdue fee rounded to two decimal places
     */
    public double calculateOwnedPastDueAmount(LocalDate currentDate) {
        LocalDate effectiveReturn = (returnDate != null) ? returnDate : currentDate;
        long daysLate = ChronoUnit.DAYS.between(dueDate, effectiveReturn);
        double fee = 0.0;
        if (daysLate > 0) {
            fee = daysLate * 0.5;
        }
        // cache the computed value
        ownedPastDueAmount = round(fee);
        return ownedPastDueAmount;
    }

    /** Helper to round a double to two decimal places. */
    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /** Registers this rental in the global list used by {@link Tape}. */
    public void register() {
        Tape.registerRental(this);
    }
}

/**
 * Represents a complete rental transaction (could contain multiple
 * {@link VideoRental}s).
 */
class RentalTransaction {

    /** Date when the transaction was created. */
    private LocalDate rentalDate;

    /** Total price for the transaction (cached). */
    private double totalPrice;

    /** List of video rentals belonging to this transaction. */
    private List<VideoRental> rentals;

    /** Customer who performed the transaction. */
    private Customer customer;

    /** No‑argument constructor. */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /** Gets the rental date. */
    public LocalDate getRentalDate() {
        return rentalDate;
    }

    /** Sets the rental date. */
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    /** Gets the total price (cached). */
    public double getTotalPrice() {
        return totalPrice;
    }

    /** Sets the total price (cached). */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /** Gets the list of rentals. */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /** Sets the list of rentals. */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /** Gets the customer. */
    public Customer getCustomer() {
        return customer;
    }

    /** Sets the customer. */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Calculates the total price for this transaction.
     *
     * <p>For each {@link VideoRental} in the transaction the price consists of:
     * <ul>
     *   <li>Base rental fee = {@code rentalDays} × $1.00 (any partial day counts as a full day).</li>
     *   <li>Overdue fee as returned by {@link VideoRental#calculateOwnedPastDueAmount(LocalDate)}.</li>
     * </ul>
     *
     * The sum of all rental prices is rounded to two decimal places and stored
     * in {@code totalPrice}.
     *
     * @param rentalDays number of days the customer intends to keep each tape
     * @param currentDate the date used for overdue calculations
     * @return total price for the transaction rounded to two decimal places
     */
    public double calculateTotalPrice(int rentalDays, LocalDate currentDate) {
        double sum = 0.0;
        double baseFee = rentalDays * 1.0; // $1 per day, partial days already whole

        for (VideoRental rental : rentals) {
            double overdue = rental.calculateOwnedPastDueAmount(currentDate);
            sum += baseFee + overdue;
        }

        totalPrice = round(sum);
        return totalPrice;
    }

    /** Helper to round a double to two decimal places. */
    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}