import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

class Customer {
    private String id;
    private List<VideoRental> rentals;

    /**
     * Default constructor for Customer
     */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Adds a video tape rental for this customer after verifying constraints
     * @param tape the tape to be rented
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return true if rental is successfully added, false otherwise
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
        rental.setDueDate(calculateDueDate(currentDate));
        rentals.add(rental);
        return true;
    }

    /**
     * Retrieves all unreturned tapes for this customer
     * @return list of unreturned tapes, empty list if none found
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
     * Calculates the total past due amount for all rentals
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return total past due amount rounded to two decimal places
     */
    public double calculateTotalPastDueAmount(String currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            total += rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Helper method to calculate due date (7 days from rental date)
     * @param rentalDate the rental date in "yyyy-MM-dd" format
     * @return due date in "yyyy-MM-dd" format
     */
    private String calculateDueDate(String rentalDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(rentalDate, formatter);
        LocalDate dueDate = date.plusDays(7);
        return dueDate.format(formatter);
    }

    // Getters and setters
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

    /**
     * Default constructor for Tape
     */
    public Tape() {
    }

    /**
     * Checks if the tape is available for rental on the given date
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return true if available, false otherwise
     */
    public boolean isAvailable(String currentDate) {
        // Availability logic would typically check against a rental database
        // For this implementation, we assume all tapes are available unless specified otherwise
        return true;
    }

    // Getters and setters
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
    private String dueDate;
    private String returnDate;
    private double ownedPastDueAmount;
    private Tape tape;

    /**
     * Default constructor for VideoRental
     */
    public VideoRental() {
    }

    /**
     * Calculates the overdue amount for this rental
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return overdue amount rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(String currentDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate due = LocalDate.parse(dueDate, formatter);
        LocalDate current = LocalDate.parse(currentDate, formatter);
        
        if (returnDate != null) {
            LocalDate returnDt = LocalDate.parse(returnDate, formatter);
            if (returnDt.isAfter(due)) {
                long overdueDays = ChronoUnit.DAYS.between(due, returnDt);
                double fee = overdueDays * 0.5;
                return Math.round(fee * 100.0) / 100.0;
            }
        } else {
            if (current.isAfter(due)) {
                long overdueDays = ChronoUnit.DAYS.between(due, current);
                double fee = overdueDays * 0.5;
                return Math.round(fee * 100.0) / 100.0;
            }
        }
        return 0.0;
    }

    /**
     * Calculates the base rental fee for this rental
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return base rental fee rounded to two decimal places
     */
    public double calculateBaseRentalFee(String currentDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate rentalDate = LocalDate.parse(currentDate, formatter);
        LocalDate due = LocalDate.parse(dueDate, formatter);
        
        long rentalDays = ChronoUnit.DAYS.between(rentalDate, due);
        if (rentalDays < 1) rentalDays = 1;
        
        double fee = rentalDays * 1.0;
        return Math.round(fee * 100.0) / 100.0;
    }

    // Getters and setters
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
}

class RentalTransaction {
    private String rentalDate;
    private double totalPrice;
    private List<VideoRental> rentals;
    private Customer customer;

    /**
     * Default constructor for RentalTransaction
     */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Calculates the total price for all rentals in this transaction
     * @param rentalDate the rental date in "yyyy-MM-dd" format
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return total price rounded to two decimal places
     */
    public double calculateTotalPrice(String rentalDate, String currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            total += rental.calculateBaseRentalFee(rentalDate);
            total += rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(total * 100.0) / 100.0;
    }

    // Getters and setters
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
}