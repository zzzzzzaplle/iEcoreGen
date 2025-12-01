import java.text.DecimalFormat;
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
     * @param tape The tape to be rented
     * @param currentDate The current date
     * @return true if the rental is processed successfully, false otherwise
     */
    public boolean addVedioTapeRental(Tape tape, Date currentDate) {
        if (this.rentals.size() >= 20 || calculateTotalPastDueAmount(currentDate) > 0 || !tape.isAvailable(currentDate)) {
            return false;
        }
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7); // Assuming a 7-day rental period
        rental.setDueDate(calendar.getTime());
        this.rentals.add(rental);
        return true;
    }

    /**
     * Retrieves a list of all tapes rented by the customer that have not been returned.
     * @return A list of unreturned tapes
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
     * Calculates the total past-due amount for all rentals.
     * @param currentDate The current date
     * @return The total past-due amount rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(Date currentDate) {
        double totalPastDueAmount = 0.0;
        for (VideoRental rental : this.rentals) {
            totalPastDueAmount += rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(totalPastDueAmount * 100.0) / 100.0;
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
     * @param currentDate The current date
     * @return true if the tape is available, false otherwise
     */
    public boolean isAvailable(Date currentDate) {
        for (VideoRental rental : getRentalsForTape()) {
            if (rental.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }

    private List<VideoRental> getRentalsForTape() {
        // This method should be implemented to retrieve all rentals for this tape
        // For the purpose of this example, we return an empty list
        return new ArrayList<>();
    }
}

class VideoRental {
    private Date dueDate;
    private Date returnDate;
    private double owedPastDueAmount;
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

    /**
     * Calculates the past-due amount for the rental.
     * @param currentDate The current date
     * @return The past-due amount rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(Date currentDate) {
        if (this.returnDate != null) {
            if (this.returnDate.compareTo(this.dueDate) <= 0) {
                return 0.0;
            } else {
                long overdueDays = (this.returnDate.getTime() - this.dueDate.getTime()) / (1000 * 60 * 60 * 24);
                double overdueFee = overdueDays * 0.5;
                return Math.round(overdueFee * 100.0) / 100.0;
            }
        } else {
            long overdueDays = (currentDate.getTime() - this.dueDate.getTime()) / (1000 * 60 * 60 * 24);
            double overdueFee = overdueDays * 0.5;
            return Math.round(overdueFee * 100.0) / 100.0;
        }
    }

    /**
     * Calculates the base rental fee for the rental.
     * @param currentDate The current date
     * @return The base rental fee rounded to two decimal places
     */
    public double calculateBaseRentalFee(Date currentDate) {
        long rentalDays;
        if (this.returnDate != null) {
            rentalDays = (this.returnDate.getTime() - this.dueDate.getTime()) / (1000 * 60 * 60 * 24);
        } else {
            rentalDays = (currentDate.getTime() - this.dueDate.getTime()) / (1000 * 60 * 60 * 24);
        }
        // Add 1 to include the partial day
        rentalDays += 1;
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
     * @param rentalDate The rental date
     * @param currentDate The current date
     * @return The total price rounded to two decimal places
     */
    public double calculateTotalPrice(Date rentalDate, Date currentDate) {
        double totalPrice = 0.0;
        for (VideoRental rental : this.rentals) {
            double baseRentalFee = rental.calculateBaseRentalFee(currentDate);
            double pastDueAmount = rental.calculateOwedPastDueAmount(currentDate);
            totalPrice += baseRentalFee + pastDueAmount;
        }
        this.totalPrice = Math.round(totalPrice * 100.0) / 100.0;
        return this.totalPrice;
    }
}