import java.util.ArrayList;
import java.util.List;
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
     * Adds a video tape rental for this customer after verifying constraints.
     * Checks if customer has less than 20 rentals, no past-due amount, and tape availability.
     *
     * @param tape the tape to be rented
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return true if all checks pass and rental is added; false otherwise
     */
    public boolean addVideoTapeRental(Tape tape, String currentDate) {
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
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(currentDate, formatter);
        rental.setDueDate(date.plusDays(7).format(formatter));
        
        rentals.add(rental);
        return true;
    }

    /**
     * Retrieves all unreturned tapes for this customer.
     *
     * @return list of unreturned tapes; empty list if none found
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
     * Calculates the total past-due amount for all rentals of this customer.
     *
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return total past-due amount rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(String currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
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
     * Checks if this tape is available for rental on the given date.
     * A tape is unavailable if it belongs to any active rental with null return date.
     *
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return true if tape is available; false otherwise
     */
    public boolean isAvailable(String currentDate) {
        // This implementation assumes global rental tracking would be needed
        // Since we don't have access to all rentals, we'll return true by default
        // In a real system, this would query a central rental database
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
     * Calculates the past-due amount for this rental.
     * If return date ≤ due date → overdue amount = 0.
     * If return date > due date → overdue fee = overdue days × $0.5.
     * If return date is null → overdue day = current date – due date.
     *
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return overdue fee rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(String currentDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate due = LocalDate.parse(dueDate, formatter);
        LocalDate current = LocalDate.parse(currentDate, formatter);
        
        LocalDate returnDateObj = returnDate != null ? 
            LocalDate.parse(returnDate, formatter) : current;
        
        if (returnDateObj.isAfter(due)) {
            long overdueDays = ChronoUnit.DAYS.between(due, returnDateObj);
            double fee = overdueDays * 0.5;
            return Math.round(fee * 100.0) / 100.0;
        }
        
        return 0.0;
    }

    /**
     * Calculates the base rental fee for this rental.
     * Base rental fee = rental days × $1 per day.
     * Any partial rental day counts as a full day.
     *
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return base rental fee rounded to two decimal places
     */
    public double calculateBaseRentalFee(String currentDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate rentalDate = LocalDate.parse(getRentalDateFromDueDate(), formatter);
        LocalDate current = LocalDate.parse(currentDate, formatter);
        
        long rentalDays = ChronoUnit.DAYS.between(rentalDate, current);
        if (rentalDays < 1) rentalDays = 1;
        
        double fee = rentalDays * 1.0;
        return Math.round(fee * 100.0) / 100.0;
    }
    
    private String getRentalDateFromDueDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate due = LocalDate.parse(dueDate, formatter);
        return due.minusDays(7).format(formatter);
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
     * Calculates the total price for this rental transaction.
     * Each rental total price = base rental fee + overdue fee.
     *
     * @param rentalDate the rental date in "yyyy-MM-dd" format
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return total price rounded to two decimal places
     */
    public double calculateTotalPrice(String rentalDate, String currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            double baseFee = rental.calculateBaseRentalFee(currentDate);
            double overdueFee = rental.calculateOwedPastDueAmount(currentDate);
            total += baseFee + overdueFee;
        }
        return Math.round(total * 100.0) / 100.0;
    }
}