import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

 class Customer {
    private String accountNumber;
    private String idCard;
    private List<Rental> rentals;
    
    public Customer() {
        this.rentals = new ArrayList<>();
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getIdCard() {
        return idCard;
    }
    
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    
    public List<Rental> getRentals() {
        return rentals;
    }
    
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }
    
    /**
     * Checks if customer has any past due amount
     * @return true if customer has past due amount, false otherwise
     */
    public boolean hasPastDueAmount() {
        for (Rental rental : rentals) {
            if (rental.calculateOverdueFee(LocalDate.now()) > 0) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets the number of active rentals (rentals not returned)
     * @return count of active rentals
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
     * Lists all unreturned tapes for this customer
     * @return list of tape IDs for unreturned rentals
     */
    public List<String> listUnreturnedTapes() {
        List<String> unreturnedTapes = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getReturnDate() == null) {
                unreturnedTapes.add(rental.getTape().getBarcodeId());
            }
        }
        return unreturnedTapes;
    }
}

 class VideoTape {
    private String barcodeId;
    private String title;
    private boolean available;
    
    public VideoTape() {
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

 class Rental {
    private VideoTape tape;
    private Customer customer;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double baseRentalFee;
    private static final double DAILY_RATE = 1.0;
    private static final double OVERDUE_RATE = 0.5;
    
    public Rental() {
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
    
    /**
     * Calculates the overdue fee for this rental
     * @param currentDate the current date to calculate overdue fees against
     * @return overdue fee rounded to two decimal places
     */
    public double calculateOverdueFee(LocalDate currentDate) {
        LocalDate comparisonDate = (returnDate != null) ? returnDate : currentDate;
        
        if (comparisonDate.isAfter(dueDate)) {
            long overdueDays = ChronoUnit.DAYS.between(dueDate, comparisonDate);
            double fee = overdueDays * OVERDUE_RATE;
            return Math.round(fee * 100.0) / 100.0;
        }
        
        return 0.0;
    }
    
    /**
     * Calculates the total price for this rental including base fee and overdue fee
     * @param currentDate the current date for fee calculation
     * @return total price rounded to two decimal places
     */
    public double calculateTotalPrice(LocalDate currentDate) {
        long rentalDays = ChronoUnit.DAYS.between(rentalDate, (returnDate != null) ? returnDate : currentDate);
        rentalDays = Math.max(1, rentalDays); // Minimum 1 day rental
        
        double baseFee = rentalDays * DAILY_RATE;
        double overdueFee = calculateOverdueFee(currentDate);
        double total = baseFee + overdueFee;
        
        return Math.round(total * 100.0) / 100.0;
    }
    
    /**
     * Checks if this rental is active (not returned)
     * @return true if rental is active, false otherwise
     */
    public boolean isActive() {
        return returnDate == null;
    }
}

 class VideoRentalSystem {
    private List<Customer> customers;
    private List<VideoTape> inventory;
    private List<Rental> rentals;
    
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
     * Checks tape availability for a given date
     * @param tape the video tape to check availability for
     * @param currentDate the date to check availability against
     * @return true if the tape is available, false otherwise
     */
    public boolean isTapeAvailable(VideoTape tape, LocalDate currentDate) {
        for (Rental rental : rentals) {
            if (rental.getTape().equals(tape) && rental.isActive()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Adds a video tape rental after verifying all constraints
     * @param customer the customer renting the tape
     * @param tape the tape to be rented
     * @param rentalDate the rental start date
     * @param dueDate the rental due date
     * @param currentDate the current date for validation
     * @return true if rental is processed successfully, false otherwise
     */
    public boolean addVideoRental(Customer customer, VideoTape tape, LocalDate rentalDate, LocalDate dueDate, LocalDate currentDate) {
        // Check if customer has less than 20 rentals
        if (customer.getActiveRentalCount() >= 20) {
            return false;
        }
        
        // Check if customer has past due amount
        if (customer.hasPastDueAmount()) {
            return false;
        }
        
        // Check if tape is available
        if (!isTapeAvailable(tape, currentDate)) {
            return false;
        }
        
        // Create and add new rental
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        
        customer.getRentals().add(rental);
        rentals.add(rental);
        
        return true;
    }
    
    /**
     * Calculates the total price for a customer's rental transaction
     * @param customer the customer whose rentals to calculate
     * @param currentDate the current date for fee calculation
     * @return total price rounded to two decimal places
     */
    public double calculateTotalPrice(Customer customer, LocalDate currentDate) {
        double total = 0.0;
        
        for (Rental rental : customer.getRentals()) {
            if (rental.isActive()) {
                total += rental.calculateTotalPrice(currentDate);
            }
        }
        
        return Math.round(total * 100.0) / 100.0;
    }
    
    /**
     * Lists unreturned tapes for a customer
     * @param customer the customer to check
     * @return list of tape IDs for unreturned rentals
     */
    public List<String> listUnreturnedTapes(Customer customer) {
        return customer.listUnreturnedTapes();
    }
    
    /**
     * Processes tape return by updating return date and calculating fees
     * @param rental the rental to return
     * @param returnDate the return date
     * @return overdue fee for the returned tape
     */
    public double returnTape(Rental rental, LocalDate returnDate) {
        rental.setReturnDate(returnDate);
        return rental.calculateOverdueFee(returnDate);
    }
    
    /**
     * Finds a customer by account number
     * @param accountNumber the account number to search for
     * @return the customer with matching account number, or null if not found
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
     * Finds a video tape by barcode ID
     * @param barcodeId the barcode ID to search for
     * @return the video tape with matching barcode ID, or null if not found
     */
    public VideoTape findTapeByBarcodeId(String barcodeId) {
        for (VideoTape tape : inventory) {
            if (tape.getBarcodeId().equals(barcodeId)) {
                return tape;
            }
        }
        return null;
    }
}