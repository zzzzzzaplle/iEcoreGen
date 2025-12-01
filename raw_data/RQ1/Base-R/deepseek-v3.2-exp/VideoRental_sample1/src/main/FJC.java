import java.math.BigDecimal;
import java.math.RoundingMode;
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
    
    /**
     * Default constructor
     */
    public Customer() {
        this.rentals = new ArrayList<>();
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
    
    /**
     * Checks if customer has less than maximum allowed rentals
     * @return true if customer has less than 20 rentals, false otherwise
     */
    public boolean hasLessThanMaxRentals() {
        return rentals.size() < 20;
    }
    
    /**
     * Calculates the total past-due amount for all rentals
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return total past-due amount rounded to two decimal places
     */
    public BigDecimal calculateTotalPastDueAmount(String currentDate) {
        BigDecimal total = BigDecimal.ZERO;
        for (Rental rental : rentals) {
            total = total.add(rental.calculateOverdueFee(currentDate));
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Checks if customer has any past-due amounts
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return true if customer has past-due amounts, false otherwise
     */
    public boolean hasPastDueAmount(String currentDate) {
        return calculateTotalPastDueAmount(currentDate).compareTo(BigDecimal.ZERO) > 0;
    }
    
    /**
     * Retrieves a list of all tape IDs rented by a customer that have not been returned
     * @return list of unreturned tape IDs, empty list if no active rentals
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

/**
 * Represents a video tape in the inventory
 */
 class VideoTape {
    private String barcodeId;
    private String title;
    private boolean available;
    
    /**
     * Default constructor
     */
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

/**
 * Represents a rental transaction
 */
 class Rental {
    private String rentalId;
    private Customer customer;
    private VideoTape tape;
    private String rentalDate;
    private String dueDate;
    private String returnDate;
    private int rentalDays;
    
    /**
     * Default constructor
     */
    public Rental() {
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
    
    public String getRentalDate() {
        return rentalDate;
    }
    
    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }
    
    public String getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    
    public String getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    
    public int getRentalDays() {
        return rentalDays;
    }
    
    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }
    
    /**
     * Calculates the overdue fee for this rental
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return overdue fee rounded to two decimal places
     */
    public BigDecimal calculateOverdueFee(String currentDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate due = LocalDate.parse(dueDate, formatter);
        LocalDate returnOrCurrent = (returnDate != null) ? LocalDate.parse(returnDate, formatter) : LocalDate.parse(currentDate, formatter);
        
        if (returnOrCurrent.isAfter(due)) {
            long overdueDays = ChronoUnit.DAYS.between(due, returnOrCurrent);
            return BigDecimal.valueOf(overdueDays).multiply(BigDecimal.valueOf(0.5))
                          .setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Calculates the base rental fee for this rental
     * @return base rental fee rounded to two decimal places
     */
    public BigDecimal calculateBaseRentalFee() {
        return BigDecimal.valueOf(rentalDays).multiply(BigDecimal.valueOf(1.0))
                      .setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Calculates the total price for this rental (base fee + overdue fee)
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return total price rounded to two decimal places
     */
    public BigDecimal calculateTotalPrice(String currentDate) {
        BigDecimal baseFee = calculateBaseRentalFee();
        BigDecimal overdueFee = calculateOverdueFee(currentDate);
        return baseFee.add(overdueFee).setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Checks if the rental is active (tape not returned)
     * @return true if rental is active, false otherwise
     */
    public boolean isActive() {
        return returnDate == null;
    }
}

/**
 * Represents the video rental system
 */
 class VideoRentalSystem {
    private List<Customer> customers;
    private List<VideoTape> videoInventory;
    private List<Rental> allRentals;
    
    /**
     * Default constructor
     */
    public VideoRentalSystem() {
        this.customers = new ArrayList<>();
        this.videoInventory = new ArrayList<>();
        this.allRentals = new ArrayList<>();
    }
    
    public List<Customer> getCustomers() {
        return customers;
    }
    
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
    
    public List<VideoTape> getVideoInventory() {
        return videoInventory;
    }
    
    public void setVideoInventory(List<VideoTape> videoInventory) {
        this.videoInventory = videoInventory;
    }
    
    public List<Rental> getAllRentals() {
        return allRentals;
    }
    
    public void setAllRentals(List<Rental> allRentals) {
        this.allRentals = allRentals;
    }
    
    /**
     * Finds a customer by account number
     * @param accountNumber the customer's account number
     * @return the customer object or null if not found
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
     * @param barcodeId the tape's barcode ID
     * @return the video tape object or null if not found
     */
    public VideoTape findVideoTapeByBarcode(String barcodeId) {
        for (VideoTape tape : videoInventory) {
            if (tape.getBarcodeId().equals(barcodeId)) {
                return tape;
            }
        }
        return null;
    }
    
    /**
     * Checks tape availability for a given date
     * @param tape the video tape to check
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return true if the tape is available, false otherwise
     */
    public boolean isTapeAvailable(VideoTape tape, String currentDate) {
        for (Rental rental : allRentals) {
            if (rental.getTape().equals(tape) && rental.isActive()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Adds a video tape rental after verifying all constraints
     * @param customer the customer renting the tape
     * @param tape the video tape to be rented
     * @param rentalDate the rental date in "yyyy-MM-dd" format
     * @param rentalDays number of rental days
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return true if all checks pass and rental is processed, false otherwise
     */
    public boolean addVideoTapeRental(Customer customer, VideoTape tape, String rentalDate, int rentalDays, String currentDate) {
        // Check if customer has less than 20 rentals
        if (!customer.hasLessThanMaxRentals()) {
            return false;
        }
        
        // Check if customer has past-due amounts
        if (customer.hasPastDueAmount(currentDate)) {
            return false;
        }
        
        // Check if tape is available
        if (!isTapeAvailable(tape, currentDate)) {
            return false;
        }
        
        // Create new rental
        Rental rental = new Rental();
        rental.setRentalId("RENT" + System.currentTimeMillis());
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(rentalDate);
        rental.setRentalDays(rentalDays);
        
        // Calculate due date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate rentDate = LocalDate.parse(rentalDate, formatter);
        LocalDate dueDate = rentDate.plusDays(rentalDays);
        rental.setDueDate(dueDate.format(formatter));
        
        // Add rental to customer and system
        customer.getRentals().add(rental);
        allRentals.add(rental);
        
        return true;
    }
    
    /**
     * Calculates the total price for a customer's rental transaction
     * @param customer the customer
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return total price rounded to two decimal places
     */
    public BigDecimal calculateTotalPriceForCustomer(Customer customer, String currentDate) {
        BigDecimal total = BigDecimal.ZERO;
        for (Rental rental : customer.getRentals()) {
            total = total.add(rental.calculateTotalPrice(currentDate));
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Processes tape return by updating return date
     * @param tape the tape being returned
     * @param returnDate the return date in "yyyy-MM-dd" format
     * @return true if successful, false if tape not found in active rentals
     */
    public boolean processTapeReturn(VideoTape tape, String returnDate) {
        for (Rental rental : allRentals) {
            if (rental.getTape().equals(tape) && rental.isActive()) {
                rental.setReturnDate(returnDate);
                return true;
            }
        }
        return false;
    }
}