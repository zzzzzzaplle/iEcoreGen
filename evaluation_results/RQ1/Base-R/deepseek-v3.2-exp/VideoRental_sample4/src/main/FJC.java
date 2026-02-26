import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a video tape available for rental
 */
class VideoTape {
    private String barCodeId;
    private String title;
    private boolean isAvailable;
    
    public VideoTape() {
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
        return isAvailable;
    }
    
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

/**
 * Represents a customer who can rent videos
 */
class Customer {
    private String accountNumber;
    private String idCardNumber;
    private String name;
    private List<RentalTransaction> rentalTransactions;
    
    public Customer() {
        rentalTransactions = new ArrayList<>();
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
    
    public List<RentalTransaction> getRentalTransactions() {
        return rentalTransactions;
    }
    
    public void setRentalTransactions(List<RentalTransaction> rentalTransactions) {
        this.rentalTransactions = rentalTransactions;
    }
    
    /**
     * Gets the number of active rentals (tapes not returned)
     * @return count of active rentals
     */
    public int getActiveRentalCount() {
        int count = 0;
        for (RentalTransaction rental : rentalTransactions) {
            if (rental.getReturnDate() == null) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Checks if customer has any past-due amount
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return true if customer has past-due amount, false otherwise
     */
    public boolean hasPastDueAmount(String currentDate) {
        for (RentalTransaction rental : rentalTransactions) {
            if (rental.calculateOverdueFee(currentDate) > 0) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Represents a rental transaction for a video tape
 */
class RentalTransaction {
    private String transactionId;
    private Customer customer;
    private VideoTape videoTape;
    private String rentalDate;
    private String dueDate;
    private String returnDate;
    private int rentalDays;
    private double baseRentalFee;
    private double overdueFee;
    
    public RentalTransaction() {
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
    
    public VideoTape getVideoTape() {
        return videoTape;
    }
    
    public void setVideoTape(VideoTape videoTape) {
        this.videoTape = videoTape;
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
     * Calculates the past-due amount for this video rental
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return overdue fee rounded to two decimal places
     */
    public double calculateOverdueFee(String currentDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate due = LocalDate.parse(dueDate, formatter);
        LocalDate returnDateObj;
        
        if (returnDate != null) {
            returnDateObj = LocalDate.parse(returnDate, formatter);
        } else {
            returnDateObj = LocalDate.parse(currentDate, formatter);
        }
        
        if (returnDateObj.isAfter(due)) {
            long overdueDays = ChronoUnit.DAYS.between(due, returnDateObj);
            double fee = overdueDays * 0.5;
            BigDecimal bd = new BigDecimal(Double.toString(fee));
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
        
        return 0.0;
    }
    
    /**
     * Calculates the total price for this rental
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return total price rounded to two decimal places
     */
    public double calculateTotalPrice(String currentDate) {
        baseRentalFee = rentalDays * 1.0; // $1 per day
        overdueFee = calculateOverdueFee(currentDate);
        double total = baseRentalFee + overdueFee;
        
        BigDecimal bd = new BigDecimal(Double.toString(total));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

/**
 * Manages the video rental system operations
 */
class VideoRentalSystem {
    private List<Customer> customers;
    private List<VideoTape> videoInventory;
    private List<RentalTransaction> allRentalTransactions;
    
    public VideoRentalSystem() {
        customers = new ArrayList<>();
        videoInventory = new ArrayList<>();
        allRentalTransactions = new ArrayList<>();
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
    
    public List<RentalTransaction> getAllRentalTransactions() {
        return allRentalTransactions;
    }
    
    public void setAllRentalTransactions(List<RentalTransaction> allRentalTransactions) {
        this.allRentalTransactions = allRentalTransactions;
    }
    
    /**
     * Checks tape availability for a given date
     * @param tapeBarCode the bar code of the tape to check
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return true if the tape is available, false otherwise
     */
    public boolean checkTapeAvailability(String tapeBarCode, String currentDate) {
        for (RentalTransaction rental : allRentalTransactions) {
            if (rental.getVideoTape().getBarCodeId().equals(tapeBarCode) && 
                rental.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Adds a video tape rental after verifying constraints
     * @param customer the customer renting the tape
     * @param tapeBarCode the bar code of the tape to rent
     * @param rentalDate the rental date in "yyyy-MM-dd" format
     * @param rentalDays number of days for rental
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return true if rental is processed successfully, false otherwise
     */
    public boolean addVideoTapeRental(Customer customer, String tapeBarCode, 
                                     String rentalDate, int rentalDays, String currentDate) {
        // Check if customer has less than 20 rentals
        if (customer.getActiveRentalCount() >= 20) {
            return false;
        }
        
        // Check if customer has past-due amount
        if (customer.hasPastDueAmount(currentDate)) {
            return false;
        }
        
        // Check tape availability
        if (!checkTapeAvailability(tapeBarCode, currentDate)) {
            return false;
        }
        
        // Find the video tape
        VideoTape tape = null;
        for (VideoTape v : videoInventory) {
            if (v.getBarCodeId().equals(tapeBarCode)) {
                tape = v;
                break;
            }
        }
        
        if (tape == null) {
            return false;
        }
        
        // Create rental transaction
        RentalTransaction rental = new RentalTransaction();
        rental.setTransactionId("TXN" + System.currentTimeMillis());
        rental.setCustomer(customer);
        rental.setVideoTape(tape);
        rental.setRentalDate(rentalDate);
        rental.setRentalDays(rentalDays);
        
        // Calculate due date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate rentDate = LocalDate.parse(rentalDate, formatter);
        LocalDate dueDate = rentDate.plusDays(rentalDays);
        rental.setDueDate(dueDate.format(formatter));
        
        // Add to customer's transactions and system records
        customer.getRentalTransactions().add(rental);
        allRentalTransactions.add(rental);
        
        return true;
    }
    
    /**
     * Calculates the total price for a customer's rental transaction
     * @param customer the customer whose rentals to calculate
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return total price rounded to two decimal places
     */
    public double calculateTotalPriceForCustomer(Customer customer, String currentDate) {
        double total = 0.0;
        for (RentalTransaction rental : customer.getRentalTransactions()) {
            if (rental.getReturnDate() == null) { // Active rentals only
                total += rental.calculateTotalPrice(currentDate);
            }
        }
        
        BigDecimal bd = new BigDecimal(Double.toString(total));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    /**
     * Lists unreturned tapes for a customer
     * @param customer the customer whose unreturned tapes to list
     * @return list of bar code IDs of unreturned tapes
     */
    public List<String> listUnreturnedTapes(Customer customer) {
        List<String> unreturnedTapes = new ArrayList<>();
        for (RentalTransaction rental : customer.getRentalTransactions()) {
            if (rental.getReturnDate() == null) {
                unreturnedTapes.add(rental.getVideoTape().getBarCodeId());
            }
        }
        return unreturnedTapes;
    }
    
    /**
     * Processes tape return and calculates overdue fees
     * @param tapeBarCode the bar code of the tape being returned
     * @param returnDate the return date in "yyyy-MM-dd" format
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return the overdue fee for the returned tape
     */
    public double processTapeReturn(String tapeBarCode, String returnDate, String currentDate) {
        for (RentalTransaction rental : allRentalTransactions) {
            if (rental.getVideoTape().getBarCodeId().equals(tapeBarCode) && 
                rental.getReturnDate() == null) {
                rental.setReturnDate(returnDate);
                return rental.calculateOverdueFee(currentDate);
            }
        }
        return 0.0;
    }
}