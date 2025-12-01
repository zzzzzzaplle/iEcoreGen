import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer with an id and a list of rentals.
 */
class Customer {
    private String id;
    private String accountNumber;
    private List<Rental> rentals;

    /**
     * Unparameterized constructor for Customer.
     */
    public Customer() {
        this.rentals = new ArrayList<>();
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
     * Gets the list of rentals for the customer.
     * @return the rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of rentals for the customer.
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
}

/**
 * Represents a video tape with an id and a bar code id.
 */
class VideoTape {
    private String id;
    private String barCodeId;

    /**
     * Unparameterized constructor for VideoTape.
     */
    public VideoTape() {}

    /**
     * Gets the id of the video tape.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the video tape.
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

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
     * Gets the video tape associated with the rental.
     * @return the video tape
     */
    public VideoTape getVideoTape() {
        return videoTape;
    }

    /**
     * Sets the video tape associated with the rental.
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
        LocalDate dueDate = LocalDate.parse(this.dueDate);
        LocalDate returnDate = this.returnDate != null ? LocalDate.parse(this.returnDate) : LocalDate.now();
        long overdueDays = ChronoUnit.DAYS.between(dueDate, returnDate);
        if (overdueDays <= 0) {
            return 0;
        }
        return Math.round(overdueDays * 0.5 * 100.0) / 100.0;
    }
}

/**
 * Represents a rental transaction with a customer and a list of rentals.
 */
class RentalTransaction {
    private Customer customer;
    private List<Rental> rentals;

    /**
     * Unparameterized constructor for RentalTransaction.
     */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Gets the customer associated with the rental transaction.
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer associated with the rental transaction.
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the list of rentals for the rental transaction.
     * @return the rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of rentals for the rental transaction.
     * @param rentals the rentals to set
     */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Adds a rental to the rental transaction.
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
        for (Rental rental : rentals) {
            if (rental.getReturnDate() == null) {
                LocalDate rentalDate = LocalDate.parse(rental.getRentalDate());
                LocalDate dueDate = LocalDate.parse(rental.getDueDate());
                long rentalDays = ChronoUnit.DAYS.between(rentalDate, dueDate);
                double baseRentalFee = rentalDays * 1;
                double overdueFee = rental.calculatePastDueAmount();
                totalPrice += baseRentalFee + overdueFee;
            }
        }
        return Math.round(totalPrice * 100.0) / 100.0;
    }
}

/**
 * Provides utility methods for video tape rentals.
 */
class VideoTapeRentalService {
    /**
     * Checks if a video tape is available for rental on a given date.
     * @param videoTape the video tape to check
     * @param date the date to check
     * @return true if the video tape is available, false otherwise
     */
    public boolean isTapeAvailable(VideoTape videoTape, String date) {
        // Assume a method to retrieve active rentals for a video tape
        // For simplicity, this method is not implemented here
        // In a real implementation, you would query a database or data structure to find active rentals
        return true; // Placeholder implementation
    }

    /**
     * Adds a video tape rental for a customer.
     * @param customer the customer renting the video tape
     * @param videoTape the video tape being rented
     * @param rentalDate the rental date
     * @param dueDate the due date
     * @return true if the rental is successful, false otherwise
     */
    public boolean addVideoTapeRental(Customer customer, VideoTape videoTape, String rentalDate, String dueDate) {
        if (customer.getRentals().size() >= 20) {
            return false; // Customer has too many rentals
        }
        for (Rental rental : customer.getRentals()) {
            if (rental.getVideoTape().getId().equals(videoTape.getId()) && rental.getReturnDate() == null) {
                return false; // Customer already has this tape rented
            }
            if (rental.getReturnDate() == null && rental.calculatePastDueAmount() > 0) {
                return false; // Customer has past-due rentals
            }
        }
        if (!isTapeAvailable(videoTape, rentalDate)) {
            return false; // Tape is not available
        }
        Rental rental = new Rental();
        rental.setVideoTape(videoTape);
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        customer.addRental(rental);
        return true;
    }

    /**
     * Lists unreturned tapes for a customer.
     * @param customer the customer to check
     * @return a list of unreturned tape ids
     */
    public List<String> listUnreturnedTapes(Customer customer) {
        List<String> unreturnedTapes = new ArrayList<>();
        for (Rental rental : customer.getRentals()) {
            if (rental.getReturnDate() == null) {
                unreturnedTapes.add(rental.getVideoTape().getId());
            }
        }
        return unreturnedTapes;
    }
}