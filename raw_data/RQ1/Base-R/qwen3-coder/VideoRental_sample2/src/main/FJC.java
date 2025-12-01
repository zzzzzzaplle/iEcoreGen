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

    /**
     * Constructs a new Customer with default values.
     */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Gets the account ID of the customer.
     *
     * @return the account ID
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Sets the account ID of the customer.
     *
     * @param accountId the account ID to set
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * Gets the ID card barcode of the customer.
     *
     * @return the ID card barcode
     */
    public String getIdCardBarcode() {
        return idCardBarcode;
    }

    /**
     * Sets the ID card barcode of the customer.
     *
     * @param idCardBarcode the ID card barcode to set
     */
    public void setIdCardBarcode(String idCardBarcode) {
        this.idCardBarcode = idCardBarcode;
    }

    /**
     * Gets the list of rentals for this customer.
     *
     * @return the list of rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of rentals for this customer.
     *
     * @param rentals the list of rentals to set
     */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Adds a rental to this customer's rental list.
     *
     * @param rental the rental to add
     */
    public void addRental(Rental rental) {
        this.rentals.add(rental);
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

    /**
     * Gets the barcode ID of the video tape.
     *
     * @return the barcode ID
     */
    public String getBarcodeId() {
        return barcodeId;
    }

    /**
     * Sets the barcode ID of the video tape.
     *
     * @param barcodeId the barcode ID to set
     */
    public void setBarcodeId(String barcodeId) {
        this.barcodeId = barcodeId;
    }

    /**
     * Gets the title of the video tape.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the video tape.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Checks if the video tape is available.
     *
     * @return true if available, false otherwise
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets the availability status of the video tape.
     *
     * @param available the availability status to set
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }
}

/**
 * Represents a rental transaction in the video rental system.
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

    /**
     * Gets the rental ID.
     *
     * @return the rental ID
     */
    public String getRentalId() {
        return rentalId;
    }

    /**
     * Sets the rental ID.
     *
     * @param rentalId the rental ID to set
     */
    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    /**
     * Gets the customer associated with this rental.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer associated with this rental.
     *
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the video tape associated with this rental.
     *
     * @return the video tape
     */
    public VideoTape getTape() {
        return tape;
    }

    /**
     * Sets the video tape associated with this rental.
     *
     * @param tape the video tape to set
     */
    public void setTape(VideoTape tape) {
        this.tape = tape;
    }

    /**
     * Gets the rental date.
     *
     * @return the rental date
     */
    public LocalDate getRentalDate() {
        return rentalDate;
    }

    /**
     * Sets the rental date.
     *
     * @param rentalDate the rental date to set
     */
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    /**
     * Gets the due date.
     *
     * @return the due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date.
     *
     * @param dueDate the due date to set
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the return date.
     *
     * @return the return date
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date.
     *
     * @param returnDate the return date to set
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Gets the number of rental days.
     *
     * @return the rental days
     */
    public int getRentalDays() {
        return rentalDays;
    }

    /**
     * Sets the number of rental days.
     *
     * @param rentalDays the rental days to set
     */
    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    /**
     * Gets the base rental fee.
     *
     * @return the base rental fee
     */
    public double getBaseRentalFee() {
        return baseRentalFee;
    }

    /**
     * Sets the base rental fee.
     *
     * @param baseRentalFee the base rental fee to set
     */
    public void setBaseRentalFee(double baseRentalFee) {
        this.baseRentalFee = baseRentalFee;
    }

    /**
     * Gets the overdue fee.
     *
     * @return the overdue fee
     */
    public double getOverdueFee() {
        return overdueFee;
    }

    /**
     * Sets the overdue fee.
     *
     * @param overdueFee the overdue fee to set
     */
    public void setOverdueFee(double overdueFee) {
        this.overdueFee = overdueFee;
    }

    /**
     * Checks if this rental is active (not returned).
     *
     * @return true if active, false otherwise
     */
    public boolean isActive() {
        return this.returnDate == null;
    }

    /**
     * Calculates the past-due amount for this video rental.
     * If return date ≤ due date → overdue amount = 0.
     * If return date > due date → overdue days = (return date - due date), overdue fee = overdue days × $0.5.
     * If the tape has not been returned (return date is null) → overdue day = current date – due date;
     * overdue fee = overdueDays × $0.50.
     * Dates are in "yyyy-MM-dd" format.
     *
     * @return the fee rounded to two decimal places
     */
    public double calculatePastDueAmount() {
        LocalDate referenceDate;
        
        if (this.returnDate != null) {
            // If tape has been returned, use return date as reference
            referenceDate = this.returnDate;
        } else {
            // If tape has not been returned, use current date as reference
            referenceDate = LocalDate.now();
        }
        
        // Calculate overdue days
        if (!referenceDate.isAfter(this.dueDate)) {
            // Return date is before or equal to due date, no overdue
            return 0.0;
        }
        
        long overdueDays = ChronoUnit.DAYS.between(this.dueDate, referenceDate);
        double overdueFee = overdueDays * 0.5;
        
        // Round to two decimal places
        return Math.round(overdueFee * 100.0) / 100.0;
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

    /**
     * Gets the list of customers in the system.
     *
     * @return the list of customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Sets the list of customers in the system.
     *
     * @param customers the list of customers to set
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Gets the list of video tapes in the system.
     *
     * @return the list of video tapes
     */
    public List<VideoTape> getTapes() {
        return tapes;
    }

    /**
     * Sets the list of video tapes in the system.
     *
     * @param tapes the list of video tapes to set
     */
    public void setTapes(List<VideoTape> tapes) {
        this.tapes = tapes;
    }

    /**
     * Gets the list of rentals in the system.
     *
     * @return the list of rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of rentals in the system.
     *
     * @param rentals the list of rentals to set
     */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Checks tape availability for a given date.
     * For the given current date, a tape is unavailable if it belongs to any active rental
     * whose return date is null.
     *
     * @param tapeId the barcode ID of the tape to check
     * @param currentDate the date to check availability for
     * @return true if the tape is available; otherwise, false
     */
    public boolean checkTapeAvailability(String tapeId, LocalDate currentDate) {
        // Find the tape
        VideoTape tape = null;
        for (VideoTape t : this.tapes) {
            if (t.getBarcodeId().equals(tapeId)) {
                tape = t;
                break;
            }
        }
        
        // If tape doesn't exist, it's not available
        if (tape == null) {
            return false;
        }
        
        // Check if tape is part of any active rental
        for (Rental rental : this.rentals) {
            if (rental.getTape() != null && 
                rental.getTape().getBarcodeId().equals(tapeId) && 
                rental.isActive()) {
                return false; // Tape is rented out and not returned
            }
        }
        
        return true; // Tape is available
    }

    /**
     * Adds a video tape rental after verifying customer has <20 rentals and no past-due amount,
     * and verifying the tape is available.
     *
     * @param customerId the ID of the customer
     * @param tapeId the barcode ID of the tape to rent
     * @param rentalDays the number of days to rent the tape
     * @return true if all checks pass and the rental is processed; otherwise, false
     */
    public boolean addVideoTapeRental(String customerId, String tapeId, int rentalDays) {
        // Find the customer
        Customer customer = null;
        for (Customer c : this.customers) {
            if (c.getAccountId().equals(customerId)) {
                customer = c;
                break;
            }
        }
        
        // If customer doesn't exist, cannot process rental
        if (customer == null) {
            return false;
        }
        
        // Check if customer has 20 or more active rentals
        int activeRentals = 0;
        double totalPastDue = 0.0;
        for (Rental rental : customer.getRentals()) {
            if (rental.isActive()) {
                activeRentals++;
                totalPastDue += rental.calculatePastDueAmount();
            }
        }
        
        if (activeRentals >= 20) {
            return false; // Customer has maximum rentals
        }
        
        // Check if customer has past-due amount
        if (totalPastDue > 0) {
            return false; // Customer has past-due amount
        }
        
        // Check if tape is available
        if (!checkTapeAvailability(tapeId, LocalDate.now())) {
            return false; // Tape is not available
        }
        
        // Find the tape
        VideoTape tape = null;
        for (VideoTape t : this.tapes) {
            if (t.getBarcodeId().equals(tapeId)) {
                tape = t;
                break;
            }
        }
        
        // If tape doesn't exist, cannot process rental
        if (tape == null) {
            return false;
        }
        
        // Create new rental
        Rental rental = new Rental();
        rental.setRentalId("R" + (this.rentals.size() + 1)); // Simple ID generation
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.now());
        rental.setDueDate(LocalDate.now().plusDays(rentalDays));
        rental.setRentalDays(rentalDays);
        rental.setBaseRentalFee(rentalDays * 1.0); // $1 per day
        
        // Add rental to system and customer
        this.rentals.add(rental);
        customer.addRental(rental);
        
        return true;
    }

    /**
     * Calculates the total price for a customer's rental transaction.
     * Each rental total price = base rental fee + overdue fee.
     * Base rental fee = rental days × $1 per day.
     * Any partial rental day counts as a full day.
     *
     * @param customerId the ID of the customer
     * @return the total rounded to two decimal places
     */
    public double calculateTotalPrice(String customerId) {
        // Find the customer
        Customer customer = null;
        for (Customer c : this.customers) {
            if (c.getAccountId().equals(customerId)) {
                customer = c;
                break;
            }
        }
        
        // If customer doesn't exist, total is 0
        if (customer == null) {
            return 0.0;
        }
        
        double total = 0.0;
        for (Rental rental : customer.getRentals()) {
            if (rental.isActive()) { // Only consider active rentals
                total += rental.getBaseRentalFee() + rental.calculatePastDueAmount();
            }
        }
        
        // Round to two decimal places
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Lists unreturned tapes for a customer.
     * Retrieves a list of all tapes id rented by a customer that have not been returned.
     *
     * @param customerId the ID of the customer
     * @return a list of unreturned tape IDs, or an empty list if the customer has no active rentals
     */
    public List<String> listUnreturnedTapes(String customerId) {
        List<String> unreturnedTapeIds = new ArrayList<>();
        
        // Find the customer
        Customer customer = null;
        for (Customer c : this.customers) {
            if (c.getAccountId().equals(customerId)) {
                customer = c;
                break;
            }
        }
        
        // If customer doesn't exist, return empty list
        if (customer == null) {
            return unreturnedTapeIds;
        }
        
        // Collect unreturned tape IDs
        for (Rental rental : customer.getRentals()) {
            if (rental.isActive() && rental.getTape() != null) {
                unreturnedTapeIds.add(rental.getTape().getBarcodeId());
            }
        }
        
        return unreturnedTapeIds;
    }
}