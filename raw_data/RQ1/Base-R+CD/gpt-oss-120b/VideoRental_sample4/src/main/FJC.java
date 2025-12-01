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

    /** Unique identifier of the customer (e.g., id card barcode). */
    private String id;

    /** All rentals (active or completed) belonging to this customer. */
    private List<VideoRental> rentals = new ArrayList<>();

    /** No‑argument constructor required for tests and frameworks. */
    public Customer() {
    }

    /**
     * Constructs a Customer with a given identifier.
     *
     * @param id the customer identifier
     */
    public Customer(String id) {
        this.id = id;
    }

    /** Getter for {@link #id}. */
    public String getId() {
        return id;
    }

    /** Setter for {@link #id}. */
    public void setId(String id) {
        this.id = id;
    }

    /** Getter for {@link #rentals}. */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /** Setter for {@link #rentals}. */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Attempts to add a new tape rental for this customer.
     * <p>
     * The method checks three business rules before creating the rental:
     * <ul>
     *   <li>The customer must have fewer than 20 active rentals.</li>
     *   <li>The customer must not have any past‑due amount outstanding.</li>
     *   <li>The requested tape must be available on the given date.</li>
     * </ul>
     * If all checks pass a new {@link VideoRental} is created (with a default
     * rental period of 7 days) and added to the customer’s rental list.
     *
     * @param tape        the tape the customer wants to rent
     * @param currentDate the date on which the rental is attempted
     * @return {@code true} if the rental was successfully added; {@code false}
     *         otherwise
     */
    public boolean addVedioTapeRental(Tape tape, LocalDate currentDate) {
        // 1. Check maximum active rentals (returnDate == null)
        long activeCount = rentals.stream()
                .filter(r -> r.getReturnDate() == null)
                .count();
        if (activeCount >= 20) {
            return false;
        }

        // 2. Check for any past‑due amount
        double totalPastDue = calculateTotalPastDueAmount(currentDate);
        if (totalPastDue > 0.0) {
            return false;
        }

        // 3. Verify tape availability
        if (!tape.isAvailable(currentDate)) {
            return false;
        }

        // All checks passed – create a new rental (default period = 7 days)
        LocalDate dueDate = currentDate.plusDays(7);
        VideoRental rental = new VideoRental(dueDate, null, 0.0, tape);
        rentals.add(rental);
        VideoRental.registerRental(rental);
        return true;
    }

    /**
     * Retrieves a list of all tapes that are currently rented by this customer
     * and have not yet been returned.
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
     * Calculates the total past‑due amount for this customer across all rentals,
     * using the supplied {@code currentDate} to evaluate overdue fees.
     *
     * @param currentDate the date to be used for overdue calculations
     * @return the summed past‑due amount, rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(LocalDate currentDate) {
        double sum = 0.0;
        for (VideoRental rental : rentals) {
            sum += rental.calculateOwnedPastDueAmount(currentDate);
        }
        return round(sum);
    }

    /**
     * Helper method to round a {@code double} to two decimal places using
     * {@link BigDecimal}.
     *
     * @param value the value to round
     * @return the rounded value
     */
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

    /** No‑argument constructor required for tests and frameworks. */
    public Tape() {
    }

    /**
     * Constructs a Tape with the given id and information.
     *
     * @param id                the barcode id
     * @param videoInformation  description of the video
     */
    public Tape(String id, String videoInformation) {
        this.id = id;
        this.videoInformation = videoInformation;
    }

    /** Getter for {@link #id}. */
    public String getId() {
        return id;
    }

    /** Setter for {@link #id}. */
    public void setId(String id) {
        this.id = id;
    }

    /** Getter for {@link #videoInformation}. */
    public String getVideoInformation() {
        return videoInformation;
    }

    /** Setter for {@link #videoInformation}. */
    public void setVideoInformation(String videoInformation) {
        this.videoInformation = videoInformation;
    }

    /**
     * Determines whether this tape is available for rental on the supplied date.
     * A tape is unavailable if there exists any active {@link VideoRental}
     * (i.e., a rental whose {@code returnDate} is {@code null}) that references
     * this tape.
     *
     * @param currentDate the date on which availability is checked (currently
     *                    unused but kept for signature compatibility)
     * @return {@code true} if the tape is not currently rented out; {@code false}
     *         otherwise
     */
    public boolean isAvailable(LocalDate currentDate) {
        for (VideoRental rental : VideoRental.getAllRentals()) {
            if (rental.getTape().equals(this) && rental.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Tape)) return false;
        Tape other = (Tape) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}

/**
 * Represents a single video tape rental.
 */
class VideoRental {

    /** Date by which the tape must be returned. */
    private LocalDate dueDate;

    /** Actual return date; {@code null} if not yet returned. */
    private LocalDate returnDate;

    /** Past‑due amount already owned (used for persistence, not calculation). */
    private double ownedPastDueAmount;

    /** The tape that is being rented. */
    private Tape tape;

    /** Holds every rental ever created – used for lookup in {@link Tape}. */
    private static final List<VideoRental> ALL_RENTALS = new ArrayList<>();

    /** No‑argument constructor required for tests and frameworks. */
    public VideoRental() {
    }

