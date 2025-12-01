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
     * Verifies that the customer has less than 20 rentals, has no past-due amount,
     * and that the tape is available for rental on the given date.
     *
     * @param tape the tape to be rented
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return true if all checks pass and rental is added successfully, false otherwise
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
     * Retrieves a list of all tape IDs rented by this customer that have not been returned.
     *
     * @return list of unreturned tape IDs, empty list if no active rentals
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

    private String calculateDueDate(String currentDate) {
        LocalDate date = LocalDate.parse(currentDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate dueDate = date.plusDays(7); // Assuming 7-day rental period
        return dueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}

class Tape {
    private String id;
    private String videoInformation;
    private List<VideoRental> rentals;

    public Tape() {
        this.rentals = new ArrayList<>();
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

    public List<VideoRental> getRentals() {
        return rentals;
    }

    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Checks if this tape is available for rental on the given date.
     * A tape is unavailable if it belongs to any active rental with null return date.
     *
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return true if the tape is available, false otherwise
     */
    public boolean isAvailable(String currentDate) {
        for (VideoRental rental : rentals) {
            if (rental.getReturnDate() == null) {
                return false;
            }
        }
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
     * If return date is null, uses current date to calculate overdue days.
     * Overdue fee is $0.50 per day.
     *
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return overdue fee rounded to two decimal places
     */
    public double calculateOwedPastDueAmount(String currentDate) {
        LocalDate due = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate returnOrCurrent = returnDate != null ? 
            LocalDate.parse(returnDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : 
            LocalDate.parse(currentDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        if (returnOrCurrent.isAfter(due)) {
            long overdueDays = ChronoUnit.DAYS.between(due, returnOrCurrent);
            double fee = overdueDays * 0.5;
            return Math.round(fee * 100.0) / 100.0;
        }
        return 0.0;
    }

    /**
     * Calculates the base rental fee for this rental.
     * Base rental fee is $1 per day, with partial days counting as full days.
     *
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return base rental fee rounded to two decimal places
     */
    public double calculateBaseRentalFee(String currentDate) {
        LocalDate rentalDate = returnDate != null ? 
            LocalDate.parse(returnDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : 
            LocalDate.parse(currentDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate due = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        long rentalDays = ChronoUnit.DAYS.between(due.minusDays(7), due); // Assuming 7-day base rental
        double fee = rentalDays * 1.0;
        return Math.round(fee * 100.0) / 100.0;
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
     * Total price includes base rental fees and overdue fees for all rentals.
     *
     * @param rentalDate the rental date in "yyyy-MM-dd" format
     * @param currentDate the current date in "yyyy-MM-dd" format
     * @return total price rounded to two decimal places
     */
    public double calculateTotalPrice(String rentalDate, String currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            total += rental.calculateBaseRentalFee(currentDate);
            total += rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(total * 100.0) / 100.0;
    }
}