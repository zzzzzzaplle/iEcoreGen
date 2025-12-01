import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a customer of the video rental store.
 */
class Customer {

    private String id;                      // account number
    private String name;
    private List<RentalItem> rentals;       // all rentals (active & completed)

    /** Unparameterized constructor. */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /** @return the account number of the customer */
    public String getId() {
        return id;
    }

    /** @param id the account number to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the customer's name */
    public String getName() {
        return name;
    }

    /** @param name the customer's name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return list of rentals belonging to this customer */
    public List<RentalItem> getRentals() {
        return rentals;
    }

    /** @param rentals the list of rentals to set */
    public void setRentals(List<RentalItem> rentals) {
        this.rentals = rentals;
    }
}

/**
 * Represents a video tape in the inventory.
 */
class VideoTape {

    private String tapeId;          // barcode id
    private String title;

    /** Unparameterized constructor. */
    public VideoTape() {
    }

    /** @return the barcode id of the tape */
    public String getTapeId() {
        return tapeId;
    }

    /** @param tapeId the barcode id to set */
    public void setTapeId(String tapeId) {
        this.tapeId = tapeId;
    }

    /** @return the title of the video */
    public String getTitle() {
        return title;
    }

    /** @param title the title to set */
    public void setTitle(String title) {
        this.title = title;
    }
}

/**
 * Represents a single rental (one tape rented by a customer).
 */
class RentalItem {

    private String rentalId;                // internal identifier
    private Customer customer;
    private VideoTape tape;
    private LocalDate rentalDate;           // date when tape was taken
    private LocalDate dueDate;              // date by which tape must be returned
    private LocalDate returnDate;           // null if not yet returned

    /** Unparameterized constructor. */
    public RentalItem() {
    }

    /** @return the rental identifier */
    public String getRentalId() {
        return rentalId;
    }

    /** @param rentalId the rental identifier to set */
    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    /** @return the customer that performed the rental */
    public Customer getCustomer() {
        return customer;
    }

    /** @param customer the customer to set */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /** @return the video tape that was rented */
    public VideoTape getTape() {
        return tape;
    }

    /** @param tape the video tape to set */
    public void setTape(VideoTape tape) {
        this.tape = tape;
    }

    /** @return the date the tape was rented */
    public LocalDate getRentalDate() {
        return rentalDate;
    }

    /** @param rentalDate the rental date to set */
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    /** @return the due date for the rental */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /** @param dueDate the due date to set */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /** @return the actual return date, may be {@code null} if not yet returned */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /** @param returnDate the return date to set */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Calculates the overdue fee for this rental.
     * <p>
     * If the tape has not been returned, the overdue days are calculated
     * from the due date to the current date. If the tape has been returned,
     * the overdue days are calculated from the due date to the actual return
     * date. No fee is charged when the return date is on or before the due
     * date.
     *
     * @return overdue fee rounded to two decimal places
     */
    public double calculateOverdueFee() {
        LocalDate today = LocalDate.now();
        LocalDate effectiveReturn = (returnDate == null) ? today : returnDate;

        if (!effectiveReturn.isAfter(dueDate)) {
            return 0.0;
        }

        long overdueDays = ChronoUnit.DAYS.between(dueDate, effectiveReturn);
        double fee = overdueDays * 0.50;

        return roundTwoDecimals(fee);
    }

    /**
     * Calculates the base rental fee for this rental.
     * The fee is $1 per day, and any partial day counts as a full day.
     *
     * @return base rental fee rounded to two decimal places
     */
    public double calculateBaseFee() {
        LocalDate endDate = (returnDate == null) ? LocalDate.now() : returnDate;
        long days = ChronoUnit.DAYS.between(rentalDate, endDate) + 1; // inclusive
        double fee = days * 1.0;
        return roundTwoDecimals(fee);
    }

