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

    /**
     * Constructs a new Customer object.
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
     * Adds a rental transaction to the customer's history.
     * @param rentalTransaction the rental transaction to add
     */
    public void addRentalTransaction(RentalTransaction rentalTransaction) {
        this.rentalTransactions.add(rentalTransaction);
    }

    /**
     * Lists unreturned tapes for the customer.
     * @return a list of tape IDs that have not been returned
     */
    public List<String> listUnreturnedTapes() {
        List<String> unreturnedTapes = new ArrayList<>();
        for (RentalTransaction transaction : rentalTransactions) {
            for (Rental rental : transaction.getRentals()) {
                if (rental.getReturnDate() == null) {
                    unreturnedTapes.add(rental.getTapeId());
                }
            }
        }
        return unreturnedTapes;
    }

    /**
     * Checks if the customer has any past-due amounts.
     * @return true if the customer has past-due amounts, false otherwise
     */
    public boolean hasPastDueAmount() {
        for (RentalTransaction transaction : rentalTransactions) {
            for (Rental rental : transaction.getRentals()) {
                if (rental.calculatePastDueAmount() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the customer has less than 20 active rentals.
     * @return true if the customer has less than 20 active rentals, false otherwise
     */
    public boolean hasLessThan20Rentals() {
        int count = 0;
        for (RentalTransaction transaction : rentalTransactions) {
            for (Rental rental : transaction.getRentals()) {
                if (rental.getReturnDate() == null) {
                    count++;
                }
            }
        }
        return count < 20;
    }
}

/**
 * Represents a rental transaction with multiple rentals.
 */
class RentalTransaction {
    private List<Rental> rentals;

    /**
     * Constructs a new RentalTransaction object.
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
     * Adds a rental to the transaction.
     * @param rental the rental to add
     */
    public void addRental(Rental rental) {
        this.rentals.add(rental);
    }

    /**
     * Calculates the total price for the rental transaction.
     * @return the total price rounded to two decimal places
     */
    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (Rental rental : rentals) {
            totalPrice += rental.calculateTotalPrice();
        }
        return Math.round(totalPrice * 100.0) / 100.0;
    }
}

/**
 * Represents a rental with a tape ID, rental date, and return date.
 */
class Rental {
    private String tapeId;
    private String rentalDate;
    private String dueDate;
    private String returnDate;

    /**
     * Constructs a new Rental object.
     */
    public Rental() {}

    /**
     * Gets the tape ID.
     * @return the tape ID
     */
    public String getTapeId() {
        return tapeId;
    }

    /**
     * Sets the tape ID.
     * @param tapeId the tape ID to set
     */
    public void setTapeId(String tapeId) {
        this.tapeId = tapeId;
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
     * @return the past-due amount rounded to two decimal places
     */
    public double calculatePastDueAmount() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dueDate = LocalDate.parse(this.dueDate, formatter);
        LocalDate returnDate = this.returnDate != null ? LocalDate.parse(this.returnDate, formatter) : LocalDate.now();
        long overdueDays = ChronoUnit.DAYS.between(dueDate, returnDate);
        if (overdueDays <= 0) {
            return 0;
        }
        double overdueFee = overdueDays * 0.5;
        return Math.round(overdueFee * 100.0) / 100.0;
    }

    /**
     * Calculates the total price for the rental.
     * @return the total price rounded to two decimal places
     */
    public double calculateTotalPrice() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate rentalDate = LocalDate.parse(this.rentalDate, formatter);
        LocalDate returnDate = this.returnDate != null ? LocalDate.parse(this.returnDate, formatter) : LocalDate.now();
        long rentalDays = ChronoUnit.DAYS.between(rentalDate, returnDate) + 1; // count partial days as full days
        double baseRentalFee = rentalDays * 1.0;
        double overdueFee = calculatePastDueAmount();
        return Math.round((baseRentalFee + overdueFee) * 100.0) / 100.0;
    }
}

/**
 * Represents a video tape with an ID and availability status.
 */
class VideoTape {
    private String id;
    private boolean isAvailable;

    /**
     * Constructs a new VideoTape object.
     */
    public VideoTape() {}

    /**
     * Gets the ID of the tape.
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the tape.
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Checks if the tape is available for rental.
     * @param rentals a list of rentals to check against
     * @return true if the tape is available, false otherwise
     */
    public boolean isAvailable(List<Rental> rentals) {
        for (Rental rental : rentals) {
            if (rental.getTapeId().equals(this.id) && rental.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Provides utility methods for managing video tape rentals.
 */
class RentalManager {
    private List<Customer> customers;
    private List<VideoTape> videoTapes;

    /**
     * Constructs a new RentalManager object.
     */
    public RentalManager() {
        this.customers = new ArrayList<>();
        this.videoTapes = new ArrayList<>();
    }

    /**
     * Gets the list of customers.
     * @return the customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Sets the list of customers.
     * @param customers the customers to set
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Gets the list of video tapes.
     * @return the video tapes
     */
    public List<VideoTape> getVideoTapes() {
        return videoTapes;
    }

    /**
     * Sets the list of video tapes.
     * @param videoTapes the video tapes to set
     */
    public void setVideoTapes(List<VideoTape> videoTapes) {
        this.videoTapes = videoTapes;
    }

    /**
     * Adds a video tape rental for a customer.
     * @param customerId the ID of the customer
     * @param tapeId the ID of the tape to rent
     * @return true if the rental is successful, false otherwise
     */
    public boolean addVideoTapeRental(String customerId, String tapeId) {
        Customer customer = findCustomer(customerId);
        VideoTape videoTape = findVideoTape(tapeId);
        if (customer == null || videoTape == null) {
            return false;
        }
        if (!customer.hasLessThan20Rentals() || customer.hasPastDueAmount()) {
            return false;
        }
        if (!videoTape.isAvailable(getAllRentals())) {
            return false;
        }
        RentalTransaction transaction = new RentalTransaction();
        Rental rental = new Rental();
        rental.setTapeId(tapeId);
        rental.setRentalDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        // Assume due date is 7 days from rental date
        rental.setDueDate(LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        transaction.addRental(rental);
        customer.addRentalTransaction(transaction);
        return true;
    }

    /**
     * Finds a customer by ID.
     * @param customerId the ID of the customer to find
     * @return the customer if found, null otherwise
     */
    private Customer findCustomer(String customerId) {
        for (Customer customer : customers) {
            if (customer.getIdCard().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Finds a video tape by ID.
     * @param tapeId the ID of the tape to find
     * @return the video tape if found, null otherwise
     */
    private VideoTape findVideoTape(String tapeId) {
        for (VideoTape videoTape : videoTapes) {
            if (videoTape.getId().equals(tapeId)) {
                return videoTape;
            }
        }
        return null;
    }

    /**
     * Gets all rentals across all customers.
     * @return a list of all rentals
     */
    private List<Rental> getAllRentals() {
        List<Rental> rentals = new ArrayList<>();
        for (Customer customer : customers) {
            for (RentalTransaction transaction : customer.getRentalTransactions()) {
                rentals.addAll(transaction.getRentals());
            }
        }
        return rentals;
    }
}