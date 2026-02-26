import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a customer of the video rental store.
 */
 class Customer {

    private String id;
    private List<VideoRental> rentals = new ArrayList<>();

    /** Unparameterized constructor */
    public Customer() {
    }

    /** Parameterized constructor for convenience */
    public Customer(String id) {
        this.id = id;
    }

    /** @return the customer id */
    public String getId() {
        return id;
    }

    /** @param id the customer id to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the list of rentals belonging to this customer */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /** @param rentals the rentals list to set */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Adds a video tape rental for this customer after performing all required checks.
     *
     * @param tape        the tape to rent
     * @param currentDate the date on which the rental is attempted
     * @return {@code true} if the rental was successfully added; {@code false} otherwise
     */
    public boolean addVedioTapeRental(Tape tape, Date currentDate) {
        // 1. Customer must have less than 20 active rentals
        long activeRentals = rentals.stream()
                .filter(r -> r.getReturnDate() == null)
                .count();
        if (activeRentals >= 20) {
            return false;
        }

        // 2. Customer must have no outstanding past‑due amount
        double totalPastDue = calculateTotalPastDueAmount(currentDate);
        if (totalPastDue > 0.0) {
            return false;
        }

        // 3. Tape must be available
        if (!tape.isAvailable(currentDate)) {
            return false;
        }

        // 4. Create the rental (due date is set to 7 days from today for this example)
        LocalDate due = toLocalDate(currentDate).plusDays(7);
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalDate(currentDate);
        rental.setDueDate(Date.from(due.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        rental.setReturnDate(null);
        rental.setOwnedPastDueAmount(0.0);

        // 5. Register the rental
        rentals.add(rental);
        VideoRental.registerRental(rental);
        return true;
    }

    /**
     * Retrieves a list of all tapes that have been rented by this customer and not yet returned.
     *
     * @return a list of unreturned {@link Tape}s; empty list if none
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
     * Calculates the total past‑due amount for all rentals of this customer.
     *
     * @param currentDate the date used for overdue calculations
     * @return total past‑due amount rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(Date currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            total += rental.calculateOwedPastDueAmount(currentDate);
        }
        return round(total);
    }

    /** Utility to convert {@link Date} to {@link LocalDate}. */
    private static LocalDate toLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /** Utility to round a double to two decimal places. */
    private static double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

/**
 * Represents a physical video tape.
 */
class Tape {

    private String id;
    private String videoInformation;

    /** Unparameterized constructor */
    public Tape() {
    }

    /** Parameterized constructor for convenience */
    public Tape(String id, String videoInformation) {
        this.id = id;
        this.videoInformation = videoInformation;
    }

    /** @return tape id */
    public String getId() {
        return id;
    }

    /** @param id the tape id to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return video information */
    public String getVideoInformation() {
        return videoInformation;
    }

    /** @param videoInformation the video information to set */
    public void setVideoInformation(String videoInformation) {
        this.videoInformation = videoInformation;
    }

    /**
     * Determines whether this tape is available for rental on the given date.
     *
     * @param currentDate the date for which availability is checked
     * @return {@code true} if the tape is not part of any active rental; {@code false} otherwise
     */
    public boolean isAvailable(Date currentDate) {
        // A tape is unavailable if it belongs to any rental whose returnDate is null
        for (VideoRental rental : VideoRental.getAllRentals()) {
            if (rental.getTape() != null && rental.getTape().getId().equals(this.id)
                    && rental.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Represents a single video tape rental.
 */
class VideoRental {

    private Date dueDate;
    private Date returnDate;
    private double ownedPastDueAmount; // stored after calculation, if needed
    private Tape tape;
    private Date rentalDate; // the date when the rental started

    /** Unparameterized constructor */
    public VideoRental() {
    }

    /** Static list that holds every rental created – used for availability checks */
    private static final List<VideoRental> allRentals = new ArrayList<>();

    /**
     * Registers a newly created rental in the global list.
     *
     * @param rental the rental to register
     */
    public static void registerRental(VideoRental rental) {
        allRentals.add(rental);
    }

    /**
     * @return an immutable view of all rentals
     */
    public static List<VideoRental> getAllRentals() {
        return Collections.unmodifiableList(allRentals);
    }

    /** @return due date */
    public Date getDueDate() {
        return dueDate;
    }

    /** @param dueDate the due date to set */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /** @return return date */
    public Date getReturnDate() {
        return returnDate;
    }

    /** @param returnDate the return date to set */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /** @return owned past‑due amount */
    public double getOwnedPastDueAmount() {
        return ownedPastDueAmount;
    }

    /** @param ownedPastDueAmount the owned past‑due amount to set */
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

    /** @return the rental start date */
    public Date getRentalDate() {
        return rentalDate;
    }

    /** @param rentalDate the rental start date to set */
    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    /**
     * Calculates the overdue fee for this rental as of {@code currentDate}.
     *
     * <p>Rules:
     * <ul>
     *   <li>If {@code returnDate} ≤ {@code dueDate} → fee = 0.</li>
     *   <li>If {@code returnDate} > {@code dueDate} → fee = (days overdue) × $0.50.</li>
     *   <li>If {@code returnDate} is {@code null} → fee = (days from today to dueDate) × $0.50
     *   (or 0 if today ≤ dueDate).</li>
     * </ul>
     *
     * @param currentDate the date used for calculations when the tape has not yet been returned
     * @return overdue fee rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(Date currentDate) {
        LocalDate due = toLocalDate(dueDate);
        LocalDate effectiveReturn = (returnDate != null) ? toLocalDate(returnDate) : toLocalDate(currentDate);

        long overdueDays = 0;
        if (effectiveReturn.isAfter(due)) {
            overdueDays = ChronoUnit.DAYS.between(due, effectiveReturn);
        }

        double fee = overdueDays * 0.5;
        return round(fee);
    }

    /**
     * Calculates the base rental fee for this rental.
     *
     * <p>Base fee = number of rental days × $1.00.
     * Rental days are counted from {@code rentalDate} (inclusive) to the earlier of
     * {@code returnDate} or {@code currentDate} (inclusive). Any partial day counts as a full day.
     *
     * @param currentDate the date used when the tape has not yet been returned
     * @return base fee rounded to two decimal places
     */
    public double calculateBaseRentalFee(Date currentDate) {
        LocalDate start = toLocalDate(rentalDate);
        LocalDate end = (returnDate != null) ? toLocalDate(returnDate) : toLocalDate(currentDate);

        long days = ChronoUnit.DAYS.between(start, end) + 1; // +1 to count partial day as full
        if (days < 1) {
            days = 1;
        }
        double fee = days * 1.0;
        return round(fee);
    }

    /** Utility to convert {@link Date} to {@link LocalDate}. */
    private static LocalDate toLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /** Utility to round a double to two decimal places. */
    private static double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

/**
 * Represents a transaction that may contain multiple video rentals.
 */
class RentalTransaction {

    private Date rentalDate;
    private double totalPrice;
    private List<VideoRental> rentals = new ArrayList<>();
    private Customer customer;

    /** Unparameterized constructor */
    public RentalTransaction() {
    }

    /** @return the date the transaction started */
    public Date getRentalDate() {
        return rentalDate;
    }

    /** @param rentalDate the rental date to set */
    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    /** @return the total price of the transaction */
    public double getTotalPrice() {
        return totalPrice;
    }

    /** @param totalPrice the total price to set */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /** @return the list of rentals in this transaction */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /** @param rentals the rentals list to set */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /** @return the customer associated with this transaction */
    public Customer getCustomer() {
        return customer;
    }

    /** @param customer the customer to associate */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Calculates the total price for the transaction, taking into account both
     * the base rental fee and any overdue fees for each rental.
     *
     * @param rentalDate   the date when the transaction started (used for base fee calculation)
     * @param currentDate the current date (used for overdue calculations)
     * @return total price rounded to two decimal places
     */
    public double calculateTotalPrice(Date rentalDate, Date currentDate) {
        double sum = 0.0;
        for (VideoRental rental : rentals) {
            double base = rental.calculateBaseRentalFee(currentDate);
            double overdue = rental.calculateOwedPastDueAmount(currentDate);
            sum += base + overdue;
        }
        this.totalPrice = round(sum);
        return this.totalPrice;
    }

    /** Utility to round a double to two decimal places. */
    private static double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}