    /**
     * Constructs a VideoRental with the supplied data.
     *
     * @param dueDate            the due date
     * @param returnDate         the actual return date (may be {@code null})
     * @param ownedPastDueAmount past‑due amount already recorded
     * @param tape               the rented tape
     */
    public VideoRental(LocalDate dueDate, LocalDate returnDate,
                       double ownedPastDueAmount, Tape tape) {
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.ownedPastDueAmount = ownedPastDueAmount;
        this.tape = tape;
    }

    /** Getter for {@link #dueDate}. */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /** Setter for {@link #dueDate}. */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /** Getter for {@link #returnDate}. */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /** Setter for {@link #returnDate}. */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /** Getter for {@link #ownedPastDueAmount}. */
    public double getOwnedPastDueAmount() {
        return ownedPastDueAmount;
    }

    /** Setter for {@link #ownedPastDueAmount}. */
    public void setOwnedPastDueAmount(double ownedPastDueAmount) {
        this.ownedPastDueAmount = ownedPastDueAmount;
    }

    /** Getter for {@link #tape}. */
    public Tape getTape() {
        return tape;
    }

    /** Setter for {@link #tape}. */
    public void setTape(Tape tape) {
        this.tape = tape;
    }

    /**
     * Calculates the overdue fee for this rental as of {@code currentDate}.
     * <p>
     * Rules:
     * <ul>
     *   <li>If the tape has been returned on or before the due date, fee = 0.</li>
     *   <li>If returned after the due date, fee = (days late) × $0.50.</li>
     *   <li>If not yet returned, fee = (days between {@code currentDate}
     *       and due date) × $0.50.</li>
     * </ul>
     *
     * @param currentDate the date used for calculation when the tape is still
     *                    out
     * @return the overdue fee, rounded to two decimal places
     */
    public double calculateOwnedPastDueAmount(LocalDate currentDate) {
        LocalDate effectiveReturn = (returnDate != null) ? returnDate : currentDate;
        long daysLate = ChronoUnit.DAYS.between(dueDate, effectiveReturn);
        double fee = 0.0;
        if (daysLate > 0) {
            fee = daysLate * 0.5;
        }
        return round(fee);
    }

    /**
     * Registers a newly created rental in the global list used for availability
     * checks.
     *
     * @param rental the rental to register
     */
    public static void registerRental(VideoRental rental) {
        ALL_RENTALS.add(rental);
    }

    /**
     * Returns an unmodifiable view of all rentals ever created.
     *
     * @return list of rentals
     */
    public static List<VideoRental> getAllRentals() {
        return Collections.unmodifiableList(ALL_RENTALS);
    }

    /**
     * Helper method to round a double to two decimal places.
     *
     * @param value value to round
     * @return rounded value
     */
    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

/**
 * Represents a complete rental transaction (one visit to the store) which may
 * contain several {@link VideoRental} items.
 */
class RentalTransaction {

    /** Date when the transaction started. */
    private LocalDate rentalDate;

    /** Total price of the transaction (including overdue fees). */
    private double totalPrice;

    /** All rentals that belong to this transaction. */
    private List<VideoRental> rentals = new ArrayList<>();

    /** Customer who performed the transaction. */
    private Customer customer;

    /** No‑argument constructor required for tests and frameworks. */
    public RentalTransaction() {
    }

    /**
     * Constructs a RentalTransaction for a given customer and date.
     *
     * @param rentalDate the date of the transaction
     * @param customer   the customer involved
     */
    public RentalTransaction(LocalDate rentalDate, Customer customer) {
        this.rentalDate = rentalDate;
        this.customer = customer;
    }

    /** Getter for {@link #rentalDate}. */
    public LocalDate getRentalDate() {
        return rentalDate;
    }

    /** Setter for {@link #rentalDate}. */
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    /** Getter for {@link #totalPrice}. */
    public double getTotalPrice() {
        return totalPrice;
    }

    /** Setter for {@link #totalPrice}. */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /** Getter for {@link #rentals}. */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /** Setter for {@link #rentals}. */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /** Getter for {@link #customer}. */
    public Customer getCustomer() {
        return customer;
    }

    /** Setter for {@link #customer}. */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Calculates the total price of this transaction.
     * <p>
     * For each rental:
     * <ul>
     *   <li>Base rental fee = {@code rentalDays} × $1.00 (partial days count as a full day).</li>
     *   <li>Overdue fee = value returned by {@link VideoRental#calculateOwnedPastDueAmount(LocalDate)}.</li>
     * </ul>
     * The sum of all base fees plus overdue fees is returned, rounded to two
     * decimal places.
     *
     * @param rentalDays number of days each tape is rented for (same for all rentals)
     * @param currentDate the date used for overdue calculations
     * @return the total price of the transaction, rounded to two decimals
     */
    public double calculateTotalPrice(int rentalDays, LocalDate currentDate) {
        double sum = 0.0;
        double baseFeePerRental = rentalDays * 1.0; // $1 per day
        for (VideoRental rental : rentals) {
            double overdue = rental.calculateOwnedPastDueAmount(currentDate);
            sum += baseFeePerRental + overdue;
        }
        this.totalPrice = round(sum);
        return this.totalPrice;
    }

    /**
     * Helper method to round a double to two decimal places.
     *
     * @param value value to round
     * @return rounded value
     */
    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}