import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class that stores all rentals in the system.
 * It is used by {@link Tape} to determine if a tape is currently rented.
 */
class RentalStore {
    private static final List<VideoRental> ALL_RENTALS = new ArrayList<>();

    /**
     * Adds a new rental to the global store.
     *
     * @param rental the rental to be stored
     */
    public static void addRental(VideoRental rental) {
        ALL_RENTALS.add(rental);
    }

    /**
     * Returns an unmodifiable view of all rentals.
     *
     * @return list of all rentals
     */
    public static List<VideoRental> getAllRentals() {
        return Collections.unmodifiableList(ALL_RENTALS);
    }
}

/**
 * Represents a customer of the video rental store.
 */
 class Customer {

    private String id;
    private List<VideoRental> rentals;

    /** No‑argument constructor required for tests. */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /** Getter for id. */
    public String getId() {
        return id;
    }

    /** Setter for id. */
    public void setId(String id) {
        this.id = id;
    }

    /** Getter for rentals. */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /** Setter for rentals. */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Attempts to add a new video tape rental for this customer.
     *
     * <p>The method verifies three constraints before creating the rental:
     * <ul>
     *   <li>The customer has fewer than 20 active rentals.</li>
     *   <li>The customer does not owe any past‑due amount (as of {@code currentDate}).</li>
     *   <li>The requested tape is currently available.</li>
     * </ul>
     *
     * @param tape        the tape the customer wishes to rent
     * @param currentDate the date on which the rental is being made
     * @return {@code true} if the rental was successfully created; {@code false} otherwise
     */
    public boolean addVedioTapeRental(Tape tape, LocalDate currentDate) {
        // 1) Max 20 active rentals
        long activeRentals = rentals.stream()
                .filter(r -> r.getReturnDate() == null)
                .count();
        if (activeRentals >= 20) {
            return false;
        }

        // 2) No past‑due amount
        if (calculateTotalPastDueAmount(currentDate) > 0.0) {
            return false;
        }

        // 3) Tape availability
        if (!tape.isAvailable(currentDate)) {
            return false;
        }

        // Create a new rental
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalDate(currentDate);
        // For simplicity we assume a 7‑day rental period
        rental.setDueDate(currentDate.plusDays(7));
        rental.setReturnDate(null);
        rental.setOwnedPastDueAmount(0.0);

        rentals.add(rental);
        RentalStore.addRental(rental);
        return true;
    }

    /**
     * Retrieves a list of all tapes currently rented by the customer that have not yet been returned.
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
     * Calculates the total past‑due amount for all of the customer's rentals as of the supplied date.
     *
     * @param currentDate the date against which overdue fees are calculated
     * @return total past‑due amount rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(LocalDate currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            total += rental.calculateOwedPastDueAmount(currentDate);
        }
        return round(total);
    }

    /**
     * Helper method to round a double to two decimal places using {@link BigDecimal}.
     *
     * @param value the value to round
     * @return rounded value
     */
    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

/**
 * Represents a single video tape in the inventory.
 */
class Tape {

    private String id;
    private String videoInformation;

    /** No‑argument constructor required for tests. */
    public Tape() {
    }

    /** Getter for id. */
    public String getId() {
        return id;
    }

    /** Setter for id. */
    public void setId(String id) {
        this.id = id;
    }

    /** Getter for videoInformation. */
    public String getVideoInformation() {
        return videoInformation;
    }

    /** Setter for videoInformation. */
    public void setVideoInformation(String videoInformation) {
        this.videoInformation = videoInformation;
    }

    /**
     * Determines whether this tape is available for rental on the given date.
     *
     * <p>A tape is unavailable if it belongs to any active rental (i.e., a rental whose
     * {@code returnDate} is {@code null}).
     *
     * @param currentDate the date for which availability is being checked
     * @return {@code true} if the tape is not currently rented; {@code false} otherwise
     */
    public boolean isAvailable(LocalDate currentDate) {
        for (VideoRental rental : RentalStore.getAllRentals()) {
            if (rental.getTape() != null && rental.getTape().getId().equals(this.id)) {
                if (rental.getReturnDate() == null) {
                    // Tape is currently rented out
                    return false;
                }
            }
        }
        return true;
    }
}

/**
 * Represents a single video rental (one tape) belonging to a customer.
 */
class VideoRental {

    private LocalDate dueDate;
    private LocalDate returnDate;
    private double ownedPastDueAmount;
    private Tape tape;
    private LocalDate rentalDate; // added to compute base fee

    /** No‑argument constructor required for tests. */
    public VideoRental() {
    }

    /** Getter for dueDate. */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /** Setter for dueDate. */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /** Getter for returnDate. */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /** Setter for returnDate. */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /** Getter for ownedPastDueAmount. */
    public double getOwnedPastDueAmount() {
        return ownedPastDueAmount;
    }

    /** Setter for ownedPastDueAmount. */
    public void setOwnedPastDueAmount(double ownedPastDueAmount) {
        this.ownedPastDueAmount = ownedPastDueAmount;
    }

    /** Getter for tape. */
    public Tape getTape() {
        return tape;
    }

    /** Setter for tape. */
    public void setTape(Tape tape) {
        this.tape = tape;
    }

    /** Getter for rentalDate. */
    public LocalDate getRentalDate() {
        return rentalDate;
    }

    /** Setter for rentalDate. */
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    /**
     * Calculates the overdue fee for this rental as of {@code currentDate}.
     *
     * <p>If the tape has been returned, the fee is based on the actual return date.
     * If it has not yet been returned, the fee is based on the difference between
     * {@code currentDate} and the due date.
     *
     * @param currentDate the date against which the overdue fee is calculated
     * @return overdue fee rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(LocalDate currentDate) {
        LocalDate effectiveReturnDate = (returnDate != null) ? returnDate : currentDate;

        if (!effectiveReturnDate.isAfter(dueDate)) {
            return 0.0;
        }

        long overdueDays = ChronoUnit.DAYS.between(dueDate, effectiveReturnDate);
        double fee = overdueDays * 0.5;
        return round(fee);
    }

    /**
     * Calculates the base rental fee for this rental.
     *
     * <p>The base fee equals the number of rental days (rounded up to the next whole day)
     * multiplied by $1.00 per day.
     *
     * @param currentDate the date used when the tape has not yet been returned
     * @return base rental fee rounded to two decimal places
     */
    public double calculateBaseRentalFee(LocalDate currentDate) {
        LocalDate endDate = (returnDate != null) ? returnDate : currentDate;
        long days = ChronoUnit.DAYS.between(rentalDate, endDate) + 1; // inclusive
        double fee = days * 1.0;
        return round(fee);
    }

    /**
     * Helper method to round a double to two decimal places.
     *
     * @param value the value to round
     * @return rounded value
     */
    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

/**
 * Represents a transaction that may contain multiple rentals for a single customer.
 */
class RentalTransaction {

    private LocalDate rentalDate;
    private double totalPrice;
    private List<VideoRental> rentals;
    private Customer customer;

    /** No‑argument constructor required for tests. */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /** Getter for rentalDate. */
    public LocalDate getRentalDate() {
        return rentalDate;
    }

    /** Setter for rentalDate. */
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    /** Getter for totalPrice. */
    public double getTotalPrice() {
        return totalPrice;
    }

    /** Setter for totalPrice. */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /** Getter for rentals. */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /** Setter for rentals. */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /** Getter for customer. */
    public Customer getCustomer() {
        return customer;
    }

    /** Setter for customer. */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Calculates the total price for the transaction.
     *
     * <p>The total price is the sum, for each rental, of:
     * <ul>
     *   <li>Base rental fee (see {@link VideoRental#calculateBaseRentalFee(LocalDate)})</li>
     *   <li>Any overdue fee (see {@link VideoRental#calculateOwedPastDueAmount(LocalDate)})</li>
     * </ul>
     *
     * @param rentalDate  the date the transaction started (often the same as each rental's rentalDate)
     * @param currentDate the date used for calculations when a tape has not yet been returned
     * @return total transaction price rounded to two decimal places
     */
    public double calculateTotalPrice(LocalDate rentalDate, LocalDate currentDate) {
        double sum = 0.0;
        for (VideoRental rental : rentals) {
            double base = rental.calculateBaseRentalFee(currentDate);
            double overdue = rental.calculateOwedPastDueAmount(currentDate);
            sum += base + overdue;
        }
        this.totalPrice = round(sum);
        return this.totalPrice;
    }

    /**
     * Helper method to round a double to two decimal places.
     *
     * @param value the value to round
     * @return rounded value
     */
    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}