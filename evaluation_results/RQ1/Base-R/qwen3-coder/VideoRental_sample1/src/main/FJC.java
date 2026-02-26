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
        this.pastDueAmount = 0.0;
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
     * Checks if the customer has any past due amount.
     *
     * @return true if there is a past due amount greater than 0, false otherwise
     */
    public boolean hasPastDueAmount() {
        return this.pastDueAmount > 0;
    }

    /**
     * Gets the count of currently active rentals (not returned).
     *
     * @return the number of active rentals
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
        this.available = true;
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
        this.baseRentalFee = 0.0;
        this.overdueFee = 0.0;
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
     * Calculates the past-due amount for this rental.
     * If return date ≤ due date → overdue amount = 0.
     * If return date > due date → overdue days = (return date - due date), overdue fee = overdue days × $0.5.
     * If the tape has not been returned (return date is null) → overdue day = current date – due date;
     * overdue fee = overdueDays × $0.50.
     *
     * @return the calculated overdue fee rounded to two decimal places
     */
    public double calculatePastDueAmount() {
        LocalDate currentDate = LocalDate.now();
        long overdueDays = 0;

        if (this.returnDate == null) {
            // Tape not returned yet
            if (currentDate.isAfter(this.dueDate)) {
                overdueDays = ChronoUnit.DAYS.between(this.dueDate, currentDate);
            }
        } else {
            // Tape has been returned
            if (this.returnDate.isAfter(this.dueDate)) {
                overdueDays = ChronoUnit.DAYS.between(this.dueDate, this.returnDate);
            }
        }

        double fee = overdueDays * 0.5;
        return Math.round(fee * 100.0) / 100.0; // Round to 2 decimal places
    }
}

/**
 * Represents a rental transaction containing multiple rentals.
 */
class RentalTransaction {
    private String transactionId;
    private Customer customer;
    private List<Rental> rentals;
    private double totalAmount;

    /**
     * Default constructor for RentalTransaction.
     */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
        this.totalAmount = 0.0;
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

    /**
     * Calculates the total price for this rental transaction.
     * Each rental total price = base rental fee + overdue fee.
     * Base rental fee = rental days × $1 per day. Any partial rental day counts as a full day.
     *
     * @return the total price rounded to two decimal places
     */
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Rental rental : this.rentals) {
            double baseFee = rental.getRentalDays() * 1.0;
            double overdueFee = rental.calculatePastDueAmount();
            total += baseFee + overdueFee;
        }
        return Math.round(total * 100.0) / 100.0; // Round to 2 decimal places
    }
}

/**
 * Manages the video rental system operations.
 */
class RentalSystem {
    private List<Customer> customers;
    private List<VideoTape> videoTapes;
    private List<RentalTransaction> transactions;

    /**
     * Default constructor for RentalSystem.
     */
    public RentalSystem() {
        this.customers = new ArrayList<>();
        this.videoTapes = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    // Getters and setters
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<VideoTape> getVideoTapes() {
        return videoTapes;
    }

    public void setVideoTapes(List<VideoTape> videoTapes) {
        this.videoTapes = videoTapes;
    }

    public List<RentalTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<RentalTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Checks tape availability for a given date.
     * For the given current date, a tape is unavailable if it belongs to any active rental whose return date is null.
     *
     * @param tapeId the barcode ID of the tape to check
     * @return true if the tape is available, false otherwise
     */
    public boolean checkTapeAvailability(String tapeId) {
        // First check if tape exists
        VideoTape tape = findTapeById(tapeId);
        if (tape == null) {
            return false;
        }

        // Check if tape is part of any active rental
        for (Customer customer : customers) {
            for (Rental rental : customer.getRentals()) {
                if (tapeId.equals(rental.getTapeId()) && rental.getReturnDate() == null) {
                    return false; // Tape is rented out and not returned
                }
            }
        }
        return true; // Tape is available
    }

    /**
     * Adds a video tape rental after verifying customer and tape conditions.
     * Verifies customer has <20 rentals and no past-due amount, and verifies the tape is available.
     *
     * @param customer the customer requesting the rental
     * @param tapeId   the barcode ID of the tape to rent
     * @param rentalDays the number of days for the rental
     * @return true if all checks pass and the rental is processed, false otherwise
     */
    public boolean addVideoTapeRental(Customer customer, String tapeId, int rentalDays) {
        // Check if customer has past due amount
        if (customer.hasPastDueAmount()) {
            return false;
        }

        // Check if customer has less than 20 active rentals
        if (customer.getActiveRentalsCount() >= 20) {
            return false;
        }

        // Check if tape is available
        if (!checkTapeAvailability(tapeId)) {
            return false;
        }

        // Process the rental
        Rental rental = new Rental();
        rental.setTapeId(tapeId);
        rental.setRentalDate(LocalDate.now());
        rental.setDueDate(LocalDate.now().plusDays(rentalDays));
        rental.setRentalDays(rentalDays);
        rental.setBaseRentalFee(rentalDays * 1.0);

        customer.getRentals().add(rental);
        return true;
    }

    /**
     * Lists unreturned tapes for a customer.
     * Retrieves a list of all tapes ID rented by a customer that have not been returned.
     *
     * @param customer the customer to check
     * @return a list of unreturned tape IDs, or an empty list if none
     */
    public List<String> listUnreturnedTapes(Customer customer) {
        List<String> unreturnedTapes = new ArrayList<>();
        for (Rental rental : customer.getRentals()) {
            if (rental.getReturnDate() == null) {
                unreturnedTapes.add(rental.getTapeId());
            }
        }
        return unreturnedTapes;
    }

    /**
     * Helper method to find a tape by its ID.
     *
     * @param tapeId the ID of the tape to find
     * @return the VideoTape object if found, null otherwise
     */
    private VideoTape findTapeById(String tapeId) {
        for (VideoTape tape : videoTapes) {
            if (tapeId.equals(tape.getBarcodeId())) {
                return tape;
            }
        }
        return null;
    }
}