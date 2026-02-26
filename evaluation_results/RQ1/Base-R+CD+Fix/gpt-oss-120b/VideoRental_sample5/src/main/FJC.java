import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Repository that stores all rentals in the system.
 * It is used by {@link Tape} to check availability.
 */
class RentalRepository {
    private static final List<VideoRental> ALL_RENTALS = new ArrayList<>();

    public static List<VideoRental> getAllRentals() {
        return Collections.unmodifiableList(ALL_RENTALS);
    }

    public static void addRental(VideoRental rental) {
        ALL_RENTALS.add(rental);
    }
}

/**
 * Represents a customer of the video‑rental store.
 */
 class Customer {

    private String id;
    private List<VideoRental> rentals;

    /** Unparameterized constructor */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /** Parameterized constructor (convenient for tests) */
    public Customer(String id) {
        this.id = id;
        this.rentals = new ArrayList<>();
    }

    /* ---------------------------------------------------------------------- */
    /* Getters and Setters                                                    */
    /* ---------------------------------------------------------------------- */

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

    /* ---------------------------------------------------------------------- */
    /* Business methods                                                       */
    /* ---------------------------------------------------------------------- */

    /**
     * Attempts to add a new tape rental for this customer.
     *
     * @param tape        the tape the customer wants to rent
     * @param currentDate the date on which the rental is requested
     * @return {@code true} if the rental is successfully processed,
     *         {@code false} otherwise (e.g., customer already has 20 rentals,
     *         has past‑due amount, or the tape is unavailable)
     */
    public boolean addVedioTapeRental(Tape tape, LocalDate currentDate) {
        // 1. does the customer already have 20 active rentals?
        long activeRentals = rentals.stream()
                .filter(r -> r.getReturnDate() == null)
                .count();
        if (activeRentals >= 20) {
            return false;
        }

        // 2. does the customer owe any past‑due amount?
        double pastDue = calculateTotalPastDueAmount(currentDate);
        if (pastDue > 0.0) {
            return false;
        }

        // 3. is the tape available?
        if (!tape.isAvailable(currentDate)) {
            return false;
        }

        // 4. create the rental (default rental period = 7 days)
        LocalDate dueDate = currentDate.plusDays(7);
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalStartDate(currentDate);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        rental.setOwnedPastDueAmount(0.0);

        // 5. add to collections
        rentals.add(rental);
        RentalRepository.addRental(rental);
        return true;
    }

    /**
     * Returns a list of all tapes that this customer has rented but not yet returned.
     *
     * @return list of unreturned {@link Tape}s; empty list if none
     */
    public List<Tape> getUnreturnedTapes() {
        List<Tape> result = new ArrayList<>();
        for (VideoRental rental : rentals) {
            if (rental.getReturnDate() == null) {
                result.add(rental.getTape());
            }
        }
        return result;
    }

    /**
     * Calculates the total past‑due amount (overdue fees) the customer owes across
     * all of his/her rentals as of {@code currentDate}.
     *
     * @param currentDate the date used for the calculation
     * @return total overdue fee rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(LocalDate currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            total += rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(total * 100.0) / 100.0;
    }
}

/**
 * Represents a video tape in the inventory.
 */
class Tape {

    private String id;
    private String videoInformation;

    /** Unparameterized constructor */
    public Tape() {
    }

    /** Parameterized constructor (convenient for tests) */
    public Tape(String id, String videoInformation) {
        this.id = id;
        this.videoInformation = videoInformation;
    }

    /* ---------------------------------------------------------------------- */
    /* Getters and Setters                                                    */
    /* ---------------------------------------------------------------------- */

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

    /* ---------------------------------------------------------------------- */
    /* Business methods                                                       */
    /* ---------------------------------------------------------------------- */

    /**
     * Determines whether this tape is available for rent on {@code currentDate}.
     * A tape is unavailable if it belongs to any active rental whose return date
     * is {@code null}.
     *
     * @param currentDate the date for which availability is queried
     * @return {@code true} if the tape is not part of any active rental,
     *         {@code false} otherwise
     */
    public boolean isAvailable(LocalDate currentDate) {
        for (VideoRental rental : RentalRepository.getAllRentals()) {
            if (rental.getTape() != null && Objects.equals(rental.getTape().getId(), this.id)) {
                if (rental.getReturnDate() == null) {
                    // The tape is currently checked out
                    return false;
                }
            }
        }
        return true;
    }
}

/**
 * Represents a single video tape rental.
 */
class VideoRental {

    private LocalDate rentalStartDate; // when the tape was taken out
    private LocalDate dueDate;         // expected return date
    private LocalDate returnDate;      // actual return date (null if not returned)
    private double ownedPastDueAmount; // cached overdue amount (optional)
    private Tape tape;

    /** Unparameterized constructor */
    public VideoRental() {
    }

    /* ---------------------------------------------------------------------- */
    /* Getters and Setters                                                    */
    /* ---------------------------------------------------------------------- */

    public LocalDate getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(LocalDate rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

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

    /* ---------------------------------------------------------------------- */
    /* Business methods                                                       */
    /* ---------------------------------------------------------------------- */

    /**
     * Calculates the overdue fee for this rental as of {@code currentDate}.
     * <p>
     * Rules:
     * <ul>
     *   <li>If the tape has been returned on or before the due date, the fee is 0.</li>
     *   <li>If the tape has been returned after the due date, fee = overdue days × $0.50.</li>
     *   <li>If the tape is still out (returnDate == null), fee = (currentDate - dueDate) × $0.50.</li>
     * </ul>
     * The result is rounded to two decimal places.
     *
     * @param currentDate the date used for the calculation when the tape is still out
     * @return overdue fee rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(LocalDate currentDate) {
        LocalDate effectiveReturn = (returnDate != null) ? returnDate : currentDate;
        if (!effectiveReturn.isAfter(dueDate)) {
            return 0.0;
        }
        long overdueDays = ChronoUnit.DAYS.between(dueDate, effectiveReturn);
        double fee = overdueDays * 0.50;
        return Math.round(fee * 100.0) / 100.0;
    }

    /**
     * Calculates the base rental fee for this rental.
     * Base fee = number of rental days × $1.00.
     * Any partial day counts as a full day (dates are whole days, so we simply
     * count the days between the start date and the effective return date, inclusive).
     *
     * @param currentDate the date used for the calculation when the tape is still out
     * @return base rental fee rounded to two decimal places
     */
    public double calculateBaseRentalFee(LocalDate currentDate) {
        LocalDate effectiveReturn = (returnDate != null) ? returnDate : currentDate;
        long days = ChronoUnit.DAYS.between(rentalStartDate, effectiveReturn) + 1; // inclusive
        double fee = days * 1.0;
        return Math.round(fee * 100.0) / 100.0;
    }
}

/**
 * Represents a rental transaction that may consist of multiple {@link VideoRental}s.
 */
class RentalTransaction {

    private LocalDate rentalDate;
    private double totalPrice;
    private List<VideoRental> rentals;
    private Customer customer;

    /** Unparameterized constructor */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /** Parameterized constructor (convenient for tests) */
    public RentalTransaction(LocalDate rentalDate, Customer customer) {
        this.rentalDate = rentalDate;
        this.customer = customer;
        this.rentals = new ArrayList<>();
    }

    /* ---------------------------------------------------------------------- */
    /* Getters and Setters                                                    */
    /* ---------------------------------------------------------------------- */

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private void setTotalPrice(double totalPrice) {
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

    /* ---------------------------------------------------------------------- */
    /* Business methods                                                       */
    /* ---------------------------------------------------------------------- */

    /**
     * Calculates the total price for this transaction.
     * The total price is the sum of each rental's base fee plus its overdue fee.
     * The result is rounded to two decimal places and stored in {@code totalPrice}.
     *
     * @param rentalDate   the date the transaction started (normally the same as the first rental's start date)
     * @param currentDate  the date used for calculations for rentals that have not yet been returned
     * @return total price rounded to two decimal places
     */
    public double calculateTotalPrice(LocalDate rentalDate, LocalDate currentDate) {
        double sum = 0.0;
        for (VideoRental rental : rentals) {
            double base = rental.calculateBaseRentalFee(currentDate);
            double overdue = rental.calculateOwedPastDueAmount(currentDate);
            sum += base + overdue;
        }
        double rounded = Math.round(sum * 100.0) / 100.0;
        setTotalPrice(rounded);
        return rounded;
    }

    /**
     * Adds a {@link VideoRental} to this transaction.
     *
     * @param rental the rental to add
     */
    public void addRental(VideoRental rental) {
        this.rentals.add(rental);
    }
}