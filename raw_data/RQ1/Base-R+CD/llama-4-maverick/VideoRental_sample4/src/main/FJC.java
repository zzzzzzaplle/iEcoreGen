import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer with an ID and a list of video rentals.
 */
class Customer {
    private String id;
    private List<VideoRental> rentals;

    /**
     * Constructs a new Customer object.
     */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Gets the customer's ID.
     * 
     * @return the customer's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the customer's ID.
     * 
     * @param id the customer's ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the list of video rentals for the customer.
     * 
     * @return the list of video rentals
     */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of video rentals for the customer.
     * 
     * @param rentals the list of video rentals to set
     */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Adds a video tape rental for the customer.
     * 
     * @param tape        the tape to rent
     * @param currentDate the current date
     * @return true if the rental is successful, false otherwise
     */
    public boolean addVedioTapeRental(Tape tape, LocalDate currentDate) {
        if (rentals.size() >= 20) {
            return false; // Customer has reached the maximum number of rentals
        }
        if (calculateTotalPastDueAmount(currentDate) > 0) {
            return false; // Customer has past-due amounts
        }
        if (!tape.isAvailable(currentDate)) {
            return false; // Tape is not available
        }
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(currentDate.plusDays(1)); // Assume due date is one day after rental date
        rentals.add(rental);
        return true;
    }

    /**
     * Retrieves a list of unreturned tapes for the customer.
     * 
     * @return a list of unreturned tapes
     */
    public List<Tape> getUnreturnedTapes() {
        List<Tape> unreturnedTapes = new ArrayList<>();
        for (VideoRental rental : rentals) {
            if (rental.getReturnDate() == null) {
                unreturnedTapes.add(rental.getTape());
            }
        }
        return unreturnedTapes;
    }

    /**
     * Calculates the total past-due amount for the customer.
     * 
     * @param currentDate the current date
     * @return the total past-due amount
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
 * Represents a video tape with an ID and video information.
 */
class Tape {
    private String id;
    private String videoInformation;

    /**
     * Constructs a new Tape object.
     */
    public Tape() {
    }

    /**
     * Gets the tape's ID.
     * 
     * @return the tape's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the tape's ID.
     * 
     * @param id the tape's ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the video information for the tape.
     * 
     * @return the video information
     */
    public String getVideoInformation() {
        return videoInformation;
    }

    /**
     * Sets the video information for the tape.
     * 
     * @param videoInformation the video information to set
     */
    public void setVideoInformation(String videoInformation) {
        this.videoInformation = videoInformation;
    }

    /**
     * Checks if the tape is available on the given date.
     * 
     * @param currentDate the date to check availability
     * @return true if the tape is available, false otherwise
     */
    public boolean isAvailable(LocalDate currentDate) {
        // Assume we have a method to check if the tape is rented out
        // For simplicity, this example does not implement this check
        // In a real scenario, you would check against active rentals
        return true; // Simplified for demonstration
    }
}

/**
 * Represents a video rental with due date, return date, and associated tape.
 */
class VideoRental {
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double ownedPastDueAmount;
    private Tape tape;

    /**
     * Constructs a new VideoRental object.
     */
    public VideoRental() {
    }

    /**
     * Gets the due date for the rental.
     * 
     * @return the due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date for the rental.
     * 
     * @param dueDate the due date to set
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the return date for the rental.
     * 
     * @return the return date
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date for the rental.
     * 
     * @param returnDate the return date to set
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Gets the owned past-due amount for the rental.
     * 
     * @return the owned past-due amount
     */
    public double getOwnedPastDueAmount() {
        return ownedPastDueAmount;
    }

    /**
     * Sets the owned past-due amount for the rental.
     * 
     * @param ownedPastDueAmount the owned past-due amount to set
     */
    public void setOwnedPastDueAmount(double ownedPastDueAmount) {
        this.ownedPastDueAmount = ownedPastDueAmount;
    }

    /**
     * Gets the tape associated with the rental.
     * 
     * @return the tape
     */
    public Tape getTape() {
        return tape;
    }

    /**
     * Sets the tape associated with the rental.
     * 
     * @param tape the tape to set
     */
    public void setTape(Tape tape) {
        this.tape = tape;
    }

    /**
     * Calculates the owed past-due amount for the rental.
     * 
     * @param currentDate the current date
     * @return the owed past-due amount
     */
    public double calculateOwedPastDueAmount(LocalDate currentDate) {
        if (returnDate != null && !returnDate.isAfter(dueDate)) {
            return 0;
        }
        LocalDate dateToCheck = returnDate != null ? returnDate : currentDate;
        long overdueDays = ChronoUnit.DAYS.between(dueDate, dateToCheck);
        if (overdueDays <= 0) {
            return 0;
        }
        return Math.round(overdueDays * 0.5 * 100.0) / 100.0;
    }

    /**
     * Calculates the base rental fee for the rental.
     * 
     * @param currentDate the current date
     * @return the base rental fee
     */
    public double calculateBaseRentalFee(LocalDate currentDate) {
        LocalDate rentalDate = currentDate; // Assume rental date is the current date
        long rentalDays = ChronoUnit.DAYS.between(rentalDate, returnDate != null ? returnDate : currentDate);
        if (rentalDays <= 0) {
            rentalDays = 1; // At least one day
        }
        return rentalDays * 1.0; // $1 per day
    }
}

/**
 * Represents a rental transaction with a rental date, total price, and associated rentals and customer.
 */
class RentalTransaction {
    private LocalDate rentalDate;
    private double totalPrice;
    private List<VideoRental> rentals;
    private Customer customer;

    /**
     * Constructs a new RentalTransaction object.
     */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Gets the rental date for the transaction.
     * 
     * @return the rental date
     */
    public LocalDate getRentalDate() {
        return rentalDate;
    }

    /**
     * Sets the rental date for the transaction.
     * 
     * @param rentalDate the rental date to set
     */
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    /**
     * Gets the total price for the transaction.
     * 
     * @return the total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price for the transaction.
     * 
     * @param totalPrice the total price to set
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the list of rentals associated with the transaction.
     * 
     * @return the list of rentals
     */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of rentals associated with the transaction.
     * 
     * @param rentals the list of rentals to set
     */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Gets the customer associated with the transaction.
     * 
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer associated with the transaction.
     * 
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Calculates the total price for the transaction.
     * 
     * @param rentalDate  the rental date
     * @param currentDate the current date
     * @return the total price
     */
    public double calculateTotalPrice(LocalDate rentalDate, LocalDate currentDate) {
        double totalPrice = 0;
        for (VideoRental rental : rentals) {
            totalPrice += rental.calculateBaseRentalFee(currentDate) + rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(totalPrice * 100.0) / 100.0;
    }
}