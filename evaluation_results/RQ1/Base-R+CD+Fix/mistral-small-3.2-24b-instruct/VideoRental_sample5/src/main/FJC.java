import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class Customer {
    private String id;
    private List<VideoRental> rentals;

    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Adds a video tape rental for the customer.
     * @param tape The tape to be rented.
     * @param currentDate The current date.
     * @return true if the rental is processed successfully, false otherwise.
     */
    public boolean addVideoTapeRental(Tape tape, LocalDate currentDate) {
        if (this.rentals.size() >= 20) {
            return false;
        }
        double pastDueAmount = calculateTotalPastDueAmount(currentDate);
        if (pastDueAmount > 0) {
            return false;
        }
        if (!tape.isAvailable(currentDate)) {
            return false;
        }
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(currentDate.plusDays(7)); // Assuming a 7-day rental period
        this.rentals.add(rental);
        return true;
    }

    /**
     * Retrieves a list of all tapes rented by the customer that have not been returned.
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
     * Calculates the total past-due amount for all rentals.
     * @param currentDate The current date.
     * @return The total past-due amount.
     */
    public double calculateTotalPastDueAmount(LocalDate currentDate) {
        double totalPastDueAmount = 0.0;
        for (VideoRental rental : this.rentals) {
            totalPastDueAmount += rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(totalPastDueAmount * 100.0) / 100.0;
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

    public Tape() {
    }

    /**
     * Checks the availability of the tape for a given date.
     * @param currentDate The current date.
     * @return true if the tape is available, false otherwise.
     */
    public boolean isAvailable(LocalDate currentDate) {
        for (VideoRental rental : getRentalsForTape(currentDate)) {
            if (rental.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }

    // Helper method to get rentals for the tape
    private List<VideoRental> getRentalsForTape(LocalDate currentDate) {
        List<VideoRental> rentalsForTape = new ArrayList<>();
        // In a real implementation, this would query the database or some data structure
        // to find rentals for this tape. For simplicity, we return an empty list.
        return rentalsForTape;
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
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double owedPastDueAmount;
    private Tape tape;

    public VideoRental() {
    }

    /**
     * Calculates the owed past-due amount for the rental.
     * @param currentDate The current date.
     * @return The owed past-due amount.
     */
    public double calculateOwedPastDueAmount(LocalDate currentDate) {
        if (this.returnDate != null) {
            if (this.returnDate.isBefore(this.dueDate) || this.returnDate.isEqual(this.dueDate)) {
                return 0.0;
            } else {
                long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(this.dueDate, this.returnDate);
                return Math.round(overdueDays * 0.5 * 100.0) / 100.0;
            }
        } else {
            long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(this.dueDate, currentDate);
            return Math.round(overdueDays * 0.5 * 100.0) / 100.0;
        }
    }

    /**
     * Calculates the base rental fee for the rental.
     * @param currentDate The current date.
     * @return The base rental fee.
     */
    public double calculateBaseRentalFee(LocalDate currentDate) {
        LocalDate rentalDate = this.dueDate.minusDays(7); // Assuming a 7-day rental period
        long rentalDays = java.time.temporal.ChronoUnit.DAYS.between(rentalDate, currentDate);
        return rentalDays * 1.0;
    }

    // Getters and setters
    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
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
    private LocalDate rentalDate;
    private double totalPrice;
    private List<VideoRental> rentals;
    private Customer customer;

    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Calculates the total price for the rental transaction.
     * @param rentalDate The rental date.
     * @param currentDate The current date.
     * @return The total price.
     */
    public double calculateTotalPrice(LocalDate rentalDate, LocalDate currentDate) {
        double total = 0.0;
        for (VideoRental rental : this.rentals) {
            double baseRentalFee = rental.calculateBaseRentalFee(currentDate);
            double owedPastDueAmount = rental.calculateOwedPastDueAmount(currentDate);
            total += baseRentalFee + owedPastDueAmount;
        }
        return Math.round(total * 100.0) / 100.0;
    }

    // Getters and setters
    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
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