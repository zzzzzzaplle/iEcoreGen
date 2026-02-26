import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer in the video rental system
 */
class Customer {
    private String accountNumber;
    private String idCardNumber;
    private String name;
    private List<Rental> rentals;
    private double pastDueAmount;

    /**
     * Default constructor
     */
    public Customer() {
        this.rentals = new ArrayList<>();
        this.pastDueAmount = 0.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
     * Gets the number of active rentals (rentals that haven't been returned)
     * @return the count of active rentals
     */
    public int getActiveRentalCount() {
        int count = 0;
        for (Rental rental : rentals) {
            if (rental.getReturnDate() == null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if customer has any past due amount
     * @return true if past due amount > 0, false otherwise
     */
    public boolean hasPastDueAmount() {
        return pastDueAmount > 0;
    }
}

/**
 * Represents a video tape in the inventory
 */
class VideoTape {
    private String barCodeId;
    private String title;
    private boolean available;

    /**
     * Default constructor
     */
    public VideoTape() {
        this.available = true;
    }

    public String getBarCodeId() {
        return barCodeId;
    }

    public void setBarCodeId(String barCodeId) {
        this.barCodeId = barCodeId;
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
 * Represents a rental transaction for a video tape
 */
class Rental {
    private String rentalId;
    private VideoTape tape;
    private Customer customer;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double baseRentalFee;
    private double overdueFee;

    /**
     * Default constructor
     */
    public Rental() {
        this.rentalDate = LocalDate.now();
        this.dueDate = rentalDate.plusDays(7); // Default 7-day rental period
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

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
     * Checks if this rental is currently active (not returned)
     * @return true if return date is null, false otherwise
     */
    public boolean isActive() {
        return returnDate == null;
    }
}

/**
 * Main system class that handles video rental operations
 */
class VideoRentalSystem {
    private List<Customer> customers;
    private List<VideoTape> inventory;
    private List<Rental> rentals;

    /**
     * Default constructor
     */
    public VideoRentalSystem() {
        this.customers = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

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

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Calculates the past-due amount for a video rental
     * @param rental The rental to calculate overdue fee for
     * @param currentDate The current date in "yyyy-MM-dd" format
     * @return The overdue fee rounded to two decimal places
     */
    public double calculatePastDueAmount(Rental rental, String currentDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate current = LocalDate.parse(currentDate, formatter);
        LocalDate dueDate = rental.getDueDate();
        
        long overdueDays;
        
        if (rental.getReturnDate() != null) {
            // Tape has been returned
            if (rental.getReturnDate().isAfter(dueDate)) {
                overdueDays = ChronoUnit.DAYS.between(dueDate, rental.getReturnDate());
            } else {
                overdueDays = 0;
            }
        } else {
            // Tape not returned yet
            if (current.isAfter(dueDate)) {
                overdueDays = ChronoUnit.DAYS.between(dueDate, current);
            } else {
                overdueDays = 0;
            }
        }
        
        double overdueFee = overdueDays * 0.5;
        return Math.round(overdueFee * 100.0) / 100.0;
    }

    /**
     * Checks tape availability for a given date
     * @param tape The video tape to check availability for
     * @param currentDate The current date in "yyyy-MM-dd" format
     * @return true if the tape is available, false otherwise
     */
    public boolean checkTapeAvailability(VideoTape tape, String currentDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate current = LocalDate.parse(currentDate, formatter);
        
        for (Rental rental : rentals) {
            if (rental.getTape().equals(tape) && rental.isActive()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a video tape rental after verifying all constraints
     * @param customer The customer requesting the rental
     * @param tape The video tape to be rented
     * @param currentDate The current date in "yyyy-MM-dd" format
     * @return true if all checks pass and rental is processed, false otherwise
     */
    public boolean addVideoTapeRental(Customer customer, VideoTape tape, String currentDate) {
        // Check if customer has less than 20 rentals
        if (customer.getActiveRentalCount() >= 20) {
            return false;
        }
        
        // Check if customer has past due amount
        if (customer.hasPastDueAmount()) {
            return false;
        }
        
        // Check tape availability
        if (!checkTapeAvailability(tape, currentDate)) {
            return false;
        }
        
        // Create and add rental
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse(currentDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        
        rentals.add(rental);
        customer.getRentals().add(rental);
        tape.setAvailable(false);
        
        return true;
    }

    /**
     * Calculates the total price for a customer's rental transaction
     * @param customer The customer to calculate total for
     * @param currentDate The current date in "yyyy-MM-dd" format
     * @return The total price rounded to two decimal places
     */
    public double calculateTotalPrice(Customer customer, String currentDate) {
        double total = 0.0;
        
        for (Rental rental : customer.getRentals()) {
            if (rental.isActive()) {
                // Calculate base rental fee
                long rentalDays = ChronoUnit.DAYS.between(rental.getRentalDate(), 
                    LocalDate.parse(currentDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))) + 1;
                double baseFee = rentalDays * 1.0;
                
                // Calculate overdue fee
                double overdueFee = calculatePastDueAmount(rental, currentDate);
                
                total += baseFee + overdueFee;
            }
        }
        
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Lists unreturned tapes for a customer
     * @param customer The customer to check for unreturned tapes
     * @return List of bar code IDs of unreturned tapes, empty list if none
     */
    public List<String> listUnreturnedTapes(Customer customer) {
        List<String> unreturnedTapes = new ArrayList<>();
        
        for (Rental rental : customer.getRentals()) {
            if (rental.isActive()) {
                unreturnedTapes.add(rental.getTape().getBarCodeId());
            }
        }
        
        return unreturnedTapes;
    }

    /**
     * Finds a customer by account number
     * @param accountNumber The account number to search for
     * @return The customer if found, null otherwise
     */
    public Customer findCustomerByAccountNumber(String accountNumber) {
        for (Customer customer : customers) {
            if (customer.getAccountNumber().equals(accountNumber)) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Finds a video tape by bar code ID
     * @param barCodeId The bar code ID to search for
     * @return The video tape if found, null otherwise
     */
    public VideoTape findVideoTapeByBarCode(String barCodeId) {
        for (VideoTape tape : inventory) {
            if (tape.getBarCodeId().equals(barCodeId)) {
                return tape;
            }
        }
        return null;
    }
}