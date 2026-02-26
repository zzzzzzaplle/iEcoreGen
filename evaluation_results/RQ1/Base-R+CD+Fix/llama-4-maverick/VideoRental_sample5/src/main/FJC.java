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
     * Default constructor for Customer.
     */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Gets the ID of the customer.
     * @return the ID of the customer
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the customer.
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the list of video rentals for the customer.
     * @return the list of video rentals
     */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of video rentals for the customer.
     * @param rentals the list of video rentals to set
     */
    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Adds a video tape rental for the customer if the customer has less than 20 rentals and no past-due amount.
     * @param tape the tape to rent
     * @param currentDate the current date
     * @return true if the rental is successful, false otherwise
     */
    public boolean addVedioTapeRental(Tape tape, LocalDate currentDate) {
        if (rentals.size() >= 20) {
            return false;
        }
        if (calculateTotalPastDueAmount(currentDate) > 0) {
            return false;
        }
        if (!tape.isAvailable(currentDate)) {
            return false;
        }
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(currentDate.plusDays(1)); // Assuming rental period is 1 day
        rentals.add(rental);
        return true;
    }

    /**
     * Retrieves a list of all tapes rented by the customer that have not been returned.
     * @return a list of tape IDs
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
     * Calculates the total past-due amount for the customer.
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
     * Default constructor for Tape.
     */
    public Tape() {}

    /**
     * Gets the ID of the tape.
     * @return the ID of the tape
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
     * Gets the video information of the tape.
     * @return the video information
     */
    public String getVideoInformation() {
        return videoInformation;
    }

    /**
     * Sets the video information of the tape.
     * @param videoInformation the video information to set
     */
    public void setVideoInformation(String videoInformation) {
        this.videoInformation = videoInformation;
    }

    /**
     * Checks if the tape is available for rental on the given date.
     * @param currentDate the current date
     * @return true if the tape is available, false otherwise
     */
    public boolean isAvailable(LocalDate currentDate) {
        // Assuming there's a method to get the active rental for the tape
        // For simplicity, this example doesn't implement that method
        // Instead, it directly checks if the tape is available
        for (Customer customer : RentalSystem.customers) {
            for (VideoRental rental : customer.getRentals()) {
                if (rental.getTape().getId().equals(this.id) && rental.getReturnDate() == null) {
                    return false;
                }
            }
        }
        return true;
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
     * Default constructor for VideoRental.
     */
    public VideoRental() {}

    /**
     * Gets the due date of the rental.
     * @return the due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date of the rental.
     * @param dueDate the due date to set
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the return date of the rental.
     * @return the return date
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date of the rental.
     * @param returnDate the return date to set
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Gets the owned past-due amount for the rental.
     * @return the owned past-due amount
     */
    public double getOwnedPastDueAmount() {
        return ownedPastDueAmount;
    }

    /**
     * Sets the owned past-due amount for the rental.
     * @param ownedPastDueAmount the owned past-due amount to set
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
     * Calculates the owed past-due amount for the rental.
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
     * @param currentDate the current date
     * @return the base rental fee
     */
    public double calculateBaseRentalFee(LocalDate currentDate) {
        LocalDate rentalDate = currentDate; // Assuming rental date is the current date
        long rentalDays = ChronoUnit.DAYS.between(rentalDate, returnDate != null ? returnDate : currentDate);
        return Math.ceil(rentalDays) * 1.0; // $1 per day
    }
}

/**
 * Represents a rental transaction with rental date, total price, and associated rentals and customer.
 */
class RentalTransaction {
    private LocalDate rentalDate;
    private double totalPrice;
    private List<VideoRental> rentals;
    private Customer customer;

    /**
     * Default constructor for RentalTransaction.
     */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Gets the rental date of the transaction.
     * @return the rental date
     */
    public LocalDate getRentalDate() {
        return rentalDate;
    }

    /**
     * Sets the rental date of the transaction.
     * @param rentalDate the rental date to set
     */
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
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
     * Gets the list of rentals associated with the transaction.
     * @return the list of rentals
     */
    public List<VideoRental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of rentals associated with the transaction.
     * @param rentals the list of rentals to set
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
        return Math.round(totalPrice * 100.0) / 100.0;
    }
}

/**
 * Represents the rental system with a list of customers.
 */
class RentalSystem {
    static List<Customer> customers = new ArrayList<>();

    public static void main(String[] args) {
        // Example usage
        Customer customer = new Customer();
        customer.setId("C001");

        Tape tape1 = new Tape();
        tape1.setId("T001");

        LocalDate currentDate = LocalDate.parse("2023-04-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        customer.addVedioTapeRental(tape1, currentDate);

        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().addAll(customer.getRentals());
        transaction.setRentalDate(currentDate);

        System.out.println("Total Price: " + transaction.calculateTotalPrice(currentDate, currentDate));
    }
}