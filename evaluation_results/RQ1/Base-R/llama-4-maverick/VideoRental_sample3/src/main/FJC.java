import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer with an ID card and rental history.
 */
class Customer {
    private String idCard;
    private List<RentalTransaction> rentalTransactions;
    private String accountNumber;

    /**
     * Default constructor for Customer.
     */
    public Customer() {
        this.rentalTransactions = new ArrayList<>();
    }

    /**
     * Gets the ID card of the customer.
     * @return the ID card
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * Sets the ID card of the customer.
     * @param idCard the ID card to set
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * Gets the rental transactions of the customer.
     * @return the rental transactions
     */
    public List<RentalTransaction> getRentalTransactions() {
        return rentalTransactions;
    }

    /**
     * Sets the rental transactions of the customer.
     * @param rentalTransactions the rental transactions to set
     */
    public void setRentalTransactions(List<RentalTransaction> rentalTransactions) {
        this.rentalTransactions = rentalTransactions;
    }

    /**
     * Gets the account number of the customer.
     * @return the account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the account number of the customer.
     * @param accountNumber the account number to set
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}

/**
 * Represents a video tape with a bar code ID.
 */
class VideoTape {
    private String barCodeId;

    /**
     * Default constructor for VideoTape.
     */
    public VideoTape() {}

    /**
     * Gets the bar code ID of the video tape.
     * @return the bar code ID
     */
    public String getBarCodeId() {
        return barCodeId;
    }

    /**
     * Sets the bar code ID of the video tape.
     * @param barCodeId the bar code ID to set
     */
    public void setBarCodeId(String barCodeId) {
        this.barCodeId = barCodeId;
    }
}

/**
 * Represents a rental transaction with multiple rentals.
 */
class RentalTransaction {
    private List<Rental> rentals;
    private double totalPrice;
    private String transactionDate;

    /**
     * Default constructor for RentalTransaction.
     */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Gets the rentals in the transaction.
     * @return the rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Sets the rentals in the transaction.
     * @param rentals the rentals to set
     */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Gets the total price of the transaction.
     * @return the total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the transaction.
     * @param totalPrice the total price to set
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the transaction date.
     * @return the transaction date
     */
    public String getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the transaction date.
     * @param transactionDate the transaction date to set
     */
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}

/**
 * Represents a rental with a video tape and rental details.
 */
class Rental {
    private VideoTape videoTape;
    private String rentalDate;
    private String dueDate;
    private String returnDate;

    /**
     * Default constructor for Rental.
     */
    public Rental() {}

    /**
     * Gets the video tape rented.
     * @return the video tape
     */
    public VideoTape getVideoTape() {
        return videoTape;
    }

    /**
     * Sets the video tape rented.
     * @param videoTape the video tape to set
     */
    public void setVideoTape(VideoTape videoTape) {
        this.videoTape = videoTape;
    }

    /**
     * Gets the rental date.
     * @return the rental date
     */
    public String getRentalDate() {
        return rentalDate;
    }

    /**
     * Sets the rental date.
     * @param rentalDate the rental date to set
     */
    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    /**
     * Gets the due date.
     * @return the due date
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date.
     * @param dueDate the due date to set
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the return date.
     * @return the return date
     */
    public String getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date.
     * @param returnDate the return date to set
     */
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Calculates the past-due amount for the rental.
     * @return the past-due amount
     */
    public double calculatePastDueAmount() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dueDate = LocalDate.parse(this.dueDate, formatter);
        LocalDate returnDate = this.returnDate != null ? LocalDate.parse(this.returnDate, formatter) : LocalDate.now();
        long overdueDays = ChronoUnit.DAYS.between(dueDate, returnDate);
        if (overdueDays <= 0) {
            return 0;
        } else {
            return Math.round(overdueDays * 0.5 * 100.0) / 100.0;
        }
    }

    /**
     * Calculates the total price for the rental.
     * @return the total price
     */
    public double calculateTotalPrice() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate rentalDate = LocalDate.parse(this.rentalDate, formatter);
        LocalDate dueDate = LocalDate.parse(this.dueDate, formatter);
        long rentalDays = ChronoUnit.DAYS.between(rentalDate, dueDate);
        double baseRentalFee = rentalDays * 1.0;
        double overdueFee = calculatePastDueAmount();
        return Math.round((baseRentalFee + overdueFee) * 100.0) / 100.0;
    }
}

/**
 * Represents a video rental system.
 */
class VideoRentalSystem {
    private List<Customer> customers;
    private List<VideoTape> videoTapes;

    /**
     * Default constructor for VideoRentalSystem.
     */
    public VideoRentalSystem() {
        this.customers = new ArrayList<>();
        this.videoTapes = new ArrayList<>();
    }

    /**
     * Gets the customers in the system.
     * @return the customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Sets the customers in the system.
     * @param customers the customers to set
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Gets the video tapes in the system.
     * @return the video tapes
     */
    public List<VideoTape> getVideoTapes() {
        return videoTapes;
    }

    /**
     * Sets the video tapes in the system.
     * @param videoTapes the video tapes to set
     */
    public void setVideoTapes(List<VideoTape> videoTapes) {
        this.videoTapes = videoTapes;
    }

    /**
     * Checks if a video tape is available for rental on a given date.
     * @param videoTape the video tape to check
     * @param date the date to check
     * @return true if available, false otherwise
     */
    public boolean checkTapeAvailability(VideoTape videoTape, String date) {
        for (Customer customer : customers) {
            for (RentalTransaction transaction : customer.getRentalTransactions()) {
                for (Rental rental : transaction.getRentals()) {
                    if (rental.getVideoTape().getBarCodeId().equals(videoTape.getBarCodeId()) && rental.getReturnDate() == null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Adds a video tape rental for a customer.
     * @param customer the customer renting the tape
     * @param videoTape the video tape to rent
     * @param rentalDate the rental date
     * @param dueDate the due date
     * @return true if the rental is successful, false otherwise
     */
    public boolean addVideoTapeRental(Customer customer, VideoTape videoTape, String rentalDate, String dueDate) {
        if (customer.getRentalTransactions().size() >= 20) {
            return false;
        }
        if (!checkTapeAvailability(videoTape, rentalDate)) {
            return false;
        }
        RentalTransaction transaction = new RentalTransaction();
        Rental rental = new Rental();
        rental.setVideoTape(videoTape);
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        transaction.getRentals().add(rental);
        customer.getRentalTransactions().add(transaction);
        return true;
    }

    /**
     * Calculates the total price for a customer's rental transaction.
     * @param transaction the rental transaction
     * @return the total price
     */
    public double calculateTotalPrice(RentalTransaction transaction) {
        double totalPrice = 0;
        for (Rental rental : transaction.getRentals()) {
            totalPrice += rental.calculateTotalPrice();
        }
        return Math.round(totalPrice * 100.0) / 100.0;
    }

    /**
     * Lists the unreturned tapes for a customer.
     * @param customer the customer
     * @return a list of unreturned tape IDs
     */
    public List<String> listUnreturnedTapes(Customer customer) {
        List<String> unreturnedTapes = new ArrayList<>();
        for (RentalTransaction transaction : customer.getRentalTransactions()) {
            for (Rental rental : transaction.getRentals()) {
                if (rental.getReturnDate() == null) {
                    unreturnedTapes.add(rental.getVideoTape().getBarCodeId());
                }
            }
        }
        return unreturnedTapes;
    }
}