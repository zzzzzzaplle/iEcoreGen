import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
     * Adds a video tape rental for this customer after verifying constraints
     * 
     * @param tape The tape to be rented
     * @param currentDate The current date in "yyyy-MM-dd" format
     * @return true if all checks pass and rental is processed, false otherwise
     */
    public boolean addVideoTapeRental(Tape tape, String currentDate) {
        // Check if customer has less than 20 rentals
        if (rentals.size() >= 20) {
            return false;
        }

        // Check if customer has any past due amount
        if (calculateTotalPastDueAmount(currentDate) > 0) {
            return false;
        }

        // Check if tape is available
        if (!tape.isAvailable(currentDate)) {
            return false;
        }

        // Create new rental with due date 7 days from current date
        LocalDate dueDate = LocalDate.parse(currentDate).plusDays(7);
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        rentals.add(rental);
        return true;
    }

    /**
     * Retrieves a list of all tape IDs rented by this customer that have not been returned
     * 
     * @return List of unreturned tape IDs, empty list if no active rentals
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
     * Calculates the total past due amount for all rentals of this customer
     * 
     * @param currentDate The current date in "yyyy-MM-dd" format
     * @return Total past due amount rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(String currentDate) {
        double totalPastDue = 0.0;
        for (VideoRental rental : rentals) {
            totalPastDue += rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(totalPastDue * 100.0) / 100.0;
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
     * Checks if this tape is available for rental on the given date
     * 
     * @param currentDate The current date in "yyyy-MM-dd" format
     * @return true if the tape is available, false otherwise
     */
    public boolean isAvailable(String currentDate) {
        // This method would typically check against a rental database
        // For this implementation, we assume all tapes are available unless rented
        // In a real system, this would query active rentals
        return true;
    }
}

class VideoRental {
    private String dueDate;
    private String returnDate;
    private double ownedPastDueAmount;
    private Tape tape;

    public VideoRental() {
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
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
     * Calculates the overdue amount for this video rental
     * 
     * @param currentDate The current date in "yyyy-MM-dd" format
     * @return Overdue fee rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(String currentDate) {
        LocalDate due = LocalDate.parse(dueDate);
        LocalDate returnOrCurrent = returnDate != null ? 
            LocalDate.parse(returnDate) : LocalDate.parse(currentDate);
        
        if (returnOrCurrent.isAfter(due)) {
            long overdueDays = ChronoUnit.DAYS.between(due, returnOrCurrent);
            double overdueFee = overdueDays * 0.5;
            return Math.round(overdueFee * 100.0) / 100.0;
        }
        return 0.0;
    }

    /**
     * Calculates the base rental fee for this video rental
     * 
     * @param currentDate The current date in "yyyy-MM-dd" format
     * @return Base rental fee rounded to two decimal places
     */
    public double calculateBaseRentalFee(String currentDate) {
        // Base rental fee is $1 per day, partial days count as full days
        // For simplicity, we'll assume a standard 7-day rental period
        long rentalDays = 7;
        double baseFee = rentalDays * 1.0;
        return Math.round(baseFee * 100.0) / 100.0;
    }
}

class RentalTransaction {
    private String rentalDate;
    private double totalPrice;
    private List<VideoRental> rentals;
    private Customer customer;

    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
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
     * Calculates the total price for this rental transaction
     * 
     * @param rentalDate The rental date in "yyyy-MM-dd" format
     * @param currentDate The current date in "yyyy-MM-dd" format
     * @return Total price (base rental fees + overdue fees) rounded to two decimal places
     */
    public double calculateTotalPrice(String rentalDate, String currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            double baseFee = rental.calculateBaseRentalFee(currentDate);
            double overdueFee = rental.calculateOwedPastDueAmount(currentDate);
            total += baseFee + overdueFee;
        }
        this.totalPrice = Math.round(total * 100.0) / 100.0;
        return this.totalPrice;
    }
}