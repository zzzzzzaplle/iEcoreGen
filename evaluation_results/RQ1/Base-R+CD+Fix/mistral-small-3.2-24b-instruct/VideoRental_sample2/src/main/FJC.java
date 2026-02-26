import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

class Customer {
    private String id;
    private List<VideoRental> rentals;

    public Customer() {
        this.rentals = new ArrayList<>();
    }

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

    /**
     * Adds a video tape rental for the customer.
     *
     * @param tape The tape to be rented.
     * @param currentDate The current date.
     * @return true if the rental is processed successfully, false otherwise.
     */
    public boolean addVideoTapeRental(Tape tape, Date currentDate) {
        if (this.rentals.size() >= 20) {
            return false;
        }

        if (this.calculateTotalPastDueAmount(currentDate) > 0) {
            return false;
        }

        if (!tape.isAvailable(currentDate)) {
            return false;
        }

        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        Calendar dueDate = Calendar.getInstance();
        dueDate.setTime(currentDate);
        dueDate.add(Calendar.DATE, 7); // Assuming rental period is 7 days
        rental.setDueDate(dueDate.getTime());
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
        double total = 0.0;
        for (VideoRental rental : this.rentals) {
            total += rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(total * 100.0) / 100.0;
    }
}

class Tape {
    private String id;
    private String videoInformation;

    public Tape() {
    }

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

    /**
     * Checks if the tape is available for rental on the given date.
     *
     * @param currentDate The current date.
     * @return true if the tape is available, false otherwise.
     */
    public boolean isAvailable(Date currentDate) {
        for (VideoRental rental : getRentals()) {
            if (rental.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }

    private List<VideoRental> getRentals() {
        // Assuming there's a way to get all rentals for this tape
        return new ArrayList<>();
    }
}

class VideoRental {
    private Date dueDate;
    private Date returnDate;
    private double ownedPastDueAmount;
    private Tape tape;

    public VideoRental() {
    }

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

    public double getOwnedPastDueAmount() {
        return ownedPastDueAmount;
    }

    public void setOwnedPastDueAmount(double ownedPastDueAmount) {
        this.ownedPastDueAmount = ownedPastDueAmount;
    }

    public Tape getTape() {
        return tape;
    }

    public void setTape(Tape tape) {
        this.tape = tape;
    }

    /**
     * Calculates the owed past-due amount for the rental.
     *
     * @param currentDate The current date.
     * @return The owed past-due amount rounded to two decimal places.
     */
    public double calculateOwedPastDueAmount(Date currentDate) {
        if (this.returnDate != null) {
            if (this.returnDate.before(this.dueDate) || this.returnDate.equals(this.dueDate)) {
                return 0.0;
            } else {
                long overdueDays = (this.returnDate.getTime() - this.dueDate.getTime()) / (24 * 60 * 60 * 1000);
                double overdueFee = overdueDays * 0.5;
                return Math.round(overdueFee * 100.0) / 100.0;
            }
        } else {
            long overdueDays = (currentDate.getTime() - this.dueDate.getTime()) / (24 * 60 * 60 * 1000);
            double overdueFee = overdueDays * 0.5;
            return Math.round(overdueFee * 100.0) / 100.0;
        }
    }

    /**
     * Calculates the base rental fee for the rental.
     *
     * @param currentDate The current date.
     * @return The base rental fee rounded to two decimal places.
     */
    public double calculateBaseRentalFee(Date currentDate) {
        Date actualReturnDate = (this.returnDate != null) ? this.returnDate : currentDate;
        long rentalDays = (actualReturnDate.getTime() - this.dueDate.getTime()) / (24 * 60 * 60 * 1000) + 1;
        double baseRentalFee = rentalDays * 1.0;
        return Math.round(baseRentalFee * 100.0) / 100.0;
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

    /**
     * Calculates the total price for the rental transaction.
     *
     * @param rentalDate The rental date.
     * @param currentDate The current date.
     * @return The total price rounded to two decimal places.
     */
    public double calculateTotalPrice(Date rentalDate, Date currentDate) {
        double total = 0.0;
        for (VideoRental rental : this.rentals) {
            double baseFee = rental.calculateBaseRentalFee(currentDate);
            double overdueFee = rental.calculateOwedPastDueAmount(currentDate);
            total += baseFee + overdueFee;
        }
        return Math.round(total * 100.0) / 100.0;
    }
}