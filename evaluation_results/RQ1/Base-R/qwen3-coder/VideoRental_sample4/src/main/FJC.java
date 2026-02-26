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
     * Creates a new customer with default values.
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
     * Creates a new video tape with default values.
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
    private String tapeId;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private int rentalDays;
    private double baseRentalFee;
    private double overdueFee;

    /**
     * Creates a new rental with default values.
     */
    public Rental() {
        this.rentalDays = 0;
        this.baseRentalFee = 0.0;
        this.overdueFee = 0.0;
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
     * @return the fee rounded to two decimal places
     */
    public double calculatePastDueAmount() {
        LocalDate comparisonDate;
        
        if (this.returnDate != null) {
            // Tape has been returned
            if (this.returnDate.isAfter(this.dueDate)) {
                long overdueDays = ChronoUnit.DAYS.between(this.dueDate, this.returnDate);
                return Math.round(overdueDays * 0.5 * 100.0) / 100.0;
            } else {
                return 0.0;
            }
        } else {
            // Tape has not been returned
            comparisonDate = LocalDate.now();
            if (comparisonDate.isAfter(this.dueDate)) {
                long overdueDays = ChronoUnit.DAYS.between(this.dueDate, comparisonDate);
                return Math.round(overdueDays * 0.5 * 100.0) / 100.0;
            } else {
                return 0.0;
            }
        }
    }
}

/**
 * Represents the video rental system.
 */
class RentalSystem {
    private List<Customer> customers;
    private List<VideoTape> videoTapes;
    private List<Rental> rentals;

    /**
     * Creates a new rental system with default values.
     */
    public RentalSystem() {
        this.customers = new ArrayList<>();
        this.videoTapes = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

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

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Checks tape availability for a given date.
     * For the given current date, a tape is unavailable if it belongs to any active rental whose return date is null.
     *
     * @param tapeId the barcode ID of the tape to check
     * @return true if the tape is available; otherwise, false
     */
    public boolean checkTapeAvailability(String tapeId) {
        // First check if tape exists
        VideoTape tape = findTapeById(tapeId);
        if (tape == null) {
            return false;
        }
        
        // Check if tape is in any active rental (not returned)
        for (Rental rental : this.rentals) {
            if (tapeId.equals(rental.getTapeId()) && rental.getReturnDate() == null) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Adds a video tape rental after verifying customer and tape conditions.
     * Verifies customer has <20 rentals and no past-due amount.
     * Verifies the tape is available.
     *
     * @param customer the customer requesting the rental
     * @param tapeId   the barcode ID of the tape to rent
     * @param rentalDays the number of days for the rental
     * @return true if all checks pass and the rental is processed; otherwise, false
     */
    public boolean addVideoTapeRental(Customer customer, String tapeId, int rentalDays) {
        // Verify customer has less than 20 rentals
        if (customer.getActiveRentalsCount() >= 20) {
            return false;
        }
        
        // Verify customer has no past-due amount
        if (customer.hasPastDueAmount()) {
            return false;
        }
        
        // Verify the tape is available
        if (!checkTapeAvailability(tapeId)) {
            return false;
        }
        
        // Process the rental
        Rental rental = new Rental();
        rental.setTapeId(tapeId);
        rental.setRentalDate(LocalDate.now());
        rental.setDueDate(LocalDate.now().plusDays(rentalDays));
        rental.setRentalDays(rentalDays);
        rental.setBaseRentalFee(rentalDays * 1.0); // $1 per day
        rental.setOverdueFee(0.0);
        
        // Add rental to system and customer
        this.rentals.add(rental);
        customer.getRentals().add(rental);
        
        return true;
    }

    /**
     * Calculates the total price for a customer's rental transaction.
     * Each rental total price = base rental fee + overdue fee.
     * Base rental fee = rental days × $1 per day.
     * Any partial rental day counts as a full day.
     *
     * @param customer the customer for whom to calculate the total
     * @return the total rounded to two decimal places
     */
    public double calculateTotalPrice(Customer customer) {
        double total = 0.0;
        
        for (Rental rental : customer.getRentals()) {
            if (rental.getReturnDate() == null) { // Only active rentals
                double overdueFee = rental.calculatePastDueAmount();
                total += rental.getBaseRentalFee() + overdueFee;
            }
        }
        
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Lists unreturned tapes for a customer.
     * Retrieves a list of all tapes id rented by a customer that have not been returned.
     *
     * @param customer the customer for whom to list unreturned tapes
     * @return list of unreturned tape IDs, or empty list if none
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
     * @param tapeId the barcode ID of the tape
     * @return the VideoTape object if found, null otherwise
     */
    private VideoTape findTapeById(String tapeId) {
        for (VideoTape tape : this.videoTapes) {
            if (tapeId.equals(tape.getBarcodeId())) {
                return tape;
            }
        }
        return null;
    }
}