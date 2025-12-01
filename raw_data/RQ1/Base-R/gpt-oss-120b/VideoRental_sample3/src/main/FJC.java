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
    private List<RentalTransaction> transactions = new ArrayList<>();

    public Customer() {
        // No‑arg constructor
    }

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    /** Getters and setters */
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

	public List<RentalTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<RentalTransaction> transactions) {
		this.transactions = transactions;
	}

    /**
     * Calculates the total past‑due amount for all *unreturned* rentals of this customer.
     *
     * @return the total overdue fee rounded to two decimal places.
     */
    public double getTotalPastDueAmount() {
        double total = 0.0;
        for (RentalTransaction tx : transactions) {
            for (Rental rental : tx.getRentals()) {
                if (rental.getReturnDate() == null) {
                    total += RentalService.calculatePastDueFee(rental);
                }
            }
        }
        return round(total);
    }

    /**
     * Returns the number of active (unreturned) rentals for this customer.
     *
     * @return count of active rentals.
     */
    public int getActiveRentalCount() {
        int count = 0;
        for (RentalTransaction tx : transactions) {
            for (Rental rental : tx.getRentals()) {
                if (rental.getReturnDate() == null) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Utility method for rounding a double to two decimal places.
     *
     * @param value value to round.
     * @return rounded value.
     */
    private static double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

/**
 * Represents a single video tape in the inventory.
 */
 class VideoTape {

    private String tapeId;
    private String title;

    public VideoTape() {
        // No‑arg constructor
    }

    public VideoTape(String tapeId, String title) {
        this.tapeId = tapeId;
        this.title = title;
    }

    /** Getters and setters */
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
 * Represents a rental of a single video tape.
 */
 class Rental {

    private VideoTape tape;
    private Customer customer;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate; // null if not yet returned

    public Rental() {
        // No‑arg constructor
    }

    public Rental(VideoTape tape, Customer customer, LocalDate rentalDate, LocalDate dueDate) {
        this.tape = tape;
        this.customer = customer;
        this.rentalDate = rentalDate;
        this.dueDate = dueDate;
    }

    /** Getters and setters */
    public VideoTape getTape() {
        return tape;
    }

    public void setTape(VideoTape tape) {
        this.tape = tape;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
     * Calculates the base rental fee.
     * Base fee = rental days × $1 per day.
     * Any partial day counts as a full day.
     *
     * @return base fee rounded to two decimals.
     */
    public double calculateBaseFee() {
        LocalDate end = (returnDate != null) ? returnDate : dueDate;
        long days = ChronoUnit.DAYS.between(rentalDate, end) + 1; // +1 to count partial day
        double fee = days * 1.0;
        return round(fee);
    }

    /**
     * Calculates the overdue fee for this rental.
     *
     * @return overdue fee rounded to two decimals.
     */
    public double calculateOverdueFee() {
        return RentalService.calculatePastDueFee(this);
    }

    /**
     * Returns the total fee for this rental (base + overdue).
     *
     * @return total fee rounded to two decimals.
     */
    public double calculateTotalFee() {
        double total = calculateBaseFee() + calculateOverdueFee();
        return round(total);
    }

    private static double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

/**
 * Represents a rental transaction that may contain multiple rentals.
 */
 class RentalTransaction {

    private String transactionId;
    private Customer customer;
    private List<Rental> rentals = new ArrayList<>();
    private LocalDate transactionDate;
    private double amountCollected; // cash received from the customer
    private double changeGiven;

    public RentalTransaction() {
        // No‑arg constructor
    }

    public RentalTransaction(String transactionId, Customer customer, LocalDate transactionDate) {
        this.transactionId = transactionId;
        this.customer = customer;
        this.transactionDate = transactionDate;
    }

    /** Getters and setters */
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmountCollected() {
        return amountCollected;
    }

    public void setAmountCollected(double amountCollected) {
        this.amountCollected = amountCollected;
    }

    public double getChangeGiven() {
        return changeGiven;
    }

    public void setChangeGiven(double changeGiven) {
        this.changeGiven = changeGiven;
    }

    /**
     * Calculates the total price for this transaction (sum of all rental totals).
     *
     * @return total price rounded to two decimal places.
     */
    public double calculateTotalPrice() {
        double sum = 0.0;
        for (Rental r : rentals) {
            sum += r.calculateTotalFee();
        }
        return round(sum);
    }

    private static double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

/**
 * Service class that holds the core business logic and in‑memory data stores.
 */
 class RentalService {

    private Map<String, Customer> customers = new HashMap<>();
    private Map<String, VideoTape> tapes = new HashMap<>();
    private Map<String, RentalTransaction> transactions = new HashMap<>();

    public RentalService() {
        // No‑arg constructor
    }

    /** Getters and setters for the internal stores (useful for tests) */
    public Map<String, Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Map<String, Customer> customers) {
        this.customers = customers;
    }

    public Map<String, VideoTape> getTapes() {
        return tapes;
    }

    public void setTapes(Map<String, VideoTape> tapes) {
        this.tapes = tapes;
    }

    public Map<String, RentalTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Map<String, RentalTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Calculates the past‑due fee for a given rental.
     * <p>
     * If return date ≤ due date → overdue amount = 0.
     * If return date > due date → overdue days = (return date - due date),
     * overdue fee = overdue days × $0.5.
     * If the tape has not been returned (return date is null) → overdue days = current date – due date,
     * overdue fee = overdueDays × $0.50.
     *
     * @param rental the rental to evaluate
     * @return overdue fee rounded to two decimal places
     */
    public static double calculatePastDueFee(Rental rental) {
        LocalDate compareDate = (rental.getReturnDate() != null) ? rental.getReturnDate() : LocalDate.now();
        if (!compareDate.isAfter(rental.getDueDate())) {
            return 0.0;
        }
        long overdueDays = ChronoUnit.DAYS.between(rental.getDueDate(), compareDate);
        double fee = overdueDays * 0.5;
        return round(fee);
    }

    /**
     * Checks if a tape is available for rent on the given date.
     * A tape is unavailable if it belongs to any active rental (return date is null).
     *
     * @param tapeId      the identifier of the tape
     * @param currentDate the date for which availability is checked (not used in the current logic)
     * @return true if the tape is available, false otherwise
     */
    public boolean isTapeAvailable(String tapeId, LocalDate currentDate) {
        for (RentalTransaction tx : transactions.values()) {
            for (Rental r : tx.getRentals()) {
                if (r.getTape().getTapeId().equals(tapeId) && r.getReturnDate() == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Adds a video tape rental after verifying business rules:
     * <ul>
     *   <li>Customer has fewer than 20 active rentals.</li>
     *   <li>Customer has no past‑due amount.</li>
     *   <li>The tape is available.</li>
     * </ul>
     *
     * @param customerId   the customer's id
     * @param tapeId       the tape's id
     * @param rentalDate   the date the rental starts
     * @param dueDate      the date the rental is due
     * @return true if the rental was successfully added; false otherwise
     */
    public boolean addVideoTapeRental(String customerId, String tapeId,
                                      LocalDate rentalDate, LocalDate dueDate) {
        Customer cust = customers.get(customerId);
        VideoTape tape = tapes.get(tapeId);
        if (cust == null || tape == null) {
            return false;
        }

        // Rule 1: less than 20 active rentals
        if (cust.getActiveRentalCount() >= 20) {
            return false;
        }

        // Rule 2: no past‑due amount
        if (cust.getTotalPastDueAmount() > 0.0) {
            return false;
        }

        // Rule 3: tape availability
        if (!isTapeAvailable(tapeId, rentalDate)) {
            return false;
        }

        // All checks passed – create rental and transaction
        Rental rental = new Rental(tape, cust, rentalDate, dueDate);
        RentalTransaction tx = new RentalTransaction(
                UUID.randomUUID().toString(),
                cust,
                rentalDate);
        tx.getRentals().add(rental);

        // Store references
        cust.getTransactions().add(tx);
        transactions.put(tx.getTransactionId(), tx);
        return true;
    }

    /**
     * Calculates the total price for a customer's rental transaction.
     * Total price = sum of (base rental fee + overdue fee) for each rental in the transaction.
     *
     * @param transactionId the identifier of the transaction
     * @return total price rounded to two decimal places, or -1 if transaction not found
     */
    public double calculateTotalPriceForTransaction(String transactionId) {
        RentalTransaction tx = transactions.get(transactionId);
        if (tx == null) {
            return -1.0;
        }
        return tx.calculateTotalPrice();
    }

    /**
     * Retrieves a list of all tape IDs that a customer has rented but not yet returned.
     *
     * @param customerId the customer's id
     * @return a list of tape IDs; empty list if none
     */
    public List<String> listUnreturnedTapes(String customerId) {
        Customer cust = customers.get(customerId);
        if (cust == null) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        for (RentalTransaction tx : cust.getTransactions()) {
            for (Rental r : tx.getRentals()) {
                if (r.getReturnDate() == null) {
                    result.add(r.getTape().getTapeId());
                }
            }
        }
        return result;
    }

    /**
     * Helper method to round a double to two decimal places.
     *
     * @param value value to round
     * @return rounded value
     */
    private static double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /* --------------------------------------------------------------------- */
    /* Additional utility methods that may be useful for unit testing       */
    /* --------------------------------------------------------------------- */

    /**
     * Registers a new customer in the system.
     *
     * @param customer the customer to add
     */
    public void registerCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
    }

    /**
     * Adds a new video tape to the inventory.
     *
     * @param tape the tape to add
     */
    public void addVideoTape(VideoTape tape) {
        tapes.put(tape.getTapeId(), tape);
    }

    /**
     * Records the return of a tape.
     *
     * @param transactionId the transaction containing the rental
     * @param tapeId        the tape being returned
     * @param returnDate    the date of return
     * @return true if the return was recorded; false otherwise
     */
    public boolean recordReturn(String transactionId, String tapeId, LocalDate returnDate) {
        RentalTransaction tx = transactions.get(transactionId);
        if (tx == null) {
            return false;
        }
        for (Rental r : tx.getRentals()) {
            if (r.getTape().getTapeId().equals(tapeId) && r.getReturnDate() == null) {
                r.setReturnDate(returnDate);
                return true;
            }
        }
        return false;
    }
}