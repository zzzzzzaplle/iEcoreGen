import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Simple in‑memory repository that keeps track of all video rentals.
 * It is used by {@link Tape#isAvailable(LocalDate)} to determine if a tape is
 * currently rented out.
 */
final class RentalRepository {

    private static final List<VideoRental> ALL_RENTALS = new ArrayList<>();

    private RentalRepository() {
        // utility class – no instances
    }

    /**
     * Adds a new rental to the repository.
     *
     * @param rental the rental to store
     */
    public static void addRental(VideoRental rental) {
        ALL_RENTALS.add(rental);
    }

    /**
     * Returns an unmodifiable view of all rentals stored in the repository.
     *
     * @return list of all rentals
     */
    public static List<VideoRental> getAllRentals() {
        return Collections.unmodifiableList(ALL_RENTALS);
    }
}

/**
 * Represents a customer of the video‑rental store.
 */
 class Customer {

    /** Unique identifier of the customer (e.g., id‑card barcode). */
    private String id;

    /** All rentals that belong to this customer. */
    private List<VideoRental> rentals;

    /** Unparameterized constructor required by the task. */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Adds a video tape rental for this customer after performing all business
     * rule checks.
     *
     * @param tape        the tape to rent
     * @param currentDate the date on which the rental is created
     * @return {@code true} if the rental was successfully added; {@code false}
     * otherwise
     */
    public boolean addVedioTapeRental(Tape tape, LocalDate currentDate) {
        // Rule 1 – maximum of 20 outstanding rentals
        long activeRentals = rentals.stream()
                .filter(r -> r.getReturnDate() == null)
                .count();
        if (activeRentals >= 20) {
            return false;
        }

        // Rule 2 – no past‑due amount outstanding
        double totalPastDue = calculateTotalPastDueAmount(currentDate);
        if (totalPastDue > 0.0) {
            return false;
        }

        // Rule 3 – tape must be available
        if (!tape.isAvailable(currentDate)) {
            return false;
        }

        // For this example we assume a standard rental period of 7 days.
        LocalDate dueDate = currentDate.plusDays(7);
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null); // not yet returned
        // ownedPastDueAmount will be calculated on demand, initialise to 0.
        rental.setOwnedPastDueAmount(0.0);

        // Store rental
        rentals.add(rental);
        RentalRepository.addRental(rental);
        return true;
    }

    /**
     * Retrieves a list of all tapes that have been rented by this customer but
     * not yet returned.
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
     * Calculates the sum of past‑due amounts for all rentals belonging to this
     * customer.
     *
     * @param currentDate the date used for the calculation (usually today)
     * @return total past‑due amount rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(LocalDate currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            total += rental.calculateOwnedPastDueAmount(currentDate);
        }
        return round(total);
    }

    /** @return the customer's identifier */
    public String getId() {
        return id;
    }

    /** @param id the identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the list of rentals */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /** @param rentals the rentals list to set */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Helper method to round a double to two decimal places using
     * {@link BigDecimal}.
     *
     * @param value value to round
     * @return rounded value
     */
    private static double round(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

/**
 * Represents a video tape in the inventory.
 */
class Tape {

    /** Barcode / unique identifier of the tape. */
    private String id;

    /** Human‑readable description of the video (title, format, etc.). */
    private String videoInformation;

    /** Unparameterized constructor required by the task. */
    public Tape() {
    }

    /**
     * Determines whether the tape is available for rental on the given date.
     * A tape is unavailable if it belongs to any active rental whose return
     * date is {@code null}.
     *
     * @param currentDate the date on which availability is checked (not used
     *                    in the current logic but kept for signature compatibility)
     * @return {@code true} if the tape is not currently rented out; {@code false}
     * otherwise
     */
    public boolean isAvailable(LocalDate currentDate) {
        for (VideoRental rental : RentalRepository.getAllRentals()) {
            if (rental.getTape().getId().equals(this.id) && rental.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }

    /** @return the tape identifier */
    public String getId() {
        return id;
    }

    /** @param id the identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return video information string */
    public String getVideoInformation() {
        return videoInformation;
    }

    /** @param videoInformation the video information to set */
    public void setVideoInformation(String videoInformation) {
        this.videoInformation = videoInformation;
    }
}

/**
 * Represents a single video rental (one tape rented by a customer).
 */
class VideoRental {

    /** Date the rental is due (expected return date). */
    private LocalDate dueDate;

    /** Actual date the tape was returned; {@code null} if not yet returned. */
    private LocalDate returnDate;

    /** Past‑due amount that has already been calculated and possibly paid. */
    private double ownedPastDueAmount;

    /** The tape that is being rented. */
    private Tape tape;

    /** Unparameterized constructor required by the task. */
    public VideoRental() {
    }

    /**
     * Calculates the past‑due amount for this rental as of {@code currentDate}.
     *
     * <p>Rules:
     * <ul>
     *   <li>If the tape has been returned on or before the due date, the fee is 0.</li>
     *   <li>If the tape has been returned after the due date, the fee is
     *   {@code overdueDays × $0.50}.</li>
     *   <li>If the tape has not been returned, the fee is calculated using
     *   {@code (currentDate – dueDate) × $0.50}.</li>
     * </ul>
     *
     * @param currentDate the date used for calculation (typically today)
     * @return overdue fee rounded to two decimal places
     */
    public double calculateOwnedPastDueAmount(LocalDate currentDate) {
        // If already settled, return stored amount
        if (ownedPastDueAmount > 0) {
            return round(ownedPastDueAmount);
        }

        LocalDate effectiveReturnDate = (returnDate != null) ? returnDate : currentDate;

        if (!effectiveReturnDate.isAfter(dueDate)) {
            return 0.0;
        }

        long overdueDays = Period.between(dueDate, effectiveReturnDate).getDays();
        // Period may return months/years, so compute total days correctly
        overdueDays = java.time.temporal.ChronoUnit.DAYS.between(dueDate, effectiveReturnDate);

        double fee = overdueDays * 0.50;
        return round(fee);
    }

    /** @return the due date */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /** @param dueDate the due date to set */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /** @return the return date (may be {@code null}) */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /** @param returnDate the return date to set */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /** @return the owned past‑due amount */
    public double getOwnedPastDueAmount() {
        return ownedPastDueAmount;
    }

    /** @param ownedPastDueAmount the past‑due amount to set */
    public void setOwnedPastDueAmount(double ownedPastDueAmount) {
        this.ownedPastDueAmount = ownedPastDueAmount;
    }

    /** @return the tape associated with this rental */
    public Tape getTape() {
        return tape;
    }

    /** @param tape the tape to associate */
    public void setTape(Tape tape) {
        this.tape = tape;
    }

    /**
     * Helper method to round a double to two decimal places.
     *
     * @param value value to round
     * @return rounded value
     */
    private static double round(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

/**
 * Represents a collection of rentals that belong to a single transaction.
 */
class RentalTransaction {

    /** Date when the transaction was created. */
    private LocalDate rentalDate;

    /** Total price of the transaction (base fees + overdue fees). */
    private double totalPrice;

    /** All rentals that belong to this transaction. */
    private List<VideoRental> rentals;

    /** Customer who performed the transaction. */
    private Customer customer;

    /** Unparameterized constructor required by the task. */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Calculates the total price for the transaction.
     *
     * <p>Base rental fee = {@code rentalDays × $1.00}. Any partial day counts as a
     * full day. Overdue fee for each rental is obtained from
     * {@link VideoRental#calculateOwnedPastDueAmount(LocalDate)}.
     *
     * @param rentalDays  number of days the customer intends to keep each tape
     * @param currentDate the date used for overdue‑fee calculation (usually today)
     * @return total price rounded to two decimal places
     */
    public double calculateTotalPrice(int rentalDays, LocalDate currentDate) {
        double baseFee = rentalDays * 1.0; // $1 per day per tape
        double sum = 0.0;
        for (VideoRental rental : rentals) {
            double overdue = rental.calculateOwnedPastDueAmount(currentDate);
            sum += baseFee + overdue;
        }
        this.totalPrice = round(sum);
        return this.totalPrice;
    }

    /** @return the rental date */
    public LocalDate getRentalDate() {
        return rentalDate;
    }

    /** @param rentalDate the date to set */
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    /** @return the total price */
    public double getTotalPrice() {
        return totalPrice;
    }

    /** @param totalPrice the total price to set */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /** @return the list of rentals */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /** @param rentals the rentals to set */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /** @return the customer */
    public Customer getCustomer() {
        return customer;
    }

    /** @param customer the customer to set */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Helper method to round a double to two decimal places.
     *
     * @param value value to round
     * @return rounded value
     */
    private static double round(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}