import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.text.DecimalFormat;

/**
 * Represents a customer in the video rental system.
 */
 class Customer {
    private String id;
    private List<VideoRental> rentals;

    /**
     * Constructs a new Customer with default values.
     */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Adds a video tape rental for this customer.
     * 
     * @param tape The tape to be rented
     * @param currentDate The current date of rental
     * @return true if rental is successful, false otherwise
     */
    public boolean addVedioTapeRental(Tape tape, Date currentDate) {
        // Check if customer has 20 or more rentals
        if (this.rentals.size() >= 20) {
            return false;
        }

        // Check if customer has past due amount
        if (calculateTotalPastDueAmount(currentDate) > 0) {
            return false;
        }

        // Check if tape is available
        if (!tape.isAvailable(currentDate)) {
            return false;
        }

        // Create new rental (7 days rental period)
        LocalDate localCurrentDate = convertDateToLocalDate(currentDate);
        LocalDate dueDate = localCurrentDate.plusDays(7);
        VideoRental rental = new VideoRental(tape, convertLocalDateToDate(dueDate));
        this.rentals.add(rental);
        return true;
    }

    /**
     * Gets a list of unreturned tapes for this customer.
     * 
     * @return List of unreturned tapes
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
     * Calculates the total past due amount for this customer.
     * 
     * @param currentDate The current date to calculate past due amounts
     * @return The total past due amount
     */
    public double calculateTotalPastDueAmount(Date currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            total += rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(total * 100.0) / 100.0;
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

    /**
     * Converts a Date object to a LocalDate object.
     * 
     * @param date The Date object to convert
     * @return The corresponding LocalDate object
     */
    private LocalDate convertDateToLocalDate(Date date) {
        return LocalDate.of(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
    }

    /**
     * Converts a LocalDate object to a Date object.
     * 
     * @param localDate The LocalDate object to convert
     * @return The corresponding Date object
     */
    private Date convertLocalDateToDate(LocalDate localDate) {
        return new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
    }
}

/**
 * Represents a video tape in the rental system.
 */
class Tape {
    private String id;
    private String videoInformation;

    /**
     * Constructs a new Tape with default values.
     */
    public Tape() {
    }

    /**
     * Checks if this tape is available for rental on the given date.
     * 
     * @param currentDate The date to check availability
     * @return true if the tape is available, false otherwise
     */
    public boolean isAvailable(Date currentDate) {
        // This method would need access to all rentals to check if this tape is currently rented
        // For this implementation, we'll assume it's handled by the system
        // In a real system, this would query all rentals to see if this tape is currently rented out
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

/**
 * Represents a video rental transaction.
 */
class VideoRental {
    private Date dueDate;
    private Date returnDate;
    private double ownedPastDueAmount;
    private Tape tape;

    /**
     * Constructs a new VideoRental with default values.
     */
    public VideoRental() {
    }

    /**
     * Constructs a new VideoRental with the specified tape and due date.
     * 
     * @param tape The tape being rented
     * @param dueDate The due date for the rental
     */
    public VideoRental(Tape tape, Date dueDate) {
        this.tape = tape;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.ownedPastDueAmount = 0.0;
    }

    /**
     * Calculates the owed past due amount for this rental.
     * 
     * @param currentDate The current date to calculate the past due amount
     * @return The past due amount
     */
    public double calculateOwedPastDueAmount(Date currentDate) {
        // If already returned, return the owned past due amount
        if (returnDate != null) {
            return ownedPastDueAmount;
        }

        // Calculate overdue days
        LocalDate currentLocalDate = LocalDate.of(currentDate.getYear() + 1900, currentDate.getMonth() + 1, currentDate.getDate());
        LocalDate dueLocalDate = LocalDate.of(dueDate.getYear() + 1900, dueDate.getMonth() + 1, dueDate.getDate());
        
        // If current date is before or equal to due date, no fee
        if (!currentLocalDate.isAfter(dueLocalDate)) {
            return 0.0;
        }
        
        // Calculate overdue days and fee
        long overdueDays = ChronoUnit.DAYS.between(dueLocalDate, currentLocalDate);
        return Math.round(overdueDays * 0.5 * 100.0) / 100.0;
    }

    /**
     * Calculates the base rental fee for this rental.
     * 
     * @param currentDate The current date to calculate the rental period
     * @return The base rental fee
     */
    public double calculateBaseRentalFee(Date currentDate) {
        Date startDate = returnDate != null ? returnDate : currentDate;
        
        // Calculate rental days (at least 1 day)
        LocalDate startLocalDate = LocalDate.of(startDate.getYear() + 1900, startDate.getMonth() + 1, startDate.getDate());
        LocalDate dueLocalDate = LocalDate.of(dueDate.getYear() + 1900, dueDate.getMonth() + 1, dueDate.getDate());
        long rentalDays = Math.max(1, ChronoUnit.DAYS.between(startLocalDate, dueLocalDate));
        
        return rentalDays * 1.0; // $1 per day
    }

    // Getters and setters
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
}

/**
 * Represents a rental transaction containing multiple video rentals.
 */
class RentalTransaction {
    private Date rentalDate;
    private double totalPrice;
    private List<VideoRental> rentals;
    private Customer customer;

    /**
     * Constructs a new RentalTransaction with default values.
     */
    public RentalTransaction() {
        this.rentals = new ArrayList<>();
    }

    /**
     * Calculates the total price for this rental transaction.
     * 
     * @param rentalDate The date when the rental started
     * @param currentDate The current date for calculating the price
     * @return The total price for the transaction
     */
    public double calculateTotalPrice(Date rentalDate, Date currentDate) {
        double total = 0.0;
        for (VideoRental rental : rentals) {
            total += rental.calculateBaseRentalFee(currentDate) + rental.calculateOwedPastDueAmount(currentDate);
        }
        return Math.round(total * 100.0) / 100.0;
    }

    // Getters and setters
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