    private double roundTwoDecimals(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

/**
 * Service class that contains core business logic for the video rental system.
 */
class RentalService {

    private List<Customer> customers;
    private List<VideoTape> inventory;
    private List<RentalItem> rentals;

    /** Unparameterized constructor. */
    public RentalService() {
        this.customers = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    /** @return the list of customers */
    public List<Customer> getCustomers() {
        return customers;
    }

    /** @param customers the list of customers to set */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /** @return the inventory of video tapes */
    public List<VideoTape> getInventory() {
        return inventory;
    }

    /** @param inventory the inventory to set */
    public void setInventory(List<VideoTape> inventory) {
        this.inventory = inventory;
    }

    /** @return the list of all rentals */
    public List<RentalItem> getRentals() {
        return rentals;
    }

    /** @param rentals the list of rentals to set */
    public void setRentals(List<RentalItem> rentals) {
        this.rentals = rentals;
    }

    /**
     * Calculates the past‑due amount for a given rental item.
     * <p>
     * The logic follows the specification:
     * <ul>
     *   <li>If return date ≤ due date → overdue amount = 0.</li>
     *   <li>If return date > due date → overdue days = (return date – due date),
     *   overdue fee = overdue days × $0.5.</li>
     *   <li>If the tape has not been returned (return date is {@code null}) →
     *   overdue days = (current date – due date), overdue fee = overdue days × $0.5.</li>
     * </ul>
     *
     * @param item the rental item for which the overdue fee is calculated
     * @return the overdue fee rounded to two decimal places
     */
    public double calculatePastDueAmount(RentalItem item) {
        return item.calculateOverdueFee();
    }

    /**
     * Checks whether a tape is available for rental on a given date.
     * A tape is unavailable if it belongs to any active rental whose return
     * date is {@code null}.
     *
     * @param tapeId      the barcode id of the tape to check
     * @param currentDate the date for which availability is queried
     * @return {@code true} if the tape is available; {@code false} otherwise
     */
    public boolean isTapeAvailable(String tapeId, LocalDate currentDate) {
        Objects.requireNonNull(tapeId, "tapeId must not be null");
        for (RentalItem ri : rentals) {
            if (ri.getTape().getTapeId().equals(tapeId) && ri.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a video tape rental for a customer after verifying all constraints.
     * <p>
     * The method checks:
     * <ul>
     *   <li>The customer has fewer than 20 active rentals.</li>
     *   <li>The customer does not owe any past‑due amount.</li>
     *   <li>The requested tape is available.</li>
     * </ul>
     *
     * @param customerId the account number of the customer
     * @param tapeId     the barcode id of the tape to rent
     * @param rentalDate the date on which the rental takes place
     * @return {@code true} if the rental is successfully processed; {@code false} otherwise
     */
    public boolean addVideoTapeRental(String customerId, String tapeId, LocalDate rentalDate) {
        Customer cust = findCustomerById(customerId);
        VideoTape tape = findTapeById(tapeId);

        if (cust == null || tape == null) {
            return false;
        }

        // 1. Active rentals count
        long activeCount = cust.getRentals().stream()
                .filter(ri -> ri.getReturnDate() == null)
                .count();
        if (activeCount >= 20) {
            return false;
        }

        // 2. Past‑due amount check
        double totalPastDue = cust.getRentals().stream()
                .filter(ri -> ri.getReturnDate() == null) // only active rentals
                .mapToDouble(this::calculatePastDueAmount)
                .sum();
        if (totalPastDue > 0.0) {
            return false;
        }

        // 3. Tape availability
        if (!isTapeAvailable(tapeId, rentalDate)) {
            return false;
        }

        // All checks passed – create rental
        RentalItem newRental = new RentalItem();
        newRental.setRentalId(generateRentalId());
        newRental.setCustomer(cust);
        newRental.setTape(tape);
        newRental.setRentalDate(rentalDate);
        // Assuming a standard rental period of 7 days
        newRental.setDueDate(rentalDate.plusDays(7));
        newRental.setReturnDate(null);

        // Persist
        rentals.add(newRental);
        cust.getRentals().add(newRental);
        return true;
    }

    /**
     * Calculates the total price for all rentals belonging to a given customer.
     * The total price for each rental is the sum of the base rental fee and any
     * overdue fee. The result is rounded to two decimal places.
     *
     * @param customerId the account number of the customer
     * @return the total price for the customer's rental transaction
     */
    public double calculateTotalPrice(String customerId) {
        Customer cust = findCustomerById(customerId);
        if (cust == null) {
            return 0.0;
        }

        double total = 0.0;
        for (RentalItem ri : cust.getRentals()) {
            double base = ri.calculateBaseFee();
            double overdue = ri.calculateOverdueFee();
            total += base + overdue;
        }
        return roundTwoDecimals(total);
    }

    /**
     * Retrieves a list of all tape ids that have been rented by a customer
     * and have not yet been returned.
     *
     * @param customerId the account number of the customer
     * @return an immutable list of tape ids; empty if none are active
     */
    public List<String> listUnreturnedTapes(String customerId) {
        Customer cust = findCustomerById(customerId);
        if (cust == null) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        for (RentalItem ri : cust.getRentals()) {
            if (ri.getReturnDate() == null) {
                result.add(ri.getTape().getTapeId());
            }
        }
        return Collections.unmodifiableList(result);
    }

    // -------------------------------------------------------------------------
    // Helper methods (private)
    // -------------------------------------------------------------------------

    private Customer findCustomerById(String id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private VideoTape findTapeById(String tapeId) {
        return inventory.stream()
                .filter(t -> t.getTapeId().equals(tapeId))
                .findFirst()
                .orElse(null);
    }

    private String generateRentalId() {
        return "R" + (rentals.size() + 1);
    }

    private double roundTwoDecimals(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

/**
 * Simple demo / test harness (not required for the assignment but useful for
 * quick manual verification).
 */
class RentalDemo {
    public static void main(String[] args) {
        RentalService service = new RentalService();

        // Populate customers
        Customer alice = new Customer();
        alice.setId("C001");
        alice.setName("Alice");
        service.getCustomers().add(alice);

        // Populate inventory
        VideoTape tape1 = new VideoTape();
        tape1.setTapeId("T100");
        tape1.setTitle("The Matrix");
        service.getInventory().add(tape1);

        VideoTape tape2 = new VideoTape();
        tape2.setTapeId("T101");
        tape2.setTitle("Inception");
        service.getInventory().add(tape2);

        // Alice rents tape1 on 2025-11-10
        service.addVideoTapeRental("C001", "T100", LocalDate.of(2025, 11, 10));

        // List unreturned tapes
        System.out.println("Unreturned tapes for Alice: " + service.listUnreturnedTapes("C001"));

        // Simulate return after due date (due date is 2025-11-17, return on 2025-11-20)
        RentalItem ri = alice.getRentals().get(0);
        ri.setReturnDate(LocalDate.of(2025, 11, 20));

        // Calculate total price (base fee + overdue)
        double total = service.calculateTotalPrice("C001");
        System.out.println("Total price for Alice: $" + total);
    }
}