import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class Customer {
    private String accountNumber;
    private String idCardBarcode;
    private List<Rental> rentals;

    /**
     * Constructs a new Customer with default values.
     */
    public Customer() {
        this.rentals = new ArrayList<>();
    }

    /**
     * @return the accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return the idCardBarcode
     */
    public String getIdCardBarcode() {
        return idCardBarcode;
    }

    /**
     * @param idCardBarcode the idCardBarcode to set
     */
    public void setIdCardBarcode(String idCardBarcode) {
        this.idCardBarcode = idCardBarcode;
    }

    /**
     * @return the rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * @param rentals the rentals to set
     */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Adds a rental to the customer's rentals list.
     * @param rental the rental to add
     */
    public void addRental(Rental rental) {
        this.rentals.add(rental);
    }

    /**
     * Checks if the customer has any past-due rentals.
     * @return true if the customer has past-due rentals, false otherwise
     */
    public boolean hasPastDueRentals() {
        for (Rental rental : rentals) {
            if (rental.getOverdueFee() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the total number of active rentals for the customer.
     * @return the total number of active rentals
     */
    public int getActiveRentalsCount() {
        int count = 0;
        for (Rental rental : rentals) {
            if (rental.getReturnDate() == null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Lists all unreturned tapes for the customer.
     * @return a list of unreturned tape IDs
     */
    public List<String> listUnreturnedTapes() {
        List<String> unreturnedTapes = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getReturnDate() == null) {
                unreturnedTapes.add(rental.getVideoTape().getBarcodeId());
            }
        }
        return unreturnedTapes;
    }
}

class Rental {
    private VideoTape videoTape;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double overdueFee;

    /**
     * Constructs a new Rental with default values.
     */
    public Rental() {
    }

    /**
     * @return the videoTape
     */
    public VideoTape getVideoTape() {
        return videoTape;
    }

    /**
     * @param videoTape the videoTape to set
     */
    public void setVideoTape(VideoTape videoTape) {
        this.videoTape = videoTape;
    }

    /**
     * @return the rentalDate
     */
    public LocalDate getRentalDate() {
        return rentalDate;
    }

    /**
     * @param rentalDate the rentalDate to set
     */
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    /**
     * @return the dueDate
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return the returnDate
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * @param returnDate the returnDate to set
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * @return the overdueFee
     */
    public double getOverdueFee() {
        return overdueFee;
    }

    /**
     * @param overdueFee the overdueFee to set
     */
    public void setOverdueFee(double overdueFee) {
        this.overdueFee = overdueFee;
    }

    /**
     * Calculates the overdue fee for the rental.
     */
    public void calculateOverdueFee() {
        if (returnDate != null) {
            if (returnDate.isBefore(dueDate) || returnDate.isEqual(dueDate)) {
                overdueFee = 0;
            } else {
                long overdueDays = returnDate.toEpochDay() - dueDate.toEpochDay();
                overdueFee = overdueDays * 0.5;
            }
        } else {
            long overdueDays = LocalDate.now().toEpochDay() - dueDate.toEpochDay();
            overdueFee = overdueDays * 0.5;
        }
        overdueFee = Math.round(overdueFee * 100.0) / 100.0;
    }

    /**
     * Calculates the total price for the rental.
     * @return the total price rounded to two decimal places
     */
    public double calculateTotalPrice() {
        long rentalDays = 0;
        if (returnDate != null) {
            rentalDays = returnDate.toEpochDay() - rentalDate.toEpochDay();
        } else {
            rentalDays = LocalDate.now().toEpochDay() - rentalDate.toEpochDay();
        }
        if (rentalDays < 1) {
            rentalDays = 1;
        }
        double baseRentalFee = rentalDays * 1.0;
        double totalPrice = baseRentalFee + overdueFee;
        return Math.round(totalPrice * 100.0) / 100.0;
    }
}

class VideoTape {
    private String barcodeId;
    private String title;
    private boolean isAvailable;

    /**
     * Constructs a new VideoTape with default values.
     */
    public VideoTape() {
    }

    /**
     * @return the barcodeId
     */
    public String getBarcodeId() {
        return barcodeId;
    }

    /**
     * @param barcodeId the barcodeId to set
     */
    public void setBarcodeId(String barcodeId) {
        this.barcodeId = barcodeId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the isAvailable
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * @param isAvailable the isAvailable to set
     */
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    /**
     * Checks if the video tape is available for rental on the given date.
     * @param currentDate the current date to check availability
     * @return true if the tape is available, false otherwise
     */
    public boolean isAvailable(LocalDate currentDate) {
        // Check if the tape is part of any active rental
        for (Rental rental : getRentalsForTape()) {
            if (rental.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets all rentals for the video tape.
     * @return a list of rentals for the tape
     */
    private List<Rental> getRentalsForTape() {
        // This method should be implemented to retrieve rentals for the tape
        // For example, from a database or a list of all rentals
        return new ArrayList<>();
    }
}

class RentalService {
    /**
     * Adds a video tape rental for a customer.
     * @param customer the customer renting the tape
     * @param videoTape the video tape to rent
     * @return true if the rental is processed successfully, false otherwise
     */
    public boolean addVideoTapeRental(Customer customer, VideoTape videoTape) {
        if (customer.getActiveRentalsCount() >= 20 || customer.hasPastDueRentals()) {
            return false;
        }
        if (!videoTape.isAvailable(LocalDate.now())) {
            return false;
        }

        Rental rental = new Rental();
        rental.setVideoTape(videoTape);
        rental.setRentalDate(LocalDate.now());
        rental.setDueDate(LocalDate.now().plusDays(7)); // Assuming a 7-day rental period
        rental.calculateOverdueFee();

        customer.addRental(rental);
        videoTape.setAvailable(false);

        return true;
    }

    /**
     * Calculates the total price for a customer's rental transaction.
     * @param customer the customer
     * @return the total price rounded to two decimal places
     */
    public double calculateTotalPriceForTransaction(Customer customer) {
        double totalPrice = 0.0;
        for (Rental rental : customer.getRentals()) {
            if (rental.getReturnDate() == null) {
                totalPrice += rental.calculateTotalPrice();
            }
        }
        return Math.round(totalPrice * 100.0) / 100.0;
    }
}