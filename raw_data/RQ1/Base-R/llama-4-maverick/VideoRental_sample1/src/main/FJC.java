import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer with an id, account number, and id card number.
 */
class Customer {
    private String id;
    private String accountNumber;
    private String idCardNumber;
    private List<RentalTransaction> rentalTransactions;

    /**
     * Unparameterized constructor for Customer.
     */
    public Customer() {
        this.rentalTransactions = new ArrayList<>();
    }

    /**
     * Gets the id of the customer.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the customer.
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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

    /**
     * Gets the id card number of the customer.
     * @return the id card number
     */
    public String getIdCardNumber() {
        return idCardNumber;
    }

    /**
     * Sets the id card number of the customer.
     * @param idCardNumber the id card number to set
     */
    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
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
}

/**
 * Represents a video tape with a bar code id.
 */
class VideoTape {
    private String barCodeId;

    /**
     * Unparameterized constructor for VideoTape.
     */
    public VideoTape() {}

    /**
     * Gets the bar code id of the video tape.
     * @return the bar code id
     */
    public String getBarCodeId() {
        return barCodeId;
    }

    /**
     * Sets the bar code id of the video tape.
     * @param barCodeId the bar code id to set
     */
    public void setBarCodeId(String barCodeId) {
        this.barCodeId = barCodeId;
    }
}

/**
 * Represents a rental with a video tape, rental date, due date, and return date.
 */
class Rental {
    private VideoTape videoTape;
    private String rentalDate;
    private String dueDate;
    private String returnDate;

    /**
     * Unparameterized constructor for Rental.
     */
    public Rental() {}

    /**
     * Gets the video tape of the rental.
     * @return the video tape
     */
    public VideoTape getVideoTape() {
        return videoTape;
    }

    /**
     * Sets the video tape of the rental.
     * @param videoTape the video tape to set
     */
    public void setVideoTape(VideoTape videoTape) {
        this.videoTape = videoTape;
    }

    /**
     * Gets the rental date of the rental.
     * @return the rental date
     */
    public String getRentalDate() {
        return rentalDate;
    }

    /**
     * Sets the rental date of the rental.
     * @param rentalDate the rental date to set
     */
    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    /**
     * Gets the due date of the rental.
     * @return the due date
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date of the rental.
     * @param dueDate the due date to set
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the return date of the rental.
     * @return the return date
     */
    public String getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date of the rental.
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
        LocalDate returnDate = this.returnDate == null ? LocalDate.now() : LocalDate.parse(this.returnDate, formatter);
        long overdueDays = ChronoUnit.DAYS.between(dueDate, returnDate);
        if (overdueDays <= 0) {
            return 0;
        }
        return Math.round(overdueDays * 0.5 * 100.0) / 100.0;
    }
}

/**
 * Represents a rental transaction with a customer, rental date, and list of rentals.
 */
class RentalTransaction {
    private Customer customer;
    private String rentalDate;
    private List<Rental> rentals;

    /**
     * Unparameterized constructor for RentalTransaction.
     */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Gets the customer of the rental transaction.
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer of the rental transaction.
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the rental date of the rental transaction.
     * @return the rental date
     */
    public String getRentalDate() {
        return rentalDate;
    }

    /**
     * Sets the rental date of the rental transaction.
     * @param rentalDate the rental date to set
     */
    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    /**
     * Gets the rentals of the rental transaction.
     * @return the rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Sets the rentals of the rental transaction.
     * @param rentals the rentals to set
     */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Calculates the total price for the rental transaction.
     * @return the total price
     */
    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (Rental rental : rentals) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate rentalDate = LocalDate.parse(rental.getRentalDate(), formatter);
            LocalDate dueDate = LocalDate.parse(rental.getDueDate(), formatter);
            long rentalDays = ChronoUnit.DAYS.between(rentalDate, dueDate);
            if (rentalDays < 0) {
                rentalDays = 0;
            }
            totalPrice += rentalDays + rental.calculatePastDueAmount();
        }
        return Math.round(totalPrice * 100.0) / 100.0;
    }

    /**
     * Lists unreturned tapes for the customer.
     * @return a list of unreturned tape ids
     */
    public List<String> listUnreturnedTapes() {
        List<String> unreturnedTapes = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getReturnDate() == null) {
                unreturnedTapes.add(rental.getVideoTape().getBarCodeId());
            }
        }
        return unreturnedTapes;
    }
}

/**
 * Represents a video rental system with methods to check tape availability and add video tape rentals.
 */
class VideoRentalSystem {
    private List<Customer> customers;
    private List<VideoTape> videoTapes;
    private List<RentalTransaction> rentalTransactions;

    /**
     * Unparameterized constructor for VideoRentalSystem.
     */
    public VideoRentalSystem() {
        this.customers = new ArrayList<>();
        this.videoTapes = new ArrayList<>();
        this.rentalTransactions = new ArrayList<>();
    }

    /**
     * Checks tape availability for a given date.
     * @param barCodeId the bar code id of the tape
     * @param date the date to check
     * @return true if the tape is available; otherwise, false
     */
    public boolean checkTapeAvailability(String barCodeId, String date) {
        for (RentalTransaction transaction : rentalTransactions) {
            for (Rental rental : transaction.getRentals()) {
                if (rental.getVideoTape().getBarCodeId().equals(barCodeId) && rental.getReturnDate() == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Adds a video tape rental.
     * @param customerId the id of the customer
     * @param barCodeId the bar code id of the tape
     * @param rentalDate the rental date
     * @param dueDate the due date
     * @return true if the rental is processed; otherwise, false
     */
    public boolean addVideoTapeRental(String customerId, String barCodeId, String rentalDate, String dueDate) {
        Customer customer = findCustomer(customerId);
        if (customer == null) {
            return false;
        }
        if (customer.getRentalTransactions().size() >= 20) {
            return false;
        }
        if (!checkTapeAvailability(barCodeId, rentalDate)) {
            return false;
        }
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(rentalDate);
        Rental rental = new Rental();
        rental.setVideoTape(findVideoTape(barCodeId));
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        transaction.getRentals().add(rental);
        customer.getRentalTransactions().add(transaction);
        rentalTransactions.add(transaction);
        return true;
    }

    /**
     * Finds a customer by id.
     * @param customerId the id of the customer
     * @return the customer; or null if not found
     */
    private Customer findCustomer(String customerId) {
        for (Customer customer : customers) {
            if (customer.getId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Finds a video tape by bar code id.
     * @param barCodeId the bar code id of the tape
     * @return the video tape; or null if not found
     */
    private VideoTape findVideoTape(String barCodeId) {
        for (VideoTape tape : videoTapes) {
            if (tape.getBarCodeId().equals(barCodeId)) {
                return tape;
            }
        }
        return null;
    }
}