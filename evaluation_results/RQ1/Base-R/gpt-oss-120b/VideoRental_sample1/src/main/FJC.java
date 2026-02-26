import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a customer of the video rental store.
 */
class Customer {
    private String customerId;
    private String name;
    private List<RentalTransaction> rentals;

    /** Unparameterized constructor */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /** Parameterized constructor for convenience */
    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
        this.rentals = new ArrayList<>();
    }

    // ---------- Getters & Setters ----------
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<RentalTransaction> getRentals() {
        return rentals;
    }
    public void setRentals(List<RentalTransaction> rentals) {
        this.rentals = rentals;
    }

    /**
     * Adds a rental transaction to the customer's list.
     *
     * @param rental the rental transaction to add
     */
    public void addRental(RentalTransaction rental) {
        this.rentals.add(rental);
    }

    /**
     * Returns the number of active (unreturned) rentals for this customer.
     *
     * @return count of rentals whose return date is null
     */
    public long countActiveRentals() {
        return rentals.stream().filter(r -> r.getReturnDate() == null).count();
    }
}

/**
 * Represents a video tape in the inventory.
 */
class VideoTape {
    private String tapeId;
    private String title;

    /** Unparameterized constructor */
    public VideoTape() {}

    /** Parameterized constructor for convenience */
    public VideoTape(String tapeId, String title) {
        this.tapeId = tapeId;
        this.title = title;
    }

    // ---------- Getters & Setters ----------
    public String getTapeId() {
        return tapeId;
    }
    public void setTapeId(String tapeId) {
        this.tapeId = tapeId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}

/**
 * Represents a single rental transaction (one tape rented by a customer).
 */
class RentalTransaction {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String transactionId;
    private String customerId;
    private String tapeId;
    private LocalDate rentalDate;   // date when tape was rented
    private LocalDate dueDate;      // date when tape is due
    private LocalDate returnDate;   // null if not yet returned

    /** Unparameterized constructor */
    public RentalTransaction() {}

    /** Parameterized constructor for convenience */
    public RentalTransaction(String transactionId,
                             String customerId,
                             String tapeId,
                             LocalDate rentalDate,
                             LocalDate dueDate) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.tapeId = tapeId;
        this.rentalDate = rentalDate;
        this.dueDate = dueDate;
        this.returnDate = null;
    }

    // ---------- Getters & Setters ----------
    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getTapeId() {
        return tapeId;
    }
    public void setTapeId(String tapeId) {
        this.tapeId = tapeId;
    }
    public LocalDate getRentalDate() {
        return rentalDate;
    }
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
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

