import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a customer of the video rental store.
 */
 class Customer {
    private String accountNumber;
    private String name;
    private String idCardBarcode;
    private List<Rental> rentals = new ArrayList<>();

    /** Unparameterized constructor */
    public Customer() {
    }

    // -----------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardBarcode() {
        return idCardBarcode;
    }

    public void setIdCardBarcode(String idCardBarcode) {
        this.idCardBarcode = idCardBarcode;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Returns the number of active (unreturned) rentals for this customer.
     *
     * @return number of rentals whose return date is null
     */
    public int getActiveRentalCount() {
        int count = 0;
        for (Rental r : rentals) {
            if (r.getReturnDate() == null) {
                count++;
            }
        }
        return count;
    }
}

/**
 * Represents a video tape in the inventory.
 */
 class VideoTape {
    private String tapeId;
    private String title;

    /** Unparameterized constructor */
    public VideoTape() {
    }

    // -----------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------
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
 * Represents a single rental transaction for a video tape.
 */
 class Rental {
    private Customer customer;
    private VideoTape tape;
    private LocalDate rentalDate;   // date when tape was taken out
    private LocalDate dueDate;      // date when tape should be returned
    private LocalDate returnDate;   // null if not yet returned

    /** Unparameterized constructor */
    public Rental() {
    }

    // -----------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public VideoTape getTape() {
        return tape;
    }

    public void setTape(VideoTape tape) {
        this.tape = tape;
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
}

/**
 * Service class that contains the core business logic for the video rental store.
 */
 class RentalStore {

    // In‑memory storage for the demo. In a real system this would be a DB.
    private List<VideoTape> inventory = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Rental> rentals = new ArrayList<>();

    /** Unparameterized constructor */
    public RentalStore() {
    }

    // -----------------------------------------------------------------
    // Getters and Setters for the internal collections (mainly for testing)
    // -----------------------------------------------------------------
    public List<VideoTape> getInventory() {
        return inventory;
    }

    public void setInventory(List<VideoTape> inventory) {
        this.inventory = inventory;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Calculates the overdue fee for a given rental.
     *
     * @param rental the rental whose overdue fee is to be calculated
     * @return overdue fee rounded to two decimal places
     */
    public double calculateOverdueFee(Rental rental) {
        if (rental == null) {
            return 0.0;
        }

        LocalDate due = rental.getDueDate();
        LocalDate compareDate = (rental.getReturnDate() != null) ? rental.getReturnDate() : LocalDate.now();

        long daysOverdue = ChronoUnit.DAYS.between(due, compareDate);
        if (daysOverdue <= 0) {
            return 0.0;
        }
        double fee = daysOverdue * 0.5;
        return Math.round(fee * 100.0) / 100.0;
    }

    /**
     * Checks whether a tape is available for rental on the given date.
     *
     * @param tapeId      the barcode identifier of the tape
     * @param currentDate the date for which availability is checked (format yyyy-MM-dd)
     * @return {@code true} if the tape is not currently rented out, {@code false} otherwise
     */
    public boolean isTapeAvailable(String tapeId, String currentDate) {
        LocalDate checkDate = LocalDate.parse(currentDate, DateTimeFormatter.ISO_LOCAL_DATE);
        for (Rental r : rentals) {
            if (r.getTape().getTapeId().equals(tapeId) && r.getReturnDate() == null) {
                // The tape is currently out; it will be unavailable regardless of the supplied date.
                return false;
            }
        }
        return true;
    }

    /**
     * Attempts to add a new video tape rental.
     *
     * @param customer   the customer who wants to rent the tape
     * @param tapeId     the barcode id of the tape to rent
     * @param rentalDate the date the rental starts (format yyyy-MM-dd)
     * @param rentalDays number of days the customer intends to keep the tape
     * @return {@code true} if the rental is successfully created; {@code false} otherwise
     */
    public boolean addVideoTapeRental(Customer customer, String tapeId, String rentalDate, int rentalDays) {
        // 1. Check that the customer exists in the system
        if (customer == null) {
            return false;
        }

        // 2. Past‑due check – if any active rental has a non‑zero overdue fee, rental is denied.
        for (Rental r : customer.getRentals()) {
            if (r.getReturnDate() == null) {
                double overdue = calculateOverdueFee(r);
                if (overdue > 0.0) {
                    return false;
                }
            }
        }

        // 3. Maximum active rentals limit (20)
        if (customer.getActiveRentalCount() >= 20) {
            return false;
        }

        // 4. Tape availability
        if (!isTapeAvailable(tapeId, rentalDate)) {
            return false;
        }

        // 5. Locate tape in inventory
        VideoTape tape = findTapeById(tapeId);
        if (tape == null) {
            return false;
        }

        // 6. Create the rental
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        LocalDate start = LocalDate.parse(rentalDate, DateTimeFormatter.ISO_LOCAL_DATE);
        rental.setRentalDate(start);
        rental.setDueDate(start.plusDays(rentalDays));
        rental.setReturnDate(null); // not yet returned

        // 7. Persist
        rentals.add(rental);
        customer.getRentals().add(rental);
        return true;
    }

    /**
     * Calculates the total price for all active rentals of a customer.
     * Base rental fee = rental days × $1.00 (partial days count as a full day).
     * Overdue fee is taken from {@link #calculateOverdueFee(Rental)}.
     *
     * @param customer the customer whose total is to be calculated
     * @return total amount rounded to two decimal places
     */
    public double calculateTotalPrice(Customer customer) {
        if (customer == null) {
            return 0.0;
        }
        double total = 0.0;
        for (Rental r : customer.getRentals()) {
            // Only consider rentals that have a start date (should always be true)
            LocalDate end = (r.getReturnDate() != null) ? r.getReturnDate() : LocalDate.now();
            long days = ChronoUnit.DAYS.between(r.getRentalDate(), end) + 1; // partial day counts as full
            double base = days * 1.0;
            double overdue = calculateOverdueFee(r);
            total += base + overdue;
        }
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Retrieves a list of tape identifiers that are currently not returned for a given customer.
     *
     * @param customer the customer whose unreturned tapes are requested
     * @return a list of tape ids; empty list if none
     */
    public List<String> listUnreturnedTapes(Customer customer) {
        if (customer == null) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        for (Rental r : customer.getRentals()) {
            if (r.getReturnDate() == null) {
                result.add(r.getTape().getTapeId());
            }
        }
        return result;
    }

    /**
     * Helper method to locate a tape in the inventory by its barcode id.
     *
     * @param tapeId the barcode identifier
     * @return the {@link VideoTape} object if found; {@code null} otherwise
     */
    private VideoTape findTapeById(String tapeId) {
        for (VideoTape t : inventory) {
            if (t.getTapeId().equals(tapeId)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Records the return of a tape.
     *
     * @param tapeId        the barcode id of the tape being returned
     * @param returnDateStr the date the tape is returned (format yyyy-MM-dd)
     * @return {@code true} if the return was processed; {@code false} if the tape was not found or already returned
     */
    public boolean processReturn(String tapeId, String returnDateStr) {
        LocalDate returnDate = LocalDate.parse(returnDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        for (Rental r : rentals) {
            if (r.getTape().getTapeId().equals(tapeId) && r.getReturnDate() == null) {
                r.setReturnDate(returnDate);
                // Overdue fee can be queried via calculateOverdueFee(r) if needed.
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the total past‑due amount (sum of overdue fees) for a given customer.
     *
     * @param customer the customer to evaluate
     * @return total overdue amount rounded to two decimal places
     */
    public double getTotalPastDueAmount(Customer customer) {
        if (customer == null) {
            return 0.0;
        }
        double total = 0.0;
        for (Rental r : customer.getRentals()) {
            total += calculateOverdueFee(r);
        }
        return Math.round(total * 100.0) / 100.0;
    }
}