import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer with an id, account number, and a list of rentals.
 */
class Customer {
    private String id;
    private String accountNumber;
    private List<Rental> rentals;

    /**
     * Default constructor for Customer.
     */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Gets the customer's id.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the customer's id.
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the customer's account number.
     * @return the accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the customer's account number.
     * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Gets the customer's rentals.
     * @return the rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Sets the customer's rentals.
     * @param rentals the rentals to set
     */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Adds a rental to the customer's list of rentals.
     * @param rental the rental to add
     */
    public void addRental(Rental rental) {
        this.rentals.add(rental);
    }

    /**
     * Checks if the customer has any past-due amounts.
     * @return true if the customer has past-due amounts, false otherwise
     */
    public boolean hasPastDueAmount() {
        for (Rental rental : rentals) {
            if (rental.getPastDueAmount() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Lists unreturned tapes for the customer.
     * @return a list of tape ids that have not been returned
     */
    public List<String> listUnreturnedTapes() {
        List<String> unreturnedTapes = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getReturnDate() == null) {
                unreturnedTapes.add(rental.getTapeId());
            }
        }
        return unreturnedTapes;
    }
}

/**
 * Represents a rental with a tape id, rental date, due date, and return date.
 */
class Rental {
    private String tapeId;
    private String rentalDate;
    private String dueDate;
    private String returnDate;

    /**
     * Default constructor for Rental.
     */
    public Rental() {}

    /**
     * Gets the tape id.
     * @return the tapeId
     */
    public String getTapeId() {
        return tapeId;
    }

    /**
     * Sets the tape id.
     * @param tapeId the tapeId to set
     */
    public void setTapeId(String tapeId) {
        this.tapeId = tapeId;
    }

    /**
     * Gets the rental date.
     * @return the rentalDate
     */
    public String getRentalDate() {
        return rentalDate;
    }

    /**
     * Sets the rental date.
     * @param rentalDate the rentalDate to set
     */
    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    /**
     * Gets the due date.
     * @return the dueDate
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date.
     * @param dueDate the dueDate to set
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the return date.
     * @return the returnDate
     */
    public String getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date.
     * @param returnDate the returnDate to set
     */
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Calculates the past-due amount for the rental.
     * @return the past-due amount
     */
    public double getPastDueAmount() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dueDate = LocalDate.parse(this.dueDate, formatter);
        LocalDate returnDate = this.returnDate != null ? LocalDate.parse(this.returnDate, formatter) : LocalDate.now();
        long overdueDays = ChronoUnit.DAYS.between(dueDate, returnDate);
        return overdueDays > 0 ? Math.round(overdueDays * 0.5 * 100.0) / 100.0 : 0;
    }
}

/**
 * Represents a video tape with an id and a list of rentals.
 */
class VideoTape {
    private String id;

    /**
     * Default constructor for VideoTape.
     */
    public VideoTape() {}

    /**
     * Gets the tape id.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the tape id.
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Checks if the tape is available for rental on a given date.
     * @param date the date to check
     * @param rentals the list of rentals to check against
     * @return true if the tape is available, false otherwise
     */
    public boolean isAvailable(String date, List<Rental> rentals) {
        for (Rental rental : rentals) {
            if (rental.getTapeId().equals(this.id) && rental.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Represents a rental transaction with a customer and a list of rentals.
 */
class RentalTransaction {
    private Customer customer;
    private List<Rental> rentals;

    /**
     * Default constructor for RentalTransaction.
     */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Gets the customer.
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer.
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the rentals.
     * @return the rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Sets the rentals.
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
     * @return the total price
     */
    public double calculateTotalPrice() {
        double totalPrice = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Rental rental : rentals) {
            LocalDate rentalDate = LocalDate.parse(rental.getRentalDate(), formatter);
            LocalDate returnDate = rental.getReturnDate() != null ? LocalDate.parse(rental.getReturnDate(), formatter) : LocalDate.now();
            long rentalDays = ChronoUnit.DAYS.between(rentalDate, returnDate);
            double rentalFee = rentalDays * 1.0;
            double overdueFee = rental.getPastDueAmount();
            totalPrice += rentalFee + overdueFee;
        }
        return Math.round(totalPrice * 100.0) / 100.0;
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
     * Gets the customers.
     * @return the customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Sets the customers.
     * @param customers the customers to set
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Gets the video tapes.
     * @return the videoTapes
     */
    public List<VideoTape> getVideoTapes() {
        return videoTapes;
    }

    /**
     * Sets the video tapes.
     * @param videoTapes the videoTapes to set
     */
    public void setVideoTapes(List<VideoTape> videoTapes) {
        this.videoTapes = videoTapes;
    }

    /**
     * Adds a video tape rental.
     * @param customerId the customer's id
     * @param tapeId the tape's id
     * @param rentalDate the rental date
     * @param dueDate the due date
     * @return true if the rental is successful, false otherwise
     */
    public boolean addVideoTapeRental(String customerId, String tapeId, String rentalDate, String dueDate) {
        Customer customer = findCustomer(customerId);
        VideoTape videoTape = findVideoTape(tapeId);
        if (customer.getRentals().size() >= 20 || customer.hasPastDueAmount() || !videoTape.isAvailable(rentalDate, customer.getRentals())) {
            return false;
        }
        Rental rental = new Rental();
        rental.setTapeId(tapeId);
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        customer.addRental(rental);
        return true;
    }

    /**
     * Finds a customer by id.
     * @param customerId the customer's id
     * @return the customer
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
     * Finds a video tape by id.
     * @param tapeId the tape's id
     * @return the video tape
     */
    private VideoTape findVideoTape(String tapeId) {
        for (VideoTape videoTape : videoTapes) {
            if (videoTape.getId().equals(tapeId)) {
                return videoTape;
            }
        }
        return null;
    }
}