    /**
     * Calculates the base rental fee for this transaction.
     * Base rental fee = number of rental days × $1.00.
     * Any partial day counts as a full day.
     *
     * @param asOfDate the date to use when the tape has not yet been returned
     * @return base fee rounded to two decimal places
     */
    public double calculateBaseFee(LocalDate asOfDate) {
        LocalDate end = (returnDate != null) ? returnDate : asOfDate;
        long days = ChronoUnit.DAYS.between(rentalDate, end) + 1; // +1 to count partial day
        if (days < 1) days = 1;
        BigDecimal fee = BigDecimal.valueOf(days).multiply(BigDecimal.valueOf(1.0));
        return fee.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Calculates the overdue fee for this transaction.
     * See {@link RentalSystem#calculatePastDueAmount(RentalTransaction)} for the exact rules.
     *
     * @param currentDate the date to use when the tape has not yet been returned
     * @return overdue fee rounded to two decimal places
     */
    public double calculateOverdueFee(LocalDate currentDate) {
        // Delegates to RentalSystem static helper (avoids circular dependency)
        return RentalSystem.calculatePastDueAmount(this, currentDate);
    }
}

/**
 * Core service class that contains the business logic for the video rental store.
 */
class RentalSystem {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final double OVERDUE_RATE_PER_DAY = 0.50;
    private static final double DAILY_RENTAL_RATE = 1.00;
    private static final int MAX_ACTIVE_RENTALS = 20;

    private Map<String, Customer> customers;          // key = customerId
    private Map<String, VideoTape> inventory;         // key = tapeId
    private List<RentalTransaction> rentals;          // all rentals

    /** Unparameterized constructor */
    public RentalSystem() {
        this.customers = new HashMap<>();
        this.inventory = new HashMap<>();
        this.rentals = new ArrayList<>();
    }

    // ---------- Getters & Setters ----------
    public Map<String, Customer> getCustomers() {
        return customers;
    }
    public void setCustomers(Map<String, Customer> customers) {
        this.customers = customers;
    }
    public Map<String, VideoTape> getInventory() {
        return inventory;
    }
    public void setInventory(Map<String, VideoTape> inventory) {
        this.inventory = inventory;
    }
    public List<RentalTransaction> getRentals() {
        return rentals;
    }
    public void setRentals(List<RentalTransaction> rentals) {
        this.rentals = rentals;
    }

    /**
     * Calculates the past‑due amount for a given rental transaction.
     * <p>
     * Rules:
     * <ul>
     *   <li>If return date ≤ due date → overdue amount = 0.</li>
     *   <li>If return date > due date → overdue days = (return date - due date),
     *       overdue fee = overdue days × $0.5.</li>
     *   <li>If the tape has not been returned (return date is null) → overdue days
     *       = current date – due date, overdue fee = overdue days × $0.5.</li>
     * </ul>
     * The fee is rounded to two decimal places.
     *
     * @param rental      the rental transaction to evaluate
     * @param currentDate the date to use when the tape is still out (ignored if returned)
     * @return overdue fee rounded to two decimal places
     */
    public static double calculatePastDueAmount(RentalTransaction rental, LocalDate currentDate) {
        LocalDate effectiveReturn = rental.getReturnDate() != null ? rental.getReturnDate() : currentDate;
        if (!effectiveReturn.isAfter(rental.getDueDate())) {
            return 0.0;
        }
        long overdueDays = ChronoUnit.DAYS.between(rental.getDueDate(), effectiveReturn);
        BigDecimal fee = BigDecimal.valueOf(overdueDays).multiply(BigDecimal.valueOf(OVERDUE_RATE_PER_DAY));
        return fee.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Checks whether a specific tape is available for rent on the given date.
     * A tape is unavailable if it belongs to any active rental whose return date is null.
     *
     * @param tapeId      the identifier of the tape to check
     * @param currentDate the date for which availability is queried (not used in logic but kept for signature compatibility)
     * @return {@code true} if the tape is available; {@code false} otherwise
     */
    public boolean isTapeAvailable(String tapeId, LocalDate currentDate) {
        return rentals.stream()
                .filter(r -> r.getTapeId().equals(tapeId) && r.getReturnDate() == null)
                .findAny()
                .isEmpty();
    }

    /**
     * Attempts to add a new video tape rental for a customer.
     * <p>
     * The method validates:
     * <ul>
     *   <li>The customer exists.</li>
     *   <li>The customer has fewer than {@value #MAX_ACTIVE_RENTALS} active rentals.</li>
     *   <li>The customer has no outstanding past‑due amount.</li>
     *   <li>The tape exists and is currently available.</li>
     * </ul>
     * If all checks pass, a new {@link RentalTransaction} is created, stored,
     * and linked to the customer. The method returns {@code true} on success.
     *
     * @param customerId the identifier of the customer renting the tape
     * @param tapeId     the identifier of the tape to rent
     * @param rentalDate the date on which the tape is rented
     * @return {@code true} if the rental is successfully processed; {@code false} otherwise
     */
    public boolean addVideoTapeRental(String customerId, String tapeId, LocalDate rentalDate) {
        Customer customer = customers.get(customerId);
        VideoTape tape = inventory.get(tapeId);
        LocalDate today = LocalDate.now();

        // Basic existence checks
        if (customer == null || tape == null) {
            return false;
        }

        // 1. Check active rentals count
        if (customer.countActiveRentals() >= MAX_ACTIVE_RENTALS) {
            return false;
        }

        // 2. Check for any past‑due amount on active rentals
        double totalPastDue = customer.getRentals().stream()
                .filter(r -> r.getReturnDate() == null) // only active rentals matter for past‑due
                .mapToDouble(r -> calculatePastDueAmount(r, today))
                .sum();
        if (totalPastDue > 0.0) {
            return false;
        }

        // 3. Verify tape availability
        if (!isTapeAvailable(tapeId, today)) {
            return false;
        }

        // All checks passed – create rental
        // Assuming a standard rental period of 7 days (can be changed as needed)
        LocalDate dueDate = rentalDate.plusDays(7);
        String transactionId = UUID.randomUUID().toString();

        RentalTransaction rental = new RentalTransaction(
                transactionId,
                customerId,
                tapeId,
                rentalDate,
                dueDate
        );

        // Store and associate
        rentals.add(rental);
        customer.addRental(rental);
        return true;
    }

    /**
     * Calculates the total price for all rentals associated with a given customer.
     * The total for each rental = base rental fee + overdue fee.
     *
     * @param customerId the identifier of the customer
     * @return total amount due rounded to two decimal places; 0.0 if the customer does not exist
     */
    public double calculateTotalPrice(String customerId) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            return 0.0;
        }
        LocalDate today = LocalDate.now();

        BigDecimal total = BigDecimal.ZERO;
        for (RentalTransaction rental : customer.getRentals()) {
            double base = rental.calculateBaseFee(today);
            double overdue = rental.calculateOverdueFee(today);
            total = total.add(BigDecimal.valueOf(base + overdue));
        }
        return total.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Retrieves a list of tape identifiers that a customer has rented but not yet returned.
     *
     * @param customerId the identifier of the customer
     * @return a list of tape IDs; empty list if none are found or the customer does not exist
     */
    public List<String> listUnreturnedTapes(String customerId) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        for (RentalTransaction rental : customer.getRentals()) {
            if (rental.getReturnDate() == null) {
                result.add(rental.getTapeId());
            }
        }
        return result;
    }

    // -------------------------------------------------------------------------
    // Helper methods to populate the system – not part of the functional spec,
    // but useful for unit testing and demonstration.
    // -------------------------------------------------------------------------

    /**
     * Adds a new customer to the system.
     *
     * @param customer the customer to add
     */
    public void addCustomer(Customer customer) {
        if (customer != null && customer.getCustomerId() != null) {
            customers.put(customer.getCustomerId(), customer);
        }
    }

    /**
     * Adds a new video tape to the inventory.
     *
     * @param tape the video tape to add
     */
    public void addVideoTape(VideoTape tape) {
        if (tape != null && tape.getTapeId() != null) {
            inventory.put(tape.getTapeId(), tape);
        }
    }

    /**
     * Records the return of a tape.
     *
     * @param transactionId the identifier of the rental transaction
     * @param returnDate    the date the tape was returned
     * @return {@code true} if the transaction was found and updated; {@code false} otherwise
     */
    public boolean processReturn(String transactionId, LocalDate returnDate) {
        Optional<RentalTransaction> opt = rentals.stream()
                .filter(r -> r.getTransactionId().equals(transactionId))
                .findFirst();
        if (opt.isPresent()) {
            RentalTransaction rental = opt.get();
            rental.setReturnDate(returnDate);
            return true;
        }
        return false;
    }
}