import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer in the video rental system.
 */
class Customer {
    private String accountId;
    private String idCardBarcode;
    private List<Rental> rentals;
    private double pastDueAmount;

    /**
     * Default constructor for Customer.
     */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    // Getters and setters
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public double getPastDueAmount() {
        return pastDueAmount;
    }

    public void setPastDueAmount(double pastDueAmount) {
        this.pastDueAmount = pastDueAmount;
    }

    /**
     * Checks if the customer has any past due amounts.
     *
     * @return true if customer has past due amounts, false otherwise
     */
    public boolean hasPastDueAmount() {
        return this.pastDueAmount > 0;
    }

    /**
     * Gets the count of active (unreturned) rentals for this customer.
     *
     * @return number of active rentals
     */
    public int getActiveRentalsCount() {
        return (int) this.rentals.stream()
                .filter(rental -> rental.getReturnDate() == null)
                .count();
    }
}

/**
 * Represents a video tape in the rental system.
 */
class VideoTape {
    private String barcodeId;
    private String title;
    private boolean available;

    /**
     * Default constructor for VideoTape.
     */
    public VideoTape() {
    }

    // Getters and setters
    public String getBarcodeId() {
        return barcodeId;
    }

    public void setBarcodeId(String barcodeId) {
        this.barcodeId = barcodeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

/**
 * Represents a rental transaction for a video tape.
 */
class Rental {
    private String tapeId;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private int rentalDays;
    private double baseRentalFee;
    private double overdueFee;

    /**
     * Default constructor for Rental.
     */
    public Rental() {
    }

    // Getters and setters
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

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public double getBaseRentalFee() {
        return baseRentalFee;
    }

    public void setBaseRentalFee(double baseRentalFee) {
        this.baseRentalFee = baseRentalFee;
    }

    public double getOverdueFee() {
        return overdueFee;
    }

    public void setOverdueFee(double overdueFee) {
        this.overdueFee = overdueFee;
    }

    /**
     * Checks if this rental is currently active (tape not returned).
     *
     * @return true if the tape has not been returned, false otherwise
     */
    public boolean isActive() {
        return this.returnDate == null;
    }
}

/**
 * Represents a complete rental transaction containing multiple rentals.
 */
class RentalTransaction {
    private String transactionId;
    private Customer customer;
    private List<Rental> rentals;
    private double totalAmount;
    private boolean isCompleted;

    /**
     * Default constructor for RentalTransaction.
     */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    // Getters and setters
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}

/**
 * Manages video rental operations and business logic.
 */
class RentalSystem {
    private List<Customer> customers;
    private List<VideoTape> inventory;
    private List<RentalTransaction> transactions;

    /**
     * Default constructor for RentalSystem.
     */
    public RentalSystem() {
        this.customers = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    // Getters and setters
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<VideoTape> getInventory() {
        return inventory;
    }

    public void setInventory(List<VideoTape> inventory) {
        this.inventory = inventory;
    }

    public List<RentalTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<RentalTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Calculates the past-due amount for a video rental.
     * If return date ≤ due date → overdue amount = 0.
     * If return date > due date → overdue days = (return date - due date), overdue fee = overdue days × $0.5.
     * If the tape has not been returned (return date is null) → overdue day = current date – due date;
     * overdue fee = overdueDays × $0.50.
     * Dates are in "yyyy-MM-dd" format. Return the fee rounded to two decimal places.
     *
     * @param dueDate   the due date for the rental
     * @param returnDate the actual return date, or null if not returned yet
     * @return the overdue fee rounded to two decimal places
     */
    public double calculatePastDueAmount(LocalDate dueDate, LocalDate returnDate) {
        long overdueDays;
        if (returnDate == null) {
            // Tape not returned yet
            overdueDays = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        } else if (returnDate.isAfter(dueDate)) {
            // Tape returned late
            overdueDays = ChronoUnit.DAYS.between(dueDate, returnDate);
        } else {
            // Tape returned on time or early
            return 0.0;
        }

        // If overdue days is negative or zero, no fee is due
        if (overdueDays <= 0) {
            return 0.0;
        }

        double fee = overdueDays * 0.5;
        return Math.round(fee * 100.0) / 100.0; // Round to two decimal places
    }

    /**
     * Checks tape availability for a given date. For the given current date,
     * a tape is unavailable if it belongs to any active rental whose return date is null.
     *
     * @param tapeId the barcode ID of the tape to check
     * @return true if the tape is available; otherwise, false
     */
    public boolean checkTapeAvailability(String tapeId) {
        // First check if tape exists in inventory
        boolean tapeExists = inventory.stream()
                .anyMatch(tape -> tape.getBarcodeId().equals(tapeId));
        
        if (!tapeExists) {
            return false;
        }

        // Check if tape is in any active rental
        for (Customer customer : customers) {
            boolean isInActiveRental = customer.getRentals().stream()
                    .anyMatch(rental -> 
                        rental.getTapeId().equals(tapeId) && 
                        rental.getReturnDate() == null
                    );
            
            if (isInActiveRental) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Adds a video tape rental after verifying customer has <20 rentals and no past-due amount,
     * and verifying the tape is available.
     *
     * @param customer the customer requesting the rental
     * @param tapeId   the barcode ID of the tape to rent
     * @param rentalDays the number of days to rent the tape
     * @return true if all checks pass and the rental is processed; otherwise, false
     */
    public boolean addVideoTapeRental(Customer customer, String tapeId, int rentalDays) {
        // Check if customer has past due amount
        if (customer.hasPastDueAmount()) {
            return false;
        }

        // Check if customer has reached maximum rentals (20)
        if (customer.getActiveRentalsCount() >= 20) {
            return false;
        }

        // Check if tape is available
        if (!checkTapeAvailability(tapeId)) {
            return false;
        }

        // Create new rental
        Rental rental = new Rental();
        rental.setTapeId(tapeId);
        rental.setRentalDate(LocalDate.now());
        rental.setDueDate(LocalDate.now().plusDays(rentalDays));
        rental.setRentalDays(rentalDays);
        rental.setBaseRentalFee(rentalDays * 1.0); // $1 per day
        rental.setOverdueFee(0.0);
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        return true;
    }

    /**
     * Calculates the total price for a customer's rental transaction.
     * Each rental total price = base rental fee + overdue fee.
     * Base rental fee = rental days × $1 per day. Any partial rental day counts as a full day.
     * Return the total rounded to two decimal places.
     *
     * @param rentals the list of rentals in the transaction
     * @return the total price rounded to two decimal places
     */
    public double calculateTotalPrice(List<Rental> rentals) {
        double total = 0.0;
        
        for (Rental rental : rentals) {
            double overdueFee = calculatePastDueAmount(
                rental.getDueDate(), 
                rental.getReturnDate()
            );
            total += rental.getBaseRentalFee() + overdueFee;
        }
        
        return Math.round(total * 100.0) / 100.0; // Round to two decimal places
    }

    /**
     * Lists unreturned tapes for a customer.
     * Retrieves a list of all tapes id rented by a customer that have not been returned.
     * Return an empty list if the customer has no active rentals.
     *
     * @param customer the customer to check
     * @return a list of unreturned tape IDs
     */
    public List<String> listUnreturnedTapes(Customer customer) {
        List<String> unreturnedTapes = new ArrayList<>();
        
        for (Rental rental : customer.getRentals()) {
            if (rental.isActive()) {
                unreturnedTapes.add(rental.getTapeId());
            }
        }
        
        return unreturnedTapes;
    }
}