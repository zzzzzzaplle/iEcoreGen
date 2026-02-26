import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class Customer {
    private String id;
    private List<VideoRental> rentals;

    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Adds a video tape rental for the customer if the customer has less than 20 rentals,
     * no past-due amount, and the tape is available.
     *
     * @param tape The tape to be rented.
     * @param currentDate The current date.
     * @return true if the rental is processed successfully, false otherwise.
     */
    public boolean addVideoTapeRental(Tape tape, Date currentDate) {
        if (this.rentals.size() >= 20 || calculateTotalPastDueAmount(currentDate) > 0 || !tape.isAvailable(currentDate)) {
            return false;
        }
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(currentDate);
        this.rentals.add(rental);
        return true;
    }

    /**
     * Retrieves a list of all tapes rented by the customer that have not been returned.
     *
     * @return A list of unreturned tapes.
     */
    public List<Tape> getUnreturnedTapes() {
        List<Tape> unreturnedTapes = new ArrayList<>();
        for (VideoRental rental : this.rentals) {
            if (rental.getReturnDate() == null) {
                unreturnedTapes.add(rental.getTape());
            }
        }
        return unreturnedTapes;
    }

    /**
     * Calculates the total past-due amount for all rentals of the customer.
     *
     * @param currentDate The current date.
     * @return The total past-due amount rounded to two decimal places.
     */
    public double calculateTotalPastDueAmount(Date currentDate) {
        double totalPastDueAmount = 0.0;
        for (VideoRental rental : this.rentals) {
            totalPastDueAmount += rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(totalPastDueAmount * 100.0) / 100.0;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<VideoRental> getRentals() {
        return rentals;
    }

    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }
}

class Tape {
    private String id;
    private String videoInformation;

    public Tape() {
    }

    /**
     * Checks if the tape is available for rental on the given date.
     *
     * @param currentDate The current date.
     * @return true if the tape is available, false otherwise.
     */
    public boolean isAvailable(Date currentDate) {
        // Assuming the tape is available if it is not part of any active rental
        // This is a simplified check and should be implemented according to the actual business logic
        return true;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoInformation() {
        return videoInformation;
    }

    public void setVideoInformation(String videoInformation) {
        this.videoInformation = videoInformation;
    }
}

class VideoRental {
    private Date dueDate;
    private Date returnDate;
    private double owedPastDueAmount;
    private Tape tape;

    public VideoRental() {
    }

    /**
     * Calculates the past-due amount for the rental.
     *
     * @param currentDate The current date.
     * @return The past-due amount rounded to two decimal places.
     */
    public double calculateOwedPastDueAmount(Date currentDate) {
        if (this.returnDate != null && this.returnDate.before(this.dueDate) || this.returnDate.equals(this.dueDate)) {
            return 0.0;
        }

        long overdueDays;
        if (this.returnDate == null) {
            overdueDays = (currentDate.getTime() - this.dueDate.getTime()) / (1000 * 60 * 60 * 24);
        } else {
            overdueDays = (this.returnDate.getTime() - this.dueDate.getTime()) / (1000 * 60 * 60 * 24);
        }

        double overdueFee = overdueDays * 0.5;
        return Math.round(overdueFee * 100.0) / 100.0;
    }

    /**
     * Calculates the base rental fee for the rental.
     *
     * @param currentDate The current date.
     * @return The base rental fee rounded to two decimal places.
     */
    public double calculateBaseRentalFee(Date currentDate) {
        long rentalDays;
        if (this.returnDate == null) {
            rentalDays = (currentDate.getTime() - this.dueDate.getTime()) / (1000 * 60 * 60 * 24);
        } else {
            rentalDays = (this.returnDate.getTime() - this.dueDate.getTime()) / (1000 * 60 * 60 * 24);
        }

        double baseRentalFee = rentalDays * 1.0;
        return Math.round(baseRentalFee * 100.0) / 100.0;
    }

    // Getters and Setters
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public double getOwedPastDueAmount() {
        return owedPastDueAmount;
    }

    public void setOwedPastDueAmount(double owedPastDueAmount) {
        this.owedPastDueAmount = owedPastDueAmount;
    }

    public Tape getTape() {
        return tape;
    }

    public void setTape(Tape tape) {
        this.tape = tape;
    }
}

class RentalTransaction {
    private Date rentalDate;
    private double totalPrice;
    private List<VideoRental> rentals;
    private Customer customer;

    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Calculates the total price for the rental transaction.
     *
     * @param rentalDate The rental date.
     * @param currentDate The current date.
     * @return The total price rounded to two decimal places.
     */
    public double calculateTotalPrice(Date rentalDate, Date currentDate) {
        double totalPrice = 0.0;
        for (VideoRental rental : this.rentals) {
            double baseRentalFee = rental.calculateBaseRentalFee(currentDate);
            double pastDueAmount = rental.calculateOwedPastDueAmount(currentDate);
            totalPrice += baseRentalFee + pastDueAmount;
        }
        return Math.round(totalPrice * 100.0) / 100.0;
    }

    // Getters and Setters
    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<VideoRental> getRentals() {
        return rentals;
    }

    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}