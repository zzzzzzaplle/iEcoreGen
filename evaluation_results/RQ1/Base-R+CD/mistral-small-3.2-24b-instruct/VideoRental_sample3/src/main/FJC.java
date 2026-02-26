import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        if (rentals.size() >= 20 || calculateTotalPastDueAmount(currentDate) > 0 || !tape.isAvailable(currentDate)) {
            return false;
        }
        VideoRental rental = new VideoRental(tape, currentDate);
        rentals.add(rental);
        return true;
    }

    /**
     * Retrieves a list of all tapes rented by the customer that have not been returned.
     *
     * @return A list of unreturned tapes.
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
     * Calculates the total past-due amount for all rentals of the customer.
     *
     * @param currentDate The current date.
     * @return The total past-due amount rounded to two decimal places.
     */
    public double calculateTotalPastDueAmount(Date currentDate) {
        double totalPastDueAmount = 0.0;
        for (VideoRental rental : rentals) {
            totalPastDueAmount += rental.calculateOwedPastDueAmount(currentDate);
        }
        return roundToTwoDecimalPlaces(totalPastDueAmount);
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

    private double roundToTwoDecimalPlaces(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(value));
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
        // This logic should be implemented based on the actual business rules
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

    public VideoRental(Tape tape, Date rentalDate) {
        this.tape = tape;
        // Assuming the due date is 7 days after the rental date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.dueDate = sdf.parse(sdf.format(new Date(rentalDate.getTime() + 7 * 24 * 60 * 60 * 1000)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates the past-due amount for the rental.
     *
     * @param currentDate The current date.
     * @return The past-due amount rounded to two decimal places.
     */
    public double calculateOwedPastDueAmount(Date currentDate) {
        if (returnDate != null && returnDate.before(dueDate) || returnDate == null && currentDate.before(dueDate)) {
            return 0.0;
        }

        long overdueDays;
        if (returnDate == null) {
            overdueDays = (currentDate.getTime() - dueDate.getTime()) / (24 * 60 * 60 * 1000);
        } else {
            overdueDays = (returnDate.getTime() - dueDate.getTime()) / (24 * 60 * 60 * 1000);
        }

        owedPastDueAmount = overdueDays * 0.5;
        return roundToTwoDecimalPlaces(owedPastDueAmount);
    }

    /**
     * Calculates the base rental fee for the rental.
     *
     * @param currentDate The current date.
     * @return The base rental fee rounded to two decimal places.
     */
    public double calculateBaseRentalFee(Date currentDate) {
        long rentalDays;
        if (returnDate == null) {
            rentalDays = (currentDate.getTime() - dueDate.getTime()) / (24 * 60 * 60 * 1000);
        } else {
            rentalDays = (returnDate.getTime() - dueDate.getTime()) / (24 * 60 * 60 * 1000);
        }

        double baseRentalFee = rentalDays * 1.0;
        return roundToTwoDecimalPlaces(baseRentalFee);
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

    private double roundToTwoDecimalPlaces(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(value));
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
     * @param rentalDate The date of the rental.
     * @param currentDate The current date.
     * @return The total price rounded to two decimal places.
     */
    public double calculateTotalPrice(Date rentalDate, Date currentDate) {
        totalPrice = 0.0;
        for (VideoRental rental : rentals) {
            double baseRentalFee = rental.calculateBaseRentalFee(currentDate);
            double pastDueAmount = rental.calculateOwedPastDueAmount(currentDate);
            totalPrice += baseRentalFee + pastDueAmount;
        }
        return roundToTwoDecimalPlaces(totalPrice);
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

    private double roundToTwoDecimalPlaces(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(value));
    }
}