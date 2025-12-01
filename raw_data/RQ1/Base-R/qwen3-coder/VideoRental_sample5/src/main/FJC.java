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
     * Constructs a new Customer with default values.
     */
    public Customer() {
        this.rentals = new ArrayList<>();
        this.pastDueAmount = 0.0;
    }

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
     * @return true if customer has past due amount, false otherwise
     */
    public boolean hasPastDueAmount() {
        return this.pastDueAmount > 0;
    }

    /**
     * Gets the count of active rentals (not returned) for this customer.
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
     * Constructs a new VideoTape with default values.
     */
    public VideoTape() {
        this.available = true;
    }

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
    private String rentalId;
    private Customer customer;
    private VideoTape tape;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private int rentalDays;
    private double baseRentalFee;
    private double overdueFee;

    /**
     * Constructs a new Rental with default values.
     */
    public Rental() {
        this.baseRentalFee = 0.0;
        this.overdueFee = 0.0;
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

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
     * @return the overdue fee rounded to two decimal places
     */
    public double calculatePastDueAmount() {
        LocalDate currentDate = LocalDate.now();
        LocalDate comparisonDate = (this.returnDate != null) ? this.returnDate : currentDate;
        
        if (!comparisonDate.isAfter(this.dueDate)) {
            return 0.0;
        }
        
        long overdueDays = ChronoUnit.DAYS.between(this.dueDate, comparisonDate);
        return Math.round(overdueDays * 0.5 * 100.0) / 100.0;
    }

    /**
     * Calculates the total price for this rental (base fee + overdue fee).
     *
     * @return the total price rounded to two decimal places
     */
    public double calculateTotalPrice() {
        double total = this.baseRentalFee + this.overdueFee;
        return Math.round(total * 100.0) / 100.0;
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
     * Constructs a new RentalTransaction with default values.
     */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
        this.totalAmount = 0.0;
    }

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
     * Calculates the total price for all rentals in this transaction.
     * Each rental total price = base rental fee + overdue fee.
     * Base rental fee = rental days × $1 per day. Any partial rental day counts as a full day.
     *
     * @return the total rounded to two decimal places
     */
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Rental rental : this.rentals) {
            total += rental.calculateTotalPrice();
        }
        return Math.round(total * 100.0) / 100.0;
    }
}

/**
 * Manages the video rental system operations.
 */
class RentalSystem {
    private List<Customer> customers;
    private List<VideoTape> tapes;
    private List<Rental> rentals;

    /**
     * Constructs a new RentalSystem with default values.
     */
    public RentalSystem() {
        this.customers = new ArrayList<>();
        this.tapes = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<VideoTape> getTapes() {
        return tapes;
    }

    public void setTapes(List<VideoTape> tapes) {
        this.tapes = tapes;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Checks tape availability for a given date.
     * For the given current date, a tape is unavailable if it belongs to any active rental
     * whose return date is null.
     *
     * @param tapeBarcode the barcode ID of the tape to check
     * @param currentDate the date to check availability for
     * @return true if the tape is available; otherwise, false
     */
    public boolean isTapeAvailable(String tapeBarcode, LocalDate currentDate) {
        // Find the tape
        VideoTape tape = tapes.stream()
                .filter(t -> t.getBarcodeId().equals(tapeBarcode))
                .findFirst()
                .orElse(null);
        
        if (tape == null) {
            return false; // Tape doesn't exist
        }
        
        // Check if tape is in any active rental (not returned)
        boolean isInActiveRental = rentals.stream()
                .anyMatch(rental -> 
                    rental.getTape() != null &&
                    rental.getTape().getBarcodeId().equals(tapeBarcode) &&
                    rental.getReturnDate() == null
                );
        
        return !isInActiveRental;
    }

    /**
     * Adds a video tape rental after verifying customer and tape conditions.
     * Verifies customer has <20 rentals and no past-due amount.
     * Verifies the tape is available.
     *
     * @param customer the customer requesting the rental
     * @param tapeBarcode the barcode ID of the tape to rent
     * @param rentalDays the number of days to rent the tape
     * @return true if all checks pass and the rental is processed; otherwise, false
     */
    public boolean addVideoTapeRental(Customer customer, String tapeBarcode, int rentalDays) {
        // Verify customer has less than 20 rentals
        if (customer.getActiveRentalsCount() >= 20) {
            return false;
        }
        
        // Verify customer has no past due amount
        if (customer.hasPastDueAmount()) {
            return false;
        }
        
        // Verify the tape is available
        if (!isTapeAvailable(tapeBarcode, LocalDate.now())) {
            return false;
        }
        
        // Find the tape
        VideoTape tape = tapes.stream()
                .filter(t -> t.getBarcodeId().equals(tapeBarcode))
                .findFirst()
                .orElse(null);
        
        if (tape == null) {
            return false;
        }
        
        // Create rental
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.now());
        rental.setDueDate(LocalDate.now().plusDays(rentalDays));
        rental.setRentalDays(rentalDays);
        rental.setBaseRentalFee(rentalDays * 1.0); // $1 per day
        
        // Add rental to system
        rentals.add(rental);
        customer.getRentals().add(rental);
        
        return true;
    }

    /**
     * Lists unreturned tapes for a customer.
     * Retrieves a list of all tapes id rented by a customer that have not been returned.
     *
     * @param customer the customer to check
     * @return a list of unreturned tape barcodes, or an empty list if no active rentals
     */
    public List<String> listUnreturnedTapes(Customer customer) {
        List<String> unreturnedTapes = new ArrayList<>();
        
        for (Rental rental : customer.getRentals()) {
            if (rental.getReturnDate() == null && rental.getTape() != null) {
                unreturnedTapes.add(rental.getTape().getBarcodeId());
            }
        }
        
        return unreturnedTapes;
    }
}