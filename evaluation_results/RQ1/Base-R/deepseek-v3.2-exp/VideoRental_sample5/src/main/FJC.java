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
     * Checks if customer has less than 20 active rentals
     * @return true if customer has less than 20 active rentals, false otherwise
     */
    public boolean hasLessThanMaxRentals() {
        long activeRentals = rentals.stream()
            .filter(rental -> rental.getReturnDate() == null)
            .count();
        return activeRentals < 20;
    }
    
    /**
     * Checks if customer has any past-due amounts
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return true if customer has past-due amounts, false otherwise
     */
    public boolean hasPastDueAmount(String currentDate) {
        return getTotalPastDueAmount(currentDate).compareTo(BigDecimal.ZERO) > 0;
    }
    
    /**
     * Calculates the total past-due amount for all customer's rentals
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return total past-due amount rounded to two decimal places
     */
    public BigDecimal getTotalPastDueAmount(String currentDate) {
        BigDecimal total = BigDecimal.ZERO;
        for (Rental rental : rentals) {
            total = total.add(rental.calculateOverdueFee(currentDate));
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Retrieves a list of all tape IDs rented by the customer that have not been returned
     * @return list of unreturned tape IDs, empty list if no active rentals
     */
    public List<String> getUnreturnedTapes() {
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
    private BigDecimal baseRentalFee;
    
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
    
    public BigDecimal getBaseRentalFee() {
        return baseRentalFee;
    }
    
    public void setBaseRentalFee(BigDecimal baseRentalFee) {
        this.baseRentalFee = baseRentalFee;
    }
    
    /**
     * Calculates the overdue fee for this rental
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return overdue fee rounded to two decimal places
     */
    public BigDecimal calculateOverdueFee(String currentDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate due = LocalDate.parse(dueDate, formatter);
        LocalDate returnOrCurrent = returnDate != null ? 
            LocalDate.parse(returnDate, formatter) : LocalDate.parse(currentDate, formatter);
        
        if (returnOrCurrent.isAfter(due)) {
            long overdueDays = ChronoUnit.DAYS.between(due, returnOrCurrent);
            BigDecimal overdueFee = BigDecimal.valueOf(overdueDays).multiply(BigDecimal.valueOf(0.5));
            return overdueFee.setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Calculates the total price for this rental (base fee + overdue fee)
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return total price rounded to two decimal places
     */
    public BigDecimal calculateTotalPrice(String currentDate) {
        BigDecimal baseFee = BigDecimal.valueOf(rentalDays).multiply(BigDecimal.ONE);
        BigDecimal overdueFee = calculateOverdueFee(currentDate);
        return baseFee.add(overdueFee).setScale(2, RoundingMode.HALF_UP);
    }
}

/**
 * Represents a rental transaction containing multiple rentals
 */
class RentalTransaction {
    private String transactionId;
    private Customer customer;
    private List<Rental> rentals;
    private String transactionDate;
    private BigDecimal totalAmount;
    private BigDecimal amountPaid;
    private BigDecimal change;
    
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
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
    
    public String getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }
    
    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
    
    public BigDecimal getChange() {
        return change;
    }
    
    public void setChange(BigDecimal change) {
        this.change = change;
    }
    
    /**
     * Calculates the total price for all rentals in the transaction
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return total price rounded to two decimal places
     */
    public BigDecimal calculateTotalPrice(String currentDate) {
        BigDecimal total = BigDecimal.ZERO;
        for (Rental rental : rentals) {
            total = total.add(rental.calculateTotalPrice(currentDate));
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Calculates the change to be given to the customer
     * @param amountPaid the amount paid by the customer
     * @return change amount rounded to two decimal places
     */
    public BigDecimal calculateChange(BigDecimal amountPaid) {
        BigDecimal total = calculateTotalPrice(java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return amountPaid.subtract(total).setScale(2, RoundingMode.HALF_UP);
    }
}

/**
 * Main system class for video rental operations
 */
class VideoRentalSystem {
    private List<Customer> customers;
    private List<VideoTape> inventory;
    private List<RentalTransaction> transactions;
    
    public VideoRentalSystem() {
        this.customers = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.transactions = new ArrayList<>();
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
    
    public List<RentalTransaction> getTransactions() {
        return transactions;
    }
    
    public void setTransactions(List<RentalTransaction> transactions) {
        this.transactions = transactions;
    }
    
    /**
     * Checks if a tape is available for rental on a given date
     * @param tapeBarcodeId the barcode ID of the tape to check
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return true if the tape is available, false otherwise
     */
    public boolean isTapeAvailable(String tapeBarcodeId, String currentDate) {
        // Check if tape exists in inventory
        VideoTape tape = inventory.stream()
            .filter(t -> t.getBarcodeId().equals(tapeBarcodeId))
            .findFirst()
            .orElse(null);
            
        if (tape == null) {
            return false;
        }
        
        // Check if tape is marked as available
        if (!tape.isAvailable()) {
            return false;
        }
        
        // Check if tape is in any active rental (return date is null)
        for (RentalTransaction transaction : transactions) {
            for (Rental rental : transaction.getRentals()) {
                if (rental.getTape().getBarcodeId().equals(tapeBarcodeId) && 
                    rental.getReturnDate() == null) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Adds a video tape rental after verifying all constraints
     * @param customerAccountNumber the customer's account number
     * @param tapeBarcodeId the tape's barcode ID
     * @param rentalDate the rental date in "yyyy-MM-dd" format
     * @param rentalDays the number of rental days
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return true if all checks pass and rental is processed, false otherwise
     */
    public boolean addVideoRental(String customerAccountNumber, String tapeBarcodeId, 
                                 String rentalDate, int rentalDays, String currentDate) {
        // Find customer
        Customer customer = customers.stream()
            .filter(c -> c.getAccountNumber().equals(customerAccountNumber))
            .findFirst()
            .orElse(null);
            
        if (customer == null) {
            return false;
        }
        
        // Check if customer has less than 20 rentals
        if (!customer.hasLessThanMaxRentals()) {
            return false;
        }
        
        // Check if customer has past-due amounts
        if (customer.hasPastDueAmount(currentDate)) {
            return false;
        }
        
        // Check if tape is available
        if (!isTapeAvailable(tapeBarcodeId, currentDate)) {
            return false;
        }
        
        // Find tape
        VideoTape tape = inventory.stream()
            .filter(t -> t.getBarcodeId().equals(tapeBarcodeId))
            .findFirst()
            .orElse(null);
            
        if (tape == null) {
            return false;
        }
        
        // Create rental
        Rental rental = new Rental();
        rental.setRentalId(java.util.UUID.randomUUID().toString());
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(rentalDate);
        
        // Calculate due date
        LocalDate rentalLocalDate = LocalDate.parse(rentalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate dueLocalDate = rentalLocalDate.plusDays(rentalDays);
        rental.setDueDate(dueLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        
        rental.setRentalDays(rentalDays);
        rental.setBaseRentalFee(BigDecimal.valueOf(rentalDays).multiply(BigDecimal.ONE));
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Create or update transaction
        RentalTransaction currentTransaction = findOrCreateCurrentTransaction(customer, currentDate);
        currentTransaction.getRentals().add(rental);
        currentTransaction.setTotalAmount(currentTransaction.calculateTotalPrice(currentDate));
        
        return true;
    }
    
    /**
     * Finds or creates a current transaction for the customer
     * @param customer the customer
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return the current transaction
     */
    private RentalTransaction findOrCreateCurrentTransaction(Customer customer, String currentDate) {
        // Look for today's transaction
        for (RentalTransaction transaction : transactions) {
            if (transaction.getCustomer().getAccountNumber().equals(customer.getAccountNumber()) &&
                transaction.getTransactionDate().equals(currentDate)) {
                return transaction;
            }
        }
        
        // Create new transaction
        RentalTransaction newTransaction = new RentalTransaction();
        newTransaction.setTransactionId(java.util.UUID.randomUUID().toString());
        newTransaction.setCustomer(customer);
        newTransaction.setTransactionDate(currentDate);
        transactions.add(newTransaction);
        
        return newTransaction;
    }
    
    /**
     * Processes a tape return and calculates any overdue fees
     * @param tapeBarcodeId the tape's barcode ID
     * @param returnDate the return date in "yyyy-MM-dd" format
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return the overdue fee for the returned tape
     */
    public BigDecimal processTapeReturn(String tapeBarcodeId, String returnDate, String currentDate) {
        // Find the active rental for this tape
        Rental rental = findActiveRentalByTapeId(tapeBarcodeId);
        
        if (rental == null) {
            return BigDecimal.ZERO;
        }
        
        // Set return date
        rental.setReturnDate(returnDate);
        
        // Calculate and return overdue fee
        return rental.calculateOverdueFee(currentDate);
    }
    
    /**
     * Finds an active rental by tape ID
     * @param tapeBarcodeId the tape's barcode ID
     * @return the active rental, or null if not found
     */
    private Rental findActiveRentalByTapeId(String tapeBarcodeId) {
        for (RentalTransaction transaction : transactions) {
            for (Rental rental : transaction.getRentals()) {
                if (rental.getTape().getBarcodeId().equals(tapeBarcodeId) && 
                    rental.getReturnDate() == null) {
                    return rental;
                }
            }
        }
        return null;
    }
    
    /**
     * Gets all outstanding video rentals with amounts due for a customer
     * @param customerAccountNumber the customer's account number
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return list of rental information with amounts due
     */
    public List<String> getOutstandingRentals(String customerAccountNumber, String currentDate) {
        List<String> outstandingRentals = new ArrayList<>();
        
        Customer customer = customers.stream()
            .filter(c -> c.getAccountNumber().equals(customerAccountNumber))
            .findFirst()
            .orElse(null);
            
        if (customer == null) {
            return outstandingRentals;
        }
        
        for (Rental rental : customer.getRentals()) {
            if (rental.getReturnDate() == null) {
                BigDecimal amountDue = rental.calculateTotalPrice(currentDate);
                String rentalInfo = String.format("Tape: %s, Due Date: %s, Amount Due: $%.2f", 
                    rental.getTape().getTitle(), rental.getDueDate(), amountDue);
                outstandingRentals.add(rentalInfo);
            }
        }
        
        return outstandingRentals;
    }
}