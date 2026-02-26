import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer with an id and a list of video rentals.
 */
class Customer {
    private String id;
    private List<VideoRental> rentals;

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
     * Gets the list of video rentals for the customer.
     * @return the rentals
     */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of video rentals for the customer.
     * @param rentals the rentals to set
     */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Adds a video tape rental for the customer.
     * @param tape the tape to rent
     * @param currentDate the current date
     * @return true if the rental is successful, false otherwise
     */
    public boolean addVedioTapeRental(Tape tape, LocalDate currentDate) {
        if (rentals.size() >= 20) {
            return false; // Customer has maximum rentals
        }
        if (calculateTotalPastDueAmount(currentDate) > 0) {
            return false; // Customer has past due amount
        }
        if (!tape.isAvailable(currentDate)) {
            return false; // Tape is not available
        }
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(currentDate.plusDays(1)); // Assuming rental duration is 1 day
        rentals.add(rental);
        return true;
    }

    /**
     * Retrieves a list of unreturned tapes for the customer.
     * @return a list of tape ids
     */
    public List<String> getUnreturnedTapes() {
        List<String> unreturnedTapes = new ArrayList<>();
        for (VideoRental rental : rentals) {
            if (rental.getReturnDate() == null) {
                unreturnedTapes.add(rental.getTape().getId());
            }
        }
        return unreturnedTapes;
    }

    /**
     * Calculates the total past due amount for the customer.
     * @param currentDate the current date
     * @return the total past due amount
     */
    public double calculateTotalPastDueAmount(LocalDate currentDate) {
        double totalPastDueAmount = 0;
        for (VideoRental rental : rentals) {
            totalPastDueAmount += rental.calculateOwedPastDueAmount(currentDate);
        }
        return totalPastDueAmount;
    }
}

/**
 * Represents a video tape with an id and video information.
 */
class Tape {
    private String id;
    private String videoInformation;

    /**
     * Unparameterized constructor for Tape.
     */
    public Tape() {}

    /**
     * Gets the id of the tape.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the tape.
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the video information of the tape.
     * @return the videoInformation
     */
    public String getVideoInformation() {
        return videoInformation;
    }

    /**
     * Sets the video information of the tape.
     * @param videoInformation the videoInformation to set
     */
    public void setVideoInformation(String videoInformation) {
        this.videoInformation = videoInformation;
    }

    /**
     * Checks if the tape is available on a given date.
     * @param currentDate the current date
     * @return true if the tape is available, false otherwise
     */
    public boolean isAvailable(LocalDate currentDate) {
        // Assuming tape is available if it's not part of an active rental
        // This logic might need to be adjusted based on the actual requirements
        for (VideoRental rental : VideoRental.getRentals()) {
            if (rental.getTape().equals(this) && rental.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Represents a video rental with due date, return date, and associated tape.
 */
class VideoRental {
    private static List<VideoRental> rentals = new ArrayList<>();
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double ownedPastDueAmount;
    private Tape tape;

    /**
     * Unparameterized constructor for VideoRental.
     */
    public VideoRental() {
        rentals.add(this);
    }

    /**
     * Gets the due date of the rental.
     * @return the dueDate
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date of the rental.
     * @param dueDate the dueDate to set
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the return date of the rental.
     * @return the returnDate
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date of the rental.
     * @param returnDate the returnDate to set
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Gets the owned past due amount of the rental.
     * @return the ownedPastDueAmount
     */
    public double getOwnedPastDueAmount() {
        return ownedPastDueAmount;
    }

    /**
     * Sets the owned past due amount of the rental.
     * @param ownedPastDueAmount the ownedPastDueAmount to set
     */
    public void setOwnedPastDueAmount(double ownedPastDueAmount) {
        this.ownedPastDueAmount = ownedPastDueAmount;
    }

    /**
     * Gets the tape associated with the rental.
     * @return the tape
     */
    public Tape getTape() {
        return tape;
    }

    /**
     * Sets the tape associated with the rental.
     * @param tape the tape to set
     */
    public void setTape(Tape tape) {
        this.tape = tape;
    }

    /**
     * Gets the list of all video rentals.
     * @return the rentals
     */
    public static List<VideoRental> getRentals() {
        return rentals;
    }

    /**
     * Calculates the owed past due amount for the rental.
     * @param currentDate the current date
     * @return the owed past due amount
     */
    public double calculateOwedPastDueAmount(LocalDate currentDate) {
        if (returnDate != null && !returnDate.isAfter(dueDate)) {
            return 0;
        }
        LocalDate dateToCheck = returnDate != null ? returnDate : currentDate;
        long overdueDays = ChronoUnit.DAYS.between(dueDate, dateToCheck);
        return Math.round(overdueDays * 0.5 * 100.0) / 100.0;
    }

    /**
     * Calculates the base rental fee for the rental.
     * @param currentDate the current date
     * @return the base rental fee
     */
    public double calculateBaseRentalFee(LocalDate currentDate) {
        LocalDate rentalDate = currentDate; // Assuming rental date is the current date
        long rentalDays = ChronoUnit.DAYS.between(rentalDate, returnDate != null ? returnDate : currentDate);
        return Math.ceil(rentalDays) * 1.0; // $1 per day, rounding up to nearest day
    }
}

/**
 * Represents a rental transaction with a rental date, total price, and associated rentals.
 */
class RentalTransaction {
    private LocalDate rentalDate;
    private double totalPrice;
    private List<VideoRental> rentals;
    private Customer customer;

    /**
     * Unparameterized constructor for RentalTransaction.
     */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Gets the rental date of the transaction.
     * @return the rentalDate
     */
    public LocalDate getRentalDate() {
        return rentalDate;
    }

    /**
     * Sets the rental date of the transaction.
     * @param rentalDate the rentalDate to set
     */
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    /**
     * Gets the total price of the transaction.
     * @return the totalPrice
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the transaction.
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the list of rentals associated with the transaction.
     * @return the rentals
     */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of rentals associated with the transaction.
     * @param rentals the rentals to set
     */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Gets the customer associated with the transaction.
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer associated with the transaction.
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Calculates the total price for the rental transaction.
     * @param rentalDate the rental date
     * @param currentDate the current date
     * @return the total price
     */
    public double calculateTotalPrice(LocalDate rentalDate, LocalDate currentDate) {
        double totalPrice = 0;
        for (VideoRental rental : rentals) {
            totalPrice += rental.calculateBaseRentalFee(currentDate) + rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(totalPrice * 100.0) / 100.0; // Rounding to two decimal places
    }
}