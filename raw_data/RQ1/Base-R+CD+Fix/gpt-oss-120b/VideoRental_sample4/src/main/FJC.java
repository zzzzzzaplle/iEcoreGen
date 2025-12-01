import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a video tape in the inventory.
 */
 class Tape {

    private String id;
    private String videoInformation;

    /**
     * Holds all rentals ever created. Used by {@link #isAvailable(LocalDate)} to
     * determine if the tape is currently rented out.
     */
    private static final List<VideoRental> ALL_RENTALS = new ArrayList<>();

    /** No‑arg constructor required for test code. */
    public Tape() {
    }

    /** Full constructor for convenience. */
    public Tape(String id, String videoInformation) {
        this.id = id;
        this.videoInformation = videoInformation;
    }

    /** Registers a rental that involves this tape. Called by {@link Customer#addVedioTapeRental(Tape, LocalDate)}. */
    static void registerRental(VideoRental rental) {
        ALL_RENTALS.add(rental);
    }

    /**
     * Checks whether this tape is available on the supplied date.
     *
     * @param currentDate the date on which availability is checked
     * @return {@code true} if the tape is not part of any active (unreturned) rental,
     *         {@code false} otherwise
     */
    public boolean isAvailable(LocalDate currentDate) {
        for (VideoRental rental : ALL_RENTALS) {
            if (rental.getTape() == this && rental.getReturnDate() == null) {
                // tape is currently rented out and not yet returned
                return false;
            }
        }
        return true;
    }

    // -------------------------------------------------------------------------
    // Getters & Setters
    // -------------------------------------------------------------------------

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
 * Represents a customer of the video rental store.
 */
 class Customer {

    private String id;
    private List<VideoRental> rentals = new ArrayList<>();

    /** No‑arg constructor required for test code. */
    public Customer() {
    }

    /** Full constructor for convenience. */
    public Customer(String id) {
        this.id = id;
    }

    /**
     * Attempts to add a new tape rental for this customer.
     *
     * @param tape        the tape the customer wants to rent
     * @param currentDate the date on which the rental is attempted
     * @return {@code true} if the rental is successful; {@code false} otherwise
     */
    public boolean addVedioTapeRental(Tape tape, LocalDate currentDate) {
        // 1. Customer may not have more than 20 active rentals
        long activeRentals = rentals.stream()
                .filter(r -> r.getReturnDate() == null)
                .count();
        if (activeRentals >= 20) {
            return false;
        }

        // 2. Customer must have no past‑due amount outstanding
        double pastDue = calculateTotalPastDueAmount(currentDate);
        if (pastDue > 0.0) {
            return false;
        }

        // 3. Tape must be available
        if (!tape.isAvailable(currentDate)) {
            return false;
        }

        // 4. Create a new rental (due date = currentDate + 7 days as an example)
        LocalDate dueDate = currentDate.plusDays(7);
        VideoRental rental = new VideoRental(currentDate, dueDate, tape);
        rentals.add(rental);
        Tape.registerRental(rental);
        return true;
    }

    /**
     * Returns a list of all tapes that are currently rented out (i.e. not yet returned)
     * for this customer.
     *
     * @return an immutable list of unreturned tapes; empty if none exist
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
     * Calculates the total past‑due amount the customer owes as of {@code currentDate}.
     *
     * @param currentDate the date used for the calculation
     * @return the sum of all overdue fees, rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(LocalDate currentDate) {
        double sum = 0.0;
        for (VideoRental rental : rentals) {
            sum += rental.calculateOwedPastDueAmount(currentDate);
        }
        return round(sum);
    }

    // -------------------------------------------------------------------------
    // Getters & Setters
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Utility
    // -------------------------------------------------------------------------

    private static double round(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

/**
 * Represents a single video tape rental.
 */
 class VideoRental {

    private LocalDate rentalStartDate;
    private LocalDate dueDate;
    private LocalDate returnDate;          // null if not yet returned
    private double ownedPastDueAmount;     // cached overdue amount (optional)
    private Tape tape;

    /** No‑arg constructor required for test code. */
    public VideoRental() {
    }

    /**
     * Constructs a new rental.
     *
     * @param rentalStartDate the date the rental started (normally the transaction date)
     * @param dueDate         the date the tape is due back
     * @param tape             the tape being rented
     */
    public VideoRental(LocalDate rentalStartDate, LocalDate dueDate, Tape tape) {
        this.rentalStartDate = rentalStartDate;
        this.dueDate = dueDate;
        this.tape = tape;
    }

    /**
     * Calculates the overdue fee for this rental as of {@code currentDate}.
     *
     * <p>Rules:
     * <ul>
     *   <li>If {@code returnDate} ≤ {@code dueDate} → fee = 0</li>
     *   <li>If {@code returnDate} > {@code dueDate} → fee = (days overdue) × $0.50</li>
     *   <li>If the tape has not been returned (returnDate == null) →
     *       fee = (days between {@code currentDate} and {@code dueDate}) × $0.50</li>
     * </ul>
     *
     * @param currentDate the date used when the tape is still out
     * @return overdue fee rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(LocalDate currentDate) {
        LocalDate effectiveReturn = (returnDate == null) ? currentDate : returnDate;
        if (!effectiveReturn.isAfter(dueDate)) {
            ownedPastDueAmount = 0.0;
            return 0.0;
        }
        long overdueDays = ChronoUnit.DAYS.between(dueDate, effectiveReturn);
        double fee = overdueDays * 0.50;
        ownedPastDueAmount = round(fee);
        return ownedPastDueAmount;
    }

    /**
     * Calculates the base rental fee for this rental.
     *
     * <p>Base fee = number of rental days × $1.00.
     * Any partial day counts as a full day (i.e., ceiling of the day count).
     *
     * @param currentDate the date used to determine the rental period if the tape is still out
     * @return base rental fee rounded to two decimal places
     */
    public double calculateBaseRentalFee(LocalDate currentDate) {
        LocalDate end = (returnDate == null) ? currentDate : returnDate;
        long days = ChronoUnit.DAYS.between(rentalStartDate, end);
        // add 1 to count the starting day as a full day (partial days count as full)
        days = days + 1;
        double fee = days * 1.0;
        return round(fee);
    }

    // -------------------------------------------------------------------------
    // Getters & Setters
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Utility
    // -------------------------------------------------------------------------

    private static double round(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

/**
 * Represents a rental transaction (a collection of one or more tape rentals).
 */
 class RentalTransaction {

    private LocalDate rentalDate;
    private double totalPrice;
    private List<VideoRental> rentals = new ArrayList<>();
    private Customer customer;

    /** No‑arg constructor required for test code. */
    public RentalTransaction() {
    }

    /**
     * Full constructor for convenience.
     *
     * @param rentalDate the date the transaction started
     * @param customer   the customer performing the transaction
     */
    public RentalTransaction(LocalDate rentalDate, Customer customer) {
        this.rentalDate = rentalDate;
        this.customer = customer;
    }

    /**
     * Calculates the total price for this transaction.
     *
     * <p>Total price = Σ (base rental fee + overdue fee) for each rental.
     *
     * @param rentalDate  the date the transaction started (normally the same as the
     *                    rental start date of each {@link VideoRental})
     * @param currentDate the date used for calculations (e.g., today)
     * @return the total amount rounded to two decimal places
     */
    public double calculateTotalPrice(LocalDate rentalDate, LocalDate currentDate) {
        double sum = 0.0;
        for (VideoRental rental : rentals) {
            double base = rental.calculateBaseRentalFee(currentDate);
            double overdue = rental.calculateOwedPastDueAmount(currentDate);
            sum += base + overdue;
        }
        totalPrice = round(sum);
        return totalPrice;
    }

    /**
     * Adds a {@link VideoRental} to this transaction.
     *
     * @param rental the rental to add
     */
    public void addRental(VideoRental rental) {
        rentals.add(rental);
    }

    // -------------------------------------------------------------------------
    // Getters & Setters
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Utility
    // -------------------------------------------------------------------------

    private static double round